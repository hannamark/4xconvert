package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The participation of a Regulatory Authority in the governmental oversight of a
 * clinical trial.
 * 
 * For example, the FDA oversees a breast cancer trial using tamoxifen. 
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table (name = "study_regulatory_authority")
public class StudyRegulatoryAuthority extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	private long studyProtocolID;
	private long regulatoryAuthorityID;
	
	/**
	 * @return the studyProtocolID
	 */
	@Column(name = "study_protocol_id")
	public long getStudyProtocolID() {
		return studyProtocolID;
	}
	/**
	 * @param studyProtocolID the studyProtocolID to set
	 */	
	public void setStudyProtocolID(long studyProtocolID) {
		this.studyProtocolID = studyProtocolID;
	}
	/**
	 * @return the regulatoryAuthorityID
	 */
	@Column(name = "regulatory_authority_id")
	public long getRegulatoryAuthorityID() {
		return regulatoryAuthorityID;
	}
	/**
	 * @param regulatoryAuthorityID the regulatoryAuthorityID to set
	 */
	public void setRegulatoryAuthorityID(long regulatoryAuthorityID) {
		this.regulatoryAuthorityID = regulatoryAuthorityID;
	}
}
