package gov.nih.nci.coppa.services.grid.dto.transform.iso;

import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.TelEmail;
import gov.nih.nci.coppa.iso.TelPerson;
import gov.nih.nci.coppa.iso.TelPhone;
import gov.nih.nci.coppa.iso.TelUrl;

import java.net.URI;
import java.net.URISyntaxException;

import org.iso._21090.TEL;
import org.iso._21090.TELEmail;
import org.iso._21090.TELPerson;
import org.iso._21090.TELPhone;
import org.iso._21090.TELUrl;

/**
 * Transforms telecoms.
 */
public final class TELTransformer implements Transformer<TEL, Tel> {

    /**
     * Public singleton.
     */
    public static final TELTransformer INSTANCE = new TELTransformer();

    private TELTransformer() {
    }

    /**
     * {@inheritDoc}
     */
    public TEL toXml(Tel input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        TEL x;
        if (input instanceof TelEmail) {
            x = new TELEmail();
        } else if (input instanceof TelPhone) {
            x = new TELPhone();
        } else if (input instanceof TelPerson) {
            x = new TELPerson();
        } else if (input instanceof TelUrl) {
            x = new TELUrl();
        } else {
            x = new TEL();
        }
        copyToXml(input, x);
        return x;
    }

    private static void copyToXml(Tel source, TEL target) {
        URI u = source.getValue();
        if (u != null) {
            target.setValue(u.toString());
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
        }
    }

    /**
     * {@inheritDoc}
     */
    public Tel toDto(TEL input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Tel d;
        if (input instanceof TELEmail) {
            d = new TelEmail();
        } else if (input instanceof TELPhone) {
            d = new TelPhone();
        } else if (input instanceof TELPerson) {
            d = new TelPerson();
        } else if (input instanceof TELUrl) {
            d = new TelUrl();
        } else {
            d = new Tel();
        }
        copyToDto(input, d);
        return d;
    }

    private static void copyToDto(TEL source, Tel target) throws DtoTransformException {
        String v = source.getValue();
        if (v != null) {
            try {
                target.setValue(new URI(v));
            } catch (URISyntaxException ex) {
                throw new DtoTransformException("error converting " + source.getClass().getSimpleName(), ex);
            }
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        }
    }

}
