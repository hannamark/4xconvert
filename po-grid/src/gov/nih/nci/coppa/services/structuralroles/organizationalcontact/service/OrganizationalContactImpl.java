package gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationalContactTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.remote.InvokeOrganizationalContactEjb;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;


import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class OrganizationalContactImpl extends OrganizationalContactImplBase {
	/**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.4.8";
    
    private static org.apache.log4j.Logger logger = LogManager.getLogger(OrganizationalContactImpl.class);
	
	private InvokeOrganizationalContactEjb organizationalContactService = new InvokeOrganizationalContactEjb();

	public OrganizationalContactImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.po.OrganizationalContact getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
	  try {
			Ii ii_iso = IITransformer.INSTANCE.toDto(id);
			OrganizationalContactDTO orgContact_dto = organizationalContactService.getOrganizationalContact(ii_iso);
			OrganizationalContact orgContact= OrganizationalContactTransformer.INSTANCE.toXml(orgContact_dto);
			return orgContact;
		} catch (Exception e) {
			logger.error("Error in getting OrganizationalContact.", e);
			throw FaultUtil.reThrowRemote(e);
		}
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] search(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException {
	  try {
		  OrganizationalContactDTO orgContact_iso = OrganizationalContactTransformer.INSTANCE.toDto(organizationalContact);
			List<OrganizationalContactDTO> results = organizationalContactService.search(orgContact_iso);
			 if (results == null) {
	              return null;
	          }
	          logger.debug("OrganizationalContact searched from COPPA:" + results.size());
	          gov.nih.nci.coppa.po.OrganizationalContact [] returnResults = new gov.nih.nci.coppa.po.OrganizationalContact[results.size()];
	          int i = 0;
	          for (OrganizationalContactDTO orgContact_res : results) {
	              gov.nih.nci.coppa.po.OrganizationalContact orgContact_res_tr = OrganizationalContactTransformer.INSTANCE.toXml(orgContact_res);
	              returnResults[i++] = orgContact_res_tr;
	          }
	          return returnResults;		
	    } catch (Exception e) {
			logger.error("Error in getting OrganizationalContact.", e);
			throw FaultUtil.reThrowRemote(e);
		}
  }

  public void update(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }
  
}

