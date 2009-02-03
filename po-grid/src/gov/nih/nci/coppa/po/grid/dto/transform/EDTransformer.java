package gov.nih.nci.coppa.po.grid.dto.transform;


import org.iso._21090.TELUrl;

import gov.nih.nci.coppa.iso.Ed;

public class EDTransformer implements Transformer<org.iso._21090.ED,gov.nih.nci.coppa.iso.Ed> {

	
	public gov.nih.nci.coppa.iso.Ed transform(org.iso._21090.ED input) throws DtoTransformException {
        gov.nih.nci.coppa.iso.Ed res = new gov.nih.nci.coppa.iso.Ed();
        res = transform(input,res);
		return res;
	}

	
	public Ed transform(org.iso._21090.ED input, Ed res) throws DtoTransformException {
		if (input == null) return null;
        res.setCharset(input.getCharset());
        res.setCompression(new CompressionTransformer().transform(input.getCompression()));
        if (input.getData()!=null){
           res.setData(input.getData());
        }
        //ISO XSD description not supported
//        res.setDescription(new STTransformer().transform(input.getDescription()));
		if (input.getIntegrityCheck()!=null) {
		    res.setIntegrityCheck(input.getIntegrityCheck());
		}
		res.setMediaType(input.getMediaType());
		res.setValue(input.getValue());
		res.setXml(input.getXml().toString());
		res.setIntegrityCheckAlgorithm(new IntegrityCheckAlgorithmTransformer().transform(input.getIntegrityCheckAlgorithm()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setThumbnail(new EDTransformer().transform(input.getThumbnail()));
        res.setReference(new TELURLTransformer().transform((TELUrl) input.getReference()));
		return res;
	}
	
	public org.iso._21090.ED transform(gov.nih.nci.coppa.iso.Ed input) throws DtoTransformException {
		org.iso._21090.ED res = new org.iso._21090.ED();
        res = transform(input,res);
		return res;
	}

	
	public org.iso._21090.ED transform(gov.nih.nci.coppa.iso.Ed input, org.iso._21090.ED res) throws DtoTransformException {
		if (input == null) return null;
        res.setCharset(input.getCharset());
        res.setCompression(new CompressionTransformer().transform(input.getCompression()));
        if (input.getData()!=null){
           res.setData(input.getData());
        }
        //ISO XSD description not supported
//        res.setDescription(new STTransformer().transform(input.getDescription()));
		if (input.getIntegrityCheck()!=null) {
		    res.setIntegrityCheck(input.getIntegrityCheck());
		}
		res.setMediaType(input.getMediaType());
		res.setValue(input.getValue());
		res.setXml(input.getXml());
		res.setIntegrityCheckAlgorithm(new IntegrityCheckAlgorithmTransformer().transform(input.getIntegrityCheckAlgorithm()));
		res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
		res.setThumbnail(new EDTransformer().transform(input.getThumbnail()));
        res.setReference(new TELURLTransformer().transform(input.getReference()));
		return res;
	}	

}
