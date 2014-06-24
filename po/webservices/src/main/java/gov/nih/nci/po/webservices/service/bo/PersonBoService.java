package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.AbstractIdentifiedPerson;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.IdentifiedPersonCR;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.person.PersonDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import java.util.List;
import java.util.Map;

/**
 * Wrapper around EJB service to implement business logic
 * for web services without breaking legacy code contracts.
 *
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
            user =  SecurityServiceProvider.getUserProvisioningManager("po")
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
            user =  SecurityServiceProvider.getUserProvisioningManager("po")
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
     *
     * {@inheritDoc}
     */
    @Override
    public void curate(Person curatedPerson) throws JMSException {

        Person current = PoRegistry.getPersonService().getById(curatedPerson.getId());

        if (current != null) {
            curatedPerson.setCreatedBy(current.getCreatedBy());
        }

        if (current == null || isCreatedByMe(current)) {
            PoRegistry.getPersonService().curate(curatedPerson);
        } else {
            //someone else made it, so create a CR
            try {
                createPersonCR(curatedPerson);
            } catch (EntityValidationException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void curate(Person curatedPerson, String ctepId) throws EntityValidationException, JMSException {

        Person current = PoRegistry.getPersonService().getById(curatedPerson.getId());

        if (current != null) {
            curatedPerson.setCreatedBy(current.getCreatedBy());
        }

        if (current == null || isCreatedByMe(current)) {
            PoRegistry.getPersonService().curate(curatedPerson, ctepId);
        } else {
            //someone else made the person, so create a CR
            createPersonCR(curatedPerson);
            createIdentifiedPersonCR(curatedPerson, ctepId);
        }

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

    private void createPersonCR(Person curatedPerson) throws EntityValidationException {
        //create a cr for the Person
        Person target = PoRegistry.getPersonService().getById(curatedPerson.getId());

        PersonCR personCR = new PersonCR(target);

        PersonDTO curatedPersonDto = (PersonDTO) PoXsnapshotHelper.createSnapshot(curatedPerson);
        curatedPersonDto.setIdentifier(null);
        PoXsnapshotHelper.copyIntoAbstractModel(curatedPersonDto, personCR, AbstractPerson.class);
        personCR.setId(null);

        PoRegistry.getInstance().getServiceLocator().getPersonCRService().create(personCR);

    }

    /**
     * Logic largely copied from PersonServiceBean
     * @param curatedPerson
     * @param ctepId
     */
    private void createIdentifiedPersonCR(Person curatedPerson, String ctepId) throws EntityValidationException {
        //if there's no ID given, just return
        if (StringUtils.isBlank(ctepId)) {
            return;
        }

        Organization ctep = PoServiceUtil.getCtepOrganization();

        IdentifiedPerson identifiedPerson = getNewIdentifiedPersonObject(curatedPerson, ctep);

        SearchCriteria<IdentifiedPerson> searchCriteria = new AnnotatedBeanSearchCriteria<IdentifiedPerson>(
                identifiedPerson);

        identifiedPerson.getAssignedIdentifier().setExtension(ctepId);

        // search for existing CtepId Record
        List<IdentifiedPerson> identifiedPeople
                = PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonService().search(searchCriteria);

        // if existing CtepId record found, then update it
        IdentifiedPerson target = null;

        if (!identifiedPeople.isEmpty()) {
            target = identifiedPeople.get(0);
            identifiedPerson.setId(target.getId());
        }


        IdentifiedPersonCR identifiedPersonCR = new IdentifiedPersonCR(target);

        IdentifiedPersonDTO proposedState = (IdentifiedPersonDTO) PoXsnapshotHelper.createSnapshot(target);
        proposedState.getAssignedId().setExtension(ctepId);

        proposedState.setIdentifier(null);

        PoXsnapshotHelper.copyIntoAbstractModel(proposedState, identifiedPersonCR, AbstractIdentifiedPerson.class);

        identifiedPersonCR.setId(null);

        PoRegistry.getInstance().getServiceLocator().getIdentifiedPersonCRService().create(identifiedPersonCR);
    }


    /**
     * Copied from PersonServiceBean...
     * @param personBo
     * @param organizationBo
     * @return
     */
    private IdentifiedPerson getNewIdentifiedPersonObject(
                                                          gov.nih.nci.po.data.bo.Person personBo,
                                                          gov.nih.nci.po.data.bo.Organization organizationBo) {
        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);

        IdentifiedPerson idenPerson = new IdentifiedPerson();
        idenPerson.setAssignedIdentifier(assIden);
        idenPerson.setPlayer(personBo);
        idenPerson.setScoper(organizationBo);
        // set bi-directional association b/w person & idenPerson
        personBo.getIdentifiedPersons().add(idenPerson);

        return idenPerson;
    }


    private static boolean isCreatedByMe(Person person) {
        String createdBy = null;

        if (person.getCreatedBy() != null) {
            createdBy = person.getCreatedBy().getLoginName();
        }

        return StringUtils.equals(UsernameHolder.getUser(), createdBy);
    }
}
