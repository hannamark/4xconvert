package gov.nih.nci.coppa.services.pa.armservice.service;

import java.rmi.RemoteException;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class ArmServiceImpl extends ArmServiceImplBase {

	
	public ArmServiceImpl() throws RemoteException {
		super();
	}
	

  public gov.nih.nci.coppa.services.pa.Arm get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Arm[] getByPlannedActivity(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Arm[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Arm[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Arm create(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Arm update(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

