package gov.nih.nci.coppa.services.pa.grid.dto.pa.faults;

import gov.nih.nci.coppa.services.grid.dto.transform.iso.DtoTransformException;
import gov.nih.nci.pa.service.PAException;

import java.rmi.RemoteException;

/**
 * See PO-927 for refactoring task.
 * This file is slightly modified from PO-GRID.  Need to incorporate from both services.
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
            if (cause instanceof PAException) {
                PAException e = (PAException) cause;
                return PAFaultTransformer.INSTANCE.toXml(e);
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
