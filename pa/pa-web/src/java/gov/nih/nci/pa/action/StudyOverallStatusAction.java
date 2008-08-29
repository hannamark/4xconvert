/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.IsoConverter;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the protocol status.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class StudyOverallStatusAction extends ActionSupport implements
        Preparable {
    /*
     * (non-Javadoc)
     * 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     */
    private static final long serialVersionUID = 1L;
    private List trialStartDate;
    private List primaryCompletionDate;
    StudyProtocolServiceRemote spService;
    StudyOverallStatusServiceRemote sosService;
    Long spIdLong;
    Ii spIdIi;

    /** 
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        spService = PaRegistry.getStudyProtocolService();
        sosService = PaRegistry.getStudyOverallStatusService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        spIdLong = spDTO.getStudyProtocolId();
        spIdIi = IsoConverter.convertIdToIsoIi(spIdLong);

        trialStartDate = new ArrayList();
        trialStartDate.add("Actual");
        trialStartDate.add("Anticipated");

        primaryCompletionDate = new ArrayList();
        primaryCompletionDate.add("Actual");
        primaryCompletionDate.add("Anticipated");
    }

    /**
     * @return Action result.
     * @throws Exception
     *             xxx.
     */
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    /**
     * @return Trial start date.
     */
    public List getTrialStartDate() {
        return trialStartDate;
    }

    /**
     * @return Trial completion date.
     */
    public List getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }
}
