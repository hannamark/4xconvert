/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.TempStudyFunding;
import gov.nih.nci.pa.domain.TempStudyIndIde;
import gov.nih.nci.pa.domain.TempStudyProtocol;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.convert.TempStudyFundingConverter;
import gov.nih.nci.pa.iso.convert.TempStudyIndIdeConverter;
import gov.nih.nci.pa.iso.convert.TempStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.TempStudyFundingDTO;
import gov.nih.nci.pa.iso.dto.TempStudyIndIdeDTO;
import gov.nih.nci.pa.iso.dto.TempStudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
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
 * @author Vrushali
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.AvoidDuplicateLiterals", 
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TempStudyProtocolBeanLocal implements TempStudyProtocolServiceLocal {
    private static final Logger LOG  = Logger.getLogger(TempStudyProtocolBeanLocal.class);
    /**
     * @param isoDTO isoDto
     * @return ii
     * @throws PAException e
     */    
    public Ii createTempStudyProtocol(TempStudyProtocolDTO isoDTO)
            throws PAException {
        LOG.info("inside create...");
        TempStudyProtocol sp = TempStudyProtocolConverter.convertFromDTOToDomain(isoDTO);
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(sp);
        return IiConverter.convertToStudyProtocolIi(sp.getId());
    }
    /**
     * @param ii ii
     * @return dto
     * @throws PAException e
     */
    @TransactionAttribute (TransactionAttributeType.SUPPORTS)
    public TempStudyProtocolDTO getTempStudyProtocol(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            LOG.error(" Ii should not be null ");
            throw new PAException(" Ii should not be null ");
        }
        LOG.debug("Entering getStudyProtocol");
        Session session = null;
        TempStudyProtocol studyProtocol = null;
        session = HibernateUtil.getCurrentSession();
        studyProtocol = (TempStudyProtocol)
        session.get(TempStudyProtocol.class, Long.valueOf(ii.getExtension()));
        if (studyProtocol == null) {
            throw new PAException("Ii could not be found.");
        }
        LOG.debug("Leaving getStudyProtocol");
        return TempStudyProtocolConverter.convertFromDomainToDTO(studyProtocol);

    }
    /**
     * 
     * @param dto criteriaDto
     * @param pagingParams pa
     * @return dtos
     * @throws PAException on err
     * @throws TooManyResultsException on err
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<TempStudyProtocolDTO> search(TempStudyProtocolDTO dto, LimitOffset pagingParams) throws PAException,
    TooManyResultsException {
        if (dto == null) {
            LOG.error(" StudyProtocolDTO should not be null ");
            throw new PAException(" StudyProtocolDTO should not be null ");
        }
        LOG.debug("Entering search");
        Session session = null;
        List<TempStudyProtocol> studyProtocolList = null;
        session = HibernateUtil.getCurrentSession();
        TempStudyProtocol exampleDO = new TempStudyProtocol();
        if (dto.getPhaseCode() != null) {
            exampleDO.setPhaseCode(PhaseCode.getByCode(dto.getPhaseCode().getCode()));
        }
        if (!PAUtil.isStNull(dto.getOfficialTitle())) {
            String title = "%" + StConverter.convertToString(dto.getOfficialTitle()) + "%";
            exampleDO.setOfficialTitle(title);
        }
        if (dto.getPrimaryPurposeCode() != null) {
            exampleDO.setPrimaryPurposeCode(PrimaryPurposeCode.getByCode(dto.getPrimaryPurposeCode().getCode()));
        }
        if (!PAUtil.isStNull(dto.getUserLastCreated())) {
            exampleDO.setUserLastCreated(StConverter.convertToString(dto.getUserLastCreated()));
        }
        Example example = Example.create(exampleDO);
        example.enableLike();
        
        Criteria criteria = session.createCriteria(TempStudyProtocol.class, "sp").add(example);            
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        criteria.setMaxResults(maxLimit);
        criteria.setFirstResult(pagingParams.getOffset());
        studyProtocolList = criteria.list();

        if (studyProtocolList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        List<TempStudyProtocolDTO> studyProtocolDTOList = convertFromDomainToDTO(studyProtocolList);
        LOG.debug("Leaving search");
        return studyProtocolDTOList;
    }
    /**
     * @param isoDTO isoDto
     * @return ii
     * @throws PAException e
     */    
    public TempStudyProtocolDTO updateTempStudyProtocol(TempStudyProtocolDTO isoDTO)
            throws PAException {
        LOG.info("inside create...");
        TempStudyProtocol sp = TempStudyProtocolConverter.convertFromDTOToDomain(isoDTO);
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.update(sp);
        return TempStudyProtocolConverter.convertFromDomainToDTO(sp);
    }
    private List<TempStudyProtocolDTO> convertFromDomainToDTO(List<TempStudyProtocol> studyProtocolList) {
        List<TempStudyProtocolDTO> studyProtocolDTOList = null;
        if (studyProtocolList != null) {
            studyProtocolDTOList = new ArrayList<TempStudyProtocolDTO>();
            for (TempStudyProtocol sp : studyProtocolList) {
                TempStudyProtocolDTO studyProtocolDTO = TempStudyProtocolConverter.convertFromDomainToDTO(sp);
                studyProtocolDTOList.add(studyProtocolDTO);
            }
        }
        return studyProtocolDTOList;
    }
    /**
     * @param ii ii to delete
     * @throws PAException e
     */
    public void delete(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        TempStudyProtocol tempSp = (TempStudyProtocol)
            session.load(TempStudyProtocol.class, Long.valueOf(ii.getExtension()));
        session.delete(tempSp);
    }
    /**
     * @param tempStudyFundingDTO tempStudyFundingDTO
     * @throws PAException on error
     */
    public void createGrant(TempStudyFundingDTO tempStudyFundingDTO) throws PAException {
        if (PAUtil.isIiNull(tempStudyFundingDTO.getTempStudyProtocolIi())) {
            throw new PAException("tempStudyProtocolIi can not be null");
        }
        Session session = null;
        TempStudyFunding tempstudyFunding = TempStudyFundingConverter.convertFromDTOToDomain(
                tempStudyFundingDTO);
        
        session = HibernateUtil.getCurrentSession();
        tempstudyFunding.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(tempstudyFunding);
    }
    /**
     * 
     * @param tempStudyProtocolIi ii to delete
     * @throws PAException on error
     */
    public void deleteGrantsForTempStudyProtocol(Ii tempStudyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(tempStudyProtocolIi)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM TEMP_STUDY_FUNDING WHERE TEMP_STUDY_PROTOCOL_IDENTIFIER  = ")
            .append(IiConverter.convertToString(tempStudyProtocolIi));
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();
    }
    /**
     * 
     * @param tempStudyProtocolIi ii
     * @return list
     * @throws PAException e
     */
    public List<TempStudyFundingDTO> getGrantsByTempStudyProtocol(Ii tempStudyProtocolIi)throws PAException {
        if (PAUtil.isIiNull(tempStudyProtocolIi)) {
            throw new PAException("Ii can not be null");
        }
        List<TempStudyFundingDTO> resultList = new ArrayList<TempStudyFundingDTO>();
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" TempStudyFunding spart join spart.tempStudyProtocol spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<? extends AbstractEntity> queryList = new ArrayList<AbstractEntity>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", IiConverter.convertToLong(tempStudyProtocolIi));
        queryList = query.list();
        for (AbstractEntity bo : queryList) {
            resultList.add(TempStudyFundingConverter.convertFromDomainToDTO((TempStudyFunding) bo));    
        }
        return resultList;
    }
    /**
     * @param tempStudyIndIdeDTO dto to create
     * @throws PAException on err
     */
    public void createIndIde(TempStudyIndIdeDTO tempStudyIndIdeDTO)
            throws PAException {
        if (PAUtil.isIiNull(tempStudyIndIdeDTO.getTempStudyProtocolIi())) {
            throw new PAException("tempStudyProtocolIi can not be null");
        }
        Session session = null;
        TempStudyIndIde tempstudyIndIde = TempStudyIndIdeConverter.convertFromDTOToDomain(
                tempStudyIndIdeDTO);
        
        session = HibernateUtil.getCurrentSession();
        tempstudyIndIde.setDateLastCreated(new Timestamp((new Date()).getTime()));
        session.save(tempstudyIndIde);
        
    }
    /**
     * @param tempStudyProtocolIi ii
     * @throws PAException on err
     */
    public void deleteIndIdeForTempStudyProtocol(Ii tempStudyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(tempStudyProtocolIi)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM TEMP_STUDY_INDIDE WHERE TEMP_STUDY_PROTOCOL_IDENTIFIER  = ")
            .append(IiConverter.convertToString(tempStudyProtocolIi));
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();
        
    }
    /**
     * @param tempStudyProtocolIi ii
     * @return List
     * @throws PAException e
     */
    public List<TempStudyIndIdeDTO> getIndIdeByTempStudyProtocol(
            Ii tempStudyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(tempStudyProtocolIi)) {
            throw new PAException("Ii can not be null");
        }
        List<TempStudyIndIdeDTO> resultList = new ArrayList<TempStudyIndIdeDTO>();
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" TempStudyIndIde spart join spart.tempStudyProtocol spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<? extends AbstractEntity> queryList = new ArrayList<AbstractEntity>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", IiConverter.convertToLong(tempStudyProtocolIi));
        queryList = query.list();
        for (AbstractEntity bo : queryList) {
            resultList.add(TempStudyIndIdeConverter.convertFromDomainToDTO((TempStudyIndIde) bo));    
        }
        return resultList;
    }
    
}
