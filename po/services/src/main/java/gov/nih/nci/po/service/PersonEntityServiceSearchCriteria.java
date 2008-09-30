package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;

/**
 * Class used to store search criteria for finding people.
 */
public class PersonEntityServiceSearchCriteria extends AbstractPersonSearchCriteria implements SearchCriteria<Person> {

    private static final String PERSON_POSTAL_ADDRESS_PROPERTY_NAME = "postalAddress";
    private Person person;

    /**
     * @return person to search for
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person to search for
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return true if at least value is non-null otherwise, false.
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return person != null
                && (isValueSpecified(person.getFirstName()) || isValueSpecified(person.getLastName())
                        || isValueSpecified(person.getMiddleName()) || isValueSpecified(person.getPrefix())
                        || isValueSpecified(person.getSuffix()) || CollectionUtils.isNotEmpty(person.getEmail())
                        || CollectionUtils.isNotEmpty(person.getFax()) || CollectionUtils.isNotEmpty(person.getPhone())
                        || CollectionUtils.isNotEmpty(person.getTty()) || CollectionUtils.isNotEmpty(person.getUrl())
                        || isAddressCriterionSpecified() || person.getStatusCode() != null);
    }

    private boolean isAddressCriterionSpecified() {
        return new AddressSearchCriteria(person.getPostalAddress()).hasOneCriterionSpecified();
    }

    /**
     * {@inheritDoc}
     */
    public Criteria getCriteria() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    protected StringBuffer getQueryWhereClause(Map<String, Object> namedParameters, String personAlias) {
        List<String> whereClause = new ArrayList<String>();
        String personAliasDot = personAlias + DOT;
        whereClause.add(addNotEqual(personAliasDot + PERSON_STATUS_PROPERTY, PERSON_STATUS_PROPERTY + "1",
                EntityStatus.NULLIFIED, namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_FIRST_NAME_PROPERTY, PERSON_FIRST_NAME_PROPERTY, person
                .getFirstName(), namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_LAST_NAME_PROPERTY, PERSON_LAST_NAME_PROPERTY, person
                .getLastName(), namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_MIDDLE_NAME_PROPERTY, PERSON_MIDDLE_NAME_PROPERTY, person
                .getMiddleName(), namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_PREFIX_PROPERTY, PERSON_PREFIX_PROPERTY, person.getPrefix(),
                namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_SUFFIX_PROPERTY, PERSON_SUFFIX_PROPERTY, person.getSuffix(),
                namedParameters));
        whereClause.add(addEqual(personAliasDot + PERSON_STATUS_PROPERTY, PERSON_STATUS_PROPERTY, person
                .getStatusCode(), namedParameters));
        String personId = personAliasDot + PERSON_ID_PROPERTY;
        whereClause.add(inIfRhs(personId, findMatchingEmail(namedParameters).toString()));
        whereClause.add(inIfRhs(personId, findMatchingPhone(namedParameters).toString()));
        whereClause.add(inIfRhs(personId, findMatchingFax(namedParameters).toString()));
        whereClause.add(inIfRhs(personId, findMatchingTty(namedParameters).toString()));
        whereClause.add(inIfRhs(personId, findMatchingUrl(namedParameters).toString()));
        whereClause.add(inIfRhs(personId, findMatchingAddress(namedParameters).toString()));
        return buildWhereClause(whereClause, WhereClauseOperator.CONJUNCTION);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find email addresses for a person
     */
    protected StringBuffer findMatchingEmail(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, person.getEmail(), Email.class, PERSON_EMAIL_PROPERTY_NAME,
                Person.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find phone numbers for a person
     */
    protected StringBuffer findMatchingPhone(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, person.getPhone(), PhoneNumber.class, PERSON_PHONE_PROPERTY_NAME,
                Person.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find fax numbers for a person
     */
    protected StringBuffer findMatchingFax(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, person.getFax(), PhoneNumber.class, PERSON_FAX_PROPERTY_NAME,
                Person.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find TTY numbers for a person
     */
    protected StringBuffer findMatchingTty(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, person.getTty(), PhoneNumber.class, PERSON_TTY_PROPERTY_NAME,
                Person.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return HQL-based select statement to find URLs for a person
     */
    protected StringBuffer findMatchingUrl(Map<String, Object> namedParameters) {
        return findMatchingContact(namedParameters, person.getUrl(), URL.class, PERSON_URL_PROPERTY_NAME, Person.class);
    }

    /**
     * @param namedParameters map to use to set named parameter values in the Query
     * @return person.id or organization.id
     */
    protected StringBuffer findMatchingAddress(Map<String, Object> namedParameters) {
        AddressSearchCriteria sc = new AddressSearchCriteria();
        sc.setAddress(person.getPostalAddress());

        return findMatchingAddress(namedParameters, sc, Person.class, PERSON_POSTAL_ADDRESS_PROPERTY_NAME);
    }
}
