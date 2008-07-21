package gov.nih.nci.pa.service.impl;

import gov.nih.nci.pa.dao.StudyProtocolDAO;
import gov.nih.nci.pa.domain.DocumentIdentification;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.QueryStudyProtocolCriteria;
import gov.nih.nci.pa.dto.QueryStudyProtocolDTO;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProtocolService;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Implementer class for query Protocol, which will be invoked by the EJB bean.
 * If need be, these methods can be exposed as web service
 * @author Harsha, Naveen
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class StudyProtocolServiceImpl implements ProtocolService {

    private static final Logger LOG  = Logger.getLogger(StudyProtocolServiceImpl.class);
    private static final int THREE = 3;

    /**
     * @param spsc StudyProtocolSearchCriteria
     * @return List QueryStudyProtocolDTO   
     * @throws PAException on error 
     */
    public List<QueryStudyProtocolDTO> 
        getStudyProtocolByCriteria(QueryStudyProtocolCriteria spsc) throws PAException {      
       LOG.debug("Entering getProtocol ");
       List<Object> queryList = new StudyProtocolDAO().getStudyProtocolByCriteria(spsc); 
       return convertToStudyProtocolDTO(queryList);
           
    }
    
    /**
    *
    * @param protocolQueryResult
    * @return List ProtocolDTO
    * @throws PAException paException
    */

   private List<QueryStudyProtocolDTO> convertToStudyProtocolDTO(
           List<Object> protocolQueryResult) throws PAException {
       LOG.debug("Entering convertToStudyProtocolDTO ");
       List<QueryStudyProtocolDTO> studyProtocolDtos = new ArrayList<QueryStudyProtocolDTO>();
       QueryStudyProtocolDTO studyProtocolDto = null;
       StudyProtocol studyProtocol = null;
       StudyOverallStatus studyOverallStatus = null;
       DocumentWorkflowStatus documentWorkflowStatus = null;
       DocumentIdentification documentIdentification = null;
       // array of objects for each row
       Object[] searchResult = null;
       try {
           for (int i = 0; i < protocolQueryResult.size(); i++) {
               searchResult = (Object[]) protocolQueryResult.get(i);
               if (searchResult == null) {
                      break; 
               }
               studyProtocolDto = new QueryStudyProtocolDTO();
               // get study protocol
               studyProtocol = (StudyProtocol) searchResult[0];
               // get studyOverallStatus
               studyOverallStatus = (StudyOverallStatus) searchResult[1];
               // get documentWorkflowStatus
               documentWorkflowStatus = (DocumentWorkflowStatus) searchResult[2];
               // get documentIdentification    
               documentIdentification =  (DocumentIdentification) searchResult[THREE];
               // transfer protocol to studyProtocolDto
               if (documentWorkflowStatus != null) {
                   studyProtocolDto.setDocumentWorkflowStatusCode(
                           documentWorkflowStatus.getDocumentWorkflowStatusCode());
                   studyProtocolDto.setDocumentWorkflowStatusDate(
                           documentWorkflowStatus.getDocumentWorkflowStatusDate());
               }
               if (documentIdentification != null) {
                   studyProtocolDto.setNciIdentifier(documentIdentification.getIdentifier());
               }
               if (studyProtocol != null) {
                   studyProtocolDto.setOfficialTitle(studyProtocol.getOfficialTitle());
                   studyProtocolDto.setStudyProtocolId(studyProtocol.getId());
               }
               if (studyOverallStatus != null) {
                   studyProtocolDto.setStudyStatusCode(studyOverallStatus.getStudyStatusCode());
                   studyProtocolDto.setStudyStatusDate(studyOverallStatus.getStudyStatusDate());
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
