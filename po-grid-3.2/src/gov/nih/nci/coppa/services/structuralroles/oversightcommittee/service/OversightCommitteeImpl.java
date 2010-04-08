package gov.nih.nci.coppa.services.structuralroles.oversightcommittee.service;

import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.coppa.services.structuralroles.identifiedorganization.service.IdentifiedOrganizationImpl;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class OversightCommitteeImpl extends OversightCommitteeImplBase {

private static org.apache.log4j.Logger logger = LogManager.getLogger(IdentifiedOrganizationImpl.class);
	
    private GenericCorrelationGridServiceImpl<OversightCommitteeDTO,OversightCommittee> impl 
    = new GenericCorrelationGridServiceImpl<OversightCommitteeDTO, OversightCommittee>(OversightCommittee.class, OversightCommitteeDTO.class);

	public OversightCommitteeImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(oversightCommittee);
  }

  public gov.nih.nci.coppa.po.OversightCommittee getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
	  return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public void update(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(oversightCommittee);
  }

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException {
      return impl.validate(oversightCommittee); 
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] query(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.query(oversightCommittee, limitOffset);
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException {
      return impl.getByPlayerIds(id);
  }

}

