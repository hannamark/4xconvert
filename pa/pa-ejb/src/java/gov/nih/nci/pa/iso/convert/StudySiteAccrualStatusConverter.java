/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/02/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudySiteAccrualStatusConverter {
    private static final Logger LOG  = Logger.getLogger(StudySiteAccrualStatusConverter.class);

    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static StudySiteAccrualStatusDTO convertFromDomainToDTO(
            StudySiteAccrualStatus bo) throws PAException {
        StudySiteAccrualStatusDTO dto = new StudySiteAccrualStatusDTO();
        dto.setIi(IiConverter.convertToIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDate(TsConverter.convertToTs(bo.getStatusDate()));
        dto.setStudyParticipationIi(IiConverter.convertToIi(bo.getStudyParticipation().getId()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto StudySiteAccrualStatusDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static StudySiteAccrualStatus convertFromDtoToDomain(
            StudySiteAccrualStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            String errmsg = " convertFromDtoToDomain has been implemented for new domain"
                          + " objects only.  StudySiteAccrualStatusDTO.ii must be null. ";
            LOG.error(errmsg);
            throw new PAException(errmsg);
        }
        if (PAUtil.isIiNull(dto.getStudyParticipationIi())) {
            String errmsg = " StudySiteAccrualStatus.studyParticipation cannot be null. ";
            LOG.error(errmsg);
            throw new PAException(errmsg);
        }

        StudyParticipation spBo = new StudyParticipation();
        spBo.setId(IiConverter.convertToLong(dto.getStudyParticipationIi()));

        StudySiteAccrualStatus bo = new StudySiteAccrualStatus();
        bo.setDateLastUpdated(new Date());
        bo.setStatusCode(RecruitmentStatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
        bo.setStudyParticipation(spBo);
        return bo;
    }

}
