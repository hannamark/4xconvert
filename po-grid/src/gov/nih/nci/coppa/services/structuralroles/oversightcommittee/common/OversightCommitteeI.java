package gov.nih.nci.coppa.services.structuralroles.oversightcommittee.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface OversightCommitteeI {

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.OversightCommittee getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.coppa.po.OversightCommittee[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.coppa.po.OversightCommittee[] search(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException ;

  public void update(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException ;

}

