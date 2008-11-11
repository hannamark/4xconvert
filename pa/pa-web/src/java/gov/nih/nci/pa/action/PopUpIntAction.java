package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
* @author Hugh Reinhart
* @since 10/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD")
public class PopUpIntAction extends ActionSupport {
    private static final long serialVersionUID = 9987838321L;
    
    private String searchName;
    private List<InterventionWebDTO> interWebList = new ArrayList<InterventionWebDTO>();

    private void loadResultList() {
        String tName = ServletActionContext.getRequest().getParameter("searchName");
        
        InterventionDTO criteria = new InterventionDTO();
        criteria.setName(StConverter.convertToSt(tName));
        
        interWebList.clear();
        try {
            List<InterventionDTO> interList = PaRegistry.getInterventionService().search(criteria);
            for (InterventionDTO inter : interList) {
                InterventionWebDTO newRec = new InterventionWebDTO();
                newRec.setType(CdConverter.convertCdToString(inter.getTypeCode()));
                newRec.setIdentifier(IiConverter.convertToString(inter.getIdentifier()));
                newRec.setDescription(StConverter.convertToString(inter.getDescriptionText()));
                newRec.setName(StConverter.convertToString(inter.getName()));
                StringBuffer onBuff = new StringBuffer("");
                List<InterventionAlternateNameDTO> ianList = PaRegistry.getInterventionAlternateNameService().
                        getByIntervention(inter.getIdentifier());
                for (InterventionAlternateNameDTO ian : ianList) {
                    if (ianList.get(0) !=  ian) {
                        onBuff.append(", ");
                    }
                    onBuff.append(StConverter.convertToString(ian.getName()));
                }
                newRec.setOtherNames(onBuff.toString());
                interWebList.add(newRec);
            }
        } catch (Exception e) {
            // don't fail on exceptions
        }
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
