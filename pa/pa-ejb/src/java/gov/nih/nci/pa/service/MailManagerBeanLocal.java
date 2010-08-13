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

package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

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

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author asharma
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(HibernateSessionInterceptor.class)
public class MailManagerBeanLocal implements MailManagerServiceLocal {

    private static final Logger LOG = Logger.getLogger(MailManagerBeanLocal.class);
    private static final int LINE_WIDTH = 65;
    private static final String TSR = "TSR_";
    private static final String EXTENSION_PDF = ".pdf";
    private static final String CURRENT_DATE = "${CurrentDate}";
    private static final String NCI_TRIAL_IDENTIFIER = "${nciTrialIdentifier}";
    private static final String OWNER_NAME = "${SubmitterName}";
    private static final String LEAD_ORG_TRIAL_IDENTIFIER = "${leadOrgTrialIdentifier}";
    private static final String RECEIPT_DATE = "${receiptDate}";
    private static final String TRIAL_TITLE = "${trialTitle}";
    private static final String AMENDMENT_NUMBER = "${amendmentNumber}";
    private static final String AMENDMENT_DATE = "${amendmentDate}";

    @EJB
    private ProtocolQueryServiceLocal protocolQueryService;
    @EJB
    private RegistryUserServiceLocal registryUserService;
    @EJB
    private CTGovXmlGeneratorServiceRemote ctGovXmlGeneratorService;
    @EJB
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private DocumentWorkflowStatusServiceLocal docWrkflStatusSrv;
    @EJB
    private StudySiteServiceLocal studySiteService;

    /**
     * {@inheritDoc}
     */
    public void sendTSREmail(Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO = protocolQueryService
                .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

            String body = "";
            String amendNumber = "";
            if (spDTO.getAmendmentNumber() != null) {
                amendNumber = spDTO.getAmendmentNumber();
            }
            if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.amend.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.amend.body");
                }
            } else if (spDTO.getIsProprietaryTrial()) {
                body = lookUpTableService.getPropertyValue("tsr.proprietary.body");
            } else {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    body = lookUpTableService.getPropertyValue("tsr.body");
                } else {
                    body = lookUpTableService.getPropertyValue("noxml.tsr.body");
                }
            }
            body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
            body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier().toString());
            body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle().toString());
            body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getDateLastCreated()));
            body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier().toString());
            body = body.replace("${fileName}", TSR + spDTO.getNciIdentifier().toString() + EXTENSION_PDF);
            if (!spDTO.getIsProprietaryTrial()) {
                body = body.replace("${fileName2}", spDTO.getNciIdentifier().toString() + ".xml");
            }

            body = body.replace(AMENDMENT_NUMBER, amendNumber);
            if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
                body = body.replace(AMENDMENT_DATE, getFormatedDate(spDTO.getAmendmentDate()));
            }

            protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            StringBuffer sb2 = new StringBuffer(folderPath);
            String tsrFile = getTSRFile(studyProtocolIi, spDTO, sb2);
            String mailSubject = "";

            if (spDTO.getIsProprietaryTrial()) {
                File[] attachments = {new File(tsrFile) };

                mailSubject = lookUpTableService.getPropertyValue("tsr.proprietary.subject");
                sendEmail(spDTO, body, attachments, mailSubject);
                new File(tsrFile).delete();
            } else {
                if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
                    String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
                    File[] attachments = {new File(xmlFile), new File(tsrFile) };

                    if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
                        mailSubject = lookUpTableService.getPropertyValue("tsr.amend.subject");
                    } else {
                        mailSubject = lookUpTableService.getPropertyValue("tsr.subject");
                    }
                    sendEmail(spDTO, body, attachments, mailSubject);
                    new File(tsrFile).delete();
                    new File(xmlFile).delete();
                } else {
                    File[] attachments = {new File(tsrFile) };

                    if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
                        mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.amend.subject");
                    } else {
                        mailSubject = lookUpTableService.getPropertyValue("noxml.tsr.subject");
                    }
                    sendEmail(spDTO, body, attachments, mailSubject);
                    new File(tsrFile).delete();
                }
            }
        } catch (Exception e) {
            throw new PAException("Exception occured while sending TSR Report to submitter", e);
        }
    }

    private String getTSRFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO, StringBuffer sb2) throws PAException {

        String tsrFile = new String(sb2.append(File.separator).append(TSR)
                                       .append(spDTO.getNciIdentifier().toString() + EXTENSION_PDF));
        try {
            ByteArrayOutputStream pdfStream = tsrReportGeneratorService.generateTsrReport(studyProtocolIi);
            pdfStream.writeTo(new FileOutputStream(tsrFile));
        } catch (Exception e) {
            throw new PAException("Exception occured while getting TSR Report to submitter", e);
        }
        return tsrFile;
    }

    private String getXmlFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO, StringBuffer sb) throws PAException {
        String xmlFile = new String(sb.append(File.separator).append(spDTO.getNciIdentifier().toString() + ".xml"));
        try {
            if (!spDTO.getIsProprietaryTrial()) {

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

    /**
     *
     * @param mailTo mailTo
     * @param subject subject
     * @param mailBody mailBody
     * @param attachments File attachments
     */
    public void sendMailWithAttachment(String mailTo, String subject, String mailBody, File[] attachments) {
        try {
            // get system properties
            Properties props = System.getProperties();

            // Set up mail server
            props.put("mail.smtp.host", lookUpTableService.getPropertyValue("smtp"));
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

    /**
     * Sends an email notifying the submitter that the protocol is amended in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
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
        sendEmail(spDTO, mailBody, null, mailSubject);

    }

    /**
     * send mail to submitter when amended trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
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
        sendEmail(spDTO, mailBody, null, mailSubject);
    }

    /**
     * Sends an email notifying the submitter that the protocol is registered in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    public void sendNotificationMail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));

        String submissionMailBody = "";
        String subOrgTrialIdentifier = "";
        if (!spDTO.getIsProprietaryTrial()) {
            submissionMailBody = lookUpTableService.getPropertyValue("trial.register.body");
        } else {
            submissionMailBody = lookUpTableService.getPropertyValue("proprietarytrial.register.body");
            PAServiceUtils serviceUtil = new PAServiceUtils();
            List<StudySiteDTO> siteList = studySiteService.getByStudyProtocol(studyProtocolIi,
                                                                              new ArrayList<StudySiteDTO>());
            for (StudySiteDTO dto : siteList) {
                if (dto.getFunctionalCode().getCode().equals(StudySiteFunctionalCode.TREATING_SITE.getCode())) {
                    subOrgTrialIdentifier = dto.getLocalStudyProtocolIdentifier().getValue();
                    submissionMailBody = submissionMailBody.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
                    submissionMailBody = submissionMailBody.replace("${subOrg}", serviceUtil
                        .getOrCreatePAOrganizationByIi(dto.getHealthcareFacilityIi()).getName());
                }
            }
        }
        submissionMailBody = submissionMailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        submissionMailBody = submissionMailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER,
                                                        spDTO.getLocalStudyProtocolIdentifier());
        submissionMailBody = submissionMailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        submissionMailBody = submissionMailBody.replace("${leadOrgName}", spDTO.getLeadOrganizationName());
        submissionMailBody = submissionMailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = "";
        if (!spDTO.getIsProprietaryTrial()) {
            mailSubject = lookUpTableService.getPropertyValue("trial.register.subject");
        } else {
            mailSubject = lookUpTableService.getPropertyValue("proprietarytrial.register.subject");
            mailSubject = mailSubject.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
        }
        // for registration, assumption made is submitter gets email as transaction is not complete won't get owners
        submissionMailBody = submissionMailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER,
                                                        spDTO.getLocalStudyProtocolIdentifier());
        submissionMailBody = submissionMailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        RegistryUser user = registryUserService.getUser(spDTO.getUserLastCreated());
        mailSubject = mailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());

        if (user != null) {
            String regUserName = user.getFirstName() + " " + user.getLastName();
            submissionMailBody = submissionMailBody.replace(OWNER_NAME, regUserName);
            sendMailWithAttachment(user.getEmailAddress(), mailSubject, submissionMailBody, null);
        } else {
            LOG.error("Registry User does not exist: " + spDTO.getUserLastCreated());
        }
    }

    /**
     * Sends an email to submitter when Amendment to trial is rejected by CTRO staff.
     * @param studyProtocolIi ii
     * @param rejectReason rr
     * @throws PAException ex
     */
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
        mailBody = mailBody.replace(RECEIPT_DATE, getFormatedDate(spDTO.getDateLastCreated()));

        String mailSubject = lookUpTableService.getPropertyValue("trial.amend.reject.subject");
        sendEmail(spDTO, mailBody, null, mailSubject);
    }

    /**
     * Sends an email notifying the submitter that the protocol is rejected by CTRO.
     * @param studyProtocolIi studyProtocolIi
     * @throws PAException ex
     */
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
        body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getDateLastCreated()));
        body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
        body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = lookUpTableService.getPropertyValue("rejection.subject");
        sendEmail(spDTO, body, null, mailSubject);
    }

    private String getFormatedCurrentDate() {
        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return format.format(date);
    }

    private String getFormatedDate(Date date) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        return format.format(date);
    }

    /**
     * send mail to submitter when trial is accepted by CTRO staff.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));
        String mailBody = lookUpTableService.getPropertyValue("trial.accept.body");
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());

        String mailSubject = lookUpTableService.getPropertyValue("trial.accept.subject");
        sendEmail(spDTO, mailBody, null, mailSubject);
    }

    /**
     * Format.
     *
     * @param unformattedXml the unformatted xml
     *
     * @return the string
     */
    private String format(String unformattedXml) {
        Writer out = new StringWriter();
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(LINE_WIDTH);
            format.setEncoding("ISO-8859-1");
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
     * Sends an email notifying the submitter that the protocol is updated in the system.
     * @param studyProtocolIi ii
     * @throws PAException ex
     */
    public void sendUpdateNotificationMail(Ii studyProtocolIi) throws PAException {

        StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
            .convertToLong(studyProtocolIi));

        String mailBody = lookUpTableService.getPropertyValue("trial.update.body");
        mailBody = mailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
        mailBody = mailBody.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());
        mailBody = mailBody.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        mailBody = mailBody.replace(TRIAL_TITLE, spDTO.getOfficialTitle());

        String mailSubject = lookUpTableService.getPropertyValue("trial.update.subject");
        sendEmail(spDTO, mailBody, null, mailSubject);

    }

    /**
     * Send cde request mail.
     *
     * @param mailFrom the mail from
     * @param mailBody the mail body
     */
    public void sendCDERequestMail(String mailFrom, String mailBody) {
        try {
            // get system properties
            Properties props = System.getProperties();

            // Set up mail server
            props.put("mail.smtp.host", lookUpTableService.getPropertyValue("smtp"));
            // Get session
            Session session = Session.getDefaultInstance(props, null);

            // Define Message
            MimeMessage message = new MimeMessage(session);
            // body
            Multipart multipart = new MimeMultipart();
            message.setFrom(new InternetAddress(mailFrom));
            message.addRecipient(Message.RecipientType.TO,
                                 new InternetAddress(lookUpTableService.getPropertyValue("CDE_REQUEST_TO_EMAIL")));
            message.setSentDate(new java.util.Date());
            message.setSubject(lookUpTableService.getPropertyValue("CDE_REQUEST_TO_EMAIL_SUBJECT"));

            BodyPart msgPart = new MimeBodyPart();
            msgPart.setText(mailBody);
            multipart.addBodyPart(msgPart);
            message.setContent(multipart);
            // Send Message
            Transport.send(message);
        } catch (Exception e) {
            LOG.error("Send Mail error", e);
        } // catch
    }

    /**
     * {@inheritDoc}
     */
    public void sendXMLAndTSREmail(String fullName, String mailTo, Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter
                .convertToLong(studyProtocolIi));

            String body = lookUpTableService.getPropertyValue("xml.body");
            body = body.replace(CURRENT_DATE, getFormatedCurrentDate());
            body = body.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier().toString());
            body = body.replace(TRIAL_TITLE, spDTO.getOfficialTitle().toString());
            body = body.replace(RECEIPT_DATE, getFormatedDate(spDTO.getDateLastCreated()));
            body = body.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier().toString());
            body = body.replace("${fileName}", spDTO.getNciIdentifier().toString() + ".xml");
            body = body.replace(OWNER_NAME, fullName);

            String folderPath = PaEarPropertyReader.getDocUploadPath();
            StringBuffer sb = new StringBuffer(folderPath);
            String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
            StringBuffer sb2 = new StringBuffer(folderPath);
            String tsrFile = getTSRFile(studyProtocolIi, spDTO, sb2);

            String mailSubject = lookUpTableService.getPropertyValue("xml.subject");
            mailSubject = mailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
            mailSubject = mailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());

            File[] attachments = {new File(xmlFile), new File(tsrFile) };
            sendMailWithAttachment(mailTo, mailSubject, body, attachments);
            new File(tsrFile).delete();
            new File(xmlFile).delete();
        } catch (Exception e) {
            throw new PAException("Exception occured while sending XML and TSR Report to submitter", e);
        }
    }

    private void sendEmail(StudyProtocolQueryDTO spDTO, String body, File[] attachments, String mailSubject)
            throws PAException {
        String emailSubject = mailSubject;
        emailSubject = emailSubject.replace(LEAD_ORG_TRIAL_IDENTIFIER, spDTO.getLocalStudyProtocolIdentifier());
        emailSubject = emailSubject.replace(NCI_TRIAL_IDENTIFIER, spDTO.getNciIdentifier());

        // We are making the assumption here that if the user created the trial then they have a registry
        // account and thus an email address saved in our system - aevansel 05/18/2010.
        // Sending email to all study owners, and not just the submitter - kanchink
        String emailAddress = null;
        try {
            Set<RegistryUser> studyOwners = getStudyOwners(spDTO);
            for (RegistryUser owner : studyOwners) {
                emailAddress = owner.getEmailAddress();
                String regUserName = owner.getFirstName() + " " + owner.getLastName();
                String emailBodyText = body.replace(OWNER_NAME, regUserName);
                sendMailWithAttachment(emailAddress, emailSubject, emailBodyText, attachments);
            }
        } catch (Exception e) {
            throw new PAException("Error attempting to send email to " + emailAddress, e);
        }
    }

    private Set<RegistryUser> getStudyOwners(StudyProtocolQueryDTO spDTO) throws PAException {
        Set<RegistryUser> studyOwners = new HashSet<RegistryUser>();
        try {
            StudyProtocol studyProtocol = (StudyProtocol) HibernateUtil.getCurrentSession()
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
    public void sendAdminAcceptanceEmail(Long userId) {
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.accept.body");
    }

    /**
     * @param userId id
     * @param reason reason
     */
    public void sendAdminRejectionEmail(Long userId, String reason) {
        sendAdminAcceptanceRejectionEmail(userId, "trial.admin.reject.body");
    }

    private void sendAdminAcceptanceRejectionEmail(Long userId, String emailBodyLookupKey) {
        try {
            RegistryUser admin = registryUserService.getUserById(userId);
            String emailSubject = lookUpTableService.getPropertyValue("trial.admin.accept.subject");
            String emailBody = lookUpTableService.getPropertyValue(emailBodyLookupKey);
            emailBody = emailBody.replace(CURRENT_DATE, getFormatedCurrentDate());
            emailBody = emailBody.replace("${affliateOrgName}", admin.getAffiliateOrg());
            emailBody = emailBody.replace(OWNER_NAME, admin.getFirstName() + " " + admin.getLastName());
            sendMailWithAttachment(admin.getEmailAddress(), emailSubject, emailBody, null);
        } catch (PAException e) {
            LOG.error("Error attempting to send email to ", e);
        }
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
    public void setCtGovXmlGeneratorService(CTGovXmlGeneratorServiceRemote ctGovXmlGeneratorService) {
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
}
