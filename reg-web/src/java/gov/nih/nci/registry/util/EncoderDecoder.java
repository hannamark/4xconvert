package gov.nih.nci.registry.util;


import org.apache.commons.codec.binary.Base64;
/**
 *
 * @author Bala Nair
 */

public class EncoderDecoder {
    /**
     *
     * @param data data
     * @return encoded String
     */
    public String encodeString(String data) {
        return  new String(Base64.encodeBase64(data.getBytes()));
    }

    /**
     *
     * @param data data
     * @return decoded String
     */
    public String decodeString(String data)  {
        return new String(Base64.decodeBase64(data.getBytes()));
    }
}
