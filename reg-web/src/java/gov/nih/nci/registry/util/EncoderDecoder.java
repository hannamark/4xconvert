package gov.nih.nci.registry.util;


import java.io.IOException;

import org.apache.log4j.Logger;

//CHECKSTYLE:OFF illegal import - PO-2388
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
//CHECKSTYLE:ON

/**
 *
 * @author Bala Nair
 */
@SuppressWarnings({ "PMD" })
public class EncoderDecoder {

    private static final Logger LOG = Logger.getLogger(EncoderDecoder.class);

    /**
     *
     * @param data data
     * @return encoded String
     */
    public String encodeString(String data) {
        BASE64Encoder base64 = new BASE64Encoder();
        String encoded = base64.encode(data.getBytes());
        return encoded;
    }


    /**
     *
     * @param data data
     * @return decoded String
     */
    public String decodeString(String data)  {
        BASE64Decoder b2 = new BASE64Decoder();

        String decodeString = null;
        try {
            byte[] decode = b2.decodeBuffer(data);
            decodeString = new String(decode);
        } catch (IOException e) {
            LOG.error("Exception in decoding String: " + data, e);
        }
        return decodeString;
    }
}
