package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class EnumTest {
	@Test
	public void test()
	{
		testMethods(AccrualReportingMethodCode.class);
		testMethods(ActiveInactiveCode.class);
		testMethods(ActiveInactivePendingCode.class);
		testMethods(ActivityCategoryCode.class);
		testMethods(ActivitySubcategoryCode.class);
		testMethods(ActualAnticipatedTypeCode.class);
		testMethods(AllocationCode.class);
		testMethods(ArmTypeCode.class);
		testMethods(BiospecimenRetentionCode.class);
		testMethods(BlindingRoleCode.class);
		testMethods(BlindingSchemaCode.class);
		testMethods(DesignConfigurationCode.class);
		testMethods(DocumentTypeCode.class);
		testMethods(DocumentWorkflowStatusCode.class);
		testMethods(EligibleGenderCode.class);
		testMethods(ExpandedAccessStatusCode.class);
		testMethods(GrantorCode.class);
		testMethods(HolderTypeCode.class);
		testMethods(IndldeTypeCode.class);
		testMethods(InterventionTypeCode.class);
		testMethods(MilestoneCode.class);
		testMethods(NciDivisionProgramCode.class);
		testMethods(NihInstHolderCode.class);
		testMethods(NihInstituteCode.class);
		testMethods(PhaseCode.class);
		testMethods(PrimaryPurposeCode.class);
		testMethods(RecruitmentStatusCode.class);
		testMethods(ResponsibilityCode.class);
		testMethods(ReviewBoardApprovalStatusCode.class);
		testMethods(StatusCode.class);
		testMethods(SamplingMethodCode.class);
		testMethods(StudyClassificationCode.class);
		testMethods(StudyContactRoleCode.class);
		testMethods(StudyModelCode.class);
		testMethods(StudyParticipationContactRoleCode.class);
		testMethods(StudyParticipationFunctionalCode.class);
		testMethods(StudyTypeCode.class);
		testMethods(SummaryFourFundingCategoryCode.class);
		testMethods(TimePerspectiveCode.class);
		testMethods(UnitsCode.class);
		testMethods(USStateCode.class);
		testMethods(StudyRelationshipTypeCode.class);
	}
	
	
	 public <C, T extends CodedEnum<C>> void testMethods(Class<T> clazz){
		
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.AccrualReportingMethodCode")){			
			 String[] x = AccrualReportingMethodCode.getDisplayNames();
			 assertEquals(AccrualReportingMethodCode.values().length, x.length);
			 assertEquals("ABBREVIATED", AccrualReportingMethodCode.ABBREVIATED.getName());
			 assertEquals("Abbreviated",AccrualReportingMethodCode.ABBREVIATED.getCode()); 
					 
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ActiveInactiveCode")){			
			 String[] x = ActiveInactiveCode.getDisplayNames();
		     assertEquals(ActiveInactiveCode.values().length, x.length);
		     assertEquals("ACTIVE", ActiveInactiveCode.ACTIVE.getName());
		     assertEquals("Active", ActiveInactiveCode.ACTIVE.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ActiveInactivePendingCode")){			
			 String[] x = ActiveInactivePendingCode.getDisplayNames();
		        assertEquals(ActiveInactivePendingCode.values().length, x.length);
		        assertEquals("ACTIVE", ActiveInactivePendingCode.ACTIVE.getName());
		        assertEquals("Active", ActiveInactivePendingCode.ACTIVE.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ActivityCategoryCode")){			
			 String[] x = ActivityCategoryCode.getDisplayNames();
		        assertEquals(ActivityCategoryCode.values().length, x.length);
		        assertEquals("OTHER", ActivityCategoryCode.OTHER.getName());
		        assertEquals("Other", ActivityCategoryCode.OTHER.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ActivitySubcategoryCode")){			
			 String[] x = ActivitySubcategoryCode.getDisplayNames();
		        assertEquals(ActivitySubcategoryCode.values().length, x.length);
		        assertEquals("OTHER", ActivitySubcategoryCode.OTHER.getName());
		        assertEquals("Other", ActivitySubcategoryCode.OTHER.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ActualAnticipatedTypeCode")){			
			 String[] x = ActualAnticipatedTypeCode.getDisplayNames();
		        assertEquals(ActualAnticipatedTypeCode.values().length, x.length);
		        assertEquals("ACTUAL", ActualAnticipatedTypeCode.ACTUAL.getName());
		        assertEquals("Actual", ActualAnticipatedTypeCode.ACTUAL.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.AllocationCode")){			
			 String[] x = AllocationCode.getDisplayNames();
		      assertEquals(AllocationCode.values().length, x.length);
		      assertEquals("RANDOMIZED_CONTROLLED_TRIAL", AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getName());
		      assertEquals("Randomized Controlled Trial", AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ArmTypeCode")){			
			 String[] x = ArmTypeCode.getDisplayNames();
		        assertEquals(ArmTypeCode.values().length, x.length);
		        assertEquals("EXPERIMENTAL", ArmTypeCode.EXPERIMENTAL.getName());
		        assertEquals("Experimental", ArmTypeCode.EXPERIMENTAL.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.BiospecimenRetentionCode")){			
			 String[] x = BiospecimenRetentionCode.getDisplayNames();
		        assertEquals(BiospecimenRetentionCode.values().length, x.length);
		        assertEquals("NONE", BiospecimenRetentionCode.NONE.getName());
		        assertEquals("None", BiospecimenRetentionCode.NONE.getCode());
			
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.BlindingRoleCode")){			
			 String[] x = BlindingRoleCode.getDisplayNames();
		        assertEquals(BlindingRoleCode.values().length, x.length);
		        assertEquals("INVESTIGATOR", BlindingRoleCode.INVESTIGATOR.getName());
		        assertEquals("Investigator", BlindingRoleCode.INVESTIGATOR.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.BlindingSchemaCode")){			
			 String[] x = BlindingSchemaCode.getDisplayNames();
		        assertEquals(BlindingSchemaCode.values().length, x.length);
		        assertEquals("OPEN", BlindingSchemaCode.OPEN.getName());
		        assertEquals("Open", BlindingSchemaCode.OPEN.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.DesignConfigurationCode")){			
			 String[] x = DesignConfigurationCode.getDisplayNames();
		        assertEquals(DesignConfigurationCode.values().length, x.length);
		        assertEquals("CROSSOVER", DesignConfigurationCode.CROSSOVER.getName());
		        assertEquals("Cross-over", DesignConfigurationCode.CROSSOVER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.DocumentTypeCode")){			
			 String[] x = DocumentTypeCode.getDisplayNames();
		        assertEquals(DocumentTypeCode.values().length, x.length);
		        assertEquals("OTHER", DocumentTypeCode.OTHER.getName());
		        assertEquals("Other", DocumentTypeCode.OTHER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.DocumentWorkflowStatusCode")){			
			 String[] x = DocumentWorkflowStatusCode.getDisplayNames();
		        assertEquals(DocumentWorkflowStatusCode.values().length, x.length);
		        assertEquals("ACCEPTED", DocumentWorkflowStatusCode.ACCEPTED.getName());
		        assertEquals("Accepted", DocumentWorkflowStatusCode.ACCEPTED.getCode());
		}  
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.EligibleGenderCode")){			
			 String[] x = EligibleGenderCode.getDisplayNames();
		        assertEquals(EligibleGenderCode.values().length, x.length);
		        assertEquals("BOTH", EligibleGenderCode.BOTH.getName());
		        assertEquals("Both", EligibleGenderCode.BOTH.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ExpandedAccessStatusCode")){			
			 String[] x = ExpandedAccessStatusCode.getDisplayNames();
		        assertEquals(ExpandedAccessStatusCode.values().length, x.length);
		        assertEquals("AVAILABLE", ExpandedAccessStatusCode.AVAILABLE.getName());
		        assertEquals("Available", ExpandedAccessStatusCode.AVAILABLE.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.GrantorCode")){			
			 String[] x = GrantorCode.getDisplayNames();
		        assertEquals(GrantorCode.values().length, x.length);
		        assertEquals("CBER", GrantorCode.CBER.getName());
		        assertEquals("CBER", GrantorCode.CBER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.HolderTypeCode")){			
			 String[] x = HolderTypeCode.getDisplayNames();
		        assertEquals(HolderTypeCode.values().length, x.length);
		        assertEquals("NIH", HolderTypeCode.NIH.getName());
		        assertEquals("NIH", HolderTypeCode.NIH.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.IndldeTypeCode")){			
			 String[] x = IndldeTypeCode.getDisplayNames();
		        assertEquals(IndldeTypeCode.values().length, x.length);
		        assertEquals("IND", IndldeTypeCode.IND.getName());
		        assertEquals("IND", IndldeTypeCode.IND.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.InterventionTypeCode")){			
			 String[] x = InterventionTypeCode.getDisplayNames();
		        assertEquals(InterventionTypeCode.values().length, x.length);
		        assertEquals("DRUG", InterventionTypeCode.DRUG.getName());
		        assertEquals("Drug", InterventionTypeCode.DRUG.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.MilestoneCode")){			
			 String[] x = MilestoneCode.getDisplayNames();
		        assertEquals(MilestoneCode.values().length, x.length);
		        assertEquals("QC_START", MilestoneCode.QC_START.getName());
		        assertEquals("QC Start Date", MilestoneCode.QC_START.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.NciDivisionProgramCode")){			
			 String[] x = NciDivisionProgramCode.getDisplayNames();
		        assertEquals(NciDivisionProgramCode.values().length, x.length);
		        assertEquals("CTEP", NciDivisionProgramCode.CTEP.getName());
		        assertEquals("CTEP", NciDivisionProgramCode.CTEP.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.NihInstHolderCode")){			
			 String[] x = NihInstHolderCode.getDisplayNames();
		        assertEquals(NihInstHolderCode.values().length, x.length);
		        assertEquals("CIT", NihInstHolderCode.CIT.getName());
		        assertEquals("CIT-Center for Information Technology", NihInstHolderCode.CIT.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.NihInstituteCode")){			
			 String[] x = NihInstituteCode.getDisplayNames();
		        assertEquals(NihInstituteCode.values().length, x.length);
		        assertEquals("CIT", NihInstituteCode.CIT.getName());
		        assertEquals("CIT-Center for Information Technology", NihInstituteCode.CIT.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.PhaseCode")){			
			 String[] x = PhaseCode.getDisplayNames();
		        assertEquals(PhaseCode.values().length, x.length);
		        assertEquals("I", PhaseCode.I.getName());
		        assertEquals("I", PhaseCode.I.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.PrimaryPurposeCode")){			
			 String[] x = PrimaryPurposeCode.getDisplayNames();
		        assertEquals(PrimaryPurposeCode.values().length, x.length);
		        assertEquals("OTHER", PrimaryPurposeCode.OTHER.getName());
		        assertEquals("Other", PrimaryPurposeCode.OTHER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.RecruitmentStatusCode")){			
			 String[] x = RecruitmentStatusCode.getDisplayNames();
		        assertEquals(RecruitmentStatusCode.values().length, x.length);
		        assertEquals("COMPLETED", RecruitmentStatusCode.COMPLETED.getName());
		        assertEquals("Completed", RecruitmentStatusCode.COMPLETED.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ResponsibilityCode")){			
			 String[] x = ResponsibilityCode.getDisplayNames();
		        assertEquals(ResponsibilityCode.values().length, x.length);
		        assertEquals("DATA_MANAGEMENT", ResponsibilityCode.DATA_MANAGEMENT.getName());
		        assertEquals("Data Management", ResponsibilityCode.DATA_MANAGEMENT.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode")){			
			 String[] x = ReviewBoardApprovalStatusCode.getDisplayNames();
		        assertEquals(ReviewBoardApprovalStatusCode.values().length, x.length);
		        assertEquals("SUBMITTED_EXEMPT", ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getName());
		        assertEquals("Submitted, exempt", ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StatusCode")){			
			 String[] x = StatusCode.getDisplayNames();
		        assertEquals(StatusCode.values().length, x.length);
		        assertEquals("ACTIVE", StatusCode.ACTIVE.getName());
		        assertEquals("Active", StatusCode.ACTIVE.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.SamplingMethodCode")){			
			 String[] x = SamplingMethodCode.getDisplayNames();
		        assertEquals(SamplingMethodCode.values().length, x.length);
		        assertEquals("CLUSTER_SAMPLING", SamplingMethodCode.CLUSTER_SAMPLING.getName());
		        assertEquals("Cluster sampling", SamplingMethodCode.CLUSTER_SAMPLING.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyClassificationCode")){			
			 String[] x = StudyClassificationCode.getDisplayNames();
		        assertEquals(StudyClassificationCode.values().length, x.length);
		        assertEquals("SAFETY", StudyClassificationCode.SAFETY.getName());
		        assertEquals("Safety", StudyClassificationCode.SAFETY.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyContactRoleCode")){			
			 String[] x = StudyContactRoleCode.getDisplayNames();
		        assertEquals(StudyContactRoleCode.values().length, x.length);
		        assertEquals("SUBMITTER", StudyContactRoleCode.SUBMITTER.getName());
		        assertEquals("Submitter", StudyContactRoleCode.SUBMITTER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyModelCode")){			
			 String[] x = StudyModelCode.getDisplayNames();
		        assertEquals(StudyModelCode.values().length, x.length);
		        assertEquals("OTHER", StudyModelCode.OTHER.getName());
		        assertEquals("Other", StudyModelCode.OTHER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyParticipationContactRoleCode")){			
			 String[] x = StudyParticipationContactRoleCode.getDisplayNames();
		        assertEquals(StudyParticipationContactRoleCode.values().length, x.length);
		        assertEquals("SUBMITTER", StudyParticipationContactRoleCode.SUBMITTER.getName());
		        assertEquals("Submitter", StudyParticipationContactRoleCode.SUBMITTER.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyParticipationFunctionalCode")){			
			 String[] x = StudyParticipationFunctionalCode.getDisplayNames();
		        assertEquals(StudyParticipationFunctionalCode.values().length, x.length);
		        assertEquals("SPONSOR", StudyParticipationFunctionalCode.SPONSOR.getName());
		        assertEquals("Sponsor", StudyParticipationFunctionalCode.SPONSOR.getCode());
		}
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyTypeCode")){			
			 String[] x = StudyTypeCode.getDisplayNames();
		        assertEquals(StudyTypeCode.values().length, x.length);
		        assertEquals("INTERVENTIONAL", StudyTypeCode.INTERVENTIONAL.getName());
		        assertEquals("Interventional", StudyTypeCode.INTERVENTIONAL.getCode());
		} 
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode")){			
			 String[] x = SummaryFourFundingCategoryCode.getDisplayNames();
		        assertEquals(SummaryFourFundingCategoryCode.values().length, x.length);
		        assertEquals("NATIONAL", SummaryFourFundingCategoryCode.NATIONAL.getName());
		        assertEquals("National", SummaryFourFundingCategoryCode.NATIONAL.getCode());
		} 
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.TimePerspectiveCode")){			
			 String[] x = TimePerspectiveCode.getDisplayNames();
		        assertEquals(TimePerspectiveCode.values().length, x.length);
		        assertEquals("OTHER", TimePerspectiveCode.OTHER.getName());
		        assertEquals("Other", TimePerspectiveCode.OTHER.getCode());
		} 
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.UnitsCode")){			
			 String[] x = UnitsCode.getDisplayNames();
		        assertEquals(UnitsCode.values().length, x.length);
		        assertEquals("DAYS", UnitsCode.DAYS.getName());
		        assertEquals("Days", UnitsCode.DAYS.getCode());
		} 
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.USStateCode")){			
			 String[] x = USStateCode.getDisplayNames();
		        assertEquals(USStateCode.values().length, x.length);
		        assertEquals("AK", USStateCode.AK.getName());
		        assertEquals("Alaska", USStateCode.AK.getCode());
		} 
		 if (clazz.getName().equals("gov.nih.nci.pa.enums.StudyRelationshipTypeCode")){			
			 String[] x = StudyRelationshipTypeCode.getDisplayNames();
		        assertEquals(StudyRelationshipTypeCode.values().length, x.length);
		        assertEquals("MOD", StudyRelationshipTypeCode.MOD.getName());
		        assertEquals("Modified", StudyRelationshipTypeCode.MOD.getCode());
		} 
		 
	}
}
