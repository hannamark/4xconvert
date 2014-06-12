package gov.nih.nci.po.webservices.convert.simple;

import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.util.PoConstants;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Person;
import org.apache.commons.collections.CollectionUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * This is Converter class for Person.
 * 
 * @author Rohit Gupta
 */
public class PersonConverter
        extends
        AbstractConverter<gov.nih.nci.po.data.bo.Person, gov.nih.nci.po.webservices.types.Person> {

    /**
     * This method is used to convert JaxB object into corresponding BO.
     * 
     *
     * @param person
     *            -object to be mapped
     * @return mapped BO person
     */
    public gov.nih.nci.po.data.bo.Person convertFromJaxbToBO(
            Person person) {

        gov.nih.nci.po.data.bo.Person personBo = null;
        if (person != null) {
            personBo = new gov.nih.nci.po.data.bo.Person();
            gov.nih.nci.po.data.bo.Address addressBo = null;

            // Set the basic attributes
            personBo.setId(person.getId());
            personBo.setPrefix(person.getPrefix());
            personBo.setFirstName(person.getFirstName());
            personBo.setMiddleName(person.getMiddleName());
            personBo.setLastName(person.getLastName());
            personBo.setSuffix(person.getSuffix());
            personBo.setStatusCode(gov.nih.nci.po.data.bo.EntityStatus
                    .valueOf(person.getStatus().value()));

            // Set the Address
            gov.nih.nci.po.webservices.types.Address address = person
                    .getAddress();
            if (address != null) {
                AddressConverter addressConverter = new AddressConverter();
                addressBo = addressConverter.convertFromJaxbToBO(address);
                personBo.setPostalAddress(addressBo);
            }

            // logic to set CtepId is removed as CtepId is handled separately.

            // Set the Contacts
            if (CollectionUtils.isNotEmpty(person.getContact())) {
                populateJaxbContactListInBo(person.getContact(), personBo);
            }
        }

        return personBo;
    }

    /**
     * This method is used to convert BO object into corresponding JaxB object.
     * 
     *
     * @param personBo
     *            - object to be mapped
     * @return mapped JaxB object
     */
    public gov.nih.nci.po.webservices.types.Person convertFromBOToJaxB(
            gov.nih.nci.po.data.bo.Person personBo) {

        gov.nih.nci.po.webservices.types.Person person = null;
        gov.nih.nci.po.webservices.types.Address address = null;
        if (personBo != null) {
            person = new Person();

            // Set the basic attributes
            person.setId(personBo.getId());
            person.setPrefix(personBo.getPrefix());
            person.setFirstName(personBo.getFirstName());
            person.setMiddleName(personBo.getMiddleName());
            person.setLastName(personBo.getLastName());
            person.setSuffix(personBo.getSuffix());
            person.setStatus(EntityStatus.fromValue(personBo.getStatusCode()
                    .name()));

            // Set the Address
            gov.nih.nci.po.data.bo.Address addressBo = personBo
                    .getPostalAddress();
            if (addressBo != null) {
                AddressConverter addressConverter = new AddressConverter();
                address = addressConverter.convertFromBOToJaxB(addressBo);
                person.setAddress(address);
            }

            // Set the CtepId
            populateBoCtepIdInJaxbPerson(personBo, person);

            // Set the Contacts
            populateBoContactListInJaxb(personBo, person.getContact());
        }

        return person;
    }

    /**
     * This method is used to populate BOCtepId into Jaxb Person.
     */
    private void populateBoCtepIdInJaxbPerson(
            gov.nih.nci.po.data.bo.Person personBo,
            gov.nih.nci.po.webservices.types.Person person) {

        String ctepId = null;
        Set<IdentifiedPerson> identifiedPersonSet = personBo
                .getIdentifiedPersons();

        if (CollectionUtils.isNotEmpty(identifiedPersonSet)) {
            Iterator<IdentifiedPerson> iterator = identifiedPersonSet
                    .iterator();
            // Iterate and look for the one that has CTEP ID root
            while (iterator.hasNext()) {
                IdentifiedPerson identifiedPerson = iterator.next();
                if ((identifiedPerson.getAssignedIdentifier() != null)
                        && PoConstants.PERSON_CTEP_ID_ROOT
                                .equalsIgnoreCase(identifiedPerson
                                        .getAssignedIdentifier().getRoot())) {
                    ctepId = identifiedPerson.getAssignedIdentifier()
                            .getExtension();
                }
            }
            person.setCtepId(ctepId);
        }
    }

}
