package gov.nih.nci.pa.dao.hibernate;

import gov.nih.nci.pa.bo.HealthcareSite;
import gov.nih.nci.pa.service.IProtocolService;


/**
 * @author Harsha
 */
public class ProtocolServiceImpl implements IProtocolService {


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