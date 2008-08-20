package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

/**
 * SearchCriteria for Organization Entity Service.
 */
public class OrgEntityServiceSearchCriteria extends AbstractOrganizationSearchCriteria implements
        SearchCriteria<Organization> {
    static final String ORGANIZATION_ALIAS = "org";

    static final String ORGANIZATION_ID_PROPERTY = "id";
    static final String ORGANIZATION_NAME_PROPERTY = "name";
    static final String ORGANIZATION_DESC_PROPERTY = "description";
    static final String ORGANIZATION_ABBRV_PROPERTY = "abbreviatedName";
    static final String ORGANIZATION_EMAIL_PROPERTY_NAME = "email";
    static final String ORGANIZATION_PHONE_PROPERTY_NAME = "phone";
    static final String ORGANIZATION_FAX_PROPERTY_NAME = "fax";
    static final String ORGANIZATION_TTY_PROPERTY_NAME = "tty";
    static final String ORGANIZATION_URL_PROPERTY_NAME = "url";
    static final String ORGANIZATION_POSTAL_ADDRESS_PROPERTY_NAME = "postalAddress";
    static final String ORGANIZATION_STATUS_PROPERTY = "statusCode";

    private Organization organization;

    /**
     * @return organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization to find matches
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return organization != null
                && (isValueSpecified(organization.getName()) || isValueSpecified(organization.getAbbreviatedName())
                        || isValueSpecified(organization.getDescription()) || organization.getId() != null
                        || CollectionUtils.isNotEmpty(organization.getEmail())
                        || CollectionUtils.isNotEmpty(organization.getFax())
                        || CollectionUtils.isNotEmpty(organization.getPhone())
                        || CollectionUtils.isNotEmpty(organization.getTty())
                        || CollectionUtils.isNotEmpty(organization.getUrl()) || isAddressCriterionSpecified() 
                        || organization.getStatusCode() != null);
    }

    private boolean isAddressCriterionSpecified() {
        return new AddressSearchCriteria(organization.getPostalAddress()).hasOneCriterionSpecified();
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param orgAlias Organization table alias
     * @return HQL-based where clause
     */
    protected StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String orgAlias) {
        List<String> whereClause = new ArrayList<String>();
        String orgAliasDot = orgAlias + DOT;
        whereClause.add(addNotEqual(orgAliasDot + ORGANIZATION_STATUS_PROPERTY, ORGANIZATION_STATUS_PROPERTY + "1",
                EntityStatus.DEPRECATED, namedParameters));
        whereClause.add(addEqual(orgAliasDot + ORGANIZATION_ID_PROPERTY, ORGANIZATION_ID_PROPERTY,
                organization.getId(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_NAME_PROPERTY, ORGANIZATION_NAME_PROPERTY, organization
                .getName(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_ABBRV_PROPERTY, ORGANIZATION_ABBRV_PROPERTY, organization
                .getAbbreviatedName(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_DESC_PROPERTY, ORGANIZATION_DESC_PROPERTY, organization
                .getDescription(), namedParameters));
        whereClause.add(addEqual(orgAliasDot + ORGANIZATION_STATUS_PROPERTY, ORGANIZATION_STATUS_PROPERTY, organization
                .getStatusCode(), namedParameters));
        String orgId = orgAliasDot + ORGANIZATION_ID_PROPERTY;
        whereClause.add(inIfRhs(orgId, findMatchingEmail(namedParameters).toString()));
        whereClause.add(inIfRhs(orgId, findMatchingPhone(namedParameters).toString()));
        whereClause.add(inIfRhs(orgId, findMatchingFax(namedParameters).toString()));
        whereClause.add(inIfRhs(orgId, findMatchingTty(namedParameters).toString()));
        whereClause.add(inIfRhs(orgId, findMatchingUrl(namedParameters).toString()));
        whereClause.add(inIfRhs(orgId, findMatchingAddress(namedParameters).toString()));
        return buildWhereClause(whereClause, WhereClauseOperator.CONJUNCTION);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find email addresses for a person
     */
    protected StringBuffer findMatchingEmail(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, organization.getEmail(), Email.class,
                ORGANIZATION_EMAIL_PROPERTY_NAME, Organization.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find phone numbers for a person
     */
    protected StringBuffer findMatchingPhone(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, organization.getPhone(), PhoneNumber.class,
                ORGANIZATION_PHONE_PROPERTY_NAME, Organization.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find fax numbers for a person
     */
    protected StringBuffer findMatchingFax(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, organization.getFax(), PhoneNumber.class,
                ORGANIZATION_FAX_PROPERTY_NAME, Organization.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find TTY numbers for a person
     */
    protected StringBuffer findMatchingTty(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, organization.getTty(), PhoneNumber.class,
                ORGANIZATION_TTY_PROPERTY_NAME, Organization.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find URLs for a person
     */
    protected StringBuffer findMatchingUrl(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, organization.getUrl(), URL.class, ORGANIZATION_URL_PROPERTY_NAME,
                Organization.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return person.id or organization.id
     */
    protected StringBuffer findMatchingAddress(Map<String, Object> namedParameters) {
        AddressSearchCriteria sc = new AddressSearchCriteria();
        sc.setAddress(organization.getPostalAddress());

        return findMatchingAddress(namedParameters, sc, Organization.class, ORGANIZATION_POSTAL_ADDRESS_PROPERTY_NAME);
    }
}
