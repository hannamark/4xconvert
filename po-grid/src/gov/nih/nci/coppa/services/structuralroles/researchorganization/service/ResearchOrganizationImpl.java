package gov.nih.nci.coppa.services.structuralroles.researchorganization.service;

import gov.nih.nci.coppa.po.ResearchOrganization;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/**
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 *
 * @created by Introduce Toolkit version 1.2
 *
 */
public class ResearchOrganizationImpl extends ResearchOrganizationImplBase {

private static org.apache.log4j.Logger logger = LogManager.getLogger(ResearchOrganizationImpl.class);

    private GenericCorrelationGridServiceImpl<ResearchOrganizationDTO,ResearchOrganization> impl 
    = new GenericCorrelationGridServiceImpl<ResearchOrganizationDTO, ResearchOrganization>(ResearchOrganization.class, ResearchOrganizationDTO.class);

    public ResearchOrganizationImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(researchOrganization);
  }

  public gov.nih.nci.coppa.po.ResearchOrganization getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] search(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.search(researchOrganization);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException {
      return impl.validate(researchOrganization);
  }

  public void update(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(researchOrganization);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.ResearchOrganization[] query(gov.nih.nci.coppa.po.ResearchOrganization researchOrganization,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      return impl.query(researchOrganization, limitOffset);
  }

}

