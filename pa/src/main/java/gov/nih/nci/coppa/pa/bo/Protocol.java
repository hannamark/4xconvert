package gov.nih.nci.coppa.pa.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Protocol bean for managing protocol
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name="protocol")
public class Protocol extends EntityIdentifier {
	
	private static final long serialVersionUID = 1234567890L;
	
    private Long id;
    private String nciIdentifier = null ;
    private String longTitleText = null ;
    private String shortTitleText = null ;
    private String intentCode  = null ;
    private String monitorCode = null ;
    private String phaseCode = null ;
    
    /**
     * set id 	
     * @param id
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
    @Column(name="ID")
    public Long getId() {
        return this.id;
    }

    @Column(name="NCI_IDENTIFIER",length=200 )
    public String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}

	@Column(name="LONG_TITLE_TEXT ",length=200 )
	public String getLongTitleText() {
		return longTitleText;
	}

	public void setLongTitleText(String longTitleText) {
		this.longTitleText = longTitleText;
	}

	@Column(name="SHORT_TITLE_TEXT",length=200 )
	public String getShortTitleText() {
		return shortTitleText;
	}

	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	@Column(name="INTENT_CODE")
	public String getIntentCode() {
		return intentCode;
	}

	public void setIntentCode(String intentCode) {
		this.intentCode = intentCode;
	}

	@Column(name="MONITOR_CODE")
	public String getMonitorCode() {
		return monitorCode;
	}

	public void setMonitorCode(String monitorCode) {
		this.monitorCode = monitorCode;
	}

	@Column(name="PHASE_CODE")
	public String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

}
