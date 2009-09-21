package gov.nih.nci.coppa.services.pa.plannedactivityservice.client;

import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.PlannedActivityType;
import gov.nih.nci.coppa.services.pa.plannedactivityservice.common.PlannedActivityServiceI;

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
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 *
 * @created by Introduce Toolkit version 1.3
 */
public class PlannedActivityServiceClient extends PlannedActivityServiceClientBase implements PlannedActivityServiceI {

    public PlannedActivityServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }

    public PlannedActivityServiceClient(String url, GlobusCredential proxy) throws MalformedURIException,
            RemoteException {
        super(url, proxy);
    }

    public PlannedActivityServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }

    public PlannedActivityServiceClient(EndpointReferenceType epr, GlobusCredential proxy)
            throws MalformedURIException, RemoteException {
        super(epr, proxy);
    }

    public static void usage() {
        System.out.println(PlannedActivityServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    PlannedActivityServiceClient client = new PlannedActivityServiceClient(args[1]);

                    getById(client);
                    getByStudyProtocol(client);
                    getPlannedEligibilityCriterion(client);
                    getPlannedEligibilityCriterionByStudyProtocol(client);
                    getByArm(client);
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

    private static void getById(PlannedActivityServiceClient client) throws RemoteException {
        System.out.println("Getting planned activity by id");
        Id id = new Id();
        id.setExtension("27450");
        printResults(client.get(id));
    }

    private static void getByArm(PlannedActivityServiceClient client) throws RemoteException {
        System.out.println("Getting planned activity by Arm");
        Id armId = new Id();
        armId.setExtension("180412");
        printResults(client.get(armId));
    }

    private static void getByStudyProtocol(PlannedActivityServiceClient client) throws RemoteException {
        System.out.println("Getting planned activity by study protocol");
        Id id = new Id();
        id.setExtension("27426");
        printResults(client.getByStudyProtocol(id));
    }

    private static void getPlannedEligibilityCriterion(PlannedActivityServiceClient client) throws RemoteException {
        System.out.println("Getting planned eligibility criterion by id");
        Id id = new Id();
        id.setExtension("27452");
        printResults(client.getPlannedEligibilityCriterion(id));
    }

    private static void getPlannedEligibilityCriterionByStudyProtocol(PlannedActivityServiceClient client)
            throws RemoteException {
        System.out.println("Getting planned eligibility criterion by study protocol");
        Id id = new Id();
        id.setExtension("27426");
        printResults(client.getPlannedEligibilityCriterionByStudyProtocol(id));
    }

    private static void printResults(PlannedActivityType... results) {
        if (results != null) {
            System.out.println("search found " + results.length + " results");
            for (int i = 0; i < results.length; i++) {
                System.out.println(ToStringBuilder.reflectionToString(results[i], ToStringStyle.MULTI_LINE_STYLE));
            }
        } else {
            System.out.println("search found no results");
        }
    }

  public gov.nih.nci.coppa.services.pa.PlannedActivity[] getByArm(gov.nih.nci.coppa.services.pa.Id armId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByArm");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByArmRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByArmRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByArmRequestArmId armIdContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByArmRequestArmId();
    armIdContainer.setId(armId);
    params.setArmId(armIdContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByArmResponse boxedResult = portType.getByArm(params);
    return boxedResult.getPlannedActivity();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion[] getPlannedEligibilityCriterionByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPlannedEligibilityCriterionByStudyProtocol");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionByStudyProtocolResponse boxedResult = portType.getPlannedEligibilityCriterionByStudyProtocol(params);
    return boxedResult.getPlannedEligibilityCriterion();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion getPlannedEligibilityCriterion(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPlannedEligibilityCriterion");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetPlannedEligibilityCriterionResponse boxedResult = portType.getPlannedEligibilityCriterion(params);
    return boxedResult.getPlannedEligibilityCriterion();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion createPlannedEligibilityCriterion(gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion plannedEligibilityCriterion) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createPlannedEligibilityCriterion");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreatePlannedEligibilityCriterionRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreatePlannedEligibilityCriterionRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreatePlannedEligibilityCriterionRequestPlannedEligibilityCriterion plannedEligibilityCriterionContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreatePlannedEligibilityCriterionRequestPlannedEligibilityCriterion();
    plannedEligibilityCriterionContainer.setPlannedEligibilityCriterion(plannedEligibilityCriterion);
    params.setPlannedEligibilityCriterion(plannedEligibilityCriterionContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreatePlannedEligibilityCriterionResponse boxedResult = portType.createPlannedEligibilityCriterion(params);
    return boxedResult.getPlannedEligibilityCriterion();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion updatePlannedEligibilityCriterion(gov.nih.nci.coppa.services.pa.PlannedEligibilityCriterion plannedEligibilityCriterion) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updatePlannedEligibilityCriterion");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdatePlannedEligibilityCriterionRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdatePlannedEligibilityCriterionRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdatePlannedEligibilityCriterionRequestPlannedEligibilityCriterion plannedEligibilityCriterionContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdatePlannedEligibilityCriterionRequestPlannedEligibilityCriterion();
    plannedEligibilityCriterionContainer.setPlannedEligibilityCriterion(plannedEligibilityCriterion);
    params.setPlannedEligibilityCriterion(plannedEligibilityCriterionContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdatePlannedEligibilityCriterionResponse boxedResult = portType.updatePlannedEligibilityCriterion(params);
    return boxedResult.getPlannedEligibilityCriterion();
    }
  }

  public void deletePlannedEligibilityCriterion(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"deletePlannedEligibilityCriterion");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeletePlannedEligibilityCriterionRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeletePlannedEligibilityCriterionRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeletePlannedEligibilityCriterionRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeletePlannedEligibilityCriterionRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeletePlannedEligibilityCriterionResponse boxedResult = portType.deletePlannedEligibilityCriterion(params);
    }
  }

  public void copyPlannedEligibilityStudyCriterions(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copyPlannedEligibilityStudyCriterions");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyPlannedEligibilityStudyCriterionsResponse boxedResult = portType.copyPlannedEligibilityStudyCriterions(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedActivity[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getPlannedActivity();
    }
  }

  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"copy");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequestFromStudyProtocolId fromStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequestFromStudyProtocolId();
    fromStudyProtocolIdContainer.setId(fromStudyProtocolId);
    params.setFromStudyProtocolId(fromStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequestToStudyProtocolId toStudyProtocolIdContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyRequestToStudyProtocolId();
    toStudyProtocolIdContainer.setId(toStudyProtocolId);
    params.setToStudyProtocolId(toStudyProtocolIdContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CopyResponse boxedResult = portType.copy(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedActivity get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getPlannedActivity();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedActivity create(gov.nih.nci.coppa.services.pa.PlannedActivity plannedActivity) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreateRequestPlannedActivity plannedActivityContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreateRequestPlannedActivity();
    plannedActivityContainer.setPlannedActivity(plannedActivity);
    params.setPlannedActivity(plannedActivityContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getPlannedActivity();
    }
  }

  public gov.nih.nci.coppa.services.pa.PlannedActivity update(gov.nih.nci.coppa.services.pa.PlannedActivity plannedActivity) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdateRequestPlannedActivity plannedActivityContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdateRequestPlannedActivity();
    plannedActivityContainer.setPlannedActivity(plannedActivity);
    params.setPlannedActivity(plannedActivityContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getPlannedActivity();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.plannedactivityservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
