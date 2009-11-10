package gov.nih.nci.accrual.accweb.util;

import gov.nih.nci.accrual.dto.util.POPatientDTO;
import gov.nih.nci.accrual.service.util.PatientServiceRemote;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.util.IiConverter;

import java.rmi.RemoteException;

public class MockPaPatientServiceBean  implements PatientServiceRemote {

    public POPatientDTO create(POPatientDTO arg0) throws RemoteException {
        POPatientDTO dto = new POPatientDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPlayerIdentifier(IiConverter.convertToIi("PT01"));
        return dto;
    }

    public POPatientDTO get(Ii arg0) throws RemoteException {
        POPatientDTO dto = new POPatientDTO();
        return dto;
    }

    public POPatientDTO update(POPatientDTO arg0) throws RemoteException {
        POPatientDTO dto = new POPatientDTO();
        dto.setIdentifier(IiConverter.convertToIi("1"));
        dto.setPlayerIdentifier(IiConverter.convertToIi("PT01"));
        return dto;
    }

}
