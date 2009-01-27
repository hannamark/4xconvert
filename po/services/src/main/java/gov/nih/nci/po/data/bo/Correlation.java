package gov.nih.nci.po.data.bo;

import gov.nih.nci.po.audit.Auditable;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

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
