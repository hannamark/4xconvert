package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 * @since 11/22/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
public class AbstractionCompletionAction extends ActionSupport implements ServletResponseAware {
   
    private List<AbstractionCompletionDTO> abstractionList = null;
    private boolean abstractionError = false;
    private Long studyProtocolId = null;
    private static final String DISPLAY_XML = "displayXML";
    private HttpServletResponse servletResponse;
    


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
        return DISPLAY_XML;
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
    
