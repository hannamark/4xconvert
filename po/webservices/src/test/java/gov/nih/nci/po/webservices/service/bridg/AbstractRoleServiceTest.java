package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bo.AbstractRoleBoService;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractRoleServiceTest<
        XML_TYPE extends gov.nih.nci.coppa.po.Correlation,
        DTO_TYPE extends CorrelationDto,
        BO_TYPE extends Correlation
        >  extends AbstractEndpointTest{

    protected AbstractRoleService<XML_TYPE, DTO_TYPE, BO_TYPE> service;
    protected GenericStructrualRoleServiceLocal<BO_TYPE> boService;

    @Before
    public void setUp() throws Exception {
        boService = mock(getBoServiceClass());
        initService();
        setupServiceLocator();
    }

    protected abstract void initService();
    protected abstract XML_TYPE getBasicModel();
    protected abstract Class<? extends GenericStructrualRoleServiceLocal<BO_TYPE>> getBoServiceClass();
    protected abstract void setId(BO_TYPE instance, long id);

    @Test
    public void testCreate() throws EntityValidationException {
        XML_TYPE clinicalResearchStaff = getBasicModel();

        Id createdId = service.create(clinicalResearchStaff);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithId() throws EntityValidationException, JMSException {

        XML_TYPE clinicalResearchStaff = getBasicModel();

        when(this.boService.create((BO_TYPE)any())).thenThrow(new IllegalArgumentException());

        service.create(clinicalResearchStaff);

    }

    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException, JMSException {
        when(this.boService.create((BO_TYPE) any())).thenThrow(new EntityValidationException(new HashMap<String, String[]>()));

        XML_TYPE instance = getBasicModel();
        service.create(instance);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        XML_TYPE query = getBasicModel();

        when(this.boService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenReturn(new ArrayList<BO_TYPE>());

        List<XML_TYPE> results = service.query(query, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        final List<BO_TYPE> entities = new ArrayList<BO_TYPE>();
        for (int i = 0; i < 5; i++) {
            entities.add(getBoInstance());
        }

        when(this.boService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<List<BO_TYPE>>() {
                            @Override
                            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];


                                int startIndex = pageSortParams.getIndex();
                                int endIndex = Math.min(entities.size(), startIndex + pageSortParams.getPageSize());


                                return entities.subList(startIndex,endIndex);
                            }
                        }
                );

        XML_TYPE clinicalResearchStaff = getBasicModel();

        List<XML_TYPE> results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    protected abstract BO_TYPE getBoInstance();


    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {
       when(this.boService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<List<BO_TYPE>>() {
                            @Override
                            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                                List<BO_TYPE> results = new ArrayList<BO_TYPE>();

                                for (int i=0; i<=service.getMaxHitsPerRequest(); i++) {
                                    results.add(getBoInstance());
                                }

                                return results;
                            }
                        }
                );

        XML_TYPE clinicalResearchStaff = getBasicModel();

        service.query(clinicalResearchStaff, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {

        final List<BO_TYPE> entities = new ArrayList<BO_TYPE>();
        for (int i = 0; i < 5; i++) {
            entities.add(getBoInstance());
        }

        when(this.boService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<List<BO_TYPE>>() {
                            @Override
                            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];


                                int startIndex = pageSortParams.getIndex();
                                int endIndex = Math.min(entities.size(), startIndex + pageSortParams.getPageSize());


                                return entities.subList(startIndex,endIndex);
                            }
                        }
                );

        XML_TYPE clinicalResearchStaff = getBasicModel();

        List<XML_TYPE> results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(results.size(), 2);

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(results.size(), 2);

        results = service.query(clinicalResearchStaff, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(results.size(), 1);
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        XML_TYPE clinicalResearchStaff = getBasicModel();

        service.query(clinicalResearchStaff, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException, JMSException {
        //stage an instance
        XML_TYPE xml = getBasicModel();

        when(serviceLocator.getPersonCRService().create(isA(PersonCR.class))).thenReturn(5L);

        service.update(xml);

        verify(this.boService).curate((BO_TYPE)any());
    }



    @Test
    public void testUpdateStatus() throws EntityValidationException, JMSException {
        //stage an instance
        BO_TYPE instance = getBoInstance();
        setId(instance, 1L);

        Ii ii = new Ii();
        ii.setExtension("1");

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        when(this.boService.getById(1L)).thenReturn(instance);

        service.updateStatus(instanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
        verify(this.boService).curate((BO_TYPE)any());
    }




    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Id nonExistantInstanceId = new Id();
        nonExistantInstanceId.setExtension("1");

        when(this.boService.getById(1L)).thenReturn(null);

        service.updateStatus(nonExistantInstanceId, new Cd().withCode(RoleStatus.ACTIVE.toString()));
    }



    @Test
    public void testValidateWithNoErrors() {
        XML_TYPE instance = getBasicModel();

        when(this.boService.validate((BO_TYPE)any()))
                .thenAnswer( new Answer<Map<String, String[]>>() {
                    @Override
                    public Map<String, String[]> answer(InvocationOnMock invocation) throws Throwable {
                        return Collections.EMPTY_MAP;
                    }
                });

        StringMap errors = service.validate(instance);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        XML_TYPE instance = getBasicModel();

        when(this.boService.validate((BO_TYPE)any()))
                .thenAnswer( new Answer<Map<String, String[]>>() {
                    @Override
                    public Map<String, String[]> answer(InvocationOnMock invocation) throws Throwable {
                        Map<String, String[]> result = new HashMap<String, String[]>();
                        result.put("foo", new String[]{"bar"});
                        return result;
                    }
                });

        StringMap errors = service.validate(instance);
        assertEquals("Expected errors were not detected.", errors.getEntry().size(), 1);
    }


    @Test
    public void testGetByPlayerIds() throws NullifiedRoleException {
        Long[] playerIdsAsLong = new Long[]{1L, 2L, 3L};
        List<Id> playerIds = new ArrayList<Id>();

        final Map<Long, List<BO_TYPE>> relationships = new HashMap<Long, List<BO_TYPE>>();
        List<BO_TYPE> instances = new ArrayList<BO_TYPE>();
        for (int i=0; i<5; i++) {
            instances.add(getBoInstance());
        }

        for (Long playerIdAsLong : playerIdsAsLong) {
            Id id = new Id();
            id.setExtension(playerIdAsLong.toString());
            playerIds.add(id);
            relationships.put(playerIdAsLong, instances);
        }



        when(this.boService.getByPlayerIds((Long[])any())).thenAnswer( new Answer<List<BO_TYPE>>() {
            @Override
            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                List<BO_TYPE> results = new ArrayList<BO_TYPE>();
                Long[] playerIds = (Long[]) invocation.getArguments()[0];

                for (Long playerId : playerIds) {
                    results.addAll(relationships.get(playerId));
                }

                return results;
            }
        });

        //check []
        List<XML_TYPE> hits = service.getByPlayerIds(Collections.EMPTY_LIST);

        //expect 0
        assertEquals(0, hits.size());

        //check [1]

        hits = service.getByPlayerIds(playerIds.subList(0,1));
        //expect 5
        assertEquals(5, hits.size());



        //check [1,2]
        hits = service.getByPlayerIds(playerIds.subList(0,2));
        //expect 10
        assertEquals(10, hits.size());

        //check [1,2,3]
        hits = service.getByPlayerIds(playerIds.subList(0,3));
        //expect 15
        assertEquals(15, hits.size());

        //check [2,3]
        hits = service.getByPlayerIds(playerIds.subList(1,3));
        //expect 10
        assertEquals(10, hits.size());

        //check [3]
        hits = service.getByPlayerIds(playerIds.subList(2,3));
        //expect 5
        assertEquals(5, hits.size());

        //check [1,1]
        hits = service.getByPlayerIds(Arrays.asList(new Id[]{playerIds.get(0), playerIds.get(0)}));
        //expect 5
        assertEquals(5, hits.size());
    }




    @Test
    public void testGetById() throws NullifiedRoleException {

        Id instanceId = new Id();
        instanceId.setExtension("1");

        when(this.boService.getById(1L)).thenReturn(getBoInstance());

        XML_TYPE hit = service.getById(instanceId);

        assertNotNull(hit);
    }

    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedById() throws NullifiedRoleException {

        Id instanceId = new Id();
        instanceId.setExtension("1");

        BO_TYPE nullifiedInstance = getBoInstance();
        nullifiedInstance.setStatus(RoleStatus.NULLIFIED);

        when(this.boService.getById(1L)).thenReturn(nullifiedInstance);

        XML_TYPE hit = service.getById(instanceId);

    }


    @Test
    public void testGetNonExistingInstanceById() throws NullifiedRoleException {
        Id instanceId = new Id();
        instanceId.setExtension("1");

        when(this.boService.getById(1L)).thenReturn(null);

        XML_TYPE hit = service.getById(instanceId);
        assertNull(hit);
    }

    @Test
    public void testGetByIds() throws NullifiedRoleException {
        final List<Long> instanceIds = Arrays.asList(new Long[]{1L, 2L, 3L});

        List<Id> ids = new ArrayList<Id>();
        for (Long instanceId : instanceIds) {
            Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(instanceId);
            Id id = IdTransformer.INSTANCE.toXml(ii);
            ids.add(id);
        }

        when(this.boService.getByIds((Long[]) any())).thenAnswer(new Answer<List<BO_TYPE>>() {
            @Override
            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                List<BO_TYPE> results = new ArrayList<BO_TYPE>();
                Long[] ids = (Long[]) invocation.getArguments()[0];

                for (Long id : ids) {
                    if (instanceIds.contains(id)) {
                        results.add(getBoInstance());
                    }
                }

                return results;
            }
        });

        List<XML_TYPE> hits = service.getByIds(ids);
        assertEquals(3, hits.size());
    }


    @Test (expected = NullifiedRoleException.class)
    public void testGetNullifiedByIds() throws NullifiedRoleException {
        List<Id> ids = new ArrayList<Id>();


        when(this.boService.getByIds((Long[]) any())).thenAnswer(new Answer<List<BO_TYPE>>() {
            @Override
            public List<BO_TYPE> answer(InvocationOnMock invocation) throws Throwable {
                List<BO_TYPE> results = new ArrayList<BO_TYPE>();

                for (int i=0; i < 5; i++) {
                    results.add(getBoInstance());
                }

                results.get(4).setStatus(RoleStatus.NULLIFIED);
                return results;
            }
        });

        service.getByIds(ids);
    }

    @Test
    public void testGetByIdsWithEmptyInput() throws NullifiedRoleException {
        when(this.boService.getByIds((Long[])any())).thenReturn(Collections.EMPTY_LIST);

        List<XML_TYPE> hits = service.getByIds(Collections.EMPTY_LIST);
        assertEquals(0, hits.size());
    }

}
