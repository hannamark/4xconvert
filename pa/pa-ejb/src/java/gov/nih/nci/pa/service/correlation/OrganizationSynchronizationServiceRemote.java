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
public interface OrganizationSynchronizationServiceRemote {
    
    /**
     * 
     * @param orgIdentifer ii of organization
     * @throws PAException on error
     */
    void synchronizeOrganization(Ii orgIdentifer) throws PAException;

    /***
     * 
     * @param hcfIdentifer po hcf identifier
     * @throws PAException on error
     */
    void synchronizeHealthCareFacility(Ii hcfIdentifer) throws PAException;
    
    /***
     * 
     * @param oscIdentifer po osc identifier
     * @throws PAException on error
     */
    void synchronizeOversightCommittee(Ii oscIdentifer) throws PAException;
    
    /***
     * 
     * @param roIdentifer po ResearchOrganization identifier
     * @throws PAException on error
     */
    void synchronizeResearchOrganization(Ii roIdentifer) throws PAException;
    

}
