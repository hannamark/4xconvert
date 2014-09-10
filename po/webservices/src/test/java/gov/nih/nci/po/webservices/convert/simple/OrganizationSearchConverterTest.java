package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationSearchDTO.AliasDTO;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is the test class for OrganizationSearchConverter.
 * 
 * @author Rohit Gupta
 * 
 */
public class OrganizationSearchConverterTest extends
        AbstractBaseSearchConverterTest {

    gov.nih.nci.po.service.OrganizationSearchDTO osDto = null;

    gov.nih.nci.po.webservices.types.OrganizationSearchCriteria osCriteria = null;

    @Before
    public void setUp() {
        osDto = new OrganizationSearchDTO();
        populateBaseSearchResultDTO(osDto);
        osDto.setName("Mayo Clinic");
        osDto.setRoCtepId("12345");
        osDto.setHcfCtepId("2345678");
        osDto.setIoCtepId("9876543");
        osDto.setChangeRequests(2);
        osDto.setPendingROs(1);
        osDto.setPendingHCFs(2);
        osDto.setStatusDate(new Date());
        osDto.setTotalROs(4);
        osDto.setTotalHCFs(3);
        osDto.setTotalIdOrgs(1);
        osDto.setTotalOversightCommitees(1);
        osDto.setTotalOrgContacts(2);        
        if(osDto.getAliasDto() == null){
            osDto.setAliasDto(new ArrayList<OrganizationSearchDTO.AliasDTO>()); 
        }
        List<AliasDTO> aliasDtoList = new ArrayList<OrganizationSearchDTO.AliasDTO>();
        aliasDtoList.add(new AliasDTO("Org Alias 123"));
        osDto.getAliasDto().addAll(aliasDtoList);

        osCriteria = new OrganizationSearchCriteria();
        populateBaseSearchCriteria(osCriteria);
        osCriteria.setCtepID("133435");
        osCriteria.setId(12789l);
        osCriteria.setStatusCode(EntityStatus.ACTIVE);
        osCriteria.setOrganizationName("Mayo");
        osCriteria.setFamilyName("Cancer Centre");
        osCriteria.setHasChangeRequest(true);
        osCriteria.setHasPendingRoRoles(false);
        osCriteria.setHasPendingHcfRoles(true);
        osCriteria.setSearchAliases(true);
        super.setUpMockObjects();
    }

    @Test
    public void testConvertFromJaxbToBo() {
        OrganizationSearchConverter osConverter = new OrganizationSearchConverter();
        gov.nih.nci.po.service.OrganizationSearchCriteria retPsCriteria = osConverter
                .convertOSCFromJaxbToBO(osCriteria);

        assertAddressForConvertFromJaxbToBo(osCriteria, retPsCriteria
                .getOrganization().getPostalAddress());

        assertBasicAttributesForConvertOSCFromJaxbToBO(osCriteria,
                retPsCriteria);
    }

    @Test
    public void testConvertFromBOToJaxB() {
        OrganizationSearchConverter osConverter = new OrganizationSearchConverter();
        OrganizationSearchResult retPsResult = (OrganizationSearchResult) osConverter
                .convertOSRFromBOToJaxB(osDto);

        // check for 'BaseSearchResult' attribute
        assertBSRForConvertFromBOToJaxB(osDto, retPsResult);

        // check for Basic Attributes
        assertBasicAttributesForConvertOSRFromBOToJaxB(osDto, retPsResult);
    }

    @Test
    public void testConvertFromBOToJaxBForNull() {
        OrganizationSearchConverter osConverter = new OrganizationSearchConverter();
        OrganizationSearchResult retPsResult = (OrganizationSearchResult) osConverter
                .convertOSRFromBOToJaxB(null);
        Assert.assertNull(retPsResult);
    }

    /**
     * This method is used to check basic attributes of OrganizationSearchResult
     * during conversion.
     */
    private void assertBasicAttributesForConvertOSRFromBOToJaxB(
            OrganizationSearchDTO osDto, OrganizationSearchResult retOsRes) {
        Assert.assertEquals(osDto.getName(), retOsRes.getOrganizationName());
        Assert.assertEquals(osDto.getFamilyName(), retOsRes.getFamilyName());
        Assert.assertEquals(osDto.getRoCtepId(), retOsRes.getRoCtepID());
        Assert.assertEquals(osDto.getHcfCtepId(), retOsRes.getHcfCtepID());
        Assert.assertEquals(osDto.getIoCtepId(), retOsRes.getOrgCtepId());
        Assert.assertEquals(osDto.getPendingROs(), retOsRes.getPendingROs());
        Assert.assertEquals(osDto.getPendingHCFs(), retOsRes.getPendingHCFs());
        Assert.assertTrue(PoWSUtil.areSameDates(retOsRes.getStatusDate(),
                osDto.getStatusDate()));
        Assert.assertEquals(osDto.getTotalROs(), retOsRes.getTotalROs());
        Assert.assertEquals(osDto.getTotalHCFs(), retOsRes.getTotalHCFs());
        Assert.assertEquals(osDto.getTotalIdOrgs(), retOsRes.getTotalIdOrgs());
        Assert.assertEquals(osDto.getTotalOversightCommitees(),
                retOsRes.getTotalOversightCommitees());
        Assert.assertEquals(osDto.getTotalOrgContacts(),
                retOsRes.getTotalOrgContacts());
        Assert.assertEquals(osDto.getAliasDto().size(),
                retOsRes.getAlias().size());
    }

    private void assertBasicAttributesForConvertOSCFromJaxbToBO(
            OrganizationSearchCriteria osCriteria,
            gov.nih.nci.po.service.OrganizationSearchCriteria osCriteriaBo) {
        Assert.assertEquals(osCriteria.getCtepID(), osCriteriaBo.getCtepID());
        Assert.assertEquals(osCriteria.getStatusCode().value(),
                osCriteriaBo.getStatusCode());
        Assert.assertEquals(osCriteria.getOrganizationName(),
                osCriteriaBo.getName());
        Assert.assertEquals(osCriteria.getFamilyName(),
                osCriteriaBo.getFamilyName());
        Assert.assertEquals(osCriteria.isHasChangeRequest(),
                osCriteriaBo.getHasChangeRequests());
        Assert.assertEquals(osCriteria.isHasChangeRequest(),
                osCriteriaBo.getHasChangeRequests());
        Assert.assertEquals(osCriteria.isHasPendingHcfRoles(),
                osCriteriaBo.getHasPendingHcfRoles());
        Assert.assertEquals(osCriteria.isHasPendingRoRoles(),
                osCriteriaBo.getHasPendingRoRoles());
        Assert.assertEquals(osCriteria.isSearchAliases(),
                osCriteriaBo.getSearchAliases());
    }

}
