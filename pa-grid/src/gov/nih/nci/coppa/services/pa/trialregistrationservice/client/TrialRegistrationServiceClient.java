package gov.nih.nci.coppa.services.pa.trialregistrationservice.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.TrialRegistrationServicePortType;
import gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.service.TrialRegistrationServiceAddressingLocator;
import gov.nih.nci.coppa.services.pa.trialregistrationservice.common.TrialRegistrationServiceI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.3
 */
public class TrialRegistrationServiceClient extends TrialRegistrationServiceClientBase implements TrialRegistrationServiceI {	

	public TrialRegistrationServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public TrialRegistrationServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public TrialRegistrationServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public TrialRegistrationServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(TrialRegistrationServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  TrialRegistrationServiceClient client = new TrialRegistrationServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

  public gov.nih.nci.coppa.services.pa.Id createInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite nctIdentifierSiteIdentifier,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createInterventionalStudyProtocol");
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyProtocol studyProtocolContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyProtocol();
    studyProtocolContainer.setStudyProtocol(studyProtocol);
    params.setStudyProtocol(studyProtocolContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyOverallStatus studyOverallStatusContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyOverallStatus();
    studyOverallStatusContainer.setStudyOverallStatus(studyOverallStatus);
    params.setStudyOverallStatus(studyOverallStatusContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyIndlde studyIndldeContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyIndlde();
    studyIndldeContainer.setStudyIndlde(studyIndlde);
    params.setStudyIndlde(studyIndldeContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyResourcing studyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyResourcing();
    studyResourcingContainer.setStudyResourcing(studyResourcing);
    params.setStudyResourcing(studyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestDocument documentContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestDocument();
    documentContainer.setDocument(document);
    params.setDocument(documentContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestLeadOrganization leadOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestLeadOrganization();
    leadOrganizationContainer.setOrganization(leadOrganization);
    params.setLeadOrganization(leadOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestPrincipalInvestigator principalInvestigatorContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestPrincipalInvestigator();
    principalInvestigatorContainer.setPerson(principalInvestigator);
    params.setPrincipalInvestigator(principalInvestigatorContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSponsorOrganization sponsorOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSponsorOrganization();
    sponsorOrganizationContainer.setOrganization(sponsorOrganization);
    params.setSponsorOrganization(sponsorOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestLeadOrganizationSiteIdentifier leadOrganizationSiteIdentifierContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestLeadOrganizationSiteIdentifier();
    leadOrganizationSiteIdentifierContainer.setStudySite(leadOrganizationSiteIdentifier);
    params.setLeadOrganizationSiteIdentifier(leadOrganizationSiteIdentifierContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestNctIdentifierSiteIdentifier nctIdentifierSiteIdentifierContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestNctIdentifierSiteIdentifier();
    nctIdentifierSiteIdentifierContainer.setStudySite(nctIdentifierSiteIdentifier);
    params.setNctIdentifierSiteIdentifier(nctIdentifierSiteIdentifierContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudySiteContact studySiteContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestStudySiteContact();
    studySiteContactContainer.setStudySiteContact(studySiteContact);
    params.setStudySiteContact(studySiteContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSummaryForOrganization summaryForOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSummaryForOrganization();
    summaryForOrganizationContainer.setOrganization(summaryForOrganization);
    params.setSummaryForOrganization(summaryForOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSummaryForStudyResourcing summaryForStudyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestSummaryForStudyResourcing();
    summaryForStudyResourcingContainer.setStudyResourcing(summaryForStudyResourcing);
    params.setSummaryForStudyResourcing(summaryForStudyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestResponsiblePartyContact responsiblePartyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequestResponsiblePartyContact();
    responsiblePartyContactContainer.setId(responsiblePartyContact);
    params.setResponsiblePartyContact(responsiblePartyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolResponse boxedResult = portType.createInterventionalStudyProtocol(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.services.pa.Id amend(gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudySite leadOrganizationSiteIdentifier,gov.nih.nci.coppa.services.pa.StudySite nctIdentifierSiteIdentifier,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"amend");
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequest params = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequest();
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyProtocol studyProtocolContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyProtocol();
    studyProtocolContainer.setStudyProtocol(studyProtocol);
    params.setStudyProtocol(studyProtocolContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyOverallStatus studyOverallStatusContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyOverallStatus();
    studyOverallStatusContainer.setStudyOverallStatus(studyOverallStatus);
    params.setStudyOverallStatus(studyOverallStatusContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyIndlde studyIndldeContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyIndlde();
    studyIndldeContainer.setStudyIndlde(studyIndlde);
    params.setStudyIndlde(studyIndldeContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyResourcing studyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyResourcing();
    studyResourcingContainer.setStudyResourcing(studyResourcing);
    params.setStudyResourcing(studyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestDocument documentContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestDocument();
    documentContainer.setDocument(document);
    params.setDocument(documentContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestLeadOrganization leadOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestLeadOrganization();
    leadOrganizationContainer.setOrganization(leadOrganization);
    params.setLeadOrganization(leadOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestPrincipalInvestigator principalInvestigatorContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestPrincipalInvestigator();
    principalInvestigatorContainer.setPerson(principalInvestigator);
    params.setPrincipalInvestigator(principalInvestigatorContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSponsorOrganization sponsorOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSponsorOrganization();
    sponsorOrganizationContainer.setOrganization(sponsorOrganization);
    params.setSponsorOrganization(sponsorOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestLeadOrganizationSiteIdentifier leadOrganizationSiteIdentifierContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestLeadOrganizationSiteIdentifier();
    leadOrganizationSiteIdentifierContainer.setStudySite(leadOrganizationSiteIdentifier);
    params.setLeadOrganizationSiteIdentifier(leadOrganizationSiteIdentifierContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestNctIdentifierSiteIdentifier nctIdentifierSiteIdentifierContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestNctIdentifierSiteIdentifier();
    nctIdentifierSiteIdentifierContainer.setStudySite(nctIdentifierSiteIdentifier);
    params.setNctIdentifierSiteIdentifier(nctIdentifierSiteIdentifierContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudySiteContact studySiteContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestStudySiteContact();
    studySiteContactContainer.setStudySiteContact(studySiteContact);
    params.setStudySiteContact(studySiteContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSummaryForOrganization summaryForOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSummaryForOrganization();
    summaryForOrganizationContainer.setOrganization(summaryForOrganization);
    params.setSummaryForOrganization(summaryForOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSummaryForStudyResourcing summaryForStudyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestSummaryForStudyResourcing();
    summaryForStudyResourcingContainer.setStudyResourcing(summaryForStudyResourcing);
    params.setSummaryForStudyResourcing(summaryForStudyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestResponsiblePartyContact responsiblePartyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendRequestResponsiblePartyContact();
    responsiblePartyContactContainer.setId(responsiblePartyContact);
    params.setResponsiblePartyContact(responsiblePartyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendResponse boxedResult = portType.amend(params);
    return boxedResult.getId();
    }
  }

  public void update(gov.nih.nci.coppa.services.pa.StudyProtocol studyProtocol,gov.nih.nci.coppa.services.pa.StudyOverallStatus studyOverallStatus,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudyIndlde[] studyIndlde,gov.nih.nci.coppa.services.pa.StudyResourcing[] studyResourcing,gov.nih.nci.coppa.services.pa.Document[] document,gov.nih.nci.coppa.po.Organization leadOrganization,gov.nih.nci.coppa.po.Person principalInvestigator,gov.nih.nci.coppa.po.Organization sponsorOrganization,gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.services.pa.StudySiteContact studySiteContact,gov.nih.nci.coppa.po.Organization summaryForOrganization,gov.nih.nci.coppa.services.pa.StudyResourcing summaryForStudyResourcing,gov.nih.nci.coppa.services.pa.Id responsiblePartyContact,gov.nih.nci.coppa.services.pa.StudyRegulatoryAuthority studyRegulatoryAuthority,gov.nih.nci.coppa.services.pa.StudySite[] collaborators,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] participatingSites,gov.nih.nci.coppa.services.pa.StudySite[] pgCdUpdatedList) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyProtocol studyProtocolContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyProtocol();
    studyProtocolContainer.setStudyProtocol(studyProtocol);
    params.setStudyProtocol(studyProtocolContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyOverallStatus studyOverallStatusContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyOverallStatus();
    studyOverallStatusContainer.setStudyOverallStatus(studyOverallStatus);
    params.setStudyOverallStatus(studyOverallStatusContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudySite studySiteContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudySite();
    studySiteContainer.setStudySite(studySite);
    params.setStudySite(studySiteContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyIndlde studyIndldeContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyIndlde();
    studyIndldeContainer.setStudyIndlde(studyIndlde);
    params.setStudyIndlde(studyIndldeContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyResourcing studyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyResourcing();
    studyResourcingContainer.setStudyResourcing(studyResourcing);
    params.setStudyResourcing(studyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestDocument documentContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestDocument();
    documentContainer.setDocument(document);
    params.setDocument(documentContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestLeadOrganization leadOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestLeadOrganization();
    leadOrganizationContainer.setOrganization(leadOrganization);
    params.setLeadOrganization(leadOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestPrincipalInvestigator principalInvestigatorContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestPrincipalInvestigator();
    principalInvestigatorContainer.setPerson(principalInvestigator);
    params.setPrincipalInvestigator(principalInvestigatorContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSponsorOrganization sponsorOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSponsorOrganization();
    sponsorOrganizationContainer.setOrganization(sponsorOrganization);
    params.setSponsorOrganization(sponsorOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudySiteContact studySiteContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudySiteContact();
    studySiteContactContainer.setStudySiteContact(studySiteContact);
    params.setStudySiteContact(studySiteContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSummaryForOrganization summaryForOrganizationContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSummaryForOrganization();
    summaryForOrganizationContainer.setOrganization(summaryForOrganization);
    params.setSummaryForOrganization(summaryForOrganizationContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSummaryForStudyResourcing summaryForStudyResourcingContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestSummaryForStudyResourcing();
    summaryForStudyResourcingContainer.setStudyResourcing(summaryForStudyResourcing);
    params.setSummaryForStudyResourcing(summaryForStudyResourcingContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestResponsiblePartyContact responsiblePartyContactContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestResponsiblePartyContact();
    responsiblePartyContactContainer.setId(responsiblePartyContact);
    params.setResponsiblePartyContact(responsiblePartyContactContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyRegulatoryAuthority studyRegulatoryAuthorityContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestStudyRegulatoryAuthority();
    studyRegulatoryAuthorityContainer.setStudyRegulatoryAuthority(studyRegulatoryAuthority);
    params.setStudyRegulatoryAuthority(studyRegulatoryAuthorityContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestCollaborators collaboratorsContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestCollaborators();
    collaboratorsContainer.setStudySite(collaborators);
    params.setCollaborators(collaboratorsContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestParticipatingSites participatingSitesContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestParticipatingSites();
    participatingSitesContainer.setStudySiteAccrualStatus(participatingSites);
    params.setParticipatingSites(participatingSitesContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestPgCdUpdatedList pgCdUpdatedListContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateRequestPgCdUpdatedList();
    pgCdUpdatedListContainer.setStudySite(pgCdUpdatedList);
    params.setPgCdUpdatedList(pgCdUpdatedListContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void reject(gov.nih.nci.coppa.services.pa.Id studyProtocol) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"reject");
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.RejectRequest params = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.RejectRequest();
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.RejectRequestStudyProtocol studyProtocolContainer = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.RejectRequestStudyProtocol();
    studyProtocolContainer.setId(studyProtocol);
    params.setStudyProtocol(studyProtocolContainer);
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.RejectResponse boxedResult = portType.reject(params);
    }
  }

}
