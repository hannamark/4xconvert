package gov.nih.nci.accrual.web.util;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

public class MockPaDiseaseParentServiceBean implements DiseaseParentServiceRemote{

    /** mock data. */
    public static List<DiseaseParentDTO> dtos;

    static {
        dtos = new ArrayList<DiseaseParentDTO>();
        DiseaseParentDTO r = new DiseaseParentDTO();
        r.setIdentifier(IiConverter.convertToIi(1L));
        r.setParentDiseaseIdentifier(IiConverter.convertToIi(1L));
        r.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        r.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2008")));
        dtos.add(r);
        r = new DiseaseParentDTO();
        r.setIdentifier(IiConverter.convertToIi(2L));
        r.setParentDiseaseIdentifier(IiConverter.convertToIi(2L));
        r.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        r.setStatusDateRangeLow(TsConverter.convertToTs(PAUtil.dateStringToTimestamp("1/1/2008")));
        dtos.add(r);
    }
    
    public List<DiseaseParentDTO> getByChildDisease(Ii arg0) throws PAException {
     // TODO Auto-generated method stub
        return null;
    }

    public List<DiseaseParentDTO> getByChildDisease(Ii[] arg0)
            throws PAException {
        return dtos;
    }

    public List<DiseaseParentDTO> getByParentDisease(Ii arg0)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public DiseaseParentDTO create(DiseaseParentDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        
    }

    public DiseaseParentDTO get(Ii arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public DiseaseParentDTO update(DiseaseParentDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    public void validate(DiseaseParentDTO arg0) throws PAException {
        // TODO Auto-generated method stub
        
    }

}
