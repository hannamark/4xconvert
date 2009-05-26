package gov.nih.nci.coppa.services.pa.grid;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.TransformerRegistry;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokePaServiceEjb;
import gov.nih.nci.pa.iso.dto.BaseDTO;
import gov.nih.nci.pa.service.BasePaService;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Generic service to handle crud methods using a generic invoker ejb.
 * @author mshestopalov
 *
 * @param <DTO> represents the xml element type
 * @param <XML> represents the DTO (remote-ejb) type
 */
public class GenericPaGridServiceImpl<DTO extends BaseDTO, XML extends Object> implements PaGridService<DTO, XML> {

    private BasePaService<DTO> service;
    private Class<XML> xmlType;
    private Class<DTO> dtoType;
    private static final Logger LOGGER = LogManager.getLogger(GenericPaGridServiceImpl.class);

    /**
     * Get xmlType.
     * @return xmlType
     */
    protected Class<XML> getXmlType() {
        return xmlType;
    }

    /**
     * Get dtoType.
     * @return dtoType;
     */
    protected Class<DTO> getDtoType() {
        return dtoType;
    }

    private BasePaService<DTO> getService() {
        if (service == null) {
            service = new InvokePaServiceEjb<DTO>(getDTOType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericPaGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
        this.xmlType = xmlType;
        this.dtoType = dtoType;
    }

    /**
     * {@inheritDoc}
     */
    public Class<XML> getXMLType() {
        return xmlType;
    }

    /**
     * {@inheritDoc}
     */
    public Class<DTO> getDTOType() {
        return dtoType;
    }

    /**
     * Get transformer.
     * @return transformer.
     */
    @SuppressWarnings("unchecked")
    protected Transformer getTransformer() {
        return TransformerRegistry.INSTANCE.getTransformer(this.getDTOType());
    }

    /**
     * {@inheritDoc}
     */
    public XML create(XML xml) throws RemoteException {
        try {
            DTO dto = getService().create((DTO) getTransformer().toDto(xml));
            return (XML) getTransformer().toXml(dto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void delete(Id id) throws RemoteException {
        try {
            getService().delete(IITransformer.INSTANCE.toDto(id));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public XML get(Id id) throws RemoteException {
        try {
            Ii iiIso = IITransformer.INSTANCE.toDto(id);
            DTO dto = (DTO) getService().get(iiIso);
            XML result = (XML) getTransformer().toXml(dto);
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public XML update(XML xml) throws RemoteException {
        try {
            DTO dto = getService().update((DTO) getTransformer().toDto(xml));
            return (XML) getTransformer().toXml(dto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }

    }
}
