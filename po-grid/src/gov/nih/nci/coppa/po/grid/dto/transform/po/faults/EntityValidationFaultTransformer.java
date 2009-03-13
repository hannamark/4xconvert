package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.po.faults.EntityValidationFault;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.po.service.EntityValidationException;

/**
 * Transforms EntityValidationException types.
 * @author smatyas
 *
 */
public final class EntityValidationFaultTransformer implements
        Transformer<EntityValidationFault, EntityValidationException> {
    /**
     * Public singleton.
     */
    public static final EntityValidationFaultTransformer INSTANCE = new EntityValidationFaultTransformer();

    private EntityValidationFaultTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public EntityValidationException toDto(EntityValidationFault input) throws DtoTransformException {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    public EntityValidationFault toXml(EntityValidationException input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        EntityValidationFault fault = POFaultHelper.toFault(new EntityValidationFault(), input);
        if (input.getErrors() != null) {
            fault.setErrors(StringMapTypeTransformer.INSTANCE.toXml(input.getErrors()));
        }
        return fault;
    }

}
