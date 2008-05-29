package gov.nih.nci.pa.domain;

import gov.nih.nci.pa.enums.TrialPhaseCode;

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
 * Protocol bean for managing protocol.
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
    private String intentCode  = null;
    private String monitorCode = null;
    private TrialPhaseCode phaseCode = null;
    private List<ProtocolStatus> protocolStatuses = new ArrayList<ProtocolStatus>();
    private List<StudySite> studySites = new ArrayList<StudySite>();
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();
    
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
    @Column(name = "INTENT_CODE")
    public String getIntentCode() {
        return intentCode;
    }

    /**
     * @param intentCode intent
     */
    public void setIntentCode(String intentCode) {
        this.intentCode = intentCode;
    }

    /**
     * @return monitor code
     */
    @Column(name = "MONITOR_CODE")
    public String getMonitorCode() {
        return monitorCode;
    }

    /**
     * @param monitorCode monitor code
     */
    public void setMonitorCode(String monitorCode) {
        this.monitorCode = monitorCode;
    }

    /**
     * @return phase code
     */
    @Column(name = "PHASE_CODE")
    @Enumerated(EnumType.STRING)
    public TrialPhaseCode getPhaseCode() {
        return phaseCode;
    }

    /**
     * @param phaseCode phase code
     */
    public void setPhaseCode(TrialPhaseCode phaseCode) {
        this.phaseCode = phaseCode;
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
    
}
