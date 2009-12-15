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
        PerformedRadiationAdministrationTransformerTest.createDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedRadiationAdministration makeXmlSimple() {
        PerformedRadiationAdministration returnVal = new PerformedRadiationAdministration();
        PerformedRadiationAdministrationTransformerTest.createXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedRadiationAdministrationDto x) {
        PerformedRadiationAdministrationTransformerTest.checkDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedRadiationAdministration x) {
        PerformedRadiationAdministrationTransformerTest.checkXmlSimpleHelper(x);
    }

    public static void createDtoSimpleHelper(PerformedRadiationAdministrationDto returnVal) {
        PerformedSubstanceAdministrationTransformerTest.createDtoSimpleHelper(returnVal);
        returnVal.setMachineTypeCode(new CDTransformerTest().makeDtoSimple());
    }

    public static void createXmlSimpleHelper(PerformedRadiationAdministration returnVal) {
        PerformedSubstanceAdministrationTransformerTest.createXmlSimpleHelper(returnVal);
        returnVal.setMachineTypeCode(new CDTransformerTest().makeXmlSimple());
    }

    public static void checkDtoSimpleHelper(PerformedRadiationAdministrationDto returnVal) {
        PerformedSubstanceAdministrationTransformerTest.checkDtoSimpleHelper(returnVal);
        new CDTransformerTest().verifyDtoSimple(returnVal.getMachineTypeCode());
    }

    public static void checkXmlSimpleHelper(PerformedRadiationAdministration returnVal) {
        PerformedSubstanceAdministrationTransformerTest.checkXmlSimpleHelper(returnVal);
        new CDTransformerTest().verifyXmlSimple(returnVal.getMachineTypeCode());
    }

}
