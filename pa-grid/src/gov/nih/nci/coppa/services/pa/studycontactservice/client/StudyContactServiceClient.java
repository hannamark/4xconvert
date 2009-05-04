package gov.nih.nci.coppa.services.pa.studycontactservice.client;

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

import gov.nih.nci.coppa.services.pa.studycontactservice.stubs.StudyContactServicePortType;
import gov.nih.nci.coppa.services.pa.studycontactservice.stubs.service.StudyContactServiceAddressingLocator;
import gov.nih.nci.coppa.services.pa.studycontactservice.common.StudyContactServiceI;
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
public class StudyContactServiceClient extends StudyContactServiceClientBase implements StudyContactServiceI {	

	public StudyContactServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyContactServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudyContactServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyContactServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudyContactServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyContactServiceClient client = new StudyContactServiceClient(args[1]);
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

  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocolAndRole(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocolAndRole");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleResponse boxedResult = portType.getByStudyProtocolAndRole(params);
    return boxedResult.getStudyContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocolAndRoles(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyContact[] studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocolAndRoles");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesResponse boxedResult = portType.getByStudyProtocolAndRoles(params);
    return boxedResult.getStudyContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudyContact();
    }
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetCurrentByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetCurrentByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetCurrentByStudyProtocolResponse boxedResult = portType.getCurrentByStudyProtocol(params);
    return boxedResult.getStudyContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact create(gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact update(gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateRequestStudyContact studyContactContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateRequestStudyContact();
    studyContactContainer.setStudyContact(studyContact);
    params.setStudyContact(studyContactContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyContact();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
