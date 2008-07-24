package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;


/**
 * Indicates what type of biospecimen is reteined: speciment and DNA, speciment only, none. 
 *
 * @author Naveen Amiruddin
 * @since 07/09/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum BiospecimenRetentionCode  implements CodedEnum<String> {

    /** Retained. */
    RETAINED("Retained"),
    /** Speciment and DNA. */
    SPECIMENT_AND_DNA("Speciment and DNA"),
    /** Speciment Only. */
    SPECIMENT_ONLY("Speciment Only"),
    /** None. */
    NONE("None");
    
    private String code;
    /**
     * 
     * @param code
     */
    private BiospecimenRetentionCode(String code) {
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
    public static BiospecimenRetentionCode getByCode(String code) {
        return getByClassAndCode(BiospecimenRetentionCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        BiospecimenRetentionCode[] l = BiospecimenRetentionCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
    

}
