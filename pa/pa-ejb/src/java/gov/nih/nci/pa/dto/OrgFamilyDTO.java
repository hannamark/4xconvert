package gov.nih.nci.pa.dto;

import java.io.Serializable;

/**
 * @author Hugh Reinhart
 * @since Nov 15, 2012
 */
public class OrgFamilyDTO implements Serializable {

    private static final long serialVersionUID = 235503630658839402L;

    private Long id;
    private String name;
    
    private String p30SerialNumber;
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the name
     */
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
     * 
     * @return p30SerialNumber
     */
    public String getP30SerialNumber() {
        return p30SerialNumber;
    }
    
    /**
     * 
     * @param p30SerialNumber the serial number to set
     */
    public void setP30SerialNumber(String p30SerialNumber) {
        this.p30SerialNumber = p30SerialNumber;
    }
    
    
}
