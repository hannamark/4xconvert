package gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.client;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.common.StudySiteAccrualStatusServiceI;

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
public class StudySiteAccrualStatusServiceClient extends StudySiteAccrualStatusServiceClientBase implements StudySiteAccrualStatusServiceI {

    public StudySiteAccrualStatusServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public StudySiteAccrualStatusServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public StudySiteAccrualStatusServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public StudySiteAccrualStatusServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(StudySiteAccrualStatusServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              StudySiteAccrualStatusServiceClient client = new StudySiteAccrualStatusServiceClient(args[1]);

              Id id = new Id();
              id.setExtension("1");
              System.out.println("Testing StudySiteAccrualStatus.getStudySiteAccrualStatus...");
              try {
                  client.getStudySiteAccrualStatus(id);
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              System.out.println("Testing StudySiteAccrualStatus.getCurrentStudySiteAccrualStatusByStudyParticipation...");
              StudySiteAccrualStatus current = client.getCurrentStudySiteAccrualStatusByStudySite(id);
              System.out.println(current);

              System.out.println("Testing StudySiteAccrualStatus.getStudySiteAccrualStatusByStudyParticipation...");
              StudySiteAccrualStatus[] rList = client.getStudySiteAccrualStatusByStudySite(id);
              System.out.println(rList);

              System.out.println("Testing StudySiteAccrualStatus.createStudySiteAccrualStatus...");
              try {
                  client.createStudySiteAccrualStatus(new StudySiteAccrualStatus());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              System.out.println("Testing StudySiteAccrualStatus.updateStudySiteAccrualStatus...");
              try {
                  client.updateStudySiteAccrualStatus(new StudySiteAccrualStatus());
                  System.out.println("Not yet implemented exception should have been thrown.");
              } catch(RemoteException e) {
                  // expected behavior
              }

              // place client calls here if you want to use this main as a
              // test....
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

  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus getStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStudySiteAccrualStatus");
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusRequest params = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusRequest();
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusRequestId idContainer = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusResponse boxedResult = portType.getStudySiteAccrualStatus(params);
    return boxedResult.getStudySiteAccrualStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus createStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createStudySiteAccrualStatus");
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.CreateStudySiteAccrualStatusRequest params = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.CreateStudySiteAccrualStatusRequest();
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.CreateStudySiteAccrualStatusRequestStudySiteAccrualStatus studySiteAccrualStatusContainer = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.CreateStudySiteAccrualStatusRequestStudySiteAccrualStatus();
    studySiteAccrualStatusContainer.setStudySiteAccrualStatus(studySiteAccrualStatus);
    params.setStudySiteAccrualStatus(studySiteAccrualStatusContainer);
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.CreateStudySiteAccrualStatusResponse boxedResult = portType.createStudySiteAccrualStatus(params);
    return boxedResult.getStudySiteAccrualStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus updateStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStudySiteAccrualStatus");
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.UpdateStudySiteAccrualStatusRequest params = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.UpdateStudySiteAccrualStatusRequest();
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.UpdateStudySiteAccrualStatusRequestStudySiteAccrualStatus studySiteAccrualStatusContainer = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.UpdateStudySiteAccrualStatusRequestStudySiteAccrualStatus();
    studySiteAccrualStatusContainer.setStudySiteAccrualStatus(studySiteAccrualStatus);
    params.setStudySiteAccrualStatus(studySiteAccrualStatusContainer);
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.UpdateStudySiteAccrualStatusResponse boxedResult = portType.updateStudySiteAccrualStatus(params);
    return boxedResult.getStudySiteAccrualStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] getStudySiteAccrualStatusByStudySite(gov.nih.nci.coppa.services.pa.Id studySiteId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStudySiteAccrualStatusByStudySite");
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusByStudySiteRequest params = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusByStudySiteRequest();
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusByStudySiteRequestStudySiteId studySiteIdContainer = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusByStudySiteRequestStudySiteId();
    studySiteIdContainer.setId(studySiteId);
    params.setStudySiteId(studySiteIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetStudySiteAccrualStatusByStudySiteResponse boxedResult = portType.getStudySiteAccrualStatusByStudySite(params);
    return boxedResult.getStudySiteAccrualStatus();
    }
  }

  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus getCurrentStudySiteAccrualStatusByStudySite(gov.nih.nci.coppa.services.pa.Id studySiteId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCurrentStudySiteAccrualStatusByStudySite");
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetCurrentStudySiteAccrualStatusByStudySiteRequest params = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetCurrentStudySiteAccrualStatusByStudySiteRequest();
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetCurrentStudySiteAccrualStatusByStudySiteRequestStudySiteId studySiteIdContainer = new gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetCurrentStudySiteAccrualStatusByStudySiteRequestStudySiteId();
    studySiteIdContainer.setId(studySiteId);
    params.setStudySiteId(studySiteIdContainer);
    gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.stubs.GetCurrentStudySiteAccrualStatusByStudySiteResponse boxedResult = portType.getCurrentStudySiteAccrualStatusByStudySite(params);
    return boxedResult.getStudySiteAccrualStatus();
    }
  }

}
