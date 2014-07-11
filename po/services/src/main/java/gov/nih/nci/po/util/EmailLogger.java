/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The nci-commons
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This nci-commons Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the nci-commons Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the nci-commons Software; (ii) distribute and
 * have distributed to and by third parties the nci-commons Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package gov.nih.nci.po.util;

import gov.nih.nci.po.service.external.CtepImportServiceBean;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.CyclicBuffer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 
 * @author gax
 */
// CHECKSTYLE:OFF
public final class EmailLogger extends AppenderSkeleton { // NOPMD

    private static final int BUFF_SIZE = 1024;

    /**
     * Name of the SMTP logger in po-log4j.xml.
     */
    public static final String LOGGER_NAME = "EMAIL_ERROR_LOGGER";

    /**
     * SMTP logger.
     */
    public static final Logger LOG = Logger.getLogger(LOGGER_NAME);

    static {

        try {
            EmailLogger smtpAppender = new EmailLogger();
            smtpAppender.setBufferSize(BUFF_SIZE);
            smtpAppender.setSMTPHost(CtepImportServiceBean.getConfig()
                    .getProperty("log.smtp.host"));
            smtpAppender.setFrom(CtepImportServiceBean.getConfig().getProperty(
                    "log.smtp.from"));
            smtpAppender.setTo(CtepImportServiceBean.getConfig().getProperty(
                    "log.smtp.to"));
            smtpAppender.setSubject("CTEP message processing error");
            smtpAppender.setLayout(new PatternLayout(
                    "[%d{ISO8601}]%n%n%-5p%n%n%c%n%n%m%n%n"));
            smtpAppender.setThreshold(Priority.ERROR);
            smtpAppender.activateOptions();
            LOG.addAppender(smtpAppender);
        } catch (Exception e) {
            LOG.error(e, e);
        }
    }

    private String to;
    /**
     * Comma separated list of cc recipients.
     */
    private String cc;
    /**
     * Comma separated list of bcc recipients.
     */
    private String bcc;
    private String from;
    private String subject;
    private String smtpHost;
    private String smtpUsername;
    private String smtpPassword;
    private boolean smtpDebug = false;
    private int bufferSize = 512;
    private boolean locationInfo = false;

    protected CyclicBuffer cb = new CyclicBuffer(bufferSize); // NOPMD
    protected Message msg; // NOPMD

    protected TriggeringEventEvaluator evaluator; // NOPMD

    /**
     * The default constructor will instantiate the appender with a
     * {@link TriggeringEventEvaluator} that will trigger on events with level
     * ERROR or higher.
     */
    private EmailLogger() {
        this(new DefaultEvaluator());
    }

    /**
     * Use <code>evaluator</code> passed as parameter as the
     * {@link TriggeringEventEvaluator} for this SMTPAppender.
     */
    public EmailLogger(TriggeringEventEvaluator evaluator) {
        this.evaluator = evaluator;
    }

    /**
     * Activate the specified options, such as the smtp host, the recipient,
     * from, etc.
     */
    public void activateOptions() {
        Session session = createSession();
        msg = new MimeMessage(session);

        try {
            addressMessage(msg);
            if (subject != null) {
                msg.setSubject(subject);
            }
        } catch (MessagingException e) {
            LogLog.error("Could not activate SMTPAppender options.", e);
        }
    }

    /**
     * Address message.
     * 
     * @param msg
     *            message, may not be null.
     * @throws MessagingException
     *             thrown if error addressing message.
     */
    protected void addressMessage(final Message msg) throws MessagingException { // NOPMD
        if (from != null) {
            msg.setFrom(getAddress(from));
        } else {
            msg.setFrom();
        }

        if (to != null && to.length() > 0) {
            msg.setRecipients(Message.RecipientType.TO, parseAddress(to));
        }

        // Add CC receipients if defined.
        if (cc != null && cc.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, parseAddress(cc));
        }

        // Add BCC receipients if defined.
        if (bcc != null && bcc.length() > 0) {
            msg.setRecipients(Message.RecipientType.BCC, parseAddress(bcc));
        }
    }

    /**
     * Create mail session.
     * 
     * @return mail session, may not be null.
     */
    protected Session createSession() { // NOPMD
        Properties props = null;
        try {
            props = new Properties(System.getProperties());
        } catch (SecurityException ex) {
            props = new Properties();
        }
        if (smtpHost != null) {
            props.put("mail.smtp.host", smtpHost);
        }

        Authenticator auth = null;
        if (smtpPassword != null && smtpUsername != null) {
            props.put("mail.smtp.auth", "true");
            auth = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(smtpUsername,
                            smtpPassword);
                }
            };
        }
        Session session = Session.getInstance(props, auth);
        if (smtpDebug) {
            session.setDebug(smtpDebug);
        }
        return session;
    }

    /**
     * Perform SMTPAppender specific appending actions, mainly adding the event
     * to a cyclic buffer and checking if the event triggers an e-mail to be
     * sent.
     */
    public void append(LoggingEvent event) {

        if (!checkEntryConditions()) {
            return;
        }

        event.getThreadName();
        event.getNDC();
        event.getMDCCopy();
        if (locationInfo) {
            event.getLocationInformation();
        }
        cb.add(event);
        if (evaluator.isTriggeringEvent(event)) {
            sendBuffer();
        }
    }

    /**
     * This method determines if there is a sense in attempting to append.
     * 
     * <p>
     * It checks whether there is a set output target and also if there is a set
     * layout. If these checks fail, then the boolean value <code>false</code>
     * is returned.
     */
    protected boolean checkEntryConditions() {
        if (this.msg == null) {
            errorHandler.error("Message object not configured.");
            return false;
        }

        if (this.evaluator == null) {
            errorHandler
                    .error("No TriggeringEventEvaluator is set for appender ["
                            + name + "].");
            return false;
        }

        if (this.layout == null) {
            errorHandler.error("No layout set for appender named [" + name
                    + "].");
            return false;
        }
        return true;
    }

    synchronized public void close() {
        this.closed = true;
    }

    InternetAddress getAddress(String addressStr) {
        try {
            return new InternetAddress(addressStr);
        } catch (AddressException e) {
            errorHandler.error("Could not parse address [" + addressStr + "].",
                    e, ErrorCode.ADDRESS_PARSE_FAILURE);
            return null;
        }
    }

    InternetAddress[] parseAddress(String addressStr) {
        try {
            return InternetAddress.parse(addressStr, true);
        } catch (AddressException e) {
            errorHandler.error("Could not parse address [" + addressStr + "].",
                    e, ErrorCode.ADDRESS_PARSE_FAILURE);
            return null;
        }
    }

    /**
     * Returns value of the <b>To</b> option.
     */
    public String getTo() {
        return to;
    }

    /**
     * The <code>SMTPAppender</code> requires a {@link org.apache.log4j.Layout
     * layout}.
     */
    public boolean requiresLayout() {
        return true;
    }

    /**
     * Send the contents of the cyclic buffer as an e-mail message.
     */
    protected void sendBuffer() { // NOPMD

        // Note: this code already owns the monitor for this
        // appender. This frees us from needing to synchronize on 'cb'.
        try {
            MimeBodyPart part = new MimeBodyPart();

            StringBuffer sbuf = new StringBuffer();
            String t = layout.getHeader();
            if (t != null) // NOPMD
                sbuf.append(t);
            int len = cb.length();
            for (int i = 0; i < len; i++) {
                // sbuf.append(MimeUtility.encodeText(layout.format(cb.get())));
                LoggingEvent event = cb.get();
                sbuf.append(layout.format(event));
                if (layout.ignoresThrowable()) {
                    String[] s = event.getThrowableStrRep();
                    if (s != null) {
                        for (int j = 0; j < s.length; j++) {
                            sbuf.append(s[j]);
                            sbuf.append(Layout.LINE_SEP);
                        }
                    }
                }
            }
            t = layout.getFooter();
            if (t != null) // NOPMD
                sbuf.append(t);
            part.setContent(sbuf.toString(), layout.getContentType());

            Multipart mp = new MimeMultipart();
            mp.addBodyPart(part);
            msg.setContent(mp);

            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (Exception e) {
            LogLog.error("Error occured while sending e-mail notification.", e);
        }
    }

    /**
     * Returns value of the <b>EvaluatorClass</b> option.
     */
    public String getEvaluatorClass() {
        return evaluator == null ? null : evaluator.getClass().getName();
    }

    /**
     * Returns value of the <b>From</b> option.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns value of the <b>Subject</b> option.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * The <b>From</b> option takes a string value which should be a e-mail
     * address of the sender.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * The <b>Subject</b> option takes a string value which should be a the
     * subject of the e-mail message.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * The <b>BufferSize</b> option takes a positive integer representing the
     * maximum number of logging events to collect in a cyclic buffer. When the
     * <code>BufferSize</code> is reached, oldest events are deleted as new
     * events are added to the buffer. By default the size of the cyclic buffer
     * is 512 events.
     */
    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
        cb.resize(bufferSize);
    }

    /**
     * The <b>SMTPHost</b> option takes a string value which should be a the
     * host name of the SMTP server that will send the e-mail message.
     */
    public void setSMTPHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    /**
     * Returns value of the <b>SMTPHost</b> option.
     */
    public String getSMTPHost() {
        return smtpHost;
    }

    /**
     * The <b>To</b> option takes a string value which should be a comma
     * separated list of e-mail address of the recipients.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Returns value of the <b>BufferSize</b> option.
     */
    public int getBufferSize() {
        return bufferSize;
    }

    /**
     * The <b>EvaluatorClass</b> option takes a string value representing the
     * name of the class implementing the {@link TriggeringEventEvaluator}
     * interface. A corresponding object will be instantiated and assigned as
     * the triggering event evaluator for the SMTPAppender.
     */
    public void setEvaluatorClass(String value) {
        evaluator = (TriggeringEventEvaluator) OptionConverter
                .instantiateByClassName(value, TriggeringEventEvaluator.class,
                        evaluator);
    }

    /**
     * The <b>LocationInfo</b> option takes a boolean value. By default, it is
     * set to false which means there will be no effort to extract the location
     * information related to the event. As a result, the layout that formats
     * the events as they are sent out in an e-mail is likely to place the wrong
     * location information (if present in the format).
     * 
     * <p>
     * Location information extraction is comparatively very slow and should be
     * avoided unless performance is not a concern.
     */
    public void setLocationInfo(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    /**
     * Returns value of the <b>LocationInfo</b> option.
     */
    public boolean getLocationInfo() { // NOPMD
        return locationInfo;
    }

    /**
     * Set the cc recipient addresses.
     * 
     * @param addresses
     *            recipient addresses as comma separated string, may be null.
     */
    public void setCc(final String addresses) {
        this.cc = addresses;
    }

    /**
     * Get the cc recipient addresses.
     * 
     * @return recipient addresses as comma separated string, may be null.
     */
    public String getCc() {
        return cc;
    }

    /**
     * Set the bcc recipient addresses.
     * 
     * @param addresses
     *            recipient addresses as comma separated string, may be null.
     */
    public void setBcc(final String addresses) {
        this.bcc = addresses;
    }

    /**
     * Get the bcc recipient addresses.
     * 
     * @return recipient addresses as comma separated string, may be null.
     */
    public String getBcc() {
        return bcc;
    }

    /**
     * The <b>SmtpPassword</b> option takes a string value which should be the
     * password required to authenticate against the mail server.
     * 
     * @param password
     *            password, may be null.
     */
    public void setSMTPPassword(final String password) {
        this.smtpPassword = password;
    }

    /**
     * The <b>SmtpUsername</b> option takes a string value which should be the
     * username required to authenticate against the mail server.
     * 
     * @param username
     *            user name, may be null.
     */
    public void setSMTPUsername(final String username) {
        this.smtpUsername = username;
    }

    /**
     * Setting the <b>SmtpDebug</b> option to true will cause the mail session
     * to log its server interaction to stdout. This can be useful when debuging
     * the appender but should not be used during production because username
     * and password information is included in the output.
     * 
     * @param debug
     *            debug flag.
     */
    public void setSMTPDebug(final boolean debug) {
        this.smtpDebug = debug;
    }

    /**
     * Get SMTP password.
     * 
     * @return SMTP password, may be null.
     */
    public String getSMTPPassword() {
        return smtpPassword;
    }

    /**
     * Get SMTP user name.
     * 
     * @return SMTP user name, may be null.
     */
    public String getSMTPUsername() {
        return smtpUsername;
    }

    /**
     * Get SMTP debug.
     * 
     * @return SMTP debug flag.
     */
    public boolean getSMTPDebug() { // NOPMD
        return smtpDebug;
    }
}

class DefaultEvaluator implements TriggeringEventEvaluator {
    /**
     * Is this <code>event</code> the e-mail triggering event?
     * 
     * <p>
     * This method returns <code>true</code>, if the event level has ERROR level
     * or higher. Otherwise it returns <code>false</code>.
     */
    public boolean isTriggeringEvent(LoggingEvent event) {
        return event.getLevel().isGreaterOrEqual(Level.ERROR);
    }
}
