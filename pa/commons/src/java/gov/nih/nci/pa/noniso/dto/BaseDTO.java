/**
 * 
 */
package gov.nih.nci.pa.noniso.dto;

import java.io.Serializable;

/**
 * @author vinodh
 *
 */
public class BaseDTO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4665314612576853119L;
    
    /**
     * id
     */
    private Long id;

    /**
     * default constructor
     */
    public BaseDTO() {
        super();
    }

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
    
    

}