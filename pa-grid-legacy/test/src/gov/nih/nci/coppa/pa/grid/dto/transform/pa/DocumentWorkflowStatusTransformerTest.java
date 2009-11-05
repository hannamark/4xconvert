package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.pa.DocumentWorkflowStatus;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentWorkflowStatusTransformer;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;

/**
 * Test the DocumentWorkflowStatus transformer.
 */
public class DocumentWorkflowStatusTransformerTest extends
        AbstractTransformerTestBase<DocumentWorkflowStatusTransformer, DocumentWorkflowStatus, DocumentWorkflowStatusDTO> {

    @Override
    public DocumentWorkflowStatusDTO makeDtoSimple() {
        DocumentWorkflowStatusDTO result = new DocumentWorkflowStatusDTO();
        result.setCommentText(new STTransformerTest().makeDtoSimple());
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setStatusCode(new CDTransformerTest().makeDtoSimple());
        result.setStatusDateRange(new IVLTSTransformerTest().makeDtoSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public DocumentWorkflowStatus makeXmlSimple() {
        DocumentWorkflowStatus result = new DocumentWorkflowStatus();
        result.setCommentText(new STTransformerTest().makeXmlSimple());
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setStatusCode(new CDTransformerTest().makeXmlSimple());
        result.setStatusDateRange(new IVLTSTransformerTest().makeXmlSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(DocumentWorkflowStatusDTO input) {
        new STTransformerTest().verifyDtoSimple(input.getCommentText());
        new IITransformerTest().verifyDtoSimple(input.getIdentifier());
        new CDTransformerTest().verifyDtoSimple(input.getStatusCode());
        new IVLTSTransformerTest().verifyDtoSimple(input.getStatusDateRange());
        new IITransformerTest().verifyDtoSimple(input.getStudyProtocolIdentifier());
    }

    @Override
    public void verifyXmlSimple(DocumentWorkflowStatus input) {
        new STTransformerTest().verifyXmlSimple(input.getCommentText());
        new IITransformerTest().verifyXmlSimple(input.getIdentifier());
        new CDTransformerTest().verifyXmlSimple(input.getStatusCode());
        new IVLTSTransformerTest().verifyXmlSimple(input.getStatusDateRange());
        new IITransformerTest().verifyXmlSimple(input.getStudyProtocolIdentifier());
    }
}
