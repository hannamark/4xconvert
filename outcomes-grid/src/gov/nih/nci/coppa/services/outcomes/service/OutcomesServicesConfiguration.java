package gov.nih.nci.coppa.services.outcomes.service;

import gov.nih.nci.cagrid.introduce.servicetools.ServiceConfiguration;

import org.globus.wsrf.config.ContainerConfig;
import java.io.File;
import javax.naming.InitialContext;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 * 
 * This class holds all service properties which were defined for the service to have
 * access to.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class OutcomesServicesConfiguration implements ServiceConfiguration {

	public static OutcomesServicesConfiguration  configuration = null;
    public String etcDirectoryPath;
    	
	public static OutcomesServicesConfiguration getConfiguration() throws Exception {
		if (OutcomesServicesConfiguration.configuration != null) {
			return OutcomesServicesConfiguration.configuration;
		}
		MessageContext ctx = MessageContext.getCurrentContext();

		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/serviceconfiguration";
		try {
			javax.naming.Context initialContext = new InitialContext();
			OutcomesServicesConfiguration.configuration = (OutcomesServicesConfiguration) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate service configuration.", e);
		}

		return OutcomesServicesConfiguration.configuration;
	}
	

	
	private String gridServicePrincipalSeparator;
	
	
    public String getEtcDirectoryPath() {
		return ContainerConfig.getBaseDirectory() + File.separator + etcDirectoryPath;
	}
	
	public void setEtcDirectoryPath(String etcDirectoryPath) {
		this.etcDirectoryPath = etcDirectoryPath;
	}


	
	public String getGridServicePrincipalSeparator() {
		return gridServicePrincipalSeparator;
	}
	
	
	public void setGridServicePrincipalSeparator(String gridServicePrincipalSeparator) {
		this.gridServicePrincipalSeparator = gridServicePrincipalSeparator;
	}

	
}
