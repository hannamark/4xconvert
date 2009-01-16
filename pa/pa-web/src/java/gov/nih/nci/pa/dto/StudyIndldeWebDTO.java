/**
 * 
 */
package gov.nih.nci.pa.dto;

//import gov.nih.nci.coppa.iso.Bl;
//import gov.nih.nci.coppa.iso.Cd;
//import gov.nih.nci.coppa.iso.Ii;
//import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;


/**
 * @author Hong Gao
 *
 */
public class StudyIndldeWebDTO {

    private String studyProtocolIi;
    private String expandedAccessStatus;
    private String expandedAccessIndicator;
    private String indldeNumber;
    private String indldeType;
    private String programCode;
    private String grantor;
    private String holderType;
    private String nihInstHolder;
    private String nciDivProgHolder;
    private String id;

    /**
     * @param iso StudyResourcingDTO object
     */
    public StudyIndldeWebDTO(StudyIndldeDTO iso) {
        super();
          this.expandedAccessStatus = iso.getExpandedAccessStatusCode().getCode();
          this.grantor = iso.getGrantorCode().getCode();
          
          if (iso.getExpandedAccessIndicator().getValue() != null) {
            if (iso.getExpandedAccessIndicator().getValue().toString().equalsIgnoreCase("true")) {
              this.expandedAccessIndicator = "Yes";
            } else {
              this.expandedAccessIndicator = "No";
            } 
          }
          //this.expandedAccessIndicator = iso.getExpandedAccessIndicator().getValue().toString();
          this.indldeNumber = iso.getIndldeNumber().getValue();
          this.indldeType = iso.getIndldeTypeCode().getCode();
          this.holderType = iso.getHolderTypeCode().getCode();
          this.nihInstHolder = iso.getNihInstHolderCode().getCode();
          this.nciDivProgHolder = iso.getNciDivProgHolderCode().getCode();
          this.id = iso.getIdentifier().getExtension();
    }
    
    /** .
     *  Default Constructor
     */
    public StudyIndldeWebDTO() {
        super();
    }

    /**
     * @return the studyProtocolIi
     */
    public String getStudyProtocolIi() {
        return studyProtocolIi;
    }

    /**
     * @param studyProtocolIi the studyProtocolIi to set
     */
    public void setStudyProtocolIi(String studyProtocolIi) {
        this.studyProtocolIi = studyProtocolIi;
    }

    /**
     * @return the expandedAccessStatus
     */
    public String getExpandedAccessStatus() {
        return expandedAccessStatus;
    }

    /**
     * @param expandedAccessStatus the expandedAccessStatus to set
     */
    public void setExpandedAccessStatus(String expandedAccessStatus) {
        this.expandedAccessStatus = expandedAccessStatus;
    }

    /**
     * @return the expandedAccessIndicator
     */
    public String getExpandedAccessIndicator() {
        return expandedAccessIndicator;
    }

    /**
     * @param expandedAccessIndicator the expandedAccessIndicator to set
     */
    public void setExpandedAccessIndicator(String expandedAccessIndicator) {
        this.expandedAccessIndicator = expandedAccessIndicator;
    }

    /**
     * @return the indldeNumber
     */
    public String getIndldeNumber() {
        return indldeNumber;
    }

    /**
     * @param indldeNumber the indldeNumber to set
     */
    public void setIndldeNumber(String indldeNumber) {
        this.indldeNumber = indldeNumber;
    }

    /**
     * @return the indldeType
     */
    public String getIndldeType() {
        return indldeType;
    }

    /**
     * @param indldeType the indldeType to set
     */
    public void setIndldeType(String indldeType) {
        this.indldeType = indldeType;
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
     * @return the nihInstHolder
     */
    public String getNihInstHolder() {
        return nihInstHolder;
    }

    /**
     * @param nihInstHolder the nihInstHolder to set
     */
    public void setNihInstHolder(String nihInstHolder) {
        this.nihInstHolder = nihInstHolder;
    }

    /**
     * @return the nciDivProgHolder
     */
    public String getNciDivProgHolder() {
        return nciDivProgHolder;
    }

    /**
     * @param nciDivProgHolder the nciDivProgHolder to set
     */
    public void setNciDivProgHolder(String nciDivProgHolder) {
        this.nciDivProgHolder = nciDivProgHolder;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}

