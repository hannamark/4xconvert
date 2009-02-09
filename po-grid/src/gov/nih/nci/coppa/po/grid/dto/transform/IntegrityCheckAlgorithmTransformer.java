package gov.nih.nci.coppa.po.grid.dto.transform;

import org.iso._21090.IntegrityCheckAlgorithm;

public class IntegrityCheckAlgorithmTransformer implements Transformer<IntegrityCheckAlgorithm, gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm> {

	
	public gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm transform(IntegrityCheckAlgorithm input) throws DtoTransformException {
		if (input == null) return null;
		gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm ica = gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm.valueOf(input.value());
		return ica;
	}

	
	public gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm transform(
			IntegrityCheckAlgorithm input,
			gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm res)
			throws DtoTransformException {
		if (input == null) return null;
		res = gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm.valueOf(input.value());
		return res;
	}
	
	public IntegrityCheckAlgorithm transform(gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm input) throws DtoTransformException {
		if (input == null) return null;
		IntegrityCheckAlgorithm ica = IntegrityCheckAlgorithm.fromValue(input.name());
		return ica;
	}

	
	public IntegrityCheckAlgorithm transform(
			gov.nih.nci.coppa.iso.IntegrityCheckAlgorithm input,
			IntegrityCheckAlgorithm res)
			throws DtoTransformException {
		if (input == null) return null;
		IntegrityCheckAlgorithm ica = IntegrityCheckAlgorithm.fromValue(input.name());
		return res;
	}	

}
