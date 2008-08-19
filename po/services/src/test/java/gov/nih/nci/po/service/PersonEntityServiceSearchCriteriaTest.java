package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PersonEntityServiceSearchCriteriaTest {

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
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().setFirstName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().setLastName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().setMiddleName("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().setPrefix("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().setSuffix("name");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPhone().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getFax().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getTty().add(new PhoneNumber("123-123-1234"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getEmail().add(new Email("test@example.com"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getUrl().add(new URL("http://www.example.com/"));
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setCityOrMunicipality("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setDeliveryAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setPostalCode("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());

        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setStateOrProvince("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setStreetAddressLine("a");
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setCountry(new Country());
        assertFalse(yesCrit.hasOneCriterionSpecified());

        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setCountry(new Country(null, null, null, "a"));
        assertTrue(yesCrit.hasOneCriterionSpecified());
        
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setPostalAddress(new Address());
        assertFalse(yesCrit.hasOneCriterionSpecified());
        yesCrit.getPerson().getPostalAddress().setCountry(new Country(null, null, null, null));
        yesCrit.getPerson().getPostalAddress().getCountry().setId(new Long(1));
        assertFalse(yesCrit.hasOneCriterionSpecified());
        
        
        //Not used...
        yesCrit.setPerson(new Person());
        yesCrit.getPerson().setStatusCode(EntityStatus.CURATED);
        assertFalse(yesCrit.hasOneCriterionSpecified());
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
    public void getQueryWhereClauseIfMiddleName() {
        criteria.getPerson().setMiddleName("mName");
        StringBuffer queryWhereClause = criteria.getQueryWhereClause(namedParams, personAlias);
        String expected = AbstractSearchCriteria.WHERE
        + getExpectedILike(PERSONALIASDOT + AbstractPersonSearchCriteria.PERSON_MIDDLE_NAME_PROPERTY,
                AbstractPersonSearchCriteria.PERSON_MIDDLE_NAME_PROPERTY);
        assertEquals(expected, queryWhereClause.toString());
        assertEquals("%" + criteria.getPerson().getMiddleName().toLowerCase() + "%", namedParams
                .remove(AbstractPersonSearchCriteria.PERSON_MIDDLE_NAME_PROPERTY));
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