/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.service.PAException;

import java.io.File;
import java.util.Collection;

/**
 * @author Bala Nair
 * @since 04/14/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public interface MailManagerService {

    /**
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException PAException
     */
    void sendTSREmail(Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    void sendAmendNotificationMail(Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @param unmatchedEmails email addresses that did not match any registry users during trial registration
     * @throws PAException ex
     */
    void sendNotificationMail(Ii studyProtocolIi, Collection<String> unmatchedEmails) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @param rejectReason re
     * @throws PAException ex
     */
    void sendAmendRejectEmail(Ii studyProtocolIi, String rejectReason) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    void sendRejectionEmail(Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @throws PAException e
     */
    void sendAcceptEmail(Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    void sendUpdateNotificationMail(Ii studyProtocolIi) throws PAException;

    /**
     * Send cde request mail.
     * 
     * @param mailFrom the mail from
     * @param mailBody the mail body
     * 
     * @throws PAException the PA exception
     */
    void sendCDERequestMail(String mailFrom, String mailBody) throws PAException;

    /**
     * Sends a CDE request mail for markers.
     * @param studyProtocolIi The ii of the study protocol this marker is meant for
     * @param from the from address
     * @param marker the marker containing the name and HUGO code if specified
     * @param markerText the marker text as written in the proposal
     * @throws PAException on error
     */
    void sendMarkerCDERequestMail(Ii studyProtocolIi, String from, PlannedMarkerDTO marker, String markerText)
            throws PAException;

    /**
     * Sends a new marker acceptance email to CDE.
     * @param nciIdentifier nciIdentifier
     * @param from the from address
     * @param marker the marker containing the name and HUGO code if specified
     * @throws PAException on error
     */
    void sendMarkerAcceptanceMailToCDE(String nciIdentifier, String from, PlannedMarkerDTO marker) throws PAException;

    /**
     * Sends a CDE request mail for markers.
     * @param nciIdentifier nciIdentifier
     * @param to address
     * @param marker the marker containing the name and HUGO code if specified
     * @param question marker question submitted by logged in user.
     * @throws PAException on error
     */
    void sendMarkerQuestionToCTROMail(String nciIdentifier, String to, 
            PlannedMarkerDTO marker, String question) throws PAException;
    
    /**
     * 
     * @param mailTo to
     * @param subject subject
     * @param mailBody body
     * @param attachments attach
     */
    void sendMailWithAttachment(String mailTo, String subject, String mailBody, File[] attachments);

    /**
     * Send xml and tsr email.
     * 
     * @param fullName name of the recepient
     * @param mailTo who to send the xml and tsr email to
     * @param studyProtocolIi the study protocol ii
     * 
     * @throws PAException the PA exception
     */
    void sendXMLAndTSREmail(String fullName, String mailTo, Ii studyProtocolIi) throws PAException;

    /**
     * 
     * @param userId userId
     */
    void sendAdminAcceptanceEmail(Long userId);

    /**
     * 
     * @param userId user
     * @param reason rejection reason
     */
    void sendAdminRejectionEmail(Long userId, String reason);
    
    /**
     * Search a registry user by email and send the username e-mail.
     * @param emailAddress The e-mail address to search for and to which the e-mail must be sent.
     * @return true if a user name was found.
     * @throws PAException when an error occurs.
     */
    boolean sendSearchUsernameEmail(String emailAddress) throws PAException;
    
    /**
     * Sends an email to CTRO warning about a trial registration, for which incorrect owner email addresses
     * were provided.
     * @param studyProtocolId study ID
     * @param emails bad emails
     * @throws PAException exception
     */
    void sendUnidentifiableOwnerEmail(Long studyProtocolId,
            Collection<String> emails) throws PAException;
    
    
    /**
     * Sends an email with text/html content.
     * 
     * @param mailTo to
     * @param subject subject
     * @param mailBody body
     */
    void sendMailWithHtmlBody(String mailTo, String subject, String mailBody);

}
