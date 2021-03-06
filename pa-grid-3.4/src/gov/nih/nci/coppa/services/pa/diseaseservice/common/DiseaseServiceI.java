package gov.nih.nci.coppa.services.pa.diseaseservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public interface DiseaseServiceI {

  public gov.nih.nci.coppa.services.pa.Disease get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.Disease create(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.Disease update(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.Disease[] search(gov.nih.nci.coppa.services.pa.Disease searchCriteria) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

}

