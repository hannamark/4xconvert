package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.webservices.service.bo.filter.AliasFilter;

/**
 * @param <TYPE>    see {@link AbstractRoleBoService}
 * @param <CR_TYPE> see {@link AbstractRoleBoService}
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractEnhancedOrganizationRoleBoService
        <TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        extends AbstractRoleBoService<TYPE, CR_TYPE> {

    /**
     * Default constructor that initializes some filters.
     */
    public AbstractEnhancedOrganizationRoleBoService() {
        super();
        getUpdateFilters().add(new AliasFilter<TYPE, CR_TYPE>());
        getCrCreationFilters().add(new AliasFilter<TYPE, CR_TYPE>());
    }



}
