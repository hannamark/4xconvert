package gov.nih.nci.coppa.services.structuralroles.patient.service;

import gov.nih.nci.coppa.po.Patient;
import gov.nih.nci.coppa.po.grid.services.GenericCorrelationGridServiceImpl;
import gov.nih.nci.services.correlation.PatientDTO;

import java.rmi.RemoteException;

/** 
 * Patient service.
 * 
 * @created by Introduce Toolkit version 1.3
 * @author mshestopalov
 */
public class PatientImpl extends PatientImplBase {

    private GenericCorrelationGridServiceImpl<PatientDTO, Patient> impl 
    = new GenericCorrelationGridServiceImpl<PatientDTO, Patient>(Patient.class, PatientDTO.class);
	
	public PatientImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.Id create(gov.nih.nci.coppa.po.Patient patient) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    return impl.create(patient);
  }

  public gov.nih.nci.coppa.po.Patient getById(gov.nih.nci.coppa.po.Id id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    return impl.getById(id);
  }

  public gov.nih.nci.coppa.po.Patient[] getByIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
    return impl.getByIds(id);
  }

  public gov.nih.nci.coppa.po.Patient[] getByPlayerIds(gov.nih.nci.coppa.po.Id[] id) throws RemoteException {
    return impl.getByPlayerIds(id);
  }

  public gov.nih.nci.coppa.po.Patient[] query(gov.nih.nci.coppa.po.Patient patient,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    return impl.query(patient, limitOffset);
  }

  public gov.nih.nci.coppa.po.Patient[] search(gov.nih.nci.coppa.po.Patient patient) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
    return impl.search(patient);
  }

  public void update(gov.nih.nci.coppa.po.Patient patient) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    impl.update(patient);
  }

  public void updateStatus(gov.nih.nci.coppa.po.Id targetId,gov.nih.nci.coppa.po.Cd statusCode) throws RemoteException, gov.nih.nci.coppa.po.faults.EntityValidationFault {
    impl.updateStatus(targetId, statusCode);
  }

  public gov.nih.nci.coppa.po.StringMap validate(gov.nih.nci.coppa.po.Patient patient) throws RemoteException {
    return impl.validate(patient);
  }

}

