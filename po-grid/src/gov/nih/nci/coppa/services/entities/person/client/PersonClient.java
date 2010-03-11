package gov.nih.nci.coppa.services.entities.person.client;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.client.util.ClientParameterHelper;
import gov.nih.nci.coppa.services.entities.person.common.PersonI;
import gov.nih.nci.coppa.services.grid.util.GridTestMethod;
import gov.nih.nci.iso21090.Constants;
import gov.nih.nci.iso21090.extensions.Id;

import java.lang.reflect.Method;
import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.AD;
import org.iso._21090.ADXP;
import org.iso._21090.AddressPartType;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.iso._21090.ENPN;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.TELEmail;

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

    private static ClientParameterHelper<PersonClient> helper =
        new ClientParameterHelper<PersonClient>(PersonClient.class);

    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = Constants.NCI_OID + ".1";

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

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
            String[] localArgs = new String[] {"-getId"};
            helper.setLocalArgs(localArgs);
            helper.setupParams(args);

            PersonClient client = new PersonClient(helper.getArgument("-url"));

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
    private static void queryPersons(PersonClient client) throws RemoteException {
        LimitOffset limitOffset = new LimitOffset();
        limitOffset.setLimit(1);
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

    @GridTestMethod
    private static void getPerson(PersonClient client) throws RemoteException {
        Id id = createII();
        Person result = client.getById(id);
        ClientUtils.handleResult(result);
    }

    @GridTestMethod
    private static void getNullifiedPerson(PersonClient client) throws RemoteException {
        try {
            Id id = createII();
            Person result = client.getById(id);
            ClientUtils.handleResult(result);
        } catch (NullifiedEntityFault e) {
            System.out.println("NullifiedEntityFault");
            e.printStackTrace(System.out);
        }
    }

    @GridTestMethod
    private static void createPerson(PersonClient client) throws RemoteException {
        Person person = new Person();
        ENPN enpn = new ENPN();
        ENXP enxp = new ENXP();
        enxp.setValue("Jones");
        enxp.setType(EntityNamePartType.FAM);
        enpn.getPart().add(enxp);
        enxp = new ENXP();
        enxp.setValue("Fred");
        enxp.setType(EntityNamePartType.GIV);
        enpn.getPart().add(enxp);
        person.setName(enpn);

        TELEmail email = new TELEmail();
        email.setValue("mailto:foo@example.com");
        DSETTEL tels = new DSETTEL();
        tels.getItem().add(email);
        person.setTelecomAddress(tels);

        AD ad = new AD();
        ADXP adxp = new ADXP();
        adxp.setValue("1 st");
        adxp.setType(AddressPartType.SAL);
        ad.getPart().add(adxp);
        adxp = new ADXP();
        adxp.setValue("Washington");
        adxp.setType(AddressPartType.CTY);
        ad.getPart().add(adxp);
        adxp = new ADXP();
        adxp.setValue("DC");
        adxp.setType(AddressPartType.STA);
        ad.getPart().add(adxp);
        adxp = new ADXP();
        adxp.setValue("United States");
        adxp.setCode("USA");
        adxp.setCodeSystem("ISO 3166-1 alpha-3");
        adxp.setType(AddressPartType.CNT);
        ad.getPart().add(adxp);
        adxp = new ADXP();
        adxp.setValue("20009");
        adxp.setType(AddressPartType.ZIP);
        ad.getPart().add(adxp);
        person.setPostalAddress(ad);

        ClientUtils.handleResult(client.create(person));
    }

    private static Id createII() {
        Id id = new Id();
        id.setRoot(PERSON_ROOT);
        id.setIdentifierName(PERSON_IDENTIFIER_NAME);
        id.setExtension(helper.getArgument("-getId", "1"));
        return id;
    }

  public gov.nih.nci.coppa.po.Person getById(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
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

  public gov.nih.nci.iso21090.extensions.Id create(gov.nih.nci.coppa.po.Person person) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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

  public void updateStatus(gov.nih.nci.iso21090.extensions.Id targetId,gov.nih.nci.iso21090.extensions.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
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
