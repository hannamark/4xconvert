package gov.nih.nci.pa.dto;

/**
 * @author Kalpana Guthikonda
 *
 */
public class SummaryFourSponsorsWebDTO {
    
    private String rowId;
    private String orgId;
    private String orgName;
    
    /**
     * @return the rowId
     */
    public String getRowId() {
        return rowId;
    }
    /**
     * @param rowId the rowId to set
     */
    public void setRowId(String rowId) {
        this.rowId = rowId;
    }
    /**
     * @return the orgId
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    /**
     * @return the orgName
     */
    public String getOrgName() {
        return orgName;
    }
    /**
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

}
