package gov.nih.nci.po.webservices.convert.simple;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.webservices.types.Contact;
import gov.nih.nci.po.webservices.types.ContactType;
import gov.nih.nci.po.webservices.types.Entity;
import gov.nih.nci.po.webservices.util.PoWSUtil;
import org.apache.commons.collections.CollectionUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

/**
 * This is the base class for all Entity converters in PO.
 * 
 * @author Rohit Gupta
 * 
 * @param <BO>
 *            business object
 * @param <JAXB>
 *            jaxb object
 */
public abstract class AbstractConverter<BO extends PersistentObject, JAXB extends Entity> {

    /**
     *
     * @param jaxb
     *            JAXB
     * @return domain object
     */
    public abstract BO convertFromJaxbToBO(JAXB jaxb);

    /**
     *
     * @param bo
     *            domain object
     * @return jaxb JAXB
     */
    public abstract JAXB convertFromBOToJaxB(BO bo);

    /**
     * This method is used to populate JaxbContactList into Contactable BO.
     * 
     * @param contactList
     *            list from which data to be populated
     * @param contactable
     *            Contactable BO object to be populated
     */
    protected void populateJaxbContactListInBo(List<Contact> contactList,
            gov.nih.nci.po.data.bo.Contactable contactable) {
        for (Contact contact : contactList) {
            // code is put in separate method to avoid CyclomaticComplexity
            populateContactInBO(contact, contactable);
        }
    }

    private void populateContactInBO(Contact contact,
            gov.nih.nci.po.data.bo.Contactable contactable) {
        String contactType = contact.getType().value();
        if (contactType.equalsIgnoreCase(ContactType.EMAIL.toString())) {
            contactable.getEmail().add(new Email(contact.getValue()));
        } else if (contactType.equalsIgnoreCase(ContactType.PHONE.toString())) {
            contactable.getPhone().add(new PhoneNumber(contact.getValue()));
        } else if (contactType.equalsIgnoreCase(ContactType.FAX.toString())) {
            contactable.getFax().add(new PhoneNumber(contact.getValue()));
        } else if (contactType.equalsIgnoreCase(ContactType.TTY.toString())) {
            contactable.getTty().add(new PhoneNumber(contact.getValue()));
        } else if (contactType.equalsIgnoreCase(ContactType.URL.toString())) {
            contactable.getUrl().add(new URL(contact.getValue()));
        }
    }

    /**
     * This method is used to populate BO ContactList into JaxB contactList.
     * 
     * @param contactable
     *            Contactable BO object from which data will be populated
     * @param contactList
     *            list in which data will be populated
     */
    protected void populateBoContactListInJaxb(
            gov.nih.nci.po.data.bo.Contactable contactable,
            List<Contact> contactList) {
        populateContactInJaxB(contactable.getEmail(), contactList,
                ContactType.EMAIL);
        populateContactInJaxB(contactable.getPhone(), contactList,
                ContactType.PHONE);
        populateContactInJaxB(contactable.getFax(), contactList,
                ContactType.FAX);
        populateContactInJaxB(contactable.getTty(), contactList,
                ContactType.TTY);
        populateContactInJaxB(contactable.getUrl(), contactList,
                ContactType.URL);
    }

    private void populateContactInJaxB(
            List<? extends gov.nih.nci.po.data.bo.Contact> dataList,
            List<Contact> contactList, ContactType contactType) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (gov.nih.nci.po.data.bo.Contact boContact : dataList) {
                gov.nih.nci.po.webservices.types.Contact jaxbContact = new Contact();
                jaxbContact.setType(contactType);
                jaxbContact.setValue(boContact.getValue());
                contactList.add(jaxbContact);
            }
        }
    }

    /**
     * This method is used to convert java.util.Date to
     * javax.xml.datatype.XMLGregorianCalendar.
     * 
     * @param date
     *            java.util.Date object to be converted
     * @return date in XMLGregorianCalendar format
     */
    protected XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        return PoWSUtil.toXMLGregorianCalendar(date);
    }

    /**
     * This method is used to convert XMLGregorianCalendar to java.util.Date.
     * 
     * @param calendar
     *            XMLGregorianCalendar object to be converted
     * @return date in java.util.Date format
     */
    protected Date toDate(XMLGregorianCalendar calendar) {
        return PoWSUtil.toDate(calendar);
    }
}
