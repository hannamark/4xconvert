package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
 * Enumeration  for InstitutionCode.
 *
 * @author Kalpana Guthikonda
 * @since 06/04/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum InstitutionCode implements CodedEnum<String>  {

    /** AG.*/
    AG("AG"), 
    /** AI.*/
    AI("AI"), 
    /**AO.*/
    AO("AO"),
    /**AR.*/
    AR("AR"),
    /**AT.*/
    AT("AT"),
    /**BC.*/
    BC("BC"),
    /**CA.*/
    CA("CA"),
    /**CB.*/
    CB("CB"),
    /**CL.*/
    CL("CL"),
    /**CM.*/
    CM("CM"),
    /**CN.*/
    CN("CN"),
    /**CO.*/
    CO("CO");
 
    private String code;

    /**
     * Constructor for InstitutionCode.
     * @param code
     */

    private InstitutionCode(String code) {
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
     * @return String display name
     */
    public String getName() {
        return name();
    }


    /**
     * @param code code
     * @return InstitutionCode
     */
    public static InstitutionCode getByCode(String code) {
        return getByClassAndCode(InstitutionCode.class, code);
    }
       
    
    /**
     * construct a array of display names for InstitutionCode Enum.
     * @return String[] display names for InstitutionCode
     */
    public static String[]  getDisplayNames() {
        InstitutionCode[] codes = InstitutionCode.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }        
}
