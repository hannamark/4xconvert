/**
 * 
 */
package gov.nih.nci.pa.test.util;

import java.security.Principal;

/**
 * @author asharma
 *
 */
public class MockPrincipal implements Principal {
	 private String name;

	    // TODO Findbugs
	    public MockPrincipal(String name)
	    {
	        this.name = name;
	    }

	    public String getName()
	    {
	        return name;
	    }
}
