package gov.nih.nci.services.correlation;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.convert.ContactListConverter;
import gov.nih.nci.po.data.convert.TelDSetConverter;
import net.sf.xsnapshot.TransformContext;

/**
 * Extension point for XSnapshot to populate AbstractCustomBasePersonRoleDTO from AbstractBaseOrganizationRole.
 */
public class ExtendedEnhancedOrganizationRoleDTOHelper extends AbstractEnhancedOrganizationRoleDTOHelper {

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyIntoModel(Object snapshot, Object model, TransformContext context) {
        super.copyIntoModel(snapshot, model, context);

        AbstractBaseEnhancedOrganizationRoleDTO s = (AbstractBaseEnhancedOrganizationRoleDTO) snapshot;
        AbstractEnhancedOrganizationRole m = (AbstractEnhancedOrganizationRole) model;
        TelDSetConverter.convertToContactList(s.getTelecomAddress(), m);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void copyIntoSnapshot(Object model, Object snapshot, TransformContext context) {
        super.copyIntoSnapshot(model, snapshot, context);

        AbstractBaseEnhancedOrganizationRoleDTO s = (AbstractBaseEnhancedOrganizationRoleDTO) snapshot;
        AbstractEnhancedOrganizationRole m = (AbstractEnhancedOrganizationRole) model;
        s.setTelecomAddress(ContactListConverter.convertToDSet(m));
    }
}
