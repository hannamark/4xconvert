package gov.nih.nci.po.webservices.service.bo.filter;

import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.webservices.service.exception.ServiceException;

/**
 * @param <TYPE> The correlation type.
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class StatusValidatingRoleFilter<TYPE extends Correlation> implements RoleUpdateFilter<TYPE> {

    /**
     * Validates the status update, if any, present.
     *
     * If an invalid transition occurs (e.g. any update from PENDING), then a ServiceException is thrown.
     * @param currentInstance The current instance of the model.
     * @param updatedInstance The proposed state.
     */
    @Override
    public void handle(TYPE currentInstance, TYPE updatedInstance) {
         if (currentInstance.getStatus() != RoleStatus.ACTIVE && updatedInstance.getStatus() == RoleStatus.ACTIVE) {
            throw new ServiceException(
                    String.format(
                            "Illegal attempt to update status of %s %d from %s to %s",
                            currentInstance.getClass().getSimpleName(),
                            updatedInstance.getId(),
                            currentInstance.getStatus(),
                            updatedInstance.getStatus()
                    )
            );
        }
    }
}
