package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        || isValueSpecified(organization.getDescription()) || organization.getId() != null);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @param orgAlias Organization table alias
     * @return HQL-based where clause
     */
    protected StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String orgAlias) {
        List<String> whereClause = new ArrayList<String>();
        String orgAliasDot = orgAlias + DOT;
        whereClause.add(addEqual(orgAliasDot + ORGANIZATION_ID_PROPERTY, ORGANIZATION_ID_PROPERTY,
                organization.getId(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_NAME_PROPERTY, ORGANIZATION_NAME_PROPERTY, organization
                .getName(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_ABBRV_PROPERTY, ORGANIZATION_ABBRV_PROPERTY, organization
                .getAbbreviatedName(), namedParameters));
        whereClause.add(addILike(orgAliasDot + ORGANIZATION_DESC_PROPERTY, ORGANIZATION_DESC_PROPERTY, organization
                .getDescription(), namedParameters));
        return buildWhereClause(whereClause, WhereClauseOperator.CONJUNCTION);
    }
}
