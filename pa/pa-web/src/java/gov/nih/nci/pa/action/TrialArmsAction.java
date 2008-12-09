package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Arm;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialArmsWebDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.SignatureDeclareThrowsException", "PMD.TooManyMethods" })
public class TrialArmsAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1884666890L;

    private static final String ACT_EDIT = "edit";
    private static final String ACT_LIST = "list";
    private static final String ACT_LIST_ARM = "listArm";
    private static final String ACT_EDIT_NEW_ARM = "editNewArm";
    private static final String ACT_EDIT_ARM = "editArm";
    private static final String ACT_LIST_GROUP = "listGroup";
    private static final String ACT_EDIT_NEW_GROUP = "editNewGroup";
    private static final String ACT_EDIT_GROUP = "editGroup";
        
    private ArmServiceRemote armService;
    private PlannedActivityServiceRemote plaService;
    private InterventionServiceRemote intService;

    private Ii spIdIi;
    
    private List<TrialArmsWebDTO> armList;
    private List<InterventionWebDTO> intList;
    private String currentAction;
    private String selectedArmIdentifier;
    private String armName;
    private String armType;
    private String armDescription;
    private String checkBoxEntry;


    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        armService = PaRegistry.getArmService();
        plaService = PaRegistry.getPlannedActivityService();
        intService = PaRegistry.getInterventionService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);

        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        setCurrentAction(ACT_LIST_ARM);
        return ACT_LIST;
    }
    
    /**
     * @return Action result.
     * @throws Exception exception.
     */
    public String observational() throws Exception {
        loadForm();
        setCurrentAction(ACT_LIST_GROUP);
        return ACT_LIST;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String create() throws Exception {
        loadEditForm(null);
        setCurrentAction(ACT_EDIT_NEW_ARM);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String createGroup() throws Exception {
        loadEditForm(null);
        setCurrentAction(ACT_EDIT_NEW_GROUP);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        loadEditForm(getSelectedArmIdentifier());
        setCurrentAction(ACT_EDIT_ARM);
        return ACT_EDIT;
    }
    /**
     * @return result
     * @throws Exception exception
     */
    public String editGroup() throws Exception {
        loadEditForm(getSelectedArmIdentifier());
        setCurrentAction(ACT_EDIT_GROUP);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        armService.delete(IiConverter.convertToIi(getSelectedArmIdentifier()));
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        loadForm();
        return ACT_LIST;
    }
    /**
     * @return result
     * @throws Exception exception
     */
    public String add() throws Exception {
        businessRules();
        if (hasActionErrors()) {
            reloadInterventions();
            return ACT_EDIT;
        }
        ArmDTO newArm = new ArmDTO();
        newArm.setIdentifier(null);
        newArm.setDescriptionText(StConverter.convertToSt(getArmDescription()));
        newArm.setName(StConverter.convertToSt(getArmName()));
        newArm.setStudyProtocolIdentifier(spIdIi);
        if (getCurrentAction().equals(ACT_EDIT_NEW_ARM)) {
            newArm.setTypeCode(CdConverter.convertStringToCd(getArmType()));
        }
        newArm.setInterventions(getAssociatedInterventions());
        try {
            armService.create(newArm);
        } catch (PAException e) {
            addActionError(e.getMessage());
            reloadInterventions();
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        setCurrentAction(getCurrentAction().equals(ACT_EDIT_NEW_ARM) ? ACT_LIST_ARM : ACT_LIST_GROUP); 
        return ACT_LIST;
    }
    /**
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        businessRules();
        if (hasActionErrors()) {
            reloadInterventions();
            return ACT_EDIT;
        }
        ArmDTO updatedArm = new ArmDTO();
        updatedArm.setIdentifier(IiConverter.convertToIi(getSelectedArmIdentifier()));
        updatedArm.setDescriptionText(StConverter.convertToSt(getArmDescription()));
        updatedArm.setName(StConverter.convertToSt(getArmName()));
        updatedArm.setStudyProtocolIdentifier(spIdIi);
        if (getCurrentAction().equals(ACT_EDIT_ARM)) {
            updatedArm.setTypeCode(CdConverter.convertStringToCd(getArmType()));
        }
        updatedArm.setInterventions(getAssociatedInterventions());
        try {
            armService.update(updatedArm);
        } catch (PAException e) {
            addActionError(e.getMessage());
            reloadInterventions();
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        setCurrentAction(getCurrentAction().equals(ACT_EDIT_ARM) ? ACT_LIST_ARM : ACT_LIST_GROUP); 
        return ACT_LIST;
    }
    
    private void businessRules() throws Exception {
        if ((getCurrentAction().equals(ACT_EDIT_ARM) || getCurrentAction().equals(ACT_EDIT_NEW_ARM))
                && (!PAUtil.isEmpty(armType) && armType.equals(ArmTypeCode.NO_INTERVENTION.getCode()) 
                && (getAssociatedIds().size() > 0))) {
            addActionError("Arms of type " + armType + " cannot have associated interventions.  ");
        }
    }

    private void loadForm() throws Exception {
        setArmList(new ArrayList<TrialArmsWebDTO>());
        List<ArmDTO> armIsoList = armService.getByStudyProtocol(spIdIi);
        for (ArmDTO arm : armIsoList) {
            TrialArmsWebDTO webDto = new TrialArmsWebDTO();
            webDto.setDescription(StConverter.convertToString(arm.getDescriptionText()));
            webDto.setIdentifier(IiConverter.convertToString(arm.getIdentifier()));
            StringBuffer intBuff = new StringBuffer();
            List<PlannedActivityDTO> paList = plaService.getByArm(arm.getIdentifier());
            for (PlannedActivityDTO pa : paList) {
                InterventionDTO inter = intService.get(pa.getInterventionIdentifier());
                if (intBuff.length() > 0) {
                    intBuff.append(", ");
                }
                intBuff.append(StConverter.convertToString(inter.getName()));
            }
            webDto.setInterventions(intBuff.toString());
            webDto.setName(StConverter.convertToString(arm.getName()));
            webDto.setType(CdConverter.convertCdToString(arm.getTypeCode()));
            getArmList().add(webDto);
        }
    }

    private void loadEditForm(String armId) throws Exception {
        Set<Long> cInterventions = new HashSet<Long>();
        if (armId != null) {
            ArmDTO cArm = armService.get(IiConverter.convertToIi(armId));
            setArmDescription(StConverter.convertToString(cArm.getDescriptionText()));
            setArmName(StConverter.convertToString(cArm.getName()));
            setArmType(CdConverter.convertCdToString(cArm.getTypeCode()));
            setSelectedArmIdentifier(armId);
            List<PlannedActivityDTO> paList = plaService.getByArm(IiConverter.convertToIi(armId));
            for (PlannedActivityDTO pa : paList) {
                cInterventions.add(IiConverter.convertToLong(pa.getIdentifier()));
            }
        } else {
            setSelectedArmIdentifier(null);
        }
        setIntList(new ArrayList<InterventionWebDTO>());
        setCheckBoxEntry("");
        List<PlannedActivityDTO> plaList = plaService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pla : plaList) {
            if ((ActivityCategoryCode.INTERVENTION.equals(
                    ActivityCategoryCode.getByCode(CdConverter.convertCdToString(pla.getCategoryCode()))))
                 && (!PAUtil.isIiNull(pla.getInterventionIdentifier()))) {
                InterventionDTO intDto = intService.get(pla.getInterventionIdentifier());
                InterventionWebDTO intWebDto = new InterventionWebDTO();
                intWebDto.setDescription(StConverter.convertToString(intDto.getDescriptionText()));
                intWebDto.setIdentifier(IiConverter.convertToString(pla.getIdentifier()));
                intWebDto.setName(StConverter.convertToString(intDto.getName()));
                intWebDto.setArmAssignment(cInterventions.contains(IiConverter.convertToLong(pla.getIdentifier())));
                getIntList().add(intWebDto);
                if (intWebDto.getArmAssignment()) {
                    setCheckBoxEntry(getCheckBoxEntry() + intWebDto.getIdentifier() + ",");
                }
            }
        }
    }
    
    private void reloadInterventions() throws Exception {
        setIntList(new ArrayList<InterventionWebDTO>());
        List<PlannedActivityDTO> plaList = plaService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pla : plaList) {
            if ((ActivityCategoryCode.INTERVENTION.equals(
                    ActivityCategoryCode.getByCode(CdConverter.convertCdToString(pla.getCategoryCode()))))
                 && (!PAUtil.isIiNull(pla.getInterventionIdentifier()))) {
                InterventionDTO intDto = intService.get(pla.getInterventionIdentifier());
                InterventionWebDTO intWebDto = new InterventionWebDTO();
                intWebDto.setDescription(StConverter.convertToString(intDto.getDescriptionText()));
                intWebDto.setIdentifier(IiConverter.convertToString(pla.getIdentifier()));
                intWebDto.setName(StConverter.convertToString(intDto.getName()));
                intWebDto.setArmAssignment(getAssociatedIds().contains(
                        IiConverter.convertToLong(pla.getIdentifier())));
                getIntList().add(intWebDto);
            }
        }
    }
    
    private Set<Long> getAssociatedIds() {
        String clicks = getCheckBoxEntry();
        Set<Long> tSet = new HashSet<Long>();
        while (clicks.indexOf(',') > 1) {
            Long lid = Long.valueOf(clicks.substring(0, clicks.indexOf(',')));
            clicks = clicks.substring(clicks.indexOf(',') + 1);
            if (tSet.contains(lid)) {
                tSet.remove(lid);
            } else {
                tSet.add(lid);
            }
        }
        return tSet;
    }
    
    private DSet<Ii> getAssociatedInterventions() {
        Set<Long> tSet = getAssociatedIds();
        Set<Ii> intSet = new HashSet<Ii>();
        for (Long t : tSet) {
            intSet.add(IiConverter.convertToIi(t));
        }
        DSet<Ii> interventions = new DSet<Ii>();
        interventions.setItem(intSet);
        return interventions;
    }
    /**
     * @return the armsList
     */
    public List<TrialArmsWebDTO> getArmList() {
        return armList;
    }

    /**
     * @param armList the armList to set
     */
    public void setArmList(List<TrialArmsWebDTO> armList) {
        this.armList = armList;
    }

    /**
     * @return the intList
     */
    public List<InterventionWebDTO> getIntList() {
        return intList;
    }

    /**
     * @param intList the intList to set
     */
    public void setIntList(List<InterventionWebDTO> intList) {
        this.intList = intList;
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
     * @return the armName
     */
    public String getArmName() {
        return armName;
    }

    /**
     * @param armName the armName to set
     */
    public void setArmName(String armName) {
        this.armName = PAUtil.stringSetter(armName, Arm.NAME_LENGTH);
    }

    /**
     * @return the armType
     */
    public String getArmType() {
        return armType;
    }

    /**
     * @param armType the armType to set
     */
    public void setArmType(String armType) {
        this.armType = armType;
    }

    /**
     * @return the armDescription
     */
    public String getArmDescription() {
        return armDescription;
    }

    /**
     * @param armDescription the armDescription to set
     */
    public void setArmDescription(String armDescription) {
        this.armDescription = PAUtil.stringSetter(armDescription, Arm.DESCRIPTION_TEXT_LENGTH);
    }

    /**
     * @return the checkBoxEntry
     */
    public String getCheckBoxEntry() {
        return checkBoxEntry;
    }

    /**
     * @param checkBoxEntry the checkBoxEntry to set
     */
    public void setCheckBoxEntry(String checkBoxEntry) {
        this.checkBoxEntry = checkBoxEntry;
    }

    /**
     * @return the selectedArmIdentifier
     */
    public String getSelectedArmIdentifier() {
        return selectedArmIdentifier;
    }

    /**
     * @param selectedArmIdentifier the selectedArmIdentifier to set
     */
    public void setSelectedArmIdentifier(String selectedArmIdentifier) {
        this.selectedArmIdentifier = PAUtil.stringSetter(selectedArmIdentifier);
    }
}
