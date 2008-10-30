package gov.nih.nci.registry.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
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
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.dto.StudyParticipationWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.Constants;

/**
 * 
 * @author Bala Nair
 * 
 */
@Validation
@SuppressWarnings({ "PMD" })
public class SubmitTrialAction extends ActionSupport implements
                                                    ServletResponseAware {
    private static final String VIEW_TRIAL = "view";
    private static final Logger LOG  = Logger.getLogger(SubmitTrialAction.class);
    private InterventionalStudyProtocolWebDTO protocolWebDTO = new InterventionalStudyProtocolWebDTO();
    private StudyParticipationWebDTO participationWebDTO = new StudyParticipationWebDTO();
    private TrialFundingWebDTO trialFundingWebDTO = new TrialFundingWebDTO();
    private StudyOverallStatusWebDTO overallStatusWebDTO = new StudyOverallStatusWebDTO();
    private TrialDocumentWebDTO trialDocumentWebDTO = new TrialDocumentWebDTO();
    private Long cbValue;
    private String page;
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private Long id = null;
    private HttpServletResponse servletResponse;

    private static final int MAXF = 1024; 
 
    /**
     * create protocol.
     * @return String
     */
    public String create() {
        try {        
            
            clearErrorsAndMessages();
            
            // validate the form elements
            validateForm();

            if (hasFieldErrors()) {
                return ERROR;
            }
            
            //create Study Protocol
            InterventionalStudyProtocolDTO protocolDTO = new InterventionalStudyProtocolDTO();
            protocolDTO.setPhaseCode(CdConverter.convertToCd(
                                    PhaseCode.getByCode(protocolWebDTO.getTrialPhase())));
            protocolDTO.setOfficialTitle(StConverter.convertToSt(
                                    protocolWebDTO.getTrialTitle()));
            protocolDTO.setStartDate(TsConverter.convertToTs(
                                    PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate())));
            protocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(
                                    PAUtil.dateStringToTimestamp(protocolWebDTO.getStartDate())));
            protocolDTO.setStartDateTypeCode(CdConverter.convertToCd(
                                    ActualAnticipatedTypeCode.getByCode(protocolWebDTO.getStartDateType())));
            protocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                    .convertToCd(ActualAnticipatedTypeCode.getByCode(protocolWebDTO.getCompletionDateType())));
            Ii studyProtocolIi = RegistryServiceLocator.getStudyProtocolService()
                                    .createInterventionalStudyProtocol(protocolDTO);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(studyProtocolIi));
            
            ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.STUDY_PROTOCOL_II, IiConverter.convertToString(studyProtocolIi));
            
            //create study participation
            createStudyParticipation(studyProtocolIi);
            
            //create study overall status
            createStudyStatus(studyProtocolIi);
            
            if (!PAUtil.isEmpty(trialFundingWebDTO.getFundingMechanismCode())) {
                // create the Study Grants
                createStudyResource(studyProtocolIi);
            }
            //upload protocol document
            uploadProtocolDocument(studyProtocolIi);
            
            //upload IRB Approval document
            uploadIrbApproval(studyProtocolIi);
            
            // createStudyContact
            createStudyContact(studyProtocolIi);
            
            // after creating the study protocol, query the protocol for viewing
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
            
            StudyResourcingDTO studyResoureDTO = new StudyResourcingDTO();            
    
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(
                            BlConverter.convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(
                            CdConverter.convertStringToCd(trialFundingWebDTO.getFundingMechanismCode()));
            studyResoureDTO.setNciDivisionProgramCode(
                            CdConverter.convertToCd(MonitorCode.getByCode(
                               trialFundingWebDTO.getNciDivisionProgramCode())));
            studyResoureDTO.setNihInstitutionCode(
                            CdConverter.convertStringToCd(trialFundingWebDTO.getNihInstitutionCode()));
            studyResoureDTO.setSerialNumber(
                            IntConverter.convertToInt(trialFundingWebDTO.getSerialNumber()));
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

            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
            //overallStatusDTO.setIi(IiConverter.convertToIi((Long) null));
            overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
            overallStatusDTO.setStatusCode(CdConverter.convertToCd(
                        StudyStatusCode.getByCode(overallStatusWebDTO.getStatusCode())));
            overallStatusDTO.setStatusDate(TsConverter.convertToTs(
                        PAUtil.dateStringToTimestamp(overallStatusWebDTO.getStatusDate())));
            RegistryServiceLocator.getStudyOverallStatusService().create(overallStatusDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while creating study overall status: " + pae);
        }
    }
    
    /**
     * Uploads Protocol Document.
     * @param studyProtocolIi
     */
    private void uploadProtocolDocument(Ii studyProtocolIi) {
        try {

            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setStudyProtocolIi(studyProtocolIi);
            docDTO.setTypeCode(
                    CdConverter.convertStringToCd(DocumentTypeCode.Protocol_Document.getCode()));
            docDTO.setFileName(StConverter.convertToSt(protocolDocFileName));
            docDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            docDTO.setText(EdConverter.convertToEd(
                        readInputStream(new FileInputStream(protocolDoc))));
            RegistryServiceLocator.getDocumentService().create(docDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while uploading Protocol Document: " + pae);
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            LOG.error("Exception occured reading  Protocol Document " + ioe);
            
        }
    }
    
    /**
     * Uploads IRB Approval Document.
     * @param studyProtocolIi
     */
    private void uploadIrbApproval(Ii studyProtocolIi) {
        try {

            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setStudyProtocolIi(studyProtocolIi);
            docDTO.setTypeCode(
                    CdConverter.convertStringToCd(DocumentTypeCode.IRB_Approval_Document.getCode()));
            docDTO.setFileName(StConverter.convertToSt(irbApprovalFileName));
            docDTO.setUserLastUpdated((StConverter.convertToSt(
                    ServletActionContext.getRequest().getRemoteUser())));
            docDTO.setText(EdConverter.convertToEd(
                        readInputStream(new FileInputStream(irbApproval))));
            RegistryServiceLocator.getDocumentService().create(docDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while uploading IRB Approval Document: " + pae);
        } catch (IOException ioe) {
            //ioe.printStackTrace();
            LOG.error("Exception occured reading  IRB Approval Document " + ioe);
            
        }
    }
    
    /**
     * @param studyProtocolIi
     */
    private void createStudyParticipation(Ii studyProtocolIi) {
        try {
        // create Study Participation record
        StudyParticipationDTO studyPartcipationDTO = new StudyParticipationDTO();
        studyPartcipationDTO.setStudyProtocolIi(studyProtocolIi);
        studyPartcipationDTO.setLocalStudyProtocolIdentifier(
                      StConverter.convertToSt(participationWebDTO.getLocalProtocolIdentifier()));
        studyPartcipationDTO.setHealthcareFacilityIi(IiConverter.convertToIi("1"));
        studyPartcipationDTO.setFunctionalCode(
                    CdConverter.convertStringToCd(
                              StudyParticipationFunctionalCode.LEAD_ORAGANIZATION.getCode()));
        studyPartcipationDTO.setStatusCode(
                    CdConverter.convertStringToCd(StatusCode.ACTIVE.getCode()));
        RegistryServiceLocator.getStudyParticipationService().create(studyPartcipationDTO);
        } catch (PAException pae) {
            //pae.printStackTrace();
            LOG.error("Exception occured while creating study participation: " + pae);
        }
    }
    
    //TODO this method needs to be modified and appropriate
    // StudyContacts (Submitter or PI) have to be created
    // for each trial submitted
    /**
     * @param studyProtocolIi
     */
    private void createStudyContact(Ii studyProtocolIi) {
        try {
        LOG.info(" creating study contact ");
        // create Study Contact record        
        StudyContactDTO studyContactDTO = new StudyContactDTO();
        studyContactDTO.setStudyProtocolIi(studyProtocolIi);
        studyContactDTO.setHealthCareProvider(IiConverter.convertToIi("1"));
        studyContactDTO.setRoleCode(CdConverter.convertStringToCd(
                            StudyContactRoleCode.SUBMITTER.getCode()));
        studyContactDTO.setPrimaryIndicator(BlConverter.convertToBl(true));
        
        RegistryServiceLocator.getStudyContactService().create(studyContactDTO);
        } catch (PAException pae) {
            pae.printStackTrace();
            LOG.error("Exception occured while creating study contact: " + pae);
        } catch (Exception ex) {
        ex.printStackTrace();
        LOG.error("Exception occured while creating study contact: " + ex);
    }
    }
    
    /**
     * query the created protocol and all associated data.
     * @return String
     */
    public String query() {
        try {
            
            Ii studyProtocolIi = IiConverter.convertToIi((String) ServletActionContext.
                                    getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II)); 
                        
            InterventionalStudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().
                                                             getInterventionalStudyProtocol(studyProtocolIi);
            InterventionalStudyProtocolWebDTO trialWebDTO =
                                                          new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store InterventionalStudyProtocolDTO 
            ServletActionContext.getRequest().setAttribute(
                                    Constants.TRIAL_SUMMARY, trialWebDTO);
            
            // query the study grants 
            List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService().
                                                 getstudyResourceByStudyProtocol(studyProtocolIi);
            List <TrialFundingWebDTO> trialFundingList;
            if (!(isoList.isEmpty())) { 
                trialFundingList = new ArrayList<TrialFundingWebDTO>();                
                for (StudyResourcingDTO dto : isoList) {
                    trialFundingList.add(new TrialFundingWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(
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
                ServletActionContext.getRequest().setAttribute(
                                        Constants.TRIAL_OVERALL_STATUS, overallStatusList.get(0));
            }
            
            // query the study participation sites
            List<StudyParticipationDTO> studyParticipationISOList = 
                                       RegistryServiceLocator.getStudyParticipationService().
                                               getByStudyProtocol(studyProtocolIi);
            
            List <StudyParticipationWebDTO> studyParticipationList;
            if (!(studyParticipationISOList.isEmpty())) { 
                studyParticipationList = new ArrayList<StudyParticipationWebDTO>();                
                for (StudyParticipationDTO dto : studyParticipationISOList) {
                    studyParticipationList.add(new StudyParticipationWebDTO(dto));
                }
                // put an entry in the session and store TrialFunding
                ServletActionContext.getRequest().setAttribute(
                                        Constants.STUDY_PARTICIPATION, studyParticipationList.get(0));
                
            }
            
            // query the trial documents
            List<DocumentDTO> documentISOList = 
                                       RegistryServiceLocator.getDocumentService().
                                               getDocumentsByStudyProtocol(studyProtocolIi);
            List <TrialDocumentWebDTO> documentList;
            if (!(documentISOList.isEmpty())) { 
                documentList = new ArrayList<TrialDocumentWebDTO>();                
                for (DocumentDTO dto : documentISOList) {
                    documentList.add(new TrialDocumentWebDTO(dto));
                }
                
                for (TrialDocumentWebDTO webdto : documentList) {
                    if (webdto.getTypeCode().equalsIgnoreCase(
                            DocumentTypeCode.Protocol_Document.getCode())) {
                        // put an entry in the session and store Protocol Document
                        ServletActionContext.getRequest().setAttribute(
                                                Constants.PROTOCOL_DOCUMENT, webdto);
                        
                    }
                    if (webdto.getTypeCode().equalsIgnoreCase(
                            DocumentTypeCode.IRB_Approval_Document.getCode())) {
                        // put an entry in the session and store IRB Approval Document
                        ServletActionContext.getRequest().setAttribute(
                                                Constants.IRB_APPROVAL, webdto);                        
                    }                    
                }
                
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
    public String viewDoc() {
        LOG.info("Entering viewProtocolDoc");
        try {  
            DocumentDTO  docDTO = 
                RegistryServiceLocator.getDocumentService().get(IiConverter.convertToIi(id));

            InterventionalStudyProtocolWebDTO spDTO = (InterventionalStudyProtocolWebDTO) ServletActionContext
                                .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);

            
            StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
            sb.append(File.separator).append(spDTO.getNciAccessionNumber()).append(File.separator).
                append(docDTO.getIdentifier().getExtension()).append('-').append(docDTO.getFileName().getValue());

            File downloadFile = new File(sb.toString());
            servletResponse = ServletActionContext.getResponse();
            servletResponse.setContentType("application/x-unknown");
            FileInputStream fileToDownload = new FileInputStream(downloadFile);
            servletResponse.setHeader("Content-Disposition", "attachment; filename="
                    + downloadFile.getName());
            servletResponse.setContentLength(fileToDownload.available());
            int data;
            ServletOutputStream out = servletResponse.getOutputStream();
            while ((data = fileToDownload.read()) != -1) {
                out.write(data);
            }
            out.flush();
            out.close();             
        } catch (FileNotFoundException err) {
            LOG.error("TrialDocumentAction failed with FileNotFoundException: "
                    + err);
            this.addActionError("File not found: " + err.getLocalizedMessage());
            query();
            return ERROR;
        } catch (Exception e) {
            //e.printStackTrace();
            LOG.error("Exception occured while retrieving document " + e);
        }
        return NONE;
    }

    
    /**
     * validate the submit trial form elements.
     */
    private void validateForm()  {
        if (PAUtil.isEmpty(participationWebDTO.getLocalProtocolIdentifier())) {
            addFieldError("participationWebDTO.localProtocolIdentifier",
                    getText("error.submit.localProtocolIdentifier"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getTrialTitle())) {
            addFieldError("protocolWebDTO.trialTitle",
                    getText("error.submit.trialTitle"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getTrialPhase())) {
            addFieldError("protocolWebDTO.trialPhase",
                    getText("error.submit.trialPhase"));
        }
        
        // if funding mechanism is selected, make all other 
        // funding fields required
        if (!PAUtil.isEmpty(trialFundingWebDTO.getFundingMechanismCode())) {            
            if (PAUtil.isEmpty(trialFundingWebDTO.getNihInstitutionCode())) {                
                addFieldError("trialFundingWebDTO.nihInstitutionCode",
                        getText("error.submit.nihInstitutionCode"));
            }
            if (PAUtil.isEmpty(trialFundingWebDTO.getSerialNumber())) {                
                addFieldError("trialFundingWebDTO.serialNumber",
                        getText("error.submit.serialNumber"));
            } else if (!PAUtil.isEmpty(trialFundingWebDTO.getSerialNumber())) {
                
                try {
                    Long.valueOf(trialFundingWebDTO.getSerialNumber());
                } catch (NumberFormatException nfe) {
                    // if cannot be converted to long then it's not a number
                    addFieldError("trialFundingWebDTO.serialNumber",
                            getText("error.submit.serialNumberType"));
                }
                
            }
            if (PAUtil.isEmpty(trialFundingWebDTO.getNciDivisionProgramCode())) {                
                addFieldError("trialFundingWebDTO.nciDivisionProgramCode",
                        getText("error.submit.nciDivisionProgramCode"));
            }
        }
        
        if (PAUtil.isEmpty(overallStatusWebDTO.getStatusCode())) {
            addFieldError("overallStatusWebDTO.statusCode",
                    getText("error.submit.statusCode"));
        }
        if (PAUtil.isEmpty(overallStatusWebDTO.getStatusDate())) {
            addFieldError("overallStatusWebDTO.statusDate",
                    getText("error.submit.statusDate"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getStartDate())) {
            addFieldError("protocolWebDTO.startDate",
                    getText("error.submit.startDate"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getStartDateType())) {
            addFieldError("protocolWebDTO.startDateType",
                    getText("error.submit.dateType"));
        }
        if (PAUtil.isEmpty(protocolWebDTO.getCompletionDate())) {
            addFieldError("protocolWebDTO.completionDate",
                    getText("error.submit.completionDate"));
        }        
        if (PAUtil.isEmpty(protocolWebDTO.getCompletionDateType())) {
            addFieldError("protocolWebDTO.completionDateType",
                    getText("error.submit.dateType"));
        }        
        if (PAUtil.isEmpty(protocolDocFileName)) {
            addFieldError("trialDocumentWebDTO.protocolDocFileName",
                    getText("error.submit.protocolDocument"));           
        }
        if (PAUtil.isEmpty(irbApprovalFileName)) {
            addFieldError("trialDocumentWebDTO.irbApprovalFileName",
                    getText("error.submit.irbApproval"));           
        }
        
        
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
      * @return protocolWebDTO
      */
     public InterventionalStudyProtocolWebDTO getProtocolWebDTO() {
         return protocolWebDTO;
     }
     
     /**
      * @param protocolWebDTO protocolWebDTO
      */
     public void setProtocolWebDTO(InterventionalStudyProtocolWebDTO protocolWebDTO) {
         this.protocolWebDTO = protocolWebDTO;
     }
     
     /**
      * @return the overallStatusWebDTO
      */
     public StudyOverallStatusWebDTO getOverallStatusWebDTO() {
         return overallStatusWebDTO;
     }

     /**
      * @param overallStatusWebDTO the overallStatusWebDTO to set
      */
     public void setOverallStatusWebDTO(StudyOverallStatusWebDTO overallStatusWebDTO) {
         this.overallStatusWebDTO = overallStatusWebDTO;
     }

     
     /**
      * @return the participationWebDTO
      */
     public StudyParticipationWebDTO getParticipationWebDTO() {
         return participationWebDTO;
     }

     /**
      * @param participationWebDTO the participationWebDTO to set
      */
     public void setParticipationWebDTO(StudyParticipationWebDTO participationWebDTO) {
         this.participationWebDTO = participationWebDTO;
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
     * @return the trialDocumentWebDTO
     */
    public TrialDocumentWebDTO getTrialDocumentWebDTO() {
        return trialDocumentWebDTO;
    }

    /**
     * @param trialDocumentWebDTO the trialDocumentWebDTO to set
     */
    public void setTrialDocumentWebDTO(TrialDocumentWebDTO trialDocumentWebDTO) {
        this.trialDocumentWebDTO = trialDocumentWebDTO;
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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDoc;
     }
    
     /**
     * @param protocolDoc protocolDoc
     */
    public void setProtocolDoc(File protocolDoc) {
         this.protocolDoc = protocolDoc;
     }
    
    /**
     * @return protocolDocFileName
     */
    public String getProtocolDocFileName() {
         return protocolDocFileName;
     }
    
     /**
     * @param protocolDocFileName protocolDocFileName
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocFileName = protocolDocFileName;
     } 
    
    /**
     * @return irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
     }
    
     /**
     * @param irbApproval irbApproval
     */
    public void setIrbApproval(File irbApproval) {
         this.irbApproval = irbApproval;
     }
    
    /**
     * @return irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
         return irbApprovalFileName;
     }
    
     /**
     * @param irbApprovalFileName irbApprovalFileName
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
     } 

}
