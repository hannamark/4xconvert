/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.convert.StudySiteContactConverter;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.service.search.StudySiteSortCriterion;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.AssignedIdentifierEnum;
import gov.nih.nci.pa.util.CorrelationUtils;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySiteBeanLocal extends AbstractRoleIsoService<StudySiteDTO, StudySite, StudySiteConverter> implements
        StudySiteServiceLocal {

    private static final Logger LOG = Logger.getLogger(ArmBeanLocal.class);

    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;

    /**
     * @param dto StudySiteDTO
     * @return StudySiteDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteDTO create(StudySiteDTO dto) throws PAException {
        StudySiteDTO createDto = businessRules(dto);
        createDto.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        StudySiteDTO resultDto = super.create(createDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * @param dto StudySiteDTO
     * @return StudySiteDTO
     * @throws PAException PAException
     */
    @Override
    public StudySiteDTO update(StudySiteDTO dto) throws PAException {
        StudySiteDTO updateDto = businessRules(dto);
        getStatusCode(updateDto);
        StudySiteDTO resultDto = super.update(updateDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * creates a new record of studyprotocol by changing to new studyprotocol identifier.
     * @param fromStudyProtocolIi from where the study protocol objects to be copied
     * @param toStudyProtocolIi to where the study protocol objects to be copied
     * @return map
     * @throws PAException on error
     */
    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        List<StudySiteDTO> dtos = getByStudyProtocol(fromStudyProtocolIi);
        Map<Ii, Ii> map = new HashMap<Ii, Ii>();
        List<StudySiteContactDTO> spcDtos = null;
        List<StudySiteAccrualStatusDTO> accDtos = null;
        Session session = PaHibernateUtil.getCurrentSession();
        StudySite bo = null;
        Ii from = null;
        Ii to = null;
        StudySiteContactConverter ssc = new StudySiteContactConverter();

        for (StudySiteDTO dto : dtos) {
            from = dto.getIdentifier();
            to = new Ii();
            dto.setIdentifier(null);
            dto.setStudyProtocolIdentifier(toStudyProtocolIi);
            bo = convertFromDtoToDomain(dto);
            session.save(bo);
            to.setIdentifierName(from.getIdentifierName());
            to.setRoot(from.getRoot());
            to.setExtension(bo.getId().toString());
            // create study contact
            spcDtos = PaRegistry.getStudySiteContactService().getByStudySite(from);
            for (StudySiteContactDTO spcDto : spcDtos) {
                spcDto.setIdentifier(null);
                spcDto.setStudySiteIi(to);
                spcDto.setStudyProtocolIdentifier(toStudyProtocolIi);
                session.save(ssc.convertFromDtoToDomain(spcDto));
            }
            // create study accrual status
            if (StudySiteFunctionalCode.TREATING_SITE.getCode().equals(dto.getFunctionalCode().getCode())) {
                accDtos = studySiteAccrualStatusService.getStudySiteAccrualStatusByStudySite(from);
                for (StudySiteAccrualStatusDTO accDto : accDtos) {
                    accDto.setIdentifier(null);
                    accDto.setStudySiteIi(to);
                    session.save(new StudySiteAccrualStatusConverter().convertFromDtoToDomain(accDto));
                }
            }
            map.put(from, to);
        }
        return map;
    }

    private StudySiteDTO businessRules(StudySiteDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && PAUtil.isIiNull(dto.getResearchOrganizationIi())
            && PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Either healthcare facility or research organization or Oversight committee"
                + " must be set.");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            throw new PAException("Healthcare facility and research organization cannot both be set.");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("Healthcare facility and oversight committee cannot both be set.");
        }
        if (!PAUtil.isIiNull(dto.getResearchOrganizationIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            throw new PAException("research organization and oversight committee cannot both be set.");
        }
        final String approvalStatusCodeString = CdConverter.convertCdToString(dto.getReviewBoardApprovalStatusCode());
        ReviewBoardApprovalStatusCode code = ReviewBoardApprovalStatusCode.getByCode(approvalStatusCodeString);
        if (code != null) {
            String approvalNumber = StConverter.convertToString(dto.getReviewBoardApprovalNumber());
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().toString().equals(code.getCode().toString())
                && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                throw new PAException("Review board approval number must be set for status '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "'.");
            }
            if (ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().toString().equals(code.getCode().toString())
                || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().toString()
                                                                  .equals(code.getCode().toString())
                || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().toString()
                                                                 .equals(code.getCode().toString())
                && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                dto.setReviewBoardApprovalNumber(StConverter.convertToSt(null));
            }
            if (PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
                throw new PAException("Oversight committee (board) must be set when review board approval status is '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "' or '"
                    + ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getDisplayName() + "'.");
            }
        } else {
            dto.setOversightCommitteeIi(null);
            dto.setReviewBoardApprovalDate(null);
            dto.setReviewBoardApprovalNumber(null);
        }
        enforceNoDuplicate(dto);
        enforceNoDuplicateTrial(dto);
        return dto;
    }

    private String getOrganizationId(StudySiteDTO dto) {
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            return (PAUtil.isIiNull(dto.getResearchOrganizationIi())
                ? IiConverter.convertToString(dto.getOversightCommitteeIi())
                : IiConverter.convertToString(dto.getResearchOrganizationIi()));
        }
        return IiConverter.convertToString(dto.getHealthcareFacilityIi());
    }

    private String getFunctionalCode(StudySiteDTO dto) {
        return (PAUtil.isCdNull(dto.getFunctionalCode())) ? "" : CdConverter.convertCdToString(dto.getFunctionalCode());
    }

    private void enforceNoDuplicate(StudySiteDTO dto) throws PAException {
        String newOrgId = getOrganizationId(dto);
        String newFunction = getFunctionalCode(dto);
        List<StudySiteDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
        for (StudySiteDTO sp : spList) {
            boolean sameSite = IiConverter.convertToLong(sp.getIdentifier())
                                          .equals(IiConverter.convertToLong(dto.getIdentifier()));
            boolean sameOrg = newOrgId.equals(getOrganizationId(sp));
            boolean sameFunction = newFunction.equals(getFunctionalCode(sp));
            if (!sameSite && sameOrg && sameFunction) {
                throw new PADuplicateException("This organization has already been entered as a '" + newFunction
                    + "' for this study.");
            }
        }

    }

    private void enforceOnlyOneOversightCommittee(StudySiteDTO dto) throws PAException {
        if (!PAUtil.isCdNull(dto.getReviewBoardApprovalStatusCode())) {
            List<StudySiteDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudySiteDTO sp : spList) {
                if (!IiConverter.convertToLong(dto.getIdentifier())
                                .equals(IiConverter.convertToLong(sp.getIdentifier()))
                    && !PAUtil.isCdNull(sp.getReviewBoardApprovalStatusCode())) {
                    sp.setReviewBoardApprovalStatusCode(null);
                    update(sp);
                }
            }
        }
    }

    /**
     *
     * @param dto dto
     * @throws PAException e
     */
    private void enforceNoDuplicateTrial(StudySiteDTO dto) throws PAException {
        Session session = null;
        List<StudySite> queryList = new ArrayList<StudySite>();
        session = PaHibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = " select spart " + " from StudySite spart " + " join spart.researchOrganization as ro "
            + " join spart.studyProtocol as sp " + " join sp.documentWorkflowStatuses as dws  "
            + " where spart.localStudyProtocolIdentifier = :localStudyProtocolIdentifier "
            + " and spart.functionalCode = '" + StudySiteFunctionalCode.getByCode(dto.getFunctionalCode().getCode())
            + "'" + " and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'" + " and sp.statusCode ='"
            + ActStatusCode.ACTIVE + "'" + " and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
            + "  where dws.studyProtocol = dws1.studyProtocol ) or dws.id is null ) " + " and ro.id = :orgIdentifier";

        LOG.info("query study_Site = " + hql + ".  ");
        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("localStudyProtocolIdentifier",
                           StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()));
        if (PAUtil.isIiNotNull(dto.getResearchOrganizationIi())
            && IiConverter.RESEARCH_ORG_IDENTIFIER_NAME.equalsIgnoreCase(dto.getResearchOrganizationIi()
                                                                            .getIdentifierName())) {
            ResearchOrganization ro = new CorrelationUtils().getStructuralRoleByIi(dto.getResearchOrganizationIi());
            query.setParameter("orgIdentifier", ro.getId());
        } else {
            query.setParameter("orgIdentifier", IiConverter.convertToLong(dto.getResearchOrganizationIi()));
        }
        // step 3: query the result
        queryList = query.list();
        if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())) {
            for (StudySite sp : queryList) {
                // When create DTO get Id will be null and if queryList is having value then its duplicate
                // When update check if the record is same if not then throw ex
                if ((dto.getIdentifier() == null)
                    || (!String.valueOf(sp.getId()).equals(dto.getIdentifier().getExtension()))) {
                    throw new PAValidationException("Duplicate Trial Submission: A trial exists in the system with the "
                        + "same Lead Organization Trial Identifier for the selected Lead Organization");
                }
            }
        }
        if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())
            && CollectionUtils.isNotEmpty(queryList)) {
            for (StudySite sp : queryList) {
                // When create DTO get Id will be null and if queryList is having value then its duplicate
                // When update check if the record is same if not then throw ex
                if ((dto.getIdentifier() == null)
                    || (!String.valueOf(sp.getId()).equals(dto.getIdentifier().getExtension()))) {
                        throw new PAValidationException("Duplicate Trial Submission: A trial exists in the system with "
                                + "the same " + getIdentifierName(sp));
                }
            }
        }
    }

    private String getIdentifierName(StudySite sp) {
        StringBuffer sbuf = new StringBuffer();
        String spOrgName = sp.getResearchOrganization().getOrganization().getName();
        if (AssignedIdentifierEnum.NCT.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.NCT.getCode());
        } else if (AssignedIdentifierEnum.DCP.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.DCP.getCode());
        } else if (AssignedIdentifierEnum.CTEP.getDisplayValue().equals(spOrgName)) {
            sbuf.append(AssignedIdentifierEnum.CTEP.getCode());
        }

        return sbuf.append(" Trial Identifier").toString();
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams) throws PAException,
        TooManyResultsException {
        if (dto == null) {
            throw new PAException("StudySiteDTO should not be null");
        }

        StudySite criteria = Converters.get(StudySiteConverter.class).convertFromDtoToDomain(dto);

        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        PageSortParams<StudySite> params = new PageSortParams<StudySite>(maxLimit, pagingParams.getOffset(),
                StudySiteSortCriterion.STUDY_SITE_ID, false);
        List<StudySite> studySiteList = search(new AnnotatedBeanSearchCriteria<StudySite>(criteria), params);

        if (studySiteList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        return convertFromDomainToDTOs(studySiteList);
    }

    /**
     * @param dto dto
     * @throws PAException e
     */
    @Override
    public void validate(StudySiteDTO dto) throws PAException {
        enforceNoDuplicateTrial(dto);
    }

    private void getStatusCode(StudySiteDTO dto) throws PAException {
        PAServiceUtils paServiceUtil = new PAServiceUtils();
        StructuralRole sr = null;
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            Ii hcfIi = IiConverter.convertToPoHealthCareFacilityIi(dto.getHealthcareFacilityIi().getExtension());
            sr = paServiceUtil.getStructuralRole(hcfIi);
        }
        if (!PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            Ii roIi = IiConverter.convertToPoResearchOrganizationIi(dto.getResearchOrganizationIi().getExtension());
            sr = paServiceUtil.getStructuralRole(roIi);
        }
        if (!PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            Ii ocIi = IiConverter.convertToPoOversightCommitteeIi(dto.getOversightCommitteeIi().getExtension());
            sr = paServiceUtil.getStructuralRole(ocIi);
        }
        if (sr != null) {
            dto.setStatusCode(getFunctionalRoleStatusCode(CdConverter.convertStringToCd(sr.getStatusCode().getCode()),
                                                          ActStatusCode.ACTIVE));
        }

    }

    /**
     * getStudySiteIiByTrialAndPoHcfIi.
     * @param studyProtocolIi ii
     * @param poHcfIi ii
     * @return ii
     * @throws EntityValidationException when error
     * @throws CurationException when error
     * @throws PAException when error
     * @throws TooManyResultsException when error
     */
    public Ii getStudySiteIiByTrialAndPoHcfIi(Ii studyProtocolIi, Ii poHcfIi) throws EntityValidationException,
            CurationException, PAException, TooManyResultsException {
        StudySiteDTO criteria = new StudySiteDTO();
        criteria.setStudyProtocolIdentifier(studyProtocolIi);
        // get the pa hcf from the po hcf
        StructuralRole strRl = new CorrelationUtils().getStructuralRoleByIi(poHcfIi);
        Ii myIi = IiConverter.convertToPoHealthCareFacilityIi(strRl.getId().toString());
        criteria.setHealthcareFacilityIi(myIi);
        LimitOffset limit = new LimitOffset(1, 0);
        List<StudySiteDTO> freshStudySiteDTOList = search(criteria, limit);
        return freshStudySiteDTOList.get(0).getIdentifier();
    }
}
