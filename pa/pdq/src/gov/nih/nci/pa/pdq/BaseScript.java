package gov.nih.nci.pa.pdq;

import gov.nih.nci.pa.util.PAAttributeMaxLen;

import java.io.PrintStream;

/**
 * @author hreinhart
 *
 */
public class BaseScript {
    protected PrintStream out;

    protected String fix(String original) {
        return fix(original, PAAttributeMaxLen.LONG_TEXT_LENGTH);
    }
    protected String fix(String original, int length) {
        String work = null;
        if (original.length() > length) {
            work = original.substring(0, length);
        } else {
            work = original;
        }
        String revised = "";
        for (int i = 0; i < work.length(); i++){
            char c = work.charAt(i);
            if (c == '\'') {
                revised += "''";
            }else{
                revised += c;
            }
        }
        return revised;
    }
}
