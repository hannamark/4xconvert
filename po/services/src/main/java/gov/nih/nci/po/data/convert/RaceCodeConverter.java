package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.bidimap.UnmodifiableBidiMap;
import org.apache.commons.lang.StringUtils;

/**
 * Converter to translate between ISO & PO race codes.
 */
public class RaceCodeConverter {
    /**
     * Bidirectional map race codes.
     * <table border="1">
     * <tr><th>Key(String)</th><th>Value(EntityStatus)</th/></tr>
     * <tr><td>{@link RaceCode#WH#getValue()}</td><td>{@link RaceCode#WH}</td></tr>
     * <tr><td>{@link RaceCode#B_AA#getValue()}</td><td>{@link RaceCode#B_AA}</td></tr>
     * <tr><td>{@link RaceCode#NH_OPI#getValue()}</td><td>{@link RaceCode#NH_OPI}</td></tr>
     * <tr><td>{@link RaceCode#AS#getValue()}</td><td>{@link RaceCode#AS}</td></tr>
     * <tr><td>{@link RaceCode#AI_AN#getValue()}</td><td>{@link RaceCode#AI_AN}</td></tr>
     * <tr><td>{@link RaceCode#NR#getValue()}</td><td>{@link RaceCode#NR}</td></tr>
     * <tr><td>{@link RaceCode#UNK#getValue()}</td><td>{@link RaceCode#UNK}</td></tr>
     * </table>
     */
    public static final BidiMap STATUS_MAP;
    static {
        DualHashBidiMap map = new DualHashBidiMap();
        for (RaceCode r : RaceCode.values()) {
            map.put(r.getValue(), r);
        }
        STATUS_MAP = UnmodifiableBidiMap.decorate(map);
    }

    /**
     * convert {@link Cd} to other types.
     */
    public static class DSetCdConverter extends AbstractXSnapshotConverter<DSet<Cd>> {
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <TO> TO convert(Class<TO> returnClass, DSet<Cd> value) {
            if (returnClass == Set.class) {
                return (TO) convertToRaceCodeSet(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }
    }

    /**
     * convert {@link RaceCode} to other types.
     */
    public static class EnumConverter extends AbstractXSnapshotConverter<Set<RaceCode>> {
        /**
         * {@inheritDoc}
         */
        @Override
        @SuppressWarnings("unchecked")
        public <TO> TO convert(Class<TO> returnClass, Set<RaceCode> value) {
            if (returnClass == DSet.class) {
                return (TO) convertToDsetOfCd(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }
    }

    /**
     * @param cds a status code
     * @return best guess of <code>iso</code>'s ISO equivalent.
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static Set<RaceCode> convertToRaceCodeSet(DSet<Cd> cds) {
        if (cds == null || CollectionUtils.isEmpty(cds.getItem())) {
            return null;
        }
        Set<RaceCode> races = new HashSet<RaceCode>();
        for (Cd cd : cds.getItem()) {
            if (cd == null) {
                continue;
            }

            if (cd.getNullFlavor() != null) {
                continue;
            }
            String code = cd.getCode();
            checkBlank(code);
            RaceCode race = (RaceCode) STATUS_MAP.get(code.toLowerCase());
            checkNull(race, code);
            races.add(race);
        }
        return races;

    }

    private static void checkBlank(String code) {
        if (StringUtils.isBlank(code)) {
            throw new PoIsoConstraintException("code must be set");
        }
    }

    private static void checkNull(RaceCode race, String code) {
        if (race == null) {
            throw new PoIsoConstraintException("unsupported code " + code);
        }
    }

    /**
     * @param races PO entity status.
     * @return best guess of <code>DSet&lt;Cd&gt;</code>'s ISO equivalent.
     */
    public static DSet<Cd> convertToDsetOfCd(Set<RaceCode> races) {
        if (CollectionUtils.isEmpty(races)) {
            return null;
        }
        DSet<Cd> cds = new DSet<Cd>();
        cds.setItem(new HashSet<Cd>());
        for (RaceCode raceCode : races) {
            Cd iso = convertToCd(raceCode);
            cds.getItem().add(iso);
        }
        return cds;
    }

    private static Cd convertToCd(RaceCode raceCode) {
        Cd iso = new Cd();
        if (raceCode == null) {
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            String code = (String) STATUS_MAP.getKey(raceCode);
            iso.setCode(code);
        }
        return iso;
    }
}