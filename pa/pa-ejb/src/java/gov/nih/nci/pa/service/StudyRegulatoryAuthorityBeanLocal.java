/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.domain.StudyRegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudyRegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class StudyRegulatoryAuthorityBeanLocal 
 extends AbstractCurrentStudyIsoService<StudyRegulatoryAuthorityDTO, StudyRegulatoryAuthority,
  StudyRegulatoryAuthorityConverter> implements StudyRegulatoryAuthorityServiceLocal {
 
   private static final Logger LOG = Logger.getLogger(StudyRegulatoryAuthorityBeanLocal.class);

 /**
 *
 * @param sraDTO as parameter
 * @return StudyRegulatoryAuthorityDTO as DTO
 * @throws PAException on exception
 */
 @SuppressWarnings("unchecked")
 @Override
 public StudyRegulatoryAuthorityDTO update(StudyRegulatoryAuthorityDTO sraDTO) throws PAException {
   StudyRegulatoryAuthorityDTO sraDTO1 = null;
   if (sraDTO == null) {
       LOG.error(" StudyRegulatoryAuthorityDTO should not be null ");
       throw new PAException(" StudyRegulatoryAuthorityDTO should not be null ");

   }
   Timestamp now = new Timestamp((new Date()).getTime());
   Session session = null;
   List<StudyRegulatoryAuthority> queryList = new ArrayList<StudyRegulatoryAuthority>();
   session = HibernateUtil.getCurrentSession();

   Query query = null;
   String hql = " select sra " + " from StudyRegulatoryAuthority sra " + " join sra.studyProtocol sp "
   + " where sp.id = " + IiConverter.convertToLong(sraDTO.getStudyProtocolIdentifier());
   LOG.info(" query StudyRegulatoryAuthority = " + hql);
   query = session.createQuery(hql);
   queryList = query.list();
   StudyRegulatoryAuthority sra = queryList.get(0);

   RegulatoryAuthority ra = new RegulatoryAuthority();
   ra.setId(IiConverter.convertToLong(sraDTO.getRegulatoryAuthorityIdentifier()));
   sra.setRegulatoryAuthority(ra);
   sra.setDateLastUpdated(now);
   sraDTO1 = super.update(
           Converters.get(StudyRegulatoryAuthorityConverter.class).convertFromDomainToDto(sra));
   return sraDTO1;
 }

}
