/**
 * 
 */
package gov.nih.nci.pa.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.StudyRelationship;
import gov.nih.nci.pa.iso.convert.StudyRelationshipConverter;
import gov.nih.nci.pa.iso.dto.StudyRelationshipDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", "PMD.ExcessiveMethodLength",
    "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity", "PMD.TooManyMethods" })

 public class StudyRelationshipBeanLocal extends 
  AbstractBaseIsoService <StudyRelationshipDTO, StudyRelationship, StudyRelationshipConverter>
  implements StudyRelationshipServiceLocal {
  
   private static final Logger LOG  = Logger.getLogger(StudyRelationshipBeanLocal.class);
  
   /**
    * {@inheritDoc}
    */
     @TransactionAttribute(TransactionAttributeType.SUPPORTS)
     public List<StudyRelationshipDTO> search(StudyRelationshipDTO dto, LimitOffset pagingParams) throws PAException,
       TooManyResultsException {
       if (dto == null) {
          throw new PAException(" StudyRelationshipDTO should not be null ");
       }
       LOG.debug("Entering search");
       Session session = null;
       List <StudyRelationship> studyRelationshipList = null;
       session = HibernateUtil.getCurrentSession();
       StudyRelationship exampleDO = new StudyRelationship();
       Criteria criteria = session.createCriteria(StudyRelationship.class);
       if (!PAUtil.isIiNull(dto.getIdentifier())) {
        exampleDO.setId(IiConverter.convertToLong(dto.getIdentifier()));
        Example example = Example.create(exampleDO);
        criteria.add(example);
      }
      if (!PAUtil.isIiNull(dto.getSourceStudyProtocolIdentifier())) {
        criteria.createAlias("sourceStudyProtocol", "sp")
          .add(Expression.eq("sp.id", IiConverter.convertToLong(dto.getSourceStudyProtocolIdentifier())));

      }
      if (!PAUtil.isIiNull(dto.getTargetStudyProtocolIdentifier())) {
        criteria.createAlias("targetStudyProtocol", "sp2")
          .add(Expression.eq("sp2.id", IiConverter.convertToLong(dto.getTargetStudyProtocolIdentifier())));
      }
      int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
      criteria.setMaxResults(maxLimit);
      criteria.setFirstResult(pagingParams.getOffset());
      studyRelationshipList = criteria.list();
      if (studyRelationshipList.size() > PAConstants.MAX_SEARCH_RESULTS) {
       throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
      }
      List<StudyRelationshipDTO> studyRelationshipDTOList = convertFromDomainToDTO(studyRelationshipList);
      LOG.debug("Leaving search");
      return studyRelationshipDTOList;
     }

     private List<StudyRelationshipDTO> convertFromDomainToDTO(List<StudyRelationship> studyProtocolList) {
      List<StudyRelationshipDTO> studyRelationshipDTOList = new ArrayList<StudyRelationshipDTO>();
      StudyRelationshipConverter studyRelationshipConverter = new StudyRelationshipConverter();
      if (studyProtocolList != null) {
       studyRelationshipDTOList = new ArrayList<StudyRelationshipDTO>();
         for (StudyRelationship sp : studyProtocolList) {
           studyRelationshipDTOList.add(studyRelationshipConverter.convertFromDomainToDto(sp));
         }
      }
      return studyRelationshipDTOList;
    }

}
