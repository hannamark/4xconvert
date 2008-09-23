package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;

/**
 * Criteria class to search for duplicate organizations.
 * NOTE: 
 * NOTE: This criteria class is a placeholder for future duplicate org search requirements.
 * NOTE: This implements Contactable so that it may be used AbstractEditContactListAction
 */
public class DuplicatesOrganizationSearchCriteria extends OrgEntityServiceSearchCriteria implements Serializable,
        Contactable {

    private static final long serialVersionUID = 1L;

    /**
     * Default Constructor.
     */
    public DuplicatesOrganizationSearchCriteria() {
        setOrganization(new Organization());
    }

    /**
     * {@inheritDoc}
     */
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        return super.getQuery(orderByProperty, isCountOnly);
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Email> getEmail() {
        return getOrganization().getEmail();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getFax() {
        return getOrganization().getFax();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getPhone() {
        return getOrganization().getPhone();
    }

    /**
     * {@inheritDoc}
     */
    public List<PhoneNumber> getTty() {
        return getOrganization().getTty();
    }

    /**
     * {@inheritDoc}
     */
    public List<URL> getUrl() {
        return getOrganization().getUrl();
    }
}
