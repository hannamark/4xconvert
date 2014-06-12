/**
 * 
 */
package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Person;
import gov.nih.nci.po.webservices.types.PersonRole;
import gov.nih.nci.po.webservices.types.PersonSearchCriteria;
import gov.nih.nci.po.webservices.types.PersonSearchResult;

import java.util.List;

/**
 * Person Service Interface.
 * 
 * @author Denis G. Krylov
 * 
 */
public interface PersonService {

    /**
     * @param person
     *            - person to be created
     * @return Person- created Person
     */
    Person createPerson(Person person);

    /**
     * @param person
     *            - person to be updated
     * @return Person- updated person
     */
    Person updatePerson(Person person);

    /**
     * @param personID
     *            - Id of the person whose status is to be changed.
     * @param status
     *            - new status
     * @return Person- updated person
     */
    Person changePersonStatus(long personID, EntityStatus status);

    /**
     * @param personID
     *            - Id of the person to be get
     * @return Person- person corresponding to giving Id
     */
    Person getPerson(long personID);

    /**
     * @param ctepID
     *            - ctepId of the person(s) to be get
     * @return List<Person>- List<Person> for given CtepId
     */
    List<Person> getPersonsByCtepId(String ctepID);

    /**
     * 
     * @param psCriteria
     *            - PersonSearchCriteria
     * @return - List<PersonSearchResult> - list of PersonSearchResult matching
     *         the search criteria
     */
    List<PersonSearchResult> searchPersons(PersonSearchCriteria psCriteria);

    /**
     * @param personRole
     *            - PersonRole to be created
     * @return PersonRole- created PersonRole
     */
    PersonRole createPersonRole(PersonRole personRole);

    /**
     * @param personRole
     *            - PersonRole to be updated
     * @return PersonRole- updated PersonRole
     */
    PersonRole updatePersonRole(PersonRole personRole);

    /**
     * This method is used to get the PersonRoles for given PersonId.
     * 
     * @param personID
     *            -Id of the Person whose PersonRole to be get
     * @return List<PersonRole> - List<PersonRole> corresponding to given
     *         personId
     */
    List<PersonRole> getPersonRolesByPersonId(long personID);

    /**
     * @param <T>
     *            - HealthCareProvider or OrganizationalContact or
     *            ClinicalResearchStaff
     * @param clazz
     *            HealthCareProvider or OrganizationalContact or
     *            ClinicalResearchStaff Class
     * @param personRoleID
     *            DB PersonRole Id
     * @return <T extends PersonRole> - Object of type either HealthCareProvider
     *         or OrganizationalContact or ClinicalResearchStaff
     */
    <T extends PersonRole> T getPersonRoleById(Class<T> clazz, long personRoleID);

    /**
     * @param <T>
     *            - HealthCareProvider or OrganizationalContact or
     *            ClinicalResearchStaff
     * @param clazz
     *            HealthCareProvider or OrganizationalContact or
     *            ClinicalResearchStaff
     * @param personRoleID
     *            DB PersonRole Id
     * @param status
     *            New PersonRole Status
     * @return <T extends PersonRole> Object of type either HealthCareProvider
     *         or OrganizationalContact or ClinicalResearchStaff
     */
    <T extends PersonRole> T changePersonRoleStatus(Class<T> clazz,
            long personRoleID, EntityStatus status);

}
