package gov.nih.nci.coppa.services.structuralroles.healthcareprovider.service.globus;


import java.rmi.RemoteException;

import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.jaas.JaasGssUtil;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.w3c.dom.Node;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize<methodName> method for each method on this grid service.
 * The method is responsibe for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * apon a successful authorization or will throw an exception apon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class HealthCareProviderAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://enterpriseservices.nci.nih.gov/structuralroles/HealthCareProvider";
	
	
	public HealthCareProviderAuthorization() {
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
	
	public static GlobusCredential getInvocationCredential() {
        org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
        Subject subject = (Subject) ctx.getProperty(Constants.INVOCATION_SUBJECT);
        GlobusGSSCredentialImpl credential = (GlobusGSSCredentialImpl) JaasGssUtil.getCredential(subject);
        return credential.getGlobusCredential();
    }
					
	public static void authorizeGetServiceSecurityMetadata() throws RemoteException {
		
		
	}
					
	public static void authorizeCreate() throws RemoteException {
		
		
	}
					
	public static void authorizeGetById() throws RemoteException {
		
		
	}
					
	public static void authorizeGetByIds() throws RemoteException {
		
		
	}
					
	public static void authorizeSearch() throws RemoteException {
		
		
	}
					
	public static void authorizeUpdate() throws RemoteException {
		
		
	}
					
	public static void authorizeUpdateStatus() throws RemoteException {
		
		
	}
					
	public static void authorizeValidate() throws RemoteException {
		
		
	}
	
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			try{
				authorizeGetServiceSecurityMetadata();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("create")){
			try{
				authorizeCreate();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("getById")){
			try{
				authorizeGetById();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("getByIds")){
			try{
				authorizeGetByIds();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("search")){
			try{
				authorizeSearch();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("update")){
			try{
				authorizeUpdate();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("updateStatus")){
			try{
				authorizeUpdateStatus();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("validate")){
			try{
				authorizeValidate();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
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

	}
	
	
}
