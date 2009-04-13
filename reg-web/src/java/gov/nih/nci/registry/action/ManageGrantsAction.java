/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.registry.dto.TrialFundingWebDTO;
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
public class ManageGrantsAction extends ActionSupport {
    /**
     * 
     * @return s
     */
    @SuppressWarnings("unchecked")
    public String addGrant() {
        String fundingMechanismCode = ServletActionContext.getRequest().getParameter("fundingMechanismCode");
        String nihInstitutionCode = ServletActionContext.getRequest().getParameter("nihInstitutionCode");
        String serialNumber = ServletActionContext.getRequest().getParameter("serialNumber");
        String nciDivisionProgramCode = ServletActionContext.getRequest().getParameter("nciDivisionProgramCode");
        TrialFundingWebDTO grantHolder = new TrialFundingWebDTO();
        grantHolder.setFundingMechanismCode(fundingMechanismCode);
        grantHolder.setNihInstitutionCode(nihInstitutionCode);
        grantHolder.setSerialNumber(serialNumber);
        grantHolder.setNciDivisionProgramCode(nciDivisionProgramCode);
        grantHolder.setRowId(UUID.randomUUID().toString());
        List<TrialFundingWebDTO> sessionList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
            .getSession().getAttribute(Constants.GRANT_LIST);
        if (sessionList != null) {
            sessionList.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, sessionList); 
        } else {
            List<TrialFundingWebDTO> tempList = new ArrayList<TrialFundingWebDTO>();
            tempList.add(grantHolder);
            ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, tempList);
        }
        
        return "display_grants";
    }
    /**
     * 
     * @return result
     */
    @SuppressWarnings("unchecked")
    public String deleteGrant() {
        String rowid = ServletActionContext.getRequest().getParameter("uuid");
        TrialFundingWebDTO holder;
        List<TrialFundingWebDTO> sessionList = (List<TrialFundingWebDTO>) ServletActionContext.getRequest()
        .getSession().getAttribute(Constants.GRANT_LIST);
        for (int i = 0; i < sessionList.size(); i++) {
            holder = (TrialFundingWebDTO) sessionList.get(i);
            if (holder.getRowId().equals(rowid)) {
                sessionList.remove(i);
            }
        }
        ServletActionContext.getRequest().getSession().setAttribute(Constants.GRANT_LIST, sessionList);
        return "display_grants";
    }


}
