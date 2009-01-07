package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

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
     * @return List list of sp ids
     * @throws PAException on error
     */
    List<Long> synchronizeHealthCareFacility(Ii hcfIdentifer) throws PAException;
    
    /***
     * 
     * @param oscIdentifer po osc identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    List<Long> synchronizeOversightCommittee(Ii oscIdentifer) throws PAException;
    
    /***
     * 
     * @param roIdentifer po ResearchOrganization identifier
     * @return List list of sp ids
     * @throws PAException on error
     */
    List<Long> synchronizeResearchOrganization(Ii roIdentifer) throws PAException;
    

}
