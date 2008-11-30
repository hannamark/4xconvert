package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.service.PAException;

import javax.ejb.Remote;

/**
 * @author Naveen Amiruddin
 * @since 11/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Remote
public interface PersonSynchronizationServiceRemote {

    /**
     * 
     * @param perIdentifer ii of Person
     * @throws PAException on error
     */
    void synchronizePerson(Ii perIdentifer) throws PAException;

    /***
     * 
     * @param crsIdentifer po ClinicalResearchStaff identifier
     * @throws PAException on error
     */
    void synchronizeClinicalResearchStaff(Ii crsIdentifer) throws PAException;
    
}
