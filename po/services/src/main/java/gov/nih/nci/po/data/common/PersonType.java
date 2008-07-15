package gov.nih.nci.po.data.common;

import gov.nih.nci.po.audit.Auditable;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Type information for people.  For now, types are simple names.  A
 * person type may be considered a <i>basic type</i>.  Basic types are
 * displayed to users upon creation, or anytime a limited subset of person types
 * is desired.
 */
@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)  // Unit tests write, so cannot use read-only
public class PersonType extends AbstractType implements Auditable {

    private static final long serialVersionUID = 4949273982967100482L;
    
    /**
     * Standard PersonType.name for investigators.
     */
    public static final String INVESTIGATOR = "Investigator";

    /**
     * Default constructor.
     * @deprecated use non-default constructor
     */
    @Deprecated
    public PersonType() {
        // hibernate constructor
    }

    /**
     * @param name name
     */
    public PersonType(String name) {
        super(name);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o instanceof PersonType) {
            PersonType x = (PersonType) o;
            return new EqualsBuilder()
                    .append(getId(), x.getId())
                    .append(getName(), x.getName())
                    .append(isBasicType(), x.isBasicType())
                    .isEquals();
        }
        return false;        
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .append(getName())
                .append(isBasicType())
                .toHashCode();
    }  
}