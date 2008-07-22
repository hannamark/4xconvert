package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.NCISpecificInformationData;
import gov.nih.nci.pa.enums.MonitorCode;
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
     
   /**
    *
    * @param protocolID for protocol ID            
    * @return List queryList
    * @throws PAException paException
    */
     public NCISpecificInfoDTO getNCISpecificInfo(String protocolID) throws PAException {
       LOG.debug("Entering getNCISpecificInfo ");
       Session session = null;
       Query query = null;
       try {
            session = HibernateUtil.getCurrentSession();
            String hql = checkNCISpecificInfo(protocolID);
            query = session.createQuery(hql);
       } catch (HibernateException hbe) {
           //LOG.error(" Hibernate exception in query protocol ", hbe);
           throw new PAException(" Hibernate exception in query protocol ", hbe);
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
    public NCISpecificInfoDTO updateNCISpecificData(NCISpecificInformationData nciSpecificInformationData) 
                                                                                                   throws PAException {
        LOG.debug("Entering getNCISpecificInfo ");
        Session session = null;
        StudyProtocol studyProtocol = new StudyProtocol();
        NCISpecificInfoDTO nciSpecificInfoDto = null; 
        try {
             session = HibernateUtil.getCurrentSession();
             Transaction transaction = session.beginTransaction();
             
             MonitorCode monitorCode = null;
             studyProtocol.setId(nciSpecificInformationData.getProtocolId());
             if (nciSpecificInformationData.getMonitorCode() != null) {
                 monitorCode = MonitorCode.getByCode(nciSpecificInformationData.getMonitorCode());
                 studyProtocol.setMonitorCode(monitorCode);
             }
            
             //session.save(studyProtocol);
             session.saveOrUpdate(studyProtocol);
             transaction.commit();
             //set the DTO object for return
             /*
             if (nciSpecificInfoData.getProtocolId() != null) {
                 nciSpecificInfoDto.setStudySiteID(nciSpecificInfoData.getProtocolId());
             }
             if (nciSpecificInfoData.getMonitorCode() != null) {
                nciSpecificInfoDto.setMonitorCode(nciSpecificInfoData.getMonitorCode());
             }
             */
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in query protocol ", hbe);
            throw new PAException(" Hibernate exception in query protocol ", hbe);
        }
        LOG.debug("Leaving updateNCISpecificInfo ");
        return nciSpecificInfoDto;              
    }     
     

   /**
    * generate HQL query for search NCI Specific Info.
    *
    * @param protocolID 
    * @return hql
    * @throws PAException paException
    */
   private String checkNCISpecificInfo(String protocolID) throws PAException {
       String hql = null;
       try {    
           hql = " select sp.id, sp.monitorCode "
                + " from StudyProtocol as sp  "  
               + generateWhereClause(protocolID);                    
       } catch (Exception e) {
           LOG.error("General error in while converting to DTO1", e);
           throw new PAException("General error in while converting to DTO1", e);
       } finally {
           LOG.debug("Leaving generateSafetyRegulationQuery ");
       }
       return hql;

   }
   
   /**
   * generate a where clause for a given ProtocolID.
   *
   * @param ProtocolID
   * @return String String
   * @throws PAException paException
   */
    private String generateWhereClause(String protocolID) throws PAException {
      LOG.debug("Entering generateWhereClause ");
      StringBuffer where = new StringBuffer();
      try {
          where.append("where sp.id = ").append(protocolID);            
      } catch (Exception e) {
          LOG.error("General error in while create where clause", e);
          throw new PAException("General error in while create where clause", e);
      } finally {
          LOG.debug("Leaving generateWhereClause ");
      }
      
      return where.toString();
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
      try {  
           for (Iterator it = query.iterate(); it.hasNext();) {
              nciSpecificInfoDto = new NCISpecificInfoDTO();
              Object[] row = (Object[]) it.next();             
              if (row[0] != null) {
                  nciSpecificInfoDto.setStudySiteID(Long.parseLong(row[0].toString()));
              }
              if (row[1] != null) {
                  monitorCode = (MonitorCode) (row[1]);
                  nciSpecificInfoDto.setMonitorCode(monitorCode);
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
