package gov.nih.nci.pa.action;

import com.opensymphony.xwork2.ActionSupport;

import gov.nih.nci.pa.service.PAException;

import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;
import gov.nih.nci.pa.dto.DiseaseConditionDTO;

/**
 * adapted from PO.
 * @author
 * 
 */
public class DiseaseConditionAction extends ActionSupport {
    private static final long P_ID = 1;
    private List<DiseaseConditionDTO> records = new ArrayList<DiseaseConditionDTO>();
    
    /**  
     * @return res
     */
    public String getDiseaseConditions() {
        try {       
            //TODO the line below needs to be removed when this page is called from menubar
            HibernateUtil.getHibernateHelper().openAndBindSession();
            records = PaRegistry.getDiseaseCondService().getDiseaseCondition(P_ID);
            return SUCCESS;
        } catch (PAException e) {           
          return ERROR;
        }
    }

    /**
     * 
     * @return List of DiseaseConditionDTO
     */
    public List<DiseaseConditionDTO> getRecords() {
        return records;
    }

    /**
     * 
     * @param records List
     */
    public void setRecords(List<DiseaseConditionDTO> records) {
        this.records = records;
    }
   
}
