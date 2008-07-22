package gov.nih.nci.pa.action;

import org.apache.log4j.Logger;

import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.dto.NCISpecificInfoData;
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
public class NCISpecificInformationAction  extends ActionSupport {
     private static final Logger LOG  = Logger.getLogger(NCISpecificInformationAction.class);

    /**
     * 
     */
     private static final long serialVersionUID = 1L;
     //TO-DO: This value should extracted from session variable
     private static String protocolID = "2";  
     private NCISpecificInfoDTO nciSpecificInfoDTO;
     private NCISpecificInfoData nciSpecificInfoData = new NCISpecificInfoData();
  
     /**  
     * @return res
     */
    public String queryCheck()  {
        try {              
            this.nciSpecificInfoDTO = 
                      PaRegistry.getNCISpecificInfoService().getNCISpecificInfo(protocolID);              
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**  
     * @return res
     */
    public String queryUpdate()  {
        try {              
            this.nciSpecificInfoDTO = 
                      PaRegistry.getNCISpecificInfoService().updateNCISpecificData(nciSpecificInfoData);              
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
/**
 * 
 * @return nciSpecificInfoDTO dto
 */
   public NCISpecificInfoDTO getNciSpecificInfoDTO() {
    return nciSpecificInfoDTO;
   }
/**
 * 
 * @param nciSpecificInfoDTO ref
 */
  public void setNciSpecificInfoDTO(NCISpecificInfoDTO nciSpecificInfoDTO) {
     this.nciSpecificInfoDTO = nciSpecificInfoDTO;
  }
  
  /**
   * 
   * @return nciSpecificInfoData dto
   */
     public NCISpecificInfoData getNciSpecificInfoData() {
      return nciSpecificInfoData;
     }
  /**
   * 
   * @param nciSpecificInfoData ref
   */
    public void setNciSpecificInfoData(NCISpecificInfoData nciSpecificInfoData) {
       this.nciSpecificInfoData = nciSpecificInfoData;
    }
}
