package gov.nih.nci.coppa.services.pa.armservice.service;

import gov.nih.nci.coppa.services.pa.armservice.service.globus.resource.ArmServiceResource;
import  gov.nih.nci.coppa.services.pa.service.PAServicesConfiguration;

import java.rmi.RemoteException;

import javax.naming.InitialContext;
import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * Provides some simple accessors for the Impl.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public abstract class ArmServiceImplBase {
	
	public ArmServiceImplBase() throws RemoteException {
	
	}
	
	public PAServicesConfiguration getConfiguration() throws Exception {
		return PAServicesConfiguration.getConfiguration();
	}
	
	
	public gov.nih.nci.coppa.services.pa.armservice.service.globus.resource.ArmServiceResourceHome getResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("home");
		return (gov.nih.nci.coppa.services.pa.armservice.service.globus.resource.ArmServiceResourceHome)resource;
	}

	
	
	
	public gov.nih.nci.coppa.services.pa.service.globus.resource.PAServicesResourceHome getPAServicesResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("pAServicesHome");
		return (gov.nih.nci.coppa.services.pa.service.globus.resource.PAServicesResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyprotocolservice.service.globus.resource.StudyProtocolServiceResourceHome getStudyProtocolServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyProtocolServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyprotocolservice.service.globus.resource.StudyProtocolServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.service.globus.resource.StudySiteAccrualStatusServiceResourceHome getStudySiteAccrualStatusServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studySiteAccrualStatusServiceHome");
		return (gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.service.globus.resource.StudySiteAccrualStatusServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyresourcingservice.service.globus.resource.StudyResourcingServiceResourceHome getStudyResourcingServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyResourcingServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyresourcingservice.service.globus.resource.StudyResourcingServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyregulatoryauthorityservice.service.globus.resource.StudyRegulatoryAuthorityServiceResourceHome getStudyRegulatoryAuthorityServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyRegulatoryAuthorityServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyregulatoryauthorityservice.service.globus.resource.StudyRegulatoryAuthorityServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.service.globus.resource.StudyRecruitmentStatusServiceResourceHome getStudyRecruitmentStatusServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyRecruitmentStatusServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.service.globus.resource.StudyRecruitmentStatusServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyparticipationservice.service.globus.resource.StudyParticipationServiceResourceHome getStudyParticipationServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyParticipationServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyparticipationservice.service.globus.resource.StudyParticipationServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.service.globus.resource.StudyParticipationContactServiceResourceHome getStudyParticipationContactServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyParticipationContactServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.service.globus.resource.StudyParticipationContactServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.service.globus.resource.StudyOverallStatusServiceResourceHome getStudyOverallStatusServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyOverallStatusServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyoverallstatusservice.service.globus.resource.StudyOverallStatusServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyoutcomemeasureservice.service.globus.resource.StudyOutcomeMeasureServiceResourceHome getStudyOutcomeMeasureServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyOutcomeMeasureServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyoutcomemeasureservice.service.globus.resource.StudyOutcomeMeasureServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyonholdservice.service.globus.resource.StudyOnholdServiceResourceHome getStudyOnholdServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyOnholdServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyonholdservice.service.globus.resource.StudyOnholdServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyindideservice.service.globus.resource.StudyIndIdeServiceResourceHome getStudyIndIdeServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyIndIdeServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyindideservice.service.globus.resource.StudyIndIdeServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studydiseaseservice.service.globus.resource.StudyDiseaseServiceResourceHome getStudyDiseaseServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyDiseaseServiceHome");
		return (gov.nih.nci.coppa.services.pa.studydiseaseservice.service.globus.resource.StudyDiseaseServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studycontactservice.service.globus.resource.StudyContactServiceResourceHome getStudyContactServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyContactServiceHome");
		return (gov.nih.nci.coppa.services.pa.studycontactservice.service.globus.resource.StudyContactServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.plannedactivityservice.service.globus.resource.PlannedActivityServiceResourceHome getPlannedActivityServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("plannedActivityServiceHome");
		return (gov.nih.nci.coppa.services.pa.plannedactivityservice.service.globus.resource.PlannedActivityServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.service.globus.resource.DocumentWorkflowStatusServiceResourceHome getDocumentWorkflowStatusServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("documentWorkflowStatusServiceHome");
		return (gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.service.globus.resource.DocumentWorkflowStatusServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.documentservice.service.globus.resource.DocumentServiceResourceHome getDocumentServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("documentServiceHome");
		return (gov.nih.nci.coppa.services.pa.documentservice.service.globus.resource.DocumentServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studyrelationshipservice.service.globus.resource.StudyRelationshipServiceResourceHome getStudyRelationshipServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studyRelationshipServiceHome");
		return (gov.nih.nci.coppa.services.pa.studyrelationshipservice.service.globus.resource.StudyRelationshipServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studysiteservice.service.globus.resource.StudySiteServiceResourceHome getStudySiteServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studySiteServiceHome");
		return (gov.nih.nci.coppa.services.pa.studysiteservice.service.globus.resource.StudySiteServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service.globus.resource.StudySiteParticipationServiceResourceHome getStudySiteParticipationServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studySiteParticipationServiceHome");
		return (gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service.globus.resource.StudySiteParticipationServiceResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.pa.studysitecontactservice.service.globus.resource.StudySiteContactServiceResourceHome getStudySiteContactServiceResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("studySiteContactServiceHome");
		return (gov.nih.nci.coppa.services.pa.studysitecontactservice.service.globus.resource.StudySiteContactServiceResourceHome)resource;
	}
	
	
	protected ResourceHome getResourceHome(String resourceKey) throws Exception {
		MessageContext ctx = MessageContext.getCurrentContext();

		ResourceHome resourceHome = null;
		
		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + resourceKey;
		try {
			javax.naming.Context initialContext = new InitialContext();
			resourceHome = (ResourceHome) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate resource home. : " + resourceKey, e);
		}

		return resourceHome;
	}


}

