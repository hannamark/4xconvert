package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
public enum NciDivProgHolderCode implements CodedEnum<String> {
    /** CCR-Center for Cancer Research. */
    CCR("CCR-Center for Cancer Research"),
    /** CTEP-Cancer Therapy Evaluation Program. */
    CTEP("CTEP-Cancer Therapy Evaluation Program"),
    /** DCB-Division of Cancer Biology. */
    DCB("DCB-Division of Cancer Biology"),
    /** DCCPS-Division of Cancer Control and Population Sciences. */
    DCCPS("DCCPS-Division of Cancer Control and Population Sciences"),
    /** DCEG-Division of Cancer Epidemiology and Genetics. */
    DCEG("DCEG-Division of Cancer Epidemiology and Genetics"),
    /** DTP-Developmental Therapeutics Program. */
    DTP("DTP-Developmental Therapeutics Program"),
    /** DCP-Division of Cancer Prevention. */
    DCP("DCP-Division of Cancer Prevention"),
    /** DEA-Division of Extramural Activities. */
    DEA("DEA-Division of Extramural Activities"),
    /** OD-Office of the Director, NCI, NIH. */
    OD("OD-Office of the Director, NCI, NIH"),
    /** OSB/SPOREs -Organ Systems Branch (OSB) /Specialized Programs of Research Excellence (SPOREs). */
    OSB_SPOREs("OSB/SPOREs -Organ Systems Branch (OSB) /Specialized Programs of Research Excellence (SPOREs)"),
    /** CIP-Cencer Imaging Program. */
    CIP("CIP-Cencer Imaging Program"),
    /** CDP-Cancer Diagnosis Program. */
    CDP("CDP-Cancer Diagnosis Program"),
    /** TRP-Translational Research Program. */
    TRP("TRP-Translational Research Program"),
    /** RRP-Radiation Research Program. */
    RRP("RRP-Radiation Research Program"),
    /** N/A-Not applicable. */
    NA("N/A-Not applicable");    

    private String code;
    /**
     * 
     * @param code
     */
    private NciDivProgHolderCode(String code) {
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
     * @return NciDivProgHolderCode 
     */
    public static NciDivProgHolderCode getByCode(String code) {
        return getByClassAndCode(NciDivProgHolderCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        NciDivProgHolderCode[] l = NciDivProgHolderCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
