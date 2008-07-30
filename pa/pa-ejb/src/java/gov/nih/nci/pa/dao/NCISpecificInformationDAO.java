package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.ReportingDataSetMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.dto.NCISpecificInformationDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * 
 * @author gnaveh
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.UnusedLocalVariable" })
public class NCISpecificInformationDAO {

      private static final Logger LOG  = Logger.getLogger(NCISpecificInformationDAO.class);
      private static final int THREE = 3;
     
   /**
    *
    * @param studyProtocolId for ID            
    * @return List queryList
    * @throws PAException paException
    */
     public NCISpecificInformationDTO getNCISpecificInformation(Long studyProtocolId) throws PAException {
       LOG.debug("Entering getNCISpecificInformation ");
       Session session = null;
       Query query = null;
       try {
           session = HibernateUtil.getCurrentSession();
           String hql = checkNCISpecificInformation(studyProtocolId);
           query = session.createQuery(hql);
        } catch (HibernateException hbe) {
           //LOG.error(" Hibernate exception in getNCISpecificInformation ", hbe);
           throw new PAException(" Hibernate exception in getNCISpecificInformation ", hbe);
       }
       LOG.debug("Leaving getNCISpecificInformation ");
       return convertToTrialDesignDTO(query);           
   }

     /**
     *
     * @param nciSpecificInformationData for abstracted data            
     * @return List queryList
     * @throws PAException paException
     */
     @SuppressWarnings("PMD.CompareObjectsWithEquals")
     public NCISpecificInformationDTO updateNCISpecificInformation(
                     NCISpecificInformationData nciSpecificInformationData)throws PAException {
        LOG.debug("Entering updateNCISpecificInformation ");        
        NCISpecificInformationDTO nciSpecificInformationDto = null;
        Long studyProtocolId = Long.parseLong(nciSpecificInformationData.getStudyProtocolID());
        StudyProtocol studyProtocol = new StudyProtocol();
        List<StudyProtocol> queryList = new ArrayList<StudyProtocol>();
        Query query = null;
        try { 
          if (studyProtocolId > 0) {
              Session session = HibernateUtil.getCurrentSession();
              Transaction transaction = session.beginTransaction();
/*           
              studyProtocol = (StudyProtocol) session.load(StudyProtocol.class, studyProtocolId);             
               //studyProtocol.setId(Long.parseLong(nciSpecificInformationData.getStudyProtocolID()));
*/
              String hql = "select sp from StudyProtocol as sp where sp.id =" + studyProtocolId;
              query = session.createQuery(hql);
              queryList = query.list();
              studyProtocol = (StudyProtocol) queryList.get(0);
  
              studyProtocol.setMonitorCode(MonitorCode.getByCode(
                nciSpecificInformationData.getMonitorCode()));
              studyProtocol.setReportingDataSetMethodCode(ReportingDataSetMethodCode.getByCode(
                  nciSpecificInformationData.getReportingDataSetMethodCode()));
              studyProtocol.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.getByCode(
                  nciSpecificInformationData.getSummaryFourFundingCategoryCode()));          
              session.saveOrUpdate(studyProtocol);
              transaction.commit();
/*                                  
              nciSpecificInformationDto.setStudyProtocolID(studyProtocolId);
              nciSpecificInformationDto.setMonitorCode(
                  studyProtocol.getMonitorCode());
              nciSpecificInformationDto.setReportingDataSetMethodCode(
                  studyProtocol.getReportingDataSetMethodCode());
              nciSpecificInformationDto.setSummaryFourFundingCategoryCode(
                  studyProtocol.getSummaryFourFundingCategoryCode());
*/
          } else {
             LOG.info("nciSpecificInformationData.getStudyProtocolID is null");
          }
                          
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in  updateNCISpecificInformation ", hbe);
            throw new PAException("Hibernate exception in updateNCISpecificInformation ", hbe);
        } catch (Exception ex) {
            LOG.error("Other Exception " , ex);
            throw new PAException("Other exception in updateNCISpecificInformation ", ex);
        }
        LOG.debug("Leaving updateNCISpecificInformation ");
        return nciSpecificInformationDto;              
    }     
 
   /**
    * generate HQL query for search NCI Specific Info.
    *
    * @param studyProtocolID 
    * @return hql
    * @throws PAException paException
    */

   @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
   private String checkNCISpecificInformation(Long studyProtocolId) throws PAException {
       LOG.debug("Starting checkNCISpecificInformation ");
       StringBuffer hql = new StringBuffer();
       try {   
    
           hql.append(" select sp.id, sp.monitorCode, sp.reportingDataSetMethodCode, sp.summaryFourFundingCategoryCode"
                    + " from StudyProtocol as sp  " 
                    + " where sp.id =" + studyProtocolId);                                               
       } catch (Exception e) {
           LOG.error("General error in while checkNCISpecificInformation ", e);
           throw new PAException("General error in while checkNCISpecificInformation ", e);
       } finally {
           LOG.debug("Leaving checkNCISpecificInformation ");
       }
       return hql.toString();
   }
   
   /**
   *
   * @param trialDesignResult
   * @return TrialDesignDTO
   * @throws PAException paException
   */

  private NCISpecificInformationDTO convertToTrialDesignDTO(Query query) throws PAException {
      LOG.debug("Entering convert To TrialDesignDTO ");
      NCISpecificInformationDTO nciSpecificInformationDto = null;     
      MonitorCode monitorCode = null;
      ReportingDataSetMethodCode reportingDataSetMethodCode = null;
      SummaryFourFundingCategoryCode summaryFourFundingCategoryCode = null;
      try {  
           for (Iterator it = query.iterate(); it.hasNext();) {
              nciSpecificInformationDto = new NCISpecificInformationDTO();
              Object[] row = (Object[]) it.next();             
              if (row[0] != null) {
                 nciSpecificInformationDto.setStudyProtocolID(Long.parseLong(row[0].toString()));
              }
              if (row[1] != null) {
                  monitorCode = (MonitorCode) (row[1]);
                  nciSpecificInformationDto.setMonitorCode(monitorCode);
              }
              if (row[2] != null) {
                  reportingDataSetMethodCode = (ReportingDataSetMethodCode) (row[2]);
                  nciSpecificInformationDto.setReportingDataSetMethodCode(reportingDataSetMethodCode);
              }
              if (row[THREE] != null) {
                  summaryFourFundingCategoryCode = (SummaryFourFundingCategoryCode) (row[THREE]);
                  nciSpecificInformationDto.setSummaryFourFundingCategoryCode(summaryFourFundingCategoryCode);
              }              
            }          
      } catch (Exception e) {
          LOG.error("General error in while converting to DTO", e);
          throw new PAException("General error in while converting to DTO:" + e, e);
      } finally {
          LOG.debug("Leaving convert nciSpecificInformationDAO To nciSpecificInformationDto ");
      }
      return nciSpecificInformationDto;
  }

}
