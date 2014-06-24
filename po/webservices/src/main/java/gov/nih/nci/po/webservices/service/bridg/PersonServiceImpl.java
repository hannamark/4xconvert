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
import gov.nih.nci.po.data.convert.IdConverterRegistry;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonSortCriterion;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.po.webservices.convert.bridg.PersonTransformer;
import gov.nih.nci.po.webservices.service.bo.PersonBoService;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Service("personBridgService")
public class PersonServiceImpl implements EntityService<Person> {

    private static final int DEFAULT_MAX_HITS = 500;

    private final PersonBoService personBoService;

    private int maxHitsPerRequest = DEFAULT_MAX_HITS;


    /**
     * Constructor.
     * @param boService The BO service to delegate to.
     */
    @Autowired
    public PersonServiceImpl(PersonBoService boService) {
        this.personBoService = boService;
    }

    @Override
    public Id create(Person definition) throws EntityValidationException {
        Id result = null;

        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Person bo = (gov.nih.nci.po.data.bo.Person) PoXsnapshotHelper.createModel(dto);

            long id = personBoService.create(bo);

            Ii ii = new IdConverter.ClinicalResearchStaffIdConverter().convertToIi(id);

            result = IdTransformer.INSTANCE.toXml(ii);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }


        return result;
    }

    @Override
    public List<Person> query(Person spec, LimitOffset limitOffset) throws TooManyResultsException {
        int limit = limitOffset.getLimit();
        int offset = limitOffset.getOffset();

        if (offset < 0) {
            throw new ServiceException("Offset can not be negative");
        }

        List<Person> results = new ArrayList<Person>();


        try {

            PersonDTO dto = PersonTransformer.INSTANCE.toDto(spec);

            gov.nih.nci.po.data.bo.Person bo = (gov.nih.nci.po.data.bo.Person) PoXsnapshotHelper.createModel(dto);

            SearchCriteria<gov.nih.nci.po.data.bo.Person> criteria
                    = new AnnotatedBeanSearchCriteria<gov.nih.nci.po.data.bo.Person>(bo);

            PageSortParams<gov.nih.nci.po.data.bo.Person> pageSortParams
                    = new PageSortParams<gov.nih.nci.po.data.bo.Person>(
                    limit <= 0 ? Integer.MAX_VALUE : limit,
                    offset,
                    PersonSortCriterion.PERSON_LASTNAME,
                    false);

            List<gov.nih.nci.po.data.bo.Person> boHits = personBoService.search(criteria, pageSortParams);

            //convert to bridge service model
            results.addAll(generateList(boHits));

            if (results.size() > this.maxHitsPerRequest) {
                throw new TooManyResultsException(this.maxHitsPerRequest);
            }

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return results;
    }

    @Override
    public void update(Person definition) throws EntityValidationException {
        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Person instance = (gov.nih.nci.po.data.bo.Person) PoXsnapshotHelper.createModel(dto);

            personBoService.curate(instance);
        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatus(Id targetId, Cd statusCode) throws EntityValidationException {

        Long id = Long.parseLong(targetId.getExtension());

        Validate.notNull(id, "Can not update entity with null id.");

        gov.nih.nci.po.data.bo.Person instance
                = personBoService.getById(id);

        EntityStatus newStatus = EntityStatus.valueOf(statusCode.getCode());

        instance.setStatusCode(newStatus);

        try {
            personBoService.curate(instance);
        } catch (JMSException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    @SuppressWarnings("CPD-START")
    public StringMap validate(Person definition) {
        StringMap result = null;

        try {
            PersonDTO dto = PersonTransformer.INSTANCE.toDto(definition);

            gov.nih.nci.po.data.bo.Person instance = (gov.nih.nci.po.data.bo.Person) PoXsnapshotHelper.createModel(dto);

            Map<String, String[]> errors = personBoService.validate(instance);

            result = StringMapHelper.toStringMap(errors);

        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }


        return result;
    }


    @Override
    public Person getById(Id id) throws NullifiedEntityException {
        gov.nih.nci.po.data.bo.Person hit = personBoService.getById(Long.parseLong(id.getExtension()));

        if (hit != null) {
            validateNotNullified(hit);
        }

        Person result = null;
        try {
            result = PersonTransformer.INSTANCE.toXml(
                    (PersonDTO) PoXsnapshotHelper.createSnapshot(hit)
            );


        } catch (DtoTransformException e) {
            throw new ServiceException(e);
        }

        return result;
    }



    private void validateNotNullified(gov.nih.nci.po.data.bo.Person item)
            throws NullifiedEntityException {
        List<gov.nih.nci.po.data.bo.Person> list = new ArrayList<gov.nih.nci.po.data.bo.Person>();
        list.add(item);
        validateNotNullified(list);
    }

    private void validateNotNullified(Collection<gov.nih.nci.po.data.bo.Person> items)
            throws NullifiedEntityException {
        Map<Ii, Ii> nullifiedEntities = new HashMap<Ii, Ii>();

        IdConverter idConverter = IdConverterRegistry.find(gov.nih.nci.po.data.bo.Person.class);

        for (gov.nih.nci.po.data.bo.Person item : items) {

            if (item.getStatusCode() == EntityStatus.NULLIFIED) {
                PersonDTO dto = (PersonDTO) PoXsnapshotHelper.createSnapshot(item);

                Long duplicatedEntityId = null;

                if (item.getDuplicateOf() != null) {
                    duplicatedEntityId = item.getDuplicateOf().getId();
                }

                nullifiedEntities.put(dto.getIdentifier(), idConverter.convertToIi(duplicatedEntityId));
            }

        }

        if (!nullifiedEntities.isEmpty()) {
            throw new NullifiedEntityException(nullifiedEntities);
        }
    }

    private List<Person> generateList(
            Collection<gov.nih.nci.po.data.bo.Person> boInstances
    ) {
        List<Person> results = new ArrayList<Person>();

        for (gov.nih.nci.po.data.bo.Person hit : boInstances) {
            try {
                results.add(
                        PersonTransformer.INSTANCE.toXml(
                                (PersonDTO) PoXsnapshotHelper.createSnapshot(hit)
                        )
                );
            } catch (DtoTransformException e) {
                throw new ServiceException(e);
            }
        }

        return results;
    }

    /**
     * @return The maximum number of hits that can be returned by a query.
     */
    @SuppressWarnings("CPD-END")
    public int getMaxHitsPerRequest() {
        return maxHitsPerRequest;
    }

    /**
     * @param maxHitsPerRequest The maximum number of hits that can be returned by a query.
     */
    public void setMaxHitsPerRequest(int maxHitsPerRequest) {
        this.maxHitsPerRequest = maxHitsPerRequest;
    }
}
