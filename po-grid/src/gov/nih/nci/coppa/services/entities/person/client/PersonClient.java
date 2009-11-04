package gov.nih.nci.coppa.services.entities.person.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.entities.person.common.PersonI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.ENPN;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;

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
public class PersonClient extends PersonClientBase implements PersonI {
    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = "2.16.840.1.113883.3.26.4.1";

    public PersonClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public PersonClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public PersonClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public PersonClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(PersonClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              PersonClient client = new PersonClient(args[1]);
              // place client calls here if you want to use this main as a
              // test....

              System.out.println("Getting Person");
              getPerson(client);
              System.out.println("Getting Nullified Person");
              getNullifiedPerson(client);
              System.out.println("Searching for Persons");
              searchPersons(client);
              System.out.println("NEW!! Searching for Persons");
              queryPersons(client);
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

    private static void searchPersons(PersonClient client) throws RemoteException {
        Person criteria = createCriteria();
        gov.nih.nci.coppa.po.Person[] results = client.search(criteria);
        ClientUtils.handleSearchResults(results);
    }
    private static void queryPersons(PersonClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(2);
        limitOffset.setOffset(0);
        Person criteria = createCriteria();
        gov.nih.nci.coppa.po.Person[] results = client.query(criteria, limitOffset);
        ClientUtils.handleSearchResults(results);
    }

    private static Person createCriteria() {
        Person criteria = new Person();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatusCode(statusCode);
        ENPN enpn = new ENPN();
        ENXP enxp = new ENXP();
        enxp.setValue("Jones");
        enxp.setType(EntityNamePartType.FAM);
        enpn.getPart().add(enxp);
        criteria.setName(enpn);
        return criteria;
    }

    private static void getPerson(PersonClient client) throws RemoteException {
        Id id = createII();
        Person result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    private static void getNullifiedPerson(PersonClient client) throws RemoteException {
        try {
            Id id = createII();
            Person result = client.getById(id);
            ClientUtils.handleResult(result);
        } catch (NullifiedEntityFault e) {
            System.out.println("NullifiedEntityFault");
            e.printStackTrace(System.out);
        } catch (RemoteException e) {
            throw e;
        }
    }

    private static Id createII() {
        Id id = new Id();
        id.setRoot(PERSON_ROOT);
        id.setIdentifierName(PERSON_IDENTIFIER_NAME);
        id.setExtension("501");
        return id;
    }

  public gov.nih.nci.coppa.po.Person getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.entities.person.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.entities.person.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getPerson();
    }
  }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.entities.person.stubs.CreateRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.CreateRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.CreateRequestPerson personContainer = new gov.nih.nci.coppa.services.entities.person.stubs.CreateRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Person person) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.entities.person.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.ValidateRequestPerson personContainer = new gov.nih.nci.coppa.services.entities.person.stubs.ValidateRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }

  public gov.nih.nci.coppa.po.Person[] search(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.entities.person.stubs.SearchRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.SearchRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.SearchRequestPerson personContainer = new gov.nih.nci.coppa.services.entities.person.stubs.SearchRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getPerson();
    }
  }

  public void update(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateRequestPerson personContainer = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.Person[] query(gov.nih.nci.coppa.po.Person person,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"query");
    gov.nih.nci.coppa.services.entities.person.stubs.QueryRequest params = new gov.nih.nci.coppa.services.entities.person.stubs.QueryRequest();
    gov.nih.nci.coppa.services.entities.person.stubs.QueryRequestPerson personContainer = new gov.nih.nci.coppa.services.entities.person.stubs.QueryRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.QueryRequestLimitOffset limitOffsetContainer = new gov.nih.nci.coppa.services.entities.person.stubs.QueryRequestLimitOffset();
    limitOffsetContainer.setLimitOffset(limitOffset);
    params.setLimitOffset(limitOffsetContainer);
    gov.nih.nci.coppa.services.entities.person.stubs.QueryResponse boxedResult = portType.query(params);
    return boxedResult.getPerson();
    }
  }

}
