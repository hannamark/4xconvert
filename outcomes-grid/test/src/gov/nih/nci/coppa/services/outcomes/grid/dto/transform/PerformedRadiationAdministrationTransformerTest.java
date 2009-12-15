package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedRadiationAdministration;

public final class PerformedRadiationAdministrationTransformerTest
        extends
        AbstractTransformerTestBase<PerformedRadiationAdministrationTransformer<PerformedRadiationAdministration, PerformedRadiationAdministrationDto>, PerformedRadiationAdministration, PerformedRadiationAdministrationDto> {

    @Override
    public PerformedRadiationAdministrationDto makeDtoSimple() {

        PerformedRadiationAdministrationDto returnVal = new PerformedRadiationAdministrationDto();
        PerformedRadiationAdministrationTransformerTest.makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedRadiationAdministration makeXmlSimple() {
        PerformedRadiationAdministration returnVal = new PerformedRadiationAdministration();
        PerformedRadiationAdministrationTransformerTest.makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedRadiationAdministrationDto x) {
        PerformedRadiationAdministrationTransformerTest.verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedRadiationAdministration x) {
        PerformedRadiationAdministrationTransformerTest.verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedRadiationAdministrationDto returnVal) {
        PerformedSubstanceAdministrationTransformerTest.makeDtoSimpleHelper(returnVal);
        returnVal.setMachineTypeCode(new CDTransformerTest().makeDtoSimple());
    }

    public static void makeXmlSimpleHelper(PerformedRadiationAdministration returnVal) {
        PerformedSubstanceAdministrationTransformerTest.makeXmlSimpleHelper(returnVal);
        returnVal.setMachineTypeCode(new CDTransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimpleHelper(PerformedRadiationAdministrationDto returnVal) {
        PerformedSubstanceAdministrationTransformerTest.verifyDtoSimpleHelper(returnVal);
        new CDTransformerTest().verifyDtoSimple(returnVal.getMachineTypeCode());
    }

    public static void verifyXmlSimpleHelper(PerformedRadiationAdministration returnVal) {
        PerformedSubstanceAdministrationTransformerTest.verifyXmlSimpleHelper(returnVal);
        new CDTransformerTest().verifyXmlSimple(returnVal.getMachineTypeCode());
    }

}
