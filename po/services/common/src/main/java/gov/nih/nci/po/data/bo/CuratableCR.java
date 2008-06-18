package gov.nih.nci.po.data.bo;

import java.util.Set;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Used to ensure that Curatable types always define accessors to their associated CRs.
 * @param <T> CuratableCR class (OrganizationCR or PersonCR)
 */
public interface CuratableCR<T extends PersistentObject> {

    /**
     * @return all change requests for the curatable
     */
    Set<T> getAllChangeRequests();

    /**
     * @return all unprocessed change requests
     */
    Set<T> getUnprocessedChangeRequests();

}