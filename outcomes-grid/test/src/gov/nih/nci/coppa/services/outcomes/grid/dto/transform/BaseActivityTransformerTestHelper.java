package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.AbstractActivityDto;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformerTest;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformerTest;
import gov.nih.nci.coppa.services.outcomes.ActivityType;

public final class BaseActivityTransformerTestHelper {

    public static void makeDtoSimple(AbstractActivityDto returnVal) {
        returnVal.setIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setCategoryCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setStudyProtocolIdentifier(new IITransformerTest().makeDtoSimple());
        returnVal.setSubcategoryCode(new CDTransformerTest().makeDtoSimple());
        returnVal.setTextDescription(new STTransformerTest().makeDtoSimple());
    }

    public static void makeXmlSimple(ActivityType returnVal) {
        returnVal.setIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setCategoryCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setStudyProtocolIdentifier(new IITransformerTest().makeXmlSimple());
        returnVal.setSubcategoryCode(new CDTransformerTest().makeXmlSimple());
        returnVal.setTextDescription(new STTransformerTest().makeXmlSimple());
    }

    public static void verifyDtoSimple(AbstractActivityDto x) {
        new IITransformerTest().verifyDtoSimple(x.getIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getCategoryCode());
        new IITransformerTest().verifyDtoSimple(x.getStudyProtocolIdentifier());
        new CDTransformerTest().verifyDtoSimple(x.getSubcategoryCode());
        new STTransformerTest().verifyDtoSimple(x.getTextDescription());
    }

    public static void verifyXmlSimple(ActivityType x) {
        new IITransformerTest().verifyXmlSimple(x.getIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getCategoryCode());
        new IITransformerTest().verifyXmlSimple(x.getStudyProtocolIdentifier());
        new CDTransformerTest().verifyXmlSimple(x.getSubcategoryCode());
        new STTransformerTest().verifyXmlSimple(x.getTextDescription());
    }

}
