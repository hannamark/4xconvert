package gov.nih.nci.coppa.services.outcomes.activityrelationship.service;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.outcome.grid.services.GenericAccrualGridServiceImpl;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.ActivityRelationship;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.ActivityRelationshipTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeActivityRelationshipEjb;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * ActivityRelationship service. Dispatches to the remote EJBs and the Transformers.
 * 
 */
public class ActivityRelationshipImpl extends ActivityRelationshipImplBase {

    private static final Logger logger = LogManager.getLogger(ActivityRelationshipImpl.class);
    private final InvokeActivityRelationshipEjb activityRelationshipService = new InvokeActivityRelationshipEjb();

    private GenericAccrualGridServiceImpl<ActivityRelationshipDto, ActivityRelationship> impl
        = new GenericAccrualGridServiceImpl<ActivityRelationshipDto, 
        ActivityRelationship>(ActivityRelationship.class, ActivityRelationshipDto.class);
	
	public ActivityRelationshipImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship[] getByTargetPerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id,gov.nih.nci.coppa.services.outcomes.CD typeCode) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          Cd cdDto = CDTransformer.INSTANCE.toDto(typeCode);
          List<ActivityRelationshipDto> actRelDtos = activityRelationshipService
              .getByTargetPerformedActivity(iiDto, cdDto);
          return ActivityRelationshipTransformer.INSTANCE.convert(actRelDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship[] getBySourcePerformedActivity(gov.nih.nci.coppa.services.outcomes.Id id,gov.nih.nci.coppa.services.outcomes.CD typeCode) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          Cd cdDto = CDTransformer.INSTANCE.toDto(typeCode);
          List<ActivityRelationshipDto> actRelDtos = activityRelationshipService
              .getBySourcePerformedActivity(iiDto, cdDto);
          return ActivityRelationshipTransformer.INSTANCE.convert(actRelDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship[] getByStudyProtocol(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<ActivityRelationshipDto> actRelDtos = activityRelationshipService
              .getByStudyProtocol(iiDto);
          return ActivityRelationshipTransformer.INSTANCE.convert(actRelDtos);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship get(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
    return impl.get(id);
  }

  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship create(gov.nih.nci.coppa.services.outcomes.ActivityRelationship activityRelationship) throws RemoteException {
    return impl.create(activityRelationship);
  }

  public gov.nih.nci.coppa.services.outcomes.ActivityRelationship update(gov.nih.nci.coppa.services.outcomes.ActivityRelationship activityRelationship) throws RemoteException {
    return impl.update(activityRelationship);
  }

  public void delete(gov.nih.nci.coppa.services.outcomes.Id id) throws RemoteException {
      impl.delete(id);
  }

}

