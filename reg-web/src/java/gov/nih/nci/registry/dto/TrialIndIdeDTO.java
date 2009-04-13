/**
 * 
 */
package gov.nih.nci.registry.dto;

import java.util.UUID;

import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Vrushali
 *
 */
public class TrialIndIdeDTO {
    private String indIdeId;
    private String expandedAccess;
    private String expandedAccessType; //expandedAccessIndicator
    private String grantor;
    private String holderType; //holderTypeCode
    private String programCode;
    private String number; //indIdeNumber
    private String indIde; //IndIdeTypeCode
    
    
    private String rowId;
    /**
     * 
     * @param isoDto dto
     */
    public TrialIndIdeDTO(StudyIndldeDTO isoDto) {
        super();
        this.indIdeId = IiConverter.convertToString(isoDto.getIdentifier());
        this.expandedAccess = CdConverter.convertCdToString(isoDto.getExpandedAccessStatusCode());
        if (isoDto.getExpandedAccessIndicator().getValue() != null) {
            if (isoDto.getExpandedAccessIndicator().getValue().toString().equalsIgnoreCase("true")) {
              this.expandedAccessType = "Yes";
            } else {
              this.expandedAccessType = "No";
            } 
          }
        this.grantor = CdConverter.convertCdToString(isoDto.getGrantorCode());
        this.holderType = CdConverter.convertCdToString(isoDto.getHolderTypeCode());       
        if (isoDto.getNihInstHolderCode().getCode() != null) {
            this.programCode = CdConverter.convertCdToString(isoDto.getNihInstHolderCode());
        }
        if (isoDto.getNciDivProgHolderCode().getCode() != null) {
            this.programCode = CdConverter.convertCdToString(isoDto.getNciDivProgHolderCode());
        }
        this.number = StConverter.convertToString(isoDto.getIndldeNumber());
        this.indIde = CdConverter.convertCdToString(isoDto.getIndldeTypeCode());
        this.rowId = UUID.randomUUID().toString();
    }
    /**
     * Default.
     */
    public TrialIndIdeDTO() {
        super();
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
     * @return the indIdeId
     */
    public String getIndIdeId() {
        return indIdeId;
    }
    /**
     * @param indIdeId the indIdeId to set
     */
    public void setIndIdeId(String indIdeId) {
        this.indIdeId = indIdeId;
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
    
}
