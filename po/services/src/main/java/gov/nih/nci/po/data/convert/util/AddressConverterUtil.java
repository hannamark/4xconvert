package gov.nih.nci.po.data.convert.util;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author smatyas
 *
 */
public class AddressConverterUtil {
    private static void setValue(List<Adxp> l, String s, AddressPartType addressPartType) {
        Adxp x;
        if (StringUtils.isNotBlank(s)) {
            x = Adxp.createAddressPart(addressPartType);
            x.setValue(s);
            l.add(x);
        }
    }

    /**
     * @param streetAddressLine street address
     * @param deliveryAddressLine delivery address
     * @param cityOrMunicipality city name
     * @param stateOrProvince state or province
     * @param postalCode postal code
     * @param countryAlpha3 ISO-3316 3-letter country code
     * @return simply ISO address
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public static Ad create(String streetAddressLine, String deliveryAddressLine, String cityOrMunicipality,
            String stateOrProvince, String postalCode, String countryAlpha3) {
        Ad iso = new Ad();
        List<Adxp> l = new ArrayList<Adxp>();
        iso.setPart(l);
        setValue(l, streetAddressLine, AddressPartType.AL);
        setValue(l, deliveryAddressLine, AddressPartType.DAL);
        setValue(l, cityOrMunicipality, AddressPartType.CTY);
        setValue(l, stateOrProvince, AddressPartType.STA);
        setValue(l, postalCode, AddressPartType.ZIP);

        Adxp x;
        x = Adxp.createAddressPart(AddressPartType.CNT);
        x.setCode(countryAlpha3);
        l.add(x);
        return iso;
    }
}
