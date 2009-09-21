package gov.nih.nci.coppa.services.pa.grid;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.Id;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeStudyCurrentPaServiceEjb;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.service.StudyCurrentPaService;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Generic service to handle StudyProtocol ref methods using a generic StudyProtocol ref methods invoker ejb.
 *
 * @author Steve Lustbader
 *
 * @param <DTO> represents the xml element type
 * @param <XML> represents the DTO (remote-ejb) type
 */

public class GenericStudyCurrentPaGridServiceImpl<DTO extends StudyDTO, XML extends Object> extends
        GenericStudyPaGridServiceImpl<DTO, XML> implements StudyCurrentPaGridService<DTO, XML> {

    private static final Logger LOGGER = LogManager.getLogger(GenericStudyCurrentPaGridServiceImpl.class);
    private StudyCurrentPaService<DTO> service;

    private StudyCurrentPaService<DTO> getService() {
        if (service == null) {
            service = new InvokeStudyCurrentPaServiceEjb<DTO>(getDTOType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericStudyCurrentPaGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
        super(xmlType, dtoType);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "cast" })
    public XML getCurrentByStudyProtocol(Id id) throws RemoteException {
        try {
            Ii iiIso = IITransformer.INSTANCE.toDto(id);
            DTO dto = (DTO) getService().getCurrentByStudyProtocol(iiIso);
            return (XML) getTransformer().toXml(dto);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw FaultUtil.reThrowRemote(e);
        }
    }

}
