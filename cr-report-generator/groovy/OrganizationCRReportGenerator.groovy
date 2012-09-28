import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.*
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

/**
 * @author Monish
 *
 */

def props = new Properties()
new File("resolved.build.properties").withInputStream { stream ->
    props.load(stream)
}
println "Using " + props['po.jdbc.url'] + " to connect to PO database"

def poSourceConnection = ReportGenUtils.getPoSourceConnection();
def orginalList
def filteredList

def addrChangeSheetHeaderList = [ "CR ID - Current", "Organization Name - Current", "PO-ID Ð CR", "Street Address - Current", "Delivery Address - Current", "City - Current",
    "State Ð Current", "Zip - Current", "Country - Current", "Email - Current", "Complete Address - Current", "Organization Name - CR",
    "Street Address - CR","Delivery Address - CR","City - CR","State Ð CR","Zip - CR","Country Ð CR","Email Ð CR","Complete Address Ð CR",
    "Change In","CTRO Decision / Apply Change?","CTRO Comments" ];

def addrNoChangeSheetHeaderList = [ "CR ID - Current", "Organization Name - Current", "PO-ID Ð CR", "Street Address - Current", "Delivery Address - Current", "City - Current",
    "State Ð Current", "Zip - Current", "Country - Current", "Email - Current", "Complete Address - Current", "Organization Name - CR",
    "Street Address - CR","Delivery Address - CR","City - CR","State Ð CR","Zip - CR","Country Ð CR","Email Ð CR","Complete Address Ð CR"];

Workbook workbook = new XSSFWorkbook();
CreationHelper createHelper = workbook.getCreationHelper();
Sheet sheet = workbook.createSheet("Valid CRs-To be Reviewed");
Sheet sheet1 = workbook.createSheet("Duplicate CRs");
Sheet sheet2 = workbook.createSheet("Invalid Or Phantom CRs");

Font headerFont = workbook.createFont();
headerFont.setFontHeightInPoints((short)14);
headerFont.setFontName("Courier New");
headerFont.setItalic(true);
headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)

CellStyle headerStyle = workbook.createCellStyle();
headerStyle.setFont(headerFont)
headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);

def rowNum = 0
def rowNum1 = 0
def rowNum2 = 0
def fieldsHavingChanges = ""

Row row = sheet.createRow(rowNum++)
row.setHeight(500.shortValue())
createHeaderRow(row,headerStyle,addrChangeSheetHeaderList)

Row row1 = sheet1.createRow(rowNum1++)
row1.setHeight(500.shortValue())
createHeaderRow(row1,headerStyle,addrNoChangeSheetHeaderList)

Row row2 = sheet2.createRow(rowNum2++)
row2.setHeight(500.shortValue())
createHeaderRow(row2,headerStyle,addrNoChangeSheetHeaderList)

poSourceConnection.eachRow(Queries.orgsWithCRSQL) { orgsWithCR ->

    poSourceConnection.eachRow(Queries.currentOrgDetailsSQL + orgsWithCR.target) { currOrg ->

        currentOrganization = new Organization(currOrg.poid,currOrg.name,
                currOrg.city,currOrg.suite,
                currOrg.zip,currOrg.state,
                currOrg.street,currOrg.country,
                currOrg.email,null);

        orginalList = []
        
        poSourceConnection.eachRow(Queries.crIDsSQL + orgsWithCR.target + """ order by id asc""") { orgCrs ->

            poSourceConnection.eachRow(Queries.crOrgDetailsSQL + orgCrs.id) { crOfcurrOrg ->
                orginalList << new Organization(null,crOfcurrOrg.name,
                        crOfcurrOrg.city,crOfcurrOrg.suite,
                        crOfcurrOrg.zip,crOfcurrOrg.state,
                        crOfcurrOrg.street,crOfcurrOrg.country,
                        crOfcurrOrg.email,crOfcurrOrg.crid)
            }
        }
        filteredList = []
        orginalList.each {
            if (filteredList.contains(it)) {
                row2 = sheet2.createRow(rowNum2++)
                row2.createCell(0.shortValue()).setCellValue(it.getCrId())
                row2.createCell(1.shortValue()).setCellValue(currentOrganization.getName())
                row2.createCell(2.shortValue()).setCellValue(currentOrganization.getOrgPOId())
                row2.createCell(3.shortValue()).setCellValue(currentOrganization.getStreetAddressLine())
                row2.createCell(4.shortValue()).setCellValue(currentOrganization.getDeliveryAddressLine())
                row2.createCell(5.shortValue()).setCellValue(currentOrganization.getCityOrMunicipality())
                row2.createCell(6.shortValue()).setCellValue(currentOrganization.getStateOrProvince())
                row2.createCell(7.shortValue()).setCellValue(currentOrganization.getPostalCode())
                row2.createCell(8.shortValue()).setCellValue(currentOrganization.getCountry())
                row2.createCell(9.shortValue()).setCellValue(currentOrganization.getEmail())
                row2.createCell(10.shortValue()).setCellValue(currentOrganization.getCompleteAddress())
                row2.createCell(11.shortValue()).setCellValue(it.getName())
                row2.createCell(12.shortValue()).setCellValue(it.getStreetAddressLine())
                row2.createCell(13.shortValue()).setCellValue(it.getDeliveryAddressLine())
                row2.createCell(14.shortValue()).setCellValue(it.getCityOrMunicipality())
                row2.createCell(15.shortValue()).setCellValue(it.getStateOrProvince())
                row2.createCell(16.shortValue()).setCellValue(it.getPostalCode())
                row2.createCell(17.shortValue()).setCellValue(it.getCountry())
                row2.createCell(18.shortValue()).setCellValue(it.getEmail())
                row2.createCell(19.shortValue()).setCellValue(it.getCompleteAddress())
            }else{
                filteredList.add(it)
            }
        }
        
        filteredList.each {
            if(currentOrganization.equals(it)){
                row1 = sheet1.createRow(rowNum1++)
                row1.createCell(0.shortValue()).setCellValue(it.getCrId())
                row1.createCell(1.shortValue()).setCellValue(currentOrganization.getName())
                row1.createCell(2.shortValue()).setCellValue(currentOrganization.getOrgPOId())
                row1.createCell(3.shortValue()).setCellValue(currentOrganization.getStreetAddressLine())
                row1.createCell(4.shortValue()).setCellValue(currentOrganization.getDeliveryAddressLine())
                row1.createCell(5.shortValue()).setCellValue(currentOrganization.getCityOrMunicipality())
                row1.createCell(6.shortValue()).setCellValue(currentOrganization.getStateOrProvince())
                row1.createCell(7.shortValue()).setCellValue(currentOrganization.getPostalCode())
                row1.createCell(8.shortValue()).setCellValue(currentOrganization.getCountry())
                row1.createCell(9.shortValue()).setCellValue(currentOrganization.getEmail())
                row1.createCell(10.shortValue()).setCellValue(currentOrganization.getCompleteAddress())
                row1.createCell(11.shortValue()).setCellValue(it.getName())
                row1.createCell(12.shortValue()).setCellValue(it.getStreetAddressLine())
                row1.createCell(13.shortValue()).setCellValue(it.getDeliveryAddressLine())
                row1.createCell(14.shortValue()).setCellValue(it.getCityOrMunicipality())
                row1.createCell(15.shortValue()).setCellValue(it.getStateOrProvince())
                row1.createCell(16.shortValue()).setCellValue(it.getPostalCode())
                row1.createCell(17.shortValue()).setCellValue(it.getCountry())
                row1.createCell(18.shortValue()).setCellValue(it.getEmail())
                row1.createCell(19.shortValue()).setCellValue(it.getCompleteAddress())

            }else {
                
                row = sheet.createRow(rowNum++)
                row.createCell(0.shortValue()).setCellValue(it.getCrId())
                row.createCell(1.shortValue()).setCellValue(currentOrganization.getName())
                row.createCell(2.shortValue()).setCellValue(currentOrganization.getOrgPOId())
                row.createCell(3.shortValue()).setCellValue(currentOrganization.getStreetAddressLine())
                row.createCell(4.shortValue()).setCellValue(currentOrganization.getDeliveryAddressLine())
                row.createCell(5.shortValue()).setCellValue(currentOrganization.getCityOrMunicipality())
                row.createCell(6.shortValue()).setCellValue(currentOrganization.getStateOrProvince())
                row.createCell(7.shortValue()).setCellValue(currentOrganization.getPostalCode())
                row.createCell(8.shortValue()).setCellValue(currentOrganization.getCountry())
                row.createCell(9.shortValue()).setCellValue(currentOrganization.getEmail())
                row.createCell(10.shortValue()).setCellValue(currentOrganization.getCompleteAddress())
                row.createCell(11.shortValue()).setCellValue(it.getName())
                row.createCell(12.shortValue()).setCellValue(it.getStreetAddressLine())
                row.createCell(13.shortValue()).setCellValue(it.getDeliveryAddressLine())
                row.createCell(14.shortValue()).setCellValue(it.getCityOrMunicipality())
                row.createCell(15.shortValue()).setCellValue(it.getStateOrProvince())
                row.createCell(16.shortValue()).setCellValue(it.getPostalCode())
                row.createCell(17.shortValue()).setCellValue(it.getCountry())
                row.createCell(18.shortValue()).setCellValue(it.getEmail())
                row.createCell(19.shortValue()).setCellValue(it.getCompleteAddress())
                
                fieldsHavingChanges = ""
                if (!StringUtils.equals(currentOrganization.getName(), it.getName())){
                    fieldsHavingChanges = fieldsHavingChanges + "Organization Name, ";
                }
                if (!StringUtils.equals(currentOrganization.getStreetAddressLine(), it.getStreetAddressLine())){
                    fieldsHavingChanges = fieldsHavingChanges + "Street Address, ";
                }
                if (!StringUtils.equals(currentOrganization.getDeliveryAddressLine(), it.getDeliveryAddressLine())){
                    fieldsHavingChanges = fieldsHavingChanges + "Delivery Address, ";
                }
                if (!StringUtils.equals(currentOrganization.getCityOrMunicipality(), it.getCityOrMunicipality())){
                    fieldsHavingChanges = fieldsHavingChanges + "City, ";
                }
                if (!StringUtils.equals(currentOrganization.getStateOrProvince(), it.getStateOrProvince())){
                    fieldsHavingChanges = fieldsHavingChanges + "State, ";
                }
                if (!StringUtils.equals(currentOrganization.getPostalCode(), it.getPostalCode())){
                    fieldsHavingChanges = fieldsHavingChanges + "Zip, ";
                }
                if (!StringUtils.equals(currentOrganization.getCountry(), it.getCountry())){
                    fieldsHavingChanges = fieldsHavingChanges + "Country, ";
                }
                if (!StringUtils.equals(currentOrganization.getEmail(), it.getEmail())){
                    fieldsHavingChanges = fieldsHavingChanges + "Email";
                }
                row.createCell(20.shortValue()).setCellValue(fieldsHavingChanges)
                row.createCell(21.shortValue()).setCellValue("")
                row.createCell(22.shortValue()).setCellValue("")
            }
        }
    }
}
FileOutputStream fileOut = new FileOutputStream("temp/Organization-CR-Report-"+ReportGenUtils.getFormatedCurrentDate()+".xlsx");
workbook.write(fileOut);
fileOut.close();

void createHeaderRow(headerRow,style,headerValues) {
    headerValues.eachWithIndex() { obj, i -> 
            headerRow.createCell(i.shortValue()).setCellValue(obj)
            headerRow.getCell(i).setCellStyle(style)
        };
}