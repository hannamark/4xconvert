/**
 * 
 */
package gov.nih.nci.pa.action;

import java.util.List;

import gov.nih.nci.pa.domain.OrgFamilyProgramCode;
import gov.nih.nci.pa.noniso.convert.OrgFamilyProgramCodeConverter;
import gov.nih.nci.pa.noniso.dto.OrgFamilyProgramCodeDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.OrgFamilyProgramCodeService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action dealing with Org Family Program Codes
 * 
 * @author vinodh
 *
 */
public class OrgFamilyProgramCodeAction extends ActionSupport implements
        Preparable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2243575924091115071L;

    private static final Logger LOG = Logger
            .getLogger(OrgFamilyProgramCodeAction.class);

    private OrgFamilyProgramCodeService orgFamilyProgramCodeService;

    private Long studySiteId;
    private String poOrgFamilyId;
    private String poOrgFamilyName;
    private OrgFamilyProgramCodeDTO orgFamProgramCodeDto;

    private Boolean changeMadeFlag = Boolean.FALSE;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        orgFamilyProgramCodeService = PaRegistry
                .getOrgFamilyProgramCodeService();
        if (orgFamProgramCodeDto == null) {
            orgFamProgramCodeDto = new OrgFamilyProgramCodeDTO();
        }
    }

    /**
     * Setting up
     * 
     * @return Action result
     * @throws PAException
     *             on errror
     */
    @Override
    public String execute() throws PAException {
        setPoOrgFamilyId(ServletActionContext.getRequest().getParameter(
                "poOrgFamilyId"));
        setPoOrgFamilyName(ServletActionContext.getRequest().getParameter(
                "poOrgFamilyName"));
        if (StringUtils.isEmpty(poOrgFamilyId)
                || StringUtils.isEmpty(poOrgFamilyName)) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Organization family id and name are required");
        }
        return Action.SUCCESS;
    }

    /**
     * Adds new program code to the org family
     * 
     * @return Action result
     * @throws PAException
     *             on errror
     */
    public String create() throws PAException {
        orgFamProgramCodeDto.setFamilyPoId(getPoOrgFamilyId());

        try {
            OrgFamilyProgramCode orgFamPrgCd = new OrgFamilyProgramCodeConverter()
                    .convertFromDtoToDomain(orgFamProgramCodeDto);
            validateInputs();
            if (hasActionErrors()) {
                return Action.ERROR;
            }
            List<OrgFamilyProgramCode> srchRes = orgFamilyProgramCodeService
                    .search(orgFamPrgCd);
            if (srchRes != null && !srchRes.isEmpty()) {
                ServletActionContext.getRequest().setAttribute(
                        Constants.FAILURE_MESSAGE,
                        "Entered program code and program name combo already exists for, "
                                + poOrgFamilyName);
                return Action.ERROR;
            }
            orgFamilyProgramCodeService.createOrgFamilyProgramCode(orgFamPrgCd);
            setChangeMadeFlag(true);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Error while adding new program code for the family, "
                            + poOrgFamilyName);
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }
    
  
    private void validateInputs() {
        if (StringUtils.isEmpty(poOrgFamilyId)
                || StringUtils.isEmpty(poOrgFamilyName)) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE,
                    "Organization family id and name are required");
        }

        if (StringUtils.isEmpty(orgFamProgramCodeDto.getProgramCode())) {
            addFieldError("orgFamProgramCodeDto.programCode",
                    "Program code is a required field");
        }

        if (StringUtils.isEmpty(orgFamProgramCodeDto.getProgramName())) {
            addFieldError("orgFamProgramCodeDto.programName",
                    "Program name is a required field");
        }
    }

    /**
     * @return the changeMadeFlag
     */
    public boolean isChangeMadeFlag() {
        return changeMadeFlag;
    }

    /**
     * @param changeMadeFlag
     *            the changeMadeFlag to set
     */
    public void setChangeMadeFlag(boolean changeMadeFlag) {
        this.changeMadeFlag = changeMadeFlag;
    }

    /**
     * @return the orgFamilyProgramCodeService
     */
    public OrgFamilyProgramCodeService getOrgFamilyProgramCodeService() {
        return orgFamilyProgramCodeService;
    }

    /**
     * @param orgFamilyProgramCodeService
     *            the orgFamilyProgramCodeService to set
     */
    public void setOrgFamilyProgramCodeService(
            OrgFamilyProgramCodeService orgFamilyProgramCodeService) {
        this.orgFamilyProgramCodeService = orgFamilyProgramCodeService;
    }

    /**
     * @return the studySiteId
     */
    public Long getStudySiteId() {
        return studySiteId;
    }

    /**
     * @param studySiteId
     *            the studySiteId to set
     */
    public void setStudySiteId(Long studySiteId) {
        this.studySiteId = studySiteId;
    }

    /**
     * @return the poOrgFamilyId
     */
    public String getPoOrgFamilyId() {
        return poOrgFamilyId;
    }

    /**
     * @param poOrgFamilyId
     *            the poOrgFamilyId to set
     */
    public void setPoOrgFamilyId(String poOrgFamilyId) {
        this.poOrgFamilyId = poOrgFamilyId;
    }

    /**
     * @return the poOrgFamilyName
     */
    public String getPoOrgFamilyName() {
        return poOrgFamilyName;
    }

    /**
     * @param poOrgFamilyName
     *            the poOrgFamilyName to set
     */
    public void setPoOrgFamilyName(String poOrgFamilyName) {
        this.poOrgFamilyName = poOrgFamilyName;
    }

    /**
     * @return the orgFamProgramCodeDto
     */
    public OrgFamilyProgramCodeDTO getOrgFamProgramCodeDto() {
        return orgFamProgramCodeDto;
    }

    /**
     * @param orgFamProgramCodeDto
     *            the orgFamProgramCodeDto to set
     */
    public void setOrgFamProgramCodeDto(
            OrgFamilyProgramCodeDTO orgFamProgramCodeDto) {
        this.orgFamProgramCodeDto = orgFamProgramCodeDto;
    }

}
