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
package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.audit.AuditLogDetail;
import gov.nih.nci.po.audit.AuditLogRecord;
import gov.nih.nci.po.audit.AuditLogRecordSearchCriteria;
import gov.nih.nci.po.audit.AuditLogRecordSortCriterion;
import gov.nih.nci.po.audit.AuditType;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.search.PageSortParams;
import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * Test casses for the AuditableServiceBean.
 */
public class AuditableServiceBeanTest extends AbstractHibernateTestCase {

    @Test
    public void testSearch() throws Exception {
        UsernameHolder.setUser("a");
        Session s = PoHibernateUtil.getCurrentSession();
        URL url = new URL("http://www.example.com/");
        s.save(url);
        s.flush();

        AuditableServiceBean asb = new AuditableServiceBean();
        AuditLogRecordSearchCriteria criteria = new AuditLogRecordSearchCriteria(url.getId(), null);
        List<AuditLogRecord> l = asb.search(criteria);
        assertEquals(1, l.size());
        assertEquals(1, asb.count(criteria));
        AuditLogRecord alr = l.get(0);
        assertEquals(url.getId(), alr.getEntityId());
        assertEquals(AuditType.INSERT, alr.getType());

        Set<Long> longs = new HashSet<Long>();
        longs.add(alr.getTransactionId());

        criteria = new AuditLogRecordSearchCriteria(null, longs);
        l = asb.search(criteria);
        assertEquals(1, l.size());
        assertEquals(1, asb.count(criteria));

        Thread.sleep(100L); // make sure the create times are different on the audit records

        UsernameHolder.setUser("b");
        url.setValue("https://www.example.com");
        s.update(url);
        s.flush();

        longs.add(alr.getTransactionId() + 1);

        l = asb.search(criteria);
        assertEquals(2, l.size());
        assertEquals(2, asb.count(criteria));

        l = asb.search(criteria,
                new PageSortParams<AuditLogRecord>(1, 0, AuditLogRecordSortCriterion.CREATED_DATE, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.INSERT, l.get(0).getType());
        l = asb.search(criteria,
                       new PageSortParams<AuditLogRecord>(1, 1, AuditLogRecordSortCriterion.CREATED_DATE, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.UPDATE, l.get(0).getType());

        l = asb.search(criteria,
                new PageSortParams<AuditLogRecord>(1, 0, AuditLogRecordSortCriterion.CREATED_DATE, true));
        assertEquals(1, l.size());
        assertEquals(AuditType.UPDATE, l.get(0).getType());
        l = asb.search(criteria,
                       new PageSortParams<AuditLogRecord>(1, 1, AuditLogRecordSortCriterion.CREATED_DATE, true));
        assertEquals(1, l.size());
        assertEquals(AuditType.INSERT, l.get(0).getType());

        l = asb.search(criteria,
                new PageSortParams<AuditLogRecord>(1, 0, AuditLogRecordSortCriterion.USERNAME, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.INSERT, l.get(0).getType());
        l = asb.search(criteria,
                       new PageSortParams<AuditLogRecord>(1, 1, AuditLogRecordSortCriterion.USERNAME, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.UPDATE, l.get(0).getType());

        l = asb.search(criteria,
                new PageSortParams<AuditLogRecord>(1, 0, AuditLogRecordSortCriterion.AUDIT_TYPE, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.INSERT, l.get(0).getType());
        l = asb.search(criteria,
                       new PageSortParams<AuditLogRecord>(1, 1, AuditLogRecordSortCriterion.AUDIT_TYPE, false));
        assertEquals(1, l.size());
        assertEquals(AuditType.UPDATE, l.get(0).getType());
    }

    @Test
    public void testRelatedDetails() throws Exception {
        UsernameHolder.setUser("a");
        Session s = PoHibernateUtil.getCurrentSession();
        URL url = new URL("http://www.example.com/");
        s.save(url);
        s.flush();

        AuditLogRecord alr1 = new AuditLogRecord(AuditType.INSERT, "foo", 2L, "test", new Date());
        AuditLogDetail ald1 = new AuditLogDetail(alr1, "test", null, url.getId().toString(), true);
        alr1.getDetails().add(ald1);
        s.save(alr1);

        AuditLogRecord alr2 = new AuditLogRecord(AuditType.INSERT, "foo", 3L, "test", new Date());
        AuditLogDetail ald2 = new AuditLogDetail(alr2, "test", null, url.getId().toString(), false);
        alr2.getDetails().add(ald2);
        s.save(alr2);

        AuditLogRecord alr3 = new AuditLogRecord(AuditType.INSERT, "foo", 4L, "test", new Date());
        AuditLogDetail ald3 = new AuditLogDetail(alr3, "test", url.getId().toString(), null, true);
        alr3.getDetails().add(ald3);
        s.save(alr3);

        AuditLogRecord alr4 = new AuditLogRecord(AuditType.INSERT, "foo", 5L, "test", new Date());
        AuditLogDetail ald4 = new AuditLogDetail(alr4, "test", url.getId().toString(), null, false);
        alr4.getDetails().add(ald4);
        s.save(alr4);
        s.flush();

        Set<Long> expected = new HashSet<Long>();
        expected.add(alr1.getId());
        expected.add(alr3.getId());

        AuditableServiceBean asb = new AuditableServiceBean();
        AuditLogRecordSearchCriteria criteria = new AuditLogRecordSearchCriteria(url.getId(), null);
        List<AuditLogRecord> l = asb.search(criteria);
        assertEquals(3, l.size());
        assertEquals(3, asb.count(criteria));

        boolean foundExact = false;
        for (AuditLogRecord record : l) {
            expected.remove(record.getId());
            if (record.getEntityId().equals(url.getId())) {
                foundExact = true;
                assertEquals(AuditType.INSERT, record.getType());
            }
        }
        assertTrue(expected.isEmpty());
        assertTrue(foundExact);
    }
}
