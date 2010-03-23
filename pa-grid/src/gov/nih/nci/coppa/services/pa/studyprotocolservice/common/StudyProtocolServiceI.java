package gov.nih.nci.coppa.services.pa.studyprotocolservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public interface StudyProtocolServiceI {

  public gov.nih.nci.coppa.services.pa.StudyProtocol getStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyProtocol updateStudyProtocol(gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol getInterventionalStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol updateInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol interventionalStudyProtocol) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.iso21090.extensions.Id createInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.InterventionalStudyProtocol interventionalStudyProtocol) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.StudyProtocol[] search(gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault ;

}

