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
@SuppressWarnings("PMD")
public class GeneralTrialDesignAction extends ActionSupport {
    
    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    
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
       // enforceBusinessRules();
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
                        gtdDTO.getLeadOrganizationIdentifier() , gtdDTO.getLocalProtocolIdentifier());
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
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);            
            
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
        if (scDtos != null && scDtos.size() > 0) {
            gtdDTO.setResponsiblePartyType("pi");
            scDto = scDtos.get(0);
            dset = scDto.getTelecomAddresses();
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = PaRegistry.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            gtdDTO.setResponsiblePartyType("sponsor");
            if (spDtos != null && spDtos.size() > 0) {
                gtdDTO.setResponsiblePartyType("sponsor");
                spart = spDtos.get(0);
                dset = spart.getTelecomAddresses();
                CorrelationUtils cUtils = new CorrelationUtils();
                Person p = cUtils.getPAPersonByPAOrganizationalContactId((
                        Long.valueOf(spart.getOrganizationalContactIi().getExtension())));
                gtdDTO.setResponsiblePersonIdentifier(p.getIdentifier());
                gtdDTO.setResponsiblePersonName(p.getFirstName());
                
                
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
        if (phones != null && phones.size() > 0) {
            gtdDTO.setContactPhone(phones.get(0));
        }
        if (emails != null && emails.size() > 0) {
            gtdDTO.setContactEmail(emails.get(0));
        }        
    }
    private void copySponsor(Ii studyProtocolIi) throws PAException {
        StudyParticipationDTO spart = new StudyParticipationDTO();
        spart.setFunctionalCode(CdConverter.convertToCd(StudyParticipationFunctionalCode.SPONSOR));
        List<StudyParticipationDTO> spDtos = PaRegistry.getStudyParticipationService()
                        .getByStudyProtocol(studyProtocolIi, spart);
        if (spDtos != null && spDtos.size() > 0) {
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
            if (phones != null && phones.size() > 0) {
                gtdDTO.setCentralContactPhone(phones.get(0));
            }
            if (emails != null && emails.size() > 0) {
                gtdDTO.setCentralContactEmail(emails.get(0));
            }        

        }
   }

    
    private void updateStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setOfficialTitle(StConverter.convertToSt(gtdDTO.getOfficialTitle()));
//        spDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(gtdDTO.getPhaseCode()))); 
//        spDTO.setPrimaryPurposeCode(
//                CdConverter.convertToCd(PrimaryPurposeCode.getByCode(gtdDTO.getPrimaryPurposeCode())));
//        spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(gtdDTO.getPrimaryPurposeOtherText()));
//        spDTO.setPhaseOtherText(StConverter.convertToSt(gtdDTO.getPhaseOtherText()));
        spDTO.setAcronym(StConverter.convertToSt(gtdDTO.getAcronym()));
        spDTO.setPublicDescription(StConverter.convertToSt(gtdDTO.getPublicDescription()));
        spDTO.setScientificDescription(StConverter.convertToSt(gtdDTO.getScientificDescription()));
        spDTO.setKeywordText(StConverter.convertToSt(gtdDTO.getKeywordText()));
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
        if (srDtos == null || srDtos.isEmpty() || srDtos.size() > 1) {
            throw new PAException(" StudyParticipation is either null , or more than one lead is found for a "  
                    + " given Study Protocol id = " +  studyProtocolIi.getExtension());
        }
        spDto = srDtos.get(0);
        OrganizationCorrelationServiceBean ocb = new OrganizationCorrelationServiceBean();
        spDto.setResearchOrganizationIi(IiConverter.convertToIi(ocb.createResearchOrganizationCorrelations(roId)));
        spDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(lpIdentifier));
        PaRegistry.getStudyParticipationService().update(spDto);
        
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
        Long crs = crbb.createClinicalResearchStaffCorrelations(
                gtdDTO.getLeadOrganizationIdentifier(), gtdDTO.getCentralContactIdentifier());
        scDTO.setClinicalResearchStaffIi(IiConverter.convertToIi(crs));
        scDTO.setStudyProtocolIdentifier(studyProtocolIi);
        List<String> phones = new ArrayList<String>();
        phones.add(gtdDTO.getCentralContactPhone());
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
        if (scDtos != null && scDtos.size() > 0) {
            scDto = scDtos.get(0);
            PaRegistry.getStudyContactService().delete(scDtos.get(0).getIdentifier());
        } else {
            StudyParticipationContactDTO spart = new StudyParticipationContactDTO();
            spart.setRoleCode(CdConverter.convertToCd(
                    StudyParticipationContactRoleCode.RESPONSIBLE_PARTY_SPONSOR_CONTACT));
            List<StudyParticipationContactDTO> spDtos = PaRegistry.getStudyParticipationContactService()
                .getByStudyProtocol(studyProtocolIi, spart);
            if (spDtos != null && spDtos.size() > 0) {
                PaRegistry.getStudyParticipationContactService().delete(spDtos.get(0).getIdentifier());
            }
        }
        
    }
    private void createSponorContact(Ii studyProtocolIi) throws  PAException {
        PARelationServiceBean parb = new PARelationServiceBean();
        
        if (gtdDTO.getResponsiblePartyType() == null || gtdDTO.getResponsiblePartyType().equals("pi")) {
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getSponsorIdentifier(), gtdDTO.getPiIdentifier(), 
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), gtdDTO.getContactPhone());
        } else if (gtdDTO.getResponsiblePartyType().equals("sponsor")) { 
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getSponsorIdentifier(), gtdDTO.getPiIdentifier(), 
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), gtdDTO.getContactPhone());
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
}
