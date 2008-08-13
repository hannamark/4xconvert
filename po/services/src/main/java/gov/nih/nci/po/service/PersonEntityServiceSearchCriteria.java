package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;

/**
 * Class used to store search criteria for finding people.
 */
public class PersonEntityServiceSearchCriteria extends AbstractPersonSearchCriteria implements SearchCriteria<Person> {

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
                        || isValueSpecified(person.getPrefix()) || isValueSpecified(person.getSuffix()));
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
        whereClause.add(addILike(personAliasDot + PERSON_FIRST_NAME_PROPERTY, PERSON_FIRST_NAME_PROPERTY, person
                .getFirstName(), namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_LAST_NAME_PROPERTY, PERSON_LAST_NAME_PROPERTY, person
                .getLastName(), namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_PREFIX_PROPERTY, PERSON_PREFIX_PROPERTY, person.getPrefix(),
                namedParameters));
        whereClause.add(addILike(personAliasDot + PERSON_SUFFIX_PROPERTY, PERSON_SUFFIX_PROPERTY, person.getSuffix(),
                namedParameters));
        return buildWhereClause(whereClause, WhereClauseOperator.CONJUNCTION);
    }

}
