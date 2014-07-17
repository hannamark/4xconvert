package gov.nih.nci.po.webservices.convert.simple;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberType;
import gov.nih.nci.po.webservices.util.PoWSUtil;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the test class for FamilyMemberConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class FamilyMemberConverterTest extends AbstractConverterTest {

    private gov.nih.nci.po.webservices.types.FamilyMember familyMember;

    private gov.nih.nci.po.data.bo.FamilyOrganizationRelationship famOrgRelBo;

    @Before
    public void setUp() {
        // setting up gov.nih.nci.po.webservices.types.FamilyMember
        familyMember = new FamilyMember();
        familyMember.setOrganizationId(1l);
        familyMember.setType(FamilyMemberType.AFFILIATION);
        familyMember.setStartDate(toXMLGregorianCalendar(new Date()));
        familyMember.setEndDate(toXMLGregorianCalendar(new Date()));
        familyMember.setFamilyId(1l);

        // setting up gov.nih.nci.po.data.bo.FamilyOrganizationRelationship
        famOrgRelBo = new FamilyOrganizationRelationship();
        Organization organization = new Organization();
        organization.setId(1l);
        famOrgRelBo.setOrganization(organization);
        famOrgRelBo.setFunctionalType(FamilyFunctionalType.ORGANIZATIONAL);
        famOrgRelBo.setStartDate(new Date());
        famOrgRelBo.setEndDate(new Date());
        gov.nih.nci.po.data.bo.Family familyBo = new gov.nih.nci.po.data.bo.Family();
        familyBo.setId(1l);
        famOrgRelBo.setFamily(familyBo);

        super.setUpMockObjects();
    }

    @Test
    public void testConvertJaxbToBO() {
        FamilyMemberConverter fmConverter = new FamilyMemberConverter();
        gov.nih.nci.po.data.bo.FamilyOrganizationRelationship retFamOrgRel = fmConverter
                .convertFromJaxbToBO(familyMember);

        checkFamilyMemberForConvertJaxbToBO(familyMember, retFamOrgRel);
    }

    @Test
    public void testConvertJaxbToBOForNullFamilyMember() {
        FamilyMemberConverter fmConverter = new FamilyMemberConverter();
        gov.nih.nci.po.data.bo.FamilyOrganizationRelationship retFamOrgRel = fmConverter
                .convertFromJaxbToBO(null);

        assertEquals(null, retFamOrgRel);
    }

    @Test
    public void testConvertBOToJaxb() {
        FamilyMemberConverter fmConverter = new FamilyMemberConverter();
        gov.nih.nci.po.webservices.types.FamilyMember retFamilyMember = fmConverter
                .convertFromBOToJaxB(famOrgRelBo);

        checkFamilyMemberForConvertBOToJaxb(famOrgRelBo, retFamilyMember);
    }

    @Test
    public void testConvertBOToJaxbForNullFamilyOrgRelationship() {
        FamilyMemberConverter fmConverter = new FamilyMemberConverter();
        gov.nih.nci.po.webservices.types.FamilyMember retFamilyMember = fmConverter
                .convertFromBOToJaxB(null);

        assertEquals(null, retFamilyMember);
    }

    private void checkFamilyMemberForConvertJaxbToBO(
            gov.nih.nci.po.webservices.types.FamilyMember familyMember,
            gov.nih.nci.po.data.bo.FamilyOrganizationRelationship faOrgRelBo) {

        assertEquals(familyMember.getFamilyId(), faOrgRelBo.getFamily().getId()
                .longValue());
        assertEquals(familyMember.getOrganizationId(), faOrgRelBo
                .getOrganization().getId().longValue());
        assertEquals(familyMember.getType().name(), faOrgRelBo
                .getFunctionalType().name());
        assertTrue(areSameDates(familyMember.getStartDate(),
                faOrgRelBo.getStartDate()));
        assertTrue(areSameDates(familyMember.getEndDate(),
                faOrgRelBo.getEndDate()));

    }

    private void checkFamilyMemberForConvertBOToJaxb(
            gov.nih.nci.po.data.bo.FamilyOrganizationRelationship famOrgRelBo,
            gov.nih.nci.po.webservices.types.FamilyMember retFamilyMember) {

        assertEquals(famOrgRelBo.getOrganization().getId().longValue(),
                retFamilyMember.getOrganizationId());
        assertEquals(famOrgRelBo.getFamily().getId().longValue(),
                retFamilyMember.getFamilyId());
        assertEquals(famOrgRelBo.getFunctionalType().name(), retFamilyMember
                .getType().name());
        assertTrue(areSameDates(retFamilyMember.getStartDate(),
                famOrgRelBo.getStartDate()));
        assertTrue(areSameDates(retFamilyMember.getEndDate(),
                famOrgRelBo.getEndDate()));
    }

    private boolean areSameDates(XMLGregorianCalendar gcalendar, Date date) {
        return PoWSUtil.areSameDates(gcalendar, date);
    }
}
