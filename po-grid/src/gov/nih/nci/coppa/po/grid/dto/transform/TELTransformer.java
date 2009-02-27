package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Tel;

import java.net.URI;
import java.net.URISyntaxException;

import org.iso._21090.TEL;
import org.iso._21090.TELEmail;
import org.iso._21090.TELPerson;
import org.iso._21090.TELPhone;
import org.iso._21090.TELUrl;

public class TELTransformer implements Transformer<org.iso._21090.TEL, gov.nih.nci.coppa.iso.Tel> {

    public static final TELTransformer INSTANCE = new TELTransformer();

    private TELTransformer() {}

    public TEL toXml(Tel input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        org.iso._21090.TEL x;
        if (input instanceof gov.nih.nci.coppa.iso.TelEmail) {
            x = new TELEmail();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelPhone) {
            x = new TELPhone();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelPerson) {
            x = new TELPerson();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelUrl) {
            x = new TELUrl();
        } else {
            x = new org.iso._21090.TEL();
        }
        copyToXml(input, x);
        return x;
    }

    public void copyToXml(Tel source, TEL target) throws DtoTransformException {
        URI u = source.getValue();
        if (u == null) {
            target.setValue(u.toString());
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toXml(source.getNullFlavor()));
        }
    }

    public Tel toDto(TEL input) throws DtoTransformException {
        if (input == null) {
            return null;
        }
        Tel d;
        if (input instanceof TELEmail) {
            d = new gov.nih.nci.coppa.iso.TelEmail();
        } else if (input instanceof TELPhone) {
            d = new gov.nih.nci.coppa.iso.TelPhone();
        } else if (input instanceof TELPerson) {
            d = new gov.nih.nci.coppa.iso.TelPerson();
        } else if (input instanceof TELUrl) {
            d = new gov.nih.nci.coppa.iso.TelUrl();
        } else {
            d = new gov.nih.nci.coppa.iso.Tel();
        }
        copyToDto(input, d);
        return d;
    }

    public void copyToDto(TEL source, Tel target) throws DtoTransformException {
        String v = source.getValue();
        if (v != null) {
            try {
                target.setValue(new URI(v));
            } catch (URISyntaxException ex) {
                throw new DtoTransformException("error converting "+source.getClass().getSimpleName(), ex);
            }
        } else {
            target.setNullFlavor(NullFlavorTransformer.INSTANCE.toDto(source.getNullFlavor()));
        }
    }

}
