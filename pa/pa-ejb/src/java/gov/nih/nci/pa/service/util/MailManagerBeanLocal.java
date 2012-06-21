/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyOnhold;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xml.serialize.LineSeparator;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(PaHibernateSessionInterceptor.class)
public class MailManagerBeanLocal implements MailManagerServiceLocal {

    private static final String USER_NAME = "${name}";
    private static final String SEND_MAIL_ERROR = "Send Mail error";
    private static final String DEADLINE = "${Deadline}";
    private static final String HOLD_REASON = "${HoldReason}";
    private static final String HOLD_DATE = "${HoldDate}";
    private static final String DATE_PATTERN = "MM/dd/yyyy";
    private static final Logger LOG = Logger.getLogger(MailManagerBeanLocal.class);
    private static final int LINE_WIDTH = 65;
    private static final String TSR = "TSR_";
    private static final String EXTENSION_RTF = ".rtf";
    private static final String EXTENSION_HTML = ".html";
    private static final String CURRENT_DATE = "${CurrentDate}";
    private static final String NCI_TRIAL_IDENTIFIER = "${nciTrialIdentifier}";
    private static final String OWNER_NAME = "${SubmitterName}";
    private static final String LEAD_ORG_TRIAL_IDENTIFIER = "${leadOrgTrialIdentifier}";
    private static final String RECEIPT_DATE = "${receiptDate}";
    private static final String TRIAL_TITLE = "${trialTitle}";
    private static final String AMENDMENT_NUMBER = "${amendmentNumber}";
    private static final String AMENDMENT_DATE = "${amendmentDate}";
    private static final String USERNAME_SEARCH_BODY_PROPERTY = "user.usernameSearch.body";
    private static final String USERNAME_SEARCH_SUBJECT_PROPERTY = "user.usernameSearch.subject";
    private static final String ERRORS = "${errors}";
    private static final int SMTP_TIMEOUT = 120000;
    private static final String CDE_REQUEST_TO_EMAIL = "CDE_REQUEST_TO_EMAIL";
    private static final int ERROR_MSG_LENGTH = 12;
    private static final String SIR_OR_MADAM = "Sir or Madam";

    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService;
    @EJB
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkflStatusSrv;
    @EJB
    private StudySiteServiceLocal studySiteService;
    
    private final ExecutorService mailDeliveryExecutor = Executors
            .newSingleThreadExecutor();

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendTSREmail(Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO =
                protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

            String body = "";
            String amendNumber = "";
            if (spDTO.getAmendmentNumber() != null) {
                amendNumber = spDTO.getAmendmentNumber();
            }
            if (spDTO.getAmendmentDate() != null) {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.amend.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.amend.body");
                }
            } else if (spDTO.isProprietaryTrial()) {
                body = lookUpTableService.getPropertyValue("tsr.proprietary.body");
            } else {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.body");
                }
            }
            body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
            body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
            body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle().toString());
            body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));
            body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
            body = body.replace("${fileName}", TSR + spDTO.getNciIdentifier() + EXTENSION_RTF);
            if (!spDTO.isProprietaryTrial()) {
                body = body.replace("${fileName2}", spDTO.getNciIdentifier() + ".xml");
            }

            body = body.replace(AMENDMENT_NUMBER, amendNumber);
            if (spDTO.getAmendmentDate() != null) {
                body = body.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
            }

            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            StringBuffer sb2 = new StringBuffer(folderPath);
            StringBuffer sb3 = new StringBuffer(folderPath);
            String rtfTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb2, EXTENSION_RTF);
            String htmlTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb3, EXTENSION_HTML);
            String mailSubject = "";

            if (spDTO.isProprietaryTrial()) {
                File[] attachments = {new File(rtfTsrFile), new File(htmlTsrFile)};
                mailSubject = lookUpTableService.getPropertyValue("tsr.proprietary.subject");
                sendEmail(spDTO, body, attachments, mailSubject, false);
                new File(rtfTsrFile).delete();
                new File(htmlTsrFile).delete();
            } else if (BooleanUtils.isTrue(spDTO.getCtgovXmlRequiredIndicator())) {
                String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
                File[] attachments = {new File(xmlFile), new File(rtfTsrFile), new File(htmlTsrFile)};

                if (spDTO.getAmendmentDate() != null) {
                    mailSubject = lookUpTableService.getPropertyValue("tsr.amend.subject");
                } else {
                    mailSubject = lookUpTableService.getPropertyValue("tsr.subject");
                }
                sendEmail(spDTO, body, attachments, mailSubject, false);
                new File(rtfTsrFile).delete();
                new File(htmlTsrFile).delete();
                new File(xmlFile).delete();
            } else {
                File[] attachments = {new File(rtfTsrFile), new File(htmlTsrFile)};

                if (spDTO.getAmendmentDate() != null) {
                    mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.amend.subject");
                } else {
                    mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.subject");
                }
                sendEmail(spDTO, body, attachments, mailSubject, false);
                new File(rtfTsrFile).delete();
                new File(htmlTsrFile).delete();
            }

        } catch (Exception e) {
            throw new PAException("Exception occured while sending TSR Report to submitter", e);
        }
    }

    private String getTSRFile(Ii studyProtocolID, String nciIdentifier, StringBuffer pathToFile, String format)
        throws PAException {

        String tsrFile =
            pathToFile.append(File.separator).append(TSR).append(nciIdentifier).append(format).toString();
        ByteArrayOutputStream tsrStream = null;
        OutputStream tsrFileStream = null;
        try {
            if (StringUtils.equals(format, EXTENSION_RTF)) {
                tsrStream = tsrReportGeneratorService.generateRtfTsrReport(studyProtocolID);
            } else if (StringUtils.equals(format, EXTENSION_HTML)) {
                tsrStream = tsrReportGeneratorService.generateHtmlTsrReport(studyProtocolID);
            }
            tsrFileStream = new FileOutputStream(tsrFile);
            tsrStream.writeTo(tsrFileStream);
        } catch (Exception e) {
            throw new PAException("Exception occured while getting TSR Report to submitter", e);
        } finally {
            IOUtils.closeQuietly(tsrFileStream);
        }
        return tsrFile;
    }

    private String getXmlFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO, StringBuffer sb) throws PAException {
        String xmlFile = new String(sb.append(File.separator).append(spDTO.getNciIdentifier().toString() + ".xml"));
        try {
            if (!spDTO.isProprietaryTrial()) {

                // Format the xml only for non proprietary
                String xmlData = format(ctGovXmlGeneratorService.generateCTGovXml(studyProtocolIi));
                OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream(xmlFile));
                oos.write(xmlData);
                oos.close();
            }
        } catch (Exception e) {
            throw new PAException("Exception occured while getting XmlFile to submitter", e);
        }
        return xmlFile;
    }

    private String format(String unformattedXml) {
        Writer out = new StringWriter();
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(LINE_WIDTH);
            format.setEncoding("UTF-8");
            format.setIndenting(true);
            format.setIndent(2);
            format.setLineSeparator(LineSeparator.Web);
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return out.toString();
    }

    private Document parseXmlFile(String in) {
        DocumentBuilder db = null;
        Document doc = null;
        InputSource is = null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            db = dbf.newDocumentBuilder();
            is = new InputSource(new StringReader(in));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (SAXException e) {
            LOG.error(e.getLocalizedMessage());
        } catch (IOException e) {
            LOG.error(e.getLocalizedMessage());
        }
        return doc;
    }

    /**
     *
     * @param mailTo mailTo
     * @param subject subject
     * @param mailBody mailBody
     * @param attachments File attachments
     */
    @Override
    public void sendMailWithAttachment(String mailTo, String subject, String mailBody, File[] attachments) {
        try {
            String mailFrom = lookUpTableService.getPropertyValue("fromaddress");
            sendMailWithAttachment(mailTo, mailFrom, subject, mailBody, attachments, false);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    @SuppressWarnings("PMD.ExcessiveParameterList")
    private void sendMailWithAttachment(String mailTo, String mailFrom, String subject, String mailBody,
            File[] attachments, boolean async) {
        try {
            // Define Message
            MimeMessage message = prepareMessage(mailTo, mailFrom, subject);
            // body
            Multipart multipart = new MimeMultipart();
            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            if (!ArrayUtils.isEmpty(attachments)) {
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
            if (!async) {
                Transport.send(message);
            } else {
                invokeTransportAsync(message);
            }
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    
    private void invokeTransportAsync(final MimeMessage message) {
        mailDeliveryExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Transport.send(message);
                } catch (Exception e) {
                    LOG.error(SEND_MAIL_ERROR, e);
                }
            }
        });
    }

    /**
     * Sends an email notifying the submitter that the protocol is amended in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAmendNotificationMail(Ii studyProtocolIi) throws PAException {

        StudyProtocolQueryDTO spDTO = protocolQueryService
            .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

        String mailBody = lookUpTableService.getPropertyValue("trial.amend.body");
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace("${leadOrgName}", spDTO.getLeadOrganizationName());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.subject");
        sendEmail(spDTO, mailBody, null, mailSubject, false);

    }

    /**
     * send mail to submitter when amended trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
            .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.amend.accept.body");
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());

        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.accept.subject");
        sendEmail(spDTO, mailBody, null, mailSubject, false);
    }

    /**
     * Sends an email notifying the submitter that the protocol is registered in the system.
     * @param studyProtocolIi ii
     * @param unmatchedEmails email addresses that did not match any registry users during trial registration
     * @throws PAException ex
     */
    @Override
    public void sendNotificationMail(Ii studyProtocolIi, Collection<String> unmatchedEmails) throws PAException {
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolIi);
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(studyProtocolId);
        RegistryUser user = registryUserService.getUser(spDTO.getLastCreated().getUserLastCreated());
        if (user == null) {
            LOG.error("Registry User does not exist: " + spDTO.getLastCreated().getUserLastCreated());
            return;
        }
        String propertyPrefix = (spDTO.isProprietaryTrial()) ? "proprietarytrial" : "trial";
        String mailBody = lookUpTableService.getPropertyValue(propertyPrefix + ".register.body");
        String mailSubject = lookUpTableService.getPropertyValue(propertyPrefix + ".register.subject");
        if (spDTO.isProprietaryTrial()) {
            String subOrgTrialIdentifier = "";
            PAServiceUtils serviceUtil = new PAServiceUtils();
            List<StudySiteDTO> siteList =
                    studySiteService.getByStudyProtocol(studyProtocolIi, new ArrayList<StudySiteDTO>());
            for (StudySiteDTO dto : siteList) {
                if (dto.getFunctionalCode().getCode().equals(StudySiteFunctionalCode.TREATING_SITE.getCode())) {
                    subOrgTrialIdentifier = dto.getLocalStudyProtocolIdentifier().getValue();
                    mailBody = mailBody.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
                    String subOrgName =
                            serviceUtil.getOrCreatePAOrganizationByIi(dto.getHealthcareFacilityIi()).getName();
                    mailBody = mailBody.replace("${subOrg}", subOrgName);
                }
            }
            mailSubject = mailSubject.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
        }
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace("${leadOrgName}", spDTO.getLeadOrganizationName());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());
        String regUserName = user.getFirstName() + " " + user.getLastName();
        mailBody = mailBody.replace(OWNER_NAME, regUserName);
        
        if (CollectionUtils.isNotEmpty(unmatchedEmails)) {
            mailBody = mailBody
                    .replace(
                            ERRORS,
                            lookUpTableService
                                    .getPropertyValue(
                                            "trial.register.unidentifiableOwner.sub.email.body")
                                    .replace(
                                            "{0}",
                                            StringUtils.join(unmatchedEmails,
                                                    "\r\n")));
        } else {
            //To remove the empty bullet
            String beforeErrSubString = mailBody.substring(0, mailBody.lastIndexOf("* ${errors}"));
            String afterErrSubString = mailBody.substring(mailBody.lastIndexOf("* ${errors}") + ERROR_MSG_LENGTH);
            mailBody = beforeErrSubString + afterErrSubString;
        }

        mailSubject = mailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        sendMailWithAttachment(user.getEmailAddress(), mailSubject, mailBody, null);
    }
    
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.MailManagerService#sendOnHoldReminder
     * (java.lang.Long, gov.nih.nci.pa.domain.StudyOnhold, java.util.Date)
     */
    @Override
    public List<String> sendOnHoldReminder(Long studyProtocolId, StudyOnhold onhold,
            Date deadline) throws PAException {     
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        String mailBody = lookUpTableService.getPropertyValue("trial.onhold.reminder.body");
        String mailSubject = lookUpTableService.getPropertyValue("trial.onhold.reminder.subject");
        
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(HOLD_DATE, getFormatedDate(onhold.getOnholdDate()));
        mailBody = mailBody.replace(DEADLINE, getFormatedDate(deadline));
        mailBody = mailBody.replace(HOLD_REASON, StringUtils.isBlank(onhold
                .getOnholdReasonText()) ? onhold.getOnholdReasonCode()
                .getCode() : onhold.getOnholdReasonText());
        return sendEmail(spDTO, mailBody, new File[0], mailSubject, true);
    }

    /**
     * Sends an email to submitter when Amendment to trial is rejected by CTRO staff.
     * @param studyProtocolIi ii
     * @param rejectReason rr
     * @throws PAException ex
     */
    @Override
    public void sendAmendRejectEmail(Ii studyProtocolIi, String rejectReason) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.amend.reject.body");
        String amendNumber = "";
        if (spDTO.getAmendmentNumber() != null) {
            amendNumber = spDTO.getAmendmentNumber();
        }
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
        mailBody = mailBody.replace(AMENDMENT_NUMBER, amendNumber);
        mailBody = mailBody.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
        mailBody = mailBody.replace("${reasonForRejection}", rejectReason);
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));

        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.reject.subject");
        sendEmail(spDTO, mailBody, null, mailSubject, false);
    }

    /**
     * Sends an email notifying the submitter that the protocol is rejected by CTRO.
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException ex
     */
    @Override
    public void sendRejectionEmail(Ii studyProtocolIi) throws PAException {
        String commentText = "";
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        List<DocumentWorkflowStatusDTO> dtoList = docWrkflStatusSrv.getByStudyProtocol(studyProtocolIi);
        for (DocumentWorkflowStatusDTO dto : dtoList) {
            if (dto.getStatusCode().getCode().equalsIgnoreCase(DocumentWorkflowStatusCode.REJECTED.getCode())
                    && dto.getCommentText() != null) {
                commentText = dto.getCommentText().getValue();
            }
        }
        String body = lookUpTableService.getPropertyValue("rejection.body");
        body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        body = body.replace("${reasoncode}", commentText);
        body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));
        body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
        body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = lookUpTableService.getPropertyValue("rejection.subject");
        sendEmail(spDTO, body, null, mailSubject, false);
    }

    /**
     * Gets the current date properly formatted.
     * @return The current date properly formatted.
     */
    String getFormatedCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }

    private String getFormatedDate(Date date) {
        DateFormat format = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return format.format(date);
    }

    /**
     * send mail to submitter when trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.accept.body");
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());

        String mailSubject = lookUpTableService.getPropertyValue("trial.accept.subject");
        sendEmail(spDTO, mailBody, null, mailSubject, false);
    }

    /**
     * Sends an email notifying the submitter that the protocol is updated in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    @Override
    public void sendUpdateNotificationMail(Ii studyProtocolIi) throws PAException {

        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));

        String mailBody = lookUpTableService.getPropertyValue("trial.update.body");
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = lookUpTableService.getPropertyValue("trial.update.subject");
        sendEmail(spDTO, mailBody, null, mailSubject, false);

    }

    /**
     * Send cde request mail.
     *
     * @param mailFrom the mail from
     * @param mailBody the mail body
     */
    @Override
    public void sendCDERequestMail(String mailFrom, String mailBody) {
        try {
            // Define Message
            MimeMessage message = prepareMessage(lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL), mailFrom,
                    lookUpTableService.getPropertyValue("CDE_REQUEST_TO_EMAIL_SUBJECT"));
            // body
            Multipart multipart = new MimeMultipart();
            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            message.setContent(multipart);
            // Send Message
            Transport.send(message);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        } // catch
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMarkerCDERequestMail(Ii studyProtocolIi, String from, PlannedMarkerDTO marker,
            String markerText) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO =
                protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

            boolean foundInHugo = StringUtils.isNotEmpty(CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));

            String body = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_BODY");
            body = body.replace("${trialIdentifier}", spDTO.getNciIdentifier());
            body = body.replace("${markerName}", StConverter.convertToString(marker.getName()));
            body = body.replace("${foundInHugo}", BooleanUtils.toStringYesNo(foundInHugo));

            String hugoClause = "";
            if (foundInHugo) {
                hugoClause = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_HUGO_CLAUSE");
                hugoClause = hugoClause.replace("${hugoCode}",
                        CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));
            }
            body = body.replace("${hugoCodeClause}", hugoClause);

            String markerTextClause = "";
            if (StringUtils.isNotEmpty(markerText)) {
                markerTextClause = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_MARKER_TEXT_CLAUSE");
                markerTextClause = markerTextClause.replace("${markerText}", markerText);
            }
            body = body.replace("${markerTextClause}", markerTextClause);

            String toAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            String subject = lookUpTableService.getPropertyValue("CDE_MARKER_REQUEST_SUBJECT");
            subject = subject.replace("${trialIdentifier}", spDTO.getNciIdentifier());
            subject = subject.replace("${markerName}", StConverter.convertToString(marker.getName()));
            sendMailWithAttachment(toAddress, from, subject, body, null, false);
        } catch (Exception e) {
            throw new PAException("An error occured while sending a request for a new CDE", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
    public void sendMarkerAcceptanceMailToCDE(String nciIdentifier, 
            String from, PlannedMarkerDTO marker) throws PAException {
        try {            
            String userId = StConverter.convertToString(marker.getUserLastCreated());
            User csmUser = CSMUserService.getInstance().getCSMUserById(Long.valueOf(userId));
            RegistryUser registryUser = registryUserService.getUser(csmUser.getLoginName());
            boolean foundInHugo = StringUtils.isNotEmpty(CdConverter.convertCdToString(marker.getHugoBiomarkerCode()));
            String hugoCode = "N/A";
            if (foundInHugo) {
                hugoCode = CdConverter.convertCdToString(marker.getHugoBiomarkerCode());
            }
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            Date date = new Date();
            StringBuilder bodyB = new StringBuilder();
            bodyB.append("Dear caDSR," + "\n\n"
            + "This is just to notify you that a marker '" 
            + StConverter.convertToString(marker.getName())
            + ",HUGO code:"
            + hugoCode
            + "' has been accepted in the CTRP Protocol Abstraction "); 
            if (StringUtils.isBlank(csmUser.getFirstName()) && StringUtils.isBlank(csmUser.getLastName())) {
                if (registryUser != null) {
                    if (StringUtils.isBlank(registryUser.getFirstName()) 
                            && StringUtils.isBlank(registryUser.getLastName())) {
                        bodyB = bodyB.append("");
                    } else {
                       bodyB = bodyB.append("by " + registryUser.getFirstName() 
                               + " " + registryUser.getLastName());  
                    }     
                }  
            } else {
                bodyB = bodyB.append("by " + csmUser.getFirstName() + " " + csmUser.getLastName());
            }
            bodyB.append(" on " 
            + dateFormat.format(date)
            + ". However, this marker may still need to be added into the caDSR repository."
            + "\n\n"
            + "Thank you"
            + "\n"
            + "NCI Clinical Trials Reporting Program");
            String body = String.valueOf(bodyB);
            String toAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            String subject = "Accepted New biomarker " 
                + StConverter.convertToString(marker.getName()) 
                + ",HUGO code:" + hugoCode + " in CTRP PA";
            sendMailWithAttachment(toAddress, from, subject, body, null, false);
        } catch (Exception e) {
            throw new PAException("An error occured while sending a acceptance email for a CDE", e);
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMarkerQuestionToCTROMail(String nciIdentifier, 
            String to, PlannedMarkerDTO marker, String question) throws PAException {
        try {               
            String body = "Dear CTRO,"
                + "\n\n"
                + "A new marker request has been submitted to caDSR for trial "
                + nciIdentifier 
                + ". However, we have the following question(s) before we accept this marker "
                + StConverter.convertToString(marker.getName()) 
                + ":\n\n"
                + "'"
                + question
                + "'\n\n"
                + "Please contact "
                + "the caDSR team.\n\n"
                + "Thank you\n"
                + "NCI Clinical Trials Reporting Program"; 

            String fromAddress = lookUpTableService.getPropertyValue(CDE_REQUEST_TO_EMAIL);
            String subject = "Question regarding new biomarker "
                + StConverter.convertToString(marker.getName()) 
                + " Request for Trial " 
                + nciIdentifier;
            sendMailWithAttachment(to, fromAddress, subject, body, null, false);
        } catch (Exception e) {
            throw new PAException("An error occured while sending a acceptance email for a CDE", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendXMLAndTSREmail(String fullName, String mailTo, Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
                .convertToLong(studyProtocolIi));

            String body = lookUpTableService.getPropertyValue("xml.body");
            body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
            body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier().toString());
            body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle().toString());
            body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getLastCreated().getDateLastCreated()));
            body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier().toString());
            body = body.replace("${fileName}", spDTO.getNciIdentifier().toString() + ".xml");
            body = body.replace(OWNER_NAME, fullName);

            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
            StringBuffer sb2 = new StringBuffer(folderPath);
            StringBuffer sb3 = new StringBuffer(folderPath);
            String rtfTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb2, EXTENSION_RTF);
            String htmlTsrFile = getTSRFile(studyProtocolIi, spDTO.getNciIdentifier(), sb3, EXTENSION_HTML);

            String mailSubject = lookUpTableService.getPropertyValue("xml.subject");
            mailSubject = mailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
            mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());

            File[] attachments = {new File(xmlFile), new File(rtfTsrFile), new File(htmlTsrFile)};
            sendMailWithAttachment(mailTo, mailSubject, body, attachments);
            new File(rtfTsrFile).delete();
            new File(htmlTsrFile).delete();
            new File(xmlFile).delete();
        } catch (Exception e) {
            throw new PAException("Exception occured while sending XML and TSR Report to submitter", e);
        }
    }

    private List<String> sendEmail(StudyProtocolQueryDTO spDTO, String body, File[] attachments, String mailSubject, 
            boolean includeSubmitter)
            throws PAException {
        Set<String> emails = new HashSet<String>();
        String emailSubject = mailSubject;
        emailSubject = emailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        emailSubject = emailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());

        // We are making the assumption here that if the user created the trial then they have a registry
        // account and thus an email address saved in our system - aevansel 05/18/2010.
        // Sending email to all study owners, and not just the submitter - kanchink
        String emailAddress = null;
        try {
            Collection<RegistryUser> recipients = new HashSet<RegistryUser>(
                    getStudyOwners(spDTO));
            if (includeSubmitter) {
                RegistryUser submitter = registryUserService.getUser(spDTO
                        .getLastCreated().getUserLastCreated());
                if (submitter != null) {
                    recipients.add(submitter);
                }
            }
            for (RegistryUser recipient : recipients) {
                emailAddress = recipient.getEmailAddress();
                String regUserName = recipient.getFirstName() + " " + recipient.getLastName();
                String emailBodyText = body.replace(OWNER_NAME, regUserName);
                sendMailWithAttachment(emailAddress, emailSubject, emailBodyText, attachments);
                emails.add(emailAddress);
            }
        } catch (Exception e) {
            throw new PAException("Error attempting to send email to " + emailAddress, e);
        }
        return new ArrayList<String>(emails);
    }

    private Set<RegistryUser> getStudyOwners(StudyProtocolQueryDTO spDTO) throws PAException {
        Set<RegistryUser> studyOwners = new HashSet<RegistryUser>();
        try {
            StudyProtocol studyProtocol = (StudyProtocol) PaHibernateUtil.getCurrentSession()
                .get(StudyProtocol.class, spDTO.getStudyProtocolId());
            if (studyProtocol != null) {
                studyOwners = studyProtocol.getStudyOwners();
            }
        } catch (Exception e) {
            throw new PAException("Error retrieving Study Protocol with Identifier = " + spDTO.getStudyProtocolId(), e);
        }
        return studyOwners;
    }

    /**
     * @param userId userid
     */
    @Override
    public void sendAdminAcceptanceEmail(Long userId) {
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.accept.body", "");
    }

    /**
     * @param userId id
     * @param reason reason
     */
    @Override
    public void sendAdminRejectionEmail(Long userId, String reason) {
        String rejectReason = "No Reason Provided.";
        if (StringUtils.isNotEmpty(reason)) {
            rejectReason = reason;
        }
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.reject.body", rejectReason);
    }

    private void sendAdminAcceptanceRejectionEmail(Long userId, String emailBodyLookupKey, String reason) {
        try {
            RegistryUser admin = registryUserService.getUserById(userId);
            String emailSubject = lookUpTableService.getPropertyValue("trial.admin.accept.subject");
            String emailBody = lookUpTableService.getPropertyValue(emailBodyLookupKey);
            emailBody = emailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
            emailBody = emailBody.replace("${affliateOrgName}", admin.getAffiliateOrg());
            if (StringUtils.isNotEmpty(reason)) {
                emailBody = emailBody.replace("${rejectReason}", reason);
            }
            emailBody = emailBody.replace(OWNER_NAME, admin.getFirstName() + " " + admin.getLastName());
            sendMailWithAttachment(admin.getEmailAddress(), emailSubject, emailBody, null);
        } catch (PAException e) {
            LOG.error("Error attempting to send email to ", e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sendSearchUsernameEmail(String emailAddress) throws PAException {
        if (!PAUtil.isValidEmail(emailAddress)) {
            throw new PAException("Validation Exception: A valid email address must be provided");
        }
        List<RegistryUser> users = registryUserService.getLoginNamesByEmailAddress(emailAddress);
        if (CollectionUtils.isNotEmpty(users)) {
            sendSearchUsernameEmail(emailAddress, users);
            return true;
        }
        return false;
    }

    /**
     * get the login names of a list of registry users.
     * @param users The List of registry users.
     * @return The list of CN extracted from the given identities.
     */
    List<String> getGridIdentityUsernames(List<RegistryUser> users) {
        List<String> userNames = new ArrayList<String>();
        for (RegistryUser user : users) {
            userNames.add(CsmUserUtil.getGridIdentityUsername(user.getCsmUser().getLoginName()));
        }
        return userNames;
    }

    /**
     * Send the user name search email.
     * @param emailAddress The e-mail address to which the mail must be send
     * @param users The list of users with that email address
     * @throws PAException If an error occurs.
     */
    void sendSearchUsernameEmail(String emailAddress, List<RegistryUser> users) throws PAException {
        String subject = lookUpTableService.getPropertyValue(USERNAME_SEARCH_SUBJECT_PROPERTY);
        String body = lookUpTableService.getPropertyValue(USERNAME_SEARCH_BODY_PROPERTY);
        RegistryUser user = users.get(0);
        body = body.replace("${firstName}", user.getFirstName());
        body = body.replace("${lastName}", user.getLastName());
        String userNamesString = StringUtils.join(getGridIdentityUsernames(users), ", ");
        body = body.replace("${userNames}", userNamesString);
        sendMailWithAttachment(emailAddress, subject, body, null);
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param ctGovXmlGeneratorService the ctGovXmlGeneratorService to set
     */
    public void setCtGovXmlGeneratorService(CTGovXmlGeneratorServiceLocal ctGovXmlGeneratorService) {
        this.ctGovXmlGeneratorService = ctGovXmlGeneratorService;
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(TSRReportGeneratorServiceRemote tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param docWrkflStatusSrv the docWrkflStatusSrv to set
     */
    public void setDocWrkflStatusSrv(DocumentWorkflowStatusServiceLocal docWrkflStatusSrv) {
        this.docWrkflStatusSrv = docWrkflStatusSrv;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    @Override
    public void sendUnidentifiableOwnerEmail(Long studyProtocolId,
            Collection<String> emails) throws PAException {
        
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.register.unidentifiableOwner.email.subject");
        String mailTo = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        String mailBody = lookUpTableService
                .getPropertyValue("trial.register.unidentifiableOwner.email.body");

        StudyProtocolQueryDTO study = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        
        String nciID = study.getNciIdentifier();
        String badEmails = StringUtils.join(emails, "\r\n");
        String submitter = identifySubmitter(study);

        mailSubject = mailSubject.replace("{0}", submitter).replace("{1}",
                nciID);
        mailBody = mailBody.replace("{0}", nciID).replace("{1}", submitter)
                .replace("{2}", badEmails);

        sendMailWithAttachment(mailTo, mailSubject, mailBody, new File[0]);

    }

    /**
     * Attempts to identify trial submitting organization.
     * 
     * @param study
     * @return
     */
    private String identifySubmitter(StudyProtocolQueryDTO study) {
        String submitter = "";        
        try {
            RegistryUser user = study.getLastCreated() != null ? registryUserService
                    .getUser(study.getLastCreated().getUserLastCreated())
                    : null;
            if (user != null) {
                submitter = findAffiliatedOrg(user);
                if (StringUtils.isBlank(submitter)) {
                    submitter = user.getFirstName() + " " + user.getLastName();
                }
            }
        } catch (Exception e) {
            LOG.error(
                    "Unable to identify an owning organization for this trial: "
                            + study.getNciIdentifier(), e);
        }
        return submitter;
    }

    /**
     * @param servUtil
     * @param user
     * @return
     */
    String findAffiliatedOrg(RegistryUser user) {
        PAServiceUtils servUtil = new PAServiceUtils();
        return servUtil.getOrgName(IiConverter.convertToPoOrganizationIi(String
                .valueOf(user.getAffiliatedOrganizationId())));
    }

    @Override
    public void sendMailWithHtmlBody(String mailTo, String subject, String mailBody) {
        try {
            MimeMessage message = prepareMessage(mailTo, lookUpTableService.getPropertyValue("fromaddress"), subject);
            message.setContent(mailBody, "text/html");
            // Send Message
            Transport.send(message);            
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    @Override
    public void sendSubmissionTerminationEmail(Long studyProtocolId) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(studyProtocolId);
        String mailBody = lookUpTableService.getPropertyValue("trial.onhold.termination.body");
        String mailSubject = lookUpTableService.getPropertyValue("trial.onhold.termination.subject");
        String ctroEmail = lookUpTableService
                .getPropertyValue("abstraction.script.mailTo");
        
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());
        sendMailWithAttachment(ctroEmail, mailSubject, mailBody, new File[0]);        
    }

    MimeMessage prepareMessage(String mailTo, String mailFrom, String subject) throws PAException {
        Session session;
        // get system properties
        Properties props = System.getProperties();
        // Set up mail server
        props.put("mail.smtp.host", lookUpTableService.getPropertyValue("smtp"));
        props.put("mail.smtp.timeout", SMTP_TIMEOUT);
        props.put("mail.smtp.connectiontimeout", SMTP_TIMEOUT);
        // Get session
        session = Session.getDefaultInstance(props, null);

        MimeMessage result = new MimeMessage(session);
        try {
            result.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            result.setFrom(new InternetAddress(mailFrom));
            result.addRecipient(Message.RecipientType.BCC,
                    new InternetAddress(lookUpTableService.getPropertyValue("log.email.address")));
            result.setSentDate(new java.util.Date());
            result.setSubject(subject);
        } catch (Exception e) {
            throw new PAException("Error preparing MIME message.", e);
        }
        return result;
    }

    @Override
    public void sendTrialOwnershipAddEmail(Long userID, Long trialID)
            throws PAException {
        
        String mailBody = lookUpTableService
                .getPropertyValue("trial.ownership.add.email.body");
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.ownership.add.email.subject");

        sendTrialOwnershipChangeEmail(userID, trialID, mailBody, mailSubject);
    }

    /**
     * @param userID
     * @param trialID
     * @param mailBody
     * @param mailSubject
     * @throws PAException
     */
    private void sendTrialOwnershipChangeEmail(Long userID, Long trialID,
            String mailBody, String mailSubject) throws PAException { // NOPMD
        RegistryUser user = registryUserService.getUserById(userID);
        StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(trialID);

        mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO
                .getNciIdentifier().toString());
        mailSubject = mailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO
                .getLocalStudyProtocolIdentifier().toString());

        mailBody = mailBody.replace(USER_NAME, getFullUserName(user));
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle()
                .toString());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO
                .getNciIdentifier().toString());
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO
                .getLocalStudyProtocolIdentifier().toString());
        
        try {
            String mailFrom = lookUpTableService
                    .getPropertyValue("fromaddress");
            sendMailWithAttachment(user.getEmailAddress(), mailFrom,
                    mailSubject, mailBody, new File[0], true);
        } catch (Exception e) {
            LOG.error(SEND_MAIL_ERROR, e);
        }
    }

    /**
     * @param user
     * @return
     */
    private String getFullUserName(RegistryUser user) {
        final String fullName = StringUtils.defaultString(user.getFirstName())
                + " " + StringUtils.defaultString(user.getLastName());
        if (StringUtils.isBlank(fullName)) {
            return SIR_OR_MADAM;
        } else {
            return fullName.trim();
        }
    }

    @Override
    public void sendTrialOwnershipRemoveEmail(Long userID, Long trialID)
            throws PAException {
        String mailBody = lookUpTableService
                .getPropertyValue("trial.ownership.remove.email.body");
        String mailSubject = lookUpTableService
                .getPropertyValue("trial.ownership.remove.email.subject");

        sendTrialOwnershipChangeEmail(userID, trialID, mailBody, mailSubject);
    }
}
