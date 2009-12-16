package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.SubmissionDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.TSTransformer;
import gov.nih.nci.coppa.services.outcomes.Submission;

/**
 * Transformer for Submission Object.
 * 
 * @author ludetc
 *
 */
public class SubmissionTransformer extends AbstractStudyTransformer<Submission, SubmissionDto> 
    implements Transformer<Submission, SubmissionDto> {

    /**
     * Public singleton.
     */
    public static final SubmissionTransformer INSTANCE = new SubmissionTransformer();
    
    /**
     * {@inheritDoc}
     */
    public Submission[] createXmlArray(int size) throws DtoTransformException {
       return new Submission[size];
    }

    /**
     * {@inheritDoc}
     */
    public SubmissionDto toDto(Submission input) throws DtoTransformException {
        SubmissionDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }
        dto.setCreateUser(STTransformer.INSTANCE.toDto(input.getCreateUser()));
        dto.setCutOffDate(TSTransformer.INSTANCE.toDto(input.getCutOffDate()));
        dto.setDescription(STTransformer.INSTANCE.toDto(input.getDescription()));
        dto.setLabel(STTransformer.INSTANCE.toDto(input.getLabel()));
        dto.setStatusCode(CDTransformer.INSTANCE.toDto(input.getStatusCode()));
        dto.setStatusDateRange(IVLTSTransformer.INSTANCE.toDto(input.getStatusDateRange()));
        dto.setSubmitUser(STTransformer.INSTANCE.toDto(input.getSubmitUser()));
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public Submission toXml(SubmissionDto input) throws DtoTransformException {
        Submission xml = super.toXml(input);
        if (xml == null) {
            return null;
        }
        xml.setCreateUser(STTransformer.INSTANCE.toXml(input.getCreateUser()));
        xml.setCutOffDate(TSTransformer.INSTANCE.toXml(input.getCutOffDate()));
        xml.setDescription(STTransformer.INSTANCE.toXml(input.getDescription()));
        xml.setLabel(STTransformer.INSTANCE.toXml(input.getLabel()));
        xml.setStatusCode(CDTransformer.INSTANCE.toXml(input.getStatusCode()));
        xml.setStatusDateRange(IVLTSTransformer.INSTANCE.toXml(input.getStatusDateRange()));
        xml.setSubmitUser(STTransformer.INSTANCE.toXml(input.getSubmitUser()));
        return xml;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected SubmissionDto newDto() {
        return new SubmissionDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Submission newXml() {
        return new Submission();
    }
}
