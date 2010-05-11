/**
 * 
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;

import java.io.File;

/**
 * @author Vrushali
 *
 */
public class MockMailManagerService implements MailManagerServiceLocal {

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

    public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendUpdateNotificationMail(gov.nih.nci.iso21090.Ii)
     */
    public void sendUpdateNotificationMail(Ii studyProtocolIi)
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

    public void sendChangeOwnershipMail(String prevOwnerMailId,
            Ii studyProtocolIi) {
        // TODO Auto-generated method stub
        
    }

	public void sendMailWithAttachment(String mailTo, String subject,
			String mailBody, File[] attachments) {
		// TODO Auto-generated method stub
		
	}

    public void sendXMLAndTSREmail(Ii studyProtocolIi) throws PAException {
        // TODO Auto-generated method stub
        
    }
}
