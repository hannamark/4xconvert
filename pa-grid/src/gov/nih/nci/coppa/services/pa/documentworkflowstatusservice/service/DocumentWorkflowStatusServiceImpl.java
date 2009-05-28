package gov.nih.nci.coppa.services.pa.documentworkflowstatusservice.service;

import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.GenericStudyPaGridServiceImpl;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;

import java.rmi.RemoteException;

/**
 * Implementation of the DocumentWorkflowStatusService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author Hugh Reinhart
 */
public class DocumentWorkflowStatusServiceImpl extends DocumentWorkflowStatusServiceImplBase {

    public DocumentWorkflowStatusServiceImpl() throws RemoteException {
        super();
    }

    private final GenericStudyPaGridServiceImpl<DocumentWorkflowStatusDTO, DocumentWorkflowStatus> impl
            = new GenericStudyPaGridServiceImpl<DocumentWorkflowStatusDTO,
                DocumentWorkflowStatus>(DocumentWorkflowStatus.class, DocumentWorkflowStatusDTO.class);


    public DocumentWorkflowStatus get(Id id) throws RemoteException, PAFault {
        return impl.get(id);
    }

    public DocumentWorkflowStatus[] getCurrentByStudyProtocol(Id id) throws RemoteException, PAFault {
        return impl.getCurrentByStudyProtocol(id);
    }

    public DocumentWorkflowStatus[] getByStudyProtocol(Id id) throws RemoteException, PAFault {
        return impl.getByStudyProtocol(id);
    }

  public void copy(Id fromStudyProtocolId,Id toStudyProtocolId) throws RemoteException, PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public DocumentWorkflowStatus create(DocumentWorkflowStatus documentWorkflowStatus) throws RemoteException, PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public DocumentWorkflowStatus update(DocumentWorkflowStatus documentWorkflowStatus) throws RemoteException, PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

  public void delete(Id id) throws RemoteException, PAFault {
    //TODO: Implement this autogenerated method
    throw new RemoteException("Not yet implemented");
  }

}

