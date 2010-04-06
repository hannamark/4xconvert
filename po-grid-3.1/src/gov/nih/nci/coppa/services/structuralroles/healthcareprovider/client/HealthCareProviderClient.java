package gov.nih.nci.coppa.services.structuralroles.healthcareprovider.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.entities.person.client.PersonClient;
import gov.nih.nci.coppa.services.structuralroles.healthcareprovider.common.HealthCareProviderI;

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
public class HealthCareProviderClient extends HealthCareProviderClientBase implements HealthCareProviderI {

    /**
     * The identifier name for healthCare Provider.
     */
    public static final String HEALTH_CARE_PROVIDER_IDENTIFIER_NAME = "NCI health care provider identifier";

    /**
     * The ii root value for healthCare Provider.
     */
    public static final String HEALTH_CARE_PROVIDER_ROOT = "2.16.840.1.113883.3.26.4.4.2";

    public HealthCareProviderClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public HealthCareProviderClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public HealthCareProviderClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public HealthCareProviderClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(HealthCareProviderClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              HealthCareProviderClient client = new HealthCareProviderClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....
              getHealthCareProvider(client);
              searchHealthCareProvider(client);
              queryHealthCareProvider(client);
              getHealthCareProvidersByPlayerIds(client);
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

    private static void getHealthCareProvider(HealthCareProviderClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(HEALTH_CARE_PROVIDER_ROOT);
        id.setIdentifierName(HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
        id.setExtension("571");
        HealthCareProvider result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static void searchHealthCareProvider(HealthCareProviderClient client) throws RemoteException {
      HealthCareProvider criteria = createCriteria();
        HealthCareProvider[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }
    
    private static void queryHealthCareProvider(HealthCareProviderClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);
        HealthCareProvider criteria = createCriteria();
        HealthCareProvider[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }
    
    private static void getHealthCareProvidersByPlayerIds(HealthCareProviderClient client) {
        Id id1 = new Id();
        id1.setRoot(PersonClient.PERSON_ROOT);
        id1.setIdentifierName(PersonClient.PERSON_IDENTIFIER_NAME);
        id1.setExtension("501");
        
        Id id2 = new Id();
        id2.setRoot(PersonClient.PERSON_ROOT);
        id2.setIdentifierName(PersonClient.PERSON_IDENTIFIER_NAME);
        id2.setExtension("2153");
        
        try {
            HealthCareProvider[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private static HealthCareProvider createCriteria() {
        HealthCareProvider criteria = new HealthCareProvider();
        CD statusCode = new CD();
        statusCode.setCode("pending");
        criteria.setStatus(statusCode);
        return criteria;
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.CreateRequestHealthCareProvider healthCareProviderContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.CreateRequestHealthCareProvider();
    healthCareProviderContainer.setHealthCareProvider(healthCareProvider);
    params.setHealthCareProvider(healthCareProviderContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareProvider getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getHealthCareProvider();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getHealthCareProvider();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] search(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.SearchRequestHealthCareProvider healthCareProviderContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.SearchRequestHealthCareProvider();
    healthCareProviderContainer.setHealthCareProvider(healthCareProvider);
    params.setHealthCareProvider(healthCareProviderContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getHealthCareProvider();
    }
  }

  public void update(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateRequestHealthCareProvider healthCareProviderContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateRequestHealthCareProvider();
    healthCareProviderContainer.setHealthCareProvider(healthCareProvider);
    params.setHealthCareProvider(healthCareProviderContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.ValidateRequestHealthCareProvider healthCareProviderContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.ValidateRequestHealthCareProvider();
    healthCareProviderContainer.setHealthCareProvider(healthCareProvider);
    params.setHealthCareProvider(healthCareProviderContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] query(gov.nih.nci.coppa.po.HealthCareProvider healthCareProvider,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequestHealthCareProvider healthCareProviderContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequestHealthCareProvider();
    healthCareProviderContainer.setHealthCareProvider(healthCareProvider);
    params.setHealthCareProvider(healthCareProviderContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getHealthCareProvider();
    }
  }

  public gov.nih.nci.coppa.po.HealthCareProvider[] getByPlayerIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByPlayerIds");
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByPlayerIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByPlayerIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.healthcareprovider.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
    return boxedResult.getHealthCareProvider();
    }
  }

}
