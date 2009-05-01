package gov.nih.nci.coppa.services.pa.studyrelationshipservice.client;

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

import gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.StudyRelationshipServicePortType;
import gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.service.StudyRelationshipServiceAddressingLocator;
import gov.nih.nci.coppa.services.pa.studyrelationshipservice.common.StudyRelationshipServiceI;
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
public class StudyRelationshipServiceClient extends StudyRelationshipServiceClientBase implements StudyRelationshipServiceI {	

	public StudyRelationshipServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public StudyRelationshipServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public StudyRelationshipServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public StudyRelationshipServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudyRelationshipServiceClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudyRelationshipServiceClient client = new StudyRelationshipServiceClient(args[1]);
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

  public gov.nih.nci.coppa.services.pa.StudyRelationship get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyRelationship();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRelationship[] search(gov.nih.nci.coppa.services.pa.StudyRelationship studyRelationship) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchRequest params = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchRequest();
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchRequestStudyRelationship studyRelationshipContainer = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchRequestStudyRelationship();
    studyRelationshipContainer.setStudyRelationship(studyRelationship);
    params.setStudyRelationship(studyRelationshipContainer);
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getStudyRelationship();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRelationship create(gov.nih.nci.coppa.services.pa.StudyRelationship studyRelationship) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateRequestStudyRelationship studyRelationshipContainer = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateRequestStudyRelationship();
    studyRelationshipContainer.setStudyRelationship(studyRelationship);
    params.setStudyRelationship(studyRelationshipContainer);
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyRelationship();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRelationship update(gov.nih.nci.coppa.services.pa.StudyRelationship studyRelationship) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateRequestStudyRelationship studyRelationshipContainer = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateRequestStudyRelationship();
    studyRelationshipContainer.setStudyRelationship(studyRelationship);
    params.setStudyRelationship(studyRelationshipContainer);
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyRelationship();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
