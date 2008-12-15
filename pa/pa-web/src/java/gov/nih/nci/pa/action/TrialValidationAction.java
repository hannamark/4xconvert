package gov.nih.nci.pa.action;
      
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.GeneralTrialDesignWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
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
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.ClinicalResearchStaffCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.HealthCareProviderCorrelationBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.SearchPersonResultDisplay;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
      
/**
* @author Naveen AMiruddin
*
*/
@SuppressWarnings("PMD")
public class TrialValidationAction extends ActionSupport {

    private static final String EDIT = "edit";    
    private GeneralTrialDesignWebDTO gtdDTO = new GeneralTrialDesignWebDTO();
    private OrganizationDTO selectedLeadOrg = null;
    private PersonDTO selectedLeadPrincipalInvestigator = null;
    private OrganizationDTO selectedSponsor = null;
    private PersonDTO responsiblePartyContact = null;
    // Summary 4 Sponsor
    private OrganizationDTO selectedSummary4Sponsor = null;   
    private List<SearchPersonResultDisplay> persons = new ArrayList<SearchPersonResultDisplay>();
    private static final int MAXIMUM_CHAR = 200;
    
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
        return EDIT;
    }

    /**
     * 
     * @return String
     */
    public String update() {
      enforceBusinessRules();
      if (hasFieldErrors()) {
        return EDIT;
      }
      save();    
      return EDIT;
    }
    /**
     * 
     * @return String
     */
    public String accept() {
      enforceBusinessRules();
      if (hasFieldErrors()) {
        return EDIT;
      }
      save();    
      createDocumentWfStatus(DocumentWorkflowStatusCode.ACCEPTED);
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Accepted");            
      return EDIT;
    }
    
    /**
     * 
     * @return String
     */
    public String reject() {
      enforceBusinessRules();
      if (hasFieldErrors()) {
        return EDIT;
      }
      save();
      return "rejectReason";
    }

    /**
     *  @return String
     */
    public String rejectReason() {
      if (PAUtil.isEmpty(gtdDTO.getCommentText())) {
        addFieldError("gtdDTO.commentText",
            getText("Rejection Reason must be Entered"));
      }
      if (hasFieldErrors()) {
        return "rejectReason";
      }
      createDocumentWfStatus(DocumentWorkflowStatusCode.REJECTED);
      sendEmail();
      query();
      ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Study Protocol Rejected");            

      return EDIT;
    }

    private void sendEmail() {
      try {
        
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
        .getRequest().getSession().getAttribute(Constants.TRIAL_SUMMARY);
        
        String commentText = getCommentText();
        
        Properties props = System.getProperties();

        // Set up mail server
        props.put("mail.smtp.host", PaRegistry.getLookUpTableService().getPropertyValue("smtp"));

        // Get session
        Session session = Session.getDefaultInstance(props, null);

        // Define Message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(PaRegistry.getLookUpTableService().getPropertyValue("fromaddress")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(spDTO.getUserLastCreated()));
       message.setSentDate(new java.util.Date());
        message.setSubject(PaRegistry.getLookUpTableService().getPropertyValue("rejection.subject"));

        // body
        Multipart multipart = new MimeMultipart();
        BodyPart msgPart = new MimeBodyPart();
        String body = PaRegistry.getLookUpTableService().getPropertyValue("rejection.body");
        String mailBody1 = body.replace("${leadorgid}", spDTO.getLeadOrganizationId().toString());
        String mailBody2 = mailBody1.replace("${reasoncode}", commentText);
        msgPart.setText(mailBody2);
        multipart.addBodyPart(msgPart);
        message.setContent(multipart); 
        // Send Message
        Transport.send(message);

      } catch (Exception e) {
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
      }
    }

    private String getCommentText() throws PAException {
      String commentText = "";
      Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
      getAttribute(Constants.STUDY_PROTOCOL_II);

      List<DocumentWorkflowStatusDTO> dtoList = PaRegistry.getDocumentWorkflowStatusService()
                          .getByStudyProtocol(studyProtocolIi);
      for (DocumentWorkflowStatusDTO dto : dtoList) {
        if (dto.getStatusCode().getCode().equalsIgnoreCase(DocumentWorkflowStatusCode.REJECTED.getCode()) 
            && dto.getCommentText() != null) {
          commentText = dto.getCommentText().getValue();
        }
        
      }
      return commentText;
    }
    
    private void createDocumentWfStatus(DocumentWorkflowStatusCode dws) {
        try {
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            
            DocumentWorkflowStatusDTO dwsDto = new DocumentWorkflowStatusDTO();
            dwsDto.setStatusCode(CdConverter.convertToCd(dws));
            dwsDto.setStatusDateRange(TsConverter.convertToTs(
                              new Timestamp(new Date().getTime())));
            dwsDto.setStudyProtocolIdentifier(studyProtocolIi);
            if (gtdDTO.getCommentText() != null) {
              dwsDto.setCommentText(StConverter.convertToSt(gtdDTO.getCommentText()));
            }
            PaRegistry.getDocumentWorkflowStatusService().create(dwsDto);

            StudyProtocolQueryDTO  studyProtocolQueryDTO = 
                PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                        Long.valueOf(studyProtocolIi.getExtension()));
            // put an entry in the session and store StudyProtocolQueryDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        }
        
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

        StudyResourcingDTO summary4ResoureDTO = PaRegistry.getStudyResourcingService().getsummary4ReportedResource(
            studyProtocolIi);
        if (summary4ResoureDTO == null) {
          PaRegistry.getStudyResourcingService().
          createStudyResourcing(createSummaryFour(new StudyResourcingDTO() , studyProtocolIi));   
        } else {
          PaRegistry.getStudyResourcingService().updateStudyResourcing(
              createSummaryFour(summary4ResoureDTO , studyProtocolIi));
        }

        ServletActionContext.getRequest().setAttribute(
            Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);            
        StudyProtocolQueryDTO  studyProtocolQueryDTO = 
          PaRegistry.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
              Long.valueOf(studyProtocolIi.getExtension()));
        // put an entry in the session and store StudyProtocolQueryDTO 
        ServletActionContext.getRequest().getSession().setAttribute(
            Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);


      } catch (Exception e) {
        ServletActionContext.getRequest().setAttribute(
            Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
      }

    }

    private void enforceBusinessRules() {
      if (PAUtil.isEmpty(gtdDTO.getLocalProtocolIdentifier())) {
        addFieldError("gtdDTO.LocalProtocolIdentifier", getText("Organization Trial ID must be Entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getOfficialTitle())) {
        addFieldError("gtdDTO.OfficialTitle", getText("OfficialTitle must be Entered"));
      }
      if (PAUtil.isEmpty(gtdDTO.getPhaseCode())) {
        addFieldError("gtdDTO.phaseCode", getText("error.phase"));
      }
      if (PAUtil.isNotEmpty(gtdDTO.getPhaseCode()) && gtdDTO.getPhaseCode().equalsIgnoreCase("other") 
              && PAUtil.isEmpty(gtdDTO.getPhaseOtherText())) {
          addFieldError("gtdDTO.phaseOtherText",
                  getText("Phase Code other text must be entered"));
          
      }
      if (PAUtil.isNotEmpty(gtdDTO.getPhaseCode()) && !gtdDTO.getPhaseCode().equalsIgnoreCase("other") 
              && PAUtil.isNotEmpty(gtdDTO.getPhaseOtherText())) {
          addFieldError("gtdDTO.phaseOtherText",
                  getText("Phase Code other must be entered only when Phase Code is Other"));
      }

      if (PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeOtherText())
          && gtdDTO.getPrimaryPurposeOtherText().length() > MAXIMUM_CHAR) {
        addFieldError("gtdDTO.primaryPurposeOtherText", getText("error.spType.other.maximumChar"));        
      }      
      if (PAUtil.isEmpty(gtdDTO.getPrimaryPurposeCode())) {
          addFieldError("gtdDTO.primaryPurposeCode", getText("error.primary"));
      }
      if (PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeCode()) && gtdDTO.getPrimaryPurposeCode().equalsIgnoreCase("other") 
              && PAUtil.isEmpty(gtdDTO.getPrimaryPurposeOtherText())) {
          addFieldError("gtdDTO.primaryPurposeOtherText",
                  getText("Primary Purpose Other other text must be entered"));
          
      }
      if (PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeCode()) 
              && !gtdDTO.getPrimaryPurposeCode().equalsIgnoreCase("other") 
              && PAUtil.isNotEmpty(gtdDTO.getPrimaryPurposeOtherText())) {
          addFieldError("gtdDTO.primaryPurposeOtherText",
                  getText("Primary Purpose Code other must be entered only when Primary Purpose Code is Other"));
      }
      if (gtdDTO.getResponsiblePartyType().equalsIgnoreCase("sponsor") 
              && PAUtil.isEmpty(gtdDTO.getResponsiblePersonName())) {
          addFieldError("gtdDTO.responsiblePersonName",
                  getText("Responsible Party Contact must be entered when Responsible Party is Sponsor"));
              
          
      }

      if (PAUtil.isEmpty(gtdDTO.getContactEmail())) {
        addFieldError("gtdDTO.contactEmail", getText("Email must be Entered"));
      }
      if (PAUtil.isNotEmpty(gtdDTO.getContactEmail()) && !PAUtil.isValidEmail(gtdDTO.getContactEmail())) {
          addFieldError("gtdDTO.contactEmail", getText("Email entered is not a valid format"));
      }
      
      if (PAUtil.isEmpty(gtdDTO.getContactPhone())) {
        addFieldError("gtdDTO.contactPhone", getText("Phone must be Entered"));
      }

    }
    
    private StudyResourcingDTO createSummaryFour(StudyResourcingDTO summary4ResoureDTO , Ii studyProtocolIi) 
    throws PAException {
        
        String poIdentifer = gtdDTO.getSummaryFourOrgIdentifier();
        CorrelationUtils cUtils = new CorrelationUtils();
        Long orgId = null;
        if (PAUtil.isNotEmpty(poIdentifer)) {
            Organization org = cUtils.getPAOrganizationByIndetifers(null, poIdentifer);
            if (org == null) {
                OrganizationCorrelationServiceBean ocsb = new OrganizationCorrelationServiceBean();
                OrganizationDTO oDto;
                try {
                    oDto = PaRegistry.getPoOrganizationEntityService().getOrganization(
                            IiConverter.converToPoOrganizationIi(poIdentifer));
                } catch (NullifiedEntityException e) {
                    throw new PAException(e);
                } 
                orgId = ocsb.createPAOrganizationUsingPO(oDto).getId();
            } else {
                // get the org from the database
                orgId = org.getId();
            }
            
        }
        
        summary4ResoureDTO.setStudyProtocolIi(studyProtocolIi);
        summary4ResoureDTO.setSummary4ReportedResourceIndicator(BlConverter.convertToBl(Boolean.TRUE));
        if (PAUtil.isNotEmpty(gtdDTO.getSummaryFourFundingCategoryCode())) {
            summary4ResoureDTO.setTypeCode(CdConverter.convertToCd(SummaryFourFundingCategoryCode
                    .getByCode(gtdDTO.getSummaryFourFundingCategoryCode())));
        } else {
            Cd cd = new Cd();
            cd.setNullFlavor(NullFlavor.NI);
            summary4ResoureDTO.setTypeCode(cd);
        }
        summary4ResoureDTO.setOrganizationIdentifier(IiConverter.convertToIi(orgId));
        return summary4ResoureDTO; 
    }

    private void copySummaryFour(StudyResourcingDTO srDTO) throws  PAException {
        if (srDTO == null) {
            return;
        }
        if (srDTO.getTypeCode() != null) {
            gtdDTO.setSummaryFourFundingCategoryCode(srDTO.getTypeCode().getCode());
        }

        if (srDTO.getOrganizationIdentifier() != null 
                    && PAUtil.isNotEmpty(srDTO.getOrganizationIdentifier().getExtension())) {   
            CorrelationUtils cUtils = new CorrelationUtils();
            Organization o = cUtils.getPAOrganizationByIndetifers(
                            Long.valueOf(srDTO.getOrganizationIdentifier().getExtension()), null);
            gtdDTO.setSummaryFourOrgIdentifier(o.getIdentifier());
            gtdDTO.setSummaryFourOrgName(o.getName());
        }
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
        String phone = gtdDTO.getContactPhone();
        phone = gtdDTO.getContactPhone().trim().replaceAll(" ", "");
        if (gtdDTO.getResponsiblePartyType() == null || gtdDTO.getResponsiblePartyType().equals("pi")) {
            parb.createPIAsResponsiblePartyRelations(
                    gtdDTO.getSponsorIdentifier(), gtdDTO.getPiIdentifier(), 
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
        } else if (gtdDTO.getResponsiblePartyType().equals("sponsor")) { 
            parb.createSponsorAsPrimaryContactRelations(gtdDTO.getSponsorIdentifier(),
                    gtdDTO.getResponsiblePersonIdentifier(), 
                    Long.valueOf(studyProtocolIi.getExtension()),  gtdDTO.getContactEmail(), phone);
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
     * @return result
     */   
    public String displayCentralContact() {
        PersonDTO centralContact = null;
        String persId = ServletActionContext.getRequest().getParameter("persId");
        try {
            centralContact = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gov.nih.nci.pa.dto.PersonDTO personDTO = 
                EnPnConverter.convertToPaPersonDTO(centralContact);

            gtdDTO.setCentralContactIdentifier(centralContact.getIdentifier().getExtension());
            gtdDTO.setCentralContactName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (Exception e) {
            return "central_contact";
        }
        return "central_contact";
    }    
    
  
    /**
     * 
     * @return res
     */
    public String getOrganizationContacts() {
        String orgContactIdentifier = ServletActionContext.getRequest().getParameter("orgContactIdentifier");
        OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
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
        } catch (Exception e) {
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
        try {
            //String orgId = ServletActionContext.getRequest().getParameter("orgId");
            String persId = ServletActionContext.getRequest().getParameter("persId");
            selectedLeadPrincipalInvestigator = PaRegistry.getPoPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            gtdDTO.setResponsiblePersonIdentifier(selectedLeadPrincipalInvestigator.getIdentifier().getExtension());
            gov.nih.nci.pa.dto.PersonDTO personDTO = 
                                            EnPnConverter.convertToPaPersonDTO(selectedLeadPrincipalInvestigator);
            gtdDTO.setResponsiblePersonName(personDTO.getLastName() + "," + personDTO.getFirstName());
        } catch (NullifiedEntityException e) {
            // TODO Auto-generated catch block NAVEEN HANDLE EXCEPTIONS!!
            e.printStackTrace();
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