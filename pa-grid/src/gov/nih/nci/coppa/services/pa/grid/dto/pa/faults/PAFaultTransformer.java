package gov.nih.nci.coppa.services.pa.grid.dto.pa.faults;

import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.AbstractTransformer;
import gov.nih.nci.coppa.services.grid.faults.CoppaFaultHelper;
import gov.nih.nci.coppa.services.pa.faults.PAFault;
import gov.nih.nci.pa.service.PAException;

/**
 * Transforms PAFault types.
 */
public final class PAFaultTransformer
    extends AbstractTransformer<PAFault, PAException>
    implements Transformer<PAFault, PAException> {
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
        return CoppaFaultHelper.toFault(new PAFault(), input);
    }

    /**
     * {@inheritDoc}
     */
    public PAFault[] createXmlArray(int arg0) throws DtoTransformException {
        return new PAFault[arg0];
    }
}
