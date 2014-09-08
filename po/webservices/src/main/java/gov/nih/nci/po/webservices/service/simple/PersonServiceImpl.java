package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.webservices.convert.simple.ClinicalResearchStaffConverter;
import gov.nih.nci.po.webservices.convert.simple.Converters;
import gov.nih.nci.po.webservices.convert.simple.HealthCareProviderConverter;
import gov.nih.nci.po.webservices.convert.simple.OrganizationalContactConverter;
import gov.nih.nci.po.webservices.convert.simple.PersonConverter;
import gov.nih.nci.po.webservices.convert.simple.PersonSearchConverter;
import gov.nih.nci.po.webservices.service.bo.ClinicalResearchStaffBoService;
import gov.nih.nci.po.webservices.service.bo.HealthCareProviderBoService;
import gov.nih.nci.po.webservices.service.bo.IdentifiedPersonBoService;
import gov.nih.nci.po.webservices.service.bo.OrganizationalContactBoService;
import gov.nih.nci.po.webservices.service.bo.PersonBoService;
import gov.nih.nci.po.webservices.service.exception.EntityNotFoundException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;
import gov.nih.nci.po.webservices.util.PoWSUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.search.OneCriterionRequiredException;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;

/**
 * This is the PersonService implementation class.
 * 
 * @author Rohit Gupta
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength",
        "PMD.CyclomaticComplexity" })
@Service("simplePersonService")
public class PersonServiceImpl implements PersonService {

    private static final Logger LOG = Logger.getLogger(PersonServiceImpl.class);
    private static final String CLASS_NULL_MSG = " as incoming Class is null.";
    private static final String NOT_FOUND_IN_DB_MSG = " as it is not found in the DB.";
    private static final String PASS_IN_REQ = " is passed in the request.";
    private static final String RAW_TYPES = "rawtypes";
    private static final String UNCHECKED = "unchecked";

    @Autowired
    private PersonBoService personBoService;

    @Autowired
    private IdentifiedPersonBoService identifiedPersonBoService;

    @Autowired
    private HealthCareProviderBoService healthCareProviderBoService;

    @Autowired
    private ClinicalResearchStaffBoService clinicalResearchStaffBoService;

    @Autowired
    private OrganizationalContactBoService organizationalContactBoService;

    @Override
    public Person createPerson(Person person) {
        // null check for Person ( to avoid NullPointer @ person.getCtepId())
        if (person == null) {
            LOG.error("The Person couldn't be created as person is null.");
            throw new ServiceException("The Person couldn't be created as person is null.");
        }

        gov.nih.nci.po.data.bo.Person personBo = null;
        long retPersonId = -1;
        String ctepId = person.getCtepId();
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error("Person couldn't be created as CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(
                    "Person couldn't be created as CTEP ID " + ctepId + PASS_IN_REQ);              
        }
        
        try {            
            // get the corresponding BO object
            PersonConverter pConverter = Converters.get(PersonConverter.class);
            personBo = pConverter.convertFromJaxbToBO(person);

            // call the BO service method to create the Person
            retPersonId = personBoService.create(personBo);
        } catch (EntityValidationException e) {
            LOG.error("Person couldn't be created as data is invalid.", e);
            throw new ServiceException("Person couldn't be created as data is invalid.", e);
        } catch (Exception e) {
            LOG.error("Exception occured while creating person " + getPersonName(person) + ".", e);
            throw new ServiceException("Exception occured while creating person " + getPersonName(person) + ".", e);
        }

        // get the Person from the database & return it
        return getPerson(retPersonId);
    }

    @Override
    public Person updatePerson(Person person) {
        // null check for Person or PersonId
        if ((person == null) || (person.getId() == null)) {
            LOG.error("The Person couldn't be updated as either person or personId is null.");
            throw new ServiceException("The Person couldn't be updated as either person or personId is null.");
        }
        
        String ctepId = person.getCtepId();
        if (StringUtils.isNotBlank(ctepId)) {
            // PO-7923 throw an exception if CTEP ID is passed.
            LOG.error("Person " + person.getId() + " couldn't be updated " + "as CTEP ID " + ctepId + PASS_IN_REQ);
            throw new ServiceException(
                    "Person " + person.getId() + " couldn't be updated " + "as CTEP ID " + ctepId + PASS_IN_REQ);
        }
        
        gov.nih.nci.po.data.bo.Person personBo = null;
        try {            
            // get the corresponding BO object
            PersonConverter pConverter = Converters.get(PersonConverter.class);
            personBo = pConverter.convertFromJaxbToBO(person);

            // call the BO service method to update the Person
            personBoService.curate(personBo);
        } catch (Exception e) {
            LOG.error("Exception occured while updating the person having Id " + person.getId() + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the person having Id " + person.getId() + ".", e);
        }

        // get the Person from the database & return it
        return getPerson(personBo.getId());
    }

    @Override
    public Person changePersonStatus(long personID, EntityStatus status) {

        // get the BO Person for given personID
        gov.nih.nci.po.data.bo.Person personBo = personBoService.getById(personID);
        if (personBo == null) {
            LOG.error("Couldn't update the Person Status for personID " + personID + NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(
                    "Couldn't update the Person Status for personID " + personID + NOT_FOUND_IN_DB_MSG);
        }

        try {
            // set the Status
            personBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus.valueOf(status.value()));

            // call the EJB service method to update Person status
            personBoService.curate(personBo);
        } catch (Exception e) {
            LOG.error("Exception occured while updating the Status for personID " + personID + ".", e);
            throw new ServiceException("Exception occured while updating the Status for personID " + personID + ".", e);
        }

        // get the Person from the database & return it
        return getPerson(personID);
    }

    @Override
    public Person getPerson(long personID) {
        // get the BO Person for given personID
        gov.nih.nci.po.data.bo.Person personBo = personBoService.getById(personID);

        // Convert to get the corresponding JaxB object & then return it
        PersonConverter pConverter = Converters.get(PersonConverter.class);
        return pConverter.convertFromBOToJaxB(personBo);
    }

    @Override
    public List<Person> getPersonsByCtepId(String ctepID) {

        gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
        assIden.setRoot(PoConstants.PERSON_CTEP_ID_ROOT);
        assIden.setIdentifierName(PoConstants.PERSON_CTEP_ID_IDENTIFIER_NAME);
        assIden.setExtension(ctepID);
        IdentifiedPerson idenPerson = new IdentifiedPerson();
        idenPerson.setAssignedIdentifier(assIden);

        // populate the SearchCriteria
        SearchCriteria<IdentifiedPerson> searchCriteria = new AnnotatedBeanSearchCriteria<IdentifiedPerson>(idenPerson);

        // get the IdentifiedPerson List matching the SearchCriteria
        List<IdentifiedPerson> identifiedPeople = identifiedPersonBoService.search(searchCriteria);

        if (identifiedPeople.isEmpty()) {
            return new ArrayList<Person>();
        } else {
            // Iterate through the list and add in the list to be returned
            List<Person> personList = new ArrayList<Person>();
            PersonConverter pConverter = Converters.get(PersonConverter.class);
            for (IdentifiedPerson idPer : identifiedPeople) {
                Person person = pConverter.convertFromBOToJaxB(idPer.getPlayer());
                personList.add(person);
            }
            return personList;
        }
    }

    @Override
    public List<PersonSearchResult> searchPersons(PersonSearchCriteria psCriteria) {

        List<PersonSearchResult> psrList = new ArrayList<PersonSearchResult>();
        if (psCriteria == null) {
            // null check to avoid null pointer during isValid() check
            LOG.error("Person search couldn't be performed as seach criteria is null.");
            throw new ServiceException("Person search couldn't be performed as seach criteria is null.");
        }

        // get the corresponding BO object
        PersonSearchConverter psConverter = new PersonSearchConverter();
        gov.nih.nci.po.service.PersonSearchCriteria psCriteriaBo = psConverter.convertPSCFromJaxbToBO(psCriteria);

        try {
            // check that the search criteria is valid
            psCriteriaBo.isValid();
        } catch (OneCriterionRequiredException e) {
            LOG.error("Person search couldn't be performed as search crietria is empty." + e);
            throw new ServiceException("Person search couldn't be performed as search crietria is empty." + e);
        }

        // instantiate PageSortParams (in ascending order of PERSON_ID)
        PageSortParams<PersonSearchDTO> pageSortParams = new PageSortParams<PersonSearchDTO>(
                psCriteria.getLimit(), psCriteria.getOffset(), null, false, getDynamicSortCriteria());

        // call the EJB service method to search the Persons
        List<PersonSearchDTO> psDtoList = personBoService.search(psCriteriaBo, pageSortParams);

        if (CollectionUtils.isNotEmpty(psDtoList)) {
            // Convert it to JaxB object & add in the list
            for (PersonSearchDTO psDto : psDtoList) {
                PersonSearchResult psResult = psConverter.convertPSRFromBOToJaxB(psDto);
                psrList.add(psResult);
            }
        }

        return psrList;
    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    @Override
    public PersonRole createPersonRole(PersonRole personRole) {
        String msg = "The PersonRole couldn't be created as ";
        if (personRole == null) {
            LOG.error(msg + "personRole is null.");
            throw new ServiceException(msg + "personRole is null.");
        }
        if (personRole.getId() != null) {
            // If "Id" is present then curate will 'update' the DB
            LOG.error(msg + "personRoleId is present in the request.");
            throw new ServiceException(msg + "personRoleId is present in the request.");
        }
        Class<? extends PersonRole> clazz = personRole.getClass();
        try {
            // get the corresponding BO (to be passed in 'curate' method)
            gov.nih.nci.po.data.bo.PersonRole perRoleBo = convertFromJaxbRoleToBO(personRole);

            // create/curate PersonRole by calling EJB service method
            GenericStructrualRoleServiceLocal roleSerLocal = getGenericStructrualRoleServiceLocal(clazz);
            long id = roleSerLocal.create(perRoleBo);

            // get PersonRole BO from DB after creation
            perRoleBo = getPersonRoleBOByDBId(clazz, id);

            // convert from BO to JaxB & return it
            return convertFromBoRoleToJaxB(perRoleBo);

        } catch (Exception e) {
            LOG.error(
                    "Exception occured while creating the PersonRole for personID " + personRole.getPersonId() + "."
                    , e
            );
            throw new ServiceException(
                    "Exception occured while creating the PersonRole for personID " + personRole.getPersonId() + "."
                    , e
            );
        }

    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    @Override
    public PersonRole updatePersonRole(PersonRole personRole) {
        String msg = "The PersonRole couldn't be updated as ";
        // null check for PersonRole or PersonRoleId
        if ((personRole == null) || (personRole.getId() == null)) {
            LOG.error(msg + "either personRole or personRoleId is null.");
            throw new ServiceException(msg + "either personRole or personRoleId is null.");
        }

        Class<? extends PersonRole> clazz = personRole.getClass();
        try {
            // get the corresponding BO (to be passed in 'curate' method)
            gov.nih.nci.po.data.bo.PersonRole perRoleBo = convertFromJaxbRoleToBO(personRole);

            // update/curate PersonRole by calling EJB service method
            GenericStructrualRoleServiceLocal roleSerLocal = getGenericStructrualRoleServiceLocal(clazz);
            roleSerLocal.curate(perRoleBo);

            // get PersonRole BO from DB after updation
            perRoleBo = getPersonRoleBOByDBId(clazz, perRoleBo.getId());

            // convert from BO to JaxB & return it
            return convertFromBoRoleToJaxB(perRoleBo);

        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the PersonRole for personRoleId " + personRole.getId() + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the PersonRole for personRoleId " + personRole.getId() + ".", e);
        }
    }

    @Override
    public List<PersonRole> getPersonRolesByPersonId(long personID) {
        // get the BO Person for given personID
        gov.nih.nci.po.data.bo.Person personBo = personBoService.getById(personID);
        if (personBo != null) {
            List<PersonRole> personRoleList = new ArrayList<PersonRole>();

            // populate HealthCareProvider list
            populateHCPPersonRole(personBo, personRoleList);

            // populate ClinicalResearchStaff list
            populateCRSPersonRole(personBo, personRoleList);

            // populate OrganizationalContact list
            populateOrgContactPersonRole(personBo, personRoleList);

            // return the personRoleList
            return personRoleList;
        }

        // If the person is not found in DB then throw ServiceException
        LOG.error("The PersonRole couldn't be fetched as person with the given ID " + personID + " is not found.");
        throw new EntityNotFoundException(
                "The PersonRole couldn't be fetched as person with the given ID " + personID + " is not found.");
    }

    @SuppressWarnings(UNCHECKED)
    @Override
    public <T extends PersonRole> T getPersonRoleById(Class<T> clazz, long personRoleID) {
        // get the PersonRole BO from the DB
        gov.nih.nci.po.data.bo.PersonRole perRoleBo = getPersonRoleBOByDBId(clazz, personRoleID);

        // check if PersonRole is found or not
        if (perRoleBo != null) {
            // Convert from BO to JaxB
            return (T) convertFromBoRoleToJaxB(perRoleBo);
        } else {
            // if PersonRole is not found
            return null;
        }
    }

    @SuppressWarnings({ RAW_TYPES, UNCHECKED })
    @Override
    public <T extends PersonRole> T changePersonRoleStatus(Class<T> clazz, long personRoleID, EntityStatus status) 
            {        
        T retPerRole = null;
        // get the PersonRole from the database
        gov.nih.nci.po.data.bo.PersonRole perRoleBo = getPersonRoleBOByDBId(clazz, personRoleID);
        if (perRoleBo == null) {
            LOG.error("Couldn't update the PersonRole Status for personRoleID " + personRoleID + NOT_FOUND_IN_DB_MSG);
            throw new EntityNotFoundException(
                    "Couldn't update the PersonRole Status for personRoleID " + personRoleID + NOT_FOUND_IN_DB_MSG);
        }
        
        try {           
             // set the status
             GenericStructrualRoleServiceLocal roleSerLocal = getGenericStructrualRoleServiceLocal(clazz);
             perRoleBo.setStatus(PoWSUtil.getBORoleStatus(status.name()));

             // update/curate PersonRole by calling EJB service method
             roleSerLocal.curate(perRoleBo);

             // convert from BO to JaxB
             retPerRole = (T) convertFromBoRoleToJaxB(perRoleBo);           
        } catch (Exception e) {
            LOG.error(
                    "Exception occured while updating the PersonRole Status for personRoleID " + personRoleID + ".", e);
            throw new ServiceException(
                    "Exception occured while updating the PersonRole Status for personRoleID " + personRoleID + ".", e);
        }

        return retPerRole;
    }

    /**
     * This method is used to get PersonRoleBO by DB Id.
     */
    @SuppressWarnings(RAW_TYPES)
    private <T extends PersonRole> gov.nih.nci.po.data.bo.PersonRole getPersonRoleBOByDBId(
            Class<T> clazz, long personRoleID) {
        
        String msg = "Couldn't get PersonRoleBOByDBId for personRoleID "
                + personRoleID;
        if (clazz == null) {
            LOG.error(msg + personRoleID + CLASS_NULL_MSG);
            throw new ServiceException(msg + personRoleID + CLASS_NULL_MSG);
        }

        GenericStructrualRoleServiceLocal rolSerLocal = getGenericStructrualRoleServiceLocal(clazz);
        gov.nih.nci.po.data.bo.PersonRole personRoleBo = (gov.nih.nci.po.data.bo.PersonRole) rolSerLocal
                .getById(personRoleID);
        return personRoleBo;
    }

    /**
     * This method is used to populate JaxB HCP-RoleList from the PersonBO.
     */
    private void populateHCPPersonRole(gov.nih.nci.po.data.bo.Person personBo,
            Collection<PersonRole> personRoleList) {
        if (CollectionUtils.isNotEmpty(personBo.getHealthCareProviders())) {
            HealthCareProviderConverter hcpConverter = Converters.get(HealthCareProviderConverter.class);
            for (gov.nih.nci.po.data.bo.HealthCareProvider hcpBo : personBo.getHealthCareProviders()) {
                HealthCareProvider hcp = hcpConverter.convertFromBOToJaxB(hcpBo);
                personRoleList.add(hcp);
            }
        }
    }

    /**
     * This method is used to populate JaxB CRS-RoleList in the PersonBO.
     */
    private void populateCRSPersonRole(gov.nih.nci.po.data.bo.Person personBo,
            Collection<PersonRole> personRoleList) {
        if (CollectionUtils.isNotEmpty(personBo.getClinicalResearchStaff())) {
            ClinicalResearchStaffConverter crsConverter = Converters.get(ClinicalResearchStaffConverter.class);
            for (gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo : personBo.getClinicalResearchStaff()) {
                ClinicalResearchStaff crs = crsConverter.convertFromBOToJaxB(crsBo);
                personRoleList.add(crs);
            }
        }
    }

    /**
     * This method is used to populate JaxB OC-RoleList in the PersonBO.
     */
    private void populateOrgContactPersonRole(
            gov.nih.nci.po.data.bo.Person personBo, Collection<PersonRole> personRoleList) {
        if (CollectionUtils.isNotEmpty(personBo.getOrganizationalContacts())) {
            OrganizationalContactConverter ocConverter = Converters.get(OrganizationalContactConverter.class);
            for (gov.nih.nci.po.data.bo.OrganizationalContact ocBo : personBo.getOrganizationalContacts()) {
                OrganizationalContact oc = ocConverter.convertFromBOToJaxB(ocBo);
                personRoleList.add(oc);
            }
        }
    }

    @SuppressWarnings(RAW_TYPES)
    private <T extends PersonRole> GenericStructrualRoleServiceLocal getGenericStructrualRoleServiceLocal(
            Class<T> personRoleClass) {
        GenericStructrualRoleServiceLocal gsRolSerLocal = null;
        if (HealthCareProvider.class.isAssignableFrom(personRoleClass)) {
            gsRolSerLocal = healthCareProviderBoService;
        } else if (ClinicalResearchStaff.class.isAssignableFrom(personRoleClass)) {
            gsRolSerLocal = clinicalResearchStaffBoService;
        } else if (OrganizationalContact.class.isAssignableFrom(personRoleClass)) {
            gsRolSerLocal = organizationalContactBoService;
        } else {
            throw new ServiceException(
                    "Exception while getting GenericStructrualRoleServiceLocal as the incoming Class is wrong.");
        }

        return gsRolSerLocal;
    }

    /**
     * This method is used to convert BO into JaxB.
     */

    private PersonRole convertFromBoRoleToJaxB(gov.nih.nci.po.data.bo.PersonRole perRoleBo) {
        if (perRoleBo instanceof gov.nih.nci.po.data.bo.HealthCareProvider) {
            gov.nih.nci.po.data.bo.HealthCareProvider hcpBo = (gov.nih.nci.po.data.bo.HealthCareProvider) perRoleBo;
            HealthCareProviderConverter hcpConverter = Converters.get(HealthCareProviderConverter.class);
            return hcpConverter.convertFromBOToJaxB(hcpBo);
        } else if (perRoleBo instanceof gov.nih.nci.po.data.bo.ClinicalResearchStaff) {
            gov.nih.nci.po.data.bo.ClinicalResearchStaff crsBo
                    = (gov.nih.nci.po.data.bo.ClinicalResearchStaff) perRoleBo;
            ClinicalResearchStaffConverter crsConverter = Converters.get(ClinicalResearchStaffConverter.class);
            return crsConverter.convertFromBOToJaxB(crsBo);
        } else if (perRoleBo instanceof gov.nih.nci.po.data.bo.OrganizationalContact) {
            gov.nih.nci.po.data.bo.OrganizationalContact ocBo
                    = (gov.nih.nci.po.data.bo.OrganizationalContact) perRoleBo;
            OrganizationalContactConverter ocConverter = Converters.get(OrganizationalContactConverter.class);
            return ocConverter.convertFromBOToJaxB(ocBo);
        } else {
            throw new ServiceException("Can't convert PersonRoleBO to JaxB as type is wrong.");
        }

    }

    /**
     * This method is used to convert JaxB into BO.
     */
    private <T extends PersonRole> gov.nih.nci.po.data.bo.PersonRole convertFromJaxbRoleToBO(PersonRole jaxbPerRole) {

        if (jaxbPerRole instanceof HealthCareProvider) {
            HealthCareProvider hcp = (HealthCareProvider) jaxbPerRole;            
            HealthCareProviderConverter hcpConverter = Converters.get(HealthCareProviderConverter.class);
            return hcpConverter.convertFromJaxbToBO(hcp);
        } else if (jaxbPerRole instanceof ClinicalResearchStaff) {
            ClinicalResearchStaff crs = (ClinicalResearchStaff) jaxbPerRole;            
            ClinicalResearchStaffConverter crsConverter = Converters.get(ClinicalResearchStaffConverter.class);
            return crsConverter.convertFromJaxbToBO(crs);
        } else if (jaxbPerRole instanceof OrganizationalContact) {
            OrganizationalContact oc = (OrganizationalContact) jaxbPerRole;            
            OrganizationalContactConverter ocConverter = Converters.get(OrganizationalContactConverter.class);
            return ocConverter.convertFromJaxbToBO(oc);
        } else {
            throw new ServiceException("Can't convert JaxB-PersonRole to BO as type is wrong.");
        }
    }

    private String getPersonName(Person person) {
        return person.getFirstName() + " " + person.getLastName();
    }

    private List<String> getDynamicSortCriteria() {
        List<String> dscList = new ArrayList<String>();
        dscList.add("PERSON_ID");
        return dscList;
    }

    /*
     * GETTERS AND SETTERS
     */

    /**
     * @return The Person BO service.
     */
    public PersonBoService getPersonBoService() {
        return personBoService;
    }

    /**
     *
     * @param personBoService  The Bo service.
     */
    public void setPersonBoService(PersonBoService personBoService) {
        this.personBoService = personBoService;
    }

    /**
     * @return The IdentifiedPerson BO service.
     */
    public IdentifiedPersonBoService getIdentifiedPersonBoService() {
        return identifiedPersonBoService;
    }

    /**
     *
     * @param identifiedPersonBoService   The Bo service.
     */
    public void setIdentifiedPersonBoService(IdentifiedPersonBoService identifiedPersonBoService) {
        this.identifiedPersonBoService = identifiedPersonBoService;
    }

    /**
     * @return The HealthCareProvider BO service.
     */
    public HealthCareProviderBoService getHealthCareProviderBoService() {
        return healthCareProviderBoService;
    }

    /**
     *
     * @param healthCareProviderBoService The Bo service.
     */
    public void setHealthCareProviderBoService(HealthCareProviderBoService healthCareProviderBoService) {
        this.healthCareProviderBoService = healthCareProviderBoService;
    }

    /**
     * @return The ClinicalResearchStaff BO service.
     */
    public ClinicalResearchStaffBoService getClinicalResearchStaffBoService() {
        return clinicalResearchStaffBoService;
    }

    /**
     *
     * @param clinicalResearchStaffBoService  The Bo service.
     */
    public void setClinicalResearchStaffBoService(ClinicalResearchStaffBoService clinicalResearchStaffBoService) {
        this.clinicalResearchStaffBoService = clinicalResearchStaffBoService;
    }

    /**
     * @return The OrganizationalContact BO service.
     */
    public OrganizationalContactBoService getOrganizationalContactBoService() {
        return organizationalContactBoService;
    }

    /**
     *
     * @param organizationalContactBoService   The Bo service.
     */
    public void setOrganizationalContactBoService(OrganizationalContactBoService organizationalContactBoService) {
        this.organizationalContactBoService = organizationalContactBoService;
    }

}
