package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.ExcelReader;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
 * @author Vrushali
 * 
 * 
 */
public class BatchHelper implements Runnable { 

    private static final Logger LOG = Logger.getLogger(BatchHelper.class);
    private String uploadLoc = null;
    private String trialDataFileName = null;
    private String unzipLoc = null;
    private String userName = null;
   /**
    * 
    * @param uploadLoc loc
    * @param trialDataFileName name
    * @param unzipLoc loc
    * @param userName name
    */
    public BatchHelper(String uploadLoc, String trialDataFileName, String unzipLoc, String userName) {
        super();
        this.uploadLoc = uploadLoc;
        this.trialDataFileName = trialDataFileName;
        this.unzipLoc = unzipLoc;
        this.userName = userName;
    }


    /**
     * 
     * @param fileName
     *            fileName
     * @throws PAException
     *             PAException
     * @return list list
     * @throws IOException i
     */
    public List<StudyProtocolBatchDTO> processExcel(String fileName)
            throws PAException, IOException {
        InputStream is;
        List<StudyProtocolBatchDTO> list = null;
        try {
            is = new FileInputStream(fileName);
            HSSFWorkbook wb = new ExcelReader().parseExcel(is);
            ExcelReader reader = new ExcelReader();
            list = reader.convertToDTOFromExcelWrokbook(wb);
        } catch (FileNotFoundException e) {
            LOG.error("BatchHelper:processExcel-" + e);
        }
        return list;
    }
    /**
     * starts the batch processing.
     */
    public void run() {
        //String subject = "Clinical Trials Reporting Program (CTRP) - Batch Trial Upload Status";
        final MailManager mailManager = new MailManager();
        try {
            // open a new Hibernate session and bind to the context
            HibernateUtil.getHibernateHelper().openAndBindSession();
           
            // start reading the xls file and create the required DTO
            List<StudyProtocolBatchDTO> dtoList = processExcel(uploadLoc
                    + File.separator + trialDataFileName);
            HashMap<String, String> map = new BatchCreateProtocols().createProtocols(dtoList, unzipLoc
                    + File.separator, userName);
            //get the Failed and Sucess count and remove it from map so that reporting of each trial 

            String sucessCount = (String) map.get("Sucess Trial Count");
            map.remove("Sucess Trial Count");
            String failedCount = (String) map.get("Failed Trial Count");
            map.remove("Failed Trial Count");
            String totalCount = Integer.valueOf(map.size()).toString();
            String attachFileName = generateExcelFileForAttachement(map);
            //get the values of email's subject and email body from the database
            String[] params = {userName , };
            MessageFormat formatterSubject = new MessageFormat(
                    RegistryServiceLocator.getLookUpTableService().getPropertyValue("trial.batchUpload.subject"));
            String emailSubject = formatterSubject.format(params);
            LOG.info("emailSubject is: " + emailSubject);
            String submissionMailBody = RegistryServiceLocator.getLookUpTableService().
                                    getPropertyValue("trial.batchUpload.body");
            submissionMailBody = submissionMailBody.replace("${totalCount}", totalCount);
            submissionMailBody = submissionMailBody.replace("${successCount}", sucessCount);
            submissionMailBody = submissionMailBody.replace("${failedCount}", failedCount);
            MessageFormat formatterBody = new MessageFormat(submissionMailBody);
            String emailBody =  formatterBody.format(params);
            
            
            // Send the batch upload report to the submitter
            mailManager.sendMailWithAattchement(userName, null, emailBody, emailSubject, attachFileName);


        } catch (Exception e) {
            LOG.error("Exception while processing batch" + e.getMessage());
            mailManager.sendMail(userName, null, "Exception while processing batch",
                    "Exception while processing batch");
        } finally {
            // unbind the  Hibernate session
            HibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        }
        
    }
    /**
     * 
     * @param map ma
     * @return st
     */
    @SuppressWarnings({"PMD.LooseCoupling" })
    private String generateExcelFileForAttachement(HashMap<String, String> map) {
        try {
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("new sheet");
            HSSFRow headerRow = sheet.createRow(0);
            HSSFCell cellId = headerRow.createCell(0);
            HSSFCell cellReason = headerRow.createCell(1);
            cellId.setCellValue(new HSSFRichTextString("Unique Trial Identifier"));
            cellReason.setCellValue(new HSSFRichTextString("Status"));
            Set<String> s = map.keySet();
            Iterator<String> iter = s.iterator();
            String mapLocalTrialId = null;
            int i = 0;
            HSSFRow detailRow;
            while (iter.hasNext()) {
                i++;
                mapLocalTrialId = (String) iter.next();
                detailRow = sheet.createRow(i);
                detailRow.createCell(0).setCellValue(new HSSFRichTextString(mapLocalTrialId));
                detailRow.createCell(1).setCellValue(new HSSFRichTextString((String) map.get(mapLocalTrialId)));
            }
            sheet.autoSizeColumn((short) 0);
            sheet.autoSizeColumn((short) 1);
            
            FileOutputStream fileOut = new FileOutputStream(uploadLoc + File.separator + "batchUploadReport.xls");
            wb.write(fileOut);
           fileOut.close();          
           LOG.error("Your file has been created succesfully");    
               } catch (Exception ex) {
                   LOG.error("exception while generating excel report" + ex.getMessage());
            }    

        return uploadLoc + File.separator + "batchUploadReport.xls";
    }


    /**
     * @return the uploadLoc
     */
    public String getUploadLoc() {
        return uploadLoc;
    }


    /**
     * @param uploadLoc the uploadLoc to set
     */
    public void setUploadLoc(String uploadLoc) {
        this.uploadLoc = uploadLoc;
    }


    /**
     * @return the trialDataFileName
     */
    public String getTrialDataFileName() {
        return trialDataFileName;
    }


    /**
     * @param trialDataFileName the trialDataFileName to set
     */
    public void setTrialDataFileName(String trialDataFileName) {
        this.trialDataFileName = trialDataFileName;
    }


    /**
     * @return the unzipLoc
     */
    public String getUnzipLoc() {
        return unzipLoc;
    }


    /**
     * @param unzipLoc the unzipLoc to set
     */
    public void setUnzipLoc(String unzipLoc) {
        this.unzipLoc = unzipLoc;
    }


    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }


    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
