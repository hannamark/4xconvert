package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * @author smatyas
 * 
 */
public class CurateEntityValidationAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private Organization organization = new Organization();
    private Organization duplicateOf = new Organization();

    /**
     * {@inheritDoc}
     */
    public void prepare() {
        organization = PoRegistry.getOrganizationService().getById(organization.getId());
        duplicateOf = PoRegistry.getOrganizationService().getById(duplicateOf.getId());
    }

    /**
     * @return org to curate
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param org to curate
     */
    public void setOrganization(Organization org) {
        this.organization = org;
    }

    /**
     * @return the duplicateOf
     */
    public Organization getDuplicateOf() {
        return duplicateOf;
    }

    /**
     * @param duplicateOf the duplicateOf to set
     */
    public void setDuplicateOf(Organization duplicateOf) {
        this.duplicateOf = duplicateOf;
    }

    /**
     * @return associated played roles for duplicateOf organization
     */
    public List<Correlation> getAssociatedPlayedRolesForDuplicateOfOrganization() {
        Set<Correlation> associatedPlayedRoles = PoRegistry.getOrganizationService().getAssociatedPlayedRoles(
                duplicateOf);
        return convertToList(associatedPlayedRoles);
    }

    private List<Correlation> convertToList(Set<Correlation> roles) {
        return (roles == null ? new ArrayList<Correlation>() : new ArrayList<Correlation>(
                roles));
    }

    /**
     * @return associated scoped roles for duplicateOf organization
     */
    public List<Correlation> getAssociatedScopedRolesForDuplicateOfOrganization() {
        Set<Correlation> associatedScopedRoles = PoRegistry.getOrganizationService().getAssociatedScopedRoles(
                duplicateOf);
        return convertToList(associatedScopedRoles);
    }

    /**
     * @return associated played roles for organization
     */
    public List<Correlation> getAssociatedPlayedRolesForOrganization() {
        Set<Correlation> associatedPlayedRoles = PoRegistry.getOrganizationService().getAssociatedPlayedRoles(
                organization);
        return convertToList(associatedPlayedRoles);
    }

    /**
     * @return associated played roles for organization
     */
    public List<Correlation> getAssociatedScopedRolesForOrganization() {
        Set<Correlation> associatedScopedRoles = PoRegistry.getOrganizationService().getAssociatedScopedRoles(
                organization);
        return convertToList(associatedScopedRoles);
    }
}
