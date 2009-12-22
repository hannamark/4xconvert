/**
 * 
 */
package gov.nih.nci.pa.session;

import gov.nih.nci.pa.action.AbstractPaActionTest;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

import com.mockrunner.mock.web.MockFilterChain;
import com.mockrunner.mock.web.MockFilterConfig;


/**
 * @author asharma
 *
 */
public class BindHibernateSessionTest extends AbstractPaActionTest {

	BindHibernateSession hibernateSession = new BindHibernateSession();
	
	@Test
	public void testDestroy() {
		hibernateSession.destroy();
		
	}

	@Test(expected=Exception.class)	
	public void testDoFilter() throws IOException, ServletException {
		
		hibernateSession.doFilter(getRequest(), getResponse(), new MockFilterChain());
	}
	@Test
	public void testInit() throws ServletException {
		hibernateSession.init(new MockFilterConfig());
		
	}

}
