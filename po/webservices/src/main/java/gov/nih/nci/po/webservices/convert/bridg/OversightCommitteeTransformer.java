package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.OversightCommittee;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.po.data.convert.AddressConverter;
import gov.nih.nci.po.data.convert.PersistentObjectConverter;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.services.correlation.OversightCommitteeDTO;

import java.util.Iterator;

/**
 * Transforms OversightCommittee instances.
 */
public class OversightCommitteeTransformer
        extends AbstractTransformer<OversightCommittee, OversightCommitteeDTO>
        implements Transformer<OversightCommittee, OversightCommitteeDTO> {
    /**
     * Public singleton.
     */
    public static final OversightCommitteeTransformer INSTANCE = new OversightCommitteeTransformer();

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeDTO toDto(OversightCommittee input)
            throws DtoTransformException {
        if (input == null) {
            return null;
        }

        OversightCommitteeDTO dto = new OversightCommitteeDTO();
        dto.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
        dto.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        dto.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        dto.setTypeCode(CDTransformer.INSTANCE.toDto(input.getTypeCode()));


        Ii ii = null;
        Iterator<Ii> iter = null;

        if (dto.getIdentifier() != null) {
            iter = dto.getIdentifier().getItem().iterator();
        }

        if (iter != null && iter.hasNext()) {
            ii = dto.getIdentifier().getItem().iterator().next();

            gov.nih.nci.po.data.bo.OversightCommittee instance = PoRegistry.getInstance().getServiceLocator()
                    .getOversightCommitteeService().getById(Long.parseLong(ii.getExtension()));

            //patch postal address
            if (instance != null) {
                DSet<Ad> address =
                        new AddressConverter.SetConverter().convert(DSet.class, instance.getPostalAddresses());
                dto.setPostalAddress(address);

                //patch duplicateOf
                Ii duplicateOf =
                        new PersistentObjectConverter.PersistentOvCConverter().convert(
                                Ii.class, instance.getDuplicateOf()
                        );
                dto.setDuplicateOf(duplicateOf);
            }

        }




        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommittee toXml(OversightCommitteeDTO input)
            throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OversightCommittee xml = new OversightCommittee();
        xml.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        xml.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        xml.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        xml.setTypeCode(CDTransformer.INSTANCE.toXml(input.getTypeCode()));
        return xml;
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommittee[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new OversightCommittee[arg0];
    }

}
