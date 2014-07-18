/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.EntityNamePartType;
import gov.nih.nci.iso21090.Enxp;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.webservices.PoEntityCannotBeCreatedException;
import gov.nih.nci.pa.webservices.PoEntityNotFoundException;
import gov.nih.nci.pa.webservices.types.OrganizationOrPersonID;
import gov.nih.nci.pa.webservices.types.Person;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.webservices.types.Address;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonSearchCriteriaDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author dkrylov
 * 
 */
public class PersonDTOBuilder {

    private static final Logger LOG = Logger.getLogger(PersonDTOBuilder.class);

    /**
     * @param p
     *            Person
     * @return PersonDTO
     */
    public PersonDTO build(Person p) {
        if (p == null) {
            return null;
        }
        if (p.getExistingPerson() != null) {
            return locateExistingPerson(p.getExistingPerson());
        } else {
            return createNewPerson(p.getNewPerson());
        }
    }

    private PersonDTO createNewPerson(gov.nih.nci.po.webservices.types.Person p) {
        try {
            PersonDTO dto = new PersonDTO();
            dto.setName(new EnPn());
            Enxp part = new Enxp(EntityNamePartType.GIV);
            part.setValue(p.getFirstName());
            dto.getName().getPart().add(part);
            // if middle name exists stick it in here!
            if (StringUtils.isNotEmpty(p.getMiddleName())) {
                Enxp partMid = new Enxp(EntityNamePartType.GIV);
                partMid.setValue(p.getMiddleName());
                dto.getName().getPart().add(partMid);
            }
            Enxp partFam = new Enxp(EntityNamePartType.FAM);
            partFam.setValue(p.getLastName());
            dto.getName().getPart().add(partFam);
            if (StringUtils.isNotEmpty(p.getPrefix())) {
                Enxp partPfx = new Enxp(EntityNamePartType.PFX);
                partPfx.setValue(p.getPrefix());
                dto.getName().getPart().add(partPfx);
            }
            if (StringUtils.isNotEmpty(p.getSuffix())) {
                Enxp partSfx = new Enxp(EntityNamePartType.SFX);
                partSfx.setValue(p.getSuffix());
                dto.getName().getPart().add(partSfx);
            }

            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            if (StringUtils.isNotBlank(getContact(p, ContactType.PHONE))) {
                Tel t = new Tel();
                t.setValue(new URI("tel", getContact(p, ContactType.PHONE),
                        null));
                telco.getItem().add(t);
            }
            if (StringUtils.isNotBlank(getContact(p, ContactType.FAX))) {
                Tel f = new Tel();
                f.setValue(new URI("x-text-fax",
                        getContact(p, ContactType.FAX), null));
                telco.getItem().add(f);
            }
            if (StringUtils.isNotBlank(getContact(p, ContactType.TTY))) {
                Tel tt = new Tel();
                tt.setValue(new URI("x-text-tel",
                        getContact(p, ContactType.TTY), null));
                telco.getItem().add(tt);
            }
            if (StringUtils.isNotBlank(getContact(p, ContactType.URL))) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(getContact(p, ContactType.URL)));
                telco.getItem().add(telurl);
            }
            if (StringUtils.isNotBlank(getContact(p, ContactType.EMAIL))) {
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:"
                        + getContact(p, ContactType.EMAIL)));
                telco.getItem().add(telemail);
            }
            dto.setTelecomAddress(telco);

            final Address addr = p.getAddress();
            dto.setPostalAddress(AddressConverterUtil.create(addr.getLine1(),
                    addr.getLine2(), addr.getCity(), addr.getStateOrProvince(),
                    addr.getPostalcode(), addr.getCountry().value()));
            dto.setStatusCode(CdConverter.convertStringToCd(p.getStatus()
                    .value()));
            return PoRegistry.getPersonEntityService().getPerson(
                    PoRegistry.getPersonEntityService().createPerson(dto));
        } catch (NullifiedEntityException e) {
            throw new PoEntityCannotBeCreatedException(
                    "It appears you have attempted to create a person "
                            + p.getFirstName()
                            + " "
                            + p.getLastName()
                            + " in NULLIFIED status. "
                            + "Such persons, albeit can be created, cannot be used on trials.");
        } catch (URISyntaxException | EntityValidationException
                | CurationException e) {
            LOG.error(e, e);
            throw new PoEntityCannotBeCreatedException(
                    "Person "
                            + p.getFirstName()
                            + " "
                            + p.getLastName()
                            + " could not be created in PO because it has either missing or invalid data. "
                            + "Person information is subject to PO business rules and validation."
                            + " Additional information: " + e.getMessage());

        }
    }

    private PersonDTO locateExistingPerson(OrganizationOrPersonID id) {
        if (id.getPoID() != null) {
            return locateExistingPersonByPOID(id.getPoID());
        } else {
            return locateExistingPersonByCtepID(id.getCtepID());
        }
    }

    private PersonDTO locateExistingPersonByCtepID(final String ctepID) {
        try {
            PersonSearchCriteriaDTO personSearchCriteria = new PersonSearchCriteriaDTO();
            personSearchCriteria.setCtepId(ctepID);
            List<PaPersonDTO> persons = PADomainUtils
                    .searchPoPersons(personSearchCriteria);
            CollectionUtils.filter(persons, new Predicate() {
                @Override
                public boolean evaluate(Object o) {
                    PaPersonDTO p = (PaPersonDTO) o;
                    return StringUtils.equalsIgnoreCase(p.getCtepId(), ctepID);
                }
            });

            if (CollectionUtils.isEmpty(persons)) {
                throw new PoEntityNotFoundException("Person with CTEP ID of "
                        + ctepID + " cannot be found in PO.");
            } else if (persons.size() > 1) {
                throw new PoEntityNotFoundException(
                        "It appears there are "
                                + persons.size()
                                + " persons with CTEP ID of "
                                + ctepID
                                + " in PO. Since we don't know which one to pick, we are having to fail this request.");
            } else {
                return locateExistingPersonByPOID(persons.get(0).getId());
            }
        } catch (NullifiedRoleException | PAException | TooManyResultsException e) {
            LOG.error(e, e);
            throw new PoEntityNotFoundException(
                    "Person with CTEP ID of "
                            + ctepID
                            + " cannot be found in PO due to an internal error. Details: "
                            + e.getMessage());
        }

    }

    private String getContact(gov.nih.nci.po.webservices.types.Person p,
            ContactType type) {
        for (Contact c : p.getContact()) {
            if (type == c.getType()) {
                return c.getValue();
            }
        }
        return StringUtils.EMPTY;
    }

    private PersonDTO locateExistingPersonByPOID(Long poID) {
        try {
            PersonDTO p = PoRegistry.getPersonEntityService().getPerson(
                    IiConverter.convertToPoPersonIi(poID.toString()));
            if (p == null) {
                throw new PoEntityNotFoundException("Person with PO ID of "
                        + poID + " cannot be found in PO.");
            }
            return p;
        } catch (NullifiedEntityException e) {
            throw new PoEntityNotFoundException(// NOPMD
                    "Person with PO ID of "
                            + poID
                            + " appears to have NULLIFIED status in PO and thus cannot be used.");
        }
    }

}
