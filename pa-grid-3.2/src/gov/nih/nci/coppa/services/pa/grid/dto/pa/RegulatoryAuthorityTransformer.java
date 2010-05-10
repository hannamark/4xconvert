package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.pa.RegulatoryAuthority;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;

/**
 * Transforms RegulatoryAuthority between XML and DTO representations.
 */
public final class RegulatoryAuthorityTransformer
    extends AbstractTransformer<RegulatoryAuthority, RegulatoryAuthorityDTO>
    implements Transformer<RegulatoryAuthority, RegulatoryAuthorityDTO> {

    /**
     * Public singleton.
     */
    public static final RegulatoryAuthorityTransformer INSTANCE = new RegulatoryAuthorityTransformer();

    private RegulatoryAuthorityTransformer() { }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthorityDTO toDto(RegulatoryAuthority xml) throws DtoTransformException {
        if (xml == null) {
            return null;
        }

        RegulatoryAuthorityDTO result = new RegulatoryAuthorityDTO();
        result.setIdentifier(IITransformer.INSTANCE.toDto(xml.getIdentifier()));
        result.setCountryIdentifier(IITransformer.INSTANCE.toDto(xml.getCountryIdentifier()));
        result.setAuthorityName(STTransformer.INSTANCE.toDto(xml.getAuthorityName()));

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthority toXml(RegulatoryAuthorityDTO dto) throws DtoTransformException {
        if (dto == null) {
            return null;
        }

        RegulatoryAuthority result = new RegulatoryAuthority();
        result.setIdentifier(IITransformer.INSTANCE.toXml(dto.getIdentifier()));
        result.setAuthorityName(STTransformer.INSTANCE.toXml(dto.getAuthorityName()));
        result.setCountryIdentifier(IITransformer.INSTANCE.toXml(dto.getCountryIdentifier()));

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public RegulatoryAuthority[] createXmlArray(int arg0) throws DtoTransformException {
        return new RegulatoryAuthority[arg0];
    }

}
