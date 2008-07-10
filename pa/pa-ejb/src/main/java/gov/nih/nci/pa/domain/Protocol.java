package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.SponsorMonitorCode;
import gov.nih.nci.pa.enums.StudyPhaseCode;
import gov.nih.nci.pa.enums.StudyTypeCode;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * A systematic evaluation of an observation or an intervention (for example, treatment, 
 * drug, device, procedure or system) in one or more subjects. Frequently this is a 
 * test of a particular hypothesis about the treatment, drug, device, 
 * procedure or system. [CDAM]  A study can be either primary or correlative. 
 * A study is considered a primary study if it has one or more correlative studies. 
 * A correlative study extends the objectives or observations of a primary study, 
 * enrolling the same, or a subset of the same, subjects as the primary study. 
 * A Clinical Trial is a Study with type= "intervention" with subjects of type human
 * 
 * @author Naveen Amiruddin
 * @since 05/22/2007
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

@Entity

public class Protocol extends AbstractEntity {

    private static final long serialVersionUID = 1234567890L;

    private Long id;
    private String nciIdentifier = null;
    private String longTitleText = null;
    private String shortTitleText = null;
    private StudyTypeCode studyTypeCode  = null;
    private SponsorMonitorCode sponsorMonitorCode = null;
    private StudyPhaseCode studyPhaseCode = null;
    private List<ProtocolStatus> protocolStatuses = new ArrayList<ProtocolStatus>();
    private List<StudySite> studySites = new ArrayList<StudySite>();
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();
    private List<StudyCondition> studyConditions = new ArrayList<StudyCondition>();
    
    /**
     * Default constructor.
     */
    public Protocol() {
        // empty constructor
    }

    /**
     * set id.
     * @param id id
     */
     public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     * @return the id
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "ID")
    public Long getId() {
        return this.id;
    }

    
    /**
     * @return nci id
     */
    @Column(name = "NCI_IDENTIFIER", length = LONG_TEXT_LENGTH)
    public String getNciIdentifier() {
        return nciIdentifier;
    }

    /**
     * @param nciIdentifier nci id
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }

    /**
     * @return long title
     */
    @Column(name = "LONG_TITLE_TEXT ", length = LONG_TEXT_LENGTH)
    public String getLongTitleText() {
        return longTitleText;
    }

    
    /**
     * @param longTitleText long title
     */
    public void setLongTitleText(String longTitleText) {
        this.longTitleText = longTitleText;
        
    }

    /**
     * @return short title
     */
    @Column(name = "SHORT_TITLE_TEXT", length = LONG_TEXT_LENGTH)
    public String getShortTitleText() {
        return shortTitleText;
    }

    /**
     * @param shortTitleText short title
     */
    public void setShortTitleText(String shortTitleText) {
        this.shortTitleText = shortTitleText;
    }

    /**
     * @return intent code
     */
    @Column(name = "MONITOR_CODE")
    @Enumerated(EnumType.STRING)
    public SponsorMonitorCode getSponsorMonitorCode() {
        return sponsorMonitorCode;
    }

    /**
     * @param sponsorMonitorCode sponsorMonitorCode
     */
    public void setSponsorMonitorCode(SponsorMonitorCode sponsorMonitorCode) {
        this.sponsorMonitorCode = sponsorMonitorCode;
    }

    /**
     * @return studyTypeCode study type code
     */
    @Column(name = "INTENT_CODE")
    @Enumerated(EnumType.STRING)
    public StudyTypeCode getStudyTypeCode() {
        return studyTypeCode;
    }

    /**
     * @param studyTypeCode studyTypeCode
     */
    public void setStudyTypeCode(StudyTypeCode studyTypeCode) {
        this.studyTypeCode = studyTypeCode;
    }

    /**
     * @return phase code
     */
    @Column(name = "PHASE_CODE")
    @Enumerated(EnumType.STRING)
    public StudyPhaseCode getStudyPhaseCode() {
        return studyPhaseCode;
    }

    /**
     * @param studyPhaseCode StudyPhaseCode
     */
    public void setStudyPhaseCode(StudyPhaseCode studyPhaseCode) {
        this.studyPhaseCode = studyPhaseCode;
    }

    /**
     * 
     * @return protocol statuses
     */
    @OneToMany(mappedBy = "protocol")
    public List<ProtocolStatus> getProtocolStatuses() {
         return protocolStatuses;
    }

    /**
     * @param protocolStatuses protocol statues 
     */
    public void setProtocolStatuses(List<ProtocolStatus> protocolStatuses) {
        this.protocolStatuses = protocolStatuses;
    }

    /**
     * @return studySites
     */
    @OneToMany(mappedBy = "protocol")
    public List<StudySite> getStudySites() {
        return studySites;
    }

    /**
     * 
     * @param studySites Study Sites
     */
    public void setStudySites(List<StudySite> studySites) {
        this.studySites = studySites;
    }

    /**
     * @return studyInvestigators
     */
    @OneToMany(mappedBy = "protocol")
    public List<StudyInvestigator> getStudyInvestigators() {
        return studyInvestigators;
    }

    /**
     * @param studyInvestigators studyInvestigators
     */
    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        this.studyInvestigators = studyInvestigators;
    }

    /**
     * 
     * @return studyConditions
     */
    @OneToMany(mappedBy = "protocol")
    public List<StudyCondition> getStudyConditions() {
        return studyConditions;
    }

    /**
     * 
     * @param studyConditions studyConditions
     */
    public void setStudyConditions(List<StudyCondition> studyConditions) {
        this.studyConditions = studyConditions;
    }
    
    
}
