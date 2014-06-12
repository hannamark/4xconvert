package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;

import java.util.Arrays;
import java.util.Map;

/**
 * Transforms Map&lt;String, String[]&gt; instances.
 *
 * @author smatyas
 *
 */
public final class StringMapTransformer
    extends AbstractTransformer<StringMap, Map<String, String[]>>
    implements Transformer<StringMap, Map<String, String[]>> {
    /**
     * Public singleton.
     */
    public static final StringMapTransformer INSTANCE = new StringMapTransformer();

    private StringMapTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> toDto(StringMap input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public StringMap toXml(Map<String, String[]> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        StringMap output = new StringMap();
        for (String key : input.keySet()) {
            StringMap.Entry entry = new StringMap.Entry();
            entry.setKey(key);
            entry.getValue().addAll(Arrays.asList(input.get(key)));
            output.getEntry().add(entry);
        }
        return output;
    }

    /**
     * {@inheritDoc}
     */
    public StringMap[] createXmlArray(int arg0) throws DtoTransformException {
        return new StringMap[arg0];
    }

}
