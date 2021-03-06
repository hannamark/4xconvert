package gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.services.client.ClientUtils;
import gov.nih.nci.coppa.services.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.entities.person.client.PersonClient;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.common.ClinicalResearchStaffI;
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
public class ClinicalResearchStaffClient extends ClinicalResearchStaffClientBase implements ClinicalResearchStaffI {

    private static ClientParameterHelper<ClinicalResearchStaffClient> helper =
        new ClientParameterHelper<ClinicalResearchStaffClient>(ClinicalResearchStaffClient.class);

    /**
     * The identifier name for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME = "NCI clinical research staff identifier";

    /**
     * The ii root value for ClinicalResearchStaff.
     */
    public static final String CLINICAL_RESEARCH_STAFF_ROOT = Constants.NCI_OID + ".4.1";

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

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
            String[] localArgs = new String[] {"-getId", "-getId2", "-playerId", "-playerId2"};
            helper.setLocalArgs(localArgs);
            helper.setupParams(args);

            ClinicalResearchStaffClient client = new ClinicalResearchStaffClient(helper.getArgument("-url"));

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
    private static void getClinicalResearchStaff(ClinicalResearchStaffClient client) {
        Id id = createII(helper.getArgument("-getId", "1"));
        ClinicalResearchStaff result;
        try {
            result = client.getById(id);
            ClientUtils.print(result);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     */
    private static Id createII(String idSt) {
        Id id = new Id();
        id.setRoot(CLINICAL_RESEARCH_STAFF_ROOT);
        id.setIdentifierName(CLINICAL_RESEARCH_STAFF_IDENTIFIER_NAME);
        id.setExtension(idSt);
        return id;
    }

    @GridTestMethod
    private static void getClinicalResearchStaffs(ClinicalResearchStaffClient client) {
        Id id = createII(helper.getArgument("-getId", "1"));
        Id id2 = createII(helper.getArgument("-getId2", "2"));

        try {
            ClinicalResearchStaff[] results = client.getByIds(new Id[] {id, id2});
            ClientUtils.print(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @GridTestMethod
    private static void getClinicalResearchStaffsByPlayerIds(ClinicalResearchStaffClient client) {
        Id id1 = new Id();
        id1.setRoot(PersonClient.PERSON_ROOT);
        id1.setIdentifierName(PersonClient.PERSON_IDENTIFIER_NAME);
        id1.setExtension(helper.getArgument("-playerId", "1"));

        Id id2 = new Id();
        id2.setRoot(PersonClient.PERSON_ROOT);
        id2.setIdentifierName(PersonClient.PERSON_IDENTIFIER_NAME);
        id2.setExtension(helper.getArgument("-playerId2", "2"));

        try {
            ClinicalResearchStaff[] results = client.getByPlayerIds(new Id[] {id1, id2});
            ClientUtils.print(results);
        } catch (NullifiedRoleFault e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @GridTestMethod
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
            ClientUtils.print(results);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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

  public gov.nih.nci.coppa.po.ClinicalResearchStaff getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
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

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] getByIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
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

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] getByPlayerIds(gov.nih.nci.iso21090.extensions.Id[] id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByPlayerIds");
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByPlayerIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByPlayerIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByPlayerIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByPlayerIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.stubs.GetByPlayerIdsResponse boxedResult = portType.getByPlayerIds(params);
    return boxedResult.getClinicalResearchStaff();
    }
  }

}
