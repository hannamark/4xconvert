package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.po.faults.StringMapType;
import gov.nih.nci.coppa.po.faults.StringMapTypeEntry;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * Transforms axis-based StringMapType types.
 * 
 * @author smatyas
 * 
 */
public final class StringMapTypeTransformer implements Transformer<StringMapType, Map<String, String[]>> {
    /**
     * Public singleton.
     */
    public static final StringMapTypeTransformer INSTANCE = new StringMapTypeTransformer();

    private StringMapTypeTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> toDto(StringMapType input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public StringMapType toXml(Map<String, String[]> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        StringMapType output = new StringMapType();
        List<StringMapTypeEntry> entries = new ArrayList<StringMapTypeEntry>();
        for (String key : input.keySet()) {
            if (StringUtils.isNotBlank(key)) {
                StringMapTypeEntry entry = new StringMapTypeEntry();
                entry.setKey(key);
                entry.setValue(input.get(key));
                entries.add(entry);
            }
        }
        output.setEntry(entries.toArray(new StringMapTypeEntry[entries.size()]));
        return output;
    }

}
