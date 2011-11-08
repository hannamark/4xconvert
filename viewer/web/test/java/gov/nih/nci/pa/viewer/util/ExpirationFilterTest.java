/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This viewer Software License (the License) is between NCI and You. You (or 
 * Your) shall mean a person or an entity, and all other entities that control, 
 * are controlled by, or are under common control with the entity. Control for 
 * purposes of this definition means (i) the direct or indirect power to cause 
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares, 
 * or (iii) beneficial ownership of such entity. 
 *
 * This License is granted provided that You agree to the conditions described 
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up, 
 * no-charge, irrevocable, transferable and royalty-free right and license in 
 * its rights in the viewer Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and 
 * have distributed to and by third parties the viewer Software and any 
 * modifications and derivative works thereof; and (iii) sublicense the 
 * foregoing rights set out in (i) and (ii) to third parties, including the 
 * right to license such rights to further third parties. For sake of clarity, 
 * and not by way of limitation, NCI shall have no right of accounting or right 
 * of payment from You or Your sub-licensees for the rights granted under this 
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the 
 * above copyright notice, this list of conditions and the disclaimer and 
 * limitation of liability of Article 6, below. Your redistributions in object 
 * code form must reproduce the above copyright notice, this list of conditions 
 * and the disclaimer of Article 6 in the documentation and/or other materials 
 * provided with the distribution, if any. 
 *
 * Your end-user documentation included with the redistribution, if any, must 
 * include the following acknowledgment: This product includes software 
 * developed by 5AM and the National Cancer Institute. If You do not include 
 * such end-user documentation, You shall include this acknowledgment in the 
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM" 
 * to endorse or promote products derived from this Software. This License does 
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the 
 * terms of this License. 
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this 
 * Software into Your proprietary programs and into any third party proprietary 
 * programs. However, if You incorporate the Software into third party 
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software 
 * into such third party proprietary programs and for informing Your 
 * sub-licensees, including without limitation Your end-users, of their 
 * obligation to secure any required permissions from such third parties before 
 * incorporating the Software into such third party proprietary software 
 * programs. In the event that You fail to obtain such permissions, You agree 
 * to indemnify NCI for any claims against NCI by such third parties, except to 
 * the extent prohibited by law, resulting from Your failure to obtain such 
 * permissions. 
 *
 * For sake of clarity, and not by way of limitation, You may add Your own 
 * copyright statement to Your modifications and to the derivative works, and 
 * You may provide additional or different license terms and conditions in Your 
 * sublicenses of modifications of the Software, or any derivative works of the 
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES, 
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY, 
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO 
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR 
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.viewer.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
/**
 * @author Michael Visee
 */
public class ExpirationFilterTest {
    
    private ExpirationFilter sut = new ExpirationFilter();

    /**
     * ExpectedException rule.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test the init method in the successful case
     * @throws ServletException in case of error
     */
    @Test
    public void testInit() throws ServletException {
        FilterConfig config = createFilterConfig("p1,p2", "10, 20");
        sut.init(config);
        List<ExpirationRule> rules = sut.getRules();
        assertNotNull("No rule set", rules);
        assertEquals("Wrong number of collections", 2, rules.size());
        ExpirationRule rule0 = rules.get(0);
        assertEquals("Wrong pattern in first rule", "p1", rule0.getPattern());
        assertEquals("Wrong delay in first rule", 10, rule0.getDelayInSeconds());
        ExpirationRule rule1 = rules.get(1);
        assertEquals("Wrong pattern in second rule", "p2", rule1.getPattern());
        assertEquals("Wrong delay in second rule", 20, rule1.getDelayInSeconds());
    }

    /**
     * Test the init method in the case arrays are of wrong length.
     * @throws ServletException in case of error
     */
    @Test
    public void testInitDifferentLength() throws ServletException {
        thrown.expect(ServletException.class);
        thrown.expectMessage("Configuration exception : Pattern and delay arrays have different lengths");
        FilterConfig config = createFilterConfig("p1,p2", "10");
        sut.init(config);
    }

    /**
     * Test the init method with a number format.
     * @throws ServletException in case of error
     */
    @Test
    public void testInitFormatError() throws ServletException {
        thrown.expect(ServletException.class);
        thrown.expectMessage("Configuration exception : delay abcd is not an integer");
        FilterConfig config = createFilterConfig("p1", "abcd");
        sut.init(config);
    }

    /**
     * Test the doFilter method in the static case.
     * @throws ServletException in case of error
     * @throws IOException in case of error
     */
    @Test
    public void testDoFilterStatic() throws ServletException, IOException {
        FilterConfig config = createFilterConfig("/static/.*", "10");
        sut.init(config);
        HttpServletRequest request = createRequest("/viewer", "/viewer/static/image.gif");
        HttpServletResponse response = createResponse();
        FilterChain chain = mock(FilterChain.class);
        long now = System.currentTimeMillis();
        sut.doFilter(request, response, chain);
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(response).setDateHeader(eq("Expires"), captor.capture());
        verify(response, never()).setHeader(anyString(), anyString());
        assertTrue(now + 10000L <= captor.getValue());
        verify(chain).doFilter(request, response);
    }

    /**
     * Test the doFilter method in the dynamic case.
     * @throws ServletException in case of error
     * @throws IOException in case of error
     */
    @Test
    public void testDoFilterDynamic() throws ServletException, IOException {
        FilterConfig config = createFilterConfig("/static/.*", "10");
        sut.init(config);
        HttpServletRequest request = createRequest("/viewer", "/viewer/blablabla");
        HttpServletResponse response = createResponse();
        FilterChain chain = mock(FilterChain.class);
        sut.doFilter(request, response, chain);
        verify(response).setHeader("Cache-Control", "max-age=0, must-revalidate, no-cache");
        verify(response, never()).setDateHeader(anyString(), anyLong());
        verify(chain).doFilter(request, response);
    }

    private FilterConfig createFilterConfig(String patterns, String delays) {
        FilterConfig config = mock(FilterConfig.class);
        when(config.getInitParameter("patterns")).thenReturn(patterns);
        when(config.getInitParameter("delaysInSeconds")).thenReturn(delays);
        return config;
    }

    private HttpServletRequest createRequest(String contextPath, String uri) {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn(contextPath);
        when(request.getRequestURI()).thenReturn(uri);
        return request;
    }

    private HttpServletResponse createResponse() {
        return mock(HttpServletResponse.class);
    }

}
