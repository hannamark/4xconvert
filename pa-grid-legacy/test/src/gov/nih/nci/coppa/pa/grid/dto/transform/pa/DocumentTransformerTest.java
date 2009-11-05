package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.EDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.pa.Document;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.pa.iso.dto.DocumentDTO;

/**
 * Test the Document transformer.
 */
public class DocumentTransformerTest extends
        AbstractTransformerTestBase<DocumentTransformer, Document, DocumentDTO> {

    @Override
    public DocumentDTO makeDtoSimple() {
        DocumentDTO result = new DocumentDTO();
        result.setActiveIndicator(new BLTransformerTest().makeDtoSimple());
        result.setFileName(new STTransformerTest().makeDtoSimple());
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setInactiveCommentText(new STTransformerTest().makeDtoSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
        result.setText(new EDTransformerTest().makeDtoSimple());
        result.setTypeCode(new CDTransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public Document makeXmlSimple() {
        Document result = new Document();
        result.setActiveIndicator(new BLTransformerTest().makeXmlSimple());
        result.setFileName(new STTransformerTest().makeXmlSimple());
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setInactiveCommentText(new STTransformerTest().makeXmlSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeXmlSimple());
        result.setText(new EDTransformerTest().makeXmlSimple());
        result.setTypeCode(new CDTransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(DocumentDTO i) {
        new BLTransformerTest().verifyDtoSimple(i.getActiveIndicator());
        new STTransformerTest().verifyDtoSimple(i.getFileName());
        new IITransformerTest().verifyDtoSimple(i.getIdentifier());
        new STTransformerTest().verifyDtoSimple(i.getInactiveCommentText());
        new IITransformerTest().verifyDtoSimple(i.getStudyProtocolIdentifier());
        new EDTransformerTest().verifyDtoSimple(i.getText());
        new CDTransformerTest().verifyDtoSimple(i.getTypeCode());
    }

    @Override
    public void verifyXmlSimple(Document i) {
        new BLTransformerTest().verifyXmlSimple(i.getActiveIndicator());
        new STTransformerTest().verifyXmlSimple(i.getFileName());
        new IITransformerTest().verifyXmlSimple(i.getIdentifier());
        new STTransformerTest().verifyXmlSimple(i.getInactiveCommentText());
        new IITransformerTest().verifyXmlSimple(i.getStudyProtocolIdentifier());
        new EDTransformerTest().verifyXmlSimple(i.getText());
        new CDTransformerTest().verifyXmlSimple(i.getTypeCode());
    }

}
