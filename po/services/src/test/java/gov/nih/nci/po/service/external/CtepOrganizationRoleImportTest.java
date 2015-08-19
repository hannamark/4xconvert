package gov.nih.nci.po.service.external;

import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.common.exceptions.CTEPEntException;
import gov.nih.nci.coppa.services.OrganizationService;
import gov.nih.nci.coppa.services.PersonService;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.AbstractMockTest;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericOrganizationRoleServiceLocal;
import gov.nih.nci.po.service.HealthCareFacilityServiceBean;
import gov.nih.nci.po.service.HealthCareFacilityServiceLocal;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceBean;
import gov.nih.nci.po.service.IdentifiedOrganizationServiceLocal;
import gov.nih.nci.po.service.MessageProducerTest;
import gov.nih.nci.po.service.OrganizationServiceBean;
import gov.nih.nci.po.service.OrganizationServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceBean;
import gov.nih.nci.po.service.external.stubs.CTEPOrgServiceStubBuilder;
import gov.nih.nci.po.service.external.stubs.CTEPOrganizationServiceStub;
import gov.nih.nci.po.util.PoHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.NamingException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class CtepOrganizationRoleImportTest<ROLE_TYPE extends Correlation> extends AbstractMockTest {

    private static final String CTEP_LOGIN_NAME = "ctepecm";
    private static final Country USA = new Country("United States", "840", "US", "USA");

    private CtepOrganizationImporter importer;
    private Ii ctepId;

    protected Organization localOrg;
    private Ii localOrgIi;
    protected IdentifiedOrganization localIdentifiedOrganization;

    protected HealthCareFacility localHealthCareFacility;
    protected ResearchOrganization localResearchOrganization;

    private AbstractEnhancedOrganizationRole localRole;


    protected OrganizationService ctepOrganizationService;
    protected OrganizationDTO ctepOrganizationDto;
    protected HealthCareFacilityDTO ctepHealthCareFacilityDto;
    protected ResearchOrganizationDTO ctepReseachOrganizationDto;


    private User ctepUser;
    private User nonCtepUser;
    
    private Session mSession;
    private Transaction mTransaction; 

    @Before
    public void setup() throws CTEPEntException {
        setupServiceLocator();

        ctepUser = new User();
        ctepUser.setLoginName(CTEP_LOGIN_NAME);

        nonCtepUser = new User();
        nonCtepUser.setLoginName("nonCtepUser");

        //init org
        localOrg = new Organization();
        localOrg.setId(1L);
        localOrg.setName("currentOrgName");
        localOrg.setPostalAddress(getAddress());

        localOrgIi = new IdConverter.OrgIdConverter().convertToIi(1L);

        //mock org service local to return local org
        when(serviceLocator.getOrganizationService().getById(1L)).thenReturn(localOrg);

        //setup health care facility

        localHealthCareFacility = new HealthCareFacility();
        localHealthCareFacility.setId(1L);
        localHealthCareFacility.setPlayer(localOrg);
        localHealthCareFacility.setName("currentRoleName");
        when(serviceLocator.getHealthCareFacilityService().getById(1L)).thenReturn(localHealthCareFacility);

        //setup research org
        localResearchOrganization = new ResearchOrganization();
        localResearchOrganization.setId(1L);
        localResearchOrganization.setPlayer(localOrg);
        localResearchOrganization.setName("currentRoleName");

        when(serviceLocator.getResearchOrganizationService().getById(1L)).thenReturn(localResearchOrganization);

        ctepId = new Ii();
        ctepId.setExtension("12345");
        ctepId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepId.setIdentifierName("no name for ctep id");


        when(serviceLocator.getIdentifiedOrganizationService().search(any(SearchCriteria.class))).
            thenAnswer(new Answer<List<IdentifiedOrganization>>() {
                @Override
                public List<IdentifiedOrganization> answer(InvocationOnMock invocation) throws Throwable {
                    List<IdentifiedOrganization> results = new ArrayList<IdentifiedOrganization>();

                    if (localIdentifiedOrganization != null) {
                        results.add(localIdentifiedOrganization);
                    }

                    return results;
                }
            });

        ctepOrganizationService = mock(OrganizationService.class);
        ctepOrganizationDto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(localOrg);

        //stage ctep org
        when(ctepOrganizationService.getOrganizationById(localOrgIi))
                .thenAnswer( new Answer<OrganizationDTO>() {
                    @Override
                    public OrganizationDTO answer(InvocationOnMock invocation) throws Throwable {
                        return ctepOrganizationDto;
                    }
                });

        //stage hcf
        ctepHealthCareFacilityDto = (HealthCareFacilityDTO) PoXsnapshotHelper.createSnapshot(localHealthCareFacility);
        when(ctepOrganizationService.getHealthCareFacility(localOrgIi))
                .thenAnswer(new Answer<HealthCareFacilityDTO>() {
                    @Override
                    public HealthCareFacilityDTO answer(InvocationOnMock invocation) throws Throwable {
                        return  ctepHealthCareFacilityDto;
                    }
                });

        //stage research org
        ctepReseachOrganizationDto = (ResearchOrganizationDTO) PoXsnapshotHelper.createSnapshot(localResearchOrganization);
        when(ctepOrganizationService.getResearchOrganization(localOrgIi))
                .thenAnswer( new Answer<ResearchOrganizationDTO>() {
                    @Override
                    public ResearchOrganizationDTO answer(InvocationOnMock invocation) throws Throwable {
                        return ctepReseachOrganizationDto;
                    }
                });


        importer = new CtepOrganizationImporter(mock(Context.class));
        importer.setCtepOrgService(ctepOrganizationService);

        when(serviceLocator.getCountryService().getCountryByAlpha3("USA"))
                .thenReturn(USA);

        localRole = getRoleUnderTest();

        mSession = PoHibernateUtil.getCurrentSession();              
        mTransaction = mSession.beginTransaction(); 
    }

    private Address getAddress() {
        Address result = new Address();

        result.setStreetAddressLine("123 Elm St");
        result.setCityOrMunicipality("Herndon");
        result.setStateOrProvince("VA");
        result.setPostalCode("20171");
        result.setCountry(USA);

        return result;
    }

    protected abstract AbstractEnhancedOrganizationRole getRoleUnderTest();
    protected abstract void verifyRoleServiceCurateCalled() throws JMSException, EntityValidationException;
    protected abstract void verifyRoleChangeRequestCreated() throws EntityValidationException;
    protected abstract void verifyRoleServiceCurateNotCalled() throws EntityValidationException, JMSException;
    

    @Test
    public void testNameUpdatePersistsToOrganization() throws EntityValidationException, JMSException, CtepImportException {

        mTransaction.begin();
        //setup role
        //localRole is CTEP generated
        localRole.setCreatedBy(ctepUser);
        localRole.getOtherIdentifiers().add(ctepId);
        assertTrue(localRole.isCtepOwned());

        //localRole is not overridden
        localRole.setOverriddenBy(null);

        //setup org
        //localOrganization is CTEP generated
        localIdentifiedOrganization = new IdentifiedOrganization();
        localIdentifiedOrganization.setId(1L);
        localIdentifiedOrganization.setAssignedIdentifier(ctepId);
        localIdentifiedOrganization.setPlayer(localOrg);
        localIdentifiedOrganization.setCreatedBy(ctepUser);

        //localOrganization is not overridden
        localIdentifiedOrganization.setOverriddenBy(null);

        //the role name changes
        assertEquals(1, getCtepDto().getName().getPart().size());
        getCtepDto().getName().getPart().get(0).setValue("newRoleName");

        //then organization directly updated
        importer.importOrganization(localOrgIi);

        //verify org updated
//        verify(serviceLocator.getOrganizationService()).curate(localOrg);
        verifyRoleServiceCurateCalled();

        assertEquals("currentOrgName", localOrg.getName());

        assertEquals("newRoleName", localRole.getName());
        assertEquals(1, localRole.getAlias().size());
        assertEquals("currentRoleName", localRole.getAlias().get(0).getValue());
    }

    protected abstract AbstractEnhancedOrganizationRoleDTO getCtepDto();


    @Test
    public void testNameUpdatePersistsToOrganizationForOverriddenRole() throws JMSException, EntityValidationException, CtepImportException {


        //localRole is CTEP generated
        localRole.setCreatedBy(ctepUser);
        localRole.getOtherIdentifiers().add(ctepId);
        assertTrue(localRole.isCtepOwned());

        //localRole is overridden
        User otherUser = new User();
        otherUser.setLoginName("otherUser");
        localRole.setOverriddenBy(otherUser);

        //localOrganization is CTEP generated
        localIdentifiedOrganization = new IdentifiedOrganization();
        localIdentifiedOrganization.setId(1L);
        localIdentifiedOrganization.setAssignedIdentifier(ctepId);
        localIdentifiedOrganization.setPlayer(localOrg);
        localIdentifiedOrganization.setCreatedBy(ctepUser);


        //localOrganization is not overridden
        localIdentifiedOrganization.setOverriddenBy(null);

        //the role name changes
        assertEquals(1, getCtepDto().getName().getPart().size());
        getCtepDto().getName().getPart().get(0).setValue("newRoleName");

        //import
        importer.importOrganization(localOrgIi);

        //localOrg is not directly updated
        verify(serviceLocator.getOrganizationService(), never()).curate(localOrg);
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));

        //change request for role is generated
        verifyRoleServiceCurateNotCalled();
        verifyRoleChangeRequestCreated();

        //org updated accordingly
        assertEquals("currentOrgName", localOrg.getName());

        //role name is changed
        assertEquals("newRoleName", localRole.getName());

    }


    @Test
    public void testNameUpdatePersistsToOverriddenOrganization() throws EntityValidationException, JMSException, CtepImportException {
        //localRole is CTEP generated
        localRole.setCreatedBy(ctepUser);
        localRole.getOtherIdentifiers().add(ctepId);
        assertTrue(localRole.isCtepOwned());

        //localRole is not overridden
        localRole.setOverriddenBy(null);

        //localOrganization is CTEP generated
        localIdentifiedOrganization = new IdentifiedOrganization();
        localIdentifiedOrganization.setId(1L);
        localIdentifiedOrganization.setAssignedIdentifier(ctepId);
        localIdentifiedOrganization.setPlayer(localOrg);
        localIdentifiedOrganization.setCreatedBy(ctepUser);

        //localOrganization is overridden
        User otherUser  = new User();
        otherUser.setLoginName("otherUser");
        localOrg.setOverriddenBy(otherUser);

        //the role name changes
        assertEquals(1, getCtepDto().getName().getPart().size());
        getCtepDto().getName().getPart().get(0).setValue("newRoleName");


        importer.importOrganization(localOrgIi);

        //change request is generated
        //localOrg is not directly updated
        verify(serviceLocator.getOrganizationService(), never()).curate(localOrg);
        verify(serviceLocator.getOrganizationCRService()).create(any(OrganizationCR.class));

        //change request for role is generated
        verifyRoleServiceCurateNotCalled();
        verifyRoleChangeRequestCreated();
    }

}
