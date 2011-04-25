package gov.nih.nci.pa.viewer.dto.criteria;

import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Max Shestopalov
 */
public class Summ4RepCriteriaWebDto extends AbstractBaseCriteriaWebDto<Summ4RepCriteriaDto> {

    private String familyId = "";
    private String orgName = "";
    private List<String> orgNames = new ArrayList<String>();

    /**
     * Generate ISO DTO version.
     * @return ISO DTO version
     * @throws PAException if error in generating ISO DTO
     */
    public Summ4RepCriteriaDto getIsoDto() throws PAException {
        Summ4RepCriteriaDto dto = new Summ4RepCriteriaDto();
        super.setInterval(dto);
        for (String name : getOrgNames()) {
            dto.getOrgNames().add(StConverter.convertToSt(name));
        }
        if (StringUtils.isNotBlank(getOrgName())) {
            dto.getOrgNames().add(StConverter.convertToSt(getOrgName()));
        }
        return dto;
    }

    /**
     * @return the orgNames
     */
    public List<String> getOrgNames() {
        return orgNames;
    }

    /**
     * @param orgNames the orgNames to set
     */
    public void setOrgNames(List<String> orgNames) {
        this.orgNames = orgNames;
    }

    /**
     * @return the familyId
     */
    public String getFamilyId() {
        return familyId;
    }

    /**
     * @param familyId the familyId to set
     */
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
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
