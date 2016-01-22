package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.OrgFamilyDTO;
import gov.nih.nci.pa.iso.dto.ProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.FamilyHelper;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.registry.util.Constants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.json.JSONArray;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Program codes
 * @author lalit-sb
 *
 */

@SuppressWarnings({  "PMD.TooManyMethods" })
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
        boolean isProgramCodeAdmin = ServletActionContext.getRequest()
                .isUserInRole(Constants.PROGRAM_CODE_ADMINISTRATOR);
        List<OrgFamilyDTO> affiliatedFamilies = new ArrayList<OrgFamilyDTO>();
        if (isProgramCodeAdmin) {
            affiliatedFamilies = getAllFamiliesDto();
        } else {
            affiliatedFamilies = FamilyHelper.getByOrgId(registryUser.getAffiliatedOrganizationId());
        }
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
     * Get all family DTOs
     * @return List<OrgFamilyDTO> List<OrgFamilyDTO>
     * @throws PAException
     */
    private List<OrgFamilyDTO> getAllFamiliesDto() throws PAException {
        List<OrgFamilyDTO> families = FamilyHelper.getAllFamilies();
        Collections.sort(families, new Comparator<OrgFamilyDTO>() {
            @Override
            public int compare(OrgFamilyDTO o1, OrgFamilyDTO o2) {
                return StringUtils
                        .defaultString(o1.getName())
                        .compareTo(
                                StringUtils.defaultString(o2.getName()));
            }
        });
        return families;
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
    
    /**
     * Gets program codes for family 
     * @throws UnsupportedEncodingException Unsupported encoding exception
     * @return JSON String
     */
    
    public StreamResult fetchProgramCodesForFamily() throws UnsupportedEncodingException {
        JSONObject root = new JSONObject();
        JSONArray arr = new JSONArray();
        root.put("data", arr);
        populateProgramCodes(arr);
        return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
    }
    
    private void populateProgramCodes(JSONArray arr) {
        LOG.debug("populating program codes for [familyPOId : " + poId + "]");
        FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(Long.parseLong(poId));
        if (familyDTO != null) {
            for (ProgramCodeDTO programCodeDTO : familyDTO.getProgramCodes()) {
                JSONObject o = new JSONObject();
                o.put("programCodeId", programCodeDTO.getId());
                o.put("programName", programCodeDTO.getProgramName());
                o.put("programCode", programCodeDTO.getProgramCode());
                o.put("isActive", programCodeDTO.isActive());
                arr.put(o);
             }
        }

      }
    
    /**
     * Creates a new program code 
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult createProgramCode() throws IOException {
        try {
            LOG.debug("Creating and adding a new program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put("data", arr);
            addProgramCode();
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    private void addProgramCode() throws PAValidationException {
        FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(Long.parseLong(poId));
        String programCode = request.getParameter("newProgramCode"); 
        String programName = request.getParameter("newProgramName");
        // create and add a new active program code to the family
        ProgramCodeDTO programCodeDTO = new ProgramCodeDTO();
        programCodeDTO.setProgramCode(programCode);
        programCodeDTO.setProgramName(programName);
        programCodeDTO.setActive(true);
        familyProgramCodeService.createProgramCode(familyDTO, programCodeDTO);
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
     * Update program code 
     * @throws IOException IO exception
     * @return JSON String
     */
    
    public StreamResult updateProgramCode() throws IOException {
        try {
            LOG.debug("Updating program code for [familyPOId : " + poId + "]");
            JSONObject root = new JSONObject();
            JSONArray arr = new JSONArray();
            root.put("data", arr);
            editProgramCode();
            return new StreamResult(new ByteArrayInputStream(root.toString().getBytes(UTF_8)));
        } catch (Exception e) {
            return handleExceptionDuringAjax(e);
        }
    }
    
    private void editProgramCode() throws PAValidationException {
        FamilyDTO familyDTO = familyProgramCodeService.getFamilyDTOByPoId(Long.parseLong(poId));
        
        String currentProgramCodeValue = request.getParameter("currentProgramCode");
        String currentProgramCodeId = request.getParameter("currentProgramCodeId");
        ProgramCodeDTO currentProgramCodeDTO = new ProgramCodeDTO();
        currentProgramCodeDTO.setId(Long.parseLong(currentProgramCodeId));
        currentProgramCodeDTO.setProgramCode(currentProgramCodeValue);
        
        String updatedProgramCode = request.getParameter("updatedProgramCode");
        String updatedProgramName = request.getParameter("updatedProgramName");
        ProgramCodeDTO updatedProgramCodeDTO = new ProgramCodeDTO();
        updatedProgramCodeDTO.setProgramCode(updatedProgramCode);
        updatedProgramCodeDTO.setProgramName(updatedProgramName);
        
        familyProgramCodeService.updateProgramCode(familyDTO, currentProgramCodeDTO, updatedProgramCodeDTO);
      }

    /**
     * Does bean injections
     * @throws Exception on exception
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
