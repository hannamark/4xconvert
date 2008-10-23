package gov.nih.nci.po.web.duplicates;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.search.SearchOrganizationAction;

import com.opensymphony.xwork2.Preparable;

/**
 * Action to search for duplicate organizations.
 */
public class DuplicatesOrganizationAction extends SearchOrganizationAction implements Preparable {

    /**
     * action result to view entity's detail.
     */
    static final String DETAIL_RESULT = "detail";

    private static final long serialVersionUID = 1L;
    private Organization organization = new Organization();
    private Organization source = new Organization();

    /**
     * @return detail page
     */
    public String detail() {
        organization = PoRegistry.getOrganizationService().getById(organization.getId());
        return DETAIL_RESULT;
    }

    /**
     * @return organization to view detail
     */
    public Organization getOrganization() {
        return organization;
    }

    /**
     * @param organization to view detail
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    /**
     * @return source organization to find duplicates for.
     */
    public Organization getSource() {
        return source;
    }

    /**
     * @param source organization to find duplicates for.
     */
    public void setSource(Organization source) {
        this.source = source;
    }
}
