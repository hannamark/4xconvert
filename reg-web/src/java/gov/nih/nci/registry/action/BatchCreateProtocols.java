package gov.nih.nci.registry.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PARelationServiceBean;
import gov.nih.nci.pa.util.PAUtil;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.RegistryUtil;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
/**
 * @author Vrushali
 * 
 */
@SuppressWarnings("PMD")
public class BatchCreateProtocols {
    private static Logger log = Logger.getLogger(BatchCreateProtocols.class);
    private static final int MAXF = 1024;
    private HashMap<String, String> map = null;
    /**
     * 
     * @param dtoList list
     * @param folderPath path 
     * @param userName name
     * @return map
     * @throws PAException ex
     */
    public HashMap createProtocols(List<StudyProtocolBatchDTO> dtoList, String folderPath, String userName)
            throws PAException {
        log.error("Entering into createProtocols...having size of dtolist"
                + dtoList.size());
        if (dtoList == null || dtoList.size() < 1) {
            throw new PAException("DTO list is Empty");
        }
        Iterator iterator = dtoList.iterator();
        map = new HashMap<String, String>();
        String result = "";
        TrialBatchDataValidator validator = null;
        while (iterator.hasNext()) {
            StudyProtocolBatchDTO batchDto = (StudyProtocolBatchDTO) iterator
                    .next();
            // check if the record qualifies to be added
            //validate the record
            
            if (batchDto != null) { // && batchDto.isValidRecord()) {
                result = "";
                validator = new TrialBatchDataValidator();
                result = validator.validateForm(batchDto);
                if (null == result || result.length() < 1) {
                    result = buildProtocol(batchDto, folderPath , userName);    
                } else {
                    result = "Trial submission failed for Lead Organization Trial Identifier " 
                    + batchDto.getLocalProtocolIdentifier() +  " " + result + "\n";
                }
                log.error("putting values in map local protocol Id as " 
                        + batchDto.getLocalProtocolIdentifier() + "and response as " + result);
                map.put(batchDto.getLocalProtocolIdentifier() , result);
            }
        }
        log.error("leaving into createProtocols...");
        return map;
    }

    /**
     * 
     * @param dto dto
     * @param folderPath path
     * @param userName name
     * @return protocol Id
     * @throws PAException ex
     */
    private String buildProtocol(StudyProtocolBatchDTO dto , String folderPath , String userName)  {

        Ii studyProtocolIi = null;
        String protocolAssignedId  = null;
        log.error("Entering into buildProtocol...");
        // before creating the protocol check for duplicate
        // using the Lead Org Trial Identifier and Lead Org Identifier
/*        Organization paOrg = new Organization();

        paOrg.setIdentifier(dto.getLeadOrgNCIIdentifier());
        paOrg = RegistryServiceLocator.getPAOrganizationService()
                .getOrganizationByIndetifers(paOrg);

        if (paOrg != null && paOrg.getId() != null) {
            StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
            criteria.setLeadOrganizationTrialIdentifier(dto
                    .getLeadOrgNCIIdentifier());
            criteria.setLeadOrganizationId(paOrg.getId());

            List<StudyProtocolQueryDTO> records = RegistryServiceLocator
                    .getProtocolQueryService().getStudyProtocolByCriteria(
                            criteria);
            if (records != null && records.size() > 0) {
                throw new PAException(
                        "Duplicate Trial Submission: A trial exists in the system "
                                + " for the Lead Organization selected and entered Trial Identifier");
            }
        }

*/      try {  
        log.info("TrialType() " + dto.getTrialType());
        if (dto.getTrialType().equals("Observational")) {
            studyProtocolIi = RegistryServiceLocator
                    .getStudyProtocolService()
                    .createObservationalStudyProtocol(
                            (ObservationalStudyProtocolDTO) createProtocolDTO(dto, userName));
        } else {
            studyProtocolIi = RegistryServiceLocator
                    .getStudyProtocolService()
                    .createInterventionalStudyProtocol(
                            (InterventionalStudyProtocolDTO) createProtocolDTO(dto, userName));
        }
        log.error("Trial is registered with ID: "
                + IiConverter.convertToString(studyProtocolIi));
        // create study overall status
        createStudyStatus(studyProtocolIi, dto);
        // create IND/IDE information *One- times*
         createIndIdeIndicators(studyProtocolIi, dto);
        // create the Study Grants One- times*
        createStudyResources(studyProtocolIi, dto);
        if (PAUtil.isNotEmpty(dto.getProtcolDocumentFileName())) {
            uploadDocument(studyProtocolIi, DocumentTypeCode.Protocol_Document.getCode(), 
                      dto.getProtcolDocumentFileName(), folderPath);
        }
        if (PAUtil.isNotEmpty(dto.getIrbApprovalDocumentFileName())) {
            uploadDocument(studyProtocolIi, DocumentTypeCode.IRB_Approval_Document.getCode(),
                     dto.getIrbApprovalDocumentFileName(), folderPath);
        }
        
        if (PAUtil.isNotEmpty(dto.getInformedConsentDocumentFileName())) {
            uploadDocument(studyProtocolIi, DocumentTypeCode.Informed_Consent_Document.getCode(),
                    dto.getInformedConsentDocumentFileName(), folderPath);
        }
        
        if (PAUtil.isNotEmpty(dto.getParticipatinSiteDocumentFileName())) {
            uploadDocument(studyProtocolIi,
                    DocumentTypeCode.Participating_sites.getCode(),
                    dto.getParticipatinSiteDocumentFileName(), folderPath);
        }
        
        if (PAUtil.isNotEmpty(dto.getOtherTrialRelDocumentFileName())) {
            uploadDocument(studyProtocolIi, DocumentTypeCode.Other.getCode(), 
                     dto.getOtherTrialRelDocumentFileName(), folderPath);  
        }
        log.error("Before Summ4Funding lookup");
        //Summary 4 Info
        OrganizationBatchDTO summ4Sponsor = buildSummary4Sponsor(dto);
        if (!orgDTOIsEmpty(summ4Sponsor)) {
            //look up for org only when dto is not empty
            Ii selectedSummary4Sponsor = lookUpOrgs(summ4Sponsor);
            if (selectedSummary4Sponsor != null) {
                    new PARelationServiceBean().createSummary4ReportedSource(selectedSummary4Sponsor.getExtension(), 
                            SummaryFourFundingCategoryCode.getByCode(dto.getSumm4FundingCat()), IiConverter
                            .convertToLong(studyProtocolIi));
            }
        } else {
            log.error("Summary 4 Info is Empty ...");
        }
        // create/Lookup the org
        log.error("buildProtocol -Create or lookup the org ");
        OrganizationBatchDTO orgDto = buildLeadOrgDto(dto);
        Ii orgIdIi = lookUpOrgs(orgDto);
        if (null != orgIdIi) {
                new PARelationServiceBean().createLeadOrganizationRelations(orgIdIi
                        .getExtension(),
                        IiConverter.convertToLong(studyProtocolIi), dto
                                .getLocalProtocolIdentifier());
            }
        log.error("buildProtocol -Create or lookup the Person");
        //look up Person
        PersonBatchDTO piDto = buildLeadPIDto(dto);
        Ii leadPrincipalInvestigator = lookUpPersons(piDto);
        if (leadPrincipalInvestigator != null) {
            new PARelationServiceBean().createPrincipalInvestigatorRelations(orgIdIi.getExtension(), 
                    leadPrincipalInvestigator.getExtension(), IiConverter
            .convertToLong(studyProtocolIi), StudyTypeCode.getByCode(dto.getTrialType()));
        }
        log.error("buildProtocol -Create or lookup the Sponsor ");
        //look up sponser
        OrganizationBatchDTO sponsorOrgDto = buildSponsorOrgDto(dto);
        Ii sponsorIdIi = lookUpOrgs(sponsorOrgDto);
        if (sponsorIdIi != null) {
            new PARelationServiceBean().createSponsorRelations(sponsorIdIi.getExtension(), 
                    IiConverter.convertToLong(studyProtocolIi));
            if (dto.getResponsibleParty().equalsIgnoreCase("pi")) {
                new PARelationServiceBean().createPIAsResponsiblePartyRelations(sponsorIdIi.getExtension(), 
                        leadPrincipalInvestigator.getExtension(),
                        IiConverter.convertToLong(studyProtocolIi), dto.getPiEmail(), dto.getPiPhone());
            } else {
                //look up new Person or create if needed.
                PersonBatchDTO sponsorPersonDto = buildSponsorContact(dto);
                Ii responsiblePartyContact = lookUpPersons(sponsorPersonDto);
                new PARelationServiceBean().createSponsorAsPrimaryContactRelations(sponsorIdIi.getExtension(), 
                        responsiblePartyContact.getExtension(), IiConverter
                        .convertToLong(studyProtocolIi), dto.getSponsorContactEmail(), dto.getSponsorContactPhone());
            }
            
        }
        log.error("sponsor relation done...");
        //get the protocol
         protocolAssignedId = 
             RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi).
             getAssignedIdentifier().getExtension().toString();
         protocolAssignedId = "Trial with Lead Organization Trial Identifier " + dto.getLocalProtocolIdentifier()
             + " has been successfully registered and assigned the NCI Identifier " + protocolAssignedId + "\n";
        } catch (PAException ex) {
            log.error("buildprotocol exception-" + ex.getMessage());
            protocolAssignedId =  "Trial submission failed for Lead Organization Trial Identifier " 
            + dto.getLocalProtocolIdentifier() + ex.getMessage() + "\n";
        } catch (Exception exc) {
        protocolAssignedId =  "Trial submission failed for Lead Organization Trial Identifier " 
            + dto.getLocalProtocolIdentifier() + exc.getMessage() + "\n";
        log.error("buildprotocol exception-" + exc.getMessage());
    }
        log.error("response " + protocolAssignedId);
        return protocolAssignedId;
    }
    private StudyProtocolDTO createProtocolDTO(StudyProtocolBatchDTO batchDto, String userName) {
        StudyProtocolDTO protocolDTO = null;
        if (batchDto.getTrialType().equals("Observational")) {
            protocolDTO = new ObservationalStudyProtocolDTO();
        } else {
            protocolDTO = new InterventionalStudyProtocolDTO();
        }
        protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode
                .getByCode(batchDto.getPhase())));
        if (PAUtil.isNotEmpty(batchDto.getPhaseOtherValueSp())) {
            protocolDTO.setPhaseOtherText(
                    StConverter.convertToSt(batchDto.getPhaseOtherValueSp()));
        }
        protocolDTO.setOfficialTitle(StConverter.convertToSt(batchDto
                .getTitle()));
        protocolDTO.setStartDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(batchDto.getStudyStartDate())));
        protocolDTO.setPrimaryCompletionDate(TsConverter.convertToTs(PAUtil
                .dateStringToTimestamp(batchDto.getPrimaryCompletionDate())));
        protocolDTO.setStartDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(batchDto
                        .getStudyStartDateType())));
        protocolDTO.setPrimaryCompletionDateTypeCode(CdConverter
                .convertToCd(ActualAnticipatedTypeCode.getByCode(batchDto
                        .getPrimaryCompletionDateType())));
        protocolDTO.setPrimaryPurposeCode(CdConverter
                .convertToCd(PrimaryPurposeCode.getByCode(batchDto
                        .getPrimaryPurpose())));
        if (PAUtil.isNotEmpty(batchDto.getPrimaryPurposeOtherValueSp())) {
            protocolDTO.setPrimaryPurposeOtherText(
                    StConverter.convertToSt(batchDto.getPrimaryPurposeOtherValueSp()));
        }
        log.error("Setting User Name as " + userName);
        protocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
        return protocolDTO;
    }

    private void createStudyStatus(Ii studyProtocolIi,
            StudyProtocolBatchDTO batchDto) throws PAException {
            // create study overall status
            StudyOverallStatusDTO overallStatusDTO = new StudyOverallStatusDTO();
            // overallStatusDTO.setIi(IiConverter.convertToIi((Long) null));
            overallStatusDTO.setStudyProtocolIi(studyProtocolIi);
            overallStatusDTO.setStatusCode(CdConverter
                    .convertToCd(StudyStatusCode.getByCode(batchDto
                            .getCurrentTrialStatus())));
            overallStatusDTO.setStatusDate(TsConverter
                    .convertToTs(PAUtil.dateStringToTimestamp(batchDto
                            .getCurrentTrialStatusDate())));
            RegistryServiceLocator.getStudyOverallStatusService().create(
                    overallStatusDTO);
            log.error("leaving createStudyStatus....");
    }

    /**
     * 
     * @param studyProtocolIi
     *            studyProtocolIi
     * @param batchDto
     *            batchDto
     * @throws PAException 
     */
    private void createStudyResources(Ii studyProtocolIi,
            StudyProtocolBatchDTO batchDto) throws PAException {
            StudyResourcingDTO studyResoureDTO = null;
            studyResoureDTO = new StudyResourcingDTO();
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter
                    .convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(CdConverter
                    .convertStringToCd(batchDto.getNihGrantFundingMechanism()));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter
                    .convertToCd(MonitorCode.getByCode(batchDto
                            .getNihGrantNCIDivisionCode())));
            studyResoureDTO.setNihInstitutionCode(CdConverter
                    .convertStringToCd(batchDto.getNihGrantInstituteCode()));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(batchDto
                    .getNihGrantSrNumber()));
            RegistryServiceLocator.getStudyResourcingService()
                    .createStudyResourcing(studyResoureDTO);
            log.error("leaving createStudyResources ....");
     }

    /**
     * This method is first look up the Org. If org Found then return the id
     * else it will create the new org assumpion- even if the lookup return
     * multiple org we are taking the first one
     * 
     * @param batchDto
     *            batchDto
     * @return Str
     * @throws PAException
     *             PAException
     */
    private Ii lookUpOrgs(OrganizationBatchDTO batchDto) throws PAException {
        log.error("Entering lookup Org ...");
        Ii orgId = null;
        try {
                String orgName = batchDto.getName();
                String countryName = batchDto.getCountry();
                String cityName = batchDto.getCity();
                String zipCode = batchDto.getZip();
                if (orgName.equals("") && countryName.equals("")
                        && cityName.equals("") && zipCode.equals("")) {
                    String message = "Please enter at least one search criteria";
                    log.error(" lookUpOrgs" + message);
                    throw new PAException(message);
                }
                if (countryName.equals("")) {
                    String message = "Please select a country";
                    log.error(" lookUpOrgs" + message);
                    throw new PAException(message);
                }
                OrganizationDTO criteria = new OrganizationDTO();
                if (batchDto.getOrgCTEPId() != null && batchDto.getOrgCTEPId().length() > 0) {
                    IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
                    identifiedOrganizationDTO.setAssignedId(
                            IiConverter.converToIdentifiedEntityIi(batchDto.getOrgCTEPId()));
                    List<IdentifiedOrganizationDTO> identifiedOrgs = RegistryServiceLocator
                            .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
                    if (null == identifiedOrgs || identifiedOrgs.size() == 0) {
                        log.error("No Organization Found");
                        throw new PAException("No Organization Found");
                    } else {
                    criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
                    }
                } else {
                    criteria.setName(EnOnConverter.convertToEnOn(orgName));
                    criteria.setPostalAddress(AddressConverterUtil.create(null, null,
                        cityName, null, zipCode, countryName));
                }
                List<OrganizationDTO> poOrgDtos = RegistryServiceLocator
                        .getPoOrganizationEntityService().search(criteria);
                if (null == poOrgDtos || poOrgDtos.size() == 0) {
                    // create a new org and then return the new Org
                    log.error(" lookUpOrgs Serch return no org so creating new");
                    orgId = createOrganization(batchDto);
                } else {
                    // return the Id of the org
                    orgId = poOrgDtos.get(0).getIdentifier();
                    log.error(" lookUpOrgs Serch returned orgId" + orgId.getExtension().toString());
                }
            } catch (Exception e) {
                log.error("lookUpOrgs exception" + e.getMessage());
                throw new PAException(e.getMessage());
            }
        log.error("leaving lookup Org with OrgId" + orgId.getExtension());
        return orgId;
    }

    /**
     * his method is first look up the Person. If person Found then return the id
     * else it will create the new Person Assumpion- even if the lookup return
     * multiple Person we are taking the first one
     * @param batchDto
     *            batchDto
     * @return str
     * @throws PAException
     *             ex
     */
    public Ii createOrganization(OrganizationBatchDTO batchDto)
            throws PAException {
        log.error("Entering Create Org ..");
        OrganizationDTO orgDto = new OrganizationDTO();
        Ii orgId = null;
        String orgName = batchDto.getName();
        if (PAUtil.isEmpty(orgName)) {
            throw new PAException("Organization is a required field");
        }
        
        String orgStAddress = batchDto.getStreetAddress();
        if (PAUtil.isEmpty(orgStAddress)) {
              log.error("Street address is a required field");
              throw new PAException("Street address is a required field"); 
        }
        String cityName = batchDto.getCity();
        if (PAUtil.isEmpty(cityName)) {
              log.error("createOrganization throwing exception" + "City is a required field");
              throw new PAException("City is a required field");
        } 
        String stateName = batchDto.getState();
        if (PAUtil.isEmpty(stateName)) {
              log.error("createOrganization throwing exception" + "State is a required field");
              throw new PAException("State is a required field");
        }
        String zipCode = batchDto.getZip();
        if (PAUtil.isEmpty(zipCode)) {
            log.error("createOrganization throwing exception" + "Zip is a required field");
            throw new PAException("Zip is a required field");
        }
        String countryName = batchDto.getCountry();
        if (PAUtil.isEmpty(countryName)) {
            log.error("createOrganization throwing exception" + "Country is a required field");
            throw new PAException("Country is a required field");
        }
        String email = batchDto.getEmail();
        if (PAUtil.isEmpty(email)) { 
              log.error("Email is a required field");
              throw new PAException("Email is a required field"); 
        } else if (!RegistryUtil.isValidEmailAddress(email)) {
              log.error("Email address is invalid " + email);
              throw new PAException("Email address is invalid"); 
        }
        String phoneNumer = batchDto.getPhone();
        String faxNumber = batchDto.getFax();
        String ttyNumber = batchDto.getTty();
        String url = batchDto.getUrl();
        
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress,
                null, cityName, stateName, zipCode, countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());
        try {
            if (phoneNumer != null && phoneNumer.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phoneNumer, null));
                telco.getItem().add(t);
            }
            if (faxNumber != null && faxNumber.length() > 0) {
                Tel fax = new Tel();
                fax.setValue(new URI("x-text-fax", faxNumber, null));
                telco.getItem().add(fax);
            }
            if (ttyNumber != null && ttyNumber.length() > 0) {
                Tel tty = new Tel();
                tty.setValue(new URI("x-text-tel", ttyNumber, null));
                telco.getItem().add(tty);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                telco.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            telco.getItem().add(telemail);
            orgDto.setTelecomAddress(telco);
            Ii id = RegistryServiceLocator.getPoOrganizationEntityService()
                    .createOrganization(orgDto);
            List<OrganizationDTO> callConvert = new ArrayList<OrganizationDTO>();
            callConvert.add(RegistryServiceLocator
                    .getPoOrganizationEntityService().getOrganization(id));
            orgId = callConvert.get(0).getIdentifier();
        } catch (NullifiedEntityException e1) {
            log.error("createOrganization exception1 " + e1.getMessage());
            throw new PAException(e1.getMessage());
        } catch (EntityValidationException e3) {
            log.error("createOrganization exception3 " + e3.getMessage());
            throw new PAException(e3.getMessage());
        } catch (PAException e4) {
            log.error("createOrganization exception4 " + e4.getMessage());
            throw new PAException(e4.getMessage());
        } catch (URISyntaxException e5) {
            log.error("createOrganization exception5 " + e5.getMessage());
            throw new PAException(e5.getMessage());
        }
        log.error("leaving Create Org with OrgId" + orgId.getExtension());
        return orgId;
    }
    /**
     * 
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     */
    public Ii lookUpPersons(PersonBatchDTO batchDto) throws PAException {
        log.error("Entering Look up person...");
        Ii personId = null;
        try {
            String firstName = batchDto.getFirstName();
            String lastName = batchDto.getLastName();
            String email = batchDto.getEmail();
            String ctep = batchDto.getPersonCTEPId();
            if ((firstName != null) && (firstName.equals("")) && (lastName != null) && (lastName.equals(""))
                    && (email.equals("")) && ctep != null && !(ctep.length() > 0)) {
                String message = "Please enter at least one search criteria";
                    log.error(message);
                    throw new PAException(message);
            }
            gov.nih.nci.services.person.PersonDTO p = new gov.nih.nci.services.person.PersonDTO();
            //
            if (email != null && email.length() > 0) {
                DSet<Tel> list = new DSet<Tel>();
                list.setItem(new HashSet<Tel>());
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
                p.setTelecomAddress(list);
            }
            //
            List<gov.nih.nci.services.person.PersonDTO> poPersonList = 
                                                        new ArrayList<gov.nih.nci.services.person.PersonDTO>();
            if (ctep != null && ctep.length() > 0) {
                IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
                identifiedPersonDTO.setIdentifier(IiConverter.converToIdentifiedEntityIi(ctep));
                List<IdentifiedPersonDTO> retResultList = 
                                  RegistryServiceLocator.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
                if (null == retResultList || retResultList.size() == 0) {
                   log.error("No Person Found...");
                   throw new PAException("No Person Found...");
                }
            } else {
                p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
            }
            poPersonList = RegistryServiceLocator.getPoPersonEntityService().search(p);
            if (null == poPersonList ||  poPersonList.size() ==  0) {
                log.error("No Person found so creating new Person");
                personId = createPerson(batchDto);
            }  else {
                personId = poPersonList.get(0).getIdentifier();             
                log.error("Person found ");
            }
            
        } catch (URISyntaxException e) {
            log.error("lookUpPersons exception " + e.getMessage());
            throw new PAException("lookUpPersons exception " + e.getMessage());
        }
        log.error("leaving Look up person  with personId" + personId);
        return personId;
    }
    /**
     * 
     * @param  batchDto dto
     * @return Ii personId
     * @throws PAException ex 
     */
    public Ii createPerson(PersonBatchDTO batchDto) throws PAException  {
        log.error("Entering created person  ...");
        Ii personId = null; 
        String firstName = batchDto.getFirstName();
        if (PAUtil.isEmpty(firstName)) {
           log.error("First Name is a required field");
        throw new PAException("First Name is a required field");
        }
        String lastName = batchDto.getLastName();
        if (PAUtil.isEmpty(lastName)) {
            log.error("Last Name is a required field");
        throw new PAException("Last Name is a required field");
        }
        String email = batchDto.getEmail();
        if (PAUtil.isEmpty(email)) {
            log.error("Email is a required field");
         throw new PAException("Email is a required field");
        } else if (!RegistryUtil.isValidEmailAddress(email)) {
            log.error("Email address is invalid");
        throw new PAException("Email address is invalid");
        }
        String streetAddr = batchDto.getStreetAddress();
        if (PAUtil.isEmpty(streetAddr)) {
            log.error("Street address is a required field");
        throw new PAException("Street address is a required field");
        }
        String city = batchDto.getCity();
        if (PAUtil.isEmpty(city)) {
            log.error("City is a required field");
        throw new PAException("City is a required field");
        }
        String zip = batchDto.getZip();
        if (PAUtil.isEmpty(zip)) {
            log.error("Zip is a required field");
        throw new PAException("Zip is a required field");
        }
        String country = batchDto.getCountry();
        if (PAUtil.isEmpty(country)) {
            log.error("Country is a required field");
        throw new PAException("Country is a required field");
        }
    
        //String midName = batchDto.getMiddleName();
        String state =  batchDto.getState();
        String phone = batchDto.getPhone();
        String tty = batchDto.getTty();
        String fax = batchDto.getFax();
        String url = batchDto.getUrl();
        //
        gov.nih.nci.services.person.PersonDTO dto = new gov.nih.nci.services.person.PersonDTO();
        dto.setName(new EnPn());
        Enxp part = new Enxp(EntityNamePartType.GIV);
        part.setValue(firstName);
        dto.getName().getPart().add(part);
        // if middel name exists stick it in here!
        /*if (midName != null && PAUtil.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.FAM);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }*/
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);
      
        dto.getName().getPart().add(part);
        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
        try {
            if (phone != null && phone.length() > 0) {
                Tel t = new Tel();
                t.setValue(new URI("tel", phone, null));
                list.getItem().add(t);
            }
            if (fax != null && fax.length() > 0) {
                Tel faxTel = new Tel();
                faxTel.setValue(new URI("x-text-fax", fax, null));
                list.getItem().add(faxTel);
            }
            if (tty != null && tty.length() > 0) {
                Tel ttyTel = new Tel();
                ttyTel.setValue(new URI("x-text-tel", tty, null));
                list.getItem().add(ttyTel);
            }
            if (url != null && url.length() > 0) {
                TelUrl telurl = new TelUrl();
                telurl.setValue(new URI(url));
                list.getItem().add(telurl);
            }
            TelEmail telemail = new TelEmail();
            telemail.setValue(new URI("mailto:" + email));
            list.getItem().add(telemail);
            dto.setTelecomAddress(list);
            dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
            personId = RegistryServiceLocator.getPoPersonEntityService().createPerson(dto);
        } catch (EntityValidationException e) {
            log.error("PERS_CREATE_RESPONSE " + e.getMessage());
            throw new PAException("PERS_CREATE_RESPONSE " + e.getMessage());
        } catch (PAException e) {
            log.error("PERS_CREATE_RESPONSE " + e.getMessage());
            throw new PAException("PERS_CREATE_RESPONSE " + e.getMessage());
        } catch (URISyntaxException e) {
            log.error("PERS_CREATE_RESPONSE " + e.getMessage());
            throw new PAException("PERS_CREATE_RESPONSE " + e.getMessage());
        }
        log.error("leaving created person  with personId" + personId);
        return personId;
    }
    /**
     * 
     * @param dto dto
     * @return orgDto
     */
    private OrganizationBatchDTO buildLeadOrgDto(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO orgDto = new OrganizationBatchDTO();
        orgDto.setName(dto.getLeadOrgName());
        orgDto.setOrgCTEPId(dto.getLeadOrgCTEPOrgNo());
        orgDto.setStreetAddress(dto.getLeadOrgStreetAddress());
        orgDto.setCity(dto.getLeadOrgCity());
        orgDto.setState(dto.getLeadOrgState());
        orgDto.setZip(dto.getLeadOrgZip());
        orgDto.setCountry(dto.getLeadOrgCountry());
        orgDto.setEmail(dto.getLeadOrgEmail());
        orgDto.setPhone(dto.getLeadOrgPhone());
        orgDto.setTty(dto.getLeadOrgTTY());
        orgDto.setFax(dto.getLeadOrgFax());
        orgDto.setUrl(dto.getLeadOrgUrl());
        orgDto.setType(dto.getLeadOrgType());
        return orgDto;
    }
    /**
     * 
     * @param dto
     * @return
     */
        private PersonBatchDTO buildLeadPIDto(StudyProtocolBatchDTO dto) {
        PersonBatchDTO personDto = new PersonBatchDTO();
        personDto.setFirstName(dto.getPiFirstName());
        personDto.setMiddleName(dto.getPiMiddleName());
        personDto.setLastName(dto.getPiLastName());
        personDto.setPersonCTEPId(dto.getPiPersonCTEPPersonNo());
        personDto.setStreetAddress(dto.getPiStreetAddress());
        personDto.setCity(dto.getPiCity());
        personDto.setState(dto.getPiState());
        personDto.setZip(dto.getPiZip());
        personDto.setCountry(dto.getPiCountry());
        personDto.setEmail(dto.getPiEmail());
        personDto.setPhone(dto.getPiPhone());
        personDto.setTty(dto.getPiTTY());
        personDto.setFax(dto.getPiFax());
        personDto.setUrl(dto.getPiUrl());
        return personDto;
    }
    /**
     * 
     * @param dto dto
     * @return SponsorDto
     */
    private OrganizationBatchDTO buildSponsorOrgDto(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO sponsorDto = new OrganizationBatchDTO();
        sponsorDto.setName(dto.getSponsorOrgName());
        sponsorDto.setOrgCTEPId(dto.getSponsorCTEPOrgNumber());
        sponsorDto.setStreetAddress(dto.getSponsorStreetAddress());
        sponsorDto.setCity(dto.getSponsorCity());
        sponsorDto.setState(dto.getSponsorState());
        sponsorDto.setZip(dto.getSponsorZip());
        sponsorDto.setCountry(dto.getSponsorCountry());
        sponsorDto.setEmail(dto.getSponsorEmail());
        sponsorDto.setPhone(dto.getSponsorPhone());
        sponsorDto.setTty(dto.getSponsorTTY());
        sponsorDto.setFax(dto.getSponsorFax());
        sponsorDto.setUrl(dto.getSponsorURL());
        return sponsorDto;
    }
    /**
     * 
     * @param dto dto
     * @return dto
     */
    private PersonBatchDTO buildSponsorContact(StudyProtocolBatchDTO dto) {
        PersonBatchDTO  sponsorContact = new PersonBatchDTO();
        sponsorContact.setFirstName(dto.getSponsorContactFName());
        sponsorContact.setMiddleName(dto.getSponsorContactMName());
        sponsorContact.setLastName(dto.getSponsorContactLName());
        sponsorContact.setPersonCTEPId(dto.getSponsorContactCTEPPerNo());
        sponsorContact.setStreetAddress(dto.getSponsorContactStreetAddress());
        sponsorContact.setCity(dto.getSponsorContactCity());
        sponsorContact.setState(dto.getSponsorContactState());
        sponsorContact.setZip(dto.getSponsorContactZip());
        sponsorContact.setCountry(dto.getSponsorContactCountry());
        sponsorContact.setEmail(dto.getSponsorContactEmail());
        sponsorContact.setPhone(dto.getSponsorContactPhone());
        sponsorContact.setTty(dto.getSponsorContactTTY());
        sponsorContact.setFax(dto.getSponsorContactFax());
        sponsorContact.setUrl(dto.getSponsorContactUrl());
        return sponsorContact;
    }
    /**
     * 
     * @param studyProtocolIi
     * @param docTypeCode
     * @param fileName
     * @param folderPath
     * @throws PAException ex 
     */
    private void uploadDocument(Ii studyProtocolIi, String docTypeCode, String fileName, String folderPath) 
    throws PAException {
        log.error("Entering uploadDocument having docTypeCode " + docTypeCode);
        try {
            DocumentDTO docDTO = new DocumentDTO();
            docDTO.setStudyProtocolIi(studyProtocolIi);
            docDTO.setTypeCode(CdConverter.convertStringToCd(docTypeCode));
            docDTO.setFileName(StConverter.convertToSt(fileName));
            // docDTO.setUserLastUpdated((StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser())));
            docDTO.setText(EdConverter.convertToEd(readInputStream(new FileInputStream(folderPath + fileName))));
            RegistryServiceLocator.getDocumentService().create(docDTO);
        } catch (IOException ioe) {
            // ioe.printStackTrace();
            log.error("Exception occured reading '" + fileName + "' Exception is" + ioe);
            throw new PAException("Exception occured reading '" + fileName + "' Exception is" + ioe.getMessage());
        }
        log.error("Leaving uploadDocument ...");
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
    private void createIndIdeIndicators(Ii studyProtocolIi, StudyProtocolBatchDTO dto) throws PAException {
        log.error("Entering createIndIdeIndicators....");   
        StudyIndldeDTO indldeDTO = null;
            indldeDTO = new StudyIndldeDTO();
            indldeDTO.setStudyProtocolIi(studyProtocolIi);
            // indldeDTO.setStatusDateRange(statusDateRange)sDateRange(statusDateRange)
            indldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(dto.getIndType()));
            indldeDTO.setIndldeNumber(StConverter.convertToSt(dto.getIndNumber()));
            indldeDTO.setGrantorCode(CdConverter.convertStringToCd(dto.getIndGrantor()));
            indldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(dto.getIndHolderType()));
            if (dto.getIndHolderType().equalsIgnoreCase("NIH")) {
                indldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(dto.getIndNIHInstitution()));
            }
            if (dto.getIndHolderType().equalsIgnoreCase("NCI")) {
                indldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(dto.getIndNCIDivision()));
            }
            indldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(
                    dto.getIndHasExpandedAccess())));
            indldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(dto.getIndExpandedAccessStatus()));
            RegistryServiceLocator.getStudyIndldeService().create(indldeDTO);
     log.error("leaving createIndIdeIndicators....");
    }
    private OrganizationBatchDTO buildSummary4Sponsor(StudyProtocolBatchDTO dto) {
        OrganizationBatchDTO summ4Sponsor = new OrganizationBatchDTO();
        summ4Sponsor.setName(dto.getSumm4OrgName());
        summ4Sponsor.setOrgCTEPId(dto.getSumm4OrgCTEPOrgNo());
        summ4Sponsor.setStreetAddress(dto.getSumm4OrgStreetAddress());
        summ4Sponsor.setCity(dto.getSumm4City());
        summ4Sponsor.setState(dto.getSumm4State());
        summ4Sponsor.setZip(dto.getSumm4Zip());
        summ4Sponsor.setCountry(dto.getSumm4Country());
        summ4Sponsor.setEmail(dto.getSumm4Email());
        summ4Sponsor.setPhone(dto.getSumm4Phone());
        summ4Sponsor.setTty(dto.getSumm4TTY());
        summ4Sponsor.setFax(dto.getSumm4Fax());
        summ4Sponsor.setUrl(dto.getSumm4Url());
        return summ4Sponsor;
    }
    private boolean orgDTOIsEmpty(OrganizationBatchDTO dto) {
        int nullCount = 0;
        if (PAUtil.isEmpty(dto.getName())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getStreetAddress())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getCity())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getState())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getZip())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getCountry())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getEmail())) {
            nullCount += 1;
        }
        if (PAUtil.isEmpty(dto.getPhone())) {
            nullCount += 1;
        }
        if (nullCount == 0) {
            return false;
        }
        return true;
    }
}
