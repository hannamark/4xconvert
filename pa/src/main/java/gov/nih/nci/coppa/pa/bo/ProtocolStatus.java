package gov.nih.nci.coppa.pa.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.NotNull;

/**
 * Protocol bean for managing protocol.
 * @author Naveen Amiruddin
 * @since 05/22/2007
 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the copyright holder, NCI.
 */

@Entity
@SuppressWarnings("PMD.UselessOverridingMethod")
@Table(name = "PROTOCOL_STATUS")

public class ProtocolStatus extends AbstractEntity {
	
    private String statusCode ;
    private Date statusDate ;
    private Protocol protocol ;
    

	@Column(name = "STATUS_CODE")
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	@Column(name = "STATUS_DATE")
	public Date getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}
	
    @ManyToOne
    @JoinColumn(name = "PROTOCOL_ID", updatable = false)
    @NotNull
	public Protocol getProtocol() {
		return protocol;
	}
	
	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

}
