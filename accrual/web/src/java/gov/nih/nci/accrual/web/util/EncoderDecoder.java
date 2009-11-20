package gov.nih.nci.accrual.web.util;

import java.io.IOException;
import org.apache.log4j.Logger;
import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Bala Nair
 */
@SuppressWarnings({ "PMD" })
public class EncoderDecoder {

    private static final Logger LOG = Logger.getLogger(EncoderDecoder.class);
    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();
    
    /**
     * 
     * @param data data
     * @return encoded String
     */
    public static String encodeString(String data) {
        return encoder.encode(data.getBytes());
    }

    /**
     * 
     * @param data data
     * @return decoded String
     */
    public static String decodeString(String data) {
        String decodeString = null;
        try {
            byte[] decodeArray = decoder.decodeBuffer(data);
            decodeString = new String(decodeArray);
            LOG.info("decoded String " + decodeString);
        } catch (IOException e) {
            LOG.error("Error in EncoderDecoder.decodeString");
        }
        return decodeString;
    }
}