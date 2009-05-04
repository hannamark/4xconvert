package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.client;

import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.structuralroles.healthcarefacility.common.HealthCareFacilityI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;

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
public class HealthCareFacilityClient extends HealthCareFacilityClientBase implements HealthCareFacilityI {	
    /**
     * The identifier name for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_IDENTIFIER_NAME = "NCI health care facility identifier";

    /**
     * The ii root value for healthCare Facility.
     */
    public static final String HEALTH_CARE_FACILITY_ROOT = "2.16.840.1.113883.3.26.4.4.3";
    
	public HealthCareFacilityClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public HealthCareFacilityClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public HealthCareFacilityClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public HealthCareFacilityClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(HealthCareFacilityClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  HealthCareFacilityClient client = new HealthCareFacilityClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
              getHealthCareFacility(client);
              searchHealthCareFacility(client);
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
	
    private static void getHealthCareFacility(HealthCareFacilityClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(HEALTH_CARE_FACILITY_ROOT);
        id.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        id.setExtension("640");
        HealthCareFacility result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static void searchHealthCareFacility(HealthCareFacilityClient client) throws RemoteException {
        HealthCareFacility criteria = new HealthCareFacility();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        HealthCareFacility[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

  public gov.nih.nci.coppa.po.HealthCareFacility getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getHealthCareFacility();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getHealthCareFacility();
    }
  }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateRequestHealthCareFacility healthCareFacilityContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateRequestHealthCareFacility();
    healthCareFacilityContainer.setHealthCareFacility(healthCareFacility);
    params.setHealthCareFacility(healthCareFacilityContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateRequestHealthCareFacility healthCareFacilityContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateRequestHealthCareFacility();
    healthCareFacilityContainer.setHealthCareFacility(healthCareFacility);
    params.setHealthCareFacility(healthCareFacilityContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] search(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchRequestHealthCareFacility healthCareFacilityContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchRequestHealthCareFacility();
    healthCareFacilityContainer.setHealthCareFacility(healthCareFacility);
    params.setHealthCareFacility(healthCareFacilityContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getHealthCareFacility();
    }
  }

  public void update(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateRequestHealthCareFacility healthCareFacilityContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateRequestHealthCareFacility();
    healthCareFacilityContainer.setHealthCareFacility(healthCareFacility);
    params.setHealthCareFacility(healthCareFacilityContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.HealthCareFacility[] query(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequestHealthCareFacility healthCareFacilityContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequestHealthCareFacility();
    healthCareFacilityContainer.setHealthCareFacility(healthCareFacility);
    params.setHealthCareFacility(healthCareFacilityContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getHealthCareFacility();
    }
  }

}
