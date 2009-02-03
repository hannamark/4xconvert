package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.EnOn;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.iso._21090.ENON;

public class ENONTransformer implements Transformer<ENON, EnOn> {

    protected static Logger logger = LogManager.getLogger(ENONTransformer.class);

    public EnOn transform(ENON input) throws DtoTransformException {
        EnOn res = new EnOn();
        res = transform(input, res);
        return res;
    }

    public EnOn transform(ENON input, EnOn res) throws DtoTransformException {
        if (input == null)
            return null;
        res = (EnOn) new ENTransformer().transform(input, res);
        return res;
    }

    public org.iso._21090.ENON transform(EnOn input) throws DtoTransformException {
        ENON res = new ENON();
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.ENON transform(EnOn input, ENON res) throws DtoTransformException {
        if (input == null)
            return null;
        res = (org.iso._21090.ENON) new ENTransformer().transform(input, res);
        return res;
    }

}
