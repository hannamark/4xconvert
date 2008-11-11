package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.AvoidDuplicateLiterals" })
public class PlannedActivityServiceBean
        extends AbstractStudyPaService<PlannedActivityDTO>
        implements PlannedActivityServiceRemote {

    private static final Logger LOG  = Logger.getLogger(PlannedActivityServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * @param ii index
     * @return the planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        PlannedActivityDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            PlannedActivity bo = (PlannedActivity) session.get(PlannedActivity.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = PlannedActivityConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

    private PlannedActivityDTO createOrUpdate(PlannedActivityDTO dto) throws PAException {
        PlannedActivity bo = null;
        PlannedActivityDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo = PlannedActivityConverter.convertFromDtoToDomain(dto);
            } else {
                bo = (PlannedActivity) session.get(PlannedActivity.class,
                        IiConverter.convertToLong(dto.getIdentifier()));

                PlannedActivity delta = PlannedActivityConverter.convertFromDtoToDomain(dto);
                if (delta.getAlternateName() != null) {
                    bo.setAlternateName(delta.getAlternateName());
                }
                if (delta.getCategoryCode() != null) {
                    bo.setCategoryCode(delta.getCategoryCode());
                }
                bo.setUserLastUpdated(delta.getUserLastUpdated());
                if (delta.getDescriptionText() != null) {
                    bo.setDescriptionText(delta.getDescriptionText());
                }
                if (delta.getIntervention() != null) {
                    bo.setIntervention(delta.getIntervention());
                }
                if (delta.getLeadProductIndicator() != null) {
                    bo.setLeadProductIndicator(delta.getLeadProductIndicator());
                }
                if (delta.getName() != null) {
                    bo.setName(delta.getName());
                }
                if (delta.getSubcategoryCode() != null) {
                    bo.setSubcategoryCode(delta.getSubcategoryCode());
                }
            }
            bo.setDateLastUpdated(new Date());
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = PlannedActivityConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in createOrUpdate().  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto planned activity to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            serviceError("StudyProtocol must be set.  ");
        }
        return createOrUpdate(dto);
    }

    /**
     * @param dto planned activity to update
     * @return the updated planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Create method should be used to modify existing.  ");
        }
        return createOrUpdate(dto);
    }

    /**
     * @param ii index
     * @throws PAException exception.
     */
    @Override
    public void delete(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        LOG.info("Entering delete().  ");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            PlannedActivity bo = (PlannedActivity) session.get(PlannedActivity.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting ii = "
                + IiConverter.convertToString(ii) + ".  ", hbe);
        }
        LOG.info("Leaving delete().  ");
    }

    /**
     *  @param ii study protocol index
     *  @return list of planned activities
     *  @throws PAException exception
     */
    @Override
    public List<PlannedActivityDTO> getByStudyProtocol(Ii ii)
            throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        LOG.info("Entering getByStudyProtocol");

        Session session = null;
        List<PlannedActivity> queryList = new ArrayList<PlannedActivity>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedActivity pa "
                       + "join pa.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by pa.id ";
            LOG.info("query PlannedActivity = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByStudyProtocol.  ", hbe);
        }
        ArrayList<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
        for (PlannedActivity bo : queryList) {
            resultList.add(PlannedActivityConverter.convertFromDomainToDTO(bo));
        }
        LOG.info("Leaving getByStudyProtocol, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

    /**
     * @param ii index of arm
     * @return list of planned activities associated w/arm
     * @throws PAException exception
     */
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found.  ");
        }
        LOG.info("Entering getByArm.  ");

        Session session = null;
        List<PlannedActivity> queryList = new ArrayList<PlannedActivity>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedActivity pa "
                       + "join pa.arms a "
                       + "where a.id = :armId "
                       + "order by pa.id ";
            LOG.info("query PlannedActivity = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("armId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getByArm.  ", hbe);
        }
        ArrayList<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
        for (PlannedActivity bo : queryList) {
            resultList.add(PlannedActivityConverter.convertFromDomainToDTO(bo));
        }
        LOG.info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

}
