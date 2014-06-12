package gov.nih.nci.po.webservices.service.simple;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.po.service.FamilyServiceLocal;
import gov.nih.nci.po.service.OrganizationRelationshipServiceLocal;
import gov.nih.nci.po.webservices.convert.simple.AbstractConverterTest;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.Family;
import gov.nih.nci.po.webservices.types.FamilyMemberRelationship;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This is the test class for FamilyServiceImpl.
 * 
 * @author Rohit Gupta
 * 
 */
public class FamilyServiceTest extends AbstractConverterTest {

    @Before
    public void setUp() {
        super.setUpMockObjects();
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName
     */
    @Test
    public void testSearchFamiliesByName() {
        FamilyService famService = new FamilyServiceImpl();
        List<Family> famList = famService.searchFamiliesByName("Cancer");

        Assert.assertNotNull(famList);
        Assert.assertTrue(famList.size() == 2);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName-Name is Null
     */
    @Test(expected = ServiceException.class)
    public void testSearchFamiliesByNameForNullName() {
        FamilyService famService = new FamilyServiceImpl();
        famService.searchFamiliesByName(null);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByName- Exception Scenario
     */
    @Test(expected = ServiceException.class)
    public void testSearchFamiliesByNameForExceptionScenario() {
        FamilyServiceLocal fsLocal = mock(FamilyServiceLocal.class);
        when(serviceLocator.getFamilyService()).thenReturn(fsLocal);
        when(
                fsLocal.search(isA(SearchCriteria.class),
                        isA(PageSortParams.class)))
                .thenThrow(
                        new ServiceException(
                                "Exception occured while performing searchFamiliesByName() for name: Cancer",
                                null));

        FamilyService famService = new FamilyServiceImpl();
        famService.searchFamiliesByName("Mayo");
    }

    /**
     * Testcase for FamilyService-searchFamiliesByOrgId
     */
    @Test
    public void testSearchFamiliesByOrgId() {
        FamilyService famService = new FamilyServiceImpl();
        List<Family> famList = famService.searchFamiliesByOrgId(1l);

        Assert.assertNotNull(famList);
        Assert.assertTrue(famList.size() == 2);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByOrgId-Org not exist
     */
    @Test(expected = ServiceException.class)
    public void testSearchFamiliesByOrgIdForOrgNotExist() {
        FamilyService famService = new FamilyServiceImpl();
        famService.searchFamiliesByOrgId(1002l);
    }

    /**
     * Testcase for FamilyService-searchFamiliesByOrgId- Exception Scenario
     */
    @Test(expected = ServiceException.class)
    public void testSearchFamiliesByOrgIdForExceptionScenario() {
        FamilyServiceLocal fsLocal = mock(FamilyServiceLocal.class);
        when(serviceLocator.getFamilyService()).thenReturn(fsLocal);
        when(
                fsLocal.search(isA(SearchCriteria.class),
                        isA(PageSortParams.class)))
                .thenThrow(
                        new ServiceException(
                                "Exception occured while performing searchFamiliesByOrgId() for Id:1l",
                                null));

        FamilyService famService = new FamilyServiceImpl();
        famService.searchFamiliesByOrgId(1l);
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId
     */
    @Test
    public void testGetFamilyMemberRelationshipsByFamilyId() {
        FamilyService famService = new FamilyServiceImpl();
        List<FamilyMemberRelationship> fmrList = famService
                .getFamilyMemberRelationshipsByFamilyId(1l);

        Assert.assertNotNull(fmrList);
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId-Family
     * not exist
     */
    @Test(expected = ServiceException.class)
    public void testGetFamilyMemberRelationshipsByFamilyIdForFamilyNotExist() {
        FamilyService famService = new FamilyServiceImpl();
        famService.getFamilyMemberRelationshipsByFamilyId(1002l);
    }

    /**
     * Testcase for FamilyService-getFamilyMemberRelationshipsByFamilyId- for
     * Exception Scenario
     */
    @Test(expected = ServiceException.class)
    public void testGetFamilyMemberRelationshipsByFamilyIdForExceptionScenario() {
        OrganizationRelationshipServiceLocal orgRelSerLocal = mock(OrganizationRelationshipServiceLocal.class);
        when(serviceLocator.getOrganizationRelationshipService()).thenReturn(
                orgRelSerLocal);
        when(orgRelSerLocal.search(isA(SearchCriteria.class)))
                .thenThrow(
                        new ServiceException(
                                "Exception occured in getFamilyMemberRelationshipsByFamilyId() for Id:1l",
                                null));
        FamilyService famService = new FamilyServiceImpl();
        famService.getFamilyMemberRelationshipsByFamilyId(1l);
    }
}
