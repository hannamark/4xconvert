package gov.nih.nci.coppa.services.structuralroles.oversightcommittee.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.structuralroles.oversightcommittee.common.OversightCommitteeI;

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
public class OversightCommitteeClient extends OversightCommitteeClientBase implements OversightCommitteeI {	
	
	/**
     * The identifier name for for OversightCommittee.
     */
    public static final String OVERSIGHT_COMMITTEE_IDENTIFIER_NAME  = "NCI oversight committee identifier";

    /**
     * The ii root value for OversightCommittee.
     */
    public static final String OVERSIGHT_COMMITTEE_ROOT = "2.16.840.1.113883.3.26.4.4.4";

	public OversightCommitteeClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public OversightCommitteeClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public OversightCommitteeClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public OversightCommitteeClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(OversightCommitteeClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  OversightCommitteeClient client = new OversightCommitteeClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			  getOversightCommittee(client);
			  searchOversightCommittee(client);
			  queryOversightCommittee(client);
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

	private static void getOversightCommittee(OversightCommitteeClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(OVERSIGHT_COMMITTEE_ROOT);
        id.setIdentifierName(OVERSIGHT_COMMITTEE_IDENTIFIER_NAME);
        id.setExtension("604");
        OversightCommittee result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static void searchOversightCommittee(OversightCommitteeClient client) throws RemoteException {
        OversightCommittee criteria = createCriteria();
        OversightCommittee[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    /**
     * @return
     */
    private static OversightCommittee createCriteria() {
        OversightCommittee criteria = new OversightCommittee();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        return criteria;
    }
    
    private static void queryOversightCommittee(OversightCommitteeClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);        
        OversightCommittee criteria = createCriteria();
        OversightCommittee[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.CreateRequestOversightCommittee oversightCommitteeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.CreateRequestOversightCommittee();
    oversightCommitteeContainer.setOversightCommittee(oversightCommittee);
    params.setOversightCommittee(oversightCommitteeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.OversightCommittee getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getOversightCommittee();
    }
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getOversightCommittee();
    }
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] search(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.SearchRequestOversightCommittee oversightCommitteeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.SearchRequestOversightCommittee();
    oversightCommitteeContainer.setOversightCommittee(oversightCommittee);
    params.setOversightCommittee(oversightCommitteeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getOversightCommittee();
    }
  }

  public void update(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateRequestOversightCommittee oversightCommitteeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateRequestOversightCommittee();
    oversightCommitteeContainer.setOversightCommittee(oversightCommittee);
    params.setOversightCommittee(oversightCommitteeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.ValidateRequestOversightCommittee oversightCommitteeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.ValidateRequestOversightCommittee();
    oversightCommitteeContainer.setOversightCommittee(oversightCommittee);
    params.setOversightCommittee(oversightCommitteeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.OversightCommittee[] query(gov.nih.nci.coppa.po.OversightCommittee oversightCommittee,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequestOversightCommittee oversightCommitteeContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequestOversightCommittee();
    oversightCommitteeContainer.setOversightCommittee(oversightCommittee);
    params.setOversightCommittee(oversightCommitteeContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.oversightcommittee.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getOversightCommittee();
    }
  }

}
