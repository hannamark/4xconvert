package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * 
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class TrialDocumentAction extends ActionSupport implements
ServletResponseAware {
 
    private static final Logger LOG  = Logger.getLogger(TrialDocumentAction.class);
    private static final String FILE_PATH = "C:/COPPA/Trial_Documents/";
    private File upload;
    private String uploadFileName;
    private List<TrialDocumentWebDTO> trialDocumentList;
    private TrialDocumentWebDTO trialDocumentWebDTO;
    private Long id = null;
    private HttpServletResponse servletResponse;
    private String page;
         
    /**  
     * @return result
     */
    public String query()  {
        LOG.info("Entering query");
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
            List<DocumentDTO> isoList = PaRegistry.getDocumentService().
            getDocumentsByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {                
                trialDocumentList = new ArrayList<TrialDocumentWebDTO>();                
                for (DocumentDTO dto : isoList) {
                    trialDocumentList.add(new TrialDocumentWebDTO(dto));
                }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, 
                        getText("error.trialDocument.noRecords"));
            }
            return SUCCESS;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**
     * @return result
     */
     public String input() {
         return INPUT;
     }
     
     /**
      * @return result
      */
     public String create() {
         LOG.info("Entering create");
         try {           
             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II); 
             DocumentDTO docDTO = new DocumentDTO();
             docDTO.setStudyProtocolIi(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             docDTO.setUserLastUpdated((StConverter.convertToSt(
                     ServletActionContext.getRequest().getRemoteUser())));
             DocumentDTO doc = PaRegistry.getDocumentService().createTrialDocument(docDTO); 

             StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
             .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);

             String fullFileName = FILE_PATH + "\\" + spDTO.getNciIdentifier() 
             + "\\" + doc.getIi().getExtension() + uploadFileName;

             File outputFile = new File(fullFileName);
             FileUtils.copyFile(upload, outputFile);
             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
             return SUCCESS;
         } catch (Exception e) {
             addActionError(e.getLocalizedMessage());
             return INPUT;
         }          
     }
     
     /**
      * @return result
      */
     public String saveFile() {
         LOG.info("Entering saveFile");
         try {  
             DocumentDTO  docDTO = 
                 PaRegistry.getDocumentService().getTrialDocumentById(IiConverter.convertToIi(id));

             StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
             .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);

             String filename = FILE_PATH + "\\" + spDTO.getNciIdentifier() + "\\"  
             + docDTO.getIi().getExtension() + docDTO.getFileName().getValue();

             File downloadFile = new File(filename);

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
             addActionError(e.getLocalizedMessage());
             query();
             return ERROR;
         }
         return NONE;
     }
     
     /**
      * @return result
      */
     public String edit() {
         LOG.info("Entering edit");
         try {  
             DocumentDTO  docDTO = 
                 PaRegistry.getDocumentService().getTrialDocumentById(IiConverter.convertToIi(id));
             trialDocumentWebDTO = new TrialDocumentWebDTO(docDTO);
         } catch (Exception e) {
             addActionError(e.getLocalizedMessage());
             return INPUT;
         }
         return INPUT;
     }
     
     /**
      * @return result
      */
     public String update() {
         LOG.info("Entering update");
         try {  
             
             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II); 
             DocumentDTO  docDTO = new DocumentDTO();
             docDTO = PaRegistry.getDocumentService().getTrialDocumentById(IiConverter.convertToIi(id));
             docDTO.setStudyProtocolIi(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             docDTO.setUserLastUpdated((StConverter.convertToSt(
                     ServletActionContext.getRequest().getRemoteUser())));
             PaRegistry.getDocumentService().updateTrialDocument(docDTO);
             create();
             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
         } catch (Exception e) {
             addActionError(e.getLocalizedMessage());
             return INPUT;
         }
         return SUCCESS;
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
     * @return trialDocumentList
     */
    public List<TrialDocumentWebDTO> getTrialDocumentList() {
        return trialDocumentList;
    }

    /**
     * @param trialDocumentList trialDocumentList
     */
    public void setTrialDocumentList(List<TrialDocumentWebDTO> trialDocumentList) {
        this.trialDocumentList = trialDocumentList;
    }

    /**
     * @return trialDocumentWebDTO
     */
    public TrialDocumentWebDTO getTrialDocumentWebDTO() {
        return trialDocumentWebDTO;
    }

    /**
     * @param trialDocumentWebDTO trialDocumentWebDTO
     */
    public void setTrialDocumentWebDTO(TrialDocumentWebDTO trialDocumentWebDTO) {
        this.trialDocumentWebDTO = trialDocumentWebDTO;
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

}
