package gov.nih.nci.po.webservices.service.simple.soap;


import gov.nih.nci.coppa.test.TstProperties;
import gov.nih.nci.po.webservices.service.simple.AbstractFamilyServiceTest;
import gov.nih.nci.po.webservices.service.simple.soap.family.FamilyService;
import gov.nih.nci.po.webservices.service.simple.soap.family.FamilyService_Service;
import gov.nih.nci.po.webservices.service.simple.soap.family.GetFamilyMemberRelationshipsByFamilyIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.GetFamilyMemberRelationshipsByFamilyIdResponse;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByNameRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByNameResponse;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByOrgIdRequest;
import gov.nih.nci.po.webservices.service.simple.soap.family.SearchFamiliesByOrgIdResponse;
import gov.nih.nci.po.webservices.service.utils.AuthUtils;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMember;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import junit.framework.Assert;
import org.apache.commons.dbutils.DbUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

/**
 * This is an Integration test class for FamilyService(SOAP).
 * 
 * @author Rohit Gupta
 * 
 */
public class FamilyServiceTest extends AbstractFamilyServiceTest {

    private FamilyService familyService = null;

    @BeforeClass
    public static void setUpClass() throws Exception {
        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        setUpFamilyServiceData();

        AuthUtils.removeBasicAuthSupport();
    }

    @Before
    public void setUp() throws Exception {
        // get FamilyService
        QName serviceName = new QName(
                "http://soap.simple.service.webservices.po.nci.nih.gov/family/",
                "FamilyService");
        URL url = new URL(TstProperties.getFamilyServiceURL());

        AuthUtils.addBasicAuthSupport(
                TstProperties.getWebserviceUsername(),
                TstProperties.getWebservicePassword()
        );

        FamilyService_Service service = new FamilyService_Service(url,
                serviceName);

        AuthUtils.removeBasicAuthSupport();

        familyService = service.getFamilyServicePort();

        final Binding binding = ((BindingProvider) familyService).getBinding();
        AuthUtils.addWsSecurityUTSupport(binding, TstProperties.getWebserviceUsername(), TstProperties.getWebservicePassword());

    }

    @Test
    public void testSearchFamiliesByNameWithoutAuthentication() {
        AuthUtils.addWsSecurityUTSupport(((BindingProvider) familyService).getBinding(), "bogusUser", "bogusPassword");
        SearchFamiliesByNameRequest req = new SearchFamiliesByNameRequest();
        req.setName("Cancer");
        try {
            familyService.searchFamiliesByName(req);
            fail();
        } catch (WebServiceException wse) {
            assertEquals("The server sent HTTP status code 401: Unauthorized", wse.getMessage().trim());
        }

    }

    /**
     * Testcase for FamilyService-searchFamiliesByName
     */
    @Test
    public void testSearchFamiliesByName() {
        SearchFamiliesByNameRequest req = new SearchFamiliesByNameRequest();
        req.setName("Cancer");
        SearchFamiliesByNameResponse res = familyService
                .searchFamiliesByName(req);
        List<Family> familyList = res.getFamilyList();
        Assert.assertNotNull(familyList);
        Assert.assertTrue(familyList.size() >= 2);
        for (Family fam : familyList) {
            Assert.assertTrue(fam.getName().contains("Cancer"));
        }
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName-no record found
     */
    @Test
    public void testSearchFamiliesByNameForNoRecordFound() {
        SearchFamiliesByNameRequest req = new SearchFamiliesByNameRequest();
        req.setName("Cancerrrrrrrrrrrrrrrrrrrrr");
        SearchFamiliesByNameResponse res = familyService
                .searchFamiliesByName(req);
        List<Family> familyList = res.getFamilyList();
        Assert.assertTrue(familyList.size() == 0);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName-search criteria not
     * specified
     */
    @Test
    public void testSearchFamiliesByNameForNameNotSpecified() {
        String excepMessage = null;
        SearchFamiliesByNameRequest req = new SearchFamiliesByNameRequest();
        req.setName(null);
        try {
            familyService.searchFamiliesByName(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage.contains("One of '{name}' is expected"));
    }

    /**
     * Testcase for FamilyService-searchFamiliesByOrgId
     */
    @Test
    public void testSearchFamiliesByOrgId() {
        SearchFamiliesByOrgIdRequest req = new SearchFamiliesByOrgIdRequest();
        req.setOrganizationId(org1.getId());
        SearchFamiliesByOrgIdResponse res = familyService
                .searchFamiliesByOrgId(req);
        List<Family> familyList = res.getFamilyList();
        Assert.assertNotNull(familyList);
        Assert.assertTrue(familyList.size() >= 2);

        for (Family fam : familyList) {
            List<FamilyMember> fmList = fam.getMember();
            boolean isOrgFound = false;
            for (FamilyMember fm : fmList) {
                if (org1.getId().longValue() == fm.getOrganizationId()) {
                    isOrgFound = true;
                }
            }
            if (!isOrgFound) {
                Assert.fail("FamilyMember doesn't contain the OrgId:"
                        + org1.getId());
            }
        }
    }

    /**
     * Testcase for FamilyService-searchFamiliesByOrgId - Org not found
     */
    @Test
    public void testSearchFamiliesByOrgIdForOrgNotFound() {
        String excepMessage = null;
        SearchFamiliesByOrgIdRequest req = new SearchFamiliesByOrgIdRequest();
        req.setOrganizationId(999999999999999999l); // no record present
        try {
            familyService.searchFamiliesByOrgId(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }

        Assert.assertTrue(excepMessage
                .contains("Family search couldn't be performed as Organization for Id: 999999999999999999 doesn't exist"));
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyId() {
        GetFamilyMemberRelationshipsByFamilyIdRequest req = new GetFamilyMemberRelationshipsByFamilyIdRequest();
        req.setFamilyId(familyId1);
        GetFamilyMemberRelationshipsByFamilyIdResponse res = familyService
                .getFamilyMemberRelationshipsByFamilyId(req);
        List<FamilyMemberRelationship> fmrList = res
                .getFamilyMemberRelationshipList();
        Assert.assertNotNull(fmrList);
        Assert.assertTrue(fmrList.size() >= 2);

        for (FamilyMemberRelationship fmr : fmrList) {
            Assert.assertEquals(familyId1, fmr.getFamilyId());
        }
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId- family
     * not found
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyIdForFamilyNotFound() {
        String excepMessage = null;
        GetFamilyMemberRelationshipsByFamilyIdRequest req = new GetFamilyMemberRelationshipsByFamilyIdRequest();
        req.setFamilyId(999999999999999999l);
        try {
            familyService.getFamilyMemberRelationshipsByFamilyId(req);
        } catch (Exception e) {
            excepMessage = e.getMessage();
        }
        Assert.assertTrue(excepMessage
                .contains("FamilyMemberRelationship couldn't be fetched as Family for Id: 999999999999999999 doesn't exist"));
    }

    @AfterClass
    public static void tearDownClass() {
        DbUtils.closeQuietly(conn);
    }

}
