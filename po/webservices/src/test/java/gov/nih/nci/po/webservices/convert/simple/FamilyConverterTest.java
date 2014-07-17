package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyP30;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.FamilyOrganizationRelationshipOrgComparator;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberType;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import javax.xml.datatype.XMLGregorianCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This is the test class for FamilyConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class FamilyConverterTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.Family family;

    private gov.nih.nci.po.data.bo.Family familyBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.Family
        family = new Family();
        family.setId(4l);
        family.setName("Test Name");
        family.setStartDate(toXMLGregorianCalendar(new Date()));
        family.setP30SerialNumber("serialNumber");
        FamilyMember familyMember = new FamilyMember();
        familyMember.setOrganizationId(1l);
        familyMember.setType(FamilyMemberType.AFFILIATION);
        familyMember.setStartDate(toXMLGregorianCalendar(new Date()));
        familyMember.setEndDate(toXMLGregorianCalendar(new Date()));
        family.getMember().add(familyMember);
        family.setStatus(EntityStatus.ACTIVE);

        // setting up gov.nih.nci.po.data.bo.Family
        familyBo = new gov.nih.nci.po.data.bo.Family();        
        familyBo.setId(23l);
        familyBo.setName("Family BO test name");
        familyBo.setStartDate(new Date());
        familyBo.setStatusCode(FamilyStatus.ACTIVE);
        FamilyP30 fp30 = new FamilyP30();
        fp30.setSerialNumber("some serialNumber");
        familyBo.setFamilyP30(fp30);
        FamilyOrganizationRelationship faOrgRel = new FamilyOrganizationRelationship();
        Organization organization = new Organization();
        organization.setId(1l);        
        faOrgRel.setOrganization(organization);
        faOrgRel.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        faOrgRel.setStartDate(new Date());
        faOrgRel.setEndDate(new Date());        
        faOrgRel.setFamily(familyBo);
        familyBo.getFamilyOrganizationRelationships().add(faOrgRel);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        FamilyConverter fConverter = new FamilyConverter();
        gov.nih.nci.po.data.bo.Family familyBo = fConverter
                .convertFromJaxbToBO(family);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(family, familyBo);

        // Assertion for Family Member List
        checkFamilyMemberListForConvertJaxbToBO(family, familyBo);
    }

    @Test
    public void testConvertJaxbToBOForNullFamily() {
        FamilyConverter fConverter = new FamilyConverter();
        // Family is null while calling the converter
        gov.nih.nci.po.data.bo.Family retFamilyBo = fConverter
                .convertFromJaxbToBO(null);

        Assert.assertEquals(null, retFamilyBo);
    }

    @Test
    public void testConvertJaxbToBOForNullFamilyMember() {
        FamilyConverter fConverter = new FamilyConverter();

        // FamilyMember list is null while calling the converter
        family.getMember().clear();

        gov.nih.nci.po.data.bo.Family retFamilyBo = fConverter
                .convertFromJaxbToBO(family);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertJaxbToBO(family, retFamilyBo);

        // Assertion for Family Member List
        Assert.assertEquals(new TreeSet<FamilyOrganizationRelationship>(
                new FamilyOrganizationRelationshipOrgComparator()), retFamilyBo
                .getFamilyOrganizationRelationships());
    }

    @Test
    public void testConvertBOToJaxb() {
        FamilyConverter fConverter = new FamilyConverter();
        gov.nih.nci.po.webservices.types.Family retFamily = fConverter
                .convertFromBOToJaxB(familyBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(familyBo, retFamily);

        // Assertion for Family Member details
        checkFamilyMemberListForConvertBOToJaxb(familyBo, retFamily);
    }

    @Test
    public void testConvertBOToJaxbForNullFamily() {
        FamilyConverter fConverter = new FamilyConverter();
        gov.nih.nci.po.webservices.types.Family retFamily = fConverter
                .convertFromBOToJaxB(null);

        Assert.assertEquals(null, retFamily);
    }

    @Test
    public void testConvertBOToJaxbForNullFamilyMember() {
        FamilyConverter fConverter = new FamilyConverter();

        // FamilyMember list is null while calling the converter
        familyBo.getFamilyOrganizationRelationships().clear();

        gov.nih.nci.po.webservices.types.Family retFamily = fConverter
                .convertFromBOToJaxB(familyBo);

        // Assertion for Basic Attributes
        checkBasicAttributesForConvertBOToJaxb(familyBo, retFamily);

        // Assertion for Family Member List
        Assert.assertEquals(new ArrayList<FamilyMember>(),
                retFamily.getMember());
    }

    /**
     * This method is used to compare the basic attribute of Family object after
     * JAXB to BO Conversion
     * 
     * @param family
     *            JAXB object
     * @param retFamilyBo
     *            BO object after conversion
     */
    private void checkBasicAttributesForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Family family,
            gov.nih.nci.po.data.bo.Family retFamilyBo) {
        Assert.assertEquals(family.getId(), retFamilyBo.getId());
        Assert.assertEquals(family.getName(), retFamilyBo.getName());
        Assert.assertEquals(family.getP30SerialNumber(), retFamilyBo.getFamilyP30().getSerialNumber());
        Assert.assertEquals(family.getStatus().value(), retFamilyBo
                .getStatusCode().name());
        Assert.assertTrue(areSameDates(family.getStartDate(),
                retFamilyBo.getStartDate()));
    }

    /**
     * This method is used to compare the Family Members of Family object after
     * JAXB to BO Conversion
     * 
     * @param family
     *            JAXB object
     * @param retFamilyBo
     *            BO object after conversion
     */
    private void checkFamilyMemberListForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.Family family,
            gov.nih.nci.po.data.bo.Family retFamilyBo) {
        List<FamilyMember> fmList = family.getMember();
        SortedSet<FamilyOrganizationRelationship> forSet = retFamilyBo
                .getFamilyOrganizationRelationships();

        // check for the size
        Assert.assertEquals(fmList.size(), forSet.size());

        // check for the contents of the FamilyMembers
        for (FamilyMember familyMember : fmList) {
            SortedSet<FamilyOrganizationRelationship> faOrgRelSet = familyBo
                    .getFamilyOrganizationRelationships();
            Iterator<gov.nih.nci.po.data.bo.FamilyOrganizationRelationship> iterator = faOrgRelSet
                    .iterator();
            while (iterator.hasNext()) {
                gov.nih.nci.po.data.bo.FamilyOrganizationRelationship faOrgRel = iterator
                        .next();
                if (familyMember.getType().value()
                        .equalsIgnoreCase(faOrgRel.getFunctionalType().name())) {
                    // assuming both element are same on the basis of Type
                    Assert.assertEquals(familyMember.getOrganizationId(),
                            faOrgRel.getOrganization().getId().longValue());
                    Assert.assertEquals(familyMember.getType().name(), faOrgRel
                            .getFunctionalType().name());
                    Assert.assertTrue(areSameDates(familyMember.getStartDate(),
                            faOrgRel.getStartDate()));
                    Assert.assertTrue(areSameDates(familyMember.getEndDate(),
                            faOrgRel.getEndDate()));
                }
            }
        }

    }

    /**
     * This method is used to compare the basic attribute of Family object after
     * BO to JAXB Conversion
     * 
     * @param familyBo
     *            BO object
     * @param retFamily
     *            JAXB object after conversion
     */
    private void checkBasicAttributesForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Family familyBo,
            gov.nih.nci.po.webservices.types.Family retFamily) {
        Assert.assertEquals(familyBo.getId(), retFamily.getId());
        Assert.assertEquals(familyBo.getName(), retFamily.getName());
        Assert.assertEquals(familyBo.getFamilyP30().getSerialNumber(), retFamily.getP30SerialNumber());
        Assert.assertEquals(familyBo.getStatusCode().name(), retFamily
                .getStatus().value());
        Assert.assertTrue(areSameDates(retFamily.getStartDate(),
                familyBo.getStartDate()));
    }

    /**
     * This method is used to compare the address of Family object after BO to
     * JAXB Conversion
     * 
     * @param familyBo
     *            BO object
     * @param retFamily
     *            JAXB object after conversion
     */
    private void checkFamilyMemberListForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.Family familyBo,
            gov.nih.nci.po.webservices.types.Family retFamily) {

        SortedSet<FamilyOrganizationRelationship> forSet = familyBo
                .getFamilyOrganizationRelationships();
        List<FamilyMember> fmList = retFamily.getMember();

        // check for the size
        Assert.assertEquals(fmList.size(), forSet.size());

        // check for the contents of the FamilyOrganizationRelationship
        SortedSet<FamilyOrganizationRelationship> faOrgRelSet = familyBo
                .getFamilyOrganizationRelationships();
        Iterator<gov.nih.nci.po.data.bo.FamilyOrganizationRelationship> iterator = faOrgRelSet
                .iterator();
        while (iterator.hasNext()) {
            gov.nih.nci.po.data.bo.FamilyOrganizationRelationship faOrgRel = iterator
                    .next();
            for (FamilyMember familyMember : fmList) {
                if (faOrgRel.getFunctionalType().name()
                        .equalsIgnoreCase(familyMember.getType().value())) {
                    // assuming both element are same on the basis of Type
                    Assert.assertEquals(faOrgRel.getOrganization().getId()
                            .longValue(), familyMember.getOrganizationId());
                    Assert.assertEquals(faOrgRel.getFunctionalType().name(),
                            familyMember.getType().name());
                    Assert.assertTrue(areSameDates(familyMember.getStartDate(),
                            faOrgRel.getStartDate()));
                    Assert.assertTrue(areSameDates(familyMember.getEndDate(),
                            faOrgRel.getEndDate()));
                }
            }
        }
    }

    /**
     * This test case is added for Code Coverage
     */
    @Test
    public void testToXMLGregorianCalendarForNullDate() {
        FamilyConverter fConverter = new FamilyConverter();
        XMLGregorianCalendar date = fConverter.toXMLGregorianCalendar(null);
        Assert.assertNull(date);
    }

    /**
     * This test case is added for Code Coverage
     */
    @Test
    public void testToDateForNullDate() {
        FamilyConverter fConverter = new FamilyConverter();
        Assert.assertEquals(null, fConverter.toDate(null));
    }

    private boolean areSameDates(XMLGregorianCalendar gcalendar, Date date) {
        return PoWSUtil.areSameDates(gcalendar, date);
    }
}
