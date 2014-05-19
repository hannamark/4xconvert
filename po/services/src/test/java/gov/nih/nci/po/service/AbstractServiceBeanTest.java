package gov.nih.nci.po.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.fiveamsolutions.nci.commons.util.JndiUtils;

public abstract class AbstractServiceBeanTest extends AbstractBeanTest {
    
    @BeforeClass
    public static void setUpJNDI() {
        contextBuilder.bind("java:app/po-services/ResearchOrganizationServiceBean", EjbTestHelper.getResearchOrganizationServiceBean());
        contextBuilder.bind("java:app/po-services/IdentifiedOrganizationServiceBean", EjbTestHelper.getIdentifiedOrganizationServiceBean());
        contextBuilder.bind("java:app/po-services/IdentifiedPersonServiceBean", EjbTestHelper.getIdentifiedPersonServiceBean());
        contextBuilder.bind("java:app/po-services/HealthCareProviderServiceBean", EjbTestHelper.getHealthCareProviderServiceBean());
        contextBuilder.bind("java:app/po-services/HealthCareFacilityServiceBean", EjbTestHelper.getHealthCareFacilityServiceBean());
        contextBuilder.bind("java:app/po-services/ClinicalResearchStaffServiceBean", EjbTestHelper.getClinicalResearchStaffServiceBean());
        contextBuilder.bind("java:app/po-services/OrganizationalContactServiceBean", EjbTestHelper.getOrganizationalContactService());
        contextBuilder.bind("java:app/po-services/OversightCommitteeServiceBean", EjbTestHelper.getOversightCommitteeServiceBean());
        contextBuilder.bind("java:app/po-services/PatientServiceBean", EjbTestHelper.getPatientServiceBean());
        contextBuilder.bind("java:app/po-services/FamilyServiceBean", EjbTestHelper.getFamilyServiceBean());
        contextBuilder.bind("java:app/po-services/FamilyOrganizationRelationshipServiceBean", EjbTestHelper.getFamilyOrganizationRelationshipService());
        contextBuilder.bind("java:app/po-services/OrganizationRelationshipServiceBean", EjbTestHelper.getOrganizationRelationshipService());
    }

    @AfterClass
    public static void tearDownJNDI() {
        contextBuilder.clear();
    }
    
    public HealthCareFacilityServiceBean getHealthCareFacilityServiceBean() {
        return (HealthCareFacilityServiceBean) JndiUtils.lookup("java:app/po-services/HealthCareFacilityServiceBean");
    }
    public ResearchOrganizationServiceBean getResearchOrganizationServiceBean() {
        return (ResearchOrganizationServiceBean) JndiUtils.lookup("java:app/po-services/ResearchOrganizationServiceBean");
    }
    public OversightCommitteeServiceBean getOversightCommitteeServiceBean() {
        return (OversightCommitteeServiceBean) JndiUtils.lookup("java:app/po-services/OversightCommitteeServiceBean");
    }
}
