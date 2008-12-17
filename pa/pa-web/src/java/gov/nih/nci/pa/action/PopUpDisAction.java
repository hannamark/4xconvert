package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
* @author Hugh Reinhart
* @since 11/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD.CyclomaticComplexity")
public class PopUpDisAction extends ActionSupport {
    private static final long serialVersionUID = 8987838321L;

    private static final Logger LOG = Logger.getLogger(PopUpDisAction.class);
    private static final int MAX_SEARCH_RESULT_SIZE = 200; 

    private String searchName;
    private List<DiseaseWebDTO> disWebList = new ArrayList<DiseaseWebDTO>();


    private void loadResultList() {
        disWebList.clear();
        String tName = ServletActionContext.getRequest().getParameter("searchName");
        
        if (PAUtil.isEmpty(tName)) {
            String message = "Please enter at least one search criteria"; 
            addActionError(message);
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return;
        }

        DiseaseDTO criteria = new DiseaseDTO();
        criteria.setPreferredName(StConverter.convertToSt(tName));
        List<DiseaseDTO> diseaseList = null;
        try {
            diseaseList = PaRegistry.getDiseaseService().search(criteria);
        } catch (Exception e) {
            error("Exception while loading disease results.", e);
            return;
        }
        if (diseaseList.size() > MAX_SEARCH_RESULT_SIZE) {
            error("Too many diseases found.  Please narrow search.");
            return;
        }
        for (DiseaseDTO disease : diseaseList) {
            DiseaseWebDTO newRec = new DiseaseWebDTO();
            newRec.setDiseaseIdentifier(IiConverter.convertToString(disease.getIdentifier()));
            newRec.setPreferredName(StConverter.convertToString(disease.getPreferredName()));
            newRec.setCode(StConverter.convertToString(disease.getDiseaseCode()));
            newRec.setConceptId(StConverter.convertToString(disease.getNtTermIdentifier()));
            newRec.setMenuDisplayName(StConverter.convertToString(disease.getMenuDisplayName()));
            getDisWebList().add(newRec);
        }
        loadParentPreferredNames();
    }
    
    private void loadParentPreferredNames() {
        Ii[] iis = new Ii[disWebList.size()];
        int x = 0;
        for (DiseaseWebDTO dto : disWebList) {
            iis[x++] = IiConverter.convertToIi(dto.getDiseaseIdentifier());
        }
        List<DiseaseParentDTO> dpList;
        try {
            dpList = PaRegistry.getDiseaseParentService().getByChildDisease(iis);
        } catch (Exception e) {
            error("Exception thrown while getting disease parents.", e); 
            return;
        }
        HashMap<String, String> childParent = new HashMap<String, String>();
        for (DiseaseParentDTO dp : dpList) {
            String child = IiConverter.convertToString(dp.getIdentifier());
            DiseaseDTO parentDTO;
            try {
                parentDTO = PaRegistry.getDiseaseService().get(dp.getParentDiseaseIdentifier());
            } catch (Exception e) {
                error("Exception throw while getting disease name for parent.", e);
                return;
            }
            if (childParent.containsKey(child)) {
                childParent.put(child, childParent.get(child) + ", "
                        + StConverter.convertToString(parentDTO.getPreferredName()));
            } else {
                childParent.put(child, StConverter.convertToString(parentDTO.getPreferredName()));
            }
        }
        for (DiseaseWebDTO dto : disWebList) {
            dto.setParentPreferredName(childParent.get(dto.getDiseaseIdentifier()));
        }
    }

    private void error(String errMsg, Throwable t) {
        LOG.error(errMsg, t);
        addActionError(errMsg);
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, errMsg);
    }

    private void error(String errMsg) {
        error(errMsg, null);
    }

    /**
     * @return result
     */
    public String displayList() {
        loadResultList();
        return SUCCESS;
    }
    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }
    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }
    /**
     * @return the disWebList
     */
    public List<DiseaseWebDTO> getDisWebList() {
        return disWebList;
    }
    /**
     * @param disWebList the disWebList to set
     */
    public void setDisWebList(List<DiseaseWebDTO> disWebList) {
        this.disWebList = disWebList;
    }
}
