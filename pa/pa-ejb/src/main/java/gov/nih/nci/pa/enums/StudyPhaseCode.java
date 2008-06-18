package gov.nih.nci.pa.enums;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;


/**
 * Enumeration  for Trial Phase codes.
 *
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum StudyPhaseCode implements CodedEnum<String> {
    
    /**
    * NA.
    */
    N_A("N/A"), 
    /**
     * Phase 0.
     */
    PHASEO("Phase 0"), 
    /**
     * Phase I.
     */
    PHASEI("Phase I"), 
    /**
     * Phase 1/2.
     */
    PHASEI_PHASE2("Phase 1/Phase 2"), 
    /**
     * Phase 2.
     */
    PHASE2("Phase 2"), 
    /**
     * Phase 2/3.
     */
    PHASE2_PHASE3("Phase 2/Phase 3"), 
    /**
     * Phase 3.
     */
    PHASE3("Phase 3"), 
    /**
     * Phase 4.
     */
    PHASE4("Phase 4"),
    /**
     * I.
     */
    I("I"),
    /**
     * II.
     */
    II("II"),
    /**
     * III.
     */
    III("III");
    
    private String code;
    /**
     * 
     * @param code
     */
    private StudyPhaseCode(String code) {
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
     * @return StudyPhaseCode 
     */
    public static StudyPhaseCode getByCode(String code) {
        return getByClassAndCode(StudyPhaseCode.class, code);
    }
}
