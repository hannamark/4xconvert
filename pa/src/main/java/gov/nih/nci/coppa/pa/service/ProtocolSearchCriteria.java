package gov.nih.nci.coppa.pa.service;

/**
 * @author Hugh
 *
 */
public class ProtocolSearchCriteria {    
    private String accessionNumber = "NCI_IDENTIFIER";
    private String officialTitle = "LONG_TITLE_TEXT";
 
    /**
     * @return accession number
     */
    public String getAccessionNumber() {
        return accessionNumber;
    }
    
    /**
     * @param accessionNumber accession number
     */
    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    /**
     * @return title
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    
    /**
     * @param officialTitle title
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
}