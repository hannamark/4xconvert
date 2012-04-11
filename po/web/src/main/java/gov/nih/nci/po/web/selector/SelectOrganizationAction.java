package gov.nih.nci.po.web.selector;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.search.SearchOrganizationAction;

import org.apache.commons.collections.CollectionUtils;

import com.opensymphony.xwork2.Preparable;

/**
 * Action to search for organizations.
 */
public class SelectOrganizationAction extends SearchOrganizationAction implements Preparable {

    /**
     * action result to view entity's detail.
     */
    static final String DETAIL_RESULT = "detail";

    private static final long serialVersionUID = 1L;
    private Organization organization = new Organization();
    private Organization source = new Organization();

    /**
     * {@inheritDoc}
     */
    @Override
    public String search() {
        super.search();
        if (CollectionUtils.isEmpty(getResults().getList())) {
            addActionError("No results found.");
        }
        return SUCCESS;
    }

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
     * @return source organization to search for.
     */
    public Organization getSource() {
        return source;
    }

    /**
     * @param source organization to search for.
     */
    public void setSource(Organization source) {
        this.source = source;
    }
}
