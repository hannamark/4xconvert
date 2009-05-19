package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
/**
 * Transforms Id[] (XML type) and Ii[] (DTO type) instances.
 * @author smatyas
 */
public final class IdArrayTransformer
    extends AbstractTransformer<Id[], Ii[]>
    implements Transformer<Id[], Ii[]> {
    /**
     * Public singleton.
     */
    public static final IdArrayTransformer INSTANCE = new IdArrayTransformer();

    private IdArrayTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public Ii[] toDto(Id[] input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Ii[] results = new Ii[input.length];
        int i = 0;
        for (Id id : input) {
            results[i] = IdTransformer.INSTANCE.toDto(id);
            i++;
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Id[] toXml(Ii[] input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Id[] results = new Id[input.length];
        int i = 0;
        for (Ii id : input) {
            results[i] = IdTransformer.INSTANCE.toXml(id);
            i++;
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    public Id[][] createXmlArray(int arg0) throws DtoTransformException {
        return new Id[arg0][];
    }

}
