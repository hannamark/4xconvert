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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.ArmServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyPaService;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;



/**
 * A utility class for all pa services .
 * @author Naveen Amiruddin
 * @since 10/26/2009
 */

@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength", 
    "PMD.TooManyMethods" , "PMD.ExcessiveParameterList" , "PMD.ExcessiveMethodLength" })
public class PAServiceUtils {
    
    private static final String UNCHECKED = "unchecked";
    /**
     * Executes an sql.
     * @param sql sql to be executed
     * @return number of counts
     */
    public int executeSql(String sql) {
        Session session = HibernateUtil.getCurrentSession();
        return session.createSQLQuery(sql).executeUpdate();
    }
    
    /**
     * Executes an list of sql.
     * @param sqls list of sqls
     */
    public void executeSql(List<String> sqls) {
        for (String sql : sqls) {
            executeSql(sql);
        }
    }    
    /**
     * does a deep copy of protocol to a new protocol.
     * @param fromStudyProtocolIi study protocol ii
     * @throws PAException  on error 
     * @return ii 
     */
    public Ii copy(Ii fromStudyProtocolIi) throws PAException {
        InterventionalStudyProtocolDTO dto = PaRegistry.getStudyProtocolService().
                getInterventionalStudyProtocol(fromStudyProtocolIi);
        dto.setIdentifier(null);
        dto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
        Ii toIi = PaRegistry.getStudyProtocolService().createInterventionalStudyProtocol(dto);
        executeCopy(getRemoteService(IiConverter.convertToStudyMilestoneIi(null)), fromStudyProtocolIi , toIi);
        executeCopy(getRemoteService(IiConverter.convertToDocumentWorkFlowStatusIi(null)), fromStudyProtocolIi , toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyIndIdeIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyDiseaseIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyObjectiveIi(null)), fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStratumGroupIi(null)), fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyResourcingIi(null)), fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyOnHoldIi(null)), fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyOverallStatusIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyRecruitmentStatusIi(null))
                            , fromStudyProtocolIi, toIi);
        StudyPaService<ArmDTO> sp = getRemoteService(IiConverter.convertToArmIi(null));
        Map<Ii , Ii> map = sp.copy(fromStudyProtocolIi, toIi);
        ArmServiceRemote as = getRemoteService(IiConverter.convertToArmIi(null));
        as.copy(fromStudyProtocolIi, toIi , map);
        executeCopy(getRemoteService(IiConverter.convertToStudyContactIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudySiteIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyOutcomeMeasureIi(null)) , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToStudyRegulatoryAuthorityIi(null))
            , fromStudyProtocolIi, toIi);
        executeCopy(getRemoteService(IiConverter.convertToDocumentIi(null)) , fromStudyProtocolIi, toIi);
        
        return toIi;
        
    }
    
    @SuppressWarnings(UNCHECKED)
    private void executeCopy(StudyPaService sp , Ii from , Ii to) throws PAException {
        sp.copy(from, to);
        
    }
    
    /**
     * an utility method to create or update.
     * @param dtos list of dtos 
     * @param id identifier
     * @param studyProtocolIi study protocol ii
     * @throws PAException on error
     */
    public void createOrUpdate(List<? extends StudyDTO> dtos, Ii id, Ii studyProtocolIi) throws PAException {
        if (dtos == null || dtos.isEmpty()) {
            return;
        }
        for (StudyDTO dto : dtos) {
            dto.setStudyProtocolIdentifier(studyProtocolIi);
            StudyPaService<StudyDTO> paService = getRemoteService(id);
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                paService.create(dto);
            } else {
                paService.update(dto);
            }
        }
    } 

    
    /**
     * 
     * @param <TYPE> any type extending StudyPaService 
     * @param isoIi iso ii
     * @return any type extending StudyPaService 
     * @throws PAException on error
     */
    @SuppressWarnings(UNCHECKED)
    public <TYPE extends StudyPaService>  TYPE getRemoteService(Ii isoIi) throws PAException {
        if (IiConverter.STUDY_MILESTONE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyMilestoneService(); 
        } else if (IiConverter.STUDY_IND_IDE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyIndldeService(); 
        } else if (IiConverter.STUDY_DISEASE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyDiseaseService(); 
        } else if (IiConverter.STUDY_OBJECTIVE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyObjectiveService(); 
        } else if (IiConverter.STRATUM_GROUP_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStratumGroupService(); 
        } else if (IiConverter.STUDY_RESOURCING_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyResourcingService(); 
        } else if (IiConverter.STUDY_ONHOLD_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyOnholdService(); 
        } else if (IiConverter.STUDY_OVERALL_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyOverallStatusService(); 
        } else if (IiConverter.STUDY_RECRUITMENT_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyRecruitmentStatusService(); 
        } else if (IiConverter.PLANNED_ACTIVITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getPlannedActivityService(); 
        } else if (IiConverter.ARM_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getArmService(); 
        } else if (IiConverter.STUDY_SITE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudySiteService(); 
        } else if (IiConverter.STUDY_CONTACT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyContactService(); 
        } else if (IiConverter.STUDY_OUTCOME_MEASURE_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyOutcomeMeasurService(); 
        } else if (IiConverter.DOCUMENT_WORKFLOW_STATUS_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getDocumentWorkflowStatusService(); 
        } else if (IiConverter.STUDY_REGULATORY_AUTHORITY_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getStudyRegulatoryAuthorityService(); 
        } else if (IiConverter.DOCUMENT_IDENTIFIER_NAME.equals(isoIi.getIdentifierName())) {    
            return (TYPE) PaRegistry.getDocumentService(); 
        } else {
            throw new PAException(" unknown identifier name provided  : " + isoIi.getIdentifierName());            
        }
    }
    
    /**
     * removes the sponsor contact.
     * @param studyProtocolIi studyPorotocol Ii
     * @throws PAException on error
     */
    public void removeResponsibleParty(Ii studyProtocolIi) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (scDtos != null && !scDtos.isEmpty()) {
            scDto = scDtos.get(0);
            PaRegistry.getStudyContactService().delete(scDtos.get(0).getIdentifier());
        } else {
            // delete from Study Site and it will delete study_site contact
            StudySiteDTO spart = new StudySiteDTO();
            spart.setFunctionalCode(CdConverter.convertToCd(
                  StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR));
              List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService()
                  .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && !spDtos.isEmpty()) {
                PaRegistry.getStudySiteService().delete(spDtos.get(0).getIdentifier());
            }

        }
    }
    
    /**
     * 
     * @param studyProtocolIi study protocol ii
     * @param leadOrganizationDTO Lead Organization 
     * @param principalInvestigatorDTO Principal Investigator
     * @param sponsorOrganizationDTO Sponsor Organization
     * @param responsiblePartyContactIi Responsible Party
     * @param studyContactDTO Study Contact
     * @param studySiteContactDTO Study Site Contact
     * @throws PAException on error
     */
    public void createResponsibleParty(Ii studyProtocolIi , OrganizationDTO leadOrganizationDTO , 
            PersonDTO principalInvestigatorDTO , OrganizationDTO sponsorOrganizationDTO , Ii responsiblePartyContactIi, 
            StudyContactDTO studyContactDTO , StudySiteContactDTO studySiteContactDTO) throws PAException {
        if (studyContactDTO != null) {
            createPIAsResponsibleParty(studyProtocolIi, leadOrganizationDTO , 
                    principalInvestigatorDTO , studyContactDTO);
        } else {
            createSponsorAsPrimaryContact(studyProtocolIi, sponsorOrganizationDTO ,
                    responsiblePartyContactIi, studySiteContactDTO);
        }
        
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
            OrganizationDTO sponsorOrganizationDTO ,
            Ii responsiblePartyContactIi ,
            StudySiteContactDTO studySiteContactDTO) throws PAException {

        DSet<Tel> dset = studySiteContactDTO.getTelecomAddresses();
        PAContactDTO contactDto = new PAContactDTO();
        contactDto.setOrganizationIdentifier(
                IiConverter.convertToPoOrganizationIi(sponsorOrganizationDTO.getIdentifier().getExtension()));
        contactDto.setStudyProtocolIdentifier(studyProtocolIi);
        contactDto.setEmail(DSetConverter.convertDSetToList(dset, PAConstants.EMAIL).get(0));
        contactDto.setPhone(DSetConverter.convertDSetToList(dset, PAConstants.PHONE).get(0));
        if (responsiblePartyContactIi.getRoot().equalsIgnoreCase(IiConverter.PERSON_ROOT)) {
            contactDto.setPersonIdentifier(responsiblePartyContactIi);
        }
        if (responsiblePartyContactIi.getRoot().equalsIgnoreCase(IiConverter.ORGANIZATIONAL_CONTACT_ROOT)) {
            contactDto.setSrIdentifier(responsiblePartyContactIi);
        }
        new PARelationServiceBean().createSponsorAsPrimaryContactRelations(contactDto);
    }

    
    /**
     * 
     * @param studyProtocolIi study Protocol Ii
     * @param nctIdentifierDTO study site identifier
     * @throws PAException on error
     */
    public void manageNCTIdentifier(Ii studyProtocolIi , StudySiteDTO nctIdentifierDTO) throws PAException {
        List<StudySiteDTO> studySites = getStudySite(studyProtocolIi,
                                StudySiteFunctionalCode.IDENTIFIER_ASSIGNER, true);
        StudySiteDTO studySite = PAUtil.getFirstObj(studySites);
        StudyPaService<StudySiteDTO>  spService = getRemoteService(IiConverter.convertToStudySiteIi(null));
        if (nctIdentifierDTO != null  && !PAUtil.isStNull(nctIdentifierDTO.getLocalStudyProtocolIdentifier())) {
             
            if (studySite == null) {
                // user is creating for the first time
                studySite = createNCTidentifierObj(studyProtocolIi, nctIdentifierDTO);
                spService.create(studySite);
                
            } else {
                // check if both is same, then there is no reason to update as its not changed
                if (!StConverter.convertToString(studySite.getLocalStudyProtocolIdentifier())
                        .equals(StConverter.convertToString(nctIdentifierDTO.getLocalStudyProtocolIdentifier()))) {
                    studySite.setLocalStudyProtocolIdentifier(nctIdentifierDTO.getLocalStudyProtocolIdentifier());
                    spService.update(studySite);
                }
            }
        } else {
            if (studySite != null) {
                // user is deleting
                spService.delete(studySite.getIdentifier());
            }
        }
    }    
    /**
     * 
     * @param studyProtocolIi study protocol identifier
     * @param organizationDto organization Dto
     * @param summary4studyResourcingDTO summary four Resourcing Dto
     * @throws PAException on error
     */
    public void manageSummaryFour(Ii studyProtocolIi , OrganizationDTO organizationDto ,
            StudyResourcingDTO summary4studyResourcingDTO) throws PAException {
        if (organizationDto != null && organizationDto.getIdentifier() != null) {
            SummaryFourFundingCategoryCode summaryFourFundingCategoryCode = null;
        
            if (summary4studyResourcingDTO != null && !PAUtil.isCdNull(summary4studyResourcingDTO.getTypeCode())) {
                summaryFourFundingCategoryCode = SummaryFourFundingCategoryCode.getByCode(
                        summary4studyResourcingDTO.getTypeCode().getCode());
            }
            CorrelationUtils corrUtils = new CorrelationUtils();
            String orgPoIdentifier = organizationDto.getIdentifier().getExtension();
            if (orgPoIdentifier  == null) {
                throw new PAException(" Organization PO Identifier is null");
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
            
            StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
                    studyProtocolIi);
            if (summary4ResoureDTO == null) {
                // summary 4 record does not exist,so create a new one
                summary4ResoureDTO = new StudyResourcingDTO();
                summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                if (summaryFourFundingCategoryCode != null) {
                    summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summaryFourFundingCategoryCode));
                }
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
                PaRegistry.getStudyResourcingService().createStudyResourcing(summary4ResoureDTO);
            } else {
                // summary 4 record does exist,so so do an update
                summary4ResoureDTO.setStudyProtocolIdentifier(studyProtocolIi);
                summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
                if (summaryFourFundingCategoryCode != null) {
                    summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(summaryFourFundingCategoryCode));
                }
                summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(paOrg.getId()));
                PaRegistry.getStudyResourcingService().updateStudyResourcing(summary4ResoureDTO);
            }
        }
        
        
    }
    
    /**
     * 
     * @param studyProtocolIi Study Protocol Identifier
     * @param leadOrganizationDto Lead Organization Dto
     * @param localStudyProtocolIdentifier local sp Identifier
     * @throws PAException on error
     */
    public void manageLeadOrganization(Ii studyProtocolIi , OrganizationDTO leadOrganizationDto ,
            St localStudyProtocolIdentifier)
    throws PAException {
        OrganizationCorrelationServiceBean ocsr = new OrganizationCorrelationServiceBean();
        String orgPoIdentifier = leadOrganizationDto.getIdentifier().getExtension();
        if (orgPoIdentifier  == null) {
            throw new PAException("Organization Identifier is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException("Protocol Identifier is Null");
        }
        if (PAUtil.isStNull(localStudyProtocolIdentifier)) {
            throw new PAException("Local StudyProtocol Identifer is null");
        }
        Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        List<StudySiteDTO> studySiteDtos = 
            getStudySite(studyProtocolIi, StudySiteFunctionalCode.LEAD_ORGANIZATION, true);
        StudySiteDTO studySiteDTO = null;
        if (PAUtil.getFirstObj(studySiteDtos) != null) {
            studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        } else {
            studySiteDTO = new StudySiteDTO();
        }
        studySiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        studySiteDTO.setLocalStudyProtocolIdentifier(localStudyProtocolIdentifier);
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteDtos  = new ArrayList<StudySiteDTO>();
        studySiteDtos.add(studySiteDTO);
        createOrUpdate(studySiteDtos, IiConverter.convertToStudySiteIi(null), studyProtocolIi);
        
    }
    
    /**
     * 
     * @param studyProtocolIi Study Protocol Identifier
     * @param sponsorDto Lead Organization Dto
     * @throws PAException on error
     */
    public void manageSponsor(Ii studyProtocolIi , OrganizationDTO sponsorDto)
    throws PAException {
        OrganizationCorrelationServiceBean ocsr = new OrganizationCorrelationServiceBean();
        String orgPoIdentifier = sponsorDto.getIdentifier().getExtension();
        if (orgPoIdentifier  == null) {
            throw new PAException("Organization Identifier is null");
        }
        if (studyProtocolIi == null) {
            throw new PAException("Protocol Identifier is Null");
        }
        Long roId = ocsr.createResearchOrganizationCorrelations(orgPoIdentifier);
        List<StudySiteDTO> studySiteDtos = 
            getStudySite(studyProtocolIi, StudySiteFunctionalCode.SPONSOR, true);
        StudySiteDTO studySiteDTO = null;
        if (PAUtil.getFirstObj(studySiteDtos) != null) {
            studySiteDTO = PAUtil.getFirstObj(studySiteDtos);
        } else {
            studySiteDTO = new StudySiteDTO();
        }
        studySiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        studySiteDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studySiteDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteDtos  = new ArrayList<StudySiteDTO>();
        studySiteDtos.add(studySiteDTO);
        createOrUpdate(studySiteDtos, IiConverter.convertToStudySiteIi(null), studyProtocolIi);
        
    }    
    
    /**
     * 
     * @param studyProtocolIi study protocol identifier
     * @param leadOrganizationDto lead organization identifier
     * @param principalInvestigatorDto pi
     * @param studyTypeCode study type code
     * @throws PAException on error
     */
    public void managePrincipalInvestigator(Ii studyProtocolIi ,
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
            throw new PAException("Study Protocol Identifier is null");
        }
        if (studyTypeCode == null) {
            throw new PAException(" Study Protocol type is null");
        }
        ClinicalResearchStaffCorrelationServiceBean crs = new ClinicalResearchStaffCorrelationServiceBean();
        HealthCareProviderCorrelationBean hcp = new HealthCareProviderCorrelationBean();
        Long crsId = crs.createClinicalResearchStaffCorrelations(orgPoIdentifier, personPoIdentifer);
        Long hcpId = null;
        if (StudyTypeCode.INTERVENTIONAL.equals(studyTypeCode)) {
            hcpId = hcp.createHealthCareProviderCorrelationBeans(orgPoIdentifier, personPoIdentifer);
        }
        List<StudyContactDTO> studyContactDtos = 
            getStudyContact(studyProtocolIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
        StudyContactDTO studyContactDTO = null;
        if (PAUtil.getFirstObj(studyContactDtos) != null) {
            studyContactDTO = PAUtil.getFirstObj(studyContactDtos);
        } else {
            studyContactDTO = new StudyContactDTO();
        }        
        studyContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crsId));
        studyContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(hcpId));
        studyContactDTO.setRoleCode(CdConverter.convertStringToCd(
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        studyContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studyContactDtos  = new ArrayList<StudyContactDTO>();
        studyContactDtos.add(studyContactDTO);
        createOrUpdate(studyContactDtos, IiConverter.convertToStudyContactIi(null), studyProtocolIi);
    }
    
    /**
     * checks if any device is found in the planned activities list.
     * @param studyProtocolIi sp id
     * @param paList planned activities list
     * @return boolean
     */
    public boolean isDeviceFound(Ii studyProtocolIi , List<PlannedActivityDTO> paList) {
        boolean found = false;
        for (PlannedActivityDTO pa : paList) {
            if (pa.getCategoryCode() != null
                    && ActivityCategoryCode.INTERVENTION
                            .equals(ActivityCategoryCode.getByCode(CdConverter
                                    .convertCdToString(pa.getCategoryCode())))
                    && pa.getSubcategoryCode() != null
                    && pa.getSubcategoryCode().getCode() != null
                    && InterventionTypeCode.DEVICE.getCode().equalsIgnoreCase(
                            pa.getSubcategoryCode().getCode())) {
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * 
     * @param studyProtocolIi StudyProtocol Identifier
     * @param spCode functional code
     * @param isUnique determines if the result is unique
     * @return list of StudySiteDtos
     * @throws PAException on error
     */
    public List<StudySiteDTO> getStudySite(Ii studyProtocolIi , StudySiteFunctionalCode spCode , boolean isUnique)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudySiteDTO spDto = new StudySiteDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos;
        } else if (spDtos != null && spDtos.size() > 1 && isUnique) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

        }
        return spDtos;

    }

    /**
     * 
     * @param studyProtocolIi StudyProtocol Identifier
     * @param scCode functional code
     * @param isUnique determines if the result is unique
     * @return list of StudySiteDtos
     * @throws PAException on error
     */
    public List<StudyContactDTO> getStudyContact(Ii studyProtocolIi , StudyContactRoleCode scCode , boolean isUnique)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudyContactDTO spDto = new StudyContactDTO();
        Cd cd = CdConverter.convertToCd(scCode);
        spDto.setRoleCode(cd);

        List<StudyContactDTO> spDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos;
        } else if (spDtos != null && spDtos.size() > 1 && isUnique) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

        }
        return spDtos;

    }   
    /**
     * 
     * @param studyProtocolIi study protocol identifier
     * @param nctIdentifierDTO study site dto
     * @return nct identifier obj 
     * @throws PAException on error
     */
    public StudySiteDTO createNCTidentifierObj(Ii studyProtocolIi , StudySiteDTO nctIdentifierDTO)
    throws PAException {
        if (nctIdentifierDTO == null || nctIdentifierDTO.getLocalStudyProtocolIdentifier() == null
                ||  PAUtil.isEmpty(nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue())) {
            return null;
        }
        StudySiteDTO spDto =  new StudySiteDTO();
        String poOrgId = PaRegistry.getOrganizationCorrelationService().getCtGovPOIdentifier();
        long roId = PaRegistry.getOrganizationCorrelationService().createResearchOrganizationCorrelations(poOrgId);
        spDto.setStudyProtocolIdentifier(studyProtocolIi);
        spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        spDto.setLocalStudyProtocolIdentifier(nctIdentifierDTO.getLocalStudyProtocolIdentifier());
        spDto.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        return spDto;
    }
    
    /**
     * 
     * @param studyIndldeDTOs list of dtos
     * @param studyProtocolDTO studyProtocol 
     * @throws PAException on error
     */
    @SuppressWarnings(UNCHECKED)
    public void enforceNoDuplicateIndIde(List<StudyIndldeDTO> studyIndldeDTOs, StudyProtocolDTO studyProtocolDTO) 
    throws PAException {
     
        if (studyIndldeDTOs != null  && !studyIndldeDTOs.isEmpty()) {
            for (int i = 0; i < studyIndldeDTOs.size(); i++) {
                StudyIndldeDTO sp = (StudyIndldeDTO) studyIndldeDTOs.get(i);
                for (int j = ++i; j < studyIndldeDTOs.size(); j++) {
                    StudyIndldeDTO newType = (StudyIndldeDTO) studyIndldeDTOs.get(j);        
                    boolean sameType = newType.getIndldeTypeCode().getCode().equals(sp.getIndldeTypeCode().getCode());
                    boolean sameNumber = newType.getIndldeNumber().getValue().equals(sp.getIndldeNumber().getValue());
                    boolean sameGrantor = newType.getGrantorCode().getCode().equals(sp.getGrantorCode().getCode());
                    if (sameType && sameNumber && sameGrantor) {
                        throw new PADuplicateException("Duplicates Ind/Ide are not allowed.");
                    }
                }
            }
            if (!BlConverter.covertToBool(studyProtocolDTO.getFdaRegulatedIndicator())) {
                new PAException("FDA Regulated Intervention Indicator must be Yes since it has Trial IND/IDE records.");
            }
        }  
    }    
    /**
     * 
     * @param studyResourcingDTOs list of 
     * @throws PAException on error
     */
    @SuppressWarnings(UNCHECKED)
    public void enforceNoDuplicateGrants(List<StudyResourcingDTO> studyResourcingDTOs) throws PAException {
        if (studyResourcingDTOs != null  && !studyResourcingDTOs.isEmpty()) {
            for (int i = 0; i < studyResourcingDTOs.size(); i++) {
                StudyResourcingDTO sp =  studyResourcingDTOs.get(i);
                for (int j = ++i; j < studyResourcingDTOs.size(); j++) {
                    StudyResourcingDTO newType =  studyResourcingDTOs.get(j);
                    boolean sameFundingMech = newType.getFundingMechanismCode().getCode().
                                   equals(sp.getFundingMechanismCode().getCode());
                    boolean sameNih = newType.getNihInstitutionCode().getCode().
                                   equals(sp.getNihInstitutionCode().getCode());
                    boolean sameNci = newType.getNciDivisionProgramCode().getCode().
                                    equals(sp.getNciDivisionProgramCode().getCode());
                    boolean sameSerial = newType.getSerialNumber().getValue().
                                    equals(sp.getSerialNumber().getValue());
                    if (sameFundingMech && sameNih && sameNci && sameSerial) {
                          throw new PADuplicateException("Duplicates Grants are not allowed.");
                    }
               }
           }
       }  
    }
    

    /**
     * 
     * @param identifier nci Identifier
     * @return next submission number
     */
    public Integer generateSubmissionNumber(String identifier) {
        Session session = HibernateUtil.getCurrentSession();
        String query = "select max(sp.submissionNumber) from StudyProtocol sp where "
            + "sp.identifier = '" + identifier + "' ";
        Integer maxValue = (Integer) session.createQuery(query).list().get(0);
        return (maxValue == null ? 1 : maxValue + 1);
    }
    
    /**
     * Enforce recruitment status.
     * 
     * @param studyProtocolDTO the study protocol dto
     * @param participatingSites the participating sites
     * @param recruitmentStatusDto the recruitment status dto
     * 
     * @throws PAException the PA exception
     */
    @SuppressWarnings({"PMD" })
    public void enforceRecruitmentStatus(StudyProtocolDTO studyProtocolDTO, 
                                         List<StudySiteAccrualStatusDTO> participatingSites, 
                                         StudyRecruitmentStatusDTO recruitmentStatusDto) throws PAException {
        if (participatingSites != null && !participatingSites.isEmpty()) { 
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
    
    
    /**
     * Enforce regulatory info.
     * 
     * @param studyProtocolDTO the study protocol dto
     * @param studyRegAuthDTO the study reg auth dto
     * @param studyIndldeDTOs the study indlde dt os
     * @param isoDocWrkStatus the iso doc wrk status
     * @param paList the pa list
     * @param regulatoryInfoBean the regulatory info bean
     * 
     * @throws PAException the PA exception
     */
    @SuppressWarnings({"PMD" })
    public void enforceRegulatoryInfo(StudyProtocolDTO studyProtocolDTO, 
                                      StudyRegulatoryAuthorityDTO studyRegAuthDTO , 
                                      List<StudyIndldeDTO> studyIndldeDTOs,
                                      DocumentWorkflowStatusDTO isoDocWrkStatus ,
                                      List<PlannedActivityDTO> paList ,
                                      RegulatoryInformationServiceRemote regulatoryInfoBean) throws PAException {
        StringBuffer errMsg = new StringBuffer();
        if (studyRegAuthDTO == null) {
            errMsg.append("Regulatory Information fields must be Entered.\n");
        }
        if (PAUtil.isAbstractedAndAbove(isoDocWrkStatus.getStatusCode())) {
           
            if (PAConstants.YES.equalsIgnoreCase(
                  BlConverter.convertBLToString(studyProtocolDTO.getFdaRegulatedIndicator()))
                  && PAUtil.isBlNull(studyProtocolDTO.getSection801Indicator())) {
                errMsg.append("Section 801 is required if FDA Regulated indicator is true.");
            }
            
           //Display error in abstraction validation if section 801 indicator = ‘yes’,  
            if (PAConstants.YES.equalsIgnoreCase(
                    BlConverter.convertBLToString(studyProtocolDTO.getSection801Indicator()))
                    && PAConstants.YES.equalsIgnoreCase(
                            BlConverter.convertBLToString(studyProtocolDTO.getDelayedpostingIndicator()))
                    && !isDeviceFound(studyProtocolDTO.getIdentifier() , paList)) {
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
    
}
