package gov.nih.nci.po.data.bo;

/**
 * Represents the various sex codes possible for a <code>Person</code>.
 * 
 * @see CDEBrowser, <url>http://cdebrowser.nci.nih.gov/CDEBrowser/</url>, see PUBLIC-ID: 2589852
 */
public enum SexCode {
    /**
     * Female.
     */
    F("F", "Female", "C16576", null),
    /**
     * Male.
     */
    M("M", "Male", "C20197", null),
    /**
     * Unknown.
     */
    U("U", "Unknown", "C17998",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C17998"),
    /**
     * Intersex.
     */
    UN("UN", "Intersex", "C45908",
            "http://nciterms.nci.nih.gov/NCIBrowser/ConceptReport.jsp?dictionary=NCI_Thesaurus&code=C45908");

    private final String valueMeaning;
    private final String valueMeaningConceptCode;
    private final String source;
    private final String value;


    private SexCode(String value, String valueMeaning, String valueMeaningConceptCode, String source) {
        this.value = value;
        this.valueMeaning = valueMeaning;
        this.valueMeaningConceptCode = valueMeaningConceptCode;
        this.source = source;
    }

    /**
     * @return external value code
     */
    public String getValue() {
        return value;
    }

    /**
     * @return value meaning from CDE Browser
     */
    public String getValueMeaning() {
        return valueMeaning;
    }

    /**
     * @return value meaning concept code from CDE Browser
     */
    public String getValueMeaningConceptCode() {
        return valueMeaningConceptCode;
    }

    /**
     * @return concept code source
     */
    public String getSource() {
        return source;
    }
}
