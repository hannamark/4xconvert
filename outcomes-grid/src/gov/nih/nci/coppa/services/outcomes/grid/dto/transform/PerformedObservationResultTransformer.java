package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.PQTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResult;
import gov.nih.nci.coppa.services.outcomes.PerformedObservationResultType;

/**
 * Transformer for PerformedObservationResult.
 * 
 * @author ludetc
 *
 * @param <POT> xml
 * @param <Pot> dto
 */
public class PerformedObservationResultTransformer
    <POT extends PerformedObservationResultType, Pot extends PerformedObservationResultDto>
    extends AbstractStudyTransformer<POT, Pot> 
    implements Transformer<POT, Pot> {
    
    /**
     * Public singleton.
     */
    public static final PerformedObservationResultTransformer
        <PerformedObservationResult, PerformedObservationResultDto> INSTANCE 
        = new PerformedObservationResultTransformer<PerformedObservationResult, PerformedObservationResultDto>();
    
    /**
     * {@inheritDoc}
     */
    public Pot toDto(POT input) throws DtoTransformException {
        Pot dto = super.toDto(input);
        if (dto == null) {
            return null;
        }
      
        dto.setPerformedObservationIdentifier(IITransformer.INSTANCE.toDto(input.getPerformedObservationIdentifier()));
        dto.setResultCode(CDTransformer.INSTANCE.toDto(input.getResultCode()));
        dto.setResultCodeModifiedText(STTransformer.INSTANCE.toDto(input.getResultCodeModifiedText()));
        dto.setResultDateRange(IVLTSTransformer.INSTANCE.toDto(input.getResultDateRange()));
        dto.setResultIndicator(BLTransformer.INSTANCE.toDto(input.getResultIndicator()));
        dto.setResultQuantity(PQTransformer.INSTANCE.toDto(input.getResultQuantity()));
        dto.setResultText(STTransformer.INSTANCE.toDto(input.getResultText()));
        dto.setTypeCode(CDTransformer.INSTANCE.toDto(input.getTypeCode()));
        dto.setUnitOfMeasureCode(CDTransformer.INSTANCE.toDto(input.getUnitOfMeasureCode()));
        
        return dto;
    }

    /**
     * {@inheritDoc}
     */
    public POT toXml(Pot input) throws DtoTransformException {
        POT xml = super.toXml(input);
        if (xml == null) {
            return null;
        }
       
        xml.setPerformedObservationIdentifier(IITransformer.INSTANCE.toXml(input.getPerformedObservationIdentifier()));
        xml.setResultCode(CDTransformer.INSTANCE.toXml(input.getResultCode()));
        xml.setResultCodeModifiedText(STTransformer.INSTANCE.toXml(input.getResultCodeModifiedText()));
        xml.setResultDateRange(IVLTSTransformer.INSTANCE.toXml(input.getResultDateRange()));
        xml.setResultIndicator(BLTransformer.INSTANCE.toXml(input.getResultIndicator()));
        xml.setResultQuantity(PQTransformer.INSTANCE.toXml(input.getResultQuantity()));
        xml.setResultText(STTransformer.INSTANCE.toXml(input.getResultText()));
        xml.setTypeCode(CDTransformer.INSTANCE.toXml(input.getTypeCode()));
        xml.setUnitOfMeasureCode(CDTransformer.INSTANCE.toXml(input.getUnitOfMeasureCode()));
        
        return xml;
    }

    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Pot newDto() {
        return (Pot) new PerformedObservationResultDto();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected POT newXml() {
        return (POT) new PerformedObservationResult();
    }

    /**
     * {@inheritDoc}
     */
    public POT[] createXmlArray(int size) throws DtoTransformException {
        return (POT[]) new PerformedObservationResult[size];
    }
}
