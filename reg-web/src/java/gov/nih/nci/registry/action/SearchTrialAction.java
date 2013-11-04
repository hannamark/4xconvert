/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The reg-web
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This reg-web Software License (the License) is between NCI and You. You (or
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
 * its rights in the reg-web Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the reg-web Software; (ii) distribute and
 * have distributed to and by third parties the reg-web Software and any
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolStageServiceLocal;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovStudyAdapter;
import gov.nih.nci.pa.service.util.CTGovSyncServiceLocal;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.CacheUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.dto.BaseTrialDTO;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.RegistryUserWebDTO;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.util.ComparableOrganizationDTO;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 *
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.TooManyMethods" })
public class SearchTrialAction extends ActionSupport implements Preparable, ServletRequestAware {
   
    private static final String FAILURE_MESSAGE = "failureMessage";

    private static final String CTGOVIMPORT = "ctgovimport";

    private static final Logger LOG = Logger
            .getLogger(SearchTrialAction.class);    
    
    private static final long serialVersionUID = 1L;
    private static final Set<DocumentWorkflowStatusCode> ABSTRACTED_CODES =
            EnumSet.of(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE,
                       DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
    private static final Set<StudyStatusCode> UPDATEABLE_STATUS = new HashSet<StudyStatusCode>();
    private static final Set<String> EXECUTE_ACTIONS = new HashSet<String>();
    static {
        UPDATEABLE_STATUS.add(StudyStatusCode.WITHDRAWN);
        UPDATEABLE_STATUS.add(StudyStatusCode.COMPLETE);
        UPDATEABLE_STATUS.add(StudyStatusCode.ADMINISTRATIVELY_COMPLETE);

        EXECUTE_ACTIONS.add("SUBMIT");
        EXECUTE_ACTIONS.add("AMEND");
        EXECUTE_ACTIONS.add("UPDATE");
    }

    private AbstractionCompletionServiceRemote abstractionCompletionService;
    private DocumentServiceLocal documentService;
    private MailManagerServiceLocal mailManagerService;    
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;
    private RegulatoryInformationServiceRemote regulatoryInformationService;
    private StudyProtocolServiceLocal studyProtocolService;
    private StudyProtocolStageServiceLocal studyProtocolStageService;
    private CTGovSyncServiceLocal ctGovSyncService;
    
    private List<StudyProtocolQueryDTO> records;
    private SearchProtocolCriteria criteria = new SearchProtocolCriteria();
    private Long studyProtocolId;
    private String trialAction;
    private Long identifier;
    private TrialUtil trialUtils = new TrialUtil();
    private HttpServletRequest httpServletRequest;
    private CTGovStudyAdapter study;
    private String nctIdToImport;
    private boolean searchPerformed;

    private String currentUser;
    private boolean showVerifyButton = false;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        currentUser = UsernameHolder.getUser();
        abstractionCompletionService = PaRegistry.getAbstractionCompletionService();
        documentService = PaRegistry.getDocumentService();
        mailManagerService = PaRegistry.getMailManagerService();        
        protocolQueryService = PaRegistry.getCachingProtocolQueryService();
        registryUserService = PaRegistry.getRegistryUserService();
        regulatoryInformationService = PaRegistry.getRegulatoryInformationService();
        studyProtocolService = PaRegistry.getStudyProtocolService();
        studyProtocolStageService = PaRegistry.getStudyProtocolStageService();
        ctGovSyncService = PaRegistry.getCTGovSyncService();
    }
    
    /**
     * @return res
     */
    @Override
    public String execute() {
        boolean isSiteAdmin = ServletActionContext.getRequest().isUserInRole("SiteAdmin");
        ServletActionContext.getRequest().getSession().setAttribute("isSiteAdmin", isSiteAdmin);
        if (EXECUTE_ACTIONS.contains(StringUtils.upperCase(trialAction))) {
            String pId = (String) ServletActionContext.getRequest().getSession().getAttribute("protocolId");
            studyProtocolId = Long.valueOf(pId);
            getCriteria().setMyTrialsOnly(true);
            return view();
        }
        return SUCCESS;
    }

    /**
     * @return res
     */
    public String showCriteria() {
        return "criteria";
    }

    /**
     * @return res
     */
    public String query() {
        try {
            // validate the form elements
            validateForm();
            if (hasFieldErrors()) {
                return ERROR;
            }
            final StudyProtocolQueryCriteria spQueryCriteria = convertToStudyProtocolQueryCriteria();
            prepareSearchResults(spQueryCriteria);
            
            // If user is trying to search by NCT ID and getting no matches in
            // CTRP, we need to try and find
            // the trial in Ct.Gov.
            if (CollectionUtils.isEmpty(records)
                    && criteria.isNctIdentifierProvided()) {
                try {
                    study = ctGovSyncService
                            .getAdaptedCtGovStudyByNctId(criteria
                                    .getIdentifier());
                    if (study != null) {
                        // Before we inform the user about the match we found in
                        // ct.gov, we need to make sure this trial
                        // does not exist in CTRP.
                        if (studyProtocolService.getStudyProtocolsByNctId(// NOPMD
                                criteria.getIdentifier()).isEmpty()) {
                            searchPerformed = true;
                            return CTGOVIMPORT;
                        }
                    }
                } catch (Exception e) {
                    LOG.error(ExceptionUtils.getFullStackTrace(e));
                }
            }
            return SUCCESS;
        } catch (Exception e) {            
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            addActionError(e.getLocalizedMessage());            
            return ERROR;
        }
    }
    
    
    /**
     * @return string
     */
    public String importTrial() {
        if (hasActionErrors()) {
            return ERROR;
        }
        final String nctID = getNctIdToImport();
        try {
            
            // Fail the import as per requirements if there is an exact match by
            // title and category
            study = ctGovSyncService.getAdaptedCtGovStudyByNctId(nctID);
            final List<StudyProtocolQueryDTO> potentialMatches = findExistentStudies(study);
            if (!potentialMatches.isEmpty()) {
                studyProtocolId = potentialMatches.get(0).getStudyProtocolId();
                ServletActionContext.getRequest().setAttribute(
                        FAILURE_MESSAGE,
                        getText("importctgov.import.new.failure.dupe"));
                return view();
            }
            
            // Proceed with import otherwise.
            String nciID = ctGovSyncService.importTrial(nctID);            
            final Long newTrialId = IiConverter
                    .convertToLong(studyProtocolService.getStudyProtocol(
                            IiConverter.convertToAssignedIdentifierIi(nciID))
                            .getIdentifier());            
            studyProtocolId = newTrialId;
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE,
                    getText("importctgov.import.new.success", new String[] {
                            nctID, nciID }));
            return view();
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(FAILURE_MESSAGE,
                    e.getLocalizedMessage());
            LOG.error(e, e);
        }
        criteria.setIdentifierType("NCT");
        criteria.setIdentifier(nctID);
        return ERROR;
    }
    
    private List<StudyProtocolQueryDTO> findExistentStudies(
            CTGovStudyAdapter ctgovStudy) throws PAException {
        List<StudyProtocolQueryDTO> list = new ArrayList<StudyProtocolQueryDTO>();
        if (ctgovStudy != null && StringUtils.isNotBlank(ctgovStudy.getTitle())) {
            StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
            queryCriteria.setOfficialTitle(ctgovStudy.getTitle());
            queryCriteria.setTrialCategory("p");
            queryCriteria.setExcludeRejectProtocol(true);
            for (StudyProtocolQueryDTO dto : protocolQueryService
                    .getStudyProtocolByCriteria(queryCriteria)) {
                if (ctgovStudy.getTitle().equalsIgnoreCase(
                        dto.getOfficialTitle())) {
                    list.add(dto);
                }
            }
        }
        return list;
    }

    /**
     * @param spQueryCriteria
     * @throws PAException
     */
    private void prepareSearchResults(
            final StudyProtocolQueryCriteria spQueryCriteria)
            throws PAException {
        // The way Search Trials screen works today is that POST means a user is executing a new search,
        // while GET means the user is paginating through results. So for POST we always hit the back-end,
        // while for GET we also look in cache for previously retrieved query results.
        // Based on Search Trials usage pattern, if more than 10 results are retrieved by initial search,
        // the user is likely to go through pages. It makes sense to cache the search results just for a little
        // while and avoid hitting the database on each page change.
        // We are not using HttpSession as cache, because it is long-lived, is specific to each user, and does not
        // handle multiple browser tabs very well. Using HttpSession would increase risk of significant memory 
        // consumption, a memory that we don't really have.
        // We are using an EhCache instance instead, which is strictly limited by a max. number of elements in memory
        // and TTL. Enough to improve pagination performance.
        if (!"GET".equalsIgnoreCase(httpServletRequest.getMethod())) {
            CacheUtils.removeItemFromCache(CacheUtils.getSearchResultsCache(), spQueryCriteria.getUniqueCriteriaKey());
        }        
        records = protocolQueryService
                .getStudyProtocolByCriteria(spQueryCriteria);
        if (CollectionUtils.isNotEmpty(records)) {
            Collections.sort(records, new Comparator<StudyProtocolQueryDTO>() {
                public int compare(StudyProtocolQueryDTO o1, StudyProtocolQueryDTO o2) {
                    return StringUtils.defaultString(o2.getNciIdentifier()).compareTo(
                            StringUtils.defaultString(o1.getNciIdentifier()));
                }
            });              
        }
        checkToShow();
        checkVerifyData();
    }
    
    private void checkToShow() {
        for (StudyProtocolQueryDTO queryDto : records) {
            DocumentWorkflowStatusCode dwfs = queryDto.getDocumentWorkflowStatusCode();
            if (!queryDto.isProprietaryTrial() && ABSTRACTED_CODES.contains(dwfs)
                    && queryDto.getCtgovXmlRequiredIndicator() && queryDto.isSearcherTrialOwner()) {
                queryDto.setShowSendXml(true);
            }
            queryDto.setUpdate("");
            queryDto.setStatusChangeLinkText("");
            checkUpdatable(queryDto, dwfs);
        }
    }
   
//    private void checkVerifyData() {
//        for (StudyProtocolQueryDTO queryDto : records) {
//            DocumentWorkflowStatusCode dwfs = queryDto.getDocumentWorkflowStatusCode();
//            if (ABSTRACTED_CODES.contains(dwfs) && queryDto.isSearcherTrialOwner() 
//                    || queryDto.getLastCreated().getUserLastCreated().equals(currentUser)) {
//                queryDto.setVerifyData(true);
//            }  
//        }
//    }
    private void checkUpdatable(StudyProtocolQueryDTO queryDto, DocumentWorkflowStatusCode dwfs) {
        if (isUpdateableNonProperietaryTrial(queryDto, dwfs, queryDto.getStudyStatusCode())
                || isUpdateableProprietaryTrial(queryDto, dwfs)) {
            queryDto.setUpdate(getText("search.trial.update"));
            queryDto.setStatusChangeLinkText(getText("search.trial.statusChange"));
        }
    }

    private boolean isUpdateableProprietaryTrial(StudyProtocolQueryDTO queryDto, DocumentWorkflowStatusCode dwfs) {
        return dwfs != null && queryDto.isProprietaryTrial()
                && dwfs.isAcceptedOrAbove() && queryDto.isSearcherTrialOwner();
    }

    private boolean isUpdateableNonProperietaryTrial(StudyProtocolQueryDTO queryDto, DocumentWorkflowStatusCode dwfs,
            StudyStatusCode statusCode) {
        return dwfs != null && statusCode != null && dwfs.isAcceptedOrAbove()
                && queryDto.isSearcherTrialOwner() && !UPDATEABLE_STATUS.contains(statusCode);
    }

    /**
     * @return StudyProtocolQueryCriteria
     * @throws PAException
     */
    StudyProtocolQueryCriteria convertToStudyProtocolQueryCriteria() throws PAException {

        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setOfficialTitle(criteria.getOfficialTitle());
        queryCriteria.setPhaseCode(criteria.getPhaseCode());
        queryCriteria.setPrimaryPurposeCode(criteria.getPrimaryPurposeCode());
        queryCriteria.setPhaseAdditionalQualifierCode(criteria.getPhaseAdditionalQualifierCode());
        if (StringUtils.isNotEmpty(criteria.getIdentifierType()) && StringUtils.isNotEmpty(criteria.getIdentifier())) {
            convertIdentifierType(queryCriteria);
        }
        if (StringUtils.isNotEmpty(criteria.getOrganizationId())) {
            queryCriteria.getLeadOrganizationIds().add(Long.valueOf(criteria.getOrganizationId()));
        }
        if (StringUtils.isNotBlank(criteria.getParticipatingSiteId())) {
            queryCriteria.getParticipatingSiteIds().add(Long.parseLong(criteria.getParticipatingSiteId()));
        }
        if (StringUtils.isNotEmpty(criteria.getLeadAndParticipatingOrgId())) {
            queryCriteria.getLeadOrganizationIds().add(Long.valueOf(criteria.getLeadAndParticipatingOrgId()));
            queryCriteria.getParticipatingSiteIds().add(Long.parseLong(criteria.getLeadAndParticipatingOrgId()));
        }        
        queryCriteria.setOrganizationType(criteria.getOrganizationType());
        queryCriteria.setMyTrialsOnly(criteria.isMyTrialsOnly());
        queryCriteria.setUserLastCreated(currentUser);
        // exclude rejected protocols during search
        queryCriteria.setExcludeRejectProtocol(Boolean.TRUE);
        if (StringUtils.isNotEmpty(criteria.getPrincipalInvestigatorId())) {
            queryCriteria.setPrincipalInvestigatorId(criteria.getPrincipalInvestigatorId());
        }
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        Long loggedInUserId = registryUserService.getUserId(loginName);
        queryCriteria.setUserId(loggedInUserId);
        
        queryCriteria.setTrialCategory(criteria.getTrialCategory());
        queryCriteria.setHoldStatus(criteria.getHoldStatus());
        return queryCriteria;
    }

    void convertIdentifierType(StudyProtocolQueryCriteria queryCriteria) {
        if (Constants.IDENTIFIER_TYPE_NCI.equals(criteria.getIdentifierType())) {
            queryCriteria.setNciIdentifier(criteria.getIdentifier());
        } else if (Constants.IDENTIFIER_TYPE_LEAD_ORG.equals(criteria.getIdentifierType())) {
            queryCriteria.setLeadOrganizationTrialIdentifier(criteria.getIdentifier());
        } else if (Constants.IDENTIFIER_TYPE_NCT.equals(criteria.getIdentifierType())) {
            queryCriteria.setNctNumber(criteria.getIdentifier());
        } else if (Constants.IDENTIFIER_TYPE_OTHER_IDENTIFIER.equals(criteria.getIdentifierType())) {
            queryCriteria.setOtherIdentifier(criteria.getIdentifier());
        } else if (Constants.IDENTIFIER_TYPE_ALL.equals(criteria.getIdentifierType())) {
            queryCriteria.setAnyTypeIdentifier(criteria.getIdentifier());
        }
    }

    private void loadPropTrial(Ii studyProtocolIi) throws PAException, NullifiedRoleException {
        ProprietaryTrialDTO trialDTO = new ProprietaryTrialDTO();
        trialUtils.getProprietaryTrialDTOFromDb(studyProtocolIi, trialDTO);        
        final HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("leadOrganizationName", trialDTO.getLeadOrganizationName());
        request.setAttribute("leadOrgTrialIdentifier", trialDTO.getLeadOrgTrialIdentifier());
        request.setAttribute("nctIdentifier", trialDTO.getNctIdentifier());
        request.setAttribute("assignedIdentifier", trialDTO.getAssignedIdentifier());
        request.setAttribute("summaryFourOrgIdentifiers", trialDTO.getSummaryFourOrgIdentifiers());
        request.setAttribute("summaryFourFundingCategoryCode",
                trialDTO.getSummaryFourFundingCategoryCode());
        request.setAttribute("participatingSitesList", trialDTO.getParticipatingSitesList());
        request.setAttribute(
                "consortiaTrialCategoryCode",
                trialDTO.getConsortiaTrialCategoryCode());
        request.setAttribute("trialIdentifier", trialDTO.getIdentifier());
    }

    private void loadNonPropTrial(Ii studyProtocolIi, boolean maskFields) throws PAException, NullifiedRoleException {
        TrialDTO trialDTO = new TrialDTO();
        trialUtils.getTrialDTOFromDb(studyProtocolIi, trialDTO);
        if (trialDTO.getTrialType().equals("InterventionalStudyProtocol")) {
            trialDTO.setTrialType("Interventional");
        } else if (trialDTO.getTrialType().equals("NonInterventionalStudyProtocol")) {
            trialDTO.setTrialType("NonInterventional");
        }
        final HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, trialDTO);       
        request.setAttribute("trialIdentifier", trialDTO.getIdentifier());
        if (!maskFields) {
            if (!trialDTO.getFundingDtos().isEmpty()) {
                request.setAttribute(Constants.TRIAL_FUNDING_LIST, trialDTO.getFundingDtos());
            }
            if (!trialDTO.getIndIdeDtos().isEmpty()) {
                request.setAttribute(Constants.STUDY_INDIDE, trialDTO.getIndIdeDtos());
            }
        }
    }

    private StudyProtocolDTO loadTrial(Ii studyProtocolIi, boolean maskFields) throws PAException,
            NullifiedRoleException {
        ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
        StudyProtocolDTO protocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (!ISOUtil.isBlNull(protocolDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(protocolDTO.getProprietaryTrialIndicator())) {
            // prop trial
            loadPropTrial(studyProtocolIi);
        } else {
            // non prop trial
            loadNonPropTrial(studyProtocolIi, maskFields);
        }
        return protocolDTO;
    }

    /**
     * @return res
     */
    @SuppressWarnings("deprecation")
    public String view() {
        try {
            // remove the session variables stored during a previous view if any
            final HttpServletRequest req = ServletActionContext.getRequest();
            req.getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            boolean maskFields = !registryUserService.hasTrialAccess(currentUser,
                    studyProtocolId);
            req.setAttribute("maskFields", maskFields);
            StudyProtocolDTO protocolDTO = loadTrial(studyProtocolIi, maskFields);
            queryTrialDocsAndSetAttributes(studyProtocolIi, protocolDTO, maskFields);
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
            checkVerifyData();
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } catch (NullifiedRoleException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    private void checkVerifyData() throws PAException {
        if (records != null) {
            for (StudyProtocolQueryDTO queryDto : records) {
                DocumentWorkflowStatusCode dwfs = queryDto.getDocumentWorkflowStatusCode();
                if ((ABSTRACTED_CODES.contains(dwfs)) && (queryDto.isSearcherTrialOwner() 
                        || queryDto.getLastCreated().getUserLastCreated() != null 
                        && queryDto.getLastCreated().getUserLastCreated().equals(currentUser))) {
                    queryDto.setVerifyData(true);
                }  
            }
        } else {
            StudyProtocolQueryDTO studyProtocolQueryDTO = new StudyProtocolQueryDTO();
            studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(studyProtocolId);
            DocumentWorkflowStatusCode dwfs = studyProtocolQueryDTO.getDocumentWorkflowStatusCode();
            Set<RegistryUser> trialOwners = registryUserService.getAllTrialOwners(studyProtocolId);
            boolean trialOwner = false;
            for (RegistryUser user : trialOwners) {
                final User csmUser = user.getCsmUser();
                if (csmUser != null && currentUser.equals(csmUser.getName())) {
                    trialOwner = true;
                    break;
                }
            }
            if ((ABSTRACTED_CODES.contains(dwfs)) && (trialOwner 
                    || studyProtocolQueryDTO.getLastCreated().getUserLastCreated().equals(currentUser))) {
                
                setShowVerifyButton(true);
            } 
        }
    }
    private void queryTrialDocsAndSetAttributes(Ii studyProtocolIi, StudyProtocolDTO protocolDTO, boolean maskFields)
            throws PAException {
        ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
        // query the trial documents
        List<DocumentDTO> documentISOList = documentService.getDocumentsAndAllTSRByStudyProtocol(studyProtocolIi);
        if (!maskFields && !documentISOList.isEmpty()) {
            ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
        }
    }

    /**
     * @return result
     */
    public String viewDoc() {
        try {
            DocumentDTO docDTO = documentService.get(IiConverter.convertToIi(identifier));

            HttpServletResponse servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/octet-stream");
            servletResponse.setContentLength(docDTO.getText().getData().length);
            servletResponse.setHeader("Cache-Control", "cache");
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition",
                    "attachment; filename=\"" + docDTO.getFileName().getValue()
                            + "\"");

            ByteArrayInputStream bis = new ByteArrayInputStream(docDTO.getText().getData());
            ServletOutputStream out = servletResponse.getOutputStream();
            IOUtils.copy(bis, out);
            out.flush();
            out.close();
        } catch (IOException err) {
            LOG.error("TrialDocumentAction failed with IOException: " + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }

    /**
     * validate the search trial form elements.
     */
    private void validateForm() {
        validateEmptyIdentifierType();
        validateEmptyIdentifier();
        validateOrganizationType();
    }

    private void validateEmptyIdentifierType() {
        if (StringUtils.isNotEmpty(criteria.getIdentifierType())
                && !Constants.IDENTIFIER_TYPE_ALL.equals(criteria
                        .getIdentifierType())
                && StringUtils.isEmpty(criteria.getIdentifier())) {
            addFieldError("criteria.identifier",
                    getText("error.search.identifier"));
        }
    }

    private void validateEmptyIdentifier() {
        if (StringUtils.isNotEmpty(criteria.getIdentifier()) && StringUtils.isEmpty(criteria.getIdentifierType())) {
            addFieldError("criteria.identifierType", getText("error.search.identifierType"));
        }
    }

    private void validateOrganizationType() {
        if (StringUtils.isNotEmpty(criteria.getOrganizationType())
                && (criteria.getOrganizationId() == null
                        && criteria.getParticipatingSiteId() == null && criteria
                        .getLeadAndParticipatingOrgId() == null)) {
            addFieldError("criteria.organizationId",
                    getText("error.search.organization"));

        }
    }

    

    /**
     * 
     * @return st
     */
    public String getMyPartiallySavedTrial() {
        StudyProtocolStageDTO criteriaSpDTO = new StudyProtocolStageDTO();
        criteriaSpDTO.setUserLastCreated(StConverter.convertToSt(currentUser));
        criteriaSpDTO.setOfficialTitle(StConverter.convertToSt(criteria.getOfficialTitle()));
        criteriaSpDTO.setPhaseCode(CdConverter.convertStringToCd(criteria.getPhaseCode()));
        criteriaSpDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(criteria.getPrimaryPurposeCode()));
        criteriaSpDTO.setPhaseAdditionalQualifierCode(CdConverter.convertStringToCd(criteria
            .getPhaseAdditionalQualifierCode()));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        try {
            List<StudyProtocolStageDTO> spStageDTOs = studyProtocolStageService.search(criteriaSpDTO, limit);
            records = convertToSpQueryDTO(spStageDTOs);
        } catch (PAException e) {
            LOG.equals(e.getMessage());
            addActionError("Exception :" + e.getMessage());
        } catch (TooManyResultsException e) {
            LOG.equals("Exception :" + e.getMessage());
        }
        ServletActionContext.getRequest().setAttribute("partialSubmission", "yes");
        return SUCCESS;
    }

    /**
     *
     * @return view
     */
    public String partiallySubmittedView() {
        if (studyProtocolId == null) {
            addActionError("study protocol id cannot null.");
            return ERROR;
        }
        try {
            BaseTrialDTO baseTrialDTO = trialUtils.getTrialDTOForPartiallySumbissionById(studyProtocolId.toString());
            if (baseTrialDTO instanceof TrialDTO) {
                setTrialOversightOrgIfAppropriate((TrialDTO) baseTrialDTO);
                setTrialOversightCountryIfAppropriate((TrialDTO) baseTrialDTO);
            }
            ServletActionContext.getRequest().setAttribute(TrialUtil.SESSION_TRIAL_ATTRIBUTE, baseTrialDTO);
            ServletActionContext.getRequest().setAttribute("partialSubmission", "search");
        } catch (PAException e) {
            addActionError(e.getMessage());
        } catch (NullifiedRoleException e) {
            addActionError(e.getMessage());
        }
        return "partialView";
    }

    private void setTrialOversightCountryIfAppropriate(TrialDTO trialDTO) throws PAException {
        if (StringUtils.isNotEmpty(trialDTO.getLst())) {
            String countryName = regulatoryInformationService.getCountryOrOrgName(
                    Long.valueOf(trialDTO.getLst()), "Country");
            trialDTO.setTrialOversgtAuthCountryName(countryName);
        }
    }

    private void setTrialOversightOrgIfAppropriate(TrialDTO trialDTO) throws PAException {
        if (StringUtils.isNotEmpty(trialDTO.getSelectedRegAuth())) {
            String orgName = regulatoryInformationService.getCountryOrOrgName(
                    Long.valueOf(trialDTO.getSelectedRegAuth()), "RegulatoryAuthority");
            trialDTO.setTrialOversgtAuthOrgName(orgName);
        }
    }

    private List<StudyProtocolQueryDTO> convertToSpQueryDTO(List<StudyProtocolStageDTO> spStageDTOs) {
        StudyProtocolQueryDTO spQueryDTO;
        List<StudyProtocolQueryDTO> returnList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolStageDTO studyProtocolStageDTO : spStageDTOs) {
            spQueryDTO = new StudyProtocolQueryDTO();
            spQueryDTO.setStudyProtocolId(IiConverter.convertToLong(studyProtocolStageDTO.getIdentifier()));
            spQueryDTO.setOfficialTitle(StConverter.convertToString(studyProtocolStageDTO.getOfficialTitle()));
            spQueryDTO.setPhaseCode(PhaseCode.getByCode(CdConverter.convertCdToString(studyProtocolStageDTO
                    .getPhaseCode())));
            spQueryDTO.setPrimaryPurpose(CdConverter.convertCdToString(studyProtocolStageDTO.getPrimaryPurposeCode()));
            spQueryDTO.setPrimaryPurposeOtherText(CdConverter.convertCdToString(studyProtocolStageDTO
                    .getPrimaryPurposeAdditionalQualifierCode()));
            spQueryDTO.setLocalStudyProtocolIdentifier(StConverter.convertToString(studyProtocolStageDTO
                    .getLocalProtocolIdentifier()));

            spQueryDTO.setLeadOrganizationId(IiConverter.convertToLong(studyProtocolStageDTO
                    .getLeadOrganizationIdentifier()));
            if (!ISOUtil.isIiNull(studyProtocolStageDTO.getLeadOrganizationIdentifier())) {
                spQueryDTO.setLeadOrganizationName(trialUtils.getOrgName(studyProtocolStageDTO
                        .getLeadOrganizationIdentifier()));
            }
            spQueryDTO.setPiId(IiConverter.convertToLong(studyProtocolStageDTO.getPiIdentifier()));
            if (!ISOUtil.isIiNull(studyProtocolStageDTO.getPiIdentifier())) {
                spQueryDTO.setPiFullName(trialUtils.getPersonName(studyProtocolStageDTO.getPiIdentifier()));
            }
            spQueryDTO.setStudyStatusCode(StudyStatusCode.getByCode(CdConverter.convertCdToString(studyProtocolStageDTO
                    .getTrialStatusCode())));
            spQueryDTO.setStudyStatusDate(TsConverter.convertToTimestamp(studyProtocolStageDTO.getTrialStatusDate()));
            spQueryDTO.getLastCreated().setUserLastCreated(
                    StConverter.convertToString(studyProtocolStageDTO.getUserLastCreated()));
            if (!ISOUtil.isBlNull(studyProtocolStageDTO.getProprietaryTrialIndicator())
                    && BlConverter.convertToBoolean(studyProtocolStageDTO.getProprietaryTrialIndicator())) {
                spQueryDTO.setProprietaryTrial(true);
            } else {
                spQueryDTO.setProprietaryTrial(false);
            }
            returnList.add(spQueryDTO);
        }
        return returnList;
    }

    /**
     * Send xml.
     * 
     * @return the string
     */
    public String sendXml() {
        Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
        String loginName = ServletActionContext.getRequest().getRemoteUser();
        RegistryUserWebDTO regUserWebDto = RegistryUtil.getRegistryUserWebDto(loginName);
        String fullName = regUserWebDto.getFirstName() + " " + regUserWebDto.getLastName();
        String emailAddress = regUserWebDto.getEmailAddress();
        try {
            List<AbstractionCompletionDTO> errorList =
                    abstractionCompletionService.validateAbstractionCompletion(studyProtocolIi);
            if (CollectionUtils.isEmpty(errorList) || !hasAnyAbstractionErrors(errorList)) {
                mailManagerService.sendXMLAndTSREmail(fullName, emailAddress, studyProtocolIi);
            } else {
                ServletActionContext.getRequest()
                    .setAttribute(FAILURE_MESSAGE, "As Abstraction is not valid, sending letter is disabled .");
            }
        } catch (PAException e) {
            addActionError("Exception while sending XML email:" + e.getMessage());
        }
        return query();
    }
    
    private boolean hasAnyAbstractionErrors(List<AbstractionCompletionDTO> errorList) {
        boolean errorExist = false;
        for (AbstractionCompletionDTO absDto : errorList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
        }
        return errorExist;
    }
    
    /**
     * A caching delegate for the
     * PAOrganizationServiceRemote#getOrganizationsAssociatedWithStudyProtocol(String)
     * . searchTrialCriteria.jsp invokes the aforementioned method each time the
     * Search page is rendered, including cases where a user is looping through
     * the search results. Even caching for a short period of time can result in
     * significant performance improvements. We will cache on a Web App level.
     * 
     * @see https://tracker.nci.nih.gov/browse/PO-4785
     * @param organizationType
     *            organizationType
     * @return List<PaOrganizationDTO>
     * @throws PAException
     *             PAException
     */  
    public List<PaOrganizationDTO> getOrganizationsAssociatedWithStudyProtocol(
            String organizationType) throws PAException {
        return PaRegistry.getCachingPAOrganizationService()
                .getOrganizationsAssociatedWithStudyProtocol(organizationType);
    }
    
    /**
     * Merged collection of lead orgs and participating sites.
     * 
     * @return Collection<PaOrganizationDTO>
     * @throws PAException
     *             PAException
     * @throws InvocationTargetException InvocationTargetException
     * @throws IllegalAccessException IllegalAccessException
     */
    public Collection<ComparableOrganizationDTO> getLeadAndParticipatingOrganizations()
            throws PAException, IllegalAccessException,
            InvocationTargetException {
        List<PaOrganizationDTO> list = new ArrayList<PaOrganizationDTO>();
        list.addAll(getOrganizationsAssociatedWithStudyProtocol("Lead Organization"));
        list.addAll(getOrganizationsAssociatedWithStudyProtocol("Participating Site"));

        Set<ComparableOrganizationDTO> set = new TreeSet<ComparableOrganizationDTO>();
        for (PaOrganizationDTO dto : list) {
            set.add(new ComparableOrganizationDTO(dto));
        }
        return set;
    }
    
    /**
     * A caching delegate for the
     * PAPersonServiceRemote#getAllPrincipalInvestigators() .
     * searchTrialCriteria.jsp invokes the aforementioned method each time the
     * Search page is rendered, including cases where a user is looping through
     * the search results. Even caching for a short period of time can result in
     * significant performance improvements. We will cache on a Web App level.
     * 
     * @see https://tracker.nci.nih.gov/browse/PO-4785
     * 
     * @return List<PaPersonDTO>
     * @throws PAException
     *             PAException
     */    
    public List<PaPersonDTO> getAllPrincipalInvestigators() throws PAException {
        return PaRegistry.getCachingPAPersonService()
                .getAllPrincipalInvestigators();
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
     * @return SearchProtocolCriteria SearchProtocolCriteria
     */
    public SearchProtocolCriteria getCriteria() {
        return criteria;
    }

    /**
     * 
     * @param criteria SearchProtocolCriteria
     */
    public void setCriteria(SearchProtocolCriteria criteria) {
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
     * @return the trialAction
     */
    public String getTrialAction() {
        return trialAction;
    }

    /**
     * @param trialAction the trialAction to set
     */
    public void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * @return the identifier
     */
    public Long getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the trialUtils
     */
    public TrialUtil getTrialUtils() {
        return trialUtils;
    }

    /**
     * @param trialUtils the trialUtils to set
     */
    public void setTrialUtils(TrialUtil trialUtils) {
        this.trialUtils = trialUtils;
    }

    /**
     * @param abstractionCompletionService the abstractionCompletionService to set
     */
    public void setAbstractionCompletionService(AbstractionCompletionServiceRemote abstractionCompletionService) {
        this.abstractionCompletionService = abstractionCompletionService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceRemote regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studyProtocolStageService the studyProtocolStageService to set
     */
    public void setStudyProtocolStageService(StudyProtocolStageServiceLocal studyProtocolStageService) {
        this.studyProtocolStageService = studyProtocolStageService;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(
     * javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.httpServletRequest = request;
    }
    /**
     * 
     * @return showVerifyButton showVerifyButton
     */
    public boolean isShowVerifyButton() {
        return showVerifyButton;
    }
    /**
     * 
     * @param showVerifyButton showVerifyButton
     */
    public void setShowVerifyButton(boolean showVerifyButton) {
        this.showVerifyButton = showVerifyButton;
    }
    

    /**
     * 
     * @return study
     */
    public CTGovStudyAdapter getStudy() {
        return study;
    }

    /**
     * @param study
     *            the CTGovStudyAdapter to set
     */
    public void setStudy(CTGovStudyAdapter study) {
        this.study = study;
    }

    /**
     * 
     * @return nctIdToImport
     */
    public String getNctIdToImport() {
        return nctIdToImport;
    }

    /**
     * @param nctIdToImport
     *            the nctIdToImport to set
     */
    public void setNctIdToImport(String nctIdToImport) {
        this.nctIdToImport = nctIdToImport;
    }

    /**
     * @param searchPerformed
     *            the searchPerformed to set
     */
    public void setSearchPerformed(boolean searchPerformed) {
        this.searchPerformed = searchPerformed;
    }

    /**
     * @return the searchPerformed
     */
    public boolean isSearchPerformed() {
        return searchPerformed;
    }

    /**
     * @param ctGovSyncService the ctGovSyncService to set
     */
    public void setCtGovSyncService(CTGovSyncServiceLocal ctGovSyncService) {
        this.ctGovSyncService = ctGovSyncService;
    }
}
