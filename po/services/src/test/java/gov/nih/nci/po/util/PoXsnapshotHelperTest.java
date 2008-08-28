package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AbstractBeanTest;
import gov.nih.nci.po.service.OrganizationCRServiceBeanTest;
import gov.nih.nci.po.service.PersonCRServiceBeanTest;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import net.sf.xsnapshot.SnapshotHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gax
 */
public class PoXsnapshotHelperTest extends AbstractBeanTest {

    @Test
    public void roundTripOrganization() {
        Organization org = new Organization();
        OrganizationCRServiceBeanTest.fill(org, getDefaultCountry());
        org.getPostalAddress().setDeliveryAddressLine("deliveryAddressLine");
        org.setStatusDate(new Date());
        org.getEmail().add(new Email("222@example.com"));
        org.getFax().add(new PhoneNumber("123-123-1234"));
        org.getPhone().add(new PhoneNumber("123-123-1234"));
        org.getTty().add(new PhoneNumber("123-123-1234"));
        org.getUrl().add(new URL("http://example.com/"));
        
        OrganizationDTO dto = PoXsnapshotHelper.createSnapshot(org);
        Organization clone = PoXsnapshotHelper.createModel(dto);
        
        EqualsByValue.assertEquals(org, clone);
    }
    
    @Test
    public void roundTripPerson() {
        Person per = new Person();
        PersonCRServiceBeanTest.fill(per, getDefaultCountry());
        per.getPostalAddress().setDeliveryAddressLine("deliveryAddressLine");
        per.setStatusDate(new Date());
        per.getEmail().add(new Email("222@example.com"));
        per.getFax().add(new PhoneNumber("123-123-1234"));
        per.getPhone().add(new PhoneNumber("123-123-1234"));
        per.getTty().add(new PhoneNumber("123-123-1234"));
        per.getUrl().add(new URL("http://example.com/"));
        
        PersonDTO dto = PoXsnapshotHelper.createSnapshot(per);
        Person clone = PoXsnapshotHelper.createModel(dto);
        
        EqualsByValue.assertEquals(per, clone);
    }

}