package gov.nih.nci.pa.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Hugh Reinhart
 * @since Jul 24, 2013
 */
public class TrialFundingJSONAction extends ActionSupport {

    private static final long serialVersionUID = 6840926249482625503L;
    private Map<String, String> serialNumbers;

    /**
     * @return result
     */
    public String loadSerialNumbers() {
        String searchTerm = (String) (ServletActionContext.getRequest().getParameter("serialNumberMatchTerm"));
        List<I2EGrantsServiceLocal.I2EGrant> grants;
        try {
            grants = PaRegistry.getI2EGrantsService().getBySerialNumber(searchTerm);
        } catch (PAException e) {
            LOG.error("Error calling I2E Grant service", e);
            grants = new ArrayList<I2EGrantsServiceLocal.I2EGrant>();
        }
        setSerialNumbers(new HashMap<String, String>());
        for (I2EGrantsServiceLocal.I2EGrant grant : grants) {
            getSerialNumbers().put(grant.getSerialNumber(), grant.toString());
        }
        return SUCCESS;
    }

    /**
     * @return the serialNumbers
     */
    public Map<String, String> getSerialNumbers() {
        return serialNumbers;
    }

    /**
     * @param serialNumbers the serialNumbers to set
     */
    public void setSerialNumbers(Map<String, String> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }
}
