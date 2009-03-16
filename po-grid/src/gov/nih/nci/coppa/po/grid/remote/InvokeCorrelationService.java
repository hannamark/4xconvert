package gov.nih.nci.coppa.po.grid.remote;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.util.List;
import java.util.Map;

public class InvokeCorrelationService<DTO extends PoDto> implements CorrelationService<DTO> {
    ServiceLocator locator = JNDIServiceLocator.getInstance();
    private final Class<DTO> type;

    public InvokeCorrelationService(Class<DTO> type) {
        this.type = type;
    }

    public Ii createCorrelation(DTO dto) throws EntityValidationException {
        try {
            Ii result = getLocator().getService(type).createCorrelation(dto);
            return result;
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public DTO getCorrelation(Ii id) throws NullifiedRoleException {
        try {
            DTO result = (DTO) getLocator().getService(type).getCorrelation(id);
            return result;
        } catch (NullifiedRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public List<DTO> getCorrelations(Ii[] ids) throws NullifiedRoleException {
        try {
            List<DTO> results = getLocator().getService(type).getCorrelations(ids);
            return results;
        } catch (NullifiedRoleException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public List<DTO> search(DTO dto) {
        try {
            List<DTO> results = getLocator().getService(type).search(dto);
            return results;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public void updateCorrelation(DTO proposedState) throws EntityValidationException {
        try {
            getLocator().getService(type).updateCorrelation(proposedState);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public void updateCorrelationStatus(Ii target, Cd status) throws EntityValidationException {
        try {
            getLocator().getService(type).updateCorrelationStatus(target, status);
        } catch (EntityValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    public Map<String, String[]> validate(DTO dto) {
        try {
            Map<String, String[]> results = getLocator().getService(type).validate(dto);
            return results;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    public ServiceLocator getLocator() {
        return locator;
    }

}
