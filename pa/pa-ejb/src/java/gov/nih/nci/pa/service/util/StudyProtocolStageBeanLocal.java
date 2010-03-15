package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * 
 * @author Vrushali
 *
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.CyclomaticComplexity" , "PMD.NPathComplexity" , "PMD.ExcessiveParameterList" ,
    "PMD.ExcessiveClassLength" , "PMD.TooManyMethods" , "PMD.ExcessiveMethodLength" , "PMD.TooManyFields" })

public class StudyProtocolStageBeanLocal implements StudyProtocolStageServiceLocal {
    private static final Logger LOG  = Logger.getLogger(StudyProtocolStageBeanLocal.class);
    /**
     * 
     * @param ii primary id of StudyProtocol
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    public StudyProtocolStage get(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.debug("Entering getStudyProtocol");
        Session session = null;
        StudyProtocolStage studyProtocol = null;
        session = HibernateUtil.getCurrentSession();
        studyProtocol = (StudyProtocolStage)
        session.get(StudyProtocolStage.class, Long.valueOf(ii.getExtension()));
        if (studyProtocol == null) {
            throw new PAException("Ii could not be found.");
        }
       /* //get the grants
        List<StudyFundingStage> fundingDtos  = getFundings(studyProtocol.getId());
        studyProtocol.setStudyFunding(fundingDtos);
        //get ind ide and set
        List <StudyIndIdeStage> indIdeDtos = getIndIde(studyProtocol.getId());
        studyProtocol.setStudyIndIde(indIdeDtos);*/
        LOG.debug("Leaving getStudyProtocol");
        return studyProtocol;
    }
    /**
     * for creating a new ISP.
     * @param sp for 
     * @return ii ii
     * @throws PAException exception
     */
    public Ii create(StudyProtocolStage sp) throws PAException {
        createStageProtocol(sp);
        /*if (PAUtil.isListNotEmpty(sp.getStudyFunding())) {
            for (StudyFundingStage fundingDto : sp.getStudyFunding()) {
                fundingDto.setStudyProtocolStage(sp);
                createGrant(fundingDto);
            }
        }
        if (PAUtil.isListNotEmpty(sp.getStudyIndIde())) {
            for (StudyIndIdeStage indDto : sp.getStudyIndIde()) {
                indDto.setStudyProtocolStage(sp);
                createIndIde(indDto);
            }
        }*/
        return IiConverter.convertToStudyProtocolIi(sp.getId());
    }
    /**
     * 
     * @param dto criteria
     * @param pagingParams p
     * @return list of dto
     * @throws PAException on err
     * @throws TooManyResultsException on err
     */
    public List<StudyProtocolStage> search(StudyProtocolStage dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            LOG.error(" StudyProtocolDTO should not be null ");
            throw new PAException(" StudyProtocolDTO should not be null ");
        }
        LOG.debug("Entering search");
        Session session = null;
        List<StudyProtocolStage> studyProtocolList = null;
        session = HibernateUtil.getCurrentSession();
        StudyProtocolStage exampleDO = new StudyProtocolStage();
        if (dto.getPhaseCode() != null) {
            exampleDO.setPhaseCode(PhaseCode.getByCode(dto.getPhaseCode().getCode()));
        }
        if (!PAUtil.isNotEmpty(dto.getOfficialTitle())) {
            String title = "%" + dto.getOfficialTitle() + "%";
            exampleDO.setOfficialTitle(title);
        }
        if (dto.getPrimaryPurposeCode() != null) {
            exampleDO.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(dto.getPrimaryPurposeCode().getCode()));
        }
        if (!PAUtil.isNotEmpty(dto.getUserLastCreated())) {
            exampleDO.setUserLastCreated(dto.getUserLastCreated());
        }
        Example example = Example.create(exampleDO);
        example.enableLike();
        
        Criteria criteria = session.createCriteria(StudyProtocolStage.class, "sp").add(example);            
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        criteria.setMaxResults(maxLimit);
        criteria.setFirstResult(pagingParams.getOffset());
        studyProtocolList = criteria.list();

        if (studyProtocolList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        LOG.debug("Leaving search");
        return studyProtocolList;
    }
    /**
     * 
     * @param studyProtocolStage dto
     * @return dto
     * @throws PAException e
     */
    public StudyProtocolStage update(StudyProtocolStage studyProtocolStage) throws PAException {
        LOG.info("inside create...");

        Session session = null;
        session = HibernateUtil.getCurrentSession();
        studyProtocolStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.update(studyProtocolStage);
        //first delete grants and recreate
        deleteGrants(studyProtocolStage.getId());
        /*if (PAUtil.isListNotEmpty(studyProtocolStage.getStudyFunding())) {
            for (StudyFundingStage fundingDto : studyProtocolStage.getStudyFunding()) {
                fundingDto.setStudyProtocolStage(studyProtocolStage);
                createGrant(fundingDto);
            }
        }
        deleteIndIde(studyProtocolStage.getId());
        if (PAUtil.isListNotEmpty(studyProtocolStage.getStudyIndIde())) {
            for (StudyIndIdeStage indDto : studyProtocolStage.getStudyIndIde()) {
                indDto.setStudyProtocolStage(studyProtocolStage);
                createIndIde(indDto);
            }
        }*/

    return studyProtocolStage;
    }
    /**
     * 
     * @param ii ii to delete
     * @return 
     * @throws PAException e
     */
    public void delete(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StudyProtocolStage tempSp = (StudyProtocolStage)
            session.load(StudyProtocolStage.class, Long.valueOf(ii.getExtension()));
        session.delete(tempSp);
    }
    /**
     * @param tempStudyFunding tempStudyFundingDTO
     * @throws PAException on error
     */
    public void createGrant(StudyFundingStage tempStudyFunding) throws PAException {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        tempStudyFunding.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(tempStudyFunding);
    }
    /**
     * 
     * @param studyProtocolStageId id to delete
     * @throws PAException on error
     */
    public void deleteGrants(Long studyProtocolStageId) throws PAException {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM TEMP_STUDY_FUNDING WHERE TEMP_STUDY_PROTOCOL_IDENTIFIER  = ")
            .append(studyProtocolStageId);
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();
    }
    /**
     * 
     * @param studyProtocolStageId id
     * @return list
     * @throws PAException e
     */
    private List<StudyFundingStage> getFundings(Long studyProtocolStageId)throws PAException {
        
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" TempStudyFunding spart join spart.tempStudyProtocol spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<StudyFundingStage> queryList = new ArrayList<StudyFundingStage>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", studyProtocolStageId);
        queryList = query.list();
        return queryList;
    }
    /**
     * @param tempStudyIndIdeDTO dto to create
     * @throws PAException on err
     */
    private void createIndIde(StudyIndIdeStage tempStudyIndIde)
            throws PAException {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        tempStudyIndIde.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(tempStudyIndIde);
    }
    /**
     * @param studyProtocolStageId  id
     * @throws PAException on err
     */
    private void deleteIndIde(Long studyProtocolStageId)
            throws PAException {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM TEMP_STUDY_INDIDE WHERE TEMP_STUDY_PROTOCOL_IDENTIFIER  = ")
            .append(studyProtocolStageId);
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();
        
    }
    /**
     * @param studyProtocolStageIi ii
     * @return List
     * @throws PAException e
     */
    private List<StudyIndIdeStage> getIndIde(Long studyProtocolStageId) throws PAException {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" TempStudyIndIde spart join spart.tempStudyProtocol spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<StudyIndIdeStage>  queryList = new ArrayList<StudyIndIdeStage>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", studyProtocolStageId);
        queryList = query.list();
        return queryList;
    }
    
    private long createStageProtocol(StudyProtocolStage sp) {
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(sp);
        return sp.getId();
    }
}
