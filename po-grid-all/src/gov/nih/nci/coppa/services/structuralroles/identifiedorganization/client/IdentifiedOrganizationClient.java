package gov.nih.nci.coppa.services.structuralroles.identifiedorganization.client;

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

import gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.IdentifiedOrganizationPortType;
import gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.service.IdentifiedOrganizationServiceAddressingLocator;
import gov.nih.nci.coppa.services.structuralroles.identifiedorganization.common.IdentifiedOrganizationI;
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
 * @created by Introduce Toolkit version 1.2
 */
public class IdentifiedOrganizationClient extends IdentifiedOrganizationClientBase implements IdentifiedOrganizationI {	

	public IdentifiedOrganizationClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public IdentifiedOrganizationClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public IdentifiedOrganizationClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public IdentifiedOrganizationClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(IdentifiedOrganizationClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  IdentifiedOrganizationClient client = new IdentifiedOrganizationClient(args[1]);
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

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.CreateRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.CreateRequestIdentifiedOrganization();
    identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
    params.setIdentifiedOrganization(identifiedOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getIdentifiedOrganization();
    }
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getIdentifiedOrganization();
    }
  }

  public gov.nih.nci.coppa.po.IdentifiedOrganization[] search(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.SearchRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.SearchRequestIdentifiedOrganization();
    identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
    params.setIdentifiedOrganization(identifiedOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getIdentifiedOrganization();
    }
  }

  public void update(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequestIdentifiedOrganization();
    identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
    params.setIdentifiedOrganization(identifiedOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.ValidateRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.ValidateRequestIdentifiedOrganization();
    identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
    params.setIdentifiedOrganization(identifiedOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

}
