package gov.nih.nci.pa.action;
      
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.SearchPersonResultDisplay;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
      
/**
* @author Hong Gao
*
*/
@SuppressWarnings("PMD")
public class TrialValidationAction extends ActionSupport {

    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    private OrganizationDTO selectedLeadOrg = null;
    private PersonDTO selectedLeadPrincipalInvestigator = null;
    private OrganizationDTO selectedSponsor = null;
    private PersonDTO responsiblePartyContact = null;
    // Summary 4 Sponsor
    private OrganizationDTO selectedSummary4Sponsor = null;   
    private List<SearchPersonResultDisplay> persons = new ArrayList<SearchPersonResultDisplay>();
    
    /**  
     * @return res
     */
    public String query() {
        try {        

            @SuppressWarnings("PMD.UnusedLocalVariable")
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
            copySummaryFour(PaRegistry.getStudyResourcingService().getsummary4ReportedResource(studyProtocolIi));
            copyResponsibleParty(studyProtocolIi);
            copySponsor(studyProtocolIi);
        } catch (PAException e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        } 
        return "edit";
    }

    /**
     * 
     * @return String
     */
    public String update() {
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
            ServletActionContext.getRequest().setAttribute(
                    Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);            
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(
                    Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        return "edit";
    }
    
    private void copy(StudyProtocolDTO spDTO) {        
        gtdDTO.setOfficialTitle(spDTO.getOfficialTitle().getValue());
        gtdDTO.setPhaseCode(spDTO.getPhaseCode().getCode());
        gtdDTO.setPhaseOtherText(spDTO.getPhaseOtherText().getValue());
        gtdDTO.setPrimaryPurposeCode(spDTO.getPrimaryPurposeCode().getCode());
        gtdDTO.setPrimaryPurposeOtherText(spDTO.getPrimaryPurposeOtherText().getValue());
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
    private void copySummaryFour(StudyResourcingDTO srDTO) throws  PAException {
        if (srDTO == null) {
            return;
        }
        CorrelationUtils cUtils = new CorrelationUtils();
        Organization o = cUtils.getPAOrganizationByIndetifers(
                        Long.valueOf(srDTO.getOrganizationIdentifier().getExtension()), null);
        gtdDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
        gtdDTO.setSummaryFourOrgName(o.getName());
        if (srDTO.getTypeCode() != null) {
            gtdDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }
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
    private void updateStudyProtocol(Ii studyProtocolIi) throws PAException {
        StudyProtocolDTO spDTO = new StudyProtocolDTO();
        spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
        spDTO.setOfficialTitle(StConverter.convertToSt(gtdDTO.getOfficialTitle()));
        spDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(gtdDTO.getPhaseCode()))); 
        spDTO.setPrimaryPurposeCode(
                CdConverter.convertToCd(PrimaryPurposeCode.getByCode(gtdDTO.getPrimaryPurposeCode())));
        spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt(gtdDTO.getPrimaryPurposeOtherText()));
        spDTO.setPhaseOtherText(StConverter.convertToSt(gtdDTO.getPhaseOtherText()));
        PaRegistry.getStudyProtocolService().updateStudyProtocol(spDTO);
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
    
    /**
     * 
     * @return result
     */   
    public String displayLeadOrganization() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_org";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedLeadOrg = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);
            gtdDTO.setLeadOrganizationName(selectedLeadOrg.getName().getPart().get(0).getValue());
            gtdDTO.setLeadOrganizationIdentifier(selectedLeadOrg.getIdentifier().getExtension());            
        } catch (Exception e) {
            return "display_lead_org";
        }
        return "display_lead_org";
    }
    
    /**
     * 
     * @return result
     */   
    public String displayLeadPrincipalInvestigator() {
        String persId = ServletActionContext.getRequest().getParameter("persId");
        if (persId.equals("undefined")) {
            return "display_lead_prinicipal_inv";
        }
        try {
            selectedLeadPrincipalInvestigator = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setPiIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PersonDTO personDTO = 
                                            EnPnConverter.convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setPiName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            return "display_lead_prinicipal_inv";
        }
        return "display_lead_prinicipal_inv";
    }    
    
    /**
     * 
     * @return result
     */   
    public String displaySelectedSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_selected_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedSponsor = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);
            gtdDTO.setSponsorIdentifier(selectedSponsor.getIdentifier().getExtension());
            gtdDTO.setSponsorName(selectedSponsor.getName().getPart().get(0).getValue());
        } catch (Exception e) {
            return "display_selected_sponsor";
        }
        return "display_selected_sponsor";
    }    
    /**
     * 
     * @return result
     */   
    public String displaySummary4FundingSponsor() {
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        if (orgId.equals("undefined")) {
            return "display_summary4funding_sponsor";
        }
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
        try {
            selectedSummary4Sponsor = PaRegistry.getPoOrganizationEntityService().search(criteria).get(0);
            gtdDTO.setSummaryFourOrgName(selectedSummary4Sponsor.getName().getPart().get(0).getValue());
            gtdDTO.setSummaryFourOrgIdentifier(selectedSummary4Sponsor.getIdentifier().getExtension());
        } catch (Exception e) {
            return "display_summary4funding_sponsor";
        }
        return "display_summary4funding_sponsor";
    }    

  
    /**
     * 
     * @return res
     */
    public String getOrganizationContacts() {
        String orgContactIdentifier = ServletActionContext.getRequest().getParameter("orgContactIdentifier");
        OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
        /**
         * IMPORTANT
         * orgContactIdentifier MUST BE SET FROM THE DTO SINCE IT IS ALREADY THERE SINCE RESULTS WILL BE SCOPED 
         * BY THIS ORGCONTACTIDENTIFIER
         */  
        contactDTO.setScoperIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(orgContactIdentifier));
        contactDTO.getScoperIdentifier().setIdentifierName("NCI organization entity identifier");
        contactDTO.getScoperIdentifier().setRoot("UID.for.nci.entity.organization");
        try {
            List<OrganizationalContactDTO> list = PaRegistry.getPoOrganizationalContactCorrelationService()
                    .search(contactDTO);
            for (OrganizationalContactDTO organizationalContactDTO : list) {
                try {
                    PersonDTO resultDTO = PaRegistry.getPoPersonEntityService().getPerson(
                            organizationalContactDTO.getPlayerIdentifier());
                    persons.add(convertToPaPerson(resultDTO));
                } catch (NullifiedEntityException e) {
                    addActionError(e.getMessage());
                    ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
                    LOG.error("Exception occured while getting organization contact : " + e);
                    return "display_org_contacts";
                }
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            ServletActionContext.getRequest().setAttribute("failureMessage", e.getMessage());
            LOG.error("Exception occured while getting organization contact : " + e);
            return "display_org_contacts";
        }
        return "display_org_contacts";
    }  
    
    private SearchPersonResultDisplay convertToPaPerson(PersonDTO poPerson) {
        SearchPersonResultDisplay prs = new SearchPersonResultDisplay();
        List<Enxp> list = poPerson.getName().getPart();
        Iterator ite = list.iterator();
        while (ite.hasNext()) {
           Enxp part = (Enxp) ite.next();          
           if (EntityNamePartType.FAM == part.getType()) {
               prs.setLastName(part.getValue());
           } else if (EntityNamePartType.GIV == part.getType()) {
               if (prs.getFirstName() == null) {
                   prs.setFirstName(part.getValue());
               } else {
                   prs.setMiddleName(part.getValue());
               }
           }
        }
        prs.setId(Long.valueOf(poPerson.getIdentifier().getExtension().toString()));
        return prs;
    }    
    
    /**
     * 
     * @return res
     */
    public String createOrganizationContacts() {
        boolean contactExists = false;
        try {
            String orgId = ServletActionContext.getRequest().getParameter("orgId");
            String persId = ServletActionContext.getRequest().getParameter("persId");
            OrganizationalContactDTO dto = new OrganizationalContactDTO();
            dto.setScoperIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(orgId));
            dto.getScoperIdentifier().setRoot("UID.for.nci.entity.organization");
            dto.getScoperIdentifier().setIdentifierName("NCI organization entity identifier");
            // Use these two values and check if the contact already exists, if
            // they do then this means that the user selected from the list and
            // did not create a new user
            List<OrganizationalContactDTO> list = PaRegistry.getPoOrganizationalContactCorrelationService()
                    .search(dto);
            //             
            for (OrganizationalContactDTO contactDTO : list) {
                String persIdfromOrgContact = contactDTO.getPlayerIdentifier().getExtension();
                if (persIdfromOrgContact.equals(persId)) {
                    contactExists = true;
                }
            }
            if (!contactExists) {
                dto.setPlayerIdentifier(gov.nih.nci.pa.iso.util.IiConverter.convertToIi(persId));
                dto.getPlayerIdentifier().setRoot("UID.for.nci.entity.person");
                dto.getPlayerIdentifier().setIdentifierName("NCI person entity identifier");
                dto.setPrimaryIndicator(BlConverter.convertToBl(Boolean.TRUE));
                PaRegistry.getPoOrganizationalContactCorrelationService().createCorrelation(dto);
            }
            Ii personIi = gov.nih.nci.pa.iso.util.IiConverter.convertToIi(persId);
            personIi.setRoot("UID.for.nci.entity.organization");
            responsiblePartyContact = PaRegistry.getPoPersonEntityService().getPerson(personIi);
            gtdDTO.setResponsiblePersonIdentifier(responsiblePartyContact.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PersonDTO personDTO = 
                                            EnPnConverter.convertToPaPersonDTO(responsiblePartyContact);
            gtdDTO.setResponsiblePersonName(personDTO.getLastName() + "," + personDTO.getFirstName());
           // ServletActionContext.getRequest().getSession()
            //        .setAttribute("PoResponsibleContact", responsiblePartyContact);
        } catch (NullifiedEntityException e) {
            // TODO Auto-generated catch block NAVEEN HANDLE EXCEPTIONS!!
            e.printStackTrace();
        } catch (EntityValidationException e) {
            // TODO Auto-generated catch block NAVEEN HANDLE EXCEPTIONS!!
        } catch (PAException e) {
            //NAVEEN HANDLE EXCEPTIONS!!
        }
        return "display_responsible_contact";
    }    
    /**
     * 
     * @return selectedLeadOrg
     */
    public OrganizationDTO getSelectedLeadOrg() {
        return selectedLeadOrg;
    }

    /**
     * 
     * @param selectedLeadOrg selectedLeadOrg
     */
    public void setSelectedLeadOrg(OrganizationDTO selectedLeadOrg) {
        this.selectedLeadOrg = selectedLeadOrg;
    }

    /**
     * @return the persons
     */
    public List<SearchPersonResultDisplay> getPersons() {
        return persons;
    }

    /**
     * @param persons the persons to set
     */
    public void setPersons(List<SearchPersonResultDisplay> persons) {
        this.persons = persons;
    }
    
}