package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.util.BatchConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 * 
 * 
 */
@SuppressWarnings("PMD")
public class BatchUploadAction extends ActionSupport implements
        ServletResponseAware {

    private static final Logger LOG = Logger.getLogger(BatchUploadAction.class);

    private File trialData;

    private String trialDataFileName;
    private String trialDataContentType;
    private File docZip;

    private String docZipFileName;

    private String orgName;

    private HttpServletResponse servletResponse;

    private String page;

    /**
     * @param response
     *            servletResponse
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
     * @return the trialDataContentType
     */
    public String getTrialDataContentType() {
        return trialDataContentType;
    }

    /**
     * @param trialDataContentType the trialDataContentType to set
     */
    public void setTrialDataContentType(String trialDataContentType) {
        this.trialDataContentType = trialDataContentType;
    }

    /**
     * @return docZip
     */
    public File getDocZip() {
        return docZip;
    }

    /**
     * @param docZip
     *            docZip
     */
    public void setDocZip(File docZip) {
        this.docZip = docZip;
    }

    /**
     * @return docZipFileName
     */
    public String getDocZipFileName() {
        return docZipFileName;
    }

    /**
     * @param docZipFileName
     *            docZipFileName
     */
    public void setDocZipFileName(String docZipFileName) {
        this.docZipFileName = docZipFileName;
    }

    /**
     * @return OrgName
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * @param orgName
     *            orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * @return trialData
     */
    public File getTrialData() {
        return trialData;
    }

    /**
     * @param trialData
     *            trialData
     */
    public void setTrialData(File trialData) {
        this.trialData = trialData;
    }

    /**
     * @return trialDataFileName
     */
    public String getTrialDataFileName() {
        return trialDataFileName;
    }

    /**
     * @param trialDataFileName
     *            trialDataFileName
     */
    public void setTrialDataFileName(String trialDataFileName) {
        this.trialDataFileName = trialDataFileName;
    }

    /**
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * 
     * @return res
     */
    public String execute() {
        return SUCCESS;
    }

    /**
     * process the batch.
     * 
     * @return String
     */
    public String process() {
        // validate the form elements
        validateForm();
        if (hasFieldErrors()) {
          return ERROR;
        }
        // First save uploaded xls and then zip files
        String uploadedLoc;
        try {
            uploadedLoc = uploadFile();
            //helper to unzip the zip files
            BatchHelper helper = new BatchHelper();
            String unzipLoc = helper.unZipDoc(uploadedLoc + File.separator + docZipFileName);
           // start reading the xls file and create the required DTO
            List<StudyProtocolBatchDTO> dtoList = helper.processExcel(uploadedLoc + File.separator + trialDataFileName);
            new BatchCreateProtocols().createProtocols(dtoList, unzipLoc + File.separator);
        } catch (PAException e) {
            // TODO Auto-generated catch block
            LOG.error("error in Batch" + e.getMessage());
            ServletActionContext.getRequest().setAttribute(
                    "failureMessage", e.getMessage());
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute(
                "failureMessage", "Success....");
        return SUCCESS;
    }

    private String uploadFile() throws PAException {
        String uploadedLoc = "";
        
            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sbFolderPath = new StringBuffer(folderPath);
            LOG.info("folderPath..." + folderPath);
            DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss");
            df.setLenient(false);
            Date today = new Date();
            sbFolderPath.append(File.separator).append(orgName).append(
                    df.format(today));
            LOG.info("sbFolderPath...1" + sbFolderPath);
            File f = new File(sbFolderPath.toString());
            if (!f.exists()) {
                // create a new directory
                boolean md = f.mkdir();
                if (md) {
                    // save the XLS file and then the ZIp file
                    saveFile(sbFolderPath.toString(), trialDataFileName,
                            trialData);
                    saveFile(sbFolderPath.toString(), docZipFileName, docZip);
                    uploadedLoc = sbFolderPath.toString();
                }
            }
                return uploadedLoc;
    }

    /**
     * save the file.
     * 
     * @param fileName
     *            file
     */
    private void saveFile(String folderPath, String fileName, File file)
            throws PAException {
        // create the file
        LOG.debug("fileName" + fileName);
        try {
            File outFile = new File(folderPath + File.separator + fileName);
            FileOutputStream fos = new FileOutputStream(outFile);
            int bytesRead = 0;
            byte[] buffer = new byte[BatchConstants.READ_SIZE];
            InputStream stream = new FileInputStream(file);
            while ((bytesRead = stream.read(buffer, 0, BatchConstants.READ_SIZE)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new PAException(
                    "Error while creating directory  " + fileName, e);
        }
    }

    /**
     * 
     *
     */
     public void validateForm() {
        // TODO Auto-generated method stub
         if (PAUtil.isEmpty(orgName)) {
             addFieldError("orgName", getText("error.batch.orgName"));
         }
         if (PAUtil.isEmpty(trialDataFileName)) {
             addFieldError("trialDataFileName", getText("error.batch.trialDataFileName"));
         }

         if (PAUtil.isEmpty(docZipFileName)) {
             addFieldError("docZipFileName", getText("error.batch.docZipFileName"));
         }

    }
    
}
