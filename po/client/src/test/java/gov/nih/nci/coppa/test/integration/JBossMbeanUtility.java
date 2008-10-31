/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package gov.nih.nci.coppa.test.integration;

import gov.nih.nci.coppa.test.remoteapi.RemoteServiceHelper;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

/**
 * A utility class from JBM v1.40.SP3 that was adapted to meet our needs
 * 
 * @author <a href="mailto:ovidiu@feodorov.com">Ovidiu Feodorov</a>
 * @version <tt>$Revision: 2977 $</tt>
 * 
 *          $Id: Util.java 2977 2007-08-08 15:32:14Z timfox $
 */
public class JBossMbeanUtility {
    // Constants -----------------------------------------------------

    // Static --------------------------------------------------------
    private static final Logger LOG = Logger.getLogger(JBossMbeanUtility.class);
    private static String DEFALUT_SERVER_PEER_NAME = "PODestinationManager";

    public static void setDefaultServerPeerName(String name) {
        DEFALUT_SERVER_PEER_NAME = name;
    }

    public static boolean doesDestinationExist(String jndiName) throws Exception {
        return doesDestinationExist(jndiName);
    }

    public static void deployTopic(String jndiName) throws Exception {
        deployTopic(jndiName, DEFALUT_SERVER_PEER_NAME);
    }

    public static void deployTopic(String jndiName, String serverPeerName) throws Exception {
        MBeanServerConnection mBeanServer = lookupMBeanServerProxy();
        ObjectName serverObjectName = new ObjectName("jboss.mq:service=" + serverPeerName);
        String queueName = jndiName.substring(jndiName.lastIndexOf('/') + 1);
        mBeanServer.invoke(serverObjectName, "deployTopic", new Object[] { queueName, jndiName }, new String[] {
                "java.lang.String", "java.lang.String" });

        LOG.debug("Queue " + jndiName + " deployed");
    }

    public static void destroyTopic(String jndiName) throws Exception {
        destroyTopic(jndiName, DEFALUT_SERVER_PEER_NAME);
    }

    public static void destroyTopic(String jndiName, String serverPeerName) throws Exception {
        MBeanServerConnection mBeanServer = lookupMBeanServerProxy();
        ObjectName serverObjectName = new ObjectName("jboss.mq:service=" + serverPeerName);
        String queueName = jndiName.substring(jndiName.lastIndexOf('/') + 1);

        mBeanServer.invoke(serverObjectName, "destroyTopic", new Object[] { queueName },
                new String[] { "java.lang.String" });

        LOG.debug("Queue " + jndiName + " undeployed");
    }

    public static MBeanServerConnection lookupMBeanServerProxy() throws Exception {
        return RemoteServiceHelper.lookupMBeanServerProxy();
    }

    public static void removeAllMessagesOnTopic(String jndiName, String topicName) throws Exception {
        MBeanServerConnection mBeanServer = lookupMBeanServerProxy();
        ObjectName serverObjectName = new ObjectName("jboss.mq.destination:service=Topic,name=" + topicName);
        mBeanServer.invoke(serverObjectName, "removeAllMessages", new Object[] {}, new String[] {});

        LOG.debug("Removed all messages for " + jndiName + " topic/queue");
    }

    public static Object listAllSubscriptionsOnTopic(String jndiName, String topicName)
            throws Exception {
        MBeanServerConnection mBeanServer = lookupMBeanServerProxy();
        ObjectName serverObjectName = new ObjectName("jboss.mq.destination:service=Topic,name=" + topicName);
        Object invokeResult = mBeanServer.invoke(serverObjectName, "listAllSubscriptions", new Object[] {},
                new String[] {});

        LOG.debug("Removed all messages for " + jndiName + " topic/queue");
        return invokeResult;
    }

    // Attributes ----------------------------------------------------

    // Constructors --------------------------------------------------

    // Public --------------------------------------------------------

    // Package protected ---------------------------------------------

    // Protected -----------------------------------------------------

    // Private -------------------------------------------------------

    // Inner classes -------------------------------------------------
}
