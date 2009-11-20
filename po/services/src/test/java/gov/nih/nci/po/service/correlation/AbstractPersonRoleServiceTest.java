package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;


public abstract class AbstractPersonRoleServiceTest<T extends PersonRole> extends AbstractStructrualRoleServiceTest<T>{

    @Test(expected = EntityValidationException.class)
    public void testUniqueConstraint() throws Exception {
        T obj = getSampleStructuralRole();
        T obj2 = getSampleStructuralRole();
        //ensure player is same
        obj.setPlayer(obj2.getPlayer());
        //ensure scoper is same
        obj2.setScoper(obj.getScoper());
        getService().create(obj);
        getService().create(obj2);
    }
    
    @Test
    public void testSearchByAddress() throws Exception {
        Country country = new Country("JKL", "901", "JK", "JKL");
        Serializable cid = PoHibernateUtil.getCurrentSession().save(country);
        Country validCountry = (Country) PoHibernateUtil.getCurrentSession().get(Country.class, cid);
        
        T obj = getSampleStructuralRole();
        if (obj instanceof AbstractPersonRole) {
            ((AbstractPersonRole) obj).getPostalAddresses().add(new Address("10 Green D. Street", "Rockville", "MD", "20857",
                    validCountry));
            getService().create(obj);
 
            obj = getSampleStructuralRole();
            ((AbstractPersonRole) obj).getPostalAddresses().add(new Address("11 Green D. Street", "Rockville", "MD", "20857", validCountry));
            ((AbstractPersonRole) obj).getPostalAddresses().add(new Address("12 Green D. Street", "Rockville", "MD", "20857", validCountry));
            getService().create(obj);
 
            obj = getNewStructuralRole();
            ((AbstractPersonRole) obj).getPostalAddresses().add(new Address("11", null, null, null, null));
            List<T> obj_result =  getService().search(new AnnotatedBeanSearchCriteria<T>(obj));
            assertEquals(1, obj_result.size());
        }
    }

    protected void fillinPersonRoleFields(AbstractPersonRole pr) {
        pr.setPlayer(basicPerson);
        pr.setScoper(basicOrganization);
        pr.setEmail(new ArrayList<Email>());
        pr.getEmail().add(new Email("me@example.com"));
        pr.setPhone(new ArrayList<PhoneNumber>());
        pr.getPhone().add(new PhoneNumber("123-456-7890"));
        pr.setFax(new ArrayList<PhoneNumber>());
        pr.getFax().add(new PhoneNumber("098-765-4321"));
        pr.setTty(new ArrayList<PhoneNumber>());
        pr.getTty().add(new PhoneNumber("111-222-3333"));
        pr.setUrl(new ArrayList<URL>());
        pr.getUrl().add(new URL("http://www.example.com"));
        Address mailingAddress = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345", getDefaultCountry());
        pr.setPostalAddresses(new HashSet<Address>());
        pr.getPostalAddresses().add(mailingAddress);
    }

    protected void verifyPersonRole(AbstractPersonRole expected, AbstractPersonRole actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getEmail().size(), actual.getEmail().size());
        assertEquals(expected.getPlayer().getId(), actual.getPlayer().getId());
        assertEquals(expected.getScoper().getId(), actual.getScoper().getId());
        assertEquals(expected.getFax().size(), actual.getFax().size());
        assertEquals(expected.getPhone().size(), actual.getPhone().size());
        assertEquals(expected.getTty().size(), actual.getTty().size());
        assertEquals(expected.getUrl().size(), actual.getUrl().size());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPostalAddresses().size(), actual.getPostalAddresses().size());
        assertTrue(expected.isUsOrCanadaAddress());
    }
    
    
}
