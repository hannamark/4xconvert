package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.NCISpecificInformationServiceRemote;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;
import gov.nih.nci.pa.service.ProtocolServiceLocal;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockDiseaseConditionService;

/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator{
    
    private final DiseaseCondServiceLocal condServiceLocal = new MockDiseaseConditionService();

    public DiseaseCondServiceLocal getDiseaseConditionService() {
        
        return condServiceLocal;
    }

    public ProtocolOrganizationServiceRemote getProtocolOrganizationService() {
      
        return null;
    }

    public ProtocolServiceLocal getProtocolService() {
      
        return null;
    }


    
   public NCISpecificInformationServiceRemote getNCISpecificInformationService() {
        
        return null;
    }

}
