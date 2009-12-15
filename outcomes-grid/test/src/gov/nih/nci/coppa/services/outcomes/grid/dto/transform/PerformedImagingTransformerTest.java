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
        createDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedImaging makeXmlSimple() {
        PerformedImaging returnVal = new PerformedImaging();
        createXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedImagingDto x) {
        checkDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedImaging x) {
        checkXmlSimpleHelper(x);
    }

    public static void createDtoSimpleHelper(PerformedImagingDto returnVal) {
        PerformedObservationTransformerTest.createDtoSimpleHelper(returnVal);
        returnVal.setContrastAgentEnhancementIndicator(new BLTransformerTest().makeDtoSimple());
    }

    public static void createXmlSimpleHelper(PerformedImagingType returnVal) {
        PerformedObservationTransformerTest.createXmlSimpleHelper(returnVal);
        returnVal.setContrastAgentEnhancementIndicator(new BLTransformerTest().makeXmlSimple());
    }

    public static void checkDtoSimpleHelper(PerformedImagingDto x) {
        PerformedObservationTransformerTest.checkDtoSimpleHelper(x);
        new BLTransformerTest().verifyDtoSimple(x.getContrastAgentEnhancementIndicator());
    }

    public static void checkXmlSimpleHelper(PerformedImaging x) {
        PerformedObservationTransformerTest.checkXmlSimpleHelper(x);
        new BLTransformerTest().verifyXmlSimple(x.getContrastAgentEnhancementIndicator());
    }

}
