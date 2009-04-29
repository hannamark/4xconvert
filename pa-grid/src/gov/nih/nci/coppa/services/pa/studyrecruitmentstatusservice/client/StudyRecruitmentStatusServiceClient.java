package gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.client;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus;
import gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.common.StudyRecruitmentStatusServiceI;

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
public class StudyRecruitmentStatusServiceClient extends StudyRecruitmentStatusServiceClientBase implements StudyRecruitmentStatusServiceI {

    public StudyRecruitmentStatusServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public StudyRecruitmentStatusServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public StudyRecruitmentStatusServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public StudyRecruitmentStatusServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(StudyRecruitmentStatusServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              StudyRecruitmentStatusServiceClient client = new StudyRecruitmentStatusServiceClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....
              getStudyRecruitmentStatus(client);
              getCurrentStudyRecruitmentStatus(client);
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

    private static void getStudyRecruitmentStatus(StudyRecruitmentStatusServiceClient client) throws RemoteException {
        Id id = new Id();
        StudyRecruitmentStatus[] result = client.getByStudyProtocol(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));

    }

    private static void getCurrentStudyRecruitmentStatus(StudyRecruitmentStatusServiceClient client) throws RemoteException {
        Id id = new Id();
        StudyRecruitmentStatus[] result = client.getCurrentByStudyProtocol(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));

    }

  public gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudyRecruitmentStatus();
    }
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus[] getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetCurrentByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetCurrentByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId studyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetCurrentByStudyProtocolRequestStudyProtocolId();
    studyProtocolIdContainer.setId(studyProtocolId);
    params.setStudyProtocolId(studyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetCurrentByStudyProtocolResponse boxedResult = portType.getCurrentByStudyProtocol(params);
    return boxedResult.getStudyRecruitmentStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyRecruitmentStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus create(gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus studyRecruitmentStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CreateRequestStudyRecruitmentStatus studyRecruitmentStatusContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CreateRequestStudyRecruitmentStatus();
    studyRecruitmentStatusContainer.setStudyRecruitmentStatus(studyRecruitmentStatus);
    params.setStudyRecruitmentStatus(studyRecruitmentStatusContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyRecruitmentStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus update(gov.nih.nci.coppa.services.pa.StudyRecruitmentStatus studyRecruitmentStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.UpdateRequestStudyRecruitmentStatus studyRecruitmentStatusContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.UpdateRequestStudyRecruitmentStatus();
    studyRecruitmentStatusContainer.setStudyRecruitmentStatus(studyRecruitmentStatus);
    params.setStudyRecruitmentStatus(studyRecruitmentStatusContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyRecruitmentStatus();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studyrecruitmentstatusservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
