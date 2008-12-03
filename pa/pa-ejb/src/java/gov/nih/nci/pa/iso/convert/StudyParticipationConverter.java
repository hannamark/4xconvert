/**
 *
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.OversightCommittee;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

/**
 * Convert StudyParticipation domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/23/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyParticipationConverter extends AbstractConverter<StudyParticipationDTO, StudyParticipation> {
    /**
    * @param bo StudyProtocol domain object
    * @return dto
    * @throws PAException PAException
    */
    @Override
    public StudyParticipationDTO convertFromDomainToDto(StudyParticipation bo)
            throws PAException {
        StudyParticipationDTO dto = new StudyParticipationDTO();
        dto.setFunctionalCode(CdConverter.convertToCd(bo.getFunctionalCode()));
        if (bo.getHealthCareFacility() != null) {
            dto.setHealthcareFacilityIi(IiConverter.convertToIi(bo.getHealthCareFacility().getId()));
        }
        if (bo.getResearchOrganization() != null) {
            dto.setResearchOrganizationIi(IiConverter.convertToIi(bo.getResearchOrganization().getId()));
        }
        if (bo.getOversightCommittee() != null) {
            dto.setOversightCommitteeIi(IiConverter.convertToIi(bo.getOversightCommittee().getId()));
        }
        dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
        dto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(bo.getLocalStudyProtocolIdentifier()));
        dto.setReviewBoardApprovalDate(TsConverter.convertToTs(bo.getReviewBoardApprovalDate()));
        dto.setReviewBoardApprovalNumber(StConverter.convertToSt(bo.getReviewBoardApprovalNumber()));
        dto.setReviewBoardApprovalStatusCode(CdConverter.convertToCd(bo.getReviewBoardApprovalStatusCode()));
        dto.setTargetAccrualNumber(IntConverter.convertToInt(bo.getTargetAccrualNumber()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDateRangeLow(TsConverter.convertToTs(bo.getStatusDateRangeLow()));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto StudyParticipationDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    @Override
    public StudyParticipation convertFromDtoToDomain(StudyParticipationDTO dto)
            throws PAException {
        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));

        HealthCareFacility hfBo = null;
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi())) {
            hfBo = new HealthCareFacility();
            hfBo.setId(IiConverter.convertToLong(dto.getHealthcareFacilityIi()));
        }

        ResearchOrganization roBo = null;
        if (!PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            roBo = new ResearchOrganization();
            roBo.setId(IiConverter.convertToLong(dto.getResearchOrganizationIi()));
        }
        
        OversightCommittee ocBo = null;
        if (!PAUtil.isIiNull(dto.getOversightCommitteeIi())) {
            ocBo = new OversightCommittee();
            ocBo.setId(IiConverter.convertToLong(dto.getOversightCommitteeIi()));
        }

        StudyParticipation bo = new StudyParticipation();
        bo.setDateLastUpdated(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setFunctionalCode(StudyParticipationFunctionalCode.getByCode(dto.getFunctionalCode().getCode()));
        bo.setHealthCareFacility(hfBo);
        bo.setResearchOrganization(roBo);
        bo.setOversightCommittee(ocBo);
        bo.setId(IiConverter.convertToLong(dto.getIdentifier()));
        bo.setLocalStudyProtocolIdentifier(StConverter.convertToString(dto.getLocalStudyProtocolIdentifier()));
        bo.setStatusCode(StatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDateRangeLow(TsConverter.convertToTimestamp(dto.getStatusDateRangeLow()));
        bo.setStudyProtocol(spBo);
        bo.setReviewBoardApprovalDate(TsConverter.convertToTimestamp(dto.getReviewBoardApprovalDate()));
        bo.setReviewBoardApprovalNumber(StConverter.convertToString(dto.getReviewBoardApprovalNumber()));
        bo.setTargetAccrualNumber(IntConverter.convertToInteger(dto.getTargetAccrualNumber()));
        
        if (dto.getReviewBoardApprovalStatusCode() != null) {
            bo.setReviewBoardApprovalStatusCode(ReviewBoardApprovalStatusCode.getByCode(
                    dto.getReviewBoardApprovalStatusCode().getCode()));
        }
        return bo;
    }

}
