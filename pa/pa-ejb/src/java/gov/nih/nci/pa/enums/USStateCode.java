/**
 * 
 */
package gov.nih.nci.pa.enums;

import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;

/**
 * Enumeration US States.
 * 
 * @author Bala Nair
 * 
 */
public enum USStateCode implements CodedEnum<String> {

    /** International. */
    INTERNATIONAL("None (International)"),
    /** Alabama. */
    AL("Alabama"),
    /** Alaska. */
    AK("Alaska"),
    /** Arizona. */
    AZ("Arizona"),
    /** Arkansas. */
    AR("Arkansas"),
    /** California. */
    CA("California"),
    /** Colorado. */
    CO("Colorado"),
    /** Connecticut. */
    CT("Connecticut"),
    /** Delaware. */
    DE("Delaware"),
    /** District of Columbia. */
    DC("District of Columbia"),
    /** Florida. */
    FL("Florida"),
    /** Georgia. */
    GA("Georgia"),
    /** Hawaii. */
    HI("Hawaii"),
    /** Idaho. */
    ID("Idaho"),
    /** Illinois. */
    IL("Illinois"),
    /** Indiana. */
    IN("Indiana"),
    /** Iowa. */
    IA("Iowa"),
    /** Kansas. */
    KS("Kansas"),
    /** Kentucky. */
    KY("Kentucky"),
    /** Louisiana. */
    LA("Louisiana"),
    /** Maine. */
    ME("Maine"),
    /** Maryland. */
    MD("Maryland"),
    /** Massachusetts. */
    MA("Massachusetts"),
    /** Michigan. */
    MI("Michigan"),
    /** Minnesota. */
    MN("Minnesota"),
    /** Mississippi. */
    MS("Mississippi"),
    /** Missouri. */
    MO("Missouri"),
    /** Montana. */
    MT("Montana"),
    /** Nebraska. */
    NE("Nebraska"),
    /** Nevada. */
    NV("Nevada"),
    /** New Hampshire. */
    NH("New Hampshire"),
    /** New Jersey. */
    NJ("New Jersey"),
    /** New Mexico. */
    NM("New Mexico"),
    /** New York. */
    NY("New York"),
    /** North Carolina. */
    NC("North Carolina"),
    /** North Dakota. */
    ND("North Dakota"),
    /** Ohio. */
    OH("Ohio"),
    /** Oklahoma. */
    OK("Oklahoma"),
    /** Oregon. */
    OR("Oregon"),
    /** Pennsylvania. */
    PA("Pennsylvania"),
    /** Rhode Island. */
    RI("Rhode Island"),
    /** South Carolina. */
    SC("South Carolina"),
    /** South Dakota. */
    SD("South Dakota"),
    /** Tennessee. */
    TN("Tennessee"),
    /** Texas. */
    TX("Texas"),
    /** Utah. */
    UT("Utah"),
    /** Vermont. */
    VT("Vermont"),
    /** Virginia. */
    VA("Virginia"),
    /** Washington. */
    WA("Washington"),
    /** West Virginia. */
    WV("West Virginia"),
    /** Wisconsin. */
    WI("Wisconsin"),
    /** Wyoming. */
    WY("Wyoming");

    private String code;

    /**
     * Constructor for SponsorMonitorCode.
     * 
     * @param code
     */

    private USStateCode(String code) {
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
     * @param code
     *            code
     * @return Study Type Code
     */
    public static USStateCode getByCode(String code) {
        return getByClassAndCode(USStateCode.class, code);
    }

    /**
     * @return String[] display names of enums
     */
    public static String[] getDisplayNames() {
        USStateCode[] l = USStateCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }

}
