package gov.nih.nci.coppa.pa.grid.dto.transform.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.pa.StudyOverallStatus;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.StudyOverallStatusTransformer;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;

public class StudyOverallStatusTransformerTest
        extends AbstractTransformerTestBase<StudyOverallStatusTransformer, StudyOverallStatus, StudyOverallStatusDTO> {

        @Override
        public StudyOverallStatusDTO makeDtoSimple() {
            StudyOverallStatusDTO result = new StudyOverallStatusDTO();
            result.setIdentifier(new IITransformerTest().makeDtoSimple());
            result.setReasonText(new STTransformerTest().makeDtoSimple());
            result.setStatusCode(new CDTransformerTest().makeDtoSimple());
            result.setStatusDate(new TSTransformerTest().makeDtoSimple());
            result.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
            return result;
        }

        @Override
        public StudyOverallStatus makeXmlSimple() {
            StudyOverallStatus result = new StudyOverallStatus();
            result.setIdentifier(new IITransformerTest().makeXmlSimple());
            result.setReasonText(new STTransformerTest().makeXmlSimple());
            result.setStatusCode(new CDTransformerTest().makeXmlSimple());
            result.setStatusDate(new TSTransformerTest().makeXmlSimple());
            result.setStudyProtocol(new IITransformerTest().makeXmlSimple());
            return result;
        }

        @Override
        public void verifyDtoSimple(StudyOverallStatusDTO x) {
            new IITransformerTest().verifyDtoSimple(x.getIdentifier());
            new STTransformerTest().verifyDtoSimple(x.getReasonText());
            new CDTransformerTest().verifyDtoSimple(x.getStatusCode());
            new TSTransformerTest().verifyDtoSimple(x.getStatusDate());
            new IITransformerTest().verifyDtoSimple(x.getStudyProtocolIdentifier());
        }

        @Override
        public void verifyXmlSimple(StudyOverallStatus x) {
            new IITransformerTest().verifyXmlSimple(x.getIdentifier());
            new STTransformerTest().verifyXmlSimple(x.getReasonText());
            new CDTransformerTest().verifyXmlSimple(x.getStatusCode());
            new TSTransformerTest().verifyXmlSimple(x.getStatusDate());
            new IITransformerTest().verifyXmlSimple(x.getStudyProtocol());
        }
}