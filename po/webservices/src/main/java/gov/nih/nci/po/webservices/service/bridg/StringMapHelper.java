package gov.nih.nci.po.webservices.service.bridg;

import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.StringMapType;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class StringMapHelper {

    /**
     * Transforms Map<String, String[]> to StringMap.
     * @param errors The map of errors
     * @return A StringMap
     */
    public static final StringMap toStringMap(Map<String, String[]> errors) {
        StringMap result = new StringMap();

        for (Map.Entry<String, String[]> entry : errors.entrySet()) {
            StringMapType.Entry errorEntry = new StringMapType.Entry();
            errorEntry.setKey(entry.getKey());
            errorEntry.getValue().addAll(Arrays.asList(entry.getValue()));
            result.getEntry().add(errorEntry);
        }

        return result;
    }
}
