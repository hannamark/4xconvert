package gov.nih.nci.coppa.services.pa.grid.dto.pa;

import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IVLTSTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.pa.StudyOnhold;
import gov.nih.nci.pa.iso.dto.StudyOnholdDTO;

/**
 * Transforms StudyOnhold between XML and DTO representations.
 */
public final class StudyOnholdTransformer implements Transformer<StudyOnhold, StudyOnholdDTO> {

    /**
     * Public singleton.
     */
    public static final StudyOnholdTransformer INSTANCE = new StudyOnholdTransformer();

    private StudyOnholdTransformer() { }

    /**
     * {@inheritDoc}
     */
    public StudyOnholdDTO toDto(StudyOnhold xml) throws DtoTransformException {
        if (xml == null) {
            return null;
        }

        StudyOnholdDTO result = new StudyOnholdDTO();
        result.setIdentifier(IITransformer.INSTANCE.toDto(xml.getIdentifier()));
        result.setOnholdDate(IVLTSTransformer.INSTANCE.toDto(xml.getOnholdDate()));
        result.setOnholdReasonCode(CDTransformer.INSTANCE.toDto(xml.getOnholdReasonCode()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toDto(xml.getStudyProtocolIdentifier()));
        result.setOnholdReasonText(STTransformer.INSTANCE.toDto(xml.getOnholdReasonText()));

        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudyOnhold toXml(StudyOnholdDTO dto) throws DtoTransformException {
        if (dto == null) {
            return null;
        }

        StudyOnhold result = new StudyOnhold();
        result.setIdentifier(IITransformer.INSTANCE.toXml(dto.getIdentifier()));
        result.setOnholdDate(IVLTSTransformer.INSTANCE.toXml(dto.getOnholdDate()));
        result.setOnholdReasonCode(CDTransformer.INSTANCE.toXml(dto.getOnholdReasonCode()));
        result.setOnholdReasonText(STTransformer.INSTANCE.toXml(dto.getOnholdReasonText()));
        result.setStudyProtocolIdentifier(IITransformer.INSTANCE.toXml(dto.getStudyProtocolIdentifier()));

        return result;
    }

}
