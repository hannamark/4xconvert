package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.util.ExcelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
 * @author Vrushali
 * 
 * 
 */
@SuppressWarnings("PMD")
public class BatchHelper implements Runnable {

    private static Logger log = Logger.getLogger(BatchHelper.class);
    private String uploadLoc = null;
    private String trialDataFileName = null;
    private String unzipLoc = null;
   /**
    * 
    * @param uploadLoc loc
    * @param trialDataFileName name
    * @param unzipLoc loc
    */
    public BatchHelper(String uploadLoc, String trialDataFileName, String unzipLoc) {
        super();
        this.uploadLoc = uploadLoc;
        this.trialDataFileName = trialDataFileName;
        this.unzipLoc = unzipLoc;
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
        try {
            // start reading the xls file and create the required DTO
            List<StudyProtocolBatchDTO> dtoList = processExcel(uploadLoc
                    + File.separator + trialDataFileName);
            new BatchCreateProtocols().createProtocols(dtoList, unzipLoc
                    + File.separator);
        } catch (Exception e) {
            log.error("Exception while processing batch");
        }        
        
    }

}
