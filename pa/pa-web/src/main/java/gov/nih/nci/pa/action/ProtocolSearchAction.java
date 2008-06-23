package gov.nih.nci.pa.action;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
@Validation
public class ProtocolSearchAction extends ActionSupport {    
    
    private List<ProtocolDTO> records = new ArrayList<ProtocolDTO>();
    
    private ProtocolSearchCriteria criteria = new ProtocolSearchCriteria();

    /**  
     * @return res
     */ 
    public String execute() {
        return SUCCESS;
    }
    
    /**  
     * @return res
     * @throws Exception exception
     */
    @SuppressWarnings({ "PMD.ReplaceHashtableWithMap", "PMD.SystemPrintln", "PMD.SignatureDeclareThrowsException", 
        "PMD.ExcessiveParameterList" }) 
    public String query() throws Exception {
        HibernateUtil.getHibernateHelper().openAndBindSession();
        records = PaRegistry.getProtocolService().getProtocol(criteria);       
        return SUCCESS;
    }
    /**
     * 
     * @return prot
     */
    public List<ProtocolDTO>  getRecords() {
        return records;
    }
    
}