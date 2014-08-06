package gov.nih.nci.po.webservices.service.bo.filter;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.util.PoServiceUtil;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * This looks a bit wonky, but given the way that the type system is set up it's
 * the only quick way to abstract this without a CPD violation.
 *
 * @param <TYPE>    The Correlation BO type.
 * @param <CR_TYPE> The CR Type for the BO type.
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 * @author Rohit Gupta
 */
public class AliasFilter<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        implements RoleUpdateFilter<TYPE>,
        CrCreationFilter<TYPE, CR_TYPE> {

    @Override
    public void handle(TYPE currentInstance, TYPE updatedInstance) {

        if (currentInstance == null || isNotAcceptableType(currentInstance)) {
            return;
        }

        AbstractEnhancedOrganizationRole castCurrentInstance = (AbstractEnhancedOrganizationRole) currentInstance;
        AbstractEnhancedOrganizationRole castUpdatedInstance = (AbstractEnhancedOrganizationRole) updatedInstance;

        handleInternal(castCurrentInstance, castUpdatedInstance);
    }

    @Override
    public void handle(TYPE updatedInstance, CR_TYPE cr) {
        if (updatedInstance == null || isNotAcceptableType(updatedInstance)) {
            return;
        }

        AbstractEnhancedOrganizationRole castCurrentInstance = (AbstractEnhancedOrganizationRole) updatedInstance;
        AbstractEnhancedOrganizationRole castUpdatedInstance = (AbstractEnhancedOrganizationRole) cr;

        handleInternal(castCurrentInstance, castUpdatedInstance);
    }


    private void handleInternal(AbstractEnhancedOrganizationRole currentInstance,
                                AbstractEnhancedOrganizationRole updatedInstance) {

        // set the existing aliases as it was ignored during converter (if they are not already present)
        if (CollectionUtils.isNotEmpty(currentInstance.getAlias())) {
            for (int i = 0; i < currentInstance.getAlias().size(); i++) {
                Alias alias = currentInstance.getAlias().get(i);
                if (PoServiceUtil.aliasIsNotPresent(updatedInstance.getAlias(), alias.getValue())) {
                    updatedInstance.getAlias().add(alias);
                }            
            }            
        }   

        // check if existing Org name or aliases has the incoming name
        if (nameHasChanged(currentInstance, updatedInstance)
                && PoServiceUtil.aliasIsNotPresent(currentInstance.getAlias(), updatedInstance.getName())) {
            // if not then add it new name to the list of org aliases
            updatedInstance.getAlias().add(
                    new gov.nih.nci.po.data.bo.Alias(updatedInstance.getName()));
        }

        // set name to the existing name as it might have been overwritten
        // during JAXB-BO converter (set it at the 'end')
        updatedInstance.setName(currentInstance.getName());
    }

    private boolean nameHasChanged(AbstractEnhancedOrganizationRole currentInstance,
                                   AbstractEnhancedOrganizationRole updatedInstance) {
        return !StringUtils.equalsIgnoreCase(currentInstance.getName(), updatedInstance.getName());
    }


    private boolean isNotAcceptableType(TYPE currentInstance) {
        return !(currentInstance instanceof AbstractEnhancedOrganizationRole);
    }


}
