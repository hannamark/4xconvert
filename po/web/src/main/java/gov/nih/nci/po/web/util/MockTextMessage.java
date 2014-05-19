/**
 * 
 */
package gov.nih.nci.po.web.util;

import java.util.Enumeration;
import java.util.UUID;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author Denis G. Krylov
 * 
 */
@SuppressWarnings("PMD")
public final class MockTextMessage implements TextMessage {

    private String text;

    private String id = UUID.randomUUID().toString();

    /**
     * @param text
     *            text
     */
    public MockTextMessage(String text) { // NOPMD
        this.text = text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#acknowledge()
     */
    @Override
    public void acknowledge() throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#clearBody()
     */
    @Override
    public void clearBody() throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#clearProperties()
     */
    @Override
    public void clearProperties() throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getBooleanProperty(java.lang.String)
     */
    @Override
    public boolean getBooleanProperty(String arg0) throws JMSException {
        
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getByteProperty(java.lang.String)
     */
    @Override
    public byte getByteProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getDoubleProperty(java.lang.String)
     */
    @Override
    public double getDoubleProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getFloatProperty(java.lang.String)
     */
    @Override
    public float getFloatProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getIntProperty(java.lang.String)
     */
    @Override
    public int getIntProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSCorrelationID()
     */
    @Override
    public String getJMSCorrelationID() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSCorrelationIDAsBytes()
     */
    @Override
    public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSDeliveryMode()
     */
    @Override
    public int getJMSDeliveryMode() throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSDestination()
     */
    @Override
    public Destination getJMSDestination() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSExpiration()
     */
    @Override
    public long getJMSExpiration() throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSMessageID()
     */
    @Override
    public String getJMSMessageID() throws JMSException {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSPriority()
     */
    @Override
    public int getJMSPriority() throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSRedelivered()
     */
    @Override
    public boolean getJMSRedelivered() throws JMSException {
        
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSReplyTo()
     */
    @Override
    public Destination getJMSReplyTo() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSTimestamp()
     */
    @Override
    public long getJMSTimestamp() throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getJMSType()
     */
    @Override
    public String getJMSType() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getLongProperty(java.lang.String)
     */
    @Override
    public long getLongProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getObjectProperty(java.lang.String)
     */
    @Override
    public Object getObjectProperty(String arg0) throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getPropertyNames()
     */
    @Override
    public Enumeration getPropertyNames() throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getShortProperty(java.lang.String)
     */
    @Override
    public short getShortProperty(String arg0) throws JMSException {
        
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#getStringProperty(java.lang.String)
     */
    @Override
    public String getStringProperty(String arg0) throws JMSException {
        
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#propertyExists(java.lang.String)
     */
    @Override
    public boolean propertyExists(String arg0) throws JMSException {
        
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setBooleanProperty(java.lang.String, boolean)
     */
    @Override
    public void setBooleanProperty(String arg0, boolean arg1)
            throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setByteProperty(java.lang.String, byte)
     */
    @Override
    public void setByteProperty(String arg0, byte arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setDoubleProperty(java.lang.String, double)
     */
    @Override
    public void setDoubleProperty(String arg0, double arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setFloatProperty(java.lang.String, float)
     */
    @Override
    public void setFloatProperty(String arg0, float arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setIntProperty(java.lang.String, int)
     */
    @Override
    public void setIntProperty(String arg0, int arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSCorrelationID(java.lang.String)
     */
    @Override
    public void setJMSCorrelationID(String arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSCorrelationIDAsBytes(byte[])
     */
    @Override
    public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSDeliveryMode(int)
     */
    @Override
    public void setJMSDeliveryMode(int arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSDestination(javax.jms.Destination)
     */
    @Override
    public void setJMSDestination(Destination arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSExpiration(long)
     */
    @Override
    public void setJMSExpiration(long arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSMessageID(java.lang.String)
     */
    @Override
    public void setJMSMessageID(String arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSPriority(int)
     */
    @Override
    public void setJMSPriority(int arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSRedelivered(boolean)
     */
    @Override
    public void setJMSRedelivered(boolean arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSReplyTo(javax.jms.Destination)
     */
    @Override
    public void setJMSReplyTo(Destination arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSTimestamp(long)
     */
    @Override
    public void setJMSTimestamp(long arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setJMSType(java.lang.String)
     */
    @Override
    public void setJMSType(String arg0) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setLongProperty(java.lang.String, long)
     */
    @Override
    public void setLongProperty(String arg0, long arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setObjectProperty(java.lang.String,
     * java.lang.Object)
     */
    @Override
    public void setObjectProperty(String arg0, Object arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setShortProperty(java.lang.String, short)
     */
    @Override
    public void setShortProperty(String arg0, short arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.Message#setStringProperty(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void setStringProperty(String arg0, String arg1) throws JMSException {
        

    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.TextMessage#getText()
     */
    @Override
    public String getText() throws JMSException {
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.jms.TextMessage#setText(java.lang.String)
     */
    @Override
    public void setText(String arg0) throws JMSException {
        

    }

}
