package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * List of values for the program code dropdown if the IND.IDE holder
 * type selected is NIH.
 * 
 * @author Harsha
 *
 */
public enum ProgramCodesForNIH implements CodedEnum<String> {
    /**
     * National Cancer Institute.
     */
    NCI("NCI-National Cancer Institute"),
    /**
     * National Eye Institute.
     */
    NEI("NEI-National Eye Institute"),
    /**
     * National Heart, Lung, and Blood Institute.
     */
    NHLBI("NHLBI-National Heart, Lung, and Blood Institute"),
    /**
     * National Human Genome Research Institute.
     */
    NHGRI("NHGRI-National Human Genome Research Institute"),
    /**
     * National Institute on Aging.
     */
    NIA("NIA-National Institute on Aging"),
    /**
     * National Institute on Alcohol Abuse and Alcoholism.
     */
    NIAAA("NIAA-National Institute on Alcohol Abuse and Alcoholism"),
    /**
     * National Institute of Allergy and Infectious Diseases.
     */
    NIAID("NIAID-National Institute of Allergy and Infectious Diseases"),
    /**
     * National Institute of Arthritis and Musculoskeletal and Skin Diseases.
     */
    NIAMS("NIAMS-National Institute of Arthritis and Musculoskeletal and Skin Diseases"),
    /**
     * National Institute of Biomedical Imaging and Bioengineering.
     */
    NIBIB("NIBIB-National Institute of Biomedical Imaging and Bioengineering"),
    /**
     * Eunice Kennedy Shriver National Institute of Child Health and Human Development.
     */
    NICHD("NICHD-NICHD-Eunice Kennedy Shriver National Institute of Child Health and Human Development"),
    /**
     * National Institute on Deafness and Other Communication Disorders.
     */
    NIDCD("NIDCD-National Institute on Deafness and Other Communication Disorders"),
    /**
     * National Institute of Dental and Craniofacial Research.
     */
    NIDCR("NIDCR-National Institute of Dental and Craniofacial Research"),
    /**
     * National Institute of Diabetes and Digestive and Kidney Diseases.
     */
    NIDDK("NIDDK-National Institute of Diabetes and Digestive and Kidney Diseases"),
    /**
     * National Institute on Drug Abuse.
     */
    NIDA("NIDA-National Institute on Drug Abuse"),
    /**
     * National Institute of Environmental Health Sciences.
     */
    NIEHS("NIEHS-National Institute of Environmental Health Sciences"),
    /**
     * National Institute of General Medical Sciences.
     */
    NIGMS("NIGMS-National Institute of General Medical Sciences"),
    /**
     * National Institute of Mental Health.
     */
    NIMH("NIMH-National Institute of Mental Health"),
    /**
     * National Institute of Neurological Disorders and Stroke.
     */
    NINDS("NINDS-National Institute of Neurological Disorders and Stroke"),
    /**
     * National Institute of Nursing Research.
     */
    NINR("NINR-National Institute of Nursing Research"),
    /**
     * National Library of Medicine.
     */
    NLM("NLM-National Library of Medicine"),
    /**
     * Center for Information Technology.
     */
    CIT("CIT-Center for Information Technology"),
    /**
     * Center for Scientific Review.
     */
    CSR("CSR-Center for Scientific Review"),
    /**
     * John E. Fogarty International Center for Advanced Study in the Health Sciences.
     */
    FIC("FIC-John E. Fogarty International Center for Advanced Study in the Health Sciences"),
    /**
     * National Center for Complementary and Alternative Medicine.
     */
    NCCAM("NCCAM-National Center for Complementary and Alternative Medicine"),
    /**
     * National Center on Minority Health and Health Disparities.
     */
    NCMHD("NCMHD-National Center on Minority Health and Health Disparities"),
    /**
     * National Center for Research Resources (NCRR).
     */
    NCRR("NCRR-National Center for Research Resources (NCRR)"),
    /**
     * NIH Clinical Center.
     */
    CC("CC-NIH Clinical Center"),
    /**
     * Office of the Director.
     */
    OD("OD-Office of the Director");

    
    private String code;
    
    /**
     * Constructor for nihvalue.
     * @param code
     */
    private ProgramCodesForNIH(String code) {
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
    public static ProgramCodesForNIH getByCode(String code) {
        return getByClassAndCode(ProgramCodesForNIH.class, code);
    }
    
    /**
     * construct a array of display names for ProgramCodesForNIH Enum.
     * @return String[] display names for ProgramCodesForNIH
     */
    public static String[]  getDisplayNames() {
        ProgramCodesForNIH[] codes = ProgramCodesForNIH.values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }
        
}
