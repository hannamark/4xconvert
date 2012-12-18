package gov.nih.nci.po.data.bo;

import gov.nih.nci.po.util.PoRegistry;

import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Index;
import org.hibernate.validator.Length;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Base class for all organization to health care facility types.
 *
 * @xsnapshot.snapshot-class name="iso" tostring="none" generate-helper-methods="false"
 *      class="gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO"
 *      model-extends="gov.nih.nci.po.data.bo.AbstractContactableOrganizationRole"
 *      extends="gov.nih.nci.services.correlation.AbstractContactableOrganizationRoleDTO"
 *      serial-version-uid="2L"
 */
@MappedSuperclass
public abstract class AbstractEnhancedOrganizationRole extends AbstractContactableOrganizationRole {

    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_TEXT_COL_LENGTH = 160;

    private String name;

    /**
     * @return the name
     * @xsnapshot.property match="iso" type="gov.nih.nci.iso21090.EnOn"
     *                     snapshot-transformer="gov.nih.nci.po.data.convert.StringConverter"
     *                     model-transformer="gov.nih.nci.po.data.convert.EnConverter"
     */
    @Length(max = DEFAULT_TEXT_COL_LENGTH)
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    @Index(name = PoRegistry.GENERATE_INDEX_NAME_PREFIX + "name")
    public String getName() {
        return this.name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
