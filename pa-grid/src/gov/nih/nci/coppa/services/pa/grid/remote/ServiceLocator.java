package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;

import javax.naming.NamingException;

/**
 * Locator interface for PA services.
 */
/**
 * @author Steve Lustbader
 */
public interface ServiceLocator {

    /**
     * @return the remote Arm service
     * @throws NamingException if unable to lookup
     */
    ArmServiceRemote getArmService() throws NamingException;

    /**
     * @return the remote StudyProtocol service
     * @throws NamingException if unbale to lookup
     */
    StudyProtocolServiceRemote getStudyProtocolService() throws NamingException;

    /**
     * Gets the StudyResourcing service.
     * @return the remote StudyResourcingService
     * @throws NamingException if unable to lookup
     */
    StudyResourcingServiceRemote getStudyResourcingService() throws NamingException;
}
