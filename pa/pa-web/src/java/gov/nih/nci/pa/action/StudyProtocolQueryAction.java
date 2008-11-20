package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

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
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField",
        "PMD.SingularField" })
@Validation
public class StudyProtocolQueryAction extends ActionSupport implements
        ServletResponseAware {
    private List<StudyProtocolQueryDTO> records = null;
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private Long studyProtocolId = null;
    private HttpServletResponse servletResponse;
    private static final String DISPLAY_XML = "displayXML";

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

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
            records = PaRegistry.getProtocolQueryService()
                    .getStudyProtocolByCriteria(criteria);
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
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
            
            CTGovXmlGeneratorServiceBean ct = new CTGovXmlGeneratorServiceBean();
            String xmlDate = ct.generateCTGovXml(IiConverter
                    .convertToIi(studyProtocolId));
            servletResponse.setContentType("application/xml");
            servletResponse.setContentLength(xmlDate.length());
            ServletOutputStream out = servletResponse.getOutputStream();
            out.write(xmlDate.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {
            return DISPLAY_XML;
        }
        return DISPLAY_XML;
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
                    Constants.STUDY_PROTOCOL_II,
                    IiConverter.convertToIi(studyProtocolId));
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
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
     * @param response
     *            servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }
}