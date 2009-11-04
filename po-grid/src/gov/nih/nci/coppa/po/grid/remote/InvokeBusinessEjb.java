package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.services.BusinessServiceRemote;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

/**
 * Wrapper to call remote Business EJB.
 */
public class InvokeBusinessEjb implements BusinessServiceRemote {

    /**
     * {@inheritDoc}
     */
    public CorrelationNodeDTO getCorrelationByIdWithEntities(Ii id, boolean players, boolean scopers)
            throws NullifiedRoleException {
        try {
            return GridSecurityJNDIServiceLocator
                .newInstance().getBusinessService().getCorrelationByIdWithEntities(id, players, scopers);
        } catch (NullifiedRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public CorrelationNodeDTO[] getCorrelationsByIdsWithEntities(Ii[] ids, boolean players, boolean scopers)
            throws NullifiedRoleException {
        try {
            return GridSecurityJNDIServiceLocator
                .newInstance().getBusinessService().getCorrelationsByIdsWithEntities(ids, players, scopers);
        } catch (NullifiedRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public CorrelationNodeDTO[] getCorrelationsByPlayerIdsWithEntities(Cd type,
            Ii[] ids, boolean players, boolean scopers)
            throws NullifiedRoleException {
        try {
            return GridSecurityJNDIServiceLocator
                .newInstance().getBusinessService().getCorrelationsByPlayerIdsWithEntities(type, ids, players, scopers);
        } catch (NullifiedRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public EntityNodeDto getEntityByIdWithCorrelations(Ii id,
            Cd[] players, Cd[] scopers) throws NullifiedEntityException {
        try {
            return GridSecurityJNDIServiceLocator
                .newInstance().getBusinessService().getEntityByIdWithCorrelations(id, players, scopers);
        } catch (NullifiedEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

}
