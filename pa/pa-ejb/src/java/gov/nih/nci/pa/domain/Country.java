package gov.nih.nci.pa.domain;

import javax.persistence.Column;
import javax.persistence.Table;

import javax.persistence.Entity;
/**
 *  Pa COUNTRY  
 * 
 * @author Harsha
 * @since 08/05/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Entity
@Table(name =  "COUNTRY")
public class Country extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	private String alpha2;
	private String alpha3;
	private String name;
	private String numeric;
	/**
	 * @return the alpha2
	 */
	@Column(name = "alpha2")
	public String getAlpha2() {
		return alpha2;
	}
	/**
	 * @param alpha2 the alpha2 to set
	 */
	public void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}
	/**
	 * @return the alpha3
	 */
	@Column(name = "alpha3")
	public String getAlpha3() {
		return alpha3;
	}
	/**
	 * @param alpha3 the alpha3 to set
	 */
	public void setAlpha3(String alpha3) {
		this.alpha3 = alpha3;
	}
	/**
	 * @return the name
	 */
	@Column(name = "name")
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the numeric
	 */
	@Column(name = "numeric")
	public String getNumeric() {
		return numeric;
	}
	/**
	 * @param numeric the numeric to set
	 */
	public void setNumeric(String numeric) {
		this.numeric = numeric;
	}

}
