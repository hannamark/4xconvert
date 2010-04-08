package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.OrganizationalContact;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETCDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

import java.util.HashSet;

/**
 * Transforms OrganizationalContact instances.
 */
public final class OrganizationalContactTransformer
    extends AbstractTransformer<OrganizationalContact, OrganizationalContactDTO>
    implements Transformer<OrganizationalContact, OrganizationalContactDTO> {
      /**
     * Public singleton.
     */
    public static final OrganizationalContactTransformer INSTANCE = new OrganizationalContactTransformer();

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactDTO toDto(OrganizationalContact input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationalContactDTO dto = new OrganizationalContactDTO();
        dto.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
        dto.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        dto.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
        dto.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        dto.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        dto.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        dto.setTypeCode(getCdFromDSet(DSETCDTransformer.INSTANCE.toDto(input.getTypeCode())));
        dto.setTitle(STTransformer.INSTANCE.toDto(input.getTitle()));

        return dto;
    }
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public OrganizationalContact toXml(OrganizationalContactDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        OrganizationalContact xml = new OrganizationalContact();
        xml.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        xml.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        xml.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        xml.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        xml.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        xml.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        xml.setTypeCode(DSETCDTransformer.INSTANCE.toXml(convertToDSetOfCd(input.getTypeCode())));
        xml.setTitle(STTransformer.INSTANCE.toXml(input.getTitle()));
        return xml;
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContact[] createXmlArray(int size) throws DtoTransformException {
        return new OrganizationalContact[size];
    }

    private DSet<Cd> convertToDSetOfCd(Cd cd) {
        DSet<Cd> cds = new DSet<Cd>();
        cds.setItem(new HashSet<Cd>());
        cds.getItem().add(cd);
        return cds;
    }

    private Cd getCdFromDSet(DSet<Cd> cds) {
        Cd cd = new Cd();
        if (cds != null && cds.getItem() != null) {
            cd = cds.getItem().iterator().next();
        } else {
            cd.setNullFlavor(NullFlavor.NI);
        }
        return cd;
    }
}
