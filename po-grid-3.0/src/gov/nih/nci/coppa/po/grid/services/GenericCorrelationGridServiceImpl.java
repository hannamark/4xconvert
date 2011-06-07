package gov.nih.nci.coppa.po.grid.services;

import gov.nih.nci.coppa.common.LimitOffset;
import gov.nih.nci.coppa.po.Id;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.grid.dto.transform.TransformerRegistry;
import gov.nih.nci.coppa.po.grid.dto.transform.po.StringMapTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.dto.transform.util.TransformUtils;
import gov.nih.nci.coppa.po.grid.remote.InvokeCorrelationService;
import gov.nih.nci.coppa.po.grid.remote.Utils;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdArrayTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IdTransformer;
import gov.nih.nci.services.CorrelationService;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author smatyas
 *
 * @param <DTO> represents the xml element type
 * @param <XML> represents the DTO (remote-ejb) type
 */
public class GenericCorrelationGridServiceImpl<DTO extends PoDto, XML extends Object> implements
        CorrelationGridService<DTO, XML> {

    private CorrelationService<DTO> service;
    private Class<XML> xmlType;
    private Class<DTO> dtoType;

    private CorrelationService<DTO> getService() {
        if (service == null) {
            service = new InvokeCorrelationService<DTO>(getDTOType());
        }
        return service;
    }

    /**
     * @param xmlType represents the xml element type
     * @param dtoType represents the DTO (remote-ejb) type
     */
    public GenericCorrelationGridServiceImpl(Class<XML> xmlType, Class<DTO> dtoType) {
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

    @SuppressWarnings("unchecked")
    private Transformer getTransformer() {
        return TransformerRegistry.INSTANCE.getTransformer(this.getDTOType());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Id create(XML object) throws RemoteException {
        try {
            DTO dto = (DTO) getTransformer().toDto(object);
            if (dto instanceof IdentifiedPersonDTO) {
                Utils.handleLegacyCTEPPersonRoot(((IdentifiedPersonDTO) dto).getAssignedId());
            }
            Ii ii = getService().createCorrelation(dto);
            return TransformUtils.convertToOldId(IdTransformer.INSTANCE.toXml(ii));
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML getById(Id id) throws RemoteException {
        try {
            Ii iiIso = IITransformer.INSTANCE.toDto(id);
            DTO dto = getService().getCorrelation(iiIso);
            return (XML) getTransformer().toXml(dto);
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML[] getByIds(Id[] id) throws RemoteException {
        try {
            Ii[] iis = IdArrayTransformer.INSTANCE.toDto(TransformUtils.convertToNewIds(id));
            List<DTO> dtos = getService().getCorrelations(iis);
            List<XML> results = new ArrayList<XML>();
            for (DTO dto : dtos) {
                XML xml = (XML) getTransformer().toXml(dto);
                results.add(xml);
            }
            return results.toArray((XML[]) Array.newInstance(getXMLType(), results.size()));
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML[] getByPlayerIds(Id[] pid) throws RemoteException {
        try {
            Ii[] iis = IdArrayTransformer.INSTANCE.toDto(TransformUtils.convertToNewIds(pid));
            List<DTO> dtos = getService().getCorrelationsByPlayerIds(iis);
            List<XML> results = new ArrayList<XML>();
            for (DTO dto : dtos) {
                XML xml = (XML) getTransformer().toXml(dto);
                results.add(xml);
            }
            XML[] array = results.toArray((XML[]) Array.newInstance(getXMLType(), results.size()));
            return array;
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public XML[] search(XML criteria) throws RemoteException {
        try {
            return this.query(criteria, LimitOffsetTransformer.INSTANCE.toXml(Utils.DEFAULT_PAGING));
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public void update(XML object) throws RemoteException {
        try {
            DTO dto = (DTO) getTransformer().toDto(object);
            if (dto instanceof IdentifiedPersonDTO) {
                Utils.handleLegacyCTEPPersonRoot(((IdentifiedPersonDTO) dto).getAssignedId());
            }
            getService().updateCorrelation(dto);
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void updateStatus(gov.nih.nci.coppa.po.Id targetId, gov.nih.nci.coppa.po.Cd statusCode)
            throws RemoteException {
        try {
            Ii iiDto = IdTransformer.INSTANCE.toDto(TransformUtils.convertToNewId(targetId));
            Cd cdDto = CDTransformer.INSTANCE.toDto(statusCode);
            getService().updateCorrelationStatus(iiDto, cdDto);
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public StringMap validate(XML object) throws RemoteException {
        try {
            DTO dto = (DTO) getTransformer().toDto(object);
            if (dto instanceof IdentifiedPersonDTO) {
                Utils.handleLegacyCTEPPersonRoot(((IdentifiedPersonDTO) dto).getAssignedId());
            }
            Map<String, String[]> map = getService().validate(dto);
            StringMap stringMap = StringMapTransformer.INSTANCE.toXml(map);
            return stringMap;
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public XML[] query(XML criteria, LimitOffset pageParams) throws RemoteException {
        try {
            gov.nih.nci.coppa.services.LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(pageParams);
            DTO dto = (DTO) getTransformer().toDto(criteria);
            boolean isLegacyCTEPPersonRoot = false;
            if (dto instanceof IdentifiedPersonDTO) {
                isLegacyCTEPPersonRoot = Utils.handleLegacyCTEPPersonRoot(((IdentifiedPersonDTO) dto).getAssignedId());
            }
            List<DTO> dtoResults = getService().search(dto, limitOffsetDTO);
            if (dtoResults == null) {
                return null;
            }
            XML[] xmlResults = (XML[]) Array.newInstance(criteria.getClass(), dtoResults.size());
            int i = 0;
            for (DTO dtoResult : dtoResults) {
                XML xmlResult = (XML) getTransformer().toXml(dtoResult);
                if (isLegacyCTEPPersonRoot && xmlResult instanceof IdentifiedPerson) {
                    Utils.handleLegacyCTEPPersonRoot(((IdentifiedPerson) xmlResult).getAssignedId());
                }
                xmlResults[i++] = xmlResult;
            }
            return xmlResults;
        } catch (Exception e) {
            throw FaultUtil.reThrowRemote(e);
        }
    }

}
