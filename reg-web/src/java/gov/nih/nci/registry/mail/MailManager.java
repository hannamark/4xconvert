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
                            regProperties.getProperty("register.mail.subject"));
            String emailSubject = formatterSubject.format(params);
            logger.info("emailSubject is: " + emailSubject);

            MessageFormat formatterBody = new MessageFormat(
                            regProperties.getProperty("register.mail.body"));
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
            // TODO hard coding the submission notification e-mail content is a short-term fix for
            // problem with new line characters stripped out of the build.properties during build
            // have to find a permanent fix.
            String para1 = "You have successfully submitted the following protocol to the NCI "
                    + "Clinical Trials Reporting Office (CTRO):\nLead Organization Trial Identifier";
            String para2 = "This protocol has been assigned a unique NCI number for tracking which you "
                    + "may need to reference in future correspondence with the CTRO:\nNCI Trial Identifier";
            String para3 = "Shortly we will send you a Trial Summary Report which will contain key elements "
                    + "that we have abstracted from the protocol.\nWe request that you review these elements for "
                    + "accuracy and reply with your assessment. If you have questions, you may call the NCI " 
                    + "CTRO Office at 301-496-0001 or send an e-mail to TKTK@nci.nih.gov." 
                    + "\nThank you for your submission.";
            String[] params = {mailTo , };
            
            MessageFormat formatterSubject = new MessageFormat(
                                            regProperties.getProperty("submission.mail.subject"));
            String emailSubject = formatterSubject.format(params);
            logger.info("emailSubject is: " + emailSubject);

            MessageFormat formatterBody = new MessageFormat(
                    para1
                    + " " + localProtocolIdentifier + "\n \n" 
                    + para2
                    + "  " + nciIdentifier + "\n \n" 
                    +  para3);
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
        String fromAddress = new MessageFormat(regProperties.getProperty("mail.from.address")).
                            format(new String[] {regProperties.getProperty("mail.from.address") });
        return fromAddress;
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
                    regProperties.getProperty("mail.smtp.host"));

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
