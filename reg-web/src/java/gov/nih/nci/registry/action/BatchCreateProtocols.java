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
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.StudyTypeCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
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
import gov.nih.nci.registry.enums.TrialStatusReasonCode;
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
    public HashMap createProtocols(List<StudyProtocolBatchDTO> dtoList, String folderPath, String userName)
            throws PAException {
        log.info("Entering into createProtocols...having size of dtolist"
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
                    result = "Failed:" + result;
                    failedCount  += 1;
                }
                map.put(batchDto.getUniqueTrialId() , result);
            }
        }
        log.error("leaving into createProtocols... failed count" + failedCount + "sucess count" + sucessCount);
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
    private String buildProtocol(StudyProtocolBatchDTO dto , String folderPath , String userName)  {

        Ii studyProtocolIi = null;
        String protocolAssignedId  = null;
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        try {
            // before creating the protocol check for duplicate
            // using the Lead Org Trial Identifier and Lead Org Identifier
            OrganizationBatchDTO leadOrgDto = dataValidator.buildLeadOrgDto(dto);
            Ii orgIdIi = lookUpOrgs(leadOrgDto);            
            
            if (orgIdIi != null) {
                Organization paOrg = new Organization();
                paOrg.setIdentifier(IiConverter.convertToString(orgIdIi));
                paOrg = RegistryServiceLocator.getPAOrganizationService()
                        .getOrganizationByIndetifers(paOrg);
        
                if (paOrg != null && paOrg.getId() != null) {
                    StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
                    criteria.setLeadOrganizationTrialIdentifier(dto.getLocalProtocolIdentifier());
                    criteria.setLeadOrganizationId(paOrg.getId().toString());    
                    criteria.setExcludeRejectProtocol(new Boolean(true));

                    List<StudyProtocolQueryDTO> records = RegistryServiceLocator
                            .getProtocolQueryService().getStudyProtocolByCriteria(
                                    criteria);
                    if (records != null && records.size() > 0) {
                       throw new PAException(
                                "Duplicate Trial - A trial exists in the system "
                                        + " for the Lead Organization and Trial Identifier");
                    }
                }
            }            
            //look up sponser
            OrganizationBatchDTO sponsorOrgDto = dataValidator.buildSponsorOrgDto(dto);
            Ii sponsorIdIi = lookUpOrgs(sponsorOrgDto);
            //look up Person
            PersonBatchDTO piDto = dataValidator.buildLeadPIDto(dto);
            Ii leadPrincipalInvestigator = lookUpPersons(piDto);
            //Summary 4 Info
            OrganizationBatchDTO summ4Sponsor = dataValidator.buildSummary4Sponsor(dto);
            Ii selectedSummary4Sponsor = null;
            if (!dataValidator.orgDTOIsEmpty(summ4Sponsor)) {
                //look up for org only when dto is not empty
                selectedSummary4Sponsor = lookUpOrgs(summ4Sponsor);
            }
            //check if Sponsor Contact is needed if needed the the lookup ahead to catch other Validation error
            //look up new Person or create if needed.
            Ii responsiblePartyContact = null;
            if (dto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
                PersonBatchDTO sponsorPersonDto = dataValidator.buildSponsorContact(dto);
                responsiblePartyContact = lookUpPersons(sponsorPersonDto);
            }
            //now only go ahead and create the Trial
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
            log.info("Trial is registered with ID: "
                    + IiConverter.convertToString(studyProtocolIi));
            if (PAUtil.isNotEmpty(dto.getNctNumber())) {
                createStudyNCTNumber(studyProtocolIi, dto);  // create NCT number
            }
            createStudyStatus(studyProtocolIi, dto);  // create study overall status
            createIndIdeIndicators(studyProtocolIi, dto); // create IND/IDE information *One- times*
            createStudyResources(studyProtocolIi, dto); // create the Study Grants One- times*
           
            if (PAUtil.isNotEmpty(dto.getProtcolDocumentFileName())) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(), 
                          dto.getProtcolDocumentFileName(), folderPath);
            }
            if (PAUtil.isNotEmpty(dto.getIrbApprovalDocumentFileName())) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(),
                         dto.getIrbApprovalDocumentFileName(), folderPath);
            }            
            if (PAUtil.isNotEmpty(dto.getInformedConsentDocumentFileName())) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(),
                        dto.getInformedConsentDocumentFileName(), folderPath);
            }            
            if (PAUtil.isNotEmpty(dto.getParticipatinSiteDocumentFileName())) {
                uploadDocument(studyProtocolIi,
                        DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                        dto.getParticipatinSiteDocumentFileName(), folderPath);
            }            
            if (PAUtil.isNotEmpty(dto.getOtherTrialRelDocumentFileName())) {
                uploadDocument(studyProtocolIi, DocumentTypeCode.OTHER.getCode(), 
                         dto.getOtherTrialRelDocumentFileName(), folderPath);  
            }
                if (selectedSummary4Sponsor != null) {
                        new PARelationServiceBean().createSummary4ReportedSource(
                                selectedSummary4Sponsor.getExtension(), 
                                SummaryFourFundingCategoryCode.getByCode(dto.getSumm4FundingCat()), IiConverter
                                .convertToLong(studyProtocolIi));
                }
            if (null != orgIdIi) {
                    new PARelationServiceBean().createLeadOrganizationRelations(orgIdIi
                            .getExtension(),
                            IiConverter.convertToLong(studyProtocolIi), dto
                                    .getLocalProtocolIdentifier());
                }
            if (leadPrincipalInvestigator != null) {
                new PARelationServiceBean().createPrincipalInvestigatorRelations(orgIdIi.getExtension(), 
                        leadPrincipalInvestigator.getExtension(), IiConverter
                .convertToLong(studyProtocolIi), StudyTypeCode.getByCode(dto.getTrialType()));
            }
            if (sponsorIdIi != null) {
                    new PARelationServiceBean().createSponsorRelations(sponsorIdIi.getExtension(), 
                        IiConverter.convertToLong(studyProtocolIi));
                    if (dto.getResponsibleParty().equalsIgnoreCase("pi") && orgIdIi != null) {
                        new PARelationServiceBean().createPIAsResponsiblePartyRelations(orgIdIi.getExtension(), 
                            leadPrincipalInvestigator.getExtension(),
                            IiConverter.convertToLong(studyProtocolIi), dto.getPiEmail(), 
                            dto.getPiPhone());
                    } else {
                        new PARelationServiceBean().createSponsorAsPrimaryContactRelations(sponsorIdIi.getExtension(), 
                            responsiblePartyContact.getExtension(), IiConverter
                            .convertToLong(studyProtocolIi), dto.getSponsorContactEmail(), 
                             dto.getSponsorContactPhone());
                    }                
            }
            //get the protocol
             protocolAssignedId = 
                 RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi).
                 getAssignedIdentifier().getExtension().toString();
             protocolAssignedId =  " Successfully Registered with NCI Identifier " + protocolAssignedId;
             sucessCount +=  1;
        } catch (PAException ex) {
            failedCount +=  1;
            log.error("buildprotocol exception-" + ex.getMessage());
            protocolAssignedId =  "Failed:" + ex.getMessage() + "\n";
        } catch (Exception exc) {
            failedCount +=  1;
        protocolAssignedId =  "Failed:" + exc.getMessage() + "\n";
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
        //if the Phase's value is not in allowed LOV then save phase as Other
        // and comments as the value of current phase
        if (null == PhaseCode.getByCode(batchDto.getPhase()))  {
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode
                    .getByCode(PhaseCode.OTHER.getCode())));
            protocolDTO.setPhaseOtherText(
                    StConverter.convertToSt(batchDto.getPhase()));
        } else {
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode
                    .getByCode(batchDto.getPhase())));
            if (PAUtil.isNotEmpty(batchDto.getPhaseOtherValueSp())) {
                protocolDTO.setPhaseOtherText(
                    StConverter.convertToSt(batchDto.getPhaseOtherValueSp()));
            }
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
        protocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
        return protocolDTO;
    }
    
    /**
     * Create a NCT record for the study protocol.
     * @param studyProtocolIi studyProtocolIi
     */
    private void createStudyNCTNumber(Ii studyProtocolIi, StudyProtocolBatchDTO batchDto) {
        try {
            StudyParticipationDTO studyParticipationDTO;
            String poOrgid = getCTGovIdentifier();

            if (PAUtil.isNotEmpty(batchDto.getNctNumber())) {
                long roId = RegistryServiceLocator.getOrganizationCorrelationService()
                                    .createResearchOrganizationCorrelations(poOrgid);
                studyParticipationDTO = new StudyParticipationDTO();
                studyParticipationDTO.setStudyProtocolIdentifier(studyProtocolIi);
                studyParticipationDTO.setResearchOrganizationIi(IiConverter.convertToIi(poOrgid));
                studyParticipationDTO.setFunctionalCode(CdConverter.convertToCd(
                        StudyParticipationFunctionalCode.IDENTIFIER_ASSIGNER));
                studyParticipationDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(
                        batchDto.getNctNumber()));
                studyParticipationDTO.setResearchOrganizationIi(IiConverter.convertToIi(roId));
                RegistryServiceLocator.getStudyParticipationService().create(studyParticipationDTO); 
            } 
        } catch (PAException pae) {
            // pae.printStackTrace();
            log.error("Exception occured while creating NCT number: " + pae);
        }
        
    }
    
    private String getCTGovIdentifier() throws  PAException {
        OrganizationDTO poOrgDto = new OrganizationDTO();
        poOrgDto.setName(EnOnConverter.convertToEnOn("ClinicalTrials.gov"));
        List<OrganizationDTO> poOrgs = RegistryServiceLocator.getPoOrganizationEntityService().search(poOrgDto);
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
                Ii ii = RegistryServiceLocator.getPoOrganizationEntityService().createOrganization(poOrgDto);
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
 * @param studyProtocolIi ii
 * @param batchDto dto
 * @throws PAException ex
 */
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
            if (null != TrialStatusReasonCode.getByCode(batchDto.getCurrentTrialStatus())) {
                    overallStatusDTO.setReasonText(StConverter.convertToSt(batchDto.getReasonForStudyStopped()));
            }
            RegistryServiceLocator.getStudyOverallStatusService().create(
                    overallStatusDTO);
            log.info("leaving createStudyStatus....");
    }

    /**
     * 
     * @param studyProtocolIi
     *            studyProtocolIi
     * @param batchDto
     *            batchDto
     * @throws PAException ex 
     */
    private void createStudyResources(Ii studyProtocolIi,
            StudyProtocolBatchDTO batchDto) throws PAException {
        if (PAUtil.isNotEmpty(batchDto.getNihGrantFundingMechanism()) 
            && PAUtil.isNotEmpty(batchDto.getNihGrantFundingMechanism())
            && PAUtil.isNotEmpty(batchDto.getNihGrantFundingMechanism())
            && PAUtil.isNotEmpty(batchDto.getNihGrantFundingMechanism())) {
            
            StudyResourcingDTO studyResoureDTO = null;
            studyResoureDTO = new StudyResourcingDTO();
            studyResoureDTO.setStudyProtocolIi(studyProtocolIi);
            studyResoureDTO.setSummary4ReportedResourceIndicator(BlConverter
                    .convertToBl(Boolean.FALSE));
            studyResoureDTO.setFundingMechanismCode(CdConverter
                    .convertStringToCd(batchDto.getNihGrantFundingMechanism()));
            studyResoureDTO.setNciDivisionProgramCode(CdConverter
                    .convertToCd(NciDivisionProgramCode.getByCode(batchDto
                            .getNihGrantNCIDivisionCode())));
            studyResoureDTO.setNihInstitutionCode(CdConverter
                    .convertStringToCd(batchDto.getNihGrantInstituteCode()));
            studyResoureDTO.setSerialNumber(IntConverter.convertToInt(batchDto
                    .getNihGrantSrNumber()));
            RegistryServiceLocator.getStudyResourcingService()
                    .createStudyResourcing(studyResoureDTO);
            log.info("leaving createStudyResources ....");
        }
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
        log.info("Entering lookup Org ...");
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
                            IiConverter.converToIdentifiedOrgEntityIi(batchDto.getOrgCTEPId()));
                    List<IdentifiedOrganizationDTO> identifiedOrgs = RegistryServiceLocator
                            .getIdentifiedOrganizationEntityService().search(identifiedOrganizationDTO);
                    if (identifiedOrgs != null && identifiedOrgs.size() > 0) {
                        criteria.setIdentifier(identifiedOrgs.get(0).getPlayerIdentifier());
                    }
                } 
                if (null == criteria.getIdentifier()  
                        || PAUtil.isEmpty(batchDto.getOrgCTEPId())) {
                    criteria.setName(EnOnConverter.convertToEnOn(orgName));
                    criteria.setPostalAddress(AddressConverterUtil.create(null, null,
                        cityName, null, zipCode, countryName.toUpperCase()));
                }
                List<OrganizationDTO> poOrgDtos = RegistryServiceLocator
                        .getPoOrganizationEntityService().search(criteria);
                if (null == poOrgDtos || poOrgDtos.size() == 0) {
                    // create a new org and then return the new Org
                    log.info(" lookUpOrgs Serch return no org so creating new");
                    orgId = createOrganization(batchDto);
                } else {
                    // return the Id of the org
                    orgId = poOrgDtos.get(0).getIdentifier();
                    log.info(" lookUpOrgs Serch returned orgId" + orgId.getExtension().toString());
                }
            } catch (Exception e) {
                log.error("lookUpOrgs exception" + e.getMessage());
                throw new PAException(e.getMessage());
            }
        log.info("leaving lookup Org with OrgId" + orgId.getExtension());
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
        log.info("Entering Create Org ..");
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
        String zipCode = batchDto.getZip();
        if (PAUtil.isEmpty(zipCode)) {
            log.error("createOrganization throwing exception" + "Zip is a required field");
            throw new PAException("Zip is a required field");
        }
        String countryName = batchDto.getCountry();
        if (PAUtil.isEmpty(countryName)) {
            log.error("createOrganization throwing exception" + "Country is a required field");
            throw new PAException("Country is a required field");
        } else {
            countryName = countryName.toUpperCase();
        }
        String stateName = batchDto.getState();
        if (countryName != null && countryName.equalsIgnoreCase("USA")) {
            if (stateName != null && !PAUtil.isNotEmpty(stateName)) {
                throw new PAException("State is required for US");
            }            
        } 
        if (PAUtil.isNotEmpty(stateName) && (batchDto.getCountry().equalsIgnoreCase("USA")
                    || batchDto.getCountry().equalsIgnoreCase("CAN")
                    || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            stateName = stateName.toUpperCase();
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
        log.info("leaving Create Org with OrgId" + orgId.getExtension());
        return orgId;
    }
    /**
     * 
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     */
    public Ii lookUpPersons(PersonBatchDTO batchDto) throws PAException {
        log.info("Entering Look up person...");
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
            List<gov.nih.nci.services.person.PersonDTO> poPersonList = 
                                                        new ArrayList<gov.nih.nci.services.person.PersonDTO>();
            if (ctep != null && ctep.length() > 0) {
                IdentifiedPersonDTO identifiedPersonDTO = new IdentifiedPersonDTO();
                identifiedPersonDTO.setAssignedId(IiConverter.converToIdentifiedPersonEntityIi(ctep));
                List<IdentifiedPersonDTO> retResultList = 
                                  RegistryServiceLocator.getIdentifiedPersonEntityService().search(identifiedPersonDTO);
                if (retResultList != null && retResultList.size() > 0) {
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
            if (null == poPersonList ||  poPersonList.size() ==  0) {
                log.info("No Person found so creating new Person");
                personId = createPerson(batchDto);
            }  else {
                personId = poPersonList.get(0).getIdentifier();             
                log.info("Person found ");
            }
            
        } catch (Exception e) {
            log.error("lookUpPersons exception " + e.getMessage());
            throw new PAException("lookUpPersons exception " + e.getMessage());
        }
        log.info("leaving Look up person  with personId" + personId);
        return personId;
    }
    /**
     * 
     * @param  batchDto dto
     * @return Ii personId
     * @throws PAException ex 
     */
    public Ii createPerson(PersonBatchDTO batchDto) throws PAException  {
        log.info("Entering created person  ...");
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
        } else {
            country = country.toUpperCase();
        }
        String state =  batchDto.getState();
        if (country != null && country.equalsIgnoreCase("USA")) {
            if (state != null && !PAUtil.isNotEmpty(state)) {
                throw new PAException("State is required for US");
            }            
        } 
        if (PAUtil.isNotEmpty(state) && (batchDto.getCountry().equalsIgnoreCase("USA")
                || batchDto.getCountry().equalsIgnoreCase("CAN")
                || batchDto.getCountry().equalsIgnoreCase("AUS"))) {
            state = state.toUpperCase();
        }

        log.error("State as" + state + " Country as " + country);
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
        log.info("leaving created person  with personId" + personId);
        return personId;
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
        log.info("Entering uploadDocument having docTypeCode " + docTypeCode);
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
        log.info("Leaving uploadDocument ...");
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
        log.info("Entering createIndIdeIndicators....");   
        //check if the values are present..
        if (!isIndIdeEmpty(dto)) {
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
            }
     log.info("leaving createIndIdeIndicators....");
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
}
