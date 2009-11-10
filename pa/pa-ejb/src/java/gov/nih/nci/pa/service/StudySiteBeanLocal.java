/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.convert.StudySiteContactConverter;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

/**
 * @author asharma
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySiteBeanLocal  extends AbstractRoleIsoService<StudySiteDTO, StudySite, StudySiteConverter>
implements StudySiteServiceLocal {
 
  @EJB
  StudySiteContactServiceLocal studySiteContactService = null;
  @EJB
  StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
  
  
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
  public StudySiteDTO update(StudySiteDTO dto)
          throws PAException {
      StudySiteDTO updateDto = businessRules(dto);
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
  public Map<Ii , Ii> copy(Ii fromStudyProtocolIi , Ii toStudyProtocolIi) throws PAException {
      List<StudySiteDTO> dtos = getByStudyProtocol(fromStudyProtocolIi);
      Map<Ii , Ii> map = new HashMap<Ii , Ii>();
      List<StudySiteContactDTO> spcDtos = null;
      List<StudySiteAccrualStatusDTO> accDtos = null;
      Session session = HibernateUtil.getCurrentSession();
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
          spcDtos = studySiteContactService.getByStudySite(from);
          if (spcDtos != null && !spcDtos.isEmpty()) {
              for (StudySiteContactDTO spcDto : spcDtos) {
                  spcDto.setIdentifier(null);
                  spcDto.setStudySiteIi(to);
                  spcDto.setStudyProtocolIdentifier(toStudyProtocolIi);
                  session.save(ssc.convertFromDtoToDomain(spcDto));
              }
          }
          // create study accrual status
          if (StudySiteFunctionalCode.TREATING_SITE.getCode().equals(dto.getFunctionalCode().getCode())) { 
                  accDtos = studySiteAccrualStatusService.getStudySiteAccrualStatusByStudySite(from);
                  for (StudySiteAccrualStatusDTO accDto : accDtos) {
                      accDto.setIdentifier(null);
                      accDto.setStudySiteIi(to);
                      session.save(StudySiteAccrualStatusConverter.convertFromDtoToDomain(accDto));
                  }
          }
          map.put(from, to);
      }
      return map;
  }
  
  @SuppressWarnings("PMD.NPathComplexity")
  private StudySiteDTO businessRules(StudySiteDTO dto) throws PAException {
      if (PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && PAUtil.isIiNull(dto.getResearchOrganizationIi()) 
              && PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
          throw new PAException("Either healthcare facility or research organization or Oversight committee" 
                  + " must be set.  ");
      }
      if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
          throw new PAException("Healthcare facility , research organization cannot both be set.  ");
      }
      if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
          throw new PAException("Healthcare facility and over sight " 
                  + "committee cannot both be set.  ");
      }
      if (!PAUtil.isIiNull(dto.getResearchOrganizationIi()) && !PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
          throw new PAException("research organization and over sight " 
                  + "committee cannot both be set.  ");
      }
      ReviewBoardApprovalStatusCode code = ReviewBoardApprovalStatusCode.getByCode(
              CdConverter.convertCdToString(dto.getReviewBoardApprovalStatusCode()));
      if (code != null) {
          String approvalNumber = StConverter.convertToString(dto.getReviewBoardApprovalNumber());
          if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().toString().equals(code.getCode().toString())
                  && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
              throw new PAException("Review board approval number must be set for status '"
                      + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "'.  ");
          }
          if (ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().toString().equals(code.getCode().toString())
           || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().toString().equals(code.getCode().toString())
           || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().toString()
               .equals(code.getCode().toString()) && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
              dto.setReviewBoardApprovalNumber(StConverter.convertToSt(null));
          }
          if (PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
              throw new PAException("Oversight committee (board) must be set when review board approval status is '"
                      + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "' or '"
                      + ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getDisplayName() + "'.  ");
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
      } else {
          return (IiConverter.convertToString(dto.getHealthcareFacilityIi()));
      }
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
              throw new PADuplicateException("This organization has already been entered as a '"
                      + newFunction + "' for this study.");
          }
      }
      
      
  }

  private void enforceOnlyOneOversightCommittee(StudySiteDTO dto) throws PAException {
      if (!PAUtil.isCdNull(dto.getReviewBoardApprovalStatusCode())) {
          List<StudySiteDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
          for (StudySiteDTO sp : spList) {
              if (!IiConverter.convertToLong(dto.getIdentifier()).
                      equals(IiConverter.convertToLong(sp.getIdentifier()))
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
      session = HibernateUtil.getCurrentSession();
      Query query = null;
      // step 1: form the hql
      String hql = " select spart "
          + " from StudySite spart "
          + " join spart.researchOrganization as ro "
          + " join spart.studyProtocol as sp "
          + " join sp.documentWorkflowStatuses as dws  "
          + " where spart.localStudyProtocolIdentifier = :localStudyProtocolIdentifier "
          + " and spart.functionalCode = '"
          +   StudySiteFunctionalCode.getByCode(dto.getFunctionalCode().getCode()) + "'"
          + " and dws.statusCode  <> '" + DocumentWorkflowStatusCode.REJECTED + "'"
          + " and sp.statusCode ='" + ActStatusCode.ACTIVE + "'"
          + " and ( dws.id in (select max(id) from DocumentWorkflowStatus as dws1 "
          + "  where dws.studyProtocol = dws1.studyProtocol ) or dws.id is null ) "
          + " and ro.id = :orgIdentifier";

      getLogger().info("query study_Site = " + hql + ".  ");
      // step 2: construct query object
      query = session.createQuery(hql);
      query.setParameter("localStudyProtocolIdentifier",
              StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()));
      query.setParameter("orgIdentifier",
              IiConverter.convertToLong(dto.getResearchOrganizationIi()));

      // step 3: query the result
      queryList = query.list();
      if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())) {
          for (StudySite sp : queryList) {
              //When create DTO get Id will be null and if queryList is having value then its duplicate
              //When update check if the record is same if not then throw ex
              if ((dto.getIdentifier() == null) 
                      || (!String.valueOf(sp.getId()).equals(dto.getIdentifier().getExtension()))) {
                  throw new PAException("Duplicate Trial Submission: A trial exists in the system with the same "
                          + "Lead Organization Trial Identifier for the selected Lead Organization");
              }
          }
      }
      if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equalsIgnoreCase(dto.getFunctionalCode().getCode())
              && PAUtil.isListNotEmpty(queryList)) {
              throw new PAException("Duplicate Trial Submission: A trial exists in the system with the same "
                      + "NCT Trial Identifier.");
      }

      getLogger().info("Leaving enforceNoDuplicateTrial..");

  }
  
  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @TransactionAttribute(TransactionAttributeType.SUPPORTS)
  public List<StudySiteDTO> search(StudySiteDTO dto, LimitOffset pagingParams) throws PAException,
  TooManyResultsException {
      if (dto == null) {
          getLogger().error(" StudySiteDTO should not be null ");
          throw new PAException(" StudySiteDTO should not be null ");
      }
      getLogger().info("Entering search");
      Session session = null;
      List<StudySite> studySiteList = null;
      session = HibernateUtil.getCurrentSession();
      StudySite exampleDO = new StudySite();
      Criteria criteria = session.createCriteria(StudySite.class);
      if (!PAUtil.isIiNull(dto.getIdentifier())) {
          exampleDO.setId(IiConverter.convertToLong(dto.getIdentifier()));
      }
      if (!PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
          criteria.createAlias("studyProtocol", "sp")
          .add(Expression.eq("sp.id", IiConverter.convertToLong(dto.getStudyProtocolIdentifier())));
      }
      if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi())) {
          if (PAConstants.PA_INTERNAL.equals(dto.getHealthcareFacilityIi().getIdentifierName())) { 
              criteria.createAlias("healthCareFacility", "hcf")
              .add(Expression.eq("hcf.id", IiConverter.convertToLong(dto.getHealthcareFacilityIi())));
          } else {
              criteria.createAlias("healthCareFacility", "hcf")
              .add(Expression.eq("hcf.identifier", IiConverter.convertToString(dto.getHealthcareFacilityIi())));
          }
      }

      if (!PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
          if (PAConstants.PA_INTERNAL.equals(dto.getResearchOrganizationIi().getIdentifierName())) {
              criteria.createAlias("researchOrganization", "ro")
              .add(Expression.eq("ro.id", IiConverter.convertToLong(dto.getResearchOrganizationIi())));
          } else {
              criteria.createAlias("researchOrganization", "ro")
              .add(Expression.eq("ro.identifier", IiConverter.convertToString(dto.getResearchOrganizationIi())));
          }

      }

      if (!PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
          if (PAConstants.PA_INTERNAL.equals(dto.getOversightCommitteeIi().getIdentifierName())) { 
              criteria.createAlias("oversightCommittee", "oc")
              .add(Expression.eq("oc.id", IiConverter.convertToLong(dto.getOversightCommitteeIi())));
          } else {
              criteria.createAlias("oversightCommittee", "oc")
              .add(Expression.eq("oc.identifier", IiConverter.convertToString(dto.getOversightCommitteeIi())));
          }
      }

     if (dto.getFunctionalCode() != null) {
         exampleDO.setFunctionalCode(StudySiteFunctionalCode.getByCode(dto.getFunctionalCode().getCode()));
      }
      if (dto.getLocalStudyProtocolIdentifier() != null) {
          exampleDO.setLocalStudyProtocolIdentifier(StConverter.convertToString(
                  dto.getLocalStudyProtocolIdentifier()));
      }
      if (dto.getStatusCode() != null) {
          exampleDO.setStatusCode(FunctionalRoleStatusCode.getByCode(dto.getStatusCode().getCode()));
      }
      if (dto.getReviewBoardApprovalDate() != null) {
          exampleDO.setReviewBoardApprovalDate(TsConverter.convertToTimestamp(dto.getReviewBoardApprovalDate()));
      }
      if (dto.getReviewBoardApprovalNumber() != null) {
          exampleDO.setReviewBoardApprovalNumber(StConverter.convertToString(dto.getReviewBoardApprovalNumber()));
      }
      if (dto.getTargetAccrualNumber() != null) {
          exampleDO.setTargetAccrualNumber(IntConverter.convertToInteger(dto.getTargetAccrualNumber()));
      }
      if (dto.getReviewBoardOrganizationalAffiliation() != null) {
          exampleDO.setReviewBoardOrganizationalAffiliation(
                  StConverter.convertToString(dto.getReviewBoardOrganizationalAffiliation()));
      }
      if (dto.getReviewBoardApprovalStatusCode() != null) {
          exampleDO.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.getByCode(
                  dto.getReviewBoardApprovalStatusCode().getCode()));
      }
      if (dto.getProgramCodeText() != null) {
          exampleDO.setProgramCodeText(StConverter.convertToString(dto.getProgramCodeText()));
      }
      if (dto.getAccrualDateRange() != null) {
          exampleDO.setAccrualDateRangeLow(IvlConverter.convertTs().convertLow(dto.getAccrualDateRange()));
          exampleDO.setAccrualDateRangeHigh(IvlConverter.convertTs().convertHigh(dto.getAccrualDateRange()));
      }
      Example example = Example.create(exampleDO);
      criteria.add(example);
      int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
      criteria.setMaxResults(maxLimit);
      criteria.setFirstResult(pagingParams.getOffset());
      studySiteList = criteria.list();

      if (studySiteList.size() > PAConstants.MAX_SEARCH_RESULTS) {
          throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
      }
      List<StudySiteDTO> studySiteDTOList = convertFromDomainToDTO(studySiteList);
      getLogger().info("Leaving search");
      return studySiteDTOList;
  }
  
  private List<StudySiteDTO> convertFromDomainToDTO(List<StudySite> studySiteList) throws PAException {
      List<StudySiteDTO> studySiteDTOList = null;
      StudySiteConverter ssConverter = new StudySiteConverter();
      if (studySiteList != null) { 
          studySiteDTOList = new ArrayList<StudySiteDTO>();
          for (StudySite ss : studySiteList) {
              StudySiteDTO studySiteDTO = ssConverter.convertFromDomainToDto(ss);
              studySiteDTOList.add(studySiteDTO);
          }
      }
      return studySiteDTOList;
  }
  /**
   * @param dto dto
   * @throws PAException e
   */
  public void validate(StudySiteDTO dto) throws PAException {
      enforceNoDuplicateTrial(dto);    
  }


}
