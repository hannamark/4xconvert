package gov.nih.nci.pa.report.dto.criteria;

import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Max Shestopalov
 */
public class Summ4RepCriteriaDto extends AbstractStandardCriteriaDto {

    private St orgName = StConverter.convertToSt(null);

    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(St orgName) {
        this.orgName = orgName;
    }

    /**
     * @return the orgName
     */
    public St getOrgName() {
        return orgName;
    }

    /**
     * Validate that the criteria is good.
     * @param criteria criteria iso dto
     * @throws PAException exception
     */
    public static void validate(Object criteria) throws PAException {
        AbstractCriteriaDto.validate(criteria);
        if (PAUtil.isStNull(((Summ4RepCriteriaDto) criteria).getOrgName())) {
            throw new PAException("ERROR:  Organization criteria not entered.");
        }
    }
}
