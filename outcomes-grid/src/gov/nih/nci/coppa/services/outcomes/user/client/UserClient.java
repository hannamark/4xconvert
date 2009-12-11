package gov.nih.nci.coppa.services.outcomes.user.client;

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

import gov.nih.nci.coppa.services.outcomes.user.stubs.UserPortType;
import gov.nih.nci.coppa.services.outcomes.user.stubs.service.UserServiceAddressingLocator;
import gov.nih.nci.coppa.services.outcomes.user.common.UserI;
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
public class UserClient extends UserClientBase implements UserI {	

	public UserClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public UserClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public UserClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public UserClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(UserClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  UserClient client = new UserClient(args[1]);
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

  public gov.nih.nci.coppa.services.outcomes.User getUser(gov.nih.nci.coppa.services.outcomes.ST loginName) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getUser");
    gov.nih.nci.coppa.services.outcomes.user.stubs.GetUserRequest params = new gov.nih.nci.coppa.services.outcomes.user.stubs.GetUserRequest();
    gov.nih.nci.coppa.services.outcomes.user.stubs.GetUserRequestLoginName loginNameContainer = new gov.nih.nci.coppa.services.outcomes.user.stubs.GetUserRequestLoginName();
    loginNameContainer.setST(loginName);
    params.setLoginName(loginNameContainer);
    gov.nih.nci.coppa.services.outcomes.user.stubs.GetUserResponse boxedResult = portType.getUser(params);
    return boxedResult.getUser();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.User createUser(gov.nih.nci.coppa.services.outcomes.User user) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createUser");
    gov.nih.nci.coppa.services.outcomes.user.stubs.CreateUserRequest params = new gov.nih.nci.coppa.services.outcomes.user.stubs.CreateUserRequest();
    gov.nih.nci.coppa.services.outcomes.user.stubs.CreateUserRequestUser userContainer = new gov.nih.nci.coppa.services.outcomes.user.stubs.CreateUserRequestUser();
    userContainer.setUser(user);
    params.setUser(userContainer);
    gov.nih.nci.coppa.services.outcomes.user.stubs.CreateUserResponse boxedResult = portType.createUser(params);
    return boxedResult.getUser();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.User updateUser(gov.nih.nci.coppa.services.outcomes.User user) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateUser");
    gov.nih.nci.coppa.services.outcomes.user.stubs.UpdateUserRequest params = new gov.nih.nci.coppa.services.outcomes.user.stubs.UpdateUserRequest();
    gov.nih.nci.coppa.services.outcomes.user.stubs.UpdateUserRequestUser userContainer = new gov.nih.nci.coppa.services.outcomes.user.stubs.UpdateUserRequestUser();
    userContainer.setUser(user);
    params.setUser(userContainer);
    gov.nih.nci.coppa.services.outcomes.user.stubs.UpdateUserResponse boxedResult = portType.updateUser(params);
    return boxedResult.getUser();
    }
  }

}
