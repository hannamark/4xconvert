package gov.nih.nci.coppa.services.structuralroles.organizationalcontact.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;
import org.iso._21090.CD;

import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.OrganizationalContact;

import gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.OrganizationalContactPortType;
import gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.service.OrganizationalContactServiceAddressingLocator;
import gov.nih.nci.coppa.services.structuralroles.organizationalcontact.common.OrganizationalContactI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

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
public class OrganizationalContactClient extends OrganizationalContactClientBase implements OrganizationalContactI {	

	/**
     * The identifier name for.
     */
    public static final String ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME = "Organizational contact identifier";

    /**
     * The ii root value.
     */
    public static final String ORGANIZATIONAL_CONTACT_ROOT = "2.16.840.1.113883.3.26.4.4.8";
    
	public OrganizationalContactClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public OrganizationalContactClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public OrganizationalContactClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public OrganizationalContactClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(OrganizationalContactClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  OrganizationalContactClient client = new OrganizationalContactClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			  getOrgContact(client);
			  searchOrgContact(client);
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

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"create");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateRequestOrganizationalContact organizationalContactContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateRequestOrganizationalContact();
    organizationalContactContainer.setOrganizationalContact(organizationalContact);
    params.setOrganizationalContact(organizationalContactContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateResponse boxedResult = portType.create(params);
    return boxedResult.getId();
    }
  }

  public gov.nih.nci.coppa.po.OrganizationalContact getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getById");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdResponse boxedResult = portType.getById(params);
    return boxedResult.getOrganizationalContact();
    }
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getByIds");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsRequestId idContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsRequestId();
    idContainer.setId(id);
    params.setId(idContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsResponse boxedResult = portType.getByIds(params);
    return boxedResult.getOrganizationalContact();
    }
  }

  public gov.nih.nci.coppa.po.OrganizationalContact[] search(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"search");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchRequestOrganizationalContact organizationalContactContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchRequestOrganizationalContact();
    organizationalContactContainer.setOrganizationalContact(organizationalContact);
    params.setOrganizationalContact(organizationalContactContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchResponse boxedResult = portType.search(params);
    return boxedResult.getOrganizationalContact();
    }
  }

  public void update(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"update");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateRequestOrganizationalContact organizationalContactContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateRequestOrganizationalContact();
    organizationalContactContainer.setOrganizationalContact(organizationalContact);
    params.setOrganizationalContact(organizationalContactContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateResponse boxedResult = portType.update(params);
    }
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"updateStatus");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequestTargetId targetIdContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequestTargetId();
    targetIdContainer.setId(targetId);
    params.setTargetId(targetIdContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequestStatusCode statusCodeContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequestStatusCode();
    statusCodeContainer.setCd(statusCode);
    params.setStatusCode(statusCodeContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusResponse boxedResult = portType.updateStatus(params);
    }
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.OrganizationalContact organizationalContact) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"validate");
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateRequest params = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateRequest();
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateRequestOrganizationalContact organizationalContactContainer = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateRequestOrganizationalContact();
    organizationalContactContainer.setOrganizationalContact(organizationalContact);
    params.setOrganizationalContact(organizationalContactContainer);
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateResponse boxedResult = portType.validate(params);
    return boxedResult.getStringMap();
    }
  }
  private static void getOrgContact(OrganizationalContactClient client) throws RemoteException {
		Id id = new Id();
		id.setRoot(ORGANIZATIONAL_CONTACT_ROOT);
		id.setIdentifierName(ORGANIZATIONAL_CONTACT_IDENTIFIER_NAME);
		id.setExtension("648");
		OrganizationalContact result = client.getById(id);
		System.out.println(ToStringBuilder.reflectionToString(result, ToStringStyle.MULTI_LINE_STYLE));
	  }
	  private static void searchOrgContact(OrganizationalContactClient client) throws RemoteException {
		    OrganizationalContact criteria = new OrganizationalContact();
		    CD statusCode = new CD();
	        statusCode.setCode("pending");
	        criteria.setStatus(statusCode);
	        OrganizationalContact[] results = client.search(criteria);
	        System.out.println("Search OrganizationalContact Results Found: " + results.length);
	        for (OrganizationalContact orgContact : results) {
	            System.out.println(ToStringBuilder.reflectionToString(orgContact, ToStringStyle.MULTI_LINE_STYLE));
	        }
		  }

}
