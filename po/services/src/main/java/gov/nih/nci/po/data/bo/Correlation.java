package gov.nih.nci.po.data.bo;

import gov.nih.nci.coppa.iso.Ii;

import java.util.Set;

import com.fiveamsolutions.nci.commons.audit.Auditable;
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

    /**
     * @return the other identifiers of this Correlation
     */
    Set<Ii> getOtherIdentifiers();

}
