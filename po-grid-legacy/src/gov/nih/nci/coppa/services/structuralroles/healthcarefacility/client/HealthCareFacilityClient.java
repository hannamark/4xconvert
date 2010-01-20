package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.HealthCareFacility;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.entities.organization.client.OrganizationClient;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.coppa.services.structuralroles.healthcarefacility.common.HealthCareFacilityI;

import java.lang.reflect.Method;
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

    private static ClientParameterHelper<HealthCareFacilityClient> helper = 
        new ClientParameterHelper<HealthCareFacilityClient>(HealthCareFacilityClient.class);

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

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
            String[] localArgs = new String[] {"-getId", "-playerId", "-playerId2"};          
            helper.setLocalArgs(localArgs);
            helper.setupParams(args);

            HealthCareFacilityClient client = new HealthCareFacilityClient(helper.getArgument("-url"));

            for (Method method : helper.getRunMethods()) {
                System.out.println("Running " + method.getName());
                method.invoke(null, client);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @GridTestMethod
    private static void getHealthCareFacilitysByPlayerIds(HealthCareFacilityClient client) {
        Id id1 = new Id();
        id1.setRoot(OrganizationClient.ORG_ROOT);
        id1.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id1.setExtension(helper.getArgument("-playerId", "1"));

        Id id2 = new Id();
        id2.setRoot(OrganizationClient.ORG_ROOT);
        id2.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id2.setExtension(helper.getArgument("-playerId2", "2"));

        try {
            HealthCareFacility[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @GridTestMethod
    private static void getHealthCareFacility(HealthCareFacilityClient client) throws RemoteException {	    
        Id id = new Id();
        id.setRoot(HEALTH_CARE_FACILITY_ROOT);
        id.setIdentifierName(HEALTH_CARE_FACILITY_IDENTIFIER_NAME);
        id.setExtension(helper.getArgument("-getId", "1"));

        HealthCareFacility result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    @GridTestMethod
    private static void searchHealthCareFacility(HealthCareFacilityClient client) throws RemoteException {
        HealthCareFacility criteria = createCriteria();
        HealthCareFacility[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    @GridTestMethod
    private static void queryHealthCareFacility(HealthCareFacilityClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);
        HealthCareFacility criteria = createCriteria();
        HealthCareFacility[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

    /**
     * @return
     */
    private static HealthCareFacility createCriteria() {
        HealthCareFacility criteria = new HealthCareFacility();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        return criteria;
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

    public gov.nih.nci.coppa.po.HealthCareFacility[] search(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
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

    public gov.nih.nci.coppa.po.HealthCareFacility[] query(gov.nih.nci.coppa.po.HealthCareFacility healthCareFacility,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
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

    public gov.nih.nci.coppa.po.HealthCareFacility[] getByPlayerIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"getByPlayerIds");
            gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsRequest();
            gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsRequestId();
            idContainer.setId(id);
            params.setId(idContainer);
            gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
            return boxedResult.getHealthCareFacility();
        }
    }

}
