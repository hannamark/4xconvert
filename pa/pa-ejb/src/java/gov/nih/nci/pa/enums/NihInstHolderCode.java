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
public enum NihInstHolderCode implements CodedEnum<String> {
    /** NCI-National Cancer Institute. */
    NCI("NCI-National Cancer Institute"),
    /** NEI-National Eye Institute. */
    NEI("NEI-National Eye Institute"),
    /** NHLBI-National Heart, Lung, and Blood Institute. */
    NHLBI("NHLBI-National Heart, Lung, and Blood Institute"),
    /** NHGRI-National Human Genome Research Institute. */
    NHGRI("NHGRI-National Human Genome Research Institute"),
    /** NIA-National Institute on Aging. */
    NIA("NIA-National Institute on Aging"),
    /** NIAAA-National Institute on Alcohol Abuse and Alcoholism. */
    NIAAA("NIAAA-National Institute on Alcohol Abuse and Alcoholism"),
    /** NIAID-National Institute of Allergy and Infectious Diseases. */
    NIAID("NIAID-National Institute of Allergy and Infectious Diseases"),
    /** NIAMS-National Institute of Arthritis and Musculoskeletal and Skin Diseases. */
    NIAMS("NIAMS-National Institute of Arthritis and Musculoskeletal and Skin Diseases"),
    /** NIBIB-National Institute of Biomedical Imaging and Bioengineering. */
    NIBIB("NIBIB-National Institute of Biomedical Imaging and Bioengineering"),
    /** NICHD-Eunice Kennedy Shriver National Institute of Child Health and Human Development. */
    NICHD("NICHD-Eunice Kennedy Shriver National Institute of Child Health and Human Development"),
    /** NIDCD-National Institute on Deafness and Other Communication Disorders. */
    NIDCD("NIDCD-National Institute on Deafness and Other Communication Disorders"),
    /** NIDCR-National Institute of Dental and Craniofacial Research. */
    NIDCR("NIDCR-National Institute of Dental and Craniofacial Research"),    
    /** NIDDK-National Institute of Diabetes and Digestive and Kidney Diseases. */
    NIDDK("NIDDK-National Institute of Diabetes and Digestive and Kidney Diseases"),
    /** NIDA-National Institute on Drug Abuse. */
    NIDA("NIDA-National Institute on Drug Abuse"),
    /** NIEHS-National Institute of Environmental Health Sciences. */
    NIEHS("NIEHS-National Institute of Environmental Health Sciences"),
    /** NIGMS-National Institute of General Medical Sciences. */
    NIGMS("NIGMS-National Institute of General Medical Sciences"),
    /** NIMH-National Institute of Mental Health. */
    NIMH("NIMH-National Institute of Mental Health"),
    /** NINDS-National Institute of Neurological Disorders and Stroke. */
    NINDS("NINDS-National Institute of Neurological Disorders and Stroke"),
    /** NINR-National Institute of Nursing Research. */
    NINR("NINR-National Institute of Nursing Research"),
    /** NLM-National Library of Medicine. */
    NLM("NLM-National Library of Medicine"),
    /** CIT-Center for Information Technology. */
    CIT("CIT-Center for Information Technology"),
    /** CSR-Center for Scientific Review. */
    CSR("CSR-Center for Scientific Review"),
    /** FIC-John E. Fogarty International Center for Advanced Study in the Health Sciences. */
    FIC("FIC-John E. Fogarty International Center for Advanced Study in the Health Sciences"),
    /** NCCAM-National Center for Complementary and Alternative Medicine. */
    NCCAM("NCCAM-National Center for Complementary and Alternative Medicine"),
    /** NCMHD-National Center on Minority Health and Health Disparities. */
    NCMHD("NCMHD-National Center on Minority Health and Health Disparities"),
    /** NCRR-National Center for Research Resources . */
    NCRR("NCRR-National Center for Research Resources (NCRR"),
    /** CC-NIH Clinical Center. */
    CC("CC-NIH Clinical Center"),
    /** OD-Office of the Director. */
    OD("OD-Office of the Director");    

    private String code;
    /**
     * 
     * @param code
     */
    private NihInstHolderCode(String code) {
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
     * @return NihInstHolderCode 
     */
    public static NihInstHolderCode getByCode(String code) {
        return getByClassAndCode(NihInstHolderCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        NihInstHolderCode[] l = NihInstHolderCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }
}
