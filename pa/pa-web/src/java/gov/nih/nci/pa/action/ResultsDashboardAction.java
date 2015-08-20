/**
 * 
 */
package gov.nih.nci.pa.action; // NOPMD

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_OTHER_IDENTIFIERS;
import static gov.nih.nci.pa.util.Constants.IS_RESULTS_ABSTRACTOR;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.dto.StudyProcessingErrorDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProcessingErrorService;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Gopal Unnikrishnan (unnikrishnang)
 * 
 */
@SuppressWarnings({ "PMD.TooManyMethods" })
public class ResultsDashboardAction extends AbstractCheckInOutAction implements
        Preparable, ServletRequestAware {
    private static final String NON_RESULT_ABSTRACTOR_LANDING = "nonResultAbstractorLanding";
    private static final String RESULT_ABSTRACTOR_LANDING = "resultAbstractorLanding";
    private static final String AJAX_RESPONSE = "ajaxResponse";
    
    
    private static final Logger LOG = Logger.getLogger(DashboardAction.class);

    private static final long serialVersionUID = 8458441253215157815L;
    private final CorrelationUtils correlationUtils = new CorrelationUtils();
    private final PAServiceUtils paServiceUtils = new PAServiceUtils();

    private HttpServletRequest request;

    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyProtocolService studyProtocolService;
    private StudyProcessingErrorService speService;
    // fields that capture search criteria
    private Boolean section801IndicatorYes; 
    private Boolean section801IndicatorNo;
    private Date pcdFrom;
    private Date pcdTo;
    private String pcdFromType;
    private String pcdToType;
    
    //Results
    private List<StudyProtocolQueryDTO> results;
  
    //Change date parameters
    private Long studyId;
    private Date dateValue;
    private String dateAttr;
    
    private InputStream ajaxResponseStream;
 
    // Chart Data
    private int inProcessCnt = 0;
    private int completedCnt = 0;
    private int notStartedCnt = 0;
    private int issuesCnt = 0;
    
    private String studyNCIId;
    
    
    
    @Override
    public String execute() throws PAException {
        clearFilters();
        return search();
    }

  
    /**
     * Search study results
     * @return String
     * @throws PAException
     *             PAException
     */
    public String search() throws PAException {
        if (!canAccessDashboard()) {
            return NON_RESULT_ABSTRACTOR_LANDING;
        }

        try {
            StudyProtocolQueryCriteria criteria = buildCriteria();
            return search(criteria);
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return RESULT_ABSTRACTOR_LANDING;
    }

    /**
     * Update results date of a study protocol
     * @return String
     */
    public String ajaxChangeDate() {
        if (!canAccessDashboard()) {
            return NON_RESULT_ABSTRACTOR_LANDING;
        }
        
        if (studyProtocolService.updateStudyProtocolResultsDate(studyId, dateAttr, 
                new Timestamp(dateValue.getTime()))) {
            ajaxResponseStream = new ByteArrayInputStream(SUCCESS.getBytes());    
        } else {
            ajaxResponseStream = new ByteArrayInputStream(ERROR.getBytes());
        }
        return AJAX_RESPONSE;
    }
    

    /**
     * Get the study protocol id for the given trails NCI id
     * @return result view
     * @throws PAException 
     */
    public String ajaxGetStudyStudyProtocolIdByNCIId() throws PAException {
        Ii nciIi = new Ii();
        nciIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        nciIi.setExtension(getStudyNCIId());
        StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(nciIi);
        if (sp != null) {
            long spId = IiConverter.convertToLong(sp.getIdentifier());
            ajaxResponseStream = new ByteArrayInputStream(String.valueOf(spId).getBytes());
            StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService
                    .getTrialSummaryByStudyProtocolId(spId);
            ActionUtils.loadProtocolDataInSession(studyProtocolQueryDTO, correlationUtils, paServiceUtils);
        } else {
            ajaxResponseStream = new ByteArrayInputStream("".getBytes());
        }
        return AJAX_RESPONSE;
    }
    
   /** @param servletRequest
    *            the servletRequest to set
    */
   public void setServletRequest(HttpServletRequest servletRequest) {
       this.request = servletRequest;
   }

   @Override
   public void prepare() {
       ActionUtils.setUserRolesInSession(request);
       protocolQueryService = PaRegistry.getProtocolQueryService();
       studyProtocolService = PaRegistry.getStudyProtocolService();
       speService = PaRegistry.getStudyProcessingErrorService();
   }
   
    private void clearFilters() {
        pcdFrom = null;
        pcdTo = null;
        pcdFromType = null;
        pcdToType = null;
    }

    private boolean canAccessDashboard() {
        return isInRole(IS_RESULTS_ABSTRACTOR);
    }

    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(request.getSession().getAttribute(roleFlag));
    }
    
    private String search(StudyProtocolQueryCriteria... criteriaList) {
        try {
            
            results = new ArrayList<StudyProtocolQueryDTO>();
            for (StudyProtocolQueryCriteria criteria : criteriaList) {
                criteria.setNciSponsored(true);
                criteria.setStudyProtocolType(InterventionalStudyProtocol.class.getSimpleName());
                criteria.setDocumentWorkflowStatusCodes(getResultsDashboadStatusCodeFilter());
                List<StudyProtocolQueryDTO> currentResults = protocolQueryService
                        .getStudyProtocolByCriteria(criteria,
                                SKIP_ALTERNATE_TITLES, SKIP_LAST_UPDATER_INFO,
                                SKIP_OTHER_IDENTIFIERS);
                
                results.addAll(currentResults);
            }
            loadResultsChartData(results);
            
        } catch (PAException e) {
            LOG.error(e, e);
            request.setAttribute(Constants.FAILURE_MESSAGE,
                    e.getLocalizedMessage());
        }
        return RESULT_ABSTRACTOR_LANDING;
    }


    private List<String> getResultsDashboadStatusCodeFilter() {
        List<String> list = new ArrayList<String>();
        list.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode());        
        list.add(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode());
        list.add(DocumentWorkflowStatusCode.VERIFICATION_PENDING.getCode());
        return list;
    }
     
    private StudyProtocolQueryCriteria buildCriteria() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        List<Boolean> section801Indicators = new ArrayList<Boolean>();
        if (section801IndicatorYes != null && section801IndicatorYes) {
            section801Indicators.add(true);
        }
        
        if (section801IndicatorNo != null && section801IndicatorNo) {
            section801Indicators.add(false);
        }
        criteria.setSection801Indicators(section801Indicators);
        criteria.setPcdFrom(pcdFrom);
        criteria.setPcdTo(pcdTo);
        criteria.setPcdFromType(pcdFromType);
        criteria.setPcdToType(pcdToType);
        return criteria;
    }
    
    /**
     * Load counts of trials in various results reporting stages, the result  reporting stage status is determined
     * as follows 
     * 
     * Completed: protocol.trialPublishedDate != null
     * In Process: protocol.reportingInProcessDate != null && protocol.trialPublishedDate == null
     * Not Started: protocol.reportingInProcessDate == null
     * Issues: unique ( protocol.trialPublishedDate == null && 
     *       count (protocol.studyProcessingError.resolution date == null) > 0)
     * 
     */
    private void loadResultsChartData(List<StudyProtocolQueryDTO> studyProtocols) {
        for (Iterator iterator = studyProtocols.iterator(); iterator.hasNext();) {
            StudyProtocolQueryDTO studyProtocolQueryDTO = (StudyProtocolQueryDTO) iterator
                    .next();
            if (studyProtocolQueryDTO.getTrialPublishedDate() != null) {
                completedCnt++;
            } else if (studyProtocolQueryDTO.getReportingInProcessDate() != null) {
                inProcessCnt++;
            } else {
                notStartedCnt++;
            }
            
            if (studyProtocolQueryDTO.getTrialPublishedDate() == null) {
                List<StudyProcessingErrorDTO> errors  = speService.getStudyProcessingErrorByStudy(studyProtocolQueryDTO
                                    .getStudyProtocolId());
                for (Iterator iterator2 = errors.iterator(); iterator2
                        .hasNext();) {
                    StudyProcessingErrorDTO studyProcessingErrorDTO = (StudyProcessingErrorDTO) iterator2
                            .next();
                    if (studyProcessingErrorDTO.getResolutionDate() == null) {
                        issuesCnt++;
                        break;
                    }
                }
            }
        }
    }

    
    /**
     * @return the protocolQueryService
     */
    ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }


    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }


    /**
     * @return the studyProtocolService
     */
    StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }


    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }


    /**
     * @return the section801IndicatorYes
     */
    public Boolean isSection801IndicatorYes() {
        return section801IndicatorYes;
    }


    /**
     * @param section801IndicatorYes the section801IndicatorYes to set
     */
    public void setSection801IndicatorYes(Boolean section801IndicatorYes) {
        this.section801IndicatorYes = section801IndicatorYes;
    }


    /**
     * @return the section801IndicatorNo
     */
    public Boolean getSection801IndicatorNo() {
        return section801IndicatorNo;
    }


    /**
     * @param section801IndicatorNo the section801IndicatorNo to set
     */
    public void setSection801IndicatorNo(Boolean section801IndicatorNo) {
        this.section801IndicatorNo = section801IndicatorNo;
    }


    /**
     * @return the pcdFrom
     */
    public Date getPcdFrom() {
        return pcdFrom;
    }


    /**
     * @param pcdFrom the pcdFrom to set
     */
    public void setPcdFrom(Date pcdFrom) {
        this.pcdFrom = pcdFrom;
    }


    /**
     * @return the pcdTo
     */
    public Date getPcdTo() {
        return pcdTo;
    }


    /**
     * @param pcdTo the pcdTo to set
     */
    public void setPcdTo(Date pcdTo) {
        this.pcdTo = pcdTo;
    }


    /**
     * @return the pcdFromType
     */
    public String getPcdFromType() {
        return pcdFromType;
    }


    /**
     * @param pcdFromType the pcdFromType to set
     */
    public void setPcdFromType(String pcdFromType) {
        this.pcdFromType = pcdFromType;
    }


    /**
     * @return the pcdToType
     */
    public String getPcdToType() {
        return pcdToType;
    }


    /**
     * @param pcdToType the pcdToType to set
     */
    public void setPcdToType(String pcdToType) {
        this.pcdToType = pcdToType;
    }


    /**
     * @return the results
     */
    public List<StudyProtocolQueryDTO> getResults() {
        return results;
    }


    /**
     * @return the studyId
     */
    public Long getStudyId() {
        return studyId;
    }


    /**
     * @param studyId the studyId to set
     */
    public void setStudyId(Long studyId) {
        this.studyId = studyId;
    }


    /**
     * @return the dateAttr
     */
    public String getDateAttr() {
        return dateAttr;
    }


    /**
     * @param dateAttr the dateAttr to set
     */
    public void setDateAttr(String dateAttr) {
        this.dateAttr = dateAttr;
    }


    /**
     * @return the dateValue
     */
    public Date getDateValue() {
        return dateValue;
    }


    /**
     * @param dateValue the dateValue to set
     */
    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }


    /**
     * @return the ajaxResponseStream
     */
    public InputStream getAjaxResponseStream() {
        return ajaxResponseStream;
    }


    @Override
    public String view() throws PAException {
        return null;
    }


    /**
     * @return the inProcessCnt
     */
    public int getInProcessCnt() {
        return inProcessCnt;
    }


    /**
     * @param inProcessCnt the inProcessCnt to set
     */
    public void setInProcessCnt(int inProcessCnt) {
        this.inProcessCnt = inProcessCnt;
    }


    /**
     * @return the completedCnt
     */
    public int getCompletedCnt() {
        return completedCnt;
    }


    /**
     * @param completedCnt the completedCnt to set
     */
    public void setCompletedCnt(int completedCnt) {
        this.completedCnt = completedCnt;
    }


    /**
     * @return the notStartedCnt
     */
    public int getNotStartedCnt() {
        return notStartedCnt;
    }


    /**
     * @param notStartedCnt the notStartedCnt to set
     */
    public void setNotStartedCnt(int notStartedCnt) {
        this.notStartedCnt = notStartedCnt;
    }


    /**
     * @return the issuesCnt
     */
    public int getIssuesCnt() {
        return issuesCnt;
    }


    /**
     * @param issuesCnt the issuesCnt to set
     */
    public void setIssuesCnt(int issuesCnt) {
        this.issuesCnt = issuesCnt;
    }


    /**
     * @return the studyNCIId
     */
    public String getStudyNCIId() {
        return studyNCIId;
    }


    /**
     * @param studyNCIId the studyNCIId to set
     */
    public void setStudyNCIId(String studyNCIId) {
        this.studyNCIId = studyNCIId;
    }


}
