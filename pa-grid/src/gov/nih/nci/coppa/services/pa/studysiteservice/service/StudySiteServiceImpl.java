package gov.nih.nci.coppa.services.pa.studysiteservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.StudySite;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudySiteEjb;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudySiteService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author mshestopalov
 *
 */
public class StudySiteServiceImpl extends StudySiteServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteServiceImpl.class);
    private final InvokeStudySiteEjb studySiteService = new InvokeStudySiteEjb();
    private GenericStudyPaGridServiceImpl<StudySiteDTO, StudySite> impl =
            new GenericStudyPaGridServiceImpl<StudySiteDTO, StudySite>(StudySite.class, StudySiteDTO.class);

    public StudySiteServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.pa.StudySite get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.get(id);
  }

  public gov.nih.nci.coppa.services.pa.StudySite[] getByStudyProtocol(gov.nih.nci.coppa.services.pa.Id studyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getByStudyProtocol(studyProtocolId);
  }

  public gov.nih.nci.coppa.services.pa.StudySite[] getByStudyProtocolAndRole(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            StudySiteDTO spcDto = StudySiteTransformer.INSTANCE.toDto(studySite);
            List<StudySiteDTO> dtosList = studySiteService.getByStudyProtocol(iiDto, spcDto);

            return StudySiteTransformer.INSTANCE.convert(dtosList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
  }

  public gov.nih.nci.coppa.services.pa.StudySite[] getByStudyProtocolAndRoles(gov.nih.nci.coppa.services.pa.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudySite[] studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
            Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
            List<StudySiteDTO> spcDtos = StudySiteTransformer.INSTANCE.convert(studySite);
            List<StudySiteDTO> dtosList = studySiteService.getByStudyProtocol(iiDto, spcDtos);

            return StudySiteTransformer.INSTANCE.convert(dtosList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
  }

  public gov.nih.nci.coppa.services.pa.StudySite[] search(gov.nih.nci.coppa.services.pa.StudySite studySite,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          StudySiteDTO org = StudySiteTransformer.INSTANCE.toDto(studySite);
          List<StudySiteDTO> results = studySiteService.search(org, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          StudySite[] returnResults = new StudySite[results.size()];
          int i = 0;
          for (StudySiteDTO res : results) {
              returnResults[i++] = StudySiteTransformer.INSTANCE.toXml(res);
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("SEARCH:STUDYSITE", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  
  public void copy(gov.nih.nci.coppa.services.pa.Id fromStudyProtocolId,gov.nih.nci.coppa.services.pa.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudySite create(gov.nih.nci.coppa.services.pa.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudySite update(gov.nih.nci.coppa.services.pa.StudySite studySite) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

