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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.CtepJMSLogRecord;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.util.EmailLogger;
import gov.nih.nci.po.util.PoHibernateUtil;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.xml.sax.SAXException;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Date;

/**
 *
 * Ideally, this should have been a MDB, but JBoss has no docs on how to connect to an external non-JBoss JMS topic.
 *
 * @see CtepMessageReceiver
 *
 * @author gax
 *
 */
@SuppressWarnings({"PMD.TooManyMethods" })
public class CtepMessageBean implements MessageListener {

    /**
     * CTEP message TRANSACTION_TYPE.
     */
    public enum TransactionType {
        /**
         * an insert.
         */
        INSERT,
        /**
         * a nullification.
         */
        NULLIFY,
        /**
         * an update.
         */
        UPDATE,
        /**
         * a rejection.
         */
        REJECT,
        /**
         * a duplicate.
         */
        DUPLICATE,
        /**
         * a deletion.
         */
        DELETE
    }

    /**
     * CTEP message RECORD_TYPE.
     */
    public enum RecordType {
        /**
         * organization CUD.
         */
        ORGANIZATION,
        /**
         * organization address CUD.
         */
        ORGANIZATION_ADDRESS,
        /**
         * person CUD.
         */
        PERSON,
        /**
         * person address CUD.
         */
        PERSON_ADDRESS,
        /**
         * person telecom CUD.
         */
        PERSON_CONTACT
    }

    /**
     * CTEP message ORGANIZATION_TYPE.
     */
    public enum OrganizationType {
        /**
         * Research organization.
         */
        RESEARCHORGANIZATION,
        /**
         * health care facility.
         */
        HEALTHCAREFACILITY
    }

    private static final Logger LOG = Logger.getLogger(CtepMessageBean.class);
    private static final String INVALID_STATE_MSG = "Invalid value (property={0}, value={1}, message={2})";
    private static final int JMS_MSG_LENG = 4000;    
    private CtepImportService ctepImportService;

    /**
     * @param ctepImportService injected.
     */
    @EJB
    public void setCtepImportService(CtepImportService ctepImportService) {
        this.ctepImportService = ctepImportService;
    }

    private void logError(Message msg, Exception err) {
        logError(null, msg, err);
    }

    private void logError(String m, Message msg, Exception err) {
        String msgId;
        try {
            msgId = msg.getJMSMessageID();
        } catch (Exception e) {
            LOG.error(e);
            msgId = "Failed to get ID:" + e.toString();
        }
        StringBuffer sb = new StringBuffer();
        if (m != null) {
            sb.append(m);
        } else {
            sb.append("Failed to process JMS message ID:").append(msgId);
        }
        if (msg instanceof TextMessage) {
            try {
                String txt = ((TextMessage) msg).getText();
                sb.append("\nMessage Text:\n").append(txt).append('\n');
            } catch (JMSException ex) {
                LOG.error(ex);
            }
        }
        handleInvalidStateException(err, sb);
        EmailLogger.LOG.error(sb.toString(), err);
        LOG.error(sb.toString(), err);
        storeProcessingError(msg, sb.toString(), err);
    }

    

    private void handleInvalidStateException(Exception err, StringBuffer sb) {
        if (err instanceof SAXException && ((SAXException) err).getException() != null
                && ((SAXException) err).getException().getCause() instanceof InvalidStateException) {
            InvalidStateException e = (InvalidStateException) ((SAXException) err).getException().getCause();
            
            for (InvalidValue invalidValue : e.getInvalidValues()) {
                String errorMessage = MessageFormat.format(INVALID_STATE_MSG, invalidValue.getPropertyName(),
                        invalidValue.getValue(), invalidValue.getMessage());
                sb.append(errorMessage).append('\n');
            }
        }
    }

    /**
     * @param msg the message from CTEP
     */
    public void onMessage(final Message msg) {
        if (msg instanceof TextMessage) {
            insertCtepJmsLog(msg);
            processMessage((TextMessage) msg);
        } else {
            logError(String.format("Unsuported Message Type %s", msg.getClass().toString()), msg, null);
        }
    }
    
    private void storeProcessingError(Message msg, String errDescr,
            Exception err) {
        PoHibernateUtil.getHibernateHelper().openAndBindSession();
        try {
            CtepJMSLogRecord jmsLogRec = (CtepJMSLogRecord) PoHibernateUtil
                    .getCurrentSession().get(CtepJMSLogRecord.class,
                            msg.getJMSMessageID());
            jmsLogRec.setProcessingErrors(errDescr + SystemUtils.LINE_SEPARATOR
                    + ExceptionUtils.getFullStackTrace(err));
            PoHibernateUtil.getCurrentSession().save(jmsLogRec);
            PoHibernateUtil.getCurrentSession().flush();
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            PoHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        }
    }

    private void insertCtepJmsLog(final Message msg) {
        PoHibernateUtil.getHibernateHelper().openAndBindSession();
        try {
            String jmsMsg = StringUtils.substring(((TextMessage) msg).getText(), 0, JMS_MSG_LENG);
            CtepJMSLogRecord jmsLogRec = new CtepJMSLogRecord();
            jmsLogRec.setCreatedDate(new Date());
            jmsLogRec.setMessageId(msg.getJMSMessageID());
            jmsLogRec.setCtepJmsMsg(jmsMsg);
            PoHibernateUtil.getCurrentSession().save(jmsLogRec);
            PoHibernateUtil.getCurrentSession().flush();
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            PoHibernateUtil.getHibernateHelper().unbindAndCleanupSession();
        }
    }

    @SuppressWarnings("PMD.MagicNumbers")
    private void processMessage(TextMessage textMessage) {
        try {

            Digester digester = new Digester();
            digester.push(this);
            // This set of rules calls the processRow method and passes
            // in 3 parameters to the method.
            // CHECKSTYLE:OFF - magic numbers below
            digester.addCallMethod("ROWSET/ROW", "processRow", 5);
            digester.addCallParam("ROWSET/ROW/TRANSACTION_TYPE", 0);
            digester.addCallParam("ROWSET/ROW/RECORD_TYPE", 1);
            digester.addCallParam("ROWSET/ROW/RECORD_ID", 2);
            digester.addCallParam("ROWSET/ROW/ORGANIZATION_TYPE", 3);
            digester.addCallParam("ROWSET/ROW/DUPLICATE_OF", 4);
            // CHECKSTYLE:ON

            String txt = textMessage.getText();
            digester.parse(new StringReader(txt));
        } catch (Exception ex) {
            logError(textMessage, ex);
        }
    }

    private static Ii generateIi(RecordType msgType, String id) {
        Ii ii = new Ii();
        ii.setExtension(id);
        if (msgType.equals(RecordType.ORGANIZATION) || msgType.equals(RecordType.ORGANIZATION_ADDRESS)) {
            ii.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
        } else {
            ii.setRoot(CtepPersonImporter.CTEP_PERSON_ROOT);
        }
        return ii;
    }

    /**
     * called my digester.
     *
     * @param trxTypeString message TRANSACTION_TYPE.
     * @param recordTypeString message RECORD_TYPE.
     * @param recordIdIn message RECORD_ID.
     * @param organizationTypeString message ORGANIZATION_TYPE
     * @param duplicateOf message DUPLICATE_OF
     * @throws JMSException on error.
     * @throws EntityValidationException if any validation errors occur
     * @throws CtepImportException ctep import exception
     */
    // public for digester reflection call.
    public void processRow(String trxTypeString, String recordTypeString, String recordIdIn,
            String organizationTypeString, String duplicateOf) 
            throws JMSException, EntityValidationException, CtepImportException {
        String recordId = StringUtils.defaultString(recordIdIn);
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("TRANSACTION_TYPE = %s, RECORD_TYPE = %s, RECORD_ID = %s", trxTypeString,
                    recordTypeString, recordId));
        }
        RecordType msgType;
        try {
            msgType = RecordType.valueOf(StringUtils.upperCase(StringUtils.trim(recordTypeString)));
        } catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException("Unsuported Record Type in message " + recordTypeString, iae);
        }

        TransactionType trxType = null;
        try {
            trxType = TransactionType.valueOf(StringUtils.upperCase(StringUtils.trim(trxTypeString)));
        } catch (IllegalArgumentException iae) {
            LOG.error("Unsuported Transaction Type in message", iae);
        }

        OrganizationType orgType = parseOrgType(organizationTypeString);

        Ii id = generateIi(msgType, recordId.trim());

        Ii duplicateOfIi = null;
        if (StringUtils.isNotEmpty(duplicateOf)) {
            duplicateOfIi = generateIi(msgType, duplicateOf.trim());
        }
        processMessage(trxType, msgType, id, orgType, duplicateOfIi);
    }

    private OrganizationType parseOrgType(String organizationTypeString) {
        OrganizationType orgType = null;
        if (StringUtils.isNotEmpty(organizationTypeString)) {
            try {
                orgType = OrganizationType.valueOf(StringUtils.upperCase(StringUtils.trim(organizationTypeString)));
            } catch (IllegalArgumentException iae) {
                LOG.error("Unsuported Organization Type in message", iae);
            }
        }
        return orgType;
    }

    /**
     * process on ROW element in a message.
     *
     * @param trxType message TRANSACTION_TYPE.
     * @param msgType message RECORD_TYPE.
     * @param id message RECORD_ID.
     * @param orgType message ORGANIZATION_TYPE
     * @param duplicateOf message DUPLICATE_OF
     * @throws JMSException on error.
     * @throws EntityValidationException if any validation errors occur
     * @throws CtepImportException ctep import exception
     */
    // protected for testing.
    protected void processMessage(TransactionType trxType, RecordType msgType, Ii id, OrganizationType orgType,
            Ii duplicateOf) throws JMSException, EntityValidationException, CtepImportException {
        switch (trxType) {
        case REJECT:
        case DELETE:
        case DUPLICATE:
            LOG.warn(getSkipMessage(trxType, msgType, id));
            break;
        default:
            switch (msgType) {
            case ORGANIZATION:
                if (trxType == TransactionType.NULLIFY) {
                    nullifyOrganization(id, orgType, duplicateOf);
                    break;
                }
            case ORGANIZATION_ADDRESS:
                ctepImportService.importCtepOrganization(id);
                break;
            case PERSON:
            case PERSON_ADDRESS:
            case PERSON_CONTACT:
                ctepImportService.importCtepPerson(id);
                break;
            default:
                LOG.error(String.format("Unexpected RecordType enum %s", msgType.name()));
            }

        }
    }

    private void nullifyOrganization(Ii id, OrganizationType orgType, Ii duplicateOf) throws JMSException {
        if (duplicateOf != null) {
            ctepImportService.nullifyCtepOrganization(id, duplicateOf, orgType);
        } else {
            LOG.error(String.format("Cannot nullify ID %s because duplicateOf is null", id.getExtension()));
        }
    }

    /**
     * @param trxType the transaction type
     * @param id the identifier
     * @param msgType record type value
     * @return generate the message to skip processing
     * @throws JMSException if a problem occurs with JMS
     */
    protected String getSkipMessage(TransactionType trxType, RecordType msgType, Ii id) throws JMSException {
        StringBuilder b = new StringBuilder();
        b.append("Skipping the processing of message TRANSACTION_TYPE (");
        b.append(trxType);
        b.append(") and RECORD_TYPE (");
        b.append(msgType);
        b.append(") and RECORD_ID (");
        b.append(id.getExtension());
        b.append(')');
        return b.toString();
    }
}
