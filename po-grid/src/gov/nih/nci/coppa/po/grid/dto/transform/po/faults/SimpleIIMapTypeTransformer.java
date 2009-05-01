package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.faults.SimpleIIMapType;
import gov.nih.nci.coppa.po.faults.SimpleIIMapTypeEntry;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Transforms axis-based SimpleIIMapType types.
 *
 * @author smatyas
 *
 */
public final class SimpleIIMapTypeTransformer implements Transformer<SimpleIIMapType, Map<Ii, Ii>> {
    /**
     * Public singleton.
     */
    public static final SimpleIIMapTypeTransformer INSTANCE = new SimpleIIMapTypeTransformer();

    private SimpleIIMapTypeTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Map<Ii, Ii> toDto(SimpleIIMapType input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public SimpleIIMapType toXml(Map<Ii, Ii> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        SimpleIIMapType output = new SimpleIIMapType();
        List<SimpleIIMapTypeEntry> entries = new ArrayList<SimpleIIMapTypeEntry>();
        for (Ii key : input.keySet()) {
            SimpleIIMapTypeEntry entry = new SimpleIIMapTypeEntry();
            if (key != null) {
                entry.setKey(key.getExtension());
                Ii value = input.get(key);
                entry.setValue((value == null) ? null : value.getExtension());
                entries.add(entry);
            }
        }
        output.setEntry(entries.toArray(new SimpleIIMapTypeEntry[entries.size()]));
        return output;
    }

}
