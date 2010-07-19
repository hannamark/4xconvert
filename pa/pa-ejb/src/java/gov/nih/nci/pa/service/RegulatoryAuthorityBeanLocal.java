/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.RegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * @author asharma
 *
 */
public class RegulatoryAuthorityBeanLocal
extends AbstractBaseIsoService<RegulatoryAuthorityDTO, RegulatoryAuthority, RegulatoryAuthorityConverter>
implements RegulatoryAuthorityServiceLocal {

  private static final Logger LOG  = Logger.getLogger(RegulatoryAuthorityBeanLocal.class);
  private static String errMsgMethodNotImplemented = "Method not yet implemented.";



 /**
  * @param dto RegulatoryAuthorityDTO
  * @return RegulatoryAuthorityDTO
  * @throws PAException PAException
  */
  @Override
  public RegulatoryAuthorityDTO update(RegulatoryAuthorityDTO dto) throws PAException {
   LOG.error(errMsgMethodNotImplemented);
   throw new PAException(errMsgMethodNotImplemented);
  }


 /**
  * @param dto RegulatoryAuthorityDTO
  * @return RegulatoryAuthorityDTO
  * @throws PAException PAException
  */
  @Override
  public RegulatoryAuthorityDTO create(RegulatoryAuthorityDTO dto) throws PAException {
   LOG.error(errMsgMethodNotImplemented);
   throw new PAException(errMsgMethodNotImplemented);
 }

 /**
  * @param ii Ii
  * @throws PAException PAException
  */
 @Override
 public void delete(Ii ii) throws PAException {
  LOG.error(errMsgMethodNotImplemented);
  throw new PAException(errMsgMethodNotImplemented);
 }
 /**
  *
  * @param dto dto
  * @param pagingParams parms
  * @return list
  * @throws PAException on error
  * @throws TooManyResultsException on error
  */
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<RegulatoryAuthorityDTO> search(RegulatoryAuthorityDTO dto, LimitOffset pagingParams)
      throws PAException, TooManyResultsException {
     List<RegulatoryAuthorityDTO> returnList = new ArrayList<RegulatoryAuthorityDTO>();
     if (dto == null) {
         LOG.error(" RegulatoryAuthorityDTO should not be null ");
         throw new PAException(" RegulatoryAuthorityDTO should not be null ");
     }
     LOG.debug("Entering search");
     Session session = null;
     session = HibernateUtil.getCurrentSession();
     Criteria criteria = session.createCriteria(RegulatoryAuthority.class, "regAuth");
     if (!PAUtil.isStNull(dto.getAuthorityName())) {
         criteria.add(Restrictions.ilike("regAuth.authorityName", StConverter.convertToString(
                 dto.getAuthorityName()) + "%"));
     }
     if (PAUtil.isIiNotNull(dto.getCountryIdentifier())) {
         criteria.add(Restrictions.eq("regAuth.country.id", IiConverter.convertToLong(dto.getCountryIdentifier())));
     }
     if (PAUtil.isIiNotNull(dto.getIdentifier())) {
         criteria.add(Restrictions.eq("regAuth.id", IiConverter.convertToLong(dto.getIdentifier())));
     }
     int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
     criteria.setMaxResults(maxLimit);
     criteria.setFirstResult(pagingParams.getOffset());
     List<RegulatoryAuthority> regulatoryList = new ArrayList<RegulatoryAuthority>();
     LOG.debug("Search criteria" + criteria.toString());
     regulatoryList = criteria.list();
     if (regulatoryList.size() > PAConstants.MAX_SEARCH_RESULTS) {
         throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
     }
     returnList = convertFromDomainToDTOs(regulatoryList);
     LOG.debug("Leaving search");

     return returnList;
 }
 /**
  * gets the Id.
  *
  * @param authorityName
  *            orgName
  * @param countryName
  *            country Id
  * @return ii
  * @throws PAException
  *             e
  */
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public Ii getRegulatoryAuthorityId(St authorityName,
         St countryName) throws PAException {
     LOG.debug("Entering  getRegulatoryAuthorityId");
     String stAuthorityName = StConverter.convertToString(authorityName);
     String stCountryName = StConverter.convertToString(countryName);
     Session session = null;
     Long retRegAuthId = null;
     session = HibernateUtil.getCurrentSession();
     Query query = session.createQuery("select id from RegulatoryAuthority "
             + "as ra where ra.authorityName = :authorityName  and ra.country.name=:countryName");
     query.setParameter("authorityName", stAuthorityName);
     query.setParameter("countryName", stCountryName);
     query.setMaxResults(1);
     List results = query.list();
     if (results != null && !results.isEmpty()) {
       retRegAuthId = (Long) results.get(0);
     }
     LOG.debug("Leaving  getRegulatoryAuthorityId" + retRegAuthId);
     return IiConverter.convertToIi(retRegAuthId);
 }
}
