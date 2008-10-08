package gov.nih.nci.coppa.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests to ensure our topic is deployed, configured and operational
 */
public class TopicIntegrationTest {

    public static final String SUBSCRIBER_ROLE_USER = "subscriber";
    public static final String SUBSCRIBER_ROLE_USER_PASS = "pass";
    public static final String CONNECTION_FACTORY_JNDI_BINDING_NAME = "/POConnectionFactory";
    private static final String PUBLISHER_ROLE_USER_PASS = "pass";
    private static final String PUBLISHER_ROLE_USER = "publisher";
    private static final String PO_TOPIC_NAME = "POTopic";

    @Before
    public void setUp() throws Exception {
        JBossMbeanUtility.removeAllMessagesOnTopic(getDestinationJNDIName(), PO_TOPIC_NAME);
    }

    @After
    public void tearDown() throws Exception {
        JBossMbeanUtility.removeAllMessagesOnTopic(getDestinationJNDIName(), PO_TOPIC_NAME);
    }

    @Test
    public void publishMessageToTopicThenHaveMultipleSubscribersReceiveMessage() throws Exception {

        // create subscription1
        subscribe("subscriber1");
        subscribe("subscriber2");
        // create subscription2

        // publish a message
        String msg = "" + System.currentTimeMillis();
        publish(msg);

        // consume messages for both subscriptions
        assertEquals(msg, receiveMessage("subscriber1"));
        assertEquals(msg, receiveMessage("subscriber2"));
    }

    @Test
    public void subscribeThenUnsubscribeThenPostMessageThenReceiveMessage() throws Exception {
        // create subscription1
        subscribe("subscriber1");
        unsubscribe("subscriber1");
        // publish a message
        String msg = "" + System.currentTimeMillis();
        publish(msg);

        // attempt to get both messages
        assertNull("Should not receive a message since your subscription does not exist", receiveMessage("subscriber1"));

        publish(msg);

        // attempt to get message
        assertEquals(msg, receiveMessage("subscriber1"));
    }

    private String unsubscribe(String subscriptionIdentity) throws NamingException, JMSException, Exception {
        Connection connection = null;
        String messageReceived = null;
        try {
            ConnectionFactory cf = (ConnectionFactory) RemoteServiceHelper
                    .lookupSubscriber(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            session.unsubscribe(subscriptionIdentity);

            displayProviderInfo(connection.getMetaData());
        } finally {
            close(connection);
        }
        return messageReceived;
    }

    private String subscribe(String subscriptionIdentity) throws NamingException, JMSException, Exception {
        InitialContext ic = null;
        Connection connection = null;
        String messageReceived = null;
        try {
            ConnectionFactory cf = (ConnectionFactory) RemoteServiceHelper
                    .lookupSubscriber(TopicIntegrationTest.CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) RemoteServiceHelper.lookupSubscriber(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            TopicSubscriber durableSubscriber = session.createDurableSubscriber(topic, subscriptionIdentity);

            durableSubscriber.close();
            displayProviderInfo(connection.getMetaData());
        } finally {
            close(connection);
        }
        return messageReceived;
    }

    private String receiveMessage(String subscriptionIdentity) throws NamingException, JMSException, Exception {
        Connection connection = null;
        String messageReceived = null;
        try {
            ConnectionFactory cf = (ConnectionFactory) RemoteServiceHelper
                    .lookupSubscriber(TopicIntegrationTest.CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) RemoteServiceHelper.lookupSubscriber(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            connection.setClientID(subscriptionIdentity);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
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
            close(connection);
        }
        return messageReceived;
    }

    private String publish(String textMsg) throws NamingException, JMSException, Exception {
        Connection connection = null;
        String messagePublised = null;
        try {
            ConnectionFactory cf = (ConnectionFactory) RemoteServiceHelper
                    .lookupPublisher(CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) RemoteServiceHelper.lookupPublisher(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(PUBLISHER_ROLE_USER, PUBLISHER_ROLE_USER_PASS);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer publisher = session.createProducer(topic);
            connection.start();

            TextMessage message = session.createTextMessage(textMsg);
            publisher.send(message);
            log("The message (" + textMsg + ") was successfully published on the topic");

            displayProviderInfo(connection.getMetaData());

        } finally {
            close(connection);
        }
        return messagePublised;
    }

    @Test
    public void authenticateToTopicAsSubscriber() throws Exception {

        Connection connection = null;

        try {
            ConnectionFactory cf = (ConnectionFactory) RemoteServiceHelper
                    .lookupSubscriber(TopicIntegrationTest.CONNECTION_FACTORY_JNDI_BINDING_NAME);
            Topic topic = (Topic) RemoteServiceHelper.lookupSubscriber(getDestinationJNDIName());
            log("Topic " + getDestinationJNDIName() + " exists");

            connection = cf.createConnection(SUBSCRIBER_ROLE_USER, SUBSCRIBER_ROLE_USER_PASS);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer subscriber = session.createConsumer(topic);

            ExampleListener messageListener = new ExampleListener();
            subscriber.setMessageListener(messageListener);
            connection.start();

            connection.stop();
            displayProviderInfo(connection.getMetaData());

        } finally {
            close(connection);
        }
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
        return "topic/" + PO_TOPIC_NAME;
    }

    protected void log(String s) {
        System.out.println(s);
    }

    protected void displayProviderInfo(ConnectionMetaData metaData) throws Exception {
        String info = "Connected to " + metaData.getJMSProviderName() + " version " + metaData.getProviderVersion()
                + " (" + metaData.getProviderMajorVersion() + "." + metaData.getProviderMinorVersion() + ")";

        System.out.println(info);
    }

    private void close(Connection connection) {
        // ALWAYS close your connection in a finally block to avoid leaks.
        // Closing connection also takes care of closing its related objects
        // e.g. sessions.
        closeConnection(connection);
    }

    public class ExampleListener implements MessageListener {
        private Message message;

        public synchronized void onMessage(Message msg) {
            this.message = msg;
            notifyAll();
        }

        public synchronized Message getMessage() {
            return message;
        }

        protected synchronized void waitForMessage() {
            if (message != null) {
                return;
            }

            try {
                wait(5000);
            } catch (InterruptedException e) {
                // OK
            }
        }
    }

}
