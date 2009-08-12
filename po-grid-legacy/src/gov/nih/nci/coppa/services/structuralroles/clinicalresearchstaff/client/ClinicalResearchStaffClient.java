package gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.common.ClinicalResearchStaffI;

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
public class ClinicalResearchStaffClient extends ClinicalResearchStaffClientBase implements ClinicalResearchStaffI {	
    /**
     * The identifier name for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = "2.16.840.1.113883.3.26.4.4.1";
    
	public ClinicalResearchStaffClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public ClinicalResearchStaffClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public ClinicalResearchStaffClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public ClinicalResearchStaffClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(ClinicalResearchStaffClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  ClinicalResearchStaffClient client = new ClinicalResearchStaffClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
              getClinicalResearchStaff(client);
              getClinicalResearchStaffs(client);
              searchClinicalResearchStaff(client);
              queryClinicalResearchStaff(client);
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
	
    private static void getClinicalResearchStaff(ClinicalResearchStaffClient client) {
        Id id = createII();
        ClinicalResearchStaff result;
        try {
            result = client.getById(id);
            ClientUtils.handleResult(result);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private static Id createII() {
        Id id = new Id();
        id.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension("616");
        return id;
    }
    
    private static void getClinicalResearchStaffs(ClinicalResearchStaffClient client) {
        Id id = createII();
        
        Id id2 = new Id();
        id2.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        id2.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id2.setExtension("4230");
        
        try {
            ClinicalResearchStaff[] results = client.getByIds(new Id[] {id, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void searchClinicalResearchStaff(ClinicalResearchStaffClient client) {
        ClinicalResearchStaff criteria = new ClinicalResearchStaff();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        try {
            ClinicalResearchStaff[] results = client.search(criteria);
            ClientUtils.handleSearchResults(results);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private static void queryClinicalResearchStaff(ClinicalResearchStaffClient client) {
        ClinicalResearchStaff criteria = new ClinicalResearchStaff();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        try {
            LimitOffset limitOffset = new LimitOffset();
            limitOffset.setLimit(1);
            limitOffset.setOffset(0);
            ClinicalResearchStaff[] results = client.query(criteria, limitOffset);
            ClientUtils.handleSearchResults(results);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.CreateRequestClinicalResearchStaff clinicalResearchStaffContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.CreateRequestClinicalResearchStaff();
    clinicalResearchStaffContainer.setClinicalResearchStaff(clinicalResearchStaff);
    params.setClinicalResearchStaff(clinicalResearchStaffContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getClinicalResearchStaff();
    }
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getClinicalResearchStaff();
    }
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] search(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.SearchRequestClinicalResearchStaff clinicalResearchStaffContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.SearchRequestClinicalResearchStaff();
    clinicalResearchStaffContainer.setClinicalResearchStaff(clinicalResearchStaff);
    params.setClinicalResearchStaff(clinicalResearchStaffContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getClinicalResearchStaff();
    }
  }

  public void update(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateRequestClinicalResearchStaff clinicalResearchStaffContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateRequestClinicalResearchStaff();
    clinicalResearchStaffContainer.setClinicalResearchStaff(clinicalResearchStaff);
    params.setClinicalResearchStaff(clinicalResearchStaffContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.ValidateRequestClinicalResearchStaff clinicalResearchStaffContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.ValidateRequestClinicalResearchStaff();
    clinicalResearchStaffContainer.setClinicalResearchStaff(clinicalResearchStaff);
    params.setClinicalResearchStaff(clinicalResearchStaffContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] query(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequestClinicalResearchStaff clinicalResearchStaffContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequestClinicalResearchStaff();
    clinicalResearchStaffContainer.setClinicalResearchStaff(clinicalResearchStaff);
    params.setClinicalResearchStaff(clinicalResearchStaffContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getClinicalResearchStaff();
    }
  }

}
