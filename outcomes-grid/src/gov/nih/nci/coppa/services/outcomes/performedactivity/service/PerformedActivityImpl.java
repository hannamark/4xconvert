package gov.nih.nci.coppa.services.outcomes.performedactivity.service;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedActivity;
import gov.nih.nci.coppa.services.outcomes.grid.GenericAccrualGridServiceImpl;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedActivityTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedObservationTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.PerformedSubjectMilestoneTransformer;
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
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation getPerformedObservation(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation createPerformedObservation(gov.nih.nci.coppa.services.outcomes.PerformedObservation performedObservation) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedObservation updatePerformedObservation(gov.nih.nci.coppa.services.outcomes.PerformedObservation performedObservation) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging[] getPerformedImagingByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging getPerformedImaging(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging createPerformedImaging(gov.nih.nci.coppa.services.outcomes.PerformedImaging performedImaging) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedImaging updatePerformedImaging(gov.nih.nci.coppa.services.outcomes.PerformedImaging performedImaging) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure[] getPerformedProcedureByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure getPerformedProcedure(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure createPerformedProcedure(gov.nih.nci.coppa.services.outcomes.PerformedProcedure performedProcedure) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedProcedure updatePerformedProcedure(gov.nih.nci.coppa.services.outcomes.PerformedProcedure performedProcedure) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration[] getPerformedSubstanceAdministrationByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration getPerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration createPerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration performedSubstanceAdministration) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration updatePerformedSubstanceAdministration(gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration performedSubstanceAdministration) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration[] getPerformedRadiationAdministrationByStudySubject(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration getPerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration createPerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration performedRadiationAdministration) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration updatePerformedRadiationAdministration(gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration performedRadiationAdministration) throws RemoteException {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

