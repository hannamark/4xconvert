package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyDataDiscrepancy;
import gov.nih.nci.pa.domain.StudyNotes;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class StudyNotesServiceTest extends AbstractHibernateTestCase {

    private final StudyNotesServiceLocal notesService = new StudyNotesServiceLocal();
    
    @Before
    public void init() throws Exception {
        TestSchema.primeData();
        CSMUserService.setInstance(new MockCSMUserService());
      
    }
    
    @Test
    public void createDataDisc() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyDataDiscrepancy(
                "Missing something", "Added something", simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<? extends StudyNotes> studyDisList = notesService.getStudyNotesList(studyProtocolId, StudyDataDiscrepancy.class);
        assert(studyDisList.size() ==1);
       
    }
    
    @Test
    public void createDataDiscExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.addStudyDataDiscrepancy(
               null, null, simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg
                .contains("could not insert"));
        }
    }
    
    @Test
    public void createStudyChangeRecord() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<? extends StudyNotes> studyRercodsList= notesService.getStudyNotesList(studyProtocolId, StudyRecordChange.class);
        assert(studyRercodsList.size() ==1);
       
    }
    
    @Test
    public void createStudyChangeRecordExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.addStudyRecordChange(
               null, null, simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg
                .contains("could not insert"));
        }
    }
    
    @Test
    public void editDataDisc() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyDataDiscrepancy("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<? extends StudyNotes> studyDisList= notesService.getStudyNotesList(studyProtocolId,StudyDataDiscrepancy.class);
        
        assert(studyDisList.size() ==1);
        
        StudyDataDiscrepancy  studyDataDiscrepancy = (StudyDataDiscrepancy)studyDisList.get(0);
        
        notesService.editStudyDataDiscrepancy("Edit disc type", "edit action taken",
                simpleDateFormat.format(new Date()), studyDataDiscrepancy.getId());
        
        studyDisList= notesService.getStudyNotesList(studyProtocolId, StudyDataDiscrepancy.class);
        
        assert(studyDisList.size() ==1);
        studyDataDiscrepancy = new StudyDataDiscrepancy();
        studyDataDiscrepancy = (StudyDataDiscrepancy)studyDisList.get(0);
        
        assert(studyDataDiscrepancy.getDiscrepancyType().equals("Edit disc type"));
        assert(studyDataDiscrepancy.getActionTaken().equals("edit action taken")); 
        
    }
    
    @Test
    public void editDataDiscExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.editStudyDataDiscrepancy(
               "change", "change", simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg==null);
        }
    }
    
    @Test
    public void editStudyRecordChange() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        List<? extends StudyNotes> studyRercodsList= notesService.getStudyNotesList(studyProtocolId, StudyRecordChange.class);
        
        assert(studyRercodsList.size() ==1);
        
        StudyRecordChange studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        notesService.editStudyRecordChange("Edit disc type", "edit action taken",
                simpleDateFormat.format(new Date()), studyRecordChange.getId());
        
        studyRercodsList= notesService.getStudyNotesList(studyProtocolId, StudyRecordChange.class);
        
        assert(studyRercodsList.size() ==1);
        studyRecordChange = new StudyRecordChange();
        studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        assert(studyRecordChange.getChangeType().equals("Edit disc type"));
        assert(studyRecordChange.getActionTaken().equals("edit action taken")); 
        
    }
    
    @Test
    public void editStudyRecordChangeExceptionTest() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
       
        try {
        notesService.editStudyRecordChange(
               "change", "change", simpleDateFormat.format(new Date()), 0l);
        } catch(PAException e) {
        e.printStackTrace();
        String msg = e.getMessage();
        Assert.assertTrue(msg==null);
        }
    }
    
    
    @Test
    public void deleteDataDisc() throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyDataDiscrepancy(
                "Missing something", "Added something", simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<? extends StudyNotes> studyDisList = notesService.getStudyNotesList(studyProtocolId, StudyDataDiscrepancy.class); 
        assert(studyDisList.size() ==1);
        
        StudyDataDiscrepancy studyDataDiscrepancy = new StudyDataDiscrepancy();
        studyDataDiscrepancy = (StudyDataDiscrepancy)studyDisList.get(0);
        
        notesService.deleteStudyNotes(studyDataDiscrepancy.getId(), "disc");
        
        studyDisList = new ArrayList();
        
        studyDisList = notesService.getStudyNotesList(studyProtocolId, StudyDataDiscrepancy.class);
        
        assert(studyDisList.size() ==0);
        
       
    }
    
    @Test
    public void deleteStudyChangeRecord() throws Exception {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        long studyProtocolId = TestSchema.studyProtocolIds.get(0);
        notesService.addStudyRecordChange("Some change needed", "Added some change",
                simpleDateFormat.format(new Date()), studyProtocolId);
        
        List<? extends StudyNotes> studyRercodsList= notesService.getStudyNotesList(studyProtocolId, StudyRecordChange.class);
        assert(studyRercodsList.size() ==1);
        
        StudyRecordChange studyRecordChange = (StudyRecordChange)studyRercodsList.get(0);
        
        notesService.deleteStudyNotes(studyRecordChange.getId(), "studyrecord");
        
        studyRercodsList= notesService.getStudyNotesList(studyProtocolId, StudyRecordChange.class);
        
        assert(studyRercodsList.size() ==0);
        
    }
    
    @Test
    public void deleteDataDiscExceptionTest() throws Exception {
       try {
        notesService.deleteStudyNotes(0l, null);
       } catch (Exception e) {
           e.printStackTrace();
           String msg = e.getMessage();
           Assert.assertTrue(msg==null);
       }
       
    }
     
}
