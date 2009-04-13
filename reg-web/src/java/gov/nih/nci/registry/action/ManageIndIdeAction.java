/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Vrushali
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class ManageIndIdeAction extends ActionSupport {
    /**
     * Sets the ind ide information in the collection.
     * 
     * @return result
     */
    public String addIdeIndIndicator() {
        String number = ServletActionContext.getRequest().getParameter("number");
        String grantor = ServletActionContext.getRequest().getParameter("grantor");
        String programCode = ServletActionContext.getRequest().getParameter("programcode");
        String expandedAccess = ServletActionContext.getRequest().getParameter("expandedaccess");
        String expandedAccessType = ServletActionContext.getRequest().getParameter("expandedaccesstype");
        String holderType = ServletActionContext.getRequest().getParameter("holdertype");
        String indIde = ServletActionContext.getRequest().getParameter("indIde");
        if (number != null && number.equals("") && grantor != null && grantor.equals("") 
                && programCode != null && programCode.equals("") 
                && expandedAccess != null && expandedAccess.equals("No")
                && expandedAccessType != null && expandedAccessType.equals("") 
                && holderType != null && holderType.equals("") 
                && indIde != null && indIde.equals("undefined")) {
            return SUCCESS;
        }
        TrialIndIdeDTO indIdeHolder = new TrialIndIdeDTO();
        String emptyString = "";
        indIdeHolder.setExpandedAccess(expandedAccess.equals(emptyString) ? "-" : expandedAccess);
        indIdeHolder.setExpandedAccessType(expandedAccessType.equals(emptyString) ? "-" : expandedAccessType);
        indIdeHolder.setGrantor(grantor.equals(emptyString) ? "-" : grantor);
        indIdeHolder.setHolderType(holderType.equals(emptyString) ? "-" : holderType);
        indIdeHolder.setNumber(number.equals(emptyString) ? "-" : number);
        indIdeHolder.setProgramCode(programCode.equals(emptyString) ? "-" : programCode);
        indIdeHolder.setIndIde(indIde.equals(emptyString) ? "-" : indIde);
        indIdeHolder.setRowId(UUID.randomUUID().toString());
        List<TrialIndIdeDTO> sessionList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.INDIDE_LIST);
        if (sessionList != null) {
            sessionList.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, sessionList);
        } else {
            List<TrialIndIdeDTO> tempList = new ArrayList<TrialIndIdeDTO>();
            tempList.add(indIdeHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, tempList);
        }   
        return "display_ideind";
    }

    /**
     * 
     * @return result
     */
    @SuppressWarnings("unchecked")
    public String deleteIndIde() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        List<TrialIndIdeDTO> sessionList = (List<TrialIndIdeDTO>) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.INDIDE_LIST);
        TrialIndIdeDTO holder;
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialIndIdeDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.INDIDE_LIST, sessionList);
        return "display_ideind";
    }

}
