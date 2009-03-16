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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * action for edit general trial design.
 * @author NAmiruddin
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength" ,
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class GeneralTrialDesignAction extends ActionSupport {

    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();

    private static final int OFFICIAL_TITLE = 4000;
    private static final int PUBLIC_TITLE = 299;
    private static final int PUBLIC_DESCRIPTION = 5000;
    private static final int SCI_DESC = 12000;
    private static final int KEYWORD = 600;
    private static final String SPONSOR = "sponsor";
    /**
     * @return res
     */
    public String query() {
        try {

            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);

            StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            StudyProtocolQueryDTO spqDto = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.TRIAL_SUMMARY);
            CorrelationUtils cUtils = new CorrelationUtils();

            copy(spDTO);
            copy(spqDto);
            copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null));
            copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null));
            copyResponsibleParty(studyProtocolIi);
            copySponsor(studyProtocolIi);
            copyCentralContact(studyProtocolIi);
            copyNctNummber(studyProtocolIi);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return "edit";
    }

    /**
     * @return result
     */
    public String update() {
        enforceBusinessRules();
        if (hasFieldErrors()) {
          return "edit";
        }
        save();
        return "edit";
    }

    private void save() {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest()
                    .getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            updateStudyProtocol(studyProtocolIi);
            updateStudyParticipation(studyProtocolIi , CdConverter.convertToCd(
                        StudyParticipationFunctionalCode.LEAD_ORAGANIZATION) ,
                        gtdDTO.getLeadOrganizationIdentifier() , 
                        PAUtil.stringSetter(gtdDTO.getLocalProtocolIdentifier()));
            updateStudyParticipation(studyProtocolIi , CdConverter.convertToCd(
                        StudyParticipationFunctionalCode.SPONSOR) ,
                        gtdDTO.getSponsorIdentifier() , null);
            updateStudyContact(studyProtocolIi);
            removeSponsorContact(studyProtocolIi);
            createSponorContact(studyProtocolIi);
            createOrUpdateCentralContact(studyProtocolIi);
            updateNctNumber(studyProtocolIi);
            StudyProtocolQueryDTO  studyProtocolQueryDTO =
                PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                        Long.valueOf(studyProtocolIi.getExtension()));
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(PAUtil.trim(studyProtocolQueryDTO.getOfficialTitle(),
                    PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE));
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.DOC_WFS_MENU, setMenuLinks(studyProtocolQueryDTO.getDocumentWorkflowStatusCode()));


        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }

    }


    private void copy(StudyProtocolDTO spDTO) {
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setPublicTitle(spDTO.getPublicTitle().getValue());
        gtdDTO.setAssignedIdentifier(spDTO.getAssignedIdentifier().getExtension());
        gtdDTO.setAcronym(spDTO.getAcronym().getValue());
        gtdDTO.setPublicDescription(spDTO.getPublicDescription().getValue());
        gtdDTO.setScientificDescription(spDTO.getScientificDescription().getValue());
        gtdDTO.setKeywordText(spDTO.getKeywordText().getValue());
    }


    private void copy(StudyProtocolQueryDTO spqDTO) {
        gtdDTO.setLocalProtocolIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
    }
    private void copyLO(Organization o) {
        gtdDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        gtdDTO.setLeadOrganizationName(o.getName());
    }
    private void copyPI(Person p) {
        gtdDTO.setPiIdentifier(p.getIdentifier());
        gtdDTO.setPiName(p.getFullName());
    }

    private void copyResponsibleParty(Ii studyProtocolIi) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        DSet dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            gtdDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = PaRegistry.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            gtdDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                gtdDTO.setResponsiblePartyType(SPONSOR);
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                Person p = cUtils.getPAPersonByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                gtdDTO.setResponsiblePersonIdentifier(p.getIdentifier());
                 gtdDTO.setResponsiblePersonName(p.getFirstName() + ", " + p.getLastName());


            }
        }
        copy(dset);
    }

    private void copy(DSet dset) {
        if (dset == null) {
            return;
        }
        List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (phones != null && !phones.isEmpty()) {
            gtdDTO.setContactPhone(phones.get(0));
        }
        if (emails != null && !emails.isEmpty()) {
            gtdDTO.setContactEmail(emails.get(0));
        }
    }
    private void copySponsor(Ii studyProtocolIi) throws PAException {
        StudyParticipationDTO spart = new StudyParticipationDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
        List<StudyParticipationDTO> spDtos = PaRegistry.getStudyParticipationService()
                        .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByPAResearchOrganizationId(

                        Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
            gtdDTO.setSponsorName(o.getName());
            gtdDTO.setSponsorIdentifier(o.getIdentifier());
        }

    }

    private void copyNctNummber(Ii studyProtocolIi) throws PAException {
        StudyParticipationDTO spDto = getStudyParticipation(studyProtocolIi,
                    StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER);
        if (spDto != null) {
            gtdDTO.setNctIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        }
    }

    private void copyCentralContact(Ii studyProtocolIi) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> srDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (srDtos != null && !srDtos.isEmpty()) {
            CorrelationUtils cUtils = new CorrelationUtils();
            scDto = srDtos.get(0);
            Person p = cUtils.getPAPersonByPAClinicalResearchStaffId(
                    Long.valueOf(scDto.getClinicalResearchStaffIi().getExtension()));
            gtdDTO.setCentralContactIdentifier(p.getIdentifier());
            gtdDTO.setCentralContactName(p.getFullName());
            DSet dset = scDto.getTelecomAddresses();
            List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
            List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
            if (phones != null && !phones.isEmpty()) {
                gtdDTO.setCentralContactPhone(phones.get(0));
            }
            if (emails != null && !emails.isEmpty()) {
                gtdDTO.setCentralContactEmail(emails.get(0));
            }

        }
   }


    private void updateStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setOfficialTitle(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getOfficialTitle(), OFFICIAL_TITLE)));
        spDTO.setAcronym(StConverter.convertToSt(gtdDTO.getAcronym()));
        spDTO.setPublicTitle(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getPublicTitle(), PUBLIC_TITLE)));
        spDTO.setPublicDescription(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getPublicDescription(),
                PUBLIC_DESCRIPTION)));
        spDTO.setScientificDescription(StConverter.convertToSt(
                PAUtil.stringSetter(gtdDTO.getScientificDescription(), SCI_DESC)));
        spDTO.setKeywordText(StConverter.convertToSt(PAUtil.stringSetter(gtdDTO.getKeywordText(), KEYWORD)));
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
    }

    private void updateNctNumber(Ii studyProtocolIi) throws PAException {

        String poOrgid = getCtGocIdentifier();
        OrganizationCorrelationServiceBean osb = new OrganizationCorrelationServiceBean();
        StudyParticipationDTO spDto = getStudyParticipation(studyProtocolIi ,
                StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER);

        if (PAUtil.isNotEmpty(gtdDTO.getNctIdentifier())) {
            if (spDto == null) {
                long roId = osb.createResearchOrganizationCorrelations(poOrgid);
                spDto = new StudyParticipationDTO();
                spDto.setStudyProtocolIdentifier(studyProtocolIi);
                spDto.setResearchOrganizationIi(IiConverter.convertToIi(poOrgid));
                spDto.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
                spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getNctIdentifier()));
                spDto.setResearchOrganizationIi(IiConverter.convertToIi(roId));
                PaRegistry.getStudyParticipationService().create(spDto);
            } else {
                spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(gtdDTO.getNctIdentifier()));
                PaRegistry.getStudyParticipationService().update(spDto);
            }
        } else if (spDto != null) {
            // delete the record
            PaRegistry.getStudyParticipationService().delete(spDto.getIdentifier());
        }
    }
    private StudyParticipationDTO getStudyParticipation(Ii studyProtocolIi , StudyParticipationFunctionalCode spCode)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudyParticipationDTO> spDtos = PaRegistry.getStudyParticipationService()
            .getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos.get(0);
        } else if (spDtos != null && spDtos.size() > 1) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

        }
        return null;

    }
    private void updateStudyParticipation(Ii studyProtocolIi , Cd cd , String roId , String lpIdentifier)
    throws PAException {
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        spDto.setFunctionalCode(cd);
        List<StudyParticipationDTO> srDtos = PaRegistry.getStudyParticipationService()
            .getByStudyProtocol(studyProtocolIi, spDto);
        OrganizationCorrelationServiceBean ocb = new OrganizationCorrelationServiceBean();

        if (srDtos != null && srDtos.size() > 1) {
            throw new PAException(" StudyParticipation has more than one recrod is found for a "
                    + " given Study Protocol id = " +  studyProtocolIi.getExtension());
        } else if (srDtos == null || srDtos.isEmpty()) {
            spDto = new StudyParticipationDTO();
            spDto.setResearchOrganizationIi(IiConverter.convertToIi(ocb.createResearchOrganizationCorrelations(roId)));
            spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(lpIdentifier));
            spDto.setStudyProtocolIdentifier(studyProtocolIi);
            spDto.setFunctionalCode(cd);
            PaRegistry.getStudyParticipationService().create(spDto);
        } else {
            spDto = srDtos.get(0);
            spDto.setResearchOrganizationIi(IiConverter.convertToIi(ocb.createResearchOrganizationCorrelations(roId)));
            spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(lpIdentifier));
            PaRegistry.getStudyParticipationService().update(spDto);
        }

    }

    private void updateStudyContact(Ii studyProtocolIi) throws PAException {
        StudyContactDTO spDto = new StudyContactDTO();
        spDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> srDtos = PaRegistry.getStudyContactService()
            .getByStudyProtocol(studyProtocolIi, spDto);
        if (srDtos == null || srDtos.isEmpty() || srDtos.size() > 1) {
            throw new PAException(" PI is either null , or more than one lead is found for a "
                    + " given Study Protocol id = " +  studyProtocolIi.getExtension());
        }
        StudyContactDTO scDto = srDtos.get(0);

        StudyProtocolQueryDTO spqDto = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession().
                getAttribute(Constants.TRIAL_SUMMARY);

        ClinicalResearchStaffCorrelationServiceBean crbb = new ClinicalResearchStaffCorrelationServiceBean();

        Long crs = crbb.createClinicalResearchStaffCorrelations(
                                    gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getPiIdentifier());
        scDto.setClinicalResearchStaffIi(IiConverter.convertToIi(crs));
        if (spqDto.getStudyProtocolType().equalsIgnoreCase("InterventionalStudyProtocol")) {
            Long hcp = null;
            HealthCareProviderCorrelationBean hcpb = new HealthCareProviderCorrelationBean();
            hcp = hcpb.createHealthCareProviderCorrelationBeans(
                    gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getPiIdentifier());
            scDto.setHealthCareProviderIi(IiConverter.convertToIi(hcp));
        }
        PaRegistry.getStudyContactService().update(scDto);
    }

    private void createOrUpdateCentralContact(Ii studyProtocolIi) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setStudyProtocolIi(studyProtocolIi);
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.CENTRAL_CONTACT));
        List<StudyContactDTO> srDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (srDtos != null && !srDtos.isEmpty()) {
            scDto = srDtos.get(0);
            PaRegistry.getStudyContactService().update(createStudyContactObj(studyProtocolIi, scDto));
        } else {
            PaRegistry.getStudyContactService().create(createStudyContactObj(studyProtocolIi, scDto));
        }
    }
    private StudyContactDTO createStudyContactObj(Ii studyProtocolIi, StudyContactDTO scDTO) throws PAException {
        ClinicalResearchStaffCorrelationServiceBean crbb = new ClinicalResearchStaffCorrelationServiceBean();
        String phone = gtdDTO.getCentralContactPhone().trim();
        Long crs = crbb.createClinicalResearchStaffCorrelations(
                gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getCentralContactIdentifier());
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crs));
        scDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<String> phones = new ArrayList<String>();
        phones.add(phone);
        List<String> emails = new ArrayList<String>();
        emails.add(gtdDTO.getCentralContactEmail());
        DSet<Tel> dsetList = null;
        dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
        dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
        scDTO.setTelecomAddresses(dsetList);
        return scDTO;

    }
    private void removeSponsorContact(Ii studyProtocolIi) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        if (scDtos != null && !scDtos.isEmpty()) {
            scDto = scDtos.get(0);
            PaRegistry.getStudyContactService().delete(scDtos.get(0).getIdentifier());
        } else {
            // delete from Study Participation and it will delete study_participation contact
            StudyParticipationDTO spart = new StudyParticipationDTO();
            spart.setFunctionalCode(CdConverter.convertToCd(
                  StudyParticipationFunctionalCode.RESPONSIBLE_PARTY_SPONSOR));
              List<StudyParticipationDTO> spDtos = PaRegistry.getStudyParticipationService()
                  .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && !spDtos.isEmpty()) {
                PaRegistry.getStudyParticipationService().delete(spDtos.get(0).getIdentifier());
            }

        }

    }
    private void createSponorContact(Ii studyProtocolIi) throws  PAException {
        PARelationServiceBean parb = new PARelationServiceBean();
        String phone = gtdDTO.getContactPhone().trim();
        if (gtdDTO.getResponsiblePartyType() == null || gtdDTO.getResponsiblePartyType().equals("pi")) {
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getPiIdentifier(),
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
        } else if (gtdDTO.getResponsiblePartyType().equals(SPONSOR)) {
            parb.createSponsorAsPrimaryContactRelations(gtdDTO.getSponsorIdentifier(),
                    gtdDTO.getResponsiblePersonIdentifier(),
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
        }
    }

    private String getCtGocIdentifier() throws  PAException {
        OrganizationDTO poOrgDto = new OrganizationDTO();
        poOrgDto.setName(EnOnConverter.convertToEnOn("ClinicalTrials.gov"));
        List<OrganizationDTO> poOrgs = PaRegistry.getPoOrganizationEntityService().search(poOrgDto);
        String identifier = null;
        if (poOrgs == null || poOrgs.isEmpty()) {
            poOrgDto.setPostalAddress(AddressConverterUtil.create("ct.gov.address", null, "ct.mun", "VA", "20171",
                    "USA"));
            DSet<Tel> telco = new DSet<Tel>();
            telco.setItem(new HashSet<Tel>());
            Tel t = new Tel();
            try {
                t.setValue(new URI("tel", "11111", null));
            telco.getItem().add(t);
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + "ct@ct.gov"));

            telco.getItem().add(telemail);
            } catch (URISyntaxException e) {
                throw new PAException(e);
            }

            poOrgDto.setTelecomAddress(telco);
            try {
                Ii ii = PaRegistry.getPoOrganizationEntityService().createOrganization(poOrgDto);
                identifier = ii.getExtension();
            } catch (EntityValidationException e) {
                throw new PAException(e);
            }

        } else if (poOrgs.size() > 1) {
            throw new PAException(" there cannot be more than 1 record for ClinicalTrials.gov");
        } else {
            identifier = poOrgs.get(0).getIdentifier().getExtension();
        }
        return identifier;

    }

    private void enforceBusinessRules() {
      if (PAUtil.isEmpty(gtdDTO.getLocalProtocolIdentifier())) {
        addFieldError("gtdDTO.localProtocolIdentifier", getText("Organization Trial ID must be Entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getOfficialTitle())) {
        addFieldError("gtdDTO.officialTitle", getText("OfficialTitle must be Entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getSponsorIdentifier())) {
          addFieldError("gtdDTO.sponsorName", getText("Sponsor must be entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getPublicTitle())) {
        addFieldError("gtdDTO.publicTitle", getText("PublicTitle must be Entered"));
      }
      if (SPONSOR.equalsIgnoreCase(gtdDTO.getResponsiblePartyType())
              && PAUtil.isEmpty(gtdDTO.getResponsiblePersonName())) {
          addFieldError("gtdDTO.responsiblePersonName",
                  getText("Responsible Party Contact must be entered when Responsible Party is Sponsor"));
      }
    if (PAUtil.isNotEmpty(gtdDTO.getCentralContactName()) || PAUtil.isNotEmpty(gtdDTO.getCentralContactPhone())
    || PAUtil.isNotEmpty(gtdDTO.getCentralContactEmail())) {
    
    if (PAUtil.isEmpty(gtdDTO.getCentralContactName())) {
      addFieldError("gtdDTO.centralContactName", getText("Central contact Name must be entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getCentralContactPhone())) {
       addFieldError("gtdDTO.centralContactPhone", getText("Central Contact Phone must be Entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getCentralContactEmail())) {
      addFieldError("gtdDTO.centralContactEmail", getText("Central Contact Email must be Entered"));
      }
      if (PAUtil.isNotEmpty(gtdDTO.getContactEmail()) && !PAUtil.isValidEmail(gtdDTO.getContactEmail())) {
          addFieldError("gtdDTO.contactEmail", getText("Email entered is not a valid format"));
      }
      if (PAUtil.isNotEmpty(gtdDTO.getCentralContactEmail()) && !PAUtil.isValidEmail(gtdDTO.getCentralContactEmail())) {
          addFieldError("gtdDTO.centralContactEmail", getText("Central Contact Email is not a valid format"));
      }
     }
    }
    /**
     *
     * @return gtdDTO
     */
    public GeneralTrialDesignWebDTO getGtdDTO() {
        return gtdDTO;
    }

    /**
     *
     * @param gtdDTO gtdDTO
     */
    public void setGtdDTO(GeneralTrialDesignWebDTO gtdDTO) {
        this.gtdDTO = gtdDTO;
    }

    private String setMenuLinks(DocumentWorkflowStatusCode dwsCode) {
        String action = "";
        if (DocumentWorkflowStatusCode.REJECTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.REJECTED.getCode();
        } else if (DocumentWorkflowStatusCode.SUBMITTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.SUBMITTED.getCode();
        } else {
            action = DocumentWorkflowStatusCode.ACCEPTED.getCode();
        }
        return action;
    }

}
