package gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service.globus;

import gov.nih.nci.coppa.services.structuralroles.healthcarefacility.service.HealthCareFacilityImpl;

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
public class HealthCareFacilityProviderImpl{
	
	HealthCareFacilityImpl impl;
	
	public HealthCareFacilityProviderImpl() throws RemoteException {
		impl = new HealthCareFacilityImpl();
	}
	

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdResponse getById(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdResponse();
    boxedResult.setHealthCareFacility(impl.getById(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsResponse getByIds(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByIdsResponse();
    boxedResult.setHealthCareFacility(impl.getByIds(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateResponse create(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.CreateResponse();
    boxedResult.setId(impl.create(params.getHealthCareFacility().getHealthCareFacility()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateResponse validate(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.ValidateResponse();
    boxedResult.setStringMap(impl.validate(params.getHealthCareFacility().getHealthCareFacility()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchResponse search(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchRequest params) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.SearchResponse();
    boxedResult.setHealthCareFacility(impl.search(params.getHealthCareFacility().getHealthCareFacility()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateResponse update(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateResponse();
    impl.update(params.getHealthCareFacility().getHealthCareFacility());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusResponse updateStatus(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.UpdateStatusResponse();
    impl.updateStatus(params.getTargetId().getId(),params.getStatusCode().getCd());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryResponse query(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryRequest params) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.QueryResponse();
    boxedResult.setHealthCareFacility(impl.query(params.getHealthCareFacility().getHealthCareFacility(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsResponse getByPlayerIds(gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.healthcarefacility.stubs.GetByPlayerIdsResponse();
    boxedResult.setHealthCareFacility(impl.getByPlayerIds(params.getId().getId()));
    return boxedResult;
  }

}
