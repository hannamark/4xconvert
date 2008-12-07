package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
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
public class PopUpDisAction extends ActionSupport {
    private static final long serialVersionUID = 8987838321L;
    
    private String searchName;
    private List<DiseaseWebDTO> disWebList = new ArrayList<DiseaseWebDTO>();
    private static final Logger LOG = Logger.getLogger(PopUpDisAction.class);


    private void loadResultList() {
        String tName = ServletActionContext.getRequest().getParameter("searchName");
        
        DiseaseDTO criteria = new DiseaseDTO();
        criteria.setPreferredName(StConverter.convertToSt(tName));
        
        getDisWebList().clear();
        try {
            List<DiseaseDTO> diseaseList = PaRegistry.getDiseaseService().search(criteria);
            for (DiseaseDTO disease : diseaseList) {
                DiseaseWebDTO newRec = new DiseaseWebDTO();
                newRec.setDiseaseIdentifier(IiConverter.convertToString(disease.getIdentifier()));
                newRec.setPreferredName(StConverter.convertToString(disease.getPreferredName()));
                newRec.setCode(StConverter.convertToString(disease.getDiseaseCode()));
                newRec.setConceptId(StConverter.convertToString(disease.getNtTermIdentifier()));
                newRec.setMenuDisplayName(StConverter.convertToString(disease.getMenuDisplayName()));
                List<DiseaseParentDTO> parentList = PaRegistry.getDiseaseParentService().
                        getByChildDisease(disease.getIdentifier());
                StringBuffer anBuff = new StringBuffer();
                for (DiseaseParentDTO parent : parentList) {
                    if (parentList.get(0) !=  parent) {
                        anBuff.append(", ");
                    }
                    DiseaseDTO temp = PaRegistry.getDiseaseService().get(parent.getParentDiseaseIdentifier());
                    anBuff.append(StConverter.convertToString(temp.getPreferredName()));
                }
                newRec.setParentPreferredName(anBuff.toString());
                getDisWebList().add(newRec);
            }
        } catch (Exception e) {
            LOG.error("Exception while loading disease results.  ", e);
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
