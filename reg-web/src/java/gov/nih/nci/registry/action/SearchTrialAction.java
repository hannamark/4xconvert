package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PaEarPropertyReader;
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
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
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
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            // TrialWebDTO trialWebDTO = new TrialWebDTO(protocolDTO);
            // put an entry in the session and store
            // InterventionalStudyProtocolDTO
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
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
                    .getByStudyProtocol(studyProtocolIi);
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
            // put an entry in the session and getsummary4ReportedResource
            StudyResourcingDTO resourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
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
            criteria.setUserLastCreated(ServletActionContext.getRequest().getRemoteUser());
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = RegistryServiceLocator.getProtocolQueryService().getStudyProtocolByCriteria(criteria);
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
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
     * @return res
     */
    public String view() {
        try {
            // remove the session variables stored during a previous view if any
            
            ServletActionContext.getRequest().getSession().removeAttribute(Constants.TRIAL_SUMMARY);
            Ii studyProtocolIi = IiConverter.convertToIi(studyProtocolId);
            ServletActionContext.getRequest().getSession().setAttribute("spidfromviewresults", studyProtocolIi);
            StudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(
                    studyProtocolIi);
            // TrialWebDTO trialWebDTO = new TrialWebDTO(protocolDTO);
            // put an entry in the session and store
            // InterventionalStudyProtocolDTO
            ServletActionContext.getRequest().setAttribute(Constants.TRIAL_SUMMARY, protocolDTO);
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
                    .getByStudyProtocol(studyProtocolIi);
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
            
            // put an entry in the session and getsummary4ReportedResource
            StudyResourcingDTO resourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                    .getsummary4ReportedResource(studyProtocolIi);
            
            // get the organzation name
            
            Organization o = new CorrelationUtils().
                getPAOrganizationByIndetifers(Long.valueOf(resourcingDTO.
                        getOrganizationIdentifier().getExtension()), null);

            ServletActionContext.getRequest().setAttribute("summaryFourSponsorName", o.getName());  
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
}