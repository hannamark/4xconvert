/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings("PMD")
public class RegistryUtil {
    
    /**
     * check if the uploaded file type is valid.
     * @param fileName filename
     * @return boolean
     */
    public static boolean isValidFileType(String fileName) {
        boolean isValidFileType = false;
        String allowedUploadFileTypes =  PaEarPropertyReader.getAllowedUploadFileTypes();
        if (allowedUploadFileTypes != null) {
            int pos =  fileName.lastIndexOf(".");
            String uploadedFileType = fileName.substring(pos + 1, fileName.length());
            StringTokenizer st = new StringTokenizer(allowedUploadFileTypes, ",");        
            while (st.hasMoreTokens()) {
                String allowedFileType = st.nextToken();
                if (allowedFileType.equalsIgnoreCase(uploadedFileType)) {
                    isValidFileType = true;
                    break;
                }
            }
        }        
        return isValidFileType;

    }
    
    /**
     * check if the email address is valid.
     * @param emailAddress emailAddress
     * @return boolean
     */
    public  static boolean isValidEmailAddress(String emailAddress)  {
        boolean isvalidEmailAddr = false;
        Pattern email = Pattern.compile("^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$");

        Matcher fit = email.matcher(emailAddress);
        if (fit.matches()) {
            isvalidEmailAddr = true;
        } 
        return isvalidEmailAddr;
    }
    
    /**
     * check if the date is of valid format.
     * @param dateString dateString
     * @return boolean
     */
    public static boolean isValidDate(String dateString) { 
        //set the format to use as a constructor argument   
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");        
        if (dateString.trim().length() != dateFormat.toPattern().length())  {    
            return false;
        }
        dateFormat.setLenient(false);       
        try {      
            //parse the date    
            dateFormat.parse(dateString.trim());   
        } catch (ParseException pe) {    
            return false;    
        }    
        return true;  
   }
   /**
    * validates the phone number format.
    * @param phoneNumber phoneNumber
    * @return boolean
    */
   public static boolean isValidPhoneNumber(String phoneNumber) {
        boolean isValidPhoneNumber = false;
        String numPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";
        if (phoneNumber.matches(numPattern)) {
            isValidPhoneNumber = true;
        }       
        return isValidPhoneNumber;
   }

}
