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
def poSourceConnection = ReportGenUtils.getPoSourceConnection()

def dupHcfRolesSheetHeaderList = [ "Role ID", "Organization PO-ID", "Organization Name", "Role CTEP ID", "Organization Role Name", "Role Status", "Role Status Date"]
def dupRORolesSheetHeaderList = [ "Role ID", "Organization PO-ID", "Organization Name", "Role CTEP ID", "Research Organization Role Name", "Role Status", "Role Status Date", "Role Research Organization Type","Role Funding Mechanism"]
def dupOCRolesSheetHeaderList = [ "Role ID", "Organization PO-ID", "Organization Name", "Role Status", "Role Status Date", "Role Title","Role Contact Type"]
def dupOCORolesSheetHeaderList = [ "Role ID", "Organization PO-ID", "Organization Name", "Role CTEP ID", "Organization Role Type", "Role Status", "Role Status Date"]

Workbook workbook = new XSSFWorkbook()
CreationHelper createHelper = workbook.getCreationHelper()
Sheet dupHcfRolesSheet = workbook.createSheet("Duplicate Healthcare Facility Roles")
Sheet dupRORolesSheet = workbook.createSheet("Duplicate Research Organization Roles")
Sheet dupOCRolesSheet = workbook.createSheet("Duplicate Organizational Contact Roles")
Sheet dupOCORolesSheet = workbook.createSheet("Duplicate Oversight Committee Roles")

Font headerFont = workbook.createFont()
headerFont.setFontHeightInPoints((short)14)
headerFont.setFontName("Courier New")
headerFont.setItalic(true)
headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD)

CellStyle headerStyle = workbook.createCellStyle()
headerStyle.setFont(headerFont)
headerStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM)
headerStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM)
headerStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM)
headerStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM)

def dupHcfRolesSheetRowNum = 0
Row dupHcfRolesSheetRow = dupHcfRolesSheet.createRow(dupHcfRolesSheetRowNum++)
dupHcfRolesSheetRow.setHeight(500.shortValue())
createHeaderRow(dupHcfRolesSheetRow,headerStyle,dupHcfRolesSheetHeaderList)

def dupRORolesSheetRowNum = 0
Row dupRORolesSheetRow = dupRORolesSheet.createRow(dupRORolesSheetRowNum++)
dupRORolesSheetRow.setHeight(500.shortValue())
createHeaderRow(dupRORolesSheetRow,headerStyle,dupRORolesSheetHeaderList)

def dupOCRolesSheetRowNum = 0
Row dupOCRolesSheetRow = dupOCRolesSheet.createRow(dupOCRolesSheetRowNum++)
dupOCRolesSheetRow.setHeight(500.shortValue())
createHeaderRow(dupOCRolesSheetRow,headerStyle,dupOCRolesSheetHeaderList)

def dupOCORolesSheetRowNum = 0
Row dupOCORolesSheetRow = dupOCORolesSheet.createRow(dupOCORolesSheetRowNum++)
dupOCORolesSheetRow.setHeight(500.shortValue())
createHeaderRow(dupOCORolesSheetRow,headerStyle,dupOCORolesSheetHeaderList)

//Healthcare Facility
poSourceConnection.eachRow(Queries.dupHcfRoleSQL) { dupHcfRoles ->
    poSourceConnection.eachRow("""select id from healthcarefacility where status <> 'NULLIFIED' and player_id = """ + dupHcfRoles.player_id ) { dupHcfIds ->
        poSourceConnection.eachRow(Queries.dupHcfRoleDetailSQL,[dupHcfIds.id]) { dupHcfRoleDetails -> 
            dupHcfRolesSheetRow = dupHcfRolesSheet.createRow(dupHcfRolesSheetRowNum++)
            dupHcfRolesSheetRow.createCell(0.shortValue()).setCellValue(dupHcfRoleDetails.role_id)
            dupHcfRolesSheetRow.createCell(1.shortValue()).setCellValue(dupHcfRoleDetails.org_poid)
            dupHcfRolesSheetRow.createCell(2.shortValue()).setCellValue(dupHcfRoleDetails.orgname)
            dupHcfRolesSheetRow.createCell(3.shortValue()).setCellValue(dupHcfRoleDetails.role_ctepid)
            dupHcfRolesSheetRow.createCell(4.shortValue()).setCellValue(dupHcfRoleDetails.org_role_name)
            dupHcfRolesSheetRow.createCell(5.shortValue()).setCellValue(dupHcfRoleDetails.role_status)
            dupHcfRolesSheetRow.createCell(6.shortValue()).setCellValue(dupHcfRoleDetails.role_statusdate)
        }    
    }
}

//Research Organization
poSourceConnection.eachRow(Queries.dupRORoleSQL) { dupRORoles ->
    poSourceConnection.eachRow("""select id from researchorganization where status <> 'NULLIFIED' and player_id = """ + dupRORoles.player_id ) { dupROIds ->
        poSourceConnection.eachRow(Queries.dupRORoleDetailSQL,[dupROIds.id]) { dupRORoleDetails ->
            dupRORolesSheetRow = dupRORolesSheet.createRow(dupRORolesSheetRowNum++)
            dupRORolesSheetRow.createCell(0.shortValue()).setCellValue(dupRORoleDetails.role_id)
            dupRORolesSheetRow.createCell(1.shortValue()).setCellValue(dupRORoleDetails.org_poid)
            dupRORolesSheetRow.createCell(2.shortValue()).setCellValue(dupRORoleDetails.orgname)
            dupRORolesSheetRow.createCell(3.shortValue()).setCellValue(dupRORoleDetails.role_ctepid)
            dupRORolesSheetRow.createCell(4.shortValue()).setCellValue(dupRORoleDetails.org_role_name)
            dupRORolesSheetRow.createCell(5.shortValue()).setCellValue(dupRORoleDetails.role_status)
            dupRORolesSheetRow.createCell(6.shortValue()).setCellValue(dupRORoleDetails.role_statusdate)
            dupRORolesSheetRow.createCell(7.shortValue()).setCellValue(dupRORoleDetails.ro_type)
            dupRORolesSheetRow.createCell(8.shortValue()).setCellValue(dupRORoleDetails.ro_fundingmechanism)
        }
    }
}

//Organizational  Contact
poSourceConnection.eachRow(Queries.dupOCRoleSQL) { dupOCRoles ->
    poSourceConnection.eachRow("""select id from organizationalcontact where status <> 'NULLIFIED' and organization_id = """ + dupOCRoles.organization_id ) { dupOCIds ->
        poSourceConnection.eachRow(Queries.dupOCRoleDetailSQL,[dupOCIds.id]) { dupOCRoleDetails ->
            dupOCRolesSheetRow = dupOCRolesSheet.createRow(dupOCRolesSheetRowNum++)
            dupOCRolesSheetRow.createCell(0.shortValue()).setCellValue(dupOCRoleDetails.role_id)
            dupOCRolesSheetRow.createCell(1.shortValue()).setCellValue(dupOCRoleDetails.org_poid)
            dupOCRolesSheetRow.createCell(2.shortValue()).setCellValue(dupOCRoleDetails.orgname)
            dupOCRolesSheetRow.createCell(3.shortValue()).setCellValue(dupOCRoleDetails.role_status)
            dupOCRolesSheetRow.createCell(4.shortValue()).setCellValue(dupOCRoleDetails.role_statusdate)
            dupOCRolesSheetRow.createCell(5.shortValue()).setCellValue(dupOCRoleDetails.org_role_title)
            dupOCRolesSheetRow.createCell(6.shortValue()).setCellValue(dupOCRoleDetails.oct_type)
        }
    }
}


//Oversight Committee
poSourceConnection.eachRow(Queries.dupOCORoleSQL) { dupOCORoles ->
    poSourceConnection.eachRow("""select id from oversightcommittee where status <> 'NULLIFIED' and player_id = """ + dupOCORoles.player_id ) { dupOCOIds ->
        poSourceConnection.eachRow(Queries.dupOCORoleDetailSQL,[dupOCOIds.id]) { dupOCORoleDetails ->
            dupOCORolesSheetRow = dupOCORolesSheet.createRow(dupOCORolesSheetRowNum++)
            dupOCORolesSheetRow.createCell(0.shortValue()).setCellValue(dupOCORoleDetails.role_id)
            dupOCORolesSheetRow.createCell(1.shortValue()).setCellValue(dupOCORoleDetails.org_poid)
            dupOCORolesSheetRow.createCell(2.shortValue()).setCellValue(dupOCORoleDetails.orgname)
            dupOCORolesSheetRow.createCell(3.shortValue()).setCellValue(dupOCORoleDetails.role_ctepid)
            dupOCORolesSheetRow.createCell(4.shortValue()).setCellValue(dupOCORoleDetails.role_status)
            dupOCORolesSheetRow.createCell(5.shortValue()).setCellValue(dupOCORoleDetails.role_statusdate)
            dupOCORolesSheetRow.createCell(6.shortValue()).setCellValue(dupOCORoleDetails.oco_type)
        }
    }
}

FileOutputStream fileOut = new FileOutputStream("temp/Organizations-Role-Report-"+ReportGenUtils.getFormatedCurrentDate()+".xlsx");
workbook.write(fileOut);
fileOut.close();

void createHeaderRow(headerRow,style,headerValues) {
    headerValues.eachWithIndex() { obj, i ->
            headerRow.createCell(i.shortValue()).setCellValue(obj)
            headerRow.getCell(i).setCellStyle(style)
        };
}