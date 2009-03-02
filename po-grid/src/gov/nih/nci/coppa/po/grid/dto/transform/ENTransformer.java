package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.En;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.Enxp;

import java.util.List;

import org.iso._21090.EN;
import org.iso._21090.ENON;
import org.iso._21090.ENPN;
import org.iso._21090.ENXP;

public abstract class ENTransformer<ENXX extends EN, EnXx extends En> implements Transformer<ENXX, EnXx> {

    public static final ENONTransformer ENON_INSTANCE = ENONTransformer.INSTANCE;
    public static final ENPNTransformer ENPN_INSTANCE = ENPNTransformer.INSTANCE;

    protected ENTransformer() {}

    public ENXX toXml(EnXx input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        ENXX d = newXml();
        d.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(input.getNullFlavor()));
        List<ENXP> tPart = d.getPart();
        for (Enxp enxp : input.getPart()) {
            tPart.add(ENXPTransformer.INSTANCE.toXml(enxp));
        }
        return d;
    }

    public EnXx toDto(ENXX input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        EnXx d = newDto();
        copyToDto(input, d);
        return d;
    }

    private void copyToDto(ENXX source, EnXx target) throws DtoTransformException {
        target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        List<ENXP> sPart = source.getPart();
        if (sPart == null || sPart.isEmpty()) {
            return;
        }

        List<Enxp> tPart = target.getPart();
        for (ENXP enxp : sPart) {
            tPart.add(ENXPTransformer.INSTANCE.toDto(enxp));
        }
    }

    protected abstract ENXX newXml();
    protected abstract EnXx newDto();


    public static class ENONTransformer extends ENTransformer<ENON, EnOn> {

        public static final ENONTransformer INSTANCE = new ENONTransformer();

        @Override
        protected ENON newXml() {
            return new ENON();
        }

        @Override
        protected EnOn newDto() {
            return new EnOn();
        }
    }

    public static class ENPNTransformer extends  ENTransformer<ENPN, EnPn> {

        public static final ENPNTransformer INSTANCE = new ENPNTransformer();

        @Override
        protected ENPN newXml() {
            return new ENPN();
        }

        @Override
        protected EnPn newDto() {
            return new EnPn();
        }
    };
}
