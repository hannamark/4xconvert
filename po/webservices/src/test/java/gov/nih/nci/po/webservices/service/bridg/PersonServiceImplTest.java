package gov.nih.nci.po.webservices.service.bridg;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.extensions.Cd;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.convert.bridg.PersonTransformer;
import gov.nih.nci.po.webservices.service.AbstractEndpointTest;
import gov.nih.nci.po.webservices.service.bo.PersonBoService;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.math.RandomUtils;
import org.iso._21090.CD;
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
public class PersonServiceImplTest extends AbstractEndpointTest {

    PersonServiceImpl service;
    PersonBoService personBoService;

    @Before
    public void setup() throws CSException {
        setupServiceLocator();
        personBoService = mock(PersonBoService.class);
        service = new PersonServiceImpl(personBoService);
    }


    @Test
    public void testCreate() throws EntityValidationException, JMSException {

        Person person = getBasicPerson();

        Id createdId = service.create(person);

        assertNotNull(createdId);
        assertNotNull(Long.parseLong(createdId.getExtension()));

        verify(personBoService).create(any(gov.nih.nci.po.data.bo.Person.class));
    }


    @Test(expected = EntityValidationException.class)
    public void testCreateInvalidEntity() throws EntityValidationException, JMSException {
        Person person = getBasicPerson();

        when(personBoService.create(isA(gov.nih.nci.po.data.bo.Person.class)))
                .thenThrow(new EntityValidationException(Collections.EMPTY_MAP));

        service.create(person);
    }



    @Test
    public void testQueryWithNoHits() throws TooManyResultsException {

        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));


        List<Person> results = service.query(person, new LimitOffset().withLimit(100).withOffset(10));
        assertTrue(results.isEmpty());

        verify(personBoService).search(any(SearchCriteria.class), any(PageSortParams.class));

    }

    @Test
    public void testQueryWithNoPagination() throws TooManyResultsException {

        when(personBoService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<Object>() {
                            @Override
                            public List<gov.nih.nci.po.data.bo.Person> answer(InvocationOnMock invocation) throws Throwable {
                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];
                                Validate.isTrue(pageSortParams.getIndex() == 0);
                                Validate.isTrue(pageSortParams.getPageSize() == Integer.MAX_VALUE);

                                List<gov.nih.nci.po.data.bo.Person> results = new ArrayList<gov.nih.nci.po.data.bo.Person>();

                                for (int i = 0; i < 5; i++) {
                                    results.add(new gov.nih.nci.po.data.bo.Person());
                                }

                                return results;
                            }
                        }
                );

        Person person = new Person();

        List<Person> results = service.query(person, new LimitOffset().withLimit(0).withOffset(0));
        assertEquals(5, results.size());

        results = service.query(person, new LimitOffset().withLimit(-1).withOffset(0));
        assertEquals(5, results.size());
    }

    @Test(expected = TooManyResultsException.class)
    public void testQueryWithTooManyHits() throws TooManyResultsException {

        Person person = new Person();

        when(personBoService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
            .thenAnswer(
                    new Answer<Object>() {
                        @Override
                        public List<gov.nih.nci.po.data.bo.Person> answer(InvocationOnMock invocation) throws Throwable {
                            List<gov.nih.nci.po.data.bo.Person> results = new ArrayList<gov.nih.nci.po.data.bo.Person>();

                            for (int i=0; i<=service.getMaxHitsPerRequest(); i++) {
                                results.add(new gov.nih.nci.po.data.bo.Person());
                            }

                            return results;
                        }
                    }
            );

        service.query(person, new LimitOffset().withLimit(0).withOffset(0));

    }

    @Test
    public void testQueryWithPagination() throws TooManyResultsException {
        final List<gov.nih.nci.po.data.bo.Person> entities = new ArrayList<gov.nih.nci.po.data.bo.Person>();
        for (int i = 0; i < 5; i++) {
            entities.add(new gov.nih.nci.po.data.bo.Person());
        }

        when(personBoService.search(isA(SearchCriteria.class), isA(PageSortParams.class)))
                .thenAnswer(
                        new Answer<Object>() {
                            @Override
                            public List<gov.nih.nci.po.data.bo.Person> answer(InvocationOnMock invocation) throws Throwable {
                                PageSortParams pageSortParams = (PageSortParams) invocation.getArguments()[1];


                                int startIndex = pageSortParams.getIndex();
                                int endIndex = Math.min(entities.size(), startIndex + pageSortParams.getPageSize());


                                return entities.subList(startIndex,endIndex);
                            }
                        }
                );

        Person person = new Person();

        List<Person> results = service.query(person, new LimitOffset().withLimit(2).withOffset(0));
        assertEquals(2, results.size());

        results = service.query(person, new LimitOffset().withLimit(2).withOffset(2));
        assertEquals(2, results.size());

        results = service.query(person, new LimitOffset().withLimit(2).withOffset(4));
        assertEquals(1, results.size());
    }

    @Test(expected = ServiceException.class)
    public void testQueryWithNegativeOffset() throws TooManyResultsException {
        Person person = new Person();
        person.setStatusCode(new CD().withCode(EntityStatus.ACTIVE.toString()));

        service.query(person, new LimitOffset().withLimit(1000).withOffset(-1));
    }



    @Test
    public void testUpdate() throws DtoTransformException, EntityValidationException, JMSException {
        //stage an instance
        Person xml = new Person();

        service.update(xml);

        verify(personBoService).curate(any(gov.nih.nci.po.data.bo.Person.class));


    }


    @Test
    public void testUpdateStatus() throws EntityValidationException, JMSException {
        //stage an instance
        gov.nih.nci.po.data.bo.Person instance = ModelUtils.getBasicPerson();
        instance.setId(1L);

        Ii ii = new IdConverter.PersonIdConverter().convertToIi(instance.getId());

        Id instanceId = IdTransformer.INSTANCE.toXml(ii);

        //update it
        when(personBoService.getById(1L)).thenReturn(instance);

        service.updateStatus(instanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
        verify(personBoService).curate(any(gov.nih.nci.po.data.bo.Person.class));
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateStatusOfNonExistingInstance() throws EntityValidationException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(RandomUtils.nextLong());

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateStatusWithNullId() throws EntityValidationException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(null);

        Id nonExistantInstanceId = IdTransformer.INSTANCE.toXml(ii);
        service.updateStatus(nonExistantInstanceId, new Cd().withCode(EntityStatus.ACTIVE.toString()));
    }


    @Test
    public void testValidateWithNoErrors() {
        Person person = getBasicPerson();
        StringMap errors = service.validate(person);
        assertTrue("Unexpected errors were detected.", errors.getEntry().isEmpty());
    }

    @Test
    public void testValidateWithErrors() {
        Person person = getBasicPerson();

        when(personBoService.validate(any(gov.nih.nci.po.data.bo.Person.class)))
                .thenAnswer( new Answer<Map<String, String[]>>() {
                    @Override
                    public Map<String, String[]> answer(InvocationOnMock invocation) throws Throwable {
                        Map<String, String[]> result = new HashMap<String, String[]>();
                        result.put("foo", new String[]{"bar"});
                        return result;
                    }
                });

        StringMap errors = service.validate(person);
        assertEquals("Expected errors were not detected.", 1, errors.getEntry().size());
    }


    @Test
    public void testGetById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Person person = ModelUtils.getBasicPerson();
        person.setId(1L);

        when(personBoService.getById(1L)).thenReturn(person);

        Ii ii = new IdConverter.PersonIdConverter().convertToIi(person.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        Person hit = service.getById(instanceId);
        assertNotNull(hit);
    }

    @Test (expected = NullifiedEntityException.class)
    public void testGetNullifiedById() throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Person nullifiedPerson = ModelUtils.getBasicPerson();
        nullifiedPerson.setId(1L);
        nullifiedPerson.setStatusCode(EntityStatus.NULLIFIED);

        when(personBoService.getById(1L)).thenReturn(nullifiedPerson);


        Ii ii = new IdConverter.PersonIdConverter().convertToIi(nullifiedPerson.getId());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        service.getById(instanceId);
    }

    @Test
    public void testGetNonExistingInstanceById() throws NullifiedEntityException {
        Ii ii = new IdConverter.PersonIdConverter().convertToIi(RandomUtils.nextLong());
        Id instanceId = IdTransformer.INSTANCE.toXml(ii);
        Person hit = service.getById(instanceId);
        assertNull(hit);
    }

    public Person getBasicPerson() {

        gov.nih.nci.po.data.bo.Person person = ModelUtils.getBasicPerson();

        PersonDTO d = (PersonDTO) PoXsnapshotHelper.createSnapshot(person);

        try {
            return PersonTransformer.INSTANCE.toXml(d);
        } catch (DtoTransformException e) {
            throw new RuntimeException(e);
        }

    }


}
