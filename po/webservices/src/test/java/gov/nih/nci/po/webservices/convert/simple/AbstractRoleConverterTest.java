package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import junit.framework.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This is a base class with common code to be used across different "Role"
 * testcases.
 * 
 * @author Rohit Gupta
 * 
 */
public abstract class AbstractRoleConverterTest extends AbstractConverterTest {

    protected void populatePersonRoleBOObject(AbstractPersonRole personRoleBo) {
        Person person = new Person();
        person.setId(12345l);
        personRoleBo.setPlayer(person); // Set Player
        Organization organization = new Organization();
        organization.setId(34567l);
        personRoleBo.setScoper(organization); // Set Scoper

        // Set Address list
        personRoleBo.getPostalAddresses().addAll(getBoAddressList());

        // Set Contact list
        populateBOContacts(personRoleBo);
    }

    protected void populatePersonRoleJaxbObject(
            gov.nih.nci.po.webservices.types.PersonRole personRole) {
        personRole.setPersonId(1l); // Set Player
        personRole.setOrganizationId(1l); // Set Scoper

        // Set Address list
        personRole.getAddress().addAll(getJaxbAddressList());

        // Set Contact list
        personRole.getContact().addAll(getJaxbContactList());
    }

    protected void populateOrganizationRoleJaxbObject(
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole) {
        orgRole.setOrganizationId(1l); // Set Player

        // Set Address list
        orgRole.getAddress().addAll(getJaxbAddressList());

        // Set Contact list
        orgRole.getContact().addAll(getJaxbContactList());
    }

    protected void populateOrganizationRoleBOObject(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole enOrgRoleBo) {
        Organization organization = new Organization();
        organization.setId(34567l);
        enOrgRoleBo.setPlayer(organization); // Set Player

        // Set Address list
        enOrgRoleBo.getPostalAddresses().addAll(getBoAddressList());

        // Set Contact list
        populateBOContacts(enOrgRoleBo);
    }

    protected void populateOrganizationRoleBOObject(
            gov.nih.nci.po.data.bo.AbstractOversightCommittee overCommBo) {
        Organization organization = new Organization();
        organization.setId(34567l);
        overCommBo.setPlayer(organization); // Set Player

        // Set Address list
        overCommBo.getPostalAddresses().addAll(getBoAddressList());

        // Set Contact list
        populateBOContacts(overCommBo);
    }

    protected void checkAddressForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.PersonRole perRole,
            gov.nih.nci.po.data.bo.AbstractPersonRole retPRBo) {

        List<Address> prAddresList = perRole.getAddress();
        Set<gov.nih.nci.po.data.bo.Address> prBoAddressSet = retPRBo
                .getPostalAddresses();

        // check for the size
        Assert.assertEquals(prAddresList.size(), prBoAddressSet.size());

        // check for the contents of the address
        for (Address prAddress : prAddresList) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = prBoAddressSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address prBoAddress = iterator.next();
                if (prAddress.getLine1().equalsIgnoreCase(
                        prBoAddress.getCityOrMunicipality())) {
                    // if both element represent same address
                    Assert.assertEquals(prAddress.getLine1(),
                            prBoAddress.getStreetAddressLine());
                    Assert.assertEquals(prAddress.getLine2(),
                            prBoAddress.getDeliveryAddressLine());
                    Assert.assertEquals(prAddress.getCity(),
                            prBoAddress.getCityOrMunicipality());
                    Assert.assertEquals(prAddress.getStateOrProvince(),
                            prBoAddress.getStateOrProvince());
                    Assert.assertEquals(prAddress.getCountry().name(),
                            prBoAddress.getCountry().getAlpha3());
                }
            }
        }
    }

    protected void checkContactsForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.PersonRole perRole,
            gov.nih.nci.po.data.bo.AbstractPersonRole retPRBo) {
        Assert.assertEquals("my.email@mayoclinic.org", retPRBo.getEmail()
                .get(0).getValue());
        Assert.assertEquals("571-456-1245", retPRBo.getPhone().get(0)
                .getValue());
        Assert.assertEquals("571-456-1245", retPRBo.getFax().get(0).getValue());
        Assert.assertEquals("571-123-1123", retPRBo.getTty().get(0).getValue());
        Assert.assertEquals("http://www.mayoclinic.org", retPRBo.getUrl()
                .get(0).getValue());
    }

    protected void checkAddressForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole,
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole enOrgRoleBo) {

        List<Address> orAddressList = orgRole.getAddress();
        Set<gov.nih.nci.po.data.bo.Address> orBoAddressSet = enOrgRoleBo
                .getPostalAddresses();

        // check for the size
        Assert.assertEquals(orAddressList.size(), orBoAddressSet.size());

        // check for the contents of the address
        for (Address orAddress : orAddressList) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = orBoAddressSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address orBoAddress = iterator.next();
                if (orAddress.getLine1().equalsIgnoreCase(
                        orBoAddress.getCityOrMunicipality())) {
                    // if both element represent same address
                    Assert.assertEquals(orAddress.getLine1(),
                            orBoAddress.getStreetAddressLine());
                    Assert.assertEquals(orAddress.getLine2(),
                            orBoAddress.getDeliveryAddressLine());
                    Assert.assertEquals(orAddress.getCity(),
                            orBoAddress.getCityOrMunicipality());
                    Assert.assertEquals(orAddress.getStateOrProvince(),
                            orBoAddress.getStateOrProvince());
                    Assert.assertEquals(orAddress.getCountry().name(),
                            orBoAddress.getCountry().getAlpha3());
                }
            }
        }
    }

    protected void checkContactsForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole orgRole,
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole retORBo) {
        Assert.assertEquals("my.email@mayoclinic.org", retORBo.getEmail()
                .get(0).getValue());
        Assert.assertEquals("571-456-1245", retORBo.getPhone().get(0)
                .getValue());
        Assert.assertEquals("571-456-1245", retORBo.getFax().get(0).getValue());
        Assert.assertEquals("571-123-1123", retORBo.getTty().get(0).getValue());
        Assert.assertEquals("http://www.mayoclinic.org", retORBo.getUrl()
                .get(0).getValue());
    }

    protected void checkAddressForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole overComm,
            gov.nih.nci.po.data.bo.AbstractOversightCommittee retOCBo) {

        List<Address> ocAddressList = overComm.getAddress();
        Set<gov.nih.nci.po.data.bo.Address> ocBoAddressSet = retOCBo
                .getPostalAddresses();

        // check for the size
        Assert.assertEquals(ocAddressList.size(), ocBoAddressSet.size());

        // check for the contents of the address
        for (Address ocAddress : ocAddressList) {
            Iterator<gov.nih.nci.po.data.bo.Address> iterator = ocBoAddressSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.Address ocBoAddress = iterator.next();
                if (ocAddress.getLine1().equalsIgnoreCase(
                        ocBoAddress.getCityOrMunicipality())) {
                    // if both element represent same address
                    Assert.assertEquals(ocAddress.getLine1(),
                            ocBoAddress.getStreetAddressLine());
                    Assert.assertEquals(ocAddress.getLine2(),
                            ocBoAddress.getDeliveryAddressLine());
                    Assert.assertEquals(ocAddress.getCity(),
                            ocBoAddress.getCityOrMunicipality());
                    Assert.assertEquals(ocAddress.getStateOrProvince(),
                            ocBoAddress.getStateOrProvince());
                    Assert.assertEquals(ocAddress.getCountry().name(),
                            ocBoAddress.getCountry().getAlpha3());
                }
            }
        }
    }

    protected void checkContactsForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.OrganizationRole overComm,
            gov.nih.nci.po.data.bo.AbstractOversightCommittee retOCBo) {
        Assert.assertEquals("my.email@mayoclinic.org", retOCBo.getEmail()
                .get(0).getValue());
        Assert.assertEquals("571-456-1245", retOCBo.getPhone().get(0)
                .getValue());
        Assert.assertEquals("571-456-1245", retOCBo.getFax().get(0).getValue());
        Assert.assertEquals("571-123-1123", retOCBo.getTty().get(0).getValue());
        Assert.assertEquals("http://www.mayoclinic.org", retOCBo.getUrl()
                .get(0).getValue());
    }

    protected void checkAddressForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractPersonRole prBo,
            gov.nih.nci.po.webservices.types.PersonRole retPR) {

        Set<gov.nih.nci.po.data.bo.Address> prBoAddressSet = prBo
                .getPostalAddresses();
        List<Address> prAddresList = retPR.getAddress();

        // check for the size
        Assert.assertEquals(prBoAddressSet.size(), prAddresList.size());

        // compare for the address contents
        Iterator<gov.nih.nci.po.data.bo.Address> iterator = prBoAddressSet
                .iterator();
        while (iterator.hasNext()) {
            gov.nih.nci.po.data.bo.Address prBoAddress = iterator.next();
            for (Address prAddress : prAddresList) {
                if (prBoAddress.getCityOrMunicipality().equalsIgnoreCase(
                        prAddress.getLine1())) {
                    Assert.assertEquals(prBoAddress.getStreetAddressLine(),
                            prAddress.getLine1());
                    Assert.assertEquals(prBoAddress.getDeliveryAddressLine(),
                            prAddress.getLine2());
                    Assert.assertEquals(prBoAddress.getCityOrMunicipality(),
                            prAddress.getCity());
                    Assert.assertEquals(prBoAddress.getStateOrProvince(),
                            prAddress.getStateOrProvince());
                    Assert.assertEquals(prBoAddress.getCountry().getAlpha3(),
                            prAddress.getCountry().name());
                }
            }
        }

    }

    protected void checkContactsForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractPersonRole prBo,
            gov.nih.nci.po.webservices.types.PersonRole retPR) {
        for (Contact contact : retPR.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(prBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(prBo.getPhone().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(prBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(prBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(prBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

        Assert.assertEquals("my.test@nci.gov", retPR.getContact().get(0)
                .getValue());
        Assert.assertEquals("571-563-0987", retPR.getContact().get(1)
                .getValue());
        Assert.assertEquals("571-576-0912", retPR.getContact().get(2)
                .getValue());
        Assert.assertEquals("571-123-4567", retPR.getContact().get(3)
                .getValue());
        Assert.assertEquals("http://nih.gov", retPR.getContact().get(4)
                .getValue());
    }

    protected void checkAddressForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole hcfBo,
            gov.nih.nci.po.webservices.types.OrganizationRole retHCF) {

        Set<gov.nih.nci.po.data.bo.Address> hcfBoAddressSet = hcfBo
                .getPostalAddresses();
        List<Address> hcfAddressList = retHCF.getAddress();

        // check for the size
        Assert.assertEquals(hcfBoAddressSet.size(), hcfAddressList.size());

        // compare for the address contents
        Iterator<gov.nih.nci.po.data.bo.Address> iterator = hcfBoAddressSet
                .iterator();
        while (iterator.hasNext()) {
            gov.nih.nci.po.data.bo.Address hcfBoAddress = iterator.next();
            for (Address hcfAddress : hcfAddressList) {
                if (hcfBoAddress.getCityOrMunicipality().equalsIgnoreCase(
                        hcfAddress.getLine1())) {
                    Assert.assertEquals(hcfBoAddress.getStreetAddressLine(),
                            hcfAddress.getLine1());
                    Assert.assertEquals(hcfBoAddress.getDeliveryAddressLine(),
                            hcfAddress.getLine2());
                    Assert.assertEquals(hcfBoAddress.getCityOrMunicipality(),
                            hcfAddress.getCity());
                    Assert.assertEquals(hcfBoAddress.getStateOrProvince(),
                            hcfAddress.getStateOrProvince());
                    Assert.assertEquals(hcfBoAddress.getCountry().getAlpha3(),
                            hcfAddress.getCountry().name());
                }
            }
        }
    }

    protected void checkContactsForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole hcfBo,
            gov.nih.nci.po.webservices.types.OrganizationRole retHCF) {
        for (Contact contact : retHCF.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(hcfBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(hcfBo.getPhone().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(hcfBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(hcfBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(hcfBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

        Assert.assertEquals("my.test@nci.gov", retHCF.getContact().get(0)
                .getValue());
        Assert.assertEquals("571-563-0987", retHCF.getContact().get(1)
                .getValue());
        Assert.assertEquals("571-576-0912", retHCF.getContact().get(2)
                .getValue());
        Assert.assertEquals("571-123-4567", retHCF.getContact().get(3)
                .getValue());
        Assert.assertEquals("http://nih.gov", retHCF.getContact().get(4)
                .getValue());
    }

    protected void checkAddressForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractOversightCommittee overCommBo,
            gov.nih.nci.po.webservices.types.OrganizationRole retOC) {

        Set<gov.nih.nci.po.data.bo.Address> ocBoAddressSet = overCommBo
                .getPostalAddresses();
        List<Address> ocAddressList = retOC.getAddress();

        // check for the size
        Assert.assertEquals(ocBoAddressSet.size(), ocAddressList.size());

        // compare for the address contents
        Iterator<gov.nih.nci.po.data.bo.Address> iterator = ocBoAddressSet
                .iterator();
        while (iterator.hasNext()) {
            gov.nih.nci.po.data.bo.Address ocBoAddress = iterator.next();
            for (Address ocAddress : ocAddressList) {
                if (ocBoAddress.getCityOrMunicipality().equalsIgnoreCase(
                        ocAddress.getLine1())) {
                    Assert.assertEquals(ocBoAddress.getStreetAddressLine(),
                            ocAddress.getLine1());
                    Assert.assertEquals(ocBoAddress.getDeliveryAddressLine(),
                            ocAddress.getLine2());
                    Assert.assertEquals(ocBoAddress.getCityOrMunicipality(),
                            ocAddress.getCity());
                    Assert.assertEquals(ocBoAddress.getStateOrProvince(),
                            ocAddress.getStateOrProvince());
                    Assert.assertEquals(ocBoAddress.getCountry().getAlpha3(),
                            ocAddress.getCountry().name());
                }
            }
        }
    }

    protected void checkContactsForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.AbstractOversightCommittee overCommBo,
            gov.nih.nci.po.webservices.types.OrganizationRole retOC) {
        for (Contact contact : retOC.getContact()) {
            if (contact.getType().value().equalsIgnoreCase("EMAIL")) {
                Assert.assertEquals(overCommBo.getEmail().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("PHONE")) {
                Assert.assertEquals(overCommBo.getPhone().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("FAX")) {
                Assert.assertEquals(overCommBo.getFax().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("TTY")) {
                Assert.assertEquals(overCommBo.getTty().get(0).getValue(),
                        contact.getValue());
            } else if (contact.getType().value().equalsIgnoreCase("URL")) {
                Assert.assertEquals(overCommBo.getUrl().get(0).getValue(),
                        contact.getValue());
            }
        }

        Assert.assertEquals("my.test@nci.gov", retOC.getContact().get(0)
                .getValue());
        Assert.assertEquals("571-563-0987", retOC.getContact().get(1)
                .getValue());
        Assert.assertEquals("571-576-0912", retOC.getContact().get(2)
                .getValue());
        Assert.assertEquals("571-123-4567", retOC.getContact().get(3)
                .getValue());
        Assert.assertEquals("http://nih.gov", retOC.getContact().get(4)
                .getValue());
    }
}
