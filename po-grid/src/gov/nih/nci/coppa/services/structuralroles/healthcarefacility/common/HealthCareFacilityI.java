package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface HealthCareFacilityI {

  public gov.nih.nci.coppa.po.HealthCareFacility getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.coppa.po.HealthCareFacility[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException ;

  public void update(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.HealthCareFacility[] query(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault ;

  public gov.nih.nci.coppa.po.HealthCareFacility[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException ;

}

