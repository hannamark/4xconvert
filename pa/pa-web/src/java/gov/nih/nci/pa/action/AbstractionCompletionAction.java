package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.service.util.TSRReportGenerator;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 * @since 11/22/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings("PMD")

public class AbstractionCompletionAction extends ActionSupport implements ServletResponseAware {
   
    private List<AbstractionCompletionDTO> abstractionList = null;
    private boolean abstractionError = false;
    private Long studyProtocolId = null;
    private static final String DISPLAY_XML = "displayXML";
    private HttpServletResponse servletResponse;
    private static final String TSR = "TSR_";
    private static final String HTML = ".html";
    


    /**
    * @return result
    */
    public String query() {
        LOG.info("Entering query");
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            
            PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
            IiConverter.convertToLong(studyProtocolIi));
            
            abstractionList = new ArrayList<AbstractionCompletionDTO>();
            abstractionList = PaRegistry.getAbstractionCompletionService().
                validateAbstractionCompletion(studyProtocolIi);
            abstractionError = errorExists();
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
    return SUCCESS;
    }

    /**
     * 
     * @return String
     */
    public String complete() {
        LOG.info("Entering Complete");
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            
            DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
            dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTED));
            dwsDto.setStatusDateRange(TsConverter.convertToTs(
                              new Timestamp(new Date().getTime())));
            dwsDto.setStudyProtocolIdentifier(studyProtocolIi);
            PaRegistry.getDocumentWorkflowStatusService().create(dwsDto);

            StudyProtocolDTO spDTO = new StudyProtocolDTO();
            spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            spDTO.setRecordVerificationDate(TsConverter.convertToTs(
               new Timestamp(new Date().getTime())));
            PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
            
            StudyProtocolQueryDTO  studyProtocolQueryDTO = 
            PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                        IiConverter.convertToLong(studyProtocolIi));
            // put an entry in the session and store StudyProtocolQueryDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        LOG.info("Leaving Complete");
    return SUCCESS;
    }
    
    /**
     * 
     * @return String
     */
    public String verified() {
      LOG.info("Entering verified");
      try {
          Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
          getAttribute(Constants.STUDY_PROTOCOL_II);
          
          DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
          dwsDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED));
          dwsDto.setStatusDateRange(TsConverter.convertToTs(
                            new Timestamp(new Date().getTime())));
          dwsDto.setStudyProtocolIdentifier(studyProtocolIi);
          PaRegistry.getDocumentWorkflowStatusService().create(dwsDto);
          
          StudyProtocolDTO spDTO = new StudyProtocolDTO();
          spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
          spDTO.setRecordVerificationDate(TsConverter.convertToTs(
             new Timestamp(new Date().getTime())));
          PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
          
          StudyProtocolQueryDTO  studyProtocolQueryDTO = 
          PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                      IiConverter.convertToLong(studyProtocolIi));
          // put an entry in the session and store StudyProtocolQueryDTO 
          ServletActionContext.getRequest().getSession().setAttribute(
                  Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
          
      } catch (Exception e) {
          ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
      }
      LOG.info("Leaving verified");
  return SUCCESS;
    }

    /**
     * @return res
     */
    public String generateXML() {
        try {
            String pId = (String) ServletActionContext.getRequest()
                    .getParameter("studyProtocolId");
            if (pId == null) {
                return DISPLAY_XML;
            }
            
            PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                Long.valueOf(pId));
            
            String xmlData = PaRegistry.getCTGovXmlGeneratorService().generateCTGovXml(IiConverter
                    .convertToIi(studyProtocolId));
            servletResponse.setContentType("application/xml");
            servletResponse.setContentLength(xmlData.length());
            ServletOutputStream out = servletResponse.getOutputStream();
            out.write(xmlData.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            return DISPLAY_XML;
        }
        return NONE;
    }
    
    /**
     * @return res
     */
    public String viewTSR() {
      try {
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
        getAttribute(Constants.STUDY_PROTOCOL_II);

        PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
            IiConverter.convertToLong(studyProtocolIi));
        //String xmlData = PaRegistry.getCTGovXmlGeneratorService().generateCTGovXml(studyProtocolIi);  
         TSRReportGenerator tsrReport = new TSRReportGenerator();
        String htmlData = tsrReport.generateTSRHtml(studyProtocolIi);
//        String folderPath = PaEarPropertyReader.getDocUploadPath();
//        StringBuffer sb  = new StringBuffer(folderPath);
        final int i = 1000;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(i);

//        String inputFile = new String(sb.append(File.separator).append("xmlfile_").append(randomInt 
//            + studyProtocolIi.getExtension() + ".html"));
//        OutputStreamWriter oos = new OutputStreamWriter(new FileOutputStream(inputFile));
//        oos.write(xmlData);
//        oos.close();

 //       StringBuffer sb2  = new StringBuffer(folderPath);
//        String outputFile = new String(sb2.append(File.separator).append(TSR).append(randomInt 
//            + studyProtocolIi.getExtension() + HTML));
//        File downloadFile = createAttachment(new File(inputFile), new File(outputFile));
        String fileName = TSR + randomInt + studyProtocolIi.getExtension() + HTML;
//        FileInputStream fileToDownload = new FileInputStream(downloadFile);
        servletResponse.setContentType("application/octet-stream");
        servletResponse.setContentLength(htmlData.length());
        servletResponse.setHeader("Content-Disposition", "attachment; filename=\""  + fileName + "\"");
        servletResponse.setHeader("Pragma", "public");
        servletResponse.setHeader("Cache-Control", "max-age=0");

//        int data;
        ServletOutputStream servletout = servletResponse.getOutputStream();
//        while ((data = fileToDownload.read()) != -1) {
          servletout.write(htmlData.getBytes());
//        }
        servletout.flush();
        servletout.close();
//        fileToDownload.close();
        
//        new File(inputFile).delete();
//        new File(outputFile).delete();
        
      } catch (Exception e) {
        return SUCCESS;
      }
      return NONE;
    }
    
    private File createAttachment(File xml, File xmlOutput) {
      PrintWriter out = null;
      try {
        TransformerFactory tFactory = TransformerFactory.newInstance();
        InputStream xslFile = AbstractionCompletionAction.class.getClassLoader()
                            .getResourceAsStream("/tsr.xsl");

        if (xml != null) {
          out = new PrintWriter(xmlOutput);
          Transformer trans = tFactory.newTransformer(new StreamSource(xslFile));
          trans.transform(new StreamSource(xml), new StreamResult(out));
          out.close();
        }
      } catch (TransformerException te) {
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, te.getLocalizedMessage());
      } catch (FileNotFoundException fe) {
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, fe.getLocalizedMessage());
      }
      return xmlOutput;
    }

   
    
    /**
     * 
     * @return String
     */
    @SuppressWarnings({ "PMD.StringInstantiation", "PMD.ExcessiveMethodLength", "PMD.SimpleDateFormatNeedsLocale" })
    public String sendEmail() {
      LOG.info("Entering sendEmail");
      try {
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
        .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
                
        Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
        getAttribute(Constants.STUDY_PROTOCOL_II);

        Properties props = System.getProperties();
        
        // Set up mail server
        props.put("mail.smtp.host", PaRegistry.getLookUpTableService().getPropertyValue("smtp"));

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define Message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(PaRegistry.getLookUpTableService().getPropertyValue("fromaddress")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(spDTO.getUserLastCreated()));
        message.setSentDate(new java.util.Date());
        message.setSubject(PaRegistry.getLookUpTableService().getPropertyValue("tsr.subject"));
        RegistryUser userbean = PoPaServiceBeanLookup.getRegistryUserService().getUser(spDTO.getUserLastCreated());
        // body
        Multipart multipart = new MimeMultipart();

        Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        // message
        BodyPart msgPart = new MimeBodyPart();
        String body = PaRegistry.getLookUpTableService().getPropertyValue("tsr.body");
        String mailBody1 = body.replace("${CurrentDate}", format.format(date));
        String mailBody2 = mailBody1.replace("${SubmitterName}", 
                userbean.getFirstName() + " " + userbean.getLastName());
        String mailBody3 = mailBody2.replace("${localOrgID}", spDTO.getLeadOrganizationId().toString());
        String mailBody4 = mailBody3.replace("${trialTitle}", spDTO.getOfficialTitle().toString());
        String mailBody5 = mailBody4.replace("${receiptDate}", format.format(spDTO.getDateLastCreated()));
        String mailBody6 = mailBody5.replace("${nciTrialID}", spDTO.getNciIdentifier().toString());
        String mailBody7 = mailBody6.replace("${fileName}", TSR 
                                           + spDTO.getNciIdentifier().toString() + HTML);
        String mailBody8 = mailBody7.replace("${fileName2}", spDTO.getNciIdentifier().toString() + ".xml");
        msgPart.setText(mailBody8);
        multipart.addBodyPart(msgPart);

        PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
            IiConverter.convertToLong(studyProtocolIi));
        String xmlData = PaRegistry.getCTGovXmlGeneratorService().generateCTGovXml(studyProtocolIi);
        
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
        
        File htmlFile = this.createAttachment(new File(inputFile), new File(outputFile));
        File[] attachments = {new File(inputFile), htmlFile};

        // attachments        
        for (File attachment : attachments) {
          MimeBodyPart attPart = new MimeBodyPart();
          attPart.setDataHandler(new DataHandler(new FileDataSource(attachment)));
          attPart.setFileName(attachment.getName());
          multipart.addBodyPart(attPart);
        }
        message.setContent(multipart);       

        // Send Message
        Transport.send(message);

        new File(inputFile).delete();
        new File(outputFile).delete();
        
      } catch (Exception e) {
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
      }
      LOG.info("Leaving sendEmail");
      return SUCCESS;
    }
            
    /**
     * 
     * @return res
     */
    public String displayReportingXML() {
        String pId = (String) ServletActionContext.getRequest().getParameter(
                "studyProtocolId");
        ServletActionContext.getRequest().setAttribute(
                "protocolIdForXmlGeneration", pId);
        return DISPLAY_XML;
    }
    
    /**
    * @return abstractionList
    */
    public List<AbstractionCompletionDTO> getAbstractionList() {
      return abstractionList;
    }

    /**
    * @param abstractionList abstractionList
    */
    public void setAbstractionList(List<AbstractionCompletionDTO> abstractionList) {
      this.abstractionList = abstractionList;
    }
  
    private boolean errorExists() {
        boolean errorExist = false;
        for (AbstractionCompletionDTO  absDto : abstractionList) {
            if (absDto.getErrorType().equalsIgnoreCase("error")) {
                errorExist = true;
                break;
            }
        }
        return errorExist;
    }

    /**
     * 
     * @return abstractionError abstractionError
     */
    public boolean isAbstractionError() {
        return abstractionError;
    }

    /**
     * 
     * @param abstractionError abstractionError
     */
    public void setAbstractionError(boolean abstractionError) {
        this.abstractionError = abstractionError;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param response
     *            servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }    
}    
    
