package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.audit.Auditable;
import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.NotEmpty;
import gov.nih.nci.po.util.PoRegistry;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * This Entity represents an Alias.
 * 
 * @author Rohit Gupta
 */
@Entity
public class Alias implements Auditable, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Length of the value field.
     */
    protected static final int MAX_NAME_LENGTH = 254;
    private Long id;
    private String value;

    /**
     * Constructs a new alias with a blank value.
     */
    public Alias() {
        // do nothing
    }

    /**
     * @param value
     *            alias value
     */
    public Alias(String value) {
        this.value = value;
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
     * @return the value
     */
    @NotEmpty
    @Length(max = MAX_NAME_LENGTH)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "value")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}