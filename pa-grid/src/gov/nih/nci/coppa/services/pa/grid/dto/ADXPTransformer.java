package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.AdxpAdl;
import gov.nih.nci.coppa.iso.AdxpAl;
import gov.nih.nci.coppa.iso.AdxpBnn;
import gov.nih.nci.coppa.iso.AdxpBnr;
import gov.nih.nci.coppa.iso.AdxpBns;
import gov.nih.nci.coppa.iso.AdxpCar;
import gov.nih.nci.coppa.iso.AdxpCen;
import gov.nih.nci.coppa.iso.AdxpCnt;
import gov.nih.nci.coppa.iso.AdxpCpa;
import gov.nih.nci.coppa.iso.AdxpCty;
import gov.nih.nci.coppa.iso.AdxpDal;
import gov.nih.nci.coppa.iso.AdxpDel;
import gov.nih.nci.coppa.iso.AdxpDinst;
import gov.nih.nci.coppa.iso.AdxpDinsta;
import gov.nih.nci.coppa.iso.AdxpDinstq;
import gov.nih.nci.coppa.iso.AdxpDir;
import gov.nih.nci.coppa.iso.AdxpDmod;
import gov.nih.nci.coppa.iso.AdxpDmodid;
import gov.nih.nci.coppa.iso.AdxpInt;
import gov.nih.nci.coppa.iso.AdxpPob;
import gov.nih.nci.coppa.iso.AdxpPre;
import gov.nih.nci.coppa.iso.AdxpSal;
import gov.nih.nci.coppa.iso.AdxpSta;
import gov.nih.nci.coppa.iso.AdxpStb;
import gov.nih.nci.coppa.iso.AdxpStr;
import gov.nih.nci.coppa.iso.AdxpSttyp;
import gov.nih.nci.coppa.iso.AdxpUnid;
import gov.nih.nci.coppa.iso.AdxpUnit;
import gov.nih.nci.coppa.iso.AdxpZip;

import java.util.HashMap;
import java.util.Map;

import org.iso._21090.ADXP;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
final class ADXPTransformer implements Transformer<org.iso._21090.ADXP, Adxp> {

    public static final ADXPTransformer INSTANCE = new ADXPTransformer();

    private ADXPTransformer() {
    }

    public ADXP toXml(Adxp input) {
        // Don't worry about null here - this is a package-protected class and
        // the AD converter
        // has the responsibility to detect null.
        ADXP x = new ADXP();
        x.setCode(input.getCode());
        x.setValue(input.getValue());
        org.iso._21090.AddressPartType type = AddressPartTypeTransformer.INSTANCE.toXml(input.getType());
        x.setType(type);
        return x;
    }

    public Adxp toDto(ADXP input) {
        // Don't worry about null here - this is a package-protected class and
        // the AD converter
        // has the responsibility to detect null.
        AddressPartType type = AddressPartTypeTransformer.INSTANCE.toDto(input.getType());
        Adxp d = createAddressPart(type);
        d.setCode(input.getCode());
        d.setValue(input.getValue());
        return d;
    }

    /**
     * factory method to create an address part.
     *
     * @param type the typeof the part to create.
     * @return an address part with the
     */
    public static Adxp createAddressPart(AddressPartType type) {
        if (type == null) {
            return new Adxp();
        }
        Adxp value = types.get(type);
        if (value == null) {
            throw new UnsupportedOperationException(type.name());
        }
        return value;
    }

    private static Map<AddressPartType, Adxp> types = new HashMap<AddressPartType, Adxp>();

    static {
        types.put(AddressPartType.ADL, new AdxpAdl());
        types.put(AddressPartType.AL, new AdxpAl());
        types.put(AddressPartType.BNN, new AdxpBnn());
        types.put(AddressPartType.BNR, new AdxpBnr());
        types.put(AddressPartType.BNS, new AdxpBns());
        types.put(AddressPartType.CAR, new AdxpCar());
        types.put(AddressPartType.CEN, new AdxpCen());
        types.put(AddressPartType.CNT, new AdxpCnt());
        types.put(AddressPartType.CPA, new AdxpCpa());
        types.put(AddressPartType.CTY, new AdxpCty());
        types.put(AddressPartType.DAL, new AdxpDal());
        types.put(AddressPartType.DEL, new AdxpDel());
        types.put(AddressPartType.DINST, new AdxpDinst());
        types.put(AddressPartType.DINSTA, new AdxpDinsta());
        types.put(AddressPartType.DINSTQ, new AdxpDinstq());
        types.put(AddressPartType.DIR, new AdxpDir());
        types.put(AddressPartType.DMOD, new AdxpDmod());
        types.put(AddressPartType.DMODID, new AdxpDmodid());
        types.put(AddressPartType.INT, new AdxpInt());
        types.put(AddressPartType.POB, new AdxpPob());
        types.put(AddressPartType.PRE, new AdxpPre());
        types.put(AddressPartType.SAL, new AdxpSal());
        types.put(AddressPartType.STA, new AdxpSta());
        types.put(AddressPartType.STB, new AdxpStb());
        types.put(AddressPartType.STR, new AdxpStr());
        types.put(AddressPartType.STTYP, new AdxpSttyp());
        types.put(AddressPartType.UNID, new AdxpUnid());
        types.put(AddressPartType.UNIT, new AdxpUnit());
        types.put(AddressPartType.ZIP, new AdxpZip());
    }
}
