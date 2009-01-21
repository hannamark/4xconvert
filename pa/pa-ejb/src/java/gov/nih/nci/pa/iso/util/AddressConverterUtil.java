package gov.nih.nci.pa.iso.util;

import gov.nih.nci.coppa.iso.Ad;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpZip;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author Harsha
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity"  })

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
        if (StringUtils.isNotBlank(deliveryAddressLine)) {
            setValue(l, deliveryAddressLine, AddressPartType.ADL);
        }
        setValue(l, cityOrMunicipality, AddressPartType.CTY);
        setValue(l, stateOrProvince, AddressPartType.STA);
        setValue(l, postalCode, AddressPartType.ZIP);

        if (countryAlpha3 != null) {
            Adxp x;
            x = Adxp.createAddressPart(AddressPartType.CNT);
            x.setCode(countryAlpha3);
            x.setValue("adxp.value is required");
            l.add(x);
        }
        return iso;
    }
    
    /**
     * converts the iso ad to address.
     * @param ad iso address
     * @return appended string buffer
     */
    public static String convertToAddress(Ad ad) {
        
        if (ad == null || ad.getPart() == null || ad.getPart().isEmpty()) {
            return null;
        }
        List<Adxp> adxpList = ad.getPart();
        StringBuffer sb = new StringBuffer();
        for (Adxp adxp : adxpList) {
            
            if (adxp instanceof AdxpAl) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCty) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpSta) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpZip) {
                sb.append(adxp.getValue()).append(',');
            }
            if (adxp instanceof AdxpCnt) {
                sb.append(adxp.getCode());
            }
        }
        if (sb.lastIndexOf(",") > 0) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        return sb.toString();
        
    }
    
}
