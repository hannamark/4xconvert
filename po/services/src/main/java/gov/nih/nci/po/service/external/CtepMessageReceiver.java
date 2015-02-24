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
package gov.nih.nci.po.service.external;

import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.Context;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import weblogic.jms.client.WLConnectionImpl;

/**
 * CTEP JMS connection and subscription service. Startup is asynchronous and non-failing, for speed and ease of
 * deployment in an environment that may no have CTEP services (testing, dev...).
 *
 *
 * @author gax
 */
@Singleton
@Startup
public class CtepMessageReceiver extends CtepMessageBean implements CtepMessageReceiverMBean, Runnable {

    private static final Logger LOG = Logger.getLogger(CtepMessageReceiver.class);
    private MBeanServer platformMBeanServer;
    private ObjectName objectName;
    private Context initialContext;
    private TopicConnection topicConnection;
    private TopicSession topicSession;
    private TopicSubscriber topicSubscriber;
    private String topicConnectionFactoryName = CtepImportServiceBean.getConfig().getProperty(
            "ctep.jms.topic.connection.factory.name");
    private String topicName = CtepImportServiceBean.getConfig().getProperty("ctep.jms.topic.name");;
    private final String clientId = CtepImportServiceBean.getConfig().getProperty("ctep.jms.client.id");
    private String subscriptionName = CtepImportServiceBean.getConfig().getProperty("ctep.jms.subscription.name");
    private boolean busy = false;
    private String statusMessage;
    
    private static MessageListener messageListener;
    
    /**
     * @return MessageListener
     */
    public static MessageListener getCtepMessageListener() {
        return messageListener;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String getTopicConnectionFactoryName() {
        return topicConnectionFactoryName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setTopicConnectionFactoryName(String topicConnectionFactoryName) {
        this.topicConnectionFactoryName = topicConnectionFactoryName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String getTopicName() {
        return topicName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String getSubscriptionName() {
        return subscriptionName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized String getStatusMessage() {
        return statusMessage;
    }

    private synchronized void setStatusMessage(String msg) {
        statusMessage = msg;
    }

    /**
     * {@inheritDoc}
     */
    public synchronized void unsubscribe() throws JMSException {
        if (!busy && topicSubscriber != null && topicSession != null) {
            topicSubscriber.close();
            topicSession.unsubscribe(subscriptionName);
            setStatusMessage("Unsubscribed");
        }
    }

    /**
     * {@inheritDoc}
     */   
    public void create() {
        // NOOP
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @PostConstruct
    public synchronized void start() {
        // do nothing if still starting.
        if (busy) {
            return;
        }
        if (StringUtils.isBlank(subscriptionName)) {
            LOG.error("property ctep.jms.subscription.name in resource ctep-services.properties not set.");
            throw new IllegalStateException("subscriptionName not set.");
        }
        busy = true; // will remain busy until startup thread finishes.
        statusMessage = "Starting";

        // This line is critical. If Hibernate initialization happens within the user Thread that we start down the
        // line, it won't see JNDI entries and SessionFactory will fail when trying to bind itself to the JNDI.
        // Must initialize PoHibernateUtil HERE.
        PoHibernateUtil.getHibernateHelper();
        
        Thread t = new Thread(this);
        t.start();
        
        try {
            objectName = new ObjectName("PO:type=" + this.getClass().getName());
            platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
            platformMBeanServer.registerMBean(this, objectName);
        } catch (Exception e) {
            LOG.error("Problem during registration of CtepMessageMBean into JMX", e);
        }
        
    }

    /**
     * Do the startup process w/o blocking the management call threads.
     */
    public void run() {
        try {
            
            LOG.info("Delaying connecting to CTEP JMS Topic to let the application deployment complete fully...");
            Thread.sleep(DateUtils.MILLIS_PER_MINUTE);
            LOG.info("Back from sleep; now initiating CTEP JMS Topic connection...");
            
            initialContext = CtepImportServiceBean.createCtepInitialContext();
            TopicConnectionFactory connectionFactory = (TopicConnectionFactory) initialContext
                    .lookup(topicConnectionFactoryName);
            Topic topic = (Topic) initialContext.lookup(topicName);
            topicConnection = connectionFactory.createTopicConnection();
            
            ((WLConnectionImpl) topicConnection).setReconnectPolicy("all");
            final Field f = WLConnectionImpl.class.getSuperclass()
                    .getDeclaredField("TODOREMOVEDebug");
            f.setAccessible(true);
            f.set(null, true);
            
            topicConnection.setClientID(clientId);
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topicSubscriber = topicSession.createDurableSubscriber(topic, subscriptionName);
            topicSubscriber.setMessageListener(CtepMessageReceiver.this);
            topicConnection.start();
            messageListener = CtepMessageReceiver.this;
            LOG.info("CtepMessageMBean started.");
            setStatusMessage("Connected");            
        } catch (Exception e) {
            LOG.error("CtepMessageMBean failed to start.");
            LOG.error(ExceptionUtils.getFullStackTrace(e));
            setStatusMessage(e.toString());
        } finally {
            synchronized (this) {
                busy = false;
                this.notifyAll(); // tell any stop() calls to resume.
            }
        }
    }

    /**
     * {@inheritDoc}
     */    
    public synchronized void stop() {
        try {
            // if startup in progress, wait.
            while (busy) {
                this.wait();
            }
            if (topicSession != null && topicConnection != null && initialContext != null) {
                topicSession.close();
                topicConnection.close();
                initialContext.close();
                topicSession = null;
                topicConnection = null;
                topicSubscriber = null;
                initialContext = null;
                messageListener = null;
                setStatusMessage("Stopped");
                LOG.info("CtepMessageMBean stopped.");
            }
        } catch (Exception ex) {
            LOG.error(ExceptionUtils.getFullStackTrace(ex));
        }
    }

    /**
     * {@inheritDoc}
     */
    @PreDestroy
    public void destroy() {
        try {
            platformMBeanServer.unregisterMBean(this.objectName);
        } catch (Exception e) {
            LOG.error("Problem during unregistration of CtepMessageMBean from JMX", e);
        }
    }
}
