package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.ReportingDataSetMethodCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.dto.NCISpecificInfoDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.apache.log4j.Logger;
import java.util.Iterator;
/**
 * 
 * @author gnaveh
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.UnusedLocalVariable" })
public class NCISpecificInfoDAO {

      private static final Logger LOG  = Logger.getLogger(NCISpecificInfoDAO.class);
      private static final int THREE = 3;
     
   /**
    *
    * @param studyProtocolId for ID            
    * @return List queryList
    * @throws PAException paException
    */
     public NCISpecificInfoDTO getNCISpecificInfo(Long studyProtocolId) throws PAException {
       LOG.debug("Entering getNCISpecificInfo ");
       Session session = null;
       Query query = null;
       try {
           session = HibernateUtil.getCurrentSession();
           String hql = checkNCISpecificInfo(studyProtocolId);
           query = session.createQuery(hql);
        } catch (HibernateException hbe) {
           //LOG.error(" Hibernate exception in getNCISpecificInfo ", hbe);
           throw new PAException(" Hibernate exception in getNCISpecificInfo ", hbe);
       }
       LOG.debug("Leaving queryNCI Specific Info ");
       return convertToTrialDesignDTO(query);           
   }

     /**
     *
     * @param nciSpecificInformationData for abstracted data            
     * @return List queryList
     * @throws PAException paException
     */
     @SuppressWarnings("PMD.CompareObjectsWithEquals")
    public NCISpecificInfoDTO updateNCISpecificInfo(NCISpecificInformationData nciSpecificInformationData) 
                                                                                                   throws PAException {
        LOG.debug("Entering updateNCISpecificInfo ");
        Session session = null;
        //StudyProtocol studyProtocolAdd = new StudyProtocol();
        NCISpecificInfoDTO nciSpecificInfoDto = null; 
        try {
          session = HibernateUtil.getCurrentSession();
          Transaction transaction = session.beginTransaction();         
          StudyProtocol studyProtocol = (StudyProtocol) session.load(
                    StudyProtocol.class, Long.parseLong(nciSpecificInformationData.getStudyProtocolID()));
          studyProtocol.setId(Long.parseLong(nciSpecificInformationData.getStudyProtocolID()));
          studyProtocol.setMonitorCode(MonitorCode.getByCode(nciSpecificInformationData.getMonitorCode()));
          studyProtocol.setReportingDataSetMethodCode(ReportingDataSetMethodCode.getByCode(
                nciSpecificInformationData.getReportingDataSetMethodCode()));
          studyProtocol.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.getByCode(
                nciSpecificInformationData.getSummaryFourFundingCategoryCode()));          
          session.update(studyProtocol);
          transaction.commit();             
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in  updateNCISpecificInfo ", hbe);
            throw new PAException(" Hibernate exception in updateNCISpecificInfo ", hbe);
        }
        LOG.debug("Leaving updateNCISpecificInfo ");
        return nciSpecificInfoDto;              
    }     
 
   /**
    * generate HQL query for search NCI Specific Info.
    *
    * @param studyProtocolID 
    * @return hql
    * @throws PAException paException
    */

   @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
   private String checkNCISpecificInfo(Long studyProtocolId) throws PAException {
       StringBuffer hql = new StringBuffer();
       try {   
    
           hql.append(" select sp.id, sp.monitorCode, sp.reportingDataSetMethodCode, sp.summaryFourFundingCategoryCode"
                    + " from StudyProtocol as sp  " 
                    + " where sp.id =" + studyProtocolId);                                               
       } catch (Exception e) {
           LOG.error("General error in while checkNCISpecificInfo ", e);
           throw new PAException("General error in while checkNCISpecificInfo ", e);
       } finally {
           LOG.debug("Leaving checkNCISpecificInfo ");
       }
       return hql.toString();
   }
   
   /**
   *
   * @param trialDesignResult
   * @return TrialDesignDTO
   * @throws PAException paException
   */

  private NCISpecificInfoDTO convertToTrialDesignDTO(Query query) throws PAException {
      LOG.debug("Entering convert To TrialDesignDTO ");
      NCISpecificInfoDTO nciSpecificInfoDto = null;     
      MonitorCode monitorCode = null;
      ReportingDataSetMethodCode reportingDataSetMethodCode = null;
      SummaryFourFundingCategoryCode summaryFourFundingCategoryCode = null;
      try {  
           for (Iterator it = query.iterate(); it.hasNext();) {
              nciSpecificInfoDto = new NCISpecificInfoDTO();
              Object[] row = (Object[]) it.next();             
              if (row[0] != null) {
                  nciSpecificInfoDto.setStudyProtocolID(Long.parseLong(row[0].toString()));
              }
              if (row[1] != null) {
                  monitorCode = (MonitorCode) (row[1]);
                  nciSpecificInfoDto.setMonitorCode(monitorCode);
              }
              if (row[2] != null) {
                  reportingDataSetMethodCode = (ReportingDataSetMethodCode) (row[2]);
                  nciSpecificInfoDto.setReportingDataSetMethodCode(reportingDataSetMethodCode);
              }
              if (row[THREE] != null) {
                  summaryFourFundingCategoryCode = (SummaryFourFundingCategoryCode) (row[THREE]);
                  nciSpecificInfoDto.setSummaryFourFundingCategoryCode(summaryFourFundingCategoryCode);
              }              
            }          
      } catch (Exception e) {
          LOG.error("General error in while converting to DTO", e);
          throw new PAException("General error in while converting to DTO:" + e, e);
      } finally {
          LOG.debug("Leaving convert nciSpecificInfoDAO To nciSpecificInfoDto ");
      }
      return nciSpecificInfoDto;
  }

}
