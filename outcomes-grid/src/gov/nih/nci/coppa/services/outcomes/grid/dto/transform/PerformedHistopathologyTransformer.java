package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedHistopathology;


/**
 * Transformer for PerformedHistopathology.
 * 
 * @author ludetc
 *
 */
public class PerformedHistopathologyTransformer extends 
    PerformedObservationResultTransformer<PerformedHistopathology, PerformedHistopathologyDto>
    implements Transformer<PerformedHistopathology, PerformedHistopathologyDto>
    {

    /**
     * Public singleton.
     */
    public static final PerformedHistopathologyTransformer INSTANCE = new PerformedHistopathologyTransformer();
    
    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto toDto(PerformedHistopathology input) throws DtoTransformException {
        PerformedHistopathologyDto dto = super.toDto(input);
        if (dto == null) {
            return null;
        }
      
        dto.setDescription(STTransformer.INSTANCE.toDto(input.getDescription()));
        dto.setGradeCode(CDTransformer.INSTANCE.toDto(input.getGradeCode()));
        
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathology toXml(PerformedHistopathologyDto input) throws DtoTransformException {
        PerformedHistopathology xml = super.toXml(input);
        if (xml == null) {
            return null;
        }
       
        xml.setDescription(STTransformer.INSTANCE.toXml(input.getDescription()));
        xml.setGradeCode(CDTransformer.INSTANCE.toXml(input.getGradeCode()));
        
        return xml;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedHistopathologyDto newDto() {
        return new PerformedHistopathologyDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PerformedHistopathology newXml() {
        return new PerformedHistopathology();
    }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathology[] createXmlArray(int size) throws DtoTransformException {
        return new PerformedHistopathology[size];
    }
    
}
