package gov.nih.nci.po.data.bo;

import java.util.List;

/**
 * Represents information that may be used to contact. 
 *
 */
public interface Contactable {

    /**
     * @return list of email addresses
     */
    List<Email> getEmail();

    /**
     * @return list of fax numbers
     */
    List<PhoneNumber> getFax();

    /**
     * @return list of phone numbers
     */
    List<PhoneNumber> getPhone();

    /**
     * @return list of urls
     */
    List<URL> getUrl();

    /**
     * @return list of ttys
     */
    List<PhoneNumber> getTty();
}
