package gov.nih.nci.pa.service;

import gov.nih.nci.pa.bo.HealthcareSite;

/**
 * @author Harsha
 *
 */
public interface IProtocolService {
    /**
     * @param id object id
     * @return HS object
     */
    HealthcareSite getHealthcareSite(long id);
}
