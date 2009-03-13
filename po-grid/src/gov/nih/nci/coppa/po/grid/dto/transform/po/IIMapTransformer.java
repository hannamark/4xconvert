package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.IIMap;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;

import java.util.Map;

/**
 * Transforms Map&lt;Ii, Ii&gt; instances.
 * @author smatyas
 *
 */
public final class IIMapTransformer implements Transformer<IIMap, Map<Ii, Ii>> {
    /**
     * Public singleton.
     */
    public static final IIMapTransformer INSTANCE = new IIMapTransformer();

    private IIMapTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Map<Ii, Ii> toDto(IIMap input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public IIMap toXml(Map<Ii, Ii> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        IIMap output = new IIMap();
        for (Ii key : input.keySet()) {
            IIMap.Entry entry = new IIMap.Entry();
            entry.setKey(IITransformer.INSTANCE.toXml(key));
            entry.setValue(IITransformer.INSTANCE.toXml(input.get(key)));
            output.getEntry().add(entry);
        }
        return output;
    }

}
