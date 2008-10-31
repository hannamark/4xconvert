package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.EntityStatus;

import gov.nih.nci.po.data.bo.RoleStatus;
import java.io.Serializable;

import org.hibernate.validator.Validator;

/**
 * Validates a Curatable instance.
 * 
 * @author smatyas
 */
public class DuplicateStatusCheckValidator implements Validator<DuplicateStatusCheck>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public void initialize(DuplicateStatusCheck arg0) {
        //noop
    }

    /**
     * {@inheritDoc}
     */
    public boolean isValid(Object arg0) {
        if (arg0 instanceof gov.nih.nci.po.data.bo.CuratableEntity) {
            gov.nih.nci.po.data.bo.CuratableEntity c = (gov.nih.nci.po.data.bo.CuratableEntity) arg0;
            return !(!EntityStatus.NULLIFIED.equals(c.getStatusCode()) && c.getDuplicateOf() != null);
        } else if (arg0 instanceof gov.nih.nci.po.data.bo.CuratableRole) {
            gov.nih.nci.po.data.bo.CuratableRole c = (gov.nih.nci.po.data.bo.CuratableRole) arg0;
            return !(!RoleStatus.NULLIFIED.equals(c.getStatus()) && c.getDuplicateOf() != null);
        }
        return true;
    }

}
