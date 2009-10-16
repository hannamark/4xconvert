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

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelUrl;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
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
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
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
import java.util.Set;

import org.apache.log4j.Logger;
/**
 * @author Vrushali
 * 
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.ExcessiveClassLength", 
    "PMD.ExcessiveMethodLength" })
public class BatchCreateProtocols {
    private static final Logger LOG = Logger.getLogger(BatchCreateProtocols.class);
    private int sucessCount = 0;
    private int failedCount = 0;
    private static final String FAILED = "Failed:";
    /**
     * 
     * @param dtoList list
     * @param folderPath path 
     * @param userName name
     * @return map
     */
    @SuppressWarnings({"PMD.LooseCoupling" })
    public HashMap<String, String> createProtocols(List<StudyProtocolBatchDTO> dtoList,
            String folderPath, String userName) {
        LOG.info("Entering into createProtocols...having size of dtolist"
                + dtoList.size());
        HashMap<String, String> map = new HashMap<String, String>();
        if (dtoList == null || dtoList.size() < 1) {
            map.put("Failed Trial Count" , String.valueOf(failedCount));
            map.put("Sucess Trial Count" , String.valueOf(sucessCount));
            return map;
        }
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
                    result.insert(0, FAILED);
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
    private String buildProtocol(StudyProtocolBatchDTO dto , String folderPath , String userName)  {
        Ii studyProtocolIi = null;
        String protocolAssignedId  = "";
        try {
             if (!dto.getSubmissionType().equalsIgnoreCase("U")) {
             studyProtocolIi = submitOriginalOrAmendment(dto , folderPath , userName);
             } else {
                 studyProtocolIi = submitUpdate(dto , folderPath , userName);
             }
             if (PAUtil.isIiNull(studyProtocolIi)) {
                 failedCount +=  1; 
             } else {
               String strMsg = " Successfully Registered with NCI Identifier "; //get the protocol
               if (dto.getSubmissionType().equalsIgnoreCase("U")) {
                  strMsg  = " Successfully Updated NCI Identifier ";
                  
               }
               sucessCount +=  1;
              protocolAssignedId = strMsg 
                 + RegistryServiceLocator.getStudyProtocolService()
                         .getStudyProtocol(studyProtocolIi).getAssignedIdentifier().getExtension();
             
             }
        } catch (PAException ex) {
            failedCount +=  1;
            LOG.error("buildprotocol exception-" + ex.getMessage());
            protocolAssignedId = FAILED + ex.getMessage() + "\n";
        } catch (Exception exc) {
            failedCount +=  1;
            protocolAssignedId = FAILED + exc.getMessage() + "\n";
            LOG.error("buildprotocol exception-" + exc.getMessage());
        }
        LOG.info("response " + protocolAssignedId);
        return protocolAssignedId;
    }
    private Ii submitUpdate(StudyProtocolBatchDTO dto, String folderPath,
            String userName) throws PAException, NullifiedRoleException, 
            NullifiedEntityException, URISyntaxException, EntityValidationException, CurationException, IOException {
            Ii studyProtocolIi = null;
            
            StudyRegulatoryAuthorityDTO studyRegAuthDTO = new StudyRegulatoryAuthorityDTO();
            
            Long regAuthId = RegistryServiceLocator.getRegulatoryInformationService().
                getRegulatoryAuthorityId(dto.getOversightOrgName(), dto.getOversightAuthorityCountry());
            studyRegAuthDTO.setRegulatoryAuthorityIdentifier(IiConverter.convertToIi(regAuthId));
            
            TrialUtil util = new TrialUtil();
            TrialDTO trialDTO = new TrialDTO();
            
            StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
            viewCriteria.setNciIdentifier(dto.getNciTrialIdentifier());
            List<StudyProtocolQueryDTO> listofDto = RegistryServiceLocator.getProtocolQueryService().
                                        getStudyProtocolByCriteria(viewCriteria);
            if (listofDto.isEmpty() || listofDto.size() > 1) {
                throw new PAException("mutliple trial or no trial found for given NCI Trial Identifier.\n");
            }
            StudyProtocolQueryDTO querydto = listofDto.get(0);
            if (querydto.getIsProprietaryTrial() != null 
                    && querydto.getIsProprietaryTrial().equalsIgnoreCase("true")) {
                throw new PAException("Proprietary trials Update not supported. ");
            }
            studyProtocolIi = IiConverter.convertToIi(listofDto.get(0).getStudyProtocolId());
            studyRegAuthDTO.setStudyProtocolIdentifier(studyProtocolIi);
            
            trialDTO = getTrialDTOForUpdate(dto, folderPath, studyProtocolIi);

            StudyRegulatoryAuthorityDTO sraFromDatabaseDTO = RegistryServiceLocator.
                getStudyRegulatoryAuthorityService().getCurrentByStudyProtocol(studyProtocolIi);
            if (sraFromDatabaseDTO != null) {
                studyRegAuthDTO.setIdentifier(sraFromDatabaseDTO.getIdentifier());
            }
            
            StudyProtocolDTO studyProtocolDTO = null;
            studyProtocolDTO = RegistryServiceLocator.getStudyProtocolService().getStudyProtocol(studyProtocolIi);
            util.updateStudyProtcolDTO(studyProtocolDTO, trialDTO);
            
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
            studyProtocolDTO.setIdentifier(IiConverter.convertToStudyProtocolIi(
                    Long.parseLong(studyProtocolDTO.getIdentifier().getExtension())));
            StudyOverallStatusDTO overallStatusDTO = util.convertToStudyOverallStatusDTO(trialDTO);
            overallStatusDTO.setStudyProtocolIdentifier(studyProtocolIi);
            List<DocumentDTO> documentDTOs = util.convertToISODocumentList(trialDTO.getDocDtos());
            StudyContactDTO studyContactDTO = null;
            StudySiteContactDTO studySiteContactDTO = null;
            OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
            StudyResourcingDTO summary4studyResourcingDTO = RegistryServiceLocator.getStudyResourcingService()
                .getsummary4ReportedResource(studyProtocolIi); 
            if (summary4studyResourcingDTO != null) {
                util.convertToSummary4StudyResourcingDTO(trialDTO, summary4studyResourcingDTO);
                summary4studyResourcingDTO.setStudyProtocolIdentifier(studyProtocolIi);
            } else {
                summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
                if (summary4studyResourcingDTO != null) {
                    summary4studyResourcingDTO.setStudyProtocolIdentifier(studyProtocolIi);
                }
            }
            
            Ii responsiblePartyContactIi = null;
            if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
                studyContactDTO = util.convertToStudyContactDTO(trialDTO);
            } else {
                studySiteContactDTO = util.convertToStudySiteContactDTO(trialDTO);
                if (trialDTO.getResponsiblePersonName() != null && !trialDTO.getResponsiblePersonName().equals("")) {
                 responsiblePartyContactIi = IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
                  }
                  if (trialDTO.getResponsibleGenericContactName() != null 
                            && !trialDTO.getResponsibleGenericContactName().equals("")) {
                      responsiblePartyContactIi = IiConverter.
                          convertToPoOrganizationalContactIi(trialDTO.getResponsiblePersonIdentifier());
                  }
            }
            List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
            if (studyIndldeDTOs != null && !studyIndldeDTOs.isEmpty()) {
                for (Iterator it = studyIndldeDTOs.iterator(); it.hasNext();) {
                    StudyIndldeDTO indIdeDto = (StudyIndldeDTO) it.next();
                    try {
                        RegistryServiceLocator.getStudyIndldeService().validate(indIdeDto);
                    } catch (PADuplicateException dupEx) {
                        it.remove();
                    }
                }
            }
            List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
            if (studyResourcingDTOs != null && !studyResourcingDTOs.isEmpty()) {
                for (Iterator it = studyResourcingDTOs.iterator(); it.hasNext();) {
                    StudyResourcingDTO studyResourcingDTO = (StudyResourcingDTO) it.next();
                    try {
                        RegistryServiceLocator.getStudyResourcingService().validate(studyResourcingDTO);
                    } catch (PADuplicateException dupEx) {
                        it.remove();
                    }
                }
            }
          //set the NCT number 
            StudySiteDTO ssNctIdDto  = null;
            if (PAUtil.isNotEmpty(trialDTO.getNctIdentifier())) {
                ssNctIdDto = util.getStudySite(studyProtocolIi, StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
                if (ssNctIdDto != null) {
                    util.convertToNCTStudySiteDTO(trialDTO, ssNctIdDto);
                } else {
                    ssNctIdDto = new StudySiteDTO();
                    ssNctIdDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(trialDTO.getNctIdentifier()));
                }
            }
            //get the values from db and update only those are needed and then convert
                RegistryServiceLocator.getTrialRegistrationService().
                    update(studyProtocolDTO, overallStatusDTO, ssNctIdDto, studyIndldeDTOs, studyResourcingDTOs, 
                    documentDTOs, studyContactDTO, studySiteContactDTO, summary4orgDTO, summary4studyResourcingDTO, 
                    responsiblePartyContactIi, studyRegAuthDTO, null, null, null);
               
             return studyProtocolIi;
    }

    /**
     * @param batchDto
     * @param folderPath
     * @param studyProtocolIi
     * @throws PAException
     * @throws NullifiedRoleException
     * @throws NullifiedEntityException
     * @throws URISyntaxException
     * @throws EntityValidationException
     * @throws CurationException
     * @throws IOException
     * @returns trialDTO
     */
    private TrialDTO getTrialDTOForUpdate(StudyProtocolBatchDTO batchDto,
            String folderPath, Ii studyProtocolIi)
            throws PAException, NullifiedRoleException,
            NullifiedEntityException, URISyntaxException,
            EntityValidationException, CurationException, IOException {

                TrialDTO trialDTO = new TrialDTO();
                TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
                TrialUtil util = new TrialUtil();
                util.getTrialDTOFromDb(studyProtocolIi, trialDTO);
                //update the trial DTO with new values
                trialDTO.setPrimaryPurposeCode(batchDto.getPrimaryPurpose());
                if (PAUtil.isEmpty(batchDto.getPrimaryPurposeOtherValueSp())) {
                    trialDTO.setPrimaryPurposeOtherText(batchDto.getPrimaryPurposeOtherValueSp());
                }
                if (null == PhaseCode.getByCode(batchDto.getPhase()))  {
                    trialDTO.setPhaseCode(PhaseCode.OTHER.getCode());
                    trialDTO.setPhaseOtherText(batchDto.getPhase());
                } else {
                    trialDTO.setPhaseCode(batchDto.getPhase());
                    if (PAUtil.isNotEmpty(batchDto.getPhaseOtherValueSp())) {
                        trialDTO.setPhaseOtherText(batchDto.getPhaseOtherValueSp());
                    }
                } 
                String strPersonal = "Personal";
                if (PAUtil.isNotEmpty(batchDto.getResponsibleParty())) { 
                    trialDTO.setResponsiblePartyType(batchDto.getResponsibleParty());
                    if (batchDto.getResponsibleParty().equalsIgnoreCase("sponsor")) {
                        trialDTO.setContactEmail(batchDto.getSponsorContactEmail());
                        trialDTO.setContactPhone(batchDto.getSponsorContactPhone());
                        if (batchDto.getSponsorContactType().equalsIgnoreCase(strPersonal)) {
                            trialDTO.setResponsiblePersonName(batchDto.getSponsorContactLName() 
                                    + batchDto.getSponsorContactFName());
                            trialDTO.setResponsibleGenericContactName("");
                        } else {
                            trialDTO.setResponsibleGenericContactName(batchDto.getResponsibleGenericContactName());
                            trialDTO.setResponsiblePersonName("");
                        }
                    }
                }   
                trialDTO.setReason(batchDto.getReasonForStudyStopped());
                trialDTO.setStartDate(batchDto.getStudyStartDate());
                trialDTO.setStartDateType(batchDto.getStudyStartDateType());
                trialDTO.setStatusCode(batchDto.getCurrentTrialStatus());
                trialDTO.setStatusDate(batchDto.getCurrentTrialStatusDate());
                trialDTO.setCompletionDate(batchDto.getPrimaryCompletionDate());
                trialDTO.setCompletionDateType(batchDto.getPrimaryCompletionDateType());
                OrganizationBatchDTO summ4Sponsor = dataValidator.buildSummary4Sponsor(batchDto); //Summary 4 Info
                Ii summary4Sponsor = null;
                if (!dataValidator.orgDTOIsEmpty(summ4Sponsor)) {
                    summary4Sponsor = lookUpOrgs(summ4Sponsor); //look up for org only when dto is not empty
                }
                trialDTO.setSummaryFourFundingCategoryCode(batchDto.getSumm4FundingCat());
                //check if Sponsor Contact is needed if needed the the lookup ahead to catch other Validation error
                //look up new Person or create if needed.
                Ii responsiblePersonId = null;
                if (PAUtil.isNotEmpty(batchDto.getResponsibleParty())
                        && batchDto.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
                    if (batchDto.getSponsorContactType().equalsIgnoreCase(strPersonal)) {
                        PersonBatchDTO sponsorPersonDto = dataValidator.buildSponsorContact(batchDto);
                        responsiblePersonId = lookUpPersons(sponsorPersonDto);
                    } else {
                        responsiblePersonId = getTitleIi(batchDto.getResponsibleGenericContactName(),
                                batchDto.getSponsorContactEmail(), batchDto.getSponsorContactPhone(),
                                IiConverter.convertToPoOrganizationIi(trialDTO.getSponsorIdentifier()));
                    }
                }
                if (responsiblePersonId != null) {
                    trialDTO.setResponsiblePersonIdentifier(responsiblePersonId.getExtension());
                }
                if (summary4Sponsor != null) {
                    trialDTO.setSummaryFourOrgIdentifier(summary4Sponsor.getExtension());
                }
                trialDTO.setDocDtos(getDocDTOListForUpdate(batchDto, folderPath,
                        studyProtocolIi.getExtension())); //add doc to the dto
                trialDTO.setIndIdeDtos(getIndsListForUpdate(batchDto, studyProtocolIi.getExtension())); //add ind
                trialDTO.setFundingDtos(getFundingListForUpdate(batchDto, studyProtocolIi.getExtension())); //add grants
                
              //oversight info
                trialDTO.setFdaRegulatoryInformationIndicator(batchDto.getFdaRegulatoryInformationIndicator());
                trialDTO.setSection801Indicator(batchDto.getSection801Indicator());
                trialDTO.setDelayedPostingIndicator(batchDto.getDelayedPostingIndicator());
                trialDTO.setDataMonitoringCommitteeAppointedIndicator(
                        batchDto.getDataMonitoringCommitteeAppointedIndicator());
                trialDTO.setNctIdentifier(batchDto.getNctNumber());
                return trialDTO;
    }

    private List<TrialFundingWebDTO> getFundingListForUpdate(
            StudyProtocolBatchDTO batchDto, String studyProtocolId) {
        List<TrialFundingWebDTO>  fundingList = new ArrayList<TrialFundingWebDTO>();
        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        fundingList = dataValidator.convertToGrantList(batchDto);
        for (TrialFundingWebDTO dto : fundingList) {
            dto.setStudyProtocolId(studyProtocolId);
        }
        return fundingList;
    }

    private List<TrialIndIdeDTO> getIndsListForUpdate(
            StudyProtocolBatchDTO batchDto, String studyProtocolId) {
        List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
        TrialBatchDataValidator dataValidator =  new TrialBatchDataValidator();
        indList = dataValidator.convertIndsToList(batchDto);
        for (TrialIndIdeDTO dto : indList) {
            dto.setStudyProtocolId(studyProtocolId);
        }
        return indList;
    }

    private List<TrialDocumentWebDTO> getDocDTOListForUpdate(
            StudyProtocolBatchDTO batchDto, String folderPath, String studyProtocolId) throws IOException, PAException {
        TrialUtil util = new TrialUtil();
        
        List<DocumentDTO> docsFromDB = RegistryServiceLocator.getDocumentService().
            getDocumentsByStudyProtocol(IiConverter.convertToIi(studyProtocolId));
        Ii irbDocId = null;
        Ii partDocId = null;
        for (DocumentDTO doc : docsFromDB) {
            if (DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode().equals(
                        CdConverter.convertCdToString(doc.getTypeCode()))) {
                irbDocId = doc.getIdentifier();
            }
            if (DocumentTypeCode.PARTICIPATING_SITES.getCode().equals(
                    CdConverter.convertCdToString(doc.getTypeCode()))) {
                partDocId = doc.getIdentifier();
            }
        }  
        File doc = new File(folderPath + batchDto.getProtcolDocumentFileName());
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        if (PAUtil.isNotEmpty(batchDto.getIrbApprovalDocumentFileName())) {
            doc = new File(folderPath + batchDto.getIrbApprovalDocumentFileName());
            TrialDocumentWebDTO  webDto = new TrialDocumentWebDTO();
            webDto = util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(), 
                 batchDto.getIrbApprovalDocumentFileName(), doc);
            webDto.setId(irbDocId.getExtension());
            webDto.setStudyProtocolId(studyProtocolId);
            docDTOList.add(webDto);
        }
        if (PAUtil.isNotEmpty(batchDto.getParticipatinSiteDocumentFileName())) {
            doc = new File(folderPath + batchDto.getParticipatinSiteDocumentFileName());
            TrialDocumentWebDTO  webDto = new TrialDocumentWebDTO();
            webDto = util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                    batchDto.getParticipatinSiteDocumentFileName(), doc);
            if (!PAUtil.isIiNull(partDocId)) {
                webDto.setId(partDocId.getExtension());
            }
            webDto.setStudyProtocolId(studyProtocolId);
            docDTOList.add(webDto);
         }
        return docDTOList;
    }

    private Ii submitOriginalOrAmendment(StudyProtocolBatchDTO dto,
            String folderPath, String userName) throws NullifiedEntityException, PAException,
            URISyntaxException, EntityValidationException, CurationException, IOException {
        Ii studyProtocolIi = null;
        
        TrialUtil util = new TrialUtil();
        StudyProtocolDTO studyProtocolDTO = null;
        TrialDTO trialDTO = convertToTrialDTO(dto, folderPath);
        
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
        StudySiteDTO leadOrgSiteIdDTO = util.convertToStudySiteDTO(trialDTO);
        StudySiteDTO nctIdentifierSiteIdDTO = util.convertToNCTStudySiteDTO(trialDTO);
        StudyContactDTO studyContactDTO = null;
        StudySiteContactDTO studySiteContactDTO = null;
        OrganizationDTO summary4orgDTO = util.convertToSummary4OrgDTO(trialDTO);
        StudyResourcingDTO summary4studyResourcingDTO = util.convertToSummary4StudyResourcingDTO(trialDTO);
        Ii responsiblePartyContactIi = null;
        if (trialDTO.getResponsiblePartyType().equalsIgnoreCase("pi")) {
            studyContactDTO = util.convertToStudyContactDTO(trialDTO);
        } else {
            studySiteContactDTO = util.convertToStudySiteContactDTO(trialDTO);
            if (trialDTO.getResponsiblePersonName() != null && !trialDTO.getResponsiblePersonName().equals("")) {
             responsiblePartyContactIi = IiConverter.convertToPoPersonIi(trialDTO.getResponsiblePersonIdentifier());
              }
              if (trialDTO.getResponsibleGenericContactName() != null 
                        && !trialDTO.getResponsibleGenericContactName().equals("")) {
                  responsiblePartyContactIi = IiConverter.
                      convertToPoOrganizationalContactIi(trialDTO.getResponsiblePersonIdentifier());
              }
        }
        List<StudyIndldeDTO> studyIndldeDTOs = util.convertISOINDIDEList(trialDTO.getIndIdeDtos());
        List<StudyResourcingDTO> studyResourcingDTOs = util.convertISOGrantsList(trialDTO.getFundingDtos());
        if (dto.getSubmissionType().equalsIgnoreCase("O") && !PAUtil.isNotEmpty(trialDTO.getAssignedIdentifier())) {
            studyProtocolIi =  RegistryServiceLocator.getTrialRegistrationService().
                createInterventionalStudyProtocol(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs,
                        studyResourcingDTOs, documentDTOs, leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO,
                        leadOrgSiteIdDTO, nctIdentifierSiteIdDTO, studyContactDTO, studySiteContactDTO,
                        summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi);
        } else if (dto.getSubmissionType().equalsIgnoreCase("A")) {
            //get the Identifier of study protocol by giving nci identifier
            StudyProtocolQueryCriteria viewCriteria = new StudyProtocolQueryCriteria();
            viewCriteria.setNciIdentifier(trialDTO.getAssignedIdentifier());
            List<StudyProtocolQueryDTO> listofDto = RegistryServiceLocator.getProtocolQueryService().
                                        getStudyProtocolByCriteria(viewCriteria);
            if (listofDto.isEmpty() || listofDto.size() > 1) {
                throw new PAException("mutliple trial or no trial found for given NCI Trial Identifier.\n");
            } else {
                trialDTO.setIdentifier(listofDto.get(0).getStudyProtocolId().toString());
            }
            
            studyProtocolDTO = util.convertToStudyProtocolDTOForAmendment(trialDTO);
            studyProtocolDTO.setUserLastCreated(StConverter.convertToSt(userName));
            studyProtocolIi =  RegistryServiceLocator.getTrialRegistrationService().
                amend(studyProtocolDTO, overallStatusDTO, studyIndldeDTOs, studyResourcingDTOs,
                    documentDTOs, leadOrgDTO, principalInvestigatorDTO, sponsorOrgDTO, leadOrgSiteIdDTO,
                    nctIdentifierSiteIdDTO, studyContactDTO, studySiteContactDTO,
                    summary4orgDTO, summary4studyResourcingDTO, responsiblePartyContactIi);
             
        }
        return studyProtocolIi;
    }

    /*
     * this method first look if title is there if not create new
     */
    private Ii getTitleIi(String responsibleGenericContactName,
            String sponsorContactEmail, String sponsorPhone, Ii sponsorIdIi) 
            throws PAException, URISyntaxException, EntityValidationException, CurationException {
            Ii responsibleIi = null;
            OrganizationalContactDTO contactDTO = new OrganizationalContactDTO();
            contactDTO.setScoperIdentifier(sponsorIdIi);
            contactDTO.setTitle(StConverter.convertToSt(responsibleGenericContactName));
            DSet<Cd> orgContactType = new DSet<Cd>();
            Set<Cd> orgContactCd = new HashSet<Cd>();
            orgContactCd.add(CdConverter.convertStringToCd("Responsible Party"));
            orgContactType.setItem(orgContactCd);
            DSet<Tel> list = getDSetTelList(sponsorContactEmail, sponsorPhone, "", "", "");
            contactDTO.setTelecomAddress(list);
            contactDTO.setTypeCode(orgContactType);
            List<OrganizationalContactDTO> isoDtoList = new ArrayList<OrganizationalContactDTO>();
            isoDtoList = PoRegistry.getOrganizationalContactCorrelationService().search(contactDTO);
            if (isoDtoList.isEmpty()) {
                //create the title
                responsibleIi = PoRegistry.getOrganizationalContactCorrelationService()
                .createCorrelation(contactDTO);
            } else {
                responsibleIi = DSetConverter.convertToIi(isoDtoList.get(0).getIdentifier());
            }
        return responsibleIi;
    }
    private TrialDTO convertToTrialDTO(StudyProtocolBatchDTO batchDTO, String folderPath) throws IOException,
        NullifiedEntityException, PAException, URISyntaxException, EntityValidationException, CurationException {
        
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
            if (batchDTO.getSponsorContactType().equalsIgnoreCase("Personal")) {
                trialDTO.setResponsiblePersonName(batchDTO.getSponsorContactLName() 
                    + batchDTO.getSponsorContactFName());
            } else {
                trialDTO.setResponsibleGenericContactName(batchDTO.getResponsibleGenericContactName());
            }
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
        trialDTO.setProgramCodeText(batchDTO.getProgramCodeText());
        if (batchDTO.getSubmissionType().equalsIgnoreCase("A")) {
            trialDTO.setAssignedIdentifier(batchDTO.getNciTrialIdentifier());
            trialDTO.setAmendmentDate(batchDTO.getAmendmentDate());
            trialDTO.setLocalAmendmentNumber(batchDTO.getAmendmentNumber());
        }
        //oversight info
        trialDTO.setFdaRegulatoryInformationIndicator(batchDTO.getFdaRegulatoryInformationIndicator());
        trialDTO.setSection801Indicator(batchDTO.getSection801Indicator());
        trialDTO.setDelayedPostingIndicator(batchDTO.getDelayedPostingIndicator());
        trialDTO.setDataMonitoringCommitteeAppointedIndicator(
                batchDTO.getDataMonitoringCommitteeAppointedIndicator());
        

        TrialBatchDataValidator dataValidator = new TrialBatchDataValidator();
        // before creating the protocol check for duplicate
        // using the Lead Org Trial Identifier and Lead Org Identifier
        OrganizationBatchDTO leadOrgDto = dataValidator.buildLeadOrgDto(batchDTO);
        Ii orgIdIi = lookUpOrgs(leadOrgDto);            
        OrganizationBatchDTO sponsorOrgDto = dataValidator.buildSponsorOrgDto(batchDTO); //look up sponser
        Ii sponsorIdIi = lookUpOrgs(sponsorOrgDto);
        PersonBatchDTO piDto = dataValidator.buildLeadPIDto(batchDTO); //look up Person
        Ii leadPrincipalInvestigator = lookUpPersons(piDto);
        OrganizationBatchDTO summ4Sponsor = dataValidator.buildSummary4Sponsor(batchDTO); //Summary 4 Info
        Ii summary4Sponsor = null;
        if (!dataValidator.orgDTOIsEmpty(summ4Sponsor)) {
            summary4Sponsor = lookUpOrgs(summ4Sponsor); //look up for org only when dto is not empty
        }
        //check if Sponsor Contact is needed if needed the the lookup ahead to catch other Validation error
        //look up new Person or create if needed.
        Ii responsiblePersonId = null;
        if (batchDTO.getResponsibleParty().equalsIgnoreCase("Sponsor")) {
            if (batchDTO.getSponsorContactType().equalsIgnoreCase("Personal")) {
                PersonBatchDTO sponsorPersonDto = dataValidator.buildSponsorContact(batchDTO);
                responsiblePersonId = lookUpPersons(sponsorPersonDto);
            } else {
                responsiblePersonId = getTitleIi(batchDTO.getResponsibleGenericContactName(),
                        batchDTO.getSponsorContactEmail(), batchDTO.getSponsorContactPhone(),
                        sponsorIdIi);
            }
        }
        trialDTO.setLeadOrganizationIdentifier(orgIdIi.getExtension()); //All the Ii
        trialDTO.setPiIdentifier(leadPrincipalInvestigator.getExtension());
        trialDTO.setSponsorIdentifier(sponsorIdIi.getExtension());
        if (responsiblePersonId != null) {
            trialDTO.setResponsiblePersonIdentifier(responsiblePersonId.getExtension());
        }
        if (summary4Sponsor != null) {
            trialDTO.setSummaryFourOrgIdentifier(summary4Sponsor.getExtension());
        }
        trialDTO.setDocDtos(addDocDTOToList(batchDTO, folderPath)); //add doc to the dto
        trialDTO.setIndIdeDtos(dataValidator.convertIndsToList(batchDTO)); //add ind
        trialDTO.setFundingDtos(dataValidator.convertToGrantList(batchDTO)); //add grants
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
     * @throws CurationException 
     */
    private Ii lookUpOrgs(OrganizationBatchDTO batchDto) throws PAException,
        NullifiedEntityException, URISyntaxException, EntityValidationException, CurationException {
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
                            IiConverter.convertToIdentifiedOrgEntityIi(batchDto.getOrgCTEPId()));
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
        
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        List<OrganizationDTO> poOrgDtos = null;
        try {
            poOrgDtos = RegistryServiceLocator.getPoOrganizationEntityService().search(criteria, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        
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
     * @throws CurationException 
     */
    private Ii createOrganization(OrganizationBatchDTO batchDto)
            throws PAException, URISyntaxException, EntityValidationException, NullifiedEntityException, 
        CurationException {
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
        DSet<Tel> telco = getDSetTelList(email, phoneNumer, faxNumber,
                ttyNumber, url);
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
     * @param email
     * @param phoneNumer
     * @param faxNumber
     * @param ttyNumber
     * @param url
     * @return
     * @throws URISyntaxException
     */
    private DSet<Tel> getDSetTelList(String email, String phoneNumer,
            String faxNumber, String ttyNumber, String url)
            throws URISyntaxException {
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
        return telco;
    }
    /**
     * 
     * @param batchDto dto
     * @return Ii personId
     * @throws PAException ex
     * @throws EntityValidationException 
     * @throws URISyntaxException 
     * @throws CurationException 
     */
    private Ii lookUpPersons(PersonBatchDTO batchDto) throws PAException,
        URISyntaxException, EntityValidationException, CurationException {
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
           identifiedPersonDTO.setAssignedId(IiConverter.convertToIdentifiedPersonEntityIi(ctep));
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
                
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        try {
            poPersonList = RegistryServiceLocator.getPoPersonEntityService().search(p, limit);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }
        
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
     * @throws CurationException 
     */
    private Ii createPerson(PersonBatchDTO batchDto) throws PAException, 
        URISyntaxException, EntityValidationException, CurationException  {
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

        DSet<Tel> list = getDSetTelList(email, phone, fax, tty, url);
        dto.setTelecomAddress(list);
        dto.setPostalAddress(AddressConverterUtil.create(streetAddr, null, city, state, zip, country));
        personId = RegistryServiceLocator.getPoPersonEntityService().createPerson(dto);
        
        LOG.info("leaving created person  with personId" + personId);
        return personId;
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
        //for Amendment Other document type will be skipped.
        if (PAUtil.isEmpty(dto.getAmendmentDate()) && PAUtil.isEmpty(dto.getNciTrialIdentifier())
                && PAUtil.isNotEmpty(dto.getOtherTrialRelDocumentFileName())) {
                doc = new File(folderPath + dto.getOtherTrialRelDocumentFileName());
                docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(), 
                     dto.getOtherTrialRelDocumentFileName(), doc));  
        }
        //original submission will not have amendment date so protocol Highlighted doc and change memo will be skipped.
        if (PAUtil.isNotEmpty(dto.getAmendmentDate()) && PAUtil.isNotEmpty(dto.getNciTrialIdentifier())) {
             if (PAUtil.isNotEmpty(dto.getProtocolHighlightDocFileName())) {
                 doc = new File(folderPath + dto.getProtocolHighlightDocFileName());
                 docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getCode(), 
                         dto.getProtocolHighlightDocFileName(), doc));  
             }
             if (PAUtil.isNotEmpty(dto.getChangeRequestDocFileName())) {
                 doc = new File(folderPath + dto.getChangeRequestDocFileName());
                 docDTOList.add(util.convertToDocumentDTO(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode(), 
                         dto.getChangeRequestDocFileName(), doc));  
             }
        }
        return docDTOList;
    }
}
