package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyDataDiscrepancy;
import gov.nih.nci.pa.domain.StudyNotes;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecordChange;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Apratim K
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)

public class StudyNotesServiceLocal implements StudyNotesService {

    @Override
    public List<? extends StudyNotes> getStudyNotesList(
            Long studyProtocolId , Class object) throws PAException {
        
        List<? extends StudyNotes>  studyNotesList = null;
        Criteria criteria = null;
        Session session = PaHibernateUtil.getCurrentSession();
        criteria = session.createCriteria(object);
        
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(studyProtocolId);
        criteria.add(Restrictions.eq("studyProtocol", studyProtocol));
        studyNotesList = criteria.list();
        
        return studyNotesList;
    }

    @Override
    public void addStudyDataDiscrepancy(String discrepancyType,
            String actionTaken, String actionCompletionDate , long studyProtocolId) throws PAException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
            parsedDate = simpleDateFormat.parse(actionCompletionDate);
       
        Session session = PaHibernateUtil.getCurrentSession();
        StudyDataDiscrepancy studyDataDiscrepancy = new StudyDataDiscrepancy();
        studyDataDiscrepancy.setDiscrepancyType(discrepancyType);
        studyDataDiscrepancy.setActionTaken(actionTaken);
        studyDataDiscrepancy.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(studyProtocolId);
        studyDataDiscrepancy.setStudyProtocol(studyProtocol);
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                UsernameHolder.getUser()); 
        studyDataDiscrepancy.setDateLastCreated(today);
        studyDataDiscrepancy.setUserLastCreated(user);
        
        session.save(studyDataDiscrepancy);
        
        } catch (Exception e) {
          throw new PAException(e.getMessage());
        }
    }

    @Override
    public void editStudyDataDiscrepancy(String discrepancyType,
            String actionTaken, String actionCompletionDate,
            long studyDataDiscrepancyId) throws PAException {
        
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                    UsernameHolder.getUser());     
        parsedDate = simpleDateFormat.parse(actionCompletionDate);
        Session session = PaHibernateUtil.getCurrentSession();
        StudyDataDiscrepancy studyDataDiscrepancy = (StudyDataDiscrepancy) 
                session.get(StudyDataDiscrepancy.class, studyDataDiscrepancyId);
        studyDataDiscrepancy.setDiscrepancyType(discrepancyType);
        studyDataDiscrepancy.setActionTaken(actionTaken);
        studyDataDiscrepancy.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        studyDataDiscrepancy.setDateLastUpdated(today);
        studyDataDiscrepancy.setUserLastUpdated(user);
        session.update(studyDataDiscrepancy);
        } catch (Exception e) {
            throw new PAException(e.getMessage());
          }
    }

    @Override
    public void deleteStudyNotes(Long id , String type) throws PAException {
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            StudyNotes studyNotes = null;
            String queryString = null;
            if (type.equals("disc")) {
                queryString = "delete from StudyDataDiscrepancy where id=" + id;
            } else {
                queryString = "delete from StudyRecordChange where id=" + id;
               
            }
            Query query = session.createQuery(queryString);
            query.executeUpdate();
            
         
        } catch (Exception e) {
            throw new PAException(e.getMessage());
          }
        
    }

   

    @Override
    public void addStudyRecordChange(String changeType, String actionTaken,
            String actionCompletionDate, long studyProtocolId)
            throws PAException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
            parsedDate = simpleDateFormat.parse(actionCompletionDate);
       
        Session session = PaHibernateUtil.getCurrentSession();
        StudyRecordChange studyRecordChange = new StudyRecordChange();
        studyRecordChange.setChangeType(changeType);
        studyRecordChange.setActionTaken(actionTaken);
        studyRecordChange.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(studyProtocolId);
        studyRecordChange.setStudyProtocol(studyProtocol);
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                UsernameHolder.getUser()); 
        studyRecordChange.setDateLastCreated(today);
        studyRecordChange.setUserLastCreated(user);
        
        session.save(studyRecordChange);
        
        } catch (Exception e) {
          throw new PAException(e.getMessage());
        }
        
    }

    @Override
    public void editStudyRecordChange(String changeType, String actionTaken,
            String actionCompletionDate, long studyRecordChangeId)
            throws PAException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PAUtil.DATE_FORMAT);
        Date parsedDate;
        try {
        
        Date today = new Date();
        User user = CSMUserService.getInstance().getCSMUserFromCache(
                    UsernameHolder.getUser());     
        parsedDate = simpleDateFormat.parse(actionCompletionDate);
        Session session = PaHibernateUtil.getCurrentSession();
        StudyRecordChange studyRecordChange = (StudyRecordChange)
                session.get(StudyRecordChange.class, studyRecordChangeId);
        studyRecordChange.setChangeType(changeType);
        studyRecordChange.setActionTaken(actionTaken);
        studyRecordChange.setActionCompletionDate(new Timestamp(parsedDate.getTime()));
        studyRecordChange.setDateLastUpdated(today);
        studyRecordChange.setUserLastUpdated(user);
        session.update(studyRecordChange);
        } catch (Exception e) {
            throw new PAException(e.getMessage());
          }
        
    }

}
