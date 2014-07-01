package gov.nih.nci.po.webservices.service.bo.filter;

/**
 * @param <TYPE> The BO type.
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface RoleUpdateFilter<TYPE> {

    /**
     * @param currentInstance The current instance of the model.
     * @param updatedInstance The proposed state.
     */
    void handle(TYPE currentInstance, TYPE updatedInstance);
}
