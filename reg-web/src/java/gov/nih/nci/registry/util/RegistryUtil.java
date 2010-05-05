/**
 * 
 */
package gov.nih.nci.registry.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.mail.MailManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
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
   
   /**
    * Generate batch upload report email.
    * 
    * @param action the action
    * @param userName the user name
    * @param successCount the success count
    * @param failedCount the failed count
    * @param totalCount the total count
    * @param attachFileName the attach file name
    * @param errorMessage the error message
    */
   public static void generateMail(String action,
                                   String userName,  
                                   String successCount,
                                   String failedCount, 
                                   String totalCount, 
                                   String attachFileName,
                                   String errorMessage) {
      final MailManager mailManager = new MailManager();
      try {
          StringBuffer submissionMailBody = new StringBuffer();
          Calendar calendar = new GregorianCalendar();
          Date date = calendar.getTime();
          DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
          
         //get the values of email's subject and email body from the database
         String emailSubject = PaRegistry.getLookUpTableService().getPropertyValue("trial.batchUpload.subject");
         LOG.debug("emailSubject is: " + emailSubject);         
         
         //add the mail header
         String submissionMailBodyHeader = PaRegistry.getLookUpTableService().
                  getPropertyValue("trial.batchUpload.bodyHeader");
         submissionMailBodyHeader = submissionMailBodyHeader.replace("${SubmitterName}", getSumitterFullName(userName));
         submissionMailBodyHeader = submissionMailBodyHeader.replace("${CurrentDate}", format.format(date));
         submissionMailBody.append(submissionMailBodyHeader);
         //append the body text for processed or error
         String submissionMailBodyText = "";
         if (Constants.PROCESSED.equals(action)) {
            submissionMailBodyText = PaRegistry.getLookUpTableService().getPropertyValue("trial.batchUpload.body");
            submissionMailBodyText = submissionMailBodyText.replace("${totalCount}", totalCount);
            submissionMailBodyText = submissionMailBodyText.replace("${successCount}", successCount);
            submissionMailBodyText = submissionMailBodyText.replace("${failedCount}", failedCount);

            submissionMailBody.append(submissionMailBodyText);

            String submissionMailReportBody = PaRegistry.getLookUpTableService().
            getPropertyValue("trial.batchUpload.reportMsg");
            submissionMailBody.append("\n").append(submissionMailReportBody);
         } else {
            submissionMailBody.append("Error: ").append(errorMessage).append("\n");  
            String submissionMailErrorBody = PaRegistry.getLookUpTableService().
               getPropertyValue("trial.batchUpload.errorMsg");
            String currentReleaseNumber = PaRegistry.getLookUpTableService().
               getPropertyValue("current.release.no");
            submissionMailErrorBody = submissionMailErrorBody.replace("${ReleaseNumber}", currentReleaseNumber);
            submissionMailBody.append(submissionMailErrorBody);
         }
        //append the footer
        String submissionMailBodyFooter = PaRegistry.getLookUpTableService().
                getPropertyValue("trial.batchUpload.bodyFooter");
        submissionMailBody.append(submissionMailBodyFooter);

        String emailBody =  submissionMailBody.toString();

        if (!StringUtils.isEmpty(attachFileName)) {
          // Send the batch upload report to the submitter
          mailManager.sendMailWithAattchement(userName, null, emailBody, emailSubject, attachFileName);
        } else {
         // Send the batch upload Error to the submitter
         mailManager.sendMail(userName, null, emailBody, emailSubject);
        }

      } catch (PAException e) {
         LOG.error("Error occured while generating the batch upload email " + e.getMessage());
      }
  }

  /**
   * Gets the sumitter full name.
   * 
   * @param userLastCreated the user last created
   * 
   * @return the sumitter full name
   * 
   * @throws PAException the PA exception
   */
  public static String getSumitterFullName(String userLastCreated) throws PAException {
    RegistryUser registryUser = PaRegistry.getRegisterUserService().getUser(userLastCreated);
    return  registryUser.getFirstName() + " " + registryUser.getLastName();
  }
   

}
