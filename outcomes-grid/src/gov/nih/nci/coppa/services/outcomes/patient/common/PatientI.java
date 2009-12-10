package gov.nih.nci.coppa.services.outcomes.patient.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public interface PatientI {

  public gov.nih.nci.coppa.services.outcomes.Patient get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException ;

  public gov.nih.nci.coppa.services.outcomes.Patient create(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException ;

  public gov.nih.nci.coppa.services.outcomes.Patient update(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException ;

}

