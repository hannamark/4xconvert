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
package gov.nih.nci.pa.util;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialDocumentDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.dto.TrialIndIdeDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author vrushali
 *
 */
public class TrialUtil {
    private static final String SPONSOR = "sponsor";
    private static final int MAXF = 1024;

    /**
     * Default constructor.
     */
    public TrialUtil() {
        super();
    }

    /**
     *
     * @param spDTO sdto
     * @param trialDTO gdto
     */
    public void copy(StudyProtocolDTO spDTO, TrialDTO trialDTO) {
        trialDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        trialDTO.setAssignedIdentifier(PAUtil.getAssignedIdentifierExtension(spDTO));
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseOtherText(spDTO.getPhaseAdditionalQualifierCode().getCode());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setStartDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(spDTO.getStartDate())
            .toString()));
        trialDTO.setStartDateType(spDTO.getStartDateTypeCode().getCode());
        trialDTO.setCompletionDate(PAUtil.normalizeDateString(TsConverter
            .convertToTimestamp(spDTO.getPrimaryCompletionDate()).toString()));
        trialDTO.setCompletionDateType(spDTO.getPrimaryCompletionDateTypeCode().getCode());
        trialDTO.setTrialType(spDTO.getStudyProtocolType().getValue());
        trialDTO.setIdentifier(spDTO.getIdentifier().getExtension());
    }

    /**
     *
     * @param spqDTO sdto
     * @param trialDTO gdto
     */
    public void copy(StudyProtocolQueryDTO spqDTO, TrialDTO trialDTO) {
        trialDTO.setLocalProtocolIdentifier(spqDTO.getLocalStudyProtocolIdentifier());
        trialDTO.setStatusCode(spqDTO.getStudyStatusCode().getCode());
        trialDTO.setStatusDate(PAUtil.normalizeDateString(spqDTO.getStudyStatusDate().toString()));
    }

    /**
     *
     * @param o o
     * @param trialDTO gdto
     */
    public void copyLO(Organization o, TrialDTO trialDTO) {
        trialDTO.setLeadOrganizationIdentifier(o.getIdentifier());
        trialDTO.setLeadOrganizationName(o.getName());
    }

    /**
     *
     * @param p p
     * @param trialDTO dto
     */
    public void copyPI(Person p, TrialDTO trialDTO) {
        trialDTO.setPiIdentifier(p.getIdentifier());
        trialDTO.setPiName(p.getFullName());
    }

    /**
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     * @throws NullifiedRoleException e
     */
    public void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException, NullifiedRoleException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos = PaRegistry.getStudyContactService().getByStudyProtocol(studyProtocolIi, scDto);
        DSet<Tel> dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            trialDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudySiteContactDTO spart = new StudySiteContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudySiteContactDTO> spDtos = PaRegistry.getStudySiteContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            trialDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                trialDTO.setResponsiblePartyType(SPONSOR);
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                PAContactDTO paCDto = cUtils.getContactByPAOrganizationalContactId((Long.valueOf(spart
                    .getOrganizationalContactIi().getExtension())));
                if (paCDto.getFullName() != null) {
                    trialDTO.setResponsiblePersonName(paCDto.getFullName());
                    trialDTO.setResponsiblePersonIdentifier(paCDto.getPersonIdentifier().getExtension());

                }
                if (paCDto.getTitle() != null) {
                    // trialDTO.setResponsibleGenericContactName(StConverter.convertToString(isoDto.getTitle()));
                    trialDTO.setResponsiblePersonIdentifier(paCDto.getSrIdentifier().getExtension());
                }
            }
        }
        copy(dset, trialDTO);
    }

    /**
     *
     * @param dset set
     * @param trialDTO dto
     */
    public void copy(DSet<Tel> dset, TrialDTO trialDTO) {
        if (dset == null) {
            return;
        }
        List<String> phones = DSetConverter.convertDSetToList(dset, "PHONE");
        List<String> emails = DSetConverter.convertDSetToList(dset, "EMAIL");
        if (phones != null && !phones.isEmpty()) {
            trialDTO.setContactPhone(phones.get(0));
        }
        if (emails != null && !emails.isEmpty()) {
            trialDTO.setContactEmail(emails.get(0));
        }
    }

    /**
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copySponsor(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spart = new StudySiteDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByIi(spart.getResearchOrganizationIi());
            trialDTO.setSponsorName(o.getName());
            trialDTO.setSponsorIdentifier(o.getIdentifier());
        }

    }

    /**
     *
     * @param studyProtocolIi ii
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copyNctNummber(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudySiteDTO spDto = getStudySite(studyProtocolIi, StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        if (spDto != null) {
            trialDTO.setNctIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        }
    }

    /**
     *
     * @param studyProtocolIi ii
     * @param spCode code
     * @return dto
     * @throws PAException ex
     */
    public StudySiteDTO getStudySite(Ii studyProtocolIi, StudySiteFunctionalCode spCode) throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudySiteDTO spDto = new StudySiteDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudySiteDTO> spDtos = PaRegistry.getStudySiteService().getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos.get(0);
        } else if (spDtos != null && spDtos.size() > 1) {
            throw new PAException(" Found more than 1 record for a protocol id = " + studyProtocolIi.getExtension()
                    + " for a given status " + cd.getCode());

        }
        return null;

    }

    /**
     *
     * @param srDTO sdto
     * @param trialDTO tdto
     * @throws PAException ex
     */
    public void copySummaryFour(StudyResourcingDTO srDTO, TrialDTO trialDTO) throws PAException {
        if (srDTO == null) {
            return;
        }
        if (srDTO.getTypeCode() != null) {
            trialDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
        if (srDTO.getOrganizationIdentifier() != null
                && StringUtils.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIi(srDTO.getOrganizationIdentifier());
            trialDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
            trialDTO.setSummaryFourOrgName(o.getName());
        }
    }

    /**
     *
     * @param studyIndldeDTOList iiDto
     * @param trialDTO dto
     * @throws PAException ex
     */
    public void copyINDIDEList(List<StudyIndldeDTO> studyIndldeDTOList, TrialDTO trialDTO) throws PAException {
        if (studyIndldeDTOList == null) {
            return;
        }
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        // loop thru the iso dto
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new TrialIndIdeDTO(isoDto));
        }
        trialDTO.setIndDtos(indList);
    }

    /**
     *
     * @param isoGrantlist iso
     * @param trialDTO dto
     */
    public void copyGrantList(List<StudyResourcingDTO> isoGrantlist, TrialDTO trialDTO) {
        if (isoGrantlist == null) {
            return;
        }
        List<TrialFundingDTO> grantList = new ArrayList<TrialFundingDTO>();
        // loop thru iso
        for (StudyResourcingDTO isoDto : isoGrantlist) {
            grantList.add(new TrialFundingDTO(isoDto));
        }
        trialDTO.setFundingDtos(grantList);
    }

    /**
     *
     * @param trialDTO dtotoConvert
     * @return isoDto
     * @throws PAException on error
     */
    public StudyProtocolDTO convertToStudyProtocolDTO(TrialDTO trialDTO) throws PAException {
        StudyProtocolDTO isoDto = null;

        if (trialDTO.getTrialType().equalsIgnoreCase("Observational")) {
            isoDto = PaRegistry.getStudyProtocolService().getObservationalStudyProtocol(IiConverter
                                                                                            .convertToIi(trialDTO
                                                                                                .getIdentifier()));
        } else {
            isoDto = PaRegistry.getStudyProtocolService().getInterventionalStudyProtocol(IiConverter
                                                                                             .convertToIi(trialDTO
                                                                                                 .getIdentifier()));
        }
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi(trialDTO.getAssignedIdentifier());
        isoDto.setSecondaryIdentifiers(DSetConverter.convertIiToDset(assignedId));
        isoDto.setOfficialTitle(StConverter.convertToSt(trialDTO.getOfficialTitle()));
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDTO.getPhaseCode())));
        if (StringUtils.isNotEmpty(trialDTO.getPhaseOtherText())) {
            isoDto.setPhaseAdditionalQualifierCode(CdConverter.convertToCd(PhaseAdditionalQualifierCode
                .getByCode(trialDTO.getPhaseOtherText())));
        }
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(PrimaryPurposeCode.getByCode(trialDTO
            .getPrimaryPurposeCode())));

        if (StringUtils.isNotEmpty(trialDTO.getPrimaryPurposeOtherText())) {
            isoDto.setPrimaryPurposeOtherText(StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        }
        isoDto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStartDate())));
        isoDto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
            .getStartDateType())));
        isoDto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
            .getCompletionDate())));
        isoDto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
            .getCompletionDateType())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDTO.getTrialType()));
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getAmendmentDate())));
        isoDto.setAmendmentNumber(StConverter.convertToSt(trialDTO.getLocalAmendmentNumber()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO Dto
     * @return isoDto
     */
    public StudyOverallStatusDTO convertToStudyOverallStatusDTO(TrialDTO trialDTO) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO.getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStatusDate())));
        return isoDto;
    }

    /**
     *
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToSummary4OrgDTO(TrialDTO trialDTO) {
        if (trialDTO.getSummaryFourOrgIdentifier().equals("")) {
            return null;
        }
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getSummaryFourOrgIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToLeadOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getLeadOrganizationIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO do
     * @return iso
     */
    public OrganizationDTO convertToSponsorOrgDTO(TrialDTO trialDTO) {
        OrganizationDTO isoDto = new OrganizationDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getSponsorIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToLeadPI(TrialDTO trialDTO) {
        PersonDTO isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getPiIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToResponsiblePartyContactDTO(TrialDTO trialDTO) {
        PersonDTO isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getResponsiblePersonIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public StudyResourcingDTO convertToSummary4StudyResourcingDTO(TrialDTO trialDTO) {
        StudyResourcingDTO isoDto = new StudyResourcingDTO();
        isoDto.setTypeCode(CdConverter.convertStringToCd(trialDTO.getSummaryFourFundingCategoryCode()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public StudySiteDTO convertToStudySiteDTO(TrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLocalProtocolIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public StudySiteDTO convertToNCTStudySiteDTO(TrialDTO trialDTO) {
        StudySiteDTO isoDto = new StudySiteDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
        return isoDto;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public StudyContactDTO convertToStudyContactDTO(TrialDTO trialDTO) {
        StudyContactDTO iso = new StudyContactDTO();
        iso.setTelecomAddresses(getTelecomAddress(trialDTO));
        return iso;
    }

    /**
     *
     * @param trialDTO dto
     * @return iso
     */
    public StudySiteContactDTO convertToStudySiteContactDTO(TrialDTO trialDTO) {
        StudySiteContactDTO iso = new StudySiteContactDTO();
        iso.setTelecomAddresses(getTelecomAddress(trialDTO));
        return iso;
    }

    private DSet<Tel> getTelecomAddress(TrialDTO trialDTO) {
        List<String> phones = new ArrayList<String>();
        phones.add(trialDTO.getContactPhone());
        List<String> emails = new ArrayList<String>();
        emails.add(trialDTO.getContactEmail());
        DSet<Tel> dsetList = null;
        dsetList = DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
        dsetList = DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
        return dsetList;
    }

    /**
     *
     * @param docList dto
     * @return isoDTOList
     */
    public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentDTO> docList) {
        List<DocumentDTO> studyDocDTOList = new ArrayList<DocumentDTO>();
        // loop thru the iso dto
        for (TrialDocumentDTO dto : docList) {
            DocumentDTO isoDTO = new DocumentDTO();
            isoDTO.setTypeCode(CdConverter.convertStringToCd(dto.getTypeCode()));
            isoDTO.setFileName(StConverter.convertToSt(dto.getFileName()));
            isoDTO.setText(EdConverter.convertToEd(dto.getText()));
            studyDocDTOList.add(isoDTO);
        }
        return studyDocDTOList;
    }

    /**
     *
     * @param docTypeCode doc
     * @param fileName file
     * @param file file
     * @return isoDto
     * @throws IOException io
     */
    public TrialDocumentDTO convertToDocumentList(String docTypeCode, String fileName, File file) throws IOException {
        TrialDocumentDTO docDTO = new TrialDocumentDTO();
        docDTO.setTypeCode(docTypeCode);
        docDTO.setFileName(fileName);
        docDTO.setText((readInputStream(new FileInputStream(file))));
        return docDTO;
    }

    /** Read an input stream in its entirety into a byte array. */
    private static byte[] readInputStream(InputStream inputStream) throws IOException {
        int bufSize = MAXF * MAXF;
        byte[] content;
        List<byte[]> parts = new LinkedList<byte[]>();
        InputStream in = new BufferedInputStream(inputStream);
        byte[] readBuffer = new byte[bufSize];
        byte[] part = null;
        int bytesRead = 0;
        // read everyting into a list of byte arrays
        while ((bytesRead = in.read(readBuffer, 0, bufSize)) != -1) {
            part = new byte[bytesRead];
            System.arraycopy(readBuffer, 0, part, 0, bytesRead);
            parts.add(part);
        }
        // calculate the total size
        int totalSize = 0;
        for (byte[] partBuffer : parts) {
            totalSize += partBuffer.length;
        }
        // allocate the array
        content = new byte[totalSize];
        int offset = 0;
        for (byte[] partBuffer : parts) {
            System.arraycopy(partBuffer, 0, content, offset, partBuffer.length);
            offset += partBuffer.length;
        }
        return content;
    }

    /**
     *
     * @param indList ind
     * @return isoList
     */
    public List<StudyIndldeDTO> convertISOINDIDEList(List<TrialIndIdeDTO> indList) {
        if (indList.isEmpty()) {
            return null;
        }
        List<StudyIndldeDTO> studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
        // loop thru the non-iso dto
        StudyIndldeDTO isoDTO = null;
        for (TrialIndIdeDTO dto : indList) {
            isoDTO = new StudyIndldeDTO();
            isoDTO.setIndldeTypeCode(CdConverter.convertStringToCd(dto.getIndIde()));
            isoDTO.setIndldeNumber(StConverter.convertToSt(dto.getNumber()));
            isoDTO.setGrantorCode(CdConverter.convertStringToCd(dto.getGrantor()));
            isoDTO.setHolderTypeCode(CdConverter.convertStringToCd(dto.getHolderType()));
            if (dto.getHolderType().equalsIgnoreCase("NIH")) {
                isoDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
            }
            if (dto.getHolderType().equalsIgnoreCase("NCI")) {
                isoDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getProgramCode()));
            }
            isoDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(dto.getExpandedAccess())));
            isoDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getExpandedAccessType()));
            studyIndldeDTOList.add(isoDTO);
        }
        return studyIndldeDTOList;
    }

    /**
     *
     * @param grantList list
     * @return isoList
     */
    public List<StudyResourcingDTO> convertISOGrantsList(List<TrialFundingDTO> grantList) {
        if (grantList.isEmpty()) {
            return null;
        }
        List<StudyResourcingDTO> grantsDTOList = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO isoDTO = null;
        for (TrialFundingDTO dto : grantList) {
            isoDTO = new StudyResourcingDTO();
            isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
            isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanism()));
            isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(NciDivisionProgramCode.getByCode(dto
                .getNciDivisionProgramCode())));
            isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getInstituteCode()));
            isoDTO.setSerialNumber(StConverter.convertToSt(dto.getSerialNumber()));
            grantsDTOList.add(isoDTO);
        }
        return grantsDTOList;
    }
}
