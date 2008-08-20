package gov.nih.nci.po.data.convert;

import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.NullFlavor;
import gov.nih.nci.po.data.bo.RaceCode;
import gov.nih.nci.services.PoIsoConstraintException;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.collections.bidimap.UnmodifiableBidiMap;
import org.apache.commons.lang.StringUtils;

/**
 * Converter to translate between ISO & PPO race codes.
 */
public class RaceCodeConverter {
    /**
     * Bidirectional map race codes.
     * <table border="1">
     * <tr><th>Key(String)</th><th>Value(EntityStatus)</th/></tr>
     * <tr><td>"01"</td><td>{@link RaceCode#WH}</td></tr>
     * <tr><td>"03"</td><td>{@link RaceCode#B_AA}</td></tr>
     * <tr><td>"04"</td><td>{@link RaceCode#NH_OPI}</td></tr>
     * <tr><td>"05"</td><td>{@link RaceCode#AS}</td></tr>
     * <tr><td>"06"</td><td>{@link RaceCode#AI_AN}</td></tr>
     * <tr><td>"98"</td><td>{@link RaceCode#NR}</td></tr>
     * <tr><td>"99"</td><td>{@link RaceCode#UNK}</td></tr>
     * </table>
     */
    public static final BidiMap STATUS_MAP;
    static {
        DualHashBidiMap map = new DualHashBidiMap();
        map.put("01", RaceCode.WH);
        map.put("03", RaceCode.B_AA);
        map.put("04", RaceCode.NH_OPI);
        map.put("05", RaceCode.AS);
        map.put("06", RaceCode.AI_AN);
        map.put("98", RaceCode.NR);
        map.put("99", RaceCode.UNK);
        STATUS_MAP = UnmodifiableBidiMap.decorate(map);
    }

    /**
     * convert {@link Cd} to other types.
     */
    public static class CdConverter extends AbstractXSnapshotConverter<DSet<Cd>> {
        /**
         * {@inheritDoc}
         */
        @Override
        public <TO> TO convert(Class<TO> returnClass, DSet<Cd> value) {
            if (returnClass == Set.class) {
                return (TO) convertToStatusEnum(value);
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
        public <TO> TO convert(Class<TO> returnClass, Set<RaceCode> value) {
            if (returnClass == DSet.class) {
                return (TO) convertToCd(value);
            }
            throw new UnsupportedOperationException(returnClass.getName());
        }
    }

    /**
     * @param cds a status code
     * @return best guess of <code>iso</code>'s ISO equivalent.
     */
    @SuppressWarnings("PMD.UseLocaleWithCaseConversions")
    public static Set<RaceCode> convertToStatusEnum(DSet<Cd> cds) {
        if (cds == null || cds.getItem() == null) {
            return null;
        }
        Set<RaceCode> races = new HashSet<RaceCode>();
        for (Cd cd : cds.getItem()) {
            if (cd == null) {
                continue;
            }

            checkFlavorId(cd);
            if (cd.getNullFlavor() != null) {
                continue;
            }
            String code = cd.getCode();
            checkBlank(code);
            RaceCode race = (RaceCode) STATUS_MAP.get(code.toLowerCase());
            checkNull(race);
            races.add(race);
        }
        return races;

    }

    private static void checkFlavorId(Cd cd) {
        if (cd.getFlavorId() != null) {
            throw new PoIsoConstraintException("PO expects a null flavorId");
        }
    }

    private static void checkBlank(String code) {
        if (StringUtils.isBlank(code)) {
            throw new PoIsoConstraintException("code must be set");
        }
    }

    private static void checkNull(RaceCode race) {
        if (race == null) {
            throw new PoIsoConstraintException("unsupported code " + race);
        }
    }

    /**
     * @param races PO entity status.
     * @return best guess of <code>DSet&lt;Cd&gt;</code>'s ISO equivalent.
     */
    public static DSet<Cd> convertToCd(Set<RaceCode> races) {
        if (races == null || races.isEmpty()) { 
            return null; 
        }
        DSet<Cd> cds = new DSet<Cd>();
        cds.setItem(new HashSet<Cd>());
        for (RaceCode raceCode : races) {
            Cd iso = convertToCd(races, raceCode);
            cds.getItem().add(iso);
        }
        return cds;
    }

    private static Cd convertToCd(Set<RaceCode> races, RaceCode raceCode) {
        Cd iso = new Cd();
        if (raceCode == null) {            
            iso.setNullFlavor(NullFlavor.NI);
        } else {
            String code = (String) STATUS_MAP.getKey(races);
            if (code == null) {
                throw new UnsupportedOperationException(races + " not yet handled");
            }
            iso.setCode(code);
        }
        return iso;
    }
}
