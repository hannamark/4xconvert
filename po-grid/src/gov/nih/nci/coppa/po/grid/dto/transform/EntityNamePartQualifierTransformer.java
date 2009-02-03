package gov.nih.nci.coppa.po.grid.dto.transform;

import org.iso._21090.EntityNamePartQualifier;

import java.util.HashSet;
import java.util.Set;

public class EntityNamePartQualifierTransformer implements Transformer<EntityNamePartQualifier,  gov.nih.nci.coppa.iso.EntityNamePartQualifier> {

	
	public gov.nih.nci.coppa.iso.EntityNamePartQualifier transform(
			EntityNamePartQualifier input) throws DtoTransformException {
		if (input==null) return null;
 		return gov.nih.nci.coppa.iso.EntityNamePartQualifier.valueOf(input.getValue());
	}

	
	public gov.nih.nci.coppa.iso.EntityNamePartQualifier transform(
			EntityNamePartQualifier input,
			gov.nih.nci.coppa.iso.EntityNamePartQualifier res)
			throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
		return gov.nih.nci.coppa.iso.EntityNamePartQualifier.valueOf(input.getValue());
	}
	
	public Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> transform(EntityNamePartQualifier[] input) throws DtoTransformException {
		if (input==null)return null;
		try {
		Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> res = new HashSet<gov.nih.nci.coppa.iso.EntityNamePartQualifier>();
	    	EntityNamePartQualifier[] els = input;
			for(EntityNamePartQualifier el:els) {
				gov.nih.nci.coppa.iso.EntityNamePartQualifier enpq_iso = transform(el);
				res.add(enpq_iso);
			}
		return res;
		}
		catch(Exception e) {
		}
		return null;

	}
	
	public org.iso._21090.EntityNamePartQualifier transform(
			gov.nih.nci.coppa.iso.EntityNamePartQualifier input) throws DtoTransformException {
		if (input==null) return null;
 		return org.iso._21090.EntityNamePartQualifier.fromValue(input.name());
	}

	
	public org.iso._21090.EntityNamePartQualifier transform(
			gov.nih.nci.coppa.iso.EntityNamePartQualifier input,
			org.iso._21090.EntityNamePartQualifier res)
			throws DtoTransformException {
		// TODO Auto-generated method stub
		if (input==null) return null;
		return org.iso._21090.EntityNamePartQualifier.fromValue(input.name());
	}	

	public EntityNamePartQualifier[]  transform(Set<gov.nih.nci.coppa.iso.EntityNamePartQualifier> input) throws DtoTransformException {
		if (input==null)return null;
		try {
			EntityNamePartQualifier[] res = new EntityNamePartQualifier[input.size()];
			int i=0;
			for(gov.nih.nci.coppa.iso.EntityNamePartQualifier el:input){
				res[i++] = transform(el);
			}
		    return res;
		}
		catch(Exception e) {
		}
		return null;

	}

}
