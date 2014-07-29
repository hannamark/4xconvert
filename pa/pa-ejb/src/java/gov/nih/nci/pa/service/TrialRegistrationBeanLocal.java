/**
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce, copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions,  You have no right to download, copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
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
 * party proprietary programs,  You agree  that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro,Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service;

import static org.apache.commons.lang.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang.StringUtils.trim;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.NullFlavor;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyMilestone;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO;
import gov.nih.nci.pa.dto.ResponsiblePartyDTO.ResponsiblePartyType;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.AmendmentReasonCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RejectionReasonCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.enums.StudyRelationshipTypeCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudySiteStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.convert.NonInterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyInboxDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyMilestoneDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.AccrualDiseaseTerminologyServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.CTGovSyncServiceBean;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.POServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceLocal;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TrialRegistrationValidator;
import gov.nih.nci.pa.util.TrialUpdatesRecorder;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class TrialRegistrationBeanLocal extends AbstractTrialRegistrationBean // NOPMD
    implements TrialRegistrationServiceLocal { // NOPMD
    private static final String DEPRECATION = "deprecation";
    private static final String EMPTY_STR = "";    
    @Resource private SessionContext ctx;
    @EJB private DocumentServiceLocal documentService;
    @EJB private DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService;
    @EJB private LookUpTableServiceRemote lookUpTableServiceRemote;
    @EJB private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB private OrganizationCorrelationServiceRemote ocsr;
    @EJB private RegistryUserServiceLocal registryUserServiceLocal;
    @EJB private RegulatoryInformationServiceLocal regulatoryInfoBean;
    @EJB private StudyContactServiceLocal studyContactService;
    @EJB private StudyInboxServiceLocal studyInboxServiceLocal;
    @EJB private StudyIndldeServiceLocal studyIndldeService;
    @EJB private StudyMilestoneServicelocal studyMilestoneService;
    @EJB private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB private StudyProtocolServiceLocal studyProtocolService;
    @EJB private StudyRecruitmentStatusServiceLocal studyRecruitmentStatusServiceLocal;
    @EJB private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB private StudyRelationshipServiceLocal studyRelationshipService;
    @EJB private StudyResourcingServiceLocal studyResourcingService;
    @EJB private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB private StudySiteContactServiceLocal studySiteContactService;
    @EJB private StudySiteServiceLocal studySiteService;
    @EJB private TSRReportGeneratorServiceLocal tsrReportService;
    @EJB private ArmServiceLocal armService;
    @EJB private PlannedActivityServiceLocal plannedActivityService;
    @EJB private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB private AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService;
    
    private RegulatoryAuthorityServiceLocal regulatoryAuthorityService = new RegulatoryAuthorityBeanLocal();

    private static final String CREATE = "Create";
    private static final String AMENDMENT = "Amendment";
    private static final String UPDATE = "Update";
    private static final String PROTOCOL_ID_NULL = "Study Protocol Identifier is null";
    private static final String NO_PROTOCOL_FOUND = "No Study Protocol found for = ";
    private static final String SQL_APPEND = " AND FUNCTIONAL_CODE IN ";
    private static final String MILESTONE = "study_milestone";
    private static final String DOCUMENT = "document";
    private static final Logger LOG = Logger.getLogger(TrialRegistrationBeanLocal.class);
    private void addNciOrgAsCollaborator(StudyProtocolDTO studyProtocolDTO, Ii studyProtocolIi)
            throws PAException {
        StudySiteDTO nCiCollaborator = new StudySiteDTO();
        nCiCollaborator.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
        nCiCollaborator.setStatusCode(CdConverter.convertToCd(StudySiteStatusCode.ACTIVE));
        // The assumption is that there will be only 1 active org with name
        // 'National Cancer Institute' in PO and that while there may be others that contain
        // that name, there will not be a large number to pull in this very broad search.
        OrganizationDTO criteria = new OrganizationDTO();
        String exactString = "National Cancer Institute";
        criteria.setName(EnOnConverter.convertToEnOn(exactString));
        criteria.setStatusCode(CdConverter.convertToCd(EntityStatusCode.ACTIVE));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> listOrgs = new ArrayList<OrganizationDTO>();
        try {
            listOrgs = PoRegistry.getOrganizationEntityService().search(criteria, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        for (OrganizationDTO poOrg : listOrgs) {
            if (EnOnConverter.convertEnOnToString(poOrg.getName()).matches(exactString)) {
                Long paOrgId = ocsr.createResearchOrganizationCorrelations(poOrg.getIdentifier().getExtension());
                nCiCollaborator.setResearchOrganizationIi(IiConverter.convertToIi(paOrgId));
                nCiCollaborator.setStudyProtocolIdentifier(studyProtocolIi);
                nCiCollaborator.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.FUNDING_SOURCE));
                nCiCollaborator.setHealthcareFacilityIi(null);
                nCiCollaborator.setIdentifier(null);
                nCiCollaborator.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
                studySiteService.create(nCiCollaborator);
                break;
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO, // NOPMD
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException {
        return amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO,
                leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                studyContactDTO, studySiteContactDTO, summary4OrganizationDTO,
                summary4StudyResourcingDTO, responsiblePartyContactIi,
                studyRegAuthDTO, isBatchMode, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO, // NOPMD
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, Bl handleDuplicateGrantAndINDsGracefully) 
                    throws PAException {
        ResponsiblePartyDTO party = getResponsiblePartyDTO(leadOrganizationDTO,
                principalInvestigatorDTO, studyContactDTO);
        return amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO, party,
                leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                studyRegAuthDTO, isBatchMode,
                handleDuplicateGrantAndINDsGracefully);
    }

    /**
     * @param leadOrganizationDTO
     * @param principalInvestigatorDTO
     * @param studyContactDTO
     * @return
     */
    private ResponsiblePartyDTO getResponsiblePartyDTO(
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO, StudyContactDTO studyContactDTO) {
        ResponsiblePartyDTO party = new ResponsiblePartyDTO();
        if (studyContactDTO != null) {
            party.setType(ResponsiblePartyType.PRINCIPAL_INVESTIGATOR);
            party.setInvestigator(principalInvestigatorDTO);
            party.setAffiliation(leadOrganizationDTO);
            String title = StConverter.convertToString(studyContactDTO
                    .getTitle());
            party.setTitle(StringUtils.isBlank(title) ? "Principal Investigator"
                    : StringUtils.EMPTY);
        } else {
            party.setType(ResponsiblePartyType.SPONSOR);
        }
        return party;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO, // NOPMD
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode) throws PAException {
        return amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                studyResourcingDTOs, documentDTOs, leadOrganizationDTO,
                principalInvestigatorDTO, sponsorOrganizationDTO,
                partyDTO,
                leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                summary4OrganizationDTO,
                summary4StudyResourcingDTO,
                studyRegAuthDTO, isBatchMode, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii amend(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO, // NOPMD
            List<StudyIndldeDTO> studyIndldeDTOs, List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs, OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, Bl handleDuplicateGrantAndINDsGracefully) 
                    throws PAException {
        // CHECKSTYLE:ON

        try {
            validateStudyExist(studyProtocolDTO, AMENDMENT);
            studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyProtocolDTO.setCtroOverride(BlConverter.convertToBl(Boolean.FALSE));
            Ii spIi = studyProtocolDTO.getIdentifier();
            St amender = studyProtocolDTO.getUserLastCreated();
            
            // This will get us current protocol records with amendment applied on top of it.
            studyProtocolDTO = getStudyProtocolForCreateOrAmend(studyProtocolDTO, AMENDMENT);
            
            Timestamp amendmentCreationDate = new Timestamp(System.currentTimeMillis());
            Timestamp previousProtocolRecordDate = TsConverter
                    .convertToTimestamp(studyProtocolDTO.getDateLastCreated());
            
            if (studyRegAuthDTO != null) {
                studyRegAuthDTO.setStudyProtocolIdentifier(studyProtocolDTO.getIdentifier());
                StudyRegulatoryAuthorityDTO tempDTO = studyRegulatoryAuthorityService
                        .getCurrentByStudyProtocol(studyProtocolDTO
                                .getIdentifier());
                if (tempDTO != null) {
                    studyRegAuthDTO.setIdentifier(tempDTO.getIdentifier());
                }
            }
            
            // PO-6172.
            if (BlConverter
                    .convertToBool(handleDuplicateGrantAndINDsGracefully)) {
                studyResourcingService.matchToExistentGrants(
                        studyResourcingDTOs, studyProtocolDTO.getIdentifier());
                studyIndldeService.matchToExistentIndIde(studyIndldeDTOs,
                        studyProtocolDTO.getIdentifier());
            }
            
            TrialRegistrationValidator validator = createValidator();
            StudySiteDTO nctIdentifierDTO = getPAServiceUtils().extractNCTDto(studyIdentifierDTOs);
            validator.validateAmendment(studyProtocolDTO, overallStatusDTO, leadOrganizationDTO,
                                        sponsorOrganizationDTO, 
                                        summary4OrganizationDTO, summary4StudyResourcingDTO, principalInvestigatorDTO,
                                        partyDTO,
                                        studyRegAuthDTO, studyResourcingDTOs, documentDTOs,
                                        studyIndldeDTOs, nctIdentifierDTO);
            PAServiceUtils paServiceUtils = getPAServiceUtils();
            Ii toStudyProtocolIi = paServiceUtils.copy(studyProtocolDTO.getIdentifier());
            updateStudyProtocol(studyProtocolDTO, toStudyProtocolIi);            
            updateStudyIdentifiers(spIi, studyIdentifierDTOs);
            paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), spIi);
            paServiceUtils.createOrUpdate(studyResourcingDTOs, IiConverter.convertToStudyResourcingIi(null), spIi);
            updateRegulatoryAndSponsorInfo(studyProtocolDTO,
                    leadOrganizationDTO, principalInvestigatorDTO,
                    sponsorOrganizationDTO, partyDTO, studyRegAuthDTO);
            // update summary4
            paServiceUtils.manageSummaryFour(spIi, summary4OrganizationDTO, summary4StudyResourcingDTO);
            updateLeadOrganizationID(spIi, leadOrganizationDTO, leadOrganizationSiteIdentifierDTO);

            paServiceUtils.managePrincipalInvestigator(spIi, leadOrganizationDTO, principalInvestigatorDTO);
            overallStatusDTO.setStudyProtocolIdentifier(spIi);
            createStudyRelationship(spIi, toStudyProtocolIi, studyProtocolDTO);
            paServiceUtils.createMilestone(spIi, MilestoneCode.SUBMISSION_RECEIVED, null, null);
            studyOverallStatusService.create(overallStatusDTO);
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
            documentService.markAsOriginalSubmission(savedDocs);
            saveAmenderInfo(studyProtocolDTO, amender, true);
            
            // PO-5806: date_last_created fields get reversed for amendment and original.
            // Need to fix this here.
            if (previousProtocolRecordDate != null) {
                String updateOrigDate = "UPDATE study_protocol SET date_last_created = '"
                        + previousProtocolRecordDate
                        + "' WHERE identifier="
                        + IiConverter.convertToLong(toStudyProtocolIi);
                getPAServiceUtils().executeSql(updateOrigDate);
            }
            String updateAmendDate = "UPDATE study_protocol SET date_last_created = '"
                    + amendmentCreationDate
                    + "' WHERE identifier="
                    + IiConverter.convertToLong(studyProtocolDTO.getIdentifier());
            getPAServiceUtils().executeSql(updateAmendDate);
            
            studyProtocolService
                    .updatePendingTrialAssociationsToActive(IiConverter
                            .convertToLong(spIi));
            sendMail(AMENDMENT, isBatchMode, spIi, new ArrayList<String>(), EMPTY_STR);
            return studyProtocolDTO.getIdentifier();
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }

    private void saveAmenderInfo(StudyProtocolDTO studyProtocolDTO, St amender, boolean updateStudyMileStone)
            throws PAException {        
        if (!ISOUtil.isStNull(amender)) {
            Long studyProtocolId = IiConverter.convertToLong(studyProtocolDTO.getIdentifier());
            User user = CSMUserService.getInstance().getCSMUser(amender.getValue());
            if (user == null) {
                throw new PAException("Unable to find the amending user's account record.");
            }
            String spUpdate = "UPDATE study_protocol SET user_last_created_id = "
                    + user.getUserId() + " WHERE identifier=" + studyProtocolId;            
            getPAServiceUtils().executeSql(spUpdate);            
            if (updateStudyMileStone) {
            //At this point there is only 1 record in study_milestone table. 
                //Hence safe to fire an update. 
                String smUpdate = "UPDATE study_milestone SET user_last_created_id = "
                        + user.getUserId() + " , user_last_updated_id = " 
                        + user.getUserId() + " WHERE study_protocol_identifier=" + studyProtocolId;
                getPAServiceUtils().executeSql(smUpdate);
            }
        }
    }

    /**
     * Updates the study protocol identifiers.
     * @param spIi The study protocol Ii
     * @param studyIdentifierDTOs The list of study Identifier
     * @throws PAException if an error occurs
     */
    void updateStudyIdentifiers(Ii spIi, List<StudySiteDTO> studyIdentifierDTOs) throws PAException {
        if (CollectionUtils.isNotEmpty(studyIdentifierDTOs)) {
            PAServiceUtils paServiceUtils = getPAServiceUtils();
            for (StudySiteDTO studyIdentifierDTO : studyIdentifierDTOs) {
                if (studyIdentifierDTO != null
                        && !ISOUtil.isStNull(studyIdentifierDTO.getLocalStudyProtocolIdentifier())
                        && !ISOUtil.isIiNull(studyIdentifierDTO.getResearchOrganizationIi())) {
                    studyIdentifierDTO.setStudyProtocolIdentifier(spIi);
                    Cd functionalCode = CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                    studyIdentifierDTO.setFunctionalCode(functionalCode);
                    paServiceUtils.manageStudyIdentifiers(studyIdentifierDTO);
                }
            }
        }
    }

    /**
     * Updates the regulatory authority, the respomsible party and the sponsor.
     * @param studyProtocolDTO The study protocol
     * @param leadOrganizationDTO The lead organization
     * @param principalInvestigatorDTO The principal investigator
     * @param sponsorOrganizationDTO The sponsor organization
     * @param studyContactDTO The study contact
     * @param studySiteContactDTO The study site contact
     * @param responsiblePartyContactIi The responsible party Ii
     * @param studyRegAuthDTO The regulatory authority
     * @throws PAException if an error occurs
     */
    // CHECKSTYLE:OFF More than 7 Parameters
    void updateRegulatoryAndSponsorInfo(StudyProtocolDTO studyProtocolDTO, OrganizationDTO leadOrganizationDTO, //NOPMD
            PersonDTO principalInvestigatorDTO, OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO) throws PAException {
        // CHECKSTYLE:ON
        PAServiceUtils paServiceUtils = getPAServiceUtils();
        Ii spIi = studyProtocolDTO.getIdentifier();
        if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            if (studyRegAuthDTO != null) {
                List<StudyRegulatoryAuthorityDTO> sraDto = new ArrayList<StudyRegulatoryAuthorityDTO>();
                sraDto.add(studyRegAuthDTO);
                paServiceUtils.createOrUpdate(sraDto, IiConverter.convertToStudyRegulatoryAuthorityIi(null), spIi);

            }            
            paServiceUtils.manageSponsor(spIi, sponsorOrganizationDTO);           
            createResponsibleParty(partyDTO,
                    sponsorOrganizationDTO, spIi);
        } else {
            paServiceUtils.removeRegulatoryAuthority(spIi);
            paServiceUtils.removeResponsibleParty(spIi);
            paServiceUtils.removeSponsor(spIi);
        }
    }

    /**
     * update the study site identifier.
     * @param spIi The study protocol
     * @param loDTO The lead organization
     * @param loSiteDTO The lead organization site
     * @throws PAException if an error occurs
     */
    void updateLeadOrganizationID(Ii spIi, OrganizationDTO loDTO, StudySiteDTO loSiteDTO) throws PAException {
        if (loSiteDTO != null) {
            loSiteDTO.setStudyProtocolIdentifier(spIi);
            ocsr.createResearchOrganizationCorrelations(loDTO.getIdentifier().getExtension());
            loSiteDTO
                .setResearchOrganizationIi(ocsr.getPoResearchOrganizationByEntityIdentifier(loDTO.getIdentifier()));
            loSiteDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        }
        getPAServiceUtils().manageStudyIdentifiers(loSiteDTO);
    }

    private String createInboxProcessingComments(Ii spIi, List<DocumentDTO> docs) throws PAException {

        String inboxProcessingComments = TrialUpdatesRecorder
                .getRecordedUpdates();
        
        // Store changes resulted from the update.
        if (StringUtils.isNotEmpty(inboxProcessingComments)) {
            
            // Store validation errors resulted from the update separately.
            DocumentWorkflowStatusDTO isoDocWrkStatus = documentWorkFlowStatusService
                    .getCurrentByStudyProtocol(spIi);
            StringBuilder sbuf = new PAServiceUtils()
                    .createAbstractionValidationErrorsTable(spIi,
                            isoDocWrkStatus);
            if (sbuf.length() != 0) {
                createStudyInboxRecord(spIi, sbuf.toString(), null, StudyInboxTypeCode.VALIDATION);
            }

            createStudyInboxRecord(spIi, inboxProcessingComments, docs, StudyInboxTypeCode.UPDATE);
        }
        return inboxProcessingComments;
    }

    

    /**
     * @param spIi
     * @param inboxProcessingComments
     * @param docs 
     * @throws PAException
     */
    private void createStudyInboxRecord(Ii spIi, String inboxProcessingComments, List<DocumentDTO> docs,
            StudyInboxTypeCode typeCode)
            throws PAException {
        StudyInboxDTO studyInboxDTO = new StudyInboxDTO();
        studyInboxDTO.setStudyProtocolIdentifier(spIi);
        studyInboxDTO.setInboxDateRange(IvlConverter.convertTs()
                .convertToIvl(new Timestamp(new Date().getTime()), null));
        studyInboxDTO.setComments(StConverter.convertToSt(inboxProcessingComments));
        studyInboxDTO.setTypeCode(CdConverter.convertToCd(typeCode));
        StudyInboxDTO createdInbox = studyInboxServiceLocal.create(studyInboxDTO);
        if (docs != null) {
            documentService.associateDocumentsWithStudyInbox(docs,
                    createdInbox);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    @Override
    @Deprecated
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO,
            Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        return createCompleteInterventionalStudyProtocol(studyProtocolDTO,
                overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, leadOrganizationSiteIdentifierDTO,
                studyIdentifierDTOs, studyContactDTO, studySiteContactDTO,
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                responsiblePartyContactIi, studyRegAuthDTO, isBatchMode, null);
    }
    // CHECKSTYLE:ON
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "PMD.ExcessiveParameterList",
            "PMD.CyclomaticComplexity" })    
    @Override
    @Deprecated
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, StudyContactDTO studyContactDTO,
            StudySiteContactDTO studySiteContactDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Ii responsiblePartyContactIi,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException {
        ResponsiblePartyDTO party = getResponsiblePartyDTO(leadOrganizationDTO,
                principalInvestigatorDTO, studyContactDTO);
        return createCompleteInterventionalStudyProtocol(studyProtocolDTO,
                overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, party,
                leadOrganizationSiteIdentifierDTO, studyIdentifierDTOs,
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                studyRegAuthDTO, isBatchMode, owners);
        
    }
            // CHECKSTYLE:ON

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    @Override
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ii createCompleteInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO,
            List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO,
            ResponsiblePartyDTO partyDTO, 
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode)
            throws PAException {
        return createCompleteInterventionalStudyProtocol(studyProtocolDTO,
                overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                documentDTOs, leadOrganizationDTO, principalInvestigatorDTO,
                sponsorOrganizationDTO, partyDTO,  
                leadOrganizationSiteIdentifierDTO,
                studyIdentifierDTOs, 
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                studyRegAuthDTO, isBatchMode, null);
    }
    // CHECKSTYLE:ON
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "PMD.ExcessiveParameterList",
            "PMD.CyclomaticComplexity" })    
    @Override
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Ii createCompleteInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudyOverallStatusDTO overallStatusDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO principalInvestigatorDTO,
            OrganizationDTO sponsorOrganizationDTO, ResponsiblePartyDTO partyDTO,  
            StudySiteDTO leadOrganizationSiteIdentifierDTO,
            List<StudySiteDTO> studyIdentifierDTOs, 
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, 
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException {
            // CHECKSTYLE:ON
            copyStudyResourcing(studyResourcingDTOs);
            TrialRegistrationValidator validator = createValidator();
            StudySiteDTO nctIdentifierDTO = getPAServiceUtils().extractNCTDto(studyIdentifierDTOs);
            validator.validateCreation(studyProtocolDTO, overallStatusDTO, leadOrganizationDTO, sponsorOrganizationDTO,
                                       partyDTO,
                                       summary4OrganizationDTO,
                                       summary4StudyResourcingDTO, principalInvestigatorDTO,
                                       leadOrganizationSiteIdentifierDTO, studyRegAuthDTO,
                                       studyResourcingDTOs, documentDTOs, studyIndldeDTOs, nctIdentifierDTO);
            PAServiceUtils paServiceUtils = getPAServiceUtils();
            
            studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.FALSE));
            studyProtocolDTO.setCtroOverride(BlConverter.convertToBl(Boolean.FALSE));
            
            List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
            listOfDTOToCreateInPO.add(leadOrganizationDTO);

            boolean ctgovXmlRequired = studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue();
            if (ctgovXmlRequired) {
                listOfDTOToCreateInPO.add(sponsorOrganizationDTO);
            }
            listOfDTOToCreateInPO.add(principalInvestigatorDTO);
            List<OrganizationDTO> newSummary4OrganizationList = new ArrayList<OrganizationDTO>();
            if (CollectionUtils.isNotEmpty(summary4OrganizationDTO)) {
                for (OrganizationDTO summary4Org : summary4OrganizationDTO) {
                    OrganizationDTO newSummary4OrganizationDTO = paServiceUtils.findOrCreateEntity(summary4Org);
                    listOfDTOToCreateInPO.add(newSummary4OrganizationDTO);
                    paServiceUtils.createPoObject(listOfDTOToCreateInPO);
                    newSummary4OrganizationList.add(newSummary4OrganizationDTO);
                }
            }
            if (shouldDefaultFdaIndicator(studyProtocolDTO, studyIndldeDTOs, ctgovXmlRequired)) {
                studyProtocolDTO.setFdaRegulatedIndicator(BlConverter.convertToBl(Boolean.TRUE));
                studyProtocolDTO.setSection801Indicator(BlConverter.convertToBl(Boolean.FALSE));
            }
            studyProtocolDTO.setIdentifier(null);
            studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));
            Ii spIi = createStudyProtocol(studyProtocolDTO);
            paServiceUtils.createMilestone(spIi, MilestoneCode.SUBMISSION_RECEIVED, null, null);
            overallStatusDTO.setStudyProtocolIdentifier(spIi);
            studyOverallStatusService.create(overallStatusDTO);
            paServiceUtils.createOrUpdate(studyIndldeDTOs, IiConverter.convertToStudyIndIdeIi(null), spIi);
            paServiceUtils.createOrUpdate(studyResourcingDTOs, IiConverter.convertToStudyResourcingIi(null), spIi);

            paServiceUtils.manageSummaryFour(spIi, newSummary4OrganizationList, summary4StudyResourcingDTO);

            updateLeadOrganizationID(spIi, leadOrganizationDTO, leadOrganizationSiteIdentifierDTO);            
            paServiceUtils.managePrincipalInvestigator(spIi, leadOrganizationDTO, principalInvestigatorDTO);
            if (ctgovXmlRequired) {
                createSponsor(spIi, sponsorOrganizationDTO);
                createResponsibleParty(partyDTO,
                    sponsorOrganizationDTO, spIi);
            }
            // list of study identifiers like NCT,DCP, CTEP
            updateStudyIdentifiers(spIi, studyIdentifierDTOs);
            addNciOrgAsCollaborator(studyProtocolDTO, spIi);
            if (ctgovXmlRequired && studyRegAuthDTO != null) {
                studyRegAuthDTO.setStudyProtocolIdentifier(spIi);
                paServiceUtils.createOrUpdate(Arrays.asList(studyRegAuthDTO), IiConverter
                    .convertToStudyRegulatoryAuthorityIi(null), spIi);
            }
            assignOwnership(studyProtocolDTO, spIi);
            paServiceUtils.addNciIdentifierToTrial(spIi);
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
            documentService.markAsOriginalSubmission(savedDocs);            
            Collection<String> unmatchedEmails = new ArrayList<String>();
            if (owners != null && owners.getItem() != null) {
                unmatchedEmails = studyProtocolService.changeOwnership(spIi, owners);
            }  
            studyProtocolService
                .updatePendingTrialAssociationsToActive(IiConverter
                    .convertToLong(spIi));
            sendMail(CREATE, isBatchMode, spIi, unmatchedEmails, EMPTY_STR);            
            return spIi;
    }
    

    private void copyStudyResourcing(List<StudyResourcingDTO> studyResourcingDTOs) {
        if (CollectionUtils.isNotEmpty(studyResourcingDTOs)) {
            for (StudyResourcingDTO studyResourcingDTO : studyResourcingDTOs) {
                studyResourcingDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
                studyResourcingDTO.setOrganizationIdentifier(null);
                studyResourcingDTO.setTypeCode(null);
            }
        }
    }

    private boolean shouldDefaultFdaIndicator(StudyProtocolDTO studyProtocolDTO, List<StudyIndldeDTO> studyIndldeDTOs,
            boolean ctgovXmlRequired) {
        return ctgovXmlRequired && !BlConverter.convertToBool(studyProtocolDTO.getFdaRegulatedIndicator())
                && (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings({ "PMD.ExcessiveParameterList",
        "PMD.CyclomaticComplexity" }) 
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createAbbreviatedInterventionalStudyProtocol(
            StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO,
            List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO,
            PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO,
            OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO,
            List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode)
            throws PAException {
        return createAbbreviatedInterventionalStudyProtocol(studyProtocolDTO,
                studySiteAccrualStatusDTO, documentDTOs, leadOrganizationDTO,
                studySiteInvestigatorDTO, leadOrganizationStudySiteDTO,
                studySiteOrganizationDTO, studySiteDTO, nctIdentifierDTO,
                summary4OrganizationDTO, summary4StudyResourcingDTO,
                isBatchMode, null);
    }    
    // CHECKSTYLE:ON
    
    // CHECKSTYLE:OFF More than 7 Parameters
    
    
    @Override    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii updateAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO, // NOPMD
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO responsiblePartyDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs) throws PAException {
        
        Ii spIi = studyProtocolDTO.getIdentifier();        
        
        prepareAbbreviatedProtocolForCreationOrUpdate(studyProtocolDTO);
        
        TrialRegistrationValidator validator = createValidator();
        validator.validateProprietaryUpdate(studyProtocolDTO, nctID,
                leadOrgDTO, leadOrgID, sponsorDTO, investigatorDTO,
                centralContactDTO, overallStatusDTO, studyRegAuthDTO, arms,
                eligibility, outcomes, collaborators);
        
        createPoObjects(leadOrgDTO, sponsorDTO, investigatorDTO, responsiblePartyDTO,
                centralContactDTO, collaborators);
       
        try {
            updateStudyProtocol(studyProtocolDTO);                        
            updateUserLastCreated(studyProtocolDTO);
            
            String originalLeadOrgID = getPAServiceUtils().getStudyIdentifier(
                    spIi, PAConstants.LEAD_IDENTIFER_TYPE);
            String newLeadOrgID = StConverter.convertToString(leadOrgID
                    .getLocalStudyProtocolIdentifier());
            updateLeadOrganizationID(spIi, leadOrgDTO, leadOrgID);
            if (!equalsIgnoreCase(trim(originalLeadOrgID), trim(newLeadOrgID))) {
                addTrialOtherIdentifier(spIi, originalLeadOrgID);
            }
            
            if (sponsorDTO != null) {
                getPAServiceUtils().manageSponsor(spIi, sponsorDTO);
                createSummary4Sponsor(spIi, sponsorDTO);
            }
            
            createPrincipalInvestigator(leadOrgDTO, investigatorDTO, spIi);        
            createResponsibleParty(responsiblePartyDTO, sponsorDTO, spIi);
            createCentralContact(leadOrgDTO, centralContactDTO, spIi);            
            createOverallStatus(overallStatusDTO, spIi);            
            createRegulatoryAuthority(studyRegAuthDTO, spIi);            
            createArms(arms, spIi);            
            createEligibilityCriteria(eligibility, spIi);            
            createOutcomeMeasures(outcomes, spIi);            
            createCollaborators(collaborators, spIi);            
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
            
            TrialUpdatesRecorder.reset();
            TrialUpdatesRecorder.recordUpdate("Trial has been updated from ClinicalTrials.gov");
            createInboxProcessingComments(spIi, savedDocs);
            PaHibernateUtil.getCurrentSession().flush();
            return spIi;
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        } finally {
            TrialUpdatesRecorder.reset();
        }
    }

    private void addTrialOtherIdentifier(Ii spIi, String value)
            throws PAException {
        PaHibernateUtil.getCurrentSession().flush();
        StudyProtocolDTO dto = studyProtocolService.getStudyProtocol(spIi);
        for (Ii otherID : PAUtil.getOtherIdentifiers(dto)) {
            if (equalsIgnoreCase(otherID.getExtension(), value)) {
                return;
            }
        }
        Ii ii = IiConverter.convertToOtherIdentifierIi(value);
        dto.getSecondaryIdentifiers().getItem().add(ii);
        updateStudyProtocol(dto);
    }

    private void createSummary4Sponsor(Ii spIi, OrganizationDTO sponsorDTO)
            throws PAException {
        StudyResourcingDTO summary4studyResourcingDTO = new StudyResourcingDTO();
        summary4studyResourcingDTO.setTypeCode(CdConverter
                .convertToCd(SummaryFourFundingCategoryCode.INDUSTRIAL));
        getPAServiceUtils().manageSummaryFour(spIi, Arrays.asList(sponsorDTO),
                summary4studyResourcingDTO);

    }

    /**
     * @param leadOrgDTO
     * @param centralContactDTO
     * @param spIi
     * @throws PAException
     */
    private void createCentralContact(OrganizationDTO leadOrgDTO,
            PersonDTO centralContactDTO, Ii spIi) throws PAException {
        if (centralContactDTO != null) {
            createCentralContact(spIi, centralContactDTO, leadOrgDTO);
        }
    }

    /**
     * @param leadOrgDTO
     * @param investigatorDTO
     * @param spIi
     * @throws PAException
     */
    private void createPrincipalInvestigator(OrganizationDTO leadOrgDTO,
            PersonDTO investigatorDTO, Ii spIi) throws PAException {
        if (investigatorDTO != null) {                
            getPAServiceUtils().managePrincipalInvestigator(spIi,
                    leadOrgDTO, investigatorDTO);
        }
    }

    /**
     * @param overallStatusDTO
     * @param spIi
     * @throws PAException
     */
    private void createOverallStatus(StudyOverallStatusDTO overallStatusDTO,
            Ii spIi) throws PAException {
        if (overallStatusDTO != null) {
            overallStatusDTO.setStudyProtocolIdentifier(spIi);
            studyOverallStatusService.createRelaxed(overallStatusDTO);
        }
    }

    /**
     * @param studyRegAuthDTO
     * @param spIi
     * @throws PAException
     */
    private void createRegulatoryAuthority(
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, Ii spIi)
            throws PAException {
        if (studyRegAuthDTO != null) {
            StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = studyRegulatoryAuthorityService
                    .getCurrentByStudyProtocol(spIi);
            if (sraFromDatabaseDTO != null) {
                studyRegAuthDTO.setIdentifier(sraFromDatabaseDTO
                        .getIdentifier());
            }

            getPAServiceUtils()
                    .createOrUpdate(
                            Arrays.asList(studyRegAuthDTO),
                            IiConverter
                                    .convertToStudyRegulatoryAuthorityIi(null),
                            spIi);
        }
    }

    /**
     * @param collaborators
     * @param spIi
     * @throws PAException
     */
    private void createCollaborators(List<OrganizationDTO> collaborators,
            Ii spIi) throws PAException {
        if (collaborators == null) {
            return;
        }
        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spIi,
                criteriaList);
        for (StudySiteDTO sp : spList) {
            studySiteService.delete(sp.getIdentifier());
        }
        for (OrganizationDTO dto : collaborators) {
            createCollaborator(dto, spIi);
        }
    }

    /**
     * @param outcomes
     * @param spIi
     * @throws PAException
     */
    private void createOutcomeMeasures(List<StudyOutcomeMeasureDTO> outcomes,
            Ii spIi) throws PAException {
        for (StudyOutcomeMeasureDTO dto : studyOutcomeMeasureService
                .getByStudyProtocol(spIi)) {
            studyOutcomeMeasureService.delete(dto.getIdentifier());
        }
        for (StudyOutcomeMeasureDTO dto : outcomes) {
            dto.setStudyProtocolIdentifier(spIi);
            studyOutcomeMeasureService.create(dto);
        }
    }

    /**
     * @param eligibility
     * @param spIi
     * @throws PAException
     */
    private void createEligibilityCriteria(
            List<PlannedEligibilityCriterionDTO> eligibility, Ii spIi)
            throws PAException {
        for (PlannedEligibilityCriterionDTO dto: plannedActivityService.getPlannedEligibilityCriterionByStudyProtocol(spIi)) {            
            plannedActivityService.deletePlannedEligibilityCriterion(dto.getIdentifier());
        }
        for (PlannedEligibilityCriterionDTO dto: eligibility) {
            dto.setStudyProtocolIdentifier(spIi);
            plannedActivityService.createPlannedEligibilityCriterion(dto);
        }
    }

    /**
     * @param arms
     * @param spIi
     * @throws PAException
     */
    private void createArms(List<ArmDTO> arms, Ii spIi) throws PAException {
        for (ArmDTO arm: armService.getByStudyProtocol(spIi)) {
            armService.delete(arm.getIdentifier());
        }
        for (ArmDTO arm: arms) {
            arm.setStudyProtocolIdentifier(spIi);
            armService.create(arm);
        }
    }

    /**
     * @param leadOrgDTO
     * @param sponsorDTO
     * @param investigatorDTO
     * @param centralContactDTO
     * @param collaborators
     * @throws PAException
     */
    private void createPoObjects(OrganizationDTO leadOrgDTO, // NOPMD
            OrganizationDTO sponsorDTO, PersonDTO investigatorDTO,
            ResponsiblePartyDTO respPartyDTO, PersonDTO centralContactDTO,
            List<OrganizationDTO> collaborators) throws PAException {
        List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
        listOfDTOToCreateInPO.add(leadOrgDTO);
        listOfDTOToCreateInPO.add(sponsorDTO);
        listOfDTOToCreateInPO.add(investigatorDTO);
        listOfDTOToCreateInPO.add(centralContactDTO);
        if (collaborators != null) {
            listOfDTOToCreateInPO.addAll(collaborators);
        }
        if (respPartyDTO != null) {
            listOfDTOToCreateInPO.add(respPartyDTO.getAffiliation());
            listOfDTOToCreateInPO.add(respPartyDTO.getInvestigator());
        }

        POServiceUtils.matchOrCreatePoObjects(listOfDTOToCreateInPO);
    }

    
    
    @SuppressWarnings("unchecked")
    @Override    
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createAbbreviatedStudyProtocol(StudyProtocolDTO studyProtocolDTO, // NOPMD
            StudySiteDTO nctID, OrganizationDTO leadOrgDTO,
            StudySiteDTO leadOrgID, OrganizationDTO sponsorDTO,
            PersonDTO investigatorDTO, ResponsiblePartyDTO partyDTO, PersonDTO centralContactDTO,
            StudyOverallStatusDTO overallStatusDTO,
            StudyRegulatoryAuthorityDTO studyRegAuthDTO, List<ArmDTO> arms,
            List<PlannedEligibilityCriterionDTO> eligibility,
            List<StudyOutcomeMeasureDTO> outcomes,
            List<OrganizationDTO> collaborators, List<DocumentDTO> documentDTOs) throws PAException {
        
        prepareAbbreviatedProtocolForCreationOrUpdate(studyProtocolDTO);
        TrialRegistrationValidator validator = createValidator();
        validator.validateProprietaryCreation(studyProtocolDTO, nctID,
                leadOrgDTO, leadOrgID, sponsorDTO, investigatorDTO,
                centralContactDTO, overallStatusDTO, studyRegAuthDTO, arms,
                eligibility, outcomes, collaborators);
        
        createPoObjects(leadOrgDTO, sponsorDTO, investigatorDTO, partyDTO,
                centralContactDTO, collaborators);
        
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));

        try {
            Ii spIi = createStudyProtocol(studyProtocolDTO);
            getPAServiceUtils().createMilestone(spIi, MilestoneCode.SUBMISSION_RECEIVED, null, null);
            
            updateLeadOrganizationID(spIi, leadOrgDTO, leadOrgID);

            nctID.setStudyProtocolIdentifier(spIi);
            nctID.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
            String poOrgId = ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
            nctID.setResearchOrganizationIi(ocsr.getPoResearchOrganizationByEntityIdentifier(IiConverter
                    .convertToPoOrganizationIi(String.valueOf(poOrgId))));
            getPAServiceUtils().manageStudyIdentifiers(nctID);
            
            if (sponsorDTO != null) {
                createSponsor(spIi, sponsorDTO);
                createSummary4Sponsor(spIi, sponsorDTO);
            }
            
            createPrincipalInvestigator(leadOrgDTO, investigatorDTO, spIi);
            
            createResponsibleParty(partyDTO, sponsorDTO, spIi);
            
            createCentralContact(leadOrgDTO, centralContactDTO, spIi);
            
            createOverallStatus(overallStatusDTO, spIi);
            
            createRegulatoryAuthority(studyRegAuthDTO, spIi);
            
            createArms(arms, spIi);
            
            createEligibilityCriteria(eligibility, spIi);
            
            createOutcomeMeasures(outcomes, spIi);
            
            createCollaborators(collaborators, spIi);
            
            assignOwnership(studyProtocolDTO, spIi);
            getPAServiceUtils().addNciIdentifierToTrial(spIi);
            
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
            documentService.markAsOriginalSubmission(savedDocs);
            
            updateUserLastCreated(studyProtocolDTO);
            
            studyProtocolService
                    .updatePendingTrialAssociationsToActive(IiConverter
                            .convertToLong(spIi));         
            sendMail(CREATE, BlConverter.convertToBl(false), spIi,
                    CollectionUtils.EMPTY_COLLECTION, EMPTY_STR);
            PaHibernateUtil.getCurrentSession().flush();
            return spIi;
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }
    
    private void createResponsibleParty(ResponsiblePartyDTO partyDTO,
            OrganizationDTO sponsorDTO, Ii spIi) throws PAException {
        if (partyDTO == null) {
            return;
        }
        
        final PAServiceUtils utils = getPAServiceUtils();
        utils.removeResponsibleParty(spIi);        
        utils.createResponsibleParty(spIi, partyDTO, sponsorDTO);

    }

    @SuppressWarnings(DEPRECATION)
    private void createCollaborator(OrganizationDTO org, Ii spIi)
            throws PAException {
        String poOrgId = org.getIdentifier().getExtension();
        Long paOrgId = ocsr.createResearchOrganizationCorrelations(poOrgId);

        StudySiteDTO sp = new StudySiteDTO();
        sp.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.ACTIVE));
        sp.setFunctionalCode(CdConverter
                .convertToCd(StudySiteFunctionalCode.LABORATORY));
        sp.setResearchOrganizationIi(IiConverter.convertToIi(paOrgId));
        sp.setStudyProtocolIdentifier(spIi);
        sp = studySiteService.create(sp);

    }

    @SuppressWarnings(DEPRECATION)
    private void createCentralContact(Ii spIi, PersonDTO centralContactDTO,
            OrganizationDTO leadOrg) throws PAException {
        
        for (StudyContactDTO existentContact : getPAServiceUtils()
                .getStudyContact(spIi, StudyContactRoleCode.CENTRAL_CONTACT,
                        false)) {
            studyContactService.delete(existentContact.getIdentifier());
        }
        
        StudyContactDTO scDTO = new StudyContactDTO();
        scDTO.setStudyProtocolIdentifier(spIi);
        scDTO.setRoleCode(CdConverter
                .convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));

        ClinicalResearchStaffCorrelationServiceBean crbb = new ClinicalResearchStaffCorrelationServiceBean();
        Long crs = crbb.createClinicalResearchStaffCorrelations(leadOrg
                .getIdentifier().getExtension(), centralContactDTO
                .getIdentifier().getExtension());
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crs));
        scDTO.setOrganizationalContactIi(IiConverter.convertToIi(""));       
        scDTO.setTelecomAddresses(centralContactDTO.getTelecomAddress());
        scDTO.setStatusCode(CdConverter
                .convertToCd(FunctionalRoleStatusCode.PENDING));
        studyContactService.create(scDTO);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.ExcessiveParameterList")
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Ii createAbbreviatedInterventionalStudyProtocol(StudyProtocolDTO studyProtocolDTO,
            StudySiteAccrualStatusDTO studySiteAccrualStatusDTO, List<DocumentDTO> documentDTOs,
            OrganizationDTO leadOrganizationDTO, PersonDTO studySiteInvestigatorDTO,
            StudySiteDTO leadOrganizationStudySiteDTO, OrganizationDTO studySiteOrganizationDTO,
            StudySiteDTO studySiteDTO, StudySiteDTO nctIdentifierDTO, List<OrganizationDTO> summary4OrganizationDTO,
            StudyResourcingDTO summary4StudyResourcingDTO, Bl isBatchMode, DSet<Tel> owners) throws PAException {
        // CHECKSTYLE:ON
        // validate method needs to be here
        prepareAbbreviatedProtocolForCreationOrUpdate(studyProtocolDTO);
        TrialRegistrationValidator validator = createValidator();
        validator.validateProprietaryCreation(studyProtocolDTO, studySiteAccrualStatusDTO, documentDTOs,
                                              leadOrganizationDTO, studySiteInvestigatorDTO,
                                              leadOrganizationStudySiteDTO, studySiteOrganizationDTO, studySiteDTO,
                                              nctIdentifierDTO, summary4OrganizationDTO, summary4StudyResourcingDTO);
        List<PoDto> listOfDTOToCreateInPO = new ArrayList<PoDto>();
        listOfDTOToCreateInPO.add(leadOrganizationDTO);
        listOfDTOToCreateInPO.add(studySiteOrganizationDTO);
        listOfDTOToCreateInPO.add(studySiteInvestigatorDTO);
        if (CollectionUtils.isNotEmpty(summary4OrganizationDTO)) {
            for (OrganizationDTO summary4Org : summary4OrganizationDTO) {
                listOfDTOToCreateInPO.add(summary4Org);
            }
        }
        getPAServiceUtils().createPoObject(listOfDTOToCreateInPO);
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt("1"));

        try {
            Ii spIi = createStudyProtocol(studyProtocolDTO);
            getPAServiceUtils().createMilestone(spIi, MilestoneCode.SUBMISSION_RECEIVED, null, null);

            getPAServiceUtils().manageSummaryFour(spIi, summary4OrganizationDTO, summary4StudyResourcingDTO);
            updateLeadOrganizationID(spIi, leadOrganizationDTO, leadOrganizationStudySiteDTO);

            nctIdentifierDTO.setStudyProtocolIdentifier(spIi);
            nctIdentifierDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
            String poOrgId = ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
            nctIdentifierDTO.setResearchOrganizationIi(ocsr.getPoResearchOrganizationByEntityIdentifier(IiConverter
                    .convertToPoOrganizationIi(String.valueOf(poOrgId))));
            getPAServiceUtils().manageStudyIdentifiers(nctIdentifierDTO);
            // create StudySite
            Ii studySiteIi = createStudySite(spIi, studySiteOrganizationDTO, studySiteDTO);
            studySiteAccrualStatusDTO.setStudySiteIi(studySiteIi);
            studySiteAccrualStatusService.createStudySiteAccrualStatus(studySiteAccrualStatusDTO);
            // set PI            
            createStudySiteContact(studySiteIi, spIi, studySiteOrganizationDTO, studySiteInvestigatorDTO);
            assignOwnership(studyProtocolDTO, spIi);
            getPAServiceUtils().addNciIdentifierToTrial(spIi);            
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
            documentService.markAsOriginalSubmission(savedDocs);            
            Collection<String> unmatchedEmails = new ArrayList<String>();
            if (owners != null && owners.getItem() != null) {
                unmatchedEmails = studyProtocolService.changeOwnership(spIi, owners);
            }
            studyProtocolService
                    .updatePendingTrialAssociationsToActive(IiConverter
                            .convertToLong(spIi));         
            sendMail(CREATE, isBatchMode, spIi, unmatchedEmails, EMPTY_STR);
            
            return spIi;
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }

    /**
     * @param studyProtocolDTO
     */
    private void prepareAbbreviatedProtocolForCreationOrUpdate(
            StudyProtocolDTO studyProtocolDTO) {
        setPhaseAdditionalQualifier(studyProtocolDTO, studyProtocolDTO);
        setPrimaryPurposeCode(studyProtocolDTO, studyProtocolDTO);
        studyProtocolDTO.setProprietaryTrialIndicator(BlConverter.convertToBl(Boolean.TRUE));
        studyProtocolDTO.setCtroOverride(BlConverter.convertToBl(Boolean.FALSE));
    }
    

    /**
     * Creates a new Study protocol.
     * @param studyProtocolDTO The study protocol.
     * @return The Ii of the new study protocol
     * @throws PAException if an error occurs.
     */
    Ii createStudyProtocol(StudyProtocolDTO studyProtocolDTO)
            throws PAException {
        StudyProtocolDTO spDTO = getStudyProtocolForCreateOrAmend(
                studyProtocolDTO, CREATE);
        if (spDTO instanceof InterventionalStudyProtocolDTO) {
            return studyProtocolService
                    .createInterventionalStudyProtocol((InterventionalStudyProtocolDTO) spDTO);
        } else {
            return studyProtocolService
                    .createNonInterventionalStudyProtocol((NonInterventionalStudyProtocolDTO) spDTO);
        }
    }


    /**This will assign ownership.
     * @param studyProtocolDTO
     * @param isBatchMode
     * @param studyProtocolIi
     * @throws PAException
     */
    private void assignOwnership(StudyProtocolDTO studyProtocolDTO, Ii studyProtocolIi) throws PAException {
        // assign ownership
        RegistryUser usr = registryUserServiceLocal.getUser(StConverter.convertToString(studyProtocolDTO
                .getUserLastCreated()));
        if (usr != null) {
            registryUserServiceLocal.assignOwnership(usr.getId(),
                    IiConverter.convertToLong(studyProtocolIi));
        } else {
            LOG.error("A user that does *not* have an account in Registry has just registered a trial: "
                    + IiConverter.convertToLong(studyProtocolIi)
                    + ". His/her trial ownership will not be recorded, because there is no Registry user to link to..");
        }
        //PO-2646: We're adding an explicit evict of the study protocol so the complete trial is loaded
        //when searched upon later in the trial creation process. Failure to do so was resulting in NPE further down
        //the line.
        Session session = PaHibernateUtil.getCurrentSession();
        session.evict(session.get(StudyProtocol.class, IiConverter.convertToLong(studyProtocolIi)));
    }

    @SuppressWarnings("deprecation")
    private void createSponsor(Ii studyProtocolIi, OrganizationDTO sponsorOrganizationDto) throws PAException {
        String orgPoIdentifier = sponsorOrganizationDto.getIdentifier().getExtension();
        if (orgPoIdentifier == null) {
            throw new PAException("Organization Identifier is null");
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
        studyPartDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.SPONSOR.getCode()));
        studyPartDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
        studyPartDTO.setStudyProtocolIdentifier(spDTO.getIdentifier());
        studyPartDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        studySiteService.create(studyPartDTO);
    }

    private void createStudyRelationship(Ii fromStudyProtocolIi, Ii toStudyProtocolIi, StudyProtocolDTO spDto)
            throws PAException {
        StudyRelationshipDTO srDto = new StudyRelationshipDTO();
        srDto.setSequenceNumber(spDto.getSubmissionNumber());
        srDto.setSourceStudyProtocolIdentifier(toStudyProtocolIi);
        srDto.setTargetStudyProtocolIdentifier(fromStudyProtocolIi);
        srDto.setTypeCode(CdConverter.convertToCd(StudyRelationshipTypeCode.MOD));
        studyRelationshipService.create(srDto);
    }

    @SuppressWarnings(DEPRECATION)
    private Ii createStudySite(Ii studyProtocolIi, OrganizationDTO studySiteDTO, StudySiteDTO siteDTO)
            throws PAException {
        Long paHealthCareFacilityId = ocsr.createHealthCareFacilityCorrelations(studySiteDTO.getIdentifier()
                .getExtension());
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

    @SuppressWarnings(DEPRECATION)
    private void createStudySiteContact(Ii studySiteIi, Ii studyProtocolIi, OrganizationDTO siteDto, PersonDTO piDto)
            throws PAException {
        String orgPoIdentifier = siteDto.getIdentifier().getExtension();
        String perIdentifier = piDto.getIdentifier().getExtension();
        StudySiteContactDTO studySiteContactDTO = new StudySiteContactDTO();
        studySiteContactDTO.setStudySiteIi(studySiteIi);
        Long clinicalStfid = new ClinicalResearchStaffCorrelationServiceBean()
            .createClinicalResearchStaffCorrelations(orgPoIdentifier, perIdentifier);
        studySiteContactDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(clinicalStfid));
        Long healthCareProviderIi = new HealthCareProviderCorrelationBean()
            .createHealthCareProviderCorrelationBeans(orgPoIdentifier, perIdentifier);
        studySiteContactDTO.setHealthCareProviderIi(IiConverter.convertToIi(healthCareProviderIi));        
        studySiteContactDTO.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));
        studySiteContactDTO.setStudyProtocolIdentifier(studyProtocolIi);
        studySiteContactDTO.setStatusCode(CdConverter.convertStringToCd(FunctionalRoleStatusCode.PENDING.getCode()));
        studySiteContactService.create(studySiteContactDTO);
    }

    private List<String> deleteAndReplace(Ii sourceIi, Ii targetIi, StudyProtocolDTO studyProtocolDto) {
        String sqlUpd = targetIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceIi.getExtension();
        List<String> sqls = new ArrayList<String>();
        String targetId = targetIi.getExtension();
        sqls.add("Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE DOCUMENT_WORKFLOW_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from DOCUMENT WHERE STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension()
                + " and TYPE_CODE = '" + DocumentTypeCode.TSR.getCode() + "'");
        sqls.add("UPDATE STUDY_ONHOLD SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_OVERALL_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_OVERALL_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);
        sqls.add("Delete from STUDY_RECRUITMENT_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_RECRUITMENT_STATUS SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_INDLDE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_INDLDE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_RESOURCING WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE STUDY_RESOURCING SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("DELETE FROM STUDY_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("UPDATE STUDY_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd
                + " AND ROLE_CODE IN ('RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR','CENTRAL_CONTACT')");
        sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + SQL_APPEND
                + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND
                + "('RESPONSIBLE_PARTY_SPONSOR')");
        sqls.add("DELETE FROM STUDY_SITE_CONTACT WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + " AND ROLE_CODE IN "
                + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        sqls.add("UPDATE STUDY_SITE_CONTACT SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + " AND ROLE_CODE IN "
                + "('RESPONSIBLE_PARTY_SPONSOR_CONTACT')");
        // nct reject
        sqls.add("DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetId + SQL_APPEND
                + "('IDENTIFIER_ASSIGNER')");
        sqls
                .add("UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND
                        + "('IDENTIFIER_ASSIGNER')");
        // regulatory
        sqls.add("Delete from  STUDY_REGULATORY_AUTHORITY WHERE STUDY_PROTOCOL_IDENTIFIER  = " + targetId);
        sqls.add("UPDATE  STUDY_REGULATORY_AUTHORITY SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd);

        sqls.add("Delete from STUDY_RELATIONSHIP WHERE TARGET_STUDY_PROTOCOL_IDENTIFIER  = " + sourceIi.getExtension());
        String reasonCodeValue = null;
        Cd reasonCode = studyProtocolDto.getAmendmentReasonCode();
        if (reasonCode != null && !(reasonCode.getNullFlavor() != null 
              && reasonCode.getNullFlavor().equals(NullFlavor.NI))) {
              AmendmentReasonCode.getByCode(studyProtocolDto.getAmendmentReasonCode().getCode());
              reasonCodeValue = reasonCode.getCode().toUpperCase();
              sqls.add("UPDATE STUDY_PROTOCOL SET STATUS_CODE='INACTIVE',"
                      + " SUBMISSION_NUMBER = " + studyProtocolDto.getSubmissionNumber().getValue() + ","
                      + " AMENDMENT_NUMBER = '" + StConverter.
                               convertToString(studyProtocolDto.getAmendmentNumber()) + "',"
                      + " AMENDMENT_DATE='" +  TsConverter.
                               convertToTimestamp(studyProtocolDto.getAmendmentDate()) + "',"
                      + " amendment_reason_code='" + reasonCodeValue  + "'"
                      + " WHERE IDENTIFIER  = " + sourceIi.getExtension());
        } else {
              sqls.add("UPDATE STUDY_PROTOCOL SET STATUS_CODE='INACTIVE',"
                    + " SUBMISSION_NUMBER = " + studyProtocolDto.getSubmissionNumber().getValue() + ","
                    + " AMENDMENT_NUMBER= '" + StConverter.
                                 convertToString(studyProtocolDto.getAmendmentNumber()) + "',"
                    + " AMENDMENT_DATE='" +  TsConverter.
                                 convertToTimestamp(studyProtocolDto.getAmendmentDate()) + "',"
                    + " amendment_reason_code = null"
                    + " WHERE IDENTIFIER  = " + sourceIi.getExtension());
        }
        return sqls;
    }
    private void deleteSponsor(Ii targetSpIi) {
        String sql = "DELETE FROM STUDY_SITE WHERE STUDY_PROTOCOL_IDENTIFIER = " + targetSpIi.getExtension()
                + SQL_APPEND + "('SPONSOR')";
        getPAServiceUtils().executeSql(sql);
    }

    @SuppressWarnings({ "PMD.ExcessiveMethodLength", "unchecked", "rawtypes" })
    private StudyProtocolDTO getStudyProtocolForCreateOrAmend(// NOPMD
            StudyProtocolDTO studyProtocolDTO, String operation)
            throws PAException {
        StudyProtocolDTO createStudyProtocolDTO = new InterventionalStudyProtocolDTO();
        if (studyProtocolDTO instanceof NonInterventionalStudyProtocolDTO) {
            createStudyProtocolDTO = new NonInterventionalStudyProtocolDTO();
        }
        if (AMENDMENT.equalsIgnoreCase(operation)) {
            createStudyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolDTO
                    .getIdentifier());
            createStudyProtocolDTO.setAmendmentDate(studyProtocolDTO.getAmendmentDate());
            createStudyProtocolDTO.setAmendmentNumber(studyProtocolDTO.getAmendmentNumber());
            final DSet<Ii> createStudyOtherIdentifiers = getUpdatedStudyOtherIdentifiers(
                   createStudyProtocolDTO, studyProtocolDTO.getSecondaryIdentifiers());
            createStudyProtocolDTO.setSecondaryIdentifiers(createStudyOtherIdentifiers);
        } else {
            createStudyProtocolDTO.setSubmissionNumber(studyProtocolDTO.getSubmissionNumber());
            createStudyProtocolDTO.setIdentifier(null);
            createStudyProtocolDTO.setProcessingPriority(IntConverter.convertToInt(2));
            createStudyProtocolDTO.setSecondaryIdentifiers(studyProtocolDTO
                    .getSecondaryIdentifiers());
        }
        
        createStudyProtocolDTO.setOfficialTitle(studyProtocolDTO.getOfficialTitle());
        createStudyProtocolDTO.setNciGrant(studyProtocolDTO.getNciGrant());
        createStudyProtocolDTO.setPhaseCode(studyProtocolDTO.getPhaseCode());
        
        setPhaseAdditionalQualifier(studyProtocolDTO, createStudyProtocolDTO);
        setPrimaryPurposeCode(studyProtocolDTO, createStudyProtocolDTO);
        
        setInterventionalTrialFields(studyProtocolDTO, createStudyProtocolDTO);
        setNonInterventionalTrialFields(studyProtocolDTO, createStudyProtocolDTO);
        
        createStudyProtocolDTO.setStartDate(studyProtocolDTO.getStartDate());
        createStudyProtocolDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
        createStudyProtocolDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
        createStudyProtocolDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
        createStudyProtocolDTO.setCompletionDate(studyProtocolDTO.getCompletionDate());
        createStudyProtocolDTO.setCompletionDateTypeCode(studyProtocolDTO.getCompletionDateTypeCode());        
        createStudyProtocolDTO.setStudyProtocolType(studyProtocolDTO.getStudyProtocolType());
        createStudyProtocolDTO.setProgramCodeText(studyProtocolDTO.getProgramCodeText());
        createStudyProtocolDTO.setFdaRegulatedIndicator(studyProtocolDTO.getFdaRegulatedIndicator());
        createStudyProtocolDTO.setSection801Indicator(studyProtocolDTO.getSection801Indicator());
        createStudyProtocolDTO.setDelayedpostingIndicator(studyProtocolDTO.getDelayedpostingIndicator());
        createStudyProtocolDTO.setDataMonitoringCommitteeAppointedIndicator(studyProtocolDTO
                .getDataMonitoringCommitteeAppointedIndicator());
        createStudyProtocolDTO.setProprietaryTrialIndicator(studyProtocolDTO.getProprietaryTrialIndicator());
        createStudyProtocolDTO.setConsortiaTrialCategoryCode(studyProtocolDTO.getConsortiaTrialCategoryCode());
        createStudyProtocolDTO.setCtroOverride(studyProtocolDTO.getCtroOverride());
        createStudyProtocolDTO.setUserLastCreated(studyProtocolDTO.getUserLastCreated());
        createStudyProtocolDTO.setAccrualDiseaseCodeSystem(studyProtocolDTO.getAccrualDiseaseCodeSystem());
        if (!BlConverter.convertToBool(studyProtocolDTO.getProprietaryTrialIndicator())) {
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator() == null) {
                createStudyProtocolDTO.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(Boolean.TRUE));
            } else {
                createStudyProtocolDTO.setCtgovXmlRequiredIndicator(studyProtocolDTO.getCtgovXmlRequiredIndicator());
            }
        }
        
        if (!ISOUtil.isStNull(studyProtocolDTO.getAcronym())) {
            createStudyProtocolDTO.setAcronym(studyProtocolDTO.getAcronym());
        }
        if (!ISOUtil.isStNull(studyProtocolDTO.getPublicDescription())) {
            createStudyProtocolDTO.setPublicDescription(studyProtocolDTO.getPublicDescription());
        }
        if (!ISOUtil.isStNull(studyProtocolDTO.getPublicTitle())) {
            createStudyProtocolDTO.setPublicTitle(studyProtocolDTO.getPublicTitle());
        }
        if (!ISOUtil.isStNull(studyProtocolDTO.getScientificDescription())) {
            createStudyProtocolDTO.setScientificDescription(studyProtocolDTO.getScientificDescription());
        }
        if (studyProtocolDTO.getTargetAccrualNumber() != null
                && !ISOUtil.isIntNull(studyProtocolDTO.getTargetAccrualNumber()
                        .getLow())) {
            createStudyProtocolDTO.setTargetAccrualNumber(studyProtocolDTO
                    .getTargetAccrualNumber());
        }
        if (!ISOUtil.isStNull(studyProtocolDTO.getKeywordText())) {
            createStudyProtocolDTO.setKeywordText(studyProtocolDTO.getKeywordText());
        }
        if (!ISOUtil.isTsNull(studyProtocolDTO.getRecordVerificationDate())) {
            createStudyProtocolDTO.setRecordVerificationDate(studyProtocolDTO.getRecordVerificationDate());
        }
        if (!ISOUtil.isBlNull(studyProtocolDTO
                .getAcceptHealthyVolunteersIndicator())) {
            createStudyProtocolDTO
                    .setAcceptHealthyVolunteersIndicator(studyProtocolDTO
                            .getAcceptHealthyVolunteersIndicator());
        }
        if (!ISOUtil.isBlNull(studyProtocolDTO
                .getExpandedAccessIndicator())) {
            createStudyProtocolDTO
                    .setExpandedAccessIndicator(studyProtocolDTO
                            .getExpandedAccessIndicator());
        }
        if (!ISOUtil.isCdNull(studyProtocolDTO.getStudySource())) {
            createStudyProtocolDTO.setStudySource(studyProtocolDTO.getStudySource());
        }
        return createStudyProtocolDTO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void reject(Ii studyProtocolIi, St rejectionReason, 
               Cd rejectionReasonCode, MilestoneCode milestoneCode) throws PAException {
        try {
            StudyProtocolDTO studyProtocolDto = studyProtocolService.getStudyProtocol(studyProtocolIi);
            TrialRegistrationValidator validator = createValidator();
            validator.validateRejection(studyProtocolDto, milestoneCode);
            // Original trial Rejection
            if (studyProtocolDto.getSubmissionNumber().getValue().intValue() == 1) {
                createStudyMileStone(studyProtocolIi, rejectionReason, 
                        rejectionReasonCode);
            } else {
                Ii targetSpIi = studyProtocolIi;
                Ii sourceSpIi = null;
                // search the StudyProtocol to get the latest accepted protocol.
                LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
                StudyProtocolDTO studyToSearch = new StudyProtocolDTO();
                studyToSearch.setSecondaryIdentifiers(studyProtocolDto.getSecondaryIdentifiers());
                studyToSearch.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
                List<StudyProtocolDTO> spList = studyProtocolService.search(studyToSearch, limit);
                if (CollectionUtils.isNotEmpty(spList)) {
                    Collections.sort(spList, new Comparator<StudyProtocolDTO>() {
                        @Override
                        public int compare(StudyProtocolDTO o1, StudyProtocolDTO o2) {
                            return o1.getSubmissionNumber().getValue().compareTo(o2.getSubmissionNumber().getValue());
                        }
                    });
                    sourceSpIi = spList.get(spList.size() - 1).getIdentifier();
                }
                if (CollectionUtils.isEmpty(spList)) {
                    throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
                }
                if (sourceSpIi == null) {
                    throw new PAException("Discrepancies occured while Rejecting the Amended Protocol");
                }
                if (targetSpIi == null) {
                    throw new PAException("Study Relationship not found for the Amended Protocol");
                }
                StudyProtocolDTO sourceSpDto = studyProtocolService.getStudyProtocol(sourceSpIi);
                //set the last user created of source study protocol as the last user created of target study protocol.
                St lastUserCreated = sourceSpDto.getUserLastCreated();
                StudyProtocolDTO targetSpDto = studyProtocolService.getStudyProtocol(targetSpIi); 
                St targetLastUserCreated = targetSpDto.getUserLastCreated();
                saveAmenderInfo(targetSpDto, lastUserCreated, false);                
                // overwrite with the target
                sourceSpDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.ACTIVE));
                Session session = PaHibernateUtil.getCurrentSession();
                String classType = "";
                StudyProtocol source = null;
                if (sourceSpDto instanceof NonInterventionalStudyProtocolDTO) {
                    source = NonInterventionalStudyProtocolConverter
                            .convertFromDTOToDomain((NonInterventionalStudyProtocolDTO) sourceSpDto);
                    classType = PAConstants.NON_INTERVENTIONAL;
                } else if (sourceSpDto instanceof InterventionalStudyProtocolDTO) {
                    source = InterventionalStudyProtocolConverter
                            .convertFromDTOToDomain((InterventionalStudyProtocolDTO) sourceSpDto);
                    classType = PAConstants.INTERVENTIONAL;
                }
                Long id = IiConverter.convertToLong(targetSpIi);
                StudyProtocol target = (StudyProtocol) session.load(StudyProtocol.class, id);
                source.setId(target.getId());
                target = source;
                session.merge(target);
                Ii sourceIi = null;
                List<StudyContactDTO> studyContactDtos = getPAServiceUtils()
                    .getStudyContact(sourceSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
                StudyContactDTO scSourceDTO = null;
                if (PAUtil.getFirstObj(studyContactDtos) != null) {
                    scSourceDTO = PAUtil.getFirstObj(studyContactDtos);
                } else {
                    throw new PAException("Source Study Principal Investigator is not available");
                }
                sourceIi = scSourceDTO.getIdentifier();
                studyContactDtos = getPAServiceUtils()
                    .getStudyContact(targetSpIi, StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR, true);
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
                StudySiteDTO studySiteDto = getStudySiteDTO(sourceSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                List<StudySiteDTO> studySiteDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                StudySiteDTO ssSourceDTO = null;
                if (PAUtil.getFirstObj(studySiteDtos) != null) {
                    ssSourceDTO = PAUtil.getFirstObj(studySiteDtos);
                } else {
                    throw new PAException("Source Lead Organization is not available");
                }
                sourceIi = ssSourceDTO.getIdentifier();
                studySiteDto = getStudySiteDTO(targetSpIi, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                studySiteDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
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
                if (sourceSpDto.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                    studySiteDto = getStudySiteDTO(sourceSpIi, StudySiteFunctionalCode.SPONSOR);
                    List<StudySiteDTO> studySiteSponsorDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                    StudySiteDTO ssSponsorSourceDTO = null;
                    if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
                        ssSponsorSourceDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
                    } else {
                        throw new PAException("Source Lead Organization is not available");
                    }
                    sourceIi = ssSponsorSourceDTO.getIdentifier();
                    studySiteDto = getStudySiteDTO(targetSpIi, StudySiteFunctionalCode.SPONSOR);
                    studySiteSponsorDtos = getPAServiceUtils().getStudySite(studySiteDto, true);
                    StudySiteDTO ssSponsorTargetDTO = null;
                    if (PAUtil.getFirstObj(studySiteSponsorDtos) != null) {
                        ssSponsorTargetDTO = PAUtil.getFirstObj(studySiteSponsorDtos);
                        ssSponsorSourceDTO.setIdentifier(ssSponsorTargetDTO.getIdentifier());
                        ssSponsorSourceDTO.setStudyProtocolIdentifier(targetSpIi);
                        studySiteService.delete(sourceIi);
                        studySiteService.update(ssSponsorSourceDTO);
                    } else {
                        updateSponsor(sourceSpIi, targetSpIi);
                    }
                } else {
                    if (studyProtocolDto.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                        deleteSponsor(targetSpIi);
                    }
                }
                getPAServiceUtils().executeSql(deleteAndReplace(sourceSpIi, targetSpIi, studyProtocolDto));
                getPAServiceUtils().swapStudyProtocolIdentifiers(DOCUMENT, sourceSpIi, targetSpIi);
                getPAServiceUtils().swapStudyProtocolIdentifiers(MILESTONE, sourceSpIi, targetSpIi);
                if (!(MilestoneCode.LATE_REJECTION_DATE.equals(milestoneCode))) {
                      insertRejectStudyMileStone(sourceSpIi, rejectionReason, rejectionReasonCode, classType);
                }
                studyProtocolDto.setIdentifier(sourceSpIi);
                studyProtocolDto.setStatusCode(CdConverter.convertToCd(ActStatusCode.INACTIVE));
                saveAmenderInfo(studyProtocolDto, targetLastUserCreated, false);
                studyProtocolService.updateStudyProtocol(studyProtocolDto);
            }
        } catch (Exception e) {
            throw new PAException(e.getMessage(), e);
        }
    }
    
    private void createStudyMileStone(Ii studyProtocolIi, St rejectionReason, 
            Cd rejectionReasonCode) throws PAException {
        StudyMilestoneDTO smDto = new StudyMilestoneDTO();
        smDto.setMilestoneDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        smDto.setStudyProtocolIdentifier(studyProtocolIi);
        smDto.setMilestoneCode(CdConverter.convertToCd(MilestoneCode.SUBMISSION_REJECTED));
        smDto.setCommentText(rejectionReason);
        smDto.setRejectionReasonCode(rejectionReasonCode);
        studyMilestoneService.create(smDto);
    }
    
    private void  insertRejectStudyMileStone(Ii sourceSpIi, 
           St rejectionReason, Cd rejectionReasonCode, String classType) throws PAException {
        StudyMilestone sm = new StudyMilestone();
        StudyProtocol sp;
        sm.setCommentText(StConverter.convertToString(rejectionReason));
        sm.setRejectionReasonCode(RejectionReasonCode.
               getByCode(CdConverter.convertCdToString(rejectionReasonCode)));
        sm.setMilestoneCode(MilestoneCode.SUBMISSION_REJECTED);
        sm.setMilestoneDate(new Timestamp(new Date().getTime()));
        if (classType.equals(PAConstants.INTERVENTIONAL)) {
              sp = new InterventionalStudyProtocol();
        } else {
              sp = new NonInterventionalStudyProtocol();
        }
        sp.setId(IiConverter.convertToLong(sourceSpIi));
        sm.setStudyProtocol(sp);
        sm.setDateLastCreated(new Timestamp(new Date().getTime()));
        sm.setDateLastUpdated(new Timestamp(new Date().getTime()));
        User user = CSMUserService.getInstance().getCSMUser(UsernameHolder.getUser());
        sm.setUserLastCreated(user);
        sm.setUserLastUpdated(user);
        Session session = PaHibernateUtil.getCurrentSession();
        session.save(sm);
        session.flush();
        
    }
    
    private StudySiteDTO getStudySiteDTO(Ii spid, StudySiteFunctionalCode ss) {
        StudySiteDTO studySiteDto = new StudySiteDTO();
        studySiteDto.setStudyProtocolIdentifier(spid);
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(ss));
        return studySiteDto;
    }
    /**
     * @param operation
     * @param unmatchedEmails email addresses that did not match any users.
     * @param isBatchMode
     * @param studyProtocolIi
     * @param updatesList updatesList
     * @throws PAException
     */
    private void sendMail(String operation, Bl isBatchMode, Ii studyProtocolIi, Collection<String> unmatchedEmails, 
            String updatesList) 
            throws PAException {
        if (ISOUtil.isBlNull(isBatchMode) || !BlConverter.convertToBool(isBatchMode)) {
            if (AMENDMENT.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendAmendNotificationMail(studyProtocolIi);
            } else if (UPDATE.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendUpdateNotificationMail(studyProtocolIi, updatesList);
            } else if (CREATE.equalsIgnoreCase(operation)) {
                mailManagerSerivceLocal.sendNotificationMail(studyProtocolIi, unmatchedEmails);
            }
        }
    }

    /**
     * An action plan and execution of a pre-clinical for Updating an existing protocols.
     * @param studyProtocolDTO StudyProtocolDTO
     * @param overallStatusDTO OverallStatusDTO
     * @param studyIdentifierDTOs List of Study Identifier
     * @param studyIndldeDTOs list of Study Ind/ides
     * @param studyResourcingDTOs list of nih grants
     * @param documentDTOs IRB document
     * @param studyContactDTO phone and email info when Pi is responsible
     * @param studyParticipationContactDTO StudySiteContactDTO
     * @param summary4organizationDTO summary 4 organization code
     * @param summary4studyResourcingDTO summary 4 category code
     * @param responsiblePartyContactIi id of the person when sponsor is responsible
     * @param studyRegAuthDTO updated studyRegAuthDTO
     * @param collaboratorDTOs list of updated collaborators
     * @param studySiteAccrualStatusDTOs list of updated participating sites
     * @param studySiteDTOs list of StudySite DTOs with updated program code
     * @param isBatchMode to identify if batch is caller
     * @throws PAException on error
     */
    @Override
    // CHECKSTYLE:OFF More than 7 Parameters
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.ExcessiveMethodLength" })
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudySiteDTO> studyIdentifierDTOs, List<StudyIndldeDTO> studyIndldeDTOs,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            StudyContactDTO studyContactDTO, StudySiteContactDTO studyParticipationContactDTO,
            OrganizationDTO summary4organizationDTO, StudyResourcingDTO summary4studyResourcingDTO,
            Ii responsiblePartyContactIi, StudyRegulatoryAuthorityDTO studyRegAuthDTO,
            List<StudySiteDTO> collaboratorDTOs, List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs, Bl isBatchMode) throws PAException {
        // CHECKSTYLE:ON

        StudySiteDTO nctIdentifierDTO = getPAServiceUtils().extractNCTDto(studyIdentifierDTOs);        
        if (nctIdentifierDTO != null) {              
            String nctValidationResultString = getPAServiceUtils().validateNCTIdentifier(
                   nctIdentifierDTO.getLocalStudyProtocolIdentifier().getValue(), studyProtocolDTO
                   .getIdentifier());
            if (StringUtils.isNotEmpty(nctValidationResultString)) {
                throw new PAException(TrialRegistrationValidator.VALIDATION_EXCEPTION + nctValidationResultString);
            }       
        }
        try {
            TrialUpdatesRecorder.reset();
            
            Ii spIi = updateStudy(studyProtocolDTO, overallStatusDTO, studyResourcingDTOs, documentDTOs,
                        studySiteAccrualStatusDTOs, studySiteDTOs);  
            
            String existingNCT = getPAServiceUtils().getStudyIdentifier(studyProtocolDTO.getIdentifier(),
                    PAConstants.NCT_IDENTIFIER_TYPE);
            updateStudyIdentifiers(studyProtocolDTO.getIdentifier(), studyIdentifierDTOs);
            PaHibernateUtil.getCurrentSession().flush();
            String updatedNCT = getPAServiceUtils().getStudyIdentifier(studyProtocolDTO.getIdentifier(),
                    PAConstants.NCT_IDENTIFIER_TYPE);
            TrialUpdatesRecorder.isNctUpdated(existingNCT, updatedNCT);

            
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
                        
            // do not send the mail when its batch mode
            final String updatesList = createInboxProcessingComments(spIi, savedDocs);
            studyProtocolService
                .updatePendingTrialAssociationsToActive(IiConverter.convertToLong(spIi));
            sendMail(UPDATE, isBatchMode, spIi, new ArrayList<String>(), updatesList);

            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(spIi);
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(spIi);
            sendTSRXML(spIi, smDto.getMilestoneCode(), inbox);
        } catch (Exception e) {
            LOG.error(e, e);
            throw new PAException(e.getMessage(), e);
        } finally {
            TrialUpdatesRecorder.reset();
        }

    }

    private void updateParticipatingSites(List<StudySiteAccrualStatusDTO> participatingSites) throws PAException {
        if (participatingSites != null) {
            for (StudySiteAccrualStatusDTO sdto : participatingSites) {
                studySiteAccrualStatusService.createStudySiteAccrualStatus(sdto);
            }
        }
    }

    private void updateSponsor(Ii sourceSpIi, Ii targetSpIi) {
        String sqlUpd = targetSpIi.getExtension() + " WHERE STUDY_PROTOCOL_IDENTIFIER = " + sourceSpIi.getExtension();
        String sql = "UPDATE STUDY_SITE SET STUDY_PROTOCOL_IDENTIFIER = " + sqlUpd + SQL_APPEND + "('SPONSOR')";
        getPAServiceUtils().executeSql(sql);
    }

    private void updateStudyProtocol(StudyProtocolDTO studyProtocolDTO, Ii toStudyProtocolIi)
            throws PAException {
        ByteArrayOutputStream rtfStream = tsrReportService.generateRtfTsrReport(studyProtocolDTO.getIdentifier());
        DocumentDTO docDto = new DocumentDTO();
        docDto.setStudyProtocolIdentifier(toStudyProtocolIi);
        docDto.setTypeCode(CdConverter.convertToCd(DocumentTypeCode.TSR));
        docDto.setText(EdConverter.convertToEd(rtfStream.toByteArray()));
        docDto.setFileName(StConverter.convertToSt("TSR.rtf"));

        documentService.create(docDto);
        // reset milestones
        String sql = null;
        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        sql = "Delete from STUDY_MILESTONE WHERE STUDY_PROTOCOL_IDENTIFIER  = " + studyProtocolIi.getExtension();
        getPAServiceUtils().executeSql(sql);
        sql = "Delete from DOCUMENT_WORKFLOW_STATUS WHERE STUDY_PROTOCOL_IDENTIFIER  = "
                + studyProtocolIi.getExtension();
        getPAServiceUtils().executeSql(sql);
        sql = "Delete from STUDY_ONHOLD WHERE STUDY_PROTOCOL_IDENTIFIER  = " + studyProtocolIi.getExtension();
        getPAServiceUtils().executeSql(sql);
        studyProtocolDTO.setProcessingPriority(IntConverter.convertToInt(2));
        studyProtocolDTO.setAmendmentReasonCode(null);
        studyProtocolDTO.setSubmissionNumber(IntConverter.convertToInt(getPAServiceUtils()
            .generateSubmissionNumber(PAUtil.getAssignedIdentifierExtension(studyProtocolDTO))));
        studyProtocolDTO.setStatusDate(TsConverter.convertToTs(null));

        if (studyProtocolDTO instanceof NonInterventionalStudyProtocolDTO) {
            studyProtocolService
                    .updateNonInterventionalStudyProtocol((NonInterventionalStudyProtocolDTO) studyProtocolDTO);
        } else {
            studyProtocolService.updateStudyProtocol(studyProtocolDTO);
        }
    }

    private StudyProtocolDTO validateStudyExist(StudyProtocolDTO studyProtocolDTO, String operation)
            throws PAException {
        if (studyProtocolDTO == null || ISOUtil.isIiNull(studyProtocolDTO.getIdentifier())) {
            throw new PAException("Trial Identifier not provided.\n");
        }
        // make sure Trial Exist
        StudyProtocolDTO dto = studyProtocolService.getStudyProtocol(studyProtocolDTO
            .getIdentifier());
        if (dto == null) {
            throw new PAException("No Trial found for given Trial Identifier.\n");
        }
        if (!ISOUtil.isBlNull(dto.getProprietaryTrialIndicator())
                && dto.getProprietaryTrialIndicator().getValue().booleanValue()) {
            throw new PAException(operation + " to Proprietary trial is not supported. ");
        }
        return dto;
    }

    private DSet<Ii> getUpdatedStudyOtherIdentifiers(StudyProtocolDTO existingProtocol,
            DSet<Ii> newOtherIdentifiers) {
        DSet<Ii> updatedOtherIdentifiers = existingProtocol
                .getSecondaryIdentifiers();

        if (updatedOtherIdentifiers == null) {
            updatedOtherIdentifiers = new DSet<Ii>();
            updatedOtherIdentifiers.setItem(new LinkedHashSet<Ii>());
        }
        
        if (ISOUtil.isDSetNotEmpty(newOtherIdentifiers)) {
            // See https://tracker.nci.nih.gov/browse/PO-4736.
            // The reason for replacing this single line:
            // "updatedOtherIdentifiers.getItem().addAll(additionalOtherIdentifiers.getItem());"
            // with a loop and an additional method is because there is a data issue that exists
            // in some of the tiers: some of 'Other' identifiers in study_otheridentifiers had their identifier_name
            // as null, which messed up things.
            // "updatedOtherIdentifiers.getItem()" set is Hibernate's PersistentSet, which made working with it
            // a bit tricky. 
            for (Ii ii : newOtherIdentifiers.getItem()) {
                if (!PAUtil.containsIi(updatedOtherIdentifiers.getItem(),
                        ii.getExtension(), ii.getRoot())) {
                    updatedOtherIdentifiers.getItem().add(ii);
                }
            }
        } 
        return updatedOtherIdentifiers;
    }

   

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public void update(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs, List<StudySiteDTO> studySiteDTOs, Bl batchMode)
            throws PAException {
        try {
            TrialUpdatesRecorder.reset();
            
            Ii spIi = updateStudy(studyProtocolDTO, overallStatusDTO, studyResourcingDTOs, documentDTOs,
                        studySiteAccrualStatusDTOs, studySiteDTOs);            
            
            List<DocumentDTO> savedDocs = saveDocuments(documentDTOs, spIi);
                        
            // do not send the mail when its batch mode
            final String updatesList = createInboxProcessingComments(spIi, savedDocs);
            studyProtocolService
                .updatePendingTrialAssociationsToActive(IiConverter
                    .convertToLong(spIi));
            sendMail(UPDATE, batchMode, spIi, new ArrayList<String>(),
                    updatesList);

            StudyMilestoneDTO smDto = studyMilestoneService.getCurrentByStudyProtocol(spIi);
            List<StudyInboxDTO> inbox = studyInboxServiceLocal.getByStudyProtocol(spIi);
            sendTSRXML(spIi, smDto.getMilestoneCode(), inbox);
        } catch (Exception e) {
            LOG.error(e, e);
            throw new PAException(e.getMessage(), e);
        } finally {
            TrialUpdatesRecorder.reset();
        }
    }

    @SuppressWarnings({"PMD.ExcessiveParameterList", "PMD.ExcessiveMethodLength" })
    private Ii updateStudy(StudyProtocolDTO studyProtocolDTO, StudyOverallStatusDTO overallStatusDTO,
            List<StudyResourcingDTO> studyResourcingDTOs, List<DocumentDTO> documentDTOs,
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatusDTOs,
            List<StudySiteDTO> studySiteDTOs) throws PAException {
        StudyProtocolDTO spDTO = validateStudyExist(studyProtocolDTO, UPDATE);
        Ii spIi = studyProtocolDTO.getIdentifier();
        
        // Other Identifiers
        Set<Ii> originalSecondaryIDs = new HashSet<Ii>();
        if (ISOUtil.isDSetNotEmpty(spDTO.getSecondaryIdentifiers())) {
            originalSecondaryIDs.addAll(spDTO.getSecondaryIdentifiers().getItem());
        }            
        final DSet<Ii> updatedStudyOtherIdentifiers = getUpdatedStudyOtherIdentifiers(
                spDTO, studyProtocolDTO.getSecondaryIdentifiers());
        spDTO.setSecondaryIdentifiers(updatedStudyOtherIdentifiers);
        TrialUpdatesRecorder.recordUpdate(originalSecondaryIDs,
                updatedStudyOtherIdentifiers.getItem(), TrialUpdatesRecorder.IDENTIFIERS_ADDED);
        
        TrialUpdatesRecorder.recordUpdate(spDTO.getStartDate(),
                studyProtocolDTO.getStartDate(), TrialUpdatesRecorder.START_DATE_CHANGED);
        spDTO.setStartDate(studyProtocolDTO.getStartDate());            
        spDTO.setStartDateTypeCode(studyProtocolDTO.getStartDateTypeCode());
        
        TrialUpdatesRecorder.recordUpdate(spDTO.getPrimaryCompletionDate(),
                studyProtocolDTO.getPrimaryCompletionDate(), TrialUpdatesRecorder.PRIMARY_COMPLETION_DATE_CHANGED);
        spDTO.setPrimaryCompletionDate(studyProtocolDTO.getPrimaryCompletionDate());
        spDTO.setPrimaryCompletionDateTypeCode(studyProtocolDTO.getPrimaryCompletionDateTypeCode());
        
        TrialUpdatesRecorder.recordUpdate(spDTO.getCompletionDate(),
                studyProtocolDTO.getCompletionDate(), TrialUpdatesRecorder.COMPLETION_DATE_CHANGED);
        spDTO.setCompletionDate(studyProtocolDTO.getCompletionDate());
        spDTO.setCompletionDateTypeCode(studyProtocolDTO.getCompletionDateTypeCode());

        // Even though we are setting UserLastCreated value which came from DB, the value will not be updated in DB.
        // UserLastCreated is used as a place holder to determine the currently logged in user and/or the person
        // submitting the update.
        // Also, to determine the owner of the trial. Remove this line when the ejbContext.callerPrincipal will give
        // the userLogged in value.
        spDTO.setUserLastCreated(studyProtocolDTO.getUserLastCreated());
        TrialRegistrationValidator validator = createValidator();
        validator.validateUpdate(spDTO, overallStatusDTO, studyResourcingDTOs, documentDTOs, 
                studySiteAccrualStatusDTOs);
        spDTO.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
        St newAccrualDiseaseCodeSystem = ISOUtil.isStNull(studyProtocolDTO
                .getAccrualDiseaseCodeSystem()) ? spDTO
                .getAccrualDiseaseCodeSystem() : studyProtocolDTO
                .getAccrualDiseaseCodeSystem();
        TrialUpdatesRecorder.recordUpdate(spDTO.getAccrualDiseaseCodeSystem(), newAccrualDiseaseCodeSystem,
                TrialUpdatesRecorder.ACCRUAL_DISEASE_TERMINOLOGY_UPDATED);
        spDTO.setAccrualDiseaseCodeSystem(newAccrualDiseaseCodeSystem);

        updateStudyProtocol(spDTO);
        
        // Grants
        TrialUpdatesRecorder.recordUpdate(studyResourcingDTOs, TrialUpdatesRecorder.GRANT_INFORMATION_UPDATED);
        PAServiceUtils paServiceUtils = getPAServiceUtils();
        paServiceUtils.createOrUpdate(studyResourcingDTOs, IiConverter.convertToStudyResourcingIi(null), spIi);

        // Participating site's status and/or date.
        updateParticipatingSites(studySiteAccrualStatusDTOs);
        
        // Participating sites: program code only.
        TrialUpdatesRecorder.recordParticipatingSiteUpdate(studySiteDTOs,
                TrialUpdatesRecorder.PARTICIPATING_SITES_UPDATED);            
        paServiceUtils.createOrUpdate(studySiteDTOs, IiConverter.convertToStudySiteIi(null), spIi);

        // Study overall status & status date.
        TrialUpdatesRecorder.recordUpdate(overallStatusDTO, TrialUpdatesRecorder.STATUS_DATES_UPDATED);
        studyOverallStatusService.create(overallStatusDTO);
        return spIi;
    }

    /**
     * @param spDTO
     * @throws PAException
     */
    private void updateStudyProtocol(StudyProtocolDTO spDTO) throws PAException {
        if (spDTO instanceof InterventionalStudyProtocolDTO) {
            studyProtocolService.updateInterventionalStudyProtocol((InterventionalStudyProtocolDTO) spDTO,
                    null);
        } else {
            studyProtocolService
                    .updateNonInterventionalStudyProtocol((NonInterventionalStudyProtocolDTO) spDTO);
        }
    }

    /**
     * Updates last created user to "ClinicalTrials.govImport".
     * @param studyProtocolDTO study protocol DTO
     * @throws PAException 
     */
    private void updateUserLastCreated(StudyProtocolDTO studyProtocolDTO) throws PAException {
        Long studyProtocolId = IiConverter.convertToLong(studyProtocolDTO.getIdentifier());
        User ctgovimportUser = CSMUserService.getInstance().getCSMUser(CTGovSyncServiceBean.CTGOVIMPORT_USERNAME);
        String spUpdate = "update study_protocol set user_last_created_id = " + ctgovimportUser.getUserId() 
                + " where identifier = " + studyProtocolId; 
        getPAServiceUtils().executeSql(spUpdate);
    }
    
    /**
     * Save the given documents.
     * 
     * @param documentDTOs
     *            The list of document DTOs
     * @param spIi
     *            The study potocol Ii
     * @throws PAException
     *             If an error occurs
     */
    List<DocumentDTO> saveDocuments(List<DocumentDTO> documentDTOs, Ii spIi)
            throws PAException {
        PAServiceUtils paServiceUtils = getPAServiceUtils();
        Ii nullDocumentIi = IiConverter.convertToDocumentIi(null);
        List<DocumentDTO> savedDocs = paServiceUtils.createOrUpdate(
                documentDTOs, nullDocumentIi, spIi);
        if (CollectionUtils.isNotEmpty(savedDocs)) {
            StudyProtocolDTO sp = studyProtocolService.getStudyProtocol(spIi);
            String assignedIdentifier = PAUtil
                    .getAssignedIdentifierExtension(sp);
            paServiceUtils.moveDocumentContents(savedDocs, assignedIdentifier);
            for (DocumentDTO documentDTO : savedDocs) {
                TrialUpdatesRecorder
                        .recordUpdate(String.format(
                                TrialUpdatesRecorder.DOC_UPDATED,
                                DocumentTypeCode.getByCode(documentDTO
                                        .getTypeCode().getCode()).getCode()));
            }
        }
        return savedDocs;
    }

    private TrialRegistrationValidator createValidator() {
        TrialRegistrationValidator validator = new TrialRegistrationValidator(ctx);
        validator.setCsmUserUtil(CSMUserService.getInstance());
        validator.setDocumentWorkFlowStatusService(documentWorkFlowStatusService);
        validator.setLookUpTableServiceRemote(lookUpTableServiceRemote);       
        validator.setPaServiceUtils(getPAServiceUtils());
        validator.setRegistryUserServiceLocal(registryUserServiceLocal);
        validator.setRegulatoryInfoBean(regulatoryInfoBean);
        validator.setStudyInboxServiceLocal(studyInboxServiceLocal);
        validator.setStudyIndldeService(studyIndldeService);
        validator.setStudyOverallStatusService(studyOverallStatusService);
        validator.setStudyProtocolService(studyProtocolService);
        validator.setStudyRecruitmentStatusServiceLocal(studyRecruitmentStatusServiceLocal);
        validator.setStudyResourcingService(studyResourcingService);       
        validator.setRegulatoryAuthorityService(regulatoryAuthorityService);
        validator.setAccrualDiseaseTerminologyService(accrualDiseaseTerminologyService);
        return validator;
    }
    

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    /**
     * @param documentWorkFlowStatusService the documentWorkFlowStatusService to set
     */
    public void setDocumentWorkFlowStatusService(DocumentWorkflowStatusServiceLocal documentWorkFlowStatusService) {
        this.documentWorkFlowStatusService = documentWorkFlowStatusService;
    }

    /**
     * @param lookUpTableServiceRemote the lookUpTableServiceRemote to set
     */
    public void setLookUpTableServiceRemote(LookUpTableServiceRemote lookUpTableServiceRemote) {
        this.lookUpTableServiceRemote = lookUpTableServiceRemote;
    }

    /**
     * @param mailManagerSerivceLocal the mailManagerSerivceLocal to set
     */
    public void setMailManagerSerivceLocal(MailManagerServiceLocal mailManagerSerivceLocal) {
        this.mailManagerSerivceLocal = mailManagerSerivceLocal;
    }

    /**
     * @param ocsr the ocsr to set
     */
    public void setOcsr(OrganizationCorrelationServiceRemote ocsr) {
        this.ocsr = ocsr;
    }

    /**
     * @param registryUserServiceLocal the registryUserServiceLocal to set
     */
    public void setRegistryUserServiceLocal(RegistryUserServiceLocal registryUserServiceLocal) {
        this.registryUserServiceLocal = registryUserServiceLocal;
    }

    /**
     * @param regulatoryInfoBean the regulatoryInfoBean to set
     */
    public void setRegulatoryInfoBean(RegulatoryInformationServiceLocal regulatoryInfoBean) {
        this.regulatoryInfoBean = regulatoryInfoBean;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @param studyInboxServiceLocal the studyInboxServiceLocal to set
     */
    public void setStudyInboxServiceLocal(StudyInboxServiceLocal studyInboxServiceLocal) {
        this.studyInboxServiceLocal = studyInboxServiceLocal;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyMilestoneService the studyMilestoneService to set
     */
    public void setStudyMilestoneService(StudyMilestoneServicelocal studyMilestoneService) {
        this.studyMilestoneService = studyMilestoneService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param srsServiceLocal the studyRecruitmentStatusServiceLocal to set
     */
    public void setStudyRecruitmentStatusServiceLocal(StudyRecruitmentStatusServiceLocal srsServiceLocal) {
        this.studyRecruitmentStatusServiceLocal = srsServiceLocal;
    }

    /**
     * @param sraService the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal sraService) {
        this.studyRegulatoryAuthorityService = sraService;
    }

    /**
     * @param studyRelationshipService the studyRelationshipService to set
     */
    public void setStudyRelationshipService(StudyRelationshipServiceLocal studyRelationshipService) {
        this.studyRelationshipService = studyRelationshipService;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param tsrReportService the tsrReportService to set
     */
    public void setTsrReportService(TSRReportGeneratorServiceLocal tsrReportService) {
        this.tsrReportService = tsrReportService;
    }
    
    /**
     * @param regulatoryAuthorityService RegulatoryAuthorityServiceLocal
     */
    public void setRegulatoryAuthorityService(
            RegulatoryAuthorityServiceLocal regulatoryAuthorityService) {
        this.regulatoryAuthorityService = regulatoryAuthorityService;
    }

    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(
            PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(
            StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param accrualDiseaseTerminologyService the accrualDiseaseTerminologyService to set
     */
    public void setAccrualDiseaseTerminologyService(
            AccrualDiseaseTerminologyServiceRemote accrualDiseaseTerminologyService) {
        this.accrualDiseaseTerminologyService = accrualDiseaseTerminologyService;
    }
    
}
