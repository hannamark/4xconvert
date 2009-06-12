package gov.nih.nci.pa.report.dto.criteria;

import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

/**
 * @author Hugh Reinhart
 * @since 06/09/2009
 */
public class AssignedIdentifierCriteriaDto extends AbstractCriteriaDto {

    private St assignedIdentifier = StConverter.convertToSt(null);

    /**
     * Validate that the criteria is good.
     * @param criteria criteria iso dto
     * @throws PAException exception
     */
    public static void validate(Object criteria) throws PAException {
        AbstractCriteriaDto.validate(criteria);
        if (PAUtil.isStNull(((AssignedIdentifierCriteriaDto) criteria).getAssignedIdentifier())) {
            throw new PAException("ERROR:  Identifier criteria not entered.");
        }
    }

    /**
     * @return the assignedIdentifier
     */
    public St getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * @param assignedIdentifier the assignedIdentifier to set
     */
    public void setAssignedIdentifier(St assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
}
