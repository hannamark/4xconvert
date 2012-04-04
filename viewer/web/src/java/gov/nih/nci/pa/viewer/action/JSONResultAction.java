/**
 * 
 */
package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * This action class is used in viewer app to return JSON results.
 * @author Monish Dombla
 */
public class JSONResultAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private List<String> officialTitles;

    /**
     * 
     * @return String
     */
    public String loadOfficalTitles() {
        
        String searchTerm = (String) (ServletActionContext.getRequest().getParameter("officialTitleMatchTerm"));
        List<String> mathches = new ArrayList<String>();
        try {
            mathches = PaRegistry.getProtocolQueryService().getOfficialTitles(searchTerm);
        } catch (PAException e) {
            LOG.error("Error fetching Official Titles");
        }
        setOfficialTitles(mathches);
        return SUCCESS;
    }


    /**
     * 
     * @return list of strings
     */
    public List<String> getOfficialTitles() {
        return officialTitles;
    }

    /**
     * 
     * @param officialTitles The list of string to be set
     */
    public void setOfficialTitles(List<String> officialTitles) {
        this.officialTitles = officialTitles;
    }
}