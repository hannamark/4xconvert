package gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service;

import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.rmi.RemoteException;

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
    public static final String ORGANIZATIONAL_CONTACT_ROOT = Constants.NCI_OID + ".4.8";
    
    private static org.apache.log4j.Logger logger = LogManager.getLogger(OrganizationalContactImpl.class);
	
    private GenericCorrelationGridServiceImpl<OrganizationalContactDTO,OrganizationalContact> impl 
    = new GenericCorrelationGridServiceImpl<OrganizationalContactDTO, OrganizationalContact>(OrganizationalContact.class, OrganizationalContactDTO.class);

	public OrganizationalContactImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(organizationalContact);
  }

  public gov.nih.nci.coppa.po.OrganizationalContact getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
	  return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] search(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
	  return impl.search(organizationalContact);
  }

  public void update(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(organizationalContact);
  }

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException {
      return impl.validate(organizationalContact);
  }
  

  public gov.nih.nci.coppa.po.OrganizationalContact[] query(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.query(organizationalContact, limitOffset);
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException {
      return impl.getByPlayerIds(id);
  }

}

