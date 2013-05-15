package gov.nih.nci.accrual.service.util;

import gov.nih.nci.accrual.service.StudySubjectServiceLocal;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.AccrualDisease;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.lov.PrimaryPurposeCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.search.AnnotatedBeanSearchCriteria;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.data.search.SortCriterion;
import com.fiveamsolutions.nci.commons.search.SearchCriteria;
import com.fiveamsolutions.nci.commons.service.AbstractBaseSearchBean;

/**
 * @author Hugh Reinhart
 * @since Dec 14, 2012
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
public class AccrualDiseaseBeanLocal extends AbstractBaseSearchBean<AccrualDisease> 
        implements AccrualDiseaseServiceLocal {

    private static final Logger LOG = Logger.getLogger(AccrualDiseaseBeanLocal.class);

    private static final String DEFAULT_CODE_SYSTEM = "ICD9";

    /**
     * Class required by the AbstractBaseSearchBean sort functionality.
     */
    private enum AccrualDiseaseSortCriterion implements SortCriterion<AccrualDisease> {
        DISEASE_CODE("diseaseCode", null);

        private final String orderField;
        private final String leftJoinField;

        private AccrualDiseaseSortCriterion(String orderField, String leftJoinField) {
            this.orderField = orderField;
            this.leftJoinField = leftJoinField;
        }

        /**
         * {@inheritDoc}
         */
        public String getOrderField() {
            return orderField;
        }
        /**
         * {@inheritDoc}
         */
        public String getLeftJoinField() {
            return leftJoinField;
        }
    }

    @EJB
    private StudySubjectServiceLocal studySubjectSvc;
    @EJB
    private SearchStudySiteService searchStudySiteSvc;
    

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease get(Long id) {
        return (AccrualDisease) PaHibernateUtil.getCurrentSession().get(AccrualDisease.class, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease get(Ii ii) {
        if (ISOUtil.isIiNull(ii)) {
            return null;
        }
        boolean isCode = false;
        String cs = ii.getIdentifierName();
        if (StringUtils.isNotBlank(cs)) {
            List<String> csList = getValidCodeSystems(null);
            if (csList.contains(cs)) {
                isCode = true;
            }
        }
        if (isCode) {
            return getByCode(ii.getExtension());
        }
        return get(IiConverter.convertToLong(ii));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccrualDisease getByCode(String diseaseCode) {
        AccrualDisease criteria = new AccrualDisease();
        criteria.setDiseaseCode(diseaseCode);
        List<AccrualDisease> dList = search(criteria);
        return dList.isEmpty() ? null : dList.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccrualDisease> search(AccrualDisease searchCriteria) {
        PageSortParams<AccrualDisease> params = new PageSortParams<AccrualDisease>(PAConstants.MAX_SEARCH_RESULTS, 0, 
                AccrualDiseaseBeanLocal.AccrualDiseaseSortCriterion.DISEASE_CODE, false);
        SearchCriteria<AccrualDisease> criteria = new AnnotatedBeanSearchCriteria<AccrualDisease>(searchCriteria);
        List<AccrualDisease> result = super.search(criteria, params);
        if (result.isEmpty() && searchCriteria.getDiseaseCode().toUpperCase(Locale.US).charAt(0) == 'C') {
            searchCriteria.setCodeSystem("ICD-O-3");
            int length = searchCriteria.getDiseaseCode().length();
            String appendedDC = searchCriteria.getDiseaseCode().substring(0, length - 1) 
                    + "." + searchCriteria.getDiseaseCode().substring(length - 1, length);
            searchCriteria.setDiseaseCode(appendedDC);
            criteria = new AnnotatedBeanSearchCriteria<AccrualDisease>(searchCriteria);
            result = super.search(criteria, params);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTrialCodeSystem(Long spId) {
        String result = null;
        if (spId != null) {
            try {
                StudySubject ss = studySubjectSvc.searchActiveByStudyProtocol(spId);
                if (ss != null) {
                    result = ss.getDisease() != null ? ss.getDisease().getCodeSystem() 
                                : ss.getSiteDisease().getCodeSystem();
                }
            } catch (PAException e) {
                LOG.error(e);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<String> getValidCodeSystems(Long spId) {
        LinkedList<String> result = new LinkedList<String>();
        String existing = getTrialCodeSystem(spId);
        if (existing != null) {
            result.add(existing);
        } else {
            try {
                Session session = PaHibernateUtil.getCurrentSession();
                String hql = "select distinct d.codeSystem from AccrualDisease d order by d.codeSystem";
                Query query = session.createQuery(hql);
                List<String> qList = query.list();
                for (String code : qList) {
                    if (DEFAULT_CODE_SYSTEM.equals(code)) {
                        result.addFirst(code);
                    } else {
                        result.add(code);
                    }
                }
            } catch (Exception e) {
                LOG.error(e);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean diseaseCodeMandatory(Long spId) {
        Ii spIi = IiConverter.convertToStudyProtocolIi(spId);
        try {
            if (searchStudySiteSvc.isStudySiteHasDCPId(spIi)) {
                return false;
            }
            StudyProtocolDTO spDto = PaServiceLocator.getInstance().getStudyProtocolService().getStudyProtocol(spIi);
            if (PrimaryPurposeCode.PREVENTION.equals(PrimaryPurposeCode
                    .getByCode(CdConverter.convertCdToString(spDto
                            .getPrimaryPurposeCode())))) {
                return false;
            }
        } catch (PAException e) {
            LOG.error(e);
        }
        return true;
    }

    /**
     * @param studySubjectSvc the studySubjectSvc to set
     */
    public void setStudySubjectSvc(StudySubjectServiceLocal studySubjectSvc) {
        this.studySubjectSvc = studySubjectSvc;
    }

    /**
     * @param searchStudySiteSvc the searchStudySiteSvc to set
     */
    public void setSearchStudySiteSvc(SearchStudySiteService searchStudySiteSvc) {
        this.searchStudySiteSvc = searchStudySiteSvc;
    }
}
