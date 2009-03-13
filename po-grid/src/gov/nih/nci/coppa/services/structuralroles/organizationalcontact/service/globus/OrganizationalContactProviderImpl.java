package gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service.globus;

import gov.nih.nci.coppa.services.structuralroles.organizationalcontact.service.OrganizationalContactImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the CoreServicesImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class OrganizationalContactProviderImpl{
	
	OrganizationalContactImpl impl;
	
	public OrganizationalContactProviderImpl() throws RemoteException {
		impl = new OrganizationalContactImpl();
	}
	

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateResponse create(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.CreateResponse();
    boxedResult.setId(impl.create(params.getOrganizationalContact().getOrganizationalContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdResponse getById(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdResponse();
    boxedResult.setOrganizationalContact(impl.getById(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsResponse getByIds(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.GetByIdsResponse();
    boxedResult.setOrganizationalContact(impl.getByIds(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchResponse search(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.SearchResponse();
    boxedResult.setOrganizationalContact(impl.search(params.getOrganizationalContact().getOrganizationalContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateResponse update(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateResponse();
    impl.update(params.getOrganizationalContact().getOrganizationalContact());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusResponse updateStatus(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.UpdateStatusResponse();
    impl.updateStatus(params.getTargetId().getId(),params.getStatusCode().getCd());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateResponse validate(gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.organizationalcontact.stubs.ValidateResponse();
    boxedResult.setStringMap(impl.validate(params.getOrganizationalContact().getOrganizationalContact()));
    return boxedResult;
  }

}
