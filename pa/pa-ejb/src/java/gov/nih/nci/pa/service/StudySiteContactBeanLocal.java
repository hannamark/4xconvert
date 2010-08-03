/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StructuralRole;
import gov.nih.nci.pa.domain.StudySiteContact;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.StudySiteContactConverter;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;

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
     return resultList;
 }
 /**
  *@param dto dto
  *@throws PAException e
  *@return dto
  */
 @Override
    public StudySiteContactDTO create(StudySiteContactDTO dto)
            throws PAException {
     validate(dto);   
     return super.create(dto);
    }
 /**
  * @param dto to update
  * @throws PAException on err
  * @return dto
  */
 @Override
    public StudySiteContactDTO update(StudySiteContactDTO dto)
            throws PAException {
        validate(dto);
        return super.update(dto);
    }
 /**
  * validates the dto.
  * @param dto dto to validate.
  * @throws PAException e
  */
 @Override
 @SuppressWarnings({"PMD" })
 public void validate(StudySiteContactDTO dto) throws PAException {
     PAServiceUtils paServiceUtil = new PAServiceUtils();
     if (!PAUtil.isIiNull(dto.getClinicalResearchStaffIi()) && !PAUtil.isDSetTelNull(dto.getTelecomAddresses())) {
         StructuralRole sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoClinicalResearchStaffIi(
                 dto.getClinicalResearchStaffIi().getExtension()));
         if (sr != null) {
             ClinicalResearchStaffDTO  poSrDto = (ClinicalResearchStaffDTO) paServiceUtil.getCorrelationByIi(
                     IiConverter.convertToPoClinicalResearchStaffIi(sr.getIdentifier()));
             if (paServiceUtil.isEntityCountryUSAOrCanada(poSrDto.getScoperIdentifier())
                     && !PAUtil.isPhoneValidForUSA(DSetConverter.convertDSetToList(
                             dto.getTelecomAddresses(), PAConstants.PHONE).get(0))) {
                 throw new PAException("Please enter phone in xxx-xxx-xxxx format for USA or CANADA");
             }    
         }

         
     }
     if (!PAUtil.isIiNull(dto.getOrganizationalContactIi()) && !PAUtil.isDSetTelNull(dto.getTelecomAddresses())) {
         StructuralRole sr = paServiceUtil.getStructuralRole(IiConverter.convertToPoOrganizationalContactIi(
                 dto.getOrganizationalContactIi().getExtension()));
         if (sr != null) {
             OrganizationalContactDTO  poSrDto = (OrganizationalContactDTO) paServiceUtil.getCorrelationByIi(
                     IiConverter.convertToPoOrganizationalContactIi(sr.getIdentifier()));
             if (paServiceUtil.isEntityCountryUSAOrCanada(poSrDto.getScoperIdentifier())
                     && !PAUtil.isPhoneValidForUSA(DSetConverter.convertDSetToList(
                             dto.getTelecomAddresses(), PAConstants.PHONE).get(0))) {
                 throw new PAException("Please enter phone in xxx-xxx-xxxx format for USA or CANADA");
             }
         }
     }
 }



}
