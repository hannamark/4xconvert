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

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
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
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.NonInterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
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
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeMap;

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
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
* service bean for generating ct.gov.xml.
*
*/
@Stateless
@Interceptors({RemoteAuthorizationInterceptor.class, PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CTGovXmlGeneratorServiceBeanLocal extends AbstractCTGovXmlGeneratorServiceBean
    implements CTGovXmlGeneratorServiceLocal {

    private static final String PRS_PLACEHOLDER = "replace with PRS Organization Name you log in with";
    private static final Logger LOG = Logger.getLogger(CTGovXmlGeneratorServiceBeanLocal.class);
    private static final String YYYYMMDD = "yyyy-MM-dd";
    private static final String YYYYMM = "yyyy-MM";
    private static final Set<StudyStatusCode> STOPPED_STATUSES = EnumSet.of(StudyStatusCode.WITHDRAWN,
            StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL,
            StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
    
    private static final String PRINCIPAL_INVESTIGATOR = "Principal Investigator";
    private static final String SPONSOR = "Sponsor";

    static {
        createCtGovValues();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
        return generateCTGovXml(studyProtocolIi,
                new CTGovXmlGeneratorOptions[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String generateCTGovXml(Ii studyProtocolIi,
            CTGovXmlGeneratorOptions... options) throws PAException {
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
            addStudyTypeInfo(spDTO, doc, root);
            createIdInfo(spDTO, doc, root, options);
            addStudyOwnersInfo(spDTO, doc, root);
            addLeadOrgInfo(spDTO, doc, root);
            addNciSpecificInfo(spDTO, doc, root);
            addCtGovInfo(spDTO, doc, root);
            createIndInfo(spDTO, doc, root);
            XmlGenHelper.createElement("brief_title", spDTO.getPublicTitle().getValue(), doc, root);
            XmlGenHelper.createElement("acronym", spDTO.getAcronym().getValue(), doc, root);
            String officialTitle = replaceXMLCharacters(spDTO.getOfficialTitle().getValue());
            Element officialTitleElement = XmlGenHelper.createElement("official_title", officialTitle, doc);
            if (officialTitleElement != null) {
                root.appendChild(officialTitleElement);
            }
            createSponsors(spDTO.getIdentifier(), doc, root, spDTO);
            createOversightInfo(spDTO, doc, root);
            createTextBlock("brief_summary", StringUtils.substring(StConverter.convertToString(
                    spDTO.getPublicDescription()), 0, PAAttributeMaxLen.LEN_MIN_1), doc, root);
            createTextBlock("detailed_description", StringUtils.substring(StConverter.convertToString(
                    spDTO.getScientificDescription()), 0, PAAttributeMaxLen.LEN_32000), doc, root);
            createOverallStatus(spDTO, doc, root);            
            createTrialFunding(spDTO, doc, root);
            XmlGenHelper.appendElement(root, createStudyDesign(spDTO, doc));
            List<StudyOutcomeMeasureDTO> somDtos =
                getStudyOutcomeMeasureService().getByStudyProtocol(spDTO.getIdentifier());
            createPrimaryOutcome(somDtos, doc, root);
            createSecondaryOutcome(somDtos, doc, root);
            createCondition(spDTO.getIdentifier(), doc, root);
            createSubGroups(spDTO.getIdentifier(), doc, root);
            XmlGenHelper.appendElement(root,
                    XmlGenHelper.createElementWithTextblock("enrollment", IvlConverter.convertInt().convertLowToString(
                    spDTO.getTargetAccrualNumber()), doc));
            XmlGenHelper.appendElement(root, XmlGenHelper.createElementWithTextblock(
                    "enrollment_type", "anticipated", doc));
            createArmGroup(spDTO, doc, root);
            createIntervention(spDTO.getIdentifier(), doc, root);
            createEligibility(spDTO, doc, root);
            createOverallOfficial(spDTO.getIdentifier(), doc, root);
            createOverallContact(spDTO.getIdentifier(), doc, root);
            createLocation(spDTO, doc, root);
            createKeywords(spDTO, doc, root);
            Ts tsVerificationDate = spDTO.getRecordVerificationDate();
            if (ISOUtil.isTsNull(tsVerificationDate))  {
                DocumentWorkflowStatusDTO dto = getDocumentWorkflowStatusService()
                        .getCurrentByStudyProtocol(spDTO.getIdentifier());
                tsVerificationDate = TsConverter.convertToTs(IvlConverter.convertTs().convertLow(
                        dto.getStatusDateRange()));
            }
            addVerificationDate(doc, root, tsVerificationDate);

            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // set indentation, there's a bug in java 1.5 that makes the indent property not work properly.
            // setting an indent-amount fixes it.
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (Exception e) {
            LOG.error("Error while generating CT.GOV.xml", e);
            return createErrorXml(spDTO, e);
        }
    }

    private void addStudyTypeInfo(StudyProtocolDTO spDTO, Document doc,
            Element root) {
        XmlGenHelper
                .createElement(
                        "study_type",
                        spDTO instanceof NonInterventionalStudyProtocolDTO ? "Observational"
                                : PAConstants.INTERVENTIONAL, doc, root);
    }

    /**
     * @param spDTO
     * @param doc
     * @param root
     */
    private void addCtGovInfo(StudyProtocolDTO spDTO, Document doc, Element root) {
        if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            XmlGenHelper
                .createElement("is_fda_regulated",
                        BlConverter.convertBlToYesNoString(spDTO.getFdaRegulatedIndicator()), doc,
                    root);
            String section801 = BlConverter.convertBlToYesNoString(spDTO.getSection801Indicator());
            XmlGenHelper.createElement("is_section_801", section801, doc, root);
            if (BlConverter.convertToBool(spDTO.getSection801Indicator())) {
                // device doesn't matter
                String delayedPosting = BlConverter.convertBlToYesNoString(spDTO.getDelayedpostingIndicator());
                XmlGenHelper.createElement("delayed_posting", delayedPosting, doc, root);
            }
        }
    }

    /**
     * @param spDTO
     * @param doc
     * @param root
     */
    private void createKeywords(StudyProtocolDTO spDTO, Document doc,
            Element root) {
        final String keywords = StringUtils
                .defaultString(
                        StringUtils.substring(StConverter.convertToString(spDTO
                                .getKeywordText()), 0,
                                PAAttributeMaxLen.KEYWORD))
                .replace("\r\n", " ").replace("\r", " ").replace("\n", " ")
                .replaceAll("\\s+", " ").trim();
        String wrapped = WordUtils.wrap(keywords,
                PAAttributeMaxLen.PRS_KEYWORD, "\r", true);
        String[] lines = wrapped.split("\\r");
        for (String line : lines) {
            XmlGenHelper.appendElement(root, XmlGenHelper
                    .createElementWithTextblock("keyword", line, doc));
        }

    }

    /**
     * Override default addVerificationDate, but include day.
     * @param doc the xml document
     * @param root the root element
     * @param tsVerificationDate the date
     */
    protected void addVerificationDate(Document doc, Element root, Ts tsVerificationDate) {
        XmlGenHelper.appendElement(root,
                XmlGenHelper.createElementWithTextblock("verification_date",
                        PAUtil.convertTsToFormattedDate(tsVerificationDate, "yyyy-MM"), doc));
    }

    private String createErrorXml(StudyProtocolDTO spDTO, Exception e) {
        StringWriter writer = new StringWriter();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = doc.createElement("error");
            doc.appendChild(root);
            XmlGenHelper.createElement("error_description", "Unable to generate the XML", doc, root);
            String assignedIdentifierExtension = "Unknown";
            if (spDTO != null) {
                assignedIdentifierExtension = PAUtil.getAssignedIdentifierExtension(spDTO);
            }
            PdqXmlGenHelper.getFailedTrialsMap().put(assignedIdentifierExtension, e.getMessage());
            XmlGenHelper.createElement("study_identifier", assignedIdentifierExtension, doc, root);
            Element errorMessages = doc.createElement("error_messages");
            XmlGenHelper.appendElement(errorMessages,
                 XmlGenHelper.createElement("error_message", e.getMessage(), doc));
            XmlGenHelper.appendElement(root, errorMessages);
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

    /**
     * createOverallStatus.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createOverallStatus(StudyProtocolDTO spDTO, final Document doc, final Element root)
    throws PAException {
        StudyOverallStatusDTO sosDTO = getStudyOverallStatusService().getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (sosDTO != null) {

            StudyRecruitmentStatusDTO srsDto =
                getStudyRecruitmentService().getCurrentByStudyProtocol(spDTO.getIdentifier());
            if (srsDto != null) {
                XmlGenHelper.appendElement(root,
                        XmlGenHelper.createElementWithTextblock("overall_status",
                                convertToCtValues(srsDto.getStatusCode()), doc));
            }

            StudyStatusCode overStatusCode = StudyStatusCode.getByCode(sosDTO.getStatusCode().getCode());
            if (STOPPED_STATUSES.contains(overStatusCode)) {
                XmlGenHelper.appendElement(root,
                        XmlGenHelper.createElementWithTextblock("why_stopped",
                                StringUtils.substring(StConverter.convertToString(sosDTO
                        .getReasonText()), 0, PAAttributeMaxLen.LEN_160), doc));
            }

            if (!ISOUtil.isBlNull(spDTO.getExpandedAccessIndicator())) {
                if (spDTO.getExpandedAccessIndicator().getValue()) {
                    XmlGenHelper.appendElement(root,
                            XmlGenHelper.createElementWithTextblock("expanded_access_status", "Available", doc));
                } else {
                    XmlGenHelper.appendElement(root,
                            XmlGenHelper.createElementWithTextblock("expanded_access_status",
                                    "No longer available", doc));
                }
            }
        }
        XmlGenHelper.appendElement(root,
                XmlGenHelper.createElementWithTextblock("start_date",
                        convertTsToYYYYMMFormat(spDTO.getStartDate()), doc));
        XmlGenHelper.appendElement(root,
                XmlGenHelper.createElementWithTextblock("primary_compl_date", convertTsToYYYYMMFormat(spDTO
                .getPrimaryCompletionDate()), doc));
        XmlGenHelper.appendElement(root,
                XmlGenHelper.createElementWithTextblock("primary_compl_date_type", convertToCtValues(spDTO
                .getPrimaryCompletionDateTypeCode()), doc));
    }

    /**
     * createCondition.
     * @param studyProtocolIi study protocol ii.
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createCondition(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        List<StudyDiseaseDTO> sdDtos = getStudyDiseaseService().getByStudyProtocol(studyProtocolIi);

        if (CollectionUtils.isNotEmpty(sdDtos)) {
            List<PDQDiseaseDTO> diseases = new ArrayList<PDQDiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
                if (BlConverter.convertToBool(sdDto.getCtGovXmlIndicator())) {
                    PDQDiseaseDTO d = getDiseaseService().get(sdDto.getDiseaseIdentifier());
                    diseases.add(d);
                }
            }
            Collections.sort(diseases, new Comparator<PDQDiseaseDTO>() {
                @Override
                public int compare(PDQDiseaseDTO o1, PDQDiseaseDTO o2) {
                    return o1.getPreferredName().getValue().compareToIgnoreCase(o2.getPreferredName().getValue());
                }

            });
            for (PDQDiseaseDTO d : diseases) {
                XmlGenHelper.appendElement(root,
                        XmlGenHelper.createElementWithTextblock("condition",
                                StringUtils.substring(StConverter.convertToString(d
                                        .getPreferredName()), 0, PAAttributeMaxLen.LEN_160), doc));
            }

        }
    }

    /**
     * createSubGroups.
     * @param studyProtocolIi study protocol id
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createSubGroups(Ii studyProtocolIi, Document doc, Element root)
        throws PAException {
        //NOOP
    }

    private void createOverallContact(Ii studyProtocolIi, Document doc, Element root) throws PAException,
            NullifiedRoleException {
        StudyContactDTO queryScDto = new StudyContactDTO();
        queryScDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> scDTOs = getStudyContactService().getByStudyProtocol(studyProtocolIi, queryScDto);
        for (StudyContactDTO scDTO : scDTOs) {
            Element overallContact = doc.createElement("overall_contact");
            if (scDTO.getClinicalResearchStaffIi() == null && scDTO.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto = getCorUtils().getContactByPAOrganizationalContactId((Long.valueOf(scDTO
                        .getOrganizationalContactIi().getExtension())));
                XmlGenHelper.appendElement(overallContact,
                        XmlGenHelper.createElementWithTextblock("last_name", paCDto.getTitle(), doc));
            } else if (scDTO.getClinicalResearchStaffIi() != null) {
                Person p = getCorUtils().getPAPersonByIi(scDTO.getClinicalResearchStaffIi());
                XmlGenHelper.appendElement(overallContact,
                        XmlGenHelper.createElementWithTextblock(XmlGenHelper.FIRST_NAME, p.getFirstName(), doc));
                XmlGenHelper.appendElement(overallContact,
                        XmlGenHelper.createElementWithTextblock(XmlGenHelper.LAST_NAME, p.getLastName(), doc));
            }
            DSet<Tel> dset = scDTO.getTelecomAddresses();
            if (dset != null) {
                List<String> phones = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_PHONE);
                List<String> emails = DSetConverter.convertDSetToList(dset, DSetConverter.TYPE_EMAIL);
                if (CollectionUtils.isNotEmpty(phones)) {
                    XmlGenHelper.appendElement(overallContact,
                            XmlGenHelper.createElementWithTextblock(XmlGenHelper.PHONE,
                                    StringUtils.substring(phones.get(0), 0, PAAttributeMaxLen.LEN_30), doc));
                }
                if (CollectionUtils.isNotEmpty(emails)) {
                    XmlGenHelper.appendElement(overallContact,
                            XmlGenHelper.createElementWithTextblock(XmlGenHelper.EMAIL,
                                    StringUtils.substring(emails.get(0), 0, PAAttributeMaxLen.LEN_254), doc));
                }
            }
            if (overallContact.hasChildNodes()) {
                root.appendChild(overallContact);
            }

            break;
        }

    }

    /**
     * addOverallOfficialPerson.
     * @param scDTO dto.
     * @param overallofficial element
     * @param doc document
     * @throws PAException when error.
     */
    protected void addOverallOfficialPerson(StudyContactDTO scDTO, Element overallofficial, Document doc)
        throws PAException {
        Person p = getCorUtils().getPAPersonByIi(scDTO.getClinicalResearchStaffIi());

        XmlGenHelper.appendElement(overallofficial,
                XmlGenHelper.createElementWithTextblock(XmlGenHelper.FIRST_NAME, p.getFirstName(), doc));
        XmlGenHelper.appendElement(overallofficial,
                XmlGenHelper.createElementWithTextblock(XmlGenHelper.LAST_NAME, p.getLastName(), doc));
    }

    /**
     * addOverallOfficialAffiliation.
     * @param roIi research org ii
     * @param overallofficial element
     * @param doc document
     * @throws PAException when error.
     */
    protected void addOverallOfficialAffiliation(Ii roIi, Element overallofficial, Document doc) throws PAException {
        Organization o = getCorUtils().getPAOrganizationByIi(roIi);
        String data = replaceXMLCharacters(o.getName());
        XmlGenHelper.appendElement(overallofficial,
                XmlGenHelper.createElement("affiliation", data, doc));
    }

    private void createOverallOfficial(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDTOs = getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        for (StudyContactDTO scDTO : scDTOs) {
            if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equals(scDTO.getRoleCode().getCode())) {
                Element overallofficial = doc.createElement("overall_official");
                addOverallOfficialPerson(scDTO, overallofficial, doc);
                XmlGenHelper.appendElement(overallofficial,
                        XmlGenHelper.createElementWithTextblock("role", "Principal Investigator", doc));

                StudySiteDTO spartDTO = new StudySiteDTO();
                spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
                List<StudySiteDTO> sParts = getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO);

                if (CollectionUtils.isNotEmpty(sParts)) {
                    addOverallOfficialAffiliation(sParts.get(0).getResearchOrganizationIi(), overallofficial, doc);
                }

                XmlGenHelper.appendElement(root, overallofficial);
                break;
            }
        }
    }

    private void createOversightInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        Element overSightInfo = doc.createElement("oversight_info");
        if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            XmlGenHelper.appendElement(overSightInfo, createRegulatoryAuthority(spDTO, doc));
            XmlGenHelper.appendElement(overSightInfo,
                    XmlGenHelper.createElementWithTextblock("has_dmc", BlConverter.convertBlToYesNoString(spDTO
                    .getDataMonitoringCommitteeAppointedIndicator()), doc));
        }

        XmlGenHelper.appendElement(overSightInfo, createIrbInfo(spDTO, doc));
        XmlGenHelper.appendElement(root, overSightInfo);
    }

    private Element createRegulatoryAuthority(StudyProtocolDTO spDTO, Document doc) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO = getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(spDTO
                .getIdentifier());
        if (sraDTO == null) {
            return null;
        }
        String data = null;
        RegulatoryAuthority ra = getRegulatoryInformationService().get(Long.valueOf(sraDTO
                .getRegulatoryAuthorityIdentifier().getExtension()));
        Country country = getRegulatoryInformationService().getRegulatoryAuthorityCountry(Long.valueOf(sraDTO
                .getRegulatoryAuthorityIdentifier().getExtension()));
        if (country != null && ra != null) {
            data = country.getName() + " : " + ra.getAuthorityName();
        } else if (country != null) {
            data = country.getName();
        } else if (ra != null) {
            data = ra.getAuthorityName();
        }
        return XmlGenHelper.createElementWithTextblock("regulatory_authority", data, doc);

    }

    /**
     * createIrbInfo.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @return Element
     * @throws PAException when error
     */
    protected Element createIrbInfo(StudyProtocolDTO spDTO, Document doc) throws PAException {
        Element irbInfo = doc.createElement("irb_info");

        if (!BlConverter.convertToBool(spDTO.getReviewBoardApprovalRequiredIndicator())) {
            XmlGenHelper.appendElement(irbInfo, XmlGenHelper
                    .createElementWithTextblock("approval_status", "Not required", doc));
            return irbInfo;
        }

        List<StudySiteDTO> dtos = new ArrayList<StudySiteDTO>();
        StudySiteDTO dto = new StudySiteDTO();
        dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        dtos.add(dto);
        List<StudySiteDTO> spDTOs = getStudySiteService().getByStudyProtocol(spDTO.getIdentifier(), dtos);
        for (StudySiteDTO spart : spDTOs) {
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                    spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().equals(
                            spart.getReviewBoardApprovalStatusCode().getCode())) {

                XmlGenHelper.appendElement(irbInfo,
                        XmlGenHelper.createElementWithTextblock("approval_status", convertToCtValues(spart
                        .getReviewBoardApprovalStatusCode()), doc));

                XmlGenHelper.appendElement(irbInfo,
                        XmlGenHelper.createElementWithTextblock("approval_number", StConverter.convertToString(spart
                        .getReviewBoardApprovalNumber()), doc));

                Organization paOrg = getCorUtils().getPAOrganizationByIi(spart.getOversightCommitteeIi());
                    OrganizationDTO poOrg = null;
                    try {
                        poOrg = PoRegistry.getOrganizationEntityService().getOrganization(
                                IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
                    } catch (NullifiedEntityException e) {
                        throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier(), e);
                    }
                    XmlGenHelper.appendElement(irbInfo, XmlGenHelper.createElementWithTextblock(
                            "name", paOrg.getName(), doc));
                    XmlGenHelper.appendElement(irbInfo,
                            XmlGenHelper.createElementWithTextblock("affiliation", StConverter.convertToString(spart
                            .getReviewBoardOrganizationalAffiliation()), doc));
                    TelPhone telPh = getFirstTel(poOrg.getTelecomAddress().getItem(), TelPhone.class);
                    if (telPh != null) {
                        XmlGenHelper.appendElement(irbInfo,
                                XmlGenHelper.createElementWithTextblock(XmlGenHelper.PHONE, StringUtils.substring(telPh
                                .getValue().getSchemeSpecificPart(), 0, PAAttributeMaxLen.LEN_30), doc));
                    }
                    TelEmail telEmail = getFirstTel(poOrg.getTelecomAddress().getItem(), TelEmail.class);
                    if (telEmail != null) {
                            XmlGenHelper.appendElement(irbInfo,
                                    XmlGenHelper.createElementWithTextblock(XmlGenHelper.EMAIL,
                                            StringUtils.substring(telEmail.getValue().getSchemeSpecificPart(),
                                                    0, PAAttributeMaxLen.LEN_254), doc));

                        }


                    XmlGenHelper.appendElement(irbInfo,
                            XmlGenHelper.createElementWithTextblock("full_address",
                                    AddressConverterUtil.convertToAddress(poOrg.getPostalAddress()),
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

    private void createIdInfo(StudyProtocolDTO spDTO, Document doc, Element root, CTGovXmlGeneratorOptions[] options) 
            throws PAException {
        Element idInfo = doc.createElement("id_info");

        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        List<StudySiteDTO> sParts = getStudySiteService().getByStudyProtocol(spDTO.getIdentifier(), spartDTO);
        for (StudySiteDTO spart : sParts) {
            XmlGenHelper.appendElement(idInfo,
                    XmlGenHelper.createElementWithTextblock("org_study_id", StConverter.convertToString(spart
                    .getLocalStudyProtocolIdentifier()), doc));
            break;
        }

        Element assignedId = doc.createElement("secondary_id");
        XmlGenHelper.appendElement(assignedId,
                XmlGenHelper.createElementWithTextblock("id", PAUtil.getAssignedIdentifierExtension(spDTO), doc));
        XmlGenHelper.appendElement(assignedId, XmlGenHelper.createElementWithTextblock(
                "id_type", "Registry Identifier", doc));
        XmlGenHelper.appendElement(assignedId,
                XmlGenHelper.createElementWithTextblock("id_domain", "CTRP (Clinical Trial Reporting Program)", doc));
        XmlGenHelper.appendElement(idInfo, assignedId);

        boolean isProprietaryTrial = !ISOUtil.isBlNull(spDTO.getProprietaryTrialIndicator())
                && BlConverter.convertToBoolean(spDTO.getProprietaryTrialIndicator());
        if (!isProprietaryTrial) {
            List<Ii> otherIdentifierIis = PAUtil.getOtherIdentifiers(spDTO);
            for (Ii otherIdIi : otherIdentifierIis) {
                Element otherIdentifier = doc.createElement("secondary_id");
                XmlGenHelper.appendElement(otherIdentifier,
                        XmlGenHelper.createElementWithTextblock("id", otherIdIi.getExtension(), doc));                
                XmlGenHelper.appendElement(idInfo, otherIdentifier);
            }
        }
        addTrialSecondaryIdInfo(spDTO, doc, idInfo);

        addGrantInfo(spDTO, doc, idInfo);

        String prsOrgName = "";        
        if (ArrayUtils.contains(options,
                CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS)) {
            RegistryUser registryUser = getRegistryUserService().getUser(
                    StConverter.convertToString(spDTO.getUserLastCreated()));
            prsOrgName = PRS_PLACEHOLDER;
            if (registryUser != null
                    && StringUtils.isNotEmpty(registryUser.getPrsOrgName())) {
                prsOrgName = registryUser.getPrsOrgName();
            }
        } else {
            User user = CSMUserService.getInstance().getCSMUser(
                    UsernameHolder.getUser());
            RegistryUser registryUser = user != null ? getRegistryUserService()
                    .getUser(user.getLoginName()) : null;
            prsOrgName = registryUser != null
                    && StringUtils.isNotEmpty(registryUser.getPrsOrgName()) ? registryUser
                    .getPrsOrgName()
                    : PRS_PLACEHOLDER;
        }
        
        //XmlGenHelper.appendElement(idInfo, XmlGenHelper.createElementWithTextblock("org_name", prsOrgName, doc)); 
        String data = replaceXMLCharacters(prsOrgName);
        XmlGenHelper.appendElement(idInfo, XmlGenHelper.createElement("org_name", data, doc));
        XmlGenHelper.appendElement(root, idInfo);

    }

    /**
     * Add secondary ids for trial that are not the NCI id.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param idInfoNode Element
     * @throws PAException when error
     */
    protected void addTrialSecondaryIdInfo(StudyProtocolDTO spDTO, Document doc, Element idInfoNode)
        throws PAException {
        //NOOP
    }

    /**
     * Add Human Safety Subject Info.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root element root
     * @throws PAException when error
     */
    protected void addHumanSafetySubjectInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {
        //NOOP
    }

    /**
     * Add lead org info.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void addLeadOrgInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        //NOOP
    }

    /**
     * Add trial owners.
     * @param spDTO dto
     * @param doc doc
     * @param root root
     * @throws PAException when error
     */
    protected void addStudyOwnersInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {
        //NOOP
    }

    /**
     * Add specific info.
     * @param spDTO dto
     * @param doc doc
     * @param root root
     * @throws PAException when error
     */
    protected void addNciSpecificInfo(StudyProtocolDTO spDTO, Document doc, Element root)
        throws PAException {
        //NOOP
    }

    private void addGrantInfo(StudyProtocolDTO spDTO, Document doc, Element idInfoNode) throws PAException {
        List<StudyResourcingDTO> srDtos = getStudyResourcingService()
            .getStudyResourcingByStudyProtocol(spDTO.getIdentifier());

        for (StudyResourcingDTO srDto : srDtos) {
            if (!ISOUtil.isBlNull(srDto.getActiveIndicator())) {
                Element grantId = doc.createElement("secondary_id");
                StringBuilder id = new StringBuilder();
                id.append(srDto.getFundingMechanismCode().getCode());
                id.append(srDto.getNihInstitutionCode().getCode());
                id.append(srDto.getSerialNumber().getValue());
                XmlGenHelper.appendElement(grantId, XmlGenHelper.createElementWithTextblock("id", id.toString(), doc));
                XmlGenHelper.appendElement(grantId, XmlGenHelper.createElementWithTextblock(
                        "id_type", "NIH Grant Number", doc));

                XmlGenHelper.appendElement(idInfoNode, grantId);
            }
        }


    }

    /**
     * createIndInfo.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createIndInfo(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<StudyIndldeDTO> ideDtos = getStudyIndldeService().getByStudyProtocol(spDTO.getIdentifier());
        if (!getPaServiceUtil().containsNonExemptInds(ideDtos)) {
            XmlGenHelper.appendElement(root, XmlGenHelper.createElementWithTextblock(
                    "is_ind_study", XmlGenHelper.NO, doc));
            return;
        }
        StudyIndldeDTO ideDTO = getFirstNonExemptInd(ideDtos);
        XmlGenHelper.appendElement(root, XmlGenHelper.createElementWithTextblock(
                "is_ind_study", XmlGenHelper.YES, doc));
        Element idInfo = doc.createElement("ind_info");
        XmlGenHelper.appendElement(idInfo,
                XmlGenHelper.createElementWithTextblock("ind_grantor",
                        convertToCtValues(ideDTO.getGrantorCode()), doc));
        XmlGenHelper.appendElement(idInfo,
                XmlGenHelper.createElementWithTextblock("ind_number",
                        StConverter.convertToString(ideDTO.getIndldeNumber()), doc));
        XmlGenHelper.appendElement(idInfo,
                XmlGenHelper.createElementWithTextblock("has_expanded_access", BlConverter.convertBlToYesNoString(ideDTO
                .getExpandedAccessIndicator()), doc));

        if (idInfo.hasChildNodes()) {
            XmlGenHelper.appendElement(root, idInfo);
        }
    }

    /**
     * createTrialFunding.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createTrialFunding(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        //NOOP
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

    /**
     * createEligibility.
     * @param spDTO StudyProtocolDTO
     * @param doc Document
     * @param root Element
     * @throws PAException when error
     */
    protected void createEligibility(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = getPlannedActivityService()
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
            @Override
            public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
                return (!ISOUtil.isIntNull(o1.getDisplayOrder()) && !ISOUtil.isIntNull(o2.getDisplayOrder())) ? o1
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
                    nullCrit.append(createDescriptionTextString(descriptionText));
                } else if (incIndicator) {
                    incCrit.append(createDescriptionTextString(descriptionText));
                } else {
                    exCrit.append(createDescriptionTextString(descriptionText));
                }
            } else {
                value = pq.getLow().getValue();
                unit = pq.getLow().getUnit();
                operator = (!ISOUtil.isStNull(paEC.getOperator())) ? paEC.getOperator().getValue() : "";
                if (incIndicator == null) {
                    nullCrit.append(createValueUnitOperatorString(criterionName, value, unit, operator));
                } else if (incIndicator) {
                    incCrit.append(createValueUnitOperatorString(criterionName, value, unit, operator));
                } else {
                    exCrit.append(createValueUnitOperatorString(criterionName, value, unit, operator));
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
            createTextBlock("criteria", StringUtils.substring(data.toString(), 0, PAAttributeMaxLen.LEN_15000), doc,
                    eligibility);
        }
        XmlGenHelper.appendElement(eligibility,
                XmlGenHelper.createElementWithTextblock("healthy_volunteers", BlConverter.convertBlToYesNoString(spDTO
                .getAcceptHealthyVolunteersIndicator()), doc));
        XmlGenHelper.appendElement(eligibility, XmlGenHelper.createElementWithTextblock("gender", genderCode, doc));
        XmlGenHelper.appendElement(eligibility,
                XmlGenHelper.createElementWithTextblock("minimum_age", XmlGenHelper.getAgeUnit(minAge, minUnit), doc));
        XmlGenHelper.appendElement(eligibility,
                XmlGenHelper.createElementWithTextblock("maximum_age", XmlGenHelper.getAgeUnit(maxAge, maxUnit), doc));
        
        if (spDTO instanceof NonInterventionalStudyProtocolDTO) {
            NonInterventionalStudyProtocolDTO nonIntDTO = (NonInterventionalStudyProtocolDTO) spDTO;
            XmlGenHelper.appendElement(eligibility, XmlGenHelper
                    .createElementWithTextblock("study_pop", StConverter
                            .convertToString(nonIntDTO
                                    .getStudyPopulationDescription()), doc));
            XmlGenHelper.appendElement(eligibility, XmlGenHelper
                    .createElementWithTextblock("sampling_method", CdConverter
                            .convertCdToString(nonIntDTO
                                    .getSamplingMethodCode()), doc));            
        }        
        
        XmlGenHelper.appendElement(root, eligibility);

    }

     /**
     * @param text Description text
     * @return formatted String with description text
     */
    String createDescriptionTextString(String text) {
        StringBuilder str = new StringBuilder();
        str.append(XmlGenHelper.BULLET_L1);
        str.append(applyPrsFormattingFixes(text));
        str.append('\n');
        return str.toString();
    }

    /**
     * Apply some formatting fixes to achieve a better display in PRS.
     * @param text
     * @return
     */
    String applyPrsFormattingFixes(String text) { // NOPMD 
        if (text != null) {
            text = text.replaceAll("(?m)^ \\*", "  *");
            text = text.replaceAll("(?m)^\\*", "  *");
            text = text.replaceAll("(?m)^ \\-\\s", "  * ");
            text = text.replaceAll("(?m)^\\-\\s", "  * ");
        }
        return text;
    }

    /**
     * @param criterionName criterionName
     * @param value value
     * @param unit unit
     * @param operator operator
     * @return formatted string
     */
    String createValueUnitOperatorString(String criterionName, BigDecimal value, String unit,  String operator) {
        StringBuilder str = new StringBuilder();
        str.append(XmlGenHelper.TAB);
        str.append(criterionName);
        str.append(' ');
        str.append(value);
        str.append(' ');
        str.append(operator);
        str.append(' ');
        str.append(unit);
        str.append('\n');
        return str.toString();
    }

    private void createArmGroup(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<ArmDTO> arms = getArmService().getByStudyProtocol(spDTO.getIdentifier());

        if (CollectionUtils.isEmpty(arms)) {
            return;
        }
        for (ArmDTO armDTO : arms) {
            Element armGroup = doc.createElement("arm_group");
            XmlGenHelper.appendElement(armGroup,
                    XmlGenHelper.createElement("arm_group_label",
                            StConverter.convertToString(armDTO.getName()), doc));
            XmlGenHelper.appendElement(armGroup,
                    XmlGenHelper.createElement("arm_type", convertToCtValues(armDTO.getTypeCode()), doc));
            createTextBlock("arm_group_description", StringUtils.substring(
                    StConverter.convertToString(armDTO.getDescriptionText()), 0, PAAttributeMaxLen.LEN_1000)
                    , doc, armGroup);
            if (armGroup.hasChildNodes()) {
                root.appendChild(armGroup);
            }
        }
    }

    /**
     * addInterventionCdrValue.
     * @param iDto intervention dto.
     * @param root root element
     * @param doc document
     */
    protected void addInterventionCdrValue(InterventionDTO iDto, Element root, Document doc) {
        //NOOP
    }

    private void createIntervention(Ii studyProtocolIi, Document doc, Element root) throws PAException {
        List<PlannedActivityDTO> paList = getPlannedActivityService().getByStudyProtocol(studyProtocolIi);

        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                Element intervention = doc.createElement("intervention");
                InterventionDTO iDto = getInterventionService().get(pa.getInterventionIdentifier());
                if (pa.getSubcategoryCode() != null && pa.getSubcategoryCode().getCode() != null) {
                    XmlGenHelper.appendElement(intervention,
                            XmlGenHelper.createElementWithTextblock("intervention_type",
                                    pa.getSubcategoryCode().getCode(),
                            doc));
                }
                XmlGenHelper.appendElement(intervention,
                        XmlGenHelper.createElementWithTextblock("intervention_name", StringUtils.substring(StConverter
                        .convertToString(iDto.getName()), 0, PAAttributeMaxLen.LEN_160), doc));
                addInterventionCdrValue(iDto, intervention, doc);
                createTextBlock("intervention_description", StringUtils.substring(
                        StConverter.convertToString(pa.getTextDescription()), 0, PAAttributeMaxLen.LEN_1000), doc,
                        intervention);
                Iterator<InterventionAlternateNameDTO> ianIt =
                    getInterventionAlternateNameService().getByIntervention(iDto
                        .getIdentifier()).iterator();
                List<InterventionAlternateNameDTO> interventionNames = new ArrayList<InterventionAlternateNameDTO>();
                for (int i = 0; ianIt.hasNext() && i < PAAttributeMaxLen.LEN_5; i++) {
                        InterventionAlternateNameDTO ian = ianIt.next();
                        if (isPlannedActivityTypeName(ian.getNameTypeCode())) {
                            interventionNames.add(ian);
                        }
                }

                Collections.sort(interventionNames, new Comparator<InterventionAlternateNameDTO>() {
                    @Override
                    public int compare(InterventionAlternateNameDTO o1, InterventionAlternateNameDTO o2) {
                        return o1.getName().getValue().compareToIgnoreCase(o2.getName().getValue());
                    }
                });

                for (InterventionAlternateNameDTO ian : interventionNames) {
                    XmlGenHelper.appendElement(intervention,
                            XmlGenHelper.createElementWithTextblock("intervention_other_name", StringUtils.substring(
                            StConverter.convertToString(ian.getName()), 0, PAAttributeMaxLen.LEN_160), doc));
                }

                List<ArmDTO> armDtos = getArmService().getByPlannedActivity(pa.getIdentifier());
                for (ArmDTO armDTO : armDtos) {
                    XmlGenHelper.appendElement(intervention,
                            XmlGenHelper.createElement("arm_group_label", StringUtils.substring(StConverter
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
            XmlGenHelper.appendElement(studyDesign, XmlGenHelper.createElementWithTextblock(
                    "study_type", "Interventional", doc));
            XmlGenHelper.appendElement(studyDesign, createInterventional(spDTO, doc));
        } else if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("NonInterventionalStudyProtocol")) {
            XmlGenHelper.appendElement(studyDesign, XmlGenHelper.createElementWithTextblock(
                    "study_type", "Observational", doc));
            XmlGenHelper.appendElement(studyDesign, createNonInterventional(spDTO, doc));
        }
        return studyDesign;
    }
    
    private String replaceXMLCharacters(String inputData) {
        String data = inputData;
      if (XmlGenHelper.containsXmlChars(inputData)) {
               data = StringEscapeUtils.escapeXml(inputData);
      } 
      return data;
    }
    
    /**
     * Add prim purpose and phase additional qualifiers.
     * @param ispDTO InterventionalStudyProtocolDTO
     * @param doc Document
     * @param invDesign Element
     */
    protected void addDesignDetailsAdditionalQualifiers(InterventionalStudyProtocolDTO ispDTO,
            Document doc, Element invDesign) {
        //NOOP
    }

    private Element createInterventional(StudyProtocolDTO spDTO, Document doc) throws PAException {
        InterventionalStudyProtocolDTO ispDTO = getStudyProtocolService().getInterventionalStudyProtocol(spDTO
                .getIdentifier());

        Element invDesign = doc.createElement("interventional_design");
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("interventional_subtype", convertToCtValues(ispDTO
                .getPrimaryPurposeCode()), doc));
        addDesignDetailsAdditionalQualifiers(ispDTO, doc, invDesign);
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("phase", convertToCtValues(ispDTO.getPhaseCode()), doc));
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("allocation",
                        convertToCtValues(ispDTO.getAllocationCode()), doc));
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("masking",
                        convertToCtValues(ispDTO.getBlindingSchemaCode()), doc));
        List<Cd> cds = DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
        for (Cd cd : cds) {
            if (BlindingRoleCode.CAREGIVER.getCode().equals(cd.getCode())) {
                XmlGenHelper.appendElement(invDesign,
                        XmlGenHelper.createElementWithTextblock("masked_caregiver", XmlGenHelper.YES, doc));
            } else if (BlindingRoleCode.INVESTIGATOR.getCode().equals(cd.getCode())) {
                XmlGenHelper.appendElement(invDesign,
                        XmlGenHelper.createElementWithTextblock("masked_investigator", XmlGenHelper.YES, doc));
            } else if (BlindingRoleCode.OUTCOMES_ASSESSOR.getCode().equals(cd.getCode())) {
                XmlGenHelper.appendElement(invDesign,
                        XmlGenHelper.createElementWithTextblock("masked_assessor", XmlGenHelper.YES, doc));
            } else if (BlindingRoleCode.SUBJECT.getCode().equals(cd.getCode())) {
                XmlGenHelper.appendElement(invDesign,
                        XmlGenHelper.createElementWithTextblock("masked_subject", XmlGenHelper.YES, doc));
            }
        } // for
        XmlGenHelper.appendElement(invDesign, XmlGenHelper.createElementWithTextblock("assignment",
                convertToCtValues(ispDTO.getDesignConfigurationCode()), doc));
        XmlGenHelper.appendElement(invDesign, XmlGenHelper.createElementWithTextblock("endpoint", convertToCtValues(
                ispDTO.getStudyClassificationCode()), doc));
        XmlGenHelper.appendElement(invDesign,
                XmlGenHelper.createElementWithTextblock("number_of_arms", IntConverter.convertToString(ispDTO
                .getNumberOfInterventionGroups()), doc));
        return invDesign;
    }

    private Element createNonInterventional(StudyProtocolDTO spDTO, Document doc) throws PAException {
        NonInterventionalStudyProtocolDTO ospDTO = getStudyProtocolService()
                .getNonInterventionalStudyProtocol(spDTO.getIdentifier());

        Element obsDesign = doc.createElement("noninterventional_design");
        XmlGenHelper.appendElement(obsDesign,
                XmlGenHelper.createElementWithTextblock("timing",
                        convertToCtValues(ospDTO.getTimePerspectiveCode()), doc));
        XmlGenHelper.appendElement(obsDesign,
                XmlGenHelper.createElementWithTextblock("noninterventional_study_design", convertToCtValues(ospDTO
                .getStudyModelCode()), doc));
        XmlGenHelper.appendElement(obsDesign,
                XmlGenHelper.createElementWithTextblock("biospecimen_retention", convertToCtValues(ospDTO
                .getBiospecimenRetentionCode()), doc));
        createTextBlock("biospecimen_description", StConverter.convertToString(ospDTO
                .getBiospecimenDescription()), doc, obsDesign);
        XmlGenHelper.appendElement(obsDesign,
                XmlGenHelper.createElementWithTextblock("number_of_groups", IntConverter.convertToString(ospDTO
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
                XmlGenHelper.appendElement(po,
                        XmlGenHelper.createElement("outcome_measure",
                                StringUtils.substring(smDTO.getName().getValue(), 0,
                        PAAttributeMaxLen.LEN_254), doc));
                if (!ISOUtil.isStNull(smDTO.getDescription())) {
                    createTextBlock("outcome_description", StringUtils.substring(smDTO.getDescription().getValue(), 0,
                            PAAttributeMaxLen.LEN_600), doc, po);
                }
                XmlGenHelper.appendElement(po,
                        XmlGenHelper.createElement(
                                "outcome_safety_issue", BlConverter.convertBlToYesNoString(smDTO
                        .getSafetyIndicator()), doc));
                XmlGenHelper.appendElement(po,
                        XmlGenHelper.createElement(
                                "outcome_time_frame", StringUtils.substring(smDTO.getTimeFrame()
                        .getValue(), 0, PAAttributeMaxLen.LEN_254), doc));
                if (po.hasChildNodes()) {
                    XmlGenHelper.appendElement(root, po);
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
                XmlGenHelper.appendElement(om,
                        XmlGenHelper.createElement("outcome_measure",
                                StringUtils.substring(smDTO.getName().getValue(), 0,
                        PAAttributeMaxLen.LEN_254), doc));
                if (!ISOUtil.isStNull(smDTO.getDescription())) {
                    createTextBlock("outcome_description", StringUtils.substring(smDTO.getDescription().getValue(), 0,
                            PAAttributeMaxLen.LEN_600), doc, om);
                }                
                XmlGenHelper.appendElement(om,
                        XmlGenHelper.createElement(
                                "outcome_safety_issue", BlConverter.convertBlToYesNoString(smDTO
                        .getSafetyIndicator()), doc));
                XmlGenHelper.appendElement(om,
                        XmlGenHelper.createElement(
                                "outcome_time_frame", StringUtils.substring(smDTO.getTimeFrame()
                        .getValue(), 0, PAAttributeMaxLen.LEN_254), doc));
                if (om.hasChildNodes()) {
                    XmlGenHelper.appendElement(root, om);
                }
            }
        }
    }

    private void addCollaborators(Element sponsors, Ii studyProtocolIi, Document doc) throws PAException {
        List<Element> collaborators = createCollaborators(studyProtocolIi, doc);
        for (Element collaborator : collaborators) {
            if (collaborator.hasChildNodes()) {
                XmlGenHelper.appendElement(sponsors, collaborator);
            }
        }
    }

    private void createSponsors(Ii studyProtocolIi, Document doc, Element root, StudyProtocolDTO spDTO)
            throws PAException, NullifiedRoleException {
        Element sponsors = doc.createElement("sponsors");
        if (spDTO.getCtgovXmlRequiredIndicator().getValue().booleanValue()) {
            Element lead = createLeadSponsor(studyProtocolIi, doc);
            if (lead.hasChildNodes()) {
                XmlGenHelper.appendElement(sponsors, lead);
            }
            Element rp = createResponsibleParty(studyProtocolIi, doc);
            if (rp.hasChildNodes()) {
                XmlGenHelper.appendElement(sponsors, rp);
            }
        }
        addCollaborators(sponsors, studyProtocolIi, doc);

        if (sponsors.hasChildNodes()) {
            XmlGenHelper.appendElement(root, sponsors);
        }

    }

    /**
     * createResponsibleParty.
     * @param studyProtocolIi ii
     * @param doc doc
     * @return element
     * @throws PAException when error
     * @throws NullifiedRoleException when error
     */
    protected Element createResponsibleParty(Ii studyProtocolIi, Document doc)
    throws PAException, NullifiedRoleException {
        Element responsibleParty = doc.createElement("resp_party");
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        Person person = null;
        String resPartyContactName = null;
        String resPartyType = null;
        Organization sponsor = null;
        if (CollectionUtils.isNotEmpty(scDtos)) {
            scDto = scDtos.get(0);
            person = getCorUtils().getPAPersonByIi(scDto.getClinicalResearchStaffIi());
            resPartyContactName = person.getFullName();
            resPartyType = PRINCIPAL_INVESTIGATOR;
            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = getStudySiteService().getByStudyProtocol(studyProtocolIi, spartDTO);
            for (StudySiteDTO spart : sParts) {
                sponsor = getCorUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            }
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spcDtos = getStudySiteContactService().getByStudyProtocol(studyProtocolIi, spart);
            if (CollectionUtils.isNotEmpty(spcDtos)) {
                spart = spcDtos.get(0);
                PAContactDTO paCDto = getCorUtils().getContactByPAOrganizationalContactId((Long.valueOf(spart
                        .getOrganizationalContactIi().getExtension())));
                resPartyContactName = paCDto.getResponsiblePartyContactName();
                resPartyType = SPONSOR;
            }
            StudySiteDTO spDto = new StudySiteDTO();
            spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> ssDtos = getStudySiteService().getByStudyProtocol(studyProtocolIi, spDto);
            if (CollectionUtils.isNotEmpty(ssDtos)) {
                spDto = ssDtos.get(0);
                sponsor = getCorUtils().getPAOrganizationByIi(spDto.getResearchOrganizationIi());
            }

        }
        if (resPartyType != null) {
            XmlGenHelper.appendElement(responsibleParty,
                   XmlGenHelper.createElement("resp_party_type", resPartyType, doc));
        }
        if (resPartyType != null && PRINCIPAL_INVESTIGATOR.equals(resPartyType)) {
                XmlGenHelper.appendElement(responsibleParty,
                        XmlGenHelper.createElement("investigator_title", resPartyType, doc));
        }
        if (sponsor != null) {
                String data = replaceXMLCharacters(sponsor.getName());
                XmlGenHelper.appendElement(responsibleParty,
                        XmlGenHelper.createElement("investigator_affiliation", data, doc));  
        }
        return responsibleParty;
    }

    /**
     * createLeadSponsor.
     * @param studyProtocolIi ii
     * @param doc Document
     * @return Element
     * @throws PAException when error.
     */
    protected Element createLeadSponsor(Ii studyProtocolIi, Document doc) throws PAException {
        Organization sponsor = getOrgCorrelationService().getOrganizationByFunctionRole(studyProtocolIi, CdConverter
                .convertToCd(StudySiteFunctionalCode.SPONSOR));
        Element lead = doc.createElement("lead_sponsor");
        String sponsorName = sponsor.getName();
        String data = replaceXMLCharacters(StringUtils.substring(sponsorName, 0,
                PAAttributeMaxLen.LEN_160));
        XmlGenHelper.appendElement(lead,
                XmlGenHelper.createElement("agency", data, doc));
        return lead;
    }

    /**
     * createCollaborators.
     * @param studyProtocolIi trial ii.
     * @param doc doc obj
     * @return element
     * @throws PAException when error
     */
    protected List<Element> createCollaborators(Ii studyProtocolIi, Document doc) throws PAException {
        List<Element> collaborators = new ArrayList<Element>();
        List<Organization> orgs = getOrgCorrelationService().getOrganizationByStudySite(Long.valueOf(studyProtocolIi
                .getExtension()), StudySiteFunctionalCode.COLLABORATORS);
    
        for (Organization eachOrg : orgs) {
            Element collaborator = doc.createElement("collaborator");
            String data = replaceXMLCharacters(StringUtils.substring(eachOrg.getName(), 0,
                    PAAttributeMaxLen.LEN_160));
            XmlGenHelper.appendElement(collaborator,
                    XmlGenHelper.createElementWithTextblock("agency", data, doc));
            collaborators.add(collaborator);
        }
        return collaborators;
    }

    /***
     * createFacility.
     * @param sp StudySiteDTO.
     * @param location element.
     * @param doc Document
     * @throws PAException when error.
     */
    protected void createFacility(StudySiteDTO sp, Element location, Document doc) throws PAException {
        Element facility = doc.createElement("facility");
        Element address = doc.createElement("address");

        Organization orgBo = getCorUtils().getPAOrganizationByIi(sp.getHealthcareFacilityIi());
        String data = replaceXMLCharacters(orgBo.getName());
        XmlGenHelper.appendElement(facility, XmlGenHelper.createElement("name", data, doc));
        XmlGenHelper.appendElement(address, XmlGenHelper.createElementWithTextblock("city", orgBo.getCity(), doc));
        XmlGenHelper.appendElement(address, XmlGenHelper.createElementWithTextblock("state", orgBo.getState(), doc));
        XmlGenHelper.appendElement(address, XmlGenHelper.createElementWithTextblock("zip", orgBo.getPostalCode(), doc));
        XmlGenHelper.appendElement(address,
                XmlGenHelper.createElementWithTextblock("country", PADomainUtils.getCountryNameUsingAlpha3Code(orgBo
                .getCountryName()), doc));
        XmlGenHelper.appendElement(facility, address);
        XmlGenHelper.appendElement(location, facility);
    }

    private void createLocation(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException,
            NullifiedRoleException {

        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = getStudySiteService().getByStudyProtocol(spDTO.getIdentifier(), srDTO);
        TreeMap<String, StudySiteDTO> sortedList = new TreeMap<String, StudySiteDTO>();
        for (StudySiteDTO ss : spList) {
            Organization orgBo = getCorUtils().getPAOrganizationByIi(ss.getHealthcareFacilityIi());
            String key = orgBo.getName() + ss.hashCode();
            sortedList.put(key, ss);
        }
        for (StudySiteDTO sp : sortedList.values()) {
            Element location = doc.createElement("location");
            createFacility(sp, location, doc);
            StudySiteAccrualStatusDTO ssas = getStudySiteAccrualStatusService()
            .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
            if (ssas != null) {
                XmlGenHelper.appendElement(location,
                        XmlGenHelper.createElementWithTextblock("status",
                                convertToCtValues(ssas.getStatusCode()), doc));
            }

            List<StudySiteContactDTO> spcDTOs = getStudySiteContactService().getByStudySite(sp.getIdentifier());
            XmlGenHelper.appendElement(root, location);
            createContact(spcDTOs, location, doc);
            createInvestigators(spcDTOs, location, doc);
        }
    }

    /**
     * createInvestigators.
     * @param spcDTOs list of site contacts.
     * @param location element
     * @param doc document
     * @throws PAException when error.
     */
    protected void createInvestigators(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException {
        for (StudySiteContactDTO spcDTO : spcDTOs) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            Element investigator = doc.createElement("investigator");
            addInvestigatorPerson(spcDTO, investigator, doc);
            if (investigator.hasChildNodes()) {
                XmlGenHelper.appendElement(location, investigator);
            }
        }
    }
    
    /**
     * Add the investigator person fields to the given element. 
     * @param spcDTO The StudySiteContactDTO
     * @param investigator the element to which fields must be added
     * @param doc The document
     * @throws PAException when error
     */
    protected void addInvestigatorPerson(StudySiteContactDTO spcDTO, Element investigator, Document doc)
            throws PAException {
        Person p = getCorUtils().getPAPersonByIi(spcDTO.getClinicalResearchStaffIi());
        XmlGenHelper.appendElement(investigator,
                XmlGenHelper.createElementWithTextblock(XmlGenHelper.FIRST_NAME, p.getFirstName(), doc));
        addMiddleName(investigator, p.getMiddleName(), doc);
        XmlGenHelper.appendElement(investigator,
                XmlGenHelper.createElementWithTextblock(XmlGenHelper.LAST_NAME, p.getLastName(), doc));
        XmlGenHelper.appendElement(investigator,
                XmlGenHelper.createElementWithTextblock("role", convertToCtValues(spcDTO.getRoleCode()), doc));
    }

    /**
     * Adds a middle name to a contact.
     * @param contact element
     * @param name middle name
     * @param doc document to add element to
     */
    protected void addMiddleName(Element contact, String name, Document doc) {
        String middleName = StringUtils.substring(name, 0, PAAttributeMaxLen.LEN_1);
        if (StringUtils.isNotBlank(middleName)) {
            XmlGenHelper.appendElement(contact,
                    XmlGenHelper.createElementWithTextblock("middle_name", middleName + "." , doc));
        }
    }

    /**
     * createContact.
     * @param spcDTOs list of site contacts
     * @param location element
     * @param doc document
     * @throws PAException when error
     * @throws NullifiedRoleException when error.
     */
    protected void createContact(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
            throws PAException, NullifiedRoleException {
        for (StudySiteContactDTO sscDTO : spcDTOs) {
            if (!StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(sscDTO.getRoleCode().getCode())) {
                continue;
            }
            Element contact = doc.createElement("contact");
            addContactPerson(sscDTO, contact, doc);
            addPhoneAndEmail(sscDTO.getTelecomAddresses(), contact, doc);
            if (contact.hasChildNodes()) {
                XmlGenHelper.appendElement(location, contact);
            }
        }
    }
    
    /**
     * Add contact person fields to the given element.
     * @param sscDTO The studySiteContactDTO.
     * @param contact The element to which the person fields must be added
     * @param doc The document
     * @throws PAException NullifiedRoleException when error.
     * @throws NullifiedRoleException when error.
     */
    protected void addContactPerson(StudySiteContactDTO sscDTO, Element contact, Document doc) throws PAException,
            NullifiedRoleException {
        if (sscDTO.getClinicalResearchStaffIi() != null) {
            Person p = getCorUtils().getPAPersonByIi(sscDTO.getClinicalResearchStaffIi());
            XmlGenHelper.appendElement(contact,
                                       XmlGenHelper.createElementWithTextblock(XmlGenHelper.FIRST_NAME,
                                                                               p.getFirstName(), doc));
            addMiddleName(contact, p.getMiddleName(), doc);
            XmlGenHelper.appendElement(contact, XmlGenHelper.createElementWithTextblock(XmlGenHelper.LAST_NAME,
                                                                                        p.getLastName(), doc));
        } else if (sscDTO.getOrganizationalContactIi() != null) {
            Long contactId = IiConverter.convertToLong(sscDTO.getOrganizationalContactIi());
            PAContactDTO paCDto = getCorUtils().getContactByPAOrganizationalContactId(contactId);
            XmlGenHelper.appendElement(contact,
                                       XmlGenHelper.createElementWithTextblock("last_name", paCDto.getTitle(), doc));
        }
    }

    /**
     * Adds the phone and email elements to the given element.
     * @param dSet The DSet of telecom addresses
     * @param element The element to which email and phone must be added
     * @param doc The document
     */
    protected void addPhoneAndEmail(DSet<Tel> dSet, Element element, Document doc) {
        List<String> phones = DSetConverter.convertDSetToList(dSet, "PHONE");
        
        if (phones != null && !phones.isEmpty()) {
            XmlGenHelper.appendElement(element,
                    XmlGenHelper.createElementWithTextblock(XmlGenHelper.PHONE, StringUtils.substring(
                            phones.get(0), 0, PAAttributeMaxLen.LEN_30), doc));
        }
        List<String> emails = DSetConverter.convertDSetToList(dSet, "EMAIL");
        if (emails != null && !emails.isEmpty()) {
            XmlGenHelper.appendElement(element,
                    XmlGenHelper.createElementWithTextblock(XmlGenHelper.EMAIL, StringUtils.substring(
                            emails.get(0), 0, PAAttributeMaxLen.LEN_254), doc));
        }
    }

    private void createTextBlock(final String elementName, final String st, Document doc, Element root)
            throws PAException {

        XmlGenHelper
            .appendElement(doc.createElement(elementName),
                    XmlGenHelper.createTextblockElement(st, doc), root);
    }

    private StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException("Study Protocol is not available for given id = " + studyProtocolIi.getExtension());
        }
        return spDTO;
    }

    /**
     * convertTsToYYYYMMFormart.
     * @param isoTs ts
     * @return string
     */
    protected static String convertTsToYYYYMMFormat(Ts isoTs) {
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return null;
        }

        DateFormat formatter1 = new SimpleDateFormat(YYYYMM, Locale.getDefault());
        return formatter1.format(ts);
    }

    /**
     * convertTsToYYYYMMFormart.
     * @param isoTs ts
     * @return string
     */
    protected static String convertTsToYYYYMMDDFormat(Ts isoTs) {
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return null;
        }
        DateFormat formatter1 = new SimpleDateFormat(YYYYMMDD, Locale.getDefault());
        return formatter1.format(ts);
    }
    
    
}
