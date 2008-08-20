package gov.nih.nci.po.data.bo;

/**
 * Represents the various race codes possible for a <code>Person</code>.
 * 
 * @see CDEBrowser, <url>http://cdebrowser.nci.nih.gov/CDEBrowser/</url>, see PUBLIC-ID: 2453601
 */
public enum RaceCode {

    /**
     * American Indian or Alaska Native.
     */
    AI_AN("06", "American Indian or Alaska Native", "C41259",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C41259"),
    /**
     * Asian.
     */
    AS("05", "Asian", "CL106519", "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL106519"),
    /**
     * Black or African American.
     */
    B_AA("03", "Black or African American", null, null),
    /**
     * Native Hawaiian or other Pacific Islander.
     */
    NH_OPI("04", "Native Hawaiian or other Pacific Islander", "CL025744",
            "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL025744"),

    /**
     * Not Reported.
     */
    NR("98", "Not Reported", "C43235",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C43235"),
    /**
     * Unknown.
     */
    UNK("99", "Unknown", "C17998",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C17998"),
    /**
     * White.
     */
    WH("01", "White", "CL107068", "http://ncimeta.nci.nih.gov/MetaServlet/ResultServlet?cui=CL107068");

    private final String value;

    private final String valueMeaning;
    private final String valueConceptCode;
    private final String source;

    private RaceCode(String value, String valueMeaning, String valueConceptCode, String source) {
        this.value = value;
        this.valueMeaning = valueMeaning;
        this.valueConceptCode = valueConceptCode;
        this.source = source;
    }

    /**
     * @return external value code
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the description
     */
    public String getValueMeaning() {
        return valueMeaning;
    }

    /**
     * @return value meaning concept code from CDE Browser
     */
    public String getValueConceptCode() {
        return valueConceptCode;
    }
    
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }
}
