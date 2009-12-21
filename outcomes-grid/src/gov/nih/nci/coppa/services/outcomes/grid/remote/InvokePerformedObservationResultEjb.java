package gov.nih.nci.coppa.services.outcomes.grid.remote;

import java.rmi.RemoteException;
import java.util.List;
import java.util.zip.DataFormatException;

import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.service.PerformedObservationResultService;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;

/**
 * Service Invoker for PerformedObservationResult.
 * 
 * @author ludetc
 *
 */
public class InvokePerformedObservationResultEjb extends InvokeAccrualStudyServiceEjb<PerformedObservationResultDto> 
    implements PerformedObservationResultService {

    /**
     * Constructor. 
     * 
     * @param type SubmissionDto
     */
    public InvokePerformedObservationResultEjb(Class<PerformedObservationResultDto> type) {
        super(type);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto createPerformedClinicalResult(PerformedClinicalResultDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedClinicalResult(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto createPerformedDiagnosis(PerformedDiagnosisDto input) throws RemoteException,
            DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedDiagnosis(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto createPerformedHistopathology(PerformedHistopathologyDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedHistopathology(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto createPerformedImage(PerformedImageDto input) throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedImage(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto createPerformedLesionDescription(PerformedLesionDescriptionDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedLesionDescription(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto createPerformedMedicalHistoryResult(PerformedMedicalHistoryResultDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().createPerformedMedicalHistoryResult(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto getPerformedClinicalResult(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedClinicalResult(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedClinicalResultDto> getPerformedClinicalResultByPerformedActivity(Ii input)
            throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService()
                .getPerformedClinicalResultByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto getPerformedDiagnosis(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedDiagnosis(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedDiagnosisDto> getPerformedDiagnosisByPerformedActivity(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedDiagnosisByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
   }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto getPerformedHistopathology(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedHistopathology(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedHistopathologyDto> getPerformedHistopathologyByPerformedActivity(Ii input)
        throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService()
                .getPerformedHistopathologyByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto getPerformedImage(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedImage(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedImageDto> getPerformedImageByPerformedActivity(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedImageByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto getPerformedLesionDescription(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedLesionDescription(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedLesionDescriptionDto> getPerformedLesionDescriptionByPerformedActivity(Ii input)
            throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService()
                .getPerformedLesionDescriptionByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto getPerformedMedicalHistoryResult(Ii input) throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService().getPerformedMedicalHistoryResult(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
   public List<PerformedMedicalHistoryResultDto> getPerformedMedicalHistoryResultByPerformedActivity(Ii input)
            throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService()
                    .getPerformedMedicalHistoryResultByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

   /**
    * {@inheritDoc}
    */
    public List<PerformedObservationResultDto> getPerformedObservationResultByPerformedActivity(Ii input)
            throws RemoteException {
        try {
            return getLocator().getPerformedObservationResultService()
                .getPerformedObservationResultByPerformedActivity(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
   public PerformedClinicalResultDto updatePerformedClinicalResult(PerformedClinicalResultDto input)
            throws RemoteException, DataFormatException {
       try {
           return getLocator().getPerformedObservationResultService().updatePerformedClinicalResult(input);
       } catch (RemoteException e) {
           throw e;
       } catch (Exception e) {
           throw new InvokeCoppaServiceException(e.toString(), e);
       } 
   }

   /**
    * {@inheritDoc}
    */
   public PerformedDiagnosisDto updatePerformedDiagnosis(PerformedDiagnosisDto input) throws RemoteException,
            DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().updatePerformedDiagnosis(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

   /**
    * {@inheritDoc}
    */
    public PerformedHistopathologyDto updatePerformedHistopathology(PerformedHistopathologyDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().updatePerformedHistopathology(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }    
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto updatePerformedImage(PerformedImageDto input) throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().updatePerformedImage(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto updatePerformedLesionDescription(PerformedLesionDescriptionDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().updatePerformedLesionDescription(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto updatePerformedMedicalHistoryResult(PerformedMedicalHistoryResultDto input)
            throws RemoteException, DataFormatException {
        try {
            return getLocator().getPerformedObservationResultService().updatePerformedMedicalHistoryResult(input);
        } catch (RemoteException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
