package gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.client;

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

import gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.StudyParticipationContactServicePortType;
import gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.service.StudyParticipationContactServiceAddressingLocator;
import gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.common.StudyParticipationContactServiceI;
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
public class StudyParticipationContactServiceClient extends StudyParticipationContactServiceClientBase implements StudyParticipationContactServiceI {	

	public StudyParticipationContactServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyParticipationContactServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudyParticipationContactServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyParticipationContactServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudyParticipationContactServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyParticipationContactServiceClient client = new StudyParticipationContactServiceClient(args[1]);
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

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyParticipation(gov.nih.nci.coppa.services.pa.Id studyParticipationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyParticipation");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyParticipationRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyParticipationRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyParticipationRequestStudyParticipationId studyParticipationIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyParticipationRequestStudyParticipationId();
    studyParticipationIdContainer.setId(studyParticipationId);
    params.setStudyParticipationId(studyParticipationIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyParticipationResponse boxedResult = portType.getByStudyParticipation(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocolAndRole(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocolAndRole");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyParticipationContact studyParticipationContactContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleRequestStudyParticipationContact();
    studyParticipationContactContainer.setStudyParticipationContact(studyParticipationContact);
    params.setStudyParticipationContact(studyParticipationContactContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRoleResponse boxedResult = portType.getByStudyProtocolAndRole(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocolAndRoles(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocolAndRoles");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyParticipationContact studyParticipationContactContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesRequestStudyParticipationContact();
    studyParticipationContactContainer.setStudyParticipationContact(studyParticipationContact);
    params.setStudyParticipationContact(studyParticipationContactContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolAndRolesResponse boxedResult = portType.getByStudyProtocolAndRoles(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetCurrentByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetCurrentByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetCurrentByStudyProtocolResponse boxedResult = portType.getCurrentByStudyProtocol(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact create(gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CreateRequestStudyParticipationContact studyParticipationContactContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CreateRequestStudyParticipationContact();
    studyParticipationContactContainer.setStudyParticipationContact(studyParticipationContact);
    params.setStudyParticipationContact(studyParticipationContactContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyParticipationContact update(gov.nih.nci.coppa.services.pa.StudyParticipationContact studyParticipationContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.UpdateRequestStudyParticipationContact studyParticipationContactContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.UpdateRequestStudyParticipationContact();
    studyParticipationContactContainer.setStudyParticipationContact(studyParticipationContact);
    params.setStudyParticipationContact(studyParticipationContactContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyParticipationContact();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyparticipationcontactservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
