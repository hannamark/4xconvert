package gov.nih.nci.coppa.services.outcomes.performedobservationresult.client;

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

import gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.PerformedObservationResultPortType;
import gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.service.PerformedObservationResultServiceAddressingLocator;
import gov.nih.nci.coppa.services.outcomes.performedobservationresult.common.PerformedObservationResultI;
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
public class PerformedObservationResultClient extends PerformedObservationResultClientBase implements PerformedObservationResultI {	

	public PerformedObservationResultClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public PerformedObservationResultClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public PerformedObservationResultClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public PerformedObservationResultClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(PerformedObservationResultClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  PerformedObservationResultClient client = new PerformedObservationResultClient(args[1]);
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

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getPerformedObservationResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult create(gov.nih.nci.coppa.services.outcomes.PerformedObservationResult performedObservationResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreateRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreateRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreateRequestPerformedObservationResult performedObservationResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreateRequestPerformedObservationResult();
    performedObservationResultContainer.setPerformedObservationResult(performedObservationResult);
    params.setPerformedObservationResult(performedObservationResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getPerformedObservationResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult update(gov.nih.nci.coppa.services.outcomes.PerformedObservationResult performedObservationResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdateRequestPerformedObservationResult performedObservationResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdateRequestPerformedObservationResult();
    performedObservationResultContainer.setPerformedObservationResult(performedObservationResult);
    params.setPerformedObservationResult(performedObservationResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getPerformedObservationResult();
    }
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult[] getPerformedObservationResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedObservationResultByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedObservationResultByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedObservationResultByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedObservationResultByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedObservationResultByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedObservationResultByPerformedActivityResponse boxedResult = portType.getPerformedObservationResultByPerformedActivity(params);
    return boxedResult.getPerformedObservationResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology[] getPerformedHistopathologyByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedHistopathologyByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyByPerformedActivityResponse boxedResult = portType.getPerformedHistopathologyByPerformedActivity(params);
    return boxedResult.getPerformedHistopathology();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology getPerformedHistopathology(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedHistopathology");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedHistopathologyResponse boxedResult = portType.getPerformedHistopathology(params);
    return boxedResult.getPerformedHistopathology();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology createPerformedHistopathology(gov.nih.nci.coppa.services.outcomes.PerformedHistopathology performedHistopathology) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedHistopathology");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedHistopathologyRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedHistopathologyRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedHistopathologyRequestPerformedHistopathology performedHistopathologyContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedHistopathologyRequestPerformedHistopathology();
    performedHistopathologyContainer.setPerformedHistopathology(performedHistopathology);
    params.setPerformedHistopathology(performedHistopathologyContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedHistopathologyResponse boxedResult = portType.createPerformedHistopathology(params);
    return boxedResult.getPerformedHistopathology();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology updatePerformedHistopathology(gov.nih.nci.coppa.services.outcomes.PerformedHistopathology performedHistopathology) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedHistopathology");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedHistopathologyRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedHistopathologyRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedHistopathologyRequestPerformedHistopathology performedHistopathologyContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedHistopathologyRequestPerformedHistopathology();
    performedHistopathologyContainer.setPerformedHistopathology(performedHistopathology);
    params.setPerformedHistopathology(performedHistopathologyContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedHistopathologyResponse boxedResult = portType.updatePerformedHistopathology(params);
    return boxedResult.getPerformedHistopathology();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis[] getPerformedDiagnosisByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedDiagnosisByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisByPerformedActivityResponse boxedResult = portType.getPerformedDiagnosisByPerformedActivity(params);
    return boxedResult.getPerformedDiagnosis();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis getPerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedDiagnosis");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedDiagnosisResponse boxedResult = portType.getPerformedDiagnosis(params);
    return boxedResult.getPerformedDiagnosis();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis createPerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis performedDiagnosis) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedDiagnosis");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedDiagnosisRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedDiagnosisRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedDiagnosisRequestPerformedDiagnosis performedDiagnosisContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedDiagnosisRequestPerformedDiagnosis();
    performedDiagnosisContainer.setPerformedDiagnosis(performedDiagnosis);
    params.setPerformedDiagnosis(performedDiagnosisContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedDiagnosisResponse boxedResult = portType.createPerformedDiagnosis(params);
    return boxedResult.getPerformedDiagnosis();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis updatePerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis performedDiagnosis) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedDiagnosis");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedDiagnosisRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedDiagnosisRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedDiagnosisRequestPerformedDiagnosis performedDiagnosisContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedDiagnosisRequestPerformedDiagnosis();
    performedDiagnosisContainer.setPerformedDiagnosis(performedDiagnosis);
    params.setPerformedDiagnosis(performedDiagnosisContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedDiagnosisResponse boxedResult = portType.updatePerformedDiagnosis(params);
    return boxedResult.getPerformedDiagnosis();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage[] getPerformedImageByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedImageByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageByPerformedActivityResponse boxedResult = portType.getPerformedImageByPerformedActivity(params);
    return boxedResult.getPerformedImage();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage getPerformedImage(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedImage");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedImageResponse boxedResult = portType.getPerformedImage(params);
    return boxedResult.getPerformedImage();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage createPerformedImage(gov.nih.nci.coppa.services.outcomes.PerformedImage performedImage) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedImage");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedImageRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedImageRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedImageRequestPerformedImage performedImageContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedImageRequestPerformedImage();
    performedImageContainer.setPerformedImage(performedImage);
    params.setPerformedImage(performedImageContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedImageResponse boxedResult = portType.createPerformedImage(params);
    return boxedResult.getPerformedImage();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage updatePerformedImage(gov.nih.nci.coppa.services.outcomes.PerformedImage performedImage) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedImage");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedImageRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedImageRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedImageRequestPerformedImage performedImageContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedImageRequestPerformedImage();
    performedImageContainer.setPerformedImage(performedImage);
    params.setPerformedImage(performedImageContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedImageResponse boxedResult = portType.updatePerformedImage(params);
    return boxedResult.getPerformedImage();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult[] getPerformedClinicalResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedClinicalResultByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultByPerformedActivityResponse boxedResult = portType.getPerformedClinicalResultByPerformedActivity(params);
    return boxedResult.getPerformedClinicalResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult getPerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedClinicalResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedClinicalResultResponse boxedResult = portType.getPerformedClinicalResult(params);
    return boxedResult.getPerformedClinicalResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult createPerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult performedClinicalResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedClinicalResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedClinicalResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedClinicalResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedClinicalResultRequestPerformedClinicalResult performedClinicalResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedClinicalResultRequestPerformedClinicalResult();
    performedClinicalResultContainer.setPerformedClinicalResult(performedClinicalResult);
    params.setPerformedClinicalResult(performedClinicalResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedClinicalResultResponse boxedResult = portType.createPerformedClinicalResult(params);
    return boxedResult.getPerformedClinicalResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult updatePerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult performedClinicalResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedClinicalResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedClinicalResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedClinicalResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedClinicalResultRequestPerformedClinicalResult performedClinicalResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedClinicalResultRequestPerformedClinicalResult();
    performedClinicalResultContainer.setPerformedClinicalResult(performedClinicalResult);
    params.setPerformedClinicalResult(performedClinicalResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedClinicalResultResponse boxedResult = portType.updatePerformedClinicalResult(params);
    return boxedResult.getPerformedClinicalResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult[] getPerformedMedicalHistoryResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedMedicalHistoryResultByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultByPerformedActivityResponse boxedResult = portType.getPerformedMedicalHistoryResultByPerformedActivity(params);
    return boxedResult.getPerformedMedicalHistoryResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult getPerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedMedicalHistoryResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedMedicalHistoryResultResponse boxedResult = portType.getPerformedMedicalHistoryResult(params);
    return boxedResult.getPerformedMedicalHistoryResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult createPerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult performedMedicalHistoryResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedMedicalHistoryResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedMedicalHistoryResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedMedicalHistoryResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedMedicalHistoryResultRequestPerformedMedicalHistoryResult performedMedicalHistoryResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedMedicalHistoryResultRequestPerformedMedicalHistoryResult();
    performedMedicalHistoryResultContainer.setPerformedMedicalHistoryResult(performedMedicalHistoryResult);
    params.setPerformedMedicalHistoryResult(performedMedicalHistoryResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedMedicalHistoryResultResponse boxedResult = portType.createPerformedMedicalHistoryResult(params);
    return boxedResult.getPerformedMedicalHistoryResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult updatePerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult performedMedicalHistoryResult) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedMedicalHistoryResult");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedMedicalHistoryResultRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedMedicalHistoryResultRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedMedicalHistoryResultRequestPerformedMedicalHistoryResult performedMedicalHistoryResultContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedMedicalHistoryResultRequestPerformedMedicalHistoryResult();
    performedMedicalHistoryResultContainer.setPerformedMedicalHistoryResult(performedMedicalHistoryResult);
    params.setPerformedMedicalHistoryResult(performedMedicalHistoryResultContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedMedicalHistoryResultResponse boxedResult = portType.updatePerformedMedicalHistoryResult(params);
    return boxedResult.getPerformedMedicalHistoryResult();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription[] getPerformedLesionDescriptionByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedLesionDescriptionByPerformedActivity");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionByPerformedActivityRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionByPerformedActivityRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionByPerformedActivityRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionByPerformedActivityRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionByPerformedActivityResponse boxedResult = portType.getPerformedLesionDescriptionByPerformedActivity(params);
    return boxedResult.getPerformedLesionDescription();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription getPerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerformedLesionDescription");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.GetPerformedLesionDescriptionResponse boxedResult = portType.getPerformedLesionDescription(params);
    return boxedResult.getPerformedLesionDescription();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription createPerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription performedLesionDescription) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPerformedLesionDescription");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedLesionDescriptionRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedLesionDescriptionRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedLesionDescriptionRequestPerformedLesionDescription performedLesionDescriptionContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedLesionDescriptionRequestPerformedLesionDescription();
    performedLesionDescriptionContainer.setPerformedLesionDescription(performedLesionDescription);
    params.setPerformedLesionDescription(performedLesionDescriptionContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.CreatePerformedLesionDescriptionResponse boxedResult = portType.createPerformedLesionDescription(params);
    return boxedResult.getPerformedLesionDescription();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription updatePerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription performedLesionDescription) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePerformedLesionDescription");
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedLesionDescriptionRequest params = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedLesionDescriptionRequest();
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedLesionDescriptionRequestPerformedLesionDescription performedLesionDescriptionContainer = new gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedLesionDescriptionRequestPerformedLesionDescription();
    performedLesionDescriptionContainer.setPerformedLesionDescription(performedLesionDescription);
    params.setPerformedLesionDescription(performedLesionDescriptionContainer);
    gov.nih.nci.coppa.services.outcomes.performedobservationresult.stubs.UpdatePerformedLesionDescriptionResponse boxedResult = portType.updatePerformedLesionDescription(params);
    return boxedResult.getPerformedLesionDescription();
    }
  }

}
