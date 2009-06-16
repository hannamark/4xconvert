package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.report.util.TestSchema;
import gov.nih.nci.pa.report.util.TestUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SubmitterOrganizationTest {
    public SubmitterOrganizationReportBean bean = null;

    @Before
    public void setUp() throws Exception {
        bean = new SubmitterOrganizationReportBean();
        TestSchema.reset();
    }

    @Test
    public void testGet() throws Exception {
        List<St> resultList = bean.get();
        List<String> tList = TestUtil.isoListToJava(resultList);
        for (User usr : TestSchema.user) {
            assertTrue(tList.contains(usr.getOrganization()));
        }
    }
}
