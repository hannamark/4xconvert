package gov.nih.nci.coppa.services.pa.studyrelationshipservice.service.globus;

import gov.nih.nci.coppa.services.pa.studyrelationshipservice.service.StudyRelationshipServiceImpl;

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
public class StudyRelationshipServiceProviderImpl{
	
	StudyRelationshipServiceImpl impl;
	
	public StudyRelationshipServiceProviderImpl() throws RemoteException {
		impl = new StudyRelationshipServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetResponse get(gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.GetResponse();
    boxedResult.setStudyRelationship(impl.get(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchResponse search(gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.SearchResponse();
    boxedResult.setStudyRelationship(impl.search(params.getStudyRelationship().getStudyRelationship(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateResponse create(gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.CreateResponse();
    boxedResult.setStudyRelationship(impl.create(params.getStudyRelationship().getStudyRelationship()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateResponse update(gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.UpdateResponse();
    boxedResult.setStudyRelationship(impl.update(params.getStudyRelationship().getStudyRelationship()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteResponse delete(gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyrelationshipservice.stubs.DeleteResponse();
    impl.delete(params.getId().getId());
    return boxedResult;
  }

}
