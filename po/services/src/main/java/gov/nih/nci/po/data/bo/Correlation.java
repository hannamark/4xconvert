package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.audit.Auditable;

/**
 *
 * @author gax
 */
public interface Correlation extends PersistentObject, Auditable, CuratableRole {
    /**
     * @param status the Correlation's status code.
     */
    void setStatus(RoleStatus status);
}
