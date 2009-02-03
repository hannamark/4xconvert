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
        // TSRReportGenerator tsrReport = new TSRReportGenerator();
        String htmlData = PaRegistry.getTSRReportGeneratorService().generateTSRHtml(studyProtocolIi);
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
        
        //File htmlFile = this.createAttachment(new File(inputFile), new File(outputFile));
        String htmlData = PaRegistry.getTSRReportGeneratorService().generateTSRHtml(studyProtocolIi);
        OutputStreamWriter oosHtml = new OutputStreamWriter(new FileOutputStream(outputFile));
        oosHtml.write(htmlData);
        oosHtml.close();
        File[] attachments = {new File(inputFile), new File(outputFile)};

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
    
