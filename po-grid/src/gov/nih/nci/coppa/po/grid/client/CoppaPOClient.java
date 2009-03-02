package gov.nih.nci.coppa.po.grid.client;

import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;
import gov.nih.nci.coppa.po.grid.common.CoppaPOI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.DSETTEL;
import org.iso._21090.II;
import org.iso._21090.IdentifierReliability;
import org.iso._21090.IdentifierScope;
import org.iso._21090.NullFlavor;
import org.iso._21090.TELEmail;
import org.iso._21090.TelecommunicationAddressUse;
import org.iso._21090.UpdateMode;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security metadata description
 * which it will use to configure the Stub specifically for each method call.
 *
 * @created by Introduce Toolkit version 1.2
 */
public class CoppaPOClient extends CoppaPOClientBase implements CoppaPOI {
    /**
     * The identifier name for org ii's.
     */
    public static final String ORG_IDENTIFIER_NAME = "NCI organization entity identifier";

    /**
     * The ii root value for orgs.
     */
    public static final String ORG_ROOT = "2.16.840.1.113883.3.26.4.2";

    /**
     * The identifier name for person ii's.
     */
    public static final String PERSON_IDENTIFIER_NAME = "NCI person entity identifier";

    /**
     * The ii root value for people.
     */
    public static final String PERSON_ROOT = "2.16.840.1.113883.3.26.4.1";

    public CoppaPOClient(String url) throws MalformedURIException, RemoteException {
        this(url, null);
    }

    public CoppaPOClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url, proxy);
    }

    public CoppaPOClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr, null);
    }

    public CoppaPOClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException,
            RemoteException {
        super(epr, proxy);
    }

    public static void usage() {
        System.out.println(CoppaPOClient.class.getName() + " -url <service url>");
    }

    public static void main(String[] args) {
        System.out.println("Running the Grid Service Client");
        try {
            if (!(args.length < 2)) {
                if (args[0].equals("-url")) {
                    CoppaPOClient client = new CoppaPOClient(args[1]);
                    // place client calls here if you want to use this main as a
                    // test....
                    echoOrg(client);
                    echoPerson(client);
                    getOrg(client);
                    getPerson(client);
                    searchPersons(client);
                    searchOrganizations(client);

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

    private static void echoOrg(CoppaPOClient client) throws RemoteException {
        Organization request = new Organization();
        II id = new II();
        id.setControlActExtension("controlActExtension");
        id.setControlActRoot("controlActRoot");
        id.setDisplayable(Boolean.TRUE);
        id.setExtension("extension");
        id.setFlavorId("flavorId");
        id.setIdentifierName("identifierName");
        id.setNullFlavor(NullFlavor.OTH);
        id.setReliability(IdentifierReliability.USE);
        id.setRoot("root");
        id.setScope(IdentifierScope.VER);
        id.setUpdateMode(UpdateMode.D);
        id.setValidTimeHigh("validTimeHigh");
        id.setValidTimeLow("validTimeLow");
        request.setIdentifier(id);
        System.out.println("Request:");
        System.out.println("=========");
        System.out.println(ToStringBuilder.reflectionToString(request, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(request.getIdentifier(),
                ToStringStyle.MULTI_LINE_STYLE));
        Organization response = client.echoOrganization(request);
        System.out.println("Response:");
        System.out.println("=========");
        System.out.println(ToStringBuilder.reflectionToString(response, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(response.getIdentifier(),
                ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void echoPerson(CoppaPOClient client) throws RemoteException {
        Person request = new Person();
        II id = new II();
        id.setControlActExtension("controlActExtension");
        id.setControlActRoot("controlActRoot");
        id.setDisplayable(Boolean.TRUE);
        id.setExtension("extension");
        id.setFlavorId("flavorId");
        id.setIdentifierName("identifierName");
        id.setNullFlavor(NullFlavor.OTH);
        id.setReliability(IdentifierReliability.USE);
        id.setRoot("root");
        id.setScope(IdentifierScope.VER);
        id.setUpdateMode(UpdateMode.D);
        id.setValidTimeHigh("validTimeHigh");
        id.setValidTimeLow("validTimeLow");
        request.setIdentifier(id);
        TELEmail email = new TELEmail();
        email.setControlActExtension("controlExtention");
        email.setControlActRoot("controleActRoot");
        email.setFlavorId("flavorId");
        email.setUpdateMode(UpdateMode.A);
        email.getUse().add(TelecommunicationAddressUse.AS);
        email.getUse().add(TelecommunicationAddressUse.H);
        request.setTelecomAddress(new DSETTEL());
        request.getTelecomAddress().getItem().add(email);
        System.out.println("Request:");
        System.out.println("=========");
        System.out.println(ToStringBuilder.reflectionToString(request, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(request.getIdentifier(),
                ToStringStyle.MULTI_LINE_STYLE));
        Person response = client.echoPerson(request);
        System.out.println("Response:");
        System.out.println("=========");
        System.out.println(ToStringBuilder.reflectionToString(response, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(ToStringBuilder.reflectionToString(response.getIdentifier(),
                ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void searchPersons(CoppaPOClient client) throws RemoteException {
        Person criteria = new Person();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatusCode(statusCode);
        Person[] searchPersons = client.searchPersons(criteria);
        System.out.println("Search Persons Results Found: " + searchPersons.length);
        for (Person person : searchPersons) {
            print(person);
        }
    }

    private static void searchOrganizations(CoppaPOClient client) throws RemoteException {
        Organization criteria = new Organization();
        CD statusCode = new CD();
        statusCode.setCode("active");
        criteria.setStatusCode(statusCode);
        Organization[] searchOrgs = client.searchOrganizations(criteria);
        System.out.println("Search Orgs Results Found: " + searchOrgs.length);
        for (Organization org : searchOrgs) {
            print(org);
        }
    }


    private static void getPerson(CoppaPOClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(PERSON_ROOT);
        id.setIdentifierName(PERSON_IDENTIFIER_NAME);
        id.setExtension("516");
        Person result = client.getPerson(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void getOrg(CoppaPOClient client) throws RemoteException {
        Id id = new Id();
        id.setRoot(ORG_ROOT);
        id.setIdentifierName(ORG_IDENTIFIER_NAME);
        id.setExtension("537");
        Organization result = client.getOrganization(id);
        System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
    }

    private static void print(Person person) {
        print(person.getIdentifier());
    }

    private static void print(Organization org) {
        print(org.getIdentifier());
    }

    private static void print(II identifier) {
        System.out.println(ToStringBuilder.reflectionToString(identifier));
    }

  public gov.nih.nci.coppa.po.Person getPerson(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getPerson");
    gov.nih.nci.coppa.po.grid.stubs.GetPersonRequest params = new gov.nih.nci.coppa.po.grid.stubs.GetPersonRequest();
    gov.nih.nci.coppa.po.grid.stubs.GetPersonRequestIdentifier identifierContainer = new gov.nih.nci.coppa.po.grid.stubs.GetPersonRequestIdentifier();
    identifierContainer.setId(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.coppa.po.grid.stubs.GetPersonResponse boxedResult = portType.getPerson(params);
    return boxedResult.getPerson();
    }
  }

  public gov.nih.nci.coppa.po.Organization getOrganization(gov.nih.nci.coppa.po.Id identifier) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getOrganization");
    gov.nih.nci.coppa.po.grid.stubs.GetOrganizationRequest params = new gov.nih.nci.coppa.po.grid.stubs.GetOrganizationRequest();
    gov.nih.nci.coppa.po.grid.stubs.GetOrganizationRequestIdentifier identifierContainer = new gov.nih.nci.coppa.po.grid.stubs.GetOrganizationRequestIdentifier();
    identifierContainer.setId(identifier);
    params.setIdentifier(identifierContainer);
    gov.nih.nci.coppa.po.grid.stubs.GetOrganizationResponse boxedResult = portType.getOrganization(params);
    return boxedResult.getOrganization();
    }
  }

  public gov.nih.nci.coppa.po.Organization[] searchOrganizations(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"searchOrganizations");
    gov.nih.nci.coppa.po.grid.stubs.SearchOrganizationsRequest params = new gov.nih.nci.coppa.po.grid.stubs.SearchOrganizationsRequest();
    gov.nih.nci.coppa.po.grid.stubs.SearchOrganizationsRequestOrganization organizationContainer = new gov.nih.nci.coppa.po.grid.stubs.SearchOrganizationsRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.po.grid.stubs.SearchOrganizationsResponse boxedResult = portType.searchOrganizations(params);
    return boxedResult.getOrganization();
    }
  }

  public gov.nih.nci.coppa.po.Person[] searchPersons(gov.nih.nci.coppa.po.Person person) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"searchPersons");
    gov.nih.nci.coppa.po.grid.stubs.SearchPersonsRequest params = new gov.nih.nci.coppa.po.grid.stubs.SearchPersonsRequest();
    gov.nih.nci.coppa.po.grid.stubs.SearchPersonsRequestPerson personContainer = new gov.nih.nci.coppa.po.grid.stubs.SearchPersonsRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.po.grid.stubs.SearchPersonsResponse boxedResult = portType.searchPersons(params);
    return boxedResult.getPerson();
    }
  }

  public gov.nih.nci.coppa.po.Person echoPerson(gov.nih.nci.coppa.po.Person person) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"echoPerson");
    gov.nih.nci.coppa.po.grid.stubs.EchoPersonRequest params = new gov.nih.nci.coppa.po.grid.stubs.EchoPersonRequest();
    gov.nih.nci.coppa.po.grid.stubs.EchoPersonRequestPerson personContainer = new gov.nih.nci.coppa.po.grid.stubs.EchoPersonRequestPerson();
    personContainer.setPerson(person);
    params.setPerson(personContainer);
    gov.nih.nci.coppa.po.grid.stubs.EchoPersonResponse boxedResult = portType.echoPerson(params);
    return boxedResult.getPerson();
    }
  }

  public gov.nih.nci.coppa.po.Organization echoOrganization(gov.nih.nci.coppa.po.Organization organization) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"echoOrganization");
    gov.nih.nci.coppa.po.grid.stubs.EchoOrganizationRequest params = new gov.nih.nci.coppa.po.grid.stubs.EchoOrganizationRequest();
    gov.nih.nci.coppa.po.grid.stubs.EchoOrganizationRequestOrganization organizationContainer = new gov.nih.nci.coppa.po.grid.stubs.EchoOrganizationRequestOrganization();
    organizationContainer.setOrganization(organization);
    params.setOrganization(organizationContainer);
    gov.nih.nci.coppa.po.grid.stubs.EchoOrganizationResponse boxedResult = portType.echoOrganization(params);
    return boxedResult.getOrganization();
    }
  }

}
