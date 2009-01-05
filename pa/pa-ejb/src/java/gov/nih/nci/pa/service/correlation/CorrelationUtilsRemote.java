package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.PAException;

/**
 * An interface for the CorrealtionUtils service.
 * 
 * @author Hugh Reinhart
 * @since 01/05/2009 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public interface CorrelationUtilsRemote {
    /**
     * 
     * @param poIdentifer id
     * @param paIdentifer id
     * @return Person
     * @throws PAException e
     */
    Person getPAPersonByIndetifers(Long paIdentifer, String poIdentifer) throws PAException;
    
    /**
     * @param paClinicalResearchStaffId id
     * @return Person
     * @throws PAException e
     */
    Person getPAPersonByPAClinicalResearchStaffId(Long paClinicalResearchStaffId) throws PAException;

    /**
     * @param paOrganizationalContactId id
     * @return Person
     * @throws PAException e
     */
    Person getPAPersonByPAOrganizationalContactId(Long paOrganizationalContactId) throws PAException;

    /**
     * 
     * @param paIdentifer id
     * @param poIdentifer id
     * @return Organization
     * @throws PAException e
     */
    Organization getPAOrganizationByIndetifers(Long paIdentifer, String poIdentifer) throws PAException;
    
    /**
     * @param paResearchOrganizationId id
     * @return Organization
     * @throws PAException e
     */
    Organization getPAOrganizationByPAResearchOrganizationId(Long paResearchOrganizationId) throws PAException;
    
    /**
     * @param paHealthCareFacilityId id
     * @return Organization
     * @throws PAException e
     */
    Organization getPAOrganizationByPAHealthCareFacilityId(Long paHealthCareFacilityId) throws PAException;

    /**
     * @param paOversightCommitteeId id
     * @return Organization
     * @throws PAException e
     */
    Organization getPAOrganizationByPAOversightCommitteeId(Long paOversightCommitteeId) throws PAException;
    
    /**
     * 
     * @param oc oc
     * @return oc
     * @throws PAException pe
     */
    
    OrganizationalContact getPAOrganizationalContact(OrganizationalContact oc) throws PAException;
}
