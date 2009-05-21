/***
* caBIG Open Source Software License
* 
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
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
package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.registry.dto.OrganizationBatchDTO;
import gov.nih.nci.registry.dto.PersonBatchDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.util.RegistryServiceLocator;
import gov.nih.nci.registry.util.TrialUtil;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
/**
 * @author Vrushali
 * 
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength" })
public class BatchCreateProtocols {
    private static final Logger LOG = Logger.getLogger(BatchCreateProtocols.class);
    private int sucessCount = 0;
    private int failedCount = 0;
    /**
     * 
     * @param dtoList list
     * @param folderPath path 
     * @param userName name
     * @return map
     * @throws PAException ex
     */
    @SuppressWarnings({"PMD.LooseCoupling" })
    public HashMap<String, String> createProtocols(List<StudyProtocolBatchDTO> dtoList,
            String folderPath, String userName)
            throws PAException {
        LOG.info("Entering into createProtocols...having size of dtolist"
                + dtoList.size());
        if (dtoList == null || dtoList.size() < 1) {
            throw new PAException("DTO list is Empty");
        }
        HashMap<String, String> map = new HashMap<String, String>();

        Iterator iterator = dtoList.iterator();
        StringBuffer result = new StringBuffer();
        TrialBatchDataValidator validator = null;
        while (iterator.hasNext()) {
            StudyProtocolBatchDTO batchDto = (StudyProtocolBatchDTO) iterator
                    .next();
            // check if the record qualifies to be added
            //validate the record            
            if (batchDto != null) { // && batchDto.isValidRecord()) {
                result = new StringBuffer();
                validator = new TrialBatchDataValidator();
                result.append(validator.validateBatchDTO(batchDto));
                if (null == result || result.length() < 1) {
                    result.append(buildProtocol(batchDto, folderPath , userName));    
                } else {
                    result.insert(0, "Failed:");
                    failedCount  += 1;
                }
                map.put(batchDto.getUniqueTrialId() , result.toString());
            }
        }
        LOG.error("leaving into createProtocols... failed count" + failedCount + "sucess count" + sucessCount);
        map.put("Failed Trial Count" , String.valueOf(failedCount));
        map.put("Sucess Trial Count" , String.valueOf(sucessCount));
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
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private String buildProtocol(StudyProtocolBatchDTO dto , String folderPath , String userName)  {

        Ii studyProtocolIi = null;
        String protocolAssignedId  = "";
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        
        try {
            // before creating the protocol check for duplicate
            // using the Lead Org Trial Identifier and Lead Org Identifier
            OrganizationBatchDTO leadOrgDto = dataValidator.buildLeadOrgDto(dto);
            Ii orgIdIi = lookUpOrgs(leadOrgDto);            
            //look up sponser
            OrganizationBatchDTO sponsorOrgDto = dataValidator.buildSponsorOrgDto(dto);
            Ii sponsorIdIi = lookUpOrgs(sponsorOrgDto);
            //look up Person
            PersonBatchDTO piDto = dataValidator.buildLeadPIDto(dto);
            Ii leadPrincipalInvestigator = lookUpPersons(piDto);
            //Summary 4 Info
            OrganizationBatchDTO summ4Sponsor = dataValidator.buildSummary4Sponsor(dto);
            Ii summary4Sponsor = null;
            if (!dataValidator.orgDTOIsEmpty(summ4Sponsor)) {
                //look up for org only when dto is not empty
                summary4Sponsor = lookUpOrgs(summ4Sponsor);
            }
            //check if Sponsor Contact is needed if needed the the lookup ahead to catch other Validation error
            //look up new Person or create if needed.
            Ii responsiblePersonId = null;
            if (dto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
                PersonBatchDTO sponsorPersonDto = dataValidator.buildSponsorContact(dto);
                responsiblePersonId = lookUpPersons(sponsorPersonDto);
            }
            //now only go ahead and create the Trial
            LOG.info("TrialType() " + dto.getTrialType());
            //convert to trial DTO so it will easy
            TrialDTO trialDTO = convertToTrialDTO(dto);
            //All the Ii
            trialDTO.setLeadOrganizationIdentifier(orgIdIi.getExtension());
            trialDTO.setPiIdentifier(leadPrincipalInvestigator.getExtension());
            trialDTO.setSponsorIdentifier(sponsorIdIi.getExtension());
            if (responsiblePersonId != null) {
                trialDTO.setResponsiblePersonIdentifier(responsiblePersonId.getExtension());
            }
            if (summary4Sponsor != null) {
                trialDTO.setSummaryFourOrgIdentifier(summary4Sponsor.getExtension());
            }
            //add doc to the dto
            trialDTO.setDocDtos(addDocDTOToList(dto, folderPath));
            //add ind
            trialDTO.setIndIdeDtos(getIndIdeList(dto));
            //add grants
            trialDTO.setFundingDtos(getGrantList(dto));
            
            TrialUtil util = new TrialUtil();
            StudyProtocolDTO studyProtocolDTO = null;
            if (trialDTO.getTrialType().equals("Interventional")) {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            } else {
                studyProtocolDTO = util.convertToInterventionalStudyProtocolDTO(trialDTO);
            }
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            OrganizationDTO leadOrgDTO = util.convertToLeadOrgDTO(trialDTO);
            PersonDTO principalInvestigatorDTO = util.convertToLeadPI(trialDTO);
            OrganizationDTO sponsorOrgDTO = util.convertToSponsorOrgDTO(trialDTO);
            StudyParticipationDTO leadOrgParticipationIdDTO = util.convertToStudyParticipationDTO(trialDTO);
            StudyParticipationDTO nctIdentifierParticipationIdDTO = util.convertToNCTStudyParticipationDTO(trialDTO);
            StudyContactDTO studyContactDTO = null;
            StudyParticipationContactDTO studyParticipationContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
            PersonDTO responsiblePartyContactDTO = null;
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                studyContactDTO = util.convertToStudyContactDTO(trialDTO);
            } else {
                studyParticipationContactDTO = util.convertToStudyParticipationContactDTO(trialDTO);
                responsiblePartyContactDTO = util.convertToResponsiblePartyContactDTO(trialDTO);
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            
            studyProtocolIi =  RegistryServiceLocator.getTrialRegistrationService().
            createInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                    studyResourcingDTOs, documentDTOs,
                    leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgParticipationIdDTO,
                    nctIdentifierParticipationIdDTO, studyContactDTO, studyParticipationContactDTO,
                    summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactDTO);
            //get the protocol
             protocolAssignedId = " Successfully Registered with NCI Identifier " 
                 + RegistryServiceLocator.getStudyProtocolService()
                         .getStudyProtocol(studyProtocolIi).getAssignedIdentifier().getExtension();
             sucessCount +=  1;
        } catch (PAException ex) {
            failedCount +=  1;
            LOG.error("buildprotocol exception-" + ex.getMessage());
            protocolAssignedId = "Failed:" + ex.getMessage() + "\n";
        } catch (Exception exc) {
            failedCount +=  1;
            protocolAssignedId = "Failed:" + exc.getMessage() + "\n";
            LOG.error("buildprotocol exception-" + exc.getMessage());
        }
        LOG.info("response " + protocolAssignedId);
        return protocolAssignedId;
    }
    private TrialDTO convertToTrialDTO(StudyProtocolBatchDTO batchDTO) {
        TrialDTO  trialDTO = new TrialDTO();
        trialDTO.setCompletionDate(batchDTO.getPrimaryCompletionDate());
        trialDTO.setCompletionDateType(batchDTO.getPrimaryCompletionDateType());
        trialDTO.setLocalProtocolIdentifier(batchDTO.getLocalProtocolIdentifier());
        trialDTO.setNctIdentifier(batchDTO.getNctNumber());
        trialDTO.setOfficialTitle(batchDTO.getTitle());
        trialDTO.setPhaseCode(batchDTO.getPhase());
        trialDTO.setPhaseOtherText(batchDTO.getPhaseOtherValueSp());
        trialDTO.setPrimaryPurposeCode(batchDTO.getPrimaryPurpose());
        trialDTO.setPrimaryPurposeOtherText(batchDTO.getPrimaryPurposeOtherValueSp());
        trialDTO.setReason(batchDTO.getReasonForStudyStopped());
        trialDTO.setResponsiblePartyType(batchDTO.getResponsibleParty());
        trialDTO.setStartDate(batchDTO.getStudyStartDate());
        trialDTO.setStartDateType(batchDTO.getStudyStartDateType());
        trialDTO.setStatusCode(batchDTO.getCurrentTrialStatus());
        trialDTO.setStatusDate(batchDTO.getCurrentTrialStatusDate());
        trialDTO.setSummaryFourFundingCategoryCode(batchDTO.getSumm4FundingCat());
        
        trialDTO.setTrialType(batchDTO.getTrialType());
        if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
            trialDTO.setContactEmail(batchDTO.getPiEmail());
            trialDTO.setContactPhone(batchDTO.getPiPhone());
        } else {
            trialDTO.setContactEmail(batchDTO.getSponsorContactEmail());
            trialDTO.setContactPhone(batchDTO.getSponsorContactPhone());
        }
        //if the Phase's value is not in allowed LOV then save phase as Other
        // and comments as the value of current phase
        if (null == PhaseCode.getByCode(batchDTO.getPhase()))  {
            trialDTO.setPhaseCode(PhaseCode.OTHER.getCode());
            trialDTO.setPhaseOtherText(batchDTO.getPhase());
        } else {
            trialDTO.setPhaseCode(batchDTO.getPhase());
            if (PAUtil.isNotEmpty(batchDTO.getPhaseOtherValueSp())) {
                trialDTO.setPhaseOtherText(batchDTO.getPhaseOtherValueSp());
            }
        } 
        return trialDTO;
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
     * @throws EntityValidationException 
     * @throws URISyntaxException 
     * @throws NullifiedEntityException 
     */
    private Ii lookUpOrgs(OrganizationBatchDTO batchDto) throws PAException,
        NullifiedEntityException, URISyntaxException, EntityValidationException {
        LOG.info("Entering lookup Org ...");
        Ii orgId = null;
         
        String orgName = batchDto.getName();
        String countryName = batchDto.getCountry();
        String cityName = batchDto.getCity();
        String zipCode = batchDto.getZip();

        OrganizationDTO criteria = new OrganizationDTO();
        if (batchDto.getOrgCTEPId() != null && batchDto.getOrgCTEPId().length() > 0) {
               IdentifiedOrganizationDTO identifiedOrganizationDTO = new IdentifiedOrganizationDTO();
               identifiedOrganizationDTO.setAssignedId(
                            IiConverter.converToIdentifiedOrgEntityIi(batchDto.getOrgCTEPId()));
               List<IdentifiedOrganizationDTO> identifiedOrgs = RegistryServiceLocator
                            .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
               if (identifiedOrgs != null && !identifiedOrgs.isEmpty()) {
                        criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
               }
        } 
        if (null == criteria.getIdentifier() || PAUtil.isEmpty(batchDto.getOrgCTEPId())) {
               criteria.setName(EnOnConverter.convertToEnOn(orgName));
               criteria.setPostalAddress(AddressConverterUtil.create(null, null,
                        cityName, null, zipCode, countryName.toUpperCase(Locale.US)));
        }
        List<OrganizationDTO> poOrgDtos = RegistryServiceLocator
                        .getPoOrganizationEntityService().search(criteria);
        if (null == poOrgDtos || poOrgDtos.isEmpty()) {
              // create a new org and then return the new Org
              LOG.info(" lookUpOrgs Serch return no org so creating new");
              orgId = createOrganization(batchDto);
        } else {
             // return the Id of the org
             orgId = poOrgDtos.get(0).getIdentifier();
             LOG.info(" lookUpOrgs Serch returned orgId" + orgId.getExtension().toString());
        }
        LOG.info("leaving lookup Org with OrgId" + orgId.getExtension());
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
     * @throws URISyntaxException 
     * @throws EntityValidationException 
     * @throws NullifiedEntityException 
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private Ii createOrganization(OrganizationBatchDTO batchDto)
            throws PAException, URISyntaxException, EntityValidationException, NullifiedEntityException {
        LOG.info("Entering Create Org ..");
        OrganizationDTO orgDto = new OrganizationDTO();
        Ii orgId = null;
        String orgName = batchDto.getName();
        String orgStAddress = batchDto.getStreetAddress();
        String cityName = batchDto.getCity();
        String zipCode = batchDto.getZip();
        String countryName = batchDto.getCountry().toUpperCase(Locale.US);
        String stateName = batchDto.getState();
        if (PAUtil.isNotEmpty(stateName) && (batchDto.getCountry().equalsIgnoreCase("USA")
                    || batchDto.getCountry().equalsIgnoreCase("CAN")
                    || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            stateName = stateName.toUpperCase(Locale.US);
        }
        String email = batchDto.getEmail();
        String phoneNumer = batchDto.getPhone();
        String faxNumber = batchDto.getFax();
        String ttyNumber = batchDto.getTty();
        String url = batchDto.getUrl();
        
        orgDto.setName(EnOnConverter.convertToEnOn(orgName));
        orgDto.setPostalAddress(AddressConverterUtil.create(orgStAddress,
                null, cityName, stateName, zipCode, countryName));
        DSet<Tel> telco = new DSet<Tel>();
        telco.setItem(new HashSet<Tel>());

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
        
        LOG.info("leaving Create Org with OrgId" + orgId.getExtension());
        return orgId;
    }
    /**
     * 
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     * @throws EntityValidationException 
     * @throws URISyntaxException 
     */
    private Ii lookUpPersons(PersonBatchDTO batchDto) throws PAException,
        URISyntaxException, EntityValidationException {
        LOG.info("Entering Look up person...");
        Ii personId = null;
        String firstName = batchDto.getFirstName();
        String lastName = batchDto.getLastName();
        String email = batchDto.getEmail();
        String ctep = batchDto.getPersonCTEPId();
        gov.nih.nci.services.person.PersonDTO p = new gov.nih.nci.services.person.PersonDTO();
        List<gov.nih.nci.services.person.PersonDTO> poPersonList = 
                                                        new ArrayList<gov.nih.nci.services.person.PersonDTO>();
        if (ctep != null && ctep.length() > 0) {
           IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
           identifiedPersonDTO.setAssignedId(IiConverter.converToIdentifiedPersonEntityIi(ctep));
           List<IdentifiedPersonDTO> retResultList = 
                                  RegistryServiceLocator.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
           if (retResultList != null && !retResultList.isEmpty()) {
                    p.setIdentifier(retResultList.get(0).getPlayerIdentifier());
           } 
        } 
        if (null == p.getIdentifier() || PAUtil.isEmpty(ctep)) {
            if (email != null && email.length() > 0) {
                DSet<Tel> list = new DSet<Tel>();
                list.setItem(new HashSet<Tel>());
                TelEmail telemail = new TelEmail();
                telemail.setValue(new URI("mailto:" + email));
                list.getItem().add(telemail);
                p.setTelecomAddress(list);
            }
            p.setName(RemoteApiUtil.convertToEnPn(firstName, null, lastName, null, null));
        }
        poPersonList = RegistryServiceLocator.getPoPersonEntityService().search(p);
        if (null == poPersonList ||  poPersonList.isEmpty()) {
            LOG.info("No Person found so creating new Person");
            personId = createPerson(batchDto);
        }  else {
            personId = poPersonList.get(0).getIdentifier();             
            LOG.info("Person found ");
        }
        LOG.info("leaving Look up person  with personId" + personId);
        return personId;
    }
    /**
     * 
     * @param  batchDto dto
     * @return Ii personId
     * @throws PAException ex 
     * @throws URISyntaxException 
     * @throws EntityValidationException 
     */
    @SuppressWarnings({"PMD.ExcessiveMethodLength" })
    private Ii createPerson(PersonBatchDTO batchDto) throws PAException, 
        URISyntaxException, EntityValidationException  {
        LOG.info("Entering created person  ...");
        Ii personId = null; 
        String firstName = batchDto.getFirstName();
        String lastName = batchDto.getLastName();
        String email = batchDto.getEmail();
        String streetAddr = batchDto.getStreetAddress();
        String city = batchDto.getCity();
        String zip = batchDto.getZip();
        String country = batchDto.getCountry().toUpperCase(Locale.US);
        String state =  batchDto.getState();
        if (PAUtil.isNotEmpty(state) && (batchDto.getCountry().equalsIgnoreCase("USA")
                || batchDto.getCountry().equalsIgnoreCase("CAN")
                || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            state = state.toUpperCase(Locale.US);
        }

        LOG.error("State as" + state + " Country as " + country);
        String midName = batchDto.getMiddleName();
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
        if (midName != null && PAUtil.isNotEmpty(midName)) {
            Enxp partMid = new Enxp(EntityNamePartType.GIV);
            partMid.setValue(midName);
            dto.getName().getPart().add(partMid);
        }
        Enxp partFam = new Enxp(EntityNamePartType.FAM);
        partFam.setValue(lastName);
        dto.getName().getPart().add(partFam);

        DSet<Tel> list = new DSet<Tel>();
        list.setItem(new HashSet<Tel>());
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
        
        LOG.info("leaving created person  with personId" + personId);
        return personId;
    }
    private  List<TrialIndIdeDTO> getIndIdeList(StudyProtocolBatchDTO dto) throws PAException {
        LOG.info("Entering getIndIdeList....");
        List<TrialIndIdeDTO> indIdeList = new ArrayList<TrialIndIdeDTO>();
        //check if the values are present..
        if (!isIndIdeEmpty(dto)) {
            TrialIndIdeDTO indldeDTO = new TrialIndIdeDTO();
            indldeDTO.setIndIdeId(dto.getIndType());
            indldeDTO.setNumber(dto.getIndNumber());
            indldeDTO.setGrantor(dto.getIndGrantor());
            indldeDTO.setHolderType(dto.getIndHolderType());
            if (dto.getIndHolderType().equalsIgnoreCase("NIH")) {
                indldeDTO.setProgramCode(dto.getIndNIHInstitution());
            }
            if (dto.getIndHolderType().equalsIgnoreCase("NCI")) {
                indldeDTO.setProgramCode(dto.getIndNCIDivision());
            }
            indldeDTO.setExpandedAccess(dto.getIndHasExpandedAccess());
            indldeDTO.setExpandedAccessType(dto.getIndExpandedAccessStatus());
            indIdeList.add(indldeDTO);
        }
        LOG.info("leaving getIndIdeList....");
        return indIdeList;
    }
    private  List<TrialFundingWebDTO> getGrantList(StudyProtocolBatchDTO dto) throws PAException {
        LOG.info("Entering getGrantList....");
        List<TrialFundingWebDTO> grantList = new ArrayList<TrialFundingWebDTO>();
        if (PAUtil.isNotEmpty(dto.getNihGrantFundingMechanism()) 
                && PAUtil.isNotEmpty(dto.getNihGrantNCIDivisionCode())
                && PAUtil.isNotEmpty(dto.getNihGrantInstituteCode())
                && PAUtil.isNotEmpty(dto.getNihGrantSrNumber())) {
            TrialFundingWebDTO fundingDTO = new TrialFundingWebDTO();
            fundingDTO.setFundingMechanismCode(dto.getNihGrantFundingMechanism());
            fundingDTO.setNciDivisionProgramCode(dto.getNihGrantNCIDivisionCode());
            fundingDTO.setNihInstitutionCode(dto.getNihGrantInstituteCode());
            fundingDTO.setSerialNumber(dto.getNihGrantSrNumber());
            grantList.add(fundingDTO);
       }
        LOG.info("leaving getGrantList....");
        return grantList;
    }
    private boolean isIndIdeEmpty(StudyProtocolBatchDTO dto) {
        if (PAUtil.isNotEmpty(dto.getIndType()) 
                && PAUtil.isNotEmpty(dto.getIndNumber())
                && PAUtil.isNotEmpty(dto.getIndGrantor())
                && PAUtil.isNotEmpty(dto.getIndHolderType())
                && PAUtil.isNotEmpty(dto.getIndHasExpandedAccess())) {
            return false;
        }
        return true;
    }
    private List<TrialDocumentWebDTO> addDocDTOToList(StudyProtocolBatchDTO dto, String folderPath) throws IOException {
        TrialUtil util = new TrialUtil();
        File doc = new File(folderPath + dto.getProtcolDocumentFileName());
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
         docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), 
                    dto.getProtcolDocumentFileName(), doc));
         doc = new File(folderPath + dto.getIrbApprovalDocumentFileName());
         docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), 
                        dto.getIrbApprovalDocumentFileName(), doc));
        
         if (PAUtil.isNotEmpty(dto.getInformedConsentDocumentFileName())) {
            doc = new File(folderPath + dto.getInformedConsentDocumentFileName());
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(),
                    dto.getInformedConsentDocumentFileName(), doc));
        }
        if (PAUtil.isNotEmpty(dto.getParticipatinSiteDocumentFileName())) {
            doc = new File(folderPath + dto.getParticipatinSiteDocumentFileName());
            docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                    dto.getParticipatinSiteDocumentFileName(), doc));
         }
         if (PAUtil.isNotEmpty(dto.getOtherTrialRelDocumentFileName())) {
             doc = new File(folderPath + dto.getOtherTrialRelDocumentFileName());
             docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(), 
                     dto.getOtherTrialRelDocumentFileName(), doc));  
         }
        return docDTOList;
    }
}
