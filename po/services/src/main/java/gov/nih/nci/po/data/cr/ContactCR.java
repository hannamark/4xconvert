package gov.nih.nci.po.data.cr;

import gov.nih.nci.po.data.common.Contact;

/**
 * Extension to support prior values.
 */
public interface ContactCR extends Contact {

    /**
     * @return the prior string value of this contact
     */
    String getPriorValue();
    
    /**
     * @param priorValue prior value of the contact
     */
    void setPriorValue(String priorValue);
}
