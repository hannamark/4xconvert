package gov.nih.nci.po.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.RoleStatus;
import org.apache.commons.lang.StringUtils;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import java.util.HashSet;

/**
 * This class has methods which are specific to HealthCareFacility &
 * ResearchOrgnization.
 * 
 * @author Rohit Gupta
 * 
 * @param <T>
 */
public class EnhancedOrganizationRoleServiceBean<T extends Correlation> extends
        AbstractCuratableServiceBean<T> {

    /**
     * The value of the 'root' element of a ctep ii for an org.
     */
    public static final String ORG_CTEP_ID_ROOT = "2.16.840.1.113883.3.26.6.2";

    /**
     * The value of the 'identifier' element of a ctep ii for an org.
     */
    public static final String ORG_CTEP_ID_IDENTIFIER_NAME = "CTEP ID";

    /**
     * This method is used to Curate an OrgRole (HCF or RO).
     * 
     * @param curatable
     *            the object to curate.
     * @param ctepId
     *            ctepId to be updated
     * @throws JMSException
     *             any problems publishing announcements via JMS
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void curate(T curatable, String ctepId) throws JMSException {
        if (StringUtils.isNotBlank(ctepId)) {
            // Step1: remove the existing ctepId (otherwise ctepId is getting added to existing ctepId)

            // Initialize the collection to avoid LazyInitException
            curatable.getOtherIdentifiers().size();
            curatable.setOtherIdentifiers(null);
        }        

        // This 'curate' removes(if set null) existing CtepId & updates other attribute.
        curate(curatable);

        // Step2: Add the new CtepId & then curate it
        if (StringUtils.isNotBlank(ctepId)) {
            gov.nih.nci.iso21090.Ii assIden = new gov.nih.nci.iso21090.Ii();
            assIden.setRoot(ORG_CTEP_ID_ROOT);
            assIden.setIdentifierName(ORG_CTEP_ID_IDENTIFIER_NAME);
            assIden.setExtension(ctepId);
            curatable.setOtherIdentifiers(new HashSet<Ii>()); // initialize it
            curatable.getOtherIdentifiers().add(assIden);
            curate(curatable);
        }
    }


    @Override
    public void curate(T curatable) throws JMSException {

        handleStatusChange(curatable);
        super.curate(curatable);
    }

    private void handleStatusChange(T curatable) {
        if (curatable.getPriorStatus() != curatable.getStatus() && curatable.getStatus() == RoleStatus.NULLIFIED) {
            cascadeStatusChangeNullified(curatable);
        }
    }

    private void cascadeStatusChangeNullified(T curatable) {
        super.mergeAliasesToDuplicate(curatable);
    }
}
