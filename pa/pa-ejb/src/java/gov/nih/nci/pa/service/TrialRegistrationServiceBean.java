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
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Collections;
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

import org.apache.log4j.Logger;

/**
 * @author Naveen Amiruddin
 * @since 03/19/2009
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD" })
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TrialRegistrationServiceBean implements TrialRegistrationServiceRemote {
    
    private static final Logger LOG  = Logger.getLogger(TrialRegistrationServiceBean.class);
    private SessionContext ejbContext;

    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;    
    @EJB
    StudyIndldeServiceLocal studyIndldeService  = null;
    @EJB
    StudyResourcingServiceLocal studyResourcingService = null;
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
     * @param ispDto attributes of study protocol dto.
     * @throws PAException on any error
     * @return ii of StudyProtocol.
     */

    public Ii createInterventionalStudyProtocol(InterventionalStudyProtocolDTO ispDto) throws PAException {
        //helper(ispDto);
        return null;
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
        Ii fromStudyProtocolii = studyProtocolDTO.getIdentifier();
        Ii toStudyProtocolIi = createStudyProtocolObjs(
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
                responsiblePartyContactDTO);
        
        deepCopy(fromStudyProtocolii , toStudyProtocolIi);
        deepCopyParticipation(fromStudyProtocolii , toStudyProtocolIi);
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
        Map<Ii , Ii> paMap = new HashMap<Ii , Ii>();
        Ii oldPaIi;
        Ii newPaIi;
        for (PlannedActivityDTO dto : dtos) {
            oldPaIi = dto.getIdentifier();
            dto.setIdentifier(null);
            dto.setStudyProtocolIdentifier(toStudyProtocolIi);
            newPaIi = plannedActivityService.create(dto).getIdentifier();
            paMap.put(oldPaIi, newPaIi);
        }
        
        List<ArmDTO> armDtos = armService.getByStudyProtocol(fromStudyProtocolIi);
        for (ArmDTO armDto : armDtos) {
                armDto.setInterventions(getAssociatedInterventions(armDto.getIdentifier() , paMap));
                armDto.setIdentifier(null);
                armDto.setStudyProtocolIdentifier(toStudyProtocolIi);
                armService.create(armDto);
        }
    }
    
    private DSet<Ii> getAssociatedInterventions(Ii armIi , Map<Ii , Ii> paMap) throws PAException {
        List<PlannedActivityDTO> dtos = null;
        Set<Ii> iiSet = new HashSet<Ii>();
        boolean armIntFound = false;
        dtos = plannedActivityService.getByArm(armIi);
        for (PlannedActivityDTO paDto : dtos) {
            if (paMap.containsKey(paDto.getIdentifier())) {
                armIntFound = true;
                iiSet.add(paMap.get(paDto.getIdentifier()));
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
        ArrayList<StudyParticipationDTO> criteriaList = new ArrayList<StudyParticipationDTO>();
        StudyParticipationDTO searchCode = null;
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
            StudyParticipationDTO nciIdentifierParticipationIdentifierDTO , 
            StudyContactDTO studyContactDTO , 
            StudyParticipationContactDTO studyParticipationContactDTO ,
            OrganizationDTO summary4organizationDTO , 
            StudyResourcingDTO summary4studyResourcingDTO , 
            PersonDTO responsiblePartyContactDTO)
    throws PAException {
        
        enforceBusinessRules(
                studyProtocolDTO, 
                overallStatusDTO, 
                studyIndldeDTOs, 
                studyResourcingDTOs, 
                documentDTOs, 
                summary4organizationDTO, 
                summary4studyResourcingDTO, 
                leadOrganizationDTO, 
                principalInvestigatorDTO, 
                responsiblePartyContactDTO, 
                sponsorOrganizationDTO, 
                studyContactDTO, 
                studyParticipationContactDTO, 
                leadOrganizationParticipationIdentifierDTO, 
                nciIdentifierParticipationIdentifierDTO);
        
        Ii oldIdentifier =  studyProtocolDTO.getIdentifier();
        Ii studyProtocolIi = null;
        StudyTypeCode studyTypeCode = null;
        studyProtocolDTO.setIdentifier(null);
        if (studyProtocolDTO instanceof InterventionalStudyProtocolDTO) {
            studyProtocolIi =  studyProtocolService.createInterventionalStudyProtocol(
                        (InterventionalStudyProtocolDTO) studyProtocolDTO);
            studyTypeCode = StudyTypeCode.INTERVENTIONAL;
        } else if (studyProtocolDTO instanceof StudyProtocolDTO) {
            studyProtocolIi =  studyProtocolService.createObservationalStudyProtocol(
                    (ObservationalStudyProtocolDTO) studyProtocolDTO);
            studyTypeCode = StudyTypeCode.OBSERVATIONAL;
        }
        
        StudyProtocolDTO oldSpDto = studyProtocolService.getStudyProtocol(oldIdentifier);
        oldSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        studyProtocolService.updateStudyProtocol(oldSpDto);

        
        // set the study over all status 
        overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
        //studyOverallStatusService.create(overallStatusDTO);
        createOverallStatuses(studyProtocolIi, oldIdentifier , overallStatusDTO);
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
        return studyProtocolIi;
    }
    
    private void createOverallStatuses(Ii studyProtocolIi , Ii oldStudyProtocolIi , 
            StudyOverallStatusDTO currentStatus) 
    throws PAException {
        List<StudyOverallStatusDTO> sosList = studyOverallStatusService.getCurrentByStudyProtocol(oldStudyProtocolIi);
        boolean first = true;
        boolean statusFound = false;
        for (StudyOverallStatusDTO sos : sosList) {
            if (first) {
                if (sos.getStatusCode().getCode().equals(currentStatus.getStatusCode().getCode())) {
                    sos.setReasonText(currentStatus.getReasonText());
                    sos.setStatusCode(currentStatus.getStatusCode());
                    sos.setStatusDate(currentStatus.getStatusDate());
                    statusFound = true;
                }
            }
            first = false;
            sos.setIdentifier(null);
            sos.setStudyProtocolIi(studyProtocolIi);
        } // for
        
        Collections.reverse(sosList);
        if (!statusFound) {
            sosList.add(currentStatus);
        }
        
        for (StudyOverallStatusDTO sos : sosList) {
            studyOverallStatusService.create(sos);
        }
    }
    
    private void createIndIdes(Ii studyProtocolIi , List<StudyIndldeDTO> studyIndldeDTOs) throws PAException {
        for (StudyIndldeDTO studyIndldeDTO : studyIndldeDTOs) {
            studyIndldeDTO.setStudyProtocolIi(studyProtocolIi);
            studyIndldeDTO.setIdentifier(null);
            studyIndldeService.create(studyIndldeDTO);
        }
    }

    
    private void createStudyResources(Ii studyProtocolIi , List<StudyResourcingDTO> studyResourcingDTOs) 
    throws PAException {
        for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
            studyResourcingDTO.setStudyProtocolIi(studyProtocolIi);
            studyResourcingDTO.setIdentifier(null);
            studyResourcingService.createStudyResourcing(studyResourcingDTO);
        }
    }

    private void createdocuments(Ii studyProtocolIi , List<DocumentDTO> documentDTOs) 
    throws PAException {
        for (DocumentDTO documentDTO : documentDTOs) {
            documentDTO.setStudyProtocolIi(studyProtocolIi);
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
            new PARelationServiceBean().createSummary4ReportedSource(organizationDto.getIdentifier()
                    .getExtension(), summaryFourFundingCategoryCode, IiConverter
                    .convertToLong(studyProtocolIi));            
            
        }
    }
    
    private void createLeadOrganization(Ii studyProtocolIi , OrganizationDTO leadOrganizationDto , 
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO) 
    throws PAException {
        new PARelationServiceBean().createLeadOrganizationRelations(
                leadOrganizationDto.getIdentifier().getExtension(), 
                IiConverter.convertToLong(studyProtocolIi), 
                leadOrganizationParticipationIdentifierDTO.getLocalStudyProtocolIdentifier().getValue());        
    }
    
    private void createPrincipalInvestigator(Ii studyProtocolIi , 
            OrganizationDTO leadOrganizationDto ,
            PersonDTO principalInvestigatorDto , 
            StudyTypeCode studyTypeCode) throws PAException {
        new PARelationServiceBean().createPrincipalInvestigatorRelations(
                leadOrganizationDto.getIdentifier().getExtension() , 
                principalInvestigatorDto.getIdentifier().getExtension(), 
                IiConverter.convertToLong(studyProtocolIi), studyTypeCode);
    }
    
    private void createSponsor(Ii studyProtocolIi , OrganizationDTO sponsorOrganizationDto) throws PAException {
        new PARelationServiceBean().createSponsorRelations(sponsorOrganizationDto.getIdentifier().getExtension(), 
                IiConverter.convertToLong(studyProtocolIi));
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
                DSetConverter.convertDSetToList(dset, "EMAIL").get(0), 
                DSetConverter.convertDSetToList(dset, "PHONE").get(0));        
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
                DSetConverter.convertDSetToList(dset, "EMAIL").get(0), 
                DSetConverter.convertDSetToList(dset, "PHONE").get(0));        
    }

    private void enforceBusinessRules(
            StudyProtocolDTO studyProtocolDTO , 
            StudyOverallStatusDTO overallStatusDTO , 
            List<StudyIndldeDTO> studyIndldeDTOs , 
            List<StudyResourcingDTO> studyResourcingDTOs , 
            List<DocumentDTO> documentDTOs , 
            OrganizationDTO summary4organizationDTO , 
            StudyResourcingDTO summary4studyResourcingDTO , 
            OrganizationDTO leadOrganizationDTO , 
            PersonDTO principalInvestigatorDTO , 
            PersonDTO responsiblePartyContactDTO ,
            OrganizationDTO sponsorOrganizationDTO , 
            StudyContactDTO studyContactDTO , 
            StudyParticipationContactDTO studyParticipationContactDTO ,
            StudyParticipationDTO leadOrganizationParticipationIdentifierDTO ,
            StudyParticipationDTO nciIdentifierParticipationIdentifierDTO) 
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
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), "EMAIL") == null 
                    ? "Email cannot be null , " : "");
            sb.append(DSetConverter.getFirstElement(studyContactDTO.getTelecomAddresses(), "PHONE") == null 
                    ? "Phone cannot be null , " : "");
        }
        if (studyParticipationContactDTO != null) {
            sb.append(DSetConverter.getFirstElement(studyParticipationContactDTO.getTelecomAddresses(), "EMAIL") 
                    == null ? "Email cannot be null , " : "");
            sb.append(DSetConverter.getFirstElement(studyParticipationContactDTO.getTelecomAddresses(), "PHONE") 
                    == null  ? "Phone cannot be null , " : "");

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
