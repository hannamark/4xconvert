package gov.nih.nci.coppa.services.outcomes.patient.service;

import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PatientTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokePatientEjb;

import java.rmi.RemoteException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Patient Service. Dispatches to the remote EJBs and the Transformers.
 *
 */
public class PatientImpl extends PatientImplBase {
    private static final Logger logger = LogManager.getLogger(PatientImpl.class);
    private InvokePatientEjb patientEjb = new InvokePatientEjb();

	public PatientImpl() throws RemoteException {
		super();
	}

  public gov.nih.nci.coppa.services.outcomes.Patient get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
       try {
           PatientDto dto = patientEjb.get(IITransformer.INSTANCE.toDto(id));
           return PatientTransformer.INSTANCE.toXml(dto);
       } catch (Exception e) {
           logger.error(e.getMessage(), e);
           throw FaultUtil.reThrowRemote(e);
       }
  }

  public gov.nih.nci.coppa.services.outcomes.Patient create(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException {
    try {
        PatientDto dto = patientEjb.create(PatientTransformer.INSTANCE.toDto(patient));
        return PatientTransformer.INSTANCE.toXml(dto);
    }  catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw FaultUtil.reThrowRemote(e);
    }
  }

  public gov.nih.nci.coppa.services.outcomes.Patient update(gov.nih.nci.coppa.services.outcomes.Patient patient) throws RemoteException {
    try {
        PatientDto dto = patientEjb.update(PatientTransformer.INSTANCE.toDto(patient));
        return PatientTransformer.INSTANCE.toXml(dto);
    }  catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw FaultUtil.reThrowRemote(e);
    }
  }
}

