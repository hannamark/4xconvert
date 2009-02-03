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
		org.iso._21090.ENXP[] part = input.getPart();
		if (part==null)return res;
    	List<Enxp> part_iso = res.getPart();
		ENXPTransformer transformer = new ENXPTransformer();
		for (org.iso._21090.ENXP enxp : part) {
			Enxp enxp_iso = transformer.transform(enxp);
			if (enxp_iso!=null)part_iso.add(enxp_iso);
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
        res.setPart(new org.iso._21090.ENXP[part_iso.size()]);
		ENXPTransformer transformer = new ENXPTransformer();
		int i = 0;
		for (gov.nih.nci.coppa.iso.Enxp enxp_iso : part_iso) {
			logger.debug("Found part");
			org.iso._21090.ENXP enxp = transformer
					.transform(enxp_iso);
			res.setPart(i++, enxp);
		}
   		return res;
	}


}
