package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
/**
 *
 * @author Kalpana Guthikonda
 * @since 09/30/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum DocumentTypeCode implements CodedEnum<String> {
    
    /** Trial_Document.*/
    Protocol_Document("Protocol Document"), 
    /**IRB_Approval_Document.*/
    IRB_Approval_Document("IRB Approval Document"), 
    /**Informed_Consent_Document.*/
    Informed_Consent_Document("Informed Consent Document"),
    /**Other.*/
    Other("Other");
 
    private String code;

    /**
     * Constructor for DocumentTypeCode.
     * @param code
     */

    private DocumentTypeCode(String code) {
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
     * @return DocumentTypeCode
     */
    public static DocumentTypeCode getByCode(String code) {
        return getByClassAndCode(DocumentTypeCode.class, code);
    }
       
    
    /**
     * construct a array of display names for DocumentTypeCode Enum.
     * @return String[] display names for DocumentTypeCode
     */
    public static String[]  getDisplayNames() {
        DocumentTypeCode[] codes = DocumentTypeCode.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }        
}
