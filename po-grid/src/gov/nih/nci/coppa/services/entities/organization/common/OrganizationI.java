package gov.nih.nci.coppa.services.entities.organization.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface OrganizationI {

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public void update(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.Organization[] search(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault ;

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Organization organization) throws RemoteException ;

  public gov.nih.nci.coppa.po.Organization getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault ;

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.Organization[] query(gov.nih.nci.coppa.po.Organization organization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault ;

}

