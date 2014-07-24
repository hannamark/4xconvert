package gov.nih.nci.webservices.rest.client;

import gov.nih.nci.po.ws.common.types.EntityStatus;
import gov.nih.nci.po.ws.common.types.Organization;
import gov.nih.nci.po.ws.common.types.OrganizationRole;
import gov.nih.nci.po.ws.common.types.OrganizationSearchCriteria;
import gov.nih.nci.po.ws.common.types.OrganizationSearchResultList;
import gov.nih.nci.po.ws.common.types.RoleType;


import java.util.List;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface OrganizationService {

    /**
     *
     * @param id The id of the {@link Organization} to retrieve.
     * @return The {@link Organization} with the given id or null if not found.
     */
    Organization getOrganization(Long id);

    /**
     *
     * @param organization The {@link Organization} to create.
     * @return The id of the newly created {@link Organization}
     */
    Long createOrganization(Organization organization);

    /**
     *
     * @param criteria The {@link OrganizationSearchCriteria} to use.
     * @return The list of {@link gov.nih.nci.po.ws.common.types.OrganizationSearchResult}s found. Must not be null.
     */
    OrganizationSearchResultList searchOrganizations(OrganizationSearchCriteria criteria);

    /**
     *
     * @param updatedOrganization The updated {@link Organization} to save.
     * @return The state after saving.  Must not be null.
     */
    Organization updateOrganization(Organization updatedOrganization);

    /**
     *
     * @param id The id of the {@link Organization} to update.
     * @param newStatus The new {@link EntityStatus}.
     * @return The resulting {@link EntityStatus} after saving.
     */
    EntityStatus updateOrganizationStatus(Long id, EntityStatus newStatus);

    /**
     *
     * @param roleType The {@link RoleType} to look for.
     * @param organizationRoleId The id of the {@link OrganizationRole} to retrieve.
     * @param <T> An implementor of {@link OrganizationRole}.
     * @return The {@link OrganizationRole} with the given id or null if not found.
     */
    <T extends OrganizationRole> T getOrganizationRoleById(RoleType roleType, Long organizationRoleId);

    /**
     *
     * @param roleType The {@link RoleType} to look for.
     * @param ids The id of the {@link OrganizationRole}s to retrieve.
     * @param <T> An implementor of {@link OrganizationRole}.
     * @return A list of hits.
     */
    <T extends OrganizationRole> List<T> getOrganizationRolesByIds(RoleType roleType, Long... ids);

    /**
     *
     * @param orgId The id of an {@link Organization}
     * @return The list of {@link OrganizationRole}s for which the given org is a player.
     */
    List<OrganizationRole> getOrganizationRolesByOrgId(Long orgId);

    /**
     *
     * @param ctepId The CTEP id to look for.
     * @return The list of roles for the given CTEP id.
     */
    List<OrganizationRole> getOrganizationRolesByCtepId(String ctepId);

    /**
     *
     * @param organizationRole The instance to create.
     * @param <T> An implementor of {@link OrganizationRole}
     * @return The id of the created {@link OrganizationRole}
     */
    <T extends OrganizationRole> Long createOrganizationRole(T organizationRole);


    /**
     *
     * @param organizationRole The {@link OrganizationRole} to save.
     * @param <T> An implementor of {@link OrganizationRole}
     * @return The updated state of the instance.
     */
    <T extends OrganizationRole> T updateOrganizationRole(T organizationRole);


    /**
     *
     * @param roleType The type of the target {@link OrganizationRole}
     * @param roleId The id of the role.
     * @param newStatus The new {@link EntityStatus}
     * @return The resulting {@link EntityStatus} after saving.
     */
    EntityStatus updateOrganizationRoleStatus(RoleType roleType, Long roleId, EntityStatus newStatus);
}