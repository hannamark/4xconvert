/**
 *
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

import org.apache.commons.lang.StringUtils;
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
@SuppressWarnings({"PMD.FinalFieldCouldBeStatic", "PMD.TooManyMethods", "PMD.CyclomaticComplexity",
        "PMD.NPathComplexity", "PMD.ExcessiveClassLength" })
public class MailManagerBeanLocal implements MailManagerServiceLocal {

    private static final Logger LOG = Logger.getLogger(MailManagerBeanLocal.class);
    private static final int VAL = 65;
    private static final String TSR = "TSR_";
    private static final String EXTENSION_PDF = ".pdf";
    private final String currentDate = "${CurrentDate}";
    private final String nciTrialIdentifier = "${nciTrialIdentifier}";
    private final String ownerName = "${SubmitterName}";
    private final String leadOrgTrialIdentifier = "${leadOrgTrialIdentifier}";
    private final String receiptDate = "${receiptDate}";
    private final String trialTitle = "${trialTitle}";
    private final String amendmentNumber = "${amendmentNumber}";
    private final String amendmentDate = "${amendmentDate}";

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
   * @param studyProtocolIi studyProtocolIi
   * @throws PAException PAException
   */
  @SuppressWarnings({ "PMD.StringInstantiation", "PMD.ExcessiveMethodLength" })
  public void sendTSREmail(Ii studyProtocolIi) throws PAException {
    LOG.debug("Entering sendTSREmail");
    try {
      StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                                      IiConverter.convertToLong(studyProtocolIi));

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
      } else if (StringUtils.isNotEmpty(spDTO.getIsProprietaryTrial())
              && spDTO.getIsProprietaryTrial().equalsIgnoreCase("true")) {
          body = lookUpTableService.getPropertyValue("tsr.proprietary.body");
      } else {
          if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
              body = lookUpTableService.getPropertyValue("tsr.body");
          } else {
              body = lookUpTableService.getPropertyValue("noxml.tsr.body");
          }
      }
      body = body.replace(currentDate, getFormatedCurrentDate());
      body = body.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier().toString());
      body = body.replace(trialTitle, spDTO.getOfficialTitle().toString());
      body = body.replace(receiptDate, getFormatedDate(spDTO.getDateLastCreated()));
      body = body.replace(nciTrialIdentifier, spDTO.getNciIdentifier().toString());
      body = body.replace("${fileName}", TSR
                                         + spDTO.getNciIdentifier().toString() + EXTENSION_PDF);
      if (StringUtils.isEmpty(spDTO.getIsProprietaryTrial())
              || spDTO.getIsProprietaryTrial().equalsIgnoreCase("false")) {
          body = body.replace("${fileName2}", spDTO.getNciIdentifier().toString() + ".xml");
      }

      body = body.replace(amendmentNumber, amendNumber);
      if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
          body = body.replace(amendmentDate, getFormatedDate(spDTO.getAmendmentDate()));
      }

      protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
      String folderPath = PaEarPropertyReader.getDocUploadPath();
      StringBuffer sb  = new StringBuffer(folderPath);
      StringBuffer sb2  = new StringBuffer(folderPath);
      String tsrFile = getTSRFile(studyProtocolIi, spDTO, sb2);
      String mailSubject = "";

      if (StringUtils.isNotEmpty(spDTO.getIsProprietaryTrial())
              && spDTO.getIsProprietaryTrial().equalsIgnoreCase("true")) {
          File[] attachments = {new File(tsrFile)};

          mailSubject = lookUpTableService.getPropertyValue("tsr.proprietary.subject");
          sendEmail(spDTO, body, attachments, mailSubject);
          new File(tsrFile).delete();
      } else {
          if (spDTO.getCtgovXmlRequiredIndicator().booleanValue()) {
              String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
              File[] attachments = {new File(xmlFile), new File(tsrFile)};

              if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
                  mailSubject = lookUpTableService.getPropertyValue("tsr.amend.subject");
              } else {
                  mailSubject = lookUpTableService.getPropertyValue("tsr.subject");
              }
              sendEmail(spDTO, body, attachments, mailSubject);
              new File(tsrFile).delete();
              new File(xmlFile).delete();
          } else {
              File[] attachments = {new File(tsrFile)};

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
    LOG.debug("Leaving sendTSREmail");

}

  private String getTSRFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO,
          StringBuffer sb2) throws PAException {

      String tsrFile = new String(sb2.append(File.separator).append(TSR).append(
              spDTO.getNciIdentifier().toString() + EXTENSION_PDF));
      try {
          ByteArrayOutputStream pdfStream = tsrReportGeneratorService.generateTsrReport(studyProtocolIi);
          pdfStream.writeTo(new FileOutputStream(tsrFile));
      } catch (Exception e) {
          throw new PAException("Exception occured while getting TSR Report to submitter", e);
      }
      return tsrFile;
  }

  private String getXmlFile(Ii studyProtocolIi, StudyProtocolQueryDTO spDTO,
          StringBuffer sb) throws PAException {
      String xmlFile = new String(sb.append(File.separator).append(
              spDTO.getNciIdentifier().toString() + ".xml"));
      try {
          if (StringUtils.isEmpty(spDTO.getIsProprietaryTrial())
                  || spDTO.getIsProprietaryTrial().equalsIgnoreCase("false")) {


              //Format the xml only for non proprietary
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
  public  void sendMailWithAttachment(String mailTo, String subject,
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
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace(amendmentNumber, amendNumber);
    mailBody = mailBody.replace(amendmentDate, getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
    mailBody = mailBody.replace("${leadOrgName}", spDTO.getLeadOrganizationName());
    mailBody = mailBody.replace(trialTitle, spDTO.getOfficialTitle());

    String mailSubject = lookUpTableService.getPropertyValue("trial.amend.subject");
    sendEmail(spDTO, mailBody, null, mailSubject);

    }

 /**
  * send mail to submitter when amended trial is accepted by CTRO staff.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException {
    LOG.debug("Entering send Amend Accept Email");
    StudyProtocolQueryDTO spDTO = protocolQueryService
                  .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
    String mailBody = lookUpTableService.getPropertyValue("trial.amend.accept.body");
    String amendNumber = "";
    if (spDTO.getAmendmentNumber() != null) {
        amendNumber = spDTO.getAmendmentNumber();
    }
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
    mailBody = mailBody.replace(amendmentNumber, amendNumber);
    mailBody = mailBody.replace(amendmentDate, getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());

    String mailSubject = lookUpTableService.getPropertyValue("trial.amend.accept.subject");
    sendEmail(spDTO, mailBody, null, mailSubject);
    LOG.debug("Leaving sendAmendAcceptEmail");
  }

 /**
  * Sends an email notifying the submitter that the protocol is registered in the system.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendNotificationMail(Ii studyProtocolIi) throws PAException  {
    String  valueFalse = "false";
    StudyProtocolQueryDTO spDTO = protocolQueryService
        .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

    String submissionMailBody = "";
    String subOrgTrialIdentifier = "";
    if (spDTO.getIsProprietaryTrial() == null
            || spDTO.getIsProprietaryTrial().equalsIgnoreCase(valueFalse)) {
        submissionMailBody = lookUpTableService.getPropertyValue("trial.register.body");
    } else {
        submissionMailBody = lookUpTableService.getPropertyValue("proprietarytrial.register.body");
        PAServiceUtils serviceUtil = new PAServiceUtils();
        List<StudySiteDTO> siteList = studySiteService.getByStudyProtocol(
                studyProtocolIi, new ArrayList<StudySiteDTO>());
        for (StudySiteDTO dto : siteList) {
            if (dto.getFunctionalCode().getCode().equals(StudySiteFunctionalCode.TREATING_SITE.getCode())) {
                subOrgTrialIdentifier = dto.getLocalStudyProtocolIdentifier().getValue();
                submissionMailBody = submissionMailBody.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
                submissionMailBody = submissionMailBody.replace("${subOrg}",
                        serviceUtil.getOrCreatePAOrganizationByIi(dto.getHealthcareFacilityIi()).getName());
            }
        }
    }
    submissionMailBody = submissionMailBody.replace(currentDate, getFormatedCurrentDate());
    submissionMailBody = submissionMailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
    submissionMailBody = submissionMailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    submissionMailBody = submissionMailBody.replace("${leadOrgName}", spDTO.getLeadOrganizationName());
    submissionMailBody = submissionMailBody.replace(trialTitle, spDTO.getOfficialTitle());

    String mailSubject = "";
    if (spDTO.getIsProprietaryTrial() == null
            || spDTO.getIsProprietaryTrial().equalsIgnoreCase(valueFalse)) {
        mailSubject = lookUpTableService.getPropertyValue("trial.register.subject");
    } else {
        mailSubject = lookUpTableService.getPropertyValue("proprietarytrial.register.subject");
        mailSubject = mailSubject.replace("${subOrgTrialIdentifier}", subOrgTrialIdentifier);
    }
    sendEmail(spDTO, submissionMailBody, null, mailSubject);
  }
 /**
  * Sends an email to submitter when Amendment to trial is rejected by CTRO staff.
  * @param studyProtocolIi ii
  * @param rejectReason rr
  * @throws PAException ex
  */
  public void sendAmendRejectEmail(Ii studyProtocolIi, String rejectReason) throws PAException {
    LOG.debug("Entering send Amend reject Email");
    StudyProtocolQueryDTO spDTO = protocolQueryService
                  .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
    String mailBody = lookUpTableService.getPropertyValue("trial.amend.reject.body");
    String amendNumber = "";
    if (spDTO.getAmendmentNumber() != null) {
        amendNumber = spDTO.getAmendmentNumber();
    }
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
    mailBody = mailBody.replace(amendmentNumber, amendNumber);
    mailBody = mailBody.replace(amendmentDate, getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace("${reasonForRejection}", rejectReason);
    mailBody = mailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
    mailBody = mailBody.replace(receiptDate, getFormatedDate(spDTO.getDateLastCreated()));

    String mailSubject = lookUpTableService.getPropertyValue("trial.amend.reject.subject");
    sendEmail(spDTO, mailBody, null, mailSubject);
    LOG.debug("Leaving sendAmendRejectEmail");
  }

 /**
  * Sends an email notifying the submitter that the protocol is rejected by CTRO.
  * @param studyProtocolIi studyProtocolIi
  * @throws PAException ex
  */
  public void sendRejectionEmail(Ii studyProtocolIi) throws PAException {
    String commentText = "";
    StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                                     IiConverter.convertToLong(studyProtocolIi));
    List<DocumentWorkflowStatusDTO> dtoList = docWrkflStatusSrv.getByStudyProtocol(
                                                studyProtocolIi);
    for (DocumentWorkflowStatusDTO dto : dtoList) {
        if (dto.getStatusCode().getCode().equalsIgnoreCase(DocumentWorkflowStatusCode.REJECTED.getCode())
                && dto.getCommentText() != null) {
            commentText = dto.getCommentText().getValue();
        }
    }
    String body = lookUpTableService.getPropertyValue("rejection.body");
    body = body.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
    body = body.replace("${reasoncode}", commentText);
    body = body.replace(receiptDate, getFormatedDate(spDTO.getDateLastCreated()));
    body = body.replace(currentDate, getFormatedCurrentDate());
    body = body.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    body = body.replace(trialTitle, spDTO.getOfficialTitle());

    String mailSubject = lookUpTableService.getPropertyValue("rejection.subject");
    sendEmail(spDTO, body, null, mailSubject);
 }
 @SuppressWarnings({"PMD.SimpleDateFormatNeedsLocale" })
 private String getFormatedCurrentDate() {
    Calendar calendar = new GregorianCalendar();
    Date date = calendar.getTime();
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    return format.format(date);
 }
 @SuppressWarnings({"PMD.SimpleDateFormatNeedsLocale" })
 private String getFormatedDate(Date date) {
    DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    return format.format(date);
 }

 /**
  * send mail to submitter when trial is accepted by CTRO staff.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
    LOG.debug("Entering send Accept Email");
    StudyProtocolQueryDTO spDTO = protocolQueryService
                  .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
    String mailBody = lookUpTableService.getPropertyValue("trial.accept.body");
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
    mailBody = mailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());

    String mailSubject = lookUpTableService.getPropertyValue("trial.accept.subject");
    sendEmail(spDTO, mailBody, null, mailSubject);
    LOG.debug("Leaving send AcceptEmail");
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
        format.setLineWidth(VAL);
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

    StudyProtocolQueryDTO spDTO = protocolQueryService
    .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

    String mailBody = lookUpTableService.getPropertyValue("trial.update.body");
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
    mailBody = mailBody.replace(trialTitle, spDTO.getOfficialTitle());

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
           props.put("mail.smtp.host",
           lookUpTableService.getPropertyValue("smtp"));
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
      LOG.debug("Entering sendXMLAndTSREmail");
      try {
          StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                  IiConverter.convertToLong(studyProtocolIi));

          String body = lookUpTableService.getPropertyValue("xml.body");
          body = body.replace(currentDate, getFormatedCurrentDate());
          body = body.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier().toString());
          body = body.replace(trialTitle, spDTO.getOfficialTitle().toString());
          body = body.replace(receiptDate, getFormatedDate(spDTO.getDateLastCreated()));
          body = body.replace(nciTrialIdentifier, spDTO.getNciIdentifier().toString());
          body = body.replace("${fileName}", spDTO.getNciIdentifier().toString() + ".xml");
          body = body.replace(ownerName, fullName);

          String folderPath = PaEarPropertyReader.getDocUploadPath();
          StringBuffer sb  = new StringBuffer(folderPath);
          String xmlFile = getXmlFile(studyProtocolIi, spDTO, sb);
          StringBuffer sb2  = new StringBuffer(folderPath);
          String tsrFile = getTSRFile(studyProtocolIi, spDTO, sb2);

          String mailSubject = lookUpTableService.getPropertyValue("xml.subject");
          mailSubject = mailSubject.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
          mailSubject = mailSubject.replace(nciTrialIdentifier, spDTO.getNciIdentifier());

          File[] attachments = {new File(xmlFile), new File(tsrFile)};
          sendMailWithAttachment(mailTo, mailSubject, body, attachments);
          new File(tsrFile).delete();
          new File(xmlFile).delete();
      } catch (Exception e) {
          throw new PAException("Exception occured while sending XML and TSR Report to submitter", e);
      }
      LOG.debug("Leaving sendXMLAndTSREmail");
  }


  private void sendEmail(StudyProtocolQueryDTO spDTO, String body,
          File[] attachments, String mailSubject) throws PAException {
      String emailSubject = mailSubject;
      emailSubject = emailSubject.replace(leadOrgTrialIdentifier, spDTO.getLocalStudyProtocolIdentifier());
      emailSubject = emailSubject.replace(nciTrialIdentifier, spDTO.getNciIdentifier());

      // We are making the assumption here that if the user created the trial then they have a registry
      // account and thus an email address saved in our system - aevansel 05/18/2010.
      // Sending email to all study owners, and not just the submitter - kanchink
      String emailAddress = null;
      try {
          Set <RegistryUser> studyOwners = getStudyOwners(spDTO);
          for (RegistryUser owner : studyOwners) {
              emailAddress = owner.getEmailAddress();
              String regUserName = owner.getFirstName() + " " + owner.getLastName();
              String emailBodyText = body.replace(ownerName, regUserName);
              sendMailWithAttachment(emailAddress, emailSubject, emailBodyText, attachments);
          }
      } catch (Exception e) {
          throw new PAException("Error attempting to send email to " + emailAddress, e);
      }
  }

  private Set<RegistryUser> getStudyOwners(StudyProtocolQueryDTO spDTO) throws PAException {
      Set <RegistryUser> studyOwners = new HashSet<RegistryUser>();
      try {
        StudyProtocol studyProtocol =
            (StudyProtocol) HibernateUtil.getCurrentSession().get(StudyProtocol.class, spDTO.getStudyProtocolId());
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
            String emailBody =  lookUpTableService.getPropertyValue(emailBodyLookupKey);
            emailBody = emailBody.replace(currentDate, getFormatedCurrentDate());
            emailBody = emailBody.replace("${affliateOrgName}", admin.getAffiliateOrg());
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
