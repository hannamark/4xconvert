package gov.nih.nci.accrual.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.accrual.dto.HistoricalSubmissionDto;
import gov.nih.nci.accrual.service.AbstractServiceTest;
import gov.nih.nci.accrual.util.AccrualUtil;
import gov.nih.nci.accrual.util.TestSchema;
import gov.nih.nci.pa.domain.AccrualCollections;
import gov.nih.nci.pa.domain.BatchFile;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.util.PAUtil;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SubmissionHistoryServiceTest extends AbstractServiceTest<SubmissionHistoryService> {

    RegistryUser user;

    @Override
    @Before
    public void instantiateServiceBean() throws Exception {
        AccrualCsmUtil.setCsmUtil(new MockCsmUtil());
        SubmissionHistoryBean shb = new SubmissionHistoryBean();
        shb.setSearchTrialSvc(new SearchTrialBean());
        bean = shb;
        user = TestSchema.registryUsers.get(0);
    }

    @Test
    public void dummyTest() throws Exception {
        assertTrue(true);
    }

    // @Test
    public void searchNoDataTest() throws Exception {
        List<HistoricalSubmissionDto> rList = bean.search(null, null, null);
        assertTrue(rList.isEmpty());
        rList = bean.search(null, null, user);
        assertTrue(rList.isEmpty());
    }

    // @Test
    public void searchGuiAbbreviatedTest() throws Exception {
        // entered through UI
        StudySiteSubjectAccrualCount dataGui = new StudySiteSubjectAccrualCount();
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui.setDateLastUpdated(new Date());
        TestSchema.addUpdObject(dataGui);

        // entered through BATCH
        StudySiteSubjectAccrualCount dataBatch = new StudySiteSubjectAccrualCount();
        dataBatch.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        dataBatch.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataBatch.setDateLastUpdated(new Date());
        TestSchema.addUpdObject(dataBatch);

        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
    }

    // @Test
    public void searchGuiCompleteTest() throws Exception {
        // entered through UI
        StudySubject dataGui = new StudySubject();
        dataGui.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui.setDateLastUpdated(new Date());
        dataGui.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui);

        // entered through BATCH
        StudySubject dataBatch = new StudySubject();
        dataBatch.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataBatch.setSubmissionTypeCode(AccrualSubmissionTypeCode.BATCH);
        dataBatch.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataBatch.setDateLastUpdated(new Date());
        dataBatch.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataBatch);

        // deleted
        StudySubject dataDeleted = new StudySubject();
        dataDeleted.setStatusCode(FunctionalRoleStatusCode.NULLIFIED);
        dataDeleted.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataDeleted.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataDeleted.setDateLastUpdated(new Date());
        dataDeleted.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataBatch);

        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
    }

    // @Test
    public void searchBatchTest() throws Exception {
        BatchFile bf = new BatchFile();
        bf.setDateLastCreated(new Date());
        bf.setUserLastCreated(user.getCsmUser());
        bf.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf.setSubmitter(user);
        bf.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf);

        AccrualCollections ac = new AccrualCollections();
        ac.setBatchFile(bf);
        ac.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac.setResults("abccb");
        TestSchema.addUpdObject(ac);

        List<HistoricalSubmissionDto> rList = bean.search(null, null, user);
        assertEquals(1, rList.size());
        HistoricalSubmissionDto dto = rList.get(0);
        assertEquals("NCI-2009-00001", dto.getNciNumber());
        assertEquals(bf.getId(), dto.getBatchFileIdentifier());
        assertEquals(bf.getDateLastCreated(), dto.getDate());
        assertTrue(dto.getFileName().contains(bf.getFileLocation()));
        assertEquals("No", dto.getResult());
        assertEquals(bf.getSubmissionTypeCode(), dto.getSubmissionType());
        assertEquals(AccrualUtil.getDisplayName(user), dto.getUsername());
    }

    // @Test
    public void searchWithDatesTest() throws Exception {
        Date early = PAUtil.dateStringToDateTime("1/1/2012");
        Date middle = PAUtil.dateStringToDateTime("1/10/2012");
        Date late = PAUtil.dateStringToDateTime("1/20/2012");

        // COMPLETE
        StudySubject dataGui1 = new StudySubject();
        dataGui1.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui1.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui1.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui1.setDateLastUpdated(early);
        dataGui1.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui1);
        StudySubject dataGui2 = new StudySubject();
        dataGui2.setStatusCode(FunctionalRoleStatusCode.ACTIVE);
        dataGui2.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        dataGui2.setStudyProtocol(TestSchema.studyProtocols.get(0));
        dataGui2.setDateLastUpdated(middle);
        dataGui2.setPatient(TestSchema.patients.get(0));
        TestSchema.addUpdObject(dataGui2);

        // ABBREVIATED
        StudySiteSubjectAccrualCount abbrGui1 = new StudySiteSubjectAccrualCount();
        abbrGui1.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        abbrGui1.setStudyProtocol(TestSchema.studyProtocols.get(0));
        abbrGui1.setDateLastUpdated(late);
        TestSchema.addUpdObject(abbrGui1);
        StudySiteSubjectAccrualCount abbrGui2 = new StudySiteSubjectAccrualCount();
        abbrGui2.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        abbrGui2.setStudyProtocol(TestSchema.studyProtocols.get(0));
        abbrGui2.setDateLastUpdated(middle);
        TestSchema.addUpdObject(abbrGui2);

        // BATCH
        BatchFile bf1 = new BatchFile();
        bf1.setDateLastCreated(early);
        bf1.setUserLastCreated(user.getCsmUser());
        bf1.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf1.setSubmitter(user);
        bf1.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf1);
        AccrualCollections ac1 = new AccrualCollections();
        ac1.setBatchFile(bf1);
        ac1.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac1.setResults("abccb");
        TestSchema.addUpdObject(ac1);
        BatchFile bf2 = new BatchFile();
        bf2.setDateLastCreated(middle);
        bf2.setUserLastCreated(user.getCsmUser());
        bf2.setSubmissionTypeCode(AccrualSubmissionTypeCode.SERVICE);
        bf2.setSubmitter(user);
        bf2.setFileLocation("xyzzy");
        TestSchema.addUpdObject(bf2);
        AccrualCollections ac2 = new AccrualCollections();
        ac2.setBatchFile(bf2);
        ac2.setNciNumber("NCI-2009-00001"); // value taken from TestSchema
        ac2.setResults("abccb");
        TestSchema.addUpdObject(ac2);

        List<HistoricalSubmissionDto> rList = bean.search(PAUtil.dateStringToTimestamp("1/5/2012"),
                PAUtil.dateStringToTimestamp("1/15/2012"), user);
        assertEquals(3, rList.size());

        HistoricalSubmissionDto dto = rList.get(0);
        assertNotNull(dto.getCompleteTrialId());
        //assertNotNull(dto.getAssignedIdentifier());
        assertNotNull(dto.getStudySubjectId());
        dto = rList.get(1);
        assertNotNull(dto.getAbbreviatedTrialId());
        dto = rList.get(2);
        assertNotNull(dto.getBatchFileIdentifier());
    }

    // @Test
    public void getBatchResultTest() throws Exception {
        SubmissionHistoryBean b = (SubmissionHistoryBean) bean;

        AccrualCollections ac = new AccrualCollections();
        ac.setPassedValidation(false);
        assertEquals("No", b.getBatchResult(ac));

        ac.setPassedValidation(true);
        ac.setTotalImports(50);
        assertEquals("Yes", b.getBatchResult(ac));

        ac.setTotalImports(null);
        ac.setDateLastCreated(new Date());
        assertNull(b.getBatchResult(ac));

        ac.setDateLastCreated(PAUtil.dateStringToDateTime("7/23/2012"));
        assertEquals("No", b.getBatchResult(ac));
    }
}
