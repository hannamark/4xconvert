package gov.nih.nci.coppa.po.grid.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.PersonTransformer;
import gov.nih.nci.coppa.po.grid.remote.InvokeOrganizationEjb;
import gov.nih.nci.coppa.po.grid.remote.InvokePersonEjb;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class. IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class CoppaPOImpl extends CoppaPOImplBase {

    private static org.apache.log4j.Logger logger = LogManager.getLogger(CoppaPOImpl.class);
    private InvokePersonEjb personService = new InvokePersonEjb();
    private InvokeOrganizationEjb organizationService = new InvokeOrganizationEjb();

    public CoppaPOImpl() throws RemoteException {
        super();
    }

    public gov.nih.nci.coppa.po.Person getPerson(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
        try {
            Ii ii_iso = new IITransformer().transform(identifier);
            PersonDTO person_iso = personService.getPerson(ii_iso);
            gov.nih.nci.coppa.po.Person person = new PersonTransformer().transform(person_iso);
            return person;
        } catch (Exception e) {
            logger.error("Error in getting persons.", e);
            throw reThrowRemote(e);
        }
    }

    public gov.nih.nci.coppa.po.Organization getOrganization(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
        try {
            Ii ii_iso = new IITransformer().transform(identifier);
            OrganizationDTO org_dto = organizationService.getOrganization(ii_iso);
            Organization org = new OrganizationTransformer().transform(org_dto);
            return org;
        } catch (Exception e) {
            logger.error("Error in getting persons.", e);
            throw reThrowRemote(e);
        }
    }

    public gov.nih.nci.coppa.po.Organization[] searchOrganizations(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        try{
            OrganizationDTO org = new OrganizationTransformer().transform(organization);
            List<OrganizationDTO> results = organizationService.search(org);
            if (results == null) {
                return null;
            }
            OrganizationTransformer transformer = new OrganizationTransformer();
            gov.nih.nci.coppa.po.Organization[] returnResults = new gov.nih.nci.coppa.po.Organization[results.size()];
            int i = 0;
            for (OrganizationDTO res : results) {
                gov.nih.nci.coppa.po.Organization o = transformer.transform(res);
                returnResults[i++] = o;
            }
            return returnResults;
        } catch (Exception e) {
            logger.error("Error in searching persons.", e);
            throw reThrowRemote(e);
        }
    }

    public gov.nih.nci.coppa.po.Person[] searchPersons(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        try {
            PersonDTO person_iso = new PersonTransformer().transform(person);
            List<PersonDTO> results = personService.search(person_iso);
            if (results == null) {
                return null;
            }
            logger.debug("Persons searched from COPPA:" + results.size());
            PersonTransformer transformer = new PersonTransformer();
            gov.nih.nci.coppa.po.Person[] returnResults = new gov.nih.nci.coppa.po.Person[results.size()];
            int i = 0;
            for (PersonDTO person_res : results) {
                gov.nih.nci.coppa.po.Person person_res_tr = transformer.transform(person_res);
                returnResults[i++] = person_res_tr;
            }
            return returnResults;
        } catch (Exception e) {
            logger.error("Error in searching persons.", e);
            throw reThrowRemote(e);
        }
    }

    public gov.nih.nci.coppa.po.Person echoPerson(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        return person;
    }

    public gov.nih.nci.coppa.po.Organization echoOrganization(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        return organization;
    }

    private static RemoteException reThrowRemote(Throwable t) throws RemoteException {
        if (t instanceof RemoteException) {
            throw (RemoteException)t;
        }
        RemoteException re = new RemoteException(t.toString(), t);
        throw re;
    }
}
