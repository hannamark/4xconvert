package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.CuratableEntity;
import gov.nih.nci.po.data.bo.CuratableRole;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.hibernate.validator.Validator;

import java.io.Serializable;

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
        if (arg0 instanceof CuratableEntity) {
            CuratableEntity<?, ?> c = (CuratableEntity<?, ?>) arg0;
            return !(!EntityStatus.NULLIFIED.equals(c.getStatusCode()) && c.getDuplicateOf() != null);
        } else if (arg0 instanceof CuratableRole) {
            CuratableRole<?, ?> c = (CuratableRole<?, ?>) arg0;
            return !(!RoleStatus.NULLIFIED.equals(c.getStatus()) && c.getDuplicateOf() != null);
        }
        return true;
    }

}
