/**
 * 
 */
package gov.nih.nci.pa.noniso.convert;

import gov.nih.nci.pa.domain.OrgFamilyProgramCode;
import gov.nih.nci.pa.noniso.dto.OrgFamilyProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;

/**
 * @author vinodh
 *
 */
public class OrgFamilyProgramCodeConverter extends AbstractConverter<OrgFamilyProgramCodeDTO, OrgFamilyProgramCode> {

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgFamilyProgramCode convertFromDtoToDomain(OrgFamilyProgramCodeDTO dto)
            throws PAException {
        OrgFamilyProgramCode prgCd = new OrgFamilyProgramCode();
        convertFromDtoToDomain(dto, prgCd);
        return prgCd;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void convertFromDtoToDomain(OrgFamilyProgramCodeDTO dto, OrgFamilyProgramCode bo)
            throws PAException {
        bo.setId(dto.getId());
        bo.setFamilyPoId(dto.getFamilyPoId());
        bo.setProgramCode(dto.getProgramCode());
        bo.setProgramName(dto.getProgramName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrgFamilyProgramCodeDTO convertFromDomainToDto(OrgFamilyProgramCode bo) throws PAException {
        OrgFamilyProgramCodeDTO dto = new OrgFamilyProgramCodeDTO();
        dto.setId(bo.getId());
        dto.setFamilyPoId(bo.getFamilyPoId());
        dto.setProgramCode(bo.getProgramCode());
        dto.setProgramName(bo.getProgramName());
        
        dto.setUserLastCreated(bo.getUserLastCreated() == null ? null : bo.getUserLastCreated().getLoginName());
        dto.setUserLastUpdated(bo.getUserLastUpdated() == null ? null : bo.getUserLastUpdated().getLoginName());
        dto.setDateLastCreated(bo.getDateLastCreated());
        dto.setDateLastUpdated(bo.getDateLastUpdated());
        
        return dto;
    }

}
