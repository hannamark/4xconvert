/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyOverallStatusConverterTest {
    private Session sess;
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset1();
        TestSchema.primeData();
        sess = TestSchema.getSession();
    }    

    @Test
    public void convertFromDomainToDTO() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setId(123L);
        bo.setStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL);
        bo.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2008"));
        bo.setStudyProtocol(sp);
        
        StudyOverallStatusDTO dto = StudyOverallStatusConverter.convertFromDomainToDTO(bo);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIi()));
        assertEquals(bo.getStatusCode().getCode(), dto.getStatusCode().getCode());
        assertEquals(bo.getStatusDate(), TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }
    
    @Test
    public void convertFromDTOToDomain() throws Exception {
        StudyProtocol sp = (StudyProtocol) sess.load(StudyProtocol.class, TestSchema.studyProtocolIds.get(0));
        StudyOverallStatusDTO dto = new StudyOverallStatusDTO();
        dto.setIi(IiConverter.convertToIi((Long) null));
        dto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.IN_REVIEW));
        dto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("7/11/2002")));
        dto.setStudyProtocolIi(IiConverter.convertToIi(sp.getId()));
        
        StudyOverallStatus bo = StudyOverallStatusConverter.convertFromDtoToDomain(dto);
        assertEquals(bo.getId(), IiConverter.convertToLong(dto.getIi()));
        assertEquals(bo.getStatusCode().getCode(), dto.getStatusCode().getCode());
        assertEquals(bo.getStatusDate(), TsConverter.convertToTimestamp(dto.getStatusDate()));
        assertEquals(bo.getStudyProtocol().getId(), IiConverter.convertToLong(dto.getStudyProtocolIi()));
    }
}
