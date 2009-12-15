package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.DSETCDTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedObservation;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationType;

public final class PerformedObservationTransformerTest
        extends
        AbstractTransformerTestBase<PerformedObservationTransformer<PerformedObservation, PerformedObservationDto>, PerformedObservation, PerformedObservationDto> {

    @Override
    public PerformedObservationDto makeDtoSimple() {
        PerformedObservationDto returnVal = new PerformedObservationDto();
        makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedObservation makeXmlSimple() {
        PerformedObservation returnVal = new PerformedObservation();
        makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedObservationDto x) {
        verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedObservation x) {
        verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedObservationDto returnVal) {

        PerformedActivityTransformerTest.makeDtoSimpleHelper(returnVal);
        returnVal.setMethodCode(new DSETCDTransformerTest().makeDtoSimple());
        returnVal.setTargetSiteCode(new CDTransformerTest().makeDtoSimple());

    }

    public static void makeXmlSimpleHelper(PerformedObservationType returnVal) {
        PerformedActivityTransformerTest.makeXmlSimpleHelper(returnVal);
        returnVal.setMethodCode(new DSETCDTransformerTest().makeXmlSimple());
        returnVal.setTargetSiteCode(new CDTransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimpleHelper(PerformedObservationDto x) {
        PerformedActivityTransformerTest.verifyDtoSimpleHelper(x);
        new DSETCDTransformerTest().verifyDtoSimple(x.getMethodCode());
        new CDTransformerTest().verifyDtoSimple(x.getTargetSiteCode());
    }

    public static void verifyXmlSimpleHelper(PerformedObservationType x) {
        PerformedActivityTransformerTest.verifyXmlSimpleHelper(x);
        new DSETCDTransformerTest().verifyXmlSimple(x.getMethodCode());
        new CDTransformerTest().verifyXmlSimple(x.getTargetSiteCode());
    }

}
