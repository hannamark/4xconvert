package gov.nih.nci.po.service.external;

import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractOversightCommittee;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.util.PoServiceUtil;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.AbstractBaseEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.TransformerUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Utility class for CTEP-related work.
 *
 * @author smatyas
 * @author Rohit Gupta
 *
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public final class CtepUtils {
    private static final String NULL_STRING = "null";

    private CtepUtils() {
    }

    /**
     * @param dto object
     * @return string representing the @param dto
     */
    public static String toString(HealthCareFacilityDTO dto) {
        StringBuffer b = new StringBuffer();
        b.append("hcf identifiers: \n");
        b.append(printIdentifierSet(dto, "hcf"));
        b.append(printII("hcf.player", dto.getPlayerIdentifier()));
        b.append(String.format("hcf.status: %s", (dto.getStatus() != null ? dto.getStatus().getCode() : NULL_STRING)));
        b.append('\n');
        return b.toString();
    }

    /**
     * @param dto object
     * @return string representing the @param dto
     */
    public static String toString(ResearchOrganizationDTO dto) {
        StringBuffer b = new StringBuffer();
        b.append("ro identifiers: \n");
        b.append(printIdentifierSet(dto, "ro"));
        b.append(printII("ro.player", dto.getPlayerIdentifier()));
        b.append(String.format("ro.status: %s", (dto.getStatus() != null ? dto.getStatus().getCode() : NULL_STRING)));
        b.append('\n');
        b.append(String.format("ro.typeCode.code: ", dto.getTypeCode().getCode()));
        b.append('\n');
        St displayName = dto.getTypeCode().getDisplayName();
        b.append(String.format("ro.typeCode.displayName: %s", ((displayName == null) ? NULL_STRING : displayName
                .getValue())));
        b.append('\n');
        Cd funding = dto.getFundingMechanism();
        b.append(String.format("ro.fundingMech: ", ((funding == null) ? NULL_STRING : funding.getCode())));
        b.append('\n');
        return b.toString();
    }

    private static String printIdentifierSet(CorrelationDto dto, String prefix) {
        StringBuffer b = new StringBuffer();
        if (dto.getIdentifier() != null) {
            for (Ii ii : dto.getIdentifier().getItem()) {
                b.append(printII(prefix, ii));
            }
        }
        return b.toString();
    }

    private static String printII(String prefix, Ii ii) {
        StringBuffer b = new StringBuffer();
        if (ii != null) {
            b.append(String.format("%s.ii.identifierName: %s", prefix, ii.getIdentifierName()));
            b.append('\n');
            b.append(String.format("%s.ii.extension: %s", prefix, ii.getExtension()));
            b.append('\n');
            b.append(String.format("%s.ii.root: %s", prefix, ii.getRoot()));
            b.append('\n');
        } else {
            b.append(String.format("%s.ii: %s", prefix, NULL_STRING));
            b.append('\n');
        }
        return b.toString();
    }



    /**
     * Determines whether or not a ctep organization represents an update to an existing org.
     * Only name, address, and email are considered.
     *
     * @param ctepOrg ctep organization (not nullable)
     * @param localOrg current local organization (not nullable)
     * @return true if the ctep org has different data than localOrg
     */
    static boolean isOrganizationDifferent(Organization ctepOrg, Organization localOrg) {
        if (isDifferentHelper(ctepOrg, localOrg)) {
            return true;
        }

        Address ctepAddr = ctepOrg.getPostalAddress() == null ? new Address() : ctepOrg.getPostalAddress();
        Address localAddr = localOrg.getPostalAddress() == null ? new Address() : localOrg.getPostalAddress();

        return !localAddr.contentEquals(ctepAddr);
    }
    
    /**
     * Determines whether or not a ctep organization represents an update to Only 'name'/Alias of an existing org.
     *
     * @param ctepOrg ctep organization (not nullable)
     * @param localOrg current local organization (not nullable)
     * @return true if the ctep org has different data than localOrg
     */
    static boolean isOnlyOrgNameAliasDifferent(Organization ctepOrg, Organization localOrg) {
        Address ctepAddr = ctepOrg.getPostalAddress() == null ? new Address() : ctepOrg.getPostalAddress();
        Address localAddr = localOrg.getPostalAddress() == null ? new Address() : localOrg.getPostalAddress();
        
        return hasOrgNameOrAliasChanged(localOrg, ctepOrg)
                && areEmailListsEqual(localOrg.getEmail(), ctepOrg.getEmail())
                && arePhoneNumberListsEqual(localOrg.getPhone(), ctepOrg.getPhone())
                && ObjectUtils.equals(localOrg.getStatusCode(), ctepOrg.getStatusCode()) 
                && localAddr.contentEquals(ctepAddr);
    }
    
    private static boolean isDifferentHelper(Organization ctepOrg, Organization localOrg) {
        if (!StringUtils.equals(localOrg.getName(), ctepOrg.getName())) {
            return true;
        }

        if (!areEmailListsEqual(localOrg.getEmail(), ctepOrg.getEmail())) {
            return true;
        }
        
        if (!arePhoneNumberListsEqual(localOrg.getPhone(), ctepOrg.getPhone())) {
            return true;
        }

        if (ObjectUtils.notEqual(localOrg.getStatusCode(), ctepOrg.getStatusCode())) {
            return true;
        }
        return false;
    }
    
    private static boolean hasOrgNameOrAliasChanged(Organization poOrg, Organization ctepOrg) {
        return !StringUtils.equalsIgnoreCase(poOrg.getName(), ctepOrg.getName()) 
                || hasAliasChanged(poOrg.getAlias(), ctepOrg.getAlias());
    }
    
    private static boolean hasAliasChanged(List<Alias> poAliasList, List<Alias> ctepAliasList) {
        boolean result = false;
        for (Alias ctepAlias : ctepAliasList) {
            if (PoServiceUtil.aliasIsNotPresent(poAliasList, ctepAlias.getValue())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * @param ctepOrg data from CTEP
     * @param localOrg local data, either org or change request
     * @param update if true, localOrg is modified
     * @return true if data was different, false if all data was already the same
     */
    static void copy(Organization ctepOrg, AbstractOrganization localOrg) {
        // doing this here instead of a 'copy' method in the org itself because all
        // of the relationships are not copied (change requests, roles, etc) The org
        // we are copying from is just the base fields. Also, the org from ctep
        // does not provide fax, phone, tty, url, so those fields are skipped.
        if (!StringUtils.equals(localOrg.getName(), ctepOrg.getName())) {
            localOrg.setName(ctepOrg.getName());
        }

        if (ObjectUtils.notEqual(localOrg.getStatusCode(), ctepOrg.getStatusCode())) {
            localOrg.setStatusCode(ctepOrg.getStatusCode());
        }
        CtepUtils.copyAddress(ctepOrg, localOrg);

        if (!areEmailListsEqual(localOrg.getEmail(), ctepOrg.getEmail())) {
            localOrg.getEmail().clear();
            localOrg.getEmail().addAll(ctepOrg.getEmail());
        }
    }

    private static void copyAddress(Organization src, AbstractOrganization dest) {
        if (src.getPostalAddress() == null && dest.getPostalAddress() != null) {
            dest.setPostalAddress(null);
        } else if (src.getPostalAddress() != null && dest.getPostalAddress() == null) {
            Address address = new Address();
            address.copy(src.getPostalAddress());
            dest.setPostalAddress(address);
        } else if (!dest.getPostalAddress().contentEquals(src.getPostalAddress())) {
            dest.getPostalAddress().copy(src.getPostalAddress());
        }
    }

    /**
     * Checks if two lists of email addresses contain the same addresses, regardless of order.
     * @param list1 first list of email addresses
     * @param list2 other list of email addresses
     * @return true if both lists contain the same addresses, ignoring order
     */
    public static boolean areEmailListsEqual(List<Email> list1, List<Email> list2) {
        Transformer valueTransformer = TransformerUtils.invokerTransformer("getValue");
        List<Email> transformedList1 = new ArrayList<Email>(list1);
        List<Email> transformedList2 = new ArrayList<Email>(list2);
        CollectionUtils.transform(transformedList1, valueTransformer);
        CollectionUtils.transform(transformedList2, valueTransformer);
        return CollectionUtils.isEqualCollection(transformedList1, transformedList2);
    }
    
    /**
    * Checks if two lists of Phone Number contain the same addresses, regardless of order.
    * @param list1 first list of Phone Number
    * @param list2 other list of Phone Number
    * @return true if both lists contain the same Phone Number, ignoring order
    */
    public static boolean arePhoneNumberListsEqual(List<PhoneNumber> list1, List<PhoneNumber> list2) {
        Transformer valueTransformer = TransformerUtils.invokerTransformer("getValue");
        List<PhoneNumber> transformedList1 = new ArrayList<PhoneNumber>(list1);
        List<PhoneNumber> transformedList2 = new ArrayList<PhoneNumber>(list2);
        CollectionUtils.transform(transformedList1, valueTransformer);
        CollectionUtils.transform(transformedList2, valueTransformer);
        return CollectionUtils.isEqualCollection(transformedList1, transformedList2);
    }

    /**
     * CTEP sends phone number in format that causes Curation exception in PO. See 
     * https://tracker.nci.nih.gov/browse/PO-2392. This method will attempt to change the format
     * to the one supported by PO.
     * @see https://tracker.nci.nih.gov/browse/PO-2392
     * @param dto AbstractBaseEnhancedOrganizationRoleDTO
     * 
     */
    public static void converPhoneNumberFormats(
            AbstractBaseEnhancedOrganizationRoleDTO dto) {
        if (dto != null) {
            converPhoneNumberFormats(dto.getTelecomAddress());
        }
    }
    
    /**
     * CTEP sends phone number in format that causes Curation exception in PO.
     * See https://tracker.nci.nih.gov/browse/PO-2392. This method will attempt
     * to change the format to the one supported by PO.
     * 
     * @see https://tracker.nci.nih.gov/browse/PO-2392
     * @param telecomAddress
     *            DSet<Tel>
     * 
     */
    public static void converPhoneNumberFormats(DSet<Tel> telecomAddress) {
        if (telecomAddress != null && telecomAddress.getItem() != null) {
            for (Tel tel : telecomAddress.getItem()) {
                if (tel.getValue() != null) {
                    reformatPhoneNumber(tel);
                }
            }
        }
    }

    private static void reformatPhoneNumber(Tel tel) {
        URI ctepPhone = tel.getValue();        
        try {
            URI poPhone = new URI(reformatPhoneNumber(ctepPhone.toString()));
            tel.setValue(poPhone);
        } catch (URISyntaxException e) { // NOPMD
        }
    }

    private static String reformatPhoneNumber(String phone) {
        return phone
                .replaceFirst("^tel:\\((\\d+)\\)-", "tel:$1-")
                .replaceFirst("^x-text-fax:\\((\\d+)\\)-", "x-text-fax:$1-")
                .replaceFirst("^x-text-tel:\\((\\d+)\\)-", "x-text-tel:$1-")
                .replaceFirst("^tel:\\((\\d+)\\)\\s*?(\\d)", "tel:$1-$2")
                .replaceFirst("^x-text-fax:\\((\\d+)\\)\\s*?(\\d)",
                        "x-text-fax:$1-$2")
                .replaceFirst("^x-text-tel:\\((\\d+)\\)\\s*?(\\d)",
                        "x-text-tel:$1-$2");
    }

    /**
     * Validate an addresses received from CTEP. Throw meaningful exception if incomplete.
     * @param role the organization role (either hcf or ro)
     * @throws CtepImportException exception
     */
    public static void validateAddresses(AbstractOrganizationRole role) throws CtepImportException {
        if (role == null) {
            return;
        } else if (role instanceof AbstractOversightCommittee) {
            validatePostalAddresses(((AbstractOversightCommittee) role).getPostalAddresses());
        } else if (role instanceof AbstractEnhancedOrganizationRole) {
            validatePostalAddresses(((AbstractEnhancedOrganizationRole) role).getPostalAddresses());
        }
    }

    private static void validatePostalAddresses(Set<Address> addresses) throws CtepImportException {
        if (addresses != null) {
            for (Address addr : addresses) {
                validateAddress(addr);
            }
        }
    }

    /**
     * Validate an address received from CTEP. Throw meaningful exception if incomplete.
     * @param addr the address
     * @throws CtepImportException exception
     */
    public static void validateAddress(Address addr) throws CtepImportException {
        if (addr != null) {
            if (StringUtils.isBlank(addr.getStreetAddressLine())) {
                throw new CtepImportException("street address missing", "Street missing in CTEP address.");
            }
            if (StringUtils.isBlank(addr.getCityOrMunicipality())) {
                throw new CtepImportException("city missing", "City missing in CTEP address.");
            }
            if (null == addr.getCountry()) {
                throw new CtepImportException("country missing", "Country missing in CTEP address.");
            }
        }
    }
}
