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
        makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedSubstanceAdministration makeXmlSimple() {
        PerformedSubstanceAdministration returnVal = new PerformedSubstanceAdministration();
        makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedSubstanceAdministrationDto x) {
        verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedSubstanceAdministration x) {
        verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedSubstanceAdministrationDto returnVal) {
        PerformedActivityTransformerTest.makeDtoSimpleHelper(returnVal);
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

    public static void makeXmlSimpleHelper(PerformedSubstanceAdministrationType returnVal) {
        PerformedActivityTransformerTest.makeXmlSimpleHelper(returnVal);
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

    public static void verifyDtoSimpleHelper(PerformedSubstanceAdministrationDto returnVal) {
        PerformedActivityTransformerTest.verifyDtoSimpleHelper(returnVal);
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

    public static void verifyXmlSimpleHelper(PerformedSubstanceAdministrationType returnVal) {
        PerformedActivityTransformerTest.verifyXmlSimpleHelper(returnVal);
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
