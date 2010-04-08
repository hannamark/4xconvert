package gov.nih.nci.coppa.services.pa.trialregistrationservice.service.globus;


import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import gov.nih.nci.cagrid.introduce.servicetools.security.AuthorizationExtension;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.globus.wsrf.config.ContainerConfig;
import org.w3c.dom.Node;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize method for each method on this grid service.
 * The method is responsibe for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * apon a successful authorization or will throw an exception apon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class TrialRegistrationServiceAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://enterpriseservices.nci.nih.gov/PAServices/TrialRegistrationService";
	
	Map authorizationClassMap = new HashMap();
	
	
	public TrialRegistrationServiceAuthorization() {
	}
	
	protected String getServiceNamespace(){
		return SERVICE_NAMESPACE;
	}
	
	public static String getCallerIdentity() {
		String caller = org.globus.wsrf.security.SecurityManager.getManager().getCaller();
		if ((caller == null) || (caller.equals("<anonymous>"))) {
			return null;
		} else {
			return caller;
		}
	}
					
	public void authorizeCreateInterventionalStudyProtocol(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
		// authorization using service authorization from the enforce_auth extension
		((AuthorizationExtension)authorizationClassMap.get("enforce_auth")).authorizeService(peerSubject,context,operation);
		 	  
	}
	   				
	public void authorizeAmendInterventionalStudyProtocol(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
		// authorization using service authorization from the enforce_auth extension
		((AuthorizationExtension)authorizationClassMap.get("enforce_auth")).authorizeService(peerSubject,context,operation);
		 	  
	}
	   				
	public void authorizeUpdateInterventionalStudyProtocol(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
		// authorization using service authorization from the enforce_auth extension
		((AuthorizationExtension)authorizationClassMap.get("enforce_auth")).authorizeService(peerSubject,context,operation);
		 	  
	}
	   				
	public void authorizeGetServiceSecurityMetadata(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
	}
	   				
	public void authorizeCreateProprietaryInterventionalStudyProtocol(Subject peerSubject, MessageContext context, QName operation) throws AuthorizationException {
		
		// authorization using service authorization from the enforce_auth extension
		((AuthorizationExtension)authorizationClassMap.get("enforce_auth")).authorizeService(peerSubject,context,operation);
		 	  
	}
	   
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("createInterventionalStudyProtocol")){
			authorizeCreateInterventionalStudyProtocol(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("amendInterventionalStudyProtocol")){
			authorizeAmendInterventionalStudyProtocol(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("updateInterventionalStudyProtocol")){
			authorizeUpdateInterventionalStudyProtocol(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			authorizeGetServiceSecurityMetadata(peerSubject, context, operation);
			return true;
		} else if(operation.getLocalPart().equals("createProprietaryInterventionalStudyProtocol")){
			authorizeCreateProprietaryInterventionalStudyProtocol(peerSubject, context, operation);
			return true;
		} 		
		return false;
	}
	

	public Node getPolicy(Node query) throws InvalidPolicyException {
		return null;
	}


	public String[] getPolicyNames() {
		return null;
	}


	public Node setPolicy(Node policy) throws InvalidPolicyException {
		return null;
	}


	public void close() throws CloseException {


	}


	public void initialize(PDPConfig config, String name, String id) throws InitializeException {
    	try{
    		String serviceName = (String)config.getProperty(name, "serviceName");
    	    String etcPath = ContainerConfig.getBaseDirectory() + File.separator + (String)config.getProperty(name, "etcDirectoryPath");

    	 
	   		authorizationClassMap.put("enforce_auth",Class.forName("org.cagrid.enforce.authorization.extension.service.EnforceAuthorization").newInstance());
			((AuthorizationExtension)authorizationClassMap.get("enforce_auth")).initialize(serviceName, etcPath);
			
    	} catch (Exception e){
        	throw new InitializeException(e.getMessage(),e);
		}
	}
	
	
}
