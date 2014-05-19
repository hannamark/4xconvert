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
package gov.nih.nci.coppa.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.UUID;

import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests to ensure our topic is deployed, configured and operational
 */
public class TopicIntegrationTest {

    private static final Logger LOG = Logger.getLogger(TopicIntegrationTest.class);

    private static final String SUBSCRIBER_ROLE_USER = "subscriber";
    private static final String SUBSCRIBER_ROLE_USER_PASS = "pass";
    private static final String CONNECTION_FACTORY_JNDI_BINDING_NAME = "jms/PORemoteConnectionFactory";
    private static final String PUBLISHER_ROLE_USER_PASS = "pass";
    private static final String PUBLISHER_ROLE_USER = "publisher";
    private static final String PO_TOPIC_NAME = "jms/topic/POTopic";

    @Before
    public void setUp() throws Exception {
        //JBossMbeanUtility.removeAllMessagesOnTopic(getDestinationJNDIName(),PO_TOPIC_NAME);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void publishMessageToTopicThenHaveMultipleSubscribersReceiveMessage() throws Exception {

        subscribe("subscriber1");
        subscribe("subscriber2");
        
        // get rid of possible old messages not yet received
        consumeAll("subscriber1");      
        consumeAll("subscriber2");        

        // publish a message
        String msg = "publishMessageToTopicThenHaveMultipleSubscribersReceiveMessage_"+UUID.randomUUID().toString();
        publish(msg);

        // consume messages for both subscriptions
        assertEquals(msg, receiveMessage("subscriber1"));
        assertEquals(msg, receiveMessage("subscriber2"));
    }

    @Test
    public void subscribeThenUnsubscribeThenPostMessageThenReceiveMessage() throws Exception {
        //create subscription1
        subscribe("subscriber1");
        consumeAll("subscriber1");    
        unsubscribe("subscriber1");
        // publish a message
        String msg = "subscribeThenUnsubscribeThenPostMessageThenReceiveMessage_"+UUID.randomUUID().toString();
        publish(msg);

        // attempt to get  both messages
        assertNull("Should not receive a message since your subscription does not exist", receiveMessage("subscriber1"));

        publish(msg);

        // attempt to get message
        assertEquals(msg, receiveMessage("subscriber1"));
    }

    private String unsubscribe(String subscriptionIdentity)
            throws NamingException, JMSException, Exception {
        InitialContext ic = null;
        Connection connection = null;
        String messageReceived = null;
        try {
            ic = getContext();
            ConnectionFactory cf = (ConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER,
                    SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            session.unsubscribe(subscriptionIdentity);

            displayProviderInfo(connection.getMetaData());
        } finally {
            close(ic, connection);
        }
        return messageReceived;
    }

    private String subscribe(String subscriptionIdentity) throws NamingException, JMSException,
    Exception {
        InitialContext ic = null;
        Connection connection = null;
        String messageReceived = null;
        try {
            ic = getContext();
            ConnectionFactory cf = (ConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) ic.lookup(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER,
                    SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, subscriptionIdentity);

            durableSubscriber.close();
            displayProviderInfo(connection.getMetaData());
        } finally {
            close(ic, connection);
        }
        return messageReceived;
    }
    
    private void consumeAll(String subscriptionIdentity)
            throws NamingException, JMSException, Exception {
        InitialContext ic = null;
        Connection connection = null;       
        try {
            ic = getContext();
            TopicConnectionFactory cf = (TopicConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) ic.lookup(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER,
                    SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber durableSubscriber = session
                    .createDurableSubscriber(topic, subscriptionIdentity);

            ExampleListener messageListener = new ExampleListener(500L);
            durableSubscriber.setMessageListener(messageListener);

            connection.start();

            while (true) {
                messageListener.waitForMessage();
                if (messageListener.getMessage() != null) {
                    messageListener.setMessage(null);
                } else {
                    break;
                }
            }

        } finally {
            close(ic, connection);
        }

    }



    private String receiveMessage(String subscriptionIdentity) throws NamingException, JMSException,
            Exception {
        InitialContext ic = null;
        Connection connection = null;
        String messageReceived = null;
        try {
            ic = getContext();
            TopicConnectionFactory cf = (TopicConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) ic.lookup(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, subscriptionIdentity);

            ExampleListener messageListener = new ExampleListener();
            durableSubscriber.setMessageListener(messageListener);

            connection.start();
            messageListener.waitForMessage();

            TextMessage message = (TextMessage) messageListener.getMessage();
            if (message != null) {
                log("Received message: " + message.getText());
                displayProviderInfo(connection.getMetaData());
                messageReceived = message.getText();
            }

        } finally {
            close(ic, connection);
        }
        return messageReceived;
    }
    
    


    private String publish(String textMsg) throws NamingException, JMSException, Exception {
        InitialContext ic = null;
        Connection connection = null;
        String messagePublised = null;
        try {
            ic = getContext();
            ConnectionFactory cf = (ConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) ic.lookup(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(PUBLISHER_ROLE_USER, PUBLISHER_ROLE_USER_PASS);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(topic);
            connection.start();

            TextMessage message = session.createTextMessage(textMsg);
            publisher.send(message);
            log("The message (" + textMsg + ") was successfully published on the topic");

            displayProviderInfo(connection.getMetaData());

        } finally {
            close(ic, connection);
        }
        return messagePublised;
    }


    @Test
    public void authenticateToTopicAsSubscriber() throws Exception {

        InitialContext ic = null;
        Connection connection = null;

        try {
            ic = getContext();
            ConnectionFactory cf = (ConnectionFactory) ic
                    .lookup(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) ic.lookup(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            MessageConsumer subscriber = session.createConsumer(topic);

            ExampleListener messageListener = new ExampleListener();
            subscriber.setMessageListener(messageListener);
            connection.start();

            connection.stop();
            displayProviderInfo(connection.getMetaData());

        } finally {
            close(ic, connection);
        }
    }

    private InitialContext getContext() throws NamingException {
       return RemoteServiceHelper.createNewJndiContext();
    }

    private void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (JMSException jmse) {
            log("Could not close connection " + connection + " exception was " + jmse);
        }
    }

    protected String getDestinationJNDIName() {
        return PO_TOPIC_NAME;
    }

    protected void log(String s) {
        LOG.info(s);
    }

    protected void displayProviderInfo(ConnectionMetaData metaData)
            throws Exception {
        String info = "Connected to "
                + metaData.getJMSProviderName() + " version "
                + metaData.getProviderVersion() + " ("
                + metaData.getProviderMajorVersion() + "."
                + metaData.getProviderMinorVersion() + ")";

        log(info);
    }

    private void close(InitialContext ic, Connection connection)
            throws Exception {
        if (ic != null) {
            try {
                ic.close();
            } catch (Exception e) {
                throw e;
            }
        }

        // ALWAYS close your connection in a finally block to avoid leaks.
        // Closing connection also takes care of closing its related objects
        // e.g. sessions.
        closeConnection(connection);
    }

    public class ExampleListener implements MessageListener {
        private Message message;
        private long blockTimeMillis = 5000L;

        public ExampleListener() {
        }

        public ExampleListener(long blockTimeMillis) {
            this.blockTimeMillis = blockTimeMillis;
        }

        public synchronized void onMessage(Message msg) {
            this.message = msg;
            notifyAll();
        }

        public synchronized Message getMessage() {
            return message;
        }
        
        public synchronized void setMessage(Message message) {
            this.message = message;
        }

        protected synchronized void waitForMessage() {
            if (message != null) {
                return;
            }

            try {
                wait(blockTimeMillis);
            } catch (InterruptedException e) {
                // OK
            }
        }
    }

}
