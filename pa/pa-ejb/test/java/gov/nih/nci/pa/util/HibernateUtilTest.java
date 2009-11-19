/**
 * 
 */
package gov.nih.nci.pa.util;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author asharma
 *
 */
public class HibernateUtilTest {
	
	HibernateUtil hibernateUtil = new HibernateUtil();
	
	 @Test
	  public void getHibernateHelperTest() {
		 assertNotNull(hibernateUtil.getHibernateHelper());
	 }
	 
}
