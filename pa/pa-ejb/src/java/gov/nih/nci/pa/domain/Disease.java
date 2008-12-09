package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.ActiveInactivePendingCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.Length;

/**
 * @author Hugh Reinhart
 * @since 11/29/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name = "DISEASE")
public class Disease extends AbstractEntityWithStatusCode<ActiveInactivePendingCode> {
    private static final long serialVersionUID = 1276767890L;
    
    private String diseaseCode;
    private String ntTermIdentifier;
    private String preferredName;
    private String menuDisplayName;

    private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>(); 
    private List<DiseaseAltername> diseaseAlternames = new ArrayList<DiseaseAltername>();
    private List<DiseaseParent> diseaseParents = new ArrayList<DiseaseParent>();
    private List<DiseaseParent> diseaseChildren = new ArrayList<DiseaseParent>();
    
    /**
     * @return the diseaseCode
     */
    @Column(name = "DISEASE_CODE")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getDiseaseCode() {
        return diseaseCode;
    }
    /**
     * @param diseaseCode the diseaseCode to set
     */
    public void setDiseaseCode(String diseaseCode) {
        this.diseaseCode = diseaseCode;
    }
    /**
     * @return the ntTermIdentifier
     */
    @Column(name = "NT_TERM_IDENTIFIER")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getNtTermIdentifier() {
        return ntTermIdentifier;
    }
    /**
     * @param ntTermIdentifier the ntTermIdentifier to set
     */
    public void setNtTermIdentifier(String ntTermIdentifier) {
        this.ntTermIdentifier = ntTermIdentifier;
    }
    /**
     * @return the preferredName
     */
    @Column(name = "PREFERRED_NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getPreferredName() {
        return preferredName;
    }
    /**
     * @param preferredName the preferredName to set
     */
    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }
    /**
     * @return the menuDisplayName
     */
    @Column(name = "MENU_DISPLAY_NAME")
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getMenuDisplayName() {
        return menuDisplayName;
    }
    /**
     * @param menuDisplayName the menuDisplayName to set
     */
    public void setMenuDisplayName(String menuDisplayName) {
        this.menuDisplayName = menuDisplayName;
    }
    /**
     * @return the studyDiseases
     */
    @OneToMany(mappedBy = "disease")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<StudyDisease> getStudyDiseases() {
        return studyDiseases;
    }
    /**
     * @param studyDiseases the studyDiseases to set
     */
    public void setStudyDiseases(List<StudyDisease> studyDiseases) {
        this.studyDiseases = studyDiseases;
    }
    /**
     * @return the diseaseAlternames
     */
    @OneToMany(mappedBy = "disease")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<DiseaseAltername> getDiseaseAlternames() {
        return diseaseAlternames;
    }
    /**
     * @param diseaseAlternames the diseaseAlternames to set
     */
    public void setDiseaseAlternames(List<DiseaseAltername> diseaseAlternames) {
        this.diseaseAlternames = diseaseAlternames;
    }
    /**
     * @return the diseaseParents
     */
    @OneToMany(mappedBy = "disease")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<DiseaseParent> getDiseaseParents() {
        return diseaseParents;
    }
    /**
     * @param diseaseParents the diseaseParents to set
     */
    public void setDiseaseParents(List<DiseaseParent> diseaseParents) {
        this.diseaseParents = diseaseParents;
    }
    /**
     * @return the diseaseChildren
     */
    @OneToMany(mappedBy = "parentDisease")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public List<DiseaseParent> getDiseaseChildren() {
        return diseaseChildren;
    }
    /**
     * @param diseaseChildren the diseaseChildren to set
     */
    public void setDiseaseChildren(List<DiseaseParent> diseaseChildren) {
        this.diseaseChildren = diseaseChildren;
    }
}
