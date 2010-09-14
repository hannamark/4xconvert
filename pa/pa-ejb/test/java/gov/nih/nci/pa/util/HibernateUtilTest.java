/**
 *
 */
package gov.nih.nci.pa.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * @author asharma
 *
 */
public class HibernateUtilTest {

     @Test
      public void getHibernateHelperTest() {
         assertNotNull(HibernateUtil.getHibernateHelper());
     }

}
