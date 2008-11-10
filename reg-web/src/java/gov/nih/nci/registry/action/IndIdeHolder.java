package gov.nih.nci.registry.action;

import java.io.Serializable;

/**
 * 
 * @author Harsha
 *
 */
public class IndIdeHolder implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String indIde;
    private String number;
    private String grantor;
    private String holderType;
    private String programCode;
    private String expandedAccess;
    private String expandedAccessType;
    private String rowId;

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
     * @return the number
     */
    public String getNumber() {
        return number;
    }
    /**
     * @param number the number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }
    /**
     * @return the grantor
     */
    public String getGrantor() {
        return grantor;
    }
    /**
     * @param grantor the grantor to set
     */
    public void setGrantor(String grantor) {
        this.grantor = grantor;
    }
    /**
     * @return the holderType
     */
    public String getHolderType() {
        return holderType;
    }
    /**
     * @param holderType the holderType to set
     */
    public void setHolderType(String holderType) {
        this.holderType = holderType;
    }
    /**
     * @return the programCode
     */
    public String getProgramCode() {
        return programCode;
    }
    /**
     * @param programCode the programCode to set
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    /**
     * @return the expandedAccess
     */
    public String getExpandedAccess() {
        return expandedAccess;
    }
    /**
     * @param expandedAccess the expandedAccess to set
     */
    public void setExpandedAccess(String expandedAccess) {
        this.expandedAccess = expandedAccess;
    }
    /**
     * @return the expandedAccessType
     */
    public String getExpandedAccessType() {
        return expandedAccessType;
    }
    /**
     * @param expandedAccessType the expandedAccessType to set
     */
    public void setExpandedAccessType(String expandedAccessType) {
        this.expandedAccessType = expandedAccessType;
    }
    /**
     * @return the indIde
     */
    public String getIndIde() {
        return indIde;
    }
    /**
     * @param indIde the indIde to set
     */
    public void setIndIde(String indIde) {
        this.indIde = indIde;
    }
    
}
