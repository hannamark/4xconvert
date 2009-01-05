package gov.nih.nci.service;

import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;

/**
 * @author hreinhart
 *
 */
public class MockCorrelationUtils implements CorrelationUtilsRemote {

    /**
     * @param paIdentifer
     * @param poIdentifer
     * @return
     * @throws PAException
     */
    public Organization getPAOrganizationByIndetifers(Long paIdentifer,
            String poIdentifer) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param paHealthCareFacilityId
     * @return
     * @throws PAException
     */
    public Organization getPAOrganizationByPAHealthCareFacilityId(
            Long paHealthCareFacilityId) throws PAException {
      for (HealthCareFacility hcf : MockOrganizationCorrelationService.hcfList) {
          if (hcf.getId().equals(paHealthCareFacilityId)) {
              return hcf.getOrganization();
          }
      }
      throw new PAException("HealthCareFacility not found.");
    }

    /**
     * @param paOversightCommitteeId
     * @return
     * @throws PAException
     */
    public Organization getPAOrganizationByPAOversightCommitteeId(
            Long paOversightCommitteeId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param paResearchOrganizationId
     * @return
     * @throws PAException
     */
    public Organization getPAOrganizationByPAResearchOrganizationId(
            Long paResearchOrganizationId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param oc
     * @return
     * @throws PAException
     */
    public OrganizationalContact getPAOrganizationalContact(
            OrganizationalContact oc) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param paIdentifer
     * @param poIdentifer
     * @return
     * @throws PAException
     */
    public Person getPAPersonByIndetifers(Long paIdentifer, String poIdentifer)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param paClinicalResearchStaffId
     * @return
     * @throws PAException
     */
    public Person getPAPersonByPAClinicalResearchStaffId(
            Long paClinicalResearchStaffId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param paOrganizationalContactId
     * @return
     * @throws PAException
     */
    public Person getPAPersonByPAOrganizationalContactId(
            Long paOrganizationalContactId) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

}
