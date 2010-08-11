/**
 *
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author vrushali
 *
 */
public enum PhaseAdditionalQualifierCode implements CodedEnum<String> {
    /*** Pilot.  */
    PILOT("Pilot");

    private String code;
    private PhaseAdditionalQualifierCode(String code) {
        this.code = code;
        register(this);
    }
    /**
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return name
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * @param str str
     * @return Name
     */
    public String getNameByCode(String str) {
        return getByCode(str).name();
    }

    /**
    *
    * @return String name
    */
    public String getName() {
        return name();
    }

    /**
     * @param code Code
     * @return code
     */
    public static PhaseAdditionalQualifierCode getByCode(String code) {
        return getByClassAndCode(PhaseAdditionalQualifierCode.class, code);
    }
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        int i = 0;
        String[] result = new String[PhaseAdditionalQualifierCode.values().length];
        for (PhaseAdditionalQualifierCode value : PhaseAdditionalQualifierCode.values()) {
            result[i++] = value.getCode();
        }
        return result;
    }

}
