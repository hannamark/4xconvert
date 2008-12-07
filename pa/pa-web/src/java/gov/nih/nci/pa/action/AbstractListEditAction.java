package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Abstract action class for use-cases in which a list is displayed on
 * the first jsp.  Creates and updates are done on a seconde edit jsp.
 * @author Hugh Reinhart
 * @since 12/6/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public abstract class AbstractListEditAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1234573645L;

    /** Action result to call list jsp. */
    protected static final String AR_LIST = "list";
    /** Action result to call edit jsp. */
    protected static final String AR_EDIT = "edit";

    /** Bean to store current action. */
    protected String currentAction;
    /** Bean to store current action. */
    protected String selectedRowIdentifier;

    /** Index of current StudyProtocol. */
    protected Ii spIi;
    /** StudyDiseaseService. */
    protected StudyDiseaseServiceRemote studyDisesaeSvc;
    /** DiseaseService. */
    protected DiseaseServiceRemote diseaseSvc;
    /** DiseaseParentService. */
    protected DiseaseParentServiceRemote diseaseParentSvc;

    /**
     * @throws Exception exception
     */
    public void prepare() throws Exception {
        studyDisesaeSvc = PaRegistry.getStudyDiseaseService();
        diseaseSvc = PaRegistry.getDiseaseService();
        diseaseParentSvc = PaRegistry.getDiseaseParentService();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        spIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
    }

    /**
     * Call initial list jsp.
     * @return action result
     * @throws Exception exception
     */
    @Override
    public String execute() throws Exception {
        loadListForm();
        return AR_LIST;
    }

    /**
     * Call the edit jsp in "create" mode.
     * @return action result
     * @throws Exception exception
     */
    public String create() throws Exception {
        loadEditForm();
        setCurrentAction("create");
        return AR_EDIT;
    }
    /**
     * Call the edit jsp in "edit" mode.
     * @return action result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        loadEditForm();
        setCurrentAction("edit");
        return AR_EDIT;
    }
    
    /**
     * Add a new record to database (needs to be overridden).  Returns to list jsp.
     * @return result
     * @throws Exception exception
     */
    public String add() throws Exception {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        return execute();
    }

    /**
     * Update a record in the database (needs to be overridden).  Returns to list jsp.
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return execute();
    }

    /**
     * Delete a record from database (needs to be overridden). Returns to list jsp.
     * @return action result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        return execute();
    }

    /**
     * @throws Exception exception
     */
    protected abstract void loadListForm() throws Exception;

    /**
     * @throws Exception exception
     */
    protected abstract void loadEditForm() throws Exception;

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
