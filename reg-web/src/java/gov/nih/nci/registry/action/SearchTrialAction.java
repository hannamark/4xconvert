/***
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.registry.dto.SearchProtocolCriteria;
import gov.nih.nci.registry.util.Constants;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Bala Nair
 * 
 */
@SuppressWarnings({ "PMD" })
@Validation
public class SearchTrialAction extends ActionSupport {
    private List<StudyProtocolQueryDTO> records = null;
    private SearchProtocolCriteria criteria = new SearchProtocolCriteria();
    private Long studyProtocolId = null;
    private HttpServletResponse servletResponse;

    /**
     * @return res
     */
    public String execute() {
        //check if users accepted the desclaimer if not show one
        String strDesclaimer = (String) ServletActionContext.getRequest().getSession().getAttribute("disclaimer");
        if (strDesclaimer == null || !strDesclaimer.equals("accept")) {
            return "show_Disclaimer_Page";
        }
        String pId = (String) ServletActionContext.getRequest().getSession().getAttribute("protocolId");
        try {
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(pId);
            //TODO need to find a permanent solution
            // workaround to get the appropriate trial type
            StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
            viewCriteria.setStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
            RegistryServiceLocator.getProtocolQueryService().
                                        getStudyProtocolByCriteria(viewCriteria);
            // end of workaround
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);            
            // TrialWebDTO trialWebDTO = new TrialWebDTO(protocolDTO);
            // put an entry in the session and store
            // InterventionalStudyProtocolDTO
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
            if (protocolDTO != null) {
                String trialType = null;
              String studyProtocolType = protocolDTO.getStudyProtocolType().getValue();
              if (studyProtocolType.equals("InterventionalStudyProtocol")) {
                  trialType = "Interventional";
              } else if (studyProtocolType.equals("ObservationalStudyProtocol")) {
                  trialType = "Observational";
              }
              ServletActionContext.getRequest().setAttribute(Constants.TRIAL_TYPE, trialType);
            }
            // query the study grants
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_FUNDING_LIST, isoList);
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_OVERALL_STATUS);
           /* List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
           */ // List <StudyOverallStatusWebDTO> overallStatusList;
            StudyOverallStatusDTO overallStatusISO = RegistryServiceLocator.getStudyOverallStatusService()
            .getCurrentByStudyProtocol(studyProtocolIi);
            if (overallStatusISO != null) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_OVERALL_STATUS,
                        overallStatusISO);
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.STUDY_PARTICIPATION);
            // query the lead organization study participation site
            StudySiteDTO leadSiteDTO = getStudySite(studyProtocolIi,
                    StudySiteFunctionalCode.LEAD_ORGANIZATION);
            if (leadSiteDTO != null) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_PARTICIPATION,
                                                                    leadSiteDTO);
            }            
            // query the NCT number
            StudySiteDTO nctSiteDTO = getStudySite(studyProtocolIi,
                                            StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            if (nctSiteDTO != null) {
                String nctNumber = StConverter.convertToString(nctSiteDTO.getLocalStudyProtocolIdentifier());
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_NCT_NUMBER, nctNumber);
            } 
            // retrieve responsible party info
            getReponsibleParty(studyProtocolIi, false);
            StudyProtocolQueryDTO queryDTO = RegistryServiceLocator.getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
            // put an entry in the session and avoid conflict using
            // STUDY_PROTOCOL_II for now
            ServletActionContext.getRequest().setAttribute(Constants.STUDY_PROTOCOL_II, queryDTO);
            // put an entry in the session and getsummary4ReportedResource
            StudyResourcingDTO resourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
            // get the organzation name
            if (resourcingDTO != null && resourcingDTO.
                    getOrganizationIdentifier() != null && resourcingDTO.
                    getOrganizationIdentifier().getExtension() != null && !resourcingDTO.
                    getOrganizationIdentifier().getExtension().equals("")) {
                Organization o = new CorrelationUtils().
                    getPAOrganizationByIi(resourcingDTO.getOrganizationIdentifier());
                ServletActionContext.getRequest().setAttribute("summaryFourSponsorName", o.getName());  
            }

            // put an entry in the session and avoid conflict using
            // NIH_INSTITUTE for now
            ServletActionContext.getRequest().setAttribute(Constants.NIH_INSTITUTE, resourcingDTO);
            List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
                    .getByStudyProtocol(studyProtocolIi);
            // List<StudyIndldeDTO> studyIndldeDTOList
            if (!(studyIndldeDTOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_INDIDE, studyIndldeDTOList);
            }
            // query the trial documents
            List<DocumentDTO> documentISOList = RegistryServiceLocator.getDocumentService()
                    .getDocumentsByStudyProtocol(studyProtocolIi);
            // List <TrialDocumentWebDTO> documentList;
            if (!(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } catch (NullifiedRoleException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } finally {
            ServletActionContext.getRequest().getSession().removeAttribute(("protocolId"));            
        }
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
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = RegistryServiceLocator.getProtocolQueryService().
                              getStudyProtocolByCriteria(convertToStudyProtocolQueryCriteria());

            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage" , "Exception occured during trials search : " + e.getMessage());
            return ERROR;
        }
    }

    /**
     * @return StudyProtocolQueryCriteria
     */
    private StudyProtocolQueryCriteria convertToStudyProtocolQueryCriteria() {
        
        StudyProtocolQueryCriteria queryCriteria = new StudyProtocolQueryCriteria();
        queryCriteria.setOfficialTitle(criteria.getOfficialTitle());
        queryCriteria.setPhaseCode(criteria.getPhaseCode());
        queryCriteria.setPrimaryPurposeCode(criteria.getPrimaryPurposeCode());
        if (PAUtil.isNotEmpty(criteria.getIdentifierType())
                 && PAUtil.isNotEmpty(criteria.getIdentifier())) {            
            if (criteria.getIdentifierType().equals(Constants.IDENTIFIER_TYPE_NCI)) {
                queryCriteria.setNciIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_LEAD_ORG)) {
                queryCriteria.setLeadOrganizationTrialIdentifier(criteria.getIdentifier());
            } else if (criteria.getIdentifierType().equals(
                        Constants.IDENTIFIER_TYPE_NCT)) {
                queryCriteria.setNctNumber(criteria.getIdentifier());
            }
        }
        if (criteria.getOrganizationId() != null 
                && criteria.getOrganizationId().trim().length() > 0) {
            queryCriteria.setLeadOrganizationId(criteria.getOrganizationId().toString());
        } 
        if (criteria.getParticipatingSiteId() != null
                && criteria.getParticipatingSiteId().trim().length() > 0) {
            queryCriteria.setParticipatingSiteId(criteria.getParticipatingSiteId().toString());            
        }
        queryCriteria.setOrganizationType(criteria.getOrganizationType());
        queryCriteria.setMyTrialsOnly(new Boolean(criteria.getMyTrialsOnly()));
        queryCriteria.setUserLastCreated(ServletActionContext.getRequest().getRemoteUser());
        // exclude rejected protocols during search
        queryCriteria.setExcludeRejectProtocol(new Boolean(true));        
        return queryCriteria;
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
     * @return res
     */
    public String view() {
        boolean maskFields = false;
        try {
            // remove the session variables stored during a previous view if any
            
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            if (studyProtocolId == null) {
                String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
                studyProtocolIi = IiConverter.convertToIi(pId);
            }
            String usercreated = (String) ServletActionContext.getRequest().getParameter("usercreated");
            if (usercreated != null) {
                if (!usercreated.equals(ServletActionContext.getRequest().getRemoteUser())) {
                    maskFields = true;
                }
            }            
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
            // TODO need to find a permanent solution
            // workaround to get the appropriate trial type
            StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
            viewCriteria.setStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
            RegistryServiceLocator.getProtocolQueryService().
                                        getStudyProtocolByCriteria(viewCriteria);
            // end of workaround 
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);            
            // TrialWebDTO trialWebDTO = new TrialWebDTO(protocolDTO);
            // put an entry in the session and store
            // InterventionalStudyProtocolDTO
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
            if (protocolDTO != null) {
                String trialType = null;
              String studyProtocolType = protocolDTO.getStudyProtocolType().getValue();
              if (studyProtocolType.equals("InterventionalStudyProtocol")) {
                  trialType = "Interventional";
              } else if (studyProtocolType.equals("ObservationalStudyProtocol")) {
                  trialType = "Observational";
              }
              ServletActionContext.getRequest().setAttribute(Constants.TRIAL_TYPE, trialType);
            }
            // query the study grants
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                    .getstudyResourceByStudyProtocol(studyProtocolIi);
            if (!maskFields && !(isoList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_FUNDING_LIST, isoList);
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_OVERALL_STATUS);
           // List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.getStudyOverallStatusService()
            //        .getCurrentByStudyProtocol(studyProtocolIi);
            // List <StudyOverallStatusWebDTO> overallStatusList;
            StudyOverallStatusDTO overallStatusISO = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
            if (overallStatusISO != null) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_OVERALL_STATUS,
                        overallStatusISO);
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.STUDY_PARTICIPATION);
            // query the lead organization study participation site
            StudySiteDTO leadSiteDTO = getStudySite(studyProtocolIi,
                    StudySiteFunctionalCode.LEAD_ORGANIZATION);
            if (leadSiteDTO != null) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_PARTICIPATION,
                                                                    leadSiteDTO);
            }            
            // query the NCT number
            StudySiteDTO nctSiteDTO = getStudySite(studyProtocolIi,
                                            StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
            if (nctSiteDTO != null) {
                String nctNumber = StConverter.convertToString(nctSiteDTO.getLocalStudyProtocolIdentifier());
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_NCT_NUMBER, nctNumber);
            }            
            
            StudyProtocolQueryDTO queryDTO = RegistryServiceLocator.getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
            // put an entry in the session and avoid conflict using
            // STUDY_PROTOCOL_II for now
            ServletActionContext.getRequest().setAttribute(Constants.STUDY_PROTOCOL_II, queryDTO);
            
            // retrieve responsible party info
            getReponsibleParty(studyProtocolIi, maskFields);
            
            // put an entry in the session and getsummary4ReportedResource
            StudyResourcingDTO resourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
            
            // get the organization name
            if (resourcingDTO != null && resourcingDTO.getOrganizationIdentifier() != null 
                         && resourcingDTO.getOrganizationIdentifier().getExtension() != null) {
                Organization o = new CorrelationUtils().
                    getPAOrganizationByIi(resourcingDTO.getOrganizationIdentifier());
                ServletActionContext.getRequest().setAttribute("summaryFourSponsorName", o.getName());  
            }

           
            // put an entry in the session and avoid conflict using
            // NIH_INSTITUTE for now           
            ServletActionContext.getRequest().setAttribute(Constants.NIH_INSTITUTE, resourcingDTO);
            List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
                    .getByStudyProtocol(studyProtocolIi);
            // List<StudyIndldeDTO> studyIndldeDTOList
            if (!maskFields && !(studyIndldeDTOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_INDIDE, studyIndldeDTOList);
            }
            // query the trial documents
            List<DocumentDTO> documentISOList = RegistryServiceLocator.getDocumentService()
                    .getDocumentsByStudyProtocol(studyProtocolIi);
            // List <TrialDocumentWebDTO> documentList;
            if (!maskFields && !(documentISOList.isEmpty())) {
                ServletActionContext.getRequest().setAttribute(Constants.PROTOCOL_DOCUMENT, documentISOList);
            }
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        } catch (NullifiedRoleException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    private StudySiteDTO getStudySite(Ii studyProtocolIi , StudySiteFunctionalCode spCode)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudySiteDTO spDto = new StudySiteDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudySiteDTO> spDtos = RegistryServiceLocator.getStudySiteService()
            .getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos.get(0);
        } else if (spDtos != null && spDtos.size() > 1) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

        }
        return null;

    }

    /**
     * @return result
     */
    public String viewDoc() {
        LOG.info("Entering viewProtocolDoc");
        try {
            String docId = ServletActionContext.getRequest().getParameter("identifier");
            //spidfromviewresults
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(
                    "spidfromviewresults");
            //Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            DocumentDTO docDTO = RegistryServiceLocator.getDocumentService().get(IiConverter.convertToIi(docId));
            // InterventionalStudyProtocolWebDTO spDTO =
            // (InterventionalStudyProtocolWebDTO) ServletActionContext
            // .getRequest().getSession().getAttribute(Constants.PROTOCOL_DOCUMENT);
            StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
            sb.append(File.separator).append(spDTO.getAssignedIdentifier().getExtension()).append(File.separator)
            .append(
                    docDTO.getIdentifier().getExtension()).append('-').append(docDTO.getFileName().getValue());
            File downloadFile = new File(sb.toString());
            servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            FileInputStream fileToDownload = new FileInputStream(downloadFile);
            servletResponse.setHeader("Cache-Control", "cache"); 
            servletResponse.setHeader("Pragma", "cache");
            servletResponse.setHeader("Content-Disposition", "attachment; filename=" + downloadFile.getName());
            servletResponse.setContentLength(fileToDownload.available());
            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = fileToDownload.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException err) {
            LOG.error("TrialDocumentAction failed with FileNotFoundException: " + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            // e.printStackTrace();
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }
    
    /**
     * validate the search trial form elements.
     */
    private void validateForm() {
        if (PAUtil.isNotEmpty(criteria.getIdentifierType()) 
                 && PAUtil.isEmpty(criteria.getIdentifier())) {
            addFieldError("criteria.identifier",
                    getText("error.search.identifier"));
        }
        if (PAUtil.isNotEmpty(criteria.getIdentifier()) 
                && PAUtil.isEmpty(criteria.getIdentifierType())) {
           addFieldError("criteria.identifierType",
                   getText("error.search.identifierType"));
       }
       if (PAUtil.isNotEmpty(criteria.getOrganizationType())
               && (criteria.getOrganizationId() == null 
                       && criteria.getParticipatingSiteId() == null)) {
           addFieldError("criteria.organizationId",
                   getText("error.search.organization"));

       }

    }
    
    private void getReponsibleParty(
                Ii studyProtocolIi, boolean maskFields) throws PAException, NullifiedRoleException {
        
        try {
            // retrieve responsible party info
            StudyContactDTO scDto = new StudyContactDTO();
            String respParty = "";
            String phone = "";
            String emailAddr = "";
            scDto.setRoleCode(CdConverter.convertToCd(
                    StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
            List<StudyContactDTO> scDtos = RegistryServiceLocator.getStudyContactService().
                                                getByStudyProtocol(studyProtocolIi, scDto);
            DSet dset = null;
            CorrelationUtils cUtils = new CorrelationUtils();
            Person respPartyContact = null;
            String respPartyContactName = null;
            if (scDtos != null && scDtos.size() > 0) {
                scDto = scDtos.get(0);
                dset = scDto.getTelecomAddresses();
                respPartyContact = cUtils.getPAPersonByIi(scDto.getClinicalResearchStaffIi());
                if (respPartyContact != null) {
                    respParty = "PI";

                }
                respPartyContactName = respPartyContact.getFullName();
            } else {
                StudySiteContactDTO spart = new StudySiteContactDTO();
                spart.setRoleCode(CdConverter.convertToCd(
                        StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
                List<StudySiteContactDTO> spDtos = RegistryServiceLocator.getStudySiteContactService()
                    .getByStudyProtocol(studyProtocolIi, spart);
                if (spDtos != null && spDtos.size() > 0) {
                    spart = spDtos.get(0);
                    dset = spart.getTelecomAddresses();
                    PAContactDTO paDto = cUtils.getContactByPAOrganizationalContactId((
                            Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                    
                    respPartyContactName = paDto.getResponsiblePartyContactName();
                    }
                if (respPartyContactName != null) {
                    respParty = "Sponsor";
                }
            }
            if (dset != null) {
                List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
                List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
                if (phones != null && phones.size() > 0) {
                    phone = phones.get(0);
                }
                if (emails != null && emails.size() > 0) {
                    emailAddr = emails.get(0);
                }    
            }            
            
            Organization sponsor = null;
            StudySiteDTO spart = new StudySiteDTO();
            spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> spDtos = RegistryServiceLocator.getStudySiteService()
                            .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && spDtos.size() > 0) {
                spart = spDtos.get(0);
                sponsor = new CorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            }
            if (sponsor != null && respPartyContactName != null && !maskFields) {
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY, respParty);
                if (respParty.equals("Sponsor")) {
                    ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_CONTACT, respPartyContactName);
                }                
                ServletActionContext.getRequest().setAttribute(
                                Constants.SPONSOR, sponsor.getName());
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_PHONE, phone); 
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_EMAIL, emailAddr); 
            }
            
        } catch (NumberFormatException e) {
            throw new PAException(e.getMessage());
        } catch (PAException e) {
            throw e;
        }
        
    }
}