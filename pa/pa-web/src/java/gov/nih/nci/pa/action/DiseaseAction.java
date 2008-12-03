package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DiseaseAlternameServiceRemote;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.Constants;
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
@SuppressWarnings("PMD")
public class DiseaseAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1234584746L;
    private static final String AR_EDIT = "edit";
    private static final String AR_LIST = "list";
    
    private StudyProtocolServiceRemote sProService;
    private StudyDiseaseServiceRemote sDisService;
    private DiseaseServiceRemote diseService;
    private DiseaseAlternameServiceRemote dAltService;
    private DiseaseParentServiceRemote dParService;
   
    private Ii spIdIi;
    
    private String currentAction;
    private List<DiseaseWebDTO> diseaseList;

    /**
     * @throws Exception exception
     */
    public void prepare() throws Exception {
        sProService = PaRegistry.getStudyProtocolService();
        sDisService = PaRegistry.getStudyDiseaseService();
        diseService = PaRegistry.getDiseaseService();
        dAltService = PaRegistry.getDiseaseAlternameService();
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
        loadDiseaseList();
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
        setCurrentAction("edit");
        return AR_EDIT;
    }
    
    /**
     * @return action result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        loadDiseaseList();
        return AR_LIST;
    }
    
    private void loadDiseaseList() throws Exception {
        List<DiseaseWebDTO> nl = new ArrayList<DiseaseWebDTO>();
        DiseaseWebDTO n = new DiseaseWebDTO();
        n.setCode("code");
        n.setConceptId("conceptId");
        n.setLead("lead");
        n.setMenuDisplayName("menuDisplayName");
        n.setParentPreferredName("parentPreferredName");
        n.setPreferredName("preferredName");
        nl.add(n);
        setDiseaseList(nl);
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
