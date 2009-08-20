package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.ArrayList;
import java.util.HashSet;

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
    }
}
