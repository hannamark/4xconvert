package gov.nih.nci.pa.enums;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;


/**
 * 
 * @author NAmiruddin
 *
 */
public enum TrialPhaseCode implements CodedEnum<String> {
    
    /**
    * 
    */
    PHASEO("Phase I"), PHASEI("Phase I");

    private String code;
    /**
     * 
     * @param code
     */
    private TrialPhaseCode(String code) {
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
     * @return TrialPhaseType 
     */
    public static TrialPhaseCode getByCode(String code) {
        return getByClassAndCode(TrialPhaseCode.class, code);
    }
}
