package gov.nih.nci.coppa.po.grid.ser;

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
 *
 * @author gax
 */
public class JaxbSerializer implements Serializer {

    private static final Map<String, Marshaller> MAP = new HashMap<String, Marshaller>();

    /**
     * Serialize a JAXB object.
     *
     * @param name
     * @param attributes
     * @param value      this must be a JAXB object for marshalling
     * @param context
     * @throws IOException for XML schema noncompliance, bad object type, and any IO
     *                     trouble.
     */
    public void serialize(QName name,
                          Attributes attributes,
                          Object value,
                          SerializationContext context)
            throws IOException {
        try {
            Marshaller marshaller = MAP.get(value.getClass().getPackage().getName());
            if (marshaller == null) {
                JAXBContext jaxbContext = JAXBContext.newInstance(value.getClass().getPackage().getName());
                marshaller = jaxbContext.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
//                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                MAP.put(value.getClass().getPackage().getName(), marshaller);
            }

            marshaller.marshal(value, new Filter(context));

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
    }

    public String getMechanismType() {
        return Constants.AXIS_SAX;
    }

    /**
     * Return XML schema for the specified type, suitable for insertion into
     * the &lt;types&gt; element of a WSDL document, or underneath an
     * &lt;element&gt; or &lt;attribute&gt; declaration.
     *
     * @param javaType the Java Class we're writing out schema for
     * @param types    the Java2WSDL Types object which holds the context
     *                 for the WSDL being generated.
     * @return a type element containing a schema simpleType/complexType
     * @see org.apache.axis.wsdl.fromJava.Types
     */
    public Element writeSchema(Class javaType, Types types) throws Exception {
        return null;
    }
}
