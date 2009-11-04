package gov.nih.nci.coppa.services.pa.grid.remote;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.remote.InvokeCoppaServiceException;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.service.BasePaService;
import gov.nih.nci.pa.service.PAException;

/**
 * Generic invoke service.
 * @param <DTO> pa DTO type
 */
public class InvokePaServiceEjb<DTO extends BaseDTO> implements BasePaService<DTO> {

    private final Class<DTO> type;

    /**
     * Const.
     * @param type correlation DTO class
     */
    public InvokePaServiceEjb(Class<DTO> type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public DTO create(DTO dto) throws PAException {
        try {
            DTO result = (DTO) getLocator().getBasePaService(type).create(dto);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Ii id) throws PAException {
        try {
            getLocator().getBasePaService(type).delete(id);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public DTO get(Ii id) throws PAException {
        try {
            DTO result = (DTO) getLocator().getBasePaService(type).get(id);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public DTO update(DTO dto) throws PAException {
        try {
            DTO result = (DTO) getLocator().getBasePaService(type).update(dto);
            return result;
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void validate(DTO dto) throws PAException {
        try {
            getLocator().getBasePaService(type).validate(dto);
        } catch (PAException pae) {
            throw pae;
        } catch (Exception e) {
            throw new InvokeCoppaServiceException(e.toString(), e);
        }
    };

    /**
     * {@inheritDoc}
     * @throws Exception 
     */
    public ServiceLocator getLocator() throws Exception {
        return GridSecurityJNDIServiceLocator.newInstance();
    }

    /**
     * Get type.
     * @return type.
     */
    protected Class<DTO> getType() {
        return type;
    }

}
