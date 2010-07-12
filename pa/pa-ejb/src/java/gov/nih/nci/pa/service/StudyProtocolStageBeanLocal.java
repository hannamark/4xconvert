/**
 *
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AbstractEntity;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyFundingStage;
import gov.nih.nci.pa.domain.StudyIndIdeStage;
import gov.nih.nci.pa.domain.StudyProtocolStage;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.iso.convert.StudyFundingStageConverter;
import gov.nih.nci.pa.iso.convert.StudyIndIdeStageConverter;
import gov.nih.nci.pa.iso.convert.StudyProtocolStageConverter;
import gov.nih.nci.pa.iso.dto.StudyFundingStageDTO;
import gov.nih.nci.pa.iso.dto.StudyIndIdeStageDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolStageDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
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
    "PMD.CyclomaticComplexity", "PMD.NPathComplexity", "PMD.TooManyMethods" })
public class StudyProtocolStageBeanLocal implements StudyProtocolStageServiceLocal {
    private static final Logger LOG  = Logger.getLogger(StudyProtocolStageBeanLocal.class);
    @EJB
    private MailManagerServiceLocal mailManagerSerivceLocal;
    @EJB
    private LookUpTableServiceRemote lookUpTableService;
    @EJB
    private RegistryUserServiceLocal registryUserServiceLocal;

    /**
     * @param dto dto
     * @param pagingParams pagingParams
     * @return list
     * @throws PAException on err.
     * @throws TooManyResultsException on err
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyProtocolStageDTO> search(StudyProtocolStageDTO dto,
            LimitOffset pagingParams) throws PAException,
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

        Criteria criteria = session.createCriteria(StudyProtocolStage.class, "sp").add(example);
        int maxLimit = Math.min(pagingParams.getLimit(), PAConstants.MAX_SEARCH_RESULTS + 1);
        criteria.setMaxResults(maxLimit);
        criteria.setFirstResult(pagingParams.getOffset());
        studyProtocolList = criteria.list();
        if (studyProtocolList.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new TooManyResultsException(PAConstants.MAX_SEARCH_RESULTS);
        }
        List<StudyProtocolStageDTO> studyProtocolDTOList = convertFromDomainToDTO(studyProtocolList);
        LOG.debug("Leaving search");
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
        StudyProtocolStage tempSp = (StudyProtocolStage)
            session.load(StudyProtocolStage.class, Long.valueOf(ii.getExtension()));
        session.delete(tempSp);
    }

    /**
     * @param ii ii
     * @return Dto
     * @throws PAException on err
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyProtocolStageDTO get(Ii ii) throws PAException {
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
        LOG.debug("Leaving getStudyProtocol");
        return StudyProtocolStageConverter.convertFromDomainToDTO(studyProtocol);
    }

    /**
     *
     * @param studyProtocolStageIi ii
     * @return list
     * @throws PAException e
     */
    public List<StudyFundingStageDTO> getGrantsByStudyProtocolStage(Ii studyProtocolStageIi)throws PAException {
        if (PAUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException("Ii can not be null");
        }
        List<StudyFundingStageDTO> resultList = new ArrayList<StudyFundingStageDTO>();
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" StudyFundingStage spart join spart.studyProtocolStage spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<? extends AbstractEntity> queryList = new ArrayList<AbstractEntity>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolStageIi));
        queryList = query.list();
        for (AbstractEntity bo : queryList) {
            resultList.add(StudyFundingStageConverter.convertFromDomainToDTO((StudyFundingStage) bo));
        }
        return resultList;
    }

    /**
     * @param studyProtocolStageIi ii
     * @return List
     * @throws PAException e
     */
    public List<StudyIndIdeStageDTO> getIndIdesByStudyProtocolStage(
            Ii studyProtocolStageIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException("Ii can not be null");
        }
        List<StudyIndIdeStageDTO> resultList = new ArrayList<StudyIndIdeStageDTO>();
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        StringBuffer hql = new StringBuffer("select spart from ");
        hql.append(" StudyIndIdeStage spart join spart.studyProtocolStage spro where spro.id = :studyProtocolId");
        LOG.info(" query  = " + hql);
        List<? extends AbstractEntity> queryList = new ArrayList<AbstractEntity>();
        Query query = session.createQuery(hql.toString());
        query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolStageIi));
        queryList = query.list();
        for (AbstractEntity bo : queryList) {
            resultList.add(StudyIndIdeStageConverter.convertFromDomainToDTO((StudyIndIdeStage) bo));
        }
        return resultList;
    }

    /**
     * @param ispDTO dto
     * @param fundDTOs dto
     * @param indDTOs dto
     * @return ii
     * @throws PAException on err
     */
    public Ii create(StudyProtocolStageDTO ispDTO,
            List<StudyFundingStageDTO> fundDTOs, List<StudyIndIdeStageDTO> indDTOs)
            throws PAException {
        Ii studyProtocolStageIi = createOrUpdateStudyProtocol(ispDTO, "Create");
        createGrants(fundDTOs, studyProtocolStageIi);
        createIndIde(indDTOs, studyProtocolStageIi);
        sendPartialSubmissionMail(studyProtocolStageIi);
        return studyProtocolStageIi;
    }

    /**
     * @param isoDTO  for spStage
     *  @param fundDTOs for funding
     *  @param indDTOs for ind
     *  @return dto
     *  @throws PAException on err
     */
    public StudyProtocolStageDTO update(StudyProtocolStageDTO isoDTO,
            List<StudyFundingStageDTO> fundDTOs, List<StudyIndIdeStageDTO> indDTOs)
            throws PAException {
        Ii spIi = createOrUpdateStudyProtocol(isoDTO, "update");
        deleteGrants(spIi);
        deleteIndIdesForStudyProtocolStage(spIi);
        createGrants(fundDTOs, spIi);
        createIndIde(indDTOs, spIi);
        return get(spIi);

    }

    private Ii createOrUpdateStudyProtocol(StudyProtocolStageDTO isoDTO, String operation)
            throws PAException {
        LOG.info("inside create...");
        StudyProtocolStage sp = StudyProtocolStageConverter.convertFromDTOToDomain(isoDTO);
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        if ("Create".equalsIgnoreCase(operation)) {
            sp.setDateLastCreated(new Timestamp((new Date()).getTime()));
            session.save(sp);
        } else {
            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(sp);
        }
        return IiConverter.convertToStudyProtocolIi(sp.getId());
    }

    private List<StudyProtocolStageDTO> convertFromDomainToDTO(List<StudyProtocolStage> studyProtocolList) {
        List<StudyProtocolStageDTO> studyProtocolDTOList = null;
        if (studyProtocolList != null) {
            studyProtocolDTOList = new ArrayList<StudyProtocolStageDTO>();
            for (StudyProtocolStage sp : studyProtocolList) {
                StudyProtocolStageDTO studyProtocolDTO = StudyProtocolStageConverter.convertFromDomainToDTO(sp);
                studyProtocolDTOList.add(studyProtocolDTO);
            }
        }
        return studyProtocolDTOList;
    }

    private void createGrants(List<StudyFundingStageDTO> studyFundingStageDTOs,
            Ii studyProtocolStageIi) throws PAException {
        if (CollectionUtils.isNotEmpty(studyFundingStageDTOs)) {
            if (PAUtil.isIiNull(studyProtocolStageIi)) {
                throw new PAException("StudyProtocolStageIi can not be null");
            }
            Session session = null;
            session = HibernateUtil.getCurrentSession();
            for (StudyFundingStageDTO fundingDTO : studyFundingStageDTOs) {
                fundingDTO.setStudyProtocolStageIi(studyProtocolStageIi);
                StudyFundingStage studyFundingStage = StudyFundingStageConverter.convertFromDTOToDomain(
                        fundingDTO);
                studyFundingStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
                session.save(studyFundingStage);
            }
        }

    }

    private void deleteGrants(Ii studyProtocolStageIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM STUDY_FUNDING_STAGE WHERE STUDY_PROTOCOL_STAGE_IDENTIFIER  = ")
            .append(IiConverter.convertToString(studyProtocolStageIi));
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();
    }

    private void createIndIde(List<StudyIndIdeStageDTO> studyIndIdeStageDTOs, Ii studyProtocolStageIi)
            throws PAException {
        if (CollectionUtils.isNotEmpty(studyIndIdeStageDTOs)) {
            if (PAUtil.isIiNull(studyProtocolStageIi)) {
                throw new PAException("StudyProtocolStageIi can not be null");
            }
            Session session = null;
            session = HibernateUtil.getCurrentSession();
            for (StudyIndIdeStageDTO indDto : studyIndIdeStageDTOs) {
                indDto.setStudyProtocolStageIi(studyProtocolStageIi);
                StudyIndIdeStage studyIndIdeStage = StudyIndIdeStageConverter.convertFromDTOToDomain(
                        indDto);
                studyIndIdeStage.setDateLastCreated(new Timestamp((new Date()).getTime()));
                session.save(studyIndIdeStage);
            }
        }
    }

    private void deleteIndIdesForStudyProtocolStage(Ii studyProtocolStageIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolStageIi)) {
            throw new PAException("Ii can not be null");
        }
        Session session = null;
        session = HibernateUtil.getCurrentSession();
        session = HibernateUtil.getCurrentSession();
        StringBuffer sql = new StringBuffer("DELETE FROM STUDY_INDIDE_STAGE WHERE STUDY_PROTOCOL_STAGE_IDENTIFIER  = ")
            .append(IiConverter.convertToString(studyProtocolStageIi));
        LOG.info(" query  = " + sql);
        session.createSQLQuery(sql.toString()).executeUpdate();

    }

    private void sendPartialSubmissionMail(Ii studyProtocolStageIi) {
       try {
            PAServiceUtils paServiceUtil = new PAServiceUtils();
            StudyProtocolStageDTO spDTO = get(studyProtocolStageIi);
            String submissionMailBody = lookUpTableService.getPropertyValue("trial.partial.register.body");
            submissionMailBody = submissionMailBody.replace("${CurrentDate}", PAUtil.today());
            submissionMailBody = submissionMailBody.replace("${leadOrgTrialIdentifier}",
                StConverter.convertToString(spDTO.getLocalProtocolIdentifier()));
            submissionMailBody = submissionMailBody.replace("${leadOrgName}",
                    paServiceUtil.getOrgName(IiConverter.convertToPoOrganizationIi(
                        spDTO.getLeadOrganizationIdentifier().getExtension())));
            submissionMailBody = submissionMailBody.replace("${temporaryidentifier}", IiConverter.convertToString(
                spDTO.getIdentifier()));
            String title = "";
            if (!PAUtil.isStNull(spDTO.getOfficialTitle())) {
                title = StConverter.convertToString(spDTO.getOfficialTitle());
            }
            submissionMailBody = submissionMailBody.replace("${trialTitle}", title);
            RegistryUser registryUser = registryUserServiceLocal.getUser(
                    StConverter.convertToString(spDTO.getUserLastCreated()));
            submissionMailBody = submissionMailBody.replace("${SubmitterName}",
                 registryUser.getFirstName() + " " + registryUser.getLastName());

            String mailSubject = lookUpTableService.getPropertyValue("trial.partial.register.subject");
            mailSubject = mailSubject.replace("${leadOrgTrialIdentifier}",
                StConverter.convertToString(spDTO.getLocalProtocolIdentifier()));
            mailManagerSerivceLocal.sendMailWithAttachment(registryUser.getEmailAddress(),
                    mailSubject, submissionMailBody, null);
          } catch (Exception e) {
               LOG.error("Send Mail error Partial Submission Mail", e);
          }
    }

}
