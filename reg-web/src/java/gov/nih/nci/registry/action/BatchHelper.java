package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.util.BatchConstants;
import gov.nih.nci.registry.util.ExcelReader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Enumeration;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * 
 * @author Vrushali
 * 
 * 
 */
@SuppressWarnings("PMD")
public class BatchHelper {

    private static Logger log = Logger.getLogger(BatchHelper.class);

   /**
    * 
    * @param zipname name
    * @return str
    * @throws PAException ex
    */

    public String unZipDoc(String zipname) throws PAException {
        String folderLocation = null;
        try {
            ZipFile zipFile = new ZipFile(zipname);
            Enumeration enumeration = zipFile.entries();
            String unzipFolderName = zipFile.getName().substring(0,
                    zipname.indexOf(".zip"));
            folderLocation = unzipFolderName;
            File f = new File(unzipFolderName);
            if (!f.exists()) {
                log.debug("BatchHelper:unZipDoc not exists.. so creating new");
                // create a new directory
                boolean md = f.mkdir();
                if (md) {
                    while (enumeration.hasMoreElements()) {
                        ZipEntry zipEntry = (ZipEntry) enumeration
                                .nextElement();
                        log.debug("Unzipping: " + zipEntry.getName());

                        BufferedInputStream bis = new BufferedInputStream(
                                zipFile.getInputStream(zipEntry));

                        int size;
                        byte[] buffer = new byte[BatchConstants.READ_SIZE];
                        FileOutputStream fos = new FileOutputStream(
                                unzipFolderName + File.separator
                                        + zipEntry.getName());
                        BufferedOutputStream bos = new BufferedOutputStream(
                                fos, buffer.length);

                        while ((size = bis.read(buffer, 0, buffer.length)) != -1) {
                            bos.write(buffer, 0, size);
                        }

                        bos.flush();
                        bos.close();
                        fos.close();

                        bis.close();
                    }
                }
            }
        } catch (IOException e) {
            log.error("BatchHelper:unZipDoc-" + e);
            throw new PAException("Unable to Unzip the document");
        }
        return folderLocation;
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

}
