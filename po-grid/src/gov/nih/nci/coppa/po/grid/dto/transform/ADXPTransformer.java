package gov.nih.nci.coppa.po.grid.dto.transform;

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

import org.iso._21090.ADXP;

/**
 * Transforms the parts of an address. Should only be used by the ADTransformer class.
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

        switch (type) {
        case ADL:
            return new AdxpAdl();
        case AL:
            return new AdxpAl();
        case BNN:
            return new AdxpBnn();
        case BNR:
            return new AdxpBnr();
        case BNS:
            return new AdxpBns();
        case CAR:
            return new AdxpCar();
        case CEN:
            return new AdxpCen();
        case CNT:
            return new AdxpCnt();
        case CPA:
            return new AdxpCpa();
        case CTY:
            return new AdxpCty();
        case DAL:
            return new AdxpDal();
        case DEL:
            return new AdxpDel();
        case DINST:
            return new AdxpDinst();
        case DINSTA:
            return new AdxpDinsta();
        case DINSTQ:
            return new AdxpDinstq();
        case DIR:
            return new AdxpDir();
        case DMOD:
            return new AdxpDmod();
        case DMODID:
            return new AdxpDmodid();
        case INT:
            return new AdxpInt();
        case POB:
            return new AdxpPob();
        case PRE:
            return new AdxpPre();
        case SAL:
            return new AdxpSal();
        case STA:
            return new AdxpSta();
        case STB:
            return new AdxpStb();
        case STR:
            return new AdxpStr();
        case STTYP:
            return new AdxpSttyp();
        case UNID:
            return new AdxpUnid();
        case UNIT:
            return new AdxpUnit();
        case ZIP:
            return new AdxpZip();

            // there must be a new type added
        default:
            throw new UnsupportedOperationException(type.name());
        }
    }
}
