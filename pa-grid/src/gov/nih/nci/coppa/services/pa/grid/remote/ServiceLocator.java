package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.service.ArmServiceRemote;

import javax.naming.NamingException;

/**
 * Locator interface for PA services.
 */
public interface ServiceLocator {

    /**
     * @return the remote Arm service
     * @throws NamingException if unbale to lookup
     */
    ArmServiceRemote getArmService() throws NamingException;
}
