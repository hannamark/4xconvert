package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;

public class IITransformer implements Transformer<org.iso._21090.II,Ii> {

	
	public Ii transform(org.iso._21090.II input) throws DtoTransformException {
        Ii ii = new Ii();
        ii = transform(input,ii);
		return ii;
	}

	
	public Ii transform(org.iso._21090.II input, Ii res) throws DtoTransformException {
		if (input == null) return null;
        Ii ii = res;
        ii.setExtension(input.getExtension());
        ii.setDisplayable(input.getDisplayable());
        ii.setIdentifierName(input.getIdentifierName());
        if (input.getReliability()!=null){
           ii.setReliability(IdentifierReliability.valueOf(input.getReliability().getValue()));
        }
        if (input.getScope()!=null){
           ii.setScope(IdentifierScope.valueOf(input.getScope().getValue()));
        }
        ii.setRoot(input.getRoot());
        ii.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		return ii;
	}
	
	public org.iso._21090.II transform(Ii input) throws DtoTransformException {
		org.iso._21090.II res = new org.iso._21090.II();
        res = transform(input,res);
		return res;
	}

	
	public org.iso._21090.II transform(Ii input, org.iso._21090.II res) throws DtoTransformException {
		if (input == null) return null;	
		res.setDisplayable(input.getDisplayable());
		res.setExtension(input.getExtension());
		res.setIdentifierName(input.getIdentifierName());
		res.setRoot(input.getRoot());
		res.setReliability(org.iso._21090.IdentifierReliability.fromValue(input.getReliability().name()));
		res.setScope(org.iso._21090.IdentifierScope.fromValue(input.getScope().name()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		return res;
	}
}
