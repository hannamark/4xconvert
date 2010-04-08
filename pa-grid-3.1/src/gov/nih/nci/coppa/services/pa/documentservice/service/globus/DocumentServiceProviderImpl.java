package gov.nih.nci.coppa.services.pa.documentservice.service.globus;

import gov.nih.nci.coppa.services.pa.documentservice.service.DocumentServiceImpl;

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
public class DocumentServiceProviderImpl{
	
	DocumentServiceImpl impl;
	
	public DocumentServiceProviderImpl() throws RemoteException {
		impl = new DocumentServiceImpl();
	}
	

    public gov.nih.nci.coppa.services.pa.documentservice.stubs.GetDocumentsByStudyProtocolResponse getDocumentsByStudyProtocol(gov.nih.nci.coppa.services.pa.documentservice.stubs.GetDocumentsByStudyProtocolRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.documentservice.stubs.GetDocumentsByStudyProtocolResponse boxedResult = new gov.nih.nci.coppa.services.pa.documentservice.stubs.GetDocumentsByStudyProtocolResponse();
    boxedResult.setDocument(impl.getDocumentsByStudyProtocol(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.documentservice.stubs.GetResponse get(gov.nih.nci.coppa.services.pa.documentservice.stubs.GetRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.documentservice.stubs.GetResponse boxedResult = new gov.nih.nci.coppa.services.pa.documentservice.stubs.GetResponse();
    boxedResult.setDocument(impl.get(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.documentservice.stubs.CreateResponse create(gov.nih.nci.coppa.services.pa.documentservice.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.documentservice.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.pa.documentservice.stubs.CreateResponse();
    boxedResult.setDocument(impl.create(params.getDocument().getDocument()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.documentservice.stubs.UpdateResponse update(gov.nih.nci.coppa.services.pa.documentservice.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.documentservice.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.pa.documentservice.stubs.UpdateResponse();
    boxedResult.setDocument(impl.update(params.getDocument().getDocument()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.pa.documentservice.stubs.DeleteResponse delete(gov.nih.nci.coppa.services.pa.documentservice.stubs.DeleteRequest params) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    gov.nih.nci.coppa.services.pa.documentservice.stubs.DeleteResponse boxedResult = new gov.nih.nci.coppa.services.pa.documentservice.stubs.DeleteResponse();
    impl.delete(params.getDocument().getDocument());
    return boxedResult;
  }

}
