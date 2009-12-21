package gov.nih.nci.coppa.services.outcomes.grid.dto.transform;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.AbstractTransformerTestBase;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.outcomes.PerformedImage;

public class PerformedImageTransformerTest extends 
AbstractTransformerTestBase<PerformedImageTransformer, PerformedImage, PerformedImageDto> {

    
    Ii imageId = new Ii();
    {
        imageId.setRoot("1.1.1");
        imageId.setIdentifierName("image name");
        imageId.setExtension("199");
    }
    Ii seriesId = new Ii();
    {
        seriesId.setRoot("1.2.1");
        seriesId.setIdentifierName("Series Name");
        seriesId.setExtension("299");
    }
    
    PerformedObservationResultTestHelper helper = new PerformedObservationResultTestHelper();
    
    @Override
    public PerformedImageDto makeDtoSimple() throws DtoTransformException {
        PerformedImageDto dto = new PerformedImageDto();
        helper.makeDtoSimple(dto);
        dto.setImageIdentifier(imageId);
        dto.setSeriesIdentifier(seriesId);
        return dto;
    }

    @Override
    public PerformedImage makeXmlSimple() throws DtoTransformException {
        PerformedImage xml = new PerformedImage();
        helper.makeXmlSimple(xml);
        xml.setImageIdentifier(IITransformer.INSTANCE.toXml(imageId));
        xml.setSeriesIdentifier(IITransformer.INSTANCE.toXml(seriesId));
        return xml;     
    }

    @Override
    public void verifyDtoSimple(PerformedImageDto dto) throws DtoTransformException {
        assertEquals(dto.getImageIdentifier().getExtension(), imageId.getExtension());
        assertEquals(dto.getSeriesIdentifier().getExtension(), seriesId.getExtension());
    }

    @Override
    public void verifyXmlSimple(PerformedImage xml) throws DtoTransformException {
        assertEquals(xml.getImageIdentifier().getExtension(), imageId.getExtension());
        assertEquals(xml.getSeriesIdentifier().getExtension(), seriesId.getExtension());
    }
}