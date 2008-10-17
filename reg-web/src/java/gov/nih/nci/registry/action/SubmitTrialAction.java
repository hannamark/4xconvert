package gov.nih.nci.registry.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;


import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.util.Constants;

/**
 * 
 * @author Bala Nair
 * 
 */
@Validation
public class SubmitTrialAction extends ActionSupport
                                        implements ServletResponseAware {
    private static final String VIEW_TRIAL = "view";
    private static final Logger LOG  = Logger.getLogger(SubmitTrialAction.class);
    private List<TrialFundingWebDTO> trialFundingList;
    private TrialFundingWebDTO trialFundingWebDTO = new TrialFundingWebDTO();
    private Long cbValue;
    private String page;
    private File upload;
    private String uploadFileName;
    private HttpServletResponse servletResponse;
    private static final int MAXF = 1024; 
    /**
     * create protocol.
     * @return String
     */
    public String create() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = new InterventionalStudyProtocolDTO();
            String phaseCode = ServletActionContext.getRequest().getParameter("trialPhase");
            String officialTitle = ServletActionContext.getRequest().getParameter("trialTitle");
            String startDate = ServletActionContext.getRequest().getParameter("startDate");
            String completionDate = ServletActionContext.getRequest().getParameter("completionDate");
            String startDateType = ServletActionContext.getRequest().getParameter("startDateType");
            String completionDateType = ServletActionContext.getRequest().getParameter("completionDateType");
            
            //create Study Protocol
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(phaseCode)));
            protocolDTO.setOfficialTitle(StConverter.convertToSt(officialTitle));
            protocolDTO.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(startDate)));
            protocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(
                                    PAUtil.dateStringToTimestamp(completionDate)));
            protocolDTO.setStartDateTypeCode(CdConverter.convertToCd(
                                    ActualAnticipatedTypeCode.getByCode(startDateType)));
            protocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(completionDateType)));
            Ii studyProtocolIi = RegistryServiceLocator.getStudyProtocolService()
                                    .createInterventionalStudyProtocol(protocolDTO);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(studyProtocolIi));
            
            ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.STUDY_PROTOCOL_II, IiConverter.convertToString(studyProtocolIi));
            // create Study Participation record
            //StudyParticipationDTO studyPartcipationDTO = new StudyParticipationDTO();
            //studyPartcipationDTO.setStudyProtocolIi(studyProtocolIi);
            //studyPartcipationDTO.setLocalStudyProtocolIdentifier(
            //                                            StConverter.convertToSt(leadOrgIdentifier));
            //RegistryServiceLocator.getStudyParticipationService().create(studyPartcipationDTO);
            
            //create study overall status
            createStudyStatus(studyProtocolIi);
            
            // create the Study Grants
            createStudyResource(studyProtocolIi);
            //upload protocol document
            uploadDocument(studyProtocolIi);
            query();
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Exception occured while submitting trial: " + e);
        }
        return VIEW_TRIAL;
    }

    /**
     * @param studyProtocolIi
     */
    private void createStudyResource(Ii studyProtocolIi) {
        // create the Study Grants
        try {
            String fundingMechanism = ServletActionContext.getRequest().getParameter("fundingMechanism");
            String nihInstituteCode = ServletActionContext.getRequest().getParameter("instituteCode");
            String serialNumber = ServletActionContext.getRequest().getParameter("serialNumber");
            String divPrgCode = ServletActionContext.getRequest().getParameter("divPrgCode");
            
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            
    
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(CdConverter.convertStringToCd(fundingMechanism));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                                                            MonitorCode.getByCode(divPrgCode)));
            studyResoureDTO.setNihInstitutionCode(CdConverter.convertStringToCd(nihInstituteCode));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(serialNumber));
            studyResoureDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            RegistryServiceLocator.getStudyResourcingService().createStudyResourcing(studyResoureDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while creating study resource: " + pae);
        }
    }

    /**
     * @param studyProtocolIi
     */
    private void createStudyStatus(Ii studyProtocolIi)  {
        //create study overall status
        try {

            String trialStatus = ServletActionContext.getRequest().getParameter("trialStatus");
            String statusDate = ServletActionContext.getRequest().getParameter("statusDate");
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
            //overallStatusDTO.setIi(IiConverter.convertToIi((Long) null));
            overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
            overallStatusDTO.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialStatus)));
            overallStatusDTO.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(statusDate)));
            RegistryServiceLocator.getStudyOverallStatusService().create(overallStatusDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while creating study overall status: " + pae);
        }
    }
    
    /**
     * Uploads documents.
     * @param studyProtocolIi
     */
    private void uploadDocument(Ii studyProtocolIi) {
        try {

            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setStudyProtocolIi(studyProtocolIi);
            docDTO.setTypeCode(
                    CdConverter.convertStringToCd(DocumentTypeCode.Protocol_Document.getCode()));
            docDTO.setFileName(StConverter.convertToSt(uploadFileName));
            docDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            docDTO.setText(EdConverter.convertToEd(
                        readInputStream(new FileInputStream(upload))));
            RegistryServiceLocator.getDocumentService().create(docDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while uploading documents: " + pae);
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            LOG.error("Exception occured reading file " + ioe);
            
        }
    }
    
    /**
     * query the created protocol and all related associations.
     * @return String
     */
    public String query() {
        try {
            
            Ii studyProtocolIi = IiConverter.convertToIi((String) ServletActionContext.
                                    getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II)); 
                        
            InterventionalStudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().
                                                             getInterventionalStudyProtocol(studyProtocolIi);
            InterventionalStudyProtocolWebDTO protocolWebDTO =
                                                          new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store InterventionalStudyProtocolDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                                    Constants.TRIAL_SUMMARY, protocolWebDTO);
            
            // query the study grants 
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService().
                                                 getstudyResourceByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) { 
                trialFundingList = new ArrayList<TrialFundingWebDTO>();                
                for (StudyResourcingDTO dto : isoList) {
                    trialFundingList.add(new TrialFundingWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.TRIAL_FUNDING_LIST, trialFundingList.get(0));
            }
            
            List<StudyOverallStatusDTO> overallStatusISOList = RegistryServiceLocator.
                                                        getStudyOverallStatusService().
                                                            getByStudyProtocol(studyProtocolIi);
            List <StudyOverallStatusWebDTO> overallStatusList;
            if (!(overallStatusISOList.isEmpty())) { 
                overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();                
                for (StudyOverallStatusDTO dto : overallStatusISOList) {
                    overallStatusList.add(new StudyOverallStatusWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.TRIAL_OVERALL_STATUS, overallStatusList.get(0));
            }
 
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Exception occured while querying trial " + e);
        }
        return VIEW_TRIAL;
    }
    
    /**
     * @return result
     */
     public String input() {
         return INPUT;
     }
    
     // Read an input stream in its entirety into a byte array.
     private static byte[] readInputStream(InputStream inputStream) throws IOException {
        
        int bufSize = MAXF * MAXF; 
        byte[] content; 

        List<byte[]> parts = new LinkedList(); 
        InputStream in = new BufferedInputStream(inputStream); 

        byte[] readBuffer = new byte[bufSize]; 
        byte[] part = null; 
        int bytesRead = 0; 

        // read everything into a list of byte arrays 
        while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) { 
            part = new byte[bytesRead]; 
            System.arraycopy(readBuffer, 0, part, 0, bytesRead); 
            parts.add(part); 
        } 

        // calculate the total size 
        int totalSize = 0; 
        for (byte[] partBuffer : parts) { 
            totalSize += partBuffer.length; 
        } 

        // allocate the array 
        content = new byte[totalSize]; 
        int offset = 0; 
        for (byte[] partBuffer : parts) { 
            System.arraycopy(partBuffer, 0, content, offset, partBuffer.length); 
            offset += partBuffer.length; 
        } 

        return content; 
    }   
    
    /**
     * @return trialFundingList
     */
    public List<TrialFundingWebDTO> getTrialFundingList() {
        return trialFundingList;
    }

    /**
     * @param trialFundingList trialFundingList
     */
    public void setTrialFundingList(List<TrialFundingWebDTO> trialFundingList) {
        this.trialFundingList = trialFundingList;
    }
    
    /**
     * @return trialFundingWebDTO
     */
    public TrialFundingWebDTO getTrialFundingWebDTO() {
        return trialFundingWebDTO;
    }

    /**
     * @param trialFundingWebDTO trialFundingWebDTO
     */
    public void setTrialFundingWebDTO(TrialFundingWebDTO trialFundingWebDTO) {
        this.trialFundingWebDTO = trialFundingWebDTO;
    }
    
    /**
     * @param response servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }
    /**
     * @return servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    } 
    
    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }
    
    /**
     * @return upload
     */
    public File getUpload() {
        return upload;
     }
    
     /**
     * @param upload upload
     */
    public void setUpload(File upload) {
         this.upload = upload;
     }
    
    /**
     * @return fileName
     */
    public String getUploadFileName() {
         return uploadFileName;
     }
    
     /**
     * @param uploadFileName uploadFileName
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
     } 

}
