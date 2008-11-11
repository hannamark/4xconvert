/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The caarray-app
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This caarray-app Software License (the License) is between NCI and You. You (or
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
 * its rights in the caarray-app Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the caarray-app Software; (ii) distribute and
 * have distributed to and by third parties the caarray-app Software and any
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

import gov.nih.nci.po.data.bo.EntityStatus;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverterRegistry;
import gov.nih.nci.po.util.JNDIUtil;
import gov.nih.nci.services.SubscriberUpdateMessage;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * EJB that handles publishing changes to people and organizations to
 * a JMS queue.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MessageProducerBean implements MessageProducerLocal {

    /**
     * Name of the topic to which PO publishes.
     */
    public static final String TOPIC_NAME = "POTopic";
    private static final Logger LOG = Logger.getLogger(MessageProducerBean.class);
    static final String USERNAME_PROPERTY = "jms.publisher.username";
    static final String PASSWORD_PROPERTY = "jms.publisher.password";
    private final MessageProducer messageProducer;
    private final Session session;

    /**
     * Constructor to create the message publisher.
     * @throws NamingException on error
     * @throws JMSException on error
     * @throws IOException on io error
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public MessageProducerBean() throws NamingException, JMSException, IOException {
        Properties p = getJndiProperties();
        InitialContext ic = new InitialContext(p);
        TopicConnectionFactory connectionFactory = getTopicConnectionFactory(ic);
        Topic topic = getTopic(ic);

        String username = (p.getProperty(USERNAME_PROPERTY) == null) ? "publisher" : p.getProperty(USERNAME_PROPERTY);
        String password = (p.getProperty(PASSWORD_PROPERTY) == null) ? "pass" : p.getProperty(PASSWORD_PROPERTY);

        Connection connection = connectionFactory.createTopicConnection(username, password);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        messageProducer = session.createProducer(topic);
    }

    /**
     * @return the properties for jndi
     * @throws IOException if a problem was encountered
     */
    protected Properties getJndiProperties() throws IOException {
        return JNDIUtil.getProperties();
    }

    /**
     * Gets the topic connection factory. These are overwritable for unit tests.
     * @param ic the initial context.
     * @return the factory.
     */
    protected TopicConnectionFactory getTopicConnectionFactory(InitialContext ic) {
        return (TopicConnectionFactory) JNDIUtil.lookup(ic, "/POConnectionFactory");
    }

    /**
     * Get the topic. These are overwritable for unit tests.
     * @param ic the initial context
     * @return the topic.
     */
    protected Topic getTopic(InitialContext ic) {
        return (Topic) JNDIUtil.lookup(ic, "/topic/" + MessageProducerBean.TOPIC_NAME);
    }

    /**
     * {@inheritDoc}
     */
    public void sendUpdate(Organization org) throws JMSException {
        if (!EntityStatus.PENDING.equals(org.getStatusCode())) {
          SubscriberUpdateMessage msg
            = new SubscriberUpdateMessage(IdConverterRegistry.find(org.getClass()).convertToIi(org.getId()));
          send(msg);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void sendUpdate(ResearchOrganization rOrg) throws JMSException {
        if (!RoleStatus.PENDING.equals(rOrg.getStatus())) {
            SubscriberUpdateMessage msg
            = new SubscriberUpdateMessage(IdConverterRegistry.find(rOrg.getClass()).convertToIi(rOrg.getId()));
            send(msg);
        }
    }
    
    private synchronized void send(Serializable o) throws JMSException {
        ObjectMessage msg = session.createObjectMessage(o);
        messageProducer.send(msg);
    }

    /**
     * Called as part of the EJB lifecycle, closes the JMS session.
     */
    @SuppressWarnings("unused")
    @PreDestroy
    private void preDestroy() {
        try {
            session.close();
        } catch (JMSException e) {
            LOG.error("Unable to close session: " + e.getMessage(), e);
        }
    }
}
