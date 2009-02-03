package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.NullFlavor;

public class NullFlavorTransformer implements Transformer<org.iso._21090.NullFlavor,NullFlavor> {

	
	public NullFlavor transform(org.iso._21090.NullFlavor input) throws DtoTransformException {
		if (input == null) return null;
		  NullFlavor nullFlavor = NullFlavor.valueOf(input.getValue());
 		return nullFlavor;
	}

	
	public NullFlavor transform(org.iso._21090.NullFlavor input,
			NullFlavor res) throws DtoTransformException {
		if (input == null) return null;
		  res = NullFlavor.valueOf(input.getValue());
		return res;
	}

	public org.iso._21090.NullFlavor transform(gov.nih.nci.coppa.iso.NullFlavor input) throws DtoTransformException {
		if (input == null) return null;
		org.iso._21090.NullFlavor nullFlavor = org.iso._21090.NullFlavor.fromValue(input.name());
 		return nullFlavor;
	}

	
	public org.iso._21090.NullFlavor transform(gov.nih.nci.coppa.iso.NullFlavor input,
			org.iso._21090.NullFlavor res) throws DtoTransformException {
		if (input == null) return null;
		  res = org.iso._21090.NullFlavor.fromValue(input.name());
		return res;
	}	
	
}
