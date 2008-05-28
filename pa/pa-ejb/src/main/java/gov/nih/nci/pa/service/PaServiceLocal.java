/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.bo.HealthcareSite;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 *
 */
@Local
public interface PaServiceLocal {
    /**
     * @param id object id
     * @return HS object
     */
    HealthcareSite getHealthcareSite(long id);

}
