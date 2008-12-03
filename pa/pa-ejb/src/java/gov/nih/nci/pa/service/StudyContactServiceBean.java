package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Bala nair
 * @since 10/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyContactServiceBean
        extends AbstractStudyPaService<StudyContactDTO>  
        implements StudyContactServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyContactServiceBean.class);
    
    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param dto StudyContactDTO
     * @return StudyContactDTO
     * @throws PAException PAException
     */
    public StudyContactDTO create(
            StudyContactDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        if (PAUtil.isIiNull(dto.getClinicalResearchStaffIi())) {
            serviceError("Clinical Reseasrh Staff  must be set.  ");
        }

        StudyContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyContact bo = StudyContactConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyContact ", hbe);
        }
        return resultDto;
    }
    
    /**
     * @param dto StudyContactDTO
     * @return StudyContactDTO
     * @throws PAException PAException
     */
    public StudyContactDTO update(
        StudyContactDTO dto) throws PAException {
      if (PAUtil.isIiNull(dto.getIdentifier())) {
        serviceError("Create method should be used to modify existing.  ");
      }
      StudyContactDTO resultDto = null;
      Session session = null;
      try {
        session = HibernateUtil.getCurrentSession();
        
        StudyContact sc = (StudyContact) session.load(StudyContact.class,
            Long.valueOf(dto.getIdentifier().getExtension()));         

        StudyContact bo = StudyContactConverter.convertFromDtoToDomain(dto);
        sc = bo;
        session.merge(sc);
        session.flush();
        resultDto = StudyContactConverter.convertFromDomainToDTO(bo);
      } catch (HibernateException hbe) {
        serviceError(" Hibernate exception in updateStudyContact ", hbe);
      }
      return resultDto;
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyContactDTO   
     * @throws PAException on error 
     */
    public List<StudyContactDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getByStudyProtocol");
        Session session = null;
        List<StudyContact> queryList = new ArrayList<StudyContact>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select scontact "
                       + "from StudyContact scontact "
                       + "join scontact.studyProtocol spro "
                       + "where spro.id = :studyProtocolId " 
                       + " order by scontact.id ";
            LOG.info(" query StudyContact = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyContact for pid = " + studyProtocolIi.getExtension() , hbe);
        }
        
        List<StudyContactDTO> resultList = new ArrayList<StudyContactDTO>();
        for (StudyContact sc : queryList) {
            resultList.add(StudyContactConverter.convertFromDomainToDTO(sc));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }

    /**
     * Get list of StudyParticipations for a given protocol having
     * a given functional code.
     * @param studyProtocolIi id of protocol
     * @param scDTO StudyContactDTO with the functional code criteria
     * @return list StudyContactDTO
     * @throws PAException on error
     */
    public List<StudyContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyContactDTO scDTO) throws PAException {
        List <StudyContactDTO> scDtoList = new ArrayList<StudyContactDTO>();
        scDtoList.add(scDTO);
        return getByStudyProtocol(studyProtocolIi, scDtoList);
    }

    /**
     * Get list of StudyParticipations for a given protocol having
     * functional codes from a list.
     * @param studyProtocolIi id of protocol
     * @param scDTOList List containing desired functional codes
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public List<StudyContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyContactDTO> scDTOList) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError("Ii is null ");
        }
        if ((scDTOList == null) || (scDTOList.isEmpty())) {
            getLogger().info("Using method getByStudyProtocol(Ii).  ");
            return getByStudyProtocol(studyProtocolIi);
        }
        getLogger().info("Entering getByStudyProtocol(Ii, List<DTO>).  ");
        StringBuffer criteria = new StringBuffer();
        Session session = null;
        List<StudyContact> queryList = new ArrayList<StudyContact>();
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer("select spart "
                               + "from StudyContact spart "
                               + "join spart.studyProtocol spro "
                               + "where spro.id = :studyProtocolId ");
            boolean first = true;
            for (StudyContactDTO crit : scDTOList) {
                if (first) {
                    hql.append("and ( ");
                    first = false;
                } else {
                    criteria.append("or ");
                }
                criteria.append("spart.roleCode = '"
                    + StudyContactRoleCode.getByCode(crit.getRoleCode().getCode()) + "' ");
            }
            hql.append(criteria);
            hql.append(") order by spart.id ");
            getLogger().info(" query StudyContact = " + hql);

            Query query = session.createQuery(hql.toString());
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyContactDTO> resultList = new ArrayList<StudyContactDTO>();
        for (StudyContact sc : queryList) {
            resultList.add(StudyContactConverter.convertFromDomainToDTO(sc));
        }
        getLogger().info("Leaving getByStudyProtocol() for (" + criteria + ").  ");
        getLogger().info("Returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
    /**
     * @param ii Index of StudyContact object
     * @throws PAException PAException
     */
    @Override
    public void delete(Ii ii)
            throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Ii has null value ");
        }
        LOG.info("Entering delete().");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyContact bo = (StudyContact) session.get(StudyContact.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyContact for pid = " + ii.getExtension(), hbe);
        }
        LOG.info("Leaving delete().");
    } 
}
