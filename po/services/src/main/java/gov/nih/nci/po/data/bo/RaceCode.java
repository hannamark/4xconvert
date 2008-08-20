package gov.nih.nci.po.data.bo;

/**
 * Represents the various race codes possible for a <code>Person</code>.
 * 
 * @see CDEBrowser, <url>http://cdebrowser.nci.nih.gov/CDEBrowser/</url>, see PUBLIC-ID: 2453601
 */
public enum RaceCode {

    /**
     * 
     */
    AI_AN("American Indian or Alaska Native",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C41259"),
    /**
     * 
     */
    AS("Asian", "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL106519"),
    /**
     * 
     */
    B_AA("Black or African American", null),
    /**
     * 
     */
    NH_OPI("Native Hawaiian or other Pacific Islander",
            "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL025744"),

    /**
     * 
     */
    NR("Not Reported", "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C43235"),
    /**
     * 
     */
    UNK("Unknown", "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C17998"),
    /**
     * 
     */
    WH("White", "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL107068");

    private final String description;

    private final String source;

    private RaceCode(String description, String source) {
        this.description = description;
        this.source = source;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
