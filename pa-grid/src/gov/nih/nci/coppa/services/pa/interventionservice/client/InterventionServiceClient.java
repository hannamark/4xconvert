package gov.nih.nci.coppa.services.pa.interventionservice.client;

import gov.nih.nci.coppa.services.pa.BaseType;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.Intervention;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.interventionservice.common.InterventionServiceI;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.ST;

/**
 * Disease service implementation.  Translates XML-level objects to DTOs to call EJBs.
 *
 */
public class InterventionServiceClient extends InterventionServiceClientBase implements InterventionServiceI {

	public InterventionServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);
	}

	public InterventionServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}

	public InterventionServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}

	public InterventionServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(InterventionServiceClient.class.getName() + " -url <service url>");
	}

	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  InterventionServiceClient client = new InterventionServiceClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			  getById(client);
			  search(client);
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

	private static void getById(InterventionServiceClient client) throws RemoteException, PAFault {
        System.out.println("Getting Intervention by ID: 2043");
        Id id = new Id();
        id.setExtension("2043");
        printResults(client.get(id));
    }

    private static void search(InterventionServiceClient client) throws RemoteException, PAFault {
        System.out.println("Search for interventions by name=thymosin");
        Intervention intervention = new Intervention();
        ST name = new ST();
        name.setValue("thymosin");
        intervention.setName(name);

        ST falseSt = new ST();
        falseSt.setValue("true");
        intervention.setExactMatch(falseSt);
        intervention.setIncludeSynonym(falseSt);
        printResults(client.search(intervention));
    }

    private static void printResults(BaseType... results) {
        if (results == null || (results.length == 1 && results[0] == null)) {
            System.out.println("No results");
        } else {
            System.out.println("Found " + results.length + " result(s)");
            for (int i = 0; i < results.length; i++) {
                System.out.println(ToStringBuilder.reflectionToString(results[i], ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }



  public gov.nih.nci.coppa.services.pa.Intervention get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getIntervention();
    }
  }

  public gov.nih.nci.coppa.services.pa.Intervention create(gov.nih.nci.coppa.services.pa.Intervention intervention) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.CreateRequestIntervention interventionContainer = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.CreateRequestIntervention();
    interventionContainer.setIntervention(intervention);
    params.setIntervention(interventionContainer);
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getIntervention();
    }
  }

  public gov.nih.nci.coppa.services.pa.Intervention update(gov.nih.nci.coppa.services.pa.Intervention intervention) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.UpdateRequestIntervention interventionContainer = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.UpdateRequestIntervention();
    interventionContainer.setIntervention(intervention);
    params.setIntervention(interventionContainer);
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getIntervention();
    }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.Intervention[] search(gov.nih.nci.coppa.services.pa.Intervention searchCriteria) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.SearchRequest params = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.SearchRequest();
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.SearchRequestSearchCriteria searchCriteriaContainer = new gov.nih.nci.coppa.services.pa.interventionservice.stubs.SearchRequestSearchCriteria();
    searchCriteriaContainer.setIntervention(searchCriteria);
    params.setSearchCriteria(searchCriteriaContainer);
    gov.nih.nci.coppa.services.pa.interventionservice.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getIntervention();
    }
  }

}
