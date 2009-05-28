package gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.client;
import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.common.DocumentWorkflowStatusServiceI;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CopyRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CopyRequestFromStudyProtocolId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CopyRequestToStudyProtocolId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CreateRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CreateRequestDocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.CreateResponse;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.DeleteRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.DeleteRequestId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetByStudyProtocolRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetByStudyProtocolRequestId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetByStudyProtocolResponse;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetCurrentByStudyProtocolRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetCurrentByStudyProtocolRequestId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetCurrentByStudyProtocolResponse;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetRequestId;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.GetResponse;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.UpdateRequest;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.UpdateRequestDocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.stubs.UpdateResponse;
import gov.nih.nci.coppa.services.pa.faults.PAFault;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;

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
public class DocumentWorkflowStatusServiceClient extends DocumentWorkflowStatusServiceClientBase implements DocumentWorkflowStatusServiceI {

    public DocumentWorkflowStatusServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public DocumentWorkflowStatusServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public DocumentWorkflowStatusServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public DocumentWorkflowStatusServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(DocumentWorkflowStatusServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              DocumentWorkflowStatusServiceClient client = new DocumentWorkflowStatusServiceClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....
              getTest(client);
              getByStudyProtocolTest(client);
              getCurrentByStudyProtocolTest(client);
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

    private static void getTest(DocumentWorkflowStatusServiceClient client) throws RemoteException {
        Id id = new Id();
        id.setExtension("1");
        DocumentWorkflowStatus result = client.get(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void getByStudyProtocolTest(DocumentWorkflowStatusServiceClient client) throws RemoteException {
        Id id = new Id();
        id.setExtension("1");
        DocumentWorkflowStatus[] result = client.getByStudyProtocol(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void getCurrentByStudyProtocolTest(DocumentWorkflowStatusServiceClient client) throws RemoteException {
        Id id = new Id();
        id.setExtension("1");
        DocumentWorkflowStatus[] result = client.getCurrentByStudyProtocol(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
    }

  public DocumentWorkflowStatus[] getCurrentByStudyProtocol(Id id) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentByStudyProtocol");
    GetCurrentByStudyProtocolRequest params = new GetCurrentByStudyProtocolRequest();
    GetCurrentByStudyProtocolRequestId idContainer = new GetCurrentByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    GetCurrentByStudyProtocolResponse boxedResult = portType.getCurrentByStudyProtocol(params);
    return boxedResult.getDocumentWorkflowStatus();
    }
  }

  public DocumentWorkflowStatus[] getByStudyProtocol(Id id) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    GetByStudyProtocolRequest params = new GetByStudyProtocolRequest();
    GetByStudyProtocolRequestId idContainer = new GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getDocumentWorkflowStatus();
    }
  }

  public void copy(Id fromStudyProtocolId,Id toStudyProtocolId) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    CopyRequest params = new CopyRequest();
    CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    portType.copy(params);
    }
  }

  public DocumentWorkflowStatus get(Id id) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    GetRequest params = new GetRequest();
    GetRequestId idContainer = new GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    GetResponse boxedResult = portType.get(params);
    return boxedResult.getDocumentWorkflowStatus();
    }
  }

  public DocumentWorkflowStatus create(DocumentWorkflowStatus documentWorkflowStatus) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    CreateRequest params = new CreateRequest();
    CreateRequestDocumentWorkflowStatus documentWorkflowStatusContainer = new CreateRequestDocumentWorkflowStatus();
    documentWorkflowStatusContainer.setDocumentWorkflowStatus(documentWorkflowStatus);
    params.setDocumentWorkflowStatus(documentWorkflowStatusContainer);
    CreateResponse boxedResult = portType.create(params);
    return boxedResult.getDocumentWorkflowStatus();
    }
  }

  public DocumentWorkflowStatus update(DocumentWorkflowStatus documentWorkflowStatus) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    UpdateRequest params = new UpdateRequest();
    UpdateRequestDocumentWorkflowStatus documentWorkflowStatusContainer = new UpdateRequestDocumentWorkflowStatus();
    documentWorkflowStatusContainer.setDocumentWorkflowStatus(documentWorkflowStatus);
    params.setDocumentWorkflowStatus(documentWorkflowStatusContainer);
    UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getDocumentWorkflowStatus();
    }
  }

  public void delete(Id id) throws RemoteException, PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    DeleteRequest params = new DeleteRequest();
    DeleteRequestId idContainer = new DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    portType.delete(params);
    }
  }

}
