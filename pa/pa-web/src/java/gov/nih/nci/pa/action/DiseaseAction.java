package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * Action class for Disease/Condition use-case.
 * @author Hugh Reinhart
 * @since 12/1/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.SignatureDeclareThrowsException" })
public class DiseaseAction extends AbstractListEditAction implements Preparable {
    private static final long serialVersionUID = 1234584746L;
    
    private DiseaseWebDTO disease;
    private List<DiseaseWebDTO> diseaseList;

    /**
     * @return action result
     * @throws Exception exception
     */
    @Override
    public String edit() throws Exception {
        StudyDiseaseDTO sd = studyDisesaeSvc.get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        disease = new DiseaseWebDTO();
        disease.setDiseaseIdentifier(IiConverter.convertToString(sd.getDiseaseIdentifier()));
        disease.setStudyDiseaseIdentifier(getSelectedRowIdentifier());
        disease.setLead(PAUtil.isBlNull(sd.getLeadDiseaseIndicator()) ? null 
                : BlConverter.covertToBoolean(sd.getLeadDiseaseIndicator()).toString());
        return super.edit();
    }
    
    /**
     * @return action result
     * @throws Exception exception
     */
    @Override
    public String delete() throws Exception {
        studyDisesaeSvc.delete(IiConverter.convertToIi(getSelectedRowIdentifier()));
        return super.delete();
    }
    
    /**
     * @return result
     * @throws Exception exception
     */
    @Override
    public String add() throws Exception {
        enforceBusinessRules();
        if (!hasActionErrors()) {
            StudyDiseaseDTO sdDto = new StudyDiseaseDTO();
            sdDto.setIdentifier(null);
            sdDto.setDiseaseIdentifier(IiConverter.convertToIi(getDisease().getDiseaseIdentifier()));
            Bl blLead = null;
            if (getDisease().getLead() != null) {
                Boolean bLead = Boolean.valueOf(getDisease().getLead());
                blLead = BlConverter.convertToBl(bLead);
            }  
            sdDto.setLeadDiseaseIndicator(blLead);
            sdDto.setStudyProtocolIdentifier(spIi);
            try {
                studyDisesaeSvc.create(sdDto);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors()) {
            return super.create();
        }
        return super.add();
    }
    
    /**
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        enforceBusinessRules();
        if (!hasActionErrors()) {
            StudyDiseaseDTO pa = studyDisesaeSvc.get(IiConverter.convertToIi(disease.getStudyDiseaseIdentifier()));
            Bl blLead = null;
            if (getDisease().getLead() != null) {
                Boolean bLead = Boolean.valueOf(getDisease().getLead());
                blLead = BlConverter.convertToBl(bLead);
            }
            pa.setLeadDiseaseIndicator(blLead);
            try {
                studyDisesaeSvc.update(pa);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors()) {
            return super.edit();
        }
        return super.update();
    }

    /**
     * Method called from pop-up.  Loads selected disease.
     * @return result
     * @throws Exception on error.
     */
    public String display() throws Exception {
        setDisease(new DiseaseWebDTO());
        getDisease().setDiseaseIdentifier(ServletActionContext.getRequest().getParameter("diseaseId"));
        return super.create();
    }
    
    @SuppressWarnings("PMD.NPathComplexity")
    private void enforceBusinessRules() throws Exception {
        if (PAUtil.isEmpty(disease.getDiseaseIdentifier())) {
            addActionError("Please select a Disease/Condition to add to the trial.  ");
            return;
        }
        DiseaseDTO dto = diseaseSvc.get(IiConverter.convertToIi(getDisease().getDiseaseIdentifier()));
        String menu = StConverter.convertToString(dto.getMenuDisplayName());
        ArrayList<Long> parentList = new ArrayList<Long>();
        ArrayList<Long> childList = new ArrayList<Long>();
        // get parent diseases
        List<DiseaseParentDTO> dpList = diseaseParentSvc.getByChildDisease(dto.getIdentifier());
        for (DiseaseParentDTO dp : dpList) {
            parentList.add(IiConverter.convertToLong(dp.getParentDiseaseIdentifier()));
        }
        // get child diseases
        dpList = diseaseParentSvc.getByParentDisease(dto.getIdentifier());
        for (DiseaseParentDTO dp : dpList) {
            childList.add(IiConverter.convertToLong(dp.getDiseaseIdentifier()));
        }
        List<StudyDiseaseDTO> sdList = studyDisesaeSvc.getByStudyProtocol(spIi);
        boolean hasParent = false;
        boolean hasChild = false;
        for (StudyDiseaseDTO sd : sdList) {
            long existingId = IiConverter.convertToLong(sd.getDiseaseIdentifier());
            if (parentList.contains(existingId)) {
                hasParent = true;
            }
            if (childList.contains(existingId)) {
                hasChild = true;
            }
        }

        if (PAUtil.isEmpty(menu)) {
            addActionError("Diseases without a menu display name are not suitable for reporting.  ");
        }
        if (hasParent) {
            addActionError("Redundancy error:  this trial already includes a parent of the selected disease.  ");
        }
        if (hasChild) {
            addActionError("Redundancy error:  this trial already includes a child of the selected disease.  ");
        }
    }
    
    private String buildParentPreferredName(String diseaseId) throws Exception {
        List<DiseaseParentDTO> parentList = diseaseParentSvc.getByChildDisease(IiConverter.convertToIi(diseaseId));
        StringBuffer ppBuff = new StringBuffer();
        for (DiseaseParentDTO parent : parentList) {
            if (parentList.get(0) !=  parent) {
                ppBuff.append(", ");
            }
            DiseaseDTO temp = diseaseSvc.get(parent.getParentDiseaseIdentifier());
            ppBuff.append(StConverter.convertToString(temp.getPreferredName()));
        }
        return ppBuff.toString();
    }
    
    /**
     * @throws Exception exception
     */
    @Override
    protected void loadListForm() throws Exception {
        List<DiseaseWebDTO> nl = new ArrayList<DiseaseWebDTO>();
        List<StudyDiseaseDTO> sdList = studyDisesaeSvc.getByStudyProtocol(spIi);
        for (StudyDiseaseDTO sd : sdList) {
            DiseaseDTO d = diseaseSvc.get(sd.getDiseaseIdentifier());
            DiseaseWebDTO n = new DiseaseWebDTO();
            n.setStudyDiseaseIdentifier(IiConverter.convertToString(sd.getIdentifier()));
            n.setDiseaseIdentifier(IiConverter.convertToString(d.getIdentifier()));
            n.setCode(StConverter.convertToString(d.getDiseaseCode()));
            n.setConceptId(StConverter.convertToString(d.getNtTermIdentifier()));
            if (!PAUtil.isBlNull(sd.getLeadDiseaseIndicator()) 
                    && BlConverter.covertToBoolean(sd.getLeadDiseaseIndicator())) {
                n.setLead("Yes");
            }
            n.setMenuDisplayName(StConverter.convertToString(d.getMenuDisplayName()));
            n.setParentPreferredName(buildParentPreferredName(IiConverter.convertToString(d.getIdentifier())));
            n.setPreferredName(StConverter.convertToString(d.getPreferredName()));
            nl.add(n);
        }
        setDiseaseList(nl);
    }

    
    /**
     * Uses data from disease.diseaseIdentifier, disease.lead, and disease.studyDiseaseIdentifier.
     * Reads remainder from database.
     * @throws Exception exception
     */
    @Override
    protected void loadEditForm() throws Exception {
        if ((disease == null) || PAUtil.isEmpty(disease.getDiseaseIdentifier())) {
            disease = new DiseaseWebDTO();
        } else {
            Ii ii = IiConverter.convertToIi(disease.getDiseaseIdentifier());
            DiseaseDTO dto = diseaseSvc.get(ii);
            disease.setCode(StConverter.convertToString(dto.getDiseaseCode()));
            disease.setConceptId(StConverter.convertToString(dto.getNtTermIdentifier()));
            disease.setMenuDisplayName(StConverter.convertToString(dto.getMenuDisplayName()));
            disease.setPreferredName(StConverter.convertToString(dto.getPreferredName()));
            disease.setParentPreferredName(buildParentPreferredName(disease.getDiseaseIdentifier()));
        }
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
     * @return the diseaseList
     */
    public List<DiseaseWebDTO> getDiseaseList() {
        return diseaseList;
    }

    /**
     * @param diseaseList the diseaseList to set
     */
    public void setDiseaseList(List<DiseaseWebDTO> diseaseList) {
        this.diseaseList = diseaseList;
    }
}
