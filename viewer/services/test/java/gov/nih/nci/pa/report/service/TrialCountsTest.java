package gov.nih.nci.pa.report.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.TrialListCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.TimeUnitsCode;
import gov.nih.nci.pa.report.util.ReportUtil;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;


public class TrialCountsTest {
    TrialListLocal localSvc;

//    @Before
    public void setUp() throws Exception {
//        TestSchema.reset();
        localSvc = new TrialListReportBean();
    }

    @Test
    public void groupTest() throws Exception {
        TrialCountsReportBean bean = new TrialCountsReportBean();
        Timestamp testTs = ReportUtil.makeTimestamp(2009, 11, 21);
        System.out.println ("November 21, 2009:  " + testTs);
        System.out.println("Year:  " + ReportUtil.getYear(testTs));
        System.out.println("Month:  " + ReportUtil.getMonth(testTs));
        System.out.println("Day:  " + ReportUtil.getDay(testTs));
        TrialCountsReportBean.Group xxx = bean.new Group("Test org", testTs, 1, TimeUnitsCode.MONTH, false);
        System.out.println("Year:  " + ReportUtil.getYear(xxx.getTs()));
        System.out.println("Month:  " + ReportUtil.getMonth(xxx.getTs()));
        System.out.println("Day:  " + ReportUtil.getDay(xxx.getTs()));
        assertTrue(true);
        String x = null;
        System.out.println(x);
    }

//    @Test
    public void getTest() throws Exception {
        TrialListCriteriaDto crit = new TrialListCriteriaDto();
        List<TrialListResultDto> resultList = localSvc.get(crit);
        assertEquals(1, resultList.size());
        for (TrialListResultDto dto : resultList) {
            System.out.println(StConverter.convertToString(dto.getAssignedIdentifier()));
            System.out.println(StConverter.convertToString(dto.getOfficialTitle()));
            System.out.println(StConverter.convertToString(dto.getOrganization()));
            System.out.println(TsConverter.convertToString(dto.getDateLastCreated()));
            System.out.println(CdConverter.convertCdToString(dto.getStatusCode()));
        }
    }
}
