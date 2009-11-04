package gov.nih.nci.coppa.services.service;

import gov.nih.nci.coppa.services.service.globus.resource.CoreServicesResource;
import  gov.nih.nci.coppa.services.service.CoreServicesConfiguration;

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
public abstract class CoreServicesImplBase {
	
	public CoreServicesImplBase() throws RemoteException {
	
	}
	
	public CoreServicesConfiguration getConfiguration() throws Exception {
		return CoreServicesConfiguration.getConfiguration();
	}
	
	
	public gov.nih.nci.coppa.services.service.globus.resource.CoreServicesResourceHome getResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("home");
		return (gov.nih.nci.coppa.services.service.globus.resource.CoreServicesResourceHome)resource;
	}

	
	
	
	public gov.nih.nci.coppa.services.entities.person.service.globus.resource.PersonResourceHome getPersonResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("personHome");
		return (gov.nih.nci.coppa.services.entities.person.service.globus.resource.PersonResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.entities.organization.service.globus.resource.OrganizationResourceHome getOrganizationResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("organizationHome");
		return (gov.nih.nci.coppa.services.entities.organization.service.globus.resource.OrganizationResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service.globus.resource.HealthCareFacilityResourceHome getHealthCareFacilityResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("healthCareFacilityHome");
		return (gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service.globus.resource.HealthCareFacilityResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.service.globus.resource.ClinicalResearchStaffResourceHome getClinicalResearchStaffResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("clinicalResearchStaffHome");
		return (gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.service.globus.resource.ClinicalResearchStaffResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.healthcareprovider.service.globus.resource.HealthCareProviderResourceHome getHealthCareProviderResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("healthCareProviderHome");
		return (gov.nih.nci.coppa.services.structuralroles.healthcareprovider.service.globus.resource.HealthCareProviderResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.identifiedorganization.service.globus.resource.IdentifiedOrganizationResourceHome getIdentifiedOrganizationResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("identifiedOrganizationHome");
		return (gov.nih.nci.coppa.services.structuralroles.identifiedorganization.service.globus.resource.IdentifiedOrganizationResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.identifiedperson.service.globus.resource.IdentifiedPersonResourceHome getIdentifiedPersonResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("identifiedPersonHome");
		return (gov.nih.nci.coppa.services.structuralroles.identifiedperson.service.globus.resource.IdentifiedPersonResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service.globus.resource.OrganizationalContactResourceHome getOrganizationalContactResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("organizationalContactHome");
		return (gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service.globus.resource.OrganizationalContactResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.oversightcommittee.service.globus.resource.OversightCommitteeResourceHome getOversightCommitteeResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("oversightCommitteeHome");
		return (gov.nih.nci.coppa.services.structuralroles.oversightcommittee.service.globus.resource.OversightCommitteeResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.researchorganization.service.globus.resource.ResearchOrganizationResourceHome getResearchOrganizationResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("researchOrganizationHome");
		return (gov.nih.nci.coppa.services.structuralroles.researchorganization.service.globus.resource.ResearchOrganizationResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.structuralroles.patient.service.globus.resource.PatientResourceHome getPatientResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("patientHome");
		return (gov.nih.nci.coppa.services.structuralroles.patient.service.globus.resource.PatientResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.business.business.service.globus.resource.BusinessResourceHome getBusinessResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("businessHome");
		return (gov.nih.nci.coppa.services.business.business.service.globus.resource.BusinessResourceHome)resource;
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

