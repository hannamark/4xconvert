package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.List;
import java.util.Map;

/**
 * Wrapper class to call the OrganizationService remote EJB.
 */
public class InvokeOrganizationEjb implements OrganizationEntityServiceRemote {
    private final ServiceLocator locator = JNDIServiceLocator.getInstance();

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO org) {
        try {
            return locator.getOrganizationService().search(org);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationDTO getOrganization(Ii ii) throws NullifiedEntityException {
        try {
            return locator.getOrganizationService().getOrganization(ii);
        } catch (NullifiedEntityException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Ii createOrganization(OrganizationDTO org) throws EntityValidationException {
        try {
            return locator.getOrganizationService().createOrganization(org);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganization(OrganizationDTO proposedState) throws EntityValidationException {
        try {
            locator.getOrganizationService().updateOrganization(proposedState);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateOrganizationStatus(Ii targetOrg, Cd statusCode) throws EntityValidationException {
        try {
            locator.getOrganizationService().updateOrganizationStatus(targetOrg, statusCode);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String[]> validate(OrganizationDTO org) {
        try {
            return locator.getOrganizationService().validate(org);
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<OrganizationDTO> search(OrganizationDTO org, LimitOffset page) throws TooManyResultsException {
        try {
            return locator.getOrganizationService().search(org, page);
        } catch (TooManyResultsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }
}
