/**
 *
 */
package gov.nih.nci.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Vrushali
 *
 */
public class MockStudyIndIdeService extends MockAbstractBaseIsoService <StudyIndldeDTO> implements StudyIndldeServiceLocal {
    static List<StudyIndldeDTO> list;
    static {
        list = new ArrayList<StudyIndldeDTO>();
        StudyIndldeDTO indIde = new StudyIndldeDTO();
        indIde.setIdentifier(IiConverter.convertToIi("1"));
        list.add(indIde);
    }

    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }


    public List<StudyIndldeDTO> getByStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNotNull(ii) && ii.getExtension().equals("2")) {
            return list;
        }
        if (PAUtil.isIiNotNull(ii) && ii.getExtension().equals("3")) {
            throw new PAException("test");
        }
        return new ArrayList<StudyIndldeDTO>();
    }


    @Override
    public StudyIndldeDTO get(Ii ii) throws PAException {
        StudyIndldeDTO dto = null;
        if(PAUtil.isIiNotNull(ii) && ii.getExtension().equals("1")) {
            dto = new StudyIndldeDTO();
            dto.setIdentifier(IiConverter.convertToIi(1L));
            dto.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));
            dto.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi(1L));
            dto.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
            dto.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
            dto.setNihInstHolderCode(CdConverter.convertToCd(NihInstituteCode.NCRR));
            dto.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IND));
            dto.setGrantorCode(CdConverter.convertToCd(GrantorCode.CDER));
            dto.setIndldeNumber(StConverter.convertToSt("123456"));
        }
        if(PAUtil.isIiNotNull(ii) && ii.getExtension().equals("3")) {
            throw new PAException("exceptionTest");
        }
        return dto;
    }

    @Override
    public void delete(Ii ii) throws PAException {
        if (PAUtil.isIiNotNull(ii) && ii.getExtension().equals("3")) {
            throw new PAException("test");
        }
    }
    @Override
    public StudyIndldeDTO create(StudyIndldeDTO dto) throws PAException {
        if (dto !=null && dto.getIndldeNumber().getValue().equals("exception")) {
            throw new PAException("create");
        }
        return new StudyIndldeDTO();
    }
    @Override
    public StudyIndldeDTO update(StudyIndldeDTO dto) throws PAException {
        if (dto !=null && dto.getIndldeNumber().equals("exception")) {
            throw new PAException("update");
        }
        return new StudyIndldeDTO();
    }

}
