package gov.nih.nci.coppa.services.service;

import gov.nih.nci.cagrid.introduce.servicetools.ServiceConfiguration;

import java.io.File;

import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;
import org.globus.wsrf.config.ContainerConfig;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 * 
 * This class holds all service properties which were defined for the service to have
 * access to.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class CoreServicesConfiguration implements ServiceConfiguration {

	public static CoreServicesConfiguration  configuration = null;

	public static CoreServicesConfiguration getConfiguration() throws Exception {
		if (CoreServicesConfiguration.configuration != null) {
			return CoreServicesConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			CoreServicesConfiguration.configuration = (CoreServicesConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return CoreServicesConfiguration.configuration;
	}
	
	private String etcDirectoryPath;
	
	
	
	public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}

	
}
