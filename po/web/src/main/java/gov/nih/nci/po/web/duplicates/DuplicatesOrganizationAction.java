package gov.nih.nci.po.web.duplicates;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.DuplicatesOrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.GenericSearchServiceUtil;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action to search for duplicate organizations.
 */
public class DuplicatesOrganizationAction extends ActionSupport implements Preparable {

    /**
     * action result to view entity's detail.
     */
    static final String DETAIL_RESULT = "detail";
    
    private static final long serialVersionUID = 1L;
    private final PaginatedList<Organization> orgs = new PaginatedList<Organization>(0, new ArrayList<Organization>(),
            PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null, OrganizationSortCriterion.ORGANIZATION_ID.name(),
            SortOrderEnum.ASCENDING);

    private DuplicatesOrganizationSearchCriteria criteria = new DuplicatesOrganizationSearchCriteria();
    private String rootKey;
    private Organization organization = new Organization();
    private Organization source = new Organization();

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            criteria = (DuplicatesOrganizationSearchCriteria) getSession().getAttribute(getRootKey());
        }
    }

    private HttpSession getSession() {
        return PoHttpSessionUtil.getSession();
    }

    /**
     * @return success
     */
    public String start() {
        setRootKey(PoHttpSessionUtil.addAttribute(criteria));
        return SUCCESS;
    }

    /**
     * @return success
     */
    public String search() {
        GenericSearchServiceUtil.search(PoRegistry.getOrganizationService(), criteria, getOrgs(),
                OrganizationSortCriterion.class);
        return SUCCESS;
    }
    /**
     * @return search for duplicates results
     */
    public PaginatedList<Organization> getOrgs() {
        return orgs;
    }

    /**
     * @return criteria
     */
    public DuplicatesOrganizationSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria criteria
     */
    public void setCriteria(DuplicatesOrganizationSearchCriteria criteria) {
        this.criteria = criteria;
    }
    /**
     *
     * @return the session key of the root object (org or person)
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     *
     * @param rootKey the session key of the root object.
     */
    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
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
