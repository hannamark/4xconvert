package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.service.DiseaseCondServiceRemote;
import gov.nih.nci.pa.service.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.PAPersonServiceRemote;
import gov.nih.nci.pa.service.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockDiseaseConditionService;
import gov.nih.nci.service.MockStudyOverallStatusService;
import gov.nih.nci.service.MockStudyProtocolService;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {
    

    private final DiseaseCondServiceRemote condServiceLocal = new MockDiseaseConditionService();
    private final StudyProtocolServiceRemote studyProtocolService = new MockStudyProtocolService();
    private final StudyOverallStatusServiceRemote studyOverallStatusService = new MockStudyOverallStatusService();

    /**
     * @return condServiceLocal
     */
    public DiseaseCondServiceRemote getDiseaseConditionService() {
        return condServiceLocal;
    }

    /**
     * @return mock service
     */
    public StudyProtocolServiceRemote getStudyProtocolService() {
        return studyProtocolService;
    }

    /** 
     * return StudyOverallStatusServiceRemote
     */
    public StudyOverallStatusServiceRemote getStudyOverallStatusService() {
        return studyOverallStatusService;
    }

    /**
     * @return null
     */
    public PAOrganizationServiceRemote getPAOrganizationService() {
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
     * return StudyResourcingServiceRemote
     */
    public StudyResourcingServiceRemote getStudyResoucringService() {
        return null;
    }

    /**
     * return StudyRegulatoryAuthorityServiceRemote
     */
    public StudyRegulatoryAuthorityServiceRemote getStudyRegulatoryAuthorityService() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**
    *
    * @return OrganizationEntityServiceRemote
    */
    public  OrganizationEntityServiceRemote getPoOrganizationEntityService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getLookUpTableService()
     */
    public LookUpTableServiceRemote getLookUpTableService() {
        // TODO Auto-generated method stub
        return null;
    }
}
