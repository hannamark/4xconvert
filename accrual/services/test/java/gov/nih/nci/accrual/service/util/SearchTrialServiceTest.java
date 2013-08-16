/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.accrual.dto.util.AccrualCountsDto;
import gov.nih.nci.accrual.dto.util.SearchTrialCriteriaDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.service.SubjectAccrualServiceBean;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.accrual.util.ServiceLocatorAccInterface;
import gov.nih.nci.accrual.util.ServiceLocatorPaInterface;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Bl;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolDates;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.PaymentMethodCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteServiceRemote;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Hugh Reinhart
 * @since Aug 25, 2009
 */
public class SearchTrialServiceTest extends AbstractServiceTest<SearchTrialService> {
	SearchTrialBean bean;

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        bean = new SearchTrialBean();
    }

    @Test
    public void searchUser1() throws Exception {
        search(TestSchema.registryUsers.get(1).getId(), true);
    }

    @Test
    public void searchUser2() throws Exception {
        search(0L, false);
    }

    public void search(Long registryUserId, boolean shouldBeAuthorized) throws Exception {
        Ii ruIi = IiConverter.convertToIi(registryUserId);
        int goodCount = shouldBeAuthorized ? 1: 0;

        // second study is inactive
        List<SearchTrialResultDto> results = bean.search(new SearchTrialCriteriaDto(), ruIi);
        assertEquals(goodCount, results.size());
        if (results.size() > 0) {
            SearchTrialResultDto str = results.get(0);
            St aid = str.getAssignedIdentifier();
            assertNotNull(aid);
            Ii id = str.getIdentifier();
            assertNotNull(id);
            St lon = str.getLeadOrgName();
            assertNotNull(lon);
            St loti = str.getLeadOrgTrialIdentifier();
            assertNotNull(loti);
            St ot = str.getOfficialTitle();
            assertNotNull(ot);
            St pi = str.getPrincipalInvestigator();
            assertNotNull(pi);
            Ii spi = str.getStudyProtocolIdentifier();
            assertNotNull(spi);
            Cd status = str.getStudyStatusCode();
            assertNotNull(status);
        }

        // get by assigned identifier
        SearchTrialCriteriaDto crit = new SearchTrialCriteriaDto();
        Ii assignedId = new Ii();
        for (Ii id : TestSchema.studyProtocols.get(0).getOtherIdentifiers()) {
            if (StringUtils.equals(id.getRoot(), IiConverter.STUDY_PROTOCOL_ROOT)) {
                assignedId = id;
                break;
            }
        }

        crit.setAssignedIdentifier(StConverter.convertToSt(assignedId.getExtension()));
        assertEquals(goodCount, bean.search(crit, ruIi).size());
        crit.setAssignedIdentifier(BST);
        assertEquals(0, bean.search(crit, ruIi).size());

        // get by title
        crit = new SearchTrialCriteriaDto();
        crit.setOfficialTitle(StConverter.convertToSt(TestSchema.studyProtocols.get(0).getOfficialTitle()));
        assertEquals(goodCount, bean.search(crit, ruIi).size());
        crit.setOfficialTitle(BST);
        assertEquals(0, bean.search(crit, ruIi).size());

        // get by title
        crit = new SearchTrialCriteriaDto();
        crit.setLeadOrgTrialIdentifier(StConverter.convertToSt(TestSchema.studySites.get(0).getLocalStudyProtocolIdentifier()));
        assertEquals(goodCount, bean.search(crit, ruIi).size());
        crit.setLeadOrgTrialIdentifier(BST);
        assertEquals(0, bean.search(crit, ruIi).size());
    }

    @Test
    public void getTrialSummaryByStudyProtocolIi() throws Exception {
        SearchTrialResultDto result = bean.getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(TestSchema.studyProtocols.get(0).getId()));
        assertNotNull(result);
        assertEquals(result.getTrialType().getValue(), AccrualUtil.INTERVENTIONAL);

        try {
            bean.getTrialSummaryByStudyProtocolIi(BII);
            fail();
        } catch (PAException e) {
            // expected behavior
        }
    }

    @Test
    public void testNonInterventional1() throws Exception {
		StudyProtocol sp = new NonInterventionalStudyProtocol();
        sp.setOfficialTitle("Test Non-interventional study");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2013-00004");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        TestSchema.addUpdObject(sp);
        
        ServiceLocatorAccInterface accSvcLocator = mock(ServiceLocatorAccInterface.class);
        when(accSvcLocator.getSubjectAccrualService()).thenReturn(new SubjectAccrualServiceBean());
        AccrualServiceLocator.getInstance().setServiceLocator(accSvcLocator);
        
        SearchTrialResultDto result = bean.getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(sp.getId()));
        assertEquals(AccrualUtil.BOTH, result.getAccrualSubmissionLevel().getValue());
        assertEquals(result.getTrialType().getValue(), AccrualUtil.NONINTERVENTIONAL);
	}

    @Test
    public void testNonInterventional2() throws Exception {
		StudyProtocol sp = new NonInterventionalStudyProtocol();
        sp.setOfficialTitle("Test Non-interventional study");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2013-00005");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        TestSchema.addUpdObject(sp);
            
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(TestSchema.registryUsers.get(0));
        ssaa.setStudySite(TestSchema.studySites.get(1));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(ssaa);
        
        StudySubject subj = new StudySubject();
        subj.setDisease(TestSchema.diseases.get(0));
        subj.setPatient(TestSchema.patients.get(0));
        subj.setAssignedIdentifier("001");
        subj.setPaymentMethodCode(PaymentMethodCode.MEDICARE);
        subj.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        subj.setStudyProtocol(sp);
        subj.setStudySite(TestSchema.studySites.get(1));
        subj.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        subj.setDateLastCreated(PAUtil.dateStringToDateTime("7/9/2013"));
        TestSchema.addUpdObject(subj);
        
        ServiceLocatorAccInterface accSvcLocator = mock(ServiceLocatorAccInterface.class);
        when(accSvcLocator.getSubjectAccrualService()).thenReturn(new SubjectAccrualServiceBean());
        AccrualServiceLocator.getInstance().setServiceLocator(accSvcLocator);
        
        SearchTrialResultDto result = bean.getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(sp.getId()));
        assertEquals(AccrualUtil.SUBJECT_LEVEL, result.getAccrualSubmissionLevel().getValue());
        assertEquals(result.getTrialType().getValue(), AccrualUtil.NONINTERVENTIONAL);
	}

    @Test
    public void testNonInterventional3() throws Exception {
		SearchTrialResultDto result;
		StudyProtocol sp = new NonInterventionalStudyProtocol();
        sp.setOfficialTitle("Test Non-interventional study");
        StudyProtocolDates dates = sp.getDates();
        dates.setStartDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        dates.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("7/9/2013"));
        dates.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);

        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii assignedId = IiConverter.convertToAssignedIdentifierIi("NCI-2013-00006");
        studySecondaryIdentifiers.add(assignedId);

        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(false);
        TestSchema.addUpdObject(sp);
                     
        StudySiteAccrualAccess ssaa = new StudySiteAccrualAccess();
        ssaa.setRegistryUser(TestSchema.registryUsers.get(0));
        ssaa.setStudySite(TestSchema.studySites.get(0));
        ssaa.setStatusCode(ActiveInactiveCode.ACTIVE);
        ssaa.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        ssaa.setSource(AccrualAccessSourceCode.PA_SITE_REQUEST);
        TestSchema.addUpdObject(ssaa);
        
        StudySiteSubjectAccrualCount cnt = new StudySiteSubjectAccrualCount();
        cnt.setStudySite(TestSchema.studySites.get(0));
        cnt.setAccrualCount(10);
        cnt.setStudyProtocol(sp);
        cnt.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        TestSchema.addUpdObject(cnt);
        
        ServiceLocatorAccInterface accSvcLocator = mock(ServiceLocatorAccInterface.class);
        when(accSvcLocator.getSubjectAccrualService()).thenReturn(new SubjectAccrualServiceBean());
        AccrualServiceLocator.getInstance().setServiceLocator(accSvcLocator);
        
        result = bean.getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(sp.getId()));
        assertEquals(AccrualUtil.SUMMARY_LEVEL, result.getAccrualSubmissionLevel().getValue());
        assertEquals(result.getTrialType().getValue(), AccrualUtil.NONINTERVENTIONAL);
	}

    @Test
    public void getStudyOverallStatus() throws Exception {
        List<SearchTrialResultDto> rList = bean.search(new SearchTrialCriteriaDto(), BII);
        for (SearchTrialResultDto r : rList) {
            if (IiConverter.convertToLong(r.getStudyProtocolIdentifier()).equals(TestSchema.studyProtocols.get(0).getId())) {
                assertEquals(StudyStatusCode.ACTIVE, StudyStatusCode.getByCode(r.getStudyStatusCode().getCode()));
            }
            if (IiConverter.convertToLong(r.getStudyProtocolIdentifier()).equals(TestSchema.studyProtocols.get(1).getId())) {
                assertNull(StudyStatusCode.getByCode(r.getStudyStatusCode().getCode()));
            }
        }
    }

    @Test
    public void searchTrialExceptions() throws Exception {
        try {
            List<SearchTrialResultDto> results = bean.search(null, null);
            assertEquals(0, results.size());
        } catch (Exception ex) {
            // expected
        }

        try {
            List<SearchTrialResultDto> results = bean.search(new SearchTrialCriteriaDto(), null);
            assertEquals(0, results.size());
        } catch (Exception ex) {
            // expected
        }

        try {
            Bl flag = bean.isAuthorized(null, null);
            assertNotNull(flag);
            assertEquals(false, flag.getValue());
        } catch (Exception ex) {
            // expected
        }
    }
    
    @Test
    public void getAccrualCountsForUser() throws Exception {
    	bean = mock(SearchTrialBean.class);
    	when(bean.getAccrualCountsForUser(any(RegistryUser.class))).thenCallRealMethod();
    	when(bean.getTrialSummaryByStudyProtocolIi(any(Ii.class))).thenCallRealMethod();
    	//when(bean.setTrialCountAndMaxDate(any(AccrualCountsDto.class), any(org.hibernate.Query.class)))
    	
    	OrganizationCorrelationServiceRemote ocsr = mock(OrganizationCorrelationServiceRemote.class);
    	when(PaRegistry.getOrganizationCorrelationService()).thenReturn(ocsr);
    	when(ocsr.getPOOrgIdentifierByIdentifierType(anyString())).thenReturn("1");
    	when(ocsr.getPoResearchOrganizationByEntityIdentifier(any(Ii.class))).thenReturn(new Ii());
    	ServiceLocatorPaInterface svcLocal = mock(ServiceLocatorPaInterface.class);
    	StudySiteServiceRemote studySiteSvc = mock(StudySiteServiceRemote.class);
    	StudySiteDTO test = new StudySiteDTO();
    	test.setLocalStudyProtocolIdentifier(StConverter.convertToSt("Testing"));
    	List<StudySiteDTO> result = new ArrayList<StudySiteDTO>();
    	result.add(test);
        when(studySiteSvc.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenReturn(result);
        when(svcLocal.getStudySiteService()).thenReturn(studySiteSvc);
        PaServiceLocator.getInstance().setServiceLocator(svcLocal);
        
        //((SearchTrialBean) bean).setDummyDateAndCounts(true);
        List<AccrualCountsDto> resultList =  bean.getAccrualCountsForUser(TestSchema.registryUsers.get(1));
        assertEquals(1, resultList.size());
        assertNotNull(resultList.get(0).getNciNumber());
        assertNotNull(resultList.get(0).getLeadOrgName());
        assertNotNull(resultList.get(0).getAffiliateOrgCount());
        assertNull(resultList.get(0).getDate());
        assertNotNull(resultList.get(0).getLeadOrgTrialIdentifier());
        assertNotNull(resultList.get(0).getNctNumber());
        assertNull(resultList.get(0).getTrialCount());
    	    	
    	final Session session = PaHibernateUtil.getCurrentSession();
        StudyProtocol protocol = (StudyProtocol) session.get(
                StudyProtocol.class, TestSchema.studyProtocols.get(0).getId());
        protocol.setProprietaryTrialIndicator(true);
        session.update(protocol);
        session.flush();
        
        when(studySiteSvc.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenReturn(new ArrayList<StudySiteDTO>());
        when(svcLocal.getStudySiteService()).thenReturn(studySiteSvc);
        //((SearchTrialBean) bean).setDummyDateAndCounts(true);
        assertEquals(1, bean.getAccrualCountsForUser(TestSchema.registryUsers.get(1)).size());
        
        when(studySiteSvc.search(any(StudySiteDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(0));
        when(svcLocal.getStudySiteService()).thenReturn(studySiteSvc);
        //((SearchTrialBean) bean).setDummyDateAndCounts(true);
        try {
        	bean.getAccrualCountsForUser(TestSchema.registryUsers.get(1));
        	fail();
        } catch (Exception e) {
        	// expected
        }        
    }

    @Test
    public void validate() throws Exception {
        bean.validate(-1L);
        bean.validate(TestSchema.studyProtocols.get(0).getId());
        StudyProtocol sp = TestSchema.studyProtocols.get(0);
        StudySite duplicateSite = new StudySite();
        duplicateSite.setLocalStudyProtocolIdentifier("SWOG");
        duplicateSite.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        duplicateSite.setFunctionalCode(StudySiteFunctionalCode.TREATING_SITE);
        duplicateSite.setHealthCareFacility(TestSchema.healthCareFacilities.get(0));
        duplicateSite.setStudyProtocol(sp);
        TestSchema.addUpdObject(duplicateSite);
        try {
            bean.validate(TestSchema.studyProtocols.get(0).getId());
            fail();
        } catch (PAException e) {
            // good
        }
    }
}
