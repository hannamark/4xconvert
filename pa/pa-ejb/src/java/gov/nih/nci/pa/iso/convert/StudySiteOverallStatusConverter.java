/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteOverallStatus;
import gov.nih.nci.pa.enums.StudySiteStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;


/**
 * @author Vrushali
 *
 */
public class StudySiteOverallStatusConverter 
    extends AbstractConverter<StudySiteOverallStatusDTO, StudySiteOverallStatus> {
    
    /**
     *@param bo bObject 
     *@return iso
     *@throws PAException e
     */
    @Override
    public StudySiteOverallStatusDTO convertFromDomainToDto(
            StudySiteOverallStatus bo) throws PAException {
        StudySiteOverallStatusDTO dto = new StudySiteOverallStatusDTO();
        dto.setIdentifier(IiConverter.convertToStudySiteOverallStatusIi(bo.getId()));
        dto.setStatusCode(CdConverter.convertToCd(bo.getStatusCode()));
        dto.setStatusDate(TsConverter.convertToTs(bo.getStatusDate()));
        dto.setStudySiteIdentifier(IiConverter.convertToStudySiteIi(bo.getStudySite().getId()));
        return dto;
    }
    /**
     * @param dto d
     * @return bo
     * @throws PAException e
     */
    @Override
    public StudySiteOverallStatus convertFromDtoToDomain(
            StudySiteOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errmsg = " convertFromDtoToDomain has been implemented for new domain"
                          + " objects only.  StudySiteOverallStatusDTO.ii must be null. ";
            throw new PAException(errmsg);
        }
        if (PAUtil.isIiNull(dto.getStudySiteIdentifier())) {
            String errmsg = " StudySiteOverallStatus.studySite cannot be null. ";
            throw new PAException(errmsg);
        }

        StudySiteOverallStatus bo = new StudySiteOverallStatus();
        StudySite spBo = new StudySite();
        spBo.setId(IiConverter.convertToLong(dto.getStudySiteIdentifier()));

        bo.setDateLastUpdated(new Date());
        bo.setStatusCode(StudySiteStatusCode.getByCode(dto.getStatusCode().getCode()));
        bo.setStatusDate(TsConverter.convertToTimestamp(dto.getStatusDate()));
        bo.setStudySite(spBo);
        return bo;
    }

}
