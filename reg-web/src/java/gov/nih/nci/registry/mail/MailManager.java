package gov.nih.nci.registry.mail;

import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import gov.nih.nci.registry.util.EncoderDecoder;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;

/**
 * 
 * @author Bala Nair
 * Manages e-mail notifications.
 * 
 */
@SuppressWarnings({ "PMD" })
public class MailManager {

    private static Logger logger = Logger.getLogger(MailManager.class);
    private static Properties regProperties = PaEarPropertyReader.getProperties();

    /**
     * Sends an email notifying user to activate their user account.
     * @param mailTo mailTo
     * @param password password
     */
    public void sendConfirmationMail(String mailTo, String password) {

        try {
            String[] params = {mailTo , };

            MessageFormat formatterSubject = new MessageFormat(
                    RegistryServiceLocator.getLookUpTableService().getPropertyValue("user.account.subject"));
            String emailSubject = formatterSubject.format(params);
            logger.info("emailSubject is: " + emailSubject);

            MessageFormat formatterBody = new MessageFormat(
                    RegistryServiceLocator.getLookUpTableService().getPropertyValue("user.account.body"));
            MessageFormat formatterBodyUrl = new MessageFormat(
                            regProperties.getProperty("register.mail.body.url"));

            // encode the loginName and password and append to the URL before
            // sending the e-mail
            EncoderDecoder encodeDecoder = new EncoderDecoder();
            // added new line characters to fix URL link issues when users type
            // in special characters in their passwords
            String emailBody = formatterBody.format(params) + "\n \n"
                    + formatterBodyUrl.format(params) + "/registerUseractivate.action?loginName="
                    + encodeDecoder.encodeString(mailTo) + "&password="
                    + encodeDecoder.encodeString(password) + "&action=myaccount"
                    + "\n \n";

            logger.info("emailBody is: " + emailBody);
            sendMail(mailTo, null, emailBody, emailSubject);
        } catch (Exception e) {
            logger.error("Send confirmation mail error", e);
        }
    }
    
    /**
     * Sends an email notifying the submitter that the protocol is registered in the system.
     * @param mailTo mailTo
     * @param nciIdentifier nciIdentifier
     * @param localProtocolIdentifier localProtocolIdentifier
     */
    public void sendNotificationMail(
            String mailTo, 
            String nciIdentifier, 
            String localProtocolIdentifier)  {
    
        try {

            String[] params = {mailTo , };
            
            MessageFormat formatterSubject = new MessageFormat(
                    RegistryServiceLocator.getLookUpTableService().getPropertyValue("trial.register.subject"));
            String emailSubject = formatterSubject.format(params);
            logger.info("emailSubject is: " + emailSubject);
            String submissionMailBody = RegistryServiceLocator.getLookUpTableService().
                                    getPropertyValue("trial.register.body");
            submissionMailBody = submissionMailBody.replace("${leadOrgTrialIdentifier} ", localProtocolIdentifier);
            submissionMailBody = submissionMailBody.replace("${nciTrialIdentifier}", nciIdentifier);
            MessageFormat formatterBody = new MessageFormat(submissionMailBody);
            String emailBody =  formatterBody.format(params);

            logger.info("emailBody is: " + emailBody);
            sendMail(mailTo, null, emailBody, emailSubject);
        } catch (Exception e) {
            logger.error("Send submission notification mail error", e);
        }
     }


    /**
     * 
     * @return String
     */
    public String formatFromAddress() {
        String fromEmailAddress = null;
        try {
            String fromAddress = RegistryServiceLocator.getLookUpTableService().
                                                   getPropertyValue("fromaddress");
            fromEmailAddress = new MessageFormat(fromAddress).
                                        format(new String[] {fromAddress });
        } catch (PAException e) {
            logger.error("Error retrieving, from mail address from database for Subission e-mail", e);
        }
        
        return fromEmailAddress;
    }
    
    /**
     * 
     * @param mailTo mailTo
     * @param mailCC mailCC
     * @param mailBody mailBody
     * @param subject subject
     */
    public synchronized void sendMail(String mailTo, String mailCC,
            String mailBody, String subject) {
        try {
            // get system properties
            Properties props = System.getProperties();

            String to = mailTo;
            // Set up mail server

            props.put("mail.smtp.host", 
                    RegistryServiceLocator.getLookUpTableService().getPropertyValue("smtp"));

            // Get session
            Session session = Session.getDefaultInstance(props, null);

            // Define Message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(formatFromAddress()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));

            message.setSubject(subject);
            message.setText(mailBody);

            // Send Message

            Transport.send(message);
        } catch (Exception e) {
            logger.error("Send Mail error", e);
        } // catch
    }
}
