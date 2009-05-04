package gov.nih.nci.coppa.services.structuralroles.researchorganization.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public interface ResearchOrganizationI {

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.ResearchOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault ;

  public gov.nih.nci.coppa.po.ResearchOrganization[] search(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault ;

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException ;

  public void update(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault ;

  public gov.nih.nci.coppa.po.ResearchOrganization[] query(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault ;

}

