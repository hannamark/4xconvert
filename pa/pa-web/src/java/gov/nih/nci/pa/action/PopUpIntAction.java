package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
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
* @since 10/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class PopUpIntAction extends ActionSupport {
    private static final long serialVersionUID = 9987838321L;
    private static final Logger LOG = Logger.getLogger(PopUpIntAction.class);

    private static final int MAX_SEARCH_RESULT_SIZE = 200; 
    
    private String searchName;
    private List<InterventionWebDTO> interWebList = new ArrayList<InterventionWebDTO>();

    private void loadResultList() {
        interWebList.clear();
        String tName = ServletActionContext.getRequest().getParameter("searchName");
        
        if (PAUtil.isEmpty(tName)) {
            error("Please enter at least one search criteria."); 
            return;
        }

        InterventionDTO criteria = new InterventionDTO();
        criteria.setName(StConverter.convertToSt(tName));
        List<InterventionDTO> interList = null;
        try {
            interList = PaRegistry.getInterventionService().search(criteria);
        } catch (Exception e) {
            error("Exception thrown while getting intervention list using service.", e); 
            return;
        }
        if (interList.size() > MAX_SEARCH_RESULT_SIZE) {
            error("Too many interventions found.  Please narrow search.");
            return;
        }
        for (InterventionDTO inter : interList) {
            InterventionWebDTO newRec = new InterventionWebDTO();
            newRec.setType(CdConverter.convertCdToString(inter.getTypeCode()));
            newRec.setIdentifier(IiConverter.convertToString(inter.getIdentifier()));
            newRec.setDescription(StConverter.convertToString(inter.getDescriptionText()));
            newRec.setName(StConverter.convertToString(inter.getName()));
            interWebList.add(newRec);
        }
        loadAlternateNames();
    }

    private void loadAlternateNames() {
        Ii[] ianIis = new Ii[interWebList.size()];
        int x = 0;
        for (InterventionWebDTO dto : interWebList) {
            ianIis[x++] = IiConverter.convertToIi(dto.getIdentifier());
        }
        List<InterventionAlternateNameDTO> ianList;
        try {
            ianList = PaRegistry.getInterventionAlternateNameService().getByIntervention(ianIis);
        } catch (Exception e) {
            error("Exception thrown while getting alternate names.", e); 
            return;
        }
        HashMap<String, String> aNames = new HashMap<String, String>();
        for (InterventionAlternateNameDTO ian : ianList) {
            String id = IiConverter.convertToString(ian.getInterventionIdentifier());
            if (aNames.containsKey(id)) {
                aNames.put(id, aNames.get(id) + ", " + StConverter.convertToString(ian.getName()));
            } else {
                aNames.put(id, StConverter.convertToString(ian.getName()));
            }
        }
        for (InterventionWebDTO dto : interWebList) {
            dto.setOtherNames(aNames.get(dto.getIdentifier()));
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
     * @return the interWebList
     */
    public List<InterventionWebDTO> getInterWebList() {
        return interWebList;
    }

    /**
     * @param interWebList the interWebList to set
     */
    public void setInterWebList(List<InterventionWebDTO> interWebList) {
        this.interWebList = interWebList;
    }
}
