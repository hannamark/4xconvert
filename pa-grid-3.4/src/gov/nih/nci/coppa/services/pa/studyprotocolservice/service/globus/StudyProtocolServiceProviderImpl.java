package gov.nih.nci.coppa.services.pa.studyprotocolservice.service.globus;

import gov.nih.nci.coppa.services.pa.studyprotocolservice.service.StudyProtocolServiceImpl;

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
public class StudyProtocolServiceProviderImpl{
	
	StudyProtocolServiceImpl impl;
	
	public StudyProtocolServiceProviderImpl() throws RemoteException {
		impl = new StudyProtocolServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetStudyProtocolResponse getStudyProtocol(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetStudyProtocolResponse();
    boxedResult.setStudyProtocol(impl.getStudyProtocol(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateStudyProtocolResponse updateStudyProtocol(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateStudyProtocolResponse();
    boxedResult.setStudyProtocol(impl.updateStudyProtocol(params.getStudyProtocol().getStudyProtocol()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetInterventionalStudyProtocolResponse getInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.GetInterventionalStudyProtocolResponse();
    boxedResult.setInterventionalStudyProtocol(impl.getInterventionalStudyProtocol(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateInterventionalStudyProtocolResponse updateInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.UpdateInterventionalStudyProtocolResponse();
    boxedResult.setInterventionalStudyProtocol(impl.updateInterventionalStudyProtocol(params.getInterventionalStudyProtocol().getInterventionalStudyProtocol()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.CreateInterventionalStudyProtocolResponse createInterventionalStudyProtocol(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.CreateInterventionalStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.CreateInterventionalStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.CreateInterventionalStudyProtocolResponse();
    boxedResult.setId(impl.createInterventionalStudyProtocol(params.getInterventionalStudyProtocol().getInterventionalStudyProtocol()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.SearchResponse search(gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.SearchRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyprotocolservice.stubs.SearchResponse();
    boxedResult.setStudyProtocol(impl.search(params.getStudyProtocol().getStudyProtocol(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

}
