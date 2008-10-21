package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.EntityStatus;

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
        if (arg0 instanceof gov.nih.nci.po.data.bo.Curatable) {
            gov.nih.nci.po.data.bo.Curatable c = (gov.nih.nci.po.data.bo.Curatable) arg0;
            return !(!EntityStatus.NULLIFIED.equals(c.getStatusCode()) && c.getDuplicateOf() != null);
        }
        return true;
    }

}
