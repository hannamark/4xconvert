package gov.nih.nci.coppa.services.pa.studydiseaseservice.client;

import gov.nih.nci.coppa.services.pa.StudyDisease;
import gov.nih.nci.coppa.services.pa.studydiseaseservice.common.StudyDiseaseServiceI;
import gov.nih.nci.iso21090.extensions.Id;
import gov.nih.nci.pa.iso.util.IiConverter;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
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
public class StudyDiseaseServiceClient extends StudyDiseaseServiceClientBase implements StudyDiseaseServiceI {

    public StudyDiseaseServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public StudyDiseaseServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public StudyDiseaseServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public StudyDiseaseServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(StudyDiseaseServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              StudyDiseaseServiceClient client = new StudyDiseaseServiceClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....
              getForStudyDisease(client);
              getStudyDisease(client);
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

    private static void getForStudyDisease(StudyDiseaseServiceClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        id.setIdentifierName(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
        id.setExtension("108005");
        StudyDisease[] stCont = client.getByStudyProtocol(id);
        if (stCont != null) {
            System.out.println("get by study protocol brought back set sized " + stCont.length);
        } else {
            System.out.println("get by study protocol brought back null set");
        }
    }

    private static void getStudyDisease(StudyDiseaseServiceClient client) throws RemoteException {
        Id id = new Id();
        id.setExtension("108666");
        StudyDisease stCont = client.get(id);
        if (stCont == null) {
            System.out.println("could not find StudyParticipant");
        } else {
            System.out.println("StudyParticipant 27441 found.");
        }
    }

  public gov.nih.nci.coppa.services.pa.StudyDisease[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudyDisease();
    }
  }

  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudyDisease();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease create(gov.nih.nci.coppa.services.pa.StudyDisease studyDisease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CreateRequestStudyDisease studyDiseaseContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CreateRequestStudyDisease();
    studyDiseaseContainer.setStudyDisease(studyDisease);
    params.setStudyDisease(studyDiseaseContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudyDisease();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudyDisease update(gov.nih.nci.coppa.services.pa.StudyDisease studyDisease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.UpdateRequestStudyDisease studyDiseaseContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.UpdateRequestStudyDisease();
    studyDiseaseContainer.setStudyDisease(studyDisease);
    params.setStudyDisease(studyDiseaseContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudyDisease();
    }
  }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studydiseaseservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
