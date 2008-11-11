/**
 * 
 */
package gov.nih.nci.pa.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyRecruitmentStatusTest {
    @Test
    public void getByStudyStatusCodeTest() {
        assertEquals(StudyRecruitmentStatusCode.NOT_YET_RECRUITING.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.APPROVED).getCode());
        assertEquals(StudyRecruitmentStatusCode.RECRUITING_ACTIVE.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.ACTIVE).getCode());
        assertEquals(StudyRecruitmentStatusCode.NOT_RECRUITING.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL).getCode());
        assertEquals(StudyRecruitmentStatusCode.NOT_RECRUITING.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.CLOSED_TO_ACCRUAL_AND_INTERVENTION).getCode());
        assertEquals(StudyRecruitmentStatusCode.SUSPENDED.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL).getCode());
        assertEquals(StudyRecruitmentStatusCode.SUSPENDED.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION).getCode());
        assertEquals(StudyRecruitmentStatusCode.WITHDRAWN.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.WITHDRAWN).getCode());
        assertEquals(StudyRecruitmentStatusCode.COMPLETED.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.COMPLETE).getCode());
        assertEquals(StudyRecruitmentStatusCode.TERMINATED.getCode(), 
                StudyRecruitmentStatusCode.getByStudyStatusCode(StudyStatusCode.ADMINISTRATIVELY_COMPLETE).getCode());
    }
}
