package gov.nih.nci.registry.mail;

/**
 * 
 * @author Bala Nair
 *
 */
@SuppressWarnings({ "PMD" })
public class MailProps {

    private String mailTo;
    private String mailFrom;
    private String body;
    private String subject;
    private String smtp;

    /**
     * 
     * @param mailTo mailTo
     * @param mailFrom mailFrom
     * @param body body
     * @param subject subject
     * @param smtp smtp
     */
    public MailProps(String mailTo, String mailFrom, String body, String subject, String smtp) {
        this.setMailFrom(mailFrom);
        this.setMailTo(mailTo);
        this.setSubject(subject);
        this.setBody(body);
        this.setSmtp(smtp);

    }
    /**
     * default.
     */
    public MailProps() {
    }

    /**
     * 
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * 
     * @param body body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * 
     * @return mailFrom
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * 
     * @param mailFrom mailFrom
     */
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }
    /**
     * 
     * @return mailTo
     */
    public String getMailTo() {
        return mailTo;
    }

    /**
     * 
     * @param mailTo mailTo
     */
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    /**
     * 
     * @return smtp
     */
    public String getSmtp() {
        return smtp;
    }

    /**
     * 
     * @param smtp smtp
     */
    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    /**
     * 
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 
     * @param subject subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
