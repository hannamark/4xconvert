package gov.nih.nci.coppa.services.pa.diseaseservice.service;

import gov.nih.nci.coppa.services.pa.Disease;
import gov.nih.nci.coppa.services.pa.grid.GenericPaGridServiceImpl;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DiseaseTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeDiseaseEjb;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Disease service implementation.  Translates XML-level objects to DTOs to call EJBs.
 *
 * @created by Introduce Toolkit version 1.3
 *
 */
public class DiseaseServiceImpl extends DiseaseServiceImplBase {
    private static final Logger logger = LogManager.getLogger(DiseaseServiceImpl.class);

    private final InvokeDiseaseEjb diseaseService = new InvokeDiseaseEjb();

    private GenericPaGridServiceImpl<DiseaseDTO, Disease> impl =
            new GenericPaGridServiceImpl<DiseaseDTO, Disease>(Disease.class, DiseaseDTO.class);

    public DiseaseServiceImpl() throws RemoteException {
        super();
    }

  public gov.nih.nci.coppa.services.pa.Disease get(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      return impl.get(id);
  }

  public gov.nih.nci.coppa.services.pa.Disease[] search(gov.nih.nci.coppa.services.pa.Disease searchCriteria) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          DiseaseDTO diseaseDto = DiseaseTransformer.INSTANCE.toDto(searchCriteria);
          List<DiseaseDTO> results = diseaseService.search(diseaseDto);
          return DiseaseTransformer.INSTANCE.convert(results);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }

  }

  public gov.nih.nci.coppa.services.pa.Disease create(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    throw new RemoteException("Not yet implemented");
  }

  public gov.nih.nci.coppa.services.pa.Disease update(gov.nih.nci.coppa.services.pa.Disease disease) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    throw new RemoteException("Not yet implemented");
  }

  public void delete(gov.nih.nci.iso21090.extensions.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
    throw new RemoteException("Not yet implemented");
  }

}
