/**
 * 
 */
package gov.nih.nci.po.webservices.service.simple;

import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.Organization;
import gov.nih.nci.po.webservices.types.OrganizationRole;
import gov.nih.nci.po.webservices.types.OrganizationSearchCriteria;
import gov.nih.nci.po.webservices.types.OrganizationSearchResult;

import java.util.List;

/**
 * Organization Service Interface.
 * 
 * @author Rohit Gupta
 * 
 */
public interface OrganizationService {

    /**
     * 
     *
     * @param organization
     *            - organization to be created
     * @return Organization - created Organization
     */
    Organization createOrganization(Organization organization);

    /**
     * 
     *
     * @param organization
     *            - organization to be updated
     * @return Organization - updated Organization
     */
    Organization updateOrganization(Organization organization);

    /**
     * 
     * @param organizationID
     *            - Id of the Organization whose status is to be changed.
     * @param status
     *            - new status
     * @return Organization - updated Organization
     */
    Organization changeOrganizationStatus(long organizationID,
            EntityStatus status);

    /**
     * 
     * @param organizationID
     *            - Id of the Organization to be get
     * @return Organization - Organization corresponding to giving Id
     */
    Organization getOrganization(long organizationID);

    /**
     * 
     * @param osCriteria
     *            - OrganizationSearchCriteria
     * @return - List<OrganizationSearchResult> - list of
     *         OrganizationSearchResult matching the search criteria
     */
    List<OrganizationSearchResult> searchOrganizations(
            OrganizationSearchCriteria osCriteria);

    /**
     * 
     * @param organizationRole
     *            - OrganizationRole to be created
     * @return OrganizationRole - created OrganizationRole
     */
    OrganizationRole createOrganizationRole(OrganizationRole organizationRole);

    /**
     * 
     * @param organizationRole
     *            - OrganizationRole to be updated
     * @return OrganizationRole - updated OrganizationRole
     */
    OrganizationRole updateOrganizationRole(OrganizationRole organizationRole);

    /**
     * 
     * @param organizationID
     *            - Id of the Organization whose OrganizationRole to be get
     * @return List<OrganizationRole> - List<OrganizationRole> corresponding to
     *         given OrganizationID
     */
    List<OrganizationRole> getOrganizationRolesByOrgId(long organizationID);

    /**
     * 
     * @param ctepId
     *            - CtepId of the OrganizationRole (RO & HCF roles only)
     * @return List<OrganizationRole> - List<OrganizationRole> corresponding to
     *         given CtepId
     */
    List<OrganizationRole> getOrganizationRolesByCtepId(String ctepId);

    /**
     * @param <T>
     *            - ResearchOrganization or OversightCommittee or
     *            HealthCareFacility
     * @param clazz
     *            ResearchOrganization or OversightCommittee or
     *            HealthCareFacility Class
     * @param organizationRoleID
     *            DB OrganizationRole Id
     * @return <T extends OrganizationRole> - Object of type either
     *         ResearchOrganization or OversightCommittee or HealthCareFacility
     */
    <T extends OrganizationRole> T getOrganizationRoleById(Class<T> clazz,
            long organizationRoleID);

    /**
     * @param <T>
     *            - ResearchOrganization or OversightCommittee or
     *            HealthCareFacility
     * @param clazz
     *            ResearchOrganization or OversightCommittee or
     *            HealthCareFacility
     * @param organizationRoleID
     *            DB OrganizationRole Id
     * @param status
     *            New OrganizationRole Status
     * @return <T extends PersonRole> Object of type either ResearchOrganization
     *         or OversightCommittee or HealthCareFacility
     */
    <T extends OrganizationRole> T changeOrganizationRoleStatus(Class<T> clazz,
            long organizationRoleID, EntityStatus status);

}
