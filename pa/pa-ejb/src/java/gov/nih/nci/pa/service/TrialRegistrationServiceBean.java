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

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyRelationshipTypeCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.service.exception.PADuplicateException;
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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    SubGroupsServiceLocal subGroupsService = null;
    @EJB
    StudyParticipationServiceLocal studyParticipationService = null;
    @EJB
    StudyParticipationContactServiceLocal studyParticipationContactService = null;
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
    private static final String PROTOCOL_ID_NULL = "Study Protocol Identifer is null";
    private static final String NO_PROTOCOL_FOUND = "No Study Protocol found for = ";
    
//    @EJB
//    DocumentWorkflowStatusServiceLocal dwsService = null;
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
     * @param leadOrganizationParticipationIdentifierDTO local protocol identifier
     * @param nctIdentifierParticipationIdentifierDTO nct Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactDTO name of the person when sponsor is responsible
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
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO ,
            StudyParticipationDTO nctIdentifierParticipationIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudyParticipationContactDTO studyParticipationContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            PersonDTO responsiblePartyContactDTO)
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
                leadOrganizationParticipationIdentifierDTO ,
                nctIdentifierParticipationIdentifierDTO ,
                studyContactDTO ,
                studyParticipationContactDTO ,
                summary4organizationDTO ,
                summary4studyResourcingDTO ,
                responsiblePartyContactDTO , false);
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
     * @param leadOrganizationParticipationIdentifierDTO local protocol identifier
     * @param nctIdentifierParticipationIdentifierDTO nct Identifier
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO phone and email info when sponsor is responsible
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactDTO name of the person when sponsor is responsible
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
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO ,
            StudyParticipationDTO nctIdentifierParticipationIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudyParticipationContactDTO studyParticipationContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            PersonDTO responsiblePartyContactDTO)
    throws PAException {
        Ii fromStudyProtocolii = null;
        Ii toStudyProtocolIi = null;

        try {
            fromStudyProtocolii = studyProtocolDTO.getIdentifier();
            toStudyProtocolIi = createStudyProtocolObjs(
                    studyProtocolDTO ,
                    overallStatusDTO ,
                    studyIndldeDTOs ,
                    studyResourcingDTOs ,
                    documentDTOs ,
                    leadOrganizationDTO ,
                    principalInvestigatorDTO ,
                    sponsorOrganizationDTO ,
                    leadOrganizationParticipationIdentifierDTO ,
                    nctIdentifierParticipationIdentifierDTO ,
                    studyContactDTO ,
                    studyParticipationContactDTO ,
                    summary4organizationDTO ,
                    summary4studyResourcingDTO ,
                    responsiblePartyContactDTO , true);

            deepCopy(fromStudyProtocolii , toStudyProtocolIi);
            deepCopyParticipation(fromStudyProtocolii , toStudyProtocolIi);
        } catch (Exception e) {
            ejbContext.setRollbackOnly();
            throw new PAException(e);
        }
        return toStudyProtocolIi;

    }

    private void deepCopy(Ii fromStudyProtocolIi , Ii toStudyProtocolIi) throws PAException {
        studyDiseaseService.copy(fromStudyProtocolIi, toStudyProtocolIi);
        copyPlannedActivityArmArmInterventions(fromStudyProtocolIi, toStudyProtocolIi);
        subGroupsService.copy(fromStudyProtocolIi, toStudyProtocolIi);
        plannedActivityService.copyPlannedEligibilityStudyCriterions(fromStudyProtocolIi, toStudyProtocolIi);
        studyOutcomeMeasureService.copy(fromStudyProtocolIi, toStudyProtocolIi);
        studyRegulatoryAuthorityService.copy(fromStudyProtocolIi, toStudyProtocolIi);
    }

    private void copyPlannedActivityArmArmInterventions(Ii fromStudyProtocolIi , Ii toStudyProtocolIi)
    throws PAException {
        List<PlannedActivityDTO> dtos = plannedActivityService.getByStudyProtocol(fromStudyProtocolIi);
        Map<String , Ii> paMap = new HashMap<String , Ii>();
        Ii oldPaIi;
        Ii newPaIi;
        for (PlannedActivityDTO dto : dtos) {
            if (CdConverter.convertCdToString(dto.getCategoryCode()).equalsIgnoreCase("INTERVENTION")) {
                oldPaIi = dto.getIdentifier();
                dto.setIdentifier(null);
                dto.setStudyProtocolIdentifier(toStudyProtocolIi);
                newPaIi = plannedActivityService.create(dto).getIdentifier();
                paMap.put(oldPaIi.getExtension(), newPaIi);
            }
        }

        List<ArmDTO> armDtos = armService.getByStudyProtocol(fromStudyProtocolIi);
        for (ArmDTO armDto : armDtos) {
                armDto.setInterventions(getAssociatedInterventions(armDto.getIdentifier() , paMap));
                armDto.setIdentifier(null);
                armDto.setStudyProtocolIdentifier(toStudyProtocolIi);
                armService.create(armDto);
        }
    }

    private DSet<Ii> getAssociatedInterventions(Ii armIi , Map<String , Ii> paMap) throws PAException {
        List<PlannedActivityDTO> dtos = null;
        Set<Ii> iiSet = new HashSet<Ii>();
        boolean armIntFound = false;
        dtos = plannedActivityService.getByArm(armIi);
        for (PlannedActivityDTO paDto : dtos) {
            if (paMap.containsKey(paDto.getIdentifier().getExtension())) {
                armIntFound = true;
                iiSet.add(paMap.get(paDto.getIdentifier().getExtension()));
            }
        }
        DSet<Ii> interventions = null;
        if (armIntFound) {
            interventions = new DSet<Ii>();
            interventions.setItem(iiSet);
        }
        return interventions;

    }
    private void deepCopyParticipation(Ii fromStudyProtocolIi , Ii toStudyProtocolIi) throws PAException {

        ArrayList<StudyParticipationDTO> criteriaList = null;
        StudyParticipationDTO searchCode = null;

        // check for site related of IRB
        criteriaList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipationFunctionalCode cd : StudyParticipationFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                searchCode = new StudyParticipationDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        searchCode = new StudyParticipationDTO();
        searchCode.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.TREATING_SITE));
        criteriaList.add(searchCode);
        //IRB
        searchCode = new StudyParticipationDTO();
        searchCode.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode
                .STUDY_OVERSIGHT_COMMITTEE));
        criteriaList.add(searchCode);
        
        List<StudyParticipationDTO> dtos =  studyParticipationService.getByStudyProtocol(
                fromStudyProtocolIi , criteriaList);
        StudyParticipationDTO newSpDto = null;
        List<StudyParticipationContactDTO> spcDtos = null;
        List<StudySiteAccrualStatusDTO> accDtos = null;
        Ii oldSpIi = null;
        for (StudyParticipationDTO dto : dtos) {
            oldSpIi = dto.getIdentifier();
            dto.setIdentifier(null);
            dto.setStudyProtocolIdentifier(toStudyProtocolIi);
            try {
                newSpDto = studyParticipationService.create(dto);
            } catch (PADuplicateException pud) {
                continue;
            }

            if (StudyParticipationFunctionalCode.TREATING_SITE.getCode().equals(dto.getFunctionalCode().getCode())) {
                // create study contact
                spcDtos = studyParticipationContactService.getByStudyParticipation(oldSpIi);
                for (StudyParticipationContactDTO spcDto : spcDtos) {
                    spcDto.setIdentifier(null);
                    spcDto.setStudyProtocolIdentifier(toStudyProtocolIi);
                    spcDto.setStudyParticipationIi(newSpDto.getIdentifier());
                    studyParticipationContactService.create(spcDto);
                }

                // create study accrual status
                accDtos = studySiteAccrualStatusService.getStudySiteAccrualStatusByStudyParticipation(oldSpIi);
                for (StudySiteAccrualStatusDTO accDto : accDtos) {
                    accDto.setIdentifier(null);
                    accDto.setStudyParticipationIi(newSpDto.getIdentifier());
                    studySiteAccrualStatusService.createStudySiteAccrualStatus(accDto);
                }
            }
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
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO ,
            StudyParticipationDTO nctIdentifierDTO ,
            StudyContactDTO studyContactDTO ,
            StudyParticipationContactDTO studyParticipationContactDTO ,
            OrganizationDTO summary4organizationDTO ,
            StudyResourcingDTO summary4studyResourcingDTO ,
            PersonDTO responsiblePartyContactDTO  , boolean isAmend)
    throws PAException {

        enforceBusinessRules(
                studyProtocolDTO,
                overallStatusDTO,
                documentDTOs,
                leadOrganizationDTO,
                principalInvestigatorDTO,
                sponsorOrganizationDTO,
                studyContactDTO,
                studyParticipationContactDTO,
                leadOrganizationParticipationIdentifierDTO);

        Ii oldIdentifier =  null;
        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        StudyProtocolDTO oldSpDto = null;
        StudyProtocolDTO newSpDto = null;
        if (isAmend) {
            studyProtocolDTO.setAmendmentReasonCode(null);
            oldIdentifier = studyProtocolDTO.getIdentifier();
            //Amendment UC generate the TSR and add in document
            String strTSR = tsrReportService.generateTSRHtml(oldIdentifier);
            DocumentDTO docDto = new DocumentDTO();
            docDto.setStudyProtocolIdentifier(oldIdentifier);
            docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
            docDto.setText(EdConverter.convertToEd(strTSR.getBytes()));
            docDto.setFileName(StConverter.convertToSt("TSR.html"));
            documentService.create(docDto);
            oldSpDto = studyProtocolService.getStudyProtocol(oldIdentifier);
            oldSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
            studyProtocolService.updateStudyProtocol(oldSpDto);
        }
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
        ////// todo : when batch upload is changed to use the business service 
        createMilestone(studyProtocolIi);
        newSpDto = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (isAmend) {
            createStudyRelationship(studyProtocolIi , oldIdentifier , newSpDto);
        }
        overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
        if (isAmend) {
            createOverallStatuses(studyProtocolIi, oldIdentifier , overallStatusDTO);
        } else {
            studyOverallStatusService.create(overallStatusDTO);
        }
        if (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty()) {
            createIndIdes(studyProtocolIi , studyIndldeDTOs);
        }
        if (studyResourcingDTOs != null && !studyResourcingDTOs.isEmpty()) {
            createStudyResources(studyProtocolIi , studyResourcingDTOs);
        }
        createdocuments(studyProtocolIi , documentDTOs);
        createSummaryFour(studyProtocolIi , summary4organizationDTO , summary4studyResourcingDTO);
        createLeadOrganization(studyProtocolIi , leadOrganizationDTO , leadOrganizationParticipationIdentifierDTO);
        createPrincipalInvestigator(studyProtocolIi , leadOrganizationDTO , principalInvestigatorDTO , studyTypeCode);
        createSponsor(studyProtocolIi , sponsorOrganizationDTO);
        if (studyContactDTO != null) {
            // pi is responsible
            createPIAsResponsibleParty(studyProtocolIi , leadOrganizationDTO ,
                    principalInvestigatorDTO , studyContactDTO);
        } else if (studyParticipationContactDTO != null) {
            createSponsorAsPrimaryContact(studyProtocolIi, sponsorOrganizationDTO, responsiblePartyContactDTO,
                    studyParticipationContactDTO);
        }

        createNCTidentifier(studyProtocolIi , nctIdentifierDTO);
        return studyProtocolIi;
    }
//    private void createDocumentWorkFlowStatus(Ii studyProtocolIi) throws PAException {
//        DocumentWorkflowStatusDTO dwDto = new DocumentWorkflowStatusDTO();
//        dwDto.setStatusCode(CdConverter.convertToCd(DocumentWorkflowStatusCode.SUBMITTED));
//        dwDto.setStatusDateRange(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
//        dwDto.setStudyProtocolIdentifier(studyProtocolIi);
//        dwsService.create(dwDto);
//    }

    private void createNCTidentifier(Ii studyProtocolIi , StudyParticipationDTO nctIdentifierDTO)
    throws PAException {
        if (nctIdentifierDTO == null || nctIdentifierDTO.getLocalStudyProtocolIdentifier() == null
                ||  PAUtil.isEmpty(nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue())) {
            return;
        }
        StudyParticipationDTO spDto =  new StudyParticipationDTO();
        String poOrgId = ocsr.getCtGovPOIdentifier();
        long roId = ocsr.createResearchOrganizationCorrelations(poOrgId);
        spDto.setStudyProtocolIdentifier(studyProtocolIi);
        spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
        spDto.setLocalStudyProtocolIdentifier(nctIdentifierDTO.getLocalStudyProtocolIdentifier());
        spDto.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyParticipationService.create(spDto);
    }

    private void createStudyRelationship(Ii newStudyProtocolIi , Ii oldStudyProtocolIi , StudyProtocolDTO spDto)
    throws PAException {
        StudyRelationshipDTO srDto = new StudyRelationshipDTO();
        srDto.setSequenceNumber(spDto.getSubmissionNumber());
        srDto.setSourceStudyProtocolIdentifier(newStudyProtocolIi);
        srDto.setTargetStudyProtocolIdentifier(oldStudyProtocolIi);
        srDto.setTypeCode(CdConverter.convertToCd(StudyRelationshipTypeCode.MOD));
        studyRelationshipService.create(srDto);
    }

    private void createOverallStatuses(Ii studyProtocolIi, Ii oldStudyProtocolIi, StudyOverallStatusDTO newStatusDto)
            throws PAException {

        List<StudyOverallStatusDTO> sosList = studyOverallStatusService
                .getByStudyProtocol(oldStudyProtocolIi);
        boolean first = true;
        boolean statusFound = false;
        for (StudyOverallStatusDTO sos : sosList) {
            if (first) {
                // do it only once
                StudyOverallStatusDTO currentDto = sosList.get(sosList.size() - 1);
                if (currentDto.getStatusCode().getCode().equals(newStatusDto.getStatusCode().getCode())) {
                    statusFound = true;
                }
            }
            first = false;
            sos.setIdentifier(null);
            sos.setStudyProtocolIdentifier(studyProtocolIi);
        } // for

        if (!statusFound) {
            newStatusDto.setIdentifier(null);
            newStatusDto.setStudyProtocolIdentifier(studyProtocolIi);
            sosList.add(newStatusDto);
        }
        for (StudyOverallStatusDTO sos : sosList) {
            studyOverallStatusService.create(sos);
        }
    }

    private void createIndIdes(Ii studyProtocolIi , List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
        for (StudyIndldeDTO studyIndldeDTO : studyIndldeDTOs) {
            studyIndldeDTO.setStudyProtocolIdentifier(studyProtocolIi);
            studyIndldeDTO.setIdentifier(null);
            studyIndldeService.create(studyIndldeDTO);
        }
    }


    private void createStudyResources(Ii studyProtocolIi , List<StudyResourcingDTO> studyResourcingDTOs)
    throws PAException {
        for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
            studyResourcingDTO.setStudyProtocolIdentifier(studyProtocolIi);
            studyResourcingDTO.setIdentifier(null);
            studyResourcingService.createStudyResourcing(studyResourcingDTO);
        }
    }

    private void createdocuments(Ii studyProtocolIi , List<DocumentDTO> documentDTOs)
    throws PAException {
        for (DocumentDTO documentDTO : documentDTOs) {
            documentDTO.setStudyProtocolIdentifier(studyProtocolIi);
            documentDTO.setIdentifier(null);
            documentService.create(documentDTO);
        }
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
                    .getOrganization(IiConverter.converToPoOrganizationIi(orgPoIdentifier));
            } catch (NullifiedEntityException e) {
                // Map m = e.getNullifiedEntities();
                // LOG.error("This Organization is no longer available instead use
                // ");
                throw new PAException("This Organization is no longer available instead use ", e);
            }
            // Step 3 : check for pa org, if not create one
            Organization paOrg = corrUtils.getPAOrganizationByIndetifers(null, orgPoIdentifier);
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
    

    private void createLeadOrganization(Ii studyProtocolIi , OrganizationDTO leadOrganizationDto ,
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO)
    throws PAException {
        String orgPoIdentifier = leadOrganizationDto.getIdentifier().getExtension();
        if (orgPoIdentifier  == null) {
            throw new PAException("Organization Identifer is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException(PROTOCOL_ID_NULL);
        }
        String localSpIdentifier = leadOrganizationParticipationIdentifierDTO
            .getLocalStudyProtocolIdentifier().getValue();
        if (localSpIdentifier  == null) {
            throw new PAException("Local StudyProtocol Identifer is null");
        }
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        
        if (spDTO == null) {
            throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi.getExtension());
        }
        Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        
        StudyParticipationDTO studyPartDTO = new StudyParticipationDTO();
        studyPartDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudyParticipationFunctionalCode.LEAD_ORGANIZATION.getCode()));
        studyPartDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(localSpIdentifier));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studyPartDTO = studyParticipationService.create(studyPartDTO);
      
    }

    private void createPrincipalInvestigator(Ii studyProtocolIi ,
            OrganizationDTO leadOrganizationDto ,
            PersonDTO principalInvestigatorDto ,
            StudyTypeCode studyTypeCode) throws PAException {
        String orgPoIdentifier = leadOrganizationDto.getIdentifier().getExtension();
        String personPoIdentifer = principalInvestigatorDto.getIdentifier().getExtension();
        if (orgPoIdentifier == null) {
            throw new PAException(" Organization PO Identifier is null");
        }
        if (personPoIdentifer == null) {
            throw new PAException(" Person PO Identifier is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException(PROTOCOL_ID_NULL);
        }
        if (studyTypeCode == null) {
            throw new PAException(" Study Protocol type is null");
        }
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException(NO_PROTOCOL_FOUND + studyProtocolIi.getExtension());
        }
        ClinicalResearchStaffCorrelationServiceBean crs = new ClinicalResearchStaffCorrelationServiceBean();
        HealthCareProviderCorrelationBean hcp = new HealthCareProviderCorrelationBean();
        Long crsId = crs.createClinicalResearchStaffCorrelations(orgPoIdentifier, personPoIdentifer);
        Long hcpId = null;
        if (StudyTypeCode.INTERVENTIONAL.equals(studyTypeCode)) {
            hcpId = hcp.createHealthCareProviderCorrelationBeans(orgPoIdentifier, personPoIdentifer);
        }
        StudyContactDTO scDTO = new StudyContactDTO();
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crsId));
        scDTO.setHealthCareProviderIi(IiConverter.convertToIi(hcpId));
        scDTO.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        scDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        scDTO.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.PENDING.getCode()));
        studyContactService.create(scDTO);
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
        StudyParticipationDTO studyPartDTO = new StudyParticipationDTO();
        studyPartDTO.setFunctionalCode(CdConverter
                .convertStringToCd(StudyParticipationFunctionalCode.SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studyPartDTO = studyParticipationService.create(studyPartDTO);
    }

    private void createPIAsResponsibleParty(Ii studyProtocolIi ,
            OrganizationDTO leadOrganizationDto ,
            PersonDTO principalInvestigatorDto ,
            StudyContactDTO studyContactDTO) throws PAException {

        DSet<Tel> dset = studyContactDTO.getTelecomAddresses();

        new PARelationServiceBean().createPIAsResponsiblePartyRelations(
                leadOrganizationDto.getIdentifier().getExtension(),
                principalInvestigatorDto.getIdentifier().getExtension(),
                IiConverter.convertToLong(studyProtocolIi),
                DSetConverter.convertDSetToList(dset, PAConstants.EMAIL).get(0),
                DSetConverter.convertDSetToList(dset, PAConstants.PHONE).get(0));
    }

    private void createSponsorAsPrimaryContact(Ii studyProtocolIi ,
            OrganizationDTO sponsorOrganizationDto ,
            PersonDTO responsiblePartyContactDTO ,
            StudyParticipationContactDTO studyParticipationContactDTO) throws PAException {

        DSet<Tel> dset = studyParticipationContactDTO.getTelecomAddresses();
        new PARelationServiceBean().createSponsorAsPrimaryContactRelations(
                sponsorOrganizationDto.getIdentifier().getExtension(),
                responsiblePartyContactDTO.getIdentifier().getExtension(),
                IiConverter.convertToLong(studyProtocolIi),
                DSetConverter.convertDSetToList(dset, PAConstants.EMAIL).get(0),
                DSetConverter.convertDSetToList(dset, PAConstants.PHONE).get(0));
    }
    
    private void createMilestone(Ii studyProtocolIi) throws PAException {
        
        StudyMilestoneDTO smDto = new StudyMilestoneDTO();
        smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        smDto.setStudyProtocolIdentifier(studyProtocolIi);
        smDto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_RECEIVED));
        studyMilestoneService.create(smDto);
        
    }

    private void enforceBusinessRules(
            StudyProtocolDTO studyProtocolDTO ,
            StudyOverallStatusDTO overallStatusDTO ,
            List<DocumentDTO> documentDTOs ,
            OrganizationDTO leadOrganizationDTO ,
            PersonDTO principalInvestigatorDTO ,
            OrganizationDTO sponsorOrganizationDTO ,
            StudyContactDTO studyContactDTO ,
            StudyParticipationContactDTO studyParticipationContactDTO ,
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO
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
        if (studyContactDTO != null && studyParticipationContactDTO != null) {
            sb.append("Either StudyContactDTO or studyParticipationContactDTO should be null ,");
        }
        if (studyContactDTO == null && studyParticipationContactDTO == null) {
            sb.append("Either StudyContactDTO or studyParticipationContactDTO should not be null ,");
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
        if (leadOrganizationParticipationIdentifierDTO != null) {
            sb.append(PAUtil.isStNull(leadOrganizationParticipationIdentifierDTO.getLocalStudyProtocolIdentifier())
                    ? "Local StudyProtocol Identifier cannot be null , " : "");
        }
        sb.append(PAUtil.isIiNull(leadOrganizationDTO.getIdentifier()) ? "Lead Organization cannot be null , " : "");
        sb.append(PAUtil.isIiNull(principalInvestigatorDTO.getIdentifier())
                ? "Principal Investigator cannot be null , " : "");
        sb.append(PAUtil.isIiNull(sponsorOrganizationDTO.getIdentifier())
                ? "Sponsor Organization cannot be null , " : "");
        if (studyContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.EMAIL) == null
                    ? "Email cannot be null , " : "");
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), PAConstants.PHONE) == null
                    ? "Phone cannot be null , " : "");
        }
        if (studyParticipationContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studyParticipationContactDTO.getTelecomAddresses(),
                    PAConstants.EMAIL) == null ? "Email cannot be null , " : "");
            sb.append(DSetConverter.getFirstElement(studyParticipationContactDTO.getTelecomAddresses(),
                    PAConstants.PHONE) == null  ? "Phone cannot be null , " : "");

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

 }
