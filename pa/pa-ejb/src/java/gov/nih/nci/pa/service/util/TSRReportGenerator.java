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

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.util.List;

import org.apache.log4j.Logger;


/** 
* service bean for generating ct.gov.xml.
* 
* @author Naveen Amiruddin
* @since 01/20/2009
*/
@SuppressWarnings("PMD")
public class TSRReportGenerator {
    
    private static final String BR = "<BR>";
    private static final String TBL_B = "<TABLE>";
    private static final String TBL_E = "</TABLE>";
    private static final String TR_B = "<TR>";
    private static final String TR_E = "</TR>";
    private static final String TD_B = "<TD>";
    private static final String TD_E = "</TD>";
    private static final String BLD_B = "<B>";
    private static final String BLD_E = "</B>";
    private static final String NO_DATA = "No Data Available";
    private static final String EMPTY = "";
    private static final String YES = "Yes";
    private static final String NO = "No";
    private static final Logger LOG  = Logger.getLogger(TSRReportGenerator.class);
    private CorrelationUtils correlationUtils = new CorrelationUtils();
    /**
     * @param studyProtocolIi ii of studyprotocol
     * @return String xml output
     * @throws PAException on error
     */
    public String generateTSRHtml(Ii studyProtocolIi) throws PAException {
        LOG.debug("Entering generateTSRXml");

        if (studyProtocolIi == null) {
            throw new PAException("Study Protocol Identifier is null");
        }
        StringBuffer htmldata = new StringBuffer();
        htmldata.append(BR).append(appendBoldData("Trial Summary Report"));
        StudyProtocolDTO spDTO = PoPaServiceBeanLookup.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        appendSecondaryIdentifiers(htmldata , spDTO);
        appendTitles(htmldata , spDTO);
        appendOrgsAndPersons(htmldata , spDTO.getIdentifier());
        apppendTrialStatus(htmldata , spDTO);
        appendRegulatoryInformation(htmldata , spDTO);
        appendHumanSubjectSafety(htmldata , spDTO);
        appendIdeIde(htmldata , spDTO);
        appendNihGrants(htmldata , spDTO);
        return htmldata.toString();
    }

    private void appendNihGrants(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {
        appendTitle(html, "NIH Grants");
        html.append(TBL_B);
        List<StudyResourcingDTO> funds = PoPaServiceBeanLookup.getStudyResourcingService().
                getstudyResourceByStudyProtocol(spDto.getIdentifier());
        boolean first = true;
        for (StudyResourcingDTO fund : funds) {
            if (first) {
                first = false;
                html.append(TR_B);
                appendTDAndData(html, "Funding Mechanism");
                appendTDAndData(html, "NIH Institution Code");
                appendTDAndData(html, "Serial Number");
                appendTDAndData(html, "NCI Division/Program Code");
                appendTDAndData(html, "Funding Type");
                appendTDAndData(html, "Grant Year");
                appendTDAndData(html, "Suffix");
                html.append(TR_E);
            }
            html.append(TR_B);
            appendTDAndData(html, fund.getFundingMechanismCode().getCode());
            appendTDAndData(html, fund.getNihInstitutionCode().getCode());
            appendTDAndData(html, fund.getSerialNumber().getValue().toString());
            appendTDAndData(html, fund.getNciDivisionProgramCode().getCode());
            appendTDAndData(html, fund.getFundingTypeCode().getCode());
            appendTDAndData(html, getData(fund.getSuffixGrantYear(), true));
            appendTDAndData(html, getData(fund.getSuffixOther(), true));
            html.append(TR_E);
        }
    }
    private void appendIdeIde(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {
        
        List<StudyIndldeDTO> indides =  PoPaServiceBeanLookup.getStudyIndldeService().
                getByStudyProtocol(spDto.getIdentifier());
        appendTitle(html, "IND/IDE");
        html.append(TBL_B);
        boolean first = true;
        for (StudyIndldeDTO indDto : indides) {
            if (first) {
                first = false;
                html.append(TR_B);
                appendTDAndData(html, "IND/IDE?");
                appendTDAndData(html, "Grantor");
                appendTDAndData(html, "Number");
                appendTDAndData(html, "Holder");
                appendTDAndData(html, "Expanded Access");
                appendTDAndData(html, "Expanded access status");
                html.append(TR_E);
            }
            html.append(TR_B);
            appendTDAndData(html, indDto.getIndldeTypeCode().getCode());
            appendTDAndData(html, indDto.getGrantorCode().getCode());
            appendTDAndData(html, getData(indDto.getIndldeNumber() , true));
            if (HolderTypeCode.ORGANIZATION.getCode().equals(indDto.getHolderTypeCode().getCode())) {
                appendTDAndData(html, "Lead Organization");
            } else if (HolderTypeCode.INVESTIGATOR.getCode().equals(indDto.getHolderTypeCode().getCode())) {
                appendTDAndData(html, "Principal Investigator");
            } else if (HolderTypeCode.NIH.getCode().equals(indDto.getHolderTypeCode().getCode())) {
                appendTDAndData(html , indDto.getNihInstHolderCode().getCode());
            } else if (HolderTypeCode.NCI.getCode().equals(indDto.getHolderTypeCode().getCode())) {
                appendTDAndData(html , indDto.getNciDivProgHolderCode().getCode());
            }
            appendTDAndData(html, convertBLToString(indDto.getExpandedAccessIndicator() , false));
            appendTDAndData(html , indDto.getExpandedAccessStatusCode().getCode());
            html.append(TR_E);
        }
        html.append(TBL_E);
    }
    private void appendHumanSubjectSafety(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {
        appendTitle(html, "Human Subject Safety");
        Boolean b = BlConverter.covertToBoolean(spDto.getReviewBoardApprovalRequiredIndicator());
        if (b != null && b) {
            List<StudyParticipationDTO> partList = PoPaServiceBeanLookup.getStudyParticipationService()
                        .getByStudyProtocol(spDto.getIdentifier());
            for (StudyParticipationDTO part : partList) {
                if (ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode().equals(
                        part.getReviewBoardApprovalStatusCode().getCode())
                    || ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode().equals(
                            part.getReviewBoardApprovalStatusCode().getCode())) {

                    html.append(appendData("Board Approval Status" , part.getReviewBoardApprovalStatusCode().getCode(), 
                            true , false));
                    html.append(appendData("Board Approval Number" , 
                            getData(part.getReviewBoardApprovalNumber() , true) ,    true , false));
                    Organization paOrg = correlationUtils.getPAOrganizationByPAOversightCommitteeId(
                            IiConverter.convertToLong(part.getOversightCommitteeIi()));
                    if (paOrg != null) {
                        OrganizationDTO poOrg = null;
                        try {
                            poOrg = PoPaServiceBeanLookup.getOrganizationEntityService().
                                getOrganization(IiConverter.converToPoOrganizationIi(paOrg.getIdentifier()));
                        } catch (NullifiedEntityException e) {
                            throw new PAException(" Po Identifier is nullified " + paOrg.getIdentifier() , e);
                        }
                        html.append(appendData("Name" , paOrg.getName(), true , false));

                        Organization affOrg = correlationUtils.getPAOrganizationByPAHealthCareFacilityId(
                                IiConverter.convertToLong(part.getHealthcareFacilityIi()));      
                        if (affOrg != null) {
                            html.append(appendData(" affiliated with " , affOrg.getName(), false , false));
                        }
                        html.append(appendData("Full Address" , AddressConverterUtil.convertToAddress(
                                poOrg.getPostalAddress()), true , false));
                        Object[] telList = poOrg.getTelecomAddress().getItem().toArray();
                        for (Object tel : telList) {
                            if (tel instanceof TelPhone) {
                                html.append(appendData("Phone" , 
                                        ((TelPhone) tel).getValue().getSchemeSpecificPart() , true , false));
                                break;
                            }
                        }

                        for (Object tel : telList) {
                            if (tel instanceof TelEmail) {
                                html.append(appendData("Email" , 
                                        ((TelEmail) tel).getValue().getSchemeSpecificPart() , true , false));
                                break;
                            }
                        } // for
                    } // po org
                }
            }
        }
    }
    private void appendRegulatoryInformation(StringBuffer html , StudyProtocolDTO spDto) throws  PAException {
        html.append(appendData("Reporting Dataset Method", 
                spDto.getAccrualReportingMethodCode().getCode(), true , false));
        appendTitle(html, "Regulatory Information");
        StudyRegulatoryAuthorityDTO sraDTO = PoPaServiceBeanLookup.getStudyRegulatoryAuthorityService().
                getByStudyProtocol(spDto.getIdentifier());
        if (sraDTO != null) {
            String data = null;
            RegulatoryAuthority ra = PoPaServiceBeanLookup.getRegulatoryInformationService().
                    get(Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
            
            Country country =  PoPaServiceBeanLookup.getRegulatoryInformationService().getRegulatoryAuthorityCountry(
                    Long.valueOf(sraDTO.getRegulatoryAuthorityIdentifier().getExtension()));
            if (country != null && ra != null) {
                data = country.getName() + " : " + ra.getAuthorityName();
            } else if (country != null) {
                data = country.getName();
            } else if (ra != null) {
                data = ra.getAuthorityName();
            }
            html.append(appendData("Trial Oversight Authority", data, true , false));
        }
        html.append(appendData("DMC Appointed?", convertBLToString(
                spDto.getDataMonitoringCommitteeAppointedIndicator(), true), true , false));
        html.append(appendData("FDA Regulated Intervention?", convertBLToString(
                spDto.getFdaRegulatedIndicator(), true), true , false));
        html.append(appendData("Section 801?", convertBLToString(
                spDto.getSection801Indicator(), true), true , false));
        List<StudyIndldeDTO> indIde = PoPaServiceBeanLookup.getStudyIndldeService().
            getByStudyProtocol(spDto.getIdentifier());
        if (indIde != null && indIde.size() > 0) {
            html.append(appendData("IND/IDE Study?", YES, true , false));
        } else {
            html.append(appendData("IND/IDE Study?", NO, true , false));
        }
        html.append(appendData("Delayed Posting?", convertBLToString(
                spDto.getDelayedpostingIndicator(), true), true , false));
        
    }
    
    private void appendSecondaryIdentifiers(StringBuffer html , StudyProtocolDTO studyProtocolDto) throws  PAException {
        StudyParticipationDTO spartDTO = new StudyParticipationDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION));
        List<StudyParticipationDTO> sParts = PoPaServiceBeanLookup.
            getStudyParticipationService().getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);
        html.append(TBL_B);
        appendTDColumnAndData(html , "NCI Identifier" , studyProtocolDto.getAssignedIdentifier().getExtension() , true);

        CorrelationUtils  cUtils = new CorrelationUtils();
        for (StudyParticipationDTO spart : sParts) {
            Organization o = cUtils.getPAOrganizationByPAResearchOrganizationId(
                    Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
            appendTDColumnAndData(html , o.getName() , getData(spart.getLocalStudyProtocolIdentifier() , true) , true);
            break;
        }
        spartDTO = new StudyParticipationDTO();
        spartDTO.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
        sParts = PoPaServiceBeanLookup.
            getStudyParticipationService().getByStudyProtocol(studyProtocolDto.getIdentifier(), spartDTO);        
        for (StudyParticipationDTO spart : sParts) {
            appendTDColumnAndData(html , "Clinical Trials Identifier" , 
                    getData(spart.getLocalStudyProtocolIdentifier() , true) , true);
            break;
        }
        html.append(TBL_E);
        
    }

    private void appendTitles(StringBuffer html , StudyProtocolDTO studyProtocolDto) {
        html.append(BR).append(BLD_B).append("Official Title : ").append(BLD_E).
            append(getData(studyProtocolDto.getOfficialTitle(), true));
        html.append(BR).append(BLD_B).append("Brief Title : ").append(BLD_E).
            append(getData(studyProtocolDto.getPublicTitle(), true));
        html.append(BR).append(BLD_B).append("Acronym : ").append(BLD_E).
            append(getData(studyProtocolDto.getAcronym(), true));

        html.append(BR).append(BR).append(BLD_B).append("Acronym : ").append(BLD_E).
        append(getData(studyProtocolDto.getAcronym(), true));

        html.append(BR).append(BLD_B).append("Brief Summary : ").append(BLD_E).
        append(getData(studyProtocolDto.getAcronym(), true));

        html.append(BR).append(BLD_B).append("Detailed Description : ").append(BLD_E).
            append(getData(studyProtocolDto.getScientificDescription(), true));

        html.append(BR).append(BLD_B).append("Keywords : ").append(BLD_E).
            append(getData(studyProtocolDto.getKeywordText(), true));

    }
    private void appendOrgsAndPersons(StringBuffer html , Ii studyProtocolIi) throws PAException {
        Organization sponsor = PoPaServiceBeanLookup.getOrganizationCorrelationService().getOrganizationByFunctionRole(
                studyProtocolIi, CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
        Organization lead = PoPaServiceBeanLookup.getOrganizationCorrelationService().getOrganizationByFunctionRole(
                studyProtocolIi, CdConverter.convertToCd(StudyParticipationFunctionalCode.LEAD_ORAGANIZATION));
        Person leadPi = null;
        Person centralContact = null;
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PoPaServiceBeanLookup.getStudyContactService().
                    getByStudyProtocol(studyProtocolIi, scDto);
        CorrelationUtils cUtils = new CorrelationUtils();
        html.append(appendData("Sponsor" , sponsor.getName(), true , false));
        html.append(appendData("Lead Organization" , lead.getName(), true , false));

        for (StudyContactDTO pi : scDtos) {
            leadPi = cUtils.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(pi.getClinicalResearchStaffIi().getExtension()));
            html.append(appendData("Principal Investigator" , leadPi.getFirstMiddleLastName(), true , false));
            html.append(" , ").append(lead.getName());
            break;
            
        }
        scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        scDtos = PoPaServiceBeanLookup.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        DSet dset = null;
        List<String> emails = null;
        List<String> phones = null;
        for (StudyContactDTO cc : scDtos) {
            centralContact = cUtils.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(cc.getClinicalResearchStaffIi().getExtension()));
            html.append(appendData("Central Contact" , centralContact.getFirstMiddleLastName() , true , false));
            dset = cc.getTelecomAddresses();
            emails = DSetConverter.convertDSetToList(dset, "EMAIL");
            if (emails != null && !emails.isEmpty()) {
                html.append(appendData("Email" , emails.get(0) , true , false));
            }
            phones = DSetConverter.convertDSetToList(dset, "PHONE");
            if (phones != null && !phones.isEmpty()) {
                html.append(appendData("Phone" , phones.get(0) , true , false));
            }
            break;
            
        }
        // responsible party section
        scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        scDtos = PoPaServiceBeanLookup.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);        
        Person rp = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            scDto = scDtos.get(0);
            rp = cUtils.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(scDto.getClinicalResearchStaffIi().getExtension()));
            dset = scDto.getTelecomAddresses();
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = PoPaServiceBeanLookup.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && !spDtos.isEmpty()) {
                rp = cUtils.getPAPersonByPAOrganizationalContactId((
                        Long.valueOf(spDtos.get(0).getOrganizationalContactIi().getExtension())));
                dset = spDtos.get(0).getTelecomAddresses();
            }
        }
        html.append(appendData("Responsible Party" , rp.getFirstMiddleLastName() , true , false));
        emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (emails != null && !emails.isEmpty()) {
            html.append(appendData("Email" , emails.get(0) , true , false));
        }
        phones = DSetConverter.convertDSetToList(dset, "PHONE");
        if (phones != null && !phones.isEmpty()) {
            html.append(appendData("Phone" , phones.get(0) , true , false));
        }
        html.append(appendData("Overall Official" , leadPi.getFirstMiddleLastName(), true , false));
        html.append(" , ").append(lead.getName());
        html.append(appendData("Role" , "Principal Investigator", true , false));
    }

    private void apppendTrialStatus(StringBuffer html , StudyProtocolDTO spDto) throws PAException {
        List<StudyOverallStatusDTO> sostatuses = PoPaServiceBeanLookup.getStudyOverallStatusService().
                getByStudyProtocol(spDto.getIdentifier());
        appendTitle(html , "Trial Status");
        html.append(TBL_B);
        for (StudyOverallStatusDTO sostatus : sostatuses) {
            html.append(TR_B);
            appendTDAndData(html, sostatus.getStatusCode().getCode());
            appendTDAndData(html, PAUtil.normalizeDateString(
                    TsConverter.convertToTimestamp(sostatus.getStatusDate()).toString()));
            appendTDAndData(html, getData(sostatus.getReasonText(), false)); 
            html.append(TR_E);
        }
        html.append(TBL_E).append(BR).append(BR);
        
        html.append(appendData("Trial Start Date" , PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                spDto.getStartDate()).toString()) , true , false)).append(' ').
                append(spDto.getStartDateTypeCode().getCode());
        
        html.append(appendData("Primary Completion Date" , PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                spDto.getPrimaryCompletionDate()).toString()) , true , false)).append(' ').
                append(spDto.getPrimaryCompletionDateTypeCode().getCode());
        
    }
    private void appendTDColumnAndData(StringBuffer html , String column , String data , boolean appendTR) {
        if (appendTR) {
            html.append(TR_B);
        }
        html.append(TD_B).append(column).append(TD_E).append(TD_B).append(data).append(TD_E);
        if (appendTR) {
            html.append(TR_E);
        }

    }
    private void appendTDAndData(StringBuffer html , String data) {
        html.append(TD_B).append(data).append(TD_E);
    }
    
    private String getData(St st , boolean appendNoData) {
        if (st == null && appendNoData) {
            return NO_DATA;
        }
        return st.getValue();
    }
    private String appendBoldData(String data) {
        return BLD_B + data + BLD_E;
    }
    private String appendData(String column , String data, boolean newLine , boolean bold) {
        return (newLine ? BR : EMPTY) + (bold ? BLD_B : EMPTY) + column + " : " + (bold ? BLD_E : EMPTY) + data;
    }
    private void appendTitle(StringBuffer html , String title) {
        html.append(BR).append(BR).append(title).append(BR);
        
        
    }
    
    private static String convertBLToString(Bl bl , boolean appendNoData) {
        if (bl == null && appendNoData) {
            return NO_DATA;
        } else if (bl == null) {
            return null;
        }
        Boolean b = bl.getValue();
        if (b != null && b.booleanValue()) {
            return YES;
        } else {
            return NO;
        }
    }
    

}
