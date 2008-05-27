/**
 * 
 */
package gov.nih.nci.pa.bo;


import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

/**
 * todo: Interface with PO as source of truth.
 * Healthcare site bo.  
 * @author Hugh Reinhart
 *
 */
@Entity
@Table (appliesTo = "HEALTHCARE_SITE")
public class HealthcareSite implements Serializable {
    private static final long serialVersionUID = 1234567890L;
    
    private Long id;
    private String name;

    @SuppressWarnings("unused")    
    private void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the id of the object.
     *
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return this.id;
    }

    /**
     * determines if the object is new (has not been saved and assigned an id).
     *
     * @return whether the object is transient
     */
    @Transient
    public boolean isNew() {
        return (this.id == null);
    }

    
    /**
     * @return the name
     */
    @NotNull
    @Length(max = AbstractEntity.LONG_TEXT_LENGTH)
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    


}
