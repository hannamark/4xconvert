package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.common;

import java.rmi.RemoteException;

/** 
 * This class is autogenerated, DO NOT EDIT.
 * 
 * This interface represents the API which is accessable on the grid service from the client. 
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public interface StudySiteParticipationServiceI {

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite[] getParticipatingSitesByStudyProtocol(gov.nih.nci.iso21090.extensions.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite createParticipatingSite(gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

  public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.view.StudySite updateParticipatingSite(gov.nih.nci.iso21090.extensions.Id studySiteId,gov.nih.nci.coppa.services.pa.studysiteparticipationservice.types.management.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault ;

}

