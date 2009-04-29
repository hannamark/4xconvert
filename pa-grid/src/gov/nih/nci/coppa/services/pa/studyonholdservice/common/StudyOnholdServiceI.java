package gov.nih.nci.coppa.services.pa.studyonholdservice.common;

import gov.nih.nci.coppa.services.pa.faults.PAFault;

import java.rmi.RemoteException;

/**
 * This class is autogenerated, DO NOT EDIT.
 *
 * This interface represents the API which is accessable on the grid service from the client.
 *
 * @created by Introduce Toolkit version 1.3
 *
 */
public interface StudyOnholdServiceI {

  public gov.nih.nci.coppa.services.pa.StudyOnhold[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  /**
   * Creates copies of the StudyOnholds that are associated with fromStudyProtocolId and associates them with toStudyProtocolId instead.
   *
   * @param fromStudyProtocolId
   * @param toStudyProtocolId
   * @throws PAFault
   *	
   */
  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyOnhold[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyOnhold get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyOnhold create(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyOnhold update(gov.nih.nci.coppa.services.pa.StudyOnhold studyOnhold) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.BL isOnhold(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

}

