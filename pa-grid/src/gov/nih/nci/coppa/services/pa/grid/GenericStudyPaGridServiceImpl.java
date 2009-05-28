package gov.nih.nci.coppa.services.pa.grid;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyPaServiceEjb;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.StudyPaService;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Generic service to handle StudyProtocol ref methods using a generic StudyProtocol ref
 * methods invoker ejb.
 * @author mshestopalov
 *
 * @param <DTO> represents the xml element type
 * @param <XML> represents the DTO (remote-ejb) type
 */

public class GenericStudyPaGridServiceImpl<DTO extends StudyDTO, XML extends Object>
    extends GenericPaGridServiceImpl<DTO, XML>
    implements StudyPaGridService<DTO, XML> {
    private static final Logger LOGGER = LogManager.getLogger(GenericStudyPaGridServiceImpl.class);
    private StudyPaService<DTO> service;

    private StudyPaService<DTO> getService() {
        if (service == null) {
            service = new InvokeStudyPaServiceEjb<DTO>(getDTOType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericStudyPaGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
        super(xmlType, dtoType);
    }

    /**
     * {@inheritDoc}
     */
    public void copy(Id from, Id to) throws RemoteException {
        try {
            Ii fromId = IITransformer.INSTANCE.toDto(from);
            Ii toId = IITransformer.INSTANCE.toDto(to);
            getService().copy(fromId, toId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML[] getByStudyProtocol(Id id) throws RemoteException {
        XML[] xmls = null;
        try {
            Ii iiIso = IITransformer.INSTANCE.toDto(id);
            List<DTO> dtos = (List<DTO>) getService().getByStudyProtocol(iiIso);
            xmls = (XML[]) getTransformer().convert(dtos);
            return xmls;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML[] getCurrentByStudyProtocol(Id id) throws RemoteException {
        XML[] xmls = null;
        try {
            Ii iiIso = IITransformer.INSTANCE.toDto(id);
            List<DTO> dtos = (List<DTO>) getService().getCurrentByStudyProtocol(iiIso);
            xmls = (XML[]) getTransformer().convert(dtos);
            return xmls;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }
}
