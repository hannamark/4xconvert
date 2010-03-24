package gov.nih.nci.coppa.services.outcomes.business.service.globus;

import gov.nih.nci.coppa.services.outcomes.business.service.BusinessImpl;

import java.rmi.RemoteException;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements each method in the portType of the service.  Each method call represented
 * in the port type will be then mapped into the unwrapped implementation which the user provides
 * in the OutcomesServicesImpl class.  This class handles the boxing and unboxing of each method call
 * so that it can be correclty mapped in the unboxed interface that the developer has designed and 
 * has implemented.  Authorization callbacks are automatically made for each method based
 * on each methods authorization requirements.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class BusinessProviderImpl{
	
	BusinessImpl impl;
	
	public BusinessProviderImpl() throws RemoteException {
		impl = new BusinessImpl();
	}
	

    public gov.nih.nci.coppa.services.outcomes.business.stubs.GetResponse get(gov.nih.nci.coppa.services.outcomes.business.stubs.GetRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetResponse boxedResult = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetResponse();
    boxedResult.setPatient(impl.get(params.getPatient().getPatient()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdResponse getById(gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdResponse boxedResult = new gov.nih.nci.coppa.services.outcomes.business.stubs.GetByIdResponse();
    boxedResult.setPatient(impl.getById(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.outcomes.business.stubs.WriteResponse write(gov.nih.nci.coppa.services.outcomes.business.stubs.WriteRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.outcomes.business.stubs.WriteResponse boxedResult = new gov.nih.nci.coppa.services.outcomes.business.stubs.WriteResponse();
    boxedResult.setPatient(impl.write(params.getPatient().getPatient()));
    return boxedResult;
  }

}
