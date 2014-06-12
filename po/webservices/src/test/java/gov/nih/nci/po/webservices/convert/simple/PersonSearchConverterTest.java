package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonSearchDTO.Affiliation;
import gov.nih.nci.po.service.PersonSearchDTO.Affiliation.RoleGroup;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * This is the test class for PersonSearchConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class PersonSearchConverterTest extends AbstractBaseSearchConverterTest {

    gov.nih.nci.po.service.PersonSearchDTO psDto = null;

    gov.nih.nci.po.webservices.types.PersonSearchCriteria psCriteria = null;

    @Before
    public void setUp() {
        psDto = new PersonSearchDTO();
        populateBaseSearchResultDTO(psDto);
        psDto.setFirstName("Vinay");
        psDto.setMiddleName("K");
        psDto.setLastName("Sambhi");
        psDto.setCtepID("12345");
        psDto.setTotalCrs(4);
        psDto.setTotalHcp(5);
        psDto.setTotalOc(9);
        psDto.setTotalPending(7);

        PersonSearchDTO.Affiliation affiliation1 = new PersonSearchDTO.Affiliation(
                "Mayo", true, RoleGroup.HCP);
        PersonSearchDTO.Affiliation affiliation2 = new PersonSearchDTO.Affiliation(
                "NCI", true, RoleGroup.OC);
        psDto.setAffiliation(new TreeSet<Affiliation>());
        psDto.getAffiliation().add(affiliation1);
        psDto.getAffiliation().add(affiliation2);

        psCriteria = new PersonSearchCriteria();
        populateBaseSearchCriteria(psCriteria);
        psCriteria.setFirstName("Ajay");
        psCriteria.setLastName("Kumarrr");
        psCriteria.setEmail("abc@xyz.com");
        psCriteria.setAffiliation("Mayo Clinic");
        psCriteria.setCtepID("133435");
        psCriteria.setStatusCode(EntityStatus.ACTIVE);
        psCriteria.setHasPendingCrsRoles(true);
        psCriteria.setHasPendingHcpRoles(true);
        psCriteria.setHasPendingOcRoles(true);
        super.setUpMockObjects();
    }

    @Test
    public void testConvertFromJaxbToBo() {
        PersonSearchConverter psConverter = new PersonSearchConverter();
        gov.nih.nci.po.service.PersonSearchCriteria retPsCriteria = psConverter
                .convertPSCFromJaxbToBO(psCriteria);

        assertAddressForConvertFromJaxbToBo(psCriteria, retPsCriteria
                .getPerson().getPostalAddress());

        assertBasicAttributesForConvertPSCFromJaxbToBO(psCriteria,
                retPsCriteria);
    }

    @Test
    public void testConvertFromBOToJaxB() {
        PersonSearchConverter psConverter = new PersonSearchConverter();
        PersonSearchResult retPsResult = (PersonSearchResult) psConverter
                .convertPSRFromBOToJaxB(psDto);

        // check for 'BaseSearchResult' attribute
        assertBSRForConvertFromBOToJaxB(psDto, retPsResult);

        // check for Basic Attributes
        assertBasicAttributesForConvertPSRFromBOToJaxB(psDto, retPsResult);
    }

    @Test
    public void testConvertFromBOToJaxBForNull() {
        PersonSearchConverter psConverter = new PersonSearchConverter();
        PersonSearchResult retPsResult = (PersonSearchResult) psConverter
                .convertPSRFromBOToJaxB(null);
        Assert.assertNull(retPsResult);
    }

    @Test
    public void testConvertFromBOToJaxBForNullAffilication() {
        PersonSearchConverter psConverter = new PersonSearchConverter();
        psDto.setAffiliation(null);
        PersonSearchResult retPsResult = (PersonSearchResult) psConverter
                .convertPSRFromBOToJaxB(psDto);

        // check for 'BaseSearchResult' attribute
        assertBSRForConvertFromBOToJaxB(psDto, retPsResult);

        // check for Basic Attributes
        assertBasicAttributesForConvertPSRFromBOToJaxB(psDto, retPsResult);

        Assert.assertEquals(new ArrayList<Affiliation>(),
                retPsResult.getAffiliation());
    }

    /**
     * This method is used to check basic attributes of PersonSearchResult
     * during conversion.
     */
    private void assertBasicAttributesForConvertPSRFromBOToJaxB(
            PersonSearchDTO psDto, PersonSearchResult retPsResult) {
        Assert.assertEquals(psDto.getCtepID(), retPsResult.getCtepID());
        Assert.assertEquals(psDto.getFirstName(), retPsResult.getFirstName());
        Assert.assertEquals(psDto.getMiddleName(), retPsResult.getMiddleName());
        Assert.assertEquals(psDto.getLastName(), retPsResult.getLastName());
        Assert.assertEquals(psDto.getTotalCrs(), retPsResult.getTotalCrs());
        Assert.assertEquals(psDto.getTotalHcp(), retPsResult.getTotalHcp());
        Assert.assertEquals(psDto.getTotalOc(), retPsResult.getTotalOc());
        Assert.assertEquals(psDto.getTotalPending(),
                retPsResult.getTotalPending());

        if (psDto.getAffiliation() != null) {
            Assert.assertEquals(psDto.getAffiliation().size(), retPsResult
                    .getAffiliation().size());
            Collection<Affiliation> affCollection = psDto.getAffiliation();
            for (Affiliation affiliation : affCollection) {
                for (int i = 0; i < retPsResult.getAffiliation().size(); i++) {
                    gov.nih.nci.po.webservices.types.Affiliation aff = retPsResult
                            .getAffiliation().get(i);
                    if (affiliation.getOrgName().equalsIgnoreCase(
                            aff.getOrgName())) {
                        Assert.assertEquals(affiliation.isPending(),
                                aff.isPending());
                        Assert.assertEquals(affiliation.getGroup().name(), aff
                                .getType().name());
                    }
                }
            }
        }

    }

    private void assertBasicAttributesForConvertPSCFromJaxbToBO(
            PersonSearchCriteria psCriteria,
            gov.nih.nci.po.service.PersonSearchCriteria psCriteriaBo) {
        Assert.assertEquals(psCriteria.getFirstName(),
                psCriteriaBo.getFirstName());
        Assert.assertEquals(psCriteria.getLastName(),
                psCriteriaBo.getLastName());
        Assert.assertEquals(psCriteria.getEmail(), psCriteriaBo.getEmail());
        Assert.assertEquals(psCriteria.getAffiliation(), psCriteriaBo.getOrg());
        Assert.assertEquals(psCriteria.getCtepID(), psCriteriaBo.getCtepID());
        Assert.assertEquals(psCriteria.getStatusCode().value(),
                psCriteriaBo.getStatusCode());
        Assert.assertEquals(psCriteria.isHasPendingCrsRoles(),
                psCriteriaBo.getHasPendingCrsRoles());
        Assert.assertEquals(psCriteria.isHasPendingHcpRoles(),
                psCriteriaBo.getHasPendingHcpRoles());
        Assert.assertEquals(psCriteria.isHasPendingOcRoles(),
                psCriteriaBo.getHasPendingOcRoles());
    }

}
