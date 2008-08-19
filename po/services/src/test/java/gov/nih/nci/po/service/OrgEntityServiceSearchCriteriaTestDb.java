package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

public class OrgEntityServiceSearchCriteriaTestDb extends AbstractHibernateTestCase {

    private static final Logger LOG = Logger.getLogger(OrgEntityServiceSearchCriteriaTestDb.class);

    private static final String ORGANIZATION_ALIAS = "ORG";

    OrgEntityServiceSearchCriteria sc = new OrgEntityServiceSearchCriteria();
    private OrganizationServiceBeanTest ost;
    private long orgId;

    private Map<String, Object> namedParameters;

    @Before
    public void setupData() throws EntityValidationException {
        ost = new OrganizationServiceBeanTest();
        ost.loadData();
        ost.setUpData();
        orgId = ost.createOrganization("defaultName", "defaultCity", "defaultOrgCode", "defaultDescription");

        namedParameters = new HashMap<String, Object>();
    }


    private Query buildQuery(String queryString, Map<String, Object> namedParameters) {
        LOG.debug(queryString);
        Query query = PoHibernateUtil.getCurrentSession().createQuery(queryString);
        sc.setNamedParameters(namedParameters, query);
        LOG.debug(query.toString());
        return query;
    }

    @Test
    public void testGetQueryWhereClauseWithAllCriterion() {
        Organization o = new Organization();
        o.setAbbreviatedName("defaultOrgCode");
        o.setName("defaultName");
        o.setDescription("defaultDescription");
        o.setId(orgId);
        sc.setOrganization(o);
        StringBuffer queryString = new StringBuffer();
        queryString.append(AbstractSearchCriteria.SELECT);
        queryString.append(ORGANIZATION_ALIAS);
        queryString.append(" ");
        queryString.append(AbstractSearchCriteria.FROM);
        queryString.append(sc.tableAlias(Organization.class, ORGANIZATION_ALIAS));
        queryString.append(sc.getQueryWhereClause(namedParameters, ORGANIZATION_ALIAS));
        
        @SuppressWarnings("unchecked")
        List<Organization> results = buildQuery(queryString.toString(), namedParameters).list();
        assertEquals(1, results.size());
        assertEquals(orgId, results.get(0).getId().longValue());
    }
    
}
