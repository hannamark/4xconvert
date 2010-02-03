package gov.nih.nci.coppa.services.entities.organization.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.entities.organization.common.OrganizationI;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.extensions.Id;

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
public class OrganizationClient extends OrganizationClientBase implements OrganizationI {
 
    private static ClientParameterHelper<OrganizationClient> helper = 
        new ClientParameterHelper<OrganizationClient>(OrganizationClient.class);
    
    /**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = Constants.NCI_OID + ".2";

    public OrganizationClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public OrganizationClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public OrganizationClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public OrganizationClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
            String[] localArgs = new String[] {"-getId"};          
            helper.setLocalArgs(localArgs);
            helper.setupParams(args);
            
            OrganizationClient client = new OrganizationClient(helper.getArgument("-url"));

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
    private static void getOrg(OrganizationClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(ORG_ROOT);
        id.setIdentifierName(ORG_IDENTIFIER_NAME);
        id.setExtension(helper.getArgument("-getId", "1"));
        Organization result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static Organization createCriteria() {
        Organization criteria = new Organization();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatusCode(statusCode);
        return criteria;
    }

    @GridTestMethod
    private static void searchOrganizations(OrganizationClient client) throws RemoteException {
        Organization criteria = createCriteria();
        Organization[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    @GridTestMethod
    private static void queryOrganizations(OrganizationClient client) throws RemoteException {
        Organization criteria = createCriteria();

        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(2);
        limitOffset.setOffset(0);
        Organization[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public void update(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.UpdateRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public gov.nih.nci.coppa.po.Organization[] search(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.entities.organization.stubs.SearchRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.SearchRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.SearchRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.SearchRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getOrganization();
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.entities.organization.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.ValidateRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.ValidateRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.Organization getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.entities.organization.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getOrganization();
    }
  }

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.Organization organization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.entities.organization.stubs.CreateRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.CreateRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.CreateRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.CreateRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.Organization[] query(gov.nih.nci.coppa.po.Organization organization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequest params = new gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequest();
    gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequestOrganization organizationContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.entities.organization.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.entities.organization.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getOrganization();
    }
  }

}
