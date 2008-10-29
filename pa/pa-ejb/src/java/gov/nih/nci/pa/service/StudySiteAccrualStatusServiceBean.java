/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualStatus;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteAccrualStatusConverter;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 09/26/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudySiteAccrualStatusServiceBean implements StudySiteAccrualStatusServiceRemote {
    private static final Logger LOG  = Logger.getLogger(StudySiteAccrualStatusServiceBean.class);
    private static String errMsgMethodNotImplemented = "Method not yet implemented.";

    // Standard methods
    /**
     * @param ii index
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    public StudySiteAccrualStatusDTO getStudySiteAccrualStatus(Ii ii) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    public StudySiteAccrualStatusDTO createStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudySiteAccrualStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudySiteAccrualStatusDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            List<StudySiteAccrualStatusDTO> currentList
                    = getCurrentStudySiteAccrualStatusByStudyParticipation(dto.getStudyParticipationIi());
            RecruitmentStatusCode oldCode = null;
            Timestamp oldDate = null;
            if (!currentList.isEmpty()) {
                oldCode = RecruitmentStatusCode.getByCode(currentList.get(0).getStatusCode().getCode());
                oldDate = TsConverter.convertToTimestamp(currentList.get(0).getStatusDate());
            }

            RecruitmentStatusCode newCode = RecruitmentStatusCode.getByCode(dto.getStatusCode().getCode());
            Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());

            if (newCode == null) {
                throw new PAException(" Study site accrual status must be set ");
            }
            if (newDate == null) {
                throw new PAException(" Study site accrual status date must be set ");
            }
            if (!newCode.equals(oldCode) || !newDate.equals(oldDate)) {
                StudySiteAccrualStatus bo = StudySiteAccrualStatusConverter.convertFromDtoToDomain(dto);
                session.saveOrUpdate(bo);
                session.flush();
                resultDto = StudySiteAccrualStatusConverter.convertFromDomainToDTO(bo);
            }
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in createStudySiteAccrualStatus ", hbe);
            throw new PAException(" Hibernate exception in createStudySiteAccrualStatus ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudySiteAccrualStatusDTO
     * @return StudySiteAccrualStatusDTO
     * @throws PAException PAException
     */
    public StudySiteAccrualStatusDTO updateStudySiteAccrualStatus(StudySiteAccrualStatusDTO dto) throws PAException {
        LOG.error(errMsgMethodNotImplemented);
        throw new PAException(errMsgMethodNotImplemented);
    }



    // Custom methods
    /**
     * @param studyParticipationIi id of Participation
     * @return list StudySiteAccrualStatusDTO
     * @throws PAException on error
     */
    public List<StudySiteAccrualStatusDTO> getStudySiteAccrualStatusByStudyParticipation(Ii studyParticipationIi)
            throws PAException {
        if (PAUtil.isIiNull(studyParticipationIi)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.info("Entering getStudySiteAccrualStatusByStudyParticipation");

        Session session = null;
        List<StudySiteAccrualStatus> queryList = new ArrayList<StudySiteAccrualStatus>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select ssas "
                       + "from StudySiteAccrualStatus ssas "
                       + "join ssas.studyParticipation sp "
                       + "where sp.id = :studyParticipationId "
                       + "order by ssas.id ";
            LOG.info(" query StudySiteAccrualStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyParticipationId", IiConverter.convertToLong(studyParticipationIi));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in getStudyParticipationByCriteria ", hbe);
            throw new PAException(" Hibernate exception in getStudyParticipationByCriteria ", hbe);
        }
        ArrayList<StudySiteAccrualStatusDTO> resultList = new ArrayList<StudySiteAccrualStatusDTO>();
        for (StudySiteAccrualStatus bo : queryList) {
            resultList.add(StudySiteAccrualStatusConverter.convertFromDomainToDTO(bo));
        }

        LOG.info("Leaving getStudySiteAccrualStatusByStudyParticipation, returning "
                + resultList.size() + " object(s).");
        return resultList;
    }

    /**
     * @param studyParticipationIi Primary key assigned to a StudyProtocl.
     * @return StudySiteAccrualStatusDTO Current status.
     * @throws PAException Exception.
     */
    public List<StudySiteAccrualStatusDTO> getCurrentStudySiteAccrualStatusByStudyParticipation(
            Ii studyParticipationIi) throws PAException {
        List<StudySiteAccrualStatusDTO> ssasList =
                this.getStudySiteAccrualStatusByStudyParticipation(studyParticipationIi);
        ArrayList<StudySiteAccrualStatusDTO> resultList = new ArrayList<StudySiteAccrualStatusDTO>();
        if (!ssasList.isEmpty()) {
            resultList.add(ssasList.get(ssasList.size() - 1));
        }
        return resultList;
    }
}
