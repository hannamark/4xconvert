package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformerTest;
import gov.nih.nci.coppa.services.outcomes.PerformedSubjectMilestone;

public final class PerformedSubjectMilestoneTransformerTest
        extends
        AbstractTransformerTestBase<PerformedSubjectMilestoneTransformer<PerformedSubjectMilestone, PerformedSubjectMilestoneDto>, PerformedSubjectMilestone, PerformedSubjectMilestoneDto> {

    @Override
    public PerformedSubjectMilestoneDto makeDtoSimple() {

        PerformedSubjectMilestoneDto returnVal = new PerformedSubjectMilestoneDto();
        makeDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedSubjectMilestone makeXmlSimple() {
        PerformedSubjectMilestone returnVal = new PerformedSubjectMilestone();
        makeXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedSubjectMilestoneDto x) {
        verifyDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedSubjectMilestone x) {
        verifyXmlSimpleHelper(x);
    }

    public static void makeDtoSimpleHelper(PerformedSubjectMilestoneDto returnVal) {

        PerformedActivityTransformerTest.makeDtoSimpleHelper(returnVal);
        returnVal.setInformedConsentDate(new TSTransformerTest().makeDtoSimple());
        returnVal.setReasonCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setRegistrationDate(new TSTransformerTest().makeDtoSimple());
    }

    public static void makeXmlSimpleHelper(PerformedSubjectMilestone returnVal) {

        PerformedActivityTransformerTest.makeXmlSimpleHelper(returnVal);
        returnVal.setInformedConsentDate(new TSTransformerTest().makeXmlSimple());
        returnVal.setReasonCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setRegistrationDate(new TSTransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimpleHelper(PerformedSubjectMilestoneDto x) {
        PerformedActivityTransformerTest.verifyDtoSimpleHelper(x);
        new TSTransformerTest().verifyDtoSimple(x.getInformedConsentDate());
        new CDTransformerTest().verifyDtoSimple(x.getReasonCode());
        new TSTransformerTest().verifyDtoSimple(x.getRegistrationDate());
    }

    public static void verifyXmlSimpleHelper(PerformedSubjectMilestone x) {
        PerformedActivityTransformerTest.verifyXmlSimpleHelper(x);
        new TSTransformerTest().verifyXmlSimple(x.getInformedConsentDate());
        new CDTransformerTest().verifyXmlSimple(x.getReasonCode());
        new TSTransformerTest().verifyXmlSimple(x.getRegistrationDate());
    }

}
