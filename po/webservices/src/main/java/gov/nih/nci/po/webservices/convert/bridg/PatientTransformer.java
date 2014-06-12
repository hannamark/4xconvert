package gov.nih.nci.po.webservices.convert.bridg;


import gov.nih.nci.coppa.po.Patient;
import gov.nih.nci.iso21090.grid.dto.transform.AbstractTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.DtoTransformException;
import gov.nih.nci.iso21090.grid.dto.transform.Transformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETADTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETIITransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.DSETTELTransformer;
import gov.nih.nci.iso21090.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.correlation.PatientDTO;

/**
 * Transforms ClinicalRearchStaff instances.
 */
public final class PatientTransformer
    extends AbstractTransformer<Patient, PatientDTO>
    implements Transformer<Patient, PatientDTO> {

    /**
     * Public singleton.
     */
    public static final PatientTransformer INSTANCE = new PatientTransformer();

    /**
     * {@inheritDoc}
     */
    public PatientDTO toDto(Patient input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        PatientDTO d = new PatientDTO();
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
    public Patient toXml(PatientDTO input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Patient d = new Patient();
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
    public Patient[] createXmlArray(int arg0)
            throws DtoTransformException {
        return new Patient[arg0];
    }
}
