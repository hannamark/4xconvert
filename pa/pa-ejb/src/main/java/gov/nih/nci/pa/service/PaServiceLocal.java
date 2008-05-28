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
     * @return HS object
     */
    HealthcareSite getHealthcareSite();

}
