package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.iso.convert.StudyOutcomeMeasureConverter;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
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
 * @author Kalpana Guthikonda
 * @since 10/30/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyOutcomeMeasureServiceBean extends AbstractStudyPaService<StudyOutcomeMeasureDTO>
implements StudyOutcomeMeasureServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyOutcomeMeasureServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param ii Index
     * @return StudyOutcomeMeasureDTO
     * @throws PAException PAException
     */
    @Override
    public StudyOutcomeMeasureDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found ");
        }
        StudyOutcomeMeasureDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyOutcomeMeasure bo = (StudyOutcomeMeasure) session.get(StudyOutcomeMeasure.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyOutcomeMeasureConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto StudyOutcomeMeasureDTO
     * @return StudyOutcomeMeasureDTO
     * @throws PAException PAException
     */
    @Override
    public StudyOutcomeMeasureDTO create(
            StudyOutcomeMeasureDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        StudyOutcomeMeasureDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyOutcomeMeasure bo = StudyOutcomeMeasureConverter.convertFromDTOToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyOutcomeMeasureConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyOutcomeMeasure ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudyOutcomeMeasureDTO
     * @return StudyOutcomeMeasureDTO
     * @throws PAException PAException
     */
    @Override
    public StudyOutcomeMeasureDTO update(StudyOutcomeMeasureDTO dto)
            throws PAException {
        if (dto == null) {
            serviceError("StudyOutcomeMeasureDTO should not be null ");
        }

        Session session = null;
        StudyOutcomeMeasureDTO resultDto = null;
        List<StudyOutcomeMeasure> queryList = new ArrayList<StudyOutcomeMeasure>();

        try {
            session = HibernateUtil.getCurrentSession();
            String hql = "select sp "
                       + "from StudyOutcomeMeasure sp "
                       + "where sp.id =  " + Long.valueOf(dto.getIdentifier().getExtension());
            LOG.info(" query StudyOutcomeMeasure = " + hql);

            Query query = session.createQuery(hql);
            queryList = query.list();

            StudyOutcomeMeasure sp = queryList.get(0);
            StudyOutcomeMeasure spNew = StudyOutcomeMeasureConverter.convertFromDTOToDomain(dto);

            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            sp.setUserLastUpdated(spNew.getUserLastUpdated());
            sp.setName(spNew.getName());
            sp.setTimeFrame(spNew.getTimeFrame());
            sp.setPrimaryIndicator(spNew.getPrimaryIndicator());
            sp.setSafetyIndicator(spNew.getSafetyIndicator());
            session.update(sp);
            session.flush();
            resultDto =  StudyOutcomeMeasureConverter.convertFromDomainToDTO(sp);
        }  catch (HibernateException hbe) {
            serviceError("Hibernate exception while updating StudyOutcomeMeasure for id = "
                    + IiConverter.convertToString(dto.getIdentifier()) + ".  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii Index of StudyOutcomeMeasure object
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
            StudyOutcomeMeasure bo = (StudyOutcomeMeasure) session.get(StudyOutcomeMeasure.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyOutcomeMeasure for pid = " + ii.getExtension(), hbe);
        }
        LOG.info("Leaving delete().");
    }

    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyOutcomeMeasureDTO
     * @throws PAException on error
     */
    @Override
    public List<StudyOutcomeMeasureDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getStudyOutcomeMeasureByStudyProtocol");
        Session session = null;
        List<StudyOutcomeMeasure> queryList = new ArrayList<StudyOutcomeMeasure>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = "select s "
                       + "from StudyOutcomeMeasure s "
                       + "join s.studyProtocol spro "
                       + "where spro.id = :studyProtocolId "
                       + " order by s.id ";
            
            LOG.info(" query StudyParticipation = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyOutcomeMeasure for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyOutcomeMeasureDTO> resultList = new ArrayList<StudyOutcomeMeasureDTO>();
        for (StudyOutcomeMeasure sp : queryList) {
            resultList.add(StudyOutcomeMeasureConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }

}
