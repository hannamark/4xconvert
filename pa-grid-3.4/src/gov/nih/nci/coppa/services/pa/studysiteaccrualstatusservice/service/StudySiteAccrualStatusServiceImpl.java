package gov.nih.nci.coppa.services.pa.studysiteaccrualstatusservice.service;

import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudySiteAccrualStatusEjb;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the StudySiteAccrualStatusService. Dispatches to the remote EJBs and the Transformers.
 */
public class StudySiteAccrualStatusServiceImpl extends StudySiteAccrualStatusServiceImplBase {

    private static final Logger logger = LogManager.getLogger(StudySiteAccrualStatusServiceImpl.class);
    private final InvokeStudySiteAccrualStatusEjb ejb = new InvokeStudySiteAccrualStatusEjb();

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }


    public StudySiteAccrualStatusServiceImpl() throws RemoteException {
        super();
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus getStudySiteAccrualStatus(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        try {
            Ii iiDto = IITransformer.INSTANCE.toDto(id);
            StudySiteAccrualStatusDTO dto = ejb.getStudySiteAccrualStatus(iiDto);
            return StudySiteAccrualStatusTransformer.INSTANCE.toXml(dto);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus createStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

    /**
     * {@inheritDoc}
     */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus updateStudySiteAccrualStatus(gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus studySiteAccrualStatus) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
        throw new RemoteException("Not yet implemented");
    }

  /**
   * {@inheritDoc}
   */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus[] getStudySiteAccrualStatusByStudySite(gov.nih.nci.iso21090.extensions.Id studySiteId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(studySiteId);
          List<StudySiteAccrualStatusDTO> dtoList = ejb.getStudySiteAccrualStatusByStudySite(iiDto);
          return StudySiteAccrualStatusTransformer.INSTANCE.convert(dtoList);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  /**
   * {@inheritDoc}
   */
  public gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus getCurrentStudySiteAccrualStatusByStudySite(gov.nih.nci.iso21090.extensions.Id studySiteId) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(studySiteId);
          StudySiteAccrualStatusDTO dto = ejb.getCurrentStudySiteAccrualStatusByStudySite(iiDto);
          return StudySiteAccrualStatusTransformer.INSTANCE.toXml(dto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}
