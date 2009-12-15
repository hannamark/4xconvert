package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministration;
import gov.nih.nci.coppa.services.outcomes.PerformedSubstanceAdministrationType;

public final class PerformedSubstanceAdministrationTransformerTest
        extends
        AbstractTransformerTestBase<PerformedSubstanceAdministrationTransformer<PerformedSubstanceAdministration, PerformedSubstanceAdministrationDto>, PerformedSubstanceAdministration, PerformedSubstanceAdministrationDto> {

    @Override
    public PerformedSubstanceAdministrationDto makeDtoSimple() {

        PerformedSubstanceAdministrationDto returnVal = new PerformedSubstanceAdministrationDto();
        createDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedSubstanceAdministration makeXmlSimple() {
        PerformedSubstanceAdministration returnVal = new PerformedSubstanceAdministration();
        createXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedSubstanceAdministrationDto x) {
        checkDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedSubstanceAdministration x) {
        checkXmlSimpleHelper(x);
    }

    public static void createDtoSimpleHelper(PerformedSubstanceAdministrationDto returnVal) {
        PerformedActivityTransformerTest.createDtoSimpleHelper(returnVal);
        returnVal.setDose(new PQTransformerTest().makeDtoSimple());
        returnVal.setDoseDescription(new STTransformerTest().makeDtoSimple());
        returnVal.setDoseDuration(new PQTransformerTest().makeDtoSimple());
        returnVal.setDoseFormCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setDoseFrequencyCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setDoseModificationType(new CDTransformerTest().makeDtoSimple());
        returnVal.setDoseRegimen(new STTransformerTest().makeDtoSimple());
        returnVal.setDoseTotal(new PQTransformerTest().makeDtoSimple());
        returnVal.setRouteOfAdministrationCode(new CDTransformerTest().makeDtoSimple());
    }

    public static void createXmlSimpleHelper(PerformedSubstanceAdministrationType returnVal) {
        PerformedActivityTransformerTest.createXmlSimpleHelper(returnVal);
        returnVal.setDose(new PQTransformerTest().makeXmlSimple());
        returnVal.setDoseDescription(new STTransformerTest().makeXmlSimple());
        returnVal.setDoseDuration(new PQTransformerTest().makeXmlSimple());
        returnVal.setDoseFormCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setDoseFrequencyCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setDoseModificationType(new CDTransformerTest().makeXmlSimple());
        returnVal.setDoseRegimen(new STTransformerTest().makeXmlSimple());
        returnVal.setDoseTotal(new PQTransformerTest().makeXmlSimple());
        returnVal.setRouteOfAdministrationCode(new CDTransformerTest().makeXmlSimple());
    }

    public static void checkDtoSimpleHelper(PerformedSubstanceAdministrationDto returnVal) {
        PerformedActivityTransformerTest.checkDtoSimpleHelper(returnVal);
        new PQTransformerTest().verifyDtoSimple(returnVal.getDose());
        new STTransformerTest().verifyDtoSimple(returnVal.getDoseDescription());
        new PQTransformerTest().verifyDtoSimple(returnVal.getDoseDuration());
        new CDTransformerTest().verifyDtoSimple(returnVal.getDoseFormCode());
        new CDTransformerTest().verifyDtoSimple(returnVal.getDoseFrequencyCode());
        new CDTransformerTest().verifyDtoSimple(returnVal.getDoseModificationType());
        new STTransformerTest().verifyDtoSimple(returnVal.getDoseRegimen());
        new PQTransformerTest().verifyDtoSimple(returnVal.getDoseTotal());
        new CDTransformerTest().verifyDtoSimple(returnVal.getRouteOfAdministrationCode());
    }

    public static void checkXmlSimpleHelper(PerformedSubstanceAdministrationType returnVal) {
        PerformedActivityTransformerTest.checkXmlSimpleHelper(returnVal);
        new PQTransformerTest().verifyXmlSimple(returnVal.getDose());
        new STTransformerTest().verifyXmlSimple(returnVal.getDoseDescription());
        new PQTransformerTest().verifyXmlSimple(returnVal.getDoseDuration());
        new CDTransformerTest().verifyXmlSimple(returnVal.getDoseFormCode());
        new CDTransformerTest().verifyXmlSimple(returnVal.getDoseFrequencyCode());
        new CDTransformerTest().verifyXmlSimple(returnVal.getDoseModificationType());
        new STTransformerTest().verifyXmlSimple(returnVal.getDoseRegimen());
        new PQTransformerTest().verifyXmlSimple(returnVal.getDoseTotal());
        new CDTransformerTest().verifyXmlSimple(returnVal.getRouteOfAdministrationCode());
    }

}
