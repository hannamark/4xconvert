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
    
}
