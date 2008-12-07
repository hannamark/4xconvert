package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.DiseaseAlternameDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


/**
* @author Hugh Reinhart
* @since 12/06/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD.SignatureDeclareThrowsException")
public class PopUpDiseaseDetailsAction  extends ActionSupport {
    private static final long serialVersionUID = 8117838321L;
        
    private DiseaseWebDTO disease;
    private List<DiseaseWebDTO> parentList;
    private List<DiseaseWebDTO> childList;
    
    /**
     * @return action
     * @throws Exception exception
     */
    @Override
    public String execute() throws Exception {
        String diseaseIdentifier = ServletActionContext.getRequest().getParameter("diseaseId");
        Ii diseaseIi = IiConverter.convertToIi(diseaseIdentifier);
        if (PAUtil.isEmpty(diseaseIdentifier)) {
            throw new PAException("Error passing disease id into PopUpDiseaseDetailsAction.  ");
        }
        DiseaseServiceRemote diseaseSvc = PaRegistry.getDiseaseService();
        DiseaseDTO d = diseaseSvc.get(IiConverter.convertToIi(diseaseIdentifier));
        disease = new DiseaseWebDTO();
        disease.setCode(StConverter.convertToString(d.getDiseaseCode()));
        disease.setConceptId(StConverter.convertToString(d.getNtTermIdentifier()));
        disease.setDiseaseIdentifier(diseaseIdentifier);
        disease.setMenuDisplayName(StConverter.convertToString(d.getMenuDisplayName()));
        disease.setPreferredName(StConverter.convertToString(d.getPreferredName()));

        List<DiseaseAlternameDTO> alternameList = PaRegistry.getDiseaseAlternameService().getByDisease(diseaseIi);
        StringBuffer anBuff = new StringBuffer();
        for (DiseaseAlternameDTO altername : alternameList) {
            if (alternameList.get(0) !=  altername) {
                anBuff.append(", ");
            }
            anBuff.append(StConverter.convertToString(altername.getAlternateName()));
        }
        disease.setAlternames(anBuff.toString());

        parentList = new ArrayList<DiseaseWebDTO>();
        DiseaseParentServiceRemote diseaseParentSvc = PaRegistry.getDiseaseParentService();
        List<DiseaseParentDTO> dtoList = diseaseParentSvc.getByChildDisease(diseaseIi);
        for (DiseaseParentDTO dto : dtoList) {
            d = diseaseSvc.get(dto.getParentDiseaseIdentifier());
            DiseaseWebDTO newD = new DiseaseWebDTO();
            newD.setDiseaseIdentifier(IiConverter.convertToString(d.getIdentifier()));
            newD.setPreferredName(StConverter.convertToString(d.getPreferredName()));
            parentList.add(newD);
        }

        childList = new ArrayList<DiseaseWebDTO>();
        dtoList = diseaseParentSvc.getByParentDisease(diseaseIi);
        for (DiseaseParentDTO dto : dtoList) {
            d = diseaseSvc.get(dto.getDiseaseIdentifier());
            DiseaseWebDTO newD = new DiseaseWebDTO();
            newD.setDiseaseIdentifier(IiConverter.convertToString(d.getIdentifier()));
            newD.setPreferredName(StConverter.convertToString(d.getPreferredName()));
            childList.add(newD);
        }

        return super.execute();
    }

    /**
     * @return the disease
     */
    public DiseaseWebDTO getDisease() {
        return disease;
    }

    /**
     * @param disease the disease to set
     */
    public void setDisease(DiseaseWebDTO disease) {
        this.disease = disease;
    }

    /**
     * @return the parentList
     */
    public List<DiseaseWebDTO> getParentList() {
        return parentList;
    }

    /**
     * @param parentList the parentList to set
     */
    public void setParentList(List<DiseaseWebDTO> parentList) {
        this.parentList = parentList;
    }

    /**
     * @return the childList
     */
    public List<DiseaseWebDTO> getChildList() {
        return childList;
    }

    /**
     * @param childList the childList to set
     */
    public void setChildList(List<DiseaseWebDTO> childList) {
        this.childList = childList;
    }
}
