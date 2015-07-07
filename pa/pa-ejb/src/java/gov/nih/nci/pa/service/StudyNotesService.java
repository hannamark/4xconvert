

package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyNotes;

import java.util.List;

import javax.ejb.Local;

/**
 * @author Apratim K
 * @since 06/26/2015
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Local
public interface StudyNotesService   {
   
    /**
     * @param studyProtocolId studyProtocolId
     * @param object object
     * @return StudyNotesList
     * @throws PAException PAException
     */
    List<? extends StudyNotes> getStudyNotesList(Long studyProtocolId , Class object) throws PAException;
    
    /**
     * @param discrepancyType discrepancyType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyProtocolId studyProtocolId
     * @throws PAException PAException
     */
    void addStudyDataDiscrepancy(String discrepancyType, String actionTaken, String actionCompletionDate ,
            long studyProtocolId) throws PAException;
    
    /**
     * @param discrepancyType discrepancyType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyDataDiscrepancyId studyDataDiscrepancyId
     * @throws PAException PAException
     */
    void editStudyDataDiscrepancy(String discrepancyType, String actionTaken, String actionCompletionDate ,
            long studyDataDiscrepancyId) throws PAException;
    
    /**
     * @param id id
     * @param type type
     * @throws PAException PAException
     */
    void deleteStudyNotes(Long id , String type) throws PAException;
    
   
    /**
     * @param changeType changeType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyProtocolId studyProtocolId
     * @throws PAException PAException
     */
    void addStudyRecordChange(String changeType, String actionTaken, String actionCompletionDate ,
            long studyProtocolId) throws PAException;
    
    /**
     * @param changeType changeType
     * @param actionTaken actionTaken
     * @param actionCompletionDate actionCompletionDate
     * @param studyRecordChangeId studyRecordChangeId
     * @throws PAException PAException
     */ 
    void editStudyRecordChange(String changeType, String actionTaken, String actionCompletionDate ,
            long studyRecordChangeId) throws PAException;
   
}
