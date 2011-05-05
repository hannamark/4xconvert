/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This viewer Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the viewer Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and 
 * have distributed to and by third parties the viewer Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
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