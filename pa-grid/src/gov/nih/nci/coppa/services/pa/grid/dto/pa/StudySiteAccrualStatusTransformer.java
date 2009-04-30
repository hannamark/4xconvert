package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.coppa.services.pa.StudySiteAccrualStatus;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;

/**
 * Transforms StudySiteAccrualStatus instances.
 *
 * @author Hugh Reinhart
 */
public final class StudySiteAccrualStatusTransformer implements
        Transformer<StudySiteAccrualStatus, StudySiteAccrualStatusDTO> {

    /**
     * Public singleton.
     */
    public static final StudySiteAccrualStatusTransformer INSTANCE = new StudySiteAccrualStatusTransformer();

    private StudySiteAccrualStatusTransformer() {
    };

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatusDTO toDto(StudySiteAccrualStatus input)
            throws DtoTransformException {
        if (input == null) {
            return null;
        }
        StudySiteAccrualStatusDTO result = new StudySiteAccrualStatusDTO();
        result.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        result.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        result.setStatusDate(TSTransformer.INSTANCE.toDto(input.getStatusDate()));
        result.setStudyParticipationIi(IITransformer.INSTANCE.toDto(input.getStudyParticipation()));
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualStatus toXml(StudySiteAccrualStatusDTO input)
            throws DtoTransformException {
        if (input == null) {
            return null;
        }
        StudySiteAccrualStatus result = new StudySiteAccrualStatus();
        result.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        result.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        result.setStatusDate(TSTransformer.INSTANCE.toXml(input.getStatusDate()));
        result.setStudyParticipation(IITransformer.INSTANCE.toXml(input.getStudyParticipationIi()));
        return result;
    }

}
