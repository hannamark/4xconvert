/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate, market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights  set out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, caBIG Participant shall have no right of accounting or right of payment from You or
 * Your sub licensees for the rights granted under this License.   This  License  is  granted  at no  charge  to You.
 * Your downloading, copying, modifying, displaying, distributing or use of caBIG Software constitutes acceptance  of
 * all of the terms and conditions of this Agreement.  If You do not agree to such terms and conditions,  You have
 * no right to download,  copy,  modify, display, distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any,  must include the following acknowledgment:
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
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation  to secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide  additional  or  different  license  terms  and conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * caBIG SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.AbstractionCompletionDTO;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;

/**
 * service bean for validating the Abstraction.
 *
 * @author Kalpana Guthikonda
 * @since 11/27/2008
 */
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AbstractionCompletionServiceBean implements AbstractionCompletionServiceRemote {

    private static final String SELECT_INT_TRIAL_DESIGN_DETAILS_MSG = "Select Design Details from Interventional Trial"
            + " Design under Scientific Data menu.";
    private static final String SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG = "Select Design Details from Observational Trial"
            + " Design under Scientific Data menu.";
    private static final String SELECT_TRIAL_DESCRIPTION = "Select Trial Description from Scientific Data menu.";
    private static final String SELECT_TRIAL_DETAILS = "Select General Trial Details from Administrative Data menu.";
    private static final String YES = "Yes";
    private static final String NO = "No";

    private CorrelationUtils correlationUtils = new CorrelationUtils();
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    @EJB
    private ArmServiceLocal armService;
    @EJB
    private DocumentServiceLocal documentService;
    @EJB
    private InterventionServiceLocal interventionService;
    @EJB
    private OrganizationCorrelationServiceRemote organizationCorrelationService;
    @EJB
    private PlannedActivityServiceLocal plannedActivityService;
    @EJB
    private PlannedMarkerServiceLocal plannedMarkerService;
    @EJB
    private RegulatoryInformationServiceRemote regulatoryInformationService;
    @EJB
    private StudyContactServiceLocal studyContactService;
    @EJB
    private StudyDiseaseServiceLocal studyDiseaseService;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudyRecruitmentStatusServiceLocal studyRecruitmentStatusService;
    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB
    private StudyResourcingServiceLocal studyResourcingService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<AbstractionCompletionDTO> validateAbstractionCompletion(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }
        AbstractionMessageCollection messages = new AbstractionMessageCollection();

        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (!ISOUtil.isBlNull(studyProtocolDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(studyProtocolDTO.getProprietaryTrialIndicator())) {
            abstractionCompletionRuleForProprietary(studyProtocolDTO, messages);
        } else {
            enforceIdentifierLength(studyProtocolDTO, messages);
            enforceGeneralTrailDetails(studyProtocolDTO, messages);
            enforceNCISpecificInfo(studyProtocolDTO, messages);
            if (studyProtocolDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                enforceRegulatoryInfo(studyProtocolIi, messages);
            }
            enforceIRBInfo(studyProtocolDTO, messages);
            enforceTrialINDIDE(studyProtocolDTO, messages);
            enforceTrialStatus(studyProtocolDTO, messages);
            enforceRecruitmentStatus(studyProtocolIi, messages);

            List<DocumentDTO> isoList = documentService.getDocumentsByStudyProtocol(studyProtocolIi);
            String protocolDoc = null;
            String irbDoc = null;
            if ((CollectionUtils.isNotEmpty(isoList))) {
                for (DocumentDTO dto : isoList) {
                    if (dto.getTypeCode().getCode().equalsIgnoreCase(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())) {
                        protocolDoc = dto.getTypeCode().getCode().toString();
                    } else if (dto.getTypeCode().getCode()
                        .equalsIgnoreCase(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode())) {
                        irbDoc = dto.getTypeCode().getCode().toString();
                    }
                }
            }
            enforceDocument(protocolDoc, irbDoc, messages);

            if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
                InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
                ispDTO = studyProtocolService.getInterventionalStudyProtocol(studyProtocolIi);
                enforceInterventional(ispDTO, messages);
                if (ispDTO.getNumberOfInterventionGroups().getValue() != null) {
                    List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
                    if (aList.size() != ispDTO.getNumberOfInterventionGroups().getValue()) {
                        messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG,
                                          "Number of interventional trial arm records must be the same"
                                                  + " as Number of Arms assigned in Interventional Trial Design.");
                    }
                }
            } else if (studyProtocolDTO.getStudyProtocolType().getValue()
                .equalsIgnoreCase("ObservationalStudyProtocol")) {
                ObservationalStudyProtocolDTO ospDTO = new ObservationalStudyProtocolDTO();
                ospDTO = studyProtocolService.getObservationalStudyProtocol(studyProtocolIi);
                enforceObservational(ospDTO, messages);
                if (ospDTO.getNumberOfGroups().getValue() != null) {
                    List<ArmDTO> aList = armService.getByStudyProtocol(studyProtocolIi);
                    if (aList.size() != ospDTO.getNumberOfGroups().getValue()) {
                        messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG,
                                          "Number of Observational study group records must be the same"
                                                  + " as Number of Groups assigned in Observational Study Design.");
                    }
                }
            }
            enforceTrialDescriptionDetails(studyProtocolDTO, messages);
            enforceOutcomeMeasure(studyProtocolIi, messages);
            enforceInterventions(studyProtocolIi, messages);
            enforceTreatingSite(studyProtocolIi, messages);
            enforceStudyContactNullification(studyProtocolIi, messages);
            enforceStudySiteNullification(studyProtocolIi, messages);
            enforceStudySiteContactNullification(studyProtocolIi, messages);
            enforceArmGroup(studyProtocolIi, studyProtocolDTO, messages);
            enforceTrialFunding(studyProtocolIi, messages);
            enforceDisease(studyProtocolDTO, messages);
            enforceArmInterventional(studyProtocolIi, messages);
            enforceEligibility(studyProtocolIi, messages);
            enforceCollaborator(studyProtocolIi, messages);
            enforceSummary4OrgNullification(studyProtocolIi, messages);
            enforcePlannedMarkerStatus(studyProtocolIi, messages);
        }
        return messages.getMessages();
    }

    private void abstractionCompletionRuleForProprietary(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {

        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        enforceIdentifierLength(studyProtocolDTO, messages);
        enforceGeneralTrailDetails(studyProtocolDTO, messages);

        enforceInterventions(studyProtocolIi, messages);
        enforceStudySiteNullification(studyProtocolIi, messages);
        enforceStudySiteContactNullification(studyProtocolIi, messages);
        enforceTrialFunding(studyProtocolIi, messages);
        enforceDisease(studyProtocolDTO, messages);
        enforceSummary4OrgNullification(studyProtocolIi, messages);
        enforcePlannedMarkerStatus(studyProtocolIi, messages);
        enforceStudySiteRuleForProprietary(studyProtocolIi, messages);
        if (studyProtocolDTO.getPhaseCode().getCode() == null) {
            messages.addError(SELECT_TRIAL_DETAILS, "Trial Phase must be Entered");
        }
        if (studyProtocolDTO.getPrimaryPurposeCode().getCode() == null) {
            messages.addError(SELECT_TRIAL_DETAILS, "Primary Purpose must be Entered");
        }
        List<DocumentDTO> isoList = documentService.getDocumentsByStudyProtocol(studyProtocolIi);
        String protocolDoc = null;
        for (DocumentDTO dto : isoList) {
            if (dto.getTypeCode().getCode().equalsIgnoreCase(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())) {
                protocolDoc = dto.getTypeCode().getCode().toString();
            }
        }
        PAServiceUtils paServiceUtils = new PAServiceUtils();
        StudySiteDTO nctDto = new StudySiteDTO();
        nctDto.setStudyProtocolIdentifier(studyProtocolIi);
        nctDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        String poOrgId = organizationCorrelationService
            .getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE);
        nctDto.setResearchOrganizationIi(organizationCorrelationService
            .getPoResearchOrganizationByEntityIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId)));
        List<StudySiteDTO> studySites = paServiceUtils.getStudySite(nctDto, true);
        StudySiteDTO studySite = PAUtil.getFirstObj(studySites);
        if (protocolDoc == null && studySite == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu. "
                                      + " or Select General Trial Details from Administrative Data menu.",
                              "Either one of NCT number or Proprietary Template document is mandatory");
        }
    }

    /**
     * @param abstractionList
     * @param studyProtocolIi
     * @throws PAException
     */
    private void enforceStudySiteRuleForProprietary(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
        if (spList == null || spList.isEmpty()) {
            messages.addError("Select Participating Sites from Administrative Data menu.",
                              "No Participating Sites exists for the trial.");
            return;
        }
        // treating site for the study
        for (StudySiteDTO spartDto : spList) {
            List<StudySiteContactDTO> spContactDtos = studySiteContactService.getByStudySite(spartDto.getIdentifier());
            boolean piFound = false;
            for (StudySiteContactDTO spContactDto : spContactDtos) {
                String contactRoleCode = spContactDto.getRoleCode().getCode();
                if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)
                        || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)) {
                    piFound = true;
                }
            } // for
            Organization orgBo = getPoOrg(spartDto);
            if (!piFound) {
                // Error Message ID Does Not Match Participating Site PO ID#
                messages.addError("Select Participating Sites from Administrative Data menu.", "Participating site # "
                        + orgBo.getIdentifier() + " Must have an Investigator");

            }
            // No investigator duplicates must exist on the same treating site for the same trial.
            if (piFound && hasDuplicate(getPIForTreatingSite(spContactDtos))) {
                messages.addError("Select Participating Sites from " + " Administrative Data menu.",
                                  "Treating site can not have duplicate investigator.");
                break;
            }
        }
        // No participating site duplicates playing same role must exist on the same trial
        if (hasDuplicate(getTreatingSiteOrg(spList))) {
            messages.addError("Select Participating Sites from Administrative Data menu.",
                              "Trial cannot have duplicate Treating Site.");
        }
    }

    private void enforceStudyContactNullification(Ii studyProtocolIi,
            AbstractionMessageCollection messages) throws PAException {

        List<StudyContactDTO> studyContactDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studyContactDtos)) {
            for (StudyContactDTO studyContactDTO : studyContactDtos) {

                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studyContactDTO.getStatusCode().getCode())) {
                    if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {
                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Principal Investigator status has been set to nullified, "
                                                    + "Please select another Principal Investigator");
                    }
                    if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {
                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Central Contact status has been set to nullified, "
                                                    + "Please select another Central contact");
                    }
                    if (StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studyContactDTO.getRoleCode().getCode())) {

                       messages.addWarning(SELECT_TRIAL_DETAILS,
                                "Responsible Party Study Principal Investigator status has been set to nullified, "
                                        + "Please select another Responsible Party Study Principal Investigator");
                    }
                }
            }
        }
    }

    private void enforceStudySiteNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteDTO> studySiteDtos = studySiteService.getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studySiteDtos)) {
            for (StudySiteDTO studySiteDTO : studySiteDtos) {
                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studySiteDTO.getStatusCode().getCode())) {

                    if (StudySiteFunctionalCode.FUNDING_SOURCE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Funding Source status has been set to nullified, "
                                                    + "Please select another Funding Source");
                    }
                    if (StudySiteFunctionalCode.AGENT_SOURCE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Agent Source status has been set to nullified, "
                                                    + "Please select another Agent Source");
                    }
                    if (StudySiteFunctionalCode.LABORATORY.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {
                        messages.addWarning("Select Collaborators from Administrative Data menu.",
                                            "Laboratory status has been set to nullified, "
                                                    + "Please select another Laboratory");
                    }
                    if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Lead Organization status has been set to nullified, "
                                                    + "Please select another Lead Organization");
                    }
                    if (StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Responsible Party Sponsor status has been set to nullified, "
                                                    + "Please select another Responsible Party Sponsor");
                    }
                    if (StudySiteFunctionalCode.SPONSOR.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning(SELECT_TRIAL_DETAILS,
                                            "Sponsor status has been set to nullified, "
                                                    + "Please select another Sponsor");
                    }
                    if (StudySiteFunctionalCode.TREATING_SITE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Participating Sites from Administrative Data menu.",
                                            "Participating Site status has been set to nullified, "
                                                    + "Please select another Participating Site");
                    }
                    if (StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE.getCode().equalsIgnoreCase(
                            studySiteDTO.getFunctionalCode().getCode())) {

                        messages.addWarning("Select Human Subject Safety under Regulatory"
                                                    + " Information from Administrative Data menu.",
                                            "Board status has been set to nullified, " + "Please select another Board");
                    }
                }
            }
        }
    }

    private void enforceStudySiteContactNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteContactDTO> studySiteContactDtos = studySiteContactService.getByStudyProtocol(studyProtocolIi);
        if (CollectionUtils.isNotEmpty(studySiteContactDtos)) {
            for (StudySiteContactDTO studySiteContactDTO : studySiteContactDtos) {

                if (StructuralRoleStatusCode.NULLIFIED.getCode().equalsIgnoreCase(
                        studySiteContactDTO.getStatusCode().getCode())) {

                    if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Contact tab under Participating Sites from Administrative Data menu.",
                                "Primary Contact status has been set to nullified, "
                                        + "Please select another Primary Contact");
                    }

                    if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Investigators tab under Participating sites from Administrative Data menu.",
                                "Investigator status has been set to nullified, "
                                        + "Please select another Investigator");
                    }

                    if (StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {

                        messages.addError(SELECT_TRIAL_DETAILS,
                                "Responsible Party Sponsor Contact status has been set to nullified, "
                                        + "Please select another Responsible Party Sponsor Contact");
                    }
                    if (StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(
                            studySiteContactDTO.getRoleCode().getCode())) {
                       messages.addWarning(
                                "Select Investigators tab under Participating sites from Administrative Data menu.",
                                "Sub Investigator status has been set to nullified, "
                                        + "Please select another Sub Investigator");
                    }
                }
            }
        }

    }

    private void enforceDisease(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages)
            throws PAException {
        boolean ctgovxmlIndicator = false;
        Ii studyProtocolIi = studyProtocolDTO.getIdentifier();
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);
        for (StudyDiseaseDTO sdDto : sdDtos) {
            if (sdDto.getCtGovXmlIndicator() != null && sdDto.getCtGovXmlIndicator().getValue()) {
                ctgovxmlIndicator = true;
                break;
            }
        }
        if (CollectionUtils.isEmpty(sdDtos)) {
            messages.addError("Select Disease/Condition from Scientific Data Menu",
                              "A trial must have at least one disease/condition");
        }
        // not a proprietary trial and the studyprotocol is set to ctgov = true
        // and there are no diseases with xml inclusion indicator set to true
        if ((ISOUtil.isBlNull(studyProtocolDTO.getProprietaryTrialIndicator()) || !BlConverter
                .convertToBoolean(studyProtocolDTO.getProprietaryTrialIndicator()))
                && (!ISOUtil.isBlNull(studyProtocolDTO.getCtgovXmlRequiredIndicator()) && BlConverter
                        .convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator())) && !ctgovxmlIndicator) {
            messages.addError("Select Disease/Condition from Scientific Data Menu",
                              "Abstraction cannot be valid if trial has no diseases with ctgov xml indicator = 'yes'");
        }
    }

    private void enforceTrialFunding(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<StudyResourcingDTO> srList = studyResourcingService.getStudyResourcingByStudyProtocol(studyProtocolIi);

        if (!(srList.isEmpty())) {
            for (int i = 0; i < srList.size(); i++) {
                int j = 0;
                if (srList.size() > 1 && i != 0
                        && srList.get(j).getFundingMechanismCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getFundingMechanismCode().getCode().toString())
                        && srList.get(j).getNihInstitutionCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getNihInstitutionCode().getCode().toString())
                        && srList.get(j).getNciDivisionProgramCode().getCode().toString()
                                 .equalsIgnoreCase(srList.get(i).getNciDivisionProgramCode().getCode().toString())
                        && srList.get(j).getSerialNumber().getValue().toString()
                                 .equalsIgnoreCase(srList.get(i).getSerialNumber().getValue().toString())) {
                    messages.addError("Select Trial Funding from Administrative Data menu.",
                                      "Trial should not have Duplicate grants.");
                    if (i != srList.size()) {
                        j++;
                    }
                }
            }
        }
    }

    private void enforceArmGroup(Ii studyProtocolIi, StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) throws PAException {
        List<ArmDTO> dtos = armService.getByStudyProtocol(studyProtocolIi);
        if (dtos.isEmpty()) {
            if (studyProtocolDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
                messages.addError("Select Arm under Scientific Data menu.",
                                  "No Arm exists for the trial.");
            } else if (studyProtocolDTO.getStudyProtocolType().getValue()
                .equalsIgnoreCase("ObservationalStudyProtocol")) {
                messages.addError("Select Groups from Observational Trial Design " + "under Scientific Data menu.",
                                  "No Groups exists for the trial.");
            }
        } else {
            for (ArmDTO dto : dtos) {
                if (PAUtil.isGreaterThan(dto.getName(), PAAttributeMaxLen.ARM_NAME)) {
                    messages.addError("Select Arm/Group under Scientific Data menu.", dto.getName().getValue()
                            + "  must not be more than 62 characters  ");
                }
            }
        }
    }

    private void enforceTrialStatus(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages)
            throws PAException {
        StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(studyProtocolDTO
            .getIdentifier());
        if (sos == null) {
            messages.addError("Select Trial Status from Administrative Data menu.",
                              "No Trial Status exists for the trial.");
        }
        if (studyProtocolDTO.getStartDate().getValue() == null
                && studyProtocolDTO.getStartDateTypeCode().getCode() == null
                && studyProtocolDTO.getPrimaryCompletionDate().getValue() == null
                && studyProtocolDTO.getPrimaryCompletionDateTypeCode().getCode() == null) {

            messages.addError("Select Trial Status from Administrative Data menu.",
                              "StartDate/StartDateType and PrimaryCompletionDate/PrimaryCompletionDateType "
                                      + "must be Entered.");
        }
    }

    private void enforceTrialINDIDE(StudyProtocolDTO studyProtocolDto, AbstractionMessageCollection messages)
            throws PAException {
        List<StudyIndldeDTO> siList = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDto.getCtgovXmlRequiredIndicator());
        if (!(siList.isEmpty()) && BooleanUtils.isTrue(ctGovIndicator)) {
            checkDuplicateINDIDE(siList, messages);
            // if IND is is there for Trial Oversight Authority Country =USA
            // then Trial Oversight Authority Organization Name shld be FDA if not throw err
            // get the country and check if its usa if so then check if Org name is FDA if not throw err
            if (paServiceUtil.containsNonExemptInds(siList)) {
                StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = studyRegulatoryAuthorityService
                    .getCurrentByStudyProtocol(studyProtocolDto.getIdentifier());
                if (sraFromDatabaseDTO != null) {
                    Long sraId = Long.valueOf(sraFromDatabaseDTO.getRegulatoryAuthorityIdentifier().getExtension());
                    RegulatoryAuthority regAuth = regulatoryInformationService.get(sraId);
                    if (!("USA".equals(regAuth.getCountry().getAlpha3()) && "Food and Drug Administration"
                        .equalsIgnoreCase(regAuth.getAuthorityName()))) {
                        messages.addError("Select Regulatory under Regulatory Information from Administrative "
                                                  + "Data menu.",
                                          "For IND protocols, Oversight Authorities  must include United States: "
                                                  + "Food and Drug Administration.");
                    }
                }
                if (isCorrelationRuleRequired(studyProtocolDto)) {
                    messages.addError("Select Regulatory under Regulatory Information from Administrative "
                            + "Data menu.",
                            "FDA Regulated Intervention Indicator Should be Yes to add Trail IND IDE records.");
                }
            }
        }
    }

    /**
     * @param studyProtocolDTO
     * @return
     */
    private boolean isCorrelationRuleRequired(StudyProtocolDTO studyProtocolDTO) {
        Boolean ctGovIndicator = BlConverter.convertToBoolean(studyProtocolDTO.getCtgovXmlRequiredIndicator());
        return BooleanUtils.isTrue(ctGovIndicator) && (studyProtocolDTO.getIdentifier() != null
                && studyProtocolDTO.getFdaRegulatedIndicator() != null)
                && (studyProtocolDTO.getFdaRegulatedIndicator().getValue() != null)
                && (!Boolean.valueOf(studyProtocolDTO.getFdaRegulatedIndicator().getValue()));
    }

    private void checkDuplicateINDIDE(List<StudyIndldeDTO> siList, AbstractionMessageCollection messages) {
        for (int i = 0; i < siList.size(); i++) {
            int j = 0;
            if (siList.size() > 1
                    && i != 0
                    && siList.get(j).getGrantorCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getGrantorCode().getCode().toString())
                    && siList.get(j).getHolderTypeCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getHolderTypeCode().getCode().toString())
                    && siList.get(j).getIndldeNumber().getValue().toString()
                        .equalsIgnoreCase(siList.get(i).getIndldeNumber().getValue().toString())
                    && siList.get(j).getIndldeTypeCode().getCode().toString()
                        .equalsIgnoreCase(siList.get(i).getIndldeTypeCode().getCode().toString())) {
                messages.addError("Select Trial IND/IDE under Regulatory Information from Administrative "
                        + "Data menu.", "Trial IND/IDE should not have Duplicate values.");
                if (i != siList.size()) {
                    j++;
                }
            }
        }
    }

    private void enforceRegulatoryInfo(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {

        List<StudyRegulatoryAuthorityDTO> sraDTOList = studyRegulatoryAuthorityService
            .getByStudyProtocol(studyProtocolIi);
        StudyRegulatoryAuthorityDTO sraDTO = null;
        if (!sraDTOList.isEmpty()) {
            sraDTO = sraDTOList.get(0);
        }
        if (sraDTO == null) {
            messages.addError("Select Regulatory under Regulatory Information" + " from Administrative Data menu.",
                              "Regulatory Information fields must be Entered.");
        }
        // Display error in abstraction validation if section 801 indicator = yes,
        // delayed posting indicator is yes and trial does not include Intervention with type Device
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (YES.equalsIgnoreCase(convertBLToString(spDTO.getSection801Indicator()))
                && YES.equalsIgnoreCase(convertBLToString(spDTO.getDelayedpostingIndicator()))
                && !isDeviceFound(studyProtocolIi)) {
            messages.addError("Select Regulatory under Regulatory Information" + " from Administrative Data menu.",
                              "Delay posting indicator can only be set to \'yes\' "
                                      + " if study includes at least one intervention with type \'device\'.");
        }

    }

    private void enforceIRBInfo(StudyProtocolDTO spDto, AbstractionMessageCollection messages) throws PAException {

        Boolean reviewBoardIndicator = spDto.getReviewBoardApprovalRequiredIndicator().getValue();

        if (reviewBoardIndicator == null) {
            messages
                .addError("Select Human Subject Safety under Regulatory Information"
                                  + " from Administrative Data menu.",
                          "Review Board Approval Status is missing, Please complete Human Subject Review information.");
        }
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spDto.getIdentifier(), srDTO);

        StudyOverallStatusDTO sos = studyOverallStatusService.getCurrentByStudyProtocol(spDto.getIdentifier());
        if (sos != null && !spList.isEmpty()) {
            StudySiteDTO studySite = spList.get(0);
            if (StudyStatusCode.IN_REVIEW.getCode().equalsIgnoreCase(sos.getStatusCode().getCode())
                    && !studySite.getReviewBoardApprovalStatusCode().getCode()
                        .equals(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode())) {
                messages.addWarning("Select Human Subject Safety under Regulatory Information",
                                    "Data inconsistency: \'Submitted, pending\' value (Review Board Approval Status) "
                                            + "is only valid for the current trial status \'In-Review\'.");

            }
            if (StudyStatusCode.WITHDRAWN.getCode().equalsIgnoreCase(sos.getStatusCode().getCode())
                    && !studySite.getReviewBoardApprovalStatusCode().getCode()
                        .equals(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode())) {
                messages.addWarning("Select Human Subject Safety under Regulatory Information",
                                    "Data inconsistency: \'Submitted, denied\' value (Review Board Approval Status) is "
                                            + "only valid for the current trial status \'WithDrawn\'.");
            }
        }

        // spList Empty => No Study Oversight Committee.
        // Display warning if Study is recruiting && reviewBoardindicator is false =>
        // Board Approval Status = Submission Not Required.
        StudyRecruitmentStatusDTO studyRecruitmentStatusDto = studyRecruitmentStatusService
            .getCurrentByStudyProtocol(spDto.getIdentifier());
        RecruitmentStatusCode recruitmentStatusCode = RecruitmentStatusCode.getByCode(studyRecruitmentStatusDto
            .getStatusCode().getCode());
        if (spList.isEmpty() && BooleanUtils.isFalse(reviewBoardIndicator) && recruitmentStatusCode.isRecruiting()) {
            messages.addWarning("Select a different review board status",
                                "Data inconsistency. Review Board Approval Status cannot be 'Not required'"
                                        + " for an interventional study that is recruiting patients");
        }
    }

    /**
     * Enforce the recruitment status rules.
     * @param studyProtocolIi The study protocil Ii
     * @param messages The messages object collecting errors and warnings
     * @throws PAException if an error occurs.
     */
    void enforceRecruitmentStatus(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {

        StudyRecruitmentStatusDTO rsDto = studyRecruitmentStatusService.getCurrentByStudyProtocol(studyProtocolIi);
        RecruitmentStatusCode recruitmentStatus = RecruitmentStatusCode.getByCode(rsDto.getStatusCode().getCode());
        boolean studySiteRecruiting = isStudySiteRecruiting(studyProtocolIi);

        if (recruitmentStatus.isRecruiting() && !studySiteRecruiting) {
            String errorMsg = "Data inconsistency: At least one location needs to be recruiting if the overall "
                    + "recruitment status is '%s'";
            messages.addError("Select Participating Sites from Administrative Data menu.",
                              String.format(errorMsg, recruitmentStatus.getCode()));
        }
        boolean isInReviewOrApproved = recruitmentStatus == RecruitmentStatusCode.IN_REVIEW
                || recruitmentStatus == RecruitmentStatusCode.APPROVED;

        if (isInReviewOrApproved && studySiteRecruiting) {
            String errorMsg = "Data inconsistency. No site can recruit patients if the overall"
                    + " recruitment status is '%s'";
            messages.addWarning("Select Participating Sites from Administrative Data menu.",
                                String.format(errorMsg, recruitmentStatus.getCode()));
        }

        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);

        if (isInReviewOrApproved && studyProtocolDTO.getStartDate().getValue().before(new Date())) {
            String errorMsg = "Data inconsistency. Study Start Date cannot be in the past if the overall "
                    + "recruitment status is '%s'";
            messages.addWarning("Select study start date.", String.format(errorMsg, recruitmentStatus.getCode()));
        }
    }

    /**
     * Test if a site is recruiting.
     * @param studyProtocolIi The study protocil Ii
     * @return true if a site of the given study is recruiting.
     * @throws PAException if an error occurs.
     */
    boolean isStudySiteRecruiting(Ii studyProtocolIi) throws PAException {
        boolean studySiteRecruiting = false;
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> studySites = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);

        for (StudySiteDTO studySite : studySites) {
            List<StudySiteAccrualStatusDTO> studySiteAccrualStatuses = studySiteAccrualStatusService
                .getStudySiteAccrualStatusByStudySite(studySite.getIdentifier());

            Long tmp = 1L;
            StudySiteAccrualStatusDTO lastestStudySiteAccrualStatusDTO = null;
            if (CollectionUtils.isNotEmpty(studySiteAccrualStatuses)) {
                for (StudySiteAccrualStatusDTO studySiteAccrualStatus : studySiteAccrualStatuses) {
                    Long latestId = IiConverter.convertToLong(studySiteAccrualStatus.getIdentifier());
                    if (latestId > tmp) {
                        tmp = latestId;
                        lastestStudySiteAccrualStatusDTO = studySiteAccrualStatus;
                    }
                }
            }

            if (lastestStudySiteAccrualStatusDTO != null) {
                String latestStatusCode = lastestStudySiteAccrualStatusDTO.getStatusCode().getCode();
                RecruitmentStatusCode accrualStatus = RecruitmentStatusCode.getByCode(latestStatusCode);
                if (accrualStatus.isRecruiting()) {
                    return true;
                }
            }
        }
        return studySiteRecruiting;
    }

    private void enforceTreatingSite(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, srDTO);
        if (spList == null || spList.isEmpty()) {
            messages.addError("Select Participating Sites from Administrative Data menu.",
                              "No Participating Sites exists for the trial.");
            return;
        }
        // check if central contact exits for the study
        boolean centralContactDefined = isCentralContactDefined(studyProtocolIi);

        for (StudySiteDTO spartDto : spList) {
            List<StudySiteContactDTO> spContactDtos = studySiteContactService.getByStudySite(spartDto.getIdentifier());
            boolean piFound = false;
            boolean contactFound = false;
            for (StudySiteContactDTO spContactDto : spContactDtos) {
                String contactRoleCode = spContactDto.getRoleCode().getCode();
                if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)
                        || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(contactRoleCode)) {
                    piFound = true;
                } else if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equalsIgnoreCase(contactRoleCode)) {
                    contactFound = true;
                }

            }
            Organization orgBo = getPoOrg(spartDto);
            if (!piFound) {
                // Error Message ID Does Not Match Participating Site PO ID#
                messages.addError("Select Participating Sites from Administrative Data menu.", "Participating site # "
                        + orgBo.getIdentifier() + " Must have an Investigator");

            }
            // No investigator duplicates must exist on the same treating site for the same trial.
            if (piFound && hasDuplicate(getPIForTreatingSite(spContactDtos))) {
                messages.addError("Select Participating Sites from " + " Administrative Data menu.",
                                  "Treating site can not have duplicate investigator.");
                break;
            }
            // abstraction validation rule for participating site contact and central contact
            if (!contactFound && !centralContactDefined) {
                messages.addError("Select"
                        + " General Trial Details screen to complete Central Contact or Participating Sites screen to"
                        + " complete Participating Site Contact information.", "Participating Site Contact"
                        + " or Central Contact information is mandatory. Complete Central Contact"
                        + " or each Participating Site Contact information.");
            }

        }
        // No participating site duplicates playing same role must exist on the same trial
        if (hasDuplicate(getTreatingSiteOrg(spList))) {
            messages.addError("Select Participating Sites from Administrative Data menu.",
                              "Trial cannot have duplicate Treating Site.");
        }

    }

    private boolean isCentralContactDefined(Ii studyProtocolIi) throws PAException {
        boolean ccDefined = false;
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi);
        if (scDtos != null && !scDtos.isEmpty()) {
            for (StudyContactDTO studyContactDTO : scDtos) {
                if (StudyContactRoleCode.CENTRAL_CONTACT.getCode().equalsIgnoreCase(
                        studyContactDTO.getRoleCode().getCode())) {
                    ccDefined = true;
                }
            }
        }
        return ccDefined;
    }

    private void enforceCollaborator(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, criteriaList);
        List<String> newspList = new ArrayList<String>();
        for (StudySiteDTO spdto : spList) {
            newspList.add(spdto.getFunctionalCode().getCode() + spdto.getResearchOrganizationIi().getExtension());
        }
        if (hasDuplicate(newspList)) {
            messages.addError("Select Collaborators from Administrative Data menu.",
                              "Trial can not have a duplicate collaborator playing the same role.");
        }
    }

    /**
     * @param spartDto
     * @return
     * @throws PAException
     */
    private Organization getPoOrg(StudySiteDTO spartDto) throws PAException {
        return correlationUtils.getPAOrganizationByIi(spartDto.getHealthcareFacilityIi());
    }

    private List<String> getPIForTreatingSite(List<StudySiteContactDTO> spContactDtos) {
        List<String> piList = new ArrayList<String>();
        for (StudySiteContactDTO dto : spContactDtos) {
            if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(dto.getRoleCode().getCode())
                    || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode()
                        .equalsIgnoreCase(dto.getRoleCode().getCode())) {
                if (dto.getClinicalResearchStaffIi() != null) {
                    piList.add(dto.getClinicalResearchStaffIi().getExtension());
                }
                if (dto.getHealthCareProviderIi() != null) {
                    piList.add(dto.getHealthCareProviderIi().getExtension());
                }
            }
        }
        return piList;
    }

    private List<String> getTreatingSiteOrg(List<StudySiteDTO> spartList) {
        List<String> treatingSiteList = new ArrayList<String>();
        for (StudySiteDTO spdto : spartList) {
            treatingSiteList.add(spdto.getHealthcareFacilityIi().getExtension());
        }
        return treatingSiteList;
    }

    private <T> boolean hasDuplicate(Collection<T> list) {
        Set<T> set = new HashSet<T>();
        // Set#add returns false if the set does not change, which
        // indicates that a duplicate element has been added.
        boolean dup = false;
        for (T each : list) {
            if (!set.add(each)) {
                dup = true;
            }
        }
        return dup;
    }

    private void enforceInterventions(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        boolean interventionsList = false;
        for (PlannedActivityDTO pa : paList) {
            if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                .convertCdToString(pa.getCategoryCode())))
                    || ActivityCategoryCode.SUBSTANCE_ADMINISTRATION.equals(ActivityCategoryCode.getByCode(CdConverter
                        .convertCdToString(pa.getCategoryCode())))
                    || ActivityCategoryCode.PLANNED_PROCEDURE.equals(ActivityCategoryCode.getByCode(CdConverter
                        .convertCdToString(pa.getCategoryCode())))) {
                interventionsList = true;
                // validation rules for inactive interventions
                InterventionDTO iDto = interventionService.get(pa.getInterventionIdentifier());
                if (ActiveInactiveCode.INACTIVE.getCode().equalsIgnoreCase(iDto.getStatusCode().getCode())) {
                    messages.addWarning("Select Interventions from Scientific Data menu.", "Intervention '"
                            + iDto.getName().getValue() + "' status has been set to inactive"
                            + ", Please select another Intervention.");
                }

            }
        }
        if (!interventionsList) {
            messages.addError("Select Interventions from Scientific Data menu.",
                              "No Interventions exists for the trial.");
        }
    }

    private void enforceOutcomeMeasure(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        List<StudyOutcomeMeasureDTO> somList = studyOutcomeMeasureService.getByStudyProtocol(studyProtocolIi);
        boolean isPrimayFound = false;
        for (StudyOutcomeMeasureDTO somDto : somList) {
            if (BlConverter.convertToBool(somDto.getPrimaryIndicator())) {
                isPrimayFound = true;
                break;
            }
        }
        if (!isPrimayFound) {
            messages.addError("Select Outcome Measure from Interventional/Observational under Scientific Data menu.",
                              "Trial must include at least one PRIMARY outcome measure.");
        }
    }

    private void enforceGeneralTrailDetails(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) {
        if (!PAUtil.checkAssignedIdentifierExists(studyProtocolDTO)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "NCI Trial Identifier must be Entered");
        }
        if (studyProtocolDTO.getOfficialTitle().getValue() == null) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Official Title must be Entered");
        } else if (PAUtil.isGreaterThan(studyProtocolDTO.getOfficialTitle(), PAAttributeMaxLen.LEN_600)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Official Title cannot be more than 600 chracters ");
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getAcronym(), PAAttributeMaxLen.ACRONYM)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Acronym must not be more than 14 characters ");
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getScientificDescription(), PAAttributeMaxLen.LEN_32000)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Detailed Description must not be more than 32000 characters ");
        }
        if (PAUtil.isGreaterThan(studyProtocolDTO.getKeywordText(), PAAttributeMaxLen.KEYWORD)) {
            messages.addError(SELECT_TRIAL_DETAILS,
                              "Keywords must not be more than 160 characters ");
        }
    }

    private void enforceTrialDescriptionDetails(StudyProtocolDTO studyProtocolDTO,
            AbstractionMessageCollection messages) {
        if (studyProtocolDTO.getPublicTitle().getValue() == null) {
            messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Title must be Entered");
        } else {
            if (!PAUtil.isWithinRange(studyProtocolDTO.getPublicTitle(), PAAttributeMaxLen.LEN_18,
                                      PAAttributeMaxLen.LEN_300)) {
                messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Title must be between 18 and 300 characters ");
            }
        }
        if (studyProtocolDTO.getPublicDescription().getValue() == null) {
            messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Summary must be Entered");
        } else {
            if (PAUtil.isGreaterThan(studyProtocolDTO.getPublicDescription(), PAAttributeMaxLen.LEN_5000)) {
                messages.addError(SELECT_TRIAL_DESCRIPTION, "Brief Summary must not be more than 5000 characters ");
            }
        }
    }

    private void enforceNCISpecificInfo(StudyProtocolDTO studyProtocolDTO, AbstractionMessageCollection messages) {
        if (studyProtocolDTO.getAccrualReportingMethodCode().getCode() == null) {
            messages.addError("Select NCI Specific Information from Administrative Data menu.",
                              "Reporting Data Set Method must be Entered");
        }
    }

    private void enforceDocument(String protocolDoc, String irbDoc, AbstractionMessageCollection messages) {
        if (protocolDoc == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu.",
                              "Protocol_Document is required");
        }
        if (irbDoc == null) {
            messages.addError("Select Trial Related Documents from Administrative Data menu.",
                              "IRB_Approval_Document is required");
        }
    }

    private void enforceObservational(ObservationalStudyProtocolDTO ospDTO, AbstractionMessageCollection messages) {
        if (ospDTO.getStudyModelCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Study Model must be Entered");
        } else {
            if (ospDTO.getStudyModelCode().getCode().equalsIgnoreCase("Other")
                    && ospDTO.getStudyModelOtherText() == null) {
                messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Study Model Comment must be Entered");
            }
        }

        if (ospDTO.getTimePerspectiveCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Time Perspective must be Entered");
        } else {
            if (ospDTO.getTimePerspectiveCode().getCode().equalsIgnoreCase("Other")
                    && ospDTO.getTimePerspectiveOtherText() == null) {
                messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Time Perspective Comment must be Entered");
            }
        }
        if (ospDTO.getBiospecimenRetentionCode().getCode() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Bio-specimen Retention must be Entered");
        }
        if (ospDTO.getNumberOfGroups().getValue() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Number of Groups/Cohorts must be Entered");
        }
        if (ospDTO.getTargetAccrualNumber().getLow().getValue() == null) {
            messages.addError(SELECT_OBS_TRIAL_DESIGN_DETAILS_MSG, "Target Enrollment must be Entered");
        }
    }

    private void enforceInterventional(InterventionalStudyProtocolDTO ispDTO, AbstractionMessageCollection messages) {
        if (ispDTO.getPrimaryPurposeCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Primary Purpose must be Entered");
        }

        if (ispDTO.getPhaseCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Trial Phase must be Entered");
        }
        if (ispDTO.getDesignConfigurationCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Intervention Model must be Entered");
        }
        if (ispDTO.getNumberOfInterventionGroups().getValue() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Number of Arms must be Entered");
        }
        if (ispDTO.getBlindingSchemaCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Masking must be Entered");
        }
        if (ispDTO.getAllocationCode().getCode() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Allocation must be Entered");
        }
        if (ispDTO.getTargetAccrualNumber().getLow().getValue() == null) {
            messages.addError(SELECT_INT_TRIAL_DESIGN_DETAILS_MSG, "Target Enrollment must be Entered");
        }
    }

    private void enforceArmInterventional(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        HashMap<String, String> intervention = new HashMap<String, String>();
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier());

                if (armDtos == null || armDtos.isEmpty()) {
                    messages.addError("Select Arm from Scientific Data menu and associated Intervention.",
                                      "Every intervention in interventional trial must be associated with at least"
                                              + " one arm in interventional trial");
                }
                for (ArmDTO armDTO : armDtos) {
                    intervention.put(armDTO.getName().getValue(), armDTO.getName().getValue());
                }
            }
        }
        List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolIi);
        for (ArmDTO armDTO : arms) {
            if (ArmTypeCode.NO_INTERVENTION.getCode().equals(armDTO.getTypeCode().getCode())) {
                continue;
            }
            if (!intervention.containsKey(armDTO.getName().getValue())) {
                messages.addError("Select Arm from Scientific Data menu and associated Interventional.", "Arm "
                        + armDTO.getName().getValue() + " does not have any Intervention associated");

            }
        }
    }

    private void enforceEligibility(Ii studyProtocolIi, AbstractionMessageCollection messages) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
            .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolIi);

        if (paECs == null || paECs.isEmpty()) {
            messages.addError("Select Eligibilty Criteria from specific Interventional/Observational"
                    + " under Scientific Data menu.", " Does not have any Eligibilty Criteria");
            return;
        }
        boolean otherCriteriaExist = false;
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            if (ActivityCategoryCode.OTHER.getCode().equals(paEC.getCategoryCode().getCode())) {
                otherCriteriaExist = true;
            }
        } // for loop
        if (!otherCriteriaExist) {
            messages.addError("Select Eligibilty Criteria from specific Interventional/Observational under Scientific "
                    + "Data menu and Add Other Criteria.", " Minimum one Other criteria must be added ");

        }
    }

    private void enforceIdentifierLength(StudyProtocolDTO spDto, AbstractionMessageCollection messages)
            throws PAException {
        List<StudySiteDTO> sParts = new ArrayList<StudySiteDTO>();
        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        sParts.add(spartDTO);
        spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER));
        sParts.add(spartDTO);
        List<StudySiteDTO> dtos = studySiteService.getByStudyProtocol(spDto.getIdentifier(), sParts);
        for (StudySiteDTO dto : dtos) {
            if (PAUtil.isGreaterThan(dto.getLocalStudyProtocolIdentifier(), PAAttributeMaxLen.LEN_30)) {
                if (StudySiteFunctionalCode.LEAD_ORGANIZATION.getCode().equals(dto.getFunctionalCode().getCode())) {
                    messages.addError(SELECT_TRIAL_DETAILS,
                                      "Lead Organization Trial Identifier  cannot be more than 30 characters");
                } else if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getCode().equals(dto.getFunctionalCode()
                                                                                            .getCode())) {
                    messages.addError(SELECT_TRIAL_DETAILS,
                                      "NCT Number cannot be more than 30 characters");
                }

            }
        }
    }

    /**
     * @param studyProtocolIi
     * @param abstractionWarnList
     * @throws PAException on err
     */
    private void enforceSummary4OrgNullification(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        StudyResourcingDTO studyResourcingDTO = studyResourcingService.getSummary4ReportedResourcing(studyProtocolIi);
        if (studyResourcingDTO != null && !ISOUtil.isIiNull(studyResourcingDTO.getOrganizationIdentifier())) {
            Long paOrgId = IiConverter.convertToLong(studyResourcingDTO.getOrganizationIdentifier());
            Organization org = correlationUtils.getPAOrganizationByIi(IiConverter.convertToPaOrganizationIi(paOrgId));
            if (org != null && EntityStatusCode.NULLIFIED.getCode().equals(org.getStatusCode().getCode())) {
                messages.addWarning("Select NCI Specific Information from Administrative Data menu.",
                                    " Summary 4 Funding Sponsor  status has been set to nullified, "
                                            + "Please select another Summary 4 Funding Sponsor");
            }
        }
    }

    /**
     * Checks for the planned markers with the pending status.
     * @param studyProtocolIi the ii of the study protocol
     * @param abstractionWarningList the current list of warnings
     * @throws PAException on error
     */
    private void enforcePlannedMarkerStatus(Ii studyProtocolIi, AbstractionMessageCollection messages)
            throws PAException {
        List<PlannedMarkerDTO> plannedMarkers = plannedMarkerService.getByStudyProtocol(studyProtocolIi);
        for (PlannedMarkerDTO marker : plannedMarkers) {
            String statusCode = marker.getStatusCode().getCode();
            if (ActiveInactivePendingCode.getByCode(statusCode) == ActiveInactivePendingCode.PENDING) {
                messages.addWarning("At least one pending biomarker exists on the trial.", "Use Marker menu-option.");
                break;
            }
        }

    }

    private boolean isDeviceFound(Ii studyProtocolIi) throws PAException {
        boolean found = false;
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);
        for (PlannedActivityDTO pa : paList) {
            if (pa.getCategoryCode() != null
                    && ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                            .convertCdToString(pa.getCategoryCode()))) && pa.getSubcategoryCode() != null
                    && pa.getSubcategoryCode().getCode() != null
                    && InterventionTypeCode.DEVICE.getCode().equalsIgnoreCase(pa.getSubcategoryCode().getCode())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private static String convertBLToString(Bl bl) {
        if (bl == null) {
            return null;
        }
        return BooleanUtils.isTrue(bl.getValue()) ? YES : NO;
    }

    /**
     * Sets the CorrelationUtils Service.
     * @param corUtils The service to set
     */
    public void setCorrelationUtils(CorrelationUtils corUtils) {
        this.correlationUtils = corUtils;
    }

    /**
     * @return the paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @param armService the armService to set
     */
    public void setArmService(ArmServiceLocal armService) {
        this.armService = armService;
    }

    /**
     * @param documentService the documentService to set
     */
    public void setDocumentService(DocumentServiceLocal documentService) {
        this.documentService = documentService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param organizationCorrelationService the organizationCorrelationService to set
     */
    public void setOrganizationCorrelationService(OrganizationCorrelationServiceRemote organizationCorrelationService) {
        this.organizationCorrelationService = organizationCorrelationService;
    }

    /**
     * @param plannedActivityService the plannedActivityService to set
     */
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param plannedMarkerService the plannedMarkerService to set
     */
    public void setPlannedMarkerService(PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceRemote regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
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
     * @param studyRecruitmentStatusService the studyRecruitmentStatusService to set
     */
    public void setStudyRecruitmentStatusService(StudyRecruitmentStatusServiceLocal studyRecruitmentStatusService) {
        this.studyRecruitmentStatusService = studyRecruitmentStatusService;
    }

    /**
     * @param regulatoryAuthorityService the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal regulatoryAuthorityService) {
        this.studyRegulatoryAuthorityService = regulatoryAuthorityService;
    }

    /**
     * @param studyResourcingService the studyResourcingService to set
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResourcingService) {
        this.studyResourcingService = studyResourcingService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
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

}
