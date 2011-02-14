package gov.nih.nci.po.web.roles;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoRegistry;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class to handle editing of Organization Relationships for an Organization.
 */
//TODO Skeleton, fill in as part of PO-3144
public class OrganizationPerspectiveOrganizationRelationshipsAction extends ActionSupport {
    private static final long serialVersionUID = 1285712121733778829L;

    private Organization organization = new Organization();

    /**
     * @return show start page
     */
    public String start() {
        organization = PoRegistry.getOrganizationService().getById(organization.getId());
        initializeCollections();
        return SUCCESS;
    }

    private void initializeCollections() {
       getOrganization().getOrganizationRelationships();
    }

    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
