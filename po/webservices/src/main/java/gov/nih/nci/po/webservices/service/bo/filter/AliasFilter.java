package gov.nih.nci.po.webservices.service.bo.filter;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * This looks a bit wonky, but given the way that the type system is set up it's
 * the only quick way to abstract this without a CPD violation.
 *
 * @param <TYPE>    The Correlation BO type.
 * @param <CR_TYPE> The CR Type for the BO type.
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
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

        // set the existing aliases as it was ignored during converter
        updatedInstance.getAlias().addAll(currentInstance.getAlias());

        // check if existing Org name or aliases has the incoming name
        if (nameHasChanged(currentInstance, updatedInstance)
                && aliasIsNotPresent(currentInstance.getAlias(), updatedInstance.getName())) {
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


    /**
     * This method is used to check if the Alias list contains the name(case
     * insensitive).
     *
     * @return true if the name if not present in the list.
     */
    private boolean aliasIsNotPresent(List<Alias> aliasList, String name) {
        boolean result = true;

        for (Alias alias : aliasList) {
            if (alias.getValue().equalsIgnoreCase(name)) {
                result = false;
                break;
            }
        }

        return result;
    }

    private boolean isNotAcceptableType(TYPE currentInstance) {
        return !(currentInstance instanceof AbstractEnhancedOrganizationRole);
    }


}
