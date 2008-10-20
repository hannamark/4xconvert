/**
 * 
 */
package gov.nih.nci.pa.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
        // return null when study status does not have a corresponding recruitment status
        StudyProtocol sp = new StudyProtocol();
        sp.setId(1L);
        StudyOverallStatus bo = new StudyOverallStatus();
        bo.setStatusCode(StudyStatusCode.IN_REVIEW);
        bo.setStatusDate(PAUtil.dateStringToTimestamp("1/1/2001"));
        bo.setStudyProtocol(sp);
        bo.setCommentText(null);
        StudyRecruitmentStatus srs = StudyRecruitmentStatusServiceBean.create(bo);
        assertNull(srs);
        
        // return domain object when there is corresponding recruitment status
        bo.setStatusCode(StudyStatusCode.ACTIVE);
        srs = StudyRecruitmentStatusServiceBean.create(bo);
        assertNotNull(srs);
    }
}
