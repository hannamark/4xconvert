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
        createDtoSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public PerformedSubjectMilestone makeXmlSimple() {
        PerformedSubjectMilestone returnVal = new PerformedSubjectMilestone();
        createXmlSimpleHelper(returnVal);
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(PerformedSubjectMilestoneDto x) {
        checkDtoSimpleHelper(x);
    }

    @Override
    public void verifyXmlSimple(PerformedSubjectMilestone x) {
        checkXmlSimpleHelper(x);
    }

    public static void createDtoSimpleHelper(PerformedSubjectMilestoneDto returnVal) {

        PerformedActivityTransformerTest.createDtoSimpleHelper(returnVal);
        returnVal.setInformedConsentDate(new TSTransformerTest().makeDtoSimple());
        returnVal.setReasonCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setRegistrationDate(new TSTransformerTest().makeDtoSimple());
    }

    public static void createXmlSimpleHelper(PerformedSubjectMilestone returnVal) {

        PerformedActivityTransformerTest.createXmlSimpleHelper(returnVal);
        returnVal.setInformedConsentDate(new TSTransformerTest().makeXmlSimple());
        returnVal.setReasonCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setRegistrationDate(new TSTransformerTest().makeXmlSimple());
    }

    public static void checkDtoSimpleHelper(PerformedSubjectMilestoneDto x) {
        PerformedActivityTransformerTest.checkDtoSimpleHelper(x);
        new TSTransformerTest().verifyDtoSimple(x.getInformedConsentDate());
        new CDTransformerTest().verifyDtoSimple(x.getReasonCode());
        new TSTransformerTest().verifyDtoSimple(x.getRegistrationDate());
    }

    public static void checkXmlSimpleHelper(PerformedSubjectMilestone x) {
        PerformedActivityTransformerTest.checkXmlSimpleHelper(x);
        new TSTransformerTest().verifyXmlSimple(x.getInformedConsentDate());
        new CDTransformerTest().verifyXmlSimple(x.getReasonCode());
        new TSTransformerTest().verifyXmlSimple(x.getRegistrationDate());
    }

}
