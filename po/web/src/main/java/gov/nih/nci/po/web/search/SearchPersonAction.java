package gov.nih.nci.po.web.search;

import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.service.PersonSearchDTO;
import gov.nih.nci.po.service.PersonSearchSortEnum;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.displaytag.properties.MediaTypeEnum;
import org.displaytag.properties.SortOrderEnum;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to search for people.
 */
public class SearchPersonAction extends ActionSupport implements Preparable {

    private static final String EXPORT = "export";
    private static final String RESULTS_TABLE_UID = "row";
    private static final long serialVersionUID = 1L;
    private final PaginatedList<PersonSearchDTO> results = new PaginatedList<PersonSearchDTO>(
            0, new ArrayList<PersonSearchDTO>(),
            PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null,
            PersonSearchSortEnum.PERSON_ID.name(), SortOrderEnum.ASCENDING);

    private PersonSearchCriteria criteria = new PersonSearchCriteria();
    private String rootKey;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (getRootKey() != null) {
            criteria = (PersonSearchCriteria) getSession().getAttribute(
                    getRootKey());
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
    @Validations(customValidators = { @CustomValidator(type = "personsearchcriteria", fieldName = "criteria") })
    public String search() {
        PersonServiceLocal service = PoRegistry
                .getPersonService();
        PageSortParams<PersonSearchDTO> pageSortParams = new PageSortParams<PersonSearchDTO>(
                results.getObjectsPerPage(), (results.getPageNumber() - 1)
                        * results.getObjectsPerPage(), null, results
                        .getSortDirection().equals(SortOrderEnum.DESCENDING),
                Arrays.asList(results.getSortCriterion()));
        results.setList(service.search(criteria, pageSortParams));
        results.setFullListSize((int) service.count(criteria));
        return SUCCESS;
    }

    /**
     * Wrapper used by displaytag to bypass validation and tokenSession
     * intercepter.
     * 
     * @return success
     */
    @SuppressWarnings("deprecation")
    public String searchdt() {
        String returnValue = SUCCESS;
        ParamEncoder encoder = new ParamEncoder(RESULTS_TABLE_UID);
        String exportParamName = encoder
                .encodeParameterName(TableTagParameters.PARAMETER_EXPORTTYPE);
        String exportParamValue = ServletActionContext.getRequest()
                .getParameter(exportParamName);
        if (NumberUtils.isNumber(exportParamValue)) {
            MediaTypeEnum mediaType = MediaTypeEnum.fromCode(Integer
                    .parseInt(exportParamValue));
            if (mediaType == MediaTypeEnum.EXCEL
                    || mediaType == MediaTypeEnum.CSV) {
                returnValue = EXPORT;
                results.setPageNumber(1);
                results.setObjectsPerPage(Integer.MAX_VALUE);
            }
        }
        search();
        return returnValue;
    }

    /**
     * @return criteria
     */
    public PersonSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria
     *            criteria
     */
    public void setCriteria(PersonSearchCriteria criteria) {
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
     * @param rootKey
     *            the session key of the root object.
     */
    public void setRootKey(String rootKey) {
        PoHttpSessionUtil.validateSessionKey(rootKey);
        this.rootKey = rootKey;
    }

    /**
     * @return search results
     */
    public PaginatedList<PersonSearchDTO> getResults() {
        return results;
    }

}
