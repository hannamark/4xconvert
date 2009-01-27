package gov.nih.nci.pa.util;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.iso.util.TsConverter;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @deprecated
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
            return true;
        } else {
            if (ii.getExtension().trim().length() == 0) {
                isNull = true;
            }
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
     * checks if Cd is null.
     * @param cd cd
     * @return boolean
     */
    public static boolean isCdNull(Cd cd) {
        boolean isNull = false;
        if (cd == null || cd.getCode() == null) {
            return true;
        } else {
            if (cd.getCode().trim().length() == 0) {
                isNull = true;
            }
        }
        return isNull;
    }

    /**
     * checks if Cd is null.
     * @param bl Bl
     * @return boolean
     */
    public static boolean isBlNull(Bl bl) {
        boolean isNull = false;
        if (bl == null || bl.getValue() == null) {
            return true;
        }
        return isNull;
    }

    /**
     * Private class used to decode and normalize date strings.
     */
    private static class ValidDateFormat {
        String pattern;
        int endIndex;
        boolean lenient;

        public ValidDateFormat(String pattern) {
            this.pattern = pattern;
            this.endIndex = pattern.length();
            this.lenient = false;
        }
        public ValidDateFormat(String pattern, Integer length, Boolean lenient) {
            this.pattern = pattern;
            this.endIndex = length;
            this.lenient = lenient;
        }
    }

    /**
     * Static ordered list of valid date format patterns.
     */
    private static ValidDateFormat[] dateFormats;
    static {
        dateFormats = new ValidDateFormat[] { 
                new ValidDateFormat("MM/dd/yyyy"),
                new ValidDateFormat("yyyy-MM-dd"),
                new ValidDateFormat("yyyy/MM/dd")
        };
    }

    /**
     * Convert an input string to a Date.
     * 
     * @param inDate string to be normalized
     * @return Date
     */
    private static Date dateStringToDate(String inDate) {
        if (inDate == null) {
            return null;
        }
        
        Date outDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat();
        for (ValidDateFormat fm : dateFormats) {
            if (outDate != null) {
                break; 
            }
            sdf.applyPattern(fm.pattern);
            sdf.setLenient(fm.lenient);
            try {
                int endIndex = (inDate.trim().length() < fm.endIndex) ? inDate.trim().length() : fm.endIndex;
                outDate = sdf.parse(inDate.trim().substring(0, endIndex));
            } catch (ParseException e) {
                outDate = null;
            }
        }
        return outDate;
    }

    /**
     * 
     * @param isoTs timestamp
     * @param format data format
     * @return String
     */
    public static String convertTsToFormarttedDate(Ts isoTs , String format) {
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return  null;
        }
        DateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
        return  formatter.format(ts);
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
        Date outDate = dateStringToDate(inDate);
        if (outDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern(dateFormats[0].pattern);
        return sdf.format(outDate);
    }
        
    /**
     * Convert an input string to a Timestamp.
     * 
     * @param inDate string to be normalized
     * @return Timestamp
     */
    public static Timestamp dateStringToTimestamp(String inDate) {
        Date dt = dateStringToDate(inDate);
        return (dt == null) ? null : new Timestamp(dt.getTime());
    }
    
    /**
     * @return today's date as a string
     */
    public static String today() {
        return normalizeDateString(new Timestamp((new Date()).getTime()).toString());
    }
    
    /** 
    * Returns whether the argument is null or has only whitespace characters 
    * within it. This method is more efficient than performing a trim() operation 
    * because no intermediate strings are created and we often don't need to 
    * iterate over the whole string. 
    * 
    * @param aString String 
    * @return boolean 
    */ 
    public static boolean isEmpty(String aString) { 
     
        if (aString == null) {
            return true; 
        }
         
        int length = aString.length(); 
         
        for (int i = 0; i < length; i++) { 
            if (Character.isWhitespace(aString.charAt(i))) {
                continue; 
            }
            return false; 
        } 
     
    return true; 
    } 
    
    /**
     * 
     * @param aString aString
     * @return boolean
     */
    public static boolean isNotEmpty(String aString) { 
        return !isEmpty(aString); 
    }

    /**
     * Method designed for string setters to concatenate rather then cause exceptions.
     * @param value a string
     * @param maxLength the maximum length allowed for the string
     * @return null if empty, otherwise a string no longer than maxlegth
     */
    public static String stringSetter(String value, int maxLength) {
        if (isEmpty(value)) {
            return null;
        }
        return (value.length() > maxLength) ? value.substring(0, maxLength) : value;
    }

    /**
     * Method designed for string setters to concatenate rather then cause exceptions.
     * @param value a string
     * @return null if empty, otherwise a string no longer than the default
     */
    public static String stringSetter(String value) {
        return stringSetter(value, AbstractEntity.LONG_TEXT_LENGTH);
    }

    /**
     * Util method to validate email addresses.
     * 
     * @param email to check the string
     * @return boolean whether email is valid or not
     */
    public static boolean isValidEmail(String email) {
       Pattern p = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");
       Matcher m = p.matcher(email);
       return  m.matches();
    }

    /**
     * util method to trim a length.
     * @param data String data
     * @param len length to trim
     * @return trimmed data
     */
    public static String trim(String data , int len) {
        if (data == null) {
            return null;
        }
        if (data.length() > len) {
            return (data.substring(0, len - 1) + "...");
        } else {
            return data;
        }
    }
}
