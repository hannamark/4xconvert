package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * The method of assigning subjects to treatment or control groups.
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum AllocationCode implements CodedEnum<String> {
    
    /** NA. */
    NA("NA"),
    /**Randomized Controlled Trial. */
    RANDOMIZED_CONTROLLED_TRIAL("Randomized Controlled Trial"),
    /** Non-Randomized Trial. */
    NON_RANDOMIZED_TRIAL("Non-Randomized Trial");    
    
    private String code;
    /**
     * 
     * @param code
     */
    private AllocationCode(String code) {
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
    public static AllocationCode getByCode(String code) {
        return getByClassAndCode(AllocationCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        AllocationCode[] l = AllocationCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
    

}

