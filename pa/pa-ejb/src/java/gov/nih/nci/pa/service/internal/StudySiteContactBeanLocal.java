/**
 * 
 */
package gov.nih.nci.pa.service.internal;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteContactConverter;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.AbstractRoleIsoService;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
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
@SuppressWarnings({"PMD.ExcessiveMethodLength" , "PMD.CyclomaticComplexity" })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class StudySiteContactBeanLocal extends
 AbstractRoleIsoService<StudySiteContactDTO, StudySiteContact, StudySiteContactConverter> 
 implements StudySiteContactServiceLocal {
 
  private static final Logger LOG = Logger.getLogger(StudySiteContactBeanLocal.class);

 /**
  * @return log4j Logger
  */
 @Override
 protected Logger getLogger() {
     return LOG;
 }


 /**
  * @param studySiteIi id of protocol
  * @return list StudySiteContactDTO
  * @throws PAException on error
  */
 @SuppressWarnings("unchecked")
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<StudySiteContactDTO> getByStudySite(Ii studySiteIi) throws PAException {
     if ((studySiteIi == null) || PAUtil.isIiNull(studySiteIi)) {
         throw new PAException(" Ii should not be null ");
     }
     LOG.info("Entering getByStudySite");
     Session session = null;
     List<StudySiteContact> queryList = new ArrayList<StudySiteContact>();
     session = HibernateUtil.getCurrentSession();
     Query query = null;
     // step 1: form the hql
     String hql = "select spartcontact from StudySiteContact spartcontact "
         + "join spartcontact.studySite spart where spart.id = :studyPartId "
         + "order by spartcontact.id ";
     LOG.info(" query StudySiteContact = " + hql);
     // step 2: construct query object
     query = session.createQuery(hql);
     query.setParameter("studyPartId", IiConverter.convertToLong(studySiteIi));
     queryList = query.list();
     List<StudySiteContactDTO> resultList = new ArrayList<StudySiteContactDTO>();
     for (StudySiteContact sp : queryList) {
         resultList.add(Converters.get(StudySiteContactConverter.class).convertFromDomainToDto(sp));
     }
     LOG.info("Leaving getByStudySite");
     return resultList;
 }




}
