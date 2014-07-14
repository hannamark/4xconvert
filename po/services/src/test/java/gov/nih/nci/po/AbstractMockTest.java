package gov.nih.nci.po;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.service.ClinicalResearchStaffCRServiceLocal;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.CountryServiceLocal;
import gov.nih.nci.po.service.GenericCodeValueServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityCRServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderCRServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactCRServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeCRServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PersonCRServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationCRServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.CsmUserUtil;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractMockTest {

    protected ServiceLocator serviceLocator;
    protected User me;
    protected UserProvisioningManager userProvisioningManager;

    public void setupServiceLocator() {
        serviceLocator = mock(ServiceLocator.class);
        PoRegistry.getInstance().setServiceLocator(serviceLocator);

        ClinicalResearchStaffServiceLocal clinicalResearchStaffServiceLocal = mock(ClinicalResearchStaffServiceLocal.class);
        when(serviceLocator.getClinicalResearchStaffService()).thenReturn(clinicalResearchStaffServiceLocal);

        ClinicalResearchStaffCRServiceLocal clinicalResearchStaffCRServiceLocal = mock(ClinicalResearchStaffCRServiceLocal.class);
        when(serviceLocator.getClinicalResearchStaffCRService()).thenReturn(clinicalResearchStaffCRServiceLocal);

        CountryServiceLocal countryServiceLocal = mock(CountryServiceLocal.class);
        when(serviceLocator.getCountryService()).thenReturn(countryServiceLocal);

        PersonServiceLocal personServiceLocal = mock(PersonServiceLocal.class);
        when(serviceLocator.getPersonService()).thenReturn(personServiceLocal);

        PersonCRServiceLocal personCRServiceLocal = mock(PersonCRServiceLocal.class);
        when(serviceLocator.getPersonCRService()).thenReturn(personCRServiceLocal);

        OrganizationServiceLocal organizationServiceLocal = mock(OrganizationServiceLocal.class);
        when(serviceLocator.getOrganizationService()).thenReturn(organizationServiceLocal);

        OrganizationCRServiceLocal organizationCRServiceLocal = mock(OrganizationCRServiceLocal.class);
        when(serviceLocator.getOrganizationCRService()).thenReturn(organizationCRServiceLocal);

        IdentifiedPersonServiceLocal identifiedPersonServiceLocal = mock(IdentifiedPersonServiceLocal.class);
        when(serviceLocator.getIdentifiedPersonService()).thenReturn(identifiedPersonServiceLocal);

        IdentifiedPersonCrServiceLocal identifiedPersonCrServiceLocal = mock(IdentifiedPersonCrServiceLocal.class);
        when(serviceLocator.getIdentifiedPersonCRService()).thenReturn(identifiedPersonCrServiceLocal);

        HealthCareFacilityServiceLocal healthCareFacilityServiceLocal = mock(HealthCareFacilityServiceLocal.class);
        when(serviceLocator.getHealthCareFacilityService()).thenReturn(healthCareFacilityServiceLocal);

        HealthCareFacilityCRServiceLocal healthCareFacilityCRServiceLocal = mock(HealthCareFacilityCRServiceLocal.class);
        when(serviceLocator.getHealthCareFacilityCRService()).thenReturn(healthCareFacilityCRServiceLocal);

        HealthCareProviderServiceLocal healthCareProviderServiceLocal = mock(HealthCareProviderServiceLocal.class);
        when(serviceLocator.getHealthCareProviderService()).thenReturn(healthCareProviderServiceLocal);

        HealthCareProviderCRServiceLocal healthCareProviderCRServiceLocal = mock(HealthCareProviderCRServiceLocal.class);
        when(serviceLocator.getHealthCareProviderCRService()).thenReturn(healthCareProviderCRServiceLocal);

        IdentifiedOrganizationServiceLocal identifiedOrganizationServiceLocal = mock(IdentifiedOrganizationServiceLocal.class);
        when(serviceLocator.getIdentifiedOrganizationService()).thenReturn(identifiedOrganizationServiceLocal);

        IdentifiedOrganizationCrServiceLocal identifiedOrganizationCRServiceLocal = mock(IdentifiedOrganizationCrServiceLocal.class);
        when(serviceLocator.getIdentifiedOrganizationCRService()).thenReturn(identifiedOrganizationCRServiceLocal);

        OrganizationalContactServiceLocal organizationalContactServiceLocal = mock(OrganizationalContactServiceLocal.class);
        when(serviceLocator.getOrganizationalContactService()).thenReturn(organizationalContactServiceLocal);

        OrganizationalContactCRServiceLocal organizationalContactCRServiceLocal = mock(OrganizationalContactCRServiceLocal.class);
        when(serviceLocator.getOrganizationalContactCRService()).thenReturn(organizationalContactCRServiceLocal);

        OversightCommitteeServiceLocal oversightCommitteeServiceLocal = mock(OversightCommitteeServiceLocal.class);
        when(serviceLocator.getOversightCommitteeService()).thenReturn(oversightCommitteeServiceLocal);

        OversightCommitteeCRServiceLocal oversightCommitteeCRServiceLocal = mock(OversightCommitteeCRServiceLocal.class);
        when(serviceLocator.getOversightCommitteeCRService()).thenReturn(oversightCommitteeCRServiceLocal);

        ResearchOrganizationServiceLocal researchOrganizationServiceLocal = mock(ResearchOrganizationServiceLocal.class);
        when(serviceLocator.getResearchOrganizationService()).thenReturn(researchOrganizationServiceLocal);

        ResearchOrganizationCRServiceLocal researchOrganizationCRServiceLocal = mock(ResearchOrganizationCRServiceLocal.class);
        when(serviceLocator.getResearchOrganizationCRService()).thenReturn(researchOrganizationCRServiceLocal);

        GenericCodeValueServiceLocal genericCodeValueServiceLocal = mock(GenericCodeValueServiceLocal.class);
        when(serviceLocator.getGenericCodeValueService()).thenReturn(genericCodeValueServiceLocal);
    }


}

