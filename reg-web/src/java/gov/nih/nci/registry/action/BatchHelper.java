package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.mail.MailManager;
import gov.nih.nci.registry.util.ExcelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
 * @author Vrushali
 * 
 * 
 */
@SuppressWarnings("PMD")
public class BatchHelper { //implements Runnable {

    private static Logger log = Logger.getLogger(BatchHelper.class);
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
     */
    public List<StudyProtocolBatchDTO> processExcel(String fileName)
            throws PAException {
        InputStream is;
        List<StudyProtocolBatchDTO> list = null;
        try {
            is = new FileInputStream(fileName);
            HSSFWorkbook wb = new ExcelReader().parseExcel(is);
            ExcelReader reader = new ExcelReader();
            list = reader.convertToDTOFromExcelWrokbook(wb);
        } catch (FileNotFoundException e) {
            log.error("BatchHelper:processExcel-" + e);
        }
        return list;
    }
    /**
     * starts the batch processing.
     */
    public void run() {
        StringBuffer mailBody = new StringBuffer();
        String subject = "Batch Upload Status";
        try {
            // start reading the xls file and create the required DTO
            List<StudyProtocolBatchDTO> dtoList = processExcel(uploadLoc
                    + File.separator + trialDataFileName);
            HashMap map = new BatchCreateProtocols().createProtocols(dtoList, unzipLoc
                    + File.separator);
            Set s = map.keySet();
            Iterator iter = s.iterator();
            String mapLocalTrialId = null;
            while (iter.hasNext()) {
                mapLocalTrialId = (String) iter.next();
                log.error("user " + userName  
                        + " response" + map.get(mapLocalTrialId));
                mailBody.append(map.get(mapLocalTrialId));
                mailBody.append("\n");
            }
            final MailManager mailManager = new MailManager();
            mailManager.sendMail(userName, null, mailBody.toString(), subject);
           
        } catch (Exception e) {
            log.error("Exception while processing batch");
        }        
        
    }

}
