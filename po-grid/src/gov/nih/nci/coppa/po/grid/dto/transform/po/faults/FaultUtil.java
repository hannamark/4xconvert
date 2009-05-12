package gov.nih.nci.coppa.po.grid.dto.transform.po.faults;

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.rmi.RemoteException;

/**
 * Utility to transform and throw Throwable instances.
 * 
 * @author smatyas
 * 
 */
public final class FaultUtil {

    private FaultUtil() {
    }

    /**
     * @param cause the underlying throwable to handle
     * @return the cause translated to a type of RemoteException
     */
    public static RemoteException reThrowRemote(Throwable cause) {
        try {
            if (cause instanceof NullifiedEntityException) {
                NullifiedEntityException e = (NullifiedEntityException) cause;
                return NullifiedEntityFaultTransformer.INSTANCE.toXml(e);
            } else if (cause instanceof NullifiedRoleException) {
                NullifiedRoleException e = (NullifiedRoleException) cause;
                return NullifiedRoleFaultTransformer.INSTANCE.toXml(e);
            } else if (cause instanceof EntityValidationException) {
                EntityValidationException e = (EntityValidationException) cause;
                return EntityValidationFaultTransformer.INSTANCE.toXml(e);
            } else if (cause instanceof TooManyResultsException) {
                TooManyResultsException e = (TooManyResultsException) cause;
                return TooManyResultsFaultTransformer.INSTANCE.toXml(e);
            } else if (cause instanceof RemoteException) { /* default */
                return (RemoteException) cause;
            } else {
                return new RemoteException("Unsupported exception, skipping fault transformation", cause);
            } 
        } catch (DtoTransformException e1) {
            return new RemoteException("Critical error while processing actual exception: " + e1);
        }
    }
}
