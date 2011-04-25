package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.report.service.Summ4RepLocal;
import gov.nih.nci.pa.report.service.Summ4ReportBean;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.dto.criteria.Summ4RepCriteriaWebDto;
import gov.nih.nci.pa.viewer.dto.result.Summ4ResultWebDto;
import gov.nih.nci.pa.viewer.util.ViewerConstants;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * @author Max Shestopalov
 */
public class Summ4RepAction 
        extends AbstractReportAction <Summ4RepCriteriaWebDto, Summ4ResultWebDto> {

    private static final long serialVersionUID = 7222372874396709972L;
    private Summ4RepCriteriaWebDto criteria;
  
    private final Map<String, List<Summ4ResultWebDto>> agentDeviceMap = new HashMap<String, List<Summ4ResultWebDto>>();
    private final Map<String, List<Summ4ResultWebDto>> otherInterventionMap
        = new HashMap<String, List<Summ4ResultWebDto>>();

    private final Map<String, String> families = new TreeMap<String, String>();
    private final Map<String, String> organizations = new TreeMap<String, String>();
    private final List<String> autoCompleteResult = new ArrayList<String>();
    private final List<String> orgSearchTypes = new ArrayList<String>();
    private String orgSearchType = getText("report.orgSearchType.byName");
    
    /**
     * Limit of search results.
     */
    public static final int MAX_LIMIT = 100;
    
    /**
     * Default constructor.
     */
    public Summ4RepAction() {
        getOrgSearchTypes().add(getText("report.orgSearchType.byName"));
        getOrgSearchTypes().add(getText("report.orgSearchType.byFamily"));
    }
    
    /**
     * @return the epidemOutcomeList
     */
    public List<Summ4ResultWebDto> getEpidemOutcomeList() {
        return epidemOutcomeList;
    }

    /**
     * @return the anciCorrList
     */
    public List<Summ4ResultWebDto> getAnciCorrList() {
        return anciCorrList;
    }
    private final List<Summ4ResultWebDto> epidemOutcomeList = new ArrayList<Summ4ResultWebDto>();
    private final List<Summ4ResultWebDto> anciCorrList = new ArrayList<Summ4ResultWebDto>();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        setCriteria(new Summ4RepCriteriaWebDto());
        loadFamilies();
        loadOrganizations();
        return super.execute();
    }

    private void loadFamilies() {
        Summ4RepLocal local = ViewerServiceLocator.getInstance().getSumm4ReportService();
        try {
            getFamilies().clear();
            getFamilies().putAll(local.getFamilies(MAX_LIMIT));
        } catch (TooManyResultsException e) {
            addActionError(e.getMessage());
        }
    }

    private boolean isCriteriaValid() {
        boolean returnVal = true;
        if (CollectionUtils.isEmpty(criteria.getOrgNames()) && StringUtils.isBlank(criteria.getOrgName())) {
            addActionError("An Organization name is required.");
            returnVal = false;
        }
        
        if (StringUtils.isBlank(criteria.getIntervalStartDate())) {
            addActionError("A Start Date is required.");
            returnVal = false;
        }
        
        if (StringUtils.isBlank(criteria.getIntervalEndDate())) {
            addActionError("An End Date is required.");
            returnVal = false;
        }
        
        return returnVal;
    }
    
    private boolean isReportInError() {
        if (!isCriteriaValid()) {
            return true;
        }
        
        Summ4RepLocal local = ViewerServiceLocator.getInstance().getSumm4ReportService();
        List<Summ4RepResultDto> isoList;
        try {
            isoList = local.get(criteria.getIsoDto());
        } catch (PAException e) {
            addActionError(e.getMessage());
            return true;
        }
        setResultList(Summ4ResultWebDto.getWebList(isoList));
        
        return false;
    }
    
    private void storeItem(Summ4ResultWebDto item) {
   
        if (StringUtils.equals(item.getSortCriteria(), Summ4ReportBean.EPIDEM_OUTCOME)) {
            epidemOutcomeList.add(item);
        } else if (StringUtils.equals(item.getSortCriteria(), Summ4ReportBean.AGENT_DEVICE)) {
            // we shouldn't have an item w/out a getSubSortCriteria.
            addItemToMap(agentDeviceMap, item);
        } else if (StringUtils.equals(item.getSortCriteria(), Summ4ReportBean.ANCILLARY_CORRELATIVE)) {
            anciCorrList.add(item);
        } else if (StringUtils.equals(item.getSortCriteria(), Summ4ReportBean.OTHER_INTERVENTION)) {
            // we shouldn't have an item w/out a type.
            addItemToMap(otherInterventionMap, item);
        }
    }
    
    private void addItemToMap(Map<String, List<Summ4ResultWebDto>> myMap, Summ4ResultWebDto item) {
        if (!myMap.containsKey(item.getSubSortCriteria())) {
            myMap.put(item.getSubSortCriteria(), new ArrayList<Summ4ResultWebDto>());
        }
        myMap.get(item.getSubSortCriteria()).add(item);
    }
        
    private void sessionScopeStoreAttributes() {
        ServletActionContext.getRequest().getSession()
            .setAttribute(ViewerConstants.SUMM4_AGENT_DEVICE_RESULT_MAP, agentDeviceMap);
        
        ServletActionContext.getRequest().getSession()
            .setAttribute(ViewerConstants.SUMM4_OTHER_INTERVENTION_RESULT_MAP, otherInterventionMap);
        ServletActionContext.getRequest().getSession()
            .setAttribute(ViewerConstants.SUMM4_EPIDEMIOLOGIC_OTHER_OUTCOME_RESULT_LIST, epidemOutcomeList);
        ServletActionContext.getRequest().getSession()
            .setAttribute(ViewerConstants.SUMM4_ANCILLARY_CORRELATIVE_RESULT_LIST, anciCorrList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {
        loadFamilies();
        loadOrganizations();

        if (isReportInError()) {
            return super.execute();
        }
       
        for (Summ4ResultWebDto item : getResultList()) {
            storeItem(item);
        }
        
        sessionScopeStoreAttributes();
        
        return super.getReport();
    }
    
    /**
     * Auto complete action.
     * @return list
     * @throws PAException if error
     */
    public String getAutoComplete() throws PAException {
        Summ4RepLocal local = ViewerServiceLocator.getInstance().getSumm4ReportService();
       
        getAutoCompleteResult().clear();
        try {
            getAutoCompleteResult().addAll(local.searchPoOrgNames(criteria.getOrgName(), MAX_LIMIT));
        } catch (TooManyResultsException e) {
            getAutoCompleteResult().add(criteria.getOrgName());
        }    
        
        return super.getReport();
    }

    /**
     * Get organizations based on family name.
     * @return list
     */
    public String loadOrganizations() {
        Summ4RepLocal local = ViewerServiceLocator.getInstance().getSumm4ReportService();
        getOrganizations().clear();
        if (criteria == null || StringUtils.isEmpty(criteria.getFamilyId())) {
            return super.getReport();
        }
        try {
            Map<String, String> orgMap = local.getOrganizations(criteria.getFamilyId(), MAX_LIMIT);
            for (String orgName : orgMap.keySet()) {
                getOrganizations().put(orgName,
                        orgName.concat(" (" + orgMap.get(orgName).toUpperCase(Locale.getDefault()) + ")"));
            }
        } catch (TooManyResultsException e) {
            addActionError(e.getMessage());
        }    
        
        return super.getReport();
    }

    /**
     * @return the criteria
     */
    public Summ4RepCriteriaWebDto getCriteria() {
        return criteria;
    }
    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(Summ4RepCriteriaWebDto criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the autoCompleteResult
     */
    public List<String> getAutoCompleteResult() {
        return autoCompleteResult;
    }

    /**
     * @return the organizations
     */
    public Map<String, String> getOrganizations() {
        return organizations;
    }

    /**
     * @return the agentDeviceMap
     */
    public Map<String, List<Summ4ResultWebDto>> getAgentDeviceMap() {
        return agentDeviceMap;
    }


    /**
     * @return the otherInterventionMap
     */
    public Map<String, List<Summ4ResultWebDto>> getOtherInterventionMap() {
        return otherInterventionMap;
    }

    /**
     * @return the families
     */
    public Map<String, String> getFamilies() {
        return families;
    }

    /**
     * @return the orgSearchTypes
     */
    public List<String> getOrgSearchTypes() {
        return orgSearchTypes;
    }
    
    /**
     * @return the defaultOrgSearchType
     */
    public String getDefaultOrgSearchType() {
        return "Find by Org Name";
    }

    /**
     * @return the orgSearchType
     */
    public String getOrgSearchType() {
        return orgSearchType;
    }

    /**
     * @param orgSearchType the orgSearchType to set
     */
    public void setOrgSearchType(String orgSearchType) {
        this.orgSearchType = orgSearchType;
    }
}