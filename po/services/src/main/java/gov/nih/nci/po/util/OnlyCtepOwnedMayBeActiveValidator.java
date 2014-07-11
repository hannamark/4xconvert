package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.hibernate.mapping.Property;
import org.hibernate.validator.PropertyConstraint;
import org.hibernate.validator.Validator;

import java.io.Serializable;

/**
 * Ensures only CTEP-owned may be ACTIVE.
 * 
 * @author smatyas
 * 
 */
public class OnlyCtepOwnedMayBeActiveValidator implements Validator<OnlyCtepOwnedMayBeActive>, PropertyConstraint,
        Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(OnlyCtepOwnedMayBeActive parameters) {
        // do nothing
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object value) {
        if ((value instanceof HealthCareFacility || value instanceof ResearchOrganization)
                && isInvalid((AbstractOrganizationRole) value)) {
            return false;
        }
        return true;
    }

    private boolean isInvalid(AbstractOrganizationRole role) {
        return !role.isCtepOwned() && role.getStatus() != null && RoleStatus.ACTIVE.equals(role.getStatus());
    }

    /**
     * {@inheritDoc}
     */
    public void apply(Property property) {
        // No db constraints are implied by this validator
    }

}
