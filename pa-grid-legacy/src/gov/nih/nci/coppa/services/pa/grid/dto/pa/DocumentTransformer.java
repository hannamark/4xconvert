package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.EDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.pa.Document;
import gov.nih.nci.pa.iso.dto.DocumentDTO;

/**
 * Transforms Document instances.
 */
public final class DocumentTransformer extends AbstractTransformer<Document, DocumentDTO>
        implements Transformer<Document, DocumentDTO> {

    /**
     * Public singleton.
     */
    public static final DocumentTransformer INSTANCE = new DocumentTransformer();

    private DocumentTransformer() { }

    /**
     * {@inheritDoc}
     */
    public DocumentDTO toDto(Document xml) throws DtoTransformException {
        if (xml == null) {
            return null;
        }

        DocumentDTO result = new DocumentDTO();
        result.setActiveIndicator(BLTransformer.INSTANCE.toDto(xml.getActiveIndicator()));
        result.setFileName(STTransformer.INSTANCE.toDto(xml.getFileName()));
        result.setIdentifier(IITransformer.INSTANCE.toDto(xml.getIdentifier()));
        result.setInactiveCommentText(STTransformer.INSTANCE.toDto(xml.getInactiveCommentText()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toDto(xml.getStudyProtocolIdentifier()));
        result.setText(EDTransformer.INSTANCE.toDto(xml.getText()));
        result.setTypeCode(CDTransformer.INSTANCE.toDto(xml.getTypeCode()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Document toXml(DocumentDTO dto) throws DtoTransformException {
        if (dto == null) {
            return null;
        }

        Document result = new Document();
        result.setActiveIndicator(BLTransformer.INSTANCE.toXml(dto.getActiveIndicator()));
        result.setFileName(STTransformer.INSTANCE.toXml(dto.getFileName()));
        result.setIdentifier(IITransformer.INSTANCE.toXml(dto.getIdentifier()));
        result.setInactiveCommentText(STTransformer.INSTANCE.toXml(dto.getInactiveCommentText()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toXml(dto.getStudyProtocolIdentifier()));
        result.setText(EDTransformer.INSTANCE.toXml(dto.getText()));
        result.setTypeCode(CDTransformer.INSTANCE.toXml(dto.getTypeCode()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Document[] createXmlArray(int arg0) throws DtoTransformException {
        return new Document[arg0];
    }
}
