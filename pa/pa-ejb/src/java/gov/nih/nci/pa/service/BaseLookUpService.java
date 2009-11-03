/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;


 /**
 * @author asharma
 *
 * @param <BO>
 */
public class BaseLookUpService <BO extends AbstractLookUpEntity> {

    private static final String UNCHECKED = "unchecked";
    private final Class<BO> typeArgument;
    
    private static final Logger LOG = Logger.getLogger(BaseLookUpService.class);
   
    /**
     * 
     * @param typeArgument BO
     */
    public BaseLookUpService(Class<BO> typeArgument) {
        
        this.typeArgument = typeArgument;
    }
     
      /**
       * Search.
       * 
       * @param bo the bo
       * 
       * @return the list< b o>
       * 
       * @throws PAException the PA exception
       */
      @SuppressWarnings(UNCHECKED)
      public List<BO> search(BO bo) throws PAException {
          LOG.info("Entering search");
          Session session = null;
          List<BO> bos = new ArrayList<BO>();
          session = HibernateUtil.getCurrentSession();
          Example example = Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase();
          Criteria criteria = session.createCriteria(getTypeArgument()).add(example);
          bos = criteria.list();
          LOG.info("Leaving search");
          return bos;
      }
      
      /**
       * Gets By Id.
       * 
       * @param boId the bo id
       * 
       * @return the selected
       * 
       * @throws PAException the PA exception
       */
      @SuppressWarnings(UNCHECKED)
      public BO getById(Long boId) throws PAException {
          LOG.info("Entering getById ");
          Session session = null;
          session = HibernateUtil.getCurrentSession();
          BO bo = (BO) session.get(getTypeArgument(), boId);
          LOG.info("Leaving getById");
          return bo;
      }
      
     /**
     * @return the typeArgument
     */
     public Class<BO> getTypeArgument() {
       return typeArgument;
     }
}
