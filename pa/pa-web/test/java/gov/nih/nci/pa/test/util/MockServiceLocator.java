package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.NCISpecificInformationServiceRemote;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.ProtocolOrganizationServiceRemote;

import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockDiseaseConditionService;

/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {
    
    private final DiseaseCondServiceLocal condServiceLocal = new MockDiseaseConditionService();

    /**
     * @return condServiceLocal
     */
    public DiseaseCondServiceLocal getDiseaseConditionService() {
        return condServiceLocal;
    }

    /**
     * @return null
     */
    public ProtocolOrganizationServiceRemote getProtocolOrganizationService() {
        return null;
    }

    /**
     * @return null
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return null;
    }


    /**
     * @return null
     */
   public NCISpecificInformationServiceRemote getNCISpecificInformationService() {
        return null;
    }

   /**
   *
   * @return PAPersonServiceRemote
   */
   public PAPersonServiceRemote getPAPersonService() {
       return null;
   }

}
