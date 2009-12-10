package gov.nih.nci.coppa.services.outcomes.performedactivity.service;

import gov.nih.nci.coppa.services.outcomes.performedactivity.service.globus.resource.PerformedActivityResource;
import  gov.nih.nci.coppa.services.outcomes.service.OutcomesServicesConfiguration;

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
public abstract class PerformedActivityImplBase {
	
	public PerformedActivityImplBase() throws RemoteException {
	
	}
	
	public OutcomesServicesConfiguration getConfiguration() throws Exception {
		return OutcomesServicesConfiguration.getConfiguration();
	}
	
	
	public gov.nih.nci.coppa.services.outcomes.performedactivity.service.globus.resource.PerformedActivityResourceHome getResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("home");
		return (gov.nih.nci.coppa.services.outcomes.performedactivity.service.globus.resource.PerformedActivityResourceHome)resource;
	}

	
	
	
	public gov.nih.nci.coppa.services.outcomes.service.globus.resource.OutcomesServicesResourceHome getOutcomesServicesResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("outcomesServicesHome");
		return (gov.nih.nci.coppa.services.outcomes.service.globus.resource.OutcomesServicesResourceHome)resource;
	}
	
	public gov.nih.nci.coppa.services.outcomes.activityrelationship.service.globus.resource.ActivityRelationshipResourceHome getActivityRelationshipResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("activityRelationshipHome");
		return (gov.nih.nci.coppa.services.outcomes.activityrelationship.service.globus.resource.ActivityRelationshipResourceHome)resource;
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

