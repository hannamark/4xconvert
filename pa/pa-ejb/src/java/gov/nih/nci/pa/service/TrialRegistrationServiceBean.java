/*
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

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyRelationshipTypeCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.dto.StudySiteOverallStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AbstractionCompletionServiceRemote;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author Naveen Amiruddin
 * @since 03/19/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" , "PMD.ExcessiveParameterList" ,
    "PMD.ExcessiveClassLength" , "PMD.TooManyMethods" , "PMD.ExcessiveMethodLength" , "PMD.TooManyFields" })
@Interceptors(HibernateSessionInterceptor.class)
public class TrialRegistrationServiceBean implements TrialRegistrationServiceRemote {

    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyRelationshipServiceLocal studyRelationshipService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
    StudyIndldeServiceLocal studyIndldeService  = null;
    @EJB
    StudyResourcingServiceLocal studyResourcingService = null;
    @EJB
    StudyMilestoneServicelocal studyMilestoneService = null;
    @EJB
    DocumentServiceLocal documentService = null;
    @EJB
    StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB
    ArmServiceLocal armService = null;
    @EJB
    PlannedActivityServiceLocal plannedActivityService = null;
    @EJB
    StratumGroupServiceLocal subGroupsService = null;
    @EJB
    StudySiteServiceLocal studySiteService = null;
    @EJB
    StudySiteContactServiceLocal studySiteContactService = null;
    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB
    OrganizationCorrelationServiceRemote ocsr = null;
    @EJB 
    StudyContactServiceRemote studyContactService = null;
    @EJB
    TSRReportGeneratorServiceRemote tsrReportService = null;
    @EJB
    DocumentWorkflowStatusServiceLocal docWrkFlowStatusService = null;
    @EJB
    RegulatoryInformationServiceRemote regulatoryInfoBean = null;
    @EJB
    StudyRecruitmentStatusServiceRemote studyRecruitmentStatusServiceRemote = null;
    @EJB
    StudyObjectiveServiceRemote studyObjectiveService = null;
     @EJB
    StudySiteOverallStatusServiceLocal studySiteOverallStatusService = null;
    @EJB
    AbstractionCompletionServiceRemote abstractionCompletionService = null;
    @EJB
    StudyInboxServiceLocal studyInboxServiceLocal = null;
    
    private static final String CREATE = "Create";
    private static final String AMENDMENT = "Amendment";
    private static final String UPDAT = "Update";
    private static final String REJECTION = "Reject";
    private static PAServiceUtils paServiceUtils = new PAServiceUtils();
    
    private static final String PROTOCOL_ID_NULL = "Study Protocol Identifer is null";
    private static final String NO_PROTOCOL_FOUND = "No Study Protocol found for = ";
    private static final String EMAIL_NOT_NULL = "Email cannot be null, ";
    private static final String PHONE_NOT_NULL = "Phone cannot be null, ";
    private SessionContext ejbContext;

    @Resource
    void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }


    /**
     * An action plan and execution of a pre-clinical or clinical study including
     * all activities to test a particular hypothesis that is the basis of the study
     * regarding the effectiveness of a particular treatment, drug, device,
     * procedure, or care plan. This includes prevention, observational,
     * therapeutic, and other types of studies that involve subjects.:
     * <ul>
     * <li>
     * </ul>
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param nctIdentifierSiteIdentifierDTO nct Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @return ii of Study Protocol
     * @throws PAException on error
     */

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            PersonDTO principalInvestigatorDTO ,
            OrganizationDTO sponsorOrganizationDTO ,
            StudySiteDTO leadOrganizationSiteIdentifierDTO ,
            StudySiteDTO nctIdentifierSiteIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studySiteContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            Ii responsiblePartyContactIi)
    throws PAException {

        Ii studyProtocolIi = null;
        try {
        studyProtocolIi = createStudyProtocolObjs(
                studyProtocolDTO ,
                overallStatusDTO ,
                studyIndldeDTOs ,
                studyResourcingDTOs ,
                documentDTOs ,
                leadOrganizationDTO ,
                principalInvestigatorDTO ,
                sponsorOrganizationDTO ,
                leadOrganizationSiteIdentifierDTO ,
                nctIdentifierSiteIdentifierDTO ,
                studyContactDTO ,
                studySiteContactDTO ,
                summary4organizationDTO ,
                summary4studyResourcingDTO ,
                responsiblePartyContactIi, CREATE);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        return studyProtocolIi;


    }

    /**
     * An action plan and execution of a pre-clinical for amending an existing protocols.
     * <ul>
     * <li>
     * </ul>
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs list of documents
     * @param leadOrganizationDTO Pead organization
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsort Organization
     * @param leadOrganizationSiteIdentifierDTO local protocol identifier
     * @param nctIdentifierSiteIdentifierDTO nct Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studySiteContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @return ii of Study Protocol
     * @throws PAException on error
     */

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            PersonDTO principalInvestigatorDTO ,
            OrganizationDTO sponsorOrganizationDTO ,
            StudySiteDTO leadOrganizationSiteIdentifierDTO ,
            StudySiteDTO nctIdentifierSiteIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studySiteContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            Ii responsiblePartyContactIi)
    throws PAException {

        
        try {        
            updateStudyProtocolObjs(
                studyProtocolDTO ,
                overallStatusDTO ,
                leadOrganizationSiteIdentifierDTO ,
                nctIdentifierSiteIdentifierDTO,
                studyIndldeDTOs ,
                studyResourcingDTOs ,
                documentDTOs ,
                leadOrganizationDTO ,
                principalInvestigatorDTO,
                sponsorOrganizationDTO,
                studyContactDTO ,
                studySiteContactDTO,
                summary4organizationDTO ,
                summary4studyResourcingDTO ,
                responsiblePartyContactIi , 
                null, 
                null, 
                null,
                null , AMENDMENT);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }            
        
        return studyProtocolDTO.getIdentifier();

    }

    /**
     * An action plan and execution of a pre-clinical for Updating an existing protocols.
     * <ul>
     * <li>
     * </ul>
     *
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param nctIdentifierSiteIdentifierDTO StudysiteDTO
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs IRB document
     * @param leadOrgDTO lead OrganizationDTO
     * @param principalInvestigatorDTO lead pi
     * @param sponsorOrgDTO sponsor Organization DTO
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO StudySiteContactDTO 
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO updated studyRegAuthDTO
     * @param collaborators list of updated collaborators
     * @param participatingSites list of updated participating sites 
     * @param pgCdUpdatedList list of StudySite DTOs with updated program code
     * @throws PAException on error
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void update(
        StudyProtocolDTO studyProtocolDTO ,
        StudyOverallStatusDTO overallStatusDTO ,
        StudySiteDTO nctIdentifierSiteIdentifierDTO,
        List<StudyIndldeDTO> studyIndldeDTOs ,
        List<StudyResourcingDTO> studyResourcingDTOs ,
        List<DocumentDTO> documentDTOs ,
        OrganizationDTO leadOrgDTO,
        PersonDTO principalInvestigatorDTO,
        OrganizationDTO sponsorOrgDTO,
        StudyContactDTO studyContactDTO ,
        StudySiteContactDTO studyParticipationContactDTO, 
        OrganizationDTO summary4organizationDTO ,
        StudyResourcingDTO summary4studyResourcingDTO ,
        Ii responsiblePartyContactIi, 
        StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
        List<StudySiteDTO> collaborators, 
        List<StudySiteAccrualStatusDTO> participatingSites,
        List<StudySiteDTO> pgCdUpdatedList) throws PAException {
       
        try {
           updateStudyProtocolObjs(
                    studyProtocolDTO ,
                    overallStatusDTO ,
                    null ,
                    nctIdentifierSiteIdentifierDTO,
                    studyIndldeDTOs ,
                    studyResourcingDTOs ,
                    documentDTOs ,
                    leadOrgDTO ,
                    principalInvestigatorDTO,
                    sponsorOrgDTO,
                    studyContactDTO ,
                    studyParticipationContactDTO,
                    summary4organizationDTO ,
                    summary4studyResourcingDTO ,
                    responsiblePartyContactIi , 
                    studyRegAuthDTO, 
                    collaborators, 
                    participatingSites,
                    pgCdUpdatedList, UPDAT);

           } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
    }
    
    
    
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private void updateStudyProtocolObjs(
          StudyProtocolDTO studyProtocolDTO ,
          StudyOverallStatusDTO overallStatusDTO ,
          StudySiteDTO leadOrganizationSiteIdentifierDTO ,
          StudySiteDTO nctIdentifierSiteIdentifierDTO ,
          List<StudyIndldeDTO> studyIndldeDTOs ,
          List<StudyResourcingDTO> studyResourcingDTOs ,
          List<DocumentDTO> documentDTOs ,
          OrganizationDTO leadOrganizationDTO ,
          PersonDTO principalInvestigatorDTO,
          OrganizationDTO sponsorOrganizationDTO,
          StudyContactDTO studyContactDTO ,
          StudySiteContactDTO studySiteContactDTO, 
          OrganizationDTO summary4organizationDTO ,
          StudyResourcingDTO summary4studyResourcingDTO ,
          Ii responsiblePartyContactIi, 
          StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
          List<StudySiteDTO> collaborators, 
          List<StudySiteAccrualStatusDTO> participatingSites,
          List<StudySiteDTO> pgCdUpdatedList , String operation) throws PAException {
        
        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        Ii toStudyProtocolIi = null;
        validate(studyProtocolDTO, overallStatusDTO , operation);
        
        enforceBusinessRulesForUpdate(studyProtocolDTO ,
                                     overallStatusDTO ,
                                     studyContactDTO ,
                                     studySiteContactDTO ,
                                     studyIndldeDTOs ,
                                     studyResourcingDTOs ,
                                     studyRegAuthDTO, 
                                     participatingSites , operation);
      
       //update studyProtocol
        if (AMENDMENT.equalsIgnoreCase(operation)) {
            toStudyProtocolIi = paServiceUtils.copy(studyProtocolDTO.getIdentifier());
            String strTSR = tsrReportService.generateTSRHtml(studyProtocolDTO.getIdentifier());
            DocumentDTO docDto = new DocumentDTO();
            docDto.setStudyProtocolIdentifier(toStudyProtocolIi);
            docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
            docDto.setText(EdConverter.convertToEd(strTSR.getBytes()));
            docDto.setFileName(StConverter.convertToSt("TSR.html"));
            documentService.create(docDto);
            // reset milestones
            String sql = null;
            sql = "Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = "  + studyProtocolIi.getExtension();
            paServiceUtils.executeSql(sql);
            sql = "Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = "  
                + studyProtocolIi.getExtension();
            paServiceUtils.executeSql(sql);
            sql = "Delete from STUDY_ONHOLD WHERE STUDY_PROTOCOL_IDENTIFIER  = "  + studyProtocolIi.getExtension();
            paServiceUtils.executeSql(sql);
            createMilestone(studyProtocolIi);
            
            studyProtocolDTO.setAmendmentReasonCode(null);
        }

        studyProtocolService.updateStudyProtocol(studyProtocolDTO);
        studyProtocolIi = studyProtocolDTO.getIdentifier();
        paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierSiteIdentifierDTO);
        paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null) , studyProtocolIi);
        paServiceUtils.createOrUpdate(studyResourcingDTOs, 
                IiConverter.convertToStudyResourcingIi(null) , studyProtocolIi);
        
        if (UPDAT.equalsIgnoreCase(operation)) {
            paServiceUtils.createOrUpdate(collaborators, IiConverter.convertToStudySiteIi(null) , studyProtocolIi);
            updateParticipatingSites(participatingSites);
            paServiceUtils.createOrUpdate(pgCdUpdatedList, IiConverter.convertToStudySiteIi(null) , studyProtocolIi);
            if (studyRegAuthDTO != null) {
                List<StudyRegulatoryAuthorityDTO> sraDto = new ArrayList<StudyRegulatoryAuthorityDTO>();
                sraDto.add(studyRegAuthDTO);
                paServiceUtils.createOrUpdate(sraDto, 
                        IiConverter.convertToStudyRegulatoryAuthorityIi(null) , studyProtocolIi);

            }
            
        }
      
        paServiceUtils.createOrUpdate(documentDTOs , IiConverter.convertToDocumentIi(null) , 
                studyProtocolDTO.getIdentifier());
      
        paServiceUtils.removeResponsibleParty(studyProtocolDTO.getIdentifier());
        paServiceUtils.createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO, 
                sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
      
       // update summary4
      updateSummary4ResourcingDTO(summary4organizationDTO , summary4studyResourcingDTO);
      if (AMENDMENT.equalsIgnoreCase(operation)) {
          St localStudyProtocolIdentifier = null;
          if (leadOrganizationSiteIdentifierDTO != null) {
              localStudyProtocolIdentifier = leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier();
          }
          paServiceUtils.manageLeadOrganization(studyProtocolIi, leadOrganizationDTO, localStudyProtocolIdentifier);
          paServiceUtils.managePrincipalInvestigator(studyProtocolIi , leadOrganizationDTO , 
                  principalInvestigatorDTO , StudyTypeCode.INTERVENTIONAL);
          overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
          createStudyRelationship(studyProtocolIi , toStudyProtocolIi , studyProtocolDTO);
      } 
      studyOverallStatusService.create(overallStatusDTO);
      if (UPDAT.equalsIgnoreCase(operation)) {
          createInboxProcessingComments(documentDTOs, studyProtocolDTO);
      }
    }

    private Ii createStudyProtocolObjs(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            PersonDTO principalInvestigatorDTO ,
            OrganizationDTO sponsorOrganizationDTO ,
            StudySiteDTO leadOrganizationSiteIdentifierDTO ,
            StudySiteDTO nctIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studySiteContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            Ii responsiblePartyContactIi , String operation)
    throws PAException {
        validate(studyProtocolDTO, overallStatusDTO, operation);
        enforceBusinessRules(
                studyProtocolDTO,
                overallStatusDTO,
                documentDTOs,
                leadOrganizationDTO,
                principalInvestigatorDTO,
                sponsorOrganizationDTO,
                studyContactDTO,
                studySiteContactDTO,
                leadOrganizationSiteIdentifierDTO);

        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        if (!BlConverter.covertToBool(studyProtocolDTO.getFdaRegulatedIndicator()) 
                && (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty())) {
            studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
            studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));            
            
            // size of ind/ide > 0 
        }
        studyProtocolDTO.setIdentifier(null);
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            studyProtocolIi =  studyProtocolService.createInterventionalStudyProtocol(
                        (InterventionalStudyProtocolDTO) studyProtocolDTO);
            studyTypeCode = StudyTypeCode.INTERVENTIONAL;
        } else {
            studyProtocolIi =  studyProtocolService.createObservationalStudyProtocol(
                    (ObservationalStudyProtocolDTO) studyProtocolDTO);
            studyTypeCode = StudyTypeCode.OBSERVATIONAL;
        }
        createMilestone(studyProtocolIi);
        overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyOverallStatusService.create(overallStatusDTO);
        paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), studyProtocolIi);    
        paServiceUtils.createOrUpdate(studyResourcingDTOs, 
                IiConverter.convertToStudyResourcingIi(null), studyProtocolIi);    
        paServiceUtils.createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null), studyProtocolIi);
        createSummaryFour(studyProtocolIi , summary4organizationDTO , summary4studyResourcingDTO);
        St localStudyProtocolIdentifier = null;
        if (leadOrganizationSiteIdentifierDTO != null) {
            localStudyProtocolIdentifier = leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier();
        }
        paServiceUtils.manageLeadOrganization(studyProtocolIi , leadOrganizationDTO , localStudyProtocolIdentifier);
        paServiceUtils.managePrincipalInvestigator(studyProtocolIi , leadOrganizationDTO , 
                principalInvestigatorDTO , studyTypeCode);
        createSponsor(studyProtocolIi , sponsorOrganizationDTO);
        paServiceUtils.createResponsibleParty(studyProtocolIi, leadOrganizationDTO, principalInvestigatorDTO, 
                sponsorOrganizationDTO, responsiblePartyContactIi, studyContactDTO, studySiteContactDTO);
        paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierDTO);
        return studyProtocolIi;
    }

    private void createStudyRelationship(Ii fromStudyProtocolIi , Ii toStudyProtocolIi , StudyProtocolDTO spDto)
    throws PAException {
        StudyRelationshipDTO srDto = new StudyRelationshipDTO();
        srDto.setSequenceNumber(spDto.getSubmissionNumber());
        srDto.setSourceStudyProtocolIdentifier(toStudyProtocolIi);
        srDto.setTargetStudyProtocolIdentifier(fromStudyProtocolIi);
        srDto.setTypeCode(CdConverter.convertToCd(StudyRelationshipTypeCode.MOD));
        studyRelationshipService.create(srDto);
    }

    private void createSummaryFour(Ii studyProtocolIi , OrganizationDTO organizationDto ,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        SummaryFourFundingCategoryCode summaryFourFundingCategoryCode = null;
        if (organizationDto != null && organizationDto.getIdentifier() != null) {
            if (summary4studyResourcingDTO != null && !PAUtil.isCdNull(summary4studyResourcingDTO.getTypeCode())) {
                summaryFourFundingCategoryCode = SummaryFourFundingCategoryCode.getByCode(
                        summary4studyResourcingDTO.getTypeCode().getCode());
            }
            CorrelationUtils corrUtils = new CorrelationUtils();
            String orgPoIdentifier = organizationDto.getIdentifier().getExtension();
            if (orgPoIdentifier  == null) {
                throw new PAException(" Organization PO Identifier is null");
            }
           
            if (studyProtocolIi == null) {
                throw new PAException(PROTOCOL_ID_NULL);
            }
            StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
            if (spDTO == null) {
                throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi);
            }
            // Step 1 : get the PO Organization
            OrganizationDTO poOrg = null;
            try {
                poOrg = PoRegistry.getOrganizationEntityService()
                    .getOrganization(IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
            } catch (NullifiedEntityException e) {
                // Map m = e.getNullifiedEntities();
                // LOG.error("This Organization is no longer available instead use
                // ");
                throw new PAException("This Organization is no longer available instead use ", e);
            }
            // Step 3 : check for pa org, if not create one
            Organization paOrg = corrUtils.getPAOrganizationByIi(
                    IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
            if (paOrg == null) {
                paOrg = corrUtils.createPAOrganization(poOrg);
            }
            StudyResourcingDTO summary4ResoureDTO = new StudyResourcingDTO();
            summary4ResoureDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
            summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
            if (summaryFourFundingCategoryCode != null) {
                summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summaryFourFundingCategoryCode));
            }
            summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
            studyResourcingService.createStudyResourcing(summary4ResoureDTO);
        }
    }

    private void createSponsor(Ii studyProtocolIi , OrganizationDTO sponsorOrganizationDto) throws PAException {
        String orgPoIdentifier = sponsorOrganizationDto.getIdentifier().getExtension();
        if (orgPoIdentifier == null) {
            throw new PAException("Organization Identifer is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException(PROTOCOL_ID_NULL);
        }
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi.getExtension());
        }
        Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        StudySiteDTO studyPartDTO = new StudySiteDTO();
        studyPartDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudySiteFunctionalCode.SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studyPartDTO = studySiteService.create(studyPartDTO);
    }

    
    private void createMilestone(Ii studyProtocolIi) throws PAException {
        
        StudyMilestoneDTO smDto = new StudyMilestoneDTO();
        smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        smDto.setStudyProtocolIdentifier(studyProtocolIi);
        smDto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_RECEIVED));
        studyMilestoneService.create(smDto);
        
    }
    @SuppressWarnings({ "PMD" })
    private void enforceBusinessRules(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            PersonDTO principalInvestigatorDTO ,
            OrganizationDTO sponsorOrganizationDTO ,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studySiteContactDTO ,
            StudySiteDTO leadOrganizationSiteIdentifierDTO
            )
    throws PAException {
        StringBuffer sb = new StringBuffer();
        // validate of null objects
        sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");
        sb.append(documentDTOs == null ? "Document DTO's cannot be null , " : "");
        sb.append(leadOrganizationDTO == null ? "Lead Organization DTO cannot be null , " : "");
        sb.append(principalInvestigatorDTO == null ? "Principal Investigator DTO cannot be null , " : "");
        sb.append(sponsorOrganizationDTO == null ? "Sponsor Organization DTO cannot be null , " : "");
        if (studyContactDTO != null && studySiteContactDTO != null) {
            sb.append("Either StudyContactDTO or studySiteContactDTO should be null ,");
        }
        if (studyContactDTO == null && studySiteContactDTO == null) {
            sb.append("Either StudyContactDTO or studySiteContactDTO should not be null ,");
        }

        // validates for attributes
        sb.append(PAUtil.isStNull(studyProtocolDTO.getOfficialTitle()) ? "Official Title cannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
                ? "Trial Start Date Type cannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                ? "Primary Completion Date Type cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
                ? "Primary Completion Datecannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
        if (leadOrganizationSiteIdentifierDTO != null) {
            sb.append(PAUtil.isStNull(leadOrganizationSiteIdentifierDTO.getLocalStudyProtocolIdentifier())
                    ? "Local StudyProtocol Identifier cannot be null , " : "");
        }
        sb.append(PAUtil.isIiNull(leadOrganizationDTO.getIdentifier()) ? "Lead Organization cannot be null , " : "");
        sb.append(PAUtil.isIiNull(principalInvestigatorDTO.getIdentifier())
                ? "Principal Investigator cannot be null , " : "");
        sb.append(PAUtil.isIiNull(sponsorOrganizationDTO.getIdentifier())
                ? "Sponsor Organization cannot be null , " : "");
        if (studyContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.EMAIL) == null
                    ? EMAIL_NOT_NULL : "");
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.PHONE) == null
                    ? PHONE_NOT_NULL : "");
        }
        if (studySiteContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                    PAConstants.EMAIL) == null ? EMAIL_NOT_NULL : "");
            sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                    PAConstants.PHONE) == null  ? PHONE_NOT_NULL : "");

        }
        if (overallStatusDTO != null) {
            sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode())
                    ? "Current Trial Status cannot be null , " : "");
            sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate())
                    ? "Current Trial Status Date cannot be null , " : "");
        }
        if (sb.length() > 0) {
            throw new PAException("Validation Exception " + sb.toString());
        }

    }
    /**
     * 
     * @param studyProtocolDTO protocolDto
     * @param overallStatusDTO statusDto
     * @param isAmend amend
     * @return 
     * @throws PAException e
     */
    private void validate(StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO , String operation) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        if (REJECTION.equalsIgnoreCase(operation)) {
            DocumentWorkflowStatusDTO dws = docWrkFlowStatusService.getCurrentByStudyProtocol(
                    studyProtocolDTO.getIdentifier());
            if (!DocumentWorkflowStatusCode.SUBMITTED.getCode().equals(dws.getStatusCode().getCode())) {
                errorMsg.append("Protocol can be rejected only when Document Workflow status is REJECTED");
            }
        } else {
            studyOverallStatusService.validate(overallStatusDTO, studyProtocolDTO);
        }
        if (AMENDMENT.equalsIgnoreCase(operation) || UPDAT.equalsIgnoreCase(operation)) {
            DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
                    studyProtocolDTO.getIdentifier());
            String dwfs = isoDocWrkStatus.getStatusCode().getCode();
            String userCreated = StConverter.convertToString(
                    studyProtocolService.getStudyProtocol(studyProtocolDTO.getIdentifier()).getUserLastCreated());
            if (!userCreated.equalsIgnoreCase(StConverter.convertToString(studyProtocolDTO.getUserLastCreated()))) {
               errorMsg.append(operation).append("to Trial can be submitted by the submitter of the original Trial.\n");
            }
            StudyOverallStatusDTO statusDTO = studyOverallStatusService.getCurrentByStudyProtocol(
                    studyProtocolDTO.getIdentifier());
            if ((AMENDMENT.equalsIgnoreCase(operation))
                    && (!(dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())
                        || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())))) {
                    errorMsg.append("Trial with processing status Abstraction Verified Response or " 
                            + " Abstraction Verified No Response can be Amended.\n");
            }
            if ((UPDAT.equalsIgnoreCase(operation)) 
                && (dwfs.equals(DocumentWorkflowStatusCode.SUBMITTED.getCode())
                        || dwfs.equals(DocumentWorkflowStatusCode.REJECTED.getCode()))) {
                    errorMsg.append("Only Trials with processing status Accepted or Abstracted or  " 
                            + " Abstraction Verified No Response or  " 
                            + " Abstraction Verified No Response can be Updated.");
            }
            if (statusDTO.getStatusCode().getCode().equals(StudyStatusCode.DISAPPROVED)
                 || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.ADMINISTRATIVELY_COMPLETE)
                 || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.WITHDRAWN)
                 || statusDTO.getStatusCode().getCode().equals(StudyStatusCode.COMPLETE)) {
                errorMsg.append(operation).append(" to a Trial with Current Trial Status as Disapproved or" 
                        + " Withdrawn or Complete or Administratively Complete is not allowed.\n");
            }
        }
        if (errorMsg.length() > 0) {
                throw new PAException("Validation Exception " + errorMsg);
        }
    }
  
    /**
     * Enforce business rules for update.
     * 
     * @param studyProtocolDTO the study protocol dto
     * @param overallStatusDTO the overall status dto
     * @param documentDTOs the document dt os
     * @param leadOrganizationDTO the lead organization dto
     * @param principalInvestigatorDTO the principal investigator dto
     * @param sponsorOrganizationDTO the sponsor organization dto
     * @param studyContactDTO the study contact dto
     * @param studySiteContactDTO the study site contact dto
     * @param studyIndldeDTOs the study indlde dt os
     * @param studyResourcingDTOs the study resourcing dt os
     * @param studyRegAuthDTO the study reg auth dto
     * @param collaborators the collaborators
     * @param participatingSites the participating sites
     * 
     * @throws PAException the PA exception
     */
    private void enforceBusinessRulesForUpdate(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            StudyContactDTO studyContactDTO ,
            StudySiteContactDTO studySiteContactDTO ,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, 
            List<StudySiteAccrualStatusDTO> participatingSites , String operation) throws PAException {
        StringBuffer sb = new StringBuffer();
        // validate of null objects
        sb.append(studyProtocolDTO == null ? "Study Protocol DTO cannot be null , " : "");
        sb.append(overallStatusDTO == null ? "Study OverallStatus DTO cannot be null , " : "");
        if (studyContactDTO != null && studySiteContactDTO != null) {
            sb.append("Either StudyContactDTO or studySiteContactDTO should be null ,");
        }
        if (studyContactDTO == null && studySiteContactDTO == null) {
            sb.append("Either StudyContactDTO or studySiteContactDTO should not be null ,");
        }
        // validates for attributes
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getStartDateTypeCode())
                ? "Trial Start Date Type cannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPrimaryCompletionDateTypeCode())
                ? "Primary Completion Date Type cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getStartDate()) ? "Trial Start Date cannot be null , " : "");
        sb.append(PAUtil.isTsNull(studyProtocolDTO.getPrimaryCompletionDate())
                ? "Primary Completion Datecannot be null , " : "");
        sb.append(PAUtil.isCdNull(studyProtocolDTO.getPhaseCode()) ? "Phase cannot be null , " : "");
        if (studyContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.EMAIL) == null
                    ? EMAIL_NOT_NULL : "");
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.PHONE) == null
                    ? PHONE_NOT_NULL : "");
        }
        if (studySiteContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                    PAConstants.EMAIL) == null ? EMAIL_NOT_NULL : "");
            sb.append(DSetConverter.getFirstElement(studySiteContactDTO.getTelecomAddresses(),
                    PAConstants.PHONE) == null  ? PHONE_NOT_NULL : "");

        }
        if (overallStatusDTO != null) {
            sb.append(PAUtil.isCdNull(overallStatusDTO.getStatusCode())
                    ? "Current Trial Status cannot be null , " : "");
            sb.append(PAUtil.isTsNull(overallStatusDTO.getStatusDate())
                    ? "Current Trial Status Date cannot be null , " : "");
        }
        if (sb.length() > 0) {
            throw new PAException("Validation Exception " + sb.toString());
        }
        paServiceUtils.enforceNoDuplicateIndIde(studyIndldeDTOs, studyProtocolDTO);
        paServiceUtils.enforceNoDuplicateGrants(studyResourcingDTOs);
        if (UPDAT.equalsIgnoreCase(operation)) {
            enforceRegulatoryInfo(studyProtocolDTO, studyRegAuthDTO , studyIndldeDTOs);
            enforceRecruitmentStatus(studyProtocolDTO, participatingSites);
        }
    }
  
    private void enforceRegulatoryInfo(StudyProtocolDTO studyProtocolDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO , List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        if (studyRegAuthDTO == null) {
            errMsg.append("Regulatory Information fields must be Entered.\n");
        }
        DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
              studyProtocolDTO.getIdentifier());
        String dwfs = isoDocWrkStatus.getStatusCode().getCode();
        if (dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTED.getCode())
              || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())
                  || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())) {
          
            if (PAConstants.YES.equalsIgnoreCase(
                    BlConverter.convertBLToString(studyProtocolDTO.getFdaRegulatedIndicator()))
                    && PAConstants.NO.equalsIgnoreCase(
                            BlConverter.convertBLToString(studyProtocolDTO.getSection801Indicator()))) {
                errMsg.append("Section 801 is required if FDA Regulated indicator is true.");
            }
            
            List<PlannedActivityDTO> paList = 
                plannedActivityService.getByStudyProtocol(studyProtocolDTO.getIdentifier());            
          //Display error in abstraction validation if section 801 indicator = ‘yes’,  
            if (PAConstants.YES.equalsIgnoreCase(
                    BlConverter.convertBLToString(studyProtocolDTO.getSection801Indicator()))
                    && PAConstants.YES.equalsIgnoreCase(
                            BlConverter.convertBLToString(studyProtocolDTO.getDelayedpostingIndicator()))
                    && !paServiceUtils.isDeviceFound(studyProtocolDTO.getIdentifier() , paList)) {
                errMsg.append("Delay posting indicator can only be set to \'yes\' " 
                    + " if study includes at least one intervention with type \'device\'.");
            }
            if (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty()) { 
                if (PAConstants.NO.equalsIgnoreCase(BlConverter.convertBLToString(
                        studyProtocolDTO.getFdaRegulatedIndicator()))) {
                    errMsg.append("FDA Regulated Intervention Indicator must be Yes " 
                      +                       " since it has Trial IND/IDE records.\n");         
                }
                Long sraId = Long.valueOf(studyRegAuthDTO.getRegulatoryAuthorityIdentifier().getExtension());
                //doing this just to load the country since its lazy loaded. 
                Country country = regulatoryInfoBean.getRegulatoryAuthorityCountry(sraId);
                String regAuthName = regulatoryInfoBean.getCountryOrOrgName(sraId, "RegulatoryAuthority");
                if (country.getAlpha3().equals("USA")  
                    && !regAuthName.equalsIgnoreCase("Food and Drug Administration")) {
                    errMsg.append("For IND protocols, Oversight Authorities "
                          + " must include United States: Food and Drug Administration.\n");
                }
          }
          if (errMsg.length() > 1) {
              throw new PAException(errMsg.toString());
          }
      } 
  }   
    @SuppressWarnings({"PMD" })
    private void enforceRecruitmentStatus(StudyProtocolDTO studyProtocolDTO,  
        List<StudySiteAccrualStatusDTO> participatingSites) throws PAException {
        if (participatingSites != null && !participatingSites.isEmpty()) { 
              StudyRecruitmentStatusDTO recruitmentStatusDto = 
                  studyRecruitmentStatusServiceRemote.getCurrentByStudyProtocol(studyProtocolDTO.getIdentifier());
              if (StudyRecruitmentStatusCode.RECRUITING_ACTIVE.getCode().
                      equalsIgnoreCase(recruitmentStatusDto.getStatusCode().getCode())) {
                  boolean recruiting = false;
                  StudySiteAccrualStatusDTO latestDTO = null;
                  List<StudySiteAccrualStatusDTO> participatingSitesOld = null;
                  for (StudySiteAccrualStatusDTO studySiteAccuralStatus : participatingSites) {
                      Long latestId = IiConverter.convertToLong(studySiteAccuralStatus.getIdentifier());
                      //base condition if one of the newly changed status is recruiting ;then break
                      if (latestId == null) {
                          if (RecruitmentStatusCode.RECRUITING.getCode().
                                  equalsIgnoreCase(studySiteAccuralStatus.getStatusCode().getCode())) {
                              recruiting = true;
                              break;
                          } else if (!RecruitmentStatusCode.RECRUITING.getCode().
                                  equalsIgnoreCase(studySiteAccuralStatus.getStatusCode().getCode())) {
                              continue;
                          }
                      } else {
                          participatingSitesOld = new ArrayList<StudySiteAccrualStatusDTO>();
                          participatingSitesOld.add(studySiteAccuralStatus);
                      }
                  }
                  if (participatingSitesOld != null && !participatingSitesOld.isEmpty()) { 
                      //else sort the old statuses and the get the latest
                      Collections.sort(participatingSitesOld, new Comparator<StudySiteAccrualStatusDTO>() {
                          public int compare(StudySiteAccrualStatusDTO o1, StudySiteAccrualStatusDTO o2) {
                              return o1.getIdentifier().getExtension().compareToIgnoreCase(
                                      o2.getIdentifier().getExtension());
                          }
                      });
                      latestDTO = participatingSitesOld.get(participatingSitesOld.size() - 1);
                      if (latestDTO != null && RecruitmentStatusCode.RECRUITING.getCode().
                              equalsIgnoreCase(latestDTO.getStatusCode().getCode())) {
                          recruiting = true;
                      }
                      if (!recruiting) {
                              new PAException("Data inconsistency: Atleast one location needs to be recruiting"
                                      + " if the overall status recruitment status is\'Recruiting\'");   
                      }
                  }   
              }
        } 
    }

    private void updateParticipatingSites(
            List<StudySiteAccrualStatusDTO> participatingSites)
            throws PAException {
        if (participatingSites != null) {
            for (StudySiteAccrualStatusDTO sdto : participatingSites) {
                studySiteAccrualStatusService.createStudySiteAccrualStatus(sdto);
            }
        }
    }


    private void updateSummary4ResourcingDTO(OrganizationDTO organizationDto,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        if (organizationDto != null && organizationDto.getIdentifier() != null) {
            CorrelationUtils corrUtils = new CorrelationUtils();
            String orgPoIdentifier = organizationDto.getIdentifier()
                    .getExtension();
            if (orgPoIdentifier == null) {
                throw new PAException(" Organization PO Identifier is null");
            }
            // Step 1 : get the PO Organization
            OrganizationDTO poOrg = null;
            try {
                poOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                                IiConverter.convertToPoOrganizationIi(orgPoIdentifier));
            } catch (NullifiedEntityException e) {
                throw new PAException(
                        "This Organization is no longer available instead use ", e);
            }
            // Step 3 : check for pa org, if not create one
            Organization paOrg = corrUtils.getPAOrganizationByIi(IiConverter
                    .convertToPoOrganizationIi(orgPoIdentifier));

            if (paOrg == null) {
                paOrg = corrUtils.createPAOrganization(poOrg);
            }
            summary4studyResourcingDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
            studyResourcingService.updateStudyResourcing(summary4studyResourcingDTO);
        }
    }
    /**
     * 
     * @param studyProtocolDTO spDTO
     * @param siteOverallStatusDTO siteStatusDto
     * @param studyIndldeDTOs indDTO
     * @param studyResourcingDTOs  grants 
     * @param documentDTOs documents
     * @param leadOrganizationDTO lead Org 
     * @param leadOrganizationTrialIdentifier LeadOrgTrialId
     * @param siteInvestigatorDTO    SitePi
     * @param studySiteDTO participating Org
     * @param siteDTO local id,siteProgramCodeText and date range
     * @param nctIdentifierSiteIdentifier nct
     * @param summary4organizationDTO sum4 org
     * @param summary4CategoryCode summary 4Category Code 
     * @return Ii
     * @throws PAException e
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createInterventionalProprietaryStudyProtocol(
            StudyProtocolDTO studyProtocolDTO ,
            StudySiteOverallStatusDTO siteOverallStatusDTO ,
            List<StudyIndldeDTO> studyIndldeDTOs ,
            List<StudyResourcingDTO> studyResourcingDTOs ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            St leadOrganizationTrialIdentifier ,
            PersonDTO siteInvestigatorDTO ,
            OrganizationDTO studySiteDTO ,
            StudySiteDTO siteDTO ,
            St nctIdentifierSiteIdentifier,
            OrganizationDTO summary4organizationDTO ,
            Cd summary4CategoryCode)
    throws PAException {
        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        //validate method needs to be here
        try {
            if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
                studyProtocolIi =  studyProtocolService.createInterventionalStudyProtocol(
                            (InterventionalStudyProtocolDTO) studyProtocolDTO);
                studyTypeCode = StudyTypeCode.INTERVENTIONAL;
            } else {
                studyProtocolIi =  studyProtocolService.createObservationalStudyProtocol(
                        (ObservationalStudyProtocolDTO) studyProtocolDTO);
                studyTypeCode = StudyTypeCode.OBSERVATIONAL;
            }
            createMilestone(studyProtocolIi);
            paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null),
                    studyProtocolIi);    
            paServiceUtils.createOrUpdate(studyResourcingDTOs, 
                    IiConverter.convertToStudyResourcingIi(null), studyProtocolIi);    
            paServiceUtils.createOrUpdate(documentDTOs, IiConverter.convertToDocumentIi(null), studyProtocolIi);
            StudyResourcingDTO summary4studyResourcingDTO  = new StudyResourcingDTO();
            summary4studyResourcingDTO.setTypeCode(summary4CategoryCode);
            createSummaryFour(studyProtocolIi , summary4organizationDTO , summary4studyResourcingDTO);
          
            paServiceUtils.manageLeadOrganization(studyProtocolIi , leadOrganizationDTO ,
                    leadOrganizationTrialIdentifier);
            StudySiteDTO nctIdentifierDTO = new StudySiteDTO();
            nctIdentifierDTO.setLocalStudyProtocolIdentifier(nctIdentifierSiteIdentifier);
            paServiceUtils.manageNCTIdentifier(studyProtocolIi, nctIdentifierDTO);
    
            //create StudySite
            Ii studySiteIi = createStudySite(studyProtocolIi, studySiteDTO, siteDTO);
            siteOverallStatusDTO.setStudySiteIdentifier(studySiteIi);
            studySiteOverallStatusService.create(siteOverallStatusDTO);
            //set PI
            createStudySiteContact(studySiteIi, studyProtocolIi, studySiteDTO, siteInvestigatorDTO, studyTypeCode);
            //
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }

        return studyProtocolIi;
    }
    
    /**
     * Reject a protocol and rollback all the changes.
     * @param studyProtocolIi study protocol identifier 
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void reject(Ii studyProtocolIi) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDto = studyProtocolService.getStudyProtocol(studyProtocolIi);
            validate(studyProtocolDto, null , REJECTION);
            Ii targetSpIi = null;
            Ii sourceSpIi = null;
            StudyRelationshipDTO srDto = new StudyRelationshipDTO();
            srDto.setTargetStudyProtocolIdentifier(studyProtocolIi);
            LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
            List<StudyRelationshipDTO> dtos = null;
            try {
                dtos = studyRelationshipService.search(srDto, limit);
            } catch (TooManyResultsException e) {
                throw new PAException(e);
            }
    
            for (StudyRelationshipDTO dto : dtos) {
                targetSpIi = dto.getTargetStudyProtocolIdentifier(); // target - original - 30599
                sourceSpIi = dto.getSourceStudyProtocolIdentifier(); // newly created source - 30605 new 
                break;
            }
            if (targetSpIi == null) {
                throw new PAException("Study Relationshit not found for the Amended Protocol");
               
            }
            StudyProtocolDTO sourceSpDto = studyProtocolService.getStudyProtocol(sourceSpIi);
            // overwrite with the target
            sourceSpDto.setIdentifier(targetSpIi);
            sourceSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
            studyProtocolService.updateStudyProtocol(sourceSpDto);
            
            Ii sourceIi = null;
            List<StudyContactDTO> studyContactDtos = 
                paServiceUtils.getStudyContact(sourceSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
            StudyContactDTO scSourceDTO = null;
            if (PAUtil.getFirstObj(studyContactDtos) != null) {
                scSourceDTO = PAUtil.getFirstObj(studyContactDtos);
            } else {
                throw new PAException("Source Study Principal Investigator is not available");
            }        
            sourceIi = scSourceDTO.getIdentifier();
            studyContactDtos = 
                paServiceUtils.getStudyContact(targetSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
            StudyContactDTO scTargetDTO = null;
            if (PAUtil.getFirstObj(studyContactDtos) != null) {
                scTargetDTO = PAUtil.getFirstObj(studyContactDtos);
            } else {
                throw new PAException("Target Study Principal Investigator is not available");
            }
            
            // replace target with source
            scSourceDTO.setIdentifier(scTargetDTO.getIdentifier());
            scSourceDTO.setStudyProtocolIdentifier(targetSpIi);
            studyContactService.delete(sourceIi);
            studyContactService.update(scSourceDTO);
    
            List<StudySiteDTO> studySiteDtos = 
                paServiceUtils.getStudySite(sourceSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION, true);
            StudySiteDTO ssSourceDTO = null;
            if (PAUtil.getFirstObj(studySiteDtos) != null) {
                ssSourceDTO = PAUtil.getFirstObj(studySiteDtos);
            } else {
                throw new PAException("Source Lead Organization is not available");
            }
            sourceIi = ssSourceDTO.getIdentifier();
            studySiteDtos = paServiceUtils.getStudySite(targetSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION, true);
            StudySiteDTO ssTargetDTO = null;
            if (PAUtil.getFirstObj(studySiteDtos) != null) {
                ssTargetDTO = PAUtil.getFirstObj(studySiteDtos);
            } else {
                throw new PAException("Target Lead Organization is not available");
            }
            ssSourceDTO.setIdentifier(ssTargetDTO.getIdentifier());
            ssSourceDTO.setStudyProtocolIdentifier(targetSpIi);
            studySiteService.delete(sourceIi);
            studySiteService.update(ssSourceDTO);
            
            paServiceUtils.executeSql(deleteAndReplace(sourceSpIi , targetSpIi));
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        
    }
    
    private List<String> deleteAndReplace(Ii sourceIi, Ii targetIi) {
        String sqlUpd = targetIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceIi.getExtension();
        List<String> sqls = new ArrayList<String>();
        String targetId = targetIi.getExtension();
        sqls.add("Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_MILESTONE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE DOCUMENT_WORKFLOW_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sourceIi.getExtension());
        sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId 
                + " and TYPE_CODE = '" + DocumentTypeCode.TSR.getName() + "'");
        sqls.add("UPDATE DOCUMENT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("UPDATE STUDY_ONHOLD SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_OVERALL_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_OVERALL_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_RECRUITMENT_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_RECRUITMENT_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("DELETE FROM STUDY_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId 
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("UPDATE STUDY_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd 
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("DELETE FROM STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND FUNCTIONAL_CODE IN " 
            + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND FUNCTIONAL_CODE IN " 
                + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("DELETE FRON STUDY_SITE_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND ROLE_CODE IN " 
            + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        sqls.add("UPDATE STUDY_SITE_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND ROLE_CODE IN " 
               +  "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        sqls.add("Delete from STUDY_RELATION WHERE TARGET_STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension());
        sqls.add("Delete from STUDY_PROTOCOL WHERE IDENTIFIER  = " + sourceIi.getExtension());
        return sqls;
    }

    private Ii createStudySite(Ii studyProtocolIi,
            OrganizationDTO studySiteDTO , StudySiteDTO siteDTO) throws PAException {
        
        Long paHealthCareFacilityId = ocsr.createHealthCareFacilityCorrelations(
                studySiteDTO.getIdentifier().getExtension());
        StudySiteDTO sp = new StudySiteDTO();
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        sp.setHealthcareFacilityIi(IiConverter.convertToIi(paHealthCareFacilityId));
        sp.setIdentifier(null);
        sp.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        sp.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        sp.setStudyProtocolIdentifier(studyProtocolIi);
        sp.setProgramCodeText(siteDTO.getProgramCodeText());
        sp.setLocalStudyProtocolIdentifier(siteDTO.getLocalStudyProtocolIdentifier());
        sp.setAccrualDateRange(siteDTO.getAccrualDateRange());
        sp = studySiteService.create(sp);
        return sp.getIdentifier();
    }
    
    private Ii createStudySiteContact(Ii studySiteIi , Ii studyProtocolIi, OrganizationDTO siteDto, PersonDTO piDto,
            StudyTypeCode studyTypeCode) throws PAException {
         Ii studySiteContactIi = null;
         String orgPoIdentifier = siteDto.getIdentifier().getExtension();
         String perIdentifier = piDto.getIdentifier().getExtension();
         StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
         studySiteContactDTO.setStudySiteIi(studySiteIi);
         Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean().
            createClinicalResearchStaffCorrelations(orgPoIdentifier, perIdentifier);
         Long healthCareProviderIi = null;
         if (studyTypeCode.equals(StudyTypeCode.INTERVENTIONAL)) {
            healthCareProviderIi = new HealthCareProviderCorrelationBean().createHealthCareProviderCorrelationBeans(
                orgPoIdentifier, perIdentifier);
        }
         studySiteContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
        if (healthCareProviderIi != null) {
            studySiteContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));
        }
        studySiteContactDTO.setRoleCode(CdConverter.convertToCd(
                    StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));
        
        studySiteContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteContactDTO.setStatusCode(CdConverter.convertStringToCd(
                FunctionalRoleStatusCode.PENDING.getCode()));
        
        
        studySiteContactService.create(studySiteContactDTO);
        return studySiteContactIi;
    }
    
    @SuppressWarnings({"PMD" })
    private void createInboxProcessingComments(List<DocumentDTO> documentDTOs, StudyProtocolDTO studyProtocolDTO) 
    throws PAException {
      StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
      studyInboxDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
      studyInboxDTO.setInboxDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
      boolean docUpdated = false;
      boolean trialUpdateForReview = false;
      StringBuffer stringBuffer = new StringBuffer();
      DocumentWorkflowStatusDTO isoDocWrkStatus = docWrkFlowStatusService.getCurrentByStudyProtocol(
               studyProtocolDTO.getIdentifier());
      if (documentDTOs != null && !documentDTOs.isEmpty()) {
        for (DocumentDTO doc : documentDTOs) {
          if (DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode().equals(
                  CdConverter.convertCdToString(doc.getTypeCode()))) {
               docUpdated = true;
               break;
          }
        }
       }
       if (docUpdated) {
          stringBuffer.append("IRB Document was updated\n");
          studyInboxDTO.setComments(StConverter.convertToSt(stringBuffer.toString()));
          trialUpdateForReview = true;
       }
      
       String dwfs = isoDocWrkStatus.getStatusCode().getCode();
       if (dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTED.getCode())
             || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getCode())
                 || dwfs.equals(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getCode())) {
         List<AbstractionCompletionDTO> errorList =
               abstractionCompletionService.validateAbstractionCompletion(studyProtocolDTO.getIdentifier());
           if (!errorList.isEmpty()) {
            stringBuffer.append("<b>Type :</b>  <b>Description :</b> <b>Comments :</b>\n");
            for (AbstractionCompletionDTO abDTO : errorList) {
              stringBuffer.append(abDTO.getErrorType()).append(":").append(abDTO.getErrorDescription())
                          .append(":").append(abDTO.getComment()).append("\n");
            }
           }
           studyInboxDTO.setComments(StConverter.convertToSt(stringBuffer.toString()));
           trialUpdateForReview = true;
       }
       if (trialUpdateForReview) {
        //create 
        studyInboxServiceLocal.create(studyInboxDTO);
       } 
    }   

}
