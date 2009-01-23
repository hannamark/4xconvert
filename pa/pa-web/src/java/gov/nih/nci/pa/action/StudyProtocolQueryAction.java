package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;


/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class StudyProtocolQueryAction extends ActionSupport implements ServletResponseAware {
    private List<StudyProtocolQueryDTO> records = null;
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private Long studyProtocolId = null;
    private HttpServletResponse servletResponse;
    /**
     * @return res
     */
    public String execute() {
        return SUCCESS;
    }

    /**
     * @return res
     */
    public String showCriteria() {
        return "criteria";
    }

    /**
     * @return res
     */
    public String query() {
        try {
            records = new ArrayList<StudyProtocolQueryDTO>();
            records = PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(criteria);
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }


    /**
     * 
     * @return records
     */
    public List<StudyProtocolQueryDTO> getRecords() {
        return records;
    }

    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }

    /**
     * 
     * @param criteria
     *            StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
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
     * @param studyProtocolId
     *            studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return res
     */
    public String view() {
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry
                    .getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(studyProtocolId));
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.DOC_WFS_MENU, setMenuLinks(studyProtocolQueryDTO.getDocumentWorkflowStatusCode())); 
            
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**
     * @return res
     */
    public String viewTSR() {
        
        try {
            String pId = (String) ServletActionContext.getRequest().getParameter("studyProtocolId");
            PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                    Long.valueOf(pId));
                
            String htmlData = PaRegistry.getTSRReportGeneratorService().generateTSRHtml(
                    IiConverter.convertToIi(pId));

            final int i = 1000;
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(i);

            String fileName = "TSR" + randomInt  + ".html";
            servletResponse.setContentType("application/octet-stream");
            servletResponse.setContentLength(htmlData.length());
            servletResponse.setHeader("Content-Disposition", "attachment; filename=\""  + fileName + "\"");
            servletResponse.setHeader("Pragma", "public");
            servletResponse.setHeader("Cache-Control", "max-age=0");

            ServletOutputStream servletout = servletResponse.getOutputStream();
              servletout.write(htmlData.getBytes());
            servletout.flush();
            servletout.close();
            
          } catch (Exception e) {
              LOG.error("Error while generating TSR Summary report " , e);
              return NONE;
          }
          return NONE;
    }
    

    private String setMenuLinks(DocumentWorkflowStatusCode dwsCode) {
        String action = "";
        if (DocumentWorkflowStatusCode.REJECTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.REJECTED.getCode();
        } else if (DocumentWorkflowStatusCode.SUBMITTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.SUBMITTED.getCode();
        } else {
            action = DocumentWorkflowStatusCode.ACCEPTED.getCode();
        }
        return action;
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