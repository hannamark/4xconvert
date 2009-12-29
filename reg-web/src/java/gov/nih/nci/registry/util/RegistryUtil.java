/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * @author Bala Nair
 *
 */
@SuppressWarnings("PMD")
public class RegistryUtil {
    private static final Logger LOG = Logger.getLogger(RegistryUtil.class);
    
    /**
     * check if the uploaded file type is valid.
     * @param fileName filename
     * @return boolean
     */
    public static boolean isValidFileType(String fileName) {
        boolean isValidFileType = false;
        try {
            String allowedUploadFileTypes = PaRegistry
                            .getLookUpTableService().getPropertyValue(
                                            "allowed.uploadfile.types");
            if (allowedUploadFileTypes != null) {
                int pos = fileName.lastIndexOf(".");
                String uploadedFileType = fileName.substring(pos + 1, fileName
                        .length());
                StringTokenizer st = new StringTokenizer(
                        allowedUploadFileTypes, ",");
                while (st.hasMoreTokens()) {
                    String allowedFileType = st.nextToken();
                    if (allowedFileType.equalsIgnoreCase(uploadedFileType)) {
                        isValidFileType = true;
                        break;
                    }
                }
            }
        } catch (PAException pae) {
            LOG.error("Error occured while retrieving allowed file types from database " + pae.getMessage());
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
        if (PAUtil.isEmpty(dateString)) {
            return false;
        }
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
        if (phoneNumber != null && phoneNumber.trim().length() >= Constants.MIN_PHONE_NUM_LENGTH) {
            Pattern numberPattern = Pattern.compile("^([\\w\\s\\-\\.\\+\\(\\)])*$");
            Matcher fit = numberPattern.matcher(phoneNumber);
            if (fit.matches()) {
                isValidPhoneNumber = true;
            }
        }
        return isValidPhoneNumber;
   }
   
   /**
    * validates the upload file type against the passed file type.
    * @param fileName filename
    * @param allowedFileType allowedFileType
    * @return boolean
    */
   public static boolean isValidFileType(String fileName, String allowedFileType) {
       boolean isValidFileType = false;
       int pos = fileName.lastIndexOf(".");
       String uploadedFileType = fileName.substring(pos + 1, fileName.length());
       if (uploadedFileType != null 
               && allowedFileType.equalsIgnoreCase(uploadedFileType)) {
           isValidFileType = true;
       }
       return isValidFileType;

   }

}
