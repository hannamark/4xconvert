/*
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
package gov.nih.nci.po.web.contactable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.contactable.AbstractEditContactListAction.EmailAction;
import gov.nih.nci.po.web.contactable.AbstractEditContactListAction.FaxAction;
import gov.nih.nci.po.web.contactable.AbstractEditContactListAction.PhoneAction;
import gov.nih.nci.po.web.contactable.AbstractEditContactListAction.TtyAction;
import gov.nih.nci.po.web.contactable.AbstractEditContactListAction.UrlAction;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Action;

/**
 *
 * @author gax
 */
public class AbstractEditContactListActionTest extends AbstractPoTest {

    @Test
    public void simple() {
        EmailAction instance = new EmailAction();
        assertEquals(Action.SUCCESS, instance.edit());
    }

    @Test
    public void prepare() {
        Contactable ci = new Organization();

        String key = "foo";
        getSession().setAttribute(key, ci);

        EmailAction instance = new EmailAction();
        assertNull(instance.getContactable());
        assertNull(instance.getRootKey());
        try {
            instance.prepare();
            fail("should no work w/o a ciKey");
        } catch (IllegalArgumentException e) {
            // ok
        }
        instance.setRootKey(key);
        instance.prepare();
        assertSame(ci, instance.getContactable());
    }

    @Test
    public void testReadonlyProperty() {
        EmailAction action = new EmailAction();
        assertFalse(action.isReadonly());
        action.setReadonly(true);
        assertTrue(action.isReadonly());
    }

    @Test
    public void addRemove() {
        Contactable ci = new Organization();
        EmailAction instance = new EmailAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);
        instance.prepare();

        String v = "foo@example.com";
        assertNull(instance.find(v));
        Email e = new Email(v);
        ci.getEmail().add(e);
        assertSame(e, instance.find(v));
        assertNull(ActionHelper.getMessages());
        instance.getEntry().setValue(v);
        instance.add();
        assertTrue(ActionHelper.getMessages().get(0).contains("Already "));

        v = "bar@example.com";
        ActionHelper.getMessages().clear();
        instance.getEntry().setValue(v);
        instance.add();
        assertTrue(ActionHelper.getMessages().isEmpty());
        assertEquals(2, ci.getEmail().size());
        assertEquals(v, ci.getEmail().get(1).getValue());

        instance.getEntry().setValue(v);
        instance.remove();
        assertEquals(1, ci.getEmail().size());
        assertFalse(v.equals(ci.getEmail().get(0).getValue()));

        // make sure you can remove a value with leading or trailing spaces (PO-2693)
        v = " bar@example.com ";
        ActionHelper.getMessages().clear();
        instance.getEntry().setValue(v);
        instance.add();
        assertTrue(ActionHelper.getMessages().isEmpty());
        assertEquals(2, ci.getEmail().size());
        assertEquals(v, ci.getEmail().get(1).getValue());

        String valueNoSpaces = "bar@example.com";
        instance.getEntry().setValue(valueNoSpaces);
        instance.remove();
        assertEquals(1, ci.getEmail().size());
        assertFalse(v.equals(ci.getEmail().get(0).getValue()));
        assertFalse(valueNoSpaces.equals(ci.getEmail().get(0).getValue()));
        assertTrue(ActionHelper.getMessages().isEmpty());
    }

    @Test
    public void testRemoveOnEmptyList() {
        Contactable ci = new Organization();
        EmailAction instance = new EmailAction();
        instance.setContactable(ci);
        instance.setEntry(new Email());
        instance.remove();
        assertTrue(ActionHelper.getMessages().get(0).contains("was not found"));
    }

    @Test
    public void testEmail() {
        Contactable ci = new Organization();
        EmailAction instance = new EmailAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        Email e = instance.getEntry();
        e.setValue(v);
        instance.add();
        e = ci.getEmail().get(0);
        assertEquals(v, e.getValue());

        instance.setEmailEntry(e);
        assertEquals(e, instance.getEmailEntry());

    }

    @Test
    public void testPhone() {
        Contactable ci = new Organization();
        PhoneAction instance = new PhoneAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        PhoneNumber e = instance.getEntry();

        e.setValue(v);
        instance.add();
        e = ci.getPhone().get(0);
        assertEquals(v, e.getValue());

        instance.setPhoneEntry(e);
        assertEquals(e, instance.getPhoneEntry());
        assertFalse(instance.isUsOrCanadaFormat());

    }

    @Test
    public void testFax() {
        Contactable ci = new Organization();
        FaxAction instance = new FaxAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        PhoneNumber e = instance.getEntry();
        e.setValue(v);
        instance.add();
        e = ci.getFax().get(0);
        assertEquals(v, e.getValue());

        instance.setFaxEntry(e);
        assertEquals(e, instance.getFaxEntry());
        assertFalse(instance.isUsOrCanadaFormat());

    }

    @Test
    public void testTty() {
        Contactable ci = new Organization();
        TtyAction instance = new TtyAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        PhoneNumber e = instance.getEntry();
        e.setValue(v);
        instance.add();
        e = ci.getTty().get(0);
        assertEquals(v, e.getValue());

        instance.setTtyEntry(e);
        assertEquals(e, instance.getTtyEntry());
        assertFalse(instance.isUsOrCanadaFormat());

    }

    @Test
    public void testUrl() {
        Contactable ci = new Organization();
        UrlAction instance = new UrlAction();
        String key = "foo";
        getSession().setAttribute(key, ci);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        URL e = instance.getEntry();
        e.setValue(v);
        instance.add();
        e = ci.getUrl().get(0);
        assertEquals(v, e.getValue());

        instance.setUrlEntry(e);
        assertEquals(e, instance.getUrlEntry());
    }
}
