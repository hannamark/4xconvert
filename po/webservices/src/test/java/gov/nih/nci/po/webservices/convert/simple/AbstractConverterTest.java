package gov.nih.nci.po.webservices.convert.simple;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Family;
import gov.nih.nci.po.data.bo.FamilyFunctionalType;
import gov.nih.nci.po.data.bo.FamilyHierarchicalType;
import gov.nih.nci.po.data.bo.FamilyOrganizationRelationship;
import gov.nih.nci.po.data.bo.FamilyStatus;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationRelationship;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommittee;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
import gov.nih.nci.po.service.ClinicalResearchStaffServiceLocal;
import gov.nih.nci.po.service.CountryServiceLocal;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.FamilyOrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.FamilyServiceLocal;
import gov.nih.nci.po.service.GenericCodeValueServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.HealthCareProviderServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonCrServiceLocal;
import gov.nih.nci.po.service.IdentifiedPersonServiceLocal;
import gov.nih.nci.po.service.OrganizationRelationshipServiceLocal;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSearchDTO;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.OversightCommitteeServiceLocal;
import gov.nih.nci.po.service.PersonCRServiceLocal;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.ServiceLocator;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.CountryISO31661Alpha3Code;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;

import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * This is a base class with common code to be used across different testcases.
 * 
 * @author Rohit Gupta
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( SecurityServiceProvider.class )
public abstract class AbstractConverterTest {

    protected ServiceLocator serviceLocator = null;

    /**
     * This method is used to setup different Mock objects.
     */
    @SuppressWarnings("unchecked")
    protected void setUpMockObjects() {
        try {
            serviceLocator = mock(ServiceLocator.class);
            PoRegistry.getInstance().setServiceLocator(serviceLocator);
            // Mock setup for getting Country
            CountryServiceLocal countryServiceLocal = mock(CountryServiceLocal.class);
            when(serviceLocator.getCountryService()).thenReturn(
                    countryServiceLocal);
            when(countryServiceLocal.getCountryByAlpha3("USA")).thenReturn(
                    new Country("United States", null, null, "USA"));

            // Mock setup for getting Organization
            OrganizationServiceLocal orgSerLocal = mock(OrganizationServiceLocal.class);
            when(serviceLocator.getOrganizationService()).thenReturn(
                    orgSerLocal);
            when(
                    orgSerLocal.search(isA(OrganizationSearchCriteria.class),
                            isA(PageSortParams.class))).thenReturn(
                    getOrgSearchDtoList());
            Organization organization2 = new Organization();
            organization2.setId(2l);
            organization2.setName("Mayo Health Clinic");
            when(orgSerLocal.getById(2l)).thenReturn(organization2);
            when(orgSerLocal.getById(1002l)).thenReturn(null);
            when(orgSerLocal.create(isA(Organization.class))).thenReturn(1l);
            doNothing().when(orgSerLocal).curate(isA(Organization.class));

            Organization organization = new Organization();
            organization.setId(1l);
            organization.setName("Mayo Clinic");
            organization.setStatusCode(EntityStatus.PENDING);
            organization.getHealthCareFacilities().add(
                    getHealthCareFacilityBO(organization2));
            organization.getOversightCommittees().add(
                    getOversightCommitteeBO(organization2));
            organization.getResearchOrganizations().add(
                    getResearchOrganizationBO(organization2));
            List<Alias> aliases = new ArrayList<Alias>();
            Alias alias1 = new Alias("Org Alias111");
            Alias alias2 = new Alias("Org Alias222");
            aliases.add(alias1);
            aliases.add(alias2);
            organization.getAlias().addAll(aliases);            
            when(orgSerLocal.getById(1l)).thenReturn(organization);

            // Mock setup for getting Person
            PersonServiceLocal personServiceLocal = mock(PersonServiceLocal.class);
            when(serviceLocator.getPersonService()).thenReturn(
                    personServiceLocal);
            gov.nih.nci.po.data.bo.Person personBo = new Person();
            personBo.setId(1l);
            personBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.ACTIVE);
            personBo.getHealthCareProviders().add(
                    getHealthCareProviderBO(personBo, organization));
            personBo.getClinicalResearchStaff().add(
                    getClinicalResearchStaffBO(personBo, organization));
            personBo.getOrganizationalContacts().add(
                    getOrganizationalContactBO(personBo, organization));
            when(personServiceLocal.getById(1l)).thenReturn(personBo);
            when(personServiceLocal.getById(1002l)).thenReturn(null);
            when(personServiceLocal.create(isA(Person.class))).thenReturn(1l);
            when(personServiceLocal.create(isA(Person.class), isA(String.class))).thenReturn(1l);
            when(personServiceLocal.create(null)).thenThrow(
                    new EntityValidationException("Invalid input data", null));
            doNothing().when(personServiceLocal).curate(isA(Person.class));
            doNothing().when(personServiceLocal).curate(isA(Person.class), isA(String.class));
            when(
                    personServiceLocal
                            .search(isA(gov.nih.nci.po.service.PersonSearchCriteria.class),
                                    isA(PageSortParams.class))).thenReturn(
                    getPersonSearchDTOList()).thenReturn(
                    new ArrayList<PersonSearchDTO>());

            // Mock setup for getting Code/Value
            GenericCodeValueServiceLocal gcvLocal = mock(GenericCodeValueServiceLocal.class);
            when(serviceLocator.getGenericCodeValueService()).thenReturn(
                    gcvLocal);
            // getting OversightCommitteeType
            OversightCommitteeType ocType = new OversightCommitteeType(
                    "Ethics Committee");
            when(
                    gcvLocal.getByCode(OversightCommitteeType.class,
                            "Ethics Committee")).thenReturn(ocType);
            // getting ResearchOrganizationType
            ResearchOrganizationType roType = new ResearchOrganizationType(
                    "CCR", "CCR");
            when(gcvLocal.getByCode(ResearchOrganizationType.class, "CCR"))
                    .thenReturn(roType);
            // getting FundingMechanism
            FundingMechanism fundMech = new gov.nih.nci.po.data.bo.FundingMechanism("P30", "Center Core Grants", 
                    "Research Program Projects and Centers", FundingMechanismStatus.ACTIVE);
            when(gcvLocal.getByCode(FundingMechanism.class, "P30"))
                    .thenReturn(fundMech);
            // getting OrganizationalContactType
            OrganizationalContactType orgConType = new OrganizationalContactType(
                    "IRB");
            when(gcvLocal.getByCode(OrganizationalContactType.class, "IRB"))
                    .thenReturn(orgConType);

            // Mock setup for getting IdentifiedPerson
            IdentifiedPersonServiceLocal ipSerLocal = mock(IdentifiedPersonServiceLocal.class);
            when(serviceLocator.getIdentifiedPersonService()).thenReturn(
                    ipSerLocal);
            gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
            assIden.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
            assIden.setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);
            IdentifiedPerson idenPerson1 = new IdentifiedPerson();
            idenPerson1.setPlayer(personBo);
            idenPerson1.setAssignedIdentifier(assIden);
            List<IdentifiedPerson> identifiedPeople = new ArrayList<IdentifiedPerson>();
            identifiedPeople.add(idenPerson1);
            when(ipSerLocal.create(isA(IdentifiedPerson.class))).thenReturn(1l);
            doNothing().when(ipSerLocal).curate(isA(IdentifiedPerson.class));
            when(ipSerLocal.search(isA(SearchCriteria.class))).thenReturn(
                    identifiedPeople).thenReturn(
                    new ArrayList<IdentifiedPerson>());

            // Mock setup for getting HealthCareProvider
            HealthCareProviderServiceLocal hcplocal = mock(HealthCareProviderServiceLocal.class);
            when(serviceLocator.getHealthCareProviderService()).thenReturn(
                    hcplocal);
            gov.nih.nci.po.data.bo.HealthCareProvider hcp = getHealthCareProviderBO(
                    personBo, organization);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.HealthCareProvider hcp = (HealthCareProvider) args[0];
                    hcp.setId(1l);
                    return hcp;
                }
            }).when(hcplocal).curate(isA(HealthCareProvider.class));
            when(hcplocal.getById(isA(Long.class))).thenReturn(hcp);
            when(hcplocal.getById(54672l)).thenReturn(null);

            // Mock setup for getting ClinicalResearchStaff
            ClinicalResearchStaffServiceLocal crslocal = mock(ClinicalResearchStaffServiceLocal.class);
            when(serviceLocator.getClinicalResearchStaffService()).thenReturn(
                    crslocal);
            ClinicalResearchStaff crs = getClinicalResearchStaffBO(personBo,
                    organization);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.ClinicalResearchStaff crs = (ClinicalResearchStaff) args[0];
                    crs.setId(1l);
                    return crs;
                }
            }).when(crslocal).curate(isA(ClinicalResearchStaff.class));
            when(crslocal.getById(isA(Long.class))).thenReturn(crs);

            // Mock setup for getting OrganizationalContact
            OrganizationalContactServiceLocal oclocal = mock(OrganizationalContactServiceLocal.class);
            when(serviceLocator.getOrganizationalContactService()).thenReturn(
                    oclocal);
            OrganizationalContact oc = getOrganizationalContactBO(personBo,
                    organization);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.OrganizationalContact oc = (OrganizationalContact) args[0];
                    oc.setId(1l);
                    return oc;
                }
            }).when(oclocal).curate(isA(OrganizationalContact.class));
            when(oclocal.getById(isA(Long.class))).thenReturn(oc);

            // Mock setup for getting HealthCareFacility
            HealthCareFacilityServiceLocal hcflocal = mock(HealthCareFacilityServiceLocal.class);
            when(serviceLocator.getHealthCareFacilityService()).thenReturn(
                    hcflocal);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.HealthCareFacility hcf = (HealthCareFacility) args[0];
                    hcf.setId(1l);
                    return hcf;
                }
            }).when(hcflocal).curate(isA(HealthCareFacility.class));
            gov.nih.nci.po.data.bo.HealthCareFacility hcf = getHealthCareFacilityBO(organization);
            when(hcflocal.getById(isA(Long.class))).thenReturn(hcf);
            when(hcflocal.getById(543210l)).thenReturn(null);
            when(hcflocal.search(isA(SearchCriteria.class))).thenReturn(
                    getHCFBoList(organization));

            // Mock setup for getting OversightCommittee
            OversightCommitteeServiceLocal overCommlocal = mock(OversightCommitteeServiceLocal.class);
            when(serviceLocator.getOversightCommitteeService()).thenReturn(
                    overCommlocal);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.OversightCommittee oc = (OversightCommittee) args[0];
                    oc.setId(1l);
                    return oc;
                }
            }).when(overCommlocal).curate(isA(OversightCommittee.class));
            gov.nih.nci.po.data.bo.OversightCommittee overComm = getOversightCommitteeBO(organization);
            when(overCommlocal.getById(isA(Long.class))).thenReturn(overComm);

            // Mock setup for getting ResearchOrganization
            ResearchOrganizationServiceLocal rolocal = mock(ResearchOrganizationServiceLocal.class);
            when(serviceLocator.getResearchOrganizationService()).thenReturn(
                    rolocal);
            doAnswer(new Answer<Object>() {
                public Object answer(InvocationOnMock invocation) {
                    Object[] args = invocation.getArguments();
                    gov.nih.nci.po.data.bo.ResearchOrganization ro = (ResearchOrganization) args[0];
                    ro.setId(1l);
                    return ro;
                }
            }).when(rolocal).curate(isA(ResearchOrganization.class));
            gov.nih.nci.po.data.bo.ResearchOrganization ro = getResearchOrganizationBO(organization);
            when(rolocal.getById(isA(Long.class))).thenReturn(ro);
            when(rolocal.search(isA(SearchCriteria.class))).thenReturn(
                    getROBoList(organization));

            // Mock setup for getting Family
            FamilyServiceLocal familyServiceLocal = mock(FamilyServiceLocal.class);
            when(serviceLocator.getFamilyService()).thenReturn(
                    familyServiceLocal);
            Family familyBo1 = new Family();
            familyBo1.setId(1l);
            familyBo1.setName("Duke Cancer Institute");
            familyBo1.setStatusCode(FamilyStatus.ACTIVE);
            familyBo1.getFamilyOrganizationRelationships().add(
                    getFamilyOrganizationRelationshipList(familyBo1).get(0));
            familyBo1.getFamilyOrganizationRelationships().add(
                    getFamilyOrganizationRelationshipList(familyBo1).get(1));
            when(familyServiceLocal.getById(1l)).thenReturn(familyBo1);

            when(familyServiceLocal.getById(1002l)).thenReturn(null);

            Family familyBo2 = new Family();
            familyBo2.setId(2l);
            familyBo2.setName("Fox Chase Cancer Center");
            familyBo2.setStatusCode(FamilyStatus.ACTIVE);
            familyBo2.getFamilyOrganizationRelationships().add(
                    getFamilyOrganizationRelationshipList(familyBo2).get(1));
            List<Family> familyList = new ArrayList<Family>();
            familyList.add(familyBo1);
            familyList.add(familyBo2);
            when(
                    familyServiceLocal.search(isA(SearchCriteria.class),
                            isA(PageSortParams.class))).thenReturn(familyList);

            // Mock setup for getting OrganizationRelationshipService
            OrganizationRelationshipServiceLocal orgRelSerLocal = mock(OrganizationRelationshipServiceLocal.class);
            when(serviceLocator.getOrganizationRelationshipService())
                    .thenReturn(orgRelSerLocal);
            OrganizationRelationship orgRel = new OrganizationRelationship();
            orgRel.setId(1l);
            orgRel.setStartDate(new Date());
            orgRel.setHierarchicalType(FamilyHierarchicalType.DEPARTMENT);
            orgRel.setFamily(familyBo1);
            orgRel.setOrganization(organization);
            orgRel.setRelatedOrganization(organization2);
            List<OrganizationRelationship> orgRelBoList = new ArrayList<OrganizationRelationship>();
            orgRelBoList.add(orgRel);
            when(orgRelSerLocal.search(isA(SearchCriteria.class))).thenReturn(
                    orgRelBoList);
            
            // Mock setup for getting FamilyOrganizationRelationship
            FamilyOrganizationRelationshipServiceLocal famOrgRelSerLocal = mock(FamilyOrganizationRelationshipServiceLocal.class);
            when(serviceLocator.getFamilyOrganizationRelationshipService())
                    .thenReturn(famOrgRelSerLocal);
            when(famOrgRelSerLocal.getById(1l)).thenReturn(getFamilyOrganizationRelationshipList(familyBo1).get(0));
            when(famOrgRelSerLocal.getById(1002l)).thenReturn(null);
            
            //mock security
            UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
            when(userProvisioningManager.getUser(isA(String.class)))
                    .thenAnswer(new Answer<User>() {
                        @Override
                        public User answer(InvocationOnMock invocation) throws Throwable {
                            String username = (String) invocation.getArguments()[0];

                            User user = new User();
                            user.setLoginName(username);

                            return user;
                        }
                    });

            mockStatic(SecurityServiceProvider.class);
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po"))
                    .thenReturn(userProvisioningManager);

            //Mock PersonCRService
            PersonCRServiceLocal personCRServiceLocal = mock(PersonCRServiceLocal.class);
            when(serviceLocator.getPersonCRService()).thenReturn(personCRServiceLocal);

            //Mock IdentifiedPersonCRService
            IdentifiedPersonCrServiceLocal identifiedPersonCrServiceLocal = mock(IdentifiedPersonCrServiceLocal.class);
            when(serviceLocator.getIdentifiedPersonCRService()).thenReturn(identifiedPersonCrServiceLocal);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private HealthCareProvider getHealthCareProviderBO(Person person,
            Organization organization) {
        gov.nih.nci.po.data.bo.HealthCareProvider hcp = new HealthCareProvider();
        hcp.setId(1l);
        hcp.setCertificateLicenseText("certificateLicenseText");
        hcp.setStatus(RoleStatus.ACTIVE);
        hcp.setPlayer(person);
        hcp.setScoper(organization);
        hcp.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(hcp);
        return hcp;
    }

    private ClinicalResearchStaff getClinicalResearchStaffBO(Person person,
            Organization organization) {
        ClinicalResearchStaff crs = new ClinicalResearchStaff();
        crs.setId(23l);
        crs.setPlayer(person);
        crs.setScoper(organization);
        crs.setStatus(RoleStatus.ACTIVE);
        crs.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(crs);
        return crs;
    }

    private OrganizationalContact getOrganizationalContactBO(Person person,
            Organization organization) {
        OrganizationalContact oc = new OrganizationalContact();
        oc.setId(3456l);
        oc.setPlayer(person);
        oc.setScoper(organization);
        oc.setStatus(RoleStatus.ACTIVE);
        OrganizationalContactType orgConType = new OrganizationalContactType(
                "IRB");
        oc.setType(orgConType);
        oc.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(oc);
        return oc;
    }

    private List<HealthCareFacility> getHCFBoList(Organization org) {
        List<HealthCareFacility> hcfList = new ArrayList<HealthCareFacility>();
        hcfList.add(getHealthCareFacilityBO(org));
        return hcfList;
    }

    private HealthCareFacility getHealthCareFacilityBO(Organization playerOrg) {
        gov.nih.nci.po.data.bo.HealthCareFacility hcf = new HealthCareFacility();
        hcf.setId(1l);
        hcf.setName("Mayo HCF");
        hcf.setStatus(RoleStatus.PENDING);
        hcf.setPlayer(playerOrg);
        hcf.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(hcf);
        return hcf;
    }

    private OversightCommittee getOversightCommitteeBO(Organization playerOrg) {
        gov.nih.nci.po.data.bo.OversightCommittee oc = new OversightCommittee();
        oc.setId(1l);
        oc.setTypeCode(new OversightCommitteeType("Ethics Committee"));
        oc.setStatus(RoleStatus.PENDING);
        oc.setPlayer(playerOrg);
        oc.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(oc);
        return oc;
    }

    private List<ResearchOrganization> getROBoList(Organization org) {
        List<ResearchOrganization> roList = new ArrayList<ResearchOrganization>();
        roList.add(getResearchOrganizationBO(org));
        return roList;
    }

    private ResearchOrganization getResearchOrganizationBO(
            Organization playerOrg) {
        gov.nih.nci.po.data.bo.ResearchOrganization ro = new ResearchOrganization();
        ro.setId(1l);
        ro.setName("Mayo RO");
        ro.setStatus(RoleStatus.PENDING);
        ro.setTypeCode(new ResearchOrganizationType("RSB", "RSB"));
        ro.setPlayer(playerOrg);
        ro.getPostalAddresses().add(getBoAddressList().get(0));
        populateBOContacts(ro);
        return ro;
    }

    private List<PersonSearchDTO> getPersonSearchDTOList() {
        List<PersonSearchDTO> psDtoList = new ArrayList<PersonSearchDTO>();

        PersonSearchDTO psDto1 = new PersonSearchDTO();
        psDto1.setId(1234l);
        psDto1.setFirstName("Rohit");
        psDto1.setLastName("Gupta");
        psDto1.setStatusCode("ACTIVE");
        psDto1.setCity("Herndon");
        psDto1.setCountry("United States");
        psDto1.setCountryCode("USA");

        PersonSearchDTO psDto2 = new PersonSearchDTO();
        psDto2.setId(13478l);
        psDto2.setFirstName("RohitKumar");
        psDto2.setLastName("Jain");
        psDto2.setStatusCode("ACTIVE");
        psDto2.setCity("Atlanta");
        psDto2.setCountry("United States");
        psDto2.setCountryCode("USA");

        psDtoList.add(psDto1);
        psDtoList.add(psDto2);
        return psDtoList;
    }

    /**
     * This method is used to get list of JAXB Address.
     */
    protected List<gov.nih.nci.po.webservices.types.Address> getJaxbAddressList() {
        List<gov.nih.nci.po.webservices.types.Address> addressList = new ArrayList<Address>();
        gov.nih.nci.po.webservices.types.Address address1 = new Address();
        address1.setLine1("13621 Leagcy Circle");
        address1.setLine2("Apt G");
        address1.setCity("Herndon");
        address1.setStateOrProvince("VA");
        address1.setCountry(CountryISO31661Alpha3Code.USA);
        address1.setPostalcode("20171");

        gov.nih.nci.po.webservices.types.Address address2 = new Address();
        address2.setLine1("200 1st St");
        address2.setLine2("SW # W4");
        address2.setCity("Rochester");
        address2.setStateOrProvince("MN");
        address2.setCountry(CountryISO31661Alpha3Code.USA);
        address2.setPostalcode("55901");

        addressList.add(address1);
        addressList.add(address2);

        return addressList;
    }

    /**
     * This method is used to get list of BO Address.
     */
    protected List<gov.nih.nci.po.data.bo.Address> getBoAddressList() {
        List<gov.nih.nci.po.data.bo.Address> addressList = new ArrayList<gov.nih.nci.po.data.bo.Address>();
        gov.nih.nci.po.data.bo.Address addressBo1 = new gov.nih.nci.po.data.bo.Address();
        addressBo1.setStreetAddressLine("12253 Exbury St.");
        addressBo1.setDeliveryAddressLine("Near Reston Hospital");
        addressBo1.setCityOrMunicipality("Reston");
        addressBo1.setStateOrProvince("Virginia");
        addressBo1.setPostalCode("20166");
        addressBo1.setCountry(new Country("United States", null, null, "USA"));

        gov.nih.nci.po.data.bo.Address addressBo2 = new gov.nih.nci.po.data.bo.Address();
        addressBo2.setStreetAddressLine("2251 Centreville Rd");
        addressBo2.setDeliveryAddressLine("Herndon Clock Tower");
        addressBo2.setCityOrMunicipality("Hernodn");
        addressBo2.setPostalCode("20170");
        addressBo2.setCountry(new Country("United States", null, null, "USA"));

        addressList.add(addressBo1);
        addressList.add(addressBo2);

        return addressList;
    }

    /**
     * This method is used to get list of JAXB Contacts.
     */
    protected List<gov.nih.nci.po.webservices.types.Contact> getJaxbContactList() {
        List<gov.nih.nci.po.webservices.types.Contact> contactList = new ArrayList<Contact>();

        Contact emailContact = new Contact();
        emailContact.setType(ContactType.EMAIL);
        emailContact.setValue("my.email@mayoclinic.org");

        Contact phoneContact = new Contact();
        phoneContact.setType(ContactType.PHONE);
        phoneContact.setValue("571-456-1245");

        Contact faxContact = new Contact();
        faxContact.setType(ContactType.FAX);
        faxContact.setValue("571-456-1245");

        Contact ttyContact = new Contact();
        ttyContact.setType(ContactType.TTY);
        ttyContact.setValue("571-123-1123");

        Contact urlContact = new Contact();
        urlContact.setType(ContactType.URL);
        urlContact.setValue("http://www.mayoclinic.org");

        contactList.add(emailContact);
        contactList.add(phoneContact);
        contactList.add(faxContact);
        contactList.add(ttyContact);
        contactList.add(urlContact);
        return contactList;
    }

    /**
     * This method is used to populate different Contact in BO Object.
     * 
     */
    protected void populateBOContacts(Contactable contactableBo) {
        contactableBo.getEmail().add(new Email("my.test@nci.gov"));
        contactableBo.getPhone().add(new PhoneNumber("571-563-0987"));
        contactableBo.getFax().add(new PhoneNumber("571-576-0912"));
        contactableBo.getTty().add(new PhoneNumber("571-123-4567"));
        contactableBo.getUrl().add(new URL("http://nih.gov"));
    }

    protected XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            System.err.println(ex);
        }
        return xmlCalendar;
    }

    private List<OrganizationSearchDTO> getOrgSearchDtoList() {
        List<OrganizationSearchDTO> orgSearchDtoList = new ArrayList<OrganizationSearchDTO>();
        OrganizationSearchDTO dto = new OrganizationSearchDTO();
        dto.setId(1l);
        dto.setName("Cancer Therapy Evaluation Program");
        dto.setStatusCode("ACTIVE");
        dto.setStatusDate(new Date());
        orgSearchDtoList.add(dto);
        return orgSearchDtoList;
    }

    private List<FamilyOrganizationRelationship> getFamilyOrganizationRelationshipList(Family familyBo) {
        List<FamilyOrganizationRelationship> forList = new ArrayList<FamilyOrganizationRelationship>();

        Organization organization = new Organization();
        organization.setId(123l);
        organization.setStatusCode(EntityStatus.ACTIVE);

        FamilyOrganizationRelationship for0 = new FamilyOrganizationRelationship();
        for0.setStartDate(new Date());
        for0.setFunctionalType(FamilyFunctionalType.AFFILIATION);
        for0.setId(12345l);
        for0.setOrganization(organization);
        for0.setFamily(familyBo);

        FamilyOrganizationRelationship for1 = new FamilyOrganizationRelationship();
        for1.setStartDate(new Date());
        for1.setFunctionalType(FamilyFunctionalType.CONTRACTUAL);
        for1.setId(123456l);
        for1.setOrganization(organization);
        for1.setFamily(familyBo);

        forList.add(for0);
        forList.add(for1);
        return forList;
    }
}
