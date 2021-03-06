package gov.nih.nci.coppa.services.pa.diseaseservice.client;

import gov.nih.nci.coppa.services.pa.Disease;
import gov.nih.nci.coppa.services.pa.diseaseservice.common.DiseaseServiceI;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.iso21090.extensions.Id;

import java.rmi.RemoteException;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.globus.gsi.GlobusCredential;
import org.iso._21090.ST;

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
public class DiseaseServiceClient extends DiseaseServiceClientBase implements DiseaseServiceI {

    public DiseaseServiceClient(String url) throws MalformedURIException, RemoteException {
        this(url,null);
    }

    public DiseaseServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(url,proxy);
    }

    public DiseaseServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
           this(epr,null);
    }

    public DiseaseServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
           super(epr,proxy);
    }

    public static void usage(){
        System.out.println(DiseaseServiceClient.class.getName() + " -url <service url>");
    }

    public static void main(String [] args){
        System.out.println("Running the Grid Service Client");
        try{
        if(!(args.length < 2)){
            if(args[0].equals("-url")){
              DiseaseServiceClient client = new DiseaseServiceClient(args[1]);
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

    private static void getById(DiseaseServiceClient client) throws RemoteException, PAFault {
        System.out.println("Getting disease by ID");
        Id id = new Id();
        id.setExtension("105");
        printResults(client.get(id));
    }

    private static ST newST(String s) {
        ST st = new ST();
        st.setValue(s);
        return st;
    }

    private static void search(DiseaseServiceClient client) throws RemoteException, PAFault {
        System.out.println("Search for diseases");
        Disease disease = new Disease();
        disease.setPreferredName(newST("cancer"));
        disease.setIncludeSynonym(newST("true"));
        disease.setExactMatch(newST("true"));

        printResults(client.search(disease));
    }

    private static void printResults(Disease... results) {
        if (results == null || (results.length == 1 && results[0] == null)) {
            System.out.println("No results");
        } else {
            System.out.println("Found " + results.length + " result(s)");
            for (int i = 0; i < results.length; i++) {
                System.out.println(ToStringBuilder.reflectionToString(results[i], ToStringStyle.MULTI_LINE_STYLE));
            }
        }
    }

  public gov.nih.nci.coppa.services.pa.Disease get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"get");
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.GetRequest params = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.GetRequest();
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.GetRequestId idContainer = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.GetRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.GetResponse boxedResult = portType.get(params);
    return boxedResult.getDisease();
    }
  }

  public gov.nih.nci.coppa.services.pa.Disease create(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.CreateRequest params = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.CreateRequest();
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.CreateRequestDisease diseaseContainer = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.CreateRequestDisease();
    diseaseContainer.setDisease(disease);
    params.setDisease(diseaseContainer);
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getDisease();
    }
  }

  public gov.nih.nci.coppa.services.pa.Disease update(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.UpdateRequestDisease diseaseContainer = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.UpdateRequestDisease();
    diseaseContainer.setDisease(disease);
    params.setDisease(diseaseContainer);
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.UpdateResponse boxedResult = portType.update(params);
    return boxedResult.getDisease();
    }
  }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"delete");
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.DeleteRequest params = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.DeleteRequest();
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.DeleteRequestId idContainer = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.DeleteRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.DeleteResponse boxedResult = portType.delete(params);
    }
  }

  public gov.nih.nci.coppa.services.pa.Disease[] search(gov.nih.nci.coppa.services.pa.Disease searchCriteria) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.SearchRequest params = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.SearchRequest();
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.SearchRequestSearchCriteria searchCriteriaContainer = new gov.nih.nci.coppa.services.pa.diseaseservice.stubs.SearchRequestSearchCriteria();
    searchCriteriaContainer.setDisease(searchCriteria);
    params.setSearchCriteria(searchCriteriaContainer);
    gov.nih.nci.coppa.services.pa.diseaseservice.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getDisease();
    }
  }

}
