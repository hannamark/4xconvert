package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class HealthCareFacilityImpl extends HealthCareFacilityImplBase {
    private static org.apache.log4j.Logger logger = LogManager.getLogger(HealthCareFacilityImpl.class);
    
    private GenericCorrelationGridServiceImpl<HealthCareFacilityDTO,HealthCareFacility> impl 
        = new GenericCorrelationGridServiceImpl<HealthCareFacilityDTO, HealthCareFacility>(HealthCareFacility.class, HealthCareFacilityDTO.class);

	public HealthCareFacilityImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.HealthCareFacility getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(healthCareFacility);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException {
      return impl.validate(healthCareFacility);
  }

  public void update(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(healthCareFacility);
  }

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] query(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.query(healthCareFacility, limitOffset);
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException {
      return impl.getByPlayerIds(id); 
  }

}

