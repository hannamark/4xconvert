package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.enums.StudyParticipationFunctionalCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationDTO;
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
 * @author Hugh Reinhart
 * @since 09/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyParticipationServiceBean
        extends AbstractStudyPaService<StudyParticipationDTO>
        implements StudyParticipationServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyParticipationServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param ii Index
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found ");
        }
        StudyParticipationDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyParticipation bo = (StudyParticipation) session.get(StudyParticipation.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyParticipationConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO create(
            StudyParticipationDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        if (PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            serviceError("Either healthcare facility or research organization must be set.  ");
        }
        if (!PAUtil.isIiNull(dto.getHealthcareFacilityIi()) && !PAUtil.isIiNull(dto.getResearchOrganizationIi())) {
            serviceError("Healthcare facility and research organization cannot both be set.  ");
        }
        StudyParticipationDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipation bo = StudyParticipationConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyParticipationConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudyParticipationDTO
     * @return StudyParticipationDTO
     * @throws PAException PAException
     */
    @Override
    public StudyParticipationDTO update(StudyParticipationDTO dto)
            throws PAException {
        // enforce business rules
        if (dto == null) {
            serviceError("StudyParticipationDTO should not be null ");
        }

        Session session = null;
        StudyParticipationDTO resultDto = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();

        try {
            session = HibernateUtil.getCurrentSession();
            String hql = "select sp "
                       + "from StudyParticipation sp "
                       + "where sp.id =  " + Long.valueOf(dto.getIdentifier().getExtension());
            LOG.info(" query StudyParticipation = " + hql);

            Query query = session.createQuery(hql);
            queryList = query.list();

            StudyParticipation sp = queryList.get(0);
            StudyParticipation spNew = StudyParticipationConverter.convertFromDtoToDomain(dto);

            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            sp.setUserLastUpdated(spNew.getUserLastUpdated());
            if (spNew.getFunctionalCode() != null) {
                sp.setFunctionalCode(spNew.getFunctionalCode());
            }
            if (spNew.getFunctionalCode() != null) {
                sp.setFunctionalCode(spNew.getFunctionalCode());
            }
            if (spNew.getLocalStudyProtocolIdentifier() != null) {
                sp.setLocalStudyProtocolIdentifier(spNew.getLocalStudyProtocolIdentifier());
            }
            if (spNew.getStatusCode() != null) {
                sp.setStatusCode(spNew.getStatusCode());
            }
            if (spNew.getStatusDateRangeLow() != null) {
                sp.setStatusDateRangeLow(spNew.getStatusDateRangeLow());
            }
            session.update(sp);
            session.flush();
            resultDto =  StudyParticipationConverter.convertFromDomainToDTO(sp);
        }  catch (HibernateException hbe) {
            serviceError("Hibernate exception while updating StudyParticipation for id = "
                    + IiConverter.convertToString(dto.getIdentifier()) + ".  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii Index of StudyParticipation object
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
            StudyParticipation bo = (StudyParticipation) session.get(StudyParticipation.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyParticipation for pid = " + ii.getExtension(), hbe);
        }
        LOG.info("Leaving delete().");
    }

    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @Override
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getStudyParticipationByStudyProtocol");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = "select spart "
                       + "from StudyParticipation spart "
                       + "join spart.studyProtocol spro "
                       + "where spro.id = :studyProtocolId "
                       + " order by spart.id ";
            LOG.info(" query StudyParticipation = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(StudyParticipationConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }

    /**
     * @param studyProtocolIi id of protocol
     * @param spDTO StudyParticipationDTO
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyParticipationDTO spDTO) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError("Ii is null ");
        }
        LOG.info("Entering getStudyParticipationByStudyProtocol");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = "select spart "
                       + "from StudyParticipation spart "
                       + "join spart.studyProtocol spro "
                       + "where spro.id = :studyProtocolId "
                       + " and spart.functionalCode = '"
                       + StudyParticipationFunctionalCode.getByCode(spDTO.getFunctionalCode().getCode()) + "'"
                       + " order by spart.id ";
            LOG.info(" query StudyParticipation = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(StudyParticipationConverter.convertFromDomainToDTO(sp));
        }

        LOG.info("Leaving etStudyParticipationByStudyProtocol");
        return resultList;
    }

    /**
     * @param studyProtocolIi id of protocol
     * @param spDTOList List containing desired codes
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    public List<StudyParticipationDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyParticipationDTO> spDTOList) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError("Ii is null ");
        }
        if ((spDTOList == null) || (spDTOList.isEmpty())) {
            LOG.info("Using method getByStudyProtocol(Ii).  ");
            return getByStudyProtocol(studyProtocolIi);
        }
        LOG.info("Entering getStudyParticipationByStudyProtocol");
        Session session = null;
        List<StudyParticipation> queryList = new ArrayList<StudyParticipation>();
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer("select spart "
                               + "from StudyParticipation spart "
                               + "join spart.studyProtocol spro "
                               + "where spro.id = :studyProtocolId ");
            boolean first = true;
            for (StudyParticipationDTO crit : spDTOList) {
                if (first) {
                    hql.append("and ( ");
                    first = false;
                } else {
                    hql.append("or ");
                }
                hql.append("spart.functionalCode = '"
                    + StudyParticipationFunctionalCode.getByCode(crit.getFunctionalCode().getCode()) + "' ");
            }
            hql.append(") order by spart.id ");
            LOG.info(" query StudyParticipation = " + hql);

            Query query = session.createQuery(hql.toString());
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyParticipationDTO> resultList = new ArrayList<StudyParticipationDTO>();
        for (StudyParticipation sp : queryList) {
            resultList.add(StudyParticipationConverter.convertFromDomainToDTO(sp));
        }

        LOG.info("Leaving etStudyParticipationByStudyProtocol");
        return resultList;
    }

}
