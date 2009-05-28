package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;

/**
 * Wrapper class for invoking the DocumentWorkflowStatus remote EJB.
 */
public class InvokeDocumentWorkflowStatusEjb
    extends InvokeStudyPaServiceEjb<DocumentWorkflowStatusDTO>
    implements DocumentWorkflowStatusServiceRemote {

    /**
     * Const.
     */
    public InvokeDocumentWorkflowStatusEjb() {
        super(DocumentWorkflowStatusDTO.class);
    }
}
