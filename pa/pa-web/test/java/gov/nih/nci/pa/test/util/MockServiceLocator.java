package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.service.DiseaseCondServiceLocal;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;

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
    public PAOrganizationServiceRemote getPAOrganizationService() {
        return null;
    }

    /**
     * @return null
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return null;
    }


   /**
   *
   * @return PAPersonServiceRemote
   */
   public PAPersonServiceRemote getPAPersonService() {
       return null;
   }
   
   /**
    * @return RegulatoryInformationServiceRemote
    */
   public RegulatoryInformationServiceRemote getRegulatoryInformationService() {
		// TODO Auto-generated method stub
		return null;
	}
   
    /** 
     * return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return null;
    }

    /** 
     * return StudyResourcingServiceRemote
     */
    public StudyResourcingServiceRemote getStudyResoucringService() {
        return null;
    }

    
}
