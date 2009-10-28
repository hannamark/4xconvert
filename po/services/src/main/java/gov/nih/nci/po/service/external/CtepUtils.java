package gov.nih.nci.po.service.external;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.TransformerUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Utility class for CTEP-related work.
 *
 * @author smatyas
 *
 */
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
    static boolean isDifferent(Organization ctepOrg, Organization localOrg) {
        if (!StringUtils.equals(localOrg.getName(), ctepOrg.getName())) {
            return true;
        }

        if (!areEmailListsEqual(localOrg.getEmail(), ctepOrg.getEmail())) {
            return true;
        }

        Address ctepAddr = ctepOrg.getPostalAddress() == null ? new Address() : ctepOrg.getPostalAddress();
        Address localAddr = localOrg.getPostalAddress() == null ? new Address() : localOrg.getPostalAddress();

        return !localAddr.contentEquals(ctepAddr);
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
}
