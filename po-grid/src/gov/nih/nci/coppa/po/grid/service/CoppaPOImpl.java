package gov.nih.nci.coppa.po.grid.service;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.PersonTransformer;
import gov.nih.nci.coppa.po.grid.remote.InvokeOrganizationEjb;
import gov.nih.nci.coppa.po.grid.remote.InvokePersonEjb;
import gov.nih.nci.coppa.po.grid.stubs.types.CoppaPersonServiceFault;
import gov.nih.nci.coppa.po.grid.stubs.types.NullifiedEntityFault;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.iso._21090.II;

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
            throw new RemoteException(e.getMessage());
            //TODO Add support for CoppaPersonServiceFault
//            CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
//            coppaFault.setFaultDetailString("Error in getting persons." + e.getMessage());
//            throw coppaFault;
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
            throw new RemoteException(e.getMessage());
            //TODO Add support for CoppaPersonServiceFault
//            CoppaPersonServiceFault coppaFault = new CoppaPersonServiceFault();
//            coppaFault.setFaultDetailString("Error in getting persons." + e.getMessage());
//            throw coppaFault;
        }
    }

  public gov.nih.nci.coppa.po.Organization[] searchOrganizations(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        // TODO: Implement this autogenerated method
        throw new RemoteException("Not yet implemented");
    }

  public gov.nih.nci.coppa.po.Person[] searchPersons(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        try {
            if (logger.isDebugEnabled()) {
                StringWriter sw = new StringWriter();
                Utils.serializeObject(person, new javax.xml.namespace.QName("http://gov.nih.nci.coppa.po", "Person"),
                        sw);
                logger.debug("Search person invoked with :" + sw);
            }
            PersonDTO person_iso = new PersonTransformer().transform(person);
            List<PersonDTO> results = personService.search(person_iso);
            if (results == null)
                return null;
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
            throw new RemoteException(e.getMessage());
        }
    }

  public gov.nih.nci.coppa.po.Person echoPerson(gov.nih.nci.coppa.po.Person person) throws RemoteException {
        return person;
    }

  public gov.nih.nci.coppa.po.Organization echoOrganization(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
        return organization;
    }

}
