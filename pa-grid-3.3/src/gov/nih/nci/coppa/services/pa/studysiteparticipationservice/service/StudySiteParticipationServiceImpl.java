package gov.nih.nci.coppa.services.pa.studysiteparticipationservice.service;

import gov.nih.nci.coppa.po.grid.dto.transform.po.OrganizationTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.PersonTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeParticipatingSiteEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * Implementation of the StudySiteParticipationService for adding, updating participating sites as well
 * as finding if those sites exist based on NCI study protocol ids and ctep Organization ids.
 * 
 * @author mshestopalov
 */
public class StudySiteParticipationServiceImpl extends StudySiteParticipationServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteParticipationServiceImpl.class);
    private final InvokeParticipatingSiteEjb service = new InvokeParticipatingSiteEjb();
    
	public StudySiteParticipationServiceImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.iso21090.extensions.Id createParticipatingSiteForPropTrial(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.coppa.po.Organization organization,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus status,gov.nih.nci.coppa.po.Person investigator) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          OrganizationDTO organizationDTO = OrganizationTransformer.INSTANCE.toDto(organization);
          StudySiteDTO studySiteDTO =
                  StudySiteTransformer.INSTANCE.toDto(studySite);
          Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
          PersonDTO investigatorDTO = PersonTransformer.INSTANCE.toDto(investigator);
          StudySiteAccrualStatusDTO currentStatus = 
              StudySiteAccrualStatusTransformer.INSTANCE.toDto(status);
          Ii ii = service.createStudySiteParticipantForPropTrial(studyProtocolIi, 
                  organizationDTO, studySiteDTO, currentStatus, investigatorDTO);
          return IdTransformer.INSTANCE.toXml(ii);
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw FaultUtil.reThrowRemote(e);
      }
  }

  public void updateParticipatingSiteForPropTrial(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.iso21090.extensions.Id organizationId,gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus status,gov.nih.nci.coppa.po.Person investigator) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii orgIi = IITransformer.INSTANCE.toDto(organizationId);
          StudySiteDTO studySiteDTO =
                  StudySiteTransformer.INSTANCE.toDto(studySite);
          Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
          PersonDTO investigatorDTO = PersonTransformer.INSTANCE.toDto(investigator);
          StudySiteAccrualStatusDTO currentStatus = 
              StudySiteAccrualStatusTransformer.INSTANCE.toDto(status);
          service.updateStudySiteParticipantForPropTrial(studyProtocolIi, 
                  orgIi, studySiteDTO, currentStatus, investigatorDTO);
          
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.iso21090.extensions.Bl isParticipatingSite(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.iso21090.extensions.Id organizationId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii orgIi = IITransformer.INSTANCE.toDto(organizationId);
          Ii studyProtocolIi = IITransformer.INSTANCE.toDto(studyProtocolId);
          gov.nih.nci.iso21090.Bl isPart = service.isParticipatingSite(studyProtocolIi, orgIi); 
          gov.nih.nci.iso21090.extensions.Bl result = new gov.nih.nci.iso21090.extensions.Bl();
          result.setValue(BlConverter.convertToBoolean(isPart));
          return result;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw FaultUtil.reThrowRemote(e);
      }
  }

}

