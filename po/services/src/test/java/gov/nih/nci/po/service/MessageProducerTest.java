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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.Curatable;
import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.JMSLogRecord;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.jms.TopicConnectionFactoryStub;
import gov.nih.nci.po.util.jms.TopicStub;
import gov.nih.nci.services.SubscriberUpdateMessage;

import java.io.IOException;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 *
 */
public class MessageProducerTest extends AbstractHibernateTestCase {

    public static <T extends Curatable> void assertMessageCreated(T id, AbstractCuratableServiceBean<T> service, boolean isCreate)
            throws JMSException {
        TopicStub topic = (TopicStub) ((MessageProducerBean) service.getPublisher()).getTopic(null);
        assertMessageCreated(id, isCreate, topic);
    }

    private static <T extends PersistentObject> void assertMessageCreated(T id, boolean isCreate, Topic topic) throws JMSException {
        TopicStub topicStub = (TopicStub) topic;
        assertEquals(1, topicStub.messages.size());
        Message msg = topicStub.messages.remove(0);
        assertTrue(msg instanceof ObjectMessage);
        ObjectMessage om = (ObjectMessage) msg;
        assertEquals(SubscriberUpdateMessage.class.getName(), om.getObject().getClass().getName());
        assertEquals(id.getId(), IiConverter.convertToLong(((SubscriberUpdateMessage) om.getObject()).getId()));
        if (isCreate) {
            assertEquals("CREATE", om.getStringProperty("announcementType"));
        } else {
            assertEquals("UPDATE", om.getStringProperty("announcementType"));
        }
        
        assertNotNull(
        ((JMSLogRecord) PoHibernateUtil.getCurrentSession()
                .createQuery(" from JMSLogRecord order by createdDate desc")
                .setMaxResults(1).list().get(0)));
    }

    public static <T extends Curatable<?, ?>> void assertNoMessageCreated(T id, AbstractCuratableServiceBean<T> service) {
        TopicStub topic = (TopicStub) ((MessageProducerBean) service.getPublisher()).getTopic(null);
        assertEquals(0, topic.messages.size());
    }

    public static <T extends Curatable<?, ?>> void clearMessages(AbstractCuratableServiceBean<T> service) {
        TopicStub topic = (TopicStub) ((MessageProducerBean) service.getPublisher()).getTopic(null);
        topic.messages.clear();
    }

    @Before
    public void init() throws Exception {

    }

    @Test
    public void constructor() throws NamingException, JMSException, IOException {
        MessageProducerBean mp = new MessageProducerBean() {
            private TopicConnectionFactory connectionFactory;
            private Topic topic;

            /**
             * {@inheritDoc}
             */
            @Override
            protected TopicConnectionFactory getTopicConnectionFactory(InitialContext ic) {
                if (connectionFactory == null) {
                    connectionFactory = new TopicConnectionFactoryStub();
                }
                return connectionFactory;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            protected Topic getTopic(InitialContext ic) {
                if (topic == null) {
                    topic = new TopicStub(MessageProducerBean.TOPIC_NAME);
                }
                return topic;
            }

            @Override
            protected Properties getJndiProperties() throws IOException {
                Properties properties = new Properties();
                properties.put(MessageProducerBean.USERNAME_PROPERTY, "bogus");
                properties.put(MessageProducerBean.PASSWORD_PROPERTY, "bogus123");
                return properties;
            }
        };
        assertNotNull(mp.getConnectionFactory());
        assertNotNull(mp.getTopic());
    }

    @Test
    public void messageSentRegardlessOfEntityStatus() throws Exception {
        MessageProducerBean mp = EjbTestHelper.getMessageProducer();
        Organization o = new Organization();
        o.setId(-1L);
        o.setStatusCode(EntityStatus.PENDING);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());


        o.setStatusCode(EntityStatus.ACTIVE);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());

        o.setStatusCode(EntityStatus.NULLIFIED);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());


        o.setStatusCode(EntityStatus.INACTIVE);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());

    }
    @Test
    public void messageSentRegardlessOfRoleStatus() throws Exception {
        MessageProducerBean mp = EjbTestHelper.getMessageProducer();
        ClinicalResearchStaff o = new ClinicalResearchStaff();
        o.setId(-1L);
        o.setStatus(RoleStatus.PENDING);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());


        o.setStatus(RoleStatus.ACTIVE);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());

        o.setStatus(RoleStatus.NULLIFIED);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());


        o.setStatus(RoleStatus.SUSPENDED);
        mp.sendCreate(o.getClass(), o);
        assertMessageCreated(o, true, mp.getTopic());

        mp.sendUpdate(o.getClass(), o);
        assertMessageCreated(o, false, mp.getTopic());

    }

}
