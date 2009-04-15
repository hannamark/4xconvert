package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.En;
import gov.nih.nci.coppa.iso.EnOn;
import gov.nih.nci.coppa.iso.EnPn;
import gov.nih.nci.coppa.iso.Enxp;

import java.util.List;

import org.iso._21090.EN;
import org.iso._21090.ENON;
import org.iso._21090.ENPN;
import org.iso._21090.ENXP;

/**
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 */
public abstract class ENTransformer<ENXX extends EN, EnXx extends En> implements Transformer<ENXX, EnXx> {

    /**
     * Public singleton.
     */
    public static final ENONTransformer ENON_INSTANCE = ENONTransformer.INSTANCE;
    /**
     * Public singleton.
     */
    public static final ENPNTransformer ENPN_INSTANCE = ENPNTransformer.INSTANCE;

    /**
     * Ctr.
     */
    protected ENTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public ENXX toXml(EnXx input) {
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

    /**
     * {@inheritDoc}
     */
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

    /**
     * @return newly constructed xml object.
     */
    protected abstract ENXX newXml();

    /**
     * @return newly constructed dto object.
     */
    protected abstract EnXx newDto();

    /**
     * Org name transformer.
     */
    public static class ENONTransformer extends ENTransformer<ENON, EnOn> {

        /**
         * Public singleton.
         */
        public static final ENONTransformer INSTANCE = new ENONTransformer();

        /**
         * {@inheritDoc}
         */
        @Override
        protected ENON newXml() {
            return new ENON();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected EnOn newDto() {
            return new EnOn();
        }
    }

    /**
     * Person name transformer.
     */
    public static class ENPNTransformer extends ENTransformer<ENPN, EnPn> {

        /**
         * Public singleton.
         */
        public static final ENPNTransformer INSTANCE = new ENPNTransformer();

        /**
         * {@inheritDoc}
         */
        @Override
        protected ENPN newXml() {
            return new ENPN();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected EnPn newDto() {
            return new EnPn();
        }
    };
}
