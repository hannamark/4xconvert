package gov.nih.nci.po.data.bo;

import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * This interface represents entity that may be Overridden.
 * 
 * @author Rohit Gupta
 */
public interface Overridable {

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
