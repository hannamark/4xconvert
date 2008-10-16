package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.SexCode;
import gov.nih.nci.services.PoIsoConstraintException;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.bidimap.UnmodifiableBidiMap;
import org.apache.commons.lang.StringUtils;

/**
 * Converter to translate between ISO & PO sex codes.
 */
public class SexCodeConverter {
    /**
     * Bidirectional map sex codes.
     * <table border="1">
     * <tr><th>Key(String)</th><th>Value(EntityStatus)</th/></tr>
     * <tr><td>{@link SexCode#F#getValue()}</td><td>{@link SexCode#F}</td></tr>
     * <tr><td>{@link SexCode#M#getValue()}</td><td>{@link SexCode#M}</td></tr>
     * <tr><td>{@link SexCode#U#getValue()}</td><td>{@link SexCode#U}</td></tr>
     * <tr><td>{@link SexCode#UN#getValue()}</td><td>{@link SexCode#UN}</td></tr>
     * </table>
     */
    public static final BidiMap STATUS_MAP;
    static {
        DualHashBidiMap map = new DualHashBidiMap();
        for (SexCode s : SexCode.values()) {
            map.put(s.name(), s);
        }
        STATUS_MAP = UnmodifiableBidiMap.decorate(map);
    }

    /**
     * convert {@link Cd} to other types.
     */
    public static class CdConverter extends AbstractXSnapshotConverter<Cd> {
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <TO> TO convert(Class<TO> returnClass, Cd value) {
            if (returnClass == SexCode.class) {
                return (TO) convertToSexCode(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }
    }

    /**
     * convert {@link SexCode} to other types.
     */
    public static class EnumConverter extends AbstractXSnapshotConverter<SexCode> {
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <TO> TO convert(Class<TO> returnClass, SexCode value) {
            if (returnClass == Cd.class) {
                return (TO) convertToCd(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }
    }

    /**
     * @param cd a sex code
     * @return best guess of <code>iso</code>'s ISO equivalent.
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static SexCode convertToSexCode(Cd cd) {
        if (cd == null) {
            return null;
        }

        if (cd.getNullFlavor() != null) {
            return null;
        }
        String code = cd.getCode();
        checkBlank(code);
        SexCode sex = (SexCode) STATUS_MAP.get(code.toUpperCase());
        checkNull(sex, code);
        return sex;

    }

    private static void checkBlank(String code) {
        if (StringUtils.isBlank(code)) {
            throw new PoIsoConstraintException("code must be set");
        }
    }

    private static void checkNull(SexCode sex, String code) {
        if (sex == null) {
            throw new PoIsoConstraintException("unsupported code " + code);
        }
    }

    /**
     * @param sex PO entity status.
     * @return best guess of <code>Cd</code>'s ISO equivalent.
     */
    public static Cd convertToCd(SexCode sex) {
        Cd iso1 = new Cd();
        if (sex == null) {
            iso1.setNullFlavor(NullFlavor.NI);
        } else {
            String code = (String) STATUS_MAP.getKey(sex);
            iso1.setCode(code);
        }
        return iso1;
    }
}