package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;
import gov.nih.nci.po.data.bo.Person;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonSearchCriteriaTest {

    private PersonEntityServiceSearchCriteria criteria;
    private Map<String, Object> namedParams;
    private static final String personAlias = "abc";
    private static final String PERSONALIASDOT = personAlias + AbstractSearchCriteria.DOT;

    @Before
    public void setup() {
        criteria = new PersonEntityServiceSearchCriteria();
        criteria.setPerson(new Person());
        namedParams = new HashMap<String, Object>();
    }
    @Test
    public void testGettersAndSetters() throws Exception {
        PersonEntityServiceSearchCriteria osc = new PersonEntityServiceSearchCriteria();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(osc);
    }

    @Test
    public void testHasAtLeastOneCriterionSpecified() {
        PersonEntityServiceSearchCriteria noCrit = new PersonEntityServiceSearchCriteria();
        assertFalse(noCrit.hasOneCriterionSpecified());

        PersonEntityServiceSearchCriteria yesCrit = new PersonEntityServiceSearchCriteria();
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setFirstName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getCriteria() {
        criteria.getCriteria();
    }

    @Test
    public void getQueryWhereClause() {
        StringBuffer queryWhereClause = criteria.getQueryWhereClause(namedParams, personAlias);
        assertEquals("", queryWhereClause.toString());
    }

    @Test
    public void getQueryWhereClauseIfFirstName() {
        criteria.getPerson().setFirstName("fName");
        StringBuffer queryWhereClause = criteria.getQueryWhereClause(namedParams, personAlias);
        String expected = AbstractSearchCriteria.WHERE
                + getExpectedILike(PERSONALIASDOT + AbstractPersonSearchCriteria.PERSON_FIRST_NAME_PROPERTY,
                        AbstractPersonSearchCriteria.PERSON_FIRST_NAME_PROPERTY);
        assertEquals(expected, queryWhereClause.toString());
        assertEquals("%" + criteria.getPerson().getFirstName().toLowerCase() + "%", namedParams
                .remove(AbstractPersonSearchCriteria.PERSON_FIRST_NAME_PROPERTY));
        assertTrue(namedParams.isEmpty());
    }

    @Test
    public void getQueryWhereClauseIfLastName() {
        criteria.getPerson().setLastName("lName");
        StringBuffer queryWhereClause = criteria.getQueryWhereClause(namedParams, personAlias);
        String expected = AbstractSearchCriteria.WHERE
                + getExpectedILike(PERSONALIASDOT + AbstractPersonSearchCriteria.PERSON_LAST_NAME_PROPERTY,
                        AbstractPersonSearchCriteria.PERSON_LAST_NAME_PROPERTY);
        assertEquals(expected, queryWhereClause.toString());
        assertEquals("%" + criteria.getPerson().getLastName().toLowerCase() + "%", namedParams
                .remove(AbstractPersonSearchCriteria.PERSON_LAST_NAME_PROPERTY));
        assertTrue(namedParams.isEmpty());
    }

    private String getExpectedILike(String propertyName, String paramName) {
        String eILike = "lower(" + propertyName + ") like :" + paramName;
        return eILike;
    }
}