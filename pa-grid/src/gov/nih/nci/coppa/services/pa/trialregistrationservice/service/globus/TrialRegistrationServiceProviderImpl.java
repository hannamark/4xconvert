package gov.nih.nci.coppa.services.pa.trialregistrationservice.service.globus;

import gov.nih.nci.coppa.services.pa.trialregistrationservice.service.TrialRegistrationServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the PAServicesImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class TrialRegistrationServiceProviderImpl{
	
	TrialRegistrationServiceImpl impl;
	
	public TrialRegistrationServiceProviderImpl() throws RemoteException {
		impl = new TrialRegistrationServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolResponse createInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateInterventionalStudyProtocolResponse();
    boxedResult.setId(impl.createInterventionalStudyProtocol(params.getStudyProtocol().getInterventionalStudyProtocol(),params.getStudyOverallStatus().getStudyOverallStatus(),params.getStudyIndlde().getStudyIndlde(),params.getStudyResourcing().getStudyResourcing(),params.getDocument().getDocument(),params.getLeadOrganization().getOrganization(),params.getPrincipalInvestigator().getPerson(),params.getSponsorOrganization().getOrganization(),params.getLeadOrganizationSiteIdentifier().getStudySite(),params.getNctIdentifierSiteIdentifiers().getStudySite(),params.getStudyContact().getStudyContact(),params.getStudySiteContact().getStudySiteContact(),params.getSummaryForOrganization().getOrganization(),params.getSummaryForStudyResourcing().getStudyResourcing(),params.getResponsiblePartyContact().getId(),params.getStudyRegulatoryAuthority().getStudyRegulatoryAuthority()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendInterventionalStudyProtocolResponse amendInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.AmendInterventionalStudyProtocolResponse();
    boxedResult.setId(impl.amendInterventionalStudyProtocol(params.getStudyProtocol().getInterventionalStudyProtocol(),params.getStudyOverallStatus().getStudyOverallStatus(),params.getStudyIndlde().getStudyIndlde(),params.getStudyResourcing().getStudyResourcing(),params.getDocument().getDocument(),params.getLeadOrganization().getOrganization(),params.getPrincipalInvestigator().getPerson(),params.getSponsorOrganization().getOrganization(),params.getLeadOrganizationSiteIdentifier().getStudySite(),params.getNctIdentifierSiteIdentifiers().getStudySite(),params.getStudyContact().getStudyContact(),params.getStudySiteContact().getStudySiteContact(),params.getSummaryForOrganization().getOrganization(),params.getSummaryForStudyResourcing().getStudyResourcing(),params.getResponsiblePartyContact().getId(),params.getStudyRegulatoryAuthority().getStudyRegulatoryAuthority()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateInterventionalStudyProtocolResponse updateInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.UpdateInterventionalStudyProtocolResponse();
    impl.updateInterventionalStudyProtocol(params.getStudyProtocol().getInterventionalStudyProtocol(),params.getStudyOverallStatus().getStudyOverallStatus(),params.getStudyIdentifiers().getStudySite(),params.getStudyIndlde().getStudyIndlde(),params.getStudyResourcing().getStudyResourcing(),params.getDocument().getDocument(),params.getStudyContact().getStudyContact(),params.getStudySiteContact().getStudySiteContact(),params.getSummaryForOrganization().getOrganization(),params.getSummaryForStudyResourcing().getStudyResourcing(),params.getResponsiblePartyContact().getId(),params.getStudyRegulatoryAuthority().getStudyRegulatoryAuthority(),params.getCollaborators().getStudySite(),params.getStudySiteAccrualStatuses().getStudySiteAccrualStatus(),params.getStudySites().getStudySite());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateProprietaryInterventionalStudyProtocolResponse createProprietaryInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateProprietaryInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateProprietaryInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.trialregistrationservice.stubs.CreateProprietaryInterventionalStudyProtocolResponse();
    boxedResult.setId(impl.createProprietaryInterventionalStudyProtocol(params.getStudyProtocol().getInterventionalStudyProtocol(),params.getStudySiteAccrualStatus().getStudySiteAccrualStatus(),params.getDocuments().getDocument(),params.getLeadOrganization().getOrganization(),params.getStudySiteInvestigator().getPerson(),params.getLeadOrganizationStudySite().getStudySite(),params.getStudySiteOrganization().getOrganization(),params.getStudySite().getStudySite(),params.getNctIdentifier().getStudySite(),params.getSummaryForOrganization().getOrganization(),params.getSummaryForStudyResourcing().getStudyResourcing()));
    return boxedResult;
  }

}
