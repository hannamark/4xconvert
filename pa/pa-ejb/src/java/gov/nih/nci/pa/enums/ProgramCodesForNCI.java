package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * 
 * @author Harsha
 *
 */
public enum ProgramCodesForNCI implements CodedEnum<String> {
    /**
     * Abbreviated.
     */
    NCIValue01("ncivalue1"), 
     /**
      * Complete.
      */
    NCIValue02("ncivalue2"),
     /**
      * AE.
      */
    NCIValue03("ncivalue3");
    
    private String code;
    
    /**
     * Constructor for ncivalue.
     * @param code
     */
    private ProgramCodesForNCI(String code) {
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
    public static ProgramCodesForNCI getByCode(String code) {
        return getByClassAndCode(ProgramCodesForNCI.class, code);
    }
    
    /**
     * construct a array of display names for ProgramCodesForNCI Enum.
     * @return String[] display names for ProgramCodesForNCI
     */
    public static String[]  getDisplayNames() {
        ProgramCodesForNCI[] codes = ProgramCodesForNCI.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }

}
