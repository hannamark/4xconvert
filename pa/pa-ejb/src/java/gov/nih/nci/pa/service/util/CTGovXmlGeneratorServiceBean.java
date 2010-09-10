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

import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Adxp;
import gov.nih.nci.iso21090.AdxpAl;
import gov.nih.nci.iso21090.AdxpCnt;
import gov.nih.nci.iso21090.AdxpCty;
import gov.nih.nci.iso21090.AdxpSta;
import gov.nih.nci.iso21090.AdxpZip;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
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
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
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
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
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
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
* service bean for generating ct.gov.xml.
*
* @author Naveen Amiruddin
* @since 06/26/2008
* copyright NCI 2007.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CTGovXmlGeneratorServiceBean implements CTGovXmlGeneratorServiceRemote {

    @EJB
    private StudyProtocolServiceLocal studyProtocolService;
    @EJB
    private StudyOverallStatusServiceLocal studyOverallStatusService;
    @EJB
    private StudyIndldeServiceLocal studyIndldeService;
    @EJB
    private StudyDiseaseServiceLocal studyDiseaseService;
    @EJB
    private ArmServiceLocal armService;
    @EJB
    private PlannedActivityServiceLocal plannedActivityService;
    @EJB
    private StudySiteServiceLocal studySiteService;
    @EJB
    private StudySiteContactServiceLocal studySiteContactService;
    @EJB
    private StudyContactServiceLocal studyContactService;
    @EJB
    private StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;
    @EJB
    private StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService;
    @EJB
    private StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthorityService;
    @EJB
    private OrganizationCorrelationServiceRemote orgCorrelationService;
    @EJB
    private DocumentWorkflowStatusServiceLocal documentWorkflowStatusService;
    @EJB
    private RegulatoryInformationServiceRemote regulatoryInformationService;
    @EJB
    private DiseaseServiceLocal diseaseService;
    @EJB
    private InterventionServiceLocal interventionService;
    @EJB
    private InterventionAlternateNameServiceRemote interventionAlternateNameService;
    @EJB
    private RegistryUserServiceRemote registryUserService;
    @EJB
    private StudyRecruitmentStatusServiceLocal studyRecruitmentService;
    @EJB
    private StudyResourcingServiceLocal studyResourcingService;

    private CorrelationUtils corUtils = new CorrelationUtils();

    private static final Logger LOG = Logger.getLogger(CTGovXmlGeneratorServiceBean.class);
    private static final String TEXT_BLOCK = "textblock";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final int MAX_AGE = 999;
    private static final int ERROR_COUNT = 5;
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String NA = "N/A";
    private static final String TAB = "     ";
    private static final String DASH = "- ";
    private PAServiceUtils paServiceUtil = new PAServiceUtils();
    private static Map<String, String> nv = null;

    static {
        createCtGovValues();
    }

    /**
     * {@inheritDoc}
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StudyProtocolDTO spDTO = null;
        try {
            spDTO = getStudyProtocol(studyProtocolIi);
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("clinical_study");
            doc.appendChild(root);
            createIdInfo(spDTO, doc, root);
            if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
                createElement("is_fda_regulated", BlConverter.convertBLToString(spDTO.getFdaRegulatedIndicator()), doc,
                        root);
                createElement("is_section_801", BlConverter.convertBLToString(spDTO.getSection801Indicator()), doc,
                        root);
                if (BlConverter.convertToBool(spDTO.getSection801Indicator())) {
                    // device doesn't matter
                    createElement("delayed_posting", BlConverter.convertBLToString(spDTO.getDelayedpostingIndicator()),
                            doc, root);
                }
            }
            createIndInfo(spDTO, doc, root);
            createElement("brief_title", spDTO.getPublicTitle().getValue(), doc, root);
            createElement("acronym", spDTO.getAcronym().getValue(), doc, root);
            createElement("official_title", spDTO.getOfficialTitle().getValue(), doc, root);
            createSponsors(spDTO.getIdentifier(), doc, root, spDTO);
            createOversightInfo(spDTO, doc, root);
            createTextBlock("brief_summary", StringUtils.substring(StConverter.convertToString(
                    spDTO.getPublicDescription()), 0, PAAttributeMaxLen.LEN_MIN_1), doc, root);
            createCdataBlock("detailed_description", spDTO.getScientificDescription(), PAAttributeMaxLen.LEN_32000,
                    doc, root);
            createOverallStatus(spDTO, doc, root);
            if (!PAUtil.isBlNull(spDTO.getExpandedAccessIndicator())) {
                if (spDTO.getExpandedAccessIndicator().getValue()) {
                    appendElement(root, createElement("expanded_access_status", "Available", doc));
                } else {
                    appendElement(root, createElement("expanded_access_status", "No longer available", doc));
                }
            }
            appendElement(root, createElement("start_date", convertTsToYYYYMMFormart(spDTO.getStartDate()), doc));
            appendElement(root, createElement("primary_compl_date", convertTsToYYYYMMFormart(spDTO
                    .getPrimaryCompletionDate()), doc));
            appendElement(root, createElement("primary_compl_date_type", convertToCtValues(spDTO
                    .getPrimaryCompletionDateTypeCode()), doc));

            appendElement(root, createStudyDesign(spDTO, doc));
            List<StudyOutcomeMeasureDTO> somDtos = studyOutcomeMeasureService.getByStudyProtocol(spDTO.getIdentifier());
            createPrimaryOutcome(somDtos, doc, root);
            createSecondaryOutcome(somDtos, doc, root);
            createCondition(studyProtocolIi, doc, root);
            appendElement(root, createElement("enrollment", IvlConverter.convertInt().convertLowToString(
                    spDTO.getTargetAccrualNumber()), doc));
            appendElement(root, createElement("enrollment_type", "anticipated", doc));
            createArmGroup(spDTO, doc, root);
            createIntervention(spDTO.getIdentifier(), doc, root);
            createEligibility(spDTO, doc, root);
            createOverallOfficial(spDTO.getIdentifier(), doc, root);
            createOverallContact(spDTO.getIdentifier(), doc, root);
            createLocation(spDTO, doc, root);

            appendElement(root, createElement("keyword", StringUtils.substring(StConverter.convertToString(spDTO
                    .getKeywordText()), 0, PAAttributeMaxLen.KEYWORD), doc));
            Ts tsVerificationDate = spDTO.getRecordVerificationDate();
            if (PAUtil.isTsNull(tsVerificationDate))  {
                DocumentWorkflowStatusDTO dto = documentWorkflowStatusService
                        .getCurrentByStudyProtocol(studyProtocolIi);
                tsVerificationDate = TsConverter.convertToTs(IvlConverter.convertTs().convertLow(
                        dto.getStatusDateRange()));
            }
            appendElement(root, createElement("verification_date", PAUtil.convertTsToFormarttedDate(tsVerificationDate,
                    "yyyy-MM"), doc));

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            // set indentation
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (Exception e) {
            LOG.error("Error while generating CT.GOV.xml", e);
            return createErrorXml(spDTO, e);
        }
    }

    private String createErrorXml(StudyProtocolDTO spDTO, Exception e) {
        StringWriter writer = new StringWriter();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("error");
            doc.appendChild(root);
            createElement("error_description", "Unable to generate the XML", doc, root);
            if (spDTO == null) {
                createElement("study_identifier", "Unknown", doc, root);
            } else {
                createElement("study_identifier", PAUtil.getAssignedIdentifierExtension(spDTO), doc, root);
                createElement("study_title", spDTO.getOfficialTitle().getValue(), doc, root);
            }
            createElement("contact_info", "Please contact CTRP staff", doc, root);
            createElement("error_type", e.toString(), doc, root);
            StackTraceElement[] st = e.getStackTrace();
            Element errorMessages = doc.createElement("error_messages");
            for (int x = 0; x < Math.min(ERROR_COUNT, st.length); x++) {
                appendElement(errorMessages, createElement("error_message", st[x].toString(), doc));
            }
            appendElement(root, errorMessages);
            DOMSource domSource = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
        } catch (Exception e1) {
            LOG.error("Error while generating CT.GOV.xml", e1);
        }
        return writer.toString();
    }

    private void createOverallStatus(StudyProtocolDTO spDTO, final Document doc, final Element root)
    throws PAException {
        StudyOverallStatusDTO sosDTO = studyOverallStatusService.getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (sosDTO == null) {
            return;
        }
        StudyRecruitmentStatusDTO srsDto = studyRecruitmentService.getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (srsDto != null) {
            appendElement(root, createElement("overall_status", convertToCtValues(srsDto.getStatusCode()), doc));
        }

        StudyStatusCode overStatusCode = StudyStatusCode.getByCode(sosDTO.getStatusCode().getCode());

        if (StudyStatusCode.WITHDRAWN.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(overStatusCode)) {
            appendElement(root, createElement("why_stopped", StringUtils.substring(StConverter.convertToString(sosDTO
                    .getReasonText()), 0, PAAttributeMaxLen.LEN_160), doc));
        }
    }

    private void createCondition(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);

        if (CollectionUtils.isNotEmpty(sdDtos)) {
            List<DiseaseDTO> diseases = new ArrayList<DiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
                if (BlConverter.convertToBool(sdDto.getCtGovXmlIndicator())) {
                    DiseaseDTO d = diseaseService.get(sdDto.getDiseaseIdentifier());
                    diseases.add(d);
                }
            }
            Collections.sort(diseases, new Comparator<DiseaseDTO>() {
                public int compare(DiseaseDTO o1, DiseaseDTO o2) {
                    return o1.getPreferredName().getValue().compareToIgnoreCase(o2.getPreferredName().getValue());
                }

            });
            for (DiseaseDTO d : diseases) {
                appendElement(root, createElement("condition", StringUtils.substring(StConverter.convertToString(d
                        .getPreferredName()), 0, PAAttributeMaxLen.LEN_160), doc));
            }

        }
    }

    private void createOverallContact(Ii studyProtocolIi, Document doc, Element root) throws PAException,
            NullifiedRoleException {
        StudyContactDTO queryScDto = new StudyContactDTO();
        queryScDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> scDTOs = studyContactService.getByStudyProtocol(studyProtocolIi, queryScDto);
        for (StudyContactDTO scDTO : scDTOs) {
            Element overallContact = doc.createElement("overall_contact");
            if (scDTO.getClinicalResearchStaffIi() == null && scDTO.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto = corUtils.getContactByPAOrganizationalContactId((Long.valueOf(scDTO
                        .getOrganizationalContactIi().getExtension())));
                appendElement(overallContact, createElement("last_name", paCDto.getTitle(), doc));
            } else if (scDTO.getClinicalResearchStaffIi() != null) {
                Person p = corUtils.getPAPersonByIi(scDTO.getClinicalResearchStaffIi());
                appendElement(overallContact, createElement(FIRST_NAME, p.getFirstName(), doc));
                appendElement(overallContact, createElement(LAST_NAME, p.getLastName(), doc));
            }
            DSet<Tel> dset = scDTO.getTelecomAddresses();
            if (dset != null) {
                List<String> phones = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_PHONE);
                List<String> emails = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_EMAIL);
                if (CollectionUtils.isNotEmpty(phones)) {
                    appendElement(overallContact, createElement(PHONE, StringUtils.substring(phones.get(0), 0,
                            PAAttributeMaxLen.LEN_30), doc));
                }
                if (CollectionUtils.isNotEmpty(emails)) {
                    appendElement(overallContact, createElement(EMAIL, StringUtils.substring(emails.get(0), 0,
                            PAAttributeMaxLen.LEN_254), doc));
                }
            }
            if (overallContact.hasChildNodes()) {
                root.appendChild(overallContact);
            }

            break;
        }

    }

    private void createOverallOfficial(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDTOs = studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
        for (StudyContactDTO scDTO : scDTOs) {
            if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equals(scDTO.getRoleCode().getCode())) {
                Person p = corUtils.getPAPersonByIi(scDTO.getClinicalResearchStaffIi());
                Element overallofficial = doc.createElement("overall_official");
                appendElement(overallofficial, createElement(FIRST_NAME, p.getFirstName(), doc));
                appendElement(overallofficial, createElement(LAST_NAME, p.getLastName(), doc));
                appendElement(overallofficial, createElement("role", "Principal Investigator", doc));

                StudySiteDTO spartDTO = new StudySiteDTO();
                spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
                List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolIi, spartDTO);

                if (CollectionUtils.isNotEmpty(sParts)) {
                    Organization o = corUtils.getPAOrganizationByIi(sParts.get(0).getResearchOrganizationIi());
                    appendElement(overallofficial, createElement("affiliation", o.getName(), doc));
                }

                appendElement(root, overallofficial);
                break;
            }
        }
    }

    private void createOversightInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        Element overSightInfo = doc.createElement("oversight_info");
        if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            appendElement(overSightInfo, createRegulatoryAuthority(spDTO, doc));
            appendElement(overSightInfo, createElement("has_dmc", BlConverter.convertBLToString(spDTO
                    .getDataMonitoringCommitteeAppointedIndicator()), doc));
        }
        appendElement(overSightInfo, createIrbInfo(spDTO, doc));
        appendElement(root, overSightInfo);
    }

    private Element createRegulatoryAuthority(StudyProtocolDTO spDTO, Document doc) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO = studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spDTO
                .getIdentifier());
        if (sraDTO == null) {
            return null;
        }
        String data = null;
        RegulatoryAuthority ra = regulatoryInformationService.get(Long.valueOf(sraDTO
                .getRegulatoryAuthorityIdentifier().getExtension()));
        Country country = regulatoryInformationService.getRegulatoryAuthorityCountry(Long.valueOf(sraDTO
                .getRegulatoryAuthorityIdentifier().getExtension()));
        if (country != null && ra != null) {
            data = country.getName() + " : " + ra.getAuthorityName();
        } else if (country != null) {
            data = country.getName();
        } else if (ra != null) {
            data = ra.getAuthorityName();
        }
        return createElement("regulatory_authority", data, doc);

    }

    private Element createIrbInfo(StudyProtocolDTO spDTO, Document doc) throws PAException {
        Element irbInfo = doc.createElement("irb_info");

        if (!BlConverter.convertToBool(spDTO.getReviewBoardApprovalRequiredIndicator())) {
            appendElement(irbInfo, createElement("approval_status", "Not required", doc));
            return irbInfo;
        }

        List<StudySiteDTO> dtos = new ArrayList<StudySiteDTO>();
        StudySiteDTO dto = new StudySiteDTO();
        dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        dtos.add(dto);
        List<StudySiteDTO> spDTOs = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), dtos);
        for (StudySiteDTO spart : spDTOs) {
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                    spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())) {

                appendElement(irbInfo, createElement("approval_status", convertToCtValues(spart
                        .getReviewBoardApprovalStatusCode()), doc));

                appendElement(irbInfo, createElement("approval_number", StConverter.convertToString(spart
                        .getReviewBoardApprovalNumber()), doc));

                Organization paOrg = corUtils.getPAOrganizationByIi(spart.getOversightCommitteeIi());
                    OrganizationDTO poOrg = null;
                    try {
                        poOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                                IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
                    } catch (NullifiedEntityException e) {
                        throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier(), e);
                    }
                    appendElement(irbInfo, createElement("name", paOrg.getName(), doc));
                    appendElement(irbInfo, createElement("affiliation", StConverter.convertToString(spart
                            .getReviewBoardOrganizationalAffiliation()), doc));
                    TelPhone telPh = getFirstTel(poOrg.getTelecomAddress().getItem(), TelPhone.class);
                    if (telPh != null) {
                        appendElement(irbInfo, createElement(PHONE, StringUtils.substring(telPh
                                .getValue().getSchemeSpecificPart(), 0, PAAttributeMaxLen.LEN_30), doc));
                    }
                    TelEmail telEmail = getFirstTel(poOrg.getTelecomAddress().getItem(), TelEmail.class);
                    if (telEmail != null) {
                            appendElement(irbInfo, createElement(EMAIL, StringUtils.substring(telEmail
                                    .getValue().getSchemeSpecificPart(), 0, PAAttributeMaxLen.LEN_254), doc));

                        }


                    appendElement(irbInfo, createElement("full_address", convertToAddress(poOrg.getPostalAddress()),
                            doc));
                    break;
            }
        }

        return irbInfo;
    }

    @SuppressWarnings("unchecked")
    private <T extends Tel> T getFirstTel(Set<Tel> tels, Class<T> type) {
        T result = null;
        if (tels != null) {
            for (Tel tel : tels) {
                if (tel.getClass().equals(type)) {
                    result = (T) tel;
                    break;
                }
            }
        }
        return result;
    }

    private void createIdInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        Element idInfo = doc.createElement("id_info");

        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), spartDTO);
        for (StudySiteDTO spart : sParts) {
            appendElement(idInfo, createElement("org_study_id", StConverter.convertToString(spart
                    .getLocalStudyProtocolIdentifier()), doc));
            break;
        }

        Element assignedId = doc.createElement("secondary_id");
        appendElement(assignedId, createElement("id", PAUtil.getAssignedIdentifierExtension(spDTO), doc));
        appendElement(assignedId, createElement("id_type", "Registry Identifier", doc));
        appendElement(assignedId, createElement("id_domain", "CTRP (Clinical Trial Reporting Program)", doc));
        appendElement(idInfo, assignedId);

        addGrantInfo(spDTO, doc, idInfo);

        RegistryUser registryUser = registryUserService
                .getUser(StConverter.convertToString(spDTO.getUserLastCreated()));
        String prsOrgName = "replace with PRS Organization Name you log in with";
        if (StringUtils.isNotEmpty(registryUser.getPrsOrgName())) {
            prsOrgName = registryUser.getPrsOrgName();
        }
        appendElement(idInfo, createElement("org_name", prsOrgName, doc));

        appendElement(root, idInfo);

    }

    private void addGrantInfo(StudyProtocolDTO spDTO, Document doc, Element idInfoNode) throws PAException {
        List<StudyResourcingDTO> srDtos = studyResourcingService
            .getStudyResourcingByStudyProtocol(spDTO.getIdentifier());

        for (StudyResourcingDTO srDto : srDtos) {
            Element grantId = doc.createElement("secondary_id");
            StringBuilder id = new StringBuilder();
            id.append(srDto.getFundingMechanismCode().getCode());
            id.append(srDto.getNihInstitutionCode().getCode());
            id.append(srDto.getSerialNumber().getValue());
            appendElement(grantId, createElement("id", id.toString(), doc));
            appendElement(grantId, createElement("id_type", "NIH Grant Number", doc));

            appendElement(idInfoNode, grantId);
        }


    }

    private void createIndInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<StudyIndldeDTO> ideDtos = studyIndldeService.getByStudyProtocol(spDTO.getIdentifier());
        if (!getPaServiceUtil().containsNonExemptInds(ideDtos)) {
            appendElement(root, createElement("is_ind_study", NO, doc));
            return;
        }
        StudyIndldeDTO ideDTO = getFirstNonExemptInd(ideDtos);
        appendElement(root, createElement("is_ind_study", YES, doc));
        Element idInfo = doc.createElement("ind_info");
        appendElement(idInfo, createElement("ind_grantor", convertToCtValues(ideDTO.getGrantorCode()), doc));
        appendElement(idInfo, createElement("ind_number", StConverter.convertToString(ideDTO.getIndldeNumber()), doc));
        appendElement(idInfo, createElement("has_expanded_access", BlConverter.convertBLToString(ideDTO
                .getExpandedAccessIndicator()), doc));

        if (idInfo.hasChildNodes()) {
            appendElement(root, idInfo);
        }

    }

    /**
     * @param ideDtos
     * @return
     */
    private StudyIndldeDTO getFirstNonExemptInd(List<StudyIndldeDTO> studyIndldeDTOs) {
        StudyIndldeDTO nonExemptInd =  new StudyIndldeDTO();
        if (CollectionUtils.isNotEmpty(studyIndldeDTOs)) {
            for (StudyIndldeDTO dto : studyIndldeDTOs) {
                if (BooleanUtils.isFalse(BlConverter.convertToBoolean(dto.getExemptIndicator()))) {
                    nonExemptInd = dto;
                    break;
                }
            }
        }
        return nonExemptInd;
    }

    private void createEligibility(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
                .getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());

        if (CollectionUtils.isEmpty(paECs)) {
            return;
        }

        Element eligibility = doc.createElement("eligibility");
        String genderCode = null;
        BigDecimal minAge = BigDecimal.ZERO;
        String minUnit = "";
        BigDecimal maxAge = BigDecimal.ZERO;
        String maxUnit = "";
        StringBuffer incCrit = new StringBuffer();
        StringBuffer nullCrit = new StringBuffer();
        StringBuffer exCrit = new StringBuffer();
        BigDecimal value;
        String unit;
        String operator;
        // sorts the list on display order
        Collections.sort(paECs, new Comparator<PlannedEligibilityCriterionDTO>() {
            public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
                return (!PAUtil.isIntNull(o1.getDisplayOrder()) && !PAUtil.isIntNull(o2.getDisplayOrder())) ? o1
                        .getDisplayOrder().getValue().compareTo(o2.getDisplayOrder().getValue()) : 0;
            }
        });
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            String criterionName = StConverter.convertToString(paEC.getCriterionName());
            String descriptionText = StConverter.convertToString(paEC.getTextDescription());
            Boolean incIndicator = BlConverter.convertToBoolean(paEC.getInclusionIndicator());
            Ivl<Pq> pq = paEC.getValue();
            if ("GENDER".equalsIgnoreCase(criterionName)
                    && paEC.getEligibleGenderCode() != null) {
                genderCode = paEC.getEligibleGenderCode().getCode();
            } else if ("AGE".equalsIgnoreCase(criterionName)) {
                if (pq.getLow() != null) {
                    minAge = pq.getLow().getValue();
                    minUnit = pq.getLow().getUnit();
                }
                if (pq.getHigh() != null) {
                    maxAge = pq.getHigh().getValue();
                    maxUnit = pq.getHigh().getUnit();
                }
            } else if (descriptionText != null) {
                if (incIndicator == null) {
                    nullCrit.append(TAB);
                    nullCrit.append(DASH);
                    nullCrit.append(descriptionText);
                    nullCrit.append('\n');
                } else if (incIndicator) {
                    incCrit.append(TAB);
                    incCrit.append(DASH);
                    incCrit.append(descriptionText);
                    incCrit.append('\n');
                } else {
                    exCrit.append(TAB);
                    exCrit.append(DASH);
                    exCrit.append(descriptionText);
                    exCrit.append('\n');
                }
            } else {
                value = pq.getLow().getValue();
                unit = pq.getLow().getUnit();
                operator = (!PAUtil.isStNull(paEC.getOperator())) ? paEC.getOperator().getValue() : "";
                if (incIndicator == null) {
                    nullCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ')
                            .append(operator).append(' ').append(unit).append('\n');
                } else if (incIndicator) {
                    incCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ')
                            .append(operator).append(' ').append(unit).append('\n');
                } else {
                    exCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ').append(
                            operator).append(' ').append(unit).append('\n');
                }
            }

        } // for loop
        StringBuffer data = new StringBuffer();
        data.append('\n');
        if (nullCrit.length() > 1) {
            data.append("Criteria: \n" + nullCrit.toString() + "\n");
        }
        if (incCrit.length() > 1) {
            data.append("Inclusion Criteria: \n").append(incCrit).append('\n');
        }
        if (exCrit.length() > 1) {
            data.append("Exclusion Criteria: \n").append(exCrit).append('\n');
        }
        if (data.length() > 1) {
            createCdataBlock("criteria", StConverter.convertToSt(data.toString()), PAAttributeMaxLen.LEN_15000, doc,
                    eligibility);
        }
        appendElement(eligibility, createElement("healthy_volunteers", BlConverter.convertBLToString(spDTO
                .getAcceptHealthyVolunteersIndicator()), doc));
        appendElement(eligibility, createElement("gender", genderCode, doc));
        appendElement(eligibility, createElement("minimum_age", getAgeUnit(minAge, minUnit), doc));
        appendElement(eligibility, createElement("maximum_age", getAgeUnit(maxAge, maxUnit), doc));

        appendElement(root, eligibility);

    }

    private static String getAgeUnit(BigDecimal b, String unit) {
        if (b.intValue() == 0 || b.intValue() == MAX_AGE) {
            return "N/A";
        } else if (unit == null) {
            return null;
        }
        return PAUtil.getAge(b) + " " + unit.toLowerCase(Locale.US);
    }

    private void createArmGroup(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<ArmDTO> arms = armService.getByStudyProtocol(spDTO.getIdentifier());

        if (CollectionUtils.isEmpty(arms)) {
            return;
        }
        for (ArmDTO armDTO : arms) {
            Element armGroup = doc.createElement("arm_group");
            appendElement(armGroup,
                    createElement("arm_group_label", StConverter.convertToString(armDTO.getName()), doc));
            appendElement(armGroup, createElement("arm_type", convertToCtValues(armDTO.getTypeCode()), doc));
            createTextBlock("arm_group_description", StringUtils.substring(
                    StConverter.convertToString(armDTO.getDescriptionText()), 0, PAAttributeMaxLen.LEN_1000)
                    , doc, armGroup);
            if (armGroup.hasChildNodes()) {
                root.appendChild(armGroup);
            }
        }
    }

    private void createIntervention(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        List<PlannedActivityDTO> paList = plannedActivityService.getByStudyProtocol(studyProtocolIi);

        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                Element intervention = doc.createElement("intervention");
                InterventionDTO iDto = interventionService.get(pa.getInterventionIdentifier());
                if (pa.getSubcategoryCode() != null && pa.getSubcategoryCode().getCode() != null) {
                    appendElement(intervention, createElement("intervention_type", pa.getSubcategoryCode().getCode(),
                            doc));
                }
                appendElement(intervention, createElement("intervention_name", StringUtils.substring(StConverter
                        .convertToString(iDto.getName()), 0, PAAttributeMaxLen.LEN_160), doc));
                createTextBlock("intervention_description", StringUtils.substring(
                        StConverter.convertToString(pa.getTextDescription()), 0, PAAttributeMaxLen.LEN_1000), doc,
                        intervention);

                Iterator<InterventionAlternateNameDTO> ianIt = interventionAlternateNameService.getByIntervention(iDto
                        .getIdentifier()).iterator();
                List<InterventionAlternateNameDTO> interventionNames = new ArrayList<InterventionAlternateNameDTO>();
                for (int i = 0; ianIt.hasNext() && i < PAAttributeMaxLen.LEN_5; i++) {
                        InterventionAlternateNameDTO ian = ianIt.next();
                        if (isPlannedActivityTypeName(ian.getNameTypeCode())) {
                            interventionNames.add(ian);
                        }
                }

                Collections.sort(interventionNames, new Comparator<InterventionAlternateNameDTO>() {
                    public int compare(InterventionAlternateNameDTO o1, InterventionAlternateNameDTO o2) {
                        return o1.getName().getValue().compareToIgnoreCase(o2.getName().getValue());
                    }
                });

                for (InterventionAlternateNameDTO ian : interventionNames) {
                    appendElement(intervention, createElement("intervention_other_name", StringUtils.substring(
                            StConverter.convertToString(ian.getName()), 0, PAAttributeMaxLen.LEN_160), doc));
                }

                List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier());
                for (ArmDTO armDTO : armDtos) {
                    appendElement(intervention, createElement("arm_group_label", StringUtils.substring(StConverter
                            .convertToString(armDTO.getName()), 0, PAAttributeMaxLen.LEN_62), doc));
                }
                if (intervention.hasChildNodes()) {
                    root.appendChild(intervention);
                }

            }

        }
    }

    private boolean isPlannedActivityTypeName(St typeCode) {
        String value = typeCode.getValue();
        return
        value != null
        && (value.equalsIgnoreCase(PAConstants.SYNONYM)
                || value.equalsIgnoreCase(PAConstants.ABBREVIATION)
                || value.equalsIgnoreCase(PAConstants.US_BRAND_NAME)
                || value.equalsIgnoreCase(PAConstants.FOREIGN_BRAND_NAME)
                || value.equalsIgnoreCase(PAConstants.CODE_NAME));
    }


    private Element createStudyDesign(StudyProtocolDTO spDTO, Document doc) throws PAException {
        Element studyDesign = doc.createElement("study_design");

        if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Interventional", doc));
            appendElement(studyDesign, createInterventional(spDTO, doc));
        } else if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Observational", doc));
            appendElement(studyDesign, createObservational(spDTO, doc));
        }
        return studyDesign;
    }

    private Element createInterventional(StudyProtocolDTO spDTO, Document doc) throws PAException {
        InterventionalStudyProtocolDTO ispDTO = studyProtocolService.getInterventionalStudyProtocol(spDTO
                .getIdentifier());

        Element invDesign = doc.createElement("interventional_design");
        appendElement(invDesign, createElement("interventional_subtype", convertToCtValues(ispDTO
                .getPrimaryPurposeCode()), doc));
        appendElement(invDesign, createElement("phase", convertToCtValues(ispDTO.getPhaseCode()), doc));
        appendElement(invDesign, createElement("allocation", convertToCtValues(ispDTO.getAllocationCode()), doc));
        appendElement(invDesign, createElement("masking", convertToCtValues(ispDTO.getBlindingSchemaCode()), doc));
        if (ispDTO.getBlindedRoleCode() != null) {
            List<Cd> cds = DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
            if (cds != null) {
                for (Cd cd : cds) {
                    if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_caregiver", YES, doc));
                    } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_investigator", YES, doc));
                    } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_assessor", YES, doc));
                    } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                        appendElement(invDesign, createElement("masked_subject", YES, doc));
                    }
                } // for
            } // if
        } // if
        appendElement(invDesign, createElement("assignment",
                convertToCtValues(ispDTO.getDesignConfigurationCode()), doc));
        appendElement(invDesign, createElement("endpoint", convertToCtValues(
                ispDTO.getStudyClassificationCode()), doc));
        appendElement(invDesign, createElement("number_of_arms", IntConverter.convertToString(ispDTO
                .getNumberOfInterventionGroups()), doc));
        return invDesign;
    }

    private Element createObservational(StudyProtocolDTO spDTO, Document doc) throws PAException {
        ObservationalStudyProtocolDTO ospDTO = studyProtocolService
                .getObservationalStudyProtocol(spDTO.getIdentifier());

        Element obsDesign = doc.createElement("observational_design");
        appendElement(obsDesign, createElement("timing", convertToCtValues(ospDTO.getTimePerspectiveCode()), doc));
        appendElement(obsDesign, createElement("observational_study_design", convertToCtValues(ospDTO
                .getStudyModelCode()), doc));
        appendElement(obsDesign, createElement("biospecimen_retention", convertToCtValues(ospDTO
                .getBiospecimenRetentionCode()), doc));
        appendElement(obsDesign, createElement("biospecimen_description", StConverter.convertToString(ospDTO
                .getBiospecimenDescription()), doc));
        appendElement(obsDesign, createElement("number_of_groups", IntConverter.convertToString(ospDTO
                .getNumberOfGroups()), doc));
        return obsDesign;
    }

    private void createPrimaryOutcome(List<StudyOutcomeMeasureDTO> somDtos, Document doc, Element root)
            throws PAException {
        if (CollectionUtils.isEmpty(somDtos)) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element po = doc.createElement("primary_outcome");
                appendElement(po, createElement("outcome_measure", StringUtils.substring(smDTO.getName().getValue(), 0,
                        PAAttributeMaxLen.LEN_254), doc));
                if (!PAUtil.isStNull(smDTO.getDescription())) {
                    appendElement(po, createElement("outcome_description",
                            StringUtils.substring(smDTO.getDescription().getValue(), 0,
                                    PAAttributeMaxLen.LEN_600), doc));
                }
                appendElement(po, createElement("outcome_safety_issue", BlConverter.convertBLToString(smDTO
                        .getSafetyIndicator()), doc));
                appendElement(po, createElement("outcome_time_frame", StringUtils.substring(smDTO.getTimeFrame()
                        .getValue(), 0, PAAttributeMaxLen.LEN_254), doc));
                if (po.hasChildNodes()) {
                    appendElement(root, po);
                }
            }
        }
    }

    private void createSecondaryOutcome(List<StudyOutcomeMeasureDTO> somDtos, Document doc, Element root)
            throws PAException {
        if (CollectionUtils.isEmpty(somDtos)) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (!smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element om = doc.createElement("secondary_outcome");
                appendElement(om, createElement("outcome_measure", StringUtils.substring(smDTO.getName().getValue(), 0,
                        PAAttributeMaxLen.LEN_254), doc));
                appendElement(om, createElement("outcome_safety_issue", BlConverter.convertBLToString(smDTO
                        .getSafetyIndicator()), doc));
                appendElement(om, createElement("outcome_time_frame", StringUtils.substring(smDTO.getTimeFrame()
                        .getValue(), 0, PAAttributeMaxLen.LEN_254), doc));
                if (om.hasChildNodes()) {
                    appendElement(root, om);
                }
            }
        }
    }

    private void createSponsors(Ii studyProtocolIi, Document doc, Element root, StudyProtocolDTO spDTO)
            throws PAException, NullifiedRoleException {
        Element sponsors = doc.createElement("sponsors");
        if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            Element lead = createLeadSponsor(studyProtocolIi, doc);
            if (lead.hasChildNodes()) {
                appendElement(sponsors, lead);
            }
            Element rp = createResponsibleParty(studyProtocolIi, doc);
            if (rp.hasChildNodes()) {
                appendElement(sponsors, rp);
            }
        }
        Element collaborator = createCollaborator(studyProtocolIi, doc);
        if (collaborator != null && collaborator.hasChildNodes()) {
            appendElement(sponsors, collaborator);
        }
        if (sponsors.hasChildNodes()) {
            appendElement(root, sponsors);
        }

    }

    private Element createResponsibleParty(Ii studyProtocolIi, Document doc)
    throws PAException, NullifiedRoleException {
        Element responsibleParty = doc.createElement("resp_party");
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
        DSet<Tel> dset = null;
        Person person = null;
        String resPartyContactName = null;
        Organization sponsor = null;
        if (CollectionUtils.isNotEmpty(scDtos)) {
            dset = scDto.getTelecomAddresses();
            person = corUtils.getPAPersonByIi(scDtos.get(0).getClinicalResearchStaffIi());
            resPartyContactName = person.getFullName();
            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolIi, spartDTO);
            for (StudySiteDTO spart : sParts) {
                sponsor = corUtils.getPAOrganizationByIi(spart.getResearchOrganizationIi());
            }
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spcDtos = studySiteContactService.getByStudyProtocol(studyProtocolIi, spart);
            if (CollectionUtils.isNotEmpty(spcDtos)) {
                spart = spcDtos.get(0);
                dset = spart.getTelecomAddresses();
                PAContactDTO paCDto = corUtils.getContactByPAOrganizationalContactId((Long.valueOf(spart
                        .getOrganizationalContactIi().getExtension())));
                resPartyContactName = paCDto.getResponsiblePartyContactName();
            }
            StudySiteDTO spDto = new StudySiteDTO();
            spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> ssDtos = studySiteService.getByStudyProtocol(studyProtocolIi, spDto);
            if (CollectionUtils.isNotEmpty(ssDtos)) {
                spDto = ssDtos.get(0);
                sponsor = corUtils.getPAOrganizationByIi(spDto.getResearchOrganizationIi());
            }

        }
        if (resPartyContactName != null) {
            appendElement(responsibleParty, createElement("name_title", resPartyContactName, doc));
        }
        if (sponsor != null) {
            appendElement(responsibleParty, createElement("organization", sponsor.getName(), doc));
        }
        if (dset != null) {
            List<String> phones = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_PHONE);
            List<String> emails = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_EMAIL);
            if (CollectionUtils.isNotEmpty(phones)) {
                appendElement(responsibleParty, createElement(PHONE, StringUtils.substring(phones.get(0), 0,
                        PAAttributeMaxLen.LEN_30), doc));
            }
            if (CollectionUtils.isNotEmpty(emails)) {
                appendElement(responsibleParty, createElement(EMAIL, StringUtils.substring(emails.get(0), 0,
                        PAAttributeMaxLen.LEN_254), doc));
            }
        }
        return responsibleParty;
    }

    private Element createLeadSponsor(Ii studyProtocolIi, Document doc) throws PAException {
        Organization sponsor = orgCorrelationService.getOrganizationByFunctionRole(studyProtocolIi, CdConverter
                .convertToCd(StudySiteFunctionalCode.SPONSOR));

        Element lead = doc.createElement("lead_sponsor");
        appendElement(lead, createElement("agency", StringUtils.substring(sponsor.getName(), 0,
                PAAttributeMaxLen.LEN_160), doc));
        return lead;
    }

    private Element createCollaborator(Ii studyProtocolIi, Document doc) throws PAException {
        List<Organization> orgs = orgCorrelationService.getOrganizationByStudySite(Long.valueOf(studyProtocolIi
                .getExtension()), StudySiteFunctionalCode.COLLABORATORS);

        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        Element collaborator = doc.createElement("collaborator");
        appendElement(collaborator, createElement("agency", StringUtils.substring(orgs.get(0).getName(), 0,
                    PAAttributeMaxLen.LEN_160), doc));
        return collaborator;
    }

    private void createLocation(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException,
            NullifiedRoleException {

        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), srDTO);
        for (StudySiteDTO sp : spList) {
            Element location = doc.createElement("location");
            Element facility = doc.createElement("facility");
            Element address = doc.createElement("address");

            StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService
                    .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());

            Organization orgBo = corUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());

            appendElement(facility, createElement("name", orgBo.getName(), doc));
            appendElement(address, createElement("city", orgBo.getCity(), doc));
            appendElement(address, createElement("state", orgBo.getState(), doc));
            appendElement(address, createElement("zip", orgBo.getPostalCode(), doc));
            appendElement(address, createElement("country", PADomainUtils.getCountryNameUsingAlpha3Code(orgBo
                    .getCountryName()), doc));
            appendElement(facility, address);
            appendElement(location, facility);
            if (ssas != null) {
                appendElement(location, createElement("status", convertToCtValues(ssas.getStatusCode()), doc));
            }

            List<StudySiteContactDTO> spcDTOs = studySiteContactService.getByStudySite(sp.getIdentifier());
            appendElement(root, location);
            createContact(spcDTOs, location, doc);
            createInvestigators(spcDTOs, location, doc);
        }
    }

    private void createInvestigators(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException {
        for (StudySiteContactDTO spcDTO : spcDTOs) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            Person p = corUtils.getPAPersonByIi(spcDTO.getClinicalResearchStaffIi());
            Element investigator = doc.createElement("investigator");
            appendElement(investigator, createElement(FIRST_NAME, p.getFirstName(), doc));
            appendElement(investigator, createElement("middle_name", StringUtils.substring(p.getMiddleName(), 0,
                    PAAttributeMaxLen.LEN_2), doc));
            appendElement(investigator, createElement(LAST_NAME, p.getLastName(), doc));
            appendElement(investigator, createElement("role", convertToCtValues(spcDTO.getRoleCode()), doc));
            if (investigator.hasChildNodes()) {
                appendElement(location, investigator);
            }
        }
    }

    private void createContact(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException, NullifiedRoleException {
        for (StudySiteContactDTO sscDTO : spcDTOs) {
            if (!StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(sscDTO.getRoleCode().getCode())) {
                continue;
            }
            List<String> phones = DSetConverter.convertDSetToList(sscDTO.getTelecomAddresses(), "PHONE");
            List<String> emails = DSetConverter.convertDSetToList(sscDTO.getTelecomAddresses(), "EMAIL");
            Element contact = doc.createElement("contact");
            if (sscDTO.getClinicalResearchStaffIi() != null) {
                Person p = corUtils.getPAPersonByIi(sscDTO.getClinicalResearchStaffIi());
                appendElement(contact, createElement(FIRST_NAME, p.getFirstName(), doc));
                appendElement(contact, createElement("middle_name", StringUtils.substring(p.getMiddleName(), 0,
                        PAAttributeMaxLen.LEN_2), doc));
                appendElement(contact, createElement(LAST_NAME, p.getLastName(), doc));
            } else if (sscDTO.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto = corUtils.getContactByPAOrganizationalContactId((Long.valueOf(sscDTO
                        .getOrganizationalContactIi().getExtension())));
                appendElement(contact, createElement("last_name", paCDto.getTitle(), doc));
            }
            if (phones != null && !phones.isEmpty()) {
                appendElement(contact, createElement(PHONE, StringUtils.substring(phones.get(0), 0,
                        PAAttributeMaxLen.LEN_30), doc));
            }
            if (emails != null && !emails.isEmpty()) {
                appendElement(contact, createElement(EMAIL, StringUtils.substring(emails.get(0), 0,
                        PAAttributeMaxLen.LEN_254), doc));
            }
            if (contact.hasChildNodes()) {
                appendElement(location, contact);
            }
        }
    }

    private static void createCdataBlock(final String elementName ,  final St data , int maxLen ,
            Document doc , Element root) throws PAException {
        if (data != null) {
            Element element = createElement(elementName, StringUtils.left(data.getValue(), maxLen), doc);
            if (element != null) {
                root.appendChild(element);
            }
        }
    }

    private static Element createElement(String elementName , String data , Document doc) {
        if (StringUtils.isEmpty(elementName) || StringUtils.isEmpty(data)) {
            return null;
        }

        Element element = null;
        if (containsXmlChars(data)) {
            Element elementTxt = doc.createElement(TEXT_BLOCK);
            element = doc.createElement(elementName);
            Text text = doc.createCDATASection(data);
            elementTxt.appendChild(text);
            element.appendChild(elementTxt);
        } else {
            element = doc.createElement(elementName);
            Text text = doc.createTextNode(data);
            element.appendChild(text);
        }

        return element;
    }

    private static boolean containsXmlChars(String data) {
        boolean retVal = false;
        if (StringUtils.isNotEmpty(data)) {
            retVal = data.contains("<") || data.contains(">") || data.contains("&");
        }
        return retVal;
    }

//    private static void createTextBlock(final String elementName ,  final St data , int maxLen ,
//                Document doc , Element root)
//    throws PAException {
//        if (PAUtil.isStNull(data)) {
//            return;
//        }
//        Element elementTxt = doc.createElement(TEXT_BLOCK);
//        Element elementTag = doc.createElement(elementName);
//        Text text = doc
//                .createCDATASection(StringEscapeUtils
//    .unescapeHtml(StringUtils.substring(data.getValue(), 0, maxLen)));
//        elementTxt.appendChild(text);
//        elementTag.appendChild(elementTxt);
//        root.appendChild(elementTag);
//    }

    private void createTextBlock(final String elementName, final String st, Document doc, Element root)
            throws PAException {
        appendElement(doc.createElement(elementName), createElement(TEXT_BLOCK, st, doc), root);
    }

    private void createElement(final String elementName, String data, Document doc, Element root) {
        Element element = createElement(elementName, data, doc);

        if (element != null) {
            root.appendChild(element);
        }
    }

    private void appendElement(Element parent, Element child) {
        if (parent != null && child != null) {
            parent.appendChild(child);
        }
    }

    private void appendElement(Element parent, Element child, Element root) {
        if (parent != null && child != null && root != null) {
            parent.appendChild(child);
            root.appendChild(parent);
        }
    }

    private StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException("Study Protocol is not available for given id = " + studyProtocolIi.getExtension());
        }
        return spDTO;
    }

    private String convertToAddress(Ad ad) {
        if (ad == null || CollectionUtils.isEmpty(ad.getPart())) {
            return null;
        }
        List<Adxp> adxpList = ad.getPart();
        StringBuffer sb = new StringBuffer();
        for (Adxp adxp : adxpList) {

            if (adxp instanceof AdxpAl) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCty) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpSta) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpZip) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCnt) {
                sb.append(adxp.getCode());
            }
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();

    }

    private String convertTsToYYYYMMFormart(Ts isoTs) {
        String yyyyMM = "";
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return null;
        }
        String dateStr = PAUtil.normalizeDateString(ts.toString());
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        DateFormat formatter1 = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(dateStr);
            yyyyMM = formatter1.format(date);
        } catch (ParseException e) {
            // cannot happen
            yyyyMM = "";
        }
        return yyyyMM;
    }

    private static void createCtGovValues() {
        Map<String, String> nvMap = new HashMap<String, String>();
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode(), "Approved");
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode(), "Exempt");
        nvMap.put(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode(), "Not Required");
        nvMap.put(AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode(), "Randomized");
        nvMap.put(AllocationCode.NON_RANDOMIZED_TRIAL.getCode(), "Non-randomized");
        nvMap.put(BlindingSchemaCode.OPEN.getCode(), "Open Label");
        nvMap.put(BlindingSchemaCode.SINGLE_BLIND.getCode(), "Single Blind");
        nvMap.put(BlindingSchemaCode.DOUBLE_BLIND.getCode(), "Double Blind");
        nvMap.put(DesignConfigurationCode.SINGLE_GROUP.getCode(), "Single Group Assignment");
        nvMap.put(DesignConfigurationCode.PARALLEL.getCode(), "Parallel Assignment");
        nvMap.put(DesignConfigurationCode.CROSSOVER.getCode(), "Crossover Assignment");
        nvMap.put(DesignConfigurationCode.FACTORIAL.getCode(), "Factorial Assignment");
        nvMap.put(StudyClassificationCode.SAFETY.getCode(), "Safety Study");
        nvMap.put(StudyClassificationCode.EFFICACY.getCode(), "Efficacy Study");
        nvMap.put(StudyClassificationCode.SAFETY_OR_EFFICACY.getCode(), "Safety/Efficacy Study");
        nvMap.put(StudyClassificationCode.BIO_EQUIVALENCE.getCode(), "Bio-equivalence Study");
        nvMap.put(StudyClassificationCode.BIO_AVAILABILITY.getCode(), "Bio-availability Study");
        nvMap.put(StudyClassificationCode.PHARMACOKINETICS.getCode(), "Pharmacokinetics Study");
        nvMap.put(StudyClassificationCode.PHARMACODYNAMICS.getCode(), "Pharmacodynamics Study");
        nvMap.put(StudyClassificationCode.PHARMACOKINETICS_OR_DYNAMICS.getCode(), "Pharmacokinetics/dynamics Study");
        nvMap.put(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode(), "Principal Investigator");
        nvMap.put(AllocationCode.NA.getCode(), NA);
        nv = Collections.unmodifiableMap(nvMap);
    }

    private static String convertToCtValues(Cd cd) {
        if (PAUtil.isCdNull(cd)) {
            return null;
        }
        if (nv.containsKey(cd.getCode())) {
            return nv.get(cd.getCode());
        }
        return cd.getCode();
    }

    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * @param studyOverallStatusService the studyOverallStatusService to set
     */
    public void setStudyOverallStatusService(StudyOverallStatusServiceLocal studyOverallStatusService) {
        this.studyOverallStatusService = studyOverallStatusService;
    }

    /**
     * @param studyIndldeService the studyIndldeService to set
     */
    public void setStudyIndldeService(StudyIndldeServiceLocal studyIndldeService) {
        this.studyIndldeService = studyIndldeService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
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
    public void setPlannedActivityService(PlannedActivityServiceLocal plannedActivityService) {
        this.plannedActivityService = plannedActivityService;
    }

    /**
     * @param studySiteService the studySiteService to set
     */
    public void setStudySiteService(StudySiteServiceLocal studySiteService) {
        this.studySiteService = studySiteService;
    }

    /**
     * @param studySiteContactService the studySiteContactService to set
     */
    public void setStudySiteContactService(StudySiteContactServiceLocal studySiteContactService) {
        this.studySiteContactService = studySiteContactService;
    }

    /**
     * @param studyContactService the studyContactService to set
     */
    public void setStudyContactService(StudyContactServiceLocal studyContactService) {
        this.studyContactService = studyContactService;
    }

    /**
     * @param studySiteAccrualStatusService the studySiteAccrualStatusService to set
     */
    public void setStudySiteAccrualStatusService(StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService) {
        this.studySiteAccrualStatusService = studySiteAccrualStatusService;
    }

    /**
     * @param studyOutcomeMeasureService the studyOutcomeMeasureService to set
     */
    public void setStudyOutcomeMeasureService(StudyOutcomeMeasureServiceLocal studyOutcomeMeasureService) {
        this.studyOutcomeMeasureService = studyOutcomeMeasureService;
    }

    /**
     * @param studyRegulatoryAuthoritySvc the studyRegulatoryAuthorityService to set
     */
    public void setStudyRegulatoryAuthorityService(StudyRegulatoryAuthorityServiceLocal studyRegulatoryAuthoritySvc) {
        this.studyRegulatoryAuthorityService = studyRegulatoryAuthoritySvc;
    }

    /**
     * @param orgCorrelationService the orgCorrelationService to set
     */
    public void setOrgCorrelationService(OrganizationCorrelationServiceRemote orgCorrelationService) {
        this.orgCorrelationService = orgCorrelationService;
    }

    /**
     * @param documentWorkflowStatusService the documentWorkflowStatusService to set
     */
    public void setDocumentWorkflowStatusService(DocumentWorkflowStatusServiceLocal documentWorkflowStatusService) {
        this.documentWorkflowStatusService = documentWorkflowStatusService;
    }

    /**
     * @param regulatoryInformationService the regulatoryInformationService to set
     */
    public void setRegulatoryInformationService(RegulatoryInformationServiceRemote regulatoryInformationService) {
        this.regulatoryInformationService = regulatoryInformationService;
    }

    /**
     * @param diseaseService the diseaseService to set
     */
    public void setDiseaseService(DiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param interventionAltNameSvc the interventionAlternateNameService to set
     */
    public void setInterventionAlternateNameService(InterventionAlternateNameServiceRemote interventionAltNameSvc) {
        this.interventionAlternateNameService = interventionAltNameSvc;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceRemote registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param studyRecSvc the StudyRecruitmentStatusServiceLocal to set.
     */
    public void setStudyRecruitmentService(StudyRecruitmentStatusServiceLocal studyRecSvc) {
        this.studyRecruitmentService = studyRecSvc;
    }

    /**
     * @param studyResSvc the StudyResourcingServiceLocal to set.
     */
    public void setStudyResourcingService(StudyResourcingServiceLocal studyResSvc) {
        this.studyResourcingService = studyResSvc;
    }

    /**
     *
     * @param cUtils the CorrelationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtils cUtils) {
        this.corUtils = cUtils;
    }

    /**
     * @param paServiceUtil the paServiceUtil to set
     */
    public void setPaServiceUtil(PAServiceUtils paServiceUtil) {
        this.paServiceUtil = paServiceUtil;
    }

    /**
     * @return the paServiceUtil
     */
    public PAServiceUtils getPaServiceUtil() {
        return paServiceUtil;
    }

}
