package gov.nih.nci.po.web.search;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.displaytag.properties.SortOrderEnum;

import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.service.StrutsOrganizationSearchCriteria;
import gov.nih.nci.po.service.OrganizationSortCriterion;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.GenericSearchServiceUtil;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to search for organizations.
 */
public class SearchOrganizationAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final PaginatedList<Organization> results = new PaginatedList<Organization>(0,
            new ArrayList<Organization>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
            OrganizationSortCriterion.ORGANIZATION_ID.name(), SortOrderEnum.ASCENDING);

    private StrutsOrganizationSearchCriteria criteria = new StrutsOrganizationSearchCriteria();
    private String rootKey;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            criteria = (StrutsOrganizationSearchCriteria) getSession().getAttribute(getRootKey());
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
    @Validations(customValidators = { @CustomValidator(type = "searchcriteria", fieldName = "criteria") })
    public String search() {
        GenericSearchServiceUtil.search(PoRegistry.getOrganizationService(), criteria, getResults(),
                OrganizationSortCriterion.class);
        return SUCCESS;
    }

    /**
     * @return criteria
     */
    public StrutsOrganizationSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria criteria
     */
    public void setCriteria(StrutsOrganizationSearchCriteria criteria) {
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
     * @return search results
     */
    public PaginatedList<Organization> getResults() {
        return results;
    }

}
