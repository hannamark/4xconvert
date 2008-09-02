package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.Ii;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Protocol DAO for accessing DAO.
 *
 * @author Naveen Amiruddin
 * @since 05/30/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings("PMD")
public class PAUtil {
    /**
     * utility method to determine if the object is not null. 
     * it returns true, if the object is not null , and does not contain any white space
     * @param obj obj1
     * @return boolean 
     */
    public static boolean isNotNullOrNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            String s = obj.toString();
            if (s != null && s.trim().length() > 0) {
                return true;
            }
            return false;
        } else if (obj != null) {
            return true;
        }
        return false;
    }

    /**
     * checks if Ii is null.
     * @param ii ii
     * @return boolean
     */
    public static boolean isIiNull(Ii ii) {
        boolean isNull = false;
        if (ii == null || ii.getExtension() == null) {
            isNull = true;
        }
        if (ii.getExtension().trim().length() == 0) {
            isNull = true;
        }
        try {
            new Long(ii.getExtension());
        } catch (NumberFormatException nfe) {
            // if cannot be converted, consider as null
            isNull = true;
        }
        return isNull;
    }

    /**
     * Private class used to decode and normalize date strings.
     */
    private static class ValidDateFormat {
        String pattern;
        int endIndex;

        public ValidDateFormat(String pattern) {
            this.pattern = pattern;
            this.endIndex = pattern.length();
        }
        public ValidDateFormat(String pattern, Integer length) {
            this.pattern = pattern;
            this.endIndex = length;
        }
    }

    /**
     * Static ordered list of valid date format patterns.
     */
    private static ValidDateFormat[] dateFormats;
    static {
        dateFormats = new ValidDateFormat[] { 
                new ValidDateFormat("MM/dd/yyyy"),
                new ValidDateFormat("yyyy-MM-dd")
        };
    }

    /**
     * Convert an input string to a normalized date string.
     * The output format is determined by the first element in
     * the static dateFormats array.
     * 
     * @param inDate string to be normalized
     * @return normalized string
     */
    public static String normalizeDateString(String inDate) {
        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : dateFormats) {
            if (outDate != null) {
                continue; 
            }
            sdf.applyPattern(fm.pattern);
            try {
                outDate = sdf.parse(inDate.trim().substring(0, fm.endIndex));
            } catch (ParseException e) {
                outDate = null;
            }
        }
       
        if (outDate == null) {
            return null;
        }
        sdf.applyPattern(dateFormats[0].pattern);
        return sdf.format(outDate);
    }
}
