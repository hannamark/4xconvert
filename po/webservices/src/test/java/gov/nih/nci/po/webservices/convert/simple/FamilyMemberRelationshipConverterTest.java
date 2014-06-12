package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationshipType;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * This is the test class for FamilyMemberRelationship.
 * 
 * @author Rohit Gupta
 * 
 */
public class FamilyMemberRelationshipConverterTest extends
        AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.FamilyMemberRelationship fmr;

    private gov.nih.nci.po.data.bo.OrganizationRelationship orgRelBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.FamilyMemberRelationship
        fmr = new FamilyMemberRelationship();
        fmr.setId(45l);
        fmr.setFamilyId(1l);
        fmr.setOrganizationId(1l);
        fmr.setRelatedToOrganizationId(2l);
        fmr.setStartDate(toXMLGregorianCalendar(new Date()));
        fmr.setType(FamilyMemberRelationshipType.DEPARTMENT);

        // setting up gov.nih.nci.po.data.bo.OrganizationRelationship
        orgRelBo = new gov.nih.nci.po.data.bo.OrganizationRelationship();
        Family family = new Family();
        family.setId(25l);
        orgRelBo.setId(321l);
        orgRelBo.setFamily(family);
        Organization organization = new Organization();
        organization.setId(31l);
        orgRelBo.setOrganization(organization);
        Organization organization2 = new Organization();
        organization2.setId(39l);
        orgRelBo.setRelatedOrganization(organization2);
        orgRelBo.setHierarchicalType(FamilyHierarchicalType.SUBDIVISION);
        orgRelBo.setStartDate(new Date());

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        FamilyMemberRelationshipConverter fConverter = new FamilyMemberRelationshipConverter();
        gov.nih.nci.po.data.bo.OrganizationRelationship orgRelBo = fConverter
                .convertFromJaxbToBO(fmr);

        // Assertion for Basic Attributes
        Assert.assertEquals(fmr.getId(), orgRelBo.getId());
        Assert.assertEquals(fmr.getFamilyId(), orgRelBo.getFamily().getId()
                .longValue());
        Assert.assertEquals(fmr.getOrganizationId(), orgRelBo.getOrganization()
                .getId().longValue());
        Assert.assertEquals(fmr.getRelatedToOrganizationId(), orgRelBo
                .getRelatedOrganization().getId().longValue());
        Assert.assertEquals(fmr.getType().name(), orgRelBo
                .getHierarchicalType().name());
        Assert.assertTrue(areSameDates(fmr.getStartDate(),
                orgRelBo.getStartDate()));
    }

    @Test
    public void testConvertJaxbToBOForFamilyMemberRelationship() {
        FamilyMemberRelationshipConverter fConverter = new FamilyMemberRelationshipConverter();
        // FamilyMemberRelationship is null while calling the converter
        gov.nih.nci.po.data.bo.OrganizationRelationship retOrgRelBo = fConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retOrgRelBo);
    }

    @Test
    public void testConvertBOToJaxb() {
        FamilyMemberRelationshipConverter fConverter = new FamilyMemberRelationshipConverter();
        gov.nih.nci.po.webservices.types.FamilyMemberRelationship retFmr = fConverter
                .convertFromBOToJaxB(orgRelBo);

        // Assertion for Basic Attributes
        Assert.assertEquals(orgRelBo.getId(), retFmr.getId());
        Assert.assertEquals(orgRelBo.getFamily().getId().longValue(),
                retFmr.getFamilyId());
        Assert.assertEquals(orgRelBo.getOrganization().getId().longValue(),
                retFmr.getOrganizationId());
        Assert.assertEquals(orgRelBo.getRelatedOrganization().getId()
                .longValue(), retFmr.getRelatedToOrganizationId());
        Assert.assertEquals(orgRelBo.getHierarchicalType().name(), retFmr
                .getType().name());
        Assert.assertTrue(areSameDates(retFmr.getStartDate(),
                orgRelBo.getStartDate()));
    }

    @Test
    public void testConvertBOToJaxbForNullFamilyMemberRelationship() {
        FamilyMemberRelationshipConverter fConverter = new FamilyMemberRelationshipConverter();
        gov.nih.nci.po.webservices.types.FamilyMemberRelationship retFmr = fConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retFmr);
    }

    private boolean areSameDates(XMLGregorianCalendar gcalendar, Date date) {
        XMLGregorianCalendar gdate = toXMLGregorianCalendar(date);
        return gcalendar.getMillisecond() == gdate.getMillisecond();
    }
}
