package gov.nih.nci.po.webservices.service.bo.filter;

import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;

/**
 * @param <TYPE>    The BO type.
 * @param <CR_TYPE> The CR type.
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public interface CrCreationFilter<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>> {
    /**
     * @param updatedInstance The new state of the model.
     * @param cr              The cr for the update.  May not be null.
     */
    void handle(TYPE updatedInstance, CR_TYPE cr);
}
