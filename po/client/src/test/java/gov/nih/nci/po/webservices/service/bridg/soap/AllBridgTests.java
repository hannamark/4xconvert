package gov.nih.nci.po.webservices.service.bridg.soap;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(value = {
        ClinicalResearchStaffClientTest.class,
        HealthCareFacilityClientTest.class,
        HealthCareProviderClientTest.class,
        IdentifiedOrganizationClientTest.class,
        IdentifiedPersonClientTest.class,
        OrganizationClientTest.class,
        OrganizationalContactClientTest.class,
        OversightCommitteeClientTest.class,
        PersonClientTest.class,
        ResearchOrganizationClientTest.class
})
public class AllBridgTests {
}
