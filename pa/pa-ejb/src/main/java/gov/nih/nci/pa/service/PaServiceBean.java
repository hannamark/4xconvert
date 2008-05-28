package gov.nih.nci.pa.service;

import javax.ejb.Stateless;

import gov.nih.nci.pa.bo.HealthcareSite;

/**
 * @author Hugh Reinhart
 *
 */
@Stateless
public class PaServiceBean implements PaServiceLocal {

    /**
     * @param id object id
     * @return a HS object
     */
    public HealthcareSite getHealthcareSite(long id) {
        HealthcareSite xxx = new HealthcareSite();
        xxx.setName("test name");
        return xxx;
    }

}
