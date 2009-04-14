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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;

/**
 * @author Bala Nair
 * @since 04/14/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MailManagerServiceBean implements MailManagerServiceRemote,
                            MailManagerServiceLocal {
    
    private static final Logger LOG = Logger.getLogger(MailManagerServiceBean.class);
    private static final String TSR = "TSR_";
    private static final String HTML = ".html";
    
    @EJB
    ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    RegistryUserServiceRemote registryUserService;
    @EJB
    CTGovXmlGeneratorServiceRemote ctGovXmlGeneratorService;
    @EJB
    TSRReportGeneratorServiceRemote tsrReportGeneratorService;
    @EJB
    LookUpTableServiceRemote lookUpTableService;

    /**
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException PAException
     */
    @SuppressWarnings({ "PMD.StringInstantiation", "PMD.ExcessiveMethodLength", "PMD.SimpleDateFormatNeedsLocale" })
    public void sendTSREmail(Ii studyProtocolIi) throws PAException {
        LOG.info("Entering sendTSREmail");
        try {
          StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                                          IiConverter.convertToLong(studyProtocolIi));

          RegistryUser registryUser = registryUserService.getUser(spDTO.getUserLastCreated());

          Calendar calendar = new GregorianCalendar();
          Date date = calendar.getTime();
          DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

          String body = lookUpTableService.getPropertyValue("tsr.body");
          body = body.replace("${CurrentDate}", format.format(date));
          body = body.replace("${SubmitterName}", 
                  registryUser.getFirstName() + " " + registryUser.getLastName());
          body = body.replace("${localOrgID}", spDTO.getLeadOrganizationId().toString());
          body = body.replace("${trialTitle}", spDTO.getOfficialTitle().toString());
          body = body.replace("${receiptDate}", format.format(spDTO.getDateLastCreated()));
          body = body.replace("${nciTrialID}", spDTO.getNciIdentifier().toString());
          body = body.replace("${fileName}", TSR 
                                             + spDTO.getNciIdentifier().toString() + HTML);
          body = body.replace("${fileName2}", spDTO.getNciIdentifier().toString() + ".xml");

          protocolQueryService.getTrialSummaryByStudyProtocolId(
              IiConverter.convertToLong(studyProtocolIi));
          String xmlData = ctGovXmlGeneratorService.generateCTGovXml(studyProtocolIi);
          
          String folderPath = PaEarPropertyReader.getDocUploadPath();
          StringBuffer sb  = new StringBuffer(folderPath);
              
          String inputFile = new String(sb.append(File.separator).append(
              spDTO.getNciIdentifier().toString() + ".xml"));
          OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream(inputFile));
          oos.write(xmlData);
          oos.close();

          StringBuffer sb2  = new StringBuffer(folderPath);
          String outputFile = new String(sb2.append(File.separator).append(TSR).append(
              spDTO.getNciIdentifier().toString() + HTML));
          
          //File htmlFile = this.createAttachment(new File(inputFile), new File(outputFile));
          String htmlData = tsrReportGeneratorService.generateTSRHtml(studyProtocolIi);
          OutputStreamWriter oosHtml = new OutputStreamWriter(new FileOutputStream(outputFile));
          oosHtml.write(htmlData);
          oosHtml.close();
          File[] attachments = {new File(inputFile), new File(outputFile)};

          // Send Message
          sendMail(spDTO.getUserLastCreated(), //Mail Recipient
                  lookUpTableService.getPropertyValue("tsr.subject"), // Mail Subject
                  body, // Mail Body
                  attachments); // Mail Attachments if any

          new File(inputFile).delete();
          new File(outputFile).delete();
          
        } catch (Exception e) {
            LOG.error("Exception occured while emailing TSR Report " + e.getLocalizedMessage());
            throw new PAException("Exception occured while sending TSR Report to submitter", e);
        }
        LOG.info("Leaving sendTSREmail");

    }
    
    /**
     * 
     * @param mailTo mailTo
     * @param subject subject
     * @param mailBody mailBody
     * @param attachments File attachments
     */
    private  void sendMail(String mailTo, String subject, 
                              String mailBody, File [] attachments) {
        try {
            // get system properties
            Properties props = System.getProperties();

            // Set up mail server
            props.put("mail.smtp.host", 
                    lookUpTableService.getPropertyValue("smtp"));
            // Get session
            Session session = Session.getDefaultInstance(props, null);

            // Define Message
            MimeMessage message = new MimeMessage(session);
            // body
            Multipart multipart = new MimeMultipart();
            
            message.setFrom(new InternetAddress(lookUpTableService.getPropertyValue("fromaddress")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            message.setSentDate(new java.util.Date());            
            message.setSubject(subject);
            
            BodyPart msgPart = new MimeBodyPart();            
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            if (attachments != null && attachments.length > 0) {
                // Add attachments to message       
                for (File attachment : attachments) {
                  MimeBodyPart attPart = new MimeBodyPart();
                  attPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
                  attPart.setFileName(attachment.getName());
                  multipart.addBodyPart(attPart);
                }                
            }
            message.setContent(multipart);
            // Send Message
            Transport.send(message);
        } catch (Exception e) {
            LOG.error("Send Mail error", e);
        } // catch
    }

}
