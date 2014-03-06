package gov.nih.nci.pa.domain;

import static org.junit.Assert.*;
import gov.nih.nci.pa.enums.AccrualChangeCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PatientEthnicityCode;
import gov.nih.nci.pa.enums.PatientGenderCode;
import gov.nih.nci.pa.enums.PatientRaceCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.SamplingMethodCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudySubtypeCode;
import gov.nih.nci.pa.enums.TimePerspectiveCode;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class DomainClasssesTest {
	
	@Test
	public void testDomianClass() {
		
		BatchFile bf =  new BatchFile();
		bf.setFileLocation("fileLocation");
		bf.setPassedValidation(true);
		bf.setProcessed(true);
		bf.setSubmitter(new RegistryUser());
		bf.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
		
		AccrualCollections ac = new AccrualCollections();
		ac.setBatchFile(bf);
		ac.setChangeCode(AccrualChangeCode.YES);
		ac.setNciNumber("NCInumber");
		ac.setPassedValidation(true);
		ac.setResults(null);
		ac.setTotalImports(1);
		assertNotNull(ac.getBatchFile());
		assertNotNull(ac.getChangeCode());
		assertNotNull(ac.getNciNumber());
		assertNotNull(ac.getTotalImports());
		assertNotNull(ac.isPassedValidation());
		assertNull(ac.getResults());
		
		bf.setAccrualCollections(Arrays.asList(ac));
		assertNotNull(bf.getAccrualCollections());
		assertNotNull(bf.getFileLocation());
		assertNotNull(bf.getSubmissionTypeCode());
		assertNotNull(bf.getSubmitter());
		assertNull(bf.getResults());
				
		AccrualDisease ad = new AccrualDisease();
		ad.setCodeSystem("ICD9");
		ad.setDiseaseCode("diseaseCode");
		ad.setDisplayName("disease displayName");
		ad.setPreferredName("disease preferredName");
		assertNotNull(ad.getCodeSystem());
		assertNotNull(ad.getDiseaseCode());
		assertNotNull(ad.getDisplayName());
		assertNotNull(ad.getPreferredName());
		assertNotNull(ad.getStudySubjects());
		
		FundingMechanism fm = new FundingMechanism();
		fm.setFundingMechanismCode("fundingMechanismCode");
		assertNotNull(fm.getFundingMechanismCode());
		
		InterventionalStudyProtocol isp = new InterventionalStudyProtocol();
		isp.setAllocationCode(AllocationCode.RANDOMIZED_CONTROLLED_TRIAL);
		isp.setBlindingRoleCodeSubject(BlindingRoleCode.SUBJECT);
		isp.setBlindingRoleCodeCaregiver(BlindingRoleCode.CAREGIVER);
		isp.setBlindingRoleCodeInvestigator(BlindingRoleCode.INVESTIGATOR);
		isp.setBlindingRoleCodeOutcome(BlindingRoleCode.OUTCOMES_ASSESSOR);
		isp.setBlindingSchemaCode(BlindingSchemaCode.SINGLE_BLIND);
		isp.setDesignConfigurationCode(DesignConfigurationCode.SINGLE_GROUP);
		isp.setNumberOfInterventionGroups(1);
		isp.setStudyClassificationCode(StudyClassificationCode.SAFETY);
		assertNotNull(isp.getAllocationCode());
		assertNotNull(isp.getBlindingRoleCodeCaregiver());
		assertNotNull(isp.getBlindingRoleCodeInvestigator());
		assertNotNull(isp.getBlindingRoleCodeOutcome());
		assertNotNull(isp.getBlindingRoleCodeSubject());
		assertNotNull(isp.getBlindingSchemaCode());
		assertNotNull(isp.getDesignConfigurationCode());
		assertNotNull(isp.getNumberOfInterventionGroups());
		assertNotNull(isp.getStudyClassificationCode());
		
		NonInterventionalStudyProtocol nisp = new NonInterventionalStudyProtocol();
		nisp.setBiospecimenDescription("biospecimenDescription");
		nisp.setBiospecimenRetentionCode(BiospecimenRetentionCode.SAMPLES_WITHOUT_DNA);
		nisp.setSamplingMethodCode(SamplingMethodCode.PROBABILITY_SAMPLE);
		nisp.setNumberOfGroups(1);
		nisp.setStudyModelOtherText("studyModelOtherText");
		nisp.setTimePerspectiveCode(TimePerspectiveCode.PROSPECTIVE);
		nisp.setTimePerspectiveOtherText("timePerspectiveOtherText");
		nisp.setStudyPopulationDescription("studyPopulationDescription");
		nisp.setStudySubtypeCode(StudySubtypeCode.ANCILLARY_CORRELATIVE);
		assertNotNull(nisp.getBiospecimenDescription());
		assertNotNull(nisp.getBiospecimenRetentionCode());
		assertNotNull(nisp.getSamplingMethodCode());
		assertNotNull(nisp.getNumberOfGroups());
		assertNotNull(nisp.getStudyModelOtherText());
		assertNotNull(nisp.getTimePerspectiveCode());
		assertNotNull(nisp.getTimePerspectiveOtherText());
		assertNotNull(nisp.getStudyPopulationDescription());
		assertNotNull(nisp.getStudySubtypeCode());
		
		Country c =  new Country();
        c.setName("United States");
        c.setAlpha2("US");
        c.setAlpha3("USA");
        
		Patient p = new Patient();
        p.setBirthDate(new Timestamp(new Date().getTime()));
        p.setCountry(c);
        p.setEthnicCode(PatientEthnicityCode.HISPANIC);
        p.setRaceCode(PatientRaceCode.AMERICAN_INDIAN.getName());
        p.setSexCode(PatientGenderCode.FEMALE);
        p.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        p.setZip("TX");
        assertNotNull(p.getBirthDate());
        assertNotNull(p.getCountry());
        assertNotNull(p.getEthnicCode());
        assertNotNull(p.getRaceCode());
        assertNotNull(p.getSexCode());
        assertNotNull(p.getStatusCode());
        assertNotNull(p.getZip());
        
		StudySubject subj = new StudySubject();
        subj.setDisease(ad);
        subj.setPatient(p);
        subj.setAssignedIdentifier("001");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(new StudyProtocol());
        subj.setStudySite(new StudySite());
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UNKNOWN);
        subj.setDateLastCreated(new Timestamp(new Date().getTime()));
        subj.setDeleteReason("deleteReason");
        subj.setRegistrationGroupId("registrationGroupId");
        subj.setSiteDisease(ad);
        assertNotNull(subj.getDeleteReason());
        assertNotNull(subj.getDisease());
        assertNotNull(subj.getAssignedIdentifier());
        assertNotNull(subj.getAssignedIdentifier());
        assertNotNull(subj.getPaymentMethodCode());
        assertNotNull(subj.getStatusCode());
        assertNotNull(subj.getStudyProtocol());
        assertNotNull(subj.getStudySite());
        assertNotNull(subj.getSubmissionTypeCode());
        assertNotNull(subj.getDateLastCreated());
        assertNotNull(subj.getDeleteReason());
        assertNotNull(subj.getRegistrationGroupId());
        assertNotNull(subj.getSiteDisease());
        
        StudyAlternateTitle alTitle = new StudyAlternateTitle();        
        alTitle.setCategory("Other");
        alTitle.setStudyProtocol(new StudyProtocol());
        alTitle.setDateLastCreated(new Timestamp(new Date().getTime()));
        alTitle.setDateLastUpdated(new Timestamp(new Date().getTime()));        
        assertNotNull(alTitle.getCategory());
        assertNotNull(alTitle.getDateLastCreated());
        assertNotNull(alTitle.getDateLastUpdated());
        assertNotNull(alTitle.getStudyProtocol());
        StudyAlternateTitle otherTitle = new StudyAlternateTitle();
        otherTitle.setCategory("Spelling/Formatting Correction");
        otherTitle.setAlternateTitle("Test1");
        assertFalse(alTitle.equals(null));      
        assertTrue(alTitle.equals(alTitle));
        assertFalse(alTitle.equals(subj));        
        assertFalse(alTitle.equals(otherTitle));
        alTitle.setAlternateTitle("Test");
        assertNotNull(alTitle.getAlternateTitle());
        assertFalse(alTitle.equals(otherTitle));
        otherTitle.setAlternateTitle("Test");
        assertFalse(alTitle.equals(otherTitle));
        otherTitle.setCategory("Other");
        assertTrue(alTitle.equals(otherTitle));
        assertTrue(alTitle.hashCode() == otherTitle.hashCode());
        alTitle.setCategory(null);
        assertFalse(alTitle.equals(otherTitle));
	}
}
