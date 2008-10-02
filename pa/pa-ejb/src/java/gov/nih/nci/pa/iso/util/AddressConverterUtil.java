package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Harsha
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

//    private static String getValue(List<Adxp> l, AddressPartType addressPartType) {
//        String result = null;
//        for(Adxp lp : l) {
//            if (lp.getType().equals(addressPartType)) {
//                result = lp.getValue();
//            }
//        }
//        return result;
//    }
    
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
        if (StringUtils.isNotBlank(deliveryAddressLine)) {
            setValue(l, deliveryAddressLine, AddressPartType.ADL);
        }
        setValue(l, cityOrMunicipality, AddressPartType.CTY);
        setValue(l, stateOrProvince, AddressPartType.STA);
        setValue(l, postalCode, AddressPartType.ZIP);

        Adxp x;
        x = Adxp.createAddressPart(AddressPartType.CNT);
        x.setCode(countryAlpha3);
        l.add(x);
        return iso;
    }
    
//    public static String getStreetAdressLine(Ad adIn) {
//        return getValue(AddressPartType.AL);
//    }    
}
