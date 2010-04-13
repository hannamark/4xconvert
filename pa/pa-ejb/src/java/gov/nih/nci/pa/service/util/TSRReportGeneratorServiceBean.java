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

import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StratumGroupDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StratumGroupServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.report.AbstractTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.PdfTsrReportGenerator;
import gov.nih.nci.pa.service.util.report.TSRErrorReport;
import gov.nih.nci.pa.service.util.report.TSRReport;
import gov.nih.nci.pa.service.util.report.TSRReportArmGroup;
import gov.nih.nci.pa.service.util.report.TSRReportCollaborator;
import gov.nih.nci.pa.service.util.report.TSRReportDiseaseCondition;
import gov.nih.nci.pa.service.util.report.TSRReportEligibilityCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportGeneralTrialDetails;
import gov.nih.nci.pa.service.util.report.TSRReportHumanSubjectSafety;
import gov.nih.nci.pa.service.util.report.TSRReportIndIde;
import gov.nih.nci.pa.service.util.report.TSRReportIntervention;
import gov.nih.nci.pa.service.util.report.TSRReportInvestigator;
import gov.nih.nci.pa.service.util.report.TSRReportNihGrant;
import gov.nih.nci.pa.service.util.report.TSRReportOutcomeMeasure;
import gov.nih.nci.pa.service.util.report.TSRReportParticipatingSite;
import gov.nih.nci.pa.service.util.report.TSRReportRegulatoryInformation;
import gov.nih.nci.pa.service.util.report.TSRReportStatusDate;
import gov.nih.nci.pa.service.util.report.TSRReportSubGroupStratificationCriteria;
import gov.nih.nci.pa.service.util.report.TSRReportSummary4Information;
import gov.nih.nci.pa.service.util.report.TSRReportTrialDesign;
import gov.nih.nci.pa.service.util.report.TSRReportTrialIdentification;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * service bean for generating TSR.
 *
 * @author Naveen Amiruddin , Kalpana Guthikonda
 * @author Krishna Kanchinadam
 * @since 01/21/2009
 */
@Stateless
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.TooManyMethods",
        "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveClassLength", "PMD.TooManyFields", "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class TSRReportGeneratorServiceBean implements TSRReportGeneratorServiceRemote {

    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
    StudyIndldeServiceLocal studyIndldeService = null;
    @EJB
    StudyDiseaseServiceLocal studyDiseaseService = null;
    @EJB
    ArmServiceLocal armService = null;
    @EJB
    PlannedActivityServiceLocal plannedActivityService = null;
    @EJB
    StudySiteServiceLocal studySiteService = null;
    @EJB
    StudySiteContactServiceLocal studySiteContactService = null;
    @EJB
    StudyContactServiceLocal studyContactService = null;
    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = null;
    @EJB
    StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService = null;
    @EJB
    StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService = null;
    @EJB
    OrganizationCorrelationServiceRemote ocsr = null;
    @EJB
    DocumentWorkflowStatusServiceLocal documentWorkflowStatusService = null;
    @EJB
    RegulatoryInformationServiceRemote regulatoryInformationService = null;
    @EJB
    DiseaseServiceLocal diseaseService = null;
    @EJB
    InterventionServiceLocal interventionService = null;
    @EJB
    InterventionAlternateNameServiceRemote interventionAlternateNameService = null;
    @EJB
    StudyResourcingServiceLocal studyResourcingService = null;
    @EJB
    PAOrganizationServiceRemote paOrganizationService = null;
    @EJB
    StudyObjectiveServiceLocal studyObjectiveService = null;
    @EJB
    StratumGroupServiceLocal stratumGroupService = null;

    /** The Constant LOG. */
    private static final Logger LOG = Logger.getLogger(TSRReportGeneratorServiceBean.class);

    /** The correlation utils. */
    private final CorrelationUtils correlationUtils = new CorrelationUtils();

    private final PAServiceUtils paServiceUtils = new PAServiceUtils();
    private AbstractTsrReportGenerator tsrReportGenerator;
    private Ii studyProtocolIi;
    private StudyProtocolDTO studyProtocolDto;
    private Ii studyProtocolDtoIdentifier;
    private boolean isProprietaryTrial;

    private static final String REPORT_TITLE = "Trial Summary Report";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final String INFORMATION_NOT_PROVIDED = "Information Not Provided";
    private static final String BLANK = "";
    private static final String PROPRIETARY = "Proprietary";
    private static final String NON_PROPRIETARY = "Non-Proprietary";
    private static final String TYPE_INTERVENTIONAL = "Interventional";
    private static final String TYPE_OBSERVATIONAL = "Observational";
    private static final String CRITERION_GENDER = "GENDER";
    private static final String CRITERION_AGE = "AGE";
    private static final String ROLE_PI = "Principal Investigator";
    private static final String SPACE = " ";
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 999;
    private static final String AFFILIATED_WITH = " affiliated with ";
    private static final String IN_THE_ROLE_OF = " in the role of ";
    private static final String AS_OF = " as of ";

    /**
     * Generate tsr html.
     *
     * @param studyProtIi ii of studyprotocol.
     * @return ByteArrayOutputStream the byte stream.
     * @throws PAException the exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ByteArrayOutputStream generateTsrReport(Ii studyProtIi) throws PAException {
        LOG.debug("Entering generateTsrReport - PDF Generation.");

        ByteArrayOutputStream outputByteStream = null;
        studyProtocolIi = studyProtIi;
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }

        try {
            studyProtocolDto = studyProtocolService.getStudyProtocol(studyProtocolIi);
            studyProtocolDtoIdentifier = studyProtocolDto.getIdentifier();
            isProprietaryTrial = !PAUtil.isBlNull(studyProtocolDto.getProprietaryTrialIndicator())
                    && BlConverter.covertToBoolean(studyProtocolDto.getProprietaryTrialIndicator());

            TSRReport tsrReport = new TSRReport(REPORT_TITLE, PAUtil.today(), getNormalizedDateString(studyProtocolDto
                    .getRecordVerificationDate()));
            tsrReportGenerator = new PdfTsrReportGenerator(tsrReport, isProprietaryTrial);
            if (isProprietaryTrial) {
                setProprietaryTrialReportDetails();
            } else {
                setNonProprietaryTrialReportDetails();
            }
            outputByteStream = tsrReportGenerator.generateTsrReport();
        } catch (Exception e) {
            TSRErrorReport tsrErrorReport = new TSRErrorReport(REPORT_TITLE, studyProtocolDto
                    .getAssignedIdentifier().getExtension(), getValue(studyProtocolDto.getOfficialTitle()));
            tsrErrorReport.setErrorType(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                tsrErrorReport.getErrorReasons().add(ste.toString());
            }
            tsrReportGenerator = new PdfTsrReportGenerator(tsrErrorReport);
            outputByteStream = tsrReportGenerator.generateErrorReport();
        }
        return outputByteStream;
    }

    private void setProprietaryTrialReportDetails() throws PAException, NullifiedRoleException {
        setTrialIdentificationDetails();
        setGeneralTrialDetails();
        setSummary4Information();
        setDiseases();
        setInterventions();
        setParticipatingSites();
    }

    private void setNonProprietaryTrialReportDetails() throws PAException, NullifiedRoleException {
        setTrialIdentificationDetails();
        setGeneralTrialDetails();
        setStatusDatesDetails();
        setRegulatoryInformation();
        setHumanSubjectSafety();
        setIndIdes();
        setNihGrants();
        setSummary4Information();
        setCollaborators();
        setDiseases();
        setTrialDesign();
        setEligibilityCriteria();
        setArmGroups();
        setPrimaryAndSecondaryOutcomeMeasures();
        setSubGroupStratificationCriteria();
        setParticipatingSites();
    }

    private void setInterventions() throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolDtoIdentifier);
        if (paList != null && !paList.isEmpty()) {
            for (PlannedActivityDTO paDto : paList) {
                if (PAUtil.isTypeIntervention(paDto.getCategoryCode())) {
                    tsrReportGenerator.getInterventions().add(getIntervention(paDto));
                }
            }
        }
    }

    private TSRReportIntervention getIntervention(PlannedActivityDTO paDto) throws PAException {
        InterventionDTO interventionDto = interventionService.get(paDto.getInterventionIdentifier());
        TSRReportIntervention intervention = new TSRReportIntervention();
        intervention.setType(getValue(paDto.getSubcategoryCode()));
        intervention.setName(getValue(interventionDto.getName()));
        intervention.setAlternateName(getInterventionAltNames(interventionDto));
        intervention.setDescription(getValue(paDto.getTextDescription()));
        return intervention;
    }

    private void setTrialIdentificationDetails() throws PAException {
        TSRReportTrialIdentification trialIdentification = new TSRReportTrialIdentification();
        trialIdentification.setTrialCategory(isProprietaryTrial ? PROPRIETARY : NON_PROPRIETARY);
        trialIdentification.setNciIdentifier(studyProtocolDto.getAssignedIdentifier().getExtension());
        trialIdentification.setLeadOrgIdentifier(getTiLeadOrgIdentifier());
        trialIdentification.setNctNumber(getIdentifier(PAConstants.NCT_IDENTIFIER_TYPE));
        trialIdentification.setDcpIdentifier(getIdentifier(PAConstants.DCP_IDENTIFIER_TYPE));
        trialIdentification.setCtepIdentifier(getIdentifier(PAConstants.CTEP_IDENTIFIER_TYPE));
        trialIdentification.setAmendmentNumber(getValue(studyProtocolDto.getAmendmentNumber()));
        trialIdentification.setAmendmentDate(getNormalizedDateString(studyProtocolDto.getAmendmentDate()));
        Organization lead = ocsr.getOrganizationByFunctionRole(studyProtocolDtoIdentifier, CdConverter
                .convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        trialIdentification.setLeadOrganization(lead.getName());

        tsrReportGenerator.setTrialIdentification(trialIdentification);
    }

    private String getTiLeadOrgIdentifier() throws PAException {
        String leadOrgIdentifier = null;
        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);
        if (sParts != null && !sParts.isEmpty() && sParts.get(0).getLocalStudyProtocolIdentifier() != null) {
            leadOrgIdentifier = sParts.get(0).getLocalStudyProtocolIdentifier().getValue();
        }
        return leadOrgIdentifier;
    }

    private String getIdentifier(String identifierType) throws PAException {
        String identifier = paServiceUtils.getStudyIdentifier(studyProtocolDtoIdentifier, identifierType);
        return (StringUtils.isNotBlank(identifier) ? identifier : INFORMATION_NOT_PROVIDED);
    }

    private void setGeneralTrialDetails() throws PAException, NullifiedRoleException {
        TSRReportGeneralTrialDetails gtd = new TSRReportGeneralTrialDetails();

        gtd.setOfficialTitle(getValue(studyProtocolDto.getOfficialTitle(), INFORMATION_NOT_PROVIDED));

        if (isProprietaryTrial) {
            gtd.setPrimaryPurpose(getValue(studyProtocolDto.getPrimaryPurposeCode(), INFORMATION_NOT_PROVIDED));
            gtd.setPrimaryPurposeComment(getValue(studyProtocolDto.getPrimaryPurposeOtherText(), null));
            gtd.setPhase(getValue(studyProtocolDto.getPhaseCode(), INFORMATION_NOT_PROVIDED));
            gtd.setPhaseComment(getValue(studyProtocolDto.getPhaseOtherText(), null));
            InterventionalStudyProtocolDTO ispDTO = studyProtocolService
                    .getInterventionalStudyProtocol(studyProtocolDtoIdentifier);
            boolean interventionalType = ispDTO != null ? true : false;
            gtd.setType(interventionalType ? TYPE_INTERVENTIONAL : TYPE_OBSERVATIONAL);
        } else {
            gtd.setBriefTitle(getValue(studyProtocolDto.getPublicTitle(), INFORMATION_NOT_PROVIDED));
            gtd.setAcronym(getValue(studyProtocolDto.getAcronym(), INFORMATION_NOT_PROVIDED));
            gtd.setBriefSummary(getValue(studyProtocolDto.getPublicDescription(), INFORMATION_NOT_PROVIDED));
            gtd.setDetailedDescription(getValue(studyProtocolDto.getScientificDescription(), INFORMATION_NOT_PROVIDED));
            gtd.setKeywords(getValue(studyProtocolDto.getKeywordText(), INFORMATION_NOT_PROVIDED));
            if (studyProtocolDto.getAccrualReportingMethodCode() != null) {
                gtd.setReportingDatasetMethod(getValue(studyProtocolDto.getAccrualReportingMethodCode()
                        .getDisplayName(), INFORMATION_NOT_PROVIDED));
            }
            gtd.setSponsor(getGtdSponsorOrLeadOrganization(StudySiteFunctionalCode.SPONSOR, INFORMATION_NOT_PROVIDED));
            String leadOrganization = getGtdSponsorOrLeadOrganization(StudySiteFunctionalCode.LEAD_ORGANIZATION,
                    INFORMATION_NOT_PROVIDED);
            gtd.setLeadOrganization(leadOrganization);
            // PI
            StudyContactDTO scDto = new StudyContactDTO();
            scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
            List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolDtoIdentifier, scDto);
            if (!scDtos.isEmpty()) {
                Person leadPi = correlationUtils.getPAPersonByIi(scDtos.get(0).getClinicalResearchStaffIi());
                gtd.setPi(leadPi.getFullName() + AFFILIATED_WITH + leadOrganization);
                // Overall Official
                StringBuilder overallOffBuilder = new StringBuilder();
                overallOffBuilder.append(leadPi.getFullName()).append(AFFILIATED_WITH).append(leadOrganization).append(
                        IN_THE_ROLE_OF).append(ROLE_PI);
                gtd.setOverallOfficial(overallOffBuilder.toString());
                // Central Contact
                gtd.setCentralContact(getCentralContactDetails());
                // Responsible Party
                gtd.setResponsibleParty(getResponsiblePartyDetails());
            }
        }

        tsrReportGenerator.setGeneralTrialDetails(gtd);
    }

    private String getCentralContactDetails() throws PAException, NullifiedRoleException {
        String retVal = null;
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolDtoIdentifier, scDto);
        if (!scDtos.isEmpty()) {
            String centralContactName = "";

            StudyContactDTO cc = scDtos.get(0);
            if (cc.getClinicalResearchStaffIi() != null) {
                centralContactName = correlationUtils.getPAPersonByIi(cc.getClinicalResearchStaffIi()).getFullName();
            } else if (cc.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto = correlationUtils.getContactByPAOrganizationalContactId((Long.valueOf(cc
                        .getOrganizationalContactIi().getExtension())));
                centralContactName = paCDto.getTitle();
            }
            String email = PAUtil.getEmail(cc.getTelecomAddresses());
            String phone = PAUtil.getPhone(cc.getTelecomAddresses());
            String extn = PAUtil.getPhoneExtension(cc.getTelecomAddresses());

            StringBuilder builder = new StringBuilder();
            builder.append(centralContactName).append(", email: ").append(email).append(", phone: ").append(phone)
                    .append(StringUtils.isNotEmpty(extn) ? ", ext: " + extn : BLANK);
            retVal = builder.toString();
        }
        return retVal;
    }

    private String getResponsiblePartyDetails() throws PAException, NullifiedRoleException {
        String retVal = null;
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolDtoIdentifier, scDto);
        Person rp = null;
        String resPartyContactName = null;
        DSet<Tel> dset = null;
        Organization sponsorResponsible = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            scDto = scDtos.get(0);
            rp = correlationUtils.getPAPersonByIi(scDto.getClinicalResearchStaffIi());
            dset = scDto.getTelecomAddresses();
            resPartyContactName = rp.getFullName();
            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolDtoIdentifier, spartDTO);
            for (StudySiteDTO spart : sParts) {
                sponsorResponsible = correlationUtils.getPAOrganizationByIi(spart.getResearchOrganizationIi());
            }
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spDtos = studySiteContactService.getByStudyProtocol(studyProtocolDtoIdentifier,
                    spart);
            if (spDtos != null && !spDtos.isEmpty()) {
                PAContactDTO paCDto = correlationUtils.getContactByPAOrganizationalContactId((Long.valueOf(spDtos
                        .get(0).getOrganizationalContactIi().getExtension())));
                resPartyContactName = paCDto.getResponsiblePartyContactName();
                dset = spDtos.get(0).getTelecomAddresses();

                StudySiteDTO spDto = new StudySiteDTO();
                spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
                List<StudySiteDTO> spartDtos = studySiteService.getByStudyProtocol(studyProtocolDtoIdentifier, spDto);
                if (spartDtos != null && !spartDtos.isEmpty()) {
                    spDto = spartDtos.get(0);
                    sponsorResponsible = new CorrelationUtils()
                            .getPAOrganizationByIi(spDto.getResearchOrganizationIi());
                }
            }
        }
        String email = PAUtil.getEmail(dset);
        String phone = PAUtil.getPhone(dset);
        String extn = PAUtil.getPhoneExtension(dset);

        StringBuilder builder = new StringBuilder();
        builder.append("Name/Official Title: ").append(resPartyContactName).append(", Organization: ").append(
                sponsorResponsible.getName()).append(", email: ").append(email).append(", phone: ").append(phone)
                .append(StringUtils.isNotEmpty(extn) ? ", ext: " + extn : BLANK);
        retVal = builder.toString();

        return retVal;
    }

    private void setStatusDatesDetails() throws PAException {
        TSRReportStatusDate statusDate = new TSRReportStatusDate();
        StudyOverallStatusDTO statusDateDto = studyOverallStatusService
                .getCurrentByStudyProtocol(studyProtocolDtoIdentifier);
        statusDate.setCurrentTrialStatus(getValue(statusDateDto.getStatusCode()) + AS_OF
                + getNormalizedDateString(statusDateDto.getStatusDate()));
        statusDate.setReasonText(getValue(statusDateDto.getReasonText(), null));
        statusDate.setTrialStartDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                studyProtocolDto.getStartDate()).toString())
                + "-" + getValue(studyProtocolDto.getStartDateTypeCode()));
        statusDate.setPrimaryCompletionDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                studyProtocolDto.getPrimaryCompletionDate()).toString())
                + "-" + getValue(studyProtocolDto.getPrimaryCompletionDateTypeCode()));
        tsrReportGenerator.setStatusDate(statusDate);
    }

    private void setRegulatoryInformation() throws PAException {
        TSRReportRegulatoryInformation regInfo = new TSRReportRegulatoryInformation();
        StudyRegulatoryAuthorityDTO sraDTO = studyRegulatoryAuthorityService
                .getCurrentByStudyProtocol(studyProtocolDtoIdentifier);
        if (sraDTO != null) {
            String data = INFORMATION_NOT_PROVIDED;
            RegulatoryAuthority ra = regulatoryInformationService.get(Long.valueOf(sraDTO
                    .getRegulatoryAuthorityIdentifier().getExtension()));

            Country country = regulatoryInformationService.getRegulatoryAuthorityCountry(Long.valueOf(sraDTO
                    .getRegulatoryAuthorityIdentifier().getExtension()));
            if (country != null && ra != null) {
                data = country.getName() + ": " + ra.getAuthorityName();
            } else if (country != null) {
                data = country.getName();
            } else if (ra != null) {
                data = ra.getAuthorityName();
            }
            regInfo.setTrialOversightAuthority(data);
        }
        regInfo.setDmcAppointed(getValue(studyProtocolDto.getDataMonitoringCommitteeAppointedIndicator(),
                INFORMATION_NOT_PROVIDED));
        regInfo.setFdaRegulatedIntervention(getValue(studyProtocolDto.getFdaRegulatedIndicator(),
                INFORMATION_NOT_PROVIDED));
        regInfo.setSection801(getValue(studyProtocolDto.getSection801Indicator(), INFORMATION_NOT_PROVIDED));
        List<StudyIndldeDTO> indIde = studyIndldeService.getByStudyProtocol(studyProtocolDto.getIdentifier());
        if (indIde != null && (!indIde.isEmpty())) {
            regInfo.setIndIdeStudy(YES);
        } else {
            regInfo.setIndIdeStudy(NO);
        }
        regInfo.setDelayedPosting(getValue(studyProtocolDto.getDelayedpostingIndicator(), INFORMATION_NOT_PROVIDED));
        tsrReportGenerator.setRegulatoryInformation(regInfo);
    }

    private void setHumanSubjectSafety() throws PAException {
        TSRReportHumanSubjectSafety hss = new TSRReportHumanSubjectSafety();

        Boolean b = BlConverter.covertToBoolean(studyProtocolDto.getReviewBoardApprovalRequiredIndicator());
        if (b != null && b) {
            List<StudySiteDTO> partList = studySiteService.getByStudyProtocol(studyProtocolDtoIdentifier);
            for (StudySiteDTO part : partList) {
                if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        part.getReviewBoardApprovalStatusCode().getCode())
                        || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                                part.getReviewBoardApprovalStatusCode().getCode())
                        || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().equals(
                                part.getReviewBoardApprovalStatusCode().getCode())
                        || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().equals(
                                part.getReviewBoardApprovalStatusCode().getCode())) {
                    hss.setBoardApprovalStatus(getValue(part.getReviewBoardApprovalStatusCode(),
                            INFORMATION_NOT_PROVIDED));
                    hss.setBoardApprovalNumber(getValue(part.getReviewBoardApprovalNumber(), INFORMATION_NOT_PROVIDED));
                    Organization paOrg = correlationUtils.getPAOrganizationByIi(part.getOversightCommitteeIi());
                    if (paOrg != null) {
                        OrganizationDTO poOrg = null;
                        try {
                            poOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                                    IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
                        } catch (NullifiedEntityException e) {
                            throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier(), e);
                        }
                        String email = null;
                        String phone = null;
                        String extn = null;
                        Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                        for (Object tel : telList) {
                            if (tel instanceof TelPhone) {
                                phone = ((TelPhone) tel).getValue().getSchemeSpecificPart();
                                extn = PAUtil.getPhoneExtn(phone);
                            } else if (tel instanceof TelEmail) {
                                email = ((TelEmail) tel).getValue().getSchemeSpecificPart();
                            }
                        }
                        hss.setBoard(getHSSBoardNameAndAddress(paOrg.getName(), AddressConverterUtil
                                .convertToAddress(poOrg.getPostalAddress()), email, phone, extn));
                        hss.setAffiliation(getValue(part.getReviewBoardOrganizationalAffiliation(), null));
                    }
                }
            }
        }
        tsrReportGenerator.setHumanSubjectSafety(hss);
    }

    private String getHSSBoardNameAndAddress(String orgName, String fullAddress,
            String email, String phone, String extn) {
        StringBuilder retVal = new StringBuilder();
        retVal.append(StringUtils.isNotEmpty(orgName) ? orgName : "").append(
                StringUtils.isNotEmpty(orgName) && StringUtils.isNotEmpty(fullAddress) ? ", " : "").append(
                StringUtils.isNotEmpty(fullAddress) ? fullAddress : "");
        if (StringUtils.isNotEmpty(phone)) {
            retVal.append(", phone: ").append(phone).append(StringUtils.isNotEmpty(extn) ? ", extn: " + extn : BLANK);
        }
        if (StringUtils.isNotEmpty(email)) {
            retVal.append(", email: ").append(email);
        }
        if (StringUtils.isEmpty(retVal.toString())) {
            return null;
        }

        return retVal.toString();
    }

    private void setIndIdes() throws PAException {
        List<TSRReportIndIde> indIdes = new ArrayList<TSRReportIndIde>();

        List<StudyIndldeDTO> indides = studyIndldeService.getByStudyProtocol(studyProtocolDtoIdentifier);
        for (StudyIndldeDTO indDto : indides) {
            TSRReportIndIde indIde = new TSRReportIndIde();
            indIde.setType(getValue(indDto.getIndldeTypeCode()));
            indIde.setGrantor(getValue(indDto.getGrantorCode()));
            indIde.setNumber(getValue(indDto.getIndldeNumber()));
            indIde.setHolderType(getValue(indDto.getHolderTypeCode()));
            if (indDto.getHolderTypeCode() != null
                    && indDto.getHolderTypeCode().getCode().equals(HolderTypeCode.NIH.getCode())) {
                indIde.setHolder(getValue(indDto.getNihInstHolderCode()));
            } else if (indDto.getHolderTypeCode() != null
                    && indDto.getHolderTypeCode().getCode().equals(HolderTypeCode.NCI.getCode())) {
                indIde.setHolder(getValue(indDto.getNciDivProgHolderCode()));
            }
            indIde.setExpandedAccess(getValue(indDto.getExpandedAccessIndicator()));
            indIde.setExpandedAccessStatus(getValue(indDto.getExpandedAccessStatusCode()));
            indIdes.add(indIde);
        }
        tsrReportGenerator.setIndIdes(indIdes);
    }

    private void setNihGrants() throws PAException {
        List<TSRReportNihGrant> nihGrants = new ArrayList<TSRReportNihGrant>();
        List<StudyResourcingDTO> funds = studyResourcingService
                .getstudyResourceByStudyProtocol(studyProtocolDtoIdentifier);
        for (StudyResourcingDTO fund : funds) {
            TSRReportNihGrant nihGrant = new TSRReportNihGrant();
            nihGrant.setFundingMechanism(getValue(fund.getFundingMechanismCode()));
            nihGrant.setNihInstitutionCode(getValue(fund.getNihInstitutionCode()));
            nihGrant.setSerialNumber(getValue(fund.getSerialNumber()));
            nihGrant.setProgramCode(getValue(fund.getNciDivisionProgramCode()));
            nihGrants.add(nihGrant);
        }
        tsrReportGenerator.setNihGrants(nihGrants);
    }

    private void setSummary4Information() throws PAException {
        TSRReportSummary4Information sum4Info = new TSRReportSummary4Information();
        StudyResourcingDTO studyResourcingDTO = studyResourcingService
                .getsummary4ReportedResource(studyProtocolDtoIdentifier);
        if (studyResourcingDTO != null) {
            sum4Info.setFundingCategory(getValue(studyResourcingDTO.getTypeCode(), INFORMATION_NOT_PROVIDED));
            Organization org = null;
            if (studyResourcingDTO.getOrganizationIdentifier() != null
                    && studyResourcingDTO.getOrganizationIdentifier().getExtension() != null) {
                Organization o = new Organization();
                o.setId(Long.valueOf(studyResourcingDTO.getOrganizationIdentifier().getExtension()));
                org = paOrganizationService.getOrganizationByIndetifers(o);
            }
            if (org != null) {
                sum4Info.setFundingSponsor(StringUtils.isNotEmpty(org.getName()) ? org.getName()
                        : INFORMATION_NOT_PROVIDED);
            }
            if (studyProtocolDto.getProgramCodeText().getValue() != null) {
                sum4Info.setProgramCode(getValue(studyProtocolDto.getProgramCodeText(), INFORMATION_NOT_PROVIDED));
            }
        }
        tsrReportGenerator.setSummary4Information(sum4Info);
    }

    private void setCollaborators() throws PAException {
        List<TSRReportCollaborator> lstCollabs = new ArrayList<TSRReportCollaborator>();

        ArrayList<StudySiteDTO> criteriaList = new ArrayList<StudySiteDTO>();
        for (StudySiteFunctionalCode cd : StudySiteFunctionalCode.values()) {
            if (cd.isCollaboratorCode()) {
                StudySiteDTO searchCode = new StudySiteDTO();
                searchCode.setFunctionalCode(CdConverter.convertToCd(cd));
                criteriaList.add(searchCode);
            }
        }
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolIi, criteriaList);
        for (StudySiteDTO sp : spList) {
            Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getResearchOrganizationIi());
            TSRReportCollaborator collab = new TSRReportCollaborator();
            collab.setName(orgBo.getName());
            collab.setRole(getValue(sp.getFunctionalCode()));
            lstCollabs.add(collab);
        }
        tsrReportGenerator.setCollaborators(lstCollabs);
    }

    private void setDiseases() throws PAException {
        List<TSRReportDiseaseCondition> diseaseConditions = new ArrayList<TSRReportDiseaseCondition>();
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);
        if (sdDtos != null) {
            List<DiseaseDTO> diseases = new ArrayList<DiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
                diseases.add(diseaseService.get(sdDto.getDiseaseIdentifier()));
            }
            Collections.sort(diseases, new Comparator<DiseaseDTO>() {
                public int compare(DiseaseDTO o1, DiseaseDTO o2) {
                    return o1.getPreferredName().getValue().compareToIgnoreCase(o2.getPreferredName().getValue());
                }
            });
            for (DiseaseDTO d : diseases) {
                TSRReportDiseaseCondition diseaseCondition = new TSRReportDiseaseCondition();
                diseaseCondition.setName(getValue(d.getPreferredName()));
                diseaseConditions.add(diseaseCondition);
            }
        }
        tsrReportGenerator.setDiseaseConditions(diseaseConditions);
    }

    private void setTrialDesign() throws PAException {
        TSRReportTrialDesign trialDesign = new TSRReportTrialDesign();
        InterventionalStudyProtocolDTO ispDTO = studyProtocolService
                .getInterventionalStudyProtocol(studyProtocolDtoIdentifier);
        boolean interventionalType = ispDTO != null ? true : false;
        trialDesign.setType(interventionalType ? TYPE_INTERVENTIONAL : TYPE_OBSERVATIONAL);
        if (ispDTO != null) {
            trialDesign.setPrimaryPurpose(getValue(ispDTO.getPrimaryPurposeCode(), INFORMATION_NOT_PROVIDED));
            trialDesign
                    .setPrimaryPurposeComment(getValue(ispDTO.getPrimaryPurposeOtherText(), INFORMATION_NOT_PROVIDED));
            trialDesign.setPhase(getValue(ispDTO.getPhaseCode(), INFORMATION_NOT_PROVIDED));
            trialDesign.setPhaseComment(getValue(ispDTO.getPhaseCode(), INFORMATION_NOT_PROVIDED));
            trialDesign.setInterventionModel(getValue(ispDTO.getDesignConfigurationCode(), INFORMATION_NOT_PROVIDED));
            trialDesign.setNumberOfArms(getValue(ispDTO.getNumberOfInterventionGroups(), INFORMATION_NOT_PROVIDED));
            trialDesign.setMasking(getValue(ispDTO.getBlindingSchemaCode()));
            if (ispDTO.getBlindingSchemaCode() != null) {
                trialDesign.setMaskedRoles(getTrialDesignMaskedRoles(ispDTO));
            }
            trialDesign.setAllocation(getValue(ispDTO.getAllocationCode()));
            trialDesign.setStudyClassification(getValue(ispDTO.getStudyClassificationCode()));
            trialDesign.setTargetEnrollment(getValue(ispDTO.getTargetAccrualNumber().getLow()));
        }
        tsrReportGenerator.setTrialDesign(trialDesign);
    }

    private String getTrialDesignMaskedRoles(InterventionalStudyProtocolDTO dto) {
        String maskedRoles = null;
        String subject = NO;
        String investigator = NO;
        String caregiver = NO;
        String outcomesAssessor = NO;
        if (dto.getBlindedRoleCode() != null) {
            List<Cd> cds = DSetConverter.convertDsetToCdList(dto.getBlindedRoleCode());
            if (cds != null) {
                for (Cd cd : cds) {
                    if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                        caregiver = YES;
                    } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                        investigator = YES;
                    } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                        outcomesAssessor = YES;
                    } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                        subject = YES;
                    }
                }
            }
            maskedRoles = "Subject: " + subject + "; Investigator: " + investigator + "; Caregiver: " + caregiver
                    + "; Outcomes Assessor: " + outcomesAssessor;
        }
        return maskedRoles;
    }

    private void setEligibilityCriteria() throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
                .getPlannedEligibilityCriterionByStudyProtocol(studyProtocolDtoIdentifier);

        if (paECs != null && !paECs.isEmpty()) {
            TSRReportEligibilityCriteria eligibilityCriteria = new TSRReportEligibilityCriteria();

            eligibilityCriteria.setAcceptsHealthyVolunteers(getValue(studyProtocolDto
                    .getAcceptHealthyVolunteersIndicator(), INFORMATION_NOT_PROVIDED));
            Collections.sort(paECs, new Comparator<PlannedEligibilityCriterionDTO>() {
                public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
                    return (!PAUtil.isIntNull(o1.getDisplayOrder()) && !PAUtil.isIntNull(o2.getDisplayOrder())) ? o1
                            .getDisplayOrder().getValue().compareTo(o2.getDisplayOrder().getValue()) : 0;
                }
            });

            for (PlannedEligibilityCriterionDTO paEC : paECs) {
                String criterionName = StConverter.convertToString(paEC.getCriterionName());
                String descriptionText = StConverter.convertToString(paEC.getTextDescription());
                Boolean inclusionCriteriaIndicator = paEC.getInclusionIndicator() != null
                        && paEC.getInclusionIndicator().getValue() != null ? paEC.getInclusionIndicator().getValue()
                        : null;
                Ivl<Pq> pq = paEC.getValue();

                if (criterionName != null && criterionName.equalsIgnoreCase(CRITERION_GENDER)
                        && paEC.getEligibleGenderCode() != null) {
                    eligibilityCriteria.setGender(getValue(paEC.getEligibleGenderCode(), INFORMATION_NOT_PROVIDED));
                } else if (criterionName != null && criterionName.equalsIgnoreCase(CRITERION_AGE)) {
                    if (pq.getLow() != null && pq.getLow().getValue() != null) {
                        eligibilityCriteria
                                .setMinimumAge(pq.getLow().getValue().intValue() == MIN_AGE ? INFORMATION_NOT_PROVIDED
                                        : PAUtil.getAge(pq.getLow().getValue()) + SPACE + pq.getLow().getUnit());
                    }
                    if (pq.getHigh() != null && pq.getHigh().getValue() != null) {
                        eligibilityCriteria
                                .setMaximumAge(pq.getHigh().getValue().intValue() == MAX_AGE ? INFORMATION_NOT_PROVIDED
                                        : PAUtil.getAge(pq.getHigh().getValue()) + SPACE + pq.getHigh().getUnit());
                    }
                } else {
                    String criteriaText = null;
                    if (StringUtils.isNotEmpty(descriptionText)) {
                        criteriaText = descriptionText;
                    } else {
                        criteriaText = paEC.getCriterionName() + SPACE + getValue(paEC.getOperator()) + SPACE
                                + pq.getLow().getValue() + SPACE + pq.getLow().getUnit();
                    }

                    if (inclusionCriteriaIndicator != null) { // Inclusion OR Exclusion Criteria
                        if (inclusionCriteriaIndicator.booleanValue()) { // Inclusion criteria
                            eligibilityCriteria.getInclusionCriteria().add(criteriaText);
                        } else { // Exclusion Criteria
                            eligibilityCriteria.getExclusionCriteria().add(criteriaText);
                        }
                    } else { // Other Criteria
                        eligibilityCriteria.getOtherCriteria().add(criteriaText);
                    }
                }
            }
            tsrReportGenerator.setEligibilityCriteria(eligibilityCriteria);
        }
    }

    private void setArmGroups() throws PAException {
        List<TSRReportArmGroup> armGroups = new ArrayList<TSRReportArmGroup>();
        List<ArmDTO> arms = armService.getByStudyProtocol(studyProtocolDtoIdentifier);
        for (ArmDTO armDto : arms) {
            TSRReportArmGroup armGroup = new TSRReportArmGroup();
            armGroup.setType(getValue(armDto.getName()));
            armGroup.setLabel(getValue(armDto.getTypeCode()));
            armGroup.setDescription(getValue(armDto.getDescriptionText()));
            List<PlannedActivityDTO> paList = plannedActivityService.getByArm(armDto.getIdentifier());
            for (PlannedActivityDTO paDto : paList) {
                armGroup.getInterventions().add(getIntervention(paDto));
            }
            armGroups.add(armGroup);
        }
        tsrReportGenerator.setArmGroups(armGroups);
    }

    private void setPrimaryAndSecondaryOutcomeMeasures() throws PAException {
        List<StudyOutcomeMeasureDTO> somDtos = studyOutcomeMeasureService
                .getByStudyProtocol(studyProtocolDtoIdentifier);
        if (!somDtos.isEmpty()) {
            List<TSRReportOutcomeMeasure> primaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
            List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
            for (StudyOutcomeMeasureDTO somDto : somDtos) {
                TSRReportOutcomeMeasure outcomeMeasure = new TSRReportOutcomeMeasure();
                outcomeMeasure.setTitle(StConverter.convertToString(somDto.getName()));
                outcomeMeasure.setTimeFrame(StConverter.convertToString(somDto.getTimeFrame()));
                outcomeMeasure.setSafetyIssue(getValue(somDto.getSafetyIndicator(), INFORMATION_NOT_PROVIDED));

                if (somDto.getPrimaryIndicator().getValue().booleanValue()) {
                    primaryOutcomeMeasures.add(outcomeMeasure);
                } else {
                    secondaryOutcomeMeasures.add(outcomeMeasure);
                }
            }
            tsrReportGenerator.setPrimaryOutcomeMeasures(primaryOutcomeMeasures);
            tsrReportGenerator.setSecondaryOutcomeMeasures(secondaryOutcomeMeasures);
        }
    }

    private void setSubGroupStratificationCriteria() throws PAException {
        List<StratumGroupDTO> stratumGrpDtos = stratumGroupService.getByStudyProtocol(studyProtocolDtoIdentifier);
        if (!stratumGrpDtos.isEmpty()) {
            List<TSRReportSubGroupStratificationCriteria> sgsCriterias =
                new ArrayList<TSRReportSubGroupStratificationCriteria>();
            for (StratumGroupDTO sgDto : stratumGrpDtos) {
                TSRReportSubGroupStratificationCriteria sgsCrit = new TSRReportSubGroupStratificationCriteria();
                sgsCrit.setLabel(getValue(sgDto.getGroupNumberText(), INFORMATION_NOT_PROVIDED));
                sgsCrit.setDescription(getValue(sgDto.getDescription(), INFORMATION_NOT_PROVIDED));
                sgsCriterias.add(sgsCrit);
            }
            tsrReportGenerator.setSgsCriterias(sgsCriterias);
        }
    }

    private void setParticipatingSites() throws PAException, NullifiedRoleException {
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(studyProtocolDtoIdentifier, srDTO);
        if (!spList.isEmpty()) {
            List<TSRReportParticipatingSite> participatingSites = new ArrayList<TSRReportParticipatingSite>();
            for (StudySiteDTO sp : spList) {
                TSRReportParticipatingSite participatingSite = new TSRReportParticipatingSite();
                StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService
                        .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
                Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());
                participatingSite.setFacility(getFacility(orgBo.getName(), getLocation(orgBo)));
                String recruitmentStatus = getValue(ssas.getStatusCode()) + " as of "
                        + getNormalizedDateString(ssas.getStatusDate());
                participatingSite.setRecruitmentStatus(recruitmentStatus);
                if (isProprietaryTrial) {
                    List<StudySiteContactDTO> spcDTOs = studySiteContactService.getByStudySite(sp.getIdentifier());
                    for (StudySiteContactDTO spcDto : spcDTOs) {
                        if (StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode().equalsIgnoreCase(
                                spcDto.getRoleCode().getCode())
                                || StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode().equalsIgnoreCase(
                                        spcDto.getRoleCode().getCode())) {
                            Person p = correlationUtils.getPAPersonByIi(spcDto.getClinicalResearchStaffIi());
                            participatingSite.getInvestigators().add(
                                    new TSRReportInvestigator(p.getFirstName(), p.getMiddleName(), p.getLastName(),
                                            ROLE_PI));
                        }
                    }
                    participatingSite.setLocalTrialIdentifier(getValue(sp.getLocalStudyProtocolIdentifier(),
                            INFORMATION_NOT_PROVIDED));
                    participatingSite.setOpenForAccrualDate(getValue(IvlConverter.convertTs().convertLowToString(
                            sp.getAccrualDateRange()), INFORMATION_NOT_PROVIDED));
                    participatingSite.setClosedForAccrualDate(getValue(IvlConverter.convertTs().convertHighToString(
                            sp.getAccrualDateRange()), INFORMATION_NOT_PROVIDED));
                } else {
                    participatingSite.setTargetAccrual(getValue(sp.getTargetAccrualNumber()));
                    List<StudySiteContactDTO> spcDTOs = studySiteContactService.getByStudySite(sp.getIdentifier());
                    for (StudySiteContactDTO spcDto : spcDTOs) {
                        if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(spcDto.getRoleCode().getCode())) {
                            // Set contact info.
                            String email = PAUtil.getEmail(spcDto.getTelecomAddresses());
                            String phone = PAUtil.getPhone(spcDto.getTelecomAddresses());
                            String extn = PAUtil.getPhoneExtension(spcDto.getTelecomAddresses());
                            String contact = null;

                            if (spcDto.getClinicalResearchStaffIi() != null) {
                                Person p = correlationUtils.getPAPersonByIi(spcDto.getClinicalResearchStaffIi());
                                contact = p.getFirstName() + SPACE + p.getLastName();
                            } else if (spcDto.getOrganizationalContactIi() != null) {
                                PAContactDTO paCDto = correlationUtils.getContactByPAOrganizationalContactId((Long
                                        .valueOf(spcDto.getOrganizationalContactIi().getExtension())));
                                contact = paCDto.getTitle();
                            }
                            StringBuilder builder = new StringBuilder();
                            builder.append(contact).append(", email: ").append(email).append(", phone: ").append(phone)
                                    .append(StringUtils.isNotEmpty(extn) ? ", ext: " + extn : BLANK);
                            participatingSite.setContact(builder.toString());

                        } else {
                            // Principal Investigator
                            Person p = correlationUtils.getPAPersonByIi(spcDto.getClinicalResearchStaffIi());
                            TSRReportInvestigator pi = new TSRReportInvestigator(p.getFirstName(), p.getMiddleName(), p
                                    .getLastName(), getValue(spcDto.getRoleCode()));
                            participatingSite.getInvestigators().add(pi);
                        }
                    }
                }
                participatingSites.add(participatingSite);
            }
            tsrReportGenerator.setParticipatingSites(participatingSites);
        }
    }

    private String getFacility(String orgName, String location) {
        String retVal = INFORMATION_NOT_PROVIDED;
        if (orgName != null && location != null) {
            retVal = orgName + ", " + location;
        } else if (orgName != null) {
            retVal = orgName;
        } else if (location != null) {
            retVal = location;
        }
        return retVal;
    }

    private String getLocation(Organization orgBo) {
        String city = orgBo.getCity() != null ? orgBo.getCity() : " ";
        String state = orgBo.getState() != null ? orgBo.getState() : " ";
        String pc = orgBo.getPostalCode() != null ? orgBo.getPostalCode() : " ";
        String country = orgBo.getCountryName() != null ? orgBo.getCountryName() : " ";
        return city + ", " + state + " " + pc + " " + country;
    }

    private String getInterventionAltNames(InterventionDTO i) throws PAException {
        List<InterventionAlternateNameDTO> ianList = interventionAlternateNameService.getByIntervention(i
                .getIdentifier());
        int cnt = 1;
        StringBuffer interventionAltName = new StringBuffer();
        List<InterventionAlternateNameDTO> interventionNames = new ArrayList<InterventionAlternateNameDTO>();

        for (InterventionAlternateNameDTO ian : ianList) {
            if (ian.getNameTypeCode().getValue() != null
                    && (ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.SYNONYM)
                            || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.ABBREVIATION)
                            || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.US_BRAND_NAME)
                            || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.FOREIGN_BRAND_NAME) || ian
                            .getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.CODE_NAME))) {

                interventionNames.add(ian);

                if (cnt++ > PAAttributeMaxLen.LEN_5) {
                    break;
                }
            }
        }
        Collections.sort(interventionNames, new Comparator<InterventionAlternateNameDTO>() {
            public int compare(InterventionAlternateNameDTO o1, InterventionAlternateNameDTO o2) {
                return o1.getName().getValue().compareToIgnoreCase(o2.getName().getValue());
            }
        });

        for (Iterator<InterventionAlternateNameDTO> iter = interventionNames.iterator(); iter.hasNext();) {
            InterventionAlternateNameDTO altname = iter.next();
            interventionAltName.append(altname.getName().getValue());
            if (iter.hasNext()) {
                interventionAltName.append(", ");
            }
        }
        return interventionAltName.toString();
    }

    private String getGtdSponsorOrLeadOrganization(StudySiteFunctionalCode enumValue, String defaultValue)
            throws PAException {
        String retVal = defaultValue;
        Organization org = ocsr.getOrganizationByFunctionRole(studyProtocolDtoIdentifier, CdConverter
                .convertToCd(enumValue));
        if (org != null) {
            retVal = org.getName();
        }
        return retVal;
    }

    private String getValue(St st) {
        return getValue(st, null);
    }

    private String getValue(St st, String defaultValue) {
        return (st != null && st.getValue() != null ? st.getValue() : defaultValue);
    }

    private String getValue(Cd cd) {
        return getValue(cd, null);
    }

    private String getValue(Cd cd, String defaultValue) {
        return (cd != null ? CdConverter.convertCdToString(cd) : defaultValue);
    }

    private String getValue(String str, String defaultValue) {
        return (str != null ? str : defaultValue);
    }

    private String getValue(Bl bl) {
        return getValue(bl, null);
    }

    private String getValue(Bl bl, String defaultValue) {
        return (bl != null && bl.getValue() != null ? (bl.getValue().booleanValue() ? YES : NO) : defaultValue);
    }

    private String getValue(Int i) {
        return getValue(i, null);
    }

    private String getValue(Int i, String defaultValue) {
        return (i != null && i.getValue() != null ? i.getValue().toString() : defaultValue);
    }

    private String getNormalizedDateString(Ts ts) {
        String retVal = null;
        if (ts != null) {
            Timestamp tmStamp = TsConverter.convertToTimestamp(ts);
            if (tmStamp != null) {
                retVal = tmStamp.toString();
            }
        }
        return retVal;
    }
}
