/**
 *
 */
package gov.nih.nci.pa.action;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Action class for viewing and editing the protocol status.
 * 
 * @author Hugh Reinhart
 * @since 08/20/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 * 
 */
public class OverallStatusAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private List trialStartDate;
    private List primaryCompletionDate;

    /**
     * @return Action result.
     * @throws Exception
     *             xxx.
     */
    @Override
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public String execute() throws Exception {
        trialStartDate = new ArrayList();
        trialStartDate.add("Actual");
        trialStartDate.add("Anticipated");

        primaryCompletionDate = new ArrayList();
        primaryCompletionDate.add("Actual");
        primaryCompletionDate.add("Anticipated");

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
