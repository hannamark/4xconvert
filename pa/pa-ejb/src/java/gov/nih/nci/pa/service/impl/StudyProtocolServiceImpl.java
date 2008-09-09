package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.StudyProtocolDAO;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyTypeCode;

import gov.nih.nci.pa.service.PAException;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementer class for query Protocol, which will be invoked by the EJB bean.
 * If need be, these methods can be exposed as web service
 * @author Harsha, Naveen
 */
//@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
@SuppressWarnings("PMD")
public class StudyProtocolServiceImpl  {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceImpl.class);
    private static final int PI_PERSON_3 = 3;
    private static final int LEAD_ORG_4 = 4;
    private static final int ST_PARTY_5 = 5;
    /**
     * @param spsc StudyProtocolSearchCriteria
     * @return List QueryStudyProtocolDTO   
     * @throws PAException on error 
     */
    public List<StudyProtocolQueryDTO> 
        getStudyProtocolByCriteria(StudyProtocolQueryCriteria spsc) throws PAException {      
       LOG.debug("Entering getStudyProtocolByCriteria ");
       List<Object> queryList = new StudyProtocolDAO().getStudyProtocolByCriteria(spsc); 
       return convertToStudyProtocolDTO(queryList);
           
    }
    
    /**
     * 
     * @param studyProtocolId studyProtocolId
     * @return StudyProtocolQueryDTO
     * @throws PAException PAException 
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(Long studyProtocolId) 
    throws PAException {
        LOG.debug("Entering getTrialSummaryByStudyProtocolId ");
        if (studyProtocolId == null) {
            LOG.error(" studyProtocolId cannot be null ");
            throw new PAException(" studyProtocolId cannot be null ");
        }
        StudyProtocolQueryCriteria spqc = new StudyProtocolQueryCriteria();
        spqc.setStudyProtocolId(studyProtocolId);
        List<Object> queryList = new StudyProtocolDAO().getStudyProtocolByCriteria(spqc); 
        if (queryList == null) {
            // this will never happen is real scenario, as a practice throw exception
            LOG.error(" Study protcol was not found for id " + studyProtocolId);
            throw new PAException(" Study protcol was not found for id " + studyProtocolId);
        }
        List<StudyProtocolQueryDTO>  trialSummarys = convertToStudyProtocolDTO(queryList);
        
        if (trialSummarys == null || trialSummarys.size() <= 0) {
            // this will never happen is real scenario, as a practice throw exception
            LOG.error(" Could not be converted to DTO for id " + studyProtocolId);
            throw new PAException(" Could not be converted to DTO for id " + studyProtocolId);
        }
        StudyProtocolQueryDTO trialSummary = trialSummarys.get(0);
        LOG.debug("Leaving getTrialSummaryByStudyProtocolId ");
        return trialSummary;
    }
    
    /**
    *
    * @param protocolQueryResult
    * @return List ProtocolDTO
    * @throws PAException paException
    */

   private List<StudyProtocolQueryDTO> convertToStudyProtocolDTO(
           List<Object> protocolQueryResult) throws PAException {
       LOG.debug("Entering convertToStudyProtocolDTO ");
       List<StudyProtocolQueryDTO> studyProtocolDtos = new ArrayList<StudyProtocolQueryDTO>();
       StudyProtocolQueryDTO studyProtocolDto = null;
       StudyProtocol studyProtocol = null;
       StudyOverallStatus studyOverallStatus = null;
       DocumentWorkflowStatus documentWorkflowStatus = null;
       Organization organization = null;
       Person person = null;
       StudyParticipation studyParticipation = null;
       // array of objects for each row
       Object[] searchResult = null;
       try {
           for (int i = 0; i < protocolQueryResult.size(); i++) {
               searchResult = (Object[]) protocolQueryResult.get(i);
               if (searchResult == null) {
                      break; 
               }
               studyProtocolDto = new StudyProtocolQueryDTO();
               // get study protocol
               studyProtocol = (StudyProtocol) searchResult[0];
               // get documentWorkflowStatus
               documentWorkflowStatus = (DocumentWorkflowStatus) searchResult[1];
               // get studyOverallStatus
               studyOverallStatus = (StudyOverallStatus) searchResult[2];
               // get the person
               person = (Person) searchResult[PI_PERSON_3];
               // get the organization 
               organization = (Organization) searchResult[LEAD_ORG_4];
               // get the StudyParticipation 
               studyParticipation = (StudyParticipation) searchResult[ST_PARTY_5];
               
               // transfer protocol to studyProtocolDto
               if (documentWorkflowStatus != null) {
                   studyProtocolDto.setDocumentWorkflowStatusCode(
                           documentWorkflowStatus.getStatusCode());
                   studyProtocolDto.setDocumentWorkflowStatusDate(
                           documentWorkflowStatus.getStatusDateRangeLow());
               }
               if (studyProtocol != null) {
                   studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
                   studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
                   studyProtocolDto.setNciIdentifier(studyProtocol.getIdentifier());
                   studyProtocolDto.setStudyTypeCode(StudyTypeCode.INTERVENTIONAL);
                   //@todo : hardcoded for interventional, its has to be derived
               }
               if (studyOverallStatus != null) {
                   studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStatusCode());
                   studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStatusDate());
               }
               if (organization != null) {
                   studyProtocolDto.setLeadOrganizationName(organization.getName());
                   studyProtocolDto.setLeadOrganizationId(organization.getId());
               }
               if (person != null) {
                   studyProtocolDto.setPiFullName(person.getFullName());
                   studyProtocolDto.setPiId(person.getId());
               }
               if (studyParticipation != null) {
                   studyProtocolDto.setLocalStudyProtocolIdentifier(
                           studyParticipation.getLocalStudyProtocolIdentifier());
               }
               // add to the list
               studyProtocolDtos.add(studyProtocolDto);
           } // for loop
       } catch (Exception e) {
           LOG.error("General error in while converting to DTO", e);
           throw new PAException("General error in while converting to DTO2", e);
       } finally {
           LOG.debug("Leaving convertToStudyProtocolDTO ");
       }
       return studyProtocolDtos;
   }
    

}
