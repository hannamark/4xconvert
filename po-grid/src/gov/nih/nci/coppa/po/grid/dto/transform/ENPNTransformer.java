package gov.nih.nci.coppa.po.grid.dto.transform;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import gov.nih.nci.coppa.iso.EnPn;

public class ENPNTransformer implements Transformer<org.iso._21090.ENPN, EnPn> {
    protected static Logger logger = LogManager.getLogger(ENPNTransformer.class);

	
	public EnPn transform(org.iso._21090.ENPN input) throws DtoTransformException {
		EnPn res = new EnPn();
		res = transform(input,res);
 		return res;
	}

	
	public EnPn transform(org.iso._21090.ENPN input, EnPn res) throws DtoTransformException {
		if (input == null) return null;
        res = (EnPn)new ENTransformer().transform(input, res);
		return res;
	}

	public org.iso._21090.ENPN transform(EnPn input) throws DtoTransformException {
		org.iso._21090.ENPN res = new org.iso._21090.ENPN();
		res = transform(input,res);
 		return res;
	}

	
	public org.iso._21090.ENPN transform(EnPn input, org.iso._21090.ENPN res) throws DtoTransformException {
		if (input == null) return null;
        res = (org.iso._21090.ENPN)new ENTransformer().transform(input, res);
		return res;
	}

}
