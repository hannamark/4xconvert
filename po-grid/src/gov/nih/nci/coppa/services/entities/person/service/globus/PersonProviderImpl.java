package gov.nih.nci.coppa.services.entities.person.service.globus;

import gov.nih.nci.coppa.services.entities.person.service.PersonImpl;

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
public class PersonProviderImpl{
	
	PersonImpl impl;
	
	public PersonProviderImpl() throws RemoteException {
		impl = new PersonImpl();
	}
	

    public gov.nih.nci.coppa.services.entities.person.stubs.GetByIdResponse getById(gov.nih.nci.coppa.services.entities.person.stubs.GetByIdRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
    gov.nih.nci.coppa.services.entities.person.stubs.GetByIdResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.GetByIdResponse();
    boxedResult.setPerson(impl.getById(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.entities.person.stubs.CreateResponse create(gov.nih.nci.coppa.services.entities.person.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.entities.person.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.CreateResponse();
    boxedResult.setId(impl.create(params.getPerson().getPerson()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.entities.person.stubs.ValidateResponse validate(gov.nih.nci.coppa.services.entities.person.stubs.ValidateRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.entities.person.stubs.ValidateResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.ValidateResponse();
    boxedResult.setStringMap(impl.validate(params.getPerson().getPerson()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.entities.person.stubs.UpdateResponse update(gov.nih.nci.coppa.services.entities.person.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateResponse();
    impl.update(params.getPerson().getPerson());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusResponse updateStatus(gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.UpdateStatusResponse();
    impl.updateStatus(params.getTargetId().getId(),params.getStatusCode().getCd());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.entities.person.stubs.QueryResponse query(gov.nih.nci.coppa.services.entities.person.stubs.QueryRequest params) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.entities.person.stubs.QueryResponse boxedResult = new gov.nih.nci.coppa.services.entities.person.stubs.QueryResponse();
    boxedResult.setPerson(impl.query(params.getPerson().getPerson(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

}
