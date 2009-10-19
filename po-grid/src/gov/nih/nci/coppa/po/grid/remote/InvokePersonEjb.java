package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.Map;

/**
 * Wrapper to call remote Person EJB.
 */
public class InvokePersonEjb implements PersonEntityServiceRemote {


    /**
     * {@inheritDoc}
     */
    public List<PersonDTO> search(PersonDTO person) {
        try {
            List<PersonDTO> persons = GridSecurityJNDIServiceLocator.newInstance().getPersonService().search(person);
            return persons;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PersonDTO getPerson(Ii ii) throws NullifiedEntityException {
        
        try {
            PersonDTO person = GridSecurityJNDIServiceLocator.newInstance().getPersonService().getPerson(ii);
            return person;
        } catch (NullifiedEntityException e) {
            throw e;
        } catch (UndeclaredThrowableException e) {
            throw (e);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Ii createPerson(PersonDTO person) throws EntityValidationException {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getPersonService().createPerson(person);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updatePerson(PersonDTO proposedState) throws EntityValidationException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getPersonService().updatePerson(proposedState);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updatePersonStatus(Ii targetPer, Cd statusCode) throws EntityValidationException {
        try {
            GridSecurityJNDIServiceLocator.newInstance().getPersonService().updatePersonStatus(targetPer, statusCode);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> validate(PersonDTO person) {
        try {
            return GridSecurityJNDIServiceLocator.newInstance().getPersonService().validate(person);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PersonDTO> search(PersonDTO person, LimitOffset page) throws TooManyResultsException {
        try {
            List<PersonDTO> persons = GridSecurityJNDIServiceLocator.newInstance().getPersonService().search(person, page);
            return persons;
        } catch (TooManyResultsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
