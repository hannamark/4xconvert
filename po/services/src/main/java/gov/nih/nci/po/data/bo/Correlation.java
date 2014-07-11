package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.iso21090.Ii;

import java.util.Set;

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
    
    /**
     * @param ids the other identifiers of this Correlation.
     */
    void setOtherIdentifiers(Set<Ii> ids);

}
