/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.Disease;
import gov.nih.nci.pa.iso.convert.DiseaseConverter;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.NPathComplexity" })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DiseaseBeanLocal extends AbstractBaseIsoService<DiseaseDTO, Disease, DiseaseConverter>
implements DiseaseServiceLocal { 
  
  private static final Logger LOG = Logger.getLogger(DiseaseBeanLocal.class);
  private static final String TRUE = "true";
/**
 * @param searchCriteria search string
 * @return all diseases with preferred names or alternate names matching search string
 * @throws PAException exception
 */
 @SuppressWarnings("unchecked")
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<DiseaseDTO> search(DiseaseDTO searchCriteria) throws PAException {
    if (searchCriteria == null) {
        throw new PAException("Must pass in search criteria when calling search().");
    }
    if (searchCriteria.getPreferredName() == null) {
        throw new PAException("Must pass in a name when calling search().");
    }
    getLogger().info("Entering search().  ");
    List<Disease> queryList = new ArrayList<Disease>();
    Session session = HibernateUtil.getCurrentSession();
    StringBuffer hql = new StringBuffer();
    Query query = null;

    // step 1: form the hql
    if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)) {
        hql.append("select distinct dis "
                + "from Disease dis ");

    } else {
        hql.append("select distinct dis "
                + "from Disease dis " 
                + "left join dis.diseaseAlternames alt ");
    }
    hql.append(generateWhereClause(searchCriteria));   
    hql.append("order by dis.preferredName asc");
    getLogger().info("query Disease = " + hql.toString());

    // step 2: construct query object
    query = session.createQuery(hql.toString());

    // step 3: query the result
    queryList = query.list();
    ArrayList<DiseaseDTO> resultList = new ArrayList<DiseaseDTO>();
    for (Disease bo : queryList) {
        resultList.add(convertFromDomainToDto(bo));
    }
    getLogger().info("Leaving search(), returning " + resultList.size() + " object(s).");
    return resultList;
}

 /**
  * Generate where clause.
  * 
  * @param searchCriteria the search criteria
  * 
  * @return the string
  * 
  * @throws PAException the PA exception
  */
  @SuppressWarnings({"PMD" })
  private String generateWhereClause(DiseaseDTO searchCriteria)throws PAException {
    LOG.debug("Entering generateWhereClause ");
    StringBuffer where = new StringBuffer();
    try {
        where.append("where 1 = 1 ");
       // 
       if (PAUtil.isNotEmpty(StConverter.convertToString(searchCriteria.getPreferredName()))) {
           
           where.append(" and (dis.statusCode = 'ACTIVE')");  
                         
           //Case1:include synonym is checked and exact match is unchecked
           if (StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                 && !StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
              
              String term = PAUtil.wildcardCriteria(
                      StConverter.convertToString(searchCriteria.getPreferredName()))
                      .toUpperCase().trim().replaceAll("'", "''");
                where.append(" and (upper(dis.preferredName) like upper('%" + term + "%') " 
                + "or ((upper(alt.alternateName) like upper('%" + term + "%')) and (alt.statusCode = 'ACTIVE'))) ");
               
           }
           //Case2:include synonym is unchecked and exact match is checked
           if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                 && StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
               
               String exactString = stringToSearch(StConverter.convertToString(
                       searchCriteria.getPreferredName()).toUpperCase().trim().replaceAll("'", "''"));
               where.append(" and upper(dis.preferredName) like '" + exactString + "'");
                
           }
           //Case3:include synonym and exact match are both checked
           if (StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                 && StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
              

               String exactString = stringToSearch(StConverter.convertToString(
                       searchCriteria.getPreferredName()).toUpperCase().trim().replaceAll("'", "''"));
               
               where.append(" and (upper(dis.preferredName) like upper('" + exactString + "') "
                    + "or ((upper(alt.alternateName) like upper('" + exactString + "')) " 
                    + "and (alt.statusCode = 'ACTIVE'))) ");
                    
           } else if (!StConverter.convertToString(searchCriteria.getIncludeSynonym()).equals(TRUE)
                   && !StConverter.convertToString(searchCriteria.getExactMatch()).equals(TRUE)) {
               //Case4: both include Synonym and exact match are unchecked  
                   where.append(" and upper(dis.preferredName)  like '%" 
                           + PAUtil.wildcardCriteria(StConverter.
                              convertToString(searchCriteria.getPreferredName())
                               .toUpperCase().trim().replaceAll("'", "''"))
                           + "%'");
                   
            }
             
        }
  
    } catch (Exception e) {
        LOG.error("General error in while create where cluase", e);
        throw new PAException("General error in while create where cluase", e);
    } finally {
        LOG.debug("Leaving generateWhereClause ");
    }
    return where.toString();
 }  

 /**
  * String to search.
  * 
  * @param searchTerm the search term
  * 
  * @return the string
  */
  private String stringToSearch(String searchTerm) {
    String term = "";
    //checks if wildcard is present within the string not at extremities
    Pattern pat = Pattern.compile("^[^*].*\\**.*[^*]$");
    Matcher mat = pat.matcher(searchTerm);
    
    if (!searchTerm.contains("*")) {
        term = searchTerm;
    } 
    if (mat.find()) {
         term = PAUtil.wildcardCriteria(searchTerm);
      } else {
        term = searchTerm;
    } 
    
    return term;
 }

}
