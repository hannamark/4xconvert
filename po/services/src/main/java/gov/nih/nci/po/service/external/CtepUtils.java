package gov.nih.nci.po.service.external;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.services.CorrelationDto;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.ResearchOrganizationDTO;

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
}
