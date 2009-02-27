package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;

import java.util.List;
import java.util.Set;


import org.iso._21090.DSETTEL;
import org.iso._21090.TEL;

public class DSET_TELTransformer implements Transformer<org.iso._21090.DSETTEL, DSet<Tel>> {

    public static final DSET_TELTransformer INSTANCE = new DSET_TELTransformer();

    private DSET_TELTransformer() {}


    public DSETTEL toXml(DSet<Tel> input) throws DtoTransformException {
        if (input == null) {
            return null;
            // or do we do DSETTEL.setNullFlavor(NullFlavor.NI); ?
        }
        DSETTEL x = new DSETTEL();
        copyToXml(input, x);
        return x;
    }

    public void copyToXml(DSet<Tel> source, DSETTEL target) throws DtoTransformException {
        Set<Tel> sItem = source.getItem();
        List<TEL> tItem = target.getItem();
        for (Tel element : sItem) {
            tItem.add(TELTransformer.INSTANCE.toXml(element));
        }
    }

    public DSet<Tel> toDto(DSETTEL input) throws DtoTransformException {
        if (input == null || input.getNullFlavor() != null) {
            return null;
        }
        DSet<Tel> d = new DSet<Tel>();
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(DSETTEL source, DSet<Tel> target) throws DtoTransformException {
        List<TEL> sItem = source.getItem();
        Set<Tel> tItem = target.getItem();
        for (TEL tel : sItem) {
            tItem.add(TELTransformer.INSTANCE.toDto(tel));
        }
    }

    
}
