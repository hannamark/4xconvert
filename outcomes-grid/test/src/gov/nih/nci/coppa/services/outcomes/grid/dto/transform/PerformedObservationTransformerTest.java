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
        createDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedObservation makeXmlSimple() {
        PerformedObservation returnVal = new PerformedObservation();
        createXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedObservationDto x) {
        checkDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedObservation x) {
        checkXmlSimpleHelper(x);
    }

    public static void createDtoSimpleHelper(PerformedObservationDto returnVal) {

        PerformedActivityTransformerTest.createDtoSimpleHelper(returnVal);
        returnVal.setMethodCode(new DSETCDTransformerTest().makeDtoSimple());
        returnVal.setTargetSiteCode(new CDTransformerTest().makeDtoSimple());

    }

    public static void createXmlSimpleHelper(PerformedObservationType returnVal) {
        PerformedActivityTransformerTest.createXmlSimpleHelper(returnVal);
        returnVal.setMethodCode(new DSETCDTransformerTest().makeXmlSimple());
        returnVal.setTargetSiteCode(new CDTransformerTest().makeXmlSimple());
    }

    public static void checkDtoSimpleHelper(PerformedObservationDto x) {
        PerformedActivityTransformerTest.checkDtoSimpleHelper(x);
        new DSETCDTransformerTest().verifyDtoSimple(x.getMethodCode());
        new CDTransformerTest().verifyDtoSimple(x.getTargetSiteCode());
    }

    public static void checkXmlSimpleHelper(PerformedObservationType x) {
        PerformedActivityTransformerTest.checkXmlSimpleHelper(x);
        new DSETCDTransformerTest().verifyXmlSimple(x.getMethodCode());
        new CDTransformerTest().verifyXmlSimple(x.getTargetSiteCode());
    }

}
