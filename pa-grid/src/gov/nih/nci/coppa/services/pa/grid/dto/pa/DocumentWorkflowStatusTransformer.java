package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;

/**
 * Transforms DocumentWorkflowStatus instances.
 */
public final class DocumentWorkflowStatusTransformer
        extends AbstractTransformer<DocumentWorkflowStatus, DocumentWorkflowStatusDTO>
        implements Transformer<DocumentWorkflowStatus, DocumentWorkflowStatusDTO> {

    /**
     * Public singleton.
     */
    public static final DocumentWorkflowStatusTransformer INSTANCE = new DocumentWorkflowStatusTransformer();

    private DocumentWorkflowStatusTransformer() { }


    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatusDTO toDto(DocumentWorkflowStatus xml)
    throws DtoTransformException {
        if (xml == null) {
            return null;
        }

        DocumentWorkflowStatusDTO result = new DocumentWorkflowStatusDTO();
        result.setCommentText(STTransformer.INSTANCE.toDto(xml.getCommentText()));
        result.setIdentifier(IITransformer.INSTANCE.toDto(xml.getIdentifier()));
        result.setStatusCode(CDTransformer.INSTANCE.toDto(xml.getStatusCode()));
        result.setStatusDateRange(TSTransformer.INSTANCE.toDto(xml.getStatusDateRange()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toDto(xml.getStudyProtocolIdentifier()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatus toXml(DocumentWorkflowStatusDTO dto)
    throws DtoTransformException {
        if (dto == null) {
            return null;
        }

        DocumentWorkflowStatus result = new DocumentWorkflowStatus();
        result.setCommentText(STTransformer.INSTANCE.toXml(dto.getCommentText()));
        result.setIdentifier(IITransformer.INSTANCE.toXml(dto.getIdentifier()));
        result.setStatusCode(CDTransformer.INSTANCE.toXml(dto.getStatusCode()));
        result.setStatusDateRange(TSTransformer.INSTANCE.toXml(dto.getStatusDateRange()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toXml(dto.getStudyProtocolIdentifier()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public DocumentWorkflowStatus[] createXmlArray(int arg0)
    throws DtoTransformException {
        return new DocumentWorkflowStatus[arg0];
    }
}
