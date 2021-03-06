package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.po.faults.NullifiedRoleFault;
import gov.nih.nci.coppa.services.grid.faults.CoppaFaultHelper;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.services.correlation.NullifiedRoleException;

/**
 * Transforms NullifiedRoleException types.
 *
 * @author smatyas
 *
 */
public final class NullifiedRoleFaultTransformer
    extends AbstractTransformer<NullifiedRoleFault, NullifiedRoleException>
    implements Transformer<NullifiedRoleFault, NullifiedRoleException> {
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

    /**
     * {@inheritDoc}
     */
    public NullifiedRoleFault[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new NullifiedRoleFault[arg0];
    }

}
