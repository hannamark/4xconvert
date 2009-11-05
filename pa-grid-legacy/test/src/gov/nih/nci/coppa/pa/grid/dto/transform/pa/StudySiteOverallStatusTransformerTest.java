package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.pa.StudySiteOverallStatus;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteOverallStatusTransformer;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;

public class StudySiteOverallStatusTransformerTest
        extends
        AbstractTransformerTestBase<StudySiteOverallStatusTransformer, StudySiteOverallStatus, StudySiteOverallStatusDTO> {

    @Override
    public StudySiteOverallStatusDTO makeDtoSimple() {
        StudySiteOverallStatusDTO result = new StudySiteOverallStatusDTO();
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setStatusCode(new CDTransformerTest().makeDtoSimple());
        result.setStatusDate(new TSTransformerTest().makeDtoSimple());
        result.setStudySiteIdentifier(new IITransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public StudySiteOverallStatus makeXmlSimple() {
        StudySiteOverallStatus result = new StudySiteOverallStatus();
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setStatusCode(new CDTransformerTest().makeXmlSimple());
        result.setStatusDate(new TSTransformerTest().makeXmlSimple());
        result.setStudySiteIdentifier(new IITransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(StudySiteOverallStatusDTO x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
        new TSTransformerTest().verifyDtoSimple(x.getStatusDate());
        new IITransformerTest().verifyDtoSimple(x.getStudySiteIdentifier());
    }

    @Override
    public void verifyXmlSimple(StudySiteOverallStatus x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
        new TSTransformerTest().verifyXmlSimple(x.getStatusDate());
        new IITransformerTest().verifyXmlSimple(x.getStudySiteIdentifier());
    }
}
