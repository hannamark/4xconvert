/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.action;

import static gov.nih.nci.pa.util.Constants.IS_SU_ABSTRACTOR;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.interceptor.PreventTrialEditingInterceptor;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyCheckoutServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 *
 * @author Harsha
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods",
        "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class StudyProtocolQueryAction extends ActionSupport implements Preparable, ServletResponseAware, 
                                                            ServletRequestAware {
    private static final long serialVersionUID = -2308994602660261367L;
    private static final String SHOW_VIEW = "view";
    private static final String SHOW_VIEW_REFRESH = "viewRefresh";
    private static final String BARE = "/bare/";
    
    private ProtocolQueryServiceLocal protocolQueryService;
    private StudyCheckoutServiceLocal studyCheckoutService;
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService;
    private StudyProtocolService studyProtocolService;
    
    private List<StudyProtocolQueryDTO> records;
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private Long studyProtocolId;
    private HttpServletResponse servletResponse;
    private HttpServletRequest httpServletRequest;
    private List<String> checkoutCommands;
    private String checkInReason;
    private final PAServiceUtils paServiceUtils = new PAServiceUtils();
    private String identifier;
    private CorrelationUtilsRemote correlationUtils = new CorrelationUtils();
    
    private Long assignedTo;
    private String newProcessingPriority;
    private String processingComments;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        studyCheckoutService = PaRegistry.getStudyCheckoutService();
        tsrReportGeneratorService = PaRegistry.getTSRReportGeneratorService();
        studyProtocolService = PaRegistry.getStudyProtocolService();

        if (httpServletRequest.getServletPath().contains(BARE)) {
            // we are in BARE mode, which is typically used just to look up and
            // pick a trial in a pop-up
            // window.
            httpServletRequest.setAttribute("isBare", true);
        }
    }

    /**
     * @return res
     * @throws PAException exception
     */
    @Override
    public String execute() throws PAException {
        if (!ActionUtils.isUserRoleInSession(ServletActionContext.getRequest()
                .getSession())) {
            return showCriteria();
        }
        return SUCCESS;
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String showCriteria() throws PAException {
        final HttpServletRequest request = ServletActionContext.getRequest();
        ActionUtils.setUserRolesInSession(request);
        if (ActionUtils.isUserRoleInSession(request.getSession())) {
            return "criteriaProtected";
        } 
        throw new PAException("User configured improperly.  Use UPT to assign user to a valid group "
                + "for this application.");
    }

    /**
     * @return res
     * @throws PAException exception
     */
    @SuppressWarnings("PMD")
    public String query() throws PAException {
        if (!ActionUtils.isUserRoleInSession(ServletActionContext.getRequest()
                .getSession())) {
            return showCriteria();
        }
        validateIdentifierSearchParameters();
        if (hasFieldErrors()) {
            return ERROR;
        }

        try {
            populateIdentifierSearchParameters();
            // The way Search Trials screen works today is that POST means a
            // user is executing a new search,
            // while GET means the user is paginating through results. So for
            // POST we always hit the back-end,
            // while for GET we also look in cache for previously retrieved
            // query results.
            // Based on Search Trials usage pattern, if more than 10 results are
            // retrieved by initial search,
            // the user is likely to go through pages. It makes sense to cache
            // the search results just for a little
            // while and avoid hitting the database on each page change.
            // We are not using HttpSession as cache, because it is long-lived,
            // is specific to each user, and does not
            // handle multiple browser tabs very well. Using HttpSession would
            // increase risk of significant memory
            // consumption, a memory that we don't really have.
            // We are using an EhCache instance instead, which is strictly
            // limited by a max. number of elements in memory
            // and TTL. Enough to improve pagination performance.
            if (!"GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
                CacheUtils.removeItemFromCache(
                        CacheUtils.getSearchResultsCache(),
                        criteria.getUniqueCriteriaKey());
            }     
            records = protocolQueryService.getStudyProtocolByCriteria(criteria);
            ActionUtils.sortTrialsByNciId(records);
            return SUCCESS;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return SUCCESS;
        }
    }

    /**
     * Validates the identifier portion of the search.
     */
    private void validateIdentifierSearchParameters() {
        final String identifierType = criteria.getIdentifierType();
        if (StringUtils.isNotEmpty(identifierType)
                && !StudyProtocolQueryCriteria.ALL.equals(identifierType)
                && StringUtils.isEmpty(getIdentifier())) {
            addFieldError("identifier",
                    getText("error.studyProtocol.identifier"));
        }
        if (StringUtils.isNotEmpty(getIdentifier())
                && StringUtils.isEmpty(identifierType)) {
            addFieldError("criteria.identifierType",
                    getText("error.studyProtocol.identifierType"));
        }
    }

    /**
     * Populates the identifier search parameters.
     */
    private void populateIdentifierSearchParameters() {
        criteria.setUserLastCreated(UsernameHolder.getUser());
        if (StringUtils.isNotEmpty(criteria.getIdentifierType()) && StringUtils.isNotEmpty(getIdentifier())) {
            criteria.setIdentifier(getIdentifier());
        }
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String view() throws PAException { // NOPMD
        if (!ActionUtils.isUserRoleInSession(ServletActionContext.getRequest()
                .getSession())) {
            return showCriteria();
        }
        try {
            HttpSession session = ServletActionContext.getRequest().getSession();
            StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(studyProtocolQueryDTO.getOfficialTitle());
            if (studyProtocolQueryDTO.getPiId() != null) {
                Person piPersonInfo =
                        correlationUtils.getPAPersonByIi(IiConverter.convertToPaPersonIi(studyProtocolQueryDTO
                                .getPiId()));
                session.setAttribute(Constants.PI_PO_ID, piPersonInfo.getIdentifier());
            }
            
            session.setAttribute(Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            session.setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToStudyProtocolIi(studyProtocolId));
            // When the study protocol is selected, set its token to be the current time in milliseconds.
            session.setAttribute(PreventTrialEditingInterceptor.STUDY_PROTOCOL_TOKEN, PreventTrialEditingInterceptor
                .generateToken());
            String loginName = UsernameHolder.getUser();
            session.setAttribute(Constants.LOGGED_USER_NAME, loginName);
            setCheckoutCommands(studyProtocolQueryDTO);
            session.setAttribute("nctIdentifier", paServiceUtils.getStudyIdentifier(IiConverter
                .convertToStudyProtocolIi(studyProtocolId), PAConstants.NCT_IDENTIFIER_TYPE));
            if (!studyProtocolQueryDTO.isProprietaryTrial()) {
                session.setAttribute("dcpIdentifier", paServiceUtils.getStudyIdentifier(IiConverter
                    .convertToStudyProtocolIi(studyProtocolId), PAConstants.DCP_IDENTIFIER_TYPE));
                session.setAttribute("ctepIdentifier", paServiceUtils.getStudyIdentifier(IiConverter
                    .convertToStudyProtocolIi(studyProtocolId), PAConstants.CTEP_IDENTIFIER_TYPE));
            }
            String user = studyProtocolQueryDTO.getLastCreated().getUserLastCreated();
            String trialSubmitterOrg = "";
            RegistryUser userInfo = PaRegistry.getRegistryUserService().getUser(user);
            if (userInfo.getAffiliatedOrganizationId() != null) {
                PAServiceUtils servUtil = new PAServiceUtils();
                trialSubmitterOrg = servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(String
                        .valueOf(userInfo.getAffiliatedOrganizationId())));
                session.setAttribute(Constants.TRIAL_SUBMITTER_ORG_PO_ID, userInfo.getAffiliatedOrganizationId());
            } else {
                trialSubmitterOrg = userInfo.getAffiliateOrg();
            }
            session.setAttribute(Constants.TRIAL_SUBMITTER_ORG, trialSubmitterOrg);
            return SHOW_VIEW;
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return SHOW_VIEW;
        }
    }
    
    

    private void setCheckoutCommands(StudyProtocolQueryDTO studyProtocolQueryDTO) {
        checkoutCommands = new ArrayList<String>();
        ActionUtils.setCheckoutCommands(studyProtocolQueryDTO, checkoutCommands);
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String viewTSR() throws PAException {
        if (!ActionUtils.isUserRoleInSession(ServletActionContext.getRequest()
                .getSession())) {
            return showCriteria();
        }
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            ByteArrayOutputStream reportData =
                    tsrReportGeneratorService.generateRtfTsrReport(IiConverter.convertToIi(pId));
            servletResponse.setHeader("Content-disposition", "inline; filename=TsrReport.rtf");
            servletResponse.setContentType("application/rtf;");
            servletResponse.setContentLength(reportData.size());
            ServletOutputStream servletout = servletResponse.getOutputStream();
            reportData.writeTo(servletout);
            servletout.flush();
        } catch (Exception e) {
            LOG.error("Error while generating TSR Summary report ", e);
            return NONE;
        }
        return NONE;
    }

    /**
     * Administrative check-out.
     * @return The result name
     * @throws PAException exception
     */
    public String adminCheckOut() throws PAException {
        return checkOut(CheckOutType.ADMINISTRATIVE);
    }
    
    /**
     * Forced administrative and scientific check-out for super abstractors.
     * @return The result name
     * @throws PAException exception
     */
    public String adminAndScientificCheckOut() throws PAException {
        HttpSession session = ServletActionContext.getRequest().getSession();
        boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        if (!suAbs) {
            throw new PAException(
                    "Admin & Scientific forced check-out is only available to super abstractors.");
        }
        // forcibly check in, in case a different user has this trial checked
        // out.
        adminCheckIn();
        scientificCheckIn();
        // now check out.
        adminCheckOut();
        scientificCheckOut();
        return SHOW_VIEW_REFRESH;
    }
    
    /**
     * Forced administrative and scientific check-in for super abstractors.
     * @return The result name
     * @throws PAException exception
     */
    public String adminAndScientificCheckIn() throws PAException {
    HttpSession session = ServletActionContext.getRequest().getSession();
    boolean suAbs = BooleanUtils.toBoolean((Boolean) session
                .getAttribute(Constants.IS_SU_ABSTRACTOR));
        if (!suAbs) {
            throw new PAException(
                    "Admin & Scientific forced check-in is only available to super abstractors.");
        }
        // forcibly check out, in case a different user has this trial checked in
        adminCheckOut();
        scientificCheckOut();
        // now check in.
        adminCheckIn();
        scientificCheckIn();
    return SHOW_VIEW_REFRESH; 
    }
     
    
    /**
     * Scientific check-out.
     * @return The result name
     * @throws PAException exception
     */
    public String scientificCheckOut() throws PAException {
        return checkOut(CheckOutType.SCIENTIFIC);
    }
    
    private String checkOut(CheckOutType checkOutType) throws PAException {
        try {
            studyCheckoutService.checkOut(IiConverter.convertToStudyProtocolIi(studyProtocolId),
                    CdConverter.convertToCd(checkOutType),
                    StConverter.convertToSt(UsernameHolder.getUser()));
                String msg = getText("studyProtocol.trial.checkOut." + checkOutType.name());
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, msg);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }
    
    /**
     * Administrative check-in.
     * @return The result name
     * @throws PAException exception
     */
    public String adminCheckIn() throws PAException {
        return checkIn(CheckOutType.ADMINISTRATIVE);
    }
    
    /**
     * Scientific check-in.
     * @return The result name
     * @throws PAException exception
     */
    public String scientificCheckIn() throws PAException {
        return checkIn(CheckOutType.SCIENTIFIC);
    }

    private String checkIn(CheckOutType checkOutType) throws PAException {
        try {
            studyCheckoutService.checkIn(IiConverter.convertToStudyProtocolIi(studyProtocolId),
                    CdConverter.convertToCd(checkOutType),
                    StConverter.convertToSt(UsernameHolder.getUser()),
                    StConverter.convertToSt(getCheckInReason()));
            String msg = getText("studyProtocol.trial.checkIn." + checkOutType.name());
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, msg);
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }
    
    /**
     * @return String
     * @throws PAException PAException
     */
    @SuppressWarnings("deprecation")
    public String save() throws PAException {
        try {
            StudyProtocolDTO studyDTO = studyProtocolService.getStudyProtocol(IiConverter.convertToIi(studyProtocolId));
            final Ii assignedUser = studyDTO.getAssignedUser();
            final Ii newAssignedUser = IiConverter.convertToIi(assignedTo);
            
            studyDTO.setComments(StConverter.convertToSt(processingComments));
            if (isInRole(IS_SU_ABSTRACTOR)) {
                studyDTO.setProcessingPriority(IntConverter.convertToInt(newProcessingPriority));            
                studyDTO.setAssignedUser(newAssignedUser);
            }
            studyProtocolService.updateStudyProtocol(studyDTO);
            
            // if assigned user's changed, check out the trial in that name.
            if (isInRole(IS_SU_ABSTRACTOR) && ((ISOUtil.isIiNull(assignedUser) && !ISOUtil
                    .isIiNull(newAssignedUser))
                    || (!ISOUtil.isIiNull(assignedUser) && ISOUtil
                            .isIiNull(newAssignedUser))
                    || (!ISOUtil.isIiNull(assignedUser)
                            && !ISOUtil.isIiNull(newAssignedUser) && !assignedUser
                            .getExtension().equals(
                                    newAssignedUser.getExtension())))) {
                studyCheckoutService.handleTrialAssigneeChange(studyProtocolId);
            }
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                    getText("dashboard.save.success"));            
            return view();
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
        }
        return SHOW_VIEW_REFRESH;
    }
    
    private boolean isInRole(String roleFlag) {
        return Boolean.TRUE.equals(ServletActionContext.getRequest()
                .getSession().getAttribute(roleFlag));
    }

    /**
     * 
     * @return records
     */
    public List<StudyProtocolQueryDTO> getRecords() {
        return records;
    }

    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }

    /**
     * 
     * @param criteria StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param response servletResponse
     */
    @Override
    public void setServletResponse(HttpServletResponse response) {
        servletResponse = response;
    }

    /**
     * @return the checkoutCommands
     */
    public List<String> getCheckoutCommands() {
        return checkoutCommands;
    }

    /**
     * @param checkoutCommands the checkoutCommands to set
     */
    public void setCheckoutCommands(List<String> checkoutCommands) {
        this.checkoutCommands = checkoutCommands;
    }

    /**
     * @return the checkInReason
     */
    public String getCheckInReason() {
        return checkInReason;
    }

    /**
     * @param checkInReason the checkInReason to set
     */
    public void setCheckInReason(String checkInReason) {
        this.checkInReason = checkInReason;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @param studyCheckoutService the studyCheckoutService to set
     */
    public void setStudyCheckoutService(StudyCheckoutServiceLocal studyCheckoutService) {
        this.studyCheckoutService = studyCheckoutService;
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(TSRReportGeneratorServiceRemote tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        httpServletRequest = request;
    }

    /**
     * @return the correlationUtils
     */
    public CorrelationUtilsRemote getCorrelationUtils() {
        return correlationUtils;
    }

    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

    /**
     * @return the assignedTo
     */
    public Long getAssignedTo() {
        return assignedTo;
    }

    /**
     * @param assignedTo the assignedTo to set
     */
    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * @return the newProcessingPriority
     */
    public String getNewProcessingPriority() {
        return newProcessingPriority;
    }

    /**
     * @param newProcessingPriority the newProcessingPriority to set
     */
    public void setNewProcessingPriority(String newProcessingPriority) {
        this.newProcessingPriority = newProcessingPriority;
    }

    /**
     * @return the processingComments
     */
    public String getProcessingComments() {
        return processingComments;
    }

    /**
     * @param processingComments the processingComments to set
     */
    public void setProcessingComments(String processingComments) {
        this.processingComments = processingComments;
    }

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolService getStudyProtocolService() {
        return studyProtocolService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolService studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }
}
