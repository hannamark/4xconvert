package gov.nih.nci.coppa.services.outcomes.studysubject.client;

import gov.nih.nci.coppa.services.outcomes.BaseType;
import gov.nih.nci.coppa.services.outcomes.Id;
import gov.nih.nci.coppa.services.outcomes.StudySubject;
import gov.nih.nci.coppa.services.outcomes.studysubject.common.StudySubjectI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.II;

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
public class StudySubjectClient extends StudySubjectClientBase implements StudySubjectI {

    private static final String studyProtocolIdentifier = "10001";
    private static final String studySiteIdentifier = "20001";
    private static final String patientIdentifier = "30001";
    private static final String studySubjectIdentifier = "40001";
    private static final String outcomesLoginName = "newUser";

	public StudySubjectClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);
	}

	public StudySubjectClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}

	public StudySubjectClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}

	public StudySubjectClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(StudySubjectClient.class.getName() + " -url <service url>");
	}

	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client - StudySubjectClient");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  StudySubjectClient client = new StudySubjectClient(args[1]);
			  handleResults(createStudySubject(client));
			  getStudySubject(client);
			  getByStudyProtocol(client);
			  getByStudySite(client);
			  updateStudySubject(client);
			  deleteStudySubject(client);
			  createOutcomes(client);
			  getByOutcomesLoginName(client);
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

	private static StudySubject createStudySubject(StudySubjectClient client) throws RemoteException {
	    System.out.println("StudySubjectClient - In createStudySubject()");
	    StudySubject ss = getNewStudySubject();
	    StudySubject newSS = client.create(ss);
	    return newSS;
	}

    private static void createOutcomes(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In createOutcomes()");
        StudySubject ss = getNewStudySubject();
        org.iso._21090.ST loginName = new org.iso._21090.ST();
        loginName.setValue(StudySubjectClient.outcomesLoginName);
        ss.setOutcomesLoginName(loginName);

        StudySubject newSS = client.createOutcomes(ss);
        handleResults(newSS);
    }

	private static StudySubject getNewStudySubject() {
	    StudySubject ss = new StudySubject();

        org.iso._21090.CD statusCode = new org.iso._21090.CD();
        statusCode.setCode("Active");
        ss.setStatusCode(statusCode);

        II studyProtocolIdentifier = new II();
        studyProtocolIdentifier.setExtension(StudySubjectClient.studyProtocolIdentifier);
        ss.setStudyProtocolIdentifier(studyProtocolIdentifier);

        II studySiteIdentiier = new II();
        studySiteIdentiier.setExtension(StudySubjectClient.studySiteIdentifier);
        ss.setStudySiteIdentifier(studySiteIdentiier);

        II patientIdentifer = new II();
        patientIdentifer.setExtension(StudySubjectClient.patientIdentifier);
        ss.setPatientIdentifier(patientIdentifer);

        return ss;
	}

	private static void getStudySubject(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In getStudySubject()");
        Id id = new Id();
        id.setExtension(StudySubjectClient.studySubjectIdentifier);
        StudySubject ss = client.get(id);
        handleResults(ss);
    }

	private static void deleteStudySubject(StudySubjectClient client) throws RemoteException {
	    System.out.println("StudySubjectClient - In deleteStudySubject()");
	    StudySubject newSS = createStudySubject(client);
	    System.out.println("New Study Subject Id = " + newSS.getIdentifier().toString());

	    // delete.
	    Id id = new Id();
	    id.setExtension(newSS.getIdentifier().getExtension());
        client.delete(id);
    }

    private static void updateStudySubject(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In updateStudySubject()");
        Id id = new Id();
        id.setExtension(StudySubjectClient.studySubjectIdentifier);
        StudySubject ss = client.get(id);
        org.iso._21090.CD statusCode = new org.iso._21090.CD();
        if (ss.getStatusCode().getCode().equalsIgnoreCase("active")) {
            statusCode.setCode("Pending");
        } else {
            statusCode.setCode("Active");
        }
        ss.setStatusCode(statusCode);

        StudySubject updatedSS = client.update(ss);
        System.out.println("Study Subject Id=" + updatedSS.getIdentifier().getExtension() + ", Status=" + updatedSS.getStatusCode().getCode() + ", ");
    }

	private static void getByStudyProtocol(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In getByStudyProtocol()");
        Id id = new Id();
        id.setExtension(StudySubjectClient.studyProtocolIdentifier);
        StudySubject[] ss = client.getByStudyProtocol(id);
        handleResults(ss);
    }

	private static void getByStudySite(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In getByStudySite()");
        Id id = new Id();
        id.setExtension(StudySubjectClient.studySiteIdentifier);
        StudySubject[] ss = client.getByStudySite(id);
        handleResults(ss);
    }

	private static void getByOutcomesLoginName(StudySubjectClient client) throws RemoteException {
        System.out.println("StudySubjectClient - In getByOutcomesLoginName()");
        gov.nih.nci.coppa.services.outcomes.ST outcomesLoginName = new gov.nih.nci.coppa.services.outcomes.ST();
        outcomesLoginName.setValue(StudySubjectClient.outcomesLoginName);
        StudySubject[] ss = client.getOutcomes(outcomesLoginName);
        handleResults(ss);
    }

	private static void handleResults(BaseType... results)  {
        if (results == null || (results.length == 1 && results[0] == null)) {
            System.out.println("search found no results");
        } else {
            System.out.println("search found " + results.length + " results");
            for (int i = 0; i < results.length; i++) {
                System.out.println(ToStringBuilder.reflectionToString(results[i], ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getByStudySite(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudySite");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudySiteRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudySiteRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudySiteRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudySiteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudySiteResponse boxedResult = portType.getByStudySite(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getOutcomes(gov.nih.nci.coppa.services.outcomes.ST outcomesLoginName) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getOutcomes");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetOutcomesRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetOutcomesRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetOutcomesRequestOutcomesLoginName outcomesLoginNameContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetOutcomesRequestOutcomesLoginName();
    outcomesLoginNameContainer.setST(outcomesLoginName);
    params.setOutcomesLoginName(outcomesLoginNameContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetOutcomesResponse boxedResult = portType.getOutcomes(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject createOutcomes(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"createOutcomes");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateOutcomesRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateOutcomesRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateOutcomesRequestStudySubject studySubjectContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateOutcomesRequestStudySubject();
    studySubjectContainer.setStudySubject(studySubject);
    params.setStudySubject(studySubjectContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateOutcomesResponse boxedResult = portType.createOutcomes(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject[] getByStudyProtocol(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByStudyProtocol");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudyProtocolRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudyProtocolRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudyProtocolRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudyProtocolRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetByStudyProtocolResponse boxedResult = portType.getByStudyProtocol(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject create(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateRequestStudySubject studySubjectContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateRequestStudySubject();
    studySubjectContainer.setStudySubject(studySubject);
    params.setStudySubject(studySubjectContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getStudySubject();
    }
  }

  public gov.nih.nci.coppa.services.outcomes.StudySubject update(gov.nih.nci.coppa.services.outcomes.StudySubject studySubject) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.UpdateRequestStudySubject studySubjectContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.UpdateRequestStudySubject();
    studySubjectContainer.setStudySubject(studySubject);
    params.setStudySubject(studySubjectContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getStudySubject();
    }
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.outcomes.studysubject.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.outcomes.studysubject.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

}
