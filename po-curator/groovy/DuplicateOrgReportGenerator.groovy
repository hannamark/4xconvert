import org.apache.poi.hssf.usermodel.HSSFCellStyle
import org.apache.poi.hssf.usermodel.HSSFFont
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
def orgsMap = ReportGenUtils.getOrgsMap()
println "got Orgs " + orgsMap.size()

def duplicateOrgsHeaderList = [ "Organization Name", "Organization PO-ID", "Organization CTEP-ID", "Organization Status", "Role"];
def duplicateOrgStatsHeaderList = [ "Organization Name", "Occurrences"];

Workbook workbook = new XSSFWorkbook();
CreationHelper createHelper = workbook.getCreationHelper();
Sheet sheet = workbook.createSheet("Duplicate Organizations");
Sheet sheet1 = workbook.createSheet("Duplicate Organization Stats");

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
Row row = sheet.createRow(rowNum++)
row.setHeight(500.shortValue())
createHeaderRow(row,headerStyle,duplicateOrgsHeaderList)

Row row1 = sheet1.createRow(rowNum1++)
row1.setHeight(500.shortValue())
createHeaderRow(row1,headerStyle,duplicateOrgStatsHeaderList)

poSourceConnection.eachRow(Queries.duplicateOrgsSQL) { dupOrgs ->
    
    row1 = sheet1.createRow(rowNum1++)
    row1.createCell(0.shortValue()).setCellValue(dupOrgs.name)
    row1.createCell(1.shortValue()).setCellValue(dupOrgs.numOccurrences)
    
    poSourceConnection.eachRow(Queries.duplicateOrgIdsSQL,[dupOrgs.name]) { dupOrgIds ->
        
        def orgRoles = "";
        
        poSourceConnection.eachRow("""select count(*) as count from researchorganization where player_id = """ + dupOrgIds.id) { ro -> 
            if (ro.count > 0){
                orgRoles = orgRoles + "Research Organization,";
            }
        }
        poSourceConnection.eachRow("""select count(*) as count from healthcarefacility where player_id = """ + dupOrgIds.id) { hcf ->
            if (hcf.count > 0){
                orgRoles = orgRoles + "Healthcare Facility";
            }
        }
        
        def orgDetail = orgsMap.get(dupOrgIds.id.toLong())
        row = sheet.createRow(rowNum++)
        row.createCell(0.shortValue()).setCellValue(orgDetail.name)
        row.createCell(1.shortValue()).setCellValue(orgDetail.poid)
        row.createCell(2.shortValue()).setCellValue(orgDetail.ctepid)
        row.createCell(3.shortValue()).setCellValue(orgDetail.status)
        row.createCell(4.shortValue()).setCellValue(orgRoles)
    }
}
FileOutputStream fileOut = new FileOutputStream("temp/Duplicate-Organizations-Report-"+ReportGenUtils.getFormatedCurrentDate()+".xlsx");
workbook.write(fileOut);
fileOut.close();

void createHeaderRow(headerRow,style,headerValues) {
    headerValues.eachWithIndex() { obj, i ->
            headerRow.createCell(i.shortValue()).setCellValue(obj)
            headerRow.getCell(i).setCellStyle(style)
        };
}