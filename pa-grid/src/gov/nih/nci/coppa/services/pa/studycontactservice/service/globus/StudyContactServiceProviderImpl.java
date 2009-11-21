package gov.nih.nci.coppa.services.pa.studycontactservice.service.globus;

import gov.nih.nci.coppa.services.pa.studycontactservice.service.StudyContactServiceImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the PAServicesImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class StudyContactServiceProviderImpl{
	
	StudyContactServiceImpl impl;
	
	public StudyContactServiceProviderImpl() throws RemoteException {
		impl = new StudyContactServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleResponse getByStudyProtocolAndRole(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRoleResponse();
    boxedResult.setStudyContact(impl.getByStudyProtocolAndRole(params.getStudyProtocolId().getId(),params.getStudyContact().getStudyContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesResponse getByStudyProtocolAndRoles(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolAndRolesResponse();
    boxedResult.setStudyContact(impl.getByStudyProtocolAndRoles(params.getStudyProtocolId().getId(),params.getStudyContact().getStudyContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolResponse getByStudyProtocol(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetByStudyProtocolResponse();
    boxedResult.setStudyContact(impl.getByStudyProtocol(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyResponse copy(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CopyResponse();
    impl.copy(params.getFromStudyProtocolId().getId(),params.getToStudyProtocolId().getId());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetResponse get(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.GetResponse();
    boxedResult.setStudyContact(impl.get(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateResponse create(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.CreateResponse();
    boxedResult.setStudyContact(impl.create(params.getStudyContact().getStudyContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateResponse update(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.UpdateResponse();
    boxedResult.setStudyContact(impl.update(params.getStudyContact().getStudyContact()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteResponse delete(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.DeleteResponse();
    impl.delete(params.getId().getId());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studycontactservice.stubs.SearchResponse search(gov.nih.nci.coppa.services.pa.studycontactservice.stubs.SearchRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.pa.studycontactservice.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.pa.studycontactservice.stubs.SearchResponse();
    boxedResult.setStudyContact(impl.search(params.getStudyContact().getStudyContact(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

}
