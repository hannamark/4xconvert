package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedActivity;
import gov.nih.nci.coppa.services.outcomes.PerformedActivityType;

public final class PerformedActivityTransformerTest
        extends
        AbstractTransformerTestBase<PerformedActivityTransformer<PerformedActivity, PerformedActivityDto>, PerformedActivity, PerformedActivityDto> {

    @Override
    public PerformedActivityDto makeDtoSimple() {

        PerformedActivityDto returnVal = new PerformedActivityDto();
        makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedActivity makeXmlSimple() {
        PerformedActivity returnVal = new PerformedActivity();
        makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedActivityDto x) {
        verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedActivity x) {
        verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedActivityDto returnVal) {
        BaseActivityTransformerTestHelper.makeDtoSimple(returnVal);
        returnVal.setActualDateRange(new IVLTSTransformerTest().makeDtoSimple());
        returnVal.setActualDuration(new PQTransformerTest().makeDtoSimple());
        returnVal.setInterventionIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setName(new STTransformerTest().makeDtoSimple());
        returnVal.setNameCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setStudySubjectIdentifier(new IITransformerTest().makeDtoSimple());
    }

    public static void makeXmlSimpleHelper(PerformedActivityType returnVal) {
        BaseActivityTransformerTestHelper.makeXmlSimple(returnVal);
        returnVal.setActualDateRange(new IVLTSTransformerTest().makeXmlSimple());
        returnVal.setActualDuration(new PQTransformerTest().makeXmlSimple());
        returnVal.setInterventionIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setName(new STTransformerTest().makeXmlSimple());
        returnVal.setNameCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setStudySubjectIdentifier(new IITransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimpleHelper(PerformedActivityDto x) {
        BaseActivityTransformerTestHelper.verifyDtoSimple(x);
        new IVLTSTransformerTest().verifyDtoSimple(x.getActualDateRange());
        new PQTransformerTest().verifyDtoSimple(x.getActualDuration());
        new IITransformerTest().verifyDtoSimple(x.getInterventionIdentifier());
        new STTransformerTest().verifyDtoSimple(x.getName());
        new CDTransformerTest().verifyDtoSimple(x.getNameCode());
        new IITransformerTest().verifyDtoSimple(x.getStudySubjectIdentifier());
    }

    public static void verifyXmlSimpleHelper(PerformedActivityType x) {
        BaseActivityTransformerTestHelper.verifyXmlSimple(x);
        new IVLTSTransformerTest().verifyXmlSimple(x.getActualDateRange());
        new PQTransformerTest().verifyXmlSimple(x.getActualDuration());
        new IITransformerTest().verifyXmlSimple(x.getInterventionIdentifier());
        new STTransformerTest().verifyXmlSimple(x.getName());
        new CDTransformerTest().verifyXmlSimple(x.getNameCode());
        new IITransformerTest().verifyXmlSimple(x.getStudySubjectIdentifier());
    }

}
