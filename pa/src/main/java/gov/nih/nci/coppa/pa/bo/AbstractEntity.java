package gov.nih.nci.coppa.pa.bo;

import java.io.Serializable;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 */
public abstract class AbstractEntity implements Serializable, Auditable {
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
