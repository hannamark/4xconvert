/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.util.PAUtil;

import org.junit.Test;

/**
 * @author hreinhart
 *
 */
public class StudyRecruitmentStatusTest {
    @Test
    public void createTest() {
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setStatusCode(StudyStatusCode.ACTIVE);
        bo.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2001"));
        bo.setStudyProtocol(sp);
        bo.setCommentText(null);
        StudyRecruitmentStatus srs = StudyRecruitmentStatusServiceBean.create(bo);
        assertNotNull(srs);
        
        // return domain object when there is corresponding recruitment status
        bo.setStatusCode(StudyStatusCode.ACTIVE);
        srs = StudyRecruitmentStatusServiceBean.create(bo);
        assertNotNull(srs);
    }
}
