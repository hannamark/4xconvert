package gov.nih.nci.coppa.services.business.business.client;

import gov.nih.nci.coppa.po.Bl;
import gov.nih.nci.coppa.po.Cd;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.EntityNode;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.grid.client.ClientUtils;
import gov.nih.nci.coppa.services.business.business.common.BusinessI;
import gov.nih.nci.coppa.services.entities.person.client.PersonClient;
import gov.nih.nci.coppa.services.structuralroles.healthcareprovider.client.HealthCareProviderClient;
import gov.nih.nci.services.RoleList;

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
public class BusinessClient extends BusinessClientBase implements BusinessI {	

	public BusinessClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public BusinessClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public BusinessClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public BusinessClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(BusinessClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  BusinessClient client = new BusinessClient(args[1]);
			  // place client calls here if you want to use this main as a
              // test....
			  getPersonEntityNode(client);
			  getCorrelationEntityNode(client);
			  getCorrelationsByPlayerIdsEntityNode(client);
			  getCorrelationsByIdsEntityNode(client);
			  
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
	
	private static void getPersonEntityNode(BusinessClient client) throws RemoteException {
	    Id id = createPersonII();
	    Cd[] players = new Cd[1];
	    players[0] = new Cd();
	    players[0].setCode(RoleList.HEALTH_CARE_PROVIDER.toString());
	    EntityNode result = client.getEntityByIdWithCorrelations(id, players, null);
	    System.out.println("----top level----");
	    ClientUtils.handleResult(result);
	    System.out.println("----entity level----");
	    ClientUtils.handleResult(result.getEntity().getContent().get(0));
	    System.out.println("---- player correlations level----");
	    ClientUtils.handleResult(result.getPlayers().getContent().get(0));
	}
	
	private static void getCorrelationEntityNode(BusinessClient client) throws RemoteException {
        Id id = createHcpII();
        Bl myTrue = new Bl();
        myTrue.setValue(true);
        
        Bl myFalse = new Bl();
        myFalse.setValue(false);
        
        CorrelationNode result = client.getCorrelationByIdWithEntities(id, myTrue, myFalse);
        System.out.println("----top level----");
        ClientUtils.handleResult(result);
        System.out.println("----correlation level----");
        ClientUtils.handleResult(result.getCorrelation().getContent().get(0));
        System.out.println("---- player entity level----");
        ClientUtils.handleResult(result.getPlayer().getContent().get(0));
    }
	
	private static void getCorrelationsByPlayerIdsEntityNode(BusinessClient client) throws RemoteException {
	    Cd cd = new Cd();
        cd.setCode(RoleList.HEALTH_CARE_PROVIDER.toString());
        Id[] players = new Id[1];
        players[0] = createPersonII();
        Bl myTrue = new Bl();
        myTrue.setValue(true);
        
        Bl myFalse = new Bl();
        myFalse.setValue(false);
        
        CorrelationNode[] result = client.getCorrelationsByPlayerIdsWithEntities(cd, players, myTrue, myFalse);
        System.out.println("----top level----");
        ClientUtils.handleResult(result[0]);
        System.out.println("----correlation level----");
        ClientUtils.handleResult(result[0].getCorrelation().getContent().get(0));
        System.out.println("---- player entity level----");
        ClientUtils.handleResult(result[0].getPlayer().getContent().get(0));
    }
	
	private static void getCorrelationsByIdsEntityNode(BusinessClient client) throws RemoteException {
        
        Id[] ids = new Id[1];
        ids[0] = createHcpII();
        
        Bl myTrue = new Bl();
        myTrue.setValue(true);
        
        CorrelationNode[] result = client.getCorrelationsByIdsWithEntities(ids, myTrue, myTrue);
        System.out.println("----top level----");
        ClientUtils.handleResult(result[0]);
        System.out.println("----correlation level----");
        ClientUtils.handleResult(result[0].getCorrelation().getContent().get(0));
        System.out.println("---- player entity level----");
        ClientUtils.handleResult(result[0].getPlayer().getContent().get(0));
        System.out.println("---- scoper entity level----");
        ClientUtils.handleResult(result[0].getScoper().getContent().get(0));
    }
    
    private static Id createPersonII() {
        Id id = new Id();
        id.setRoot(PersonClient.PERSON_ROOT);
        id.setIdentifierName(PersonClient.PERSON_IDENTIFIER_NAME);
        id.setExtension("501");
        return id;
    }
    
    private static Id createHcpII() {
        Id id = new Id();
        id.setRoot(HealthCareProviderClient.HEALTH_CARE_PROVIDER_ROOT);
        id.setIdentifierName(HealthCareProviderClient.HEALTH_CARE_PROVIDER_IDENTIFIER_NAME);
        id.setExtension("592");
        return id;
    }

  public gov.nih.nci.coppa.po.EntityNode getEntityByIdWithCorrelations(gov.nih.nci.coppa.po.Id id,gov.nih.nci.coppa.po.Cd[] players,gov.nih.nci.coppa.po.Cd[] scopers) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getEntityByIdWithCorrelations");
    gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequest params = new gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequest();
    gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestId idContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestPlayers playersContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestPlayers();
    playersContainer.setCd(players);
    params.setPlayers(playersContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestScopers scopersContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsRequestScopers();
    scopersContainer.setCd(scopers);
    params.setScopers(scopersContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetEntityByIdWithCorrelationsResponse boxedResult = portType.getEntityByIdWithCorrelations(params);
    return boxedResult.getEntityNode();
    }
  }

  public gov.nih.nci.coppa.po.CorrelationNode getCorrelationByIdWithEntities(gov.nih.nci.coppa.po.Id id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCorrelationByIdWithEntities");
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequest params = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequest();
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestId idContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestPlayer playerContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestPlayer();
    playerContainer.setBl(player);
    params.setPlayer(playerContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestScoper scoperContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesRequestScoper();
    scoperContainer.setBl(scoper);
    params.setScoper(scoperContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationByIdWithEntitiesResponse boxedResult = portType.getCorrelationByIdWithEntities(params);
    return boxedResult.getCorrelationNode();
    }
  }

  public gov.nih.nci.coppa.po.CorrelationNode[] getCorrelationsByIdsWithEntities(gov.nih.nci.coppa.po.Id[] id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCorrelationsByIdsWithEntities");
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequest params = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequest();
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestId idContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestPlayer playerContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestPlayer();
    playerContainer.setBl(player);
    params.setPlayer(playerContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestScoper scoperContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesRequestScoper();
    scoperContainer.setBl(scoper);
    params.setScoper(scoperContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByIdsWithEntitiesResponse boxedResult = portType.getCorrelationsByIdsWithEntities(params);
    return boxedResult.getCorrelationNode();
    }
  }

  public gov.nih.nci.coppa.po.CorrelationNode[] getCorrelationsByPlayerIdsWithEntities(gov.nih.nci.coppa.po.Cd cd,gov.nih.nci.coppa.po.Id[] id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getCorrelationsByPlayerIdsWithEntities");
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequest params = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequest();
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestCd cdContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestCd();
    cdContainer.setCd(cd);
    params.setCd(cdContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestId idContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestPlayer playerContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestPlayer();
    playerContainer.setBl(player);
    params.setPlayer(playerContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestScoper scoperContainer = new gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesRequestScoper();
    scoperContainer.setBl(scoper);
    params.setScoper(scoperContainer);
    gov.nih.nci.coppa.services.business.business.stubs.GetCorrelationsByPlayerIdsWithEntitiesResponse boxedResult = portType.getCorrelationsByPlayerIdsWithEntities(params);
    return boxedResult.getCorrelationNode();
    }
  }

}
