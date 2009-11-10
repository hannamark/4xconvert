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

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.Ts;
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
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
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
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
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
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyObjectiveServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusBeanLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAConstants;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" ,
    "PMD.ExcessiveMethodLength" , "PMD.TooManyMethods" , "PMD.TooManyFields" , "PMD.NPathComplexity"  })
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CTGovXmlGeneratorServiceBean implements  CTGovXmlGeneratorServiceRemote {

    
    @EJB
    StudyProtocolServiceLocal studyProtocolService = null;
    @EJB
    StudyOverallStatusServiceLocal studyOverallStatusService = null;
    @EJB
    StudyIndldeServiceLocal studyIndldeService  = null;
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
    RegistryUserServiceRemote registryUserService = null;
    @EJB
    StudyObjectiveServiceLocal studyObjectiveService = null;
    
    private static final Logger LOG  = Logger.getLogger(CTGovXmlGeneratorServiceBean.class);
    private static final String TEXT_BLOCK = "textblock";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final int DAYS = 365;
    private static final int MONTHS = 12;
    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int MAX_AGE = 999;
    private static final int ERROR_COUNT = 5;
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String NA = "N/A";
    private static final String TAB = "     ";
    private static final String DASH = "- ";    
  
    private static Map<String , String> nv = new HashMap<String, String>();
    /**
     * @param studyProtocolIi ii of studyprotocol
     * @return String xml output
     * @throws PAException on error
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public String generateCTGovXml(Ii studyProtocolIi) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }
        createCtGovValues();
        LOG.debug("Entering generateCTGovXml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        StudyProtocolDTO spDTO = null;
        try {

            spDTO = getStudyProtocol(studyProtocolIi); 
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            //create the root element
            Element root = createElement("clinical_study" , doc);
            doc.appendChild(root);
            createIdInfo(spDTO, doc, root);
            createElement("is_fda_regulated" ,
                    convertBLToString(spDTO.getFdaRegulatedIndicator()) , doc , root);
            createElement("is_section_801" ,
                    convertBLToString(spDTO.getSection801Indicator()) , doc , root);
            if (YES.equalsIgnoreCase(convertBLToString(spDTO.getSection801Indicator()))) { //device doesn't matter
                createElement("delayed_posting" ,
                        convertBLToString(spDTO.getDelayedpostingIndicator()) , doc , root);
            }
            createIndInfo(spDTO , doc , root);
            createElement("brief_title" , spDTO.getPublicTitle().getValue() , doc , root);
            createElement("acronym" , spDTO.getAcronym().getValue() , doc , root);
            createElement("official_title" , spDTO.getOfficialTitle().getValue() , doc , root);
            createSponsors(spDTO.getIdentifier() , doc , root);
            createOversightInfo(spDTO , doc , root);
            createTextBlock("brief_summary", spDTO.getPublicDescription(), PAAttributeMaxLen.LEN_MIN_1 , doc, root);
            //createObjective(spDTO, doc , root);
            createCdataBlock("detailed_description" , 
                                 spDTO.getScientificDescription() , PAAttributeMaxLen.LEN_32000 , doc , root);
            createOverallStatus(spDTO, doc, root);
           //createElement("expanded_access_status", convertBLToString(spDTO.getExpandedAccessIndicator()), doc , root);
            if (spDTO.getExpandedAccessIndicator() != null && spDTO.getExpandedAccessIndicator().getValue() != null) {
                if (spDTO.getExpandedAccessIndicator().getValue()) {
                    appendElement(root ,  createElement("expanded_access_status", "Available", doc));
                } else {
                    appendElement(root ,  createElement("expanded_access_status", "No longer available", doc));
                }
            }
            appendElement(root ,  createElement("start_date", convertTsToYYYYMMFormart(spDTO.getStartDate()), doc));
            appendElement(root ,  createElement("primary_compl_date", convertTsToYYYYMMFormart(
                                spDTO.getPrimaryCompletionDate()), doc));
            appendElement(root ,  createElement("primary_compl_date_type",
                    spDTO.getPrimaryCompletionDateTypeCode(), doc));

            appendElement(root , createStudyDesign(spDTO, doc));
            List<StudyOutcomeMeasureDTO> somDtos = studyOutcomeMeasureService.getByStudyProtocol(spDTO.getIdentifier());
            createPrimaryOutcome(somDtos , doc , root);
            createSecondaryOutcome(somDtos , doc , root);
            createCondition(studyProtocolIi, doc, root);
            appendElement(root ,  createElement("enrollment", 
                    IvlConverter.convertInt().convertLowToString(spDTO.getTargetAccrualNumber()), doc));
            appendElement(root ,  createElement("enrollment_type", "anticipated", doc));
            createArmGroup(spDTO, doc, root);
            createIntervention(spDTO.getIdentifier(), doc, root);
            createEligibility(spDTO, doc , root);
            createOverallOfficial(spDTO.getIdentifier(), doc , root);
            createOverallContact(spDTO.getIdentifier(), doc , root);
            createLocation(spDTO , doc , root);
            
            appendElement(root ,  createElement("keyword", spDTO.getKeywordText(), PAAttributeMaxLen.KEYWORD , doc));
            Ts tsVerificationDate = spDTO.getRecordVerificationDate();
            if (tsVerificationDate == null || tsVerificationDate.getValue() == null) {
            DocumentWorkflowStatusDTO dto = documentWorkflowStatusService.getCurrentByStudyProtocol(studyProtocolIi);
              tsVerificationDate = TsConverter.convertToTs(IvlConverter.convertTs().
                      convertLow(dto.getStatusDateRange()));
            }
            appendElement(root ,  createElement("verification_date", PAUtil.convertTsToFormarttedDate(
                    tsVerificationDate, "yyyy-MM"), doc));

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
            LOG.error("Error while generating CT.GOV.xml "  , e);
            return createErrorXml(spDTO , e);
        }
    }

    private static String createErrorXml(StudyProtocolDTO spDTO , Exception e)  {
        StringWriter writer = new StringWriter();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();
            Element root = createElement("error" , doc);
            doc.appendChild(root);
            createElement("error_description" , "Unable to generate the XML" , doc , root);
            createElement("study_identifier" , spDTO.getAssignedIdentifier().getExtension() , doc , root);
            createElement("study_title" , spDTO.getOfficialTitle().getValue() , doc , root);
            createElement("contact_info" , "Please contact CTRP staff" , doc , root);
            createElement("error_type" , e.toString() , doc , root);
            StackTraceElement[] st =  e.getStackTrace();
            Element errorMessages = doc.createElement("error_messages");
            int x = 1;
            for (StackTraceElement se : st) {
                appendElement(errorMessages , createElement("error_message" , se.toString() , doc));
                x++;
                if (x > ERROR_COUNT) {
                    break;
                }
            }
            appendElement(root, errorMessages);
            DOMSource domSource = new DOMSource(doc);
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
        } catch (Exception e1) {
            LOG.error("Error while generating CT.GOV.xml "  , e1);
        }
        return writer.toString();
    }

    private void createOverallStatus(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
       StudyOverallStatusDTO soDTOs = studyOverallStatusService.getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (soDTOs == null) {
            return;
        }
        StudyRecruitmentStatusBeanLocal srb = new StudyRecruitmentStatusBeanLocal();
        StudyRecruitmentStatusDTO srsDtos = srb.getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (srsDtos != null) {
            appendElement(root, createElement("overall_status", srsDtos.getStatusCode() , doc));
        }

        StudyStatusCode overStatusCode = StudyStatusCode.getByCode(soDTOs.getStatusCode().getCode());

        if (StudyStatusCode.WITHDRAWN.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(overStatusCode)
                || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(overStatusCode)) {

            appendElement(root, createElement("why_stopped",
                    soDTOs.getReasonText(), PAAttributeMaxLen.LEN_160 ,  doc));
        }
    }

    private void createCondition(Ii studyProtocolIi , Document doc , Element root) throws PAException {
        List<StudyDiseaseDTO> sdDtos = studyDiseaseService.getByStudyProtocol(studyProtocolIi);
        
        if (sdDtos != null) {
            for (StudyDiseaseDTO sdDto : sdDtos) {
                if (BlConverter.covertToBool(sdDto.getCtGovXmlIndicator()) 
                        && sdDto.getLeadDiseaseIndicator() != null 
                        && sdDto.getLeadDiseaseIndicator().getValue()) {
                    DiseaseDTO d = diseaseService.get(sdDto.getDiseaseIdentifier());
                    appendElement(root, createElement(
                            "condition", d.getPreferredName(), PAAttributeMaxLen.LEN_160 , doc));
                   break; 
               }
            } 
            List<DiseaseDTO> diseases = new ArrayList<DiseaseDTO>();
            for (StudyDiseaseDTO sdDto : sdDtos) {
             if (BlConverter.covertToBool(sdDto.getCtGovXmlIndicator()) 
                     && sdDto.getLeadDiseaseIndicator() != null 
                        && !sdDto.getLeadDiseaseIndicator().getValue()) {
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
            appendElement(root, createElement(
                    "condition", d.getPreferredName(), PAAttributeMaxLen.LEN_160 , doc));
           }
            
        }
    }

    private  void createOverallContact(Ii studyProtocolIi , Document doc , Element root) 
        throws PAException,  NullifiedRoleException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> scDTOs = studyContactService.getByStudyProtocol(studyProtocolIi , scDto);
        CorrelationUtils  cUtils = new CorrelationUtils();
        for (StudyContactDTO scDTO : scDTOs) {
            Element overallContact = doc.createElement("overall_contact");
            if (scDTO.getClinicalResearchStaffIi() == null && scDTO.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto =  cUtils.getContactByPAOrganizationalContactId((
                        Long.valueOf(scDTO.getOrganizationalContactIi().getExtension())));
                appendElement(overallContact, createElement("last_name", paCDto.getTitle() , doc));
            } else if (scDTO.getClinicalResearchStaffIi() != null) {
                Person p  = cUtils.getPAPersonByIi(scDTO.getClinicalResearchStaffIi());
                appendElement(overallContact, createElement(FIRST_NAME, p.getFirstName() , doc));
                appendElement(overallContact, createElement(LAST_NAME, p.getLastName() , doc));
            }
            DSet<Tel> dset = scDTO.getTelecomAddresses();
            if (dset != null) {
                List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
                List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
                if (phones != null && !phones.isEmpty()) {
                    appendElement(overallContact , createElement(PHONE , phones.get(0), PAAttributeMaxLen.LEN_30, doc));
                }
                if (emails != null && !emails.isEmpty()) {
                    appendElement(overallContact , createElement(EMAIL, emails.get(0), PAAttributeMaxLen.LEN_254, doc));
                }
            }
            if (overallContact.hasChildNodes()) {
                root.appendChild(overallContact);
            }

            break;
        }

    }

    private  void createOverallOfficial(Ii studyProtocolIi , Document doc , Element root) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDTOs = studyContactService.getByStudyProtocol(studyProtocolIi , scDto);
        CorrelationUtils  cUtils = new CorrelationUtils();
        for (StudyContactDTO scDTO : scDTOs) {
            if (StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode().equals(scDTO.getRoleCode().getCode())) {
                Person p  = cUtils.getPAPersonByIi(scDTO.getClinicalResearchStaffIi());
                Element overallofficial = doc.createElement("overall_official");
                appendElement(overallofficial, createElement(FIRST_NAME, p.getFirstName() , doc));
                appendElement(overallofficial, createElement(LAST_NAME, p.getLastName() , doc));
                appendElement(overallofficial, createElement("role", "Principal Investigator"  , doc));

                StudySiteDTO spartDTO = new StudySiteDTO();
                spartDTO.setFunctionalCode(
                        CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
                List<StudySiteDTO> sParts = 
                                studySiteService.getByStudyProtocol(studyProtocolIi, spartDTO);

                for (StudySiteDTO spart : sParts) {
                    Organization o = cUtils.getPAOrganizationByIi(spart.getResearchOrganizationIi());
                    appendElement(overallofficial , createElement("affiliation" , o.getName() , doc));
                    break;
                }

                if (overallofficial.hasChildNodes()) {
                    appendElement(root , overallofficial);
                }
                break;
            }
        }
    }

    private void createOversightInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        Element overSightInfo  = doc.createElement("oversight_info");
        appendElement(overSightInfo , createRegulatoryAuthority(spDTO , doc));
        appendElement(overSightInfo , createIrbInfo(spDTO ,  doc));
        appendElement(overSightInfo, createElement(
                "has_dmc", convertBLToString(spDTO.getDataMonitoringCommitteeAppointedIndicator()), doc));
        appendElement(root , overSightInfo);
    }

    private Element createRegulatoryAuthority(StudyProtocolDTO spDTO , Document doc) throws PAException {
        StudyRegulatoryAuthorityDTO sraDTO = 
                        studyRegulatoryAuthorityService.getCurrentByStudyProtocol(spDTO.getIdentifier());
        if (sraDTO == null) {
            return null;
        }
        String data = null;
        RegulatoryAuthority ra = regulatoryInformationService.
                    get(Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
        Country country =  regulatoryInformationService.getRegulatoryAuthorityCountry(
                Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
        if (country != null && ra != null) {
            data = country.getName() + " : " + ra.getAuthorityName();
        } else if (country != null) {
            data = country.getName();
        } else if (ra != null) {
            data = ra.getAuthorityName();
        }
        return createElement("regulatory_authority" ,  data , doc);

    }

    private Element createIrbInfo(StudyProtocolDTO spDTO , Document doc) throws PAException {
        Element irbInfo  = doc.createElement("irb_info");

        if (spDTO.getReviewBoardApprovalRequiredIndicator() != null
                && spDTO.getReviewBoardApprovalRequiredIndicator().getValue() != null
                && !spDTO.getReviewBoardApprovalRequiredIndicator().getValue().booleanValue()) {
            appendElement(irbInfo , createElement("approval_status" ,  "Not required" , doc));
            return irbInfo;
        }

        List<StudySiteDTO> dtos = new ArrayList<StudySiteDTO>();
        StudySiteDTO dto = new StudySiteDTO();
        dto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.STUDY_OVERSIGHT_COMMITTEE));
        dtos.add(dto);
        List<StudySiteDTO> spDTOs = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), dtos);
        CorrelationUtils cUtils = new CorrelationUtils();
        for (StudySiteDTO spart : spDTOs) {
            if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        spart.getReviewBoardApprovalStatusCode().getCode())
               || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                        spart.getReviewBoardApprovalStatusCode().getCode())
               || ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode().equals(
                                spart.getReviewBoardApprovalStatusCode().getCode())
               || ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode().equals(
                                        spart.getReviewBoardApprovalStatusCode().getCode())) {

                appendElement(irbInfo , createElement("approval_status" ,
                        spart.getReviewBoardApprovalStatusCode() , doc));

                appendElement(irbInfo , createElement("approval_number" ,
                        spart.getReviewBoardApprovalNumber() , doc));
                
                Organization paOrg = cUtils.getPAOrganizationByIi(spart.getOversightCommitteeIi());
                if (paOrg != null) {
                    OrganizationDTO poOrg = null;
                    try {
                        poOrg = PoRegistry.getOrganizationEntityService().
                            getOrganization(IiConverter.convertToPoOrganizationIi(paOrg.getIdentifier()));
                    } catch (NullifiedEntityException e) {
                        throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier() , e);
                    }
                    appendElement(irbInfo , createElement("name" ,  paOrg.getName() , doc));
                    appendElement(irbInfo , createElement("affiliation" ,  
                            spart.getReviewBoardOrganizationalAffiliation() , doc));
                    Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                    for (Object tel : telList) {
                        if (tel instanceof TelPhone) {
                            appendElement(irbInfo ,
                                    createElement(PHONE , ((TelPhone) tel).getValue().getSchemeSpecificPart(),
                                            PAAttributeMaxLen.LEN_30 , doc));
                            break;
                        }
                    }

                    for (Object tel : telList) {
                        if (tel instanceof TelEmail) {
                            appendElement(irbInfo , createElement(EMAIL  ,
                                  ((TelEmail) tel).getValue().getSchemeSpecificPart(), PAAttributeMaxLen.LEN_254, doc));
                            break;
                        }
                    } // for

                    appendElement(irbInfo , createElement("full_address" ,
                            convertToAddress(poOrg.getPostalAddress()) , doc));
                    break;
                }
            } // pa org
        } // for

        return irbInfo;
    } // method

    private void createIdInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        //create info element

        Element idInfo = doc.createElement("id_info");
       
        StudySiteDTO spartDTO = new StudySiteDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
        List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), spartDTO);
        for (StudySiteDTO spart : sParts) {
            appendElement(idInfo , createElement("org_study_id" , spart.getLocalStudyProtocolIdentifier() , doc));
            break;
        }
       appendElement(idInfo , createElement("secondary_id" , spDTO.getAssignedIdentifier().getExtension(), doc));
       
       RegistryUser registryUser = registryUserService.getUser(StConverter.convertToString(spDTO.getUserLastCreated()));
       String prsOrgName = "replace with PRS Organization Name you log in with";
       if (PAUtil.isNotEmpty(registryUser.getPrsOrgName())) {
           prsOrgName = registryUser.getPrsOrgName();
       } 
       appendElement(idInfo , createElement("org_name" , prsOrgName , doc));
       
       if (idInfo.hasChildNodes()) {
            appendElement(root, idInfo);
       }

    }



    private void createIndInfo(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        //create info element

        List<StudyIndldeDTO> ideDtos = studyIndldeService.getByStudyProtocol(spDTO.getIdentifier());
        if (ideDtos == null || ideDtos.isEmpty()) {
            appendElement(root , createElement("is_ind_study" , NO, doc));
            return;
        }
        StudyIndldeDTO ideDTO = ideDtos.get(0);
        appendElement(root , createElement("is_ind_study" , YES, doc));
        Element idInfo = doc.createElement("ind_info");
        appendElement(idInfo , createElement("ind_grantor" , ideDTO.getGrantorCode(), doc));
        appendElement(idInfo , createElement("ind_number" , ideDTO.getIndldeNumber(), doc));
        appendElement(idInfo , createElement("has_expanded_access" ,
                convertBLToString(ideDTO.getExpandedAccessIndicator()), doc));

        if (idInfo.hasChildNodes()) {
            appendElement(root, idInfo);
        }

    }

    private void createEligibility(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        List<PlannedEligibilityCriterionDTO> paECs = plannedActivityService
                                            .getPlannedEligibilityCriterionByStudyProtocol(spDTO.getIdentifier());
       
            
        if (paECs == null || paECs.isEmpty()) {
            return;
        }
        Element eligibility = doc.createElement("eligibility");
        String genderCode = null;
        String criterionName = null;
        String descriptionText = null;
        BigDecimal minAge = BigDecimal.ZERO;
        String minUnit = "";
        BigDecimal maxAge = BigDecimal.ZERO;
        String maxUnit = "";
        StringBuffer incCrit = new StringBuffer();
        StringBuffer nullCrit = new StringBuffer();
        StringBuffer exCrit = new StringBuffer();
        Ivl<Pq> pq = null;
        BigDecimal value;
        String unit;
        String operator;
        Boolean incIndicator = null;
        //sorts the list on display order
        Collections.sort(paECs, new Comparator<PlannedEligibilityCriterionDTO>() {
            public int compare(PlannedEligibilityCriterionDTO o1, PlannedEligibilityCriterionDTO o2) {
              return (!PAUtil.isIntNull(o1.getDisplayOrder()) && !PAUtil.isIntNull(o2.getDisplayOrder())) 
                     ? o1.getDisplayOrder().getValue().compareTo(o2.getDisplayOrder().getValue()) : 0;
             }
         });
        for (PlannedEligibilityCriterionDTO paEC : paECs) {
            criterionName = StConverter.convertToString(paEC.getCriterionName());
            descriptionText  = StConverter.convertToString(paEC.getTextDescription());
            incIndicator = paEC.getInclusionIndicator() != null ? paEC.getInclusionIndicator().getValue() : null;
            pq = paEC.getValue();
            if (criterionName != null && criterionName.equalsIgnoreCase("GENDER")
                    && paEC.getEligibleGenderCode() != null) {
                genderCode = paEC.getEligibleGenderCode().getCode();
            } else if (criterionName != null && criterionName.equalsIgnoreCase("AGE")) {
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
                    nullCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ').
                        append(operator).append(' ').append(unit).append('\n');
                } else if (incIndicator) {
                    incCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ').
                       append(operator).append(' ').append(unit).append('\n');
                } else {
                    exCrit.append(TAB).append(DASH).append(criterionName).append(' ').append(value).append(' ').
                       append(operator).append(' ').append(unit).append('\n');
                }
            }

        } // for loop
        StringBuffer data = new StringBuffer();
        data.append('\n');
        if (nullCrit.length() > 1) {
            data.append("Criteria \n" + nullCrit.toString() + "\n");
        }
        if (incCrit.length() > 1) {
            data.append("Inclusion \n").append(incCrit).append('\n');
        }
        if (exCrit.length() > 1) {
            data.append("Exclusion \n").append(exCrit).append('\n');
        }
        if (data.length() > 1) {
            createCdataBlock("criteria", StConverter.convertToSt(data.toString()),
                    PAAttributeMaxLen.LEN_15000 , doc, eligibility);
        }
        appendElement(eligibility,
                createElement("healthy_volunteers", spDTO.getAcceptHealthyVolunteersIndicator(), doc));
        appendElement(eligibility, createElement("gender", genderCode, doc));
        appendElement(eligibility, createElement("minimum_age", convertToYears(minAge , minUnit) , doc));
        appendElement(eligibility, createElement("maximum_age", convertToYears(maxAge , maxUnit) , doc));
        if (eligibility.hasChildNodes()) {
            appendElement(root , eligibility);
        }
    }
   
    private static String convertToYears(BigDecimal b , String unit) {
        int age = 0;
        if (b.intValue() == 0 || b.intValue() ==  MAX_AGE) {
            return "N/A";
        } else if (unit == null) {
            return null;
        } else if (unit.equalsIgnoreCase("Years")) {
            age = b.intValue();
        } else if (unit.equalsIgnoreCase("months")) {
            age = b.intValue() / MONTHS;
        } else if (unit.equalsIgnoreCase("days")) {
            age = b.intValue() / DAYS;
        } else if (unit.equalsIgnoreCase("hours")) {
            age = b.intValue() / DAYS / HOURS;
        } else if (unit.equalsIgnoreCase("minutes")) {
            age = b.intValue() / DAYS / HOURS / MINUTES;
        } else {
            age = b.intValue();
        }
        return Integer.valueOf(age).toString();
    }
    private void createArmGroup(StudyProtocolDTO spDTO , Document doc , Element root) throws PAException {
        List<ArmDTO> arms = armService.getByStudyProtocol(spDTO.getIdentifier());
       
        if (arms == null || arms.isEmpty()) {
            return;
        }
        for (ArmDTO armDTO : arms) {
            Element armGroup = doc.createElement("arm_group");
            appendElement(armGroup, createElement("arm_group_label" , armDTO.getName() , doc));
            appendElement(armGroup, createElement("arm_type" , armDTO.getTypeCode() , doc));
            createTextBlock("arm_group_description", armDTO.getDescriptionText(),
                    PAAttributeMaxLen.LEN_1000 , doc, armGroup);
            if (armGroup.hasChildNodes()) {
                root.appendChild(armGroup);
            }
        }
    }

      private void createIntervention(Ii studyProtocolIi , Document doc , Element root) throws PAException {
        List<PlannedActivityDTO> paList =  plannedActivityService.getByStudyProtocol(studyProtocolIi);
      
        for (PlannedActivityDTO pa : paList) {
            if (PAUtil.isTypeIntervention(pa.getCategoryCode())) {
                Element intervention = doc.createElement("intervention");
                InterventionDTO i = interventionService.get(pa.getInterventionIdentifier());
                if (pa.getSubcategoryCode() != null && pa.getSubcategoryCode().getCode() != null) {
                    appendElement(intervention, createElement("intervention_type" , 
                            pa.getSubcategoryCode().getCode() , doc));
                }
                appendElement(intervention,
                        createElement("intervention_name" , i.getName() , PAAttributeMaxLen.LEN_160, doc));
                createTextBlock("intervention_description", pa.getTextDescription(),
                        PAAttributeMaxLen.LEN_1000 , doc, intervention);
                List<InterventionAlternateNameDTO> ianList = 
                                        interventionAlternateNameService.getByIntervention(i.getIdentifier()); 
                          
                int cnt = 1; 
                List<InterventionAlternateNameDTO> interventionNames = new ArrayList<InterventionAlternateNameDTO>();
                
                for (InterventionAlternateNameDTO ian : ianList) {
                   if (ian.getNameTypeCode().getValue() != null 
                           && (ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.SYNONYM) 
                           || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.ABBREVIATION)
                           || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.US_BRAND_NAME)
                           || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.FOREIGN_BRAND_NAME)
                           || ian.getNameTypeCode().getValue().equalsIgnoreCase(PAConstants.CODE_NAME))) {
                       
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
                
                for (InterventionAlternateNameDTO ian : interventionNames) {                    
                    appendElement(intervention,
                            createElement("intervention_other_name" , ian.getName(), PAAttributeMaxLen.LEN_160 , doc));
                    
                }

                List<ArmDTO> armDtos = armService.getByPlannedActivity(pa.getIdentifier());
                for (ArmDTO armDTO : armDtos) {
                    appendElement(intervention,
                            createElement("arm_group_label" , armDTO.getName() , PAAttributeMaxLen.LEN_62 , doc));
                }
                if (intervention.hasChildNodes()) {
                    root.appendChild(intervention);
                }

            }

        }

    }


    private Element createStudyDesign(StudyProtocolDTO spDTO , Document doc) throws PAException {
        Element studyDesign = doc.createElement("study_design");

        if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("InterventionalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Interventional", doc));
            appendElement(studyDesign, createInterventional(spDTO , doc));
        } else if (spDTO.getStudyProtocolType().getValue().equalsIgnoreCase("ObservationalStudyProtocol")) {
            appendElement(studyDesign, createElement("study_type", "Observational", doc));
            appendElement(studyDesign, createObservational(spDTO , doc));
        }
        return studyDesign;
    }
    private Element createInterventional(StudyProtocolDTO spDTO , Document doc) throws PAException {
        InterventionalStudyProtocolDTO ispDTO = studyProtocolService
                                                    .getInterventionalStudyProtocol(spDTO.getIdentifier());
      
        Element invDesign = doc.createElement("interventional_design");
        appendElement(invDesign, createElement("interventional_subtype", ispDTO.getPrimaryPurposeCode(), doc));
        appendElement(invDesign, createElement("phase", ispDTO.getPhaseCode(), doc));
        appendElement(invDesign, createElement("allocation", ispDTO.getAllocationCode(), doc));
        appendElement(invDesign, createElement("masking", ispDTO.getBlindingSchemaCode(), doc));
        if (ispDTO.getBlindedRoleCode() != null) {
            List<Cd> cds =  DSetConverter.convertDsetToCdList(ispDTO.getBlindedRoleCode());
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
        appendElement(invDesign, createElement("assignment", ispDTO.getDesignConfigurationCode(), doc));
        appendElement(invDesign, createElement("endpoint", ispDTO.getStudyClassificationCode(), doc));
        appendElement(invDesign, createElement("number_of_arms", ispDTO.getNumberOfInterventionGroups() , doc));
        return invDesign;
    }

    private Element createObservational(StudyProtocolDTO spDTO , Document doc) throws PAException {
        ObservationalStudyProtocolDTO ospDTO = 
                                        studyProtocolService.getObservationalStudyProtocol(spDTO.getIdentifier());
      
        Element obsDesign = doc.createElement("observational_design");
        appendElement(obsDesign, createElement("timing", ospDTO.getTimePerspectiveCode(), doc));
        appendElement(obsDesign, createElement("observational_study_design", ospDTO.getStudyModelCode(), doc));
        appendElement(obsDesign, createElement("biospecimen_retention", ospDTO.getBiospecimenRetentionCode(), doc));
        appendElement(obsDesign, createElement("biospecimen_description", ospDTO.getBiospecimenDescription(), doc));
        appendElement(obsDesign, createElement("number_of_groups", ospDTO.getNumberOfGroups(), doc));
        return obsDesign;
    }

    private static void createPrimaryOutcome(List<StudyOutcomeMeasureDTO> somDtos ,  Document doc , Element root)
    throws PAException {
        if (somDtos == null || somDtos.isEmpty()) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element po = createElement("primary_outcome", doc);
                appendElement(po, createElement("outcome_measure",
                        smDTO.getName().getValue(), PAAttributeMaxLen.LEN_254 , doc));
                appendElement(po,
                        createElement("outcome_safety_issue", convertBLToString(smDTO.getSafetyIndicator()), doc));
                appendElement(po, createElement("outcome_time_frame",
                        smDTO.getTimeFrame().getValue(), PAAttributeMaxLen.LEN_254 ,  doc));
                if (po.hasChildNodes()) {
                    appendElement(root, po);
                }
            }
        }
    }
    private static void createSecondaryOutcome(List<StudyOutcomeMeasureDTO> somDtos , Document doc , Element root)
    throws PAException {
        if (somDtos == null || somDtos.isEmpty()) {
            return;
        }
        for (StudyOutcomeMeasureDTO smDTO : somDtos) {
            if (!smDTO.getPrimaryIndicator().getValue().booleanValue()) {
                Element om = createElement("secondary_outcome", doc);
                appendElement(om, createElement("outcome_measure",
                         smDTO.getName().getValue(), PAAttributeMaxLen.LEN_254 , doc));
                appendElement(om,
                        createElement("outcome_safety_issue", convertBLToString(smDTO.getSafetyIndicator()), doc));
                appendElement(om, createElement("outcome_time_frame",
                        smDTO.getTimeFrame().getValue(), PAAttributeMaxLen.LEN_254 , doc));
                if (om.hasChildNodes()) {
                    appendElement(root, om);
                }
            }
        }
    }

    private void createSponsors(Ii studyProtocolIi  , Document doc , Element root) throws PAException,
        NullifiedRoleException {
        Element sponsors = doc.createElement("sponsors");
        Element lead = createLeadSponsor(studyProtocolIi , doc);
        if (lead != null && lead.hasChildNodes()) {
            appendElement(sponsors , lead);
        }
        Element collaborator = createCollaborator(studyProtocolIi , doc);
        if (collaborator != null && collaborator.hasChildNodes()) {
            appendElement(sponsors , collaborator);
        }
        Element rp = createResponsibleParty(studyProtocolIi , doc);
        if (rp != null && rp.hasChildNodes()) {
            appendElement(sponsors , rp);
        }
        if (sponsors.hasChildNodes()) {
            appendElement(root , sponsors);
        }

    }
    private Element createResponsibleParty(Ii studyProtocolIi  , Document doc)
    throws PAException, NullifiedRoleException {
        Element responsibleParty = doc.createElement("resp_party");
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = 
               studyContactService.getByStudyProtocol(studyProtocolIi, scDto);
        DSet<Tel> dset = null;
        CorrelationUtils cUtils = new CorrelationUtils();
        Person person = null;
        String resPartyContactName = null;
        Organization sponsor = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
            person = cUtils.getPAPersonByIi(scDto.getClinicalResearchStaffIi());
            resPartyContactName = person.getFullName();
            StudySiteDTO spartDTO = new StudySiteDTO();
            spartDTO.setFunctionalCode(
                    CdConverter.convertToCd(StudySiteFunctionalCode.LEAD_ORGANIZATION));
            List<StudySiteDTO> sParts = studySiteService.getByStudyProtocol(studyProtocolIi, spartDTO);
            for (StudySiteDTO spart : sParts) {
                sponsor = cUtils.getPAOrganizationByIi(spart.getResearchOrganizationIi());
            }

        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spcDtos = studySiteContactService.getByStudyProtocol(studyProtocolIi, spart);
            if (spcDtos != null && !spcDtos.isEmpty()) {
                spart = spcDtos.get(0);
                dset = spart.getTelecomAddresses();
                PAContactDTO paCDto =  cUtils.getContactByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                resPartyContactName = paCDto.getResponsiblePartyContactName();

            }
            StudySiteDTO spDto = new StudySiteDTO();
            spDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
            List<StudySiteDTO> spDtos = studySiteService.getByStudyProtocol(studyProtocolIi, spDto);
            if (spDtos != null && !spDtos.isEmpty()) {
                spDto = spDtos.get(0);
                sponsor = new CorrelationUtils().getPAOrganizationByIi(spDto.getResearchOrganizationIi());
            }

        }
        if (resPartyContactName != null) {
            appendElement(responsibleParty , createElement("name_title" , resPartyContactName , doc));
        }
        if (sponsor != null) {
            appendElement(responsibleParty , createElement("organization" , sponsor.getName() , doc));
        }
        if (dset != null) {
            List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
            List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
            if (phones != null && !phones.isEmpty()) {
                appendElement(responsibleParty , createElement(PHONE , phones.get(0), PAAttributeMaxLen.LEN_30 , doc));
            }
            if (emails != null && !emails.isEmpty()) {
                appendElement(responsibleParty , createElement(EMAIL , emails.get(0) , PAAttributeMaxLen.LEN_254, doc));
            }
        }
        return responsibleParty;
    }
    private Element createLeadSponsor(Ii studyProtocolIi , Document doc) throws PAException {
        Organization sponsor = ocsr.getOrganizationByFunctionRole(
                studyProtocolIi, CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        Element lead = doc.createElement("lead_sponsor");
        appendElement(lead, createElement("agency" , sponsor.getName() , PAAttributeMaxLen.LEN_160, doc));
        return lead;
    }

    private static Element createCollaborator(Ii studyProtocolIi , Document doc) throws PAException {
        OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean();

        List<Organization> orgs = osb.getOrganizationByStudySite(
                Long.valueOf(studyProtocolIi.getExtension()), StudySiteFunctionalCode.COLLABORATORS);

        if (orgs == null || orgs.isEmpty()) {
            return null;
        }
        Element collaborator = doc.createElement("collaborator");
        for (Organization org : orgs) {
            appendElement(collaborator , createElement("agency" , org.getName(), PAAttributeMaxLen.LEN_160, doc));
            break;
        }
        return collaborator;
    }

    private void createLocation(StudyProtocolDTO spDTO , Document doc , Element root) 
        throws PAException, NullifiedRoleException {

        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        List<StudySiteDTO> spList = studySiteService.getByStudyProtocol(spDTO.getIdentifier(), srDTO);
        CorrelationUtils cUtils = new CorrelationUtils();
        for (StudySiteDTO sp : spList) {
            Element location = doc.createElement("location");
            Element facility = doc.createElement("facility");
            Element address = doc.createElement("address");

            StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService
                                              .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
        
            Organization orgBo = cUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());

            appendElement(facility , createElement("name" , orgBo.getName() , doc));
            appendElement(address , createElement("city" , orgBo.getCity() , doc));
            appendElement(address , createElement("state" , orgBo.getState() , doc));
            appendElement(address , createElement("zip" , orgBo.getPostalCode() , doc));
            appendElement(address , createElement("country" , orgBo.getCountryName() , doc));
            appendElement(facility , address);
            appendElement(location , facility);
            if (ssas != null) {
                appendElement(location , createElement("status" , ssas.getStatusCode() , doc));
            }

            List<StudySiteContactDTO> spcDTOs = studySiteContactService.getByStudySite(sp.getIdentifier());
            appendElement(root , location);
            createContact(spcDTOs , location , doc);
            createInvestigators(spcDTOs , location , doc);
        }
    }

    private static void createInvestigators(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
    throws PAException {
        CorrelationUtils corr = new CorrelationUtils();
        for (StudySiteContactDTO spcDTO : spcDTOs) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                    equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            Person p = corr.getPAPersonByIi(spcDTO.getClinicalResearchStaffIi());
            Element investigator = doc.createElement("investigator");
            appendElement(investigator , createElement(FIRST_NAME , p.getFirstName() , doc));
            appendElement(investigator , createElement("middle_name" , p.getMiddleName() ,
                            PAAttributeMaxLen.LEN_2, doc));
            appendElement(investigator , createElement(LAST_NAME , p.getLastName() , doc));
            appendElement(investigator , createElement("role" , spcDTO.getRoleCode() , doc));
            if (investigator.hasChildNodes()) {
                appendElement(location , investigator);
            }
        }
    }

    private static void createContact(List<StudySiteContactDTO> spcDTOs, Element location, Document doc)
    throws PAException, NullifiedRoleException {
        CorrelationUtils corr = new CorrelationUtils();
        for (StudySiteContactDTO spcDTO : spcDTOs) {

            if (!StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                    equals(spcDTO.getRoleCode().getCode())) {
                continue;
            }
            List<String> phones = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "PHONE");
            List<String> emails = DSetConverter.convertDSetToList(spcDTO.getTelecomAddresses(), "EMAIL");
            Element contact = doc.createElement("contact");
            if (spcDTO.getClinicalResearchStaffIi() != null) {
                Person p = corr.getPAPersonByIi(spcDTO.getClinicalResearchStaffIi());
                appendElement(contact , createElement(FIRST_NAME , p.getFirstName() , doc));
                appendElement(contact , createElement("middle_name" , p.getMiddleName() ,
                    PAAttributeMaxLen.LEN_2 , doc));
                appendElement(contact , createElement(LAST_NAME , p.getLastName() , doc));
            } else if (spcDTO.getOrganizationalContactIi() != null) {
                PAContactDTO paCDto =  corr.getContactByPAOrganizationalContactId((
                        Long.valueOf(spcDTO.getOrganizationalContactIi().getExtension())));
                appendElement(contact , createElement("last_name" , paCDto.getTitle() , doc));
            }
            if (phones != null && !phones.isEmpty()) {
                appendElement(contact , createElement(PHONE , phones.get(0) , PAAttributeMaxLen.LEN_30 , doc));
            }
            if (emails != null && !emails.isEmpty()) {
                appendElement(contact , createElement(EMAIL , emails.get(0) , PAAttributeMaxLen.LEN_254 , doc));
            }
            if (contact.hasChildNodes()) {
                appendElement(location , contact);
            }
        }
    }
   /* private void createObjective(StudyProtocolDTO spDTO, Document doc, Element root) throws PAException {
        List<StudyObjectiveDTO> studyObjList = studyObjectiveService.getByStudyProtocol(spDTO.getIdentifier());
        StringBuffer objectiveData = new StringBuffer();
        for (StudyObjectiveDTO dto : studyObjList) {
            if (!StConverter.convertToString(dto.getDescription()).trim().equals("")) {
                if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.PRIMARY.getCode())) {
                    objectiveData.append('\n');
                    objectiveData.append(StudyObjectiveTypeCode.PRIMARY.getCode());
                    objectiveData.append('\n');
                    StringTokenizer stoken = new StringTokenizer(StConverter.convertToString(dto.getDescription()),
                        ".");
                    while (stoken.hasMoreElements()) { 
                        objectiveData.append(TAB);
                        objectiveData.append(DASH);
                        objectiveData.append(stoken.nextElement().toString());
                        objectiveData.append('\n');
                    } 
                }
                if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.SECONDARY.getCode())) {
                    objectiveData.append('\n');
                    objectiveData.append(StudyObjectiveTypeCode.SECONDARY.getCode());
                    objectiveData.append('\n');
                    StringTokenizer stoken = new StringTokenizer(StConverter.convertToString(dto.getDescription()),
                        ".");
                    while (stoken.hasMoreElements()) { 
                        objectiveData.append(TAB);
                        objectiveData.append(DASH);
                        objectiveData.append(stoken.nextElement().toString().trim());
                        objectiveData.append('\n');
                    } 
                }
                if (dto.getTypeCode().getCode().equals(StudyObjectiveTypeCode.TERNARY.getCode())) {
                    objectiveData.append('\n');
                    objectiveData.append(StudyObjectiveTypeCode.TERNARY.getCode());
                    objectiveData.append('\n');
                    StringTokenizer stoken = new StringTokenizer(StConverter.convertToString(dto.getDescription()),
                            ".");
                    while (stoken.hasMoreElements()) { 
                        objectiveData.append(TAB);
                        objectiveData.append(DASH);
                        objectiveData.append(stoken.nextElement().toString().trim());
                        objectiveData.append('\n');
                    } 
                }    
            }
        }
        if (spDTO.getScientificDescription() != null && spDTO.getScientificDescription().getValue() != null) {
            objectiveData.append(NEW_LINE);
            objectiveData.append("Outline:\n");
            StringTokenizer stoken = new StringTokenizer(
                    StConverter.convertToString(spDTO.getScientificDescription()).trim(), ".");
            while (stoken.hasMoreElements()) { 
                objectiveData.append(TAB);
                objectiveData.append(DASH);
                String s = stoken.nextElement().toString().trim().replaceAll("\\n", " ");
                String s1 = s.replaceAll("\\r", " ");
                objectiveData.append(s1);
                objectiveData.append('\n');
            } 
        }
        Integer projectedAccrual = IvlConverter.convertInt().convertLow(spDTO.getTargetAccrualNumber());
        if (projectedAccrual != null) {
            objectiveData.append(NEW_LINE);
            objectiveData.append("Projected Accrual:");
            objectiveData.append(TAB);
            objectiveData.append(DASH);
            if (projectedAccrual > 0) { 
                objectiveData.append("A total of  ").append(projectedAccrual)
                .append(" patients will be accrued for this study");
            } else {
                objectiveData.append(projectedAccrual).append(" patient.");
            }
            objectiveData.append(NEW_LINE);
        }
        if (objectiveData.length() > 1) {
            StringBuffer objectiveTitle = new StringBuffer();
            objectiveTitle.append(NEW_LINE);
            objectiveTitle.append("Objectives:");
            objectiveTitle.append(DASH);
            objectiveTitle.append(NEW_LINE);
            objectiveTitle.append(objectiveData);
            createCdataBlock("detailed_description", StConverter.convertToSt(objectiveTitle.toString()),
                        PAAttributeMaxLen.LEN_15000 , doc, root);
         }     

    }*/

    private static void createCdataBlock(final String elementName ,  final St data , int maxLen ,
            Document doc , Element root)
    throws PAException {
    if (data == null || data.getValue() == null || PAUtil.isEmpty(data.getValue())) {
        return;
    }
    Element elementTxt = doc.createElement(TEXT_BLOCK);
    Element elementTag = doc.createElement(elementName);
    Text text = doc.createCDATASection(PAUtil.stringSetter(data.getValue() , maxLen));
    elementTxt.appendChild(text);
    elementTag.appendChild(elementTxt);
    root.appendChild(elementTag);
    }

    private static void createTextBlock(final String elementName ,  final St data , int maxLen ,
                Document doc , Element root)
    throws PAException {
        if (data == null || data.getValue() == null || PAUtil.isEmpty(data.getValue())) {
            return;
        }
        appendElement(createElement(elementName, doc), createElement(TEXT_BLOCK, data.getValue(), maxLen , doc) , root);

    }
    private static Element createElement(final String elementName , final Document doc) throws PAException {
        if (PAUtil.isEmpty(elementName)) {
            LOG.error("Elementname is null");
            throw new PAException("Element name is null");
        }
        return doc.createElement(elementName);
    }

    private static Element createElement(String elementName , String data , Document doc) {
        if (data == null || elementName == null) {
            return null;
        }
        Element element = doc.createElement(elementName);
        Text text = doc.createTextNode(data);
        element.appendChild(text);
        return element;
    }
    
    private static Element createElement(String elementName , String st , int maxLength , Document doc) {
        if (st == null || elementName == null || st == null) {
            return null;
        }
        return createElement(elementName  , PAUtil.stringSetter(st , maxLength) , doc);
    }

    private static Element createElement(String elementName , Cd cd , Document doc) {
        if (cd == null || elementName == null || cd.getCode() == null) {
            return null;
        }
        return createElement(elementName  , convertToCtValues(cd) , doc);
    }
    private static Element createElement(String elementName , St st , Document doc) {
        if (st == null || elementName == null || st.getValue() == null) {
            return null;
        }
        return createElement(elementName  , st.getValue() , doc);
    }

    private static Element createElement(String elementName , St st , int maxLength , Document doc) {
        if (st == null || elementName == null || st.getValue() == null) {
            return null;
        }
        return createElement(elementName  , PAUtil.stringSetter(st.getValue(), maxLength)  , doc);
    }

    private static Element createElement(String elementName , Int i , Document doc) {
        if (i == null || elementName == null || i.getValue() == null) {
            return null;
        }
        return createElement(elementName  , i.getValue().toString() , doc);
    }
    private static Element createElement(String elementName , Bl bl , Document doc) {
        if (bl == null || elementName == null || bl.getValue() == null) {
            return null;
        }
        return createElement(elementName  , convertBLToString(bl) , doc);
    }

    private static void  createElement(final String elementName , String data , Document doc ,  Element root) {
        Element element = createElement(elementName , data , doc);
        if (element != null) {
            root.appendChild(element);
        }
    }
    private static void appendElement(Element parent, Element child) {
        if (parent != null && child != null) {
            parent.appendChild(child);
        }
    }
    private static void appendElement(Element parent, Element child , Element root) {
        if (parent != null && child != null && root != null) {
            parent.appendChild(child);
            root.appendChild(parent);
        }
    }


    ///////////////////////////////////// methods for get the data

    private StudyProtocolDTO getStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        if (spDTO == null) {
            throw new PAException("Study Protocol is not available for given id = " + studyProtocolIi.getExtension());
        }
        return spDTO;
    }
    

    ///  utilitiy methods
    private static String convertBLToString(Bl bl) {
        if (bl == null) {
            return null;
        }
        Boolean b = bl.getValue();
        if (b != null && b.booleanValue()) {
            return YES;
        } else {
            return NO;
        }
    }

    private static String convertToAddress(Ad ad) {

        if (ad == null || ad.getPart() == null || ad.getPart().isEmpty()) {
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

    private static String convertTsToYYYYMMFormart(Ts isoTs) {
        String yyyyMM = "";
        Timestamp ts = TsConverter.convertToTimestamp(isoTs);
        if (ts == null) {
            return  null;
        }
        String dateStr  = PAUtil.normalizeDateString(ts.toString());
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        DateFormat formatter1 =  new SimpleDateFormat("yyyy-MM" , Locale.getDefault());
        Date date;
        try {
            date = formatter.parse(dateStr);
            yyyyMM = formatter1.format(date);
        } catch (ParseException e) {
            yyyyMM = "";
        }
        return yyyyMM;
    }

    private static void createCtGovValues() {
        nv.put(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode() , "Approved");
        nv.put(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode() , "Exempt");
        nv.put(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode() , "Not Required");
        nv.put(AllocationCode.RANDOMIZED_CONTROLLED_TRIAL.getCode(), "Randomized");
        nv.put(AllocationCode.NON_RANDOMIZED_TRIAL.getCode(), "Non-randomized");
        nv.put(BlindingSchemaCode.OPEN.getCode() , "Open Label");
        nv.put(BlindingSchemaCode.SINGLE_BLIND.getCode(), "Single Blind");
        nv.put(BlindingSchemaCode.DOUBLE_BLIND.getCode(), "Double Blind");
        nv.put(DesignConfigurationCode.SINGLE_GROUP.getCode(), "Single Group Assignment");
        nv.put(DesignConfigurationCode.PARALLEL.getCode(), "Parallel Assignment");
        nv.put(DesignConfigurationCode.CROSSOVER.getCode() , "Crossover Assignment");
        nv.put(DesignConfigurationCode.FACTORIAL.getCode() , "Factorial Assignment");
        nv.put(StudyClassificationCode.SAFETY.getCode() , "Safety Study");
        nv.put(StudyClassificationCode.EFFICACY.getCode(), "Efficacy Study");
        nv.put(StudyClassificationCode.SAFETY_OR_EFFICACY.getCode() , "Safety/Efficacy Study");
        nv.put(StudyClassificationCode.BIO_EQUIVALENCE.getCode() , "Bio-equivalence Study");
        nv.put(StudyClassificationCode.BIO_AVAILABILITY.getCode() , "Bio-availability Study");
        nv.put(StudyClassificationCode.PHARMACOKINETICS.getCode() , "Pharmacokinetics Study");
        nv.put(StudyClassificationCode.PHARMACODYNAMICS.getCode() , "Pharmacodynamics Study");
        nv.put(StudyClassificationCode.PHARMACOKINETICS_OR_DYNAMICS.getCode() , "Pharmacokinetics/dynamics Study");
        nv.put(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode() , "Principal Investigator");
        nv.put(AllocationCode.NA.getCode(), NA);
        nv.put(PhaseCode.OTHER.getCode() , NA);
        nv.put(PhaseCode.PILOT.getCode() , NA);
        String other = "Other";
        nv.put(PrimaryPurposeCode.EARLY_DETECTION.getCode(), other);
        nv.put(PrimaryPurposeCode.EPIDEMIOLOGIC.getCode(), other);
        nv.put(PrimaryPurposeCode.OBSERVATIONAL.getCode(), other);
        nv.put(PrimaryPurposeCode.OUTCOME.getCode(), other);
        nv.put(PrimaryPurposeCode.ANCILLARY.getCode(), other);
        nv.put(PrimaryPurposeCode.CORRELATIVE.getCode(), other);
    }

    private static String convertToCtValues(Cd cd) {
        if (nv.containsKey(cd.getCode())) {
            return nv.get(cd.getCode());
        } else {
            return cd.getCode();
        }
    }

}
