package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyParticipationServiceBean
        extends AbstractStudyIsoService<StudyParticipationDTO, StudyParticipation, StudyParticipationConverter>
        implements StudyParticipationServiceRemote {

    @SuppressWarnings("PMD.NPathComplexity")
    private StudyParticipationDTO businessRules(StudyParticipationDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            serviceError("Either healthcare facility or research organization must be set.  ");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            serviceError("Healthcare facility and research organization cannot both be set.  ");
        }
        ReviewBoardApprovalStatusCode code = ReviewBoardApprovalStatusCode.getByCode(
                CdConverter.convertCdToString(dto.getReviewBoardApprovalStatusCode()));
        if (code != null) {
            String approvalNumber = StConverter.convertToString(dto.getReviewBoardApprovalNumber());
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().toString().equals(code.getCode().toString())
                    && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                serviceError("Review board approval number must be set for status '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "'.  ");
            }
            if (ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().toString().equals(code.getCode().toString())
                    && ((approvalNumber == null) || (approvalNumber.length() == 0))) {
                dto.setReviewBoardApprovalNumber(StConverter.convertToSt(PAUtil.today()));
            }
            if (PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
                serviceError("Oversight committee (board) must be set when review board approval status is '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getDisplayName() + "' or '"
                        + ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getDisplayName() + "'.  ");
            }
        } else {
            dto.setOversightCommitteeIi(null);
            dto.setReviewBoardApprovalDate(null);
            dto.setReviewBoardApprovalNumber(null);
        }
        return dto;
    }
    
    private void enforceOnlyOneOversightCommittee(StudyParticipationDTO dto) throws PAException {
        if (!PAUtil.isCdNull(dto.getReviewBoardApprovalStatusCode())) {
            List<StudyParticipationDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (StudyParticipationDTO sp : spList) {
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
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO create(StudyParticipationDTO dto) throws PAException {
        StudyParticipationDTO createDto = businessRules(dto);
        createDto.setStatusCode(CdConverter.convertToCd(StatusCode.PENDING));
        StudyParticipationDTO resultDto = super.create(createDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO update(StudyParticipationDTO dto)
            throws PAException {
        StudyParticipationDTO updateDto = businessRules(dto);
        StudyParticipationDTO resultDto = super.update(updateDto);
        enforceOnlyOneOversightCommittee(resultDto);
        return resultDto;
    }

    /**
     * Get list of StudyParticipations for a given protocol having
     * a given functional code.
     * @param studyProtocolIi id of protocol
     * @param spDTO StudyParticipationDTO with the functional code criteria
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyParticipationDTO spDTO) throws PAException {
        List <StudyParticipationDTO> spDtoList = new ArrayList<StudyParticipationDTO>();
        spDtoList.add(spDTO);
        return getByStudyProtocol(studyProtocolIi, spDtoList);
    }

    /**
     * Get list of StudyParticipations for a given protocol having
     * functional codes from a list.
     * @param studyProtocolIi id of protocol
     * @param spDTOList List containing desired functional codes
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" , "" })
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyParticipationDTO> spDTOList) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError("Ii is null ");
        }
        if ((spDTOList == null) || (spDTOList.isEmpty())) {
            getLogger().info("Using method getByStudyProtocol(Ii).  ");
            return getByStudyProtocol(studyProtocolIi);
        }
        getLogger().info("Entering getByStudyProtocol(Ii, List<DTO>).  ");
        StringBuffer criteria = new StringBuffer();
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer("select spart "
                               + "from StudyParticipation spart "
                               + "join spart.studyProtocol spro "
                               + "where spro.id = :studyProtocolId ");
            boolean first = true;
            for (StudyParticipationDTO crit : spDTOList) {
                if (first) {
                    hql.append("and ( ");
                    first = false;
                } else {
                    criteria.append("or ");
                }
                criteria.append("spart.functionalCode = '"
                    + StudyParticipationFunctionalCode.getByCode(crit.getFunctionalCode().getCode()) + "' ");
            }
            hql.append(criteria);
            hql.append(") order by spart.id ");
            getLogger().info(" query StudyParticipation = " + hql);

            Query query = session.createQuery(hql.toString());
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(convertFromDomainToDto(sp));
        }
        getLogger().info("Leaving getByStudyProtocol() for (" + criteria + ").  ");
        getLogger().info("Returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
}
