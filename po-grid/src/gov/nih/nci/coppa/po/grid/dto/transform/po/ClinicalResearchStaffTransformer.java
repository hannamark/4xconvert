package gov.nih.nci.coppa.po.grid.dto.transform.po;

import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.grid.dto.transform.CDTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSETADTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DSET_TELTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.po.grid.dto.transform.IITransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.Transformer;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;

/**
 * @author Vrushali
 *
 */
public class ClinicalResearchStaffTransformer implements Transformer<ClinicalResearchStaff,ClinicalResearchStaffDTO>{
	public static final ClinicalResearchStaffTransformer  INSTANCE = new ClinicalResearchStaffTransformer ();
	
	public ClinicalResearchStaffDTO toDto(ClinicalResearchStaff input)
			throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ClinicalResearchStaffDTO d = new ClinicalResearchStaffDTO();
        d.setIdentifier(IITransformer.INSTANCE.toDto(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toDto(input.getPlayerIdentifier()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toDto(input.getScoperIdentifier()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toDto(input.getPostalAddress()));
        d.setStatus(CDTransformer.INSTANCE.toDto(input.getStatus()));
        d.setTelecomAddress(DSET_TELTransformer.INSTANCE.toDto(input.getTelecomAddress()));
        return d;

	}

	@SuppressWarnings("unchecked")
	public ClinicalResearchStaff toXml(ClinicalResearchStaffDTO input)
			throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ClinicalResearchStaff d = new ClinicalResearchStaff();
        d.setIdentifier(IITransformer.INSTANCE.toXml(input.getIdentifier()));
        d.setPlayerIdentifier(IITransformer.INSTANCE.toXml(input.getPlayerIdentifier()));
        d.setScoperIdentifier(IITransformer.INSTANCE.toXml(input.getScoperIdentifier()));
        d.setPostalAddress(DSETADTransformer.INSTANCE.toXml(input.getPostalAddress()));
        d.setStatus(CDTransformer.INSTANCE.toXml(input.getStatus()));
        d.setTelecomAddress(DSET_TELTransformer.INSTANCE.toXml(input.getTelecomAddress()));
        return d;
	}

}
