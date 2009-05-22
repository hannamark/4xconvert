/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Vrushali
 *
 */
public enum EntityStatusCode implements CodedEnum<String> {
    /*** Active.*/
    ACTIVE("Active"), 
    /*** Pending.*/
    PENDING("Pending"),
    /*** In Active. **/
    INACTIVE("InActive"),
    /*** Suspended. */
    SUSPENDED("Suspended"),
    /*** Nullified. */
    NULLIFIED("Nullified");
    
    private String code;
    /**
     * Constructor for StatusCode.
     * @param code
     */
    private EntityStatusCode(String code) {
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
     *@return str 
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }
    /**
     * @return String display name
     */
    public String getName() {
        return name();
    }


    /**
     * @param code code
     * @return StatusCode 
     */
    public static EntityStatusCode getByCode(String code) {
        return getByClassAndCode(EntityStatusCode.class, code);
    }
    
    /**
     * construct a array of display names for Abstracted Status coded Enum.
     * @return String[] display names for Abstracted Status Code
     */
    public static String[]  getDisplayNames() {
        EntityStatusCode[] absStatusCodes = EntityStatusCode.values();
        String[] codedNames = new String[absStatusCodes.length];
        for (int i = 0; i < absStatusCodes.length; i++) {
            codedNames[i] = absStatusCodes[i].getCode();
        }
        return codedNames;
    }   
}
