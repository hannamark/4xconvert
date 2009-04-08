package gov.nih.nci.coppa.services.pa.grid.ser;

import javax.xml.namespace.QName;
import org.apache.axis.encoding.ser.BaseSerializerFactory;

/**
 *
 * @author gax
 */
public class JaxbSerializerFactory extends BaseSerializerFactory {
    public JaxbSerializerFactory(Class javaType, QName xmlType) {
        super(JaxbSerializer.class, xmlType, javaType);
    }
}
