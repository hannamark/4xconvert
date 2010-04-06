package gov.nih.nci.coppa.services.structuralroles.researchorganization.service.globus;

import gov.nih.nci.coppa.services.structuralroles.researchorganization.service.ResearchOrganizationImpl;

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
public class ResearchOrganizationProviderImpl{
	
	ResearchOrganizationImpl impl;
	
	public ResearchOrganizationProviderImpl() throws RemoteException {
		impl = new ResearchOrganizationImpl();
	}
	

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateResponse create(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.CreateResponse();
    boxedResult.setId(impl.create(params.getResearchOrganization().getResearchOrganization()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdResponse getById(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdResponse();
    boxedResult.setResearchOrganization(impl.getById(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsResponse getByIds(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByIdsResponse();
    boxedResult.setResearchOrganization(impl.getByIds(params.getId().getId()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchResponse search(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchRequest params) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.SearchResponse();
    boxedResult.setResearchOrganization(impl.search(params.getResearchOrganization().getResearchOrganization()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateResponse validate(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.ValidateResponse();
    boxedResult.setStringMap(impl.validate(params.getResearchOrganization().getResearchOrganization()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateResponse update(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateResponse();
    impl.update(params.getResearchOrganization().getResearchOrganization());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusResponse updateStatus(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusRequest params) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.UpdateStatusResponse();
    impl.updateStatus(params.getTargetId().getId(),params.getStatusCode().getCd());
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryResponse query(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryRequest params) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.QueryResponse();
    boxedResult.setResearchOrganization(impl.query(params.getResearchOrganization().getResearchOrganization(),params.getLimitOffset().getLimitOffset()));
    return boxedResult;
  }

    public gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsResponse getByPlayerIds(gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsRequest params) throws RemoteException {
    gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsResponse boxedResult = new gov.nih.nci.coppa.services.structuralroles.researchorganization.stubs.GetByPlayerIdsResponse();
    boxedResult.setResearchOrganization(impl.getByPlayerIds(params.getId().getId()));
    return boxedResult;
  }

}
