package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.ADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.convert.bridg.OrganizationTransformer;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bo.OrganizationBoService;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class OrganizationServiceImplTest extends AbstractEndpointTest {

    OrganizationServiceImpl service;
    OrganizationBoService organizationBoService;

    @Before
    public void setup() {
        setupServiceLocator();
        organizationBoService = mock(OrganizationBoService.class);
        service = new OrganizationServiceImpl(organizationBoService);


    }


    @Test
    public void testCreate() throws EntityValidationException {

        Organization organization = getBasicOrganization();

        Id createdId = service.create(organization);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }



    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException, JMSException {

        Organization organization = getBasicOrganization();

        II id = new II();
        id.setRoot(IdConverter.ORG_ROOT);
        id.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        id.setExtension("123");

        organization.setIdentifier(id);

        when(organizationBoService.create(any(gov.nih.nci.po.data.bo.Organization.class)))
                .thenThrow(new IllegalArgumentException());

        service.create(organization);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException, JMSException {
        //Should not be able to create it with a null name
        Organization organization = getBasicOrganization();

        when(organizationBoService.create(any(gov.nih.nci.po.data.bo.Organization.class)))
                .thenThrow(new EntityValidationException(Collections.EMPTY_MAP));

        service.create(organization);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        Organization organization = getBasicOrganization();
        organization.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));


        List<Organization> results = service.query(organization, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {



        Organization organization = new Organization();
        organization.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));


        when(organizationBoService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<List<gov.nih.nci.po.data.bo.Organization>>() {
                            @Override
                            public List<gov.nih.nci.po.data.bo.Organization>  answer(InvocationOnMock invocation) throws Throwable {
                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];
                                Validate.isTrue(pageSortParams.getIndex() == 0);
                                Validate.isTrue(pageSortParams.getPageSize() == Integer.MAX_VALUE);

                                List<gov.nih.nci.po.data.bo.Organization> results = new ArrayList<gov.nih.nci.po.data.bo.Organization>();

                                for (int i = 0; i < 5; i++) {
                                    results.add(new gov.nih.nci.po.data.bo.Organization());
                                }

                                return results;
                            }
                        }
                );

        List<Organization> results = service.query(organization, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(organization, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {

       when(organizationBoService.search(any(SearchCriteria.class), any(PageSortParams.class)))
                .thenAnswer(new Answer<List< gov.nih.nci.po.data.bo.Organization>>() {
                    @Override
                    public List<gov.nih.nci.po.data.bo.Organization> answer(InvocationOnMock invocation) throws Throwable {
                        List< gov.nih.nci.po.data.bo.Organization> results = new ArrayList<gov.nih.nci.po.data.bo.Organization>();

                        for (int i = 0; i<= service.getMaxHitsPerRequest(); i++) {
                            results.add(new gov.nih.nci.po.data.bo.Organization());
                        }

                        return results;
                    }
                });


        Organization organization = new Organization();
        organization.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        service.query(organization, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        final List<gov.nih.nci.po.data.bo.Organization> entities = new ArrayList<gov.nih.nci.po.data.bo.Organization>();
        for (int i = 0; i < 5; i++) {
            entities.add(new gov.nih.nci.po.data.bo.Organization());
        }

        when(organizationBoService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<List<gov.nih.nci.po.data.bo.Organization>>() {
                            @Override
                            public List<gov.nih.nci.po.data.bo.Organization> answer(InvocationOnMock invocation) throws Throwable {

                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];


                                int startIndex = pageSortParams.getIndex();
                                int endIndex = Math.min(entities.size(), startIndex + pageSortParams.getPageSize());


                                return entities.subList(startIndex, endIndex);
                            }
                        }
                );

        Organization organization = new Organization();
        organization.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        List<Organization> results = service.query(organization, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(organization, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(organization, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        Organization organization = new Organization();
        organization.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        service.query(organization, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException, JMSException {
        //stage an instance
        gov.nih.nci.po.data.bo.Organization instance = ModelUtils.getBasicOrganization();
        instance.setId(1L);

        //update it
        OrganizationDTO dto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(instance);
        Organization xml = OrganizationTransformer.INSTANCE.toXml(dto);

        //change the address
        Address address = ModelUtils.getBasicAddress();
        address.setStreetAddressLine("12345 " + RandomStringUtils.randomAlphabetic(5) + " Street NE");

        Ad newAddress = AddressConverter.SimpleConverter.convertToAd(address);
        xml.setPostalAddress(ADTransformer.INSTANCE.toXml(newAddress));

        when(organizationBoService.getById(1L)).thenReturn(instance);

        service.update(xml);

        verify(organizationBoService).curate(any(gov.nih.nci.po.data.bo.Organization.class));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatusToInvalidValue() throws DtoTransformException, EntityValidationException {
        //stage an instance
        gov.nih.nci.po.data.bo.Organization instance = ModelUtils.getBasicOrganization();

        //update it
        OrganizationDTO dto = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(instance);
        Organization xml = OrganizationTransformer.INSTANCE.toXml(dto);

        //change the status
        CD newStatusCode = new CD();
        newStatusCode.setCode(EntityStatus.ACTIVE.toString());
        xml.setStatusCode(newStatusCode);
        service.update(xml);
    }


    @Test
    public void testUpdateStatus() throws EntityValidationException, JMSException {
        //stage an instance
        gov.nih.nci.po.data.bo.Organization instance = ModelUtils.getBasicOrganization();
        instance.setId(1L);

        Ii ii = new IdConverter.OrgIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        when(organizationBoService.getById(1L)).thenReturn(instance);

        service.updateStatus(instanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));

        verify(organizationBoService).curate(any(gov.nih.nci.po.data.bo.Organization.class));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.OrgIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatusWithNullId() throws EntityValidationException {
        Ii ii = new IdConverter.OrgIdConverter().convertToIi(null);

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }

    @Test
    public void testValidateWithNoErrors() {
        Organization organization = getBasicOrganization();
        StringMap errors = service.validate(organization);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        Organization organization = getBasicOrganization();
        organization.setName(null);

        when(organizationBoService.validate(any(gov.nih.nci.po.data.bo.Organization.class)))
                .thenAnswer(
                        new Answer<Map<String, String[]>>() {
                            @Override
                            public Map<String, String[]> answer(InvocationOnMock invocation) throws Throwable {
                                Map<String, String[]> result = new HashMap<String, String[]>();

                                result.put("foo", new String[]{"bar"});

                                return result;
                            }
                        }
                );

        StringMap errors = service.validate(organization);
        assertEquals("Expected errors were not detected.", 1, errors.getEntry().size());
    }


    @Test
    public void testGetById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(1L);

        Ii ii = new IdConverter().convertToIi(organization.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        when(organizationBoService.getById(1L))
                .thenReturn(organization);

        Organization hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedEntityException.class)
    public void testGetNullifiedById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();
        organization.setId(1L);
        organization.setStatusCode(EntityStatus.NULLIFIED);

        when(organizationBoService.getById(1L))
                .thenReturn(organization);

        Ii ii = new IdConverter.OrgIdConverter().convertToIi(1L);
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedEntityException {
        Ii ii = new IdConverter.OrgIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        Organization hit = service.getById(instanceId);
        assertNull(hit);
    }

    public Organization getBasicOrganization() {
        gov.nih.nci.po.data.bo.Organization organization = ModelUtils.getBasicOrganization();

        OrganizationDTO d = (OrganizationDTO) PoXsnapshotHelper.createSnapshot(organization);

        try {
            return OrganizationTransformer.INSTANCE.toXml(d);
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }

    }
}
