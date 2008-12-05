package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for Disease/Condition use-case.
 * @author Hugh Reinhart
 * @since 12/1/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.SignatureDeclareThrowsException", 
    "PMD.NPathComplexity", "PMD.TooManyMethods" })
public class DiseaseAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1234584746L;
    private static final String AR_EDIT = "edit";
    private static final String AR_LIST = "list";
    
    private StudyDiseaseServiceRemote sDisService;
    private DiseaseServiceRemote diseService;
    private DiseaseParentServiceRemote dParService;
   
    private Ii spIdIi;
    
    private String currentAction;
    private String selectedStudyDiseaseIdentifier;
    private DiseaseWebDTO disease;
    private List<DiseaseWebDTO> diseaseList;

    /**
     * @throws Exception exception
     */
    public void prepare() throws Exception {
        sDisService = PaRegistry.getStudyDiseaseService();
        diseService = PaRegistry.getDiseaseService();
        dParService = PaRegistry.getDiseaseParentService();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * @return action result
     * @throws Exception exception
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return AR_LIST;
    }
    
    /**
     * @return action result
     * @throws Exception exception
     */
    public String create() throws Exception {
        setCurrentAction("create");
        return AR_EDIT;
    }

    /**
     * @return action result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        StudyDiseaseDTO sd = sDisService.get(IiConverter.convertToIi(getSelectedStudyDiseaseIdentifier()));
        String diseaseId = IiConverter.convertToString(sd.getDiseaseIdentifier());
        String lead = PAUtil.isBlNull(sd.getLeadDiseaseIndicator()) ? null 
                : BlConverter.covertToBoolean(sd.getLeadDiseaseIndicator()).toString();
        loadEditForm(diseaseId, lead);
        setCurrentAction("edit");
        return AR_EDIT;
    }
    
    /**
     * @return action result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        sDisService.delete(IiConverter.convertToIi(getSelectedStudyDiseaseIdentifier()));
        loadForm();
        return AR_LIST;
    }
    
    /**
     * @return result
     * @throws Exception exception
     */
    public String add() throws Exception {
        enforceBusinessRules();
        if (!hasActionErrors()) {
            StudyDiseaseDTO sdDto = new StudyDiseaseDTO();
            sdDto.setIdentifier(null);
            sdDto.setDiseaseIdentifier(IiConverter.convertToIi(getDisease().getIdentifier()));
            Bl blLead = null;
            if (getDisease().getLead() != null) {
                Boolean bLead = Boolean.valueOf(getDisease().getLead());
                blLead = BlConverter.convertToBl(bLead);
            }  
            sdDto.setLeadDiseaseIndicator(blLead);
            sdDto.setStudyProtocolIdentifier(spIdIi);
            try {
                sDisService.create(sdDto);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors()) {
            loadEditForm(getDisease().getIdentifier(), getDisease().getLead());
            setCurrentAction("create");
            return AR_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return AR_LIST;
    }
    
    /**
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        enforceBusinessRules();
        if (!hasActionErrors()) {
            StudyDiseaseDTO pa = sDisService.get(IiConverter.convertToIi(getSelectedStudyDiseaseIdentifier()));
            Bl blLead = null;
            if (getDisease().getLead() != null) {
                Boolean bLead = Boolean.valueOf(getDisease().getLead());
                blLead = BlConverter.convertToBl(bLead);
            }
            pa.setLeadDiseaseIndicator(blLead);
            try {
                sDisService.update(pa);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors()) {
            loadEditForm(getDisease().getIdentifier(), getDisease().getLead());
            setCurrentAction("edit");
            return AR_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return AR_LIST;
    }

    /**
     * @return result
     * @throws Exception on error.
     */
    public String display() throws Exception {
        loadEditForm(ServletActionContext.getRequest().getParameter("diseaseId"), null);
        return SUCCESS;
    }
    
    private void enforceBusinessRules() throws Exception {
        DiseaseDTO dto = diseService.get(IiConverter.convertToIi(getDisease().getIdentifier()));
        String menu = StConverter.convertToString(dto.getMenuDisplayName());
        ArrayList<Long> parentList = new ArrayList<Long>();
        ArrayList<Long> childList = new ArrayList<Long>();
        // get parent diseases
        List<DiseaseParentDTO> dpList = dParService.getByChildDisease(dto.getIdentifier());
        for (DiseaseParentDTO dp : dpList) {
            parentList.add(IiConverter.convertToLong(dp.getParentDiseaseIdentifier()));
        }
        // get child diseases
        dpList = dParService.getByParentDisease(dto.getIdentifier());
        for (DiseaseParentDTO dp : dpList) {
            childList.add(IiConverter.convertToLong(dp.getDiseaseIdentifier()));
        }

        if (PAUtil.isEmpty(menu)) {
            addActionError("Diseases without a menu display name are not suitable for reporting.  ");
        }
        List<StudyDiseaseDTO> sdList = sDisService.getByStudyProtocol(spIdIi);
        Boolean hasParent = false;
        Boolean hasChild = false;
        for (StudyDiseaseDTO sd : sdList) {
            if (parentList.contains(IiConverter.convertToLong(sd.getDiseaseIdentifier()))) {
                hasParent = true;
            }
            if (childList.contains(IiConverter.convertToLong(sd.getDiseaseIdentifier()))) {
                hasChild = true;
            }
        }
        if (hasParent) {
            addActionError("Redundancy error:  this trial already includes a parent of the selected disease.  ");
        }
        if (hasChild) {
            addActionError("Redundancy error:  this trial already includes a child of the selected disease.  ");
        }
    }
    
    private String buildParentPreferredName(String diseaseId) throws Exception {
        List<DiseaseParentDTO> parentList = dParService.getByChildDisease(IiConverter.convertToIi(diseaseId));
        StringBuffer ppBuff = new StringBuffer();
        for (DiseaseParentDTO parent : parentList) {
            if (parentList.get(0) !=  parent) {
                ppBuff.append(", ");
            }
            DiseaseDTO temp = diseService.get(parent.getParentDiseaseIdentifier());
            ppBuff.append(StConverter.convertToString(temp.getPreferredName()));
        }
        return ppBuff.toString();
    }
    
    private void loadForm() throws Exception {
        List<DiseaseWebDTO> nl = new ArrayList<DiseaseWebDTO>();
        List<StudyDiseaseDTO> sdList = sDisService.getByStudyProtocol(spIdIi);
        for (StudyDiseaseDTO sd : sdList) {
            DiseaseDTO d = diseService.get(sd.getDiseaseIdentifier());
            DiseaseWebDTO n = new DiseaseWebDTO();
            n.setStudyDiseaseIdentifier(IiConverter.convertToString(sd.getIdentifier()));
            n.setIdentifier(IiConverter.convertToString(d.getIdentifier()));
            n.setCode(StConverter.convertToString(d.getDiseaseCode()));
            n.setConceptId(StConverter.convertToString(d.getNtTermIdentifier()));
            if (!PAUtil.isBlNull(sd.getLeadDiseaseIndicator()) 
                    && BlConverter.covertToBoolean(sd.getLeadDiseaseIndicator())) {
                n.setLead("Yes");
            }
            n.setMenuDisplayName(StConverter.convertToString(d.getMenuDisplayName()));
            n.setParentPreferredName(buildParentPreferredName(IiConverter.convertToString(d.getIdentifier())));
            n.setPreferredName(StConverter.convertToString(d.getPreferredName()));
            nl.add(n);
        }
        setDiseaseList(nl);
    }
    
    private void loadEditForm(String diseaseId, String lead) throws Exception {
        disease = new DiseaseWebDTO();
        if (diseaseId != null) {
            Ii diseaseIi = IiConverter.convertToIi(diseaseId);
            DiseaseDTO dto = diseService.get(diseaseIi);
            disease.setIdentifier(diseaseId);
            disease.setCode(StConverter.convertToString(dto.getDiseaseCode()));
            disease.setConceptId(StConverter.convertToString(dto.getNtTermIdentifier()));
            disease.setMenuDisplayName(StConverter.convertToString(dto.getMenuDisplayName()));
            disease.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
            disease.setLead(lead);
            disease.setParentPreferredName(buildParentPreferredName(diseaseId));
        }
        setDisease(disease);
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
     * @return the selectedStudyDiseaseIdentifier
     */
    public String getSelectedStudyDiseaseIdentifier() {
        return selectedStudyDiseaseIdentifier;
    }

    /**
     * @param selectedStudyDiseaseIdentifier the selectedStudyDiseaseIdentifier to set
     */
    public void setSelectedStudyDiseaseIdentifier(
            String selectedStudyDiseaseIdentifier) {
        this.selectedStudyDiseaseIdentifier = selectedStudyDiseaseIdentifier;
    }

    /**
     * @return the disease
     */
    public DiseaseWebDTO getDisease() {
        return disease;
    }

    /**
     * @param disease the disease to set
     */
    public void setDisease(DiseaseWebDTO disease) {
        this.disease = disease;
    }

    /**
     * @return the diseaseList
     */
    public List<DiseaseWebDTO> getDiseaseList() {
        return diseaseList;
    }

    /**
     * @param diseaseList the diseaseList to set
     */
    public void setDiseaseList(List<DiseaseWebDTO> diseaseList) {
        this.diseaseList = diseaseList;
    }
}
