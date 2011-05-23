package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyCheckout;
import gov.nih.nci.pa.enums.CheckOutType;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

public class StudyCheckoutConverterTest extends
        AbstractConverterTest<StudyCheckoutConverter, StudyCheckoutDTO, StudyCheckout> {

    @Override
    public StudyCheckout makeBo() {
        StudyCheckout bo = new StudyCheckout();
        bo.setId(ID);
        bo.setStudyProtocol(getStudyProtocol());
        bo.setCheckOutType(CheckOutType.ADMINISTRATIVE);
        bo.setUserIdentifier("Test");
        return bo;
    }

    @Override
    public StudyCheckoutDTO makeDto() {
        StudyCheckoutDTO dto = new StudyCheckoutDTO();
        dto.setIdentifier(IiConverter.convertToIi(ID));
        dto.setCheckOutTypeCode(CdConverter.convertStringToCd(CheckOutType.ADMINISTRATIVE.getCode()));
        dto.setUserIdentifier(StConverter.convertToSt("Test"));
        dto.setStudyProtocolIdentifier(IiConverter.convertToIi(STUDY_PROTOCOL_ID));
        return dto;
    }

    @Override
    public void verifyBo(StudyCheckout bo) {
        assertEquals(ID, bo.getId());
        assertEquals(CheckOutType.ADMINISTRATIVE, bo.getCheckOutType());
        assertEquals("Test", bo.getUserIdentifier());
        assertEquals(STUDY_PROTOCOL_ID, bo.getStudyProtocol().getId());
    }

    @Override
    public void verifyDto(StudyCheckoutDTO dto) {
        assertEquals(ID, IiConverter.convertToLong(dto.getIdentifier()));
        assertEquals(CheckOutType.ADMINISTRATIVE.getCode(), CdConverter.convertCdToString(dto.getCheckOutTypeCode()));
        assertEquals("Test", dto.getUserIdentifier().getValue());
        assertEquals(STUDY_PROTOCOL_ID, IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
    }

}
