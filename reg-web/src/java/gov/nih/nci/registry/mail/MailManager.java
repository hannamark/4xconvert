package gov.nih.nci.registry.mail;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.EncoderDecoder;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 *
 * @author Bala Nair
 * Manages e-mail notifications.
 *
 */
public class MailManager {
    private static final Logger LOG = Logger.getLogger(MailManager.class);
    private static final Properties REG_PROPERTIES = PaEarPropertyReader.getProperties();

    /**
     * Sends an email notifying user to activate their user account.
     * @param mailTo mailTo
     */
    public void sendConfirmationMail(String mailTo) {

        try {
            String[] params = {mailTo , };

            MessageFormat formatterSubject =
                new MessageFormat(PaRegistry.getLookUpTableService().getPropertyValue("user.account.subject"));
            String emailSubject = formatterSubject.format(params);
            LOG.debug("emailSubject is: " + emailSubject);

            MessageFormat formatterBody =
                new MessageFormat(PaRegistry.getLookUpTableService().getPropertyValue("user.account.body"));
            MessageFormat formatterBodyUrl = new MessageFormat(REG_PROPERTIES.getProperty("register.mail.body.url"));

            // encode the loginName and append to the URL before sending the e-mail
            EncoderDecoder encodeDecoder = new EncoderDecoder();

            String emailBody = formatterBody.format(params) + "\n\n"
                    + formatterBodyUrl.format(params) + "?emailAddress="
                    + encodeDecoder.encodeString(mailTo) + "&action=myaccount\n\n";

            LOG.debug("emailBody is: " + emailBody);
            sendMail(mailTo, null, emailBody, emailSubject);
        } catch (Exception e) {
            LOG.error("Send confirmation mail error", e);
        }
    }
   /**
     *
     * @return String
     */
    public String formatFromAddress() {
        String fromEmailAddress = null;
        try {
            String fromAddress = PaRegistry.getLookUpTableService().getPropertyValue("fromaddress");
            fromEmailAddress = new MessageFormat(fromAddress).format(new String[] {fromAddress });
        } catch (PAException e) {
            LOG.error("Error retrieving, from mail address from database for Subission e-mail", e);
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
    public synchronized void sendMail(String mailTo, String mailCC, String mailBody, String subject) {
        try {
            MimeMessage message = prepareMessage(mailTo, subject);
            message.setText(mailBody);
            Transport.send(message);
        } catch (Exception e) {
            LOG.error("Send Mail error", e);
        }
    }

    private MimeMessage prepareMessage(String mailTo, String subject) throws PAException, MessagingException {
        Properties props = System.getProperties();
        String to = mailTo;
        props.put("mail.smtp.host", PaRegistry.getLookUpTableService().getPropertyValue("smtp"));
        Session session = Session.getDefaultInstance(props, null);

        // Define Message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(formatFromAddress()));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        return message;
    }

    /**
     *
     * @param mailTo m
     * @param mailCC m
     * @param mailBody b
     * @param subject s
     * @param attachFileName f
     */
    public void sendMailWithAattchement(String mailTo, String mailCC, String mailBody, String subject,
            String attachFileName) {
        try {
            MimeMessage message = prepareMessage(mailTo, subject);

            // create and fill the message Body
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(mailBody);

            // create the second message part for attachment
            MimeBodyPart mbp2 = new MimeBodyPart();
            // attach the file to the message
            FileDataSource fds = new FileDataSource(attachFileName);
            mbp2.setDataHandler(new DataHandler(fds));
            mbp2.setFileName(fds.getName());

            // create the Multipart and add its parts to it
            Multipart mp = new MimeMultipart();
            mp.addBodyPart(mbp1);
            mp.addBodyPart(mbp2);

            // add the Multipart to the message
            message.setContent(mp);

            message.setSentDate(new Date());
            Transport.send(message);
        } catch (Exception e) {
            LOG.error("Exception sending mail with attachment", e);
        }
    }
}
