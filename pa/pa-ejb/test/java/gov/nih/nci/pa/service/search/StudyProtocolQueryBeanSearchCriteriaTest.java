/**
 * 
 */
package gov.nih.nci.pa.service.search;

import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import junit.framework.Assert;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Denis G. Krylov
 * 
 */
public class StudyProtocolQueryBeanSearchCriteriaTest extends
        AbstractHibernateTestCase {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testQueryByAnyIdentifierType() {
        StudyProtocol sp = new StudyProtocol();
        StudyProtocolOptions options = new StudyProtocolOptions();
        options.setAnyTypeIdentifier("ANY_ID");
        StudyProtocolQueryBeanSearchCriteria criteria = new StudyProtocolQueryBeanSearchCriteria(
                sp, options);
        Query query = criteria.getQuery("", false);
        String hql = query.getQueryString();
        Assert.assertTrue(hql
                .contains("( exists (select oi.extension from obj.otherIdentifiers oi where oi.root = "
                        + "'2.16.840.1.113883.3.26.4.3' and upper(oi.extension) like '%ANY_ID%') or exists "
                        + "(select oi.extension from obj.otherIdentifiers oi where oi.root = '2.16.840.1.113883.19' "
                        + "and upper(oi.extension) like '%ANY_ID%') or exists (select ssdcp.id from StudySite ssdcp "
                        + "where ssdcp.studyProtocol.id = obj.id and lower(ssdcp.localStudyProtocolIdentifier) like "
                        + "'%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode and "
                        + "ssdcp.researchOrganization.organization.name='National Cancer Institute Division of Cancer Prevention') "
                        + " or exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and"
                        + " lower(ssdcp.localStudyProtocolIdentifier) like '%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode"
                        + " and ssdcp.researchOrganization.organization.name='ClinicalTrials.gov')  or exists"
                        + " (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and"
                        + " lower(ssdcp.localStudyProtocolIdentifier) like '%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode"
                        + " and ssdcp.researchOrganization.organization.name='Cancer Therapy Evaluation Program')  or exists"
                        + " (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and lower(ssdcp.localStudyProtocolIdentifier) "
                        + "like '%any_id%' and ssdcp.functionalCode = :leadOrgFunctionalCode)  )"));
    }

}
