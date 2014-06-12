package gov.nih.nci.po.webservices.convert.bridg;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

/**
 * Transforms ClinicalRearchStaff instances.
 */
public final class ClinicalResearchStaffTransformer
    extends AbstractTransformer<ClinicalResearchStaff, ClinicalResearchStaffDTO>
    implements Transformer<ClinicalResearchStaff, ClinicalResearchStaffDTO> {

    /**
     * Public singleton.
     */
    public static final ClinicalResearchStaffTransformer INSTANCE = new ClinicalResearchStaffTransformer();

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffDTO toDto(ClinicalResearchStaff input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ClinicalResearchStaffDTO d = new ClinicalResearchStaffDTO();
        d.setIdentifier(DSETIITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        return d;

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public ClinicalResearchStaff toXml(ClinicalResearchStaffDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ClinicalResearchStaff d = new ClinicalResearchStaff();
        d.setIdentifier(DSETIITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setTelecomAddress(DSETTELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        return d;
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaff[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new ClinicalResearchStaff[arg0];
    }
}
