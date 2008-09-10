/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * Convert StudyProtocol domain to DTO.
 *
 * @author Hugh Reinhart
 * @since 09/02/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyOverallStatusConverter {

    private static final Logger LOG  = Logger.getLogger(StudyOverallStatusConverter.class);

    /**
     * 
     * @param bo StudyProtocol domain object
     * @return dto
     * @throws PAException PAException
     */
    public static StudyOverallStatusDTO convertFromDomainToDTO(
            StudyOverallStatus bo) throws PAException {
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIi(IiConverter.convertToIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDate(TsConverter.convertToTs(bo.getStatusDate()));
        dto.setStudyProtocolidentifier(IiConverter.convertToIi(bo.getStudyProtocol().getId()));
        return dto;
    }

    /**
     * Create a new domain object from a given dto.
     * @param dto StudyOverallStatusDTO
     * @return StudyProtocol StudyProtocol
     * @throws PAException PAException
     */
    public static StudyOverallStatus convertFromDtoToDomain(
            StudyOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIi())) {
            String errmsg = " convertFromDtoToDomain has been implemented for new domain"
                          + " objects only.  StudyOverallStatusDTO.ii must be null. ";
            LOG.error(errmsg);
            throw new PAException(errmsg);
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolidentifier())) {
            String errmsg = " StudyOverallStatus.studyProtocol cannot be null. ";
            LOG.error(errmsg);
            throw new PAException(errmsg);
        }

        StudyProtocol spBo; 
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            spBo = (StudyProtocol) session.load(StudyProtocol.class, 
                    Long.valueOf(dto.getStudyProtocolidentifier().getExtension()));
        } catch (HibernateException hbe) {
            String errmsg = " Hibernate exception in convertFromDtoToDomain ";
            LOG.error(errmsg, hbe);
            throw new PAException(errmsg, hbe);
        }        
        
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setDateLastUpdated(new Date());
        bo.setStatusCode(CdConverter.convertToStudyStatusCode(dto.getStatusCode()));
        bo.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
        bo.setStudyProtocol(spBo);
        return bo;
    }

}
