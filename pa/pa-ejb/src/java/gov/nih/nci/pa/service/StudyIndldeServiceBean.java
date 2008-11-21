package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.iso.convert.StudyIndldeConverter;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyIndldeServiceBean extends AbstractStudyPaService<StudyIndldeDTO>
implements StudyIndldeServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyIndldeServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param ii Index
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found ");
        }
        StudyIndldeDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyIndlde bo = (StudyIndlde) session.get(StudyIndlde.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyIndldeConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO create(
            StudyIndldeDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        StudyIndldeDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyIndlde bo = StudyIndldeConverter.convertFromDTOToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyIndldeConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyIndlde ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO update(StudyIndldeDTO dto)
            throws PAException {
        if (dto == null) {
            serviceError("StudyIndldeDTO should not be null ");
        }

        Session session = null;
        StudyIndldeDTO resultDto = null;
        List<StudyIndlde> queryList = new ArrayList<StudyIndlde>();

        try {
            session = HibernateUtil.getCurrentSession();
            String hql = "select si "
                       + "from StudyIndlde si "
                       + "where si.id =  " + Long.valueOf(dto.getIdentifier().getExtension());
            LOG.info(" query StudyIndlde = " + hql);

            Query query = session.createQuery(hql);
            queryList = query.list();

            StudyIndlde sp = queryList.get(0);
            StudyIndlde spNew = StudyIndldeConverter.convertFromDTOToDomain(dto);

            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            sp.setUserLastUpdated(spNew.getUserLastUpdated());
            sp.setExpandedAccessStatusCode(spNew.getExpandedAccessStatusCode());
            sp.setExpandedAccessIndicator(spNew.getExpandedAccessIndicator());
            sp.setGrantorCode(spNew.getGrantorCode());
            sp.setNihInstHolderCode(spNew.getNihInstHolderCode());
            sp.setNciDivProgHolderCode(spNew.getNciDivProgHolderCode());
            sp.setHolderTypeCode(spNew.getHolderTypeCode());
            sp.setIndldeNumber(spNew.getIndldeNumber());
            sp.setIndldeTypeCode(spNew.getIndldeTypeCode());
            session.update(sp);
            session.flush();
            resultDto =  StudyIndldeConverter.convertFromDomainToDTO(sp);
        }  catch (HibernateException hbe) {
            serviceError("Hibernate exception while updating StudyIndlde for id = "
                    + IiConverter.convertToString(dto.getIdentifier()) + ".  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii Index of StudyIndlde object
     * @throws PAException PAException
     */
    @Override
    public void delete(Ii ii)
            throws PAException {
        LOG.info("Entering delete");
        
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Ii has null value ");
        }
        LOG.info("Entering delete().");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyIndlde bo = (StudyIndlde) session.get(StudyIndlde.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyIndlde for pid = " + ii.getExtension(), hbe);
        }
        LOG.info("Leaving delete().");
    }


    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @Override
    public List<StudyIndldeDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getStudyIndldeByStudyProtocol");
        Session session = null;
        List<StudyIndlde> queryList = new ArrayList<StudyIndlde>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = "select s "
                       + "from StudyIndlde s "
                       + "join s.studyProtocol spro "
                       + "where spro.id = :studyProtocolId "
                       + " order by s.id ";
            
            LOG.info(" query StudyIndlde = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyIndlde for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyIndldeDTO> resultList = new ArrayList<StudyIndldeDTO>();
        for (StudyIndlde sp : queryList) {
            resultList.add(StudyIndldeConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }
}
