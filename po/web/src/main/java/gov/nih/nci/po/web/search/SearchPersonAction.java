package gov.nih.nci.po.web.search;

import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.PersonSortCriterion;
import gov.nih.nci.po.service.StrutsPersonSearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.GenericSearchServiceUtil;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.displaytag.properties.SortOrderEnum;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to search for people.
 */
public class SearchPersonAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;
    private final PaginatedList<Person> results = new PaginatedList<Person>(0,
            new ArrayList<Person>(), PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
            PersonSortCriterion.PERSON_ID.name(), SortOrderEnum.ASCENDING);

    private StrutsPersonSearchCriteria criteria = new StrutsPersonSearchCriteria();
    private String rootKey;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            criteria = (StrutsPersonSearchCriteria) getSession().getAttribute(getRootKey());
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
        GenericSearchServiceUtil.search(PoRegistry.getPersonService(), criteria, getResults(),
                PersonSortCriterion.class);
        return SUCCESS;
    }

    /**
     * @return criteria
     */
    public StrutsPersonSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria criteria
     */
    public void setCriteria(StrutsPersonSearchCriteria criteria) {
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
    public PaginatedList<Person> getResults() {
        return results;
    }

}
