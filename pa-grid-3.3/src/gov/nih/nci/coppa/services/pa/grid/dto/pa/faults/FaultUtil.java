package gov.nih.nci.coppa.services.pa.grid.dto.pa.faults;

import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.EntityValidationFaultTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.NullifiedEntityFaultTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.NullifiedRoleFaultTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.TooManyResultsFaultTransformer;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.rmi.RemoteException;

/**
 * See PO-927 for refactoring task.
 * This file is slightly modified from PO-GRID.  Need to incorporate from both services.
 */
public final class FaultUtil {

    private FaultUtil() {
    }

    /**
     * @param input the underlying throwable to handle
     * @return the cause translated to a type of RemoteException
     */
    public static RemoteException reThrowRemote(Throwable input) {
        Throwable cause = input;
        if (cause instanceof PAException && cause.getCause() != null) {
            cause = input.getCause();
        }
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
            } else if (input instanceof PAException) {
                PAException e = (PAException) input;
                return PAFaultTransformer.INSTANCE.toXml(e);
            } else if (input instanceof RemoteException) { /* default */
                return (RemoteException) input;
            } else {
                return new RemoteException("Unsupported exception, skipping fault transformation", input);
            }
        } catch (DtoTransformException e1) {
            return new RemoteException("Critical error while processing actual exception: " + e1);
        }
    }
}
