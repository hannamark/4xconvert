/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Hugh Reinhart
 * @since 10/28/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum ActionSubcategoryCode implements CodedEnum<String> {
    /** Drug. */
    DRUG("Drug"),
    /** Device. */
    DEVICE("Device"),
    /** Biological/Vaccine. */
    BIOLOGICAL_VACCINE("Biological/Vaccine"),
    /** Procedure/Surgery. */
    PROCEDURE_SURGERY("Procedure/Surgery"),
    /** Radiation. */
    RADIATION("Radiation"),
    /** Behavioral. */
    BEHAVIORAL("Behavioral"),
    /** Genetic. */
    GENETIC("Genetic"),
    /** Dietary Supplement. */
    DIETARY_SUPPLEMENT("Dietary Supplement"),
    /** Other. */
    OTHER("Other");
    
    private String code;
    /**
     * 
     * @param code
     */
    private ActionSubcategoryCode(String code) {
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
            a[i] = l[i].getCode();
        }
        return a;
    }
}    
 
