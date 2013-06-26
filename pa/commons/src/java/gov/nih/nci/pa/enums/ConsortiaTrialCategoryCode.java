package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * @author Denis G. Krylov
 * 
 */
public enum ConsortiaTrialCategoryCode implements CodedEnum<String> {

    /** National. */
    NATIONAL("National"),
    /** Externally Peer-Reviewed. */
    EXTERNALLY_PEER_REVIEWED("Externally Peer-Reviewed");

    private String code;

    /**
     * Constructor for ConsortiaTrialCategoryCode.
     * 
     * @param code
     */

    private ConsortiaTrialCategoryCode(String code) {
        this.code = code;
        register(this);
    }

    /**
     * @return code coded value of enum
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * @return String DisplayName
     */
    @Override
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
     * @param code
     *            code
     * @return ConsortiaTrialCategoryCode
     */
    public static ConsortiaTrialCategoryCode getByCode(String code) {
        return getByClassAndCode(ConsortiaTrialCategoryCode.class, code);
    }

    /**
     * construct a array of display names for ConsortiaTrialCategoryCode Enum.
     * 
     * @return String[] display names for ConsortiaTrialCategoryCode
     */
    public static String[] getDisplayNames() {
        ConsortiaTrialCategoryCode[] codes = ConsortiaTrialCategoryCode
                .values();
        String[] codedNames = new String[codes.length];
        for (int i = 0; i < codes.length; i++) {
            codedNames[i] = codes[i].getCode();
        }
        return codedNames;
    }

}
