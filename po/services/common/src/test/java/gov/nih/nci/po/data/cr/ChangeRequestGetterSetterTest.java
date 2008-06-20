package gov.nih.nci.po.data.cr;

import gov.nih.nci.po.data.bo.GetterSetterTesterUtil;

import org.junit.Test;

public class ChangeRequestGetterSetterTest {

     /**
     * Test AddressCR getter and setters
     */
    @Test
    public void testAddressCRGetterSetters() {
        AddressCR addressCR = new AddressCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(addressCR);
    }

    /**
     * Test CertificateCR getter and setters
     */
    @Test
    public void testCertificateCRGetterSetters() {
        CertificateCR certificateCR = new CertificateCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(certificateCR);
    }

    /**
     * Test ContactInfoCR getter and setters
     */
    @Test
    public void testContactInfoCRGetterSetters() {
        ContactInfoCR contactInfoCR = new ContactInfoCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(contactInfoCR);
    }

    /**
     * Test DegreeCR getter and setters
     */
    @Test
    public void testDegreeCRGetterSetters() {
        DegreeCR degreeCR = new DegreeCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(degreeCR);
    }

    /**
     * Test EmailCR getter and setters
     */
    @Test
    public void testEmailCRGetterSetters() {
        EmailCR emailCR = new EmailCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(emailCR);
    }

    /**
     * Test LicenseCR getter and setters
     */
    @Test
    public void testLicenseCRGetterSetters() {
        LicenseCR licenseCR = new LicenseCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(licenseCR);
    }

    /**
     * Test OrganizationCR getter and setters
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testOrganzationCRGetterSetters() {
        OrganizationCR organizationCR = new OrganizationCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(organizationCR);
    }

    /**
     * Test PersonCR getter and setters
     */
    @Test
    public void testPersonCRGetterSetters() {
        PersonCR personCR = new PersonCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(personCR);
    }

    /**
     * Test PhoneNumberCR getter and setters
     */
    @Test
    public void testPhoneNumberCRGetterSetters() {
        PhoneNumberCR phoneNumberCR = new PhoneNumberCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(phoneNumberCR);
    }

    /**
     * Test SpecialityCR getter and setters
     */
    @Test
    public void testSpecialityCRGetterSetters() {
        SpecialityCR specialityCR = new SpecialityCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(specialityCR);
    }

    /**
     * Test URLCR getter and setters
     */
    @Test
    public void testURLCRGetterSetters() {
        URLCR urlCR = new URLCR();
        GetterSetterTesterUtil.assertBasicGetterSetterBehavior(urlCR);
    }
}
