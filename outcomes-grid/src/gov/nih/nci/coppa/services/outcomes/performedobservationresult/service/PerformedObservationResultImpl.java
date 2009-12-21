package gov.nih.nci.coppa.services.outcomes.performedobservationresult.service;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResult;
import gov.nih.nci.coppa.services.outcomes.Submission;
import gov.nih.nci.coppa.services.outcomes.grid.GenericAccrualStudyGridServiceImpl;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedActivityTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedClinicalResultTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedDiagnosisTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedHistopathologyTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedImageTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedLesionDescriptionTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedMedicalHistoryResultTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedObservationResultTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokePerformedObservationResultEjb;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeSubmissionEjb;
import gov.nih.nci.coppa.services.outcomes.submission.service.SubmissionImpl;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * Dispatches to the remote EJBs and Transformers.
 *
 * @author ludetc
 */
public class PerformedObservationResultImpl extends PerformedObservationResultImplBase {

    private static final Logger logger = LogManager.getLogger(SubmissionImpl.class);
    private final InvokePerformedObservationResultEjb porService =
        new InvokePerformedObservationResultEjb(PerformedObservationResultDto.class);
    private GenericAccrualStudyGridServiceImpl<PerformedObservationResultDto, PerformedObservationResult> impl
        = new GenericAccrualStudyGridServiceImpl<PerformedObservationResultDto, PerformedObservationResult>
            (PerformedObservationResult.class, PerformedObservationResultDto.class);

	
	public PerformedObservationResultImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    return impl.get(id);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult create(gov.nih.nci.coppa.services.outcomes.PerformedObservationResult performedObservationResult) throws RemoteException {
    return impl.create(performedObservationResult);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult update(gov.nih.nci.coppa.services.outcomes.PerformedObservationResult performedObservationResult) throws RemoteException {
    return impl.update(performedObservationResult);
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      impl.delete(id);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservationResult[] getPerformedObservationResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedObservationResultDto> dtos = porService
              .getPerformedObservationResultByPerformedActivity(iiDto);
          return PerformedObservationResultTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology[] getPerformedHistopathologyByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedHistopathologyDto> dtos = porService
              .getPerformedHistopathologyByPerformedActivity(iiDto);
          return PerformedHistopathologyTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology getPerformedHistopathology(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedHistopathologyDto dto = porService
              .getPerformedHistopathology(iiDto);
          return PerformedHistopathologyTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology createPerformedHistopathology(gov.nih.nci.coppa.services.outcomes.PerformedHistopathology performedHistopathology) throws RemoteException {
      try {
          PerformedHistopathologyDto dto = porService
              .createPerformedHistopathology(new PerformedHistopathologyTransformer().toDto(performedHistopathology));
          return PerformedHistopathologyTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedHistopathology updatePerformedHistopathology(gov.nih.nci.coppa.services.outcomes.PerformedHistopathology performedHistopathology) throws RemoteException {
      try {
          PerformedHistopathologyDto dto = porService
              .updatePerformedHistopathology(new PerformedHistopathologyTransformer().toDto(performedHistopathology));
          return PerformedHistopathologyTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis[] getPerformedDiagnosisByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedDiagnosisDto> dtos = porService
              .getPerformedDiagnosisByPerformedActivity(iiDto);
          return PerformedDiagnosisTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis getPerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedDiagnosisDto dto = porService
              .getPerformedDiagnosis(iiDto);
          return PerformedDiagnosisTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis createPerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis performedDiagnosis) throws RemoteException {
      try {
          PerformedDiagnosisDto dto = porService
              .createPerformedDiagnosis(new PerformedDiagnosisTransformer().toDto(performedDiagnosis));
          return PerformedDiagnosisTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis updatePerformedDiagnosis(gov.nih.nci.coppa.services.outcomes.PerformedDiagnosis performedDiagnosis) throws RemoteException {
      try {
          PerformedDiagnosisDto dto = porService
              .updatePerformedDiagnosis(new PerformedDiagnosisTransformer().toDto(performedDiagnosis));
          return PerformedDiagnosisTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage[] getPerformedImageByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedImageDto> dtos = porService
              .getPerformedImageByPerformedActivity(iiDto);
          return PerformedImageTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage getPerformedImage(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedImageDto dto = porService
              .getPerformedImage(iiDto);
          return PerformedImageTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage createPerformedImage(gov.nih.nci.coppa.services.outcomes.PerformedImage performedImage) throws RemoteException {
      try {
          PerformedImageDto dto = porService
              .createPerformedImage(new PerformedImageTransformer().toDto(performedImage));
          return PerformedImageTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }  
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImage updatePerformedImage(gov.nih.nci.coppa.services.outcomes.PerformedImage performedImage) throws RemoteException {
      try {
          PerformedImageDto dto = porService
              .updatePerformedImage(new PerformedImageTransformer().toDto(performedImage));
          return PerformedImageTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult[] getPerformedClinicalResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedClinicalResultDto> dtos = porService
              .getPerformedClinicalResultByPerformedActivity(iiDto);
          return PerformedClinicalResultTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult getPerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedClinicalResultDto dto = porService
              .getPerformedClinicalResult(iiDto);
          return PerformedClinicalResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult createPerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult performedClinicalResult) throws RemoteException {
      try {
          PerformedClinicalResultDto dto = porService
              .createPerformedClinicalResult(new PerformedClinicalResultTransformer().toDto(performedClinicalResult));
          return PerformedClinicalResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }  
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult updatePerformedClinicalResult(gov.nih.nci.coppa.services.outcomes.PerformedClinicalResult performedClinicalResult) throws RemoteException {
      try {
          PerformedClinicalResultDto dto = porService
              .updatePerformedClinicalResult(new PerformedClinicalResultTransformer().toDto(performedClinicalResult));
          return PerformedClinicalResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }  
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult[] getPerformedMedicalHistoryResultByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedMedicalHistoryResultDto> dtos = porService
              .getPerformedMedicalHistoryResultByPerformedActivity(iiDto);
          return PerformedMedicalHistoryResultTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult getPerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedMedicalHistoryResultDto dto = porService
              .getPerformedMedicalHistoryResult(iiDto);
          return PerformedMedicalHistoryResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult createPerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult performedMedicalHistoryResult) throws RemoteException {
      try {
          PerformedMedicalHistoryResultDto dto = porService
              .createPerformedMedicalHistoryResult(new PerformedMedicalHistoryResultTransformer().toDto(performedMedicalHistoryResult));
          return PerformedMedicalHistoryResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult updatePerformedMedicalHistoryResult(gov.nih.nci.coppa.services.outcomes.PerformedMedicalHistoryResult performedMedicalHistoryResult) throws RemoteException {
      try {
          PerformedMedicalHistoryResultDto dto = porService
              .updatePerformedMedicalHistoryResult(new PerformedMedicalHistoryResultTransformer().toDto(performedMedicalHistoryResult));
          return PerformedMedicalHistoryResultTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription[] getPerformedLesionDescriptionByPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedLesionDescriptionDto> dtos = porService
              .getPerformedLesionDescriptionByPerformedActivity(iiDto);
          return PerformedLesionDescriptionTransformer.INSTANCE.convert(dtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription getPerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedLesionDescriptionDto dto = porService
              .getPerformedLesionDescription(iiDto);
          return PerformedLesionDescriptionTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription createPerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription performedLesionDescription) throws RemoteException {
      try {
          PerformedLesionDescriptionDto dto = porService
              .createPerformedLesionDescription(new PerformedLesionDescriptionTransformer().toDto(performedLesionDescription));
          return PerformedLesionDescriptionTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription updatePerformedLesionDescription(gov.nih.nci.coppa.services.outcomes.PerformedLesionDescription performedLesionDescription) throws RemoteException {
      try {
          PerformedLesionDescriptionDto dto = porService
              .updatePerformedLesionDescription(new PerformedLesionDescriptionTransformer().toDto(performedLesionDescription));
          return PerformedLesionDescriptionTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      } 
  }

}

