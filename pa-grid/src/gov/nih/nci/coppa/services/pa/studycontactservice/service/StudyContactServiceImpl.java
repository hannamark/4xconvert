package gov.nih.nci.coppa.services.pa.studycontactservice.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
import gov.nih.nci.coppa.services.pa.StudyContact;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyContactTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyContactEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudyContactService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author mshestopalov
 */
public class StudyContactServiceImpl extends StudyContactServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudyContactServiceImpl.class);
    private final InvokeStudyContactEjb studyContactService = new InvokeStudyContactEjb();
    public StudyContactServiceImpl() throws RemoteException {
        super();
    }

    private GenericStudyPaGridServiceImpl<StudyContactDTO, StudyContact> impl
    = new GenericStudyPaGridServiceImpl<StudyContactDTO, StudyContact>(StudyContact.class, StudyContactDTO.class);

  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocolAndRole(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      StudyContact[] result = null;
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
          StudyContactDTO spcDto = StudyContactTransformer.INSTANCE
                  .toDto(studyContact);
          List<StudyContactDTO> dtosList = studyContactService.getByStudyProtocol(iiDto, spcDto);

          result = StudyContactTransformer.INSTANCE
          .convert(dtosList);
          return result;
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocolAndRoles(gov.nih.nci.iso21090.extensions.Id studyProtocolId,gov.nih.nci.coppa.services.pa.StudyContact[] studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          StudyContact[] result = null;
          Ii iiDto = IITransformer.INSTANCE.toDto(studyProtocolId);
          List<StudyContactDTO> spcDtos = StudyContactTransformer.INSTANCE
          .convert(studyContact);
          List<StudyContactDTO> dtosList = studyContactService.getByStudyProtocol(iiDto, spcDtos);

          result = StudyContactTransformer.INSTANCE
          .convert(dtosList);

          return result;
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.StudyContact[] search(gov.nih.nci.coppa.services.pa.StudyContact studyContact,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          StudyContactDTO sc = StudyContactTransformer.INSTANCE.toDto(studyContact);
          List<StudyContactDTO> results = studyContactService.search(sc, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          StudyContact[] returnResults = new StudyContact[results.size()];
          int i = 0;
          for (StudyContactDTO res : results) {
              returnResults[i++] = StudyContactTransformer.INSTANCE.toXml(res);
          }
          return returnResults;
      } catch (Exception e) {
          logger.error("SEARCH:STUDYSITE", e);
          throw FaultUtil.reThrowRemote(e);
      }
    }
  
  public gov.nih.nci.coppa.services.pa.StudyContact[] getByStudyProtocol(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.getByStudyProtocol(id);
  }

  public void copy(gov.nih.nci.iso21090.extensions.Id fromStudyProtocolId,gov.nih.nci.iso21090.extensions.Id toStudyProtocolId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyContact get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.get(id);
  }

  public gov.nih.nci.coppa.services.pa.StudyContact create(gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.StudyContact update(gov.nih.nci.coppa.services.pa.StudyContact studyContact) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

