package gov.nih.nci.coppa.outcome.grid.services;

import gov.nih.nci.accrual.service.BaseAccrualService;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.Id;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.TransformerRegistry;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeAccrualServiceEjb;
import gov.nih.nci.pa.iso.dto.BaseDTO;

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
public class GenericAccrualGridServiceImpl<DTO extends BaseDTO, XML extends Object> implements AccrualGridService<DTO, XML> {

    private BaseAccrualService<DTO> service;
    private Class<XML> xmlType;
    private Class<DTO> dtoType;
    private static final Logger LOGGER = LogManager.getLogger(GenericAccrualGridServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    protected Class<XML> getXmlType() {
        return xmlType;
    }

    /**
     * {@inheritDoc}
     */
    protected Class<DTO> getDtoType() {
        return dtoType;
    }

    private BaseAccrualService<DTO> getService() {
        if (service == null) {
            service = new InvokeAccrualServiceEjb<DTO>(getDtoType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericAccrualGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
        this.xmlType = xmlType;
        this.dtoType = dtoType;
    }

    /**
     * Get transformer.
     * @return transformer.
     */
    @SuppressWarnings("unchecked")
    protected Transformer getTransformer() {
        return TransformerRegistry.INSTANCE.getTransformer(this.getDtoType());
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
