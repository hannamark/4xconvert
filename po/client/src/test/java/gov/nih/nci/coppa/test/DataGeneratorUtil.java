package gov.nih.nci.coppa.test;



public class DataGeneratorUtil {

    private DataGeneratorUtil() {
        //noop
    }
    
    public static String text(int length, char defaultChar) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buf.append(defaultChar);
        }
        return buf.toString();
    }
    
    public static String words(int length, char defaultChar, int maxWordLength) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < length; i++) {
            for(int j = 0; j < maxWordLength; j++) {
                if (buf.length() < length) {
                    buf.append(defaultChar);
                }
            }
            if (buf.length() < length) {
                buf.append(' ');
            }
        }
        return buf.toString();
    }
    
}
