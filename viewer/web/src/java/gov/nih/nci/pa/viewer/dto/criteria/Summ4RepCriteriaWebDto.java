package gov.nih.nci.pa.viewer.dto.criteria;

import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.service.PAException;

/**
 * @author Max Shestopalov
 */
public class Summ4RepCriteriaWebDto extends AbstractBaseCriteriaWebDto<Summ4RepCriteriaDto> {

    private String orgName = "";

    /**
     * {@inheritDoc}
     * @throws PAException 
     */
    public Summ4RepCriteriaDto getIsoDto() throws PAException {
        Summ4RepCriteriaDto dto = new Summ4RepCriteriaDto();
        super.setInterval(dto);
        dto.setOrgName(StConverter.convertToSt(getOrgName()));
        return dto;
    }

    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}
