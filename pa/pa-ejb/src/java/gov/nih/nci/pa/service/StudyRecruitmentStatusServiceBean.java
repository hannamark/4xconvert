package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
import gov.nih.nci.pa.iso.convert.StudyRecruitmentStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 10/14/2008
 * 
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyRecruitmentStatusServiceBean 
        extends AbstractStudyPaService<StudyRecruitmentStatusDTO>  
        implements StudyRecruitmentStatusServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyRecruitmentStatusServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { 
        return LOG; 
    }
    
    /**
     * Protected static create method for auto-generating a recruitment status domain object to be 
     * used in other services.
     * @param bo the StudyOverallStatus domain object.
     * @return the recruitment status domain object.
     */
    protected static StudyRecruitmentStatus create(StudyOverallStatus bo) {
        // automatically update StudyRecruitmentStatus for applicable overall status code's
        if ((bo != null) && (StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()) != null)) {
            StudyRecruitmentStatus srsBo = new StudyRecruitmentStatus();
            srsBo.setStatusCode(StudyRecruitmentStatusCode.getByStudyStatusCode(bo.getStatusCode()));
            srsBo.setStatusDate(bo.getStatusDate());
            srsBo.setStudyProtocol(bo.getStudyProtocol());
            return srsBo;
        }
        return null;
    }
    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List.
     * @throws PAException Exception.
     */
    @Override
    public List<StudyRecruitmentStatusDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            String errMsg = " Ii should not be null ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        LOG.info("Entering getStudyOverallStatusByStudyProtocol");

        Session session = null;
        List<StudyRecruitmentStatus> queryList = new ArrayList<StudyRecruitmentStatus>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select srs from StudyRecruitmentStatus srs "
                       + "join srs.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by srs.id ";
            LOG.info(" query StudyRecruitmentStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getStudyProtocolByCriteria.  ", hbe);
        }
        ArrayList<StudyRecruitmentStatusDTO> resultList = new ArrayList<StudyRecruitmentStatusDTO>();
        for (StudyRecruitmentStatus bo : queryList) {
            resultList.add(StudyRecruitmentStatusConverter.convertFromDomainToDTO(bo));
        }

        LOG.info("Leaving getStudyOverallStatusByStudyProtocol, returning " + resultList.size() + " object(s).");
        return resultList;
    }
    
    /**
     * 
     * @param studyProtocolIi ii
     * @return StudyRecruitmentStatusDTO StudyRecruitmentStatusDTO
     * @throws PAException error 
     */
    public List<StudyRecruitmentStatusDTO> getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        List<StudyRecruitmentStatusDTO> sosList = this.getByStudyProtocol(studyProtocolIi);
        List<StudyRecruitmentStatusDTO> resultList = new ArrayList<StudyRecruitmentStatusDTO>();
        if (!sosList.isEmpty()) {
            resultList.add(sosList.get(sosList.size() - 1));
        }
        return resultList;
    }
}
