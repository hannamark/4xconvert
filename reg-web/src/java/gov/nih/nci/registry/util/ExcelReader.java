/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.enums.BatchStringConstants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @author Vrushali
 * 
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class ExcelReader {
    private static final Logger LOG = Logger.getLogger(ExcelReader.class);

    /**
     * 
     * @param wb
     *            wb
     *@param orgName orgName
     * @throws PAException
     *             PAException
     * @return List
     */
    public List<StudyProtocolBatchDTO> convertToDTOFromExcelWorkbook(
            HSSFWorkbook wb, String orgName) throws PAException {
        List<StudyProtocolBatchDTO> batchDtoList = new ArrayList<StudyProtocolBatchDTO>();
        StudyProtocolBatchDTO batchDto = null;
        if (wb == null) {
            throw new PAException(" HSSFWorkbook cannot be null ");
        }
        int numOfSheets = wb.getNumberOfSheets();
        if (numOfSheets == 0) {
            LOG.error("There are no workbook to process");
            throw new PAException(" There are no workbook to process");
        }
        for (int i = 0; i < numOfSheets; i++) {
            if (i > 0) {
                break; // loop for every work sheet in the workbook but process only the first sheet
            }
            HSSFSheet sheet = wb.getSheetAt(i);
            if (sheet == null) {
                LOG.error("There are no work sheets to process");
                throw new PAException(" There are no work sheets to process");
            }
            //create a dynamic hashmap of the headers items
            Map<Integer, String> headerMap = new HashMap<Integer, String>();
            getHeaders(sheet, headerMap);
            
            boolean flag = true; // loop every row in the work sheet
            for (Iterator rows = sheet.rowIterator(); rows.hasNext();) {
                HSSFRow row = (HSSFRow) rows.next();
                if (flag) {
                    flag = false; // skip the first row, since its a header row
                    continue;
                }
                 batchDto = new StudyProtocolBatchDTO();
                //create the dto
                createDto(row, batchDto, orgName, headerMap);
                //add the dto to the list
                batchDtoList.add(batchDto); 
            } // rows for loop
        } // work sheet for loop
        return batchDtoList;
    }
    
    private void getHeaders(HSSFSheet sheet, Map<Integer, String> headerMap) {
       if (sheet.getLastRowNum() > 0) {
            HSSFRow headerRow = sheet.getRow(0);
            short c1 = headerRow.getFirstCellNum();
            short c2 = headerRow.getLastCellNum();
            
            //populate hashmap of the headers items dynamically
             for (int c = c1; c < c2; c++) {
                HSSFCell cell = headerRow.getCell(c); // loop for every cell in each row
                String cellValue = null;
                if (cell != null) {
                    cellValue = getCellValue(cell);
                    headerMap.put(c, cellValue);
                }   
             }
        }
    }
    
    private void createDto(HSSFRow row, StudyProtocolBatchDTO batchDto, String orgName, Map<Integer, String> headerMap)
    throws PAException {
        short c1 = row.getFirstCellNum();
        short c2 = row.getLastCellNum();
        for (int c = c1; c < c2; c++) { 
            HSSFCell cell = row.getCell(c); // loop for every cell in each row
            String cellValue = null;
            if (cell != null) {
                cellValue = getCellValue(cell);
                setDto(batchDto, cellValue, c, headerMap); // set corresponding values
                if (!StringUtils.isEmpty(orgName) && orgName.equalsIgnoreCase("ctep")) {
                   batchDto.setCtepIdentifier(batchDto.getUniqueTrialId());
                }
                if (!StringUtils.isEmpty(orgName) && orgName.equalsIgnoreCase("dcp")) {
                    batchDto.setDcpIdentifier(batchDto.getUniqueTrialId());
                }
            } // if
        } // column for loop
    }
    /**
     * 
     * @param cell
     * @return
     */
    private String getCellValue(HSSFCell cell) {
        String result = null;
        int cellType = cell.getCellType();
        switch (cellType) {
        case HSSFCell.CELL_TYPE_BLANK:
            result = null;
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                result = convertDateToString(cell.getDateCellValue(), "MM/dd/yyyy");
            } else {
                result = String.valueOf(cell.getNumericCellValue());
                result = result.substring(0, result.indexOf('.'));
                }
            break;
        case HSSFCell.CELL_TYPE_STRING:
            result = cell.getRichStringCellValue().getString();
            if (null != result) {
                result =  result.trim();
            }
            break;
        default:
            break;
        }

        return result;
    }

    /**
     * 
     * @param is is
     * @return wb
     * @throws PAException exception
     * @throws IOException i
     */
    public HSSFWorkbook parseExcel(InputStream is) throws PAException, IOException {
        LOG.info("Entereing parseExcel");
        HSSFWorkbook wb = null;
        if (is == null) {
            throw new PAException(" Input stream cannot be null ");
        }
        POIFSFileSystem fs = new POIFSFileSystem(is);
        wb = new HSSFWorkbook(fs);
        if (wb == null) {
            throw new PAException(" Error reading the workbook ");
        }
        return wb;
    }

    /**
     * 
     * @param cellValue
     *            cellValue
     * @param col
     *            col
     * @throws PAException
     * @return dto dto
     */
     private StudyProtocolBatchDTO setDto(StudyProtocolBatchDTO batchDto,
        String cellValue, int col, Map<Integer, String> headerMap) throws PAException {
        String colHeader = headerMap.get(col);
        if (colHeader == null || BatchStringConstants.getByCode(colHeader) == null) {
            throw new PAException(" Unknown coloumn name " + colHeader + ". Please check the specification");
        }
        switch (BatchStringConstants.getByCode(colHeader)) {
        case IND_HAS_EXPANDED_ACCESS: if (cellValue != null && cellValue.equalsIgnoreCase("Yes")) {
                batchDto.setIndHasExpandedAccess("True"); 
             } else {
                 batchDto.setIndHasExpandedAccess(cellValue); 
             } 
             break;           
        case CTGOV_XML_INDICATOR: if (cellValue != null && cellValue.equalsIgnoreCase("No")) {
              batchDto.setCtGovXmlIndicator(false);
              } else {
               batchDto.setCtGovXmlIndicator(true); 
              } 
              break;
        case OTHER_TRIAL_IDENTIFIER: break; //to do
        default: setDtoAttributes(colHeader, cellValue, batchDto);
        }
        return batchDto;
    }

    
    /**
     * Sets the dto attributes.
     * 
     * @param colHeader the col header
     * @param cellValue the cell value
     * 
     * @throws PAException the PA exception
     */
    private void setDtoAttributes(String colHeader, String cellValue, StudyProtocolBatchDTO batchDto) 
    throws PAException {
      try {
            Class clazz = Class.forName("gov.nih.nci.registry.dto.StudyProtocolBatchDTO");
            Class[] paramTypes = new Class[] {String.class };
            Method meth = clazz
                           .getDeclaredMethod(BatchStringConstants.getByCode(colHeader).getMethodName(), paramTypes);
            meth.invoke(batchDto, cellValue);
          } catch (Exception e) {
              throw new PAException(" Error setting the cell value to Dto" + e);
          } 

    }
    /**
     * 
     * @param date
     *            date
     * @param format
     *            format
     * @return Str
     */
    public static String convertDateToString(Date date, String format) {
        SimpleDateFormat dateFormatter = null;
        String formattedDate = null;

        if (date != null && format != null) {
            dateFormatter = new SimpleDateFormat(format, Locale.US);
            formattedDate = dateFormatter.format(date);
        }
        return formattedDate;

    }
}
