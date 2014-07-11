package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import org.apache.commons.lang.StringUtils;

/**
 * @author Hugh Reinhart
 * @since Oct 22, 2013
 */
public class PersistentObjectHelper {

    /**
     * Set values for initial object creation.
     * @param obj the incoming object (must not be null)
     * @return object to persist
     */
    public static PersistentObject initialize(PersistentObject obj) {
        if (obj instanceof AbstractEnhancedOrganizationRole) {
            AbstractEnhancedOrganizationRole role = (AbstractEnhancedOrganizationRole) obj;
            if (StringUtils.isBlank(role.getName())) {
                role.setName(role.getPlayer().getName());
            }
            return role;
        } else {
            return obj;
        }
    }
}
