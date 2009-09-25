package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.service.external.CtepMessageBean.RecordType;
import gov.nih.nci.po.service.external.CtepMessageBean.TransactionType;
import gov.nih.nci.po.util.EmailLogger;
import gov.nih.nci.po.util.jms.TextMessageStub;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author gax
 */
public class CtepMessageBeanTest {

    @Test
    public void skipMessage() throws Exception {
        CtepMessageBean bean = new CtepMessageBean();
        final TextMessage txtMsg = new TextMessageStub();
        txtMsg.setText("<XMLHERE>");
        TransactionType trxType = TransactionType.DUPLICATE;
        RecordType msgType = RecordType.ORGANIZATION;
        Ii id = new Ii();
        id.setExtension("abc");

        StringBuilder b = new StringBuilder();
        b.append("Skipping the processing of message TRANSACTION_TYPE (");
        b.append(trxType);
        b.append(") and RECORD_TYPE (");
        b.append(msgType );
        b.append(") and RECORD_ID (");
        b.append(id .getExtension());
        b.append(')');
        assertEquals(b.toString(), bean.getSkipMessage(trxType, msgType, id));
    }

    private StringWriter logWriter;

    @Before
    public void setUpLogger() {
        WriterAppender wa = (WriterAppender) EmailLogger.LOG.getAppender("EMAIL");
        logWriter = new StringWriter();
        wa.setWriter(logWriter);
    }

    @After
    public void undoLogger() {
        WriterAppender wa = (WriterAppender) EmailLogger.LOG.getAppender("EMAIL");
        wa.close();
        logWriter.getBuffer().setLength(0);
        logWriter = null;
    }

    /**
     * Test of onMessage method, of class CtepMessageBean.
     * @throws JMSException
     */
    @Test
    public void testOnMessage() throws IOException, JMSException {
        int tests = 17;
        MockTextMessage[] messages = new MockTextMessage[tests];
        for (int i = 0; i < tests; i++) {
            messages[i] = new MockTextMessage(i);
        }

        Checker[] checkers = new Checker[tests];
        int idx = 0;
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.ORGANIZATION, "03013", idx++);
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.ORGANIZATION_ADDRESS, "03013", idx++);
        checkers[idx] = new Checker(true, TransactionType.NULLIFY, RecordType.ORGANIZATION_ADDRESS, "03013", idx++,
                OrganizationType.HEALTHCAREFACILITY, "RSB003");
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.ORGANIZATION_ADDRESS, "03013", idx++);
        checkers[idx] = new Checker(true, TransactionType.NULLIFY, RecordType.ORGANIZATION, "03013", idx++,
                OrganizationType.HEALTHCAREFACILITY, "RSB003");
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.PERSON, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.PERSON_ADDRESS, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.PERSON_CONTACT, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.UPDATE, RecordType.PERSON, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.NULLIFY, RecordType.PERSON_ADDRESS, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.PERSON_ADDRESS, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.NULLIFY, RecordType.PERSON_CONTACT, "42", idx++);
        checkers[idx] = new Checker(true, TransactionType.INSERT, RecordType.PERSON_CONTACT, "42", idx++);
        checkers[idx] = new Checker(false, null, null, null, idx++ /*,
                "Failed to process JMS message ID:13",
                "org.xml.sax.SAXParseException: The markup in the document preceding the root element must be well-formed",
                "at org.apache.commons.digester.Digester.parse"*/);
        checkers[idx] = new Checker(false, null, null, null, idx++/*,
                "Failed to process JMS message ID:14",
                "java.lang.IllegalArgumentException: Unsuported Record Type in message BODUS_TYPE",
                "at gov.nih.nci.po.service.external.CtepMessageBean.processMessage"*/);
        checkers[idx] = new Checker(true, TransactionType.REJECT, RecordType.ORGANIZATION, "abc", idx++);
        checkers[idx] = new Checker(true, TransactionType.DUPLICATE, RecordType.ORGANIZATION, "abc", idx++);

        for(int i = 0; i < tests; i++) {
            testOnMessage(messages[i], checkers[i]);
        }
    }

    private void testOnMessage(MockTextMessage msg, final Checker checker) {

        CtepMessageBean bean = new CtepMessageBean() {
            @Override
            protected void processMessage(TransactionType trxType, RecordType msgType, Ii id, OrganizationType orgType,
                    Ii duplicateOf) {
                checker.check(trxType, msgType, id, orgType, duplicateOf);
            }
        };

        bean.onMessage(msg);
        checker.assertCall();

        logWriter.getBuffer().setLength(0);
        logWriter.getBuffer().trimToSize();
    }

    class Checker {
        boolean expectedCalled;
        TransactionType expectedTrxType;
        RecordType expectedMsgType;
        String expectedId;
        String expectedDuplicateOfId;
        OrganizationType expectedOrgType;
        String[] errors;
        boolean extendedInfo;

        boolean called;
        TransactionType trxType;
        RecordType msgType;
        OrganizationType orgType;
        Ii id;
        Ii duplicateOfId;

        int idx;

        public Checker(boolean called, TransactionType trxType, RecordType msgType, String id, int idx, String... errors) {
            this.expectedCalled = called;
            this.expectedTrxType = trxType;
            this.expectedMsgType = msgType;
            this.expectedId = id;
            this.idx = idx;
            this.errors = errors;
        }

        public Checker(boolean called, TransactionType trxType, RecordType msgType, String id, int idx,
                OrganizationType orgType, String duplicateOfId, String... errors) {
            this(called, trxType, msgType, id, idx, errors);
            this.extendedInfo = true;
            this.expectedOrgType = orgType;
            this.expectedDuplicateOfId = duplicateOfId;
        }

        void check(TransactionType tType, RecordType mType, Ii ii, OrganizationType orgType, Ii duplicateOfIi) {
            called = true;
            this.trxType = tType;
            this.msgType = mType;
            this.id = ii;
            this.orgType = orgType;
            this.duplicateOfId = duplicateOfIi;
        }

        public void assertCall() {
            Assert.assertEquals("called" + idx, expectedCalled, called);
            if (called) {
                Assert.assertEquals("trxType" + idx, expectedTrxType, trxType);
                Assert.assertEquals("msgType" + idx, expectedMsgType, msgType);
                Assert.assertEquals("id" + idx, expectedId, id.getExtension());
                if (extendedInfo) {
                    Assert.assertEquals("orgType" + idx, expectedOrgType, orgType);
                    Assert.assertEquals("duplicateOfId" + idx, expectedDuplicateOfId, (duplicateOfId == null ? null
                            : duplicateOfId.getExtension()));
                }
            }

            // check logger entries.
            StringBuffer log = logWriter.getBuffer();
            if (log.length() > 0 && errors.length == 0) {
                Assert.fail("unexpected log entry for msg " + idx + "  :" + log);
            }
            for (String m : errors) {
                Assert.assertTrue("expected error not found for msg " + idx + ": " + m + "within \n " + log.toString(),
                        log.indexOf(m) >= 0);
            }
        }

    }

    private class MockTextMessage implements TextMessage {
        private final String text;
        private final int id;
        MockTextMessage(int id) throws IOException {
            byte[] b = new byte[512];
            int c = CtepMessageBeanTest.class.getResourceAsStream("msg"+id+".xml").read(b);
            text = new String(b, 0, c);
            this.id = id;
        }

        public void setText(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getText() throws JMSException {
            return text;
        }

        public String getJMSMessageID() throws JMSException {
            return String.valueOf(id);
        }

        public void setJMSMessageID(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long getJMSTimestamp() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSTimestamp(long arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSCorrelationID(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getJMSCorrelationID() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Destination getJMSReplyTo() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSReplyTo(Destination arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Destination getJMSDestination() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSDestination(Destination arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getJMSDeliveryMode() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSDeliveryMode(int arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean getJMSRedelivered() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSRedelivered(boolean arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getJMSType() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSType(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long getJMSExpiration() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSExpiration(long arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getJMSPriority() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setJMSPriority(int arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void clearProperties() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean propertyExists(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean getBooleanProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public byte getByteProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public short getShortProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public int getIntProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public long getLongProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public float getFloatProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double getDoubleProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String getStringProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Object getObjectProperty(String arg0) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Enumeration<?> getPropertyNames() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setBooleanProperty(String arg0, boolean arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setByteProperty(String arg0, byte arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setShortProperty(String arg0, short arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setIntProperty(String arg0, int arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setLongProperty(String arg0, long arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setFloatProperty(String arg0, float arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setDoubleProperty(String arg0, double arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setStringProperty(String arg0, String arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void setObjectProperty(String arg0, Object arg1) throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void acknowledge() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void clearBody() throws JMSException {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
