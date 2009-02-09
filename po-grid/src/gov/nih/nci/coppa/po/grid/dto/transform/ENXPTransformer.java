package gov.nih.nci.coppa.po.grid.dto.transform;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import gov.nih.nci.coppa.iso.Enxp;

public class ENXPTransformer implements Transformer<org.iso._21090.ENXP,gov.nih.nci.coppa.iso.Enxp> {
	 protected static Logger logger = LogManager.getLogger(ENXPTransformer.class);
	 
	 public  String escape(String input)throws DtoTransformException {
		  return input.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	 }
	
	public Enxp transform(org.iso._21090.ENXP input) throws DtoTransformException {
		if (input == null) return null;
		Enxp  res = new Enxp(new EntityNamePartTypeTransformer().transform(input.getType()));
		res = transform(input,res);
		return res;
	}

	
	public Enxp transform(org.iso._21090.ENXP input, Enxp res) throws DtoTransformException {
		if (input == null) return null;
		if ((res==null)&&(input.getType()!=null)) {
			res = new Enxp(new EntityNamePartTypeTransformer().transform(input.getType()));
		}
		res.setCode(input.getCode());
		res.setCodeSystem(input.getCodeSystem());
		res.setCodeSystemVersion(input.getCodeSystemVersion());
        EntityNamePartQualifierTransformer enpqt = new  EntityNamePartQualifierTransformer();
        enpqt.transform(input.getQualifier(), res.getQualifier());
        res.setValue(escape(input.getValue()));
		return res;
	}

	public org.iso._21090.ENXP transform(Enxp input) throws DtoTransformException {
		org.iso._21090.ENXP res = new org.iso._21090.ENXP();
		res = transform(input,res);
		return res;
	}

	
	public org.iso._21090.ENXP transform(Enxp input, org.iso._21090.ENXP res) throws DtoTransformException {
		if (input == null) return null;
		logger.debug("ENXP transformed:"+input.getValue());
		res.setCode(input.getCode());
		res.setCodeSystem(input.getCodeSystem());
		res.setCodeSystemVersion(input.getCodeSystemVersion());
        EntityNamePartQualifierTransformer enpqt = new  EntityNamePartQualifierTransformer();
        enpqt.transform(input.getQualifier(), res.getQualifier());
        res.setType(new EntityNamePartTypeTransformer().transform(input.getType()));
        res.setValue(input.getValue());
		return res;
	}	
	

}
