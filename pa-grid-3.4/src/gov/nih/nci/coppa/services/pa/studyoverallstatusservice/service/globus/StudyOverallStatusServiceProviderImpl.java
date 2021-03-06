package gov.nih.nci.coppa.services.pa.studyoverallstatusservice.service.globus;

import gov.nih.nci.coppa.services.pa.studyoverallstatusservice.service.StudyOverallStatusServiceImpl;

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
public class StudyOverallStatusServiceProviderImpl{
	
	StudyOverallStatusServiceImpl impl;
	
	public StudyOverallStatusServiceProviderImpl() throws RemoteException {
		impl = new StudyOverallStatusServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolResponse getByStudyProtocol(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetByStudyProtocolResponse();
    boxedResult.setStudyOverallStatus(impl.getByStudyProtocol(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyResponse copy(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CopyResponse();
    impl.copy(params.getFromStudyProtocolId().getId(),params.getToStudyProtocolId().getId());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolResponse getCurrentByStudyProtocol(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetCurrentByStudyProtocolResponse();
    boxedResult.setStudyOverallStatus(impl.getCurrentByStudyProtocol(params.getStudyProtocolId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetResponse get(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.GetResponse();
    boxedResult.setStudyOverallStatus(impl.get(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateResponse create(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.CreateResponse();
    boxedResult.setStudyOverallStatus(impl.create(params.getStudyOverallStatus().getStudyOverallStatus()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateResponse update(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.UpdateResponse();
    boxedResult.setStudyOverallStatus(impl.update(params.getStudyOverallStatus().getStudyOverallStatus()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteResponse delete(gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteResponse boxedResult = new gov.nih.nci.coppa.services.pa.studyoverallstatusservice.stubs.DeleteResponse();
    impl.delete(params.getId().getId());
    return boxedResult;
  }

}
