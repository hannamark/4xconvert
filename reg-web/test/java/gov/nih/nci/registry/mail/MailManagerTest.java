/**
 *
 */
package gov.nih.nci.registry.mail;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.registry.action.AbstractRegWebTest;
import gov.nih.nci.registry.service.MockLookUpTableService;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MailManagerTest extends AbstractRegWebTest {
    private final MailManager mail = new MailManager();

    @Test
    public void testSendMailWithAattchement() {
        mail.sendMailWithAattchement("mailTo", "mailCC", "mailBody", "subject", "attachFileName");
    }

    @Test
    public void prepareMessage() throws Exception {
        MimeMessage result = mail.prepareMessage("to", "subject");
        Address from = result.getFrom()[0];
        Address to = result.getRecipients(Message.RecipientType.TO)[0];
        Address bcc = result.getRecipients(Message.RecipientType.BCC)[0];
        assertEquals(MockLookUpTableService.FROM_ADDRESS, from.toString());
        assertEquals("to", to.toString());
        assertEquals(MockLookUpTableService.LOG_ADDRESS, bcc.toString());
        assertEquals("subject", result.getSubject());
    }
}
