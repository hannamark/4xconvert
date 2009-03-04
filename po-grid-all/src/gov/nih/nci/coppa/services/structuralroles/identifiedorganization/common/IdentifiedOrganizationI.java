package gov.nih.nci.coppa.services.structuralroles.identifiedorganization.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface IdentifiedOrganizationI {

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException ;

  public gov.nih.nci.coppa.po.IdentifiedOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException ;

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException ;

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] search(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException ;

  public void update(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException ;

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException ;

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException ;

}

