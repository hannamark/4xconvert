package gov.nih.nci.coppa.services.pa.grid.ser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.wsdl.fromJava.Types;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;

/**
 * JAXB based serializer.
 *
 * @author gax
 */
public class JaxbSerializer implements Serializer {

    private static final long serialVersionUID = -3204978709399586948L;
    private static final Map<String, Marshaller> MAP = new HashMap<String, Marshaller>();

    /**
     * {@inheritDoc}
     */
    public void serialize(QName name, Attributes attributes, Object value, SerializationContext context)
            throws IOException {
        try {
            Marshaller marshaller = MAP.get(value.getClass().getPackage().getName());
            if (marshaller == null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(value.getClass().getPackage().getName());
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
                // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                MAP.put(value.getClass().getPackage().getName(), marshaller);
            }

            marshaller.marshal(value, new Filter(context));

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    public String getMechanismType() {
        return Constants.AXIS_SAX;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Element writeSchema(Class javaType, Types types) throws Exception {
        return null;
    }
}
