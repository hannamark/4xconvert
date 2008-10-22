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
package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.service.AnnotatedBeanSearchCriteria;
import gov.nih.nci.po.service.OneCriterionRequiredException;
import gov.nih.nci.po.service.OrganizationalContactServiceLocal;
import gov.nih.nci.po.service.SearchCriteria;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author smatyas
 */
public class OrganizationalContactServiceTest extends AbstractStructrualRoleServiceTest<OrganizationalContact> {

    private Set<OrganizationalContactType> types = new HashSet<OrganizationalContactType>();
    
    private Set<OrganizationalContactType> getTypes() {
        return types;
    }

    @Before
    public void initDbData() {
        types.add(new OrganizationalContactType("For drug shipment"));
        types.add(new OrganizationalContactType("For safety issues"));
        for (OrganizationalContactType obj : getTypes()) {
            PoHibernateUtil.getCurrentSession().save(obj);
        }
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();
    }

    @Override
    OrganizationalContact getSampleStructuralRole() {
        OrganizationalContact oc = new OrganizationalContact();
        fillinPersonRoleFields(oc);
        oc.setPrimaryIndicator(Boolean.TRUE);
        oc.getTypes().addAll(getTypes());
        return oc;
    }

    @Override
    void verifyStructuralRole(OrganizationalContact expected, OrganizationalContact actual) {
        verifyPersonRole(expected, actual);
        assertEquals(expected.getPrimaryIndicator(), actual.getPrimaryIndicator());
        
        List<String> expectedValues = OrganizationalContactDTOTest.getCodeValues(expected.getTypes());
        List<String> actualValues = OrganizationalContactDTOTest.getCodeValues(actual.getTypes());
        assertEquals(expectedValues.size(), actualValues.size());
        assertTrue(expectedValues.containsAll(actualValues));
        assertTrue(actualValues.containsAll(expectedValues));
    }

    @Test
    public void testSearch() throws Exception {
        OrganizationalContactServiceLocal svc = (OrganizationalContactServiceLocal) getService();
        OrganizationalContact oc = getSampleStructuralRole();
        svc.create(oc);

        SearchCriteria<OrganizationalContact> sc = new AnnotatedBeanSearchCriteria<OrganizationalContact>(null);

        try {
            svc.search(null);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        try {
            svc.search(sc);
            fail();
        } catch (OneCriterionRequiredException e) {
            // expected
        }

        //
        // Simple fields
        //

        // id
        doSearch(oc, oc.getId(), null, null, null, null, null, null, null, null, null, 1);
        doSearch(oc, -1L, null, null, null, null, null, null, null, null, null, 0);

        // primaryIndicator
        doSearch(oc, null, null, null, oc.getPrimaryIndicator(), null, null, null, null, null, null, 1);
        doSearch(oc, null, null, null, !oc.getPrimaryIndicator(), null, null, null, null, null, null, 0);

        // id with primaryIndicator
        doSearch(oc, oc.getId(), null, null, oc.getPrimaryIndicator(), null, null, null, null, null, null, 1);
        doSearch(oc, oc.getId(), null, null, !oc.getPrimaryIndicator(), null, null, null, null, null, null, 0);

        // person + org
        doSearch(oc, null, oc.getScoper().getId(), null, null, null, null, null, null, null, null, 1);
        doSearch(oc, null, null, oc.getPlayer().getId(), null, null, null, null, null, null, null, 1);
        doSearch(oc, null, -1L, null, null, null, null, null, null, null, null, 0);
        doSearch(oc, null, null, -1L, null, null, null, null, null, null, null, 0);
        doSearch(oc, null, oc.getScoper().getId(), oc.getPlayer().getId(), null, null, null, null, null, null,
                null, 1);
        doSearch(oc, null, oc.getScoper().getId(), oc.getPlayer().getId() + 1, null, null, null, null, null,
                null, null, 0);

        // status fields
        doSearch(oc, null, null, null, null, RoleStatus.PENDING, null, null, null, null, null, 1);
        doSearch(oc, null, null, null, null, RoleStatus.ACTIVE, null, null, null, null, null, 0);
        doSearch(oc, null, oc.getId(), null, null, RoleStatus.PENDING, null, null, null, null, null, 1);
        doSearch(oc, null, -1L, null, null, RoleStatus.PENDING, null, null, null, null, null, 0);

        //
        // List fields
        //

        // email
        List<Email> emails = new ArrayList<Email>();
        emails.add(new Email(oc.getEmail().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, emails, null, null, null, null, 1);

        emails.get(0).setValue(emails.get(0).getValue().substring(1));
        doSearch(oc, null, null, null, null, null, emails, null, null, null, null, 0);

        emails.get(0).setValue(emails.get(0).getValue().substring(0, 3));
        doSearch(oc, null, null, null, null, null, emails, null, null, null, null, 0);

        emails.add(new Email("idontexist"));
        doSearch(oc, null, null, null, null, null, emails, null, null, null, null, 0);

        emails.add(new Email(oc.getEmail().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, emails, null, null, null, null, 1);

        // phone
        List<PhoneNumber> phones = new ArrayList<PhoneNumber>();
        phones.add(new PhoneNumber(oc.getPhone().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, phones, null, null, null, 1);

        phones.get(0).setValue(phones.get(0).getValue().substring(1));
        doSearch(oc, null, null, null, null, null, null, phones, null, null, null, 0);

        phones.add(new PhoneNumber(oc.getPhone().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, phones, null, null, null, 1);

        // fax
        List<PhoneNumber> faxes = new ArrayList<PhoneNumber>();
        faxes.add(new PhoneNumber(oc.getFax().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, faxes, null, null, 1);

        faxes.get(0).setValue(faxes.get(0).getValue().substring(1));
        doSearch(oc, null, null, null, null, null, null, null, faxes, null, null, 0);

        faxes.add(new PhoneNumber(oc.getFax().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, faxes, null, null, 1);

        // tty
        List<PhoneNumber> ttys = new ArrayList<PhoneNumber>();
        ttys.add(new PhoneNumber(oc.getTty().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, null, ttys, null, 1);

        ttys.get(0).setValue(ttys.get(0).getValue().substring(1));
        doSearch(oc, null, null, null, null, null, null, null, null, ttys, null, 0);

        ttys.add(new PhoneNumber(oc.getTty().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, null, ttys, null, 1);

        // url
        List<URL> urls = new ArrayList<URL>();
        urls.add(new URL(oc.getUrl().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, null, null, urls, 1);

        urls.get(0).setValue(urls.get(0).getValue().substring(1));
        doSearch(oc, null, null, null, null, null, null, null, null, null, urls, 0);

        urls.add(new URL(oc.getUrl().get(0).getValue()));
        doSearch(oc, null, null, null, null, null, null, null, null, null, urls, 1);

        // combo url + tty to prove that combos of lists work too
        doSearch(oc, null, null, null, null, null, null, null, null, ttys, urls, 1);
        doSearch(oc, oc.getId(), null, null, null, null, null, null, null, ttys, urls, 1);
        urls.remove(1);
        doSearch(oc, null, null, null, null, null, null, null, null, ttys, urls, 0);

        // Fields Remaining to test:
        // - address (tricky!)
        // - types (Set<enum>)
    }

    private void doSearch(OrganizationalContact hcp, Long id, Long orgId, Long personId, Boolean primaryIndicator,
            RoleStatus status, List<Email> email, List<PhoneNumber> phones, List<PhoneNumber> faxes, List<PhoneNumber> ttys,
            List<URL> urls, int numExpected) {
        OrganizationalContactServiceLocal svc = (OrganizationalContactServiceLocal) getService();
        OrganizationalContact example = new OrganizationalContact();
        example.setId(id);
        example.setPlayer(new Person());
        example.getPlayer().setId(personId);
        example.setScoper(new Organization());
        example.getScoper().setId(orgId);
        example.setPrimaryIndicator(primaryIndicator);
        example.setStatus(status);
        example.setEmail(email);
        example.setPhone(phones);
        example.setFax(faxes);
        example.setTty(ttys);
        example.setUrl(urls);
        SearchCriteria<OrganizationalContact> sc = new AnnotatedBeanSearchCriteria<OrganizationalContact>(example);
        List<OrganizationalContact> l = svc.search(sc);
        assertNotNull(l);
        assertEquals(numExpected, l.size());
        if (numExpected > 0) {
            assertTrue(l.contains(hcp));
        }
    }

}
