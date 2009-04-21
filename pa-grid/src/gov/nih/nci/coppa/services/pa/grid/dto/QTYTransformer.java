package gov.nih.nci.coppa.services.pa.grid.dto;

import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.Pqv;
import gov.nih.nci.coppa.iso.Qty;
import gov.nih.nci.coppa.iso.Ts;
import gov.nih.nci.coppa.iso.UncertaintyType;

import org.iso._21090.INT;
import org.iso._21090.PQ;
import org.iso._21090.QTY;
import org.iso._21090.TS;

/**
 *
 * DO NOT EDIT!!! COPY/PASTE FROM PO-GRID.  See PO-927 for refactoring task.
 * If you need to modify this file (bug?), change in po-grid and re-import to this location.
 *
 *
 * @param <QTYX> extends QTY.
 * @param <Qtyx> extends Qtyx.
 */
public abstract class QTYTransformer<QTYX extends QTY, Qtyx extends Qty> implements Transformer<QTYX, Qtyx> {

    /**
     * protected const.
     */
    protected QTYTransformer() {
    }

    /**
     * @return newly constructed xml object.
     */
    protected abstract QTYX newXml();

    /**
     * @return newly constructed dto object.
     */
    protected abstract Qtyx newDto();

    /**
     * Moves Qty fields into a QTY object.
     * @param input Qty
     * @return QTY
     * @throws DtoTransformException same as toXml
     */
    protected QTYX transformBaseXml(Qtyx input) throws DtoTransformException {
        if (input == null) {
            return null;
        }

        QTYX x = newXml();


        if (input.getOriginalText() != null) {
            x.setOriginalText(EDTextTransformer.INSTANCE.toXml(input.getOriginalText()));
        }

        if (input.getUncertainty() != null) {
            if (input.getUncertainty() instanceof Int) {
                x.setUncertainty(INTTransformer.INSTANCE.toXml((Int) input.getUncertainty()));
            } else if (input.getUncertainty() instanceof Pqv) {
                x.setUncertainty(PQVTransformer.INSTANCE.toXml((Pqv) input.getUncertainty()));
            } else if (input.getUncertainty() instanceof Ts) {
                x.setUncertainty(TSTransformer.INSTANCE.toXml((Ts) input.getUncertainty()));
            } else {
                throw new DtoTransformException("Input is not a child of QTY");
            }
        }

        if (input.getUncertaintyType() != null) {
            x.setUncertaintyType(org.iso._21090
                    .UncertaintyType.valueOf(input.getUncertaintyType().name()));
        }

        return x;
    }

    /**
     * Moves QTY fields into a Qty object.
     * @param input QTY
     * @return Qty
     * @throws DtoTransformException same as toDto
     */
    public Qtyx transformBaseDto(QTYX input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Qtyx x = newDto();

        //QTY fields.
        if (input.getOriginalText() != null) {
            x.setOriginalText(EDTextTransformer.INSTANCE.toDto(input.getOriginalText()));
        }

        if (input.getUncertainty() != null) {
            if (input.getUncertainty() instanceof INT) {
                x.setUncertainty(INTTransformer.INSTANCE.toDto((INT) input.getUncertainty()));
            } else if (input.getUncertainty() instanceof PQ) {
                x.setUncertainty(PQVTransformer.INSTANCE.toDto((PQ) input.getUncertainty()));
            } else if (input.getUncertainty() instanceof TS) {
                x.setUncertainty(TSTransformer.INSTANCE.toDto((TS) input.getUncertainty()));
            } else {
                throw new DtoTransformException("Input is not a child of QTY");
            }
        }

        if (input.getUncertaintyType() != null) {
            x.setUncertaintyType(UncertaintyType.valueOf(input.getUncertaintyType().name()));
        }

        return x;
    }
}
