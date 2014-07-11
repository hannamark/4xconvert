package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * This interface represents entity that may be Overridden.
 * 
 * @param <BO>
 *            @see Curatable#<BO>
 * @param <CR>
 *            @see Curatable#<CR>
 * 
 * @author Rohit Gupta
 */
public interface Overridable<BO extends PersistentObject, CR extends ChangeRequest<BO>>
        extends Curatable<BO, CR> {

    /**
     * Get the User who Overridden the entity.
     * 
     * @return the User.
     */
    User getOverriddenBy();

    /**
     * Set the User who Overridden the entity.
     * 
     * @param overriddenBy
     *            the user
     */
    void setOverriddenBy(User overriddenBy);
}
