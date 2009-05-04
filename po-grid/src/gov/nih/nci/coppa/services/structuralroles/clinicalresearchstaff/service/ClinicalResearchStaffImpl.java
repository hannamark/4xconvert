package gov.nih.nci.coppa.services.structuralroles.clinicalresearchstaff.service;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class ClinicalResearchStaffImpl extends ClinicalResearchStaffImplBase {
    
    private static org.apache.log4j.Logger logger = LogManager.getLogger(ClinicalResearchStaffImpl.class);
    
    GenericCorrelationGridServiceImpl<ClinicalResearchStaffDTO,ClinicalResearchStaff> impl = new GenericCorrelationGridServiceImpl<ClinicalResearchStaffDTO, ClinicalResearchStaff>(ClinicalResearchStaff.class, ClinicalResearchStaffDTO.class);
    
	public ClinicalResearchStaffImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      return impl.create(clinicalResearchStaff);
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] search(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      return impl.search(clinicalResearchStaff);
  }

  public void update(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.update(clinicalResearchStaff);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
      impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff) throws RemoteException {
      return impl.validate(clinicalResearchStaff);
  }

  public gov.nih.nci.coppa.po.ClinicalResearchStaff[] query(gov.nih.nci.coppa.po.ClinicalResearchStaff clinicalResearchStaff,gov.nih.nci.coppa.po.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.po.faults.TooManyResultsFault {
      return impl.query(clinicalResearchStaff, limitOffset);
  }

}

