package gov.nih.nci.pa.test.util;

import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.DiseaseAlternameServiceRemote;
import gov.nih.nci.pa.service.DiseaseParentServiceRemote;
import gov.nih.nci.pa.service.DiseaseServiceRemote;
import gov.nih.nci.pa.service.DocumentServiceRemote;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.service.StudyContactServiceRemote;
import gov.nih.nci.pa.service.StudyDiseaseServiceRemote;
import gov.nih.nci.pa.service.StudyIndldeServiceRemote;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceRemote;
import gov.nih.nci.pa.service.StudyOverallStatusServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationContactServiceRemote;
import gov.nih.nci.pa.service.StudyParticipationServiceRemote;
import gov.nih.nci.pa.service.StudyProtocolServiceRemote;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceRemote;
import gov.nih.nci.pa.service.StudyResourcingServiceRemote;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceRemote;
import gov.nih.nci.pa.service.SubGroupsServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareFacilityServiceRemote;
import gov.nih.nci.pa.service.util.PAHealthCareProviderRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.PAPersonServiceRemote;
import gov.nih.nci.pa.service.util.PAResearchOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.service.MockStudyOverallStatusService;
import gov.nih.nci.service.MockStudyProtocolService;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;


/**
 * adapted from PO.
 * @author Harsha
 *
 */
public class MockServiceLocator implements ServiceLocator {
    
    private final StudyProtocolServiceRemote studyProtocolService = new MockStudyProtocolService();
    private final StudyOverallStatusServiceRemote studyOverallStatusService = new MockStudyOverallStatusService();

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
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getProtocolQueryService()
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getHealthCareFacilityService()
     */
    public PAHealthCareFacilityServiceRemote getPAHealthCareFacilityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudyParticipationService()
     */
    public StudyParticipationServiceRemote getStudyParticipationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getStudySiteAccrualStatusService()
     */
    public StudySiteAccrualStatusServiceRemote getStudySiteAccrualStatusService() {
        // TODO Auto-generated method stub
        return null;
    }
    /** 
     * return DocumentServiceRemote
     */
    public DocumentServiceRemote getDocumentService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPoHealthCareProverService()
     */
    public HealthCareFacilityCorrelationServiceRemote getPoHealthCareProverService() {
        // TODO Auto-generated method stub
        return null;
    }


    public StudyParticipationContactServiceRemote getStudyParticipationContactService() {
        // TODO Auto-generated method stub
        return null;
    }

    public PersonEntityServiceRemote getPoPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    public PAHealthCareProviderRemote getPAHealthCareProviderService() {
        // TODO Auto-generated method stub
        return null;
    }

    public HealthCareProviderCorrelationServiceRemote getPoPersonCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /** 
     * return SubGroupsServiceRemote
     */
    public SubGroupsServiceRemote getSubGroupsService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPAResearchOrganizationService()
     */
    public PAResearchOrganizationServiceRemote getPAResearchOrganizationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getInterventionAlternateNameService()
     */
    public InterventionAlternateNameServiceRemote getInterventionAlternateNameService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getInterventionService()
     */
    public InterventionServiceRemote getInterventionService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getPlannedActivityService()
     */
    public PlannedActivityServiceRemote getPlannedActivityService() {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyOutcomeMeasureServiceRemote getOutcomeMeasurService() {
        return null;
    }
    public ClinicalResearchStaffCorrelationServiceRemote getPoClinicalResearchStaffCorrelationService() {
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getArmService()
     */
    public ArmServiceRemote getArmService() {
        // TODO Auto-generated method stub
        return null;
    }

    public StudyIndldeServiceRemote getStudyIndldeService() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.util.ServiceLocator#getCTGovXmlGeneratorService()
     */
    public CTGovXmlGeneratorServiceRemote getCTGovXmlGeneratorService() {
        // TODO Auto-generated method stub
        return null;
    }

    public AbstractionCompletionServiceRemote getAbstractionCompletionService()
        throws PAException {
      // TODO Auto-generated method stub
      return null;
    }

    public DocumentWorkflowStatusServiceRemote getDocumentWorkflowStatusService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public DiseaseAlternameServiceRemote getDiseaseAlternameService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public DiseaseParentServiceRemote getDiseaseParentService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public DiseaseServiceRemote getDiseaseService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return
     */
    public StudyDiseaseServiceRemote getStudyDiseaseService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return StudyContact
     */
    public StudyContactServiceRemote getStudyContactService() {
        // TODO Auto-generated method stub
        return null;
    }

    public OrganizationalContactCorrelationServiceRemote getPoOrganizationalContactCorrelationService()
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }
}
