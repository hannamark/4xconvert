package gov.nih.nci.po.data.convert.util;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author smatyas
 *
 */
public class AddressConverterUtil {
    private static void setValue(List<Adxp> l, String s, Adxp x) {
        if (StringUtils.isNotBlank(s)) {
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
     * @param countryName full country name
     * @return simply ISO address
     */
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public static Ad create(String streetAddressLine, String deliveryAddressLine, String cityOrMunicipality,
            String stateOrProvince, String postalCode, String countryAlpha3, String countryName) {
        Ad iso = new Ad();
        List<Adxp> l = new ArrayList<Adxp>();
        iso.setPart(l);
        setValue(l, streetAddressLine, new AdxpAl());
        if (StringUtils.isNotBlank(deliveryAddressLine)) {
            setValue(l, deliveryAddressLine, new AdxpAdl());
        }
        setValue(l, cityOrMunicipality, new AdxpCty());
        setValue(l, stateOrProvince, new AdxpSta());
        setValue(l, postalCode, new AdxpZip());

        Adxp x = new AdxpCnt();
        x.setValue(countryName);
        x.setCode(countryAlpha3);
        l.add(x);
        return iso;
    }
}
