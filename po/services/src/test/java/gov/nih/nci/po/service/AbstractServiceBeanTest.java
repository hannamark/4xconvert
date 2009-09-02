package gov.nih.nci.po.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractServiceBeanTest extends AbstractBeanTest {
    
    @BeforeClass
    public static void setUpJNDI() {
        contextBuilder.bind("po/ResearchOrganizationServiceBean/local", EjbTestHelper.getResearchOrganizationServiceBean());
        contextBuilder.bind("po/IdentifiedOrganizationServiceBean/local", EjbTestHelper.getIdentifiedOrganizationServiceBean());
        contextBuilder.bind("po/IdentifiedPersonServiceBean/local", EjbTestHelper.getIdentifiedPersonServiceBean());
        contextBuilder.bind("po/HealthCareProviderServiceBean/local", EjbTestHelper.getHealthCareProviderServiceBean());
        contextBuilder.bind("po/HealthCareFacilityServiceBean/local", EjbTestHelper.getHealthCareFacilityServiceBean());
        contextBuilder.bind("po/ClinicalResearchStaffServiceBean/local", EjbTestHelper.getClinicalResearchStaffServiceBean());
        contextBuilder.bind("po/OrganizationalContactServiceBean/local", EjbTestHelper.getOrganizationalContactService());
        contextBuilder.bind("po/OversightCommitteeServiceBean/local", EjbTestHelper.getOversightCommitteeServiceBean());
        contextBuilder.bind("po/PatientServiceBean/local", EjbTestHelper.getPatientServiceBean());
    }

    @AfterClass
    public static void tearDownJNDI() {
        contextBuilder.clear();
    }
}
