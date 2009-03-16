package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InvokePersonEjb implements PersonEntityServiceRemote {

    static Logger logger = LogManager.getLogger(InvokePersonEjb.class);
    ServiceLocator locator = JNDIServiceLocator.getInstance();

    public List<PersonDTO> search(PersonDTO person) {
        try {
            List<PersonDTO> persons = locator.getPersonService().search(person);
            return persons;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public PersonDTO getPerson(Ii ii) throws NullifiedEntityException {
        try {
            PersonDTO person = locator.getPersonService().getPerson(ii);
            return person;
        } catch (NullifiedEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public Ii createPerson(PersonDTO person) throws EntityValidationException {
        try {
            return locator.getPersonService().createPerson(person);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public void updatePerson(PersonDTO proposedState) throws EntityValidationException {
        try {
            locator.getPersonService().updatePerson(proposedState);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public void updatePersonStatus(Ii targetPer, Cd statusCode) throws EntityValidationException {
        try {
            locator.getPersonService().updatePersonStatus(targetPer, statusCode);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public Map<String, String[]> validate(PersonDTO person) {
        try {
            return locator.getPersonService().validate(person);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
