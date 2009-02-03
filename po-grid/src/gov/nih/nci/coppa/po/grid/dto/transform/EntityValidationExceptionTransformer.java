package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.po.StringMap;
import gov.nih.nci.coppa.po.StringMapItem;
import gov.nih.nci.coppa.po.grid.stubs.types.EntityValidationFault;
import gov.nih.nci.po.service.EntityValidationException;

import java.util.Map;
import java.util.Set;

public class EntityValidationExceptionTransformer implements Transformer< EntityValidationException,EntityValidationFault> {

	public EntityValidationFault transform(EntityValidationException input)
			throws DtoTransformException {
		EntityValidationFault res = new EntityValidationFault();
		res = transform(input,res);
		return res;
	}

	public EntityValidationFault transform(EntityValidationException input,
			EntityValidationFault res) throws DtoTransformException {
		Map<String, String[]> errors = input.getErrors();
		if (errors == null){
			res.setErrors(null);
			return res;
		}
		Set<String> keys = errors.keySet();
		StringMap map_iso = new StringMap();
		map_iso.setItem(new StringMapItem[keys.size()]);
		int i=0;
		for (String key:keys) {
		    StringMapItem item = new StringMapItem();
			item.setKey(key);
			item.setValue(errors.get(key));
			map_iso.setItem(i++, item);
		}
		res.setErrors(map_iso);
		return res;
	}





}
