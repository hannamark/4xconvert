package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Clinical trial pattern developed to compare treatment groups in a clinical trial.
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum DesignConfigurationCode implements CodedEnum<String> {
    
    /** Single Group. */
    SINGLE_GROUP("Single Group"),
    /** Parallel. */
    PARALLEL("Parallel"),
    /** Crossover Design. */
    CROSSOVER("Cross-over"),
    /** Factorial. */
    FACTORIAL("Factorial");    
    
    private String code;
    /**
     * 
     * @param code
     */
    private DesignConfigurationCode(String code) {
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
     * @return biospecimenRetentionCode 
     */
    public static DesignConfigurationCode getByCode(String code) {
        return getByClassAndCode(DesignConfigurationCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        DesignConfigurationCode[] l = DesignConfigurationCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
    

}

