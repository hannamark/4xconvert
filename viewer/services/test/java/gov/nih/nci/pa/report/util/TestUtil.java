package gov.nih.nci.pa.report.util;

import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.ArrayList;
import java.util.List;

public final class TestUtil {

    public static List<String> isoListToJava(List<St> isoList) {
        List<String> resultList = new ArrayList<String>();
        for (St iso : isoList) {
            resultList.add(StConverter.convertToString(iso));
        }
        return resultList;
    }
}
