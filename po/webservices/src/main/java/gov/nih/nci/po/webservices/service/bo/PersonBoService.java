package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

/**
 * Wrapper around EJB service to implement business logic
 * for web services without breaking legacy code contracts.
 * <p/>
 * TODO:  Configure transactions (use JTA manager)
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
@Transactional
@Service("personBoService")
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidThrowingRawExceptionTypes" })
public class PersonBoService implements PersonServiceLocal {

    @Override
    public long create(Person person) throws EntityValidationException, JMSException {
        //set createdby
        User user = null;

        try {
            user = SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        person.setCreatedBy(user);

        return PoRegistry.getPersonService().create(person);
    }

    @Override
    public long create(Person person, String ctepId) throws EntityValidationException, JMSException {
        User user = null;

        try {
            user = SecurityServiceProvider.getUserProvisioningManager("po")
                    .getUser(UsernameHolder.getUser());
        } catch (CSException e) {
            throw new RuntimeException(e);
        }

        person.setCreatedBy(user);

        return PoRegistry.getPersonService().create(person, ctepId);
    }

    @Override
    public Person getById(long id) {
        return PoRegistry.getPersonService().getById(id);
    }

    @Override
    public Map<String, String[]> validate(Person entity) {
        return PoRegistry.getPersonService().validate(entity);
    }

    /**
     * The given Person should either have an it, or be a previously created instance.
     * If the given Person has an ID, but no current instance is found...//TODO
     * <p/>
     * {@inheritDoc}
     */
    @Override
    public void curate(Person curatedPerson) throws JMSException {

        Person current = PoRegistry.getPersonService().getById(curatedPerson.getId());

        if (current != null) {
            curatedPerson.setCreatedBy(current.getCreatedBy());
        }

        PoRegistry.getPersonService().curate(curatedPerson);

    }

    @Override
    public void curate(Person curatedPerson, String ctepId) throws EntityValidationException, JMSException {

        Person current = PoRegistry.getPersonService().getById(curatedPerson.getId());

        if (current != null) {
            curatedPerson.setCreatedBy(current.getCreatedBy());
        }

        PoRegistry.getPersonService().curate(curatedPerson, ctepId);

    }


    @Override
    public List<PersonSearchDTO> search(PersonSearchCriteria criteria, PageSortParams<PersonSearchDTO> pageSortParams) {
        return PoRegistry.getPersonService().search(criteria, pageSortParams);
    }

    @Override
    public int count(PersonSearchCriteria criteria) {
        return PoRegistry.getPersonService().count(criteria);
    }

    @Override
    public List<PersonSearchDTO> getInboxPersons(PageSortParams<PersonSearchDTO> pageSortParams) {
        return PoRegistry.getPersonService().getInboxPersons(pageSortParams);
    }

    @Override
    public int countInboxPersons() {
        return PoRegistry.getPersonService().countInboxPersons();
    }

    @Override
    public List<Person> search(SearchCriteria<Person> criteria) {
        return PoRegistry.getPersonService().search(criteria);
    }

    @Override
    public List<Person> search(SearchCriteria<Person> criteria, PageSortParams<Person> pageSortParams) {
        return PoRegistry.getPersonService().search(criteria, pageSortParams);
    }

    @Override
    public int count(SearchCriteria<Person> criteria) {
        return PoRegistry.getPersonService().count(criteria);
    }


}
