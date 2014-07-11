package gov.nih.nci.po.data.bo;

import com.fiveamsolutions.nci.commons.search.Searchable;
import gov.nih.nci.po.util.NotEmpty;
import gov.nih.nci.po.util.PoRegistry;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * Lookup class for types of Funding Mechanism.
 * @author smatyas
 */
@Entity
@org.hibernate.annotations.Entity(mutable = false)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) // Unit tests write, so cannot use read-only
public class FundingMechanism extends AbstractCodeValue implements Comparable<FundingMechanism> {

    private static final long serialVersionUID = 1L;
    private String description;
    private FundingMechanismStatus status;
    private String category;

    /**
     * For unit tests only.
     * @param code type
     * @param description description
     * @param category group/category
     * @param status status
     */
    public FundingMechanism(String code, String description, String category, FundingMechanismStatus status) {
        super(code);
        this.description = description;
        this.category = category;
        this.status = status;
    }

    /**
     * @deprecated hibernate-only constructor
     */
    @Deprecated
    public FundingMechanism() {
        // for hibernate only - do nothing
        super();
    }

    /**
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the description
     */
    @Column(name = "description", updatable = false, unique = false)
    @Length(max = DESC_LENGTH)
    @NotEmpty
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "desc")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getDescription() {
        return description;
    }

    /**
     * @return status
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "status")
    @Searchable(matchMode = Searchable.MATCH_MODE_EXACT)
    public FundingMechanismStatus getStatus() {
        return status;
    }

    /**
     * @param status current status
     */
    public void setStatus(FundingMechanismStatus status) {
        this.status = status;
    }

    /**
     * @return category/group name
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "category")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getCategory() {
        return category;
    }

    /**
     * @param category the group/category of the funding mechanism
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Represents the possible funding mechanism status. Only ACTIVE funding mechanisms should be visible to user.
     */
    public enum FundingMechanismStatus {
        /**
         * Inactive codes.
         */
        INACTIVE,
        /**
         * Active codes.
         */
        ACTIVE;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(FundingMechanism o) {
        return this.getCode().compareTo(o.getCode());
    }

}
