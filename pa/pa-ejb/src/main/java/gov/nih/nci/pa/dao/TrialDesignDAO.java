package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.dto.TrialDesignDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import java.util.Iterator;
/**
 * 
 * @author gnaveh
 *
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.UnusedLocalVariable" })
public class TrialDesignDAO {

      private static final Logger LOG  = Logger.getLogger(TrialDesignDAO.class);
   /**
    *
    * @param protocolID for protocol ID            
    * @return List queryList
    * @throws PAException paException
    */
     public TrialDesignDTO getTrialDesign(String protocolID) throws PAException {
       LOG.debug("Entering getTrialDesign ");
       Session session = null;
       Query query = null;
       try {
            session = HibernateUtil.getCurrentSession();
            String hql = generateTrialDesignQuery(protocolID);
            query = session.createQuery(hql);
       } catch (HibernateException hbe) {
           LOG.error(" Hibernate exception in query protocol ", hbe);
           throw new PAException(" Hibernate exception in query protocol ", hbe);
       }
       LOG.debug("Leaving queryTrialDesign ");
       return convertToTrialDesignDTO(query);
             
   }

   /**
    * generate HQL query for search TrialDesign.
    *
    * @param protocolID 
    * @return hql
    * @throws PAException paException
    */
   private String generateTrialDesignQuery(String protocolID) throws PAException {
       LOG.debug("Entering generateTrialDesignQuery ");
       String hql = null;
       try {
   
          hql = " select sp.id, sp.acronym, sp.officialTitle "
               + " from StudyProtocol sp " 
               + generateWhereClause(protocolID);                    
       } catch (Exception e) {
           LOG.error("General error in while converting to DTO", e);
           throw new PAException("General error in while converting to DTO", e);
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
          LOG.error("General error in while create where cluase", e);
          throw new PAException("General error in while create where cluase", e);
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

  private TrialDesignDTO convertToTrialDesignDTO(Query query) throws PAException {
      LOG.debug("Entering convert To TrialDesignDTO ");
      TrialDesignDTO trialDesignDto = null;     
      try {  
           for (Iterator it = query.iterate(); it.hasNext();) {
             trialDesignDto = new TrialDesignDTO();
              Object[] row = (Object[]) it.next();             
              trialDesignDto.setStudySiteID(Long.parseLong(row[0].toString()));
              trialDesignDto.setAcronym(row[1].toString());
              trialDesignDto.setOfficialTitle(row[2].toString());              
            }          
      } catch (Exception e) {
          LOG.error("General error in while converting to DTO", e);
          throw new PAException("General error in while converting to DTO2" + e, e);
      } finally {
          LOG.debug("Leaving convert safetyRegulationResults To safetyRegulationDto ");
      }
      return trialDesignDto;
  }

}
