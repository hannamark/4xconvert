package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.ActivityRelationshipDto;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.outcomes.ActivityRelationship;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.ActivityRelationshipTransformer;

public class ActivityRelationshipTransformerTest extends
     AbstractTransformerTestBase <ActivityRelationshipTransformer , ActivityRelationship ,ActivityRelationshipDto> {
   

    @Override
    public ActivityRelationshipDto makeDtoSimple() {
        ActivityRelationshipDto returnVal = new ActivityRelationshipDto();
        returnVal.setIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setSourcePerformedActivityIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setTargetPerformedActivityIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setTypeCode(new CDTransformerTest().makeDtoSimple());
        return returnVal;
    }

    @Override
    public ActivityRelationship makeXmlSimple() {
        ActivityRelationship returnVal = new ActivityRelationship();
        returnVal.setIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setSourcePerformedActivityIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setTargetPerformedActivityIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setTypeCode(new CDTransformerTest().makeXmlSimple());
        return returnVal;
    }

    @Override
    public void verifyDtoSimple(ActivityRelationshipDto x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getSourcePerformedActivityIdentifier());
        new IITransformerTest().verifyDtoSimple(x.getTargetPerformedActivityIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getTypeCode());
    }

    @Override
    public void verifyXmlSimple(ActivityRelationship x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getSourcePerformedActivityIdentifier());
        new IITransformerTest().verifyXmlSimple(x.getTargetPerformedActivityIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getTypeCode());
    }

}
