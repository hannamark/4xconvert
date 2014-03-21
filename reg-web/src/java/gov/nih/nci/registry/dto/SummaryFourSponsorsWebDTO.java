package gov.nih.nci.registry.dto;


/**
 * @author Kalpana Guthikonda
 *
 */
public class SummaryFourSponsorsWebDTO {
    
    private String rowId;
    private String orgId;
    private String orgName;
    
    /**
     * @param rowId the UUID
     * @param orgId the orgId
     * @param orgName the orgName
     */
    public SummaryFourSponsorsWebDTO(String rowId, String orgId, String orgName) {
        this.rowId = rowId;
        this.orgId = orgId;
        this.orgName = orgName;
    }
    
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
   
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        return result;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
      boolean result = false;
      if (object == null || object.getClass() != getClass()) {
        result = false;
      } else {
          SummaryFourSponsorsWebDTO dto = (SummaryFourSponsorsWebDTO) object;
          if (this.orgId.equals(dto.getOrgId())) {
               result = true;
          }
      }
      return result;
    }

}
