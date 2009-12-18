/**
 * 
 */
package gov.nih.nci.registry.mail;

import gov.nih.nci.registry.action.AbstractRegWebTest;

import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MailManagerTest extends AbstractRegWebTest {
    private MailManager mail = new MailManager();
    @Test
    public void testSendMailWithAattchement() {
        mail.sendMailWithAattchement("mailTo", "mailCC", "mailBody", "subject", "attachFileName");
    }
}
