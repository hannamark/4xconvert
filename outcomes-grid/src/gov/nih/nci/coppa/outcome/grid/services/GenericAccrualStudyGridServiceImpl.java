package gov.nih.nci.coppa.outcome.grid.services;

import gov.nih.nci.accrual.service.BaseAccrualStudyService;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.Id;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeAccrualStudyServiceEjb;
import gov.nih.nci.pa.iso.dto.StudyDTO;

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

public class GenericAccrualStudyGridServiceImpl<DTO extends StudyDTO, XML extends Object> extends
        GenericAccrualGridServiceImpl<DTO, XML> implements AccrualStudyGridService<DTO, XML> {

    private static final Logger LOGGER = LogManager.getLogger(GenericAccrualStudyGridServiceImpl.class);
    private BaseAccrualStudyService<DTO> service;

    private BaseAccrualStudyService<DTO> getService() {
        if (service == null) {
            service = new InvokeAccrualStudyServiceEjb<DTO>(getDtoType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericAccrualStudyGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
        super(xmlType, dtoType);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "cast" })
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

}
