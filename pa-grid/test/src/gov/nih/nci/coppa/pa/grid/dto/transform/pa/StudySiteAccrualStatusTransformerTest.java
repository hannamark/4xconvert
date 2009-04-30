package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudySiteAccrualStatusTransformer;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

public class StudySiteAccrualStatusTransformerTest  extends AbstractTransformerTestBase
        <StudySiteAccrualStatusTransformer, StudySiteAccrualStatus, StudySiteAccrualStatusDTO> {

    @Override
    public StudySiteAccrualStatusDTO makeDtoSimple() {
        StudySiteAccrualStatusDTO result = new StudySiteAccrualStatusDTO();
        result.setIdentifier(new IITransformerTest().makeDtoSimple());
        result.setStatusCode(new CDTransformerTest().makeDtoSimple());
        result.setStatusDate(new TSTransformerTest().makeDtoSimple());
        result.setStudyParticipationIi(new IITransformerTest().makeDtoSimple());
        return result;
    }

    @Override
    public StudySiteAccrualStatus makeXmlSimple() {
        StudySiteAccrualStatus result = new StudySiteAccrualStatus();
        result.setIdentifier(new IITransformerTest().makeXmlSimple());
        result.setStatusCode(new CDTransformerTest().makeXmlSimple());
        result.setStatusDate(new TSTransformerTest().makeXmlSimple());
        result.setStudyParticipation(new IITransformerTest().makeXmlSimple());
        return result;
    }

    @Override
    public void verifyDtoSimple(StudySiteAccrualStatusDTO x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
        new TSTransformerTest().verifyDtoSimple(x.getStatusDate());
        new IITransformerTest().verifyDtoSimple(x.getStudyParticipationIi());
    }

    @Override
    public void verifyXmlSimple(StudySiteAccrualStatus x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
        new TSTransformerTest().verifyXmlSimple(x.getStatusDate());
        new IITransformerTest().verifyXmlSimple(x.getStudyParticipation());
    }

}
