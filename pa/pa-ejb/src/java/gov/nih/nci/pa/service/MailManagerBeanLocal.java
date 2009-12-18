/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceRemote;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

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
@SuppressWarnings({"PMD.FinalFieldCouldBeStatic", "PMD.TooManyMethods",
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity"  })
public class MailManagerBeanLocal implements MailManagerServiceLocal {
 
  private static final Logger LOG = Logger.getLogger(MailManagerBeanLocal.class);
  private static final int VAL = 65;
  private static final String TSR = "TSR_";
  private static final String WORD = ".doc";
  private final String currentDate = "${CurrentDate}";
  private final String nciTrialIdentifier = "${nciTrialIdentifier}";
  private final String  submitterName = "${SubmitterName}";

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
  @EJB
  DocumentWorkflowStatusServiceLocal docWrkflStatusSrv;
 
  /**
   * @param studyProtocolIi studyProtocolIi
   * @throws PAException PAException
   */
  @SuppressWarnings({ "PMD.StringInstantiation", "PMD.ExcessiveMethodLength" })
  public void sendTSREmail(Ii studyProtocolIi) throws PAException {
    LOG.info("Entering sendTSREmail");
    try {
      StudyProtocolQueryDTO spDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                                      IiConverter.convertToLong(studyProtocolIi));

      String body = "";
      if (spDTO.getAmendmentDate() != null && !spDTO.getAmendmentDate().equals("")) {
          body = lookUpTableService.getPropertyValue("tsr.amend.body");
      } else if (PAUtil.isNotEmpty(spDTO.getIsProprietaryTrial()) 
              && spDTO.getIsProprietaryTrial().equalsIgnoreCase("true")) {
          body = lookUpTableService.getPropertyValue("tsr.proprietary.body");
      } else {
          body = lookUpTableService.getPropertyValue("tsr.body");
      }
      body = body.replace(currentDate, getFormatedCurrentDate());
      body = body.replace("${leadOrgTrialId}", spDTO.getLocalStudyProtocolIdentifier().toString());
      body = body.replace("${trialTitle}", spDTO.getOfficialTitle().toString());
      body = body.replace("${receiptDate}", getFormatedDate(spDTO.getDateLastCreated()));
      body = body.replace("${nciTrialID}", spDTO.getNciIdentifier().toString());
      body = body.replace("${fileName}", TSR
                                         + spDTO.getNciIdentifier().toString() + WORD);
      if (PAUtil.isEmpty(spDTO.getIsProprietaryTrial()) 
              || spDTO.getIsProprietaryTrial().equalsIgnoreCase("false")) {
          body = body.replace("${fileName2}", spDTO.getNciIdentifier().toString() + ".xml");
      }
      body = body.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
      
      protocolQueryService.getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
      String folderPath = PaEarPropertyReader.getDocUploadPath();
      StringBuffer sb  = new StringBuffer(folderPath);
      String xmlFile = new String(sb.append(File.separator).append(
              spDTO.getNciIdentifier().toString() + ".xml"));

      if (PAUtil.isEmpty(spDTO.getIsProprietaryTrial()) 
              || spDTO.getIsProprietaryTrial().equalsIgnoreCase("false")) {
          //Format the xml only for non proprietary
          String xmlData = format(ctGovXmlGeneratorService.generateCTGovXml(studyProtocolIi));
          OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream(xmlFile));
          oos.write(xmlData);
          oos.close();
      }          
      StringBuffer sb2  = new StringBuffer(folderPath);
      String tsrFile = new String(sb2.append(File.separator).append(TSR).append(
          spDTO.getNciIdentifier().toString() + WORD));

      //File htmlFile = this.createAttachment(new File(inputFile), new File(outputFile));
      String htmlData = tsrReportGeneratorService.generateTSRHtml(studyProtocolIi);
      OutputStreamWriter oosHtml = new OutputStreamWriter(new FileOutputStream(tsrFile));
      oosHtml.write(htmlData);
      oosHtml.close();
      if (PAUtil.isNotEmpty(spDTO.getIsProprietaryTrial()) 
              && spDTO.getIsProprietaryTrial().equalsIgnoreCase("true")) {
          File[] attachments = {new File(tsrFile)};
          // Send Message
          sendMailWithAttachment(spDTO.getUserLastCreated(), //Mail Recipient
                  lookUpTableService.getPropertyValue("tsr.proprietary.subject"), // Mail Subject
                  body, // Mail Body
                  attachments); // Mail Attachments if any
          new File(tsrFile).delete();
      } else {
          File[] attachments = {new File(xmlFile), new File(tsrFile)};
          // Send Message
          sendMailWithAttachment(spDTO.getUserLastCreated(), //Mail Recipient
                  lookUpTableService.getPropertyValue("tsr.subject"), // Mail Subject
                  body, // Mail Body
                  attachments); // Mail Attachments if any
          new File(tsrFile).delete();
          new File(xmlFile).delete();
      }
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
  private  void sendMailWithAttachment(String mailTo, String subject,
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

  private  void sendMail(String mailTo, String subject, String mailBody) {
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
    mailBody = mailBody.replace("${amendmentNumber}", amendNumber);
    mailBody = mailBody.replace("${amendmentDate}", getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));

    sendMail(spDTO.getUserLastCreated(),
            lookUpTableService.getPropertyValue("trial.amend.subject"),
            mailBody);

    }

 /**
  * send mail to submitter when amended trial is accepted by CTRO staff.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendAmendAcceptEmail(Ii studyProtocolIi) throws PAException {
    LOG.info("Entering send Amend Accept Email");
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
    mailBody = mailBody.replace("${amendmentNumber}", amendNumber);
    mailBody = mailBody.replace("${amendmentDate}", getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    
    sendMail(spDTO.getUserLastCreated(),
              lookUpTableService.getPropertyValue("trial.amend.accept.subject"),
              mailBody);
    LOG.info("Leaving sendAmendAcceptEmail");
  }
 /**
  * Sends an email notifying the submitter that the protocol is registered in the system.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendNotificationMail(Ii studyProtocolIi) throws PAException  {
    StudyProtocolQueryDTO spDTO = protocolQueryService
        .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));

    String submissionMailBody = lookUpTableService.getPropertyValue("trial.register.body");
    submissionMailBody = submissionMailBody.replace(currentDate, getFormatedCurrentDate());
    submissionMailBody = submissionMailBody.replace("${leadOrgTrialIdentifier} ",
            spDTO.getLocalStudyProtocolIdentifier());
    submissionMailBody = submissionMailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    submissionMailBody = submissionMailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    sendMail(spDTO.getUserLastCreated(),
                lookUpTableService.getPropertyValue("trial.register.subject"),
                submissionMailBody);
  }
 /**
  * Sends an email to submitter when Amendment to trial is rejected by CTRO staff.
  * @param studyProtocolIi ii
  * @param rejectReason rr
  * @throws PAException ex
  */
  public void sendAmendRejectEmail(Ii studyProtocolIi, String rejectReason) throws PAException {
    LOG.info("Entering send Amend reject Email");
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
    mailBody = mailBody.replace("${amendmentNumber}", amendNumber);
    mailBody = mailBody.replace("${amendmentDate}", getFormatedDate(spDTO.getAmendmentDate()));
    mailBody = mailBody.replace("${reasonForRejection}", rejectReason);
    mailBody = mailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    sendMail(spDTO.getUserLastCreated(),
              lookUpTableService.getPropertyValue("trial.amend.reject.subject"),
              mailBody);
    LOG.info("Leaving sendAmendAcceptEmail");
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
    body = body.replace("${leadOrgTrialId}", spDTO.getLocalStudyProtocolIdentifier());
    body = body.replace("${reasoncode}", commentText);
    body = body.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    // Send Message
    sendMail(spDTO.getUserLastCreated(), 
            lookUpTableService.getPropertyValue("rejection.subject"), 
            body);
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
 private String getSumitterFullName(String userLastCreated) throws PAException {
    RegistryUser registryUser = registryUserService.getUser(userLastCreated);
    return  registryUser.getFirstName() + " " + registryUser.getLastName();
 }
 /**
  * send mail to submitter when trial is accepted by CTRO staff.
  * @param studyProtocolIi ii
  * @throws PAException ex
  */
  public void sendAcceptEmail(Ii studyProtocolIi) throws PAException {
    LOG.info("Entering send Accept Email");
    StudyProtocolQueryDTO spDTO = protocolQueryService
                  .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(studyProtocolIi));
    String mailBody = lookUpTableService.getPropertyValue("trial.accept.body");
    mailBody = mailBody.replace(currentDate, getFormatedCurrentDate());
    mailBody = mailBody.replace(nciTrialIdentifier, spDTO.getNciIdentifier());
    mailBody = mailBody.replace("${title}", spDTO.getOfficialTitle());
    mailBody = mailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    sendMail(spDTO.getUserLastCreated(),
              lookUpTableService.getPropertyValue("trial.accept.subject"),
              mailBody);
    LOG.info("Leaving send AcceptEmail");
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
    mailBody = mailBody.replace(submitterName, getSumitterFullName(spDTO.getUserLastCreated()));
    sendMail(spDTO.getUserLastCreated(),
            lookUpTableService.getPropertyValue("trial.update.subject"),
            mailBody);

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

 }
