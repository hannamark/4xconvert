package gov.nih.nci.pa.enums;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;


/**
 * Enumeration  for Trial Phase codes.
 *
 * @author Naveen Amiruddin
 * @since 07/03/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public enum YesNoCode implements CodedEnum<String> {
    
    /**
     * NA.
     */
     YES("Yes"), 
     /**
      * Phase 0.
      */
     NO("No");
     
    private String code;
    /**
     * 
     * @param code
     */
    private YesNoCode(String code) {
        this.code = code;
        register(this);
    }
    /**
     * @return code code
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
     * 
     * @return String name
     */
    public String getName() {
        return name();
    }

    /**
     * 
     * @param code code
     * @return TrialPhaseType 
     */
    public static YesNoCode getByCode(String code) {
        return getByClassAndCode(YesNoCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        YesNoCode[] l = YesNoCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getDisplayName();
        }
        return a;
    }
}    
 
