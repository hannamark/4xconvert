package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Overridable;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * Defines the generic methods we expect to implement for all Organization role.
 * 
 * @author Rohit Gupta
 * 
 * @param <T>
 *            structural role
 */
public interface GenericOrganizationRoleServiceLocal<T extends Correlation>
        extends GenericStructrualRoleServiceLocal<T> {

    /**
     * Curates the object by setting 'overriddenBy' attribute.
     * 
     * @param overridable
     *            the object to curate.
     * @param overriddenBy
     *            User who overrode the entity.
     */
    void override(Overridable overridable, User overriddenBy);

}
