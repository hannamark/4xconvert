package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.pa.StudyOnhold;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOnholdTransformer;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;

/**
 * Test the StudyOnhold transformer.
 */
public class StudyOnholdTransformerTest extends
        AbstractTransformerTestBase<StudyOnholdTransformer, StudyOnhold, StudyOnholdDTO> {

    @Override
    public StudyOnholdDTO makeDtoSimple() {
        StudyOnholdDTO result = new StudyOnholdDTO();
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setOnholdDate(new IVLTSTransformerTest().makeDtoSimple());
        result.setOnholdReasonCode(new CDTransformerTest().makeDtoSimple());
        result.setOnholdReasonText(new STTransformerTest().makeDtoSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public StudyOnhold makeXmlSimple() {
        StudyOnhold result = new StudyOnhold();
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setOnholdDate(new IVLTSTransformerTest().makeXmlSimple());
        result.setOnholdReasonCode(new CDTransformerTest().makeXmlSimple());
        result.setOnholdReasonText(new STTransformerTest().makeXmlSimple());
        result.setStudyProtocolIdentifier(new IITransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(StudyOnholdDTO input) {
        new IITransformerTest().verifyDtoSimple(input.getIdentifier());
        new IVLTSTransformerTest().verifyDtoSimple(input.getOnholdDate());
        new CDTransformerTest().verifyDtoSimple(input.getOnholdReasonCode());
        new STTransformerTest().verifyDtoSimple(input.getOnholdReasonText());
        new IITransformerTest().verifyDtoSimple(input.getStudyProtocolIdentifier());
    }

    @Override
    public void verifyXmlSimple(StudyOnhold input) {
        new IITransformerTest().verifyXmlSimple(input.getIdentifier());
        new IVLTSTransformerTest().verifyXmlSimple(input.getOnholdDate());
        new CDTransformerTest().verifyXmlSimple(input.getOnholdReasonCode());
        new STTransformerTest().verifyXmlSimple(input.getOnholdReasonText());
        new IITransformerTest().verifyXmlSimple(input.getStudyProtocolIdentifier());
    }
}
