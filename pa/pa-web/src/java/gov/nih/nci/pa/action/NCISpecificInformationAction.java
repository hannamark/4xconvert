package gov.nih.nci.pa.action;

import org.apache.log4j.Logger;

import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
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
     private Long studyProtocolID;  
     private NCISpecificInformationDTO nciSpecificInformationDTO;
     private NCISpecificInformationData nciSpecificInformationData = new NCISpecificInformationData();
  
     /**  
     * @return res
     */
    public String query()  {
        try {
            studyProtocolID = 2L; //get the protocol ID from session
            this.nciSpecificInformationDTO = 
                      PaRegistry.getNCISpecificInformationService().getNCISpecificInformation(
                                                                         studyProtocolID);
            LOG.info("1.) nciSpecificInformationDTO.getStudyProtocolID is: " 
                                            + nciSpecificInformationDTO.getStudyProtocolID());
            LOG.info("2.) nciSpecificInformationDTO.getMonitorCode is: " 
                                            + nciSpecificInformationDTO.getMonitorCode());
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**  
     * @return res
     */
    public String update()  {
        try {              
            this.nciSpecificInformationDTO = 
         PaRegistry.getNCISpecificInformationService().updateNCISpecificInformation(
                                nciSpecificInformationData);
            
            
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
/**
 * 
 * @return nciSpecificInformationDTO dto
 */
   public NCISpecificInformationDTO getNciSpecificInformationDTO() {
    return nciSpecificInformationDTO;
   }
/**
 * 
 * @param nciSpecificInformationDTO ref
 */
  public void setNciSpecificInformationDTO(NCISpecificInformationDTO nciSpecificInformationDTO) {
     this.nciSpecificInformationDTO = nciSpecificInformationDTO;
  }
  
  /**
   * 
   * @return nciSpecificInfoData dto
   */
     public NCISpecificInformationData getNciSpecificInformationData() {
      return nciSpecificInformationData;
     }
  /**
   * 
   * @param nciSpecificInformationData ref
   */
    public void setNciSpecificInformationData(NCISpecificInformationData nciSpecificInformationData) {
       this.nciSpecificInformationData = nciSpecificInformationData;
    }
}
