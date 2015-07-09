/**
 * 
 */
package gov.nih.nci.pa.service.search;

import static org.mockito.Mockito.*;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.OnholdReasonCode;
import gov.nih.nci.pa.service.search.StudyProtocolOptions.MilestoneFilter;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.collections.ListUtils;
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
        ServiceLocator paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        LookUpTableServiceRemote lookUpTableServiceRemote = mock(LookUpTableServiceRemote.class);
        when(lookUpTableServiceRemote.getPropertyValue(eq("nci.poid"))).thenReturn("154376");
        when (paSvcLoc.getLookUpTableService()).thenReturn(lookUpTableServiceRemote);
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
                        + " or exists (select ssdcp.id from StudySite ssdcp "
                        + "where ssdcp.studyProtocol.id = obj.id and lower(ssdcp.localStudyProtocolIdentifier) like "
                        + "'%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode and "
                        + "ssdcp.researchOrganization.organization.name='NCI - Center for Cancer Research') "
                        + " or exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and"
                        + " lower(ssdcp.localStudyProtocolIdentifier) like '%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode"
                        + " and ssdcp.researchOrganization.organization.name='ClinicalTrials.gov')  or exists"
                        + " (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and"
                        + " lower(ssdcp.localStudyProtocolIdentifier) like '%any_id%' and ssdcp.functionalCode = :idAssignerFunctionalCode"
                        + " and ssdcp.researchOrganization.organization.name='Cancer Therapy Evaluation Program')  or exists"
                        + " (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and lower(ssdcp.localStudyProtocolIdentifier) "
                        + "like '%any_id%' and ssdcp.functionalCode = :leadOrgFunctionalCode)  )"));
    }
    
    @Test
    public final void testComplexDashboardQuery() {
        StudyProtocol sp = new StudyProtocol();
        StudyProtocolOptions options = new StudyProtocolOptions();
        options.setCheckedOut(true);
        options.setCurrentOrPreviousMilestone(MilestoneCode.SUBMISSION_ACCEPTED);
        options.setExcludeCtepDcpTrials(true);
        options.setHoldRecordExists(true);
        options.setLockedTrials(true);
        options.setLockedUser("ctrp");
        options.setMilestoneFilters(Arrays.asList(new MilestoneFilter(Arrays
                .asList(MilestoneCode.SUBMISSION_ACCEPTED),
                ListUtils.EMPTY_LIST)));
        options.setNciSponsored(true);
        options.setOnholdReasons(Arrays.asList(OnholdReasonCode.INVALID_GRANT));
        options.getProcessingPriority().add(1);
        options.setSearchCTEPAndDCPTrials(true);
        options.setSearchOffHoldTrials(true);
        options.setSearchOnHoldTrials(true);
        options.setSubmittedOnOrAfter(new Date(1000000));
        options.setSubmittedOnOrBefore(new Date(2000000));
        options.setSubmitterAffiliateOrgId("123");
        options.setUserId(1L);
        options.setSearchCTEPTrials(true);
        options.setSearchDCPTrials(true);        
        StudyProtocolQueryBeanSearchCriteria criteria = new StudyProtocolQueryBeanSearchCriteria(
                sp, options);
        Query query = criteria.getQuery("", false);
        String hql = query.getQueryString();
        Assert.assertTrue(hql
                .contains("(select count(id) from obj.studyMilestones where milestoneCode=:currentOrPreviousMilestone) > 0  AND  ( (sms.milestoneCode in (:activeMilestones0)  and sms.currentlyActive = true) )  AND  (sowner.id = :studyOwnerParam or ((sowner.id <> :studyOwnerParam or sowner.id is null) and dws.statusCode != :studyOwnerDWSParam))  AND  (exists (select id from RegistryUser where str(affiliatedOrganizationId) = :affiliatedOrganizationId and csmUser.userId=obj.userLastCreated.userId))  AND  (select count(id) from obj.studyOnholds where onholdDate is not null and offholdDate is null) > 0  AND  (select count(id) from obj.studyOnholds where onholdDate is not null) > 0  AND  (select count(id) from obj.studyOnholds where onholdDate is not null and onholdReasonCode in (:onholdReasons)) > 0  AND  (select count(id) from obj.studyCheckout where userIdentifier = :checkedOutUserParam and checkInDate is null) > 0  AND  (select count(id) from obj.studyCheckout where userIdentifier is not null and checkInDate is null) > 0  AND  (obj.dateLastCreated >=:submittedOnOrAfter)  AND  (obj.dateLastCreated <=:submittedOnOrBefore)  AND  ( exists (select sponsor.id from StudySite sponsor where sponsor.studyProtocol.id = obj.id and sponsor.functionalCode = :sponsorFuncCode and sponsor.researchOrganization.organization.identifier='154376'))   AND  exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and ssdcp.localStudyProtocolIdentifier is not null and ssdcp.functionalCode = :idAssignerFunctionalCode and ssdcp.researchOrganization.organization.name='National Cancer Institute Division of Cancer Prevention')   AND  exists (select ssctep.id from StudySite ssctep where ssctep.studyProtocol.id = obj.id and ssctep.localStudyProtocolIdentifier is not null and ssctep.functionalCode = :idAssignerFunctionalCode and ssctep.researchOrganization.organization.name='Cancer Therapy Evaluation Program')  and not exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and ssdcp.localStudyProtocolIdentifier is not null and ssdcp.functionalCode = :idAssignerFunctionalCode and ssdcp.researchOrganization.organization.name='National Cancer Institute Division of Cancer Prevention')   AND  (exists (select ssctep.id from StudySite ssctep where ssctep.studyProtocol.id = obj.id and ssctep.localStudyProtocolIdentifier is not null and ssctep.functionalCode = :idAssignerFunctionalCode and ssctep.researchOrganization.organization.name='Cancer Therapy Evaluation Program')  or exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and ssdcp.localStudyProtocolIdentifier is not null and ssdcp.functionalCode = :idAssignerFunctionalCode and ssdcp.researchOrganization.organization.name='National Cancer Institute Division of Cancer Prevention') )   AND  not exists (select ssctep.id from StudySite ssctep where ssctep.studyProtocol.id = obj.id and ssctep.localStudyProtocolIdentifier is not null and ssctep.functionalCode = :idAssignerFunctionalCode and ssctep.researchOrganization.organization.name='Cancer Therapy Evaluation Program')  and not exists (select ssdcp.id from StudySite ssdcp where ssdcp.studyProtocol.id = obj.id and ssdcp.localStudyProtocolIdentifier is not null and ssdcp.functionalCode = :idAssignerFunctionalCode and ssdcp.researchOrganization.organization.name='National Cancer Institute Division of Cancer Prevention')    AND  (obj.processingPriority in (:processingPriorityParam))"));
    }
    
    
    @Test
    public final void testResultsDashBoardQuery() {
        StudyProtocol sp = new StudyProtocol();
        StudyProtocolOptions options = new StudyProtocolOptions();
        List<Boolean> sec801Inds = new ArrayList<Boolean>();
        sec801Inds.add(true);
        sec801Inds.add(false);
        options.setSection801Indicators(sec801Inds);
        options.setPcdFromDate(new java.util.Date());
        options.setPcdToDate(new java.util.Date());
        List<ActualAnticipatedTypeCode> dateTypes = new ArrayList<ActualAnticipatedTypeCode>();
        dateTypes.add(ActualAnticipatedTypeCode.ACTUAL);
        dateTypes.add(ActualAnticipatedTypeCode.ANTICIPATED);
        options.setPcdDateTypes(dateTypes);
        StudyProtocolQueryBeanSearchCriteria criteria = new StudyProtocolQueryBeanSearchCriteria(
                sp, options);
        Query query = criteria.getQuery("", false);
        String hql = query.getQueryString();
        System.out.println(hql);
        Assert.assertTrue(hql.contains("WHERE  obj.section801Indicator in (:section801Indicator)  AND  obj.dates.primaryCompletionDate >= :pcdFromDate AND  obj.dates.primaryCompletionDate <= :pcdToDate AND  obj.dates.primaryCompletionDateTypeCode in (:pcdDateType) "));
    }
  
}
