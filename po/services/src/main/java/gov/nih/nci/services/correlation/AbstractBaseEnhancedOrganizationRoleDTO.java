package gov.nih.nci.services.correlation;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;

/**
 * Custom DTO class to weave in DSet&lt;Tel&gt;.
 */
public abstract class AbstractBaseEnhancedOrganizationRoleDTO extends AbstractOrganizationRoleDTO {
    private static final long serialVersionUID = 1L;

    private DSet<Tel> telecomAddress;

    /**
     * @return the telecomAddress
     */
    public DSet<Tel> getTelecomAddress() {
        return this.telecomAddress;
    }

    /**
     * @param telecomAddress the telecomAddress to set
     */
    public void setTelecomAddress(DSet<Tel> telecomAddress) {
        this.telecomAddress = telecomAddress;
    }
}
