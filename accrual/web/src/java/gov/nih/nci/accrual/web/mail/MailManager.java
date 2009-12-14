package gov.nih.nci.accrual.web.mail;

import gov.nih.nci.accrual.util.PoPropertyReader;
import gov.nih.nci.accrual.web.util.EncoderDecoder;
import gov.nih.nci.accrual.web.util.PaServiceLocator;

import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

/**
 * 
 * @author Bala Nair
 * Manages e-mail notifications.
 * 
 */
@SuppressWarnings({ "PMD" })
public class MailManager {

    private static Logger logger = Logger.getLogger(MailManager.class);

    /**
     * Sends an email notifying user to activate their user account.
     * @param mailTo mailTo
     * @param password password
     */
    public void sendConfirmationMail(String mailTo, String password) {

        try {
            String[] params = {mailTo , };

            MessageFormat formatterSubject = new MessageFormat(
                PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("outcomes.email.subject"));
            String emailSubject = formatterSubject.format(params);
            logger.info("emailSubject is: " + emailSubject);

            MessageFormat formatterBody = new MessageFormat(
                PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("outcomes.email.body"));

            MessageFormat formatterBodyUrl = 
                new MessageFormat(PoPropertyReader.getProperties().getProperty("outcomes.url"));            

            // encode the loginName and password and append to the URL before
            // sending the e-mail
            // added new line characters to fix URL link issues when users type
            // in special characters in their passwords
            String url = formatterBodyUrl.format(params)
                + "/userAccountactivate.action?loginName=" + EncoderDecoder.encodeString(mailTo)
                + "&password=" + EncoderDecoder.encodeString(password);
            String emailBody = "<html><body><p>" + formatterBody.format(params) + "</p><p><a href=\"" + url + "\">" 
                + url + "</a></p></body></html>";
            logger.info("emailBody is: " + emailBody);
            sendMail(mailTo, null, emailBody, emailSubject);
        } catch (Exception e) {
            logger.error("Send confirmation mail error", e);
        }
    }
    
    /**
     * 
     * @param mailTo mailTo
     * @param mailCC mailCC
     * @param mailBody mailBody
     * @param subject subject
     */
    private synchronized void sendMail(String mailTo, String mailCC, String mailBody, String subject) {
        try {
            // get system properties
            Properties props = System.getProperties();

            String to = mailTo;
            // Set up mail server

            props.put("mail.smtp.host", 
                      PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("smtp"));

            // Get session
            Session session = Session.getDefaultInstance(props, null);

            // Define Message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(formatFromAddress()));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                    to));

            message.setSubject(subject);            
            if (mailBody.toLowerCase().startsWith("<html>")) {
                message.setContent(mailBody, "text/html");
            } else {
                message.setText(mailBody);
            }

            // Send Message
            Transport.send(message);
        } catch (Exception e) {
            logger.error("Send Mail error", e);
        } // catch
    }
    
    /**
     * 
     * @return String
     */
    private String formatFromAddress() {
        String fromEmailAddress = null;
        try {
            String fromAddress = PaServiceLocator.getInstance().getLookUpTableService().getPropertyValue("fromaddress");
            fromEmailAddress = new MessageFormat(fromAddress).
                                        format(new String[] {fromAddress });
        } catch (Exception e) {
            logger.error("Error retrieving, from mail address from database for Subission e-mail", e);
        }
        
        return fromEmailAddress;
    }
}