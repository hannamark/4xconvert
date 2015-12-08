package gov.nih.nci.registry.action;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

/**
 * Program codes
 * @author lalit-sb
 *
 */
public class ProgramCodesAction extends ActionSupport implements Preparable, ServletRequestAware, ServletResponseAware {
    
    private static final long serialVersionUID = 4866651110688880068L;    
    
    private static final Logger LOG = Logger
            .getLogger(ProgramCodesAction.class);
    
    private static final String UTF_8 = "UTF-8";
    
    private List<FamilyDTO> familyDTOs = new ArrayList<>();    
    private FamilyDTO selectedFamilyDTO; 
    
    private FamilyProgramCodeService familyProgramCodeService;
    

    private RegistryUserService registryUserService;    

    private LookUpTableServiceRemote lookUpTableServiceRemote;    
    
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    private String poId;
    private String reportingLength;
    private String reportingDate;
    private Long selectedDTOId;    

    /**
     * execute
     * @throws PAException paexception
     * @throws ParseException parseexception
     * @return success value
     */
    @Override
    public String execute() throws PAException, ParseException {   
        
        request = ServletActionContext.getRequest();
        RegistryUser registryUser = registryUserService.getUser(request.getRemoteUser());    
        
        List<OrgFamilyDTO> affiliatedFamilies = FamilyHelper.getByOrgId(registryUser.getAffiliatedOrganizationId());
        for (OrgFamilyDTO orgFamilyDTO : affiliatedFamilies) {            
            findOrCreateFamilyAndAddToList(orgFamilyDTO);
        }    
        
        if (!familyDTOs.isEmpty()) {            
            if (selectedDTOId != null) {
                for (FamilyDTO familyDTO : familyDTOs) {
                    if (familyDTO.getId().equals(selectedDTOId)) {
                        setSelectedFamilyDTO(familyDTO);
                    }
                }
            } else {
                setSelectedFamilyDTO(familyDTOs.get(0));
            }            
        }        
        return SUCCESS;
    }    
    
    /**
     * Changes Ajax date 
     * @throws ParseException parse exception
     * @throws IOException IO exception
     * @return stream result
     */
    public StreamResult ajaxChangeDate() throws ParseException, IOException {        
        try {            
            FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(Long.parseLong(poId));        
            familyDTO.setReportingPeriodEndDate(new 
                    SimpleDateFormat(PAUtil.DATE_FORMAT, Locale.getDefault()).parse(reportingDate));
            familyProgramCodeService.update(familyDTO);            
        
            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }            
    }
    
    /**
     * Changes period length 
     * @throws ParseException parse exception
     * @throws IOException IO exception
     * @return JSON String
     */
    public StreamResult ajaxChangeLength() throws ParseException, IOException {        
        try {                        
            FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(Long.parseLong(poId));        
            familyDTO.setReportingPeriodLength(Integer.parseInt(reportingLength));
            familyProgramCodeService.update(familyDTO);            
            
            return new StreamResult(new ByteArrayInputStream(new JSONObject()
                    .toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }        
    }
    
    private void findOrCreateFamilyAndAddToList(OrgFamilyDTO orgFamilyDTO) throws PAException, ParseException {
        FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(orgFamilyDTO.getId());        
        if (familyDTO == null) {            
            String defaultDate = lookUpTableServiceRemote.
                    getPropertyValue("programcodes.reporting.default.end_date");
            String defaultLength = lookUpTableServiceRemote.
                    getPropertyValue("programcodes.reporting.default.length");            
            Date effectiveDate = new SimpleDateFormat(PAUtil.DATE_FORMAT, 
                    Locale.getDefault()).parse(defaultDate);
            FamilyDTO newFamilyDTO = familyProgramCodeService.create(
                    new FamilyDTO(orgFamilyDTO.getId(), effectiveDate, 
                    Integer.parseInt(defaultLength)));
            newFamilyDTO.setName(orgFamilyDTO.getName());    
            familyDTOs.add(newFamilyDTO);
        } else {
            familyDTO.setName(orgFamilyDTO.getName());
            familyDTOs.add(familyDTO);
        }
    }
    
    private StreamResult handleExceptionDuringAjax(Exception e)
            throws IOException {
        LOG.error(e, e);
        response.addHeader("msg", e.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        return null;
    }

    /**
     * Does bean injections
     * @throws Exception exception
     */
    @Override
    public void prepare() throws Exception {
        familyProgramCodeService = PaRegistry.getProgramCodesFamilyService();
        registryUserService = PaRegistry.getRegistryUserService();
        lookUpTableServiceRemote = PaRegistry.getLookUpTableService();
    }
    
    /**
     * get all family dtos
     * @return familyDTO list
     */
    public List<FamilyDTO> getFamilyDTOs() {
        return familyDTOs;
    }

    /**
     * sets list of family dto's
     * @param familyDTOs list
     */
    public void setFamilyDTOs(List<FamilyDTO> familyDTOs) {
        this.familyDTOs = familyDTOs;
    }
    
    /**
     * family dto
     * @return family DTO
     */
    public FamilyDTO getSelectedFamilyDTO() {
        return selectedFamilyDTO;
    }

    /**
     * sets the selected DTO
     * @param selectedFamilyDTO selected family
     */
    public void setSelectedFamilyDTO(FamilyDTO selectedFamilyDTO) {
        this.selectedFamilyDTO = selectedFamilyDTO;
    }    
    
    /**
     * @param resp
     *            the servletResponse to set
     */
    @Override
    public void setServletResponse(HttpServletResponse resp) {
        this.response = resp;        
    }

    /**
     * @param req
     *            the servletRequest to set
     */
    @Override
    public void setServletRequest(HttpServletRequest req) {
        this.request = req;        
    }    
    
    /**
     * Gets PO ID
     * @return poId poId
     */
    public String getPoId() {
        return poId;
    }

    /**
     * sets po id
     * @param poId poId
     */
    public void setPoId(String poId) {
        this.poId = poId;
    }
    
    /**
     * reporting length
     * @return reportingLength reporting length
     */
    public String getReportingLength() {
        return reportingLength;
    }

    /**
     * sets reporting length
     * @param reportingLength reporting length
     */
    public void setReportingLength(String reportingLength) {
        this.reportingLength = reportingLength;
    }
    
    /**
     * gets reporting period date
     * @return reporting period date
     */
    public String getReportingDate() {
        return reportingDate;
    }

    /**
     * sets reporting date
     * @param reportingDate reporting date
     */
    public void setReportingDate(String reportingDate) {
        this.reportingDate = reportingDate;
    }
    
    /**
     * family program code
     * @return familyProgramCodeService family program code
     */
    public FamilyProgramCodeService getFamilyProgramCodeService() {
        return familyProgramCodeService;
    }

    /**
     * sets family program code 
     * @param familyProgramCodeService family program code service
     */
    public void setFamilyProgramCodeService(FamilyProgramCodeService familyProgramCodeService) {
        this.familyProgramCodeService = familyProgramCodeService;
    }
    
    /**
     * selected dto id
     * @return selectedDTO id
     */
    public Long getSelectedDTOId() {
        return selectedDTOId;
    }

    /**
     * sets dto id for selected family
     * @param selectedDTOId selected dto id
     */
    public void setSelectedDTOId(Long selectedDTOId) {
        this.selectedDTOId = selectedDTOId;
    }
    
    /**
     * returns registry user service 
     * @return registryUserService user service
     */
    public RegistryUserService getRegistryUserService() {
        return registryUserService;
    }

    /**
     * Sets registry user service
     * @param registryUserService registry user service
     */
    public void setRegistryUserService(RegistryUserService registryUserService) {
        this.registryUserService = registryUserService;
    }
}
