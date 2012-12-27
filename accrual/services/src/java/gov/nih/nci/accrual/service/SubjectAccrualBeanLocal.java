/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.Converters;
import gov.nih.nci.accrual.convert.PatientConverter;
import gov.nih.nci.accrual.convert.StudySubjectConverter;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.dto.SubjectAccrualDTO;
import gov.nih.nci.accrual.dto.util.PatientDto;
import gov.nih.nci.accrual.enums.CDUSPatientEthnicityCode;
import gov.nih.nci.accrual.enums.CDUSPatientGenderCode;
import gov.nih.nci.accrual.enums.CDUSPatientRaceCode;
import gov.nih.nci.accrual.enums.CDUSPaymentMethodCode;
import gov.nih.nci.accrual.service.batch.BatchFileService;
import gov.nih.nci.accrual.service.util.AccrualCsmUtil;
import gov.nih.nci.accrual.service.util.AccrualDiseaseServiceLocal;
import gov.nih.nci.accrual.service.util.CountryService;
import gov.nih.nci.accrual.service.util.SubjectAccrualCountService;
import gov.nih.nci.accrual.service.util.SubjectAccrualValidator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ed;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Patient;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.convert.StudySiteConverter;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Implementation of the subject accrual service.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveMethodLength", "PMD.ExcessiveClassLength" })
public class SubjectAccrualBeanLocal implements SubjectAccrualServiceLocal {
    private static final Logger LOG = Logger.getLogger(SubjectAccrualBeanLocal.class);
    private static final String IDENTIFIER = "identifier";

    /** Number of hours witch the batch processing thread will run before being killed. */
    public static final int BATCH_PROCESSING_THREAD_TIMEOUT_HOURS = 24;

    @EJB
    private PatientServiceLocal patientService;
    @EJB
    private StudySubjectServiceLocal studySubjectService;
    @EJB
    private PerformedActivityServiceLocal performedActivityService;
    @EJB
    private CountryService countryService;
    @EJB
    private SubjectAccrualCountService subjectAccrualCountSvc;
    @EJB
    private BatchFileService batchFileService;
    @EJB
    private SubjectAccrualValidator subjectAccrualValidator;
    @EJB
    private BatchUploadProcessingTaskServiceLocal batchUploadProcessingTaskService;
    @EJB
    private AccrualDiseaseServiceLocal diseaseSvc;
    
    private boolean useTestSeq = false;

    /** Cache for country Ii's. */
    private static CacheManager cacheManager;
    private static final String COUNTRY_CACHE_KEY = "COUNTRY_CACHE_KEY";
    private static final int CACHE_MAX_ELEMENTS = 50;
    private static final long CACHE_TIME = 43200;

    private Cache getCountryCache() {
        if (cacheManager == null || cacheManager.getStatus() != Status.STATUS_ALIVE) {
            cacheManager = CacheManager.create();
            Cache cache = new Cache(COUNTRY_CACHE_KEY, CACHE_MAX_ELEMENTS, null, false, null, false,
                CACHE_TIME, CACHE_TIME, false, CACHE_TIME, null, null, 0);
            cacheManager.removeCache(COUNTRY_CACHE_KEY);
            cacheManager.addCache(cache);
        }
        return cacheManager.getCache(COUNTRY_CACHE_KEY);
    }

    /**
     * Class used to run separate thread for processing batch submissions.
     */
    private class BatchFileProcessor implements Runnable {
        private final BatchFile batchFile;
        public BatchFileProcessor(BatchFile batchFile) {
            this.batchFile = batchFile;
        }

        @Override
        public void run() {
            try {
                batchUploadProcessingTaskService.processBatchUploads(batchFile);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    /**
     * Class used to manage batch processing thread.
     */
    private class BatchThreadManager implements Runnable {
        private final BatchFile batchFile;
        public BatchThreadManager(BatchFile batchFile) {
            this.batchFile = batchFile;
        }

        @Override
        public void run() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            try {
                executor.submit(new BatchFileProcessor(batchFile)).get(
                        BATCH_PROCESSING_THREAD_TIMEOUT_HOURS, TimeUnit.HOURS);
            } catch (Exception e) {
                LOG.error("Forcing shutdown of batch file processing thread.");
                executor.shutdownNow();
            }
        }
    }

    /**
     * The 1st 4 bytes of a byte of a file that indicates a zip file. Used to determine if the information
     * passed in to the submitBatchData method is a zip file.
     */
    private static final int ZIP_FILE_SIGNATURE = 0x504b0304;

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<SubjectAccrualDTO> manageSubjectAccruals(List<SubjectAccrualDTO> subjects) throws PAException {
        subjectAccrualValidator.validate(subjects);
        List<SubjectAccrualDTO> results = new ArrayList<SubjectAccrualDTO>();
        if (subjects != null) {
            for (SubjectAccrualDTO subject : subjects) {
                if (!AccrualUtil.isUserAllowedAccrualAccess(subject.getParticipatingSiteIdentifier())) {
                    throw new PAException("User does not have accrual access to site "
                            + subject.getParticipatingSiteIdentifier().getExtension());
                }
                StudySiteDTO participatingSite = PaServiceLocator.getInstance().getStudySiteService().get(
                                subject.getParticipatingSiteIdentifier());
                subject.setSubmissionTypeCode(CdConverter.convertToCd(AccrualSubmissionTypeCode.SERVICE_MSA));
                AccrualDisease disease = diseaseSvc.get(subject.getDiseaseIdentifier()); 
                if (disease != null) {
                    subject.setDiseaseIdentifier(IiConverter.convertToIi(disease.getId()));
                }

                Long userId = AccrualCsmUtil.getInstance().getCSMUser(
                        CaseSensitiveUsernameHolder.getUser()).getUserId();
                Long[] ids;
                if (ISOUtil.isIiNull(subject.getIdentifier())) {
                    ids = create(subject, participatingSite.getStudyProtocolIdentifier(), userId);
                } else {
                    StudySubject ssub = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, 
                            IiConverter.convertToLong(subject.getIdentifier()));
                    Long[] currentIds = {ssub.getId(), ssub.getPatient().getId()};
                    ids = update(subject, participatingSite.getStudyProtocolIdentifier(), userId, currentIds);
                }
                PaHibernateUtil.getCurrentSession().clear();
                StudySubject ssub = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, ids[0]);
                results.add(Converters.get(StudySubjectConverter.class).convertFromDomainToSubjectDTO(ssub));
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void createAccrualAccess(RegistryUser ru, Long ssId) throws PAException {
        StudySiteAccrualAccess ssaa = null;
        List<StudySiteAccrualAccess> ssasList = PaHibernateUtil.getCurrentSession()
                .createCriteria(StudySiteAccrualAccess.class)
                .add(Restrictions.eq("studySite.id", ssId))
                .add(Restrictions.eq("registryUser.id", ru.getId())).list();
        if (!ssasList.isEmpty()) {
            ssaa = ssasList.get(0);
        }
        Long userid = AccrualCsmUtil.getInstance().getCSMUser(ru.getCsmUser().getLoginName()).getUserId();
        Session session = PaHibernateUtil.getCurrentSession();
        if (ssaa == null) {
        String sql = "INSERT INTO study_site_accrual_access(identifier, study_site_identifier, status_code," 
            + "status_date_range_low, date_last_created, date_last_updated,user_last_created_id,"
            + "registry_user_id, source) VALUES (:identifier, :study_site_identifier, :status_code, "
            + ":status_date_range_low, now(), now(),:user_last_created_id, :registry_user_id, :source)";
            SQLQuery queryObject  = session.createSQLQuery(sql);
            queryObject.setParameter(IDENTIFIER, getNextId(session));
            queryObject.setParameter("study_site_identifier", ssId);
            queryObject.setParameter("status_code", ActiveInactiveCode.ACTIVE.getName());
            queryObject.setParameter("status_date_range_low", new Timestamp(new Date().getTime()));
            queryObject.setParameter("user_last_created_id", userid);
            queryObject.setParameter("registry_user_id", ru.getId());
            queryObject.setParameter("source", AccrualAccessSourceCode.ACC_GENERATED.getName());
            queryObject.executeUpdate();
        } else if (ssaa.getStatusCode().getName().equals(ActiveInactiveCode.INACTIVE.getName())) {
            String sql = "UPDATE study_site_accrual_access SET status_code='" + ActiveInactiveCode.ACTIVE.getName() 
                    + "',date_last_updated=now(),user_last_updated_id=" + userid
                    + " WHERE identifier=" + ssaa.getId(); 
            session.createSQLQuery(sql).executeUpdate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long[] create(SubjectAccrualDTO dto, Ii spIi, Long userId) throws PAException {
        if (!ISOUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Cannot create a subject accrual with an identifier set. Please use update().");
        }
        Long spId = IiConverter.convertToLong(spIi);
        Long newId = updatePatientTable(dto, userId, null);
        updateStudySubjectTable(dto, userId, null, spId, newId);

        Session session = PaHibernateUtil.getCurrentSession();
        String sql = "INSERT INTO performed_activity(identifier, study_protocol_identifier, performed_activity_type," 
                + "date_last_created, date_last_updated, study_subject_identifier, registration_date, " 
                + "user_last_created_id) "
                + "VALUES (:psmId, :spId, 'PerformedSubjectMilestone', now(), now(), :ssId, :regDate, :userId)";
        SQLQuery qry = session.createSQLQuery(sql);
        qry.setLong("psmId", newId);
        qry.setLong("spId", spId);
        qry.setLong("ssId", newId);
        qry.setTimestamp("regDate", TsConverter.convertToTimestamp(dto.getRegistrationDate()));
        qry.setLong("userId", userId);
        qry.executeUpdate();
        return new Long[]{newId, newId};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Long[] update(SubjectAccrualDTO dto, Ii spIi, Long userId, Long[] ids) throws PAException {
        Long ssId = IiConverter.convertToLong(dto.getIdentifier());
        if (ssId == null) {
            throw new PAException("Cannot update a subject accrual without an identifier set. Please use create().");
        }
        if (!ObjectUtils.equals(ssId, ids[0])) {
            throw new PAException("Ids passed into update() do not match dto.");
        }
        updateStudySubjectTable(dto, userId, ids, null, null);
        updatePatientTable(dto, userId, ids);
        String sql = "UPDATE performed_activity SET registration_date=:registrationDate, "
                   + "user_last_updated_id=:userId, date_last_updated=now() "
                   + "WHERE study_subject_identifier=:ssId ";
        SQLQuery qry = PaHibernateUtil.getCurrentSession().createSQLQuery(sql);
        qry.setTimestamp("registrationDate", TsConverter.convertToTimestamp(dto.getRegistrationDate()));
        qry.setLong("userId", userId);
        qry.setLong("ssId", ssId);
        qry.executeUpdate();
        return ids;
    }

    private Long updatePatientTable(SubjectAccrualDTO dto, Long userId, Long[] ids) 
            throws PAException {
        Element element = getCountryCache().get(dto.getCountryCode());
        if (element == null) {
            Country country = getCountryService().getByCode(CdConverter.convertCdToString(dto.getCountryCode()));
            Ii countryIi = IiConverter.convertToCountryIi(country.getId());
            element = new Element(dto.getCountryCode(), countryIi);
            getCountryCache().put(element);
        }

        PatientDto patientDTO = new PatientDto();
        patientDTO.setBirthDate(dto.getBirthDate());
        patientDTO.setCountryIdentifier((Ii) element.getValue());
        patientDTO.setEthnicCode(CdConverter.convertToCd(
                CDUSPatientEthnicityCode.getByCode(CdConverter.convertCdToString(dto.getEthnicity()))));
        patientDTO.setGenderCode(CdConverter.convertToCd(
                CDUSPatientGenderCode.getByCode(CdConverter.convertCdToString(dto.getGender()))));
        DSet<Cd> races = new DSet<Cd>();
        races.setItem(new HashSet<Cd>());
        if (dto.getRace() != null && dto.getRace().getItem() != null) {
            for (Cd cd : dto.getRace().getItem()) {
                races.getItem().add(CdConverter.convertToCd(
                        CDUSPatientRaceCode.getByCode(CdConverter.convertCdToString(cd))));
            }
        }
        patientDTO.setRaceCode(races);
        patientDTO.setZip(dto.getZipCode());
        Patient p = Converters.get(PatientConverter.class).convertFromDtoToDomain(patientDTO);

        String sql = "";
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery queryObject = null;
        Long result;
        if (ids != null) {
            result = ids[1];
            sql = "UPDATE patient SET race_code=:race_code, sex_code=:sex_code, ethnic_code=:ethnic_code, "
                    + "birth_date=:birth_date, status_code='ACTIVE', date_last_updated=now(), "
                    + "country_identifier=:country_identifier, zip=:zip , user_last_updated_id=:user_id, "
                    + "birth_month_excluded = :birth_month_excluded "
                    + "WHERE identifier= :identifier";
        } else {
            result = getNextId(session);
            sql = "INSERT INTO patient(identifier, race_code, sex_code, ethnic_code, birth_date, status_code, " 
                    + "date_last_created, date_last_updated, country_identifier, zip, user_last_created_id, " 
                    + "birth_month_excluded) " 
                    + "VALUES (:identifier, :race_code, :sex_code, :ethnic_code, :birth_date,'ACTIVE', "
                    + "now(), now(), :country_identifier, :zip, :user_id, :birth_month_excluded)";
        }
        queryObject = session.createSQLQuery(sql);
        queryObject.setParameter(IDENTIFIER, result);
        queryObject.setParameter("race_code", p.getRaceCode());
        queryObject.setParameter("sex_code", p.getSexCode().getName());
        queryObject.setParameter("ethnic_code", p.getEthnicCode().getName());
        queryObject.setParameter("birth_date", p.getBirthDate(), Hibernate.TIMESTAMP);
        queryObject.setParameter("birth_month_excluded", p.getBirthMonthExcluded());
        queryObject.setParameter("country_identifier", p.getCountry().getId());
        queryObject.setParameter("zip", p.getZip());
        queryObject.setParameter("user_id", userId);
        queryObject.executeUpdate();
        return result;
    }
    
    private synchronized Long getNextId(Session session) {
        long seq = 0;
        if (useTestSeq) {
            Random rand = new Random();
            seq = rand.nextLong();
        } else {
            SQLQuery queryObject = session.createSQLQuery("select nextval('hibernate_sequence')");
            seq = Long.valueOf(queryObject.uniqueResult().toString());
        }
        return seq;
    }

    private Long updateStudySubjectTable(SubjectAccrualDTO dto, Long userId, Long[] ids, Long spId, Long newPatientId) 
            throws PAException {
        String sql = "";
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery queryObject = null;
        dto.setPaymentMethod(CdConverter.convertToCd(
                CDUSPaymentMethodCode.getByCode(CdConverter.convertCdToString(dto.getPaymentMethod()))));
        Ii dii = dto.getDiseaseIdentifier();
        String diseaseString = ISOUtil.isIiNull(dii) ? "NULL" : IiConverter.convertToString(dii);
        Long result;
        if (ids != null) {
            result = ids[0];
            sql = "UPDATE study_subject SET study_site_identifier= :study_site_identifier, " 
                + "payment_method_code=:pmCode, status_code= :status_code, date_last_updated=now(), "
                + "assigned_identifier= :assigned_identifier, user_last_updated_id= :user_id, " 
                + "disease_identifier=" + diseaseString + ", "
                + "registration_group_id= :registration_group_id, submission_type= :submission_type "
                + "WHERE identifier= :identifier";

            queryObject = session.createSQLQuery(sql);
        } else {
            result = newPatientId;
            sql = "INSERT INTO study_subject(identifier, patient_identifier, study_protocol_identifier, " 
                    + "study_site_identifier, disease_identifier, payment_method_code, status_code," 
                    + "date_last_created, date_last_updated, assigned_identifier, submission_type," 
                    + "user_last_created_id, registration_group_id) "
                    + "VALUES (:identifier, :patient_identifier, :study_protocol_identifier, "
                    + ":study_site_identifier, " + diseaseString + ", :pmCode, :status_code, "
                    + "now(), now(), :assigned_identifier, :submission_type," 
                    + ":user_id,  :registration_group_id)";
            queryObject = session.createSQLQuery(sql);
            queryObject.setParameter("patient_identifier", newPatientId);
            queryObject.setParameter("study_protocol_identifier", spId);
        }
        queryObject.setParameter(IDENTIFIER, result);
        queryObject.setParameter("registration_group_id", StConverter.convertToString(dto.getRegistrationGroupId()));
        AccrualSubmissionTypeCode submissionType = CdConverter.convertCdToEnum(AccrualSubmissionTypeCode.class, 
                dto.getSubmissionTypeCode());
        queryObject.setParameter("submission_type", submissionType == null ? null : submissionType.getName());
        queryObject.setParameter("study_site_identifier", IiConverter.convertToLong(
                dto.getParticipatingSiteIdentifier()));
        queryObject.setParameter("status_code", FunctionalRoleStatusCode.ACTIVE.getName());
        queryObject.setParameter("assigned_identifier", StConverter.convertToString(dto.getAssignedIdentifier()));
        queryObject.setParameter("user_id", userId);
        PaymentMethodCode paymentMethod = CdConverter.convertCdToEnum(PaymentMethodCode.class, dto.getPaymentMethod());
        queryObject.setParameter("pmCode", paymentMethod == null ? null : paymentMethod.getName());
        queryObject.executeUpdate();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteSubjectAccrual(Ii subjectAccrualIi, String deleteReason) throws PAException {
        if (ISOUtil.isIiNull(subjectAccrualIi)) {
            throw new PAException("Study Subject Ii must be valid.");
        }
        StudySubject studySubject = (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class,
                IiConverter.convertToLong(subjectAccrualIi));
        if (studySubject == null) {
            throw new PAException("A Study Subject with id " + subjectAccrualIi.getExtension()
                    + " does not exist.");
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(IiConverter
                    .convertToStudySiteIi(studySubject.getStudySite().getId()))) {
            throw new PAException("User does not have accrual access to site.");
        }
        nullifyStudySubject(studySubject, deleteReason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSubjectAccrualCount(Ii participatingSiteIi, Int count, AccrualSubmissionTypeCode submissionType)
            throws PAException {
        if (!AccrualUtil.isValidTreatingSite(participatingSiteIi)) {
            throw new PAException(
                    "The treating site that is having an accrual count added to it does not exist.");
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(participatingSiteIi)) {
            throw new PAException("User does not have accrual access to site.");
        }
        doUpdateToSubjectAccrual(participatingSiteIi, count, submissionType);
    }

    private void doUpdateToSubjectAccrual(Ii participatingSiteIi, Int count, AccrualSubmissionTypeCode submissionType)
            throws PAException {
        StudySiteSubjectAccrualCount ssAccCount = getSubjectAccrualCountSvc()
        .getCountByStudySiteId(participatingSiteIi);
        if (ssAccCount == null) {
            StudySiteDTO ssDto = PaServiceLocator.getInstance().getStudySiteService().get(participatingSiteIi);
            StudySite ss = new StudySiteConverter().convertFromDtoToDomain(ssDto);
            ssAccCount = new StudySiteSubjectAccrualCount();
            ssAccCount.setStudySite(ss);
            ssAccCount.setStudyProtocol(ss.getStudyProtocol());
        } else {
            if (ObjectUtils.equals(ssAccCount.getAccrualCount(), IntConverter.convertToInteger(count))) {
                return;
            }
        }
        ssAccCount.setAccrualCount(count.getValue());
        ssAccCount.setSubmissionTypeCode(submissionType);
        List<StudySiteSubjectAccrualCount> counts = new ArrayList<StudySiteSubjectAccrualCount>();
        counts.add(ssAccCount);
        getSubjectAccrualCountSvc().save(counts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateSubjectAccrualCount(Ii participatingSiteIi, Int count, RegistryUser user,
            AccrualSubmissionTypeCode submissionType) throws PAException {
        if (ISOUtil.isIiNull(participatingSiteIi)) {
            throw new PAException("Study Site Ii cannot be null.");
        }
        if (!AccrualUtil.isUserAllowedAccrualAccess(participatingSiteIi, user)) {
            throw new PAException("User does not have accrual access to site.");
        }
        doUpdateToSubjectAccrual(participatingSiteIi, count, submissionType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void submitBatchData(Ed batchFile) throws PAException {
        if (ISOUtil.isEdNull(batchFile)) {
            throw new PAException("Null batch files are not allowed. Please provide a valid batch file.");
        }
        String filePath = generateFileLocation(batchFile);
        RegistryUser submitter = getBatchSubmitter();
        writeBatchFileToFilesystem(batchFile, filePath);

        BatchFile batch = new BatchFile();
        batch.setSubmitter(submitter);
        batch.setFileLocation(filePath);
        batch.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        getBatchFileService().save(batch);
        processBatchFiles(batch);
    }

    @Override
    public void processBatchFiles(BatchFile batchFile) {
        Thread batchThread = new Thread(new BatchThreadManager(batchFile));
        batchThread.start();
    }


    private void writeBatchFileToFilesystem(Ed batchFile, String filePath) throws PAException {
        try {
            File file = new File(filePath);
            FileUtils.writeByteArrayToFile(file, batchFile.getData());
        } catch (IOException e) {
            throw new PAException("An error has occurred while trying to submit your batch data. Please try again.", e);
        }
    }

    private String generateFileLocation(Ed batchFile) throws PAException {
        try {
            DataInputStream in =
                new DataInputStream(new BufferedInputStream(new ByteArrayInputStream(batchFile.getData())));
            String extension = in.readInt() == ZIP_FILE_SIGNATURE ? ".zip" : ".txt";
            return PaEarPropertyReader.getAccrualBatchUploadPath() + File.separator + UUID.randomUUID() + "-batchFile"
                + extension;
        } catch (IOException e) {
            throw new PAException("Unable to determine whether batch is an archive or a single file.", e);
        }
    }

    private RegistryUser getBatchSubmitter() throws PAException {
        return PaServiceLocator.getInstance().getRegistryUserService().getUser(AccrualCsmUtil.getInstance()
                .getCSMUser(CaseSensitiveUsernameHolder.getUser()).getLoginName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubjectAccrualDTO> search(Ii studyIdentifier, Ii participatingSiteIdentifier, Ts startDate, Ts endDate,
            LimitOffset pagingParams) throws PAException {
        if (ISOUtil.isIiNull(studyIdentifier)) {
            throw new PAException("Study identifier must not be null when calling seach.");
        }

        List<StudySubjectDto> studySubjectDtoList = getStudySubjectService()
            .search(IiConverter.convertToLong(studyIdentifier), IiConverter.convertToLong(participatingSiteIdentifier),
                    TsConverter.convertToTimestamp(startDate), TsConverter.convertToTimestamp(endDate), pagingParams);

        return convertStudySubjectDtoToSubjectAccrualDTOList(studySubjectDtoList);
    }

    private List<SubjectAccrualDTO> convertStudySubjectDtoToSubjectAccrualDTOList(
            List<StudySubjectDto> studySubjectDtoList) {
        List<SubjectAccrualDTO> result = new ArrayList<SubjectAccrualDTO>();
        for (StudySubjectDto studySubjectDto : studySubjectDtoList) {
            Long studySubjectId = IiConverter.convertToLong(studySubjectDto.getIdentifier());
            StudySubject studySubject =
                    (StudySubject) PaHibernateUtil.getCurrentSession().get(StudySubject.class, studySubjectId);
            result.add(Converters.get(StudySubjectConverter.class).convertFromDomainToSubjectDTO(studySubject));

        }
        return result;
    }

    private void nullifyStudySubject(StudySubject ss, String deleteReason) {
        ss.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        ss.setDeleteReason(deleteReason);
        Patient patient = ss.getPatient();
        patient.setStatusCode(StructuralRoleStatusCode.NULLIFIED);
        PaHibernateUtil.getCurrentSession().merge(ss);
        PaHibernateUtil.getCurrentSession().merge(patient);
    }

    /**
     * @param subjectAccrualCountSvc the subjectAccrualCountSvc to set
     */
    public void setSubjectAccrualCountSvc(SubjectAccrualCountService subjectAccrualCountSvc) {
        this.subjectAccrualCountSvc = subjectAccrualCountSvc;
    }

    /**
     * @return the subjectAccrualCountSvc
     */
    public SubjectAccrualCountService getSubjectAccrualCountSvc() {
        return subjectAccrualCountSvc;
    }

    /**
     * @return the patientService
     */
    public PatientServiceLocal getPatientService() {
        return patientService;
    }

    /**
     * @param patientService the patientService to set
     */
    public void setPatientService(PatientServiceLocal patientService) {
        this.patientService = patientService;
    }

    /**
     * @return the studySubjectService
     */
    public StudySubjectServiceLocal getStudySubjectService() {
        return studySubjectService;
    }

    /**
     * @param studySubjectService the studySubjectService to set
     */
    public void setStudySubjectService(StudySubjectServiceLocal studySubjectService) {
        this.studySubjectService = studySubjectService;
    }

    /**
     * @return the performedActivityService
     */
    public PerformedActivityServiceLocal getPerformedActivityService() {
        return performedActivityService;
    }

    /**
     * @param performedActivityService the performedActivityService to set
     */
    public void setPerformedActivityService(PerformedActivityServiceLocal performedActivityService) {
        this.performedActivityService = performedActivityService;
    }

    /**
     * @return the countryService
     */
    public CountryService getCountryService() {
        return countryService;
    }

    /**
     * @param countryService the countryService to set
     */
    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    /**
     * @return the batchFileService
     */
    public BatchFileService getBatchFileService() {
        return batchFileService;
    }

    /**
     * @param batchFileService the batchFileService to set
     */
    public void setBatchFileService(BatchFileService batchFileService) {
        this.batchFileService = batchFileService;
    }

    /**
     * @param subjectAccrualValidator the subjectAccrualValidator to set
     */
    public void setSubjectAccrualValidator(SubjectAccrualValidator subjectAccrualValidator) {
        this.subjectAccrualValidator = subjectAccrualValidator;
    }
    /**
     * @param diseaseSvc the diseaseSvc to set
     */
    public void setDiseaseSvc(AccrualDiseaseServiceLocal diseaseSvc) {
        this.diseaseSvc = diseaseSvc;
    }

    /**
     * @return useTestSeq
     */
    public boolean isUseTestSeq() {
        return useTestSeq;
    }

    /**
     * @param useTestSeq the useTestSeq to set
     */
    public void setUseTestSeq(boolean useTestSeq) {
        this.useTestSeq = useTestSeq;
    }
}
