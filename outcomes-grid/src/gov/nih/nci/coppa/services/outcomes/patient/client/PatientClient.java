package gov.nih.nci.coppa.services.outcomes.patient.client;

import gov.nih.nci.coppa.services.outcomes.BaseType;
import gov.nih.nci.coppa.services.outcomes.Id;
import gov.nih.nci.coppa.services.outcomes.Patient;
import gov.nih.nci.coppa.services.outcomes.patient.common.PatientI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;
import org.iso._21090.II;
import org.iso._21090.TS;

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
public class PatientClient extends PatientClientBase implements PatientI {

    public PatientClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public PatientClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(url,proxy);
    }

    public PatientClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
        this(epr,null);
    }

    public PatientClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
        super(epr,proxy);
    }

    public static void usage(){
        System.out.println(PatientClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client - PatientService");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
                PatientClient client = new PatientClient(args[1]);
                Patient newPatient = createPatient(client);
                Patient freshPatient = getPatient(client, newPatient.getIdentifier().getExtension());
                updatePatient(client, freshPatient);
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

    private static Patient createPatient(PatientClient client) throws RemoteException {
        System.out.println("PatientClient - In createPatient()");
        Patient patient = new Patient();

        TS birthDate = new TS();
        birthDate.setValue("19800928023033.0978-0000");
        patient.setBirthDate(birthDate);
        patient.setStatusDateRangeLow(null);
        patient.setZip(null);

        II orgId = new II();
        orgId.setExtension("501");
        patient.setOrganizationIdentifier(orgId);

        CD genderCode = new CD();
        genderCode.setCode("Male");
        patient.setGenderCode(genderCode);

        CD ethnicCode = new CD();
        ethnicCode.setCode("Not_Reported");
        patient.setEthnicCode(ethnicCode);

        CD statusCode = new CD();
        statusCode.setCode("Active");
        patient.setStatusCode(statusCode);

        II country = new II();
        country.setExtension("901");
        patient.setCountryIdentifier(country);

        Patient newPatient = client.create(patient);
        handleResults(newPatient);
        return newPatient;
    }

    private static Patient getPatient(PatientClient client, String idExtension) throws RemoteException {
        System.out.println("PatientClient - In getPatient()");
        Id id = new Id();
        id.setExtension(idExtension);
        Patient patient = client.get(id);
        handleResults(patient);
        return patient;
    }

    private static void updatePatient(PatientClient client, Patient patient) throws RemoteException {
        System.out.println("PatientClient - In UpdatePatient()");

        CD genderCode = new CD();
        genderCode.setCode("Female");
        patient.setGenderCode(genderCode);

        CD ethnicCode = new CD();
        ethnicCode.setCode("Hispanic_or_Latino");
        patient.setEthnicCode(ethnicCode);

        II orgId = new II();
        orgId.setExtension("501");
        patient.setOrganizationIdentifier(orgId);

        CD statusCode = new CD();
        statusCode.setCode("Cancelled");
        patient.setStatusCode(statusCode);

        Patient updatedPatient = client.update(patient);
        handleResults(updatedPatient);
    }

    private static void handleResults(BaseType... results) {
        if (results == null || (results.length == 1 && results[0] == null)) {
            System.out.println("search found no results");
        } else {
            System.out.println("search found " + results.length + " results");
            for (int i = 0; i < results.length; i++) {
                Patient pat = (Patient) results[i];
                StringBuffer s = new StringBuffer();
                s.append("Id=").append(pat.getIdentifier().getExtension()).append(", Gender=").append(pat.getGenderCode().getCode())
                .append(", Ethnicity=").append(pat.getEthnicCode().getCode()).append(", StatusCode=").append(pat.getStatusCode().getCode());
                System.out.println(s.toString());
            }
        }
    }

  public gov.nih.nci.coppa.services.outcomes.Patient get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.outcomes.patient.stubs.GetRequest params = new gov.nih.nci.coppa.services.outcomes.patient.stubs.GetRequest();
    gov.nih.nci.coppa.services.outcomes.patient.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.patient.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.patient.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getPatient();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.Patient create(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.outcomes.patient.stubs.CreateRequest params = new gov.nih.nci.coppa.services.outcomes.patient.stubs.CreateRequest();
    gov.nih.nci.coppa.services.outcomes.patient.stubs.CreateRequestPatient patientContainer = new gov.nih.nci.coppa.services.outcomes.patient.stubs.CreateRequestPatient();
    patientContainer.setPatient(patient);
    params.setPatient(patientContainer);
    gov.nih.nci.coppa.services.outcomes.patient.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getPatient();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.Patient update(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.outcomes.patient.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.outcomes.patient.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.outcomes.patient.stubs.UpdateRequestPatient patientContainer = new gov.nih.nci.coppa.services.outcomes.patient.stubs.UpdateRequestPatient();
    patientContainer.setPatient(patient);
    params.setPatient(patientContainer);
    gov.nih.nci.coppa.services.outcomes.patient.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getPatient();
    }
  }

}
