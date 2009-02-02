package gov.nih.nci.pa.iso.convert;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;

import org.apache.log4j.Logger;

/**
 * Convert StudyRecruitmentStatus domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/02/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public class StudyRecruitmentStatusConverter {

    private static final Logger LOG  = Logger.getLogger(StudyRecruitmentStatusConverter.class);
    
    /**
    *
    * @param bo StudyProtocol domain object
    * @return dto
    * @throws PAException PAException
    */
   public static StudyRecruitmentStatusDTO convertFromDomainToDTO(
           StudyRecruitmentStatus bo) throws PAException {
       StudyRecruitmentStatusDTO dto = new StudyRecruitmentStatusDTO();
       dto.setIdentifier(IiConverter.convertToIi(bo.getId()));
       dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
       dto.setStatusDate(TsConverter.convertToTs(bo.getStatusDate()));
       dto.setStudyProtocolIi(IiConverter.converToStudyProtocolIi(bo.getStudyProtocol().getId()));
       return dto;
   }

   /**
    * Create a new domain object from a given dto.
    * @param dto StudyOverallStatusDTO
    * @return StudyProtocol StudyProtocol
    * @throws PAException PAException
    */
   public static StudyRecruitmentStatus convertFromDtoToDomain(
           StudyRecruitmentStatusDTO dto) throws PAException {
       if (PAUtil.isIiNull(dto.getStudyProtocolIi())) {
           String errmsg = " StudyOverallStatus.studyProtocol cannot be null. ";
           LOG.error(errmsg);
           throw new PAException(errmsg);
       }

       StudyProtocol spBo = new StudyProtocol();
       spBo.setId(IiConverter.convertToLong(dto.getStudyProtocolIi()));

       StudyRecruitmentStatus bo = new StudyRecruitmentStatus();
       bo.setDateLastUpdated(new Date());
       bo.setStatusCode(StudyRecruitmentStatusCode.getByCode(dto.getStatusCode().getCode()));
       bo.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
       bo.setStudyProtocol(spBo);
       return bo;
   }
    
}
