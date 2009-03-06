
package gov.nih.nci.coppa.po.grid.ser;

import javax.xml.namespace.QName;
import org.apache.axis.encoding.ser.BaseDeserializerFactory;

/**
 *
 * @author gax
 */
public class JaxbDeserializerFactory extends BaseDeserializerFactory {

    public JaxbDeserializerFactory(Class javaType, QName xmlType) {
        super(JaxbDeserializer.class, xmlType, javaType);
    }

}
