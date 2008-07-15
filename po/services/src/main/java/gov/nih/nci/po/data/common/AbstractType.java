package gov.nih.nci.po.data.common;

import gov.nih.nci.po.util.NotEmpty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 * Base type information for persons and organizations. For now, types are simple names. A person or organization type
 * may be considered a <i>basic type</i>. Basic types are displayed to users upon creation, or anytime a limited subset
 * of organization or person types are desired.
 */
@MappedSuperclass
public abstract class AbstractType implements PersistentObject {

    private static final long serialVersionUID = -1699270157337673997L;
    private Long id;
    private String name;
    private boolean basicType;

    /**
     * Default constructor.
     *
     * @deprecated use non-default constructor
     */
    @Deprecated
    protected AbstractType() {
        // hibernate constructor
    }

    /**
     * @param name name
     */
    public AbstractType(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    @Column(unique = true)
    @NotEmpty
    @Length(max = AbstractNameCodeEntity.MAX_NAME_LENGTH)
    public String getName() {
        return name;
    }

    /**
     * @param name new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the basicType
     */
    @NotNull
    public boolean isBasicType() {
        return basicType;
    }

    /**
     * @param basicType the basicType to set
     */
    public void setBasicType(boolean basicType) {
        this.basicType = basicType;
    }
}
