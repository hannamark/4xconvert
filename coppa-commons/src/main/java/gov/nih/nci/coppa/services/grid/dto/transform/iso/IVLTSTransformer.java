package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import org.iso._21090.IVLTS;
import org.iso._21090.TS;

import gov.nih.nci.coppa.iso.Ivl;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.Transformer;

/**
 * Transforms timestamp intervals.
 */
public final class IVLTSTransformer implements Transformer<IVLTS, Ivl<Ts>> {

	/**
	 * Public singleton.
	 */
	public static final IVLTSTransformer INSTANCE = new IVLTSTransformer();
	
	private IVLTSTransformer() {
		// intentionally blank
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Ivl<Ts> toDto(IVLTS input) throws DtoTransformException {
		if (input == null) {
			return null;
		}
		Ivl<Ts> result = new Ivl<Ts>();
		// PO-1054 - any not in xsd, but is in our localization
		//result.setAny(input.getAny());
		result.setHigh(TSTransformer.INSTANCE.toDto(input.getHigh()));
		result.setHighClosed(input.isHighClosed());
		result.setLow(TSTransformer.INSTANCE.toDto(input.getLow()));
		result.setLowClosed(input.isLowClosed());
		result.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(input.getNullFlavor()));
		result.setOriginalText(EDTextTransformer.INSTANCE.toDto(input.getOriginalText()));
		// Cast from QTY -> TS is ok by invariant in 21090 Sec. 7.11.8.3.5
		result.setWidth(TSTransformer.INSTANCE.toDto((TS) input.getWidth())); 
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	public IVLTS toXml(Ivl<Ts> input) throws DtoTransformException {
		if (input == null) {
			return null;
		}
		IVLTS result = new IVLTS();
		// PO-1054 - where is any?
		//result.setAny(...);
		result.setHigh(TSTransformer.INSTANCE.toXml(input.getHigh()));
		result.setHighClosed(input.getHighClosed());
		result.setLow(TSTransformer.INSTANCE.toXml(input.getLow()));
		result.setLowClosed(input.getLowClosed());
		result.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
		result.setOriginalText(EDTextTransformer.INSTANCE.toXml(input.getOriginalText()));
		// Cast from QTY -> TS is ok by invariant in 21090 Sec. 7.11.8.3.5
		result.setWidth(TSTransformer.INSTANCE.toXml((Ts) input.getWidth()));
		
		return result;
	}
	
}
