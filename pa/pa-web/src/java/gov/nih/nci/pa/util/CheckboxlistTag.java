package gov.nih.nci.pa.util;

import java.util.List;
import java.util.ArrayList;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * @author gnaveh
 *
 */
public class CheckboxlistTag extends ActionSupport {
  /**
    * 
   */
  private static final long serialVersionUID = 1L;
  private List trialStartDate;
  private List primaryCompletionDate; 
  
  /**
   * @return Action result.
   * @throws Exception xxx.
   */
  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  public String execute()throws Exception {
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