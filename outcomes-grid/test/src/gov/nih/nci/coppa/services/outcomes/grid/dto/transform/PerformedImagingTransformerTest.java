package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedImaging;
import gov.nih.nci.coppa.services.outcomes.PerformedImagingType;

public final class PerformedImagingTransformerTest extends
        AbstractTransformerTestBase<PerformedImagingTransformer, PerformedImaging, PerformedImagingDto> {

    @Override
    public PerformedImagingDto makeDtoSimple() {

        PerformedImagingDto returnVal = new PerformedImagingDto();
        makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedImaging makeXmlSimple() {
        PerformedImaging returnVal = new PerformedImaging();
        makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedImagingDto x) {
        verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedImaging x) {
        verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedImagingDto returnVal) {
        PerformedObservationTransformerTest.makeDtoSimpleHelper(returnVal);
        returnVal.setContrastAgentEnhancementIndicator(new BLTransformerTest().makeDtoSimple());
    }

    public static void makeXmlSimpleHelper(PerformedImagingType returnVal) {
        PerformedObservationTransformerTest.makeXmlSimpleHelper(returnVal);
        returnVal.setContrastAgentEnhancementIndicator(new BLTransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimpleHelper(PerformedImagingDto x) {
        PerformedObservationTransformerTest.verifyDtoSimpleHelper(x);
        new BLTransformerTest().verifyDtoSimple(x.getContrastAgentEnhancementIndicator());
    }

    public static void verifyXmlSimpleHelper(PerformedImaging x) {
        PerformedObservationTransformerTest.verifyXmlSimpleHelper(x);
        new BLTransformerTest().verifyXmlSimple(x.getContrastAgentEnhancementIndicator());
    }

}
