package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
 * Enumeration  for Sponsor Monitor code.
 * database is mapped to protocol.intent_code
 * @author Gil Naveh
 * @since 07/24/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum SummaryFourFundingCategoryCode implements CodedEnum<String> {

    /** National.*/
    NATIONAL("National"), 
    /**Externally Peer-Reviewed.*/
    EXTERNALLY_PEER_REVIEWED("Externally Peer-Reviewed"), 
    /**Institutional.*/
    INSTITUTIONAL("Institutional"),
    /**Industrial.*/
    INDUSTRIAL("Industrial");
 
    private String code;

    /**
     * Constructor for SummaryFourFundingCategoryCode.
     * @param code
     */

    private SummaryFourFundingCategoryCode(String code) {
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
     * @return SummaryFourFundingCategoryCode
     */
    public static SummaryFourFundingCategoryCode getByCode(String code) {
        return getByClassAndCode(SummaryFourFundingCategoryCode.class, code);
    }
       
    
    /**
     * construct a array of display names for SummaryFourFundingCategoryCode Enum.
     * @return String[] display names for SummaryFourFundingCategoryCode
     */
    public static String[]  getDisplayNames() {
        SummaryFourFundingCategoryCode[] codes = SummaryFourFundingCategoryCode.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }        
}

