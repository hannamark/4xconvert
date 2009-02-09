package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DSET_TELTransformer<G extends Tel> implements Transformer<org.iso._21090.DSETTEL, DSet<G>> {
    protected static Logger logger = LogManager.getLogger(DSET_TELTransformer.class);
    static final QName telQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TEL");
    static final QName telEmailQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TELEmail");
    static final QName telPersonQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TELPerson");
    static final QName telPhoneQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TELPhone");
    static final QName telUrlQName = new QName("http://isodatatypes.coppa.nci.nih.gov", "TELUrl");
    static final Set<QName> telQNameSet = new HashSet<QName>();
    static {
        telQNameSet.add(telQName);
        telQNameSet.add(telPhoneQName);
        telQNameSet.add(telEmailQName);
        telQNameSet.add(telPersonQName);
        telQNameSet.add(telUrlQName);
    }

    public DSet<G> transform(org.iso._21090.DSETTEL input) throws DtoTransformException {
        DSet<G> res = new DSet<G>();
        res = transform(input, res);
        return res;
    }

    public DSet<G> transform(org.iso._21090.DSETTEL input, DSet<G> res) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        try {
            Set<G> results = new HashSet<G>();
            for (org.iso._21090.TEL element : input.getItem()) {
                gov.nih.nci.coppa.iso.Tel tel_iso = new TELTransformer().transform(element);
                results.add((G) tel_iso);
            }
            return res;
        } catch (Exception e) {
            logger.error("Error transforming DSet", e);
            throw new DtoTransformException("Error transforming DSet", e);
        }
    }

    public org.iso._21090.DSETTEL transform(DSet<G> input) throws DtoTransformException {
        org.iso._21090.DSETTEL res = new org.iso._21090.DSETTEL();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.DSETTEL transform(DSet<G> input, org.iso._21090.DSETTEL res) throws DtoTransformException {
        if (input == null)
            return null;
        try {
            for (Tel element : input.getItem()) {
                res.getItem().add(new TELTransformer().transform(element));
            }
            return res;
        } catch (Exception e) {
            logger.error("Error transforming DSet", e);
            throw new DtoTransformException("Error transforming DSet", e);
        }
    }
}
