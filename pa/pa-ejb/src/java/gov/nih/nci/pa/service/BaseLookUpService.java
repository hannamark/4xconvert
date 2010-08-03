/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.AbstractLookUpEntity;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

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
          Session session = null;
          session = HibernateUtil.getCurrentSession();
          Example example = Example.create(bo).enableLike(MatchMode.ANYWHERE).ignoreCase();
          Criteria criteria = session.createCriteria(getTypeArgument()).add(example);
          return criteria.list();
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
          Session session = null;
          session = HibernateUtil.getCurrentSession();
          return (BO) session.get(getTypeArgument(), boId);
      }
      /**
       * Gets By Code.
       * 
       * @param bo the bo
       * 
       * @return the selected
       * 
       * @throws PAException the PA exception
       */
      @SuppressWarnings(UNCHECKED)
      public BO getByCode(BO bo) throws PAException {
          Session session = null;
          List<BO> bos = new ArrayList<BO>();
          session = HibernateUtil.getCurrentSession();
          Example example = Example.create(bo);
          Criteria criteria = session.createCriteria(getTypeArgument()).add(example);
          bos = criteria.list();
          return bos.get(0);
      }
      
     /**
     * @return the typeArgument
     */
     public Class<BO> getTypeArgument() {
       return typeArgument;
     }
}
