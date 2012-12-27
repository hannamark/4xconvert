/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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
package gov.nih.nci.po.web.curation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PersonCR;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.security.exceptions.CSException;

import java.util.Map;

import javax.jms.JMSException;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.Action;

public class CuratePersonActionTest extends AbstractPoTest {
    private CuratePersonAction action;

    @Before
    public void setUp() {
        action = new CuratePersonAction();
        assertNotNull(action.getPerson());
    }

    @Test
    public void testPrepareNoRootKey() throws Exception {
        Person initial = action.getPerson();
        action.prepare();
        assertSame(initial, action.getPerson());
    }

    @Test
    public void testUsFormat() {
        assertFalse(action.isUsOrCanadaFormat());
        Address addr1 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("United States", "840", "US", "USA"));
        action.getPerson().setPostalAddress(addr1);
        assertTrue(action.isUsOrCanadaFormat());
        Address addr2 = new Address("defaultStreetAddress", "cityOrMunicipality", "defaultState", "12345",
                new Country("Tazmania", "999", "TZ", "TAZ"));
        action.getPerson().setPostalAddress(addr2);
        assertFalse(action.isUsOrCanadaFormat());
    }

    @Test
    public void testPrepareWithRootKeyButNoObjectInSession() throws Exception {
        // can only set root key to the key of an object in the session,
        // so after setting the root key, we have to clear out the session manually to test this case
        action.setRootKey("abc-123");
        getSession().clearAttributes();

        action.prepare();
        assertNull(action.getPerson());
    }

    @Test
    public void testPrepareWithRootKeyButWithObjectInSession() throws Exception {
        Person o = new Person();
        String rootKey = "a";
        getSession().setAttribute(rootKey, o);
        action.setRootKey(rootKey);
        action.prepare();
        assertSame(o, action.getPerson());
    }

    @Test
    public void testStart() {
        action.getPerson().setId(1L);
        assertEquals(CuratePersonAction.CURATE_RESULT, action.start());
        assertEquals(1L, action.getPerson().getId().longValue());
        assertEquals("firstName", action.getPerson().getFirstName());
    }

    @Test(expected = NullPointerException.class)
    public void testStartNoOrgId() {
        assertEquals(CuratePersonAction.CURATE_RESULT, action.start());
    }

    @Test
    public void testCurate() throws JMSException, CSException {
        assertEquals(Action.SUCCESS, action.curate());
    }

    @Test
    public void testCurateWithDuplicate() throws JMSException, CSException {
        Person p = new Person();
        action.setDuplicateOf(p);
        assertEquals(Action.SUCCESS, action.curate());
        assertNull(action.getPerson().getDuplicateOf());

        p.setId(1L);
        action.setDuplicateOf(p);
        assertEquals(Action.SUCCESS, action.curate());
        assertEquals(1, action.getPerson().getDuplicateOf().getId().longValue());
    }

    @Test
    public void changeCurrentChangeRequest() {
        assertEquals(CuratePersonAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());

        action.getCr().setId(1L);
        assertEquals(CuratePersonAction.CHANGE_CURRENT_CHANGE_REQUEST_RESULT, action.changeCurrentChangeRequest());
    }

    @Test
    public void testCrProperty() {
        assertNotNull(action.getCr());
        action.setCr(null);
        assertNull(action.getCr());
    }

    @Test
    public void testOrganizationProperty() {
        assertNotNull(action.getPerson());
        action.setPerson(null);
        assertNull(action.getPerson());
    }

    @Test
    public void testGetSelectChangeRequests() {
        action.getPerson().setId(1L);
        PersonCR cr1 = new PersonCR();
        cr1.setId(1L);
        action.getPerson().getChangeRequests().add(cr1);
        PersonCR cr2 = new PersonCR();
        cr2.setId(2L);
        action.getPerson().getChangeRequests().add(cr2);
        Map<String, String> selectChangeRequests = action.getSelectChangeRequests();
        assertEquals(2, selectChangeRequests.size());
        selectChangeRequests.values();
        int i = 1;
        for (String value : selectChangeRequests.values()) {
            assertEquals("CR-ID-" + i, value);
            i++;
        }
    }
}
