package gov.nih.nci.coppa.services.outcomes.performedactivity.service;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedProcedureDto;
import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedActivity;
import gov.nih.nci.coppa.services.outcomes.grid.GenericAccrualGridServiceImpl;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedActivityTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedImagingTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedObservationTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedProcedureTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedRadiationAdministrationTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedSubjectMilestoneTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedSubstanceAdministrationTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokePerformedActivityEjb;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * PerformedActivity service. Dispatches to the remote EJBs and the Transformers.
 * 
 */
public class PerformedActivityImpl extends PerformedActivityImplBase {

    private static final Logger logger = LogManager.getLogger(PerformedActivityImpl.class);
    private final InvokePerformedActivityEjb performedActivityService = new InvokePerformedActivityEjb();

    private GenericAccrualGridServiceImpl<PerformedActivityDto, PerformedActivity> impl
        = new GenericAccrualGridServiceImpl<PerformedActivityDto, 
        PerformedActivity>(PerformedActivity.class, PerformedActivityDto.class);
    
	public PerformedActivityImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.services.outcomes.PerformedActivity get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    return impl.get(id);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedActivity create(gov.nih.nci.coppa.services.outcomes.PerformedActivity performedActivity) throws RemoteException {
    return impl.create(performedActivity);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedActivity update(gov.nih.nci.coppa.services.outcomes.PerformedActivity performedActivity) throws RemoteException {
    return impl.update(performedActivity);
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    impl.delete(id);
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedActivity[] getByStudyProtocol(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedActivityDto> perActDtos = performedActivityService
              .getByStudyProtocol(iiDto);
          return PerformedActivityTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedActivity[] getByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedActivityDto> perActDtos = performedActivityService
              .getByStudySubject(iiDto);
          return PerformedActivityTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation[] getPerformedObservationByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedObservationDto> perActDtos = performedActivityService
              .getPerformedObservationByStudySubject(iiDto);
          return PerformedObservationTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone[] getPerformedSubjectMilestoneByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedSubjectMilestoneDto> perActDtos = performedActivityService
              .getPerformedSubjectMilestoneByStudySubject(iiDto);
          return PerformedSubjectMilestoneTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone getPerformedSubjectMilestone(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedSubjectMilestoneDto perActDtos = performedActivityService
              .getPerformedSubjectMilestone(iiDto);
          return PerformedSubjectMilestoneTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone createPerformedSubjectMilestone(gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone performedSubjectMilestone) throws RemoteException {
      try {
          PerformedSubjectMilestoneDto pfDto = 
              PerformedSubjectMilestoneTransformer.INSTANCE.toDto(performedSubjectMilestone);
          PerformedSubjectMilestoneDto perActDtos = performedActivityService
              .createPerformedSubjectMilestone(pfDto);
          return PerformedSubjectMilestoneTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone updatePerformedSubjectMilestone(gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone performedSubjectMilestone) throws RemoteException {
      try {
          PerformedSubjectMilestoneDto pfDto = 
              PerformedSubjectMilestoneTransformer.INSTANCE.toDto(performedSubjectMilestone);
          PerformedSubjectMilestoneDto perActDtos = performedActivityService
              .updatePerformedSubjectMilestone(pfDto);
          return PerformedSubjectMilestoneTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation getPerformedObservation(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedObservationDto perActDtos = performedActivityService
              .getPerformedObservation(iiDto);
          return PerformedObservationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation createPerformedObservation(gov.nih.nci.coppa.services.outcomes.PerformedObservation performedObservation) throws RemoteException {
      try {
          PerformedObservationDto pfDto = 
              PerformedObservationTransformer.INSTANCE.toDto(performedObservation);
          PerformedObservationDto perActDtos = performedActivityService
              .createPerformedObservation(pfDto);
          return PerformedObservationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation updatePerformedObservation(gov.nih.nci.coppa.services.outcomes.PerformedObservation performedObservation) throws RemoteException {
      try {
          PerformedObservationDto pfDto = 
              PerformedObservationTransformer.INSTANCE.toDto(performedObservation);
          PerformedObservationDto perActDtos = performedActivityService
              .updatePerformedObservation(pfDto);
          return PerformedObservationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging[] getPerformedImagingByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedImagingDto> perActDtos = performedActivityService
              .getPerformedImagingByStudySubject(iiDto);
          return PerformedImagingTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging getPerformedImaging(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedImagingDto perActDtos = performedActivityService
              .getPerformedImaging(iiDto);
          return PerformedImagingTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging createPerformedImaging(gov.nih.nci.coppa.services.outcomes.PerformedImaging performedImaging) throws RemoteException {
      try {
          PerformedImagingDto pfDto = 
              PerformedImagingTransformer.INSTANCE.toDto(performedImaging);
          PerformedImagingDto perActDtos = performedActivityService
              .createPerformedImaging(pfDto);
          return PerformedImagingTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging updatePerformedImaging(gov.nih.nci.coppa.services.outcomes.PerformedImaging performedImaging) throws RemoteException {
      try {
          PerformedImagingDto pfDto = 
              PerformedImagingTransformer.INSTANCE.toDto(performedImaging);
          PerformedImagingDto perActDtos = performedActivityService
              .updatePerformedImaging(pfDto);
          return PerformedImagingTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure[] getPerformedProcedureByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedProcedureDto> perActDtos = performedActivityService
              .getPerformedProcedureByStudySubject(iiDto);
          return PerformedProcedureTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure getPerformedProcedure(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedProcedureDto perActDtos = performedActivityService
              .getPerformedProcedure(iiDto);
          return PerformedProcedureTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure createPerformedProcedure(gov.nih.nci.coppa.services.outcomes.PerformedProcedure performedProcedure) throws RemoteException {
      try {
          PerformedProcedureDto pfDto = 
              PerformedProcedureTransformer.INSTANCE.toDto(performedProcedure);
          PerformedProcedureDto perActDtos = performedActivityService
              .createPerformedProcedure(pfDto);
          return PerformedProcedureTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure updatePerformedProcedure(gov.nih.nci.coppa.services.outcomes.PerformedProcedure performedProcedure) throws RemoteException {
      try {
          PerformedProcedureDto pfDto = 
              PerformedProcedureTransformer.INSTANCE.toDto(performedProcedure);
          PerformedProcedureDto perActDtos = performedActivityService
              .updatePerformedProcedure(pfDto);
          return PerformedProcedureTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration[] getPerformedSubstanceAdministrationByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedSubstanceAdministrationDto> perActDtos = performedActivityService
              .getPerformedSubstanceAdministrationByStudySubject(iiDto);
          return PerformedSubstanceAdministrationTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration getPerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedSubstanceAdministrationDto perActDtos = performedActivityService
              .getPerformedSubstanceAdministration(iiDto);
          return PerformedSubstanceAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration createPerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration performedSubstanceAdministration) throws RemoteException {
      try {
          PerformedSubstanceAdministrationDto pfDto = 
              PerformedSubstanceAdministrationTransformer.INSTANCE.toDto(performedSubstanceAdministration);
          PerformedSubstanceAdministrationDto perActDtos = performedActivityService
              .createPerformedSubstanceAdministration(pfDto);
          return PerformedSubstanceAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration updatePerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration performedSubstanceAdministration) throws RemoteException {
      try {
          PerformedSubstanceAdministrationDto pfDto = 
              PerformedSubstanceAdministrationTransformer.INSTANCE.toDto(performedSubstanceAdministration);
          PerformedSubstanceAdministrationDto perActDtos = performedActivityService
              .updatePerformedSubstanceAdministration(pfDto);
          return PerformedSubstanceAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration[] getPerformedRadiationAdministrationByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<PerformedRadiationAdministrationDto> perActDtos = performedActivityService
              .getPerformedRadiationAdministrationByStudySubject(iiDto);
          return PerformedRadiationAdministrationTransformer.INSTANCE.convert(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration getPerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          PerformedRadiationAdministrationDto perActDtos = performedActivityService
              .getPerformedRadiationAdministration(iiDto);
          return PerformedRadiationAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration createPerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration performedRadiationAdministration) throws RemoteException {
      try {
          PerformedRadiationAdministrationDto pfDto = 
              PerformedRadiationAdministrationTransformer.INSTANCE.toDto(performedRadiationAdministration);
          PerformedRadiationAdministrationDto perActDtos = performedActivityService
              .createPerformedRadiationAdministration(pfDto);
          return PerformedRadiationAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration updatePerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration performedRadiationAdministration) throws RemoteException {
      try {
          PerformedRadiationAdministrationDto pfDto = 
              PerformedRadiationAdministrationTransformer.INSTANCE.toDto(performedRadiationAdministration);
          PerformedRadiationAdministrationDto perActDtos = performedActivityService
              .updatePerformedRadiationAdministration(pfDto);
          return PerformedRadiationAdministrationTransformer.INSTANCE.toXml(perActDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

