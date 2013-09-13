/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.enums.OutcomeMeasureTypeCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.RealConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

/**
 * Test class for the TrialRegistrationBean.
 *
 * @author Michael Visee
 */
public class TrialRegistrationServiceTest extends AbstractTrialRegistrationTestBase {
    @Test
    public void createInterventionalStudyProtocolTest() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
		
        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(ii));
        
        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);        
        assertEquals(2, studyProtocolDTO.getProcessingPriority().getValue().intValue());

    }
    
    @Test
    public void createAbbreviatedStudyProtocolTest() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = registerAbbreviatedTrial();        
        assertEquals(2, studyProtocolDTO.getProcessingPriority().getValue().intValue());

        
    }

    /**
     * @return
     * @throws PAException
     */
    private InterventionalStudyProtocolDTO registerAbbreviatedTrial()
            throws PAException {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        
        StudySiteDTO nctID = new StudySiteDTO();
        nctID.setLocalStudyProtocolIdentifier(StConverter.convertToSt("NCT12345"));
        
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        leadOrganizationSiteIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LID12345"));
        
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        
        PersonDTO principalInvestigatorDTO  = getPI();
        
        PersonDTO centralContactDTO = principalInvestigatorDTO;
        
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        
        List<ArmDTO> arms = new ArrayList<ArmDTO>();
        ArmDTO armDTO = new ArmDTO();
        armDTO.setName(StConverter.convertToSt("ARm01"));
        armDTO.setTypeCode(CdConverter
                .convertToCd(ArmTypeCode.ACTIVE_COMPARATOR));
        armDTO.setDescriptionText(StConverter.convertToSt("ARm01"));
        arms.add(armDTO);
        
        List<PlannedEligibilityCriterionDTO> eligibility = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
        pEligibiltyCriterionDTO.setCriterionName(StConverter
                .convertToSt("GENDER"));
        pEligibiltyCriterionDTO
                .setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode.BOTH));
        pEligibiltyCriterionDTO
                .setCategoryCode(CdConverter
                        .convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter
                .convertToBl(Boolean.TRUE));
        eligibility.add(pEligibiltyCriterionDTO);  
        
        List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
        StudyOutcomeMeasureDTO outcome = new StudyOutcomeMeasureDTO();
        outcome.setName(StConverter.convertToSt("Outcome01"));
        outcome.setSafetyIndicator(BlConverter.convertToBl(true));
        outcome.setPrimaryIndicator(BlConverter
                .convertToBl(true));
        outcome.setTimeFrame(StConverter.convertToSt("Outcome01"));
        outcome.setDescription(StConverter.convertToSt("Outcome01"));
        outcome.setTypeCode(CdConverter.convertToCd(OutcomeMeasureTypeCode.PRIMARY));
        outcomes.add(outcome);     
        
        List<OrganizationDTO> collaborators = new ArrayList<OrganizationDTO>();
        collaborators.add(sponsorOrganizationDTO);
        
        List<DocumentDTO> documents = getStudyDocuments();        
        
        Ii ii = bean.createAbbreviatedStudyProtocol(studyProtocolDTO, nctID,
                leadOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                sponsorOrganizationDTO, principalInvestigatorDTO, null,
                centralContactDTO, overallStatusDTO, regAuthority, arms,
                eligibility, outcomes, collaborators, documents);
        assertFalse(ISOUtil.isIiNull(ii));
        
        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        return studyProtocolDTO;
    }


    @Test
    public void nullSiteIdentifiers() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = null;
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
		
        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(ii));
    }

    @Test
    public void nullStudyOverallStatus() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Overall Status cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = null;
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summary4Org);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationList, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullDocuments() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Protocol Document is required.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = null;

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summary4Org);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationList, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullLeadOrganization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Lead Organization cannot be null.\n");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = null;
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summary4Org);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationList, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullPrincipalInvestigator() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Principal Investigator cannot be null.\n");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = null;
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summary4Org);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationList, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSponsorOrganization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Sponsor Organization cannot be null.\n");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = null;
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        OrganizationDTO summary4Org = new  OrganizationDTO();
        summary4Org.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        List<OrganizationDTO> summary4OrganizationList = new ArrayList<OrganizationDTO>();
        summary4OrganizationList.add(summary4Org);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationList, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullLeadOrganizationSiteIdentifier() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Identifier DTO cannot be null");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = null;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
		
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullResponsibleParty() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Responsible Party must be specified. ");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = null;
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, new ResponsiblePartyDTO(), leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSummary4Organization() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Summary 4 Organization cannot be null.\n");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, null, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullSummary4StudyResourcing() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Summary Four Study Resourcing cannot be null");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        StudyResourcingDTO summary4StudyResourcing = null;
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
		
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing,
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyRegulatoryAuthority() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Regulatory Information fields must be Entered.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = null;

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolProperties() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Study Protocol cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = null;
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                                                       studyResourcingDTOs, documents, leadOrganizationDTO,
                                                       principalInvestigatorDTO, sponsorOrganizationDTO,
                                                       leadOrganizationSiteIdentifierDTO, siteIdentifiers,
                                                       studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                                                       null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolStartDate() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Trial Start Date cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setStartDate(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolStartDateTypeCode() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Trial Start Date Type cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setStartDateTypeCode(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolPrimaryCompletionDate() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Primary Completion Date cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setPrimaryCompletionDate(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolPrimaryCompletionDateTypeCode() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Primary Completion Date Type cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setPrimaryCompletionDateTypeCode(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void nullStudyProtocolCtGovXmlIndicator() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Study Protocol ClinicalTrials.gov XML indicator cannot be null.");

        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setCtgovXmlRequiredIndicator(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void createInterventionalStudyProtocolValidationTest() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Submitter is required.");
        thrown.expectMessage("Anticipated Primary Completion Date must be current or in "
                + "the future.");
        thrown.expectMessage("Document badfile.xml has an invalid file type.");

        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(spIi);
        studyProtocolDTO.setIdentifier(null);
        studyProtocolDTO.setUserLastCreated(null);
        studyProtocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(DateUtils.addYears(new Date(), -1)));
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<DocumentDTO> documentDTOs = documentService.getDocumentsByStudyProtocol(spIi);
        DocumentDTO badFileTypeDoc = new DocumentDTO();
        badFileTypeDoc.setFileName(StConverter.convertToSt("badfile.xml"));
        badFileTypeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));
        documentDTOs.add(badFileTypeDoc);
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO  = studySiteService.getByStudyProtocol(spIi, spDto).get(0) ;
        bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, new ArrayList<StudySiteDTO>(),
                null, null, null, null, null, null, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void updateTest() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        studyProtocolDTO.setIdentifier(null);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
		Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                                                               studyResourcingDTOs, documents, leadOrganizationDTO,
                                                               principalInvestigatorDTO, sponsorOrganizationDTO,
                                                               leadOrganizationSiteIdentifierDTO, siteIdentifiers,
                                                               studyContactDTO, null, summary4OrganizationDTO,
                                                               summary4StudyResourcing.get(0), null, regAuthority, BlConverter
                                                                   .convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(ii));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        new PAServiceUtils()
            .createMilestone(ii, MilestoneCode.SUBMISSION_ACCEPTED, StConverter.convertToSt("Accepted"), null);

        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        studyResourcingDTOs = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        List<DocumentDTO> documentDTOs = new ArrayList<DocumentDTO>();

        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt("test_NCT_update"));
        isoDto.setResearchOrganizationIi(IiConverter.convertToPoResearchOrganizationIi("abc"));
        siteIdentifiers.add(isoDto);
        
        bean.update(studyProtocolDTO, overallStatusDTO, siteIdentifiers, null, studyResourcingDTOs, 
        		documentDTOs, null, null, null, null, null, null, null, 
        		null, null, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void amendTrialTestWithChangeMemoDoc() throws Exception {
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
		List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("Change Memo.doc"));
        changeDoc.setText(EdConverter.convertToEd("Change Memo".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.CHANGE_MEMO_DOCUMENT));
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
    }


    protected Ii registerTrial() throws PAException {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = getListOfINDs();
        
        List<StudyResourcingDTO> studyResourcingDTOs = getListOfGrants();
        
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(ii));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        return ii;
    }

    /**
     * @return
     */
    protected List<StudyResourcingDTO> getListOfGrants() {
        List<StudyResourcingDTO> studyResourcingDTOs  = new ArrayList<StudyResourcingDTO>();
        final StudyResourcingDTO grant = new StudyResourcingDTO();
        grant.setFundingMechanismCode(CdConverter.convertStringToCd("D71"));
        grant.setNciDivisionProgramCode(CdConverter.convertStringToCd("CTEP"));
        grant.setNihInstitutionCode(CdConverter.convertStringToCd("AI"));
        grant.setSerialNumber(StConverter.convertToSt("023099"));
        grant.setFundingPercent(RealConverter.convertToReal(33d));
        studyResourcingDTOs.add(grant);
        return studyResourcingDTOs;
    }

    @Test
    public void amendTrialTestWithProtocolHighlightedDoc() throws Exception {
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
    }

    @Test
    public void amendTrialTestWithDeletedOtherIdentifiers() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = getInterventionalStudyProtocol();
        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(spIi);
        List<StudySiteDTO> siteIdentifiers = new ArrayList<StudySiteDTO>();
        List<DocumentDTO> documents = getStudyDocuments();

        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);

        Ii ii = bean.createCompleteInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documents, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                siteIdentifiers, studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0),
                null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(ii));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        createMilestones(ii);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        overallStatusDTO.setIdentifier(null);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        studyProtocolDTO.getSecondaryIdentifiers();
        studyProtocolDTO.getSecondaryIdentifiers().getItem().clear();

        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception Other identifiers cannot be modified or deleted as part of an amendment.");

        bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void amendTrialTestWithAddingOtherIdentifiers() throws Exception {
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();


        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);

        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        assertEquals(2, studyProtocolDTO.getSecondaryIdentifiers().getItem().size());
    }
    
    @Test
    public void amendTrialTestAmendingUser() throws Exception {
        
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();


        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);
        
        St userCreated = studyProtocolDTO.getUserLastCreated();
        PaHibernateUtil.getCurrentSession().createSQLQuery(
                "update study_protocol set user_last_created_id=null where identifier="
                        + studyProtocolDTO.getIdentifier().getExtension());
        
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        assertEquals(2, studyProtocolDTO.getSecondaryIdentifiers().getItem().size());
        assertEquals(2, studyProtocolDTO.getProcessingPriority().getValue().intValue());
        assertEquals(userCreated, studyProtocolDTO.getUserLastCreated());
    }
    
    @Test
    public void amendTrialTestPO6172DuplicateGrantsHandledGracefully() throws Exception {
        
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        
        // Make grants appear as 'new' by nullifying their identifiers. This is what happens when DCIM is submitting
        // via the service. In 3.9.1RC2 and prior this would fail due to 'duplicate' grants.
        List<StudyResourcingDTO> studyResourcingDTOs = getListOfGrants();       
        
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();


        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);
        
        PaHibernateUtil.getCurrentSession().createSQLQuery(
                "update study_protocol set user_last_created_id=null where identifier="
                        + studyProtocolDTO.getIdentifier().getExtension());
        
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE),
                BlConverter.convertToBl(Boolean.TRUE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
        
        studyResourcingDTOs = studyResourcingService
                .getStudyResourcingByStudyProtocol(ii);
        assertEquals(1, studyResourcingDTOs.size());
        StudyResourcingDTO grant = studyResourcingDTOs.get(0);
        assertEquals(grant.getFundingMechanismCode().getCode(), ("D71"));
        assertEquals(grant.getNciDivisionProgramCode().getCode(), ("CTEP"));
        assertEquals(grant.getNihInstitutionCode().getCode(), ("AI"));
        assertEquals(grant.getSerialNumber().getValue(), ("023099"));
        assertEquals(grant.getFundingPercent().getValue(), Double.valueOf(33d));
    }
    
    @Test
    public void amendTrialTestPO6172DuplicateINDsHandledGracefully() throws Exception {
        
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = getListOfINDs();   
        List<StudyResourcingDTO> studyResourcingDTOs = getListOfGrants();       
        
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();


        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);
        
        PaHibernateUtil.getCurrentSession().createSQLQuery(
                "update study_protocol set user_last_created_id=null where identifier="
                        + studyProtocolDTO.getIdentifier().getExtension());
        
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE),
                BlConverter.convertToBl(Boolean.TRUE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
        
        studyIndldeDTOs = studyIndldeService.getByStudyProtocol(spIi);
        assertEquals(1, studyIndldeDTOs.size());
        StudyIndldeDTO grant = studyIndldeDTOs.get(0);
        assertEquals(grant.getIndldeNumber().getValue(), ("1234"));
    }


    
    protected List<StudyIndldeDTO> getListOfINDs() {
        List<StudyIndldeDTO> list = new ArrayList<StudyIndldeDTO>();
        StudyIndldeDTO si = new StudyIndldeDTO();
        si.setIndldeTypeCode(CdConverter.convertToCd(IndldeTypeCode.IND));
        si.setGrantorCode(CdConverter.convertToCd(GrantorCode.CDER));
        si.setIndldeNumber(StConverter.convertToSt("1234"));
        si.setExpandedAccessStatusCode(CdConverter.convertToCd(ExpandedAccessStatusCode.AVAILABLE));        
        si.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.TRUE));
        si.setExemptIndicator(BlConverter.convertToBl(Boolean.FALSE));
        si.setHolderTypeCode(CdConverter.convertToCd(HolderTypeCode.NIH));
        si.setNihInstHolderCode(CdConverter.convertToCd(NihInstituteCode.NCMHD));
        list.add(si);
        return list;
    }

    @Test
    public void amendTrialTestPO6151() throws Exception {
        
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        studyProtocolDTO.setPublicTitle(StConverter.convertToSt("Public title"));
        studyProtocolDTO.setPublicDescription(StConverter.convertToSt("Public descr"));
        studyProtocolDTO.setScientificDescription(StConverter.convertToSt("Scientific descr"));
        studyProtocolService.updateStudyProtocol(studyProtocolDTO);
        
        
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();


        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("ProtocolHighlightedDocument.doc"));
        changeDoc.setText(EdConverter.convertToEd("ProtocolHighlightedDocument".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT));

        Ii newSecondaryIdentifier = new Ii();
        newSecondaryIdentifier.setExtension("Temp");
        studyProtocolDTO.getSecondaryIdentifiers().getItem().add(newSecondaryIdentifier);
        
        St userCreated = studyProtocolDTO.getUserLastCreated();
        PaHibernateUtil.getCurrentSession().createSQLQuery(
                "update study_protocol set user_last_created_id=null where identifier="
                        + studyProtocolDTO.getIdentifier().getExtension());
        
        // Change next 3 fields and then ensure that the changes have been ignored.        
        studyProtocolDTO.setPublicTitle(StConverter.convertToSt(""));
        studyProtocolDTO.setPublicDescription(StConverter.convertToSt(""));
        studyProtocolDTO.setScientificDescription(StConverter.convertToSt(""));
        
        Ii amendedSpIi = bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
        assertFalse(ISOUtil.isIiNull(amendedSpIi));
        assertEquals(IiConverter.convertToLong(ii), IiConverter.convertToLong(amendedSpIi));
        studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        assertEquals("Public title", studyProtocolDTO.getPublicTitle().getValue());
        assertEquals("Public descr", studyProtocolDTO.getPublicDescription().getValue());
        assertEquals("Scientific descr", studyProtocolDTO.getScientificDescription().getValue());
    }

    

    @Test
    public void amendTrialTestMissingConditionallyRequiredDocs() throws Exception {
        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs  = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO  = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception At least one is required: Change Memo Document or Protocol Highlighted Document.");

        bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                Arrays.asList(documents.get(1), documents.get(2)), leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null, studyContactDTO, null,
                summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter.convertToBl(Boolean.FALSE));
    }

    @Test
    public void unacceptedUpdateThenAmendTest() throws Exception {
        thrown.expect(PAException.class);
        thrown.expectMessage("Validation Exception A trial with unaccepted updates cannot be amended. Please contact"
                + " the CTRO at ncictro@mail.nih.gov to have your trial's updates accepted.");

        Ii ii = registerTrial();

        createMilestones(ii);
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(ii);
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(ii);
        overallStatusDTO.setIdentifier(null);
        List<StudyIndldeDTO> studyIndldeDTOs = studyIndldeService.getByStudyProtocol(ii);
        List<StudyResourcingDTO> studyResourcingDTOs = studyResourcingService.getStudyResourcingByStudyProtocol(ii);
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(ii);
        studyProtocolDTO.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.PREVENTION));

        List<DocumentDTO> documents = getStudyDocuments();
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        PersonDTO principalInvestigatorDTO = getPI();
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        StudySiteDTO spDto = getStudySite();
        StudySiteDTO leadOrganizationSiteIdentifierDTO = studySiteService.getByStudyProtocol(spIi, spDto).get(0);
        StudyContactDTO studyContactDTO = studyContactSvc.getByStudyProtocol(spIi).get(0);
        List<OrganizationDTO> summary4OrganizationDTO = new ArrayList<OrganizationDTO>();
        summary4OrganizationDTO.add(new OrganizationDTO());
        List<StudyResourcingDTO> summary4StudyResourcing = studyResourcingService.getSummary4ReportedResourcing(spIi);

        DocumentDTO psDoc = new DocumentDTO();
        psDoc.setFileName(StConverter.convertToSt("Participating Site Document.doc"));
        psDoc.setText(EdConverter.convertToEd("Participating Site Document".getBytes()));
        psDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PARTICIPATING_SITES));
        bean.update(studyProtocolDTO, overallStatusDTO, studyResourcingDTOs, Arrays.asList(psDoc), null, null,
                    BlConverter.convertToBl(Boolean.FALSE));

        PaHibernateUtil.getCurrentSession().flush();
        PaHibernateUtil.getCurrentSession().clear();

        studyProtocolDTO.setAmendmentDate(TsConverter.convertToTs(TestSchema.TODAY));
        DocumentDTO changeDoc = new DocumentDTO();
        changeDoc.setFileName(StConverter.convertToSt("Change Memo.doc"));
        changeDoc.setText(EdConverter.convertToEd("Change Memo".getBytes()));
        changeDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.CHANGE_MEMO_DOCUMENT));
        bean.amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                   Arrays.asList(changeDoc, documents.get(1), documents.get(2)), leadOrganizationDTO,
                   principalInvestigatorDTO, sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO, null,
                   studyContactDTO, null, summary4OrganizationDTO, summary4StudyResourcing.get(0), null, regAuthority, BlConverter
                       .convertToBl(Boolean.FALSE));
    }

    protected void createMilestones(Ii ii) throws PAException {
        paServiceUtils
            .createMilestone(ii, MilestoneCode.SUBMISSION_ACCEPTED, StConverter.convertToSt("Accepted"), null);
        paServiceUtils.createMilestone(ii, MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.ADMINISTRATIVE_READY_FOR_QC, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.ADMINISTRATIVE_QC_START, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.ADMINISTRATIVE_QC_COMPLETE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.SCIENTIFIC_READY_FOR_QC, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.SCIENTIFIC_QC_START, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.SCIENTIFIC_QC_COMPLETE, null, null);
        paServiceUtils.createMilestone(ii, MilestoneCode.INITIAL_ABSTRACTION_VERIFY, null, null);
    }

    @Test
    public void testSendTSRXML() throws PAException {
        bean.sendTSRXML(null, null, null);
        bean.sendTSRXML(spIi, null, new ArrayList<StudyInboxDTO>());
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.READY_FOR_TSR), new ArrayList<StudyInboxDTO>());
        List<StudyInboxDTO> inboxList = new ArrayList<StudyInboxDTO>();
        StudyInboxDTO inboxDto = new StudyInboxDTO();
        Ivl<Ts> ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        inboxDto.setInboxDateRange(ivlTs);
        inboxList.add(inboxDto);
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_REPORT),inboxList);
        inboxList .remove(0);
        ivlTs = new Ivl<Ts>();
        ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
        ivlTs.setHigh(TsConverter.convertToTs(new Timestamp(0)));
        inboxDto.setInboxDateRange(ivlTs);
        inboxList.add(inboxDto);
        bean.setPaServiceUtils(mock(PAServiceUtils.class));
        bean.sendTSRXML(spIi, CdConverter.convertToCd(MilestoneCode.TRIAL_SUMMARY_REPORT),inboxList);
    }

    protected InterventionalStudyProtocolDTO getInterventionalStudyProtocol() throws PAException {
        InterventionalStudyProtocolDTO studyProtocolDTO = studyProtocolService.getInterventionalStudyProtocol(spIi);
        studyProtocolDTO.setIdentifier(null);
        return studyProtocolDTO;
    }

    protected OrganizationDTO getLeadOrg() {
        OrganizationDTO leadOrganizationDTO = new OrganizationDTO();
        leadOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("abc"));
        return leadOrganizationDTO;
    }

    protected OrganizationDTO getSponsorOrg() {
        OrganizationDTO sponsorOrganizationDTO = new OrganizationDTO();
        sponsorOrganizationDTO.setIdentifier(IiConverter.convertToPoOrganizationIi("111"));
        return sponsorOrganizationDTO;
    }

    protected PersonDTO getPI() {
        PersonDTO principalInvestigatorDTO  = new PersonDTO();
        principalInvestigatorDTO.setIdentifier(IiConverter.convertToPoPersonIi("abc"));
        return principalInvestigatorDTO;
    }

    protected StudySiteDTO getStudySite() {
        StudySiteDTO spDto = new StudySiteDTO();
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        return spDto;
    }

    protected List<DocumentDTO> getStudyDocuments() {
        DocumentDTO icDoc = new DocumentDTO();
        icDoc.setFileName(StConverter.convertToSt("InformedConsent.doc"));
        icDoc.setText(EdConverter.convertToEd("Informed Consent".getBytes()));
        icDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT));

        DocumentDTO protocolDoc = new DocumentDTO();
        protocolDoc.setFileName(StConverter.convertToSt("Protocol.doc"));
        protocolDoc.setText(EdConverter.convertToEd("Protocol Document".getBytes()));
        protocolDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.PROTOCOL_DOCUMENT));

        DocumentDTO irbDoc = new DocumentDTO();
        irbDoc.setFileName(StConverter.convertToSt("IRB.doc"));
        irbDoc.setText(EdConverter.convertToEd("IRB Approval".getBytes()));
        irbDoc.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.IRB_APPROVAL_DOCUMENT));
        return Arrays.asList(icDoc, protocolDoc, irbDoc);
    }
    
    
    @Test
    public void updateAbbreviatedStudyProtocolTest() throws Exception {
        InterventionalStudyProtocolDTO studyProtocolDTO = registerAbbreviatedTrial();   
        
        StudySiteDTO nctID = new StudySiteDTO();
        nctID.setLocalStudyProtocolIdentifier(StConverter.convertToSt("NCT123456"));
        
        OrganizationDTO leadOrganizationDTO = getLeadOrg();
        
        StudySiteDTO leadOrganizationSiteIdentifierDTO = new StudySiteDTO();
        leadOrganizationSiteIdentifierDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LID123456"));
        
        OrganizationDTO sponsorOrganizationDTO = getSponsorOrg();
        
        PersonDTO principalInvestigatorDTO  = getPI();
        
        PersonDTO centralContactDTO = principalInvestigatorDTO;
        
        StudyOverallStatusDTO overallStatusDTO = studyOverallStatusService.getCurrentByStudyProtocol(spIi);
        overallStatusDTO.setIdentifier(null);
        
        StudyRegulatoryAuthorityDTO regAuthority = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spIi);
        regAuthority.setIdentifier(null);
        
        List<ArmDTO> arms = new ArrayList<ArmDTO>();
        ArmDTO armDTO = new ArmDTO();
        armDTO.setName(StConverter.convertToSt("ARm02"));
        armDTO.setTypeCode(CdConverter
                .convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDTO.setDescriptionText(StConverter.convertToSt("ARm02"));
        arms.add(armDTO);
        
        List<PlannedEligibilityCriterionDTO> eligibility = new ArrayList<PlannedEligibilityCriterionDTO>();
        PlannedEligibilityCriterionDTO pEligibiltyCriterionDTO = new PlannedEligibilityCriterionDTO();
        pEligibiltyCriterionDTO.setCriterionName(StConverter
                .convertToSt("GENDER"));
        pEligibiltyCriterionDTO
                .setEligibleGenderCode(CdConverter.convertToCd(EligibleGenderCode.FEMALE));
        pEligibiltyCriterionDTO
                .setCategoryCode(CdConverter
                        .convertToCd(ActivityCategoryCode.ELIGIBILITY_CRITERION));
        pEligibiltyCriterionDTO.setInclusionIndicator(BlConverter
                .convertToBl(Boolean.TRUE));
        eligibility.add(pEligibiltyCriterionDTO);  
        
        List<StudyOutcomeMeasureDTO> outcomes = new ArrayList<StudyOutcomeMeasureDTO>();
        StudyOutcomeMeasureDTO outcome = new StudyOutcomeMeasureDTO();
        outcome.setName(StConverter.convertToSt("Outcome02"));
        outcome.setSafetyIndicator(BlConverter.convertToBl(true));
        outcome.setPrimaryIndicator(BlConverter
                .convertToBl(true));
        outcome.setTimeFrame(StConverter.convertToSt("Outcome02"));
        outcome.setDescription(StConverter.convertToSt("Outcome02"));
        outcome.setTypeCode(CdConverter.convertToCd(OutcomeMeasureTypeCode.PRIMARY));
        outcomes.add(outcome);     
        
        List<OrganizationDTO> collaborators = new ArrayList<OrganizationDTO>();
        collaborators.add(sponsorOrganizationDTO);
        
        List<DocumentDTO> documents = new ArrayList<DocumentDTO>();
        
        Ii ii = bean.updateAbbreviatedStudyProtocol(studyProtocolDTO, nctID,                
                sponsorOrganizationDTO, principalInvestigatorDTO, null,
                centralContactDTO, overallStatusDTO, regAuthority, arms,
                eligibility, outcomes, collaborators, documents);
        assertFalse(ISOUtil.isIiNull(ii));
        
    }
}
