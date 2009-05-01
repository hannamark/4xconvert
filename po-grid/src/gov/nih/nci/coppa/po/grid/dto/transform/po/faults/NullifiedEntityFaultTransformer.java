package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.po.faults.NullifiedEntityFault;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.faults.CoppaFaultHelper;
import gov.nih.nci.services.entity.NullifiedEntityException;

/**
 * Transforms NullifiedEntityException types.
 *
 * @author smatyas
 *
 */
public final class NullifiedEntityFaultTransformer implements
        Transformer<NullifiedEntityFault, NullifiedEntityException> {
    /**
     * Public singleton.
     */
    public static final NullifiedEntityFaultTransformer INSTANCE = new NullifiedEntityFaultTransformer();

    private NullifiedEntityFaultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public NullifiedEntityException toDto(NullifiedEntityFault input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public NullifiedEntityFault toXml(NullifiedEntityException input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        NullifiedEntityFault fault = CoppaFaultHelper.toFault(new NullifiedEntityFault(), input);
        if (input.getNullifiedEntities() != null) {
            fault.setNullifiedEntries(SimpleIIMapTypeTransformer.INSTANCE.toXml(input.getNullifiedEntities()));
        }
        return fault;
    }

}
