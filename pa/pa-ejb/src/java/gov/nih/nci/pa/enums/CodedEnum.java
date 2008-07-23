package gov.nih.nci.pa.enums;

/**
 * Interface for enums which decouples lookup & display from the enum's default.
 * Copied from ctms commons
 * <p>
 * Implementations will probably want to use {@link CodedEnumHelper} to reduce
 * repeated code.
 * </p> 
 * 
 * @author Rhett Sutphin
 * @param <C>
 */

public interface CodedEnum<C> {

    /**
     * @return C 
     */
    C getCode();
    /**
     * 
     * @return String
     */
    String getDisplayName();
}
