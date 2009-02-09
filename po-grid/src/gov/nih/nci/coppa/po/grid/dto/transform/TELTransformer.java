package gov.nih.nci.coppa.po.grid.dto.transform;

import gov.nih.nci.coppa.iso.Tel;

import java.net.URI;
import java.net.URISyntaxException;

import org.iso._21090.TELEmail;
import org.iso._21090.TELPerson;
import org.iso._21090.TELPhone;
import org.iso._21090.TELUrl;

public class TELTransformer implements Transformer<org.iso._21090.TEL, gov.nih.nci.coppa.iso.Tel> {

    public Tel transform(org.iso._21090.TEL input) throws DtoTransformException {
        Tel res = null;
        if (input instanceof TELEmail) {
            res = new gov.nih.nci.coppa.iso.TelEmail();
        } else if (input instanceof TELPhone) {
            res = new gov.nih.nci.coppa.iso.TelPhone();
        } else if (input instanceof TELPerson) {
            res = new gov.nih.nci.coppa.iso.TelPerson();
        } else if (input instanceof TELUrl) {
            res = new gov.nih.nci.coppa.iso.TelUrl();
        } else {
            res = new gov.nih.nci.coppa.iso.Tel();
        }
        res = transform(input, res);
        return res;
    }

    public Tel transform(org.iso._21090.TEL input, Tel res) throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        if (input.getValue() != null) {
            try {
                URI uri = new URI(input.getValue().toString());
                res.setValue(uri);
            } catch (URISyntaxException se) {
                throw new DtoTransformException(se);
            }
        }

        return res;
    }

    public org.iso._21090.TEL transform(gov.nih.nci.coppa.iso.Tel input) throws DtoTransformException {
        org.iso._21090.TEL res = new org.iso._21090.TEL();
        if (input instanceof gov.nih.nci.coppa.iso.TelEmail) {
            res = new TELEmail();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelPhone) {
            res = new TELPhone();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelPerson) {
            res = new TELPerson();
        } else if (input instanceof gov.nih.nci.coppa.iso.TelUrl) {
            res = new TELUrl();
        } else {
            res = new org.iso._21090.TEL();
        }
        res = transform(input, res);
        return res;
    }

    public org.iso._21090.TEL transform(gov.nih.nci.coppa.iso.Tel input, org.iso._21090.TEL res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res.setNullFlavor(new NullFlavorTransformer().transform(input.getNullFlavor()));
        if (input.getValue() != null) {
            
                
                res.setValue(input.getValue().toString());
            
        }

        return res;
    }

}
