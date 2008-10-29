/**
 *
 */
package gov.nih.nci.pa.service;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.coppa.iso.Ii;

import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.iso.convert.StudyParticipationContactConverter;

import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;

import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/30/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
public class StudyParticipationContactServiceBean extends AbstractBasePaService<StudyParticipationContactDTO> implements
        StudyParticipationContactServiceRemote {
    private static final Logger LOG = Logger.getLogger(StudyParticipationContactServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * @param dto dto
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO create(StudyParticipationContactDTO dto) throws PAException {
//        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
//            serviceError(" Update method should be used to modify existing. ");
//        }
        StudyParticipationContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipationContact bo = StudyParticipationContactConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyParticipationContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii index
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
        }
        StudyParticipationContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyParticipationContact bo = (StudyParticipationContact) session.get(StudyParticipationContact.class,
                    IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyParticipationContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto dto
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO update(StudyParticipationContactDTO dto) throws PAException {
        // enforce business rules
        if (dto == null) {
            this.serviceError("dto should not be null.");
        }
        return super.update(dto);
    }

    /**
     * @param ii index of StudyParticipationContact to be deleted.
     * @throws PAException exception
     */
    public void delete(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering delete().");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipationContact bo = (StudyParticipationContact) session.get(StudyParticipationContact.class,
                    IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting " + "StudyParticipation for pid = " + ii.getExtension(),
                    hbe);
        }
        LOG.info("Leaving delete().");
    }

    /**
     * @param studyParticipationIi id of protocol
     * @return list StudyParticipationContactDTO
     * @throws PAException on error
     */
    public List<StudyParticipationContactDTO> getByStudyParticipation(Ii studyParticipationIi) throws PAException {
        if ((studyParticipationIi == null) || PAUtil.isIiNull(studyParticipationIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getByStudyParticipation");
        Session session = null;
        List<StudyParticipationContact> queryList = new ArrayList<StudyParticipationContact>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            // step 1: form the hql
            String hql = "select spartcontact " + "from StudyParticipationContact spartcontact "
                    + "join spartcontact.studyParticipation spart " + "where spart.id = :studyPartId "
                    + "order by spartcontact.id ";
            LOG.info(" query StudyParticipationContact = " + hql);
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyPartId", IiConverter.convertToLong(studyParticipationIi));
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving " + "StudyParticipation for pid = "
                    + studyParticipationIi.getExtension(), hbe);
        }
        List<StudyParticipationContactDTO> resultList = new ArrayList<StudyParticipationContactDTO>();
        for (StudyParticipationContact sp : queryList) {
            resultList.add(StudyParticipationContactConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyParticipation");
        return resultList;
    }
}
