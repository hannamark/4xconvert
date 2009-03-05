package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Ad;

import java.util.HashSet;

import java.util.Set;
import org.iso._21090.DSETAD;
import org.iso._21090.AD;

public class DSETADTransformer implements Transformer<DSETAD, DSet<Ad>> {

    public static final DSETADTransformer INSTANCE = new DSETADTransformer();

    private DSETADTransformer() {}

    public DSETAD toXml(DSet<Ad> input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        DSETAD x = new DSETAD();
        for (Ad ad : input.getItem()) {
            x.getItem().add(ADTransformer.INSTANCE.toXml(ad));
        }
        return x;
    }

    public DSet<Ad> toDto(DSETAD input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        DSet<Ad> x = new DSet<Ad>();
        Set<Ad> items = x.getItem();
        if (items == null) {
            items = new HashSet<Ad>();
            x.setItem(items);
        }
        for (AD ad : input.getItem()) {
            items.add(ADTransformer.INSTANCE.toDto(ad));
        }
        return x;
    }


}
