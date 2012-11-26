package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.service.PAException;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Hugh Reinhart
 * @since Nov 15, 2012
 */
@Local
public interface FamilyServiceLocal {

    /**
     * Look up in PO the family of a given organization.
     * @param orgId the PO organization ID
     * @return the families which the organization is a member of 
     * @throws PAException exception
     */
    List<OrgFamilyDTO> getByOrgId(Long orgId) throws PAException;

    /**
     * Look up all the PO ids for organizations which share a family with the given organization.
     * @param orgId the PO organization ID
     * @return a list of PO org ids
     * @throws PAException exception
     */
    List<Long> getAllRelatedOrgs(Long orgId) throws PAException;

    /**
     * Assign the user access to all family trials.
     * @param user the user
     * @param creator the creator
     * @throws PAException exception
     */
    void assignFamilyAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException;

    /**
     * Unassign all accrual access. Per business rule in PO-5257 this is not limited 
     * to family trials.
     * @param user the user
     * @param creator the creator
     * @throws PAException exception
     */
    void unassignFamilyAccrualAccess(RegistryUser user, RegistryUser creator) throws PAException;

    /**
     * Update the site submitter and family submitter permissions for a given trial.
     * @param trialId the trial
     * @throws PAException exception
     */
    void updateSiteAndFamilyPermissions(Long trialId) throws PAException;
}
