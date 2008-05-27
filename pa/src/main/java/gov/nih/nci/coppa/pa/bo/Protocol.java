package gov.nih.nci.coppa.pa.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Protocol bean for managing protocol.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "protocol")
public class Protocol extends AbstractEntity {
    
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    private String nciIdentifier = null;
    private String longTitleText = null;
    private String shortTitleText = null;
    private String intentCode  = null;
    private String monitorCode = null;
    private String phaseCode = null;
    private List<ProtocolStatus> protocolStatus = new ArrayList<ProtocolStatus>();
    
    
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
    public String getPhaseCode() {
        return phaseCode;
    }

    /**
     * @param phaseCode phase code
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * @return protocol status
     */
    @OneToMany(mappedBy = "protocol")
    public List<ProtocolStatus> getProtocolStatus() {
        return protocolStatus;
    }

    /**
     * @param protocolStatus protocol status
     */
    public void setProtocolStatus(List<ProtocolStatus> protocolStatus) {
        this.protocolStatus = protocolStatus;
    }
    
    
    

}
