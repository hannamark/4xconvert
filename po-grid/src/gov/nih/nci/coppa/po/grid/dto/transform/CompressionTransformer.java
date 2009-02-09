package gov.nih.nci.coppa.po.grid.dto.transform;

import org.iso._21090.Compression;

public class CompressionTransformer implements
        Transformer<org.iso._21090.Compression, gov.nih.nci.coppa.iso.Compression> {

    public gov.nih.nci.coppa.iso.Compression transform(org.iso._21090.Compression input) throws DtoTransformException {
        if (input == null)
            return null;
        gov.nih.nci.coppa.iso.Compression compression = gov.nih.nci.coppa.iso.Compression.valueOf(input.value());

        return compression;
    }

    public gov.nih.nci.coppa.iso.Compression transform(org.iso._21090.Compression input,
            gov.nih.nci.coppa.iso.Compression res) throws DtoTransformException {
        if (input == null)
            return null;
        res = gov.nih.nci.coppa.iso.Compression.valueOf(input.value());

        return res;
    }

    public org.iso._21090.Compression transform(gov.nih.nci.coppa.iso.Compression input) throws DtoTransformException {
        if (input == null)
            return null;
        org.iso._21090.Compression compression = org.iso._21090.Compression.fromValue(input.name());

        return compression;
    }

    public org.iso._21090.Compression transform(gov.nih.nci.coppa.iso.Compression input, org.iso._21090.Compression res)
            throws DtoTransformException {
        if (input == null)
            return null;
        res = org.iso._21090.Compression.fromValue(input.name());

        return res;
    }

}
