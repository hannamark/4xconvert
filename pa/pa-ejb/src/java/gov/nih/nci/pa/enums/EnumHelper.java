package gov.nih.nci.pa.enums;
/**
 * Utility methods for enums.
 * @author Rhett Sutphin
 */
public class EnumHelper {

    /**
     * @param <T> t
     * @param instance instance
     * @return String sentenceCasedName
     */
    public static <T extends Enum<T>> String sentenceCasedName(T instance) {
        StringBuilder name = new StringBuilder(instance.name().toLowerCase());
        name.replace(0, 1, name.substring(0, 1).toUpperCase());
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '_') {
               name.setCharAt(i, ' ');
            }
        }
        return name.toString();
    }
}
