package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;

import java.util.List;
import java.util.Set;

import org.iso._21090.DSETTEL;
import org.iso._21090.TEL;

public class DSET_TELTransformer implements Transformer<DSETTEL, DSet<Tel>> {

    public static final DSET_TELTransformer INSTANCE = new DSET_TELTransformer();

    private DSET_TELTransformer() {}


    public DSETTEL toXml(DSet<Tel> input) throws DtoTransformException {
        if (input == null) {
            return null;
            // or do we do DSETTEL.setNullFlavor(NullFlavor.NI); ? PO-853
        }
        DSETTEL x = new DSETTEL();
        Set<Tel> sItem = input.getItem();
        List<TEL> tItem = x.getItem(); // FIXME: prove null case in unit tests PO-853
        for (Tel element : sItem) {
            tItem.add(TELTransformer.INSTANCE.toXml(element));
        }
        return x;
    }

    public DSet<Tel> toDto(DSETTEL input) throws DtoTransformException {
        if (input == null || input.getNullFlavor() != null) {
            return null;
        }
        DSet<Tel> d = new DSet<Tel>();
        List<TEL> sItem = input.getItem();
        Set<Tel> tItem = d.getItem();
        for (TEL tel : sItem) {
            tItem.add(TELTransformer.INSTANCE.toDto(tel));
        }
        return d;
    }
}
