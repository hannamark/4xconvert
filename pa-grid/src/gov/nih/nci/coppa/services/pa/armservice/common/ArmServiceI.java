package gov.nih.nci.coppa.services.pa.armservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public interface ArmServiceI {

  /**
   * Get an Arm from its identifier
   *
   * @param id
   * @throws PAFault
   *	
   */
  public gov.nih.nci.coppa.services.pa.Arm get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  /**
   * Get the Arms associated with a planned activity
   *
   * @param id
   * @throws PAFault
   *	
   */
  public gov.nih.nci.coppa.services.pa.Arm[] getByPlannedActivity(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  /**
   * Get the Arms associated with a study protocol.
   *
   * @param id
   * @throws PAFault
   *	
   */
  public gov.nih.nci.coppa.services.pa.Arm[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  /**
   * Copies the Arms associated with a studyprotocol and associates the copies with the new studyprotocol
   *
   * @param fromStudyProtocolId
   * @param toStudyProtocolId
   * @throws PAFault
   *	
   */
  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  /**
   * Get the current Arm associated with the study protocol.
   *
   * @param studyProtocolId
   * @throws PAFault
   *	
   */
  public gov.nih.nci.coppa.services.pa.Arm getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.Arm create(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.Arm update(gov.nih.nci.coppa.services.pa.Arm arm) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

}

