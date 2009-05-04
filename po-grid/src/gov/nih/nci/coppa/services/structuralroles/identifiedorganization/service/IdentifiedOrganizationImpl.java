package gov.nih.nci.coppa.services.structuralroles.identifiedorganization.service;

import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class IdentifiedOrganizationImpl extends IdentifiedOrganizationImplBase {

	private static org.apache.log4j.Logger logger = LogManager.getLogger(IdentifiedOrganizationImpl.class);
	
    private GenericCorrelationGridServiceImpl<IdentifiedOrganizationDTO,IdentifiedOrganization> impl 
    = new GenericCorrelationGridServiceImpl<IdentifiedOrganizationDTO, IdentifiedOrganization>(IdentifiedOrganization.class, IdentifiedOrganizationDTO.class);

	public IdentifiedOrganizationImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(identifiedOrganization);
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
	  return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] search(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      return impl.search(identifiedOrganization);
  }

  public void update(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    impl.update(identifiedOrganization);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException {
    return impl.validate(identifiedOrganization);
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] query(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      return impl.query(identifiedOrganization, limitOffset);
  }

}

