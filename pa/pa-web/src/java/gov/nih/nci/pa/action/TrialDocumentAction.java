package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDocumentWebDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;

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

@Validation
public class TrialDocumentAction extends ActionSupport implements
ServletResponseAware {

    private static final Logger LOG  = Logger.getLogger(TrialDocumentAction.class);
    private static final String DELETE_RESULT = "delete";
    private File upload;
    private String uploadFileName;
    private List<TrialDocumentWebDTO> trialDocumentList;
    private TrialDocumentWebDTO trialDocumentWebDTO = new TrialDocumentWebDTO();
    private Long id = null;
    private HttpServletResponse servletResponse;
    private String page;
    private static final int MAXF = 1024;


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
         if (PAUtil.isEmpty(trialDocumentWebDTO.getTypeCode())) {
             addFieldError("trialDocumentWebDTO.typeCode",
                     getText("error.trialDocument.typeCode"));
         }
         if (PAUtil.isEmpty(uploadFileName)) {
             addFieldError("trialDocumentWebDTO.uploadFileName",
                     getText("error.trialDocument.uploadFileName"));
         }
         if (hasFieldErrors()) {
             return INPUT;
         }
         try {
             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II);
             DocumentDTO docDTO = new DocumentDTO();
             docDTO.setStudyProtocolIi(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             docDTO.setText(EdConverter.convertToEd(readInputStream(new FileInputStream(upload))));
             PaRegistry.getDocumentService().create(docDTO);
             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
             return SUCCESS;
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
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
                 PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));

             StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
             .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);


             StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
             sb.append(File.separator).append(spDTO.getNciIdentifier()).append(File.separator).
                 append(docDTO.getIdentifier().getExtension()).append('-').append(docDTO.getFileName().getValue());

             File downloadFile = new File(sb.toString());
             StringBuffer fileName = new StringBuffer(); 
             fileName.append(spDTO.getNciIdentifier()).append('-').append(docDTO.getFileName().getValue());
             
             FileInputStream fileToDownload = new FileInputStream(downloadFile);
             servletResponse.setContentType("application/octet-stream");
             servletResponse.setContentLength(fileToDownload.available());
             servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
             servletResponse.setHeader("Pragma", "public");
             servletResponse.setHeader("Cache-Control", "max-age=0");
             
             
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
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
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
                 PaRegistry.getDocumentService().get(IiConverter.convertToIi(id));
             trialDocumentWebDTO = new TrialDocumentWebDTO(docDTO);
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
         return INPUT;
     }

     /**
      * @return result
      */
     public String update() {
         LOG.info("Entering update");
         if (PAUtil.isEmpty(trialDocumentWebDTO.getTypeCode())) {
             addFieldError("trialDocumentWebDTO.typeCode", getText("error.trialDocument.typeCode"));
         }
         if (PAUtil.isEmpty(uploadFileName)) {
             addFieldError("trialDocumentWebDTO.uploadFileName", getText("error.trialDocument.uploadFileName"));
         }
         if (hasFieldErrors()) {
             return INPUT;
         }
         try {

             Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
             getAttribute(Constants.STUDY_PROTOCOL_II);
             DocumentDTO  docDTO = new DocumentDTO();
             docDTO.setIdentifier(IiConverter.convertToIi(id));
             docDTO.setStudyProtocolIi(studyProtocolIi);
             docDTO.setTypeCode(
                     CdConverter.convertStringToCd(trialDocumentWebDTO.getTypeCode()));
             docDTO.setFileName(StConverter.convertToSt(uploadFileName));
             docDTO.setText(EdConverter.convertToEd(readInputStream(new FileInputStream(upload))));
             PaRegistry.getDocumentService().update(docDTO);
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
             query();
         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return INPUT;
         }
         return SUCCESS;
     }

     /**
      * @return result
      */
     public String delete()  {

         LOG.info("Entering delete");
         if (PAUtil.isEmpty(trialDocumentWebDTO.getInactiveCommentText())) {
             addFieldError("trialDocumentWebDTO.inactiveCommentText", getText("error.trialDocument.delete.reason"));
         }
         if (hasFieldErrors()) {
             return DELETE_RESULT;
         }
         try {
             DocumentDTO docDTO = new DocumentDTO();

             docDTO = PaRegistry.getDocumentService().get(
                     IiConverter.convertToIi(id));
             docDTO.setInactiveCommentText(StConverter.convertToSt(
                     trialDocumentWebDTO.getInactiveCommentText()));
             PaRegistry.getDocumentService().delete(docDTO);

             query();
             ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
             return SUCCESS;

         } catch (Exception e) {
             ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
             return DELETE_RESULT;
         }
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

    /** Read an input stream in its entirety into a byte array. */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {

        int bufSize = MAXF * MAXF;
        byte[] content;

        List<byte[]> parts = new LinkedList();
        InputStream in = new BufferedInputStream(inputStream);

        byte[] readBuffer = new byte[bufSize];
        byte[] part = null;
        int bytesRead = 0;

        // read everyting into a list of byte arrays
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

}
