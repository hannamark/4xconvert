package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
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
            List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
            // List <StudyOverallStatusWebDTO> overallStatusList;
            if (!(overallStatusISOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_OVERALL_STATUS,
                        overallStatusISOList.get(0));
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.STUDY_PARTICIPATION);
            // query the study participation sites
            List<StudyParticipationDTO> studyParticipationISOList = RegistryServiceLocator
                    .getStudyParticipationService().getByStudyProtocol(studyProtocolIi);
            // List <StudyParticipationWebDTO> studyParticipationList;
            if (!(studyParticipationISOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_PARTICIPATION,
                        studyParticipationISOList.get(0));
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
            if (resourcingDTO != null) {
                Organization o = new CorrelationUtils().
                    getPAOrganizationByIndetifers(Long.valueOf(resourcingDTO.
                            getOrganizationIdentifier().getExtension()), null);
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
            }
        }
        queryCriteria.setLeadOrganizationId(criteria.getOrganizationId());
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
            List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.getStudyOverallStatusService()
                    .getCurrentByStudyProtocol(studyProtocolIi);
            // List <StudyOverallStatusWebDTO> overallStatusList;
            if (!(overallStatusISOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.TRIAL_OVERALL_STATUS,
                        overallStatusISOList.get(0));
            }
            // remove the session variables stored during a previous view if any
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.STUDY_PARTICIPATION);
            // query the study participation sites
            List<StudyParticipationDTO> studyParticipationISOList = RegistryServiceLocator
                    .getStudyParticipationService().getByStudyProtocol(studyProtocolIi);
            // List <StudyParticipationWebDTO> studyParticipationList;
            if (!(studyParticipationISOList.isEmpty())) {
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(Constants.STUDY_PARTICIPATION,
                        studyParticipationISOList.get(0));
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
                    getPAOrganizationByIndetifers(Long.valueOf(resourcingDTO.
                            getOrganizationIdentifier().getExtension()), null);
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
        }
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
                && criteria.getOrganizationId() == null) {
           addFieldError("criteria.organizationId",
                   getText("error.search.organization"));
       }

    }
    
    private void getReponsibleParty(
                Ii studyProtocolIi, boolean maskFields) throws PAException {
        
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
            if (scDtos != null && scDtos.size() > 0) {
                scDto = scDtos.get(0);
                dset = scDto.getTelecomAddresses();
                respPartyContact = cUtils.getPAPersonByPAClinicalResearchStaffId(
                        Long.valueOf(scDto.getClinicalResearchStaffIi().getExtension()));
                if (respPartyContact != null) {
                    respParty = "PI";

                }
            } else {
                StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
                spart.setRoleCode(CdConverter.convertToCd(
                        StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
                List<StudyParticipationContactDTO> spDtos = RegistryServiceLocator.getStudyParticipationContactService()
                    .getByStudyProtocol(studyProtocolIi, spart);
                if (spDtos != null && spDtos.size() > 0) {
                    spart = spDtos.get(0);
                    dset = spart.getTelecomAddresses();
                    respPartyContact = cUtils.getPAPersonByPAOrganizationalContactId((
                            Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                }
                if (respPartyContact != null) {
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
            StudyParticipationDTO spart = new StudyParticipationDTO();
            spart.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
            List<StudyParticipationDTO> spDtos = RegistryServiceLocator.getStudyParticipationService()
                            .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && spDtos.size() > 0) {
                spart = spDtos.get(0);
                sponsor = new CorrelationUtils().getPAOrganizationByPAResearchOrganizationId(
                            Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
            }
            if (sponsor != null && respPartyContact != null && !maskFields) {
                ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY, respParty);
                if (respParty.equals("Sponsor")) {
                    ServletActionContext.getRequest().setAttribute(
                                Constants.RESP_PARTY_CONTACT, respPartyContact.getFullName());
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