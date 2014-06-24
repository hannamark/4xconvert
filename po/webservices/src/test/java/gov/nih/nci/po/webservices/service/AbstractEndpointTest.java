package gov.nih.nci.po.webservices.service;

import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.CountryServiceLocal;
import gov.nih.nci.po.service.GenericCodeValueServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationCRServiceLocal;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PersonCRServiceLocal;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractEndpointTest {

    protected ServiceLocator serviceLocator;


    public void setupServiceLocator() {
        serviceLocator = mock(ServiceLocator.class);
        PoRegistry.getInstance().setServiceLocator(serviceLocator);

        ClinicalResearchStaffServiceLocal clinicalResearchStaffServiceLocal = mock(ClinicalResearchStaffServiceLocal.class);
        when(serviceLocator.getClinicalResearchStaffService()).thenReturn(clinicalResearchStaffServiceLocal);

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

        HealthCareProviderServiceLocal healthCareProviderServiceLocal = mock(HealthCareProviderServiceLocal.class);
        when(serviceLocator.getHealthCareProviderService()).thenReturn(healthCareProviderServiceLocal);

        IdentifiedOrganizationServiceLocal identifiedOrganizationServiceLocal = mock(IdentifiedOrganizationServiceLocal.class);
        when(serviceLocator.getIdentifiedOrganizationService()).thenReturn(identifiedOrganizationServiceLocal);

        OrganizationalContactServiceLocal organizationalContactServiceLocal = mock(OrganizationalContactServiceLocal.class);
        when(serviceLocator.getOrganizationalContactService()).thenReturn(organizationalContactServiceLocal);

        OversightCommitteeServiceLocal oversightCommitteeServiceLocal = mock(OversightCommitteeServiceLocal.class);
        when(serviceLocator.getOversightCommitteeService()).thenReturn(oversightCommitteeServiceLocal);

        ResearchOrganizationServiceLocal researchOrganizationServiceLocal = mock(ResearchOrganizationServiceLocal.class);
        when(serviceLocator.getResearchOrganizationService()).thenReturn(researchOrganizationServiceLocal);

        GenericCodeValueServiceLocal genericCodeValueServiceLocal = mock(GenericCodeValueServiceLocal.class);
        when(serviceLocator.getGenericCodeValueService()).thenReturn(genericCodeValueServiceLocal);
    }


}
