package gov.nih.nci.registry.util;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
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
import java.util.UUID;

/**
 * @author vrushali
 *
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
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
        trialDTO.setAssignedIdentifier(spDTO.getAssignedIdentifier().getExtension());
        trialDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        trialDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        trialDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        trialDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
        trialDTO.setStartDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(spDTO.getStartDate()).
                toString()));
        trialDTO.setStartDateType(spDTO.getStartDateTypeCode().getCode());
        trialDTO.setCompletionDate(PAUtil.normalizeDateString(TsConverter.
                convertToTimestamp(spDTO.getPrimaryCompletionDate()).toString()));
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
     */
    public void copyResponsibleParty(Ii studyProtocolIi, TrialDTO trialDTO) throws PAException {
        StudyContactDTO scDto = new StudyContactDTO();
        scDto.setRoleCode(CdConverter.convertToCd(StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR));
        List<StudyContactDTO> scDtos =  RegistryServiceLocator.getStudyContactService()
            .getByStudyProtocol(studyProtocolIi, scDto);
        DSet dset = null;
        if (scDtos != null && !scDtos.isEmpty()) {
            trialDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = RegistryServiceLocator.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            trialDTO.setResponsiblePartyType(SPONSOR);
            if (spDtos != null && !spDtos.isEmpty()) {
                trialDTO.setResponsiblePartyType(SPONSOR);
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                Person p = cUtils.getPAPersonByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                trialDTO.setResponsiblePersonIdentifier(p.getIdentifier());
                 trialDTO.setResponsiblePersonName(p.getFirstName() + ", " + p.getLastName());


            }
        }
        copy(dset, trialDTO);
    }
/**
 * 
 * @param dset set
 * @param trialDTO dto
 */
    public void copy(DSet dset, TrialDTO trialDTO) {
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
        StudyParticipationDTO spart = new StudyParticipationDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
        List<StudyParticipationDTO> spDtos = RegistryServiceLocator.getStudyParticipationService()
            .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && !spDtos.isEmpty()) {
            spart = spDtos.get(0);
            Organization o = new CorrelationUtils().getPAOrganizationByPAResearchOrganizationId(

                        Long.valueOf(spart.getResearchOrganizationIi().getExtension()));
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
        StudyParticipationDTO spDto = getStudyParticipation(studyProtocolIi,
                    StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER);
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
    public StudyParticipationDTO getStudyParticipation(Ii studyProtocolIi , StudyParticipationFunctionalCode spCode)
    throws PAException {
        if (studyProtocolIi == null) {
            throw new PAException(" StudyProtocol Ii is null");
        }
        StudyParticipationDTO spDto = new StudyParticipationDTO();
        Cd cd = CdConverter.convertToCd(spCode);
        spDto.setFunctionalCode(cd);

        List<StudyParticipationDTO> spDtos = RegistryServiceLocator.getStudyParticipationService()
        .getByStudyProtocol(studyProtocolIi, spDto);
        if (spDtos != null && spDtos.size() == 1) {
            return spDtos.get(0);
        } else if (spDtos != null && spDtos.size() > 1) {
            throw new PAException(" Found more than 1 record for a protocol id = "
                    + studyProtocolIi.getExtension() + " for a given status " + cd.getCode());

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
                && PAUtil.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIndetifers(Long.valueOf(srDTO.getOrganizationIdentifier()
                    .getExtension()), null);
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
        //loop thru the iso dto
        for (StudyIndldeDTO isoDto : studyIndldeDTOList) {
            indList.add(new TrialIndIdeDTO(isoDto));
        }
        trialDTO.setIndIdeDtos(indList);
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
        List<TrialFundingWebDTO> grantList = new ArrayList<TrialFundingWebDTO>(); 
        //loop thru iso
        TrialFundingWebDTO webDto = null;
        for (StudyResourcingDTO isoDto : isoGrantlist) {
            webDto = new TrialFundingWebDTO(isoDto);
            webDto.setRowId(UUID.randomUUID().toString());
            grantList.add(webDto);
        }
        trialDTO.setFundingDtos(grantList);
    }
    /**
     * 
     * @param trialDTO dtotoConvert
     * @return isoDto
     * @throws PAException on error 
     */
    public StudyProtocolDTO convertToStudyProtocolDTOForAmendment(TrialDTO trialDTO) throws PAException {
        StudyProtocolDTO isoDto = null;
        
        if (trialDTO.getTrialType().equalsIgnoreCase("Observational")) {
            isoDto = RegistryServiceLocator.getStudyProtocolService().getObservationalStudyProtocol(
                        IiConverter.convertToIi(trialDTO.getIdentifier()));
        } else {
            isoDto  = RegistryServiceLocator.getStudyProtocolService().getInterventionalStudyProtocol(
                    IiConverter.convertToIi(trialDTO.getIdentifier()));
        }
        isoDto = convertToStudyProtocolDTO(trialDTO, isoDto);
        isoDto.setAssignedIdentifier(IiConverter.convertToIi(trialDTO.getAssignedIdentifier()));
        isoDto.setAmendmentDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getAmendmentDate())));
        isoDto.setAmendmentNumber(StConverter.convertToSt(trialDTO.getLocalAmendmentNumber()));
        return isoDto;
    }

    /**
     * @param trialDTO
     * @param isoDto
     */
    private StudyProtocolDTO convertToStudyProtocolDTO(TrialDTO trialDTO,
            StudyProtocolDTO isoDto) {
        isoDto.setOfficialTitle(StConverter.convertToSt(trialDTO.getOfficialTitle()));
        isoDto.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(trialDTO.getPhaseCode())));
        if (PAUtil.isNotEmpty(trialDTO.getPhaseOtherText())) {
            isoDto.setPhaseOtherText(StConverter.convertToSt(trialDTO.getPhaseOtherText()));
        }
        isoDto.setPrimaryPurposeCode(CdConverter.convertToCd(
                PrimaryPurposeCode.getByCode(trialDTO.getPrimaryPurposeCode())));
        
        if (PAUtil.isNotEmpty(trialDTO.getPrimaryPurposeOtherText())) {
            isoDto.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(trialDTO.getPrimaryPurposeOtherText()));
        }
        isoDto.setStartDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO.getStartDate())));
        isoDto.setStartDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode.getByCode(trialDTO
                .getStartDateType())));
        isoDto.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getCompletionDate())));
        isoDto.setPrimaryCompletionDateTypeCode(CdConverter.convertToCd(ActualAnticipatedTypeCode
                .getByCode(trialDTO.getCompletionDateType())));
        isoDto.setStudyProtocolType(StConverter.convertToSt(trialDTO.getTrialType()));
        return isoDto;
    }
    /**
     * 
     * @param trialDTO Dto
     * @return isoDto
     */
    public StudyOverallStatusDTO convertToStudyOverallStatusDTO(TrialDTO trialDTO) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode.getByCode(trialDTO
                .getStatusCode())));
        isoDto.setReasonText(StConverter.convertToSt(trialDTO.getReason()));
        isoDto.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(trialDTO
                .getStatusDate())));
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
        PersonDTO  isoDto = new PersonDTO();
        isoDto.setIdentifier(IiConverter.convertToIi(trialDTO.getPiIdentifier()));
        return isoDto;
    }
    /**
     * 
     * @param trialDTO dto
     * @return iso
     */
    public PersonDTO convertToResponsiblePartyContactDTO(TrialDTO trialDTO) {
        PersonDTO  isoDto = new PersonDTO();
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
    public StudyParticipationDTO convertToStudyParticipationDTO(TrialDTO trialDTO) {
        StudyParticipationDTO isoDto = new StudyParticipationDTO();
        isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getLocalProtocolIdentifier()));
        return isoDto;
    }
    /**
     * 
     * @param trialDTO dto
     * @return iso
     */
    public StudyParticipationDTO convertToNCTStudyParticipationDTO(TrialDTO trialDTO) {
        StudyParticipationDTO isoDto = new StudyParticipationDTO();
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
   public StudyParticipationContactDTO convertToStudyParticipationContactDTO(TrialDTO trialDTO) {
       StudyParticipationContactDTO iso = new StudyParticipationContactDTO();
       iso.setTelecomAddresses(getTelecomAddress(trialDTO));
       return iso;
   }

   private DSet<Tel> getTelecomAddress(TrialDTO trialDTO) {
       List<String> phones = new ArrayList<String>();
       phones.add(trialDTO.getContactPhone());
       List<String> emails = new ArrayList<String>();
       emails.add(trialDTO.getContactEmail());
       DSet<Tel> dsetList = null;
       dsetList =  DSetConverter.convertListToDSet(phones, "PHONE", dsetList);
       dsetList =  DSetConverter.convertListToDSet(emails, "EMAIL", dsetList);
       return dsetList;
   }
   /**
    * 
    * @param docList dto
    * @return isoDTOList
    * @throws PAException ex
    */
   public List<DocumentDTO> convertToISODocumentList(List<TrialDocumentWebDTO> docList) throws PAException {
       List<DocumentDTO> studyDocDTOList = new ArrayList<DocumentDTO>();
       //loop thru the iso dto
       for (TrialDocumentWebDTO dto : docList) {
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
   public TrialDocumentWebDTO convertToDocumentDTO(String docTypeCode, String fileName, File file) throws IOException {
       TrialDocumentWebDTO docDTO = new TrialDocumentWebDTO();
       docDTO.setTypeCode(docTypeCode);
       docDTO.setFileName(fileName);
       docDTO.setText((readInputStream(new FileInputStream(file))));
       return docDTO;
   }
   /** Read an input stream in its entirety into a byte array. */
   private static byte[] readInputStream(InputStream inputStream) throws IOException {
       int bufSize = MAXF * MAXF;
       byte[] content;
       List<byte[]> parts = new LinkedList();
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
   public List<StudyIndldeDTO>  convertISOINDIDEList(List<TrialIndIdeDTO> indList) {
       if (indList != null && indList.isEmpty()) {
           return null;
       }
       List<StudyIndldeDTO>  studyIndldeDTOList = new ArrayList<StudyIndldeDTO>();
       //loop thru the non-iso dto
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
   public List<StudyResourcingDTO>  convertISOGrantsList(List<TrialFundingWebDTO> grantList) {
       if (grantList != null && grantList.isEmpty()) {
           return null;
       }
       List<StudyResourcingDTO>  grantsDTOList = new ArrayList<StudyResourcingDTO>();
       StudyResourcingDTO isoDTO = null;
       for (TrialFundingWebDTO dto : grantList) {
           isoDTO = new StudyResourcingDTO();
           isoDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.FALSE));
           isoDTO.setFundingMechanismCode(CdConverter.convertStringToCd(dto.getFundingMechanismCode()));
           isoDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
                               NciDivisionProgramCode.getByCode(dto.getNciDivisionProgramCode())));
           isoDTO.setNihInstitutionCode(CdConverter.convertStringToCd(dto.getNihInstitutionCode()));
           isoDTO.setSerialNumber(IntConverter.convertToInt(dto.getSerialNumber()));
           grantsDTOList.add(isoDTO);
       }
       return grantsDTOList;
   }
   /**
    * 
    * @param studyProtocolIi Ii
    * @return dto
    * @throws PAException ex
    */
   public TrialDTO getTrialDTOFromDb(Ii studyProtocolIi) throws PAException {
       TrialDTO trialDTO = new TrialDTO();
       StudyProtocolDTO spDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
       StudyProtocolQueryDTO spqDto = RegistryServiceLocator.getProtocolQueryService().
               getTrialSummaryByStudyProtocolId(
                   Long.valueOf(studyProtocolIi.getExtension()));
       CorrelationUtils cUtils = new CorrelationUtils();
       copy(spDTO, trialDTO);
       copy(spqDto, trialDTO);
       copyLO(cUtils.getPAOrganizationByIndetifers(spqDto.getLeadOrganizationId(), null), trialDTO);
       copyPI(cUtils.getPAPersonByIndetifers(spqDto.getPiId(), null), trialDTO);
       copyResponsibleParty(studyProtocolIi, trialDTO);
       copySponsor(studyProtocolIi, trialDTO);
       copyNctNummber(studyProtocolIi, trialDTO);
       copySummaryFour(RegistryServiceLocator.getStudyResourcingService().
                   getsummary4ReportedResource(studyProtocolIi), trialDTO);
       //Copy IND's
       List<StudyIndldeDTO> studyIndldeDTOList = RegistryServiceLocator.getStudyIndldeService()
           .getByStudyProtocol(studyProtocolIi);
       if (!(studyIndldeDTOList.isEmpty())) {
            copyINDIDEList(studyIndldeDTOList, trialDTO);
       }
       // query the study grants
       List<StudyResourcingDTO> isoList = RegistryServiceLocator.getStudyResourcingService()
                   .getstudyResourceByStudyProtocol(studyProtocolIi);
       if (!(isoList.isEmpty())) {
           copyGrantList(isoList, trialDTO);
       }
       //copy the reason for trial
       StudyOverallStatusDTO sosDto = null;
       List<StudyOverallStatusDTO> sosList = RegistryServiceLocator.getStudyOverallStatusService()
                   .getCurrentByStudyProtocol(studyProtocolIi);
       if (!sosList.isEmpty()) {
           sosDto = sosList.get(0);
       }
       if (sosDto != null) {
           trialDTO.setReason(StConverter.convertToString(sosDto.getReasonText()));
       } else {
           trialDTO.setReason(null);
       }
       return trialDTO;
   }
   /**
    * 
    * @param trialDTO dtotoConvert
    * @return isoDto
    * @throws PAException on error 
    */
   public InterventionalStudyProtocolDTO convertToInterventionalStudyProtocolDTO(TrialDTO trialDTO) throws PAException {
       InterventionalStudyProtocolDTO isoDto =  new InterventionalStudyProtocolDTO();       
       isoDto = (InterventionalStudyProtocolDTO) convertToStudyProtocolDTO(trialDTO, isoDto);
       return isoDto;
   }

}
