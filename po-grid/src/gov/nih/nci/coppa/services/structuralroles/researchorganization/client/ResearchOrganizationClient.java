package gov.nih.nci.coppa.services.structuralroles.researchorganization.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.entities.organization.client.OrganizationClient;
import gov.nih.nci.coppa.services.structuralroles.researchorganization.common.ResearchOrganizationI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.II;

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
public class ResearchOrganizationClient extends ResearchOrganizationClientBase implements ResearchOrganizationI {

    /**
     * The identifier name for for Research org.
     */
    public static final String RESEARCH_ORG_IDENTIFIER_NAME = "Research org research";

    /**
     * The ii root value for Research org.
     */
    public static final String RESEARCH_ORG_ROOT = "2.16.840.1.113883.3.26.4.4.6";

    public ResearchOrganizationClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public ResearchOrganizationClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public ResearchOrganizationClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public ResearchOrganizationClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(ResearchOrganizationClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              ResearchOrganizationClient client = new ResearchOrganizationClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....
              getResearchOrg(client);
              searchResearchOrg(client);
              queryResearchOrg(client);
              System.out.println("---getbyplayerids----");
              getResearchOrgsByPlayerIds(client);
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

    private static void getResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(RESEARCH_ORG_ROOT);
        id.setIdentifierName(RESEARCH_ORG_IDENTIFIER_NAME);
        id.setExtension("6812");
        ResearchOrganization result = client.getById(id);
        ClientUtils.handleResult(result);
        for (II ii : result.getIdentifier().getItem()) {
            System.out.println(ToStringBuilder.reflectionToString(ii, ToStringStyle.MULTI_LINE_STYLE));
        }
    }
    
    private static void getResearchOrgsByPlayerIds(ResearchOrganizationClient client) {
        Id id1 = new Id();
        id1.setRoot(OrganizationClient.ORG_ROOT);
        id1.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id1.setExtension("1847");
        
        Id id2 = new Id();
        id2.setRoot(OrganizationClient.ORG_ROOT);
        id2.setIdentifierName(OrganizationClient.ORG_IDENTIFIER_NAME);
        id2.setExtension("2119");
        
        try {
            ResearchOrganization[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.handleSearchResults(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void searchResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        ResearchOrganization criteria = createCriteria();
        ResearchOrganization[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }

    /**
     * @return
     */
    private static ResearchOrganization createCriteria() {
        ResearchOrganization criteria = new ResearchOrganization();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatus(statusCode);
        return criteria;
    }

    private static void queryResearchOrg(ResearchOrganizationClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
        limitOffset.setOffset(0);
        ResearchOrganization criteria = createCriteria();
        ResearchOrganization[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] search(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public void update(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] query(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestResearchOrganization researchOrganizationContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestResearchOrganization();
    researchOrganizationContainer.setResearchOrganization(researchOrganization);
    params.setResearchOrganization(researchOrganizationContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getResearchOrganization();
    }
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByPlayerIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByPlayerIds");
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
    return boxedResult.getResearchOrganization();
    }
  }

}
