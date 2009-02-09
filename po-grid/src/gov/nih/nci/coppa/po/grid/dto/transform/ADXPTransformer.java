package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Adxp;

import java.util.ArrayList;
import java.util.List;

public class ADXPTransformer implements Transformer<Adxp, org.iso._21090.ADXP> {

    public Adxp transform(org.iso._21090.ADXP input) throws DtoTransformException {
        if (input == null)
            return null;
        Adxp res = Adxp.createAddressPart(new AddressPartTypeTransformer().transform(input.getType()));
        res = transform(input, res);
        return res;
    }

    public Adxp transform(org.iso._21090.ADXP input, Adxp res) throws DtoTransformException {
        if (input == null)
            return res;
        if (res == null)
            res = Adxp.createAddressPart(new AddressPartTypeTransformer().transform(input.getType()));
        res.setCode(input.getCode());
        res.setValue(input.getValue());
        return res;
    }

    /*
     * public List<Adxp> transform(SetOfADXP input)throws DtoTransformException { if (input == null) return null;
     * List<Adxp> adxps_iso = new ArrayList<Adxp>(); org.iso._21090.ADXP[] adxps =input.getAdxp(); if (adxps!=null){ for
     * (org.iso._21090.ADXP adxp:adxps) { Adxp adxp_iso = transform(adxp); adxps_iso.add(adxp_iso); } } return
     * adxps_iso; }
     */

    public List<Adxp> transform(org.iso._21090.ADXP[] input) throws DtoTransformException {
        if (input == null)
            return null;
        List<Adxp> adxps_iso = new ArrayList<Adxp>(input.length);
        org.iso._21090.ADXP[] adxps = input;
        if (adxps != null) {
            for (org.iso._21090.ADXP adxp : adxps) {
                Adxp adxp_iso = transform(adxp);
                adxps_iso.add(adxp_iso);
            }
        }
        return adxps_iso;
    }

    public void transformADXP(List<org.iso._21090.ADXP> input, List<Adxp> res) throws DtoTransformException {
        if (input == null)
            return;
        for (org.iso._21090.ADXP adxp : input) {
            Adxp adxp_iso = transform(adxp);
            res.add(adxp_iso);
        }
    }

    public org.iso._21090.ADXP transform(Adxp input) throws DtoTransformException {
        org.iso._21090.ADXP res = new org.iso._21090.ADXP();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.ADXP transform(Adxp input, org.iso._21090.ADXP res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setCode(input.getCode());
        res.setValue(input.getValue());
        res.setType(new AddressPartTypeTransformer().transform(input.getType()));
        return res;
    }

    /*
     * public SetOfADXP transform(List<Adxp> input)throws DtoTransformException { if (input == null) return null;
     * SetOfADXP part = new SetOfADXP(); part.setAdxp(new org.iso._21090.ADXP[input.size()] ); int i=0; for
     * (gov.nih.nci.coppa.iso.Adxp adxp_iso:input) { org.iso._21090.ADXP adxp = transform(adxp_iso);
     * part.setAdxp(i++,adxp); } return part; }
     */

    public org.iso._21090.ADXP[] transform(List<Adxp> input) throws DtoTransformException {
        if (input == null)
            return null;
        org.iso._21090.ADXP[] part = new org.iso._21090.ADXP[input.size()];
        int i = 0;
        for (gov.nih.nci.coppa.iso.Adxp adxp_iso : input) {
            org.iso._21090.ADXP adxp = transform(adxp_iso);
            part[i++] = adxp;
        }
        return part;
    }

    public void transform(List<Adxp> input, List<org.iso._21090.ADXP> res) throws DtoTransformException {
        if (input == null)
            return;
        for (gov.nih.nci.coppa.iso.Adxp adxp_iso : input) {
            org.iso._21090.ADXP adxp = transform(adxp_iso);
            res.add(adxp);
        }
    }
}
