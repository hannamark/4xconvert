package gov.nih.nci.coppa.services.structuralroles.identifiedperson.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface IdentifiedPersonI {

  public gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException ;

  public gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson getById(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.Id id) throws RemoteException ;

  public gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson[] getByIds(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.Id[] id) throws RemoteException ;

  public gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson[] search(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException ;

  public void update(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException ;

  public void updateStatus(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException ;

  public gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.gov.nih.nci.coppa.po.IdentifiedPerson identifiedPerson) throws RemoteException ;

}

