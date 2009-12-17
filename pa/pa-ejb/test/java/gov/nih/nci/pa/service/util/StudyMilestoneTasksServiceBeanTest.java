/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyMilestoneBeanLocal;
import gov.nih.nci.pa.service.StudyMilestoneServicelocal;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.TestSchema;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author asharma
 *
 */
public class StudyMilestoneTasksServiceBeanTest {

	Session sess;
	StudyMilestoneServicelocal result = new StudyMilestoneBeanLocal();
	StudyMilestoneTasksServiceBean taskBean = new StudyMilestoneTasksServiceBean();
	@Before
    public void setUp() throws Exception {
		 TestSchema.reset1();
	     TestSchema.primeData();
	     sess = HibernateUtil.getCurrentSession();   
	     taskBean.smRemote = result;
    }
	
	/**
	 * Test method for {@link gov.nih.nci.pa.service.util.StudyMilestoneTasksServiceBean#performTask()}.
	 */
	@Test
	public void testPerformTask() throws PAException {
		taskBean.performTask();
	}

}
