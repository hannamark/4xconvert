package gov.nih.nci.pa.bo;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Auditable {
    /**
     * Standard max length for text columns.
     */
    public static final int LONG_TEXT_LENGTH = 200;
    private Long id;

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
    public boolean isNew() {
        return (this.id == null);
    }

    
 
}
