package gov.nih.nci.coppa.services.pa.studyresourcingservice.service.globus;

import gov.nih.nci.coppa.services.pa.studyresourcingservice.service.StudyResourcingServiceImpl;

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
public class StudyResourcingServiceProviderImpl{
	
	StudyResourcingServiceImpl impl;
	
	public StudyResourcingServiceProviderImpl() throws RemoteException {
		impl = new StudyResourcingServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetSummaryForReportedResourceResponse getSummaryForReportedResource(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetSummaryForReportedResourceRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetSummaryForReportedResourceResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetSummaryForReportedResourceResponse();
    boxedResult.setStudyResourcing(impl.getSummaryForReportedResource(params.getStudyProtocolId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.UpdateStudyResourcingResponse updateStudyResourcing(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.UpdateStudyResourcingRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.UpdateStudyResourcingResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.UpdateStudyResourcingResponse();
    boxedResult.setStudyResourcing(impl.updateStudyResourcing(params.getStudyResourcing().getStudyResourcing()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.CreateStudyResourcingResponse createStudyResourcing(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.CreateStudyResourcingRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.CreateStudyResourcingResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.CreateStudyResourcingResponse();
    boxedResult.setStudyResourcing(impl.createStudyResourcing(params.getStudyResourcing().getStudyResourcing()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByStudyProtocolResponse getStudyResourceByStudyProtocol(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByStudyProtocolResponse();
    boxedResult.setStudyResourcing(impl.getStudyResourceByStudyProtocol(params.getStudyProtocolId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByIDResponse getStudyResourceByID(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByIDRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByIDResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.GetStudyResourceByIDResponse();
    boxedResult.setStudyResourcing(impl.getStudyResourceByID(params.getStudyResourceId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.DeleteStudyResourceByIDResponse deleteStudyResourceByID(gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.DeleteStudyResourceByIDRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.DeleteStudyResourceByIDResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyresourcingservice.stubs.DeleteStudyResourceByIDResponse();
    impl.deleteStudyResourceByID(params.getStudyResourcing().getStudyResourcing());
    return boxedResult;
  }

}
