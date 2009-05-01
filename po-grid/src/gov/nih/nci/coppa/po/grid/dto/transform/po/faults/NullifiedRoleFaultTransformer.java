package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.faults.CoppaFaultHelper;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * Transforms NullifiedRoleException types.
 *
 * @author smatyas
 *
 */
public final class NullifiedRoleFaultTransformer implements Transformer<NullifiedRoleFault, NullifiedRoleException> {
    /**
     * Public singleton.
     */
    public static final NullifiedRoleFaultTransformer INSTANCE = new NullifiedRoleFaultTransformer();

    private NullifiedRoleFaultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public NullifiedRoleException toDto(NullifiedRoleFault input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public NullifiedRoleFault toXml(NullifiedRoleException input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        NullifiedRoleFault fault = CoppaFaultHelper.toFault(new NullifiedRoleFault(), input);
        if (input.getNullifiedEntities() != null) {
            fault.setNullifiedEntries(SimpleIIMapTypeTransformer.INSTANCE.toXml(input.getNullifiedEntities()));
        }
        return fault;
    }

}
