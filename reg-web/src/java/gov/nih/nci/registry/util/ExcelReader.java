/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
     * @throws PAException
     *             PAException
     * @return List
     */
    public List<StudyProtocolBatchDTO> convertToDTOFromExcelWrokbook(
            HSSFWorkbook wb) throws PAException {
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
            // loop every row in the work sheet
            boolean flag = true;
            for (Iterator rows = sheet.rowIterator(); rows.hasNext();) {
                HSSFRow row = (HSSFRow) rows.next();
                if (flag) {
                    flag = false; // skip the first row, since its a header row
                    continue;
                }
                short c1 = row.getFirstCellNum();
                short c2 = row.getLastCellNum();
                batchDto = new StudyProtocolBatchDTO();
                for (int c = c1; c < c2; c++) { 
                    // loop for every cell in each row
                    HSSFCell cell = row.getCell(c);
                    String cellValue = null;
                    if (cell != null) {
                        cellValue = getCellValue(cell);
                        setDto(batchDto, cellValue, c); // set corresponding values
                    } // if
                } // column for loop
                batchDtoList.add(batchDto); // add the dto to the list
            } // rows for loop
        } // work sheet for loop
        LOG.info("Leaving convertToProtocolFromExcelWrokbook");
        return batchDtoList;
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
    @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NcssMethodCount" })
     private StudyProtocolBatchDTO setDto(StudyProtocolBatchDTO batchDto,
            String cellValue, int col) throws PAException {
        switch (col) {
        case BatchConstants.UNIQUE_TRIAL_IDENTIFIER: batchDto.setUniqueTrialId(cellValue); break;
        case BatchConstants.LEAD_ORGANIZATION_TRIAL_IDENTIFIER: batchDto.setLocalProtocolIdentifier(cellValue); break;
        case BatchConstants.NCT_NUMBER: batchDto.setNctNumber(cellValue); break;
        case BatchConstants.TITLE: batchDto.setTitle(cellValue); break;
        case BatchConstants.TRIAL_TYPE: batchDto.setTrialType(cellValue); break;
        case BatchConstants.PRIMARY_PURPOSE: batchDto.setPrimaryPurpose(cellValue); break;
        case BatchConstants.PRIMARY_PURPOSE_OTHER_VALUE_SP:batchDto.setPrimaryPurposeOtherValueSp(cellValue); break;
        case BatchConstants.PHASE: batchDto.setPhase(cellValue); break;
        case BatchConstants.PHASE_OTHER_VALUE_SP:batchDto.setPhaseOtherValueSp(cellValue); break;
        case BatchConstants.SPONSOR_ORG_NAME: batchDto.setSponsorOrgName(cellValue); break;
        case BatchConstants.SPONSOR_CETP_ORG_NO: batchDto.setSponsorCTEPOrgNumber(cellValue); break;
        case BatchConstants.SPONSOR_STREET_ADDRESS: batchDto.setSponsorStreetAddress(cellValue); break;
        case BatchConstants.SPONSOR_CITY: batchDto.setSponsorCity(cellValue); break;
        case BatchConstants.SPONSOR_STATE : batchDto.setSponsorState(cellValue); break;
        case BatchConstants.SPONSOR_ZIP : batchDto.setSponsorZip(cellValue); break;
        case BatchConstants.SPONSOR_COUNTRY: batchDto.setSponsorCountry(cellValue); break;
        case BatchConstants.SPONSOR_EMAIL: batchDto.setSponsorEmail(cellValue); break;
        case BatchConstants.SPONSOR_PHONE: batchDto.setSponsorPhone(cellValue); break;
        case BatchConstants.SPONSOR_TTY: batchDto.setSponsorTTY(cellValue); break;
        case BatchConstants.SPONSOR_FAX: batchDto.setSponsorFax(cellValue); break;
        case BatchConstants.SPONSOR_URL: batchDto.setSponsorURL(cellValue); break;    
        case BatchConstants.RESPONSIBLE_PARTY: batchDto.setResponsibleParty(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_FIRST_NAME : batchDto.setSponsorContactFName(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_MIDDLE_NAME: batchDto.setSponsorContactMName(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_LAST_NAME: batchDto.setSponsorContactLName(cellValue); break;    
        case BatchConstants.SPONSOR_CONTACT_CTEP_PERSON_NO: batchDto.setSponsorContactCTEPPerNo(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_STREET_ADDRESS: batchDto.setSponsorContactStreetAddress(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_CITY: batchDto.setSponsorContactCity(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_STATE: batchDto.setSponsorContactState(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_ZIP: batchDto.setSponsorContactZip(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_COUNTRY: batchDto.setSponsorContactCountry(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_EMAIL_ID: batchDto.setSponsorContactEmail(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_PHONE: batchDto.setSponsorContactPhone(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_TTY: batchDto.setSponsorContactTTY(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_FAX: batchDto.setSponsorContactFax(cellValue); break;
        case BatchConstants.SPONSOR_CONTACT_URL: batchDto.setSponsorContactUrl(cellValue); break;    
        case BatchConstants.LEAD_ORG_NAME: batchDto.setLeadOrgName(cellValue); break;
        case BatchConstants.LEAD_ORG_CTEP_IDENTIFIER: batchDto.setLeadOrgCTEPOrgNo(cellValue); break;
        case BatchConstants.LEAD_ORG_STREET_ADDRESS: batchDto.setLeadOrgStreetAddress(cellValue); break;
        case BatchConstants.LEAD_ORG_CITY: batchDto.setLeadOrgCity(cellValue); break;
        case BatchConstants.LEAD_ORG_STATE: batchDto.setLeadOrgState(cellValue); break;
        case BatchConstants.LEAD_ORG_ZIP: batchDto.setLeadOrgZip(cellValue); break;
        case BatchConstants.LEAD_ORG_COUNTRY: batchDto.setLeadOrgCountry(cellValue); break;
        case BatchConstants.LEAD_ORG_EMAIL: batchDto.setLeadOrgEmail(cellValue); break;
        case BatchConstants.LEAD_ORG_PHONE: batchDto.setLeadOrgPhone(cellValue); break;
        case BatchConstants.LEAD_ORG_TTY: batchDto.setLeadOrgTTY(cellValue); break;
        case BatchConstants.LEAD_ORG_FAX: batchDto.setLeadOrgFax(cellValue); break;
        case BatchConstants.LEAD_ORG_URL: batchDto.setLeadOrgUrl(cellValue); break;    
        case BatchConstants.LEAD_ORG_TYPE: batchDto.setLeadOrgType(cellValue); break;
        case BatchConstants.PI_FIRST_NAME: batchDto.setPiFirstName(cellValue); break;
        case BatchConstants.PI_MIDDLE_NAME: batchDto.setPiMiddleName(cellValue); break;
        case BatchConstants.PI_LAST_NAME: batchDto.setPiLastName(cellValue); break;
        case BatchConstants.PI_PERSON_CTEP_PERSON_NUMBER: batchDto.setPiPersonCTEPPersonNo(cellValue); break;
        case BatchConstants.PI_STREET_ADDRESS: batchDto.setPiStreetAddress(cellValue); break;
        case BatchConstants.PI_CITY: batchDto.setPiCity(cellValue); break;
        case BatchConstants.PI_STATE: batchDto.setPiState(cellValue); break;
        case BatchConstants.PI_ZIP: batchDto.setPiZip(cellValue); break;
        case BatchConstants.PI_COUNTRY: batchDto.setPiCountry(cellValue); break;
        case BatchConstants.PI_EMAIL:batchDto.setPiEmail(cellValue); break;
        case BatchConstants.PI_PHONE: batchDto.setPiPhone(cellValue); break;
        case BatchConstants.PI_TTY: batchDto.setPiTTY(cellValue); break;
        case BatchConstants.PI_FAX: batchDto.setPiFax(cellValue); break;
        case BatchConstants.PI_URL: batchDto.setPiUrl(cellValue); break;
        case BatchConstants.S4_FUND_CAT: batchDto.setSumm4FundingCat(cellValue); break;
        case BatchConstants.S4_FUND_ORG_NAME: batchDto.setSumm4OrgName(cellValue); break;
        case BatchConstants.S4_FUND_ORG_CTEP_ORG_NO: batchDto.setSumm4OrgCTEPOrgNo(cellValue); break;
        case BatchConstants.S4_FUND_ORG_STREET_ADDRESS: batchDto.setSumm4OrgStreetAddress(cellValue); break;
        case BatchConstants.S4_FUND_CITY: batchDto.setSumm4City(cellValue); break;
        case BatchConstants.S4_FUND_STATE: batchDto.setSumm4State(cellValue); break;
        case BatchConstants.S4_FUND_ZIP: batchDto.setSumm4Zip(cellValue);  break;
        case BatchConstants.S4_FUND_COUNTRY: batchDto.setSumm4Country(cellValue); break;
        case BatchConstants.S4_FUND_EMAIL: batchDto.setSumm4Email(cellValue); break;
        case BatchConstants.S4_FUND_PHONE: batchDto.setSumm4Phone(cellValue); break;
        case BatchConstants.S4_FUND_TTY: batchDto.setSumm4TTY(cellValue); break;
        case BatchConstants.S4_FUND_FAX: batchDto.setSumm4Fax(cellValue); break;
        case BatchConstants.S4_FUND_URL: batchDto.setSumm4Url(cellValue); break;
        case BatchConstants.S4_PRG_CODE_TEXT: batchDto.setProgramCodeText(cellValue); break;
        case BatchConstants.NIH_GRANT_FUND_MC: batchDto.setNihGrantFundingMechanism(cellValue); break;
        case BatchConstants.NIH_GRANT_INSTITUTE_CODE: batchDto.setNihGrantInstituteCode(cellValue); break;
        case BatchConstants.NIH_GRANT_SR_NO: batchDto.setNihGrantSrNumber(cellValue); break;
        case BatchConstants.NIH_GRANT_NCI_DIV_CODE: batchDto.setNihGrantNCIDivisionCode(cellValue); break;
        case BatchConstants.CURRENT_TRIAL_STATUS: batchDto.setCurrentTrialStatus(cellValue); break;
        case BatchConstants.REASON_FOR_STUDY_STOPPED:batchDto.setReasonForStudyStopped(cellValue); break;
        case BatchConstants.CURRENT_TRIAL_STATUS_DATE: batchDto.setCurrentTrialStatusDate(cellValue); break;
        case BatchConstants.STUDY_START_DATE: batchDto.setStudyStartDate(cellValue); break;
        case BatchConstants.STUDY_START_DATE_TYPE: batchDto.setStudyStartDateType(cellValue); break;
        case BatchConstants.PRIMARY_COMP_DATE: batchDto.setPrimaryCompletionDate(cellValue); break;
        case BatchConstants.PRIMARY_COMP_DATE_TYPE: batchDto.setPrimaryCompletionDateType(cellValue); break;
        case BatchConstants.IND_TYPE: batchDto.setIndType(cellValue); break;
        case BatchConstants.IND_NUMBER: batchDto.setIndNumber(cellValue); break;
        case BatchConstants.IND_GRANTOR: batchDto.setIndGrantor(cellValue); break;
        case BatchConstants.IND_HOLDER_TYPE: batchDto.setIndHolderType(cellValue); break;
        case BatchConstants.IND_NIH_INSTITUTION: batchDto.setIndNIHInstitution(cellValue); break;
        case BatchConstants.IND_NCI_DIV_CODE: batchDto.setIndNCIDivision(cellValue); break;
        case BatchConstants.IND_HAS_EXPANDED_ACCESS: if (cellValue != null && cellValue.equalsIgnoreCase("Yes")) {
                batchDto.setIndHasExpandedAccess("True"); break;
             } else {
                 batchDto.setIndHasExpandedAccess(cellValue); break;
             }            
        case BatchConstants.IND_EXPANED_ACCESS_STATUS: batchDto.setIndExpandedAccessStatus(cellValue); break;
        case BatchConstants.PROTOCOL_DOC_FILE_NAME: batchDto.setProtcolDocumentFileName(cellValue);
        case BatchConstants.IRB_APPROVAL_DOC_FILE_NAME: batchDto.setIrbApprovalDocumentFileName(cellValue); break;
        case BatchConstants.PARTICIPATIING_SITE_DOC_FILE_NAME: 
            batchDto.setParticipatinSiteDocumentFileName(cellValue); break;
        case BatchConstants.INFORMED_CONSENT_DOC_FILE_NAME: 
            batchDto.setInformedConsentDocumentFileName(cellValue); break;
        case BatchConstants.OTHER_TRIAL_DOC_FILE_NAME: batchDto.setOtherTrialRelDocumentFileName(cellValue); break;
        case BatchConstants.SUBMISSION_TYPE: batchDto.setSubmissionType(cellValue); break;
        case BatchConstants.NCI_TRIAL_IDENTIFIER: batchDto.setNciTrialIdentifier(cellValue); break;
        case BatchConstants.AMENDMENT_NUMBER: batchDto.setAmendmentNumber(cellValue); break;
        case BatchConstants.AMENDMENT_DATE:batchDto.setAmendmentDate(cellValue); break;
        case BatchConstants.PROTOCOL_HIGHLIGHTED_DOC_FILE_NAME: batchDto.setProtocolHighlightDocFileName(cellValue); 
            break;
        case BatchConstants.CHANGE_MEMO_DOC_FILE_NAME:batchDto.setChangeRequestDocFileName(cellValue); break;
        default: throw new PAException(" Unknown coloumn #" + col + "to map ");
        }
        return batchDto;
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
