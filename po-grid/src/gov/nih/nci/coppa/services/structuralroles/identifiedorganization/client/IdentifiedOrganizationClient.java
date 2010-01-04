package gov.nih.nci.coppa.services.structuralroles.identifiedorganization.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.entities.organization.client.OrganizationClient;
import gov.nih.nci.coppa.services.structuralroles.identifiedorganization.common.IdentifiedOrganizationI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.iso._21090.IdentifierReliability;

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

    /**
     * The identifier name for for Identified org.
     */
    public static final String IDENTIFIED_ORG_IDENTIFIER_NAME = "Identified org identifier";

    /**
     * The ii root value for Identified org.
     */
    public static final String IDENTIFIED_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";

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
        System.out.println("Running the Grid Service Client idOrg");
        try{
            if(!(args.length < 2)){
                if(args[0].equals("-url")){
                    IdentifiedOrganizationClient client = new IdentifiedOrganizationClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....
                    getIdentifiedOrg(client);
                    searchIdentifiedOrg(client);
                    queryIdentifiedOrg(client);
                    System.out.println("---- get by playerids---");
                    getIdentifiedOrgsByPlayerIds(client);

                    testUpdate(client);
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

    private static void testUpdate(IdentifiedOrganizationClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("518");
        IdentifiedOrganization result = client.getById(id);

        II assignedId = new II();
        assignedId.setRoot("1.2.1.1.1.1.1");
        assignedId.setExtension("New EXT");
        assignedId.setReliability(IdentifierReliability.ISS);
        assignedId.setIdentifierName("CTEP ID");
        result.setAssignedId(assignedId);

        client.update(result);
    }


    private static void getIdentifiedOrg(IdentifiedOrganizationClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(IDENTIFIED_ORG_ROOT);
        id.setIdentifierName(IDENTIFIED_ORG_IDENTIFIER_NAME);
        id.setExtension("597");
        IdentifiedOrganization result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static void getIdentifiedOrgsByPlayerIds(IdentifiedOrganizationClient client) {
        Id id1 = new Id();
        id1.setRoot(OrganizationClient.ORG_ROOT);
        id1.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id1.setExtension("1847");

        Id id2 = new Id();
        id2.setRoot(OrganizationClient.ORG_ROOT);
        id2.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id2.setExtension("2119");

        try {
            IdentifiedOrganization[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void searchIdentifiedOrg(IdentifiedOrganizationClient client) throws RemoteException {
        IdentifiedOrganization criteria = createCriteria();
        IdentifiedOrganization[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    private static void queryIdentifiedOrg(IdentifiedOrganizationClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);
        IdentifiedOrganization criteria = createCriteria();
        IdentifiedOrganization[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

    /**
     * @return
     */
    private static IdentifiedOrganization createCriteria() {
        IdentifiedOrganization criteria = new IdentifiedOrganization();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        return criteria;
    }

    public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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

    public gov.nih.nci.coppa.po.IdentifiedOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
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

    public gov.nih.nci.coppa.po.IdentifiedOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
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

    public gov.nih.nci.coppa.po.IdentifiedOrganization[] search(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
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

    public void update(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"update");
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequest();
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateRequestIdentifiedOrganization();
            identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
            params.setIdentifiedOrganization(identifiedOrganizationContainer);
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.UpdateResponse boxedResult = portType.update(params);
        }
    }

    public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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

    public gov.nih.nci.coppa.po.IdentifiedOrganization[] query(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"query");
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequest();
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequestIdentifiedOrganization identifiedOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequestIdentifiedOrganization();
            identifiedOrganizationContainer.setIdentifiedOrganization(identifiedOrganization);
            params.setIdentifiedOrganization(identifiedOrganizationContainer);
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryRequestLimitOffset();
            limitOffsetContainer.setLimitOffset(limitOffset);
            params.setLimitOffset(limitOffsetContainer);
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.QueryResponse boxedResult = portType.query(params);
            return boxedResult.getIdentifiedOrganization();
        }
    }

    public gov.nih.nci.coppa.po.IdentifiedOrganization[] getByPlayerIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
        synchronized(portTypeMutex){
            configureStubSecurity((Stub)portType,"getByPlayerIds");
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByPlayerIdsRequest();
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByPlayerIdsRequestId();
            idContainer.setId(id);
            params.setId(idContainer);
            gov.nih.nci.coppa.services.structuralroles.identifiedorganization.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
            return boxedResult.getIdentifiedOrganization();
        }
    }

}
