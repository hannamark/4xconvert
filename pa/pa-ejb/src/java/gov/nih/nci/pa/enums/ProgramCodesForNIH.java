package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * List of values for the program code dropdown if the IND.IDE holder
 * type selected is NIH.
 * 
 * @author Harsha
 *
 */
public enum ProgramCodesForNIH implements CodedEnum<String> {
    /**
     * Abbreviated.
     */
    NIHValue01("nihvalue1"), 
     /**
      * Complete.
      */
    NIHValue02("nihvalue2"),
     /**
      * AE.
      */
    NIHValue03("nihvalue3");
    
    private String code;
    
    /**
     * Constructor for nihvalue.
     * @param code
     */
    private ProgramCodesForNIH(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    public String getCode() {
        return code;
    }
    
    /**
     *@return String DisplayName 
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }
    /**
     * @param code code
     * @return AccrualReportingMethodCode 
     */
    public static ProgramCodesForNIH getByCode(String code) {
        return getByClassAndCode(ProgramCodesForNIH.class, code);
    }
    
    /**
     * construct a array of display names for ProgramCodesForNIH Enum.
     * @return String[] display names for ProgramCodesForNIH
     */
    public static String[]  getDisplayNames() {
        ProgramCodesForNIH[] codes = ProgramCodesForNIH.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }
        
}
