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
package gov.nih.nci.po.web.importexternal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.MockCtepImportService;
import gov.nih.nci.po.service.external.CtepImportException;
import gov.nih.nci.po.service.external.CtepImportService;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.externalimport.CtepImportAction;
import gov.nih.nci.po.web.util.MockServiceLocator;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import javax.jms.JMSException;

import org.hibernate.HibernateException;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Action;

/**
 * @author Scott Miller
 *
 */
public class CtepFileUploadTest extends AbstractPoTest {

    private static final String ORG_FILE_NAME = "testOrgIds.txt";
    private static final String PERSON_FILE_NAME = "testPersonIds.txt";

    /**
     * test the org upload.
     */
    @Test
    public void testUploadOrgs() throws Exception {
        MockCtepImportService service = (MockCtepImportService) PoRegistry.getInstance().getServiceLocator()
                                                                          .getCtepImportService();
        assertEquals(0, service.getImportedOrgIds().size());

        CtepImportAction action = new CtepImportAction();
        assertEquals(Action.INPUT, action.uploadOrganizations());

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(ORG_FILE_NAME);
        File f = new File(fileUrl.toURI());

        action.setFile(f);
        action.setCtepId("NY123");
        assertEquals(Action.SUCCESS, action.uploadOrganizations());

        // Only the 18 IDs in the file should be imported, ignoring the one specified by ctepId
        assertEquals(18, service.getImportedOrgIds().size());

        action = new CtepImportAction();
        assertEquals(Action.INPUT, action.uploadOrganizations());
        action.setCtepId("NY123");
        assertEquals(Action.SUCCESS, action.uploadOrganizations());
        assertEquals(19, service.getImportedOrgIds().size());
    }

    /**
     * test the upload action org file are all skipped.
     */
    @Test
    public void testUploadOrgsAllSkipped() throws Exception {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator() {
            @Override
            public CtepImportService getCtepImportService() {
                return new CtepImportService() {
                    public Organization importCtepOrganization(Ii orgId) throws JMSException {
                        return null;
                    }
                    public Person importCtepPerson(Ii personId) throws JMSException {
                        return null;
                    }
                    public void nullifyCtepOrganization(Ii orgId, Ii duplicateOfId, OrganizationType orgType)
                            throws JMSException {
                    }
                };
            }
        });

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(ORG_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        action.setFile(f);
        assertEquals(Action.SUCCESS, action.uploadOrganizations());

        Iterator<String> itr= ActionHelper.getMessages().iterator();
        assertTrue(itr.hasNext());
        assertEquals("0 records successfully imported.", itr.next());
        assertTrue(itr.hasNext());
        String next = itr.next();
        assertTrue(next.startsWith("The following line(s) did not correspond to a record in ctep: "));
        assertFalse(itr.hasNext());
    }

    /**
     * test the upload action org file all fail.
     */
    @Test
    public void testUploadOrgsAllFail() throws Exception {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator() {
            @Override
            public CtepImportService getCtepImportService() {
                return new CtepImportService() {
                    public Organization importCtepOrganization(Ii orgId) throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                    public Person importCtepPerson(Ii personId) throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                    public void nullifyCtepOrganization(Ii orgId, Ii duplicateOfId, OrganizationType orgType)
                        throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                };
            }
        });

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(ORG_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        action.setFile(f);
        assertEquals(Action.SUCCESS, action.uploadOrganizations());

        Iterator<String> itr= ActionHelper.getMessages().iterator();
        assertTrue(itr.hasNext());
        assertEquals("0 records successfully imported.", itr.next());
        assertTrue(itr.hasNext());
        String next = itr.next();
        assertTrue(next.startsWith("An error occurred processing the following line(s): "));
        assertFalse(itr.hasNext());
    }

    @Test
    public void testUploadOrgsAllCtepExceptions() throws Exception {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator() {
            @Override
            public CtepImportService getCtepImportService() {
                return new CtepImportService() {
                    public Organization importCtepOrganization(Ii orgId) throws CtepImportException {
                        throw new CtepImportException("shortMessage", "message");
                    }
                    public Person importCtepPerson(Ii personId) throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                    public void nullifyCtepOrganization(Ii orgId, Ii duplicateOfId, OrganizationType orgType)
                        throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                };
            }
        });

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(ORG_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        action.setFile(f);
        assertEquals(Action.SUCCESS, action.uploadOrganizations());

        Iterator<String> itr= ActionHelper.getMessages().iterator();
        assertTrue(itr.hasNext());
        assertEquals("0 records successfully imported.", itr.next());
        assertTrue(itr.hasNext());
        String next = itr.next();
        assertTrue(next.startsWith("An error occurred processing the following line(s): "));
        assertTrue(next.contains("(shortMessage)"));
        assertFalse(itr.hasNext());
    }

    /**
     * test the upload action org file uploact.
     */
    @Test
    public void testUploadPeople() throws Exception {
        MockCtepImportService service = (MockCtepImportService) PoRegistry.getInstance().
        getServiceLocator().getCtepImportService();
        assertEquals(0, service.getImportedPersonIds().size());

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(PERSON_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        assertEquals(Action.INPUT, action.uploadPeople());

        action.setFile(f);
        action.setCtepId("54321");
        assertEquals(Action.SUCCESS, action.uploadPeople());

        // Only the 18 IDs in the file should be imported, ignoring the one specified by ctepId
        assertEquals(18, service.getImportedPersonIds().size());

        action = new CtepImportAction();
        assertEquals(Action.INPUT, action.uploadPeople());
        action.setCtepId("54321");
        assertEquals(Action.SUCCESS, action.uploadPeople());
        assertEquals(19, service.getImportedPersonIds().size());

    }

    /**
     * test the upload action person file are all skipped.
     */
    @Test
    public void testUploadPersonsAllSkipped() throws Exception {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator() {
            @Override
            public CtepImportService getCtepImportService() {
                return new CtepImportService() {
                    public Organization importCtepOrganization(Ii orgId) throws JMSException {
                        return null;
                    }
                    public Person importCtepPerson(Ii personId) throws JMSException {
                        return null;
                    }
                    public void nullifyCtepOrganization(Ii orgId, Ii duplicateOfId, OrganizationType orgType)
                            throws JMSException {
                    }
                };
            }
        });

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(PERSON_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        action.setFile(f);
        assertEquals(Action.SUCCESS, action.uploadPeople());

        Iterator<String> itr= ActionHelper.getMessages().iterator();
        assertTrue(itr.hasNext());
        assertEquals("0 records successfully imported.", itr.next());
        assertTrue(itr.hasNext());
        String next = itr.next();
        assertTrue(next.startsWith("The following line(s) did not correspond to a record in ctep: "));
        assertFalse(itr.hasNext());
    }

    /**
     * test the upload action person file all fail.
     */
    @Test
    public void testUploadPersonsAllFail() throws Exception {
        PoRegistry.getInstance().setServiceLocator(new MockServiceLocator() {
            @Override
            public CtepImportService getCtepImportService() {
                return new CtepImportService() {
                    public Organization importCtepOrganization(Ii orgId) throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                    public Person importCtepPerson(Ii personId) throws JMSException {
                        throw new HibernateException("Bogus");
                    }
                    public void nullifyCtepOrganization(Ii orgId, Ii duplicateOfId, OrganizationType orgType)
                            throws JMSException {
                        throw new HibernateException("Bogus");
                    }

                };
            }
        });

        URL fileUrl = ClassLoader.getSystemClassLoader().getResource(PERSON_FILE_NAME);
        File f = new File(fileUrl.toURI());

        CtepImportAction action = new CtepImportAction();
        action.setFile(f);
        assertEquals(Action.SUCCESS, action.uploadPeople());

        Iterator<String> itr= ActionHelper.getMessages().iterator();
        assertTrue(itr.hasNext());
        assertEquals("0 records successfully imported.", itr.next());
        assertTrue(itr.hasNext());
        String next = itr.next();
        assertTrue(next.startsWith("An error occurred processing the following line(s): "));
        assertFalse(itr.hasNext());
    }

    @Test
    public void testStart() {
        CtepImportAction action = new CtepImportAction();
        assertEquals(Action.INPUT, action.start());
    }
}
