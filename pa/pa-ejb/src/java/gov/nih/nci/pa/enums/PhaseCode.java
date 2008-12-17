package gov.nih.nci.pa.enums;
import static gov.nih.nci.pa.enums.CodedEnumHelper.register;
import static gov.nih.nci.pa.enums.EnumHelper.sentenceCasedName;
import static gov.nih.nci.pa.enums.CodedEnumHelper.getByClassAndCode;



/**
 * Enumeration  for  Phase codes.
 *
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public enum PhaseCode implements CodedEnum<String> {
       
    /*** 0.  */
    O("0"), 
    /*** The first step in testing a new treatment in humans. */
    I("I"), 
    /*** A clinical research protocol designed to study the safety, dosage levels and response to new treatment.*/
    I_II("I/II"),
    /*** A study to test whether a new treatment has an anticancer effect . */
    II("II"),
    /*** A trial to study response to a new treatment and the effectiveness . */
    II_III("II/III"),
    /*** A study to compare the results of people taking a new treatment . */
    III("III"),
    /*** Phase IV trial is a randomized, controlled trial that is designed . */
    IV("IV"),
    /*** Pilot.  */
    PILOT("Pilot"),
    /*** Not Applicable . */
    NA("NA"),
    /*** Other. */
    OTHER("Other");
    
    private String code;
    /**
     * 
     * @param code
     */
    private PhaseCode(String code) {
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
    public static PhaseCode getByCode(String code) {
        return getByClassAndCode(PhaseCode.class, code);
    }
    
    /**
     * @return String[] display names of enums
     */
    public static String[]  getDisplayNames() {
        PhaseCode[] l = PhaseCode.values();
        String[] a = new String[l.length];
        for (int i = 0; i < l.length; i++) {
            a[i] = l[i].getCode();
        }
        return a;
    }  
}
