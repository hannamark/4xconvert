package gov.nih.nci.coppa.services.pa.grid.dto.pa.faults;

import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.coppa.services.pa.grid.dto.DtoTransformException;
import gov.nih.nci.coppa.services.pa.grid.dto.Transformer;
import gov.nih.nci.pa.service.PAException;

/**
 * Transforms PAFault types.
 */
public final class PAFaultTransformer implements
        Transformer<PAFault, PAException> {
    /**
     * Public singleton.
     */
    public static final PAFaultTransformer INSTANCE = new PAFaultTransformer();

    private PAFaultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public PAException toDto(PAFault input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public PAFault toXml(PAException input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        return POFaultHelper.toFault(new PAFault(), input);
    }
}
