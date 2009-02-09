package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.En;
import gov.nih.nci.coppa.iso.Enxp;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ENTransformer implements Transformer<org.iso._21090.EN,gov.nih.nci.coppa.iso.En> {
	 protected static Logger logger = LogManager.getLogger(ENTransformer.class);
	
	public En transform(org.iso._21090.EN input) throws DtoTransformException {
		En res = new En();
		res = transform(input,res);
 		return res;
	}

	
	public En transform(org.iso._21090.EN input, En res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		ENXPTransformer transformer = new ENXPTransformer();
		for (org.iso._21090.ENXP enxp : input.getPart()) {
			Enxp enxp_iso = transformer.transform(enxp);
			res.getPart().add(enxp_iso);
        }
		return res;
	}

	
	public org.iso._21090.EN transform(gov.nih.nci.coppa.iso.En input) throws DtoTransformException {
		org.iso._21090.EN res = new org.iso._21090.EN();
		res = transform(input,res);
 		return res;
	}

	
	public org.iso._21090.EN transform(gov.nih.nci.coppa.iso.En input, org.iso._21090.EN res) throws DtoTransformException {
		if (input == null) return null;
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));

		List<Enxp> part_iso = input.getPart();
		if (part_iso==null) return res;
		ENXPTransformer transformer = new ENXPTransformer();
		for (gov.nih.nci.coppa.iso.Enxp enxp_iso : input.getPart()) {
			logger.debug("Found part");
			org.iso._21090.ENXP enxp = transformer
					.transform(enxp_iso);
			res.getPart().add(enxp);
		}
   		return res;
	}


}
