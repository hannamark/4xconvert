package gov.nih.nci.po.webservices.util;

import gov.nih.nci.coppa.domain.RoleStatus;
import gov.nih.nci.po.webservices.convert.simple.ConverterException;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import gov.nih.nci.po.webservices.service.simple.soap.person.RoleType;
import gov.nih.nci.po.webservices.types.ClinicalResearchStaff;
import gov.nih.nci.po.webservices.types.EntityStatus;
import gov.nih.nci.po.webservices.types.HealthCareFacility;
import gov.nih.nci.po.webservices.types.HealthCareProvider;
import gov.nih.nci.po.webservices.types.OrganizationalContact;
import gov.nih.nci.po.webservices.types.OversightCommittee;
import gov.nih.nci.po.webservices.types.ResearchOrganization;
import gov.nih.nci.po.webservices.types.Role;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This is a Utility class for PO-WebService project which has common utility
 * methods.
 * 
 * @author Rohit Gupta
 * 
 */
public class PoWSUtil {

    /**
     * This method is used to get BO RoleStatus for given JaxB status.
     * 
     * @param jaxbStatus
     *            JaxB status
     * @return Corresponding BO Role status
     */
    public static gov.nih.nci.po.data.bo.RoleStatus getBORoleStatus(
            String jaxbStatus) {
        if (EntityStatus.INACTIVE.value().equalsIgnoreCase(jaxbStatus)) {
            return gov.nih.nci.po.data.bo.RoleStatus.SUSPENDED;
        } else {
            return gov.nih.nci.po.data.bo.RoleStatus.valueOf(jaxbStatus);
        }
    }

    /**
     * This method is used to get JaxBStatus for given BO status.
     * 
     * @param boStatus
     *            BO role status
     * @return Corresponding JaxB status
     */
    @SuppressWarnings({ "PMD.PreserveStackTrace" })
    public static gov.nih.nci.po.webservices.types.EntityStatus getEntityStatus(
            String boStatus) {
        try {
            if (RoleStatus.SUSPENDED.name().equalsIgnoreCase(boStatus)) {
                return EntityStatus.INACTIVE;
            } else {
                return EntityStatus.fromValue(boStatus);
            }
        } catch (Exception e) {
            throw new ServiceException("Invalid status found:" + boStatus);
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
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        XMLGregorianCalendar xmlCalendar = null;
        try {
            GregorianCalendar gCalendar = new GregorianCalendar();
            gCalendar.setTime(date);
            xmlCalendar = DatatypeFactory.newInstance()
                    .newXMLGregorianCalendar(gCalendar);
        } catch (Exception ex) {
            throw new ConverterException("Unable to convert date '" + date
                    + "' to XML format", ex);
        }
        return xmlCalendar;
    }

    /**
     * This method is used to convert XMLGregorianCalendar to java.util.Date.
     * 
     * @param calendar
     *            XMLGregorianCalendar object to be converted
     * @return date in java.util.Date format
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * This method is used to check if the two dates are same.
     * 
     * @param gcalendar
     *            XMLGregorianCalendar object
     * @param date
     *            java.util.Date
     * @return true if the dates are same
     */
    public static boolean areSameDates(XMLGregorianCalendar gcalendar, Date date) {
        XMLGregorianCalendar gdate = toXMLGregorianCalendar(date);
        return gcalendar.getMillisecond() == gdate.getMillisecond();
    }

    /**
     * This method is used to get the PersonRole class for given roleType.
     * 
     * @param roleType
     *            person role type
     * @return corresponding PersonRole class
     */
    @SuppressWarnings("rawtypes")
    public static Class getPersonRoleClass(String roleType) {
        if (RoleType.HEALTH_CARE_PROVIDER.value().equalsIgnoreCase(roleType)) {
            return HealthCareProvider.class;
        } else if (RoleType.ORGANIZATIONAL_CONTACT.value().equalsIgnoreCase(
                roleType)) {
            return OrganizationalContact.class;
        } else if (RoleType.CLINICAL_RESEARCH_STAFF.value().equalsIgnoreCase(
                roleType)) {
            return ClinicalResearchStaff.class;
        } else {
            throw new ServiceException("Unsupported RoleType:" + roleType);
        }
    }

    /**
     * This method is used to get the OrganizationRole class for given roleType.
     * 
     * @param roleType
     *            organization role type
     * @return corresponding OrganizationRole class
     */
    @SuppressWarnings("rawtypes")
    public static Class getOrgRoleClass(String roleType) {
        if (gov.nih.nci.po.webservices.service.simple.soap.organization.RoleType.RESEARCH_ORGANIZATION
                .value().equalsIgnoreCase(roleType)) {
            return ResearchOrganization.class;
        } else if (gov.nih.nci.po.webservices.service.simple.soap.organization.RoleType.OVERSIGHT_COMMITTEE
                .value().equalsIgnoreCase(roleType)) {
            return OversightCommittee.class;
        } else if (gov.nih.nci.po.webservices.service.simple.soap.organization.RoleType.HEALTH_CARE_FACILITY
                .value().equalsIgnoreCase(roleType)) {
            return HealthCareFacility.class;
        } else {
            throw new ServiceException("Unsupported OrgRoleType:" + roleType);
        }
    }

    /**
     * This method is used to check the two input number are same or not.
     * 
     * @param urlId
     *            - Id present in the URL
     * @param payloadId
     *            - Id present in the payload
     * @return true if numbers are same else false.
     */
    public static boolean isIdSame(long urlId, long payloadId) {
        boolean isSame = false;
        if (urlId == payloadId) {
            isSame = true;
        }
        return isSame;
    }

    /**
     * This method is used to check the two input roleType are same or not.
     * 
     * @param urlRoleType
     *            - roletype present in the URL
     * @param role
     *            - role present in the payload
     * @return true if the roletype are same
     */
    public static boolean isRoleTypeSame(String urlRoleType, Role role) {
        boolean isSame = false;
        String payloadRoleType = role.getClass().getSimpleName();

        if (urlRoleType.equals(payloadRoleType)) {
            isSame = true;
        }
        return isSame;
    }
}
