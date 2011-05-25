package gov.nih.nci.po.data.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.FamilyOrganizationRelationshipServiceTest;
import gov.nih.nci.po.service.FamilyServiceBeanTest;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.services.PoIsoConstraintException;

import org.junit.Test;

/**
 *
 * @author gax
 */
public class IiConverterTest extends AbstractHibernateTestCase {
   @Test (expected = UnsupportedOperationException.class)
    public void testConvert() {
        Class<URL> returnClass = URL.class;
        IiConverter instance = new IiConverter();
        instance.convert(returnClass, new Ii());
    }

    @Test
    public void testConvertToLong() {
        Ii value = null;
        Long expResult = null;
        Long result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);

        value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);

        expResult = 2L;
        value.setNullFlavor(null);
        value.setExtension(expResult.toString());

        try {
            IiConverter.convertToLong(value);
            fail();
        } catch(IllegalArgumentException x){
            // "root must be set"
        }

        value.setRoot("123.456.789");
        result = IiConverter.convertToLong(value);
        assertEquals(expResult, result);
    }

    @Test(expected = PoIsoConstraintException.class)
    public void testExtension() {
        Ii iso = new Ii();
        iso.setRoot("tstroot");
        IiConverter.convertToLong(iso);
    }

    @Test
    public void testConvertToOrg() throws Exception {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();
        long orgId = orgTest.createOrganization();
        IiConverter converter = new IiConverter();

        Organization org = converter.convert(Organization.class, null);
        assertEquals(null, org);

        Ii value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        org = converter.convert(Organization.class, value);
        assertEquals(null, org);

        value = new Ii();
        value.setExtension("" + orgId);
        try {
            org = converter.convert(Organization.class, value);
            fail();
        } catch (PoIsoConstraintException e) {
            // expected
        }

        value.setRoot(IdConverter.ORG_ROOT);
        try {
            org = converter.convert(Organization.class, value);
            fail();
        } catch (PoIsoConstraintException e) {
            // expected
        }

        value.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        org = converter.convert(Organization.class, value);
        assertEquals(orgId, org.getId().longValue());
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToFamilyNoRoot() throws Exception {
        Ii value = new Ii();
        value.setIdentifierName(IdConverter.FAMILY_IDENTIFIER_NAME);
        value.setExtension("1");
        (new IiConverter()).convert(Family.class, value);
      
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToFamilyNoName() throws Exception {
        Ii value = new Ii();
        value.setRoot(IdConverter.FAMILY_ROOT);
        value.setExtension("1");
        (new IiConverter()).convert(Family.class, value);
      
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToFamilyOrgRelNoRoot() throws Exception {
        Ii value = new Ii();
        value.setIdentifierName(IdConverter.FAMILY_ORG_REL_IDENTIFIER_NAME);
        value.setExtension("1");
        (new IiConverter()).convert(FamilyOrganizationRelationship.class, value);
      
    }
    
    @Test(expected = PoIsoConstraintException.class)
    public void testConvertToFamilyOrgRelNoName() throws Exception {
        Ii value = new Ii();
        value.setRoot(IdConverter.FAMILY_ORG_REL_ROOT);
        value.setExtension("1");
        (new IiConverter()).convert(FamilyOrganizationRelationship.class, value);    
    }
    
    @Test
    public void testConvertToFamily() throws Exception {
        FamilyServiceBeanTest famTest = new FamilyServiceBeanTest();
        famTest.loadData();
        famTest.setUpData();
        long famId = famTest.createFamily();
        IiConverter converter = new IiConverter();

        Family fam = converter.convert(Family.class, null);
        assertEquals(null, fam);

        Ii value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        fam = converter.convert(Family.class, value);
        assertEquals(null, fam);

        value = new Ii();
        value.setExtension("" + famId);
        value.setRoot(IdConverter.FAMILY_ROOT);
        value.setIdentifierName(IdConverter.FAMILY_IDENTIFIER_NAME);
        fam = converter.convert(Family.class, value);
        assertEquals(famId, fam.getId().longValue());
    }
    
    @Test
    public void testConvertToFamilyOrganizationRelationship() throws Exception {
        FamilyOrganizationRelationshipServiceTest famOrgRelTest = new FamilyOrganizationRelationshipServiceTest();
        famOrgRelTest.loadData();
        famOrgRelTest.setUpData();
        Family fam = famOrgRelTest.createFamily();
        Organization org = famOrgRelTest.createOrg();
        long famId = famOrgRelTest.createFamOrgRel(fam, org).getId();
        IiConverter converter = new IiConverter();

        FamilyOrganizationRelationship famOrgRel = converter.convert(FamilyOrganizationRelationship.class, null);
        assertEquals(null, famOrgRel);

        Ii value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        famOrgRel = converter.convert(FamilyOrganizationRelationship.class, value);
        assertEquals(null, famOrgRel);

        value = new Ii();
        value.setExtension("" + famId);
        value.setRoot(IdConverter.FAMILY_ORG_REL_ROOT);
        value.setIdentifierName(IdConverter.FAMILY_ORG_REL_IDENTIFIER_NAME);
        famOrgRel = converter.convert(FamilyOrganizationRelationship.class, value);
        assertEquals(famId, famOrgRel.getId().longValue());
    }

    @Test
    public void testConvertToPerson() throws Exception {
        PersonServiceBeanTest personTest = new PersonServiceBeanTest();
        personTest.loadData();
        Person basicPerson = personTest.getBasicPerson();
        basicPerson.setStatusCode(EntityStatus.PENDING);
        Long personId = (Long) PoHibernateUtil.getCurrentSession().save(basicPerson);
        PoHibernateUtil.getCurrentSession().flush();
        IiConverter converter = new IiConverter();

        Person p = converter.convert(Person.class, null);
        assertEquals(null, p);

        Ii value = new Ii();
        value.setNullFlavor(NullFlavor.NI);
        p = converter.convert(Person.class, value);
        assertEquals(null, p);

        value = new Ii();
        value.setExtension("" + personId.longValue());
        try {
            p = converter.convert(Person.class, value);
            fail();
        } catch (PoIsoConstraintException e) {
            // expected
        }

        value.setRoot(IdConverter.PERSON_ROOT);
        try {
            p = converter.convert(Person.class, value);
            fail();
        } catch (PoIsoConstraintException e) {
            // expected
        }

        value.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        p = converter.convert(Person.class, value);
        assertEquals(personId.longValue(), p.getId().longValue());
    }
}