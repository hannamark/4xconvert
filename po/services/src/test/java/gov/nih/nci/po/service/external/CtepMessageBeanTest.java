package gov.nih.nci.po.service.external;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.CtepJMSLogRecord;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.AbstractCuratableServiceBean;
import gov.nih.nci.po.service.AbstractServiceBeanTest;
import gov.nih.nci.po.service.EjbTestHelper;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.HealthCareFacilityServiceBean;
import gov.nih.nci.services.correlation.HealthCareFacilityServiceTest;
import gov.nih.nci.po.service.external.CtepMessageBean.OrganizationType;
import gov.nih.nci.po.service.external.CtepMessageBean.RecordType;
import gov.nih.nci.po.service.external.CtepMessageBean.TransactionType;
import gov.nih.nci.po.util.EmailLogger;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.jms.TextMessageStub;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.Context;

import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author gax
 */
public class CtepMessageBeanTest extends AbstractServiceBeanTest {
    private boolean serviceCalled = false;

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
    private WriterAppender appender;

    @Before
    public void setUpLogger() {                
        logWriter = new StringWriter();   
        appender = new WriterAppender(new SimpleLayout(), logWriter);
        EmailLogger.LOG.addAppender(appender);
    }

    @After
    public void undoLogger() {
        EmailLogger.LOG.removeAppender(appender);
        logWriter.getBuffer().setLength(0);
        logWriter = null;
    }

    /**
     * Test of onMessage method, of class CtepMessageBean.
     * @throws JMSException
     */
    @Test
    public void testOnMessage() throws IOException, JMSException {
        int tests = 18;
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
        checkers[idx] = new Checker(false, null, null, null, idx++,
                "Failed to process JMS message ID:13",
                "org.xml.sax.SAXParseException");
        checkers[idx] = new Checker(false, null, null, null, idx++,
                "Unsuported Record Type in message BOGUS_TYPE");
        checkers[idx] = new Checker(true, TransactionType.REJECT, RecordType.ORGANIZATION, "abc", idx++);
        checkers[idx] = new Checker(true, TransactionType.DUPLICATE, RecordType.ORGANIZATION, "abc", idx++);
        checkers[idx] = new Checker(true, TransactionType.DELETE, RecordType.ORGANIZATION_ADDRESS, "03013", idx++);

        for (int i = 0; i < tests; i++) {
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

    @SuppressWarnings("unchecked")
    @Test
    public void testMsg4Full() throws Exception {
        HealthCareFacilityServiceBean hcfSvc =
            (HealthCareFacilityServiceBean) EjbTestHelper.getHealthCareFacilityServiceBean();

        // create org and 2 hcfs for this test
        final Country c = getDefaultCountry();
        final HealthCareFacilityServiceBean getService = hcfSvc;
        HealthCareFacilityServiceTest test = new HealthCareFacilityServiceTest() {
            @Override
            public Country getDefaultCountry() {
                return c;
            }

            @Override
            protected AbstractCuratableServiceBean<HealthCareFacility> getService() {
                return getService;
            }
        };
        test.setUpData();
        // create org1 with hcf1
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        // create org2 with hcf2
        test.testSimpleCreateCtepOwnedAndGet();
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        List<HealthCareFacility> hcfList =
                PoHibernateUtil.getCurrentSession().createCriteria(HealthCareFacility.class).list();

        HealthCareFacility hcf1 = hcfList.get(0);
        Organization org1 = hcf1.getPlayer();
        assertNotNull(org1);
        assertEquals(RoleStatus.PENDING, hcf1.getStatus());
        assertTrue(hcf1.isCtepOwned());
        assertEquals(hcf1.getPlayer().getName(), hcf1.getName());

        HealthCareFacility hcf2 = hcfList.get(1);
        Organization org2 = hcf2.getPlayer();
        assertNotNull(org2);
        assertEquals(RoleStatus.PENDING, hcf2.getStatus());
        assertTrue(hcf2.isCtepOwned());
        assertEquals(hcf2.getPlayer().getName(), hcf2.getName());

        Ii ctepId = new Ii();
        ctepId.setExtension("03013");
        ctepId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        ctepId.setIdentifierName("no name for ctep id");

        hcf1.getOtherIdentifiers().clear();
        hcf1.getOtherIdentifiers().add(ctepId);
        hcfSvc.curate(hcf1);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        Ii duplicateOfId = new Ii();
        duplicateOfId.setExtension("RSB003");
        duplicateOfId.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        duplicateOfId.setIdentifierName("no name for dup id");

        hcf2.getOtherIdentifiers().clear();
        hcf2.getOtherIdentifiers().add(duplicateOfId);
        hcfSvc.curate(hcf2);
        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        MockTextMessage msg = new MockTextMessage(4);
        CtepMessageBean bean = setupCtepMessageBean();
        bean.onMessage(msg);

        int jmsMsg_size = 0;
        try {
        	jmsMsg_size = PoHibernateUtil.getCurrentSession()
      			  .createQuery("from " + CtepJMSLogRecord.class.getName() 
      					  + " where messageId = " + msg.getJMSMessageID()).list().size();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		assertEquals(1, jmsMsg_size);
        HealthCareFacility freshRo = (HealthCareFacility) PoHibernateUtil.getCurrentSession().get(
                HealthCareFacility.class, hcf1.getId());
        assertEquals(RoleStatus.NULLIFIED, freshRo.getStatus());
        assertEquals(hcf2.getId(), freshRo.getDuplicateOf().getId());
    }

    private CtepMessageBean setupCtepMessageBean() {
        CtepMessageBean bean = new CtepMessageBean();

        final CtepOrganizationImporter importer = new CtepOrganizationImporter(null) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }
        };

        CtepImportServiceBean importServiceBean = new CtepImportServiceBean() {
            @Override
            protected void initImporters() {
                try {
                    setOrgImporter(importer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        bean.setCtepImportService(importServiceBean);
        return bean;
    }

    @Test
    public void testIgnoredMessages() throws Exception {
        serviceCalled = false;
        MockTextMessage msg15 = new MockTextMessage(15);
        MockTextMessage msg16 = new MockTextMessage(16);
        MockTextMessage msg17 = new MockTextMessage(17);
        CtepMessageBean bean = setupCtepMessageBeanWithDummyImporter();
        bean.onMessage(msg15);
        assertFalse("Should be ignoring this message", serviceCalled);
        bean.onMessage(msg16);
        assertFalse("Should be ignoring this message", serviceCalled);
        bean.onMessage(msg17);
        assertFalse("Should be ignoring this message", serviceCalled);
    }

    @Test
    public void testRejectMessageNullRecordId() throws Exception {
        serviceCalled = false;
        MockTextMessage msg18 = new MockTextMessage(18);
        CtepMessageBean bean = setupCtepMessageBeanWithDummyImporter();
        bean.onMessage(msg18);
        assertFalse("Should be ignoring this message", serviceCalled);
    }

    private CtepMessageBean setupCtepMessageBeanWithDummyImporter() {
        CtepMessageBean bean = new CtepMessageBean();

        final CtepOrganizationImporter importer = new CtepOrganizationImporter(null) {
            @Override
            protected void initCtepServices(Context ctepContext) {
            }

            @Override
            public void nullifyCtepOrganization(Ii ctepOrgId, Ii duplicateOfId, OrganizationType orgType)
                    throws JMSException {
                serviceCalled = true;
            }

            @Override
            public Organization importOrganization(Ii ctepOrgId) throws JMSException, EntityValidationException {
                serviceCalled = true;
                return null;
            }

        };

        CtepImportServiceBean importServiceBean = new CtepImportServiceBean() {
            @Override
            protected void initImporters() {
                try {
                    setOrgImporter(importer);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        bean.setCtepImportService(importServiceBean);
        return bean;
    }

    @Test
    public void testNullifyWithNoDuplicate() throws Exception {
        serviceCalled = false;
        MockTextMessage msg = new MockTextMessage("4-noduplicate");
        CtepMessageBean bean = setupCtepMessageBeanWithDummyImporter();
        bean.onMessage(msg);
        assertFalse("Should be ignoring NULLIFY messages without duplicateOf ID", serviceCalled);
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
                Assert.fail("unexpected log entry for msg " + idx + ": " + log);
            }
            for (String m : errors) {
                Assert.assertTrue("expected error not found for msg " + idx + ": " + m + "within \n " + log.toString(),
                        log.indexOf(m) >= 0);
            }
        }

    }

    private class MockTextMessage implements TextMessage {
        private final String text;
        private final String id;
        MockTextMessage(int id) throws IOException {
            this(String.valueOf(id));
        }
        MockTextMessage(String id) throws IOException {
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
            return id;
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
