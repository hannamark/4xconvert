/**
 * 
 */
package gov.nih.nci.service;

import java.io.File;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;

/**
 * @author Vrushali
 *
 */
public class MockMailManagerService implements MailManagerServiceLocal {

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAcceptEmail(gov.nih.nci.iso21090.Ii)
     */
    public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAmendAcceptEmail(gov.nih.nci.iso21090.Ii)
     */
    public void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAmendNotificationMail(gov.nih.nci.iso21090.Ii)
     */
    public void sendAmendNotificationMail(Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAmendRejectEmail(gov.nih.nci.iso21090.Ii, java.lang.String)
     */
    public void sendAmendRejectEmail(Ii studyProtocolIi, String rejectReason)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendCDERequestMail(java.lang.String, java.lang.String)
     */
    public void sendCDERequestMail(String mailFrom, String mailBody)
            throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendChangeOwnershipMail(java.lang.String, gov.nih.nci.iso21090.Ii)
     */
    public void sendChangeOwnershipMail(String prevOwnerMailId,
            Ii studyProtocolIi) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendNotificationMail(gov.nih.nci.iso21090.Ii)
     */
    public void sendNotificationMail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendRejectionEmail(gov.nih.nci.iso21090.Ii)
     */
    public void sendRejectionEmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendTSREmail(gov.nih.nci.iso21090.Ii)
     */
    public void sendTSREmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendUpdateNotificationMail(gov.nih.nci.iso21090.Ii)
     */
    public void sendUpdateNotificationMail(Ii studyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub

    }

    public void sendMailWithAttachment(String mailTo, String subject, String mailBody, File [] attachments) {
         // TODO Auto-generated method stub
        
    }

    public void sendXMLAndTSREmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAdminAcceptanceEmail(java.lang.Long)
     */
    public void sendAdminAcceptanceEmail(Long userId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendAdminRejectionEmail(java.lang.Long, java.lang.String)
     */
    public void sendAdminRejectionEmail(Long userId, String reason) {
        // TODO Auto-generated method stub
        
    }

}
