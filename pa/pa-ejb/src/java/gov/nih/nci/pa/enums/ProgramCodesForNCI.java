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
    
    /** CCR-Center for Cancer Research. */
    CCR("CCR"), 
    /** CIP-Cancer Imaging Program. */
    CIP("CIP") ,
    /** CDP-Cancer Diagnosis Program. */
    CDP("CDP") ,
    /** CTEP-Cancer Therapy Evaluation Program. */
    CTEP("CTEP") , 
    /** DCB-Division of Cancer Biology. */
    DCB("DCB") ,
    /** DCCPS-Division of Cancer Control and Population Sciences. */
    DCCPS("DCCPS") , 
    /** DCEG-Division of Cancer Epidemiology and Genetics. */
    DCEG("DCEG") ,
    /** DCP-Division of Cancer Prevention. */
    DCP("DCP") ,
    /** DEA-Division of Extramural Activities. */
    DEA("DEA"),
    /** DTP-Developmental Therapeutics Program. */
    DTP("DTP") ,
    /** OD-Office of the Director, NCI, NIH. */
    OD("OD") ,
    /** OSB/SPOREs -Organ Systems Branch (OSB) /Specialized Programs of Research Excellence (SPOREs). */
    OSB_SPOREs("OSB/SPOREs") ,
    /** TRP-Translational Research Program. */
    TRP("TRP") ,
    /** RRP-Radiation Research Program. */
    RRP("RRP") ,     
    /*** . */
    NA("N/A");
    
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
