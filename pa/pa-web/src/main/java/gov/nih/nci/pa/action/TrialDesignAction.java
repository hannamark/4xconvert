package gov.nih.nci.pa.action;

import org.apache.log4j.Logger;

import gov.nih.nci.pa.dto.TrialDesignDTO;
import gov.nih.nci.pa.util.PaRegistry;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * 
 * @author gnaveh
 *
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class TrialDesignAction  extends ActionSupport {
     private static final Logger LOG  = Logger.getLogger(TrialDesignAction.class);

    /**
     * 
     */
     private static final long serialVersionUID = 1L;
     //TO-DO: This value should extracted from session variable
     private static String stusyProtocolID = "1";  
     private TrialDesignDTO trialDesign;
  
     /**  
     * @return res
     */
    public String query()  {
        try {              
            this.trialDesign = PaRegistry.getTrialDesignService().getTrialDesign(stusyProtocolID);  
            if (this.trialDesign.getType().equalsIgnoreCase("ISP")) {
                LOG.info("An ISP project");               
            } else if (this.trialDesign.getType().equalsIgnoreCase("SP")) {
               LOG.info("a new trial design route to insert new page");
            }
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
/**
 * 
 * @return trialdesign dto
 */
   public TrialDesignDTO getTrialDesign() {
    return trialDesign;
   }
/**
 * 
 * @param trialDesign ref
 */
  public void setTrialDesign(TrialDesignDTO trialDesign) {
     this.trialDesign = trialDesign;
  }
}
