package gov.nih.nci.po.webservices.service.bo.filter;

import gov.nih.nci.po.data.bo.AbstractRole;

/**
 * @param <TYPE> see {@link RoleUpdateFilter}
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class CreatedByFilter<TYPE> implements RoleUpdateFilter<TYPE> {
    @Override
    public void handle(TYPE currentInstance, TYPE updatedInstance) {
        if (currentInstance != null) {
            ((AbstractRole) updatedInstance).setCreatedBy(((AbstractRole) currentInstance).getCreatedBy());
        }
    }
}
