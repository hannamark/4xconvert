package gov.nih.nci.coppa.pa.bo;

import gov.nih.nci.coppa.pa.bo.Auditable;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for objects needing this property.
 */
public abstract class Entity implements Serializable, Auditable {
    private Long id;

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

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return new ToStringBuilder(this).append("id", getId()).toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        if (isNew()) {
            return System.identityHashCode(this);
        }

        return new HashCodeBuilder().append(getClass().getName()).append(getId()).hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Entity == false) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        if (this.isNew()) {
            return false;
        }
        Entity rhs = (Entity) obj;
        return new EqualsBuilder().append(this.getId(), rhs.getId()).isEquals();
    }
}
