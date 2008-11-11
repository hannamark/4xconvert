package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
* @author Hugh Reinhart
* @since 10/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD")
public class TrialInterventionsAction extends ActionSupport implements
        Preparable {
    private static final long serialVersionUID = 1876567890L;

    private static final String ACT_CREATE = "create";
    private static final String ACT_EDIT = "edit";
        
    private PlannedActivityServiceRemote paService;
    private InterventionServiceRemote iService;
    private InterventionAlternateNameServiceRemote ianService;

    private Ii spIdIi;
    private St user;
    
    private List<InterventionWebDTO> interventionsList;
    private String currentAction;
    private String interventionIdentifier;
    private String interventionType;
    private String interventionName;
    private String interventionDescription;
    private String otherName;
    private Boolean leadIndicator;    
    private String selectedRowIdentifier;


    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        paService = PaRegistry.getPlannedActivityService();
        iService = PaRegistry.getInterventionService();
        ianService = PaRegistry.getInterventionAlternateNameService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);

        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
        user = StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String create() throws Exception {
        setCurrentAction(ACT_CREATE);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        PlannedActivityDTO paDto = paService.get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        InterventionDTO i = iService.get(paDto.getInterventionIdentifier());
        List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(i.getIdentifier());
        StringBuffer onBuff = new StringBuffer("");
        for (InterventionAlternateNameDTO ian : ianList) {
            if (ianList.get(0) !=  ian) {
                onBuff.append(", ");
            }
            onBuff.append(StConverter.convertToString(ian.getName()));
        }
        setInterventionDescription(StConverter.convertToString(i.getDescriptionText()));
        setInterventionIdentifier(IiConverter.convertToString(paDto.getInterventionIdentifier()));
        setInterventionName(StConverter.convertToString(i.getName()));
        setInterventionType(CdConverter.convertCdToString(paDto.getSubcategoryCode()));
        setLeadIndicator(BlConverter.covertToBoolean(paDto.getLeadProductIndicator()));
        setOtherName(onBuff.toString());
        setCurrentAction(ACT_EDIT);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        try {
            paService.delete(IiConverter.convertToIi(getSelectedRowIdentifier()));
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        loadForm();
        return SUCCESS;
    }

    private PlannedActivityDTO generateDto() {
        PlannedActivityDTO paDto = new PlannedActivityDTO();
        paDto.setIdentifier(null);
        paDto.setStudyProtocolIdentifier(spIdIi);
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        paDto.setInterventionIdentifier(IiConverter.convertToIi(getInterventionIdentifier()));
        paDto.setSubcategoryCode(CdConverter.convertStringToCd(getInterventionType()));
        paDto.setLeadProductIndicator(BlConverter.convertToBl(getLeadIndicator()));
        return paDto;
    }
    /**
     * @return result
     * @throws Exception exception
     */
    public String add() throws Exception {
        try {
            paService.create(generateDto());
        } catch (PAException e) {
            addActionError(e.getMessage());
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        PlannedActivityDTO pa = generateDto();
        pa.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
        try {
            paService.update(pa);
        } catch (PAException e) {
            addActionError(e.getMessage());
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception on error.
     */
    public String display() throws Exception {
        loadEditForm(ServletActionContext.getRequest().getParameter("interventionId"));
        return SUCCESS;
    }

    private void loadForm() throws Exception {
        setInterventionsList(new ArrayList<InterventionWebDTO>());
        List<PlannedActivityDTO> paList = paService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pa : paList) {
            if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                    .convertCdToString(pa.getCategoryCode())))) {
                InterventionDTO i = iService.get(pa.getInterventionIdentifier());
                List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(i.getIdentifier());
                StringBuffer onBuff = new StringBuffer("");
                for (InterventionAlternateNameDTO ian : ianList) {
                    if (ianList.get(0) !=  ian) {
                        onBuff.append(", ");
                    }
                    onBuff.append(StConverter.convertToString(ian.getName()));
                }
                InterventionWebDTO webDto = new InterventionWebDTO();
                webDto.setOtherNames(onBuff.toString());
                webDto.setDescription(StConverter.convertToString(i.getDescriptionText()));
                webDto.setIdentifier(IiConverter.convertToString(pa.getIdentifier()));
                if ((pa.getLeadProductIndicator() == null) 
                        || (BlConverter.covertToBoolean(pa.getLeadProductIndicator()) == null)
                        || (!BlConverter.covertToBoolean(pa.getLeadProductIndicator()))) {
                    webDto.setLeadIndicator(null);
                } else {
                    webDto.setLeadIndicator("Yes");
                }            
                webDto.setName(StConverter.convertToString(i.getName()));
                webDto.setType(CdConverter.convertCdToString(pa.getSubcategoryCode()));
                getInterventionsList().add(webDto);
            }
        }
    }

    private void loadEditForm(String interventionId) throws Exception {
        if (interventionId != null) {
            Ii interventionIi = IiConverter.convertToIi(interventionId);

            InterventionDTO iDto = iService.get(IiConverter.convertToIi(interventionId));
            setInterventionIdentifier(interventionId);
            setInterventionName(StConverter.convertToString(iDto.getName()));
            setInterventionDescription(StConverter.convertToString(iDto.getDescriptionText()));
            StringBuffer onBuff = new StringBuffer("");
            List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(interventionIi);
            for (InterventionAlternateNameDTO ian : ianList) {
                if (ianList.get(0) !=  ian) {
                    onBuff.append("\n");
                }
                onBuff.append(StConverter.convertToString(ian.getName()));
            }
            setOtherName(onBuff.toString());
        }
    }

    /**
     * @return the interventionsList
     */
    public List<InterventionWebDTO> getInterventionsList() {
        return interventionsList;
    }

    /**
     * @param interventionsList the interventionsList to set
     */
    public void setInterventionsList(List<InterventionWebDTO> interventionsList) {
        this.interventionsList = interventionsList;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the interventionIdentifier
     */
    public String getInterventionIdentifier() {
        return interventionIdentifier;
    }

    /**
     * @param interventionIdentifier the interventionIdentifier to set
     */
    public void setInterventionIdentifier(String interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
    }

    /**
     * @return the interventionType
     */
    public String getInterventionType() {
        return interventionType;
    }

    /**
     * @param interventionType the interventionType to set
     */
    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    /**
     * @return the interventionName
     */
    public String getInterventionName() {
        return interventionName;
    }

    /**
     * @param interventionName the interventionName to set
     */
    public void setInterventionName(String interventionName) {
        this.interventionName = interventionName;
    }

    /**
     * @return the intervetnionDescription
     */
    public String getInterventionDescription() {
        return interventionDescription;
    }

    /**
     * @param interventionDescription the intervetnionDescription to set
     */
    public void setInterventionDescription(String interventionDescription) {
        this.interventionDescription = interventionDescription;
    }

    /**
     * @return the otherName
     */
    public String getOtherName() {
        return otherName;
    }

    /**
     * @param otherName the otherName to set
     */
    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    /**
     * @return the leadIndicator
     */
    public Boolean getLeadIndicator() {
        return leadIndicator;
    }

    /**
     * @param leadIndicator the leadIndicator to set
     */
    public void setLeadIndicator(Boolean leadIndicator) {
        this.leadIndicator = leadIndicator;
    }

    /**
     * @return the selectedRowIdentifier
     */
    public String getSelectedRowIdentifier() {
        return selectedRowIdentifier;
    }

    /**
     * @param selectedRowIdentifier the selectedRowIdentifier to set
     */
    public void setSelectedRowIdentifier(String selectedRowIdentifier) {
        this.selectedRowIdentifier = selectedRowIdentifier;
    }
}
